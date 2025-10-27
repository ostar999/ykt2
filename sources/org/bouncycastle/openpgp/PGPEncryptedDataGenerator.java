package org.bouncycastle.openpgp;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.DigestOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.bcpg.ContainedPacket;
import org.bouncycastle.bcpg.PublicKeyEncSessionPacket;
import org.bouncycastle.bcpg.S2K;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyEncSessionPacket;

/* loaded from: classes9.dex */
public class PGPEncryptedDataGenerator implements SymmetricKeyAlgorithmTags, StreamGenerator {
    public static final int S2K_SHA1 = 2;
    public static final int S2K_SHA224 = 11;
    public static final int S2K_SHA256 = 8;
    public static final int S2K_SHA384 = 9;
    public static final int S2K_SHA512 = 10;

    /* renamed from: c, reason: collision with root package name */
    private Cipher f27944c;
    private CipherOutputStream cOut;
    private int defAlgorithm;
    private Provider defProvider;
    private DigestOutputStream digestOut;
    private List methods;
    private boolean oldFormat;
    private BCPGOutputStream pOut;
    private SecureRandom rand;
    private boolean withIntegrityPacket;

    public abstract class EncMethod extends ContainedPacket {
        protected int encAlgorithm;
        protected Key key;
        protected byte[] sessionInfo;

        private EncMethod() {
        }

        public abstract void addSessionInfo(byte[] bArr) throws Exception;
    }

    public class PBEMethod extends EncMethod {
        S2K s2k;

        public PBEMethod(int i2, S2K s2k, Key key) {
            super();
            this.encAlgorithm = i2;
            this.s2k = s2k;
            this.key = key;
        }

        @Override // org.bouncycastle.openpgp.PGPEncryptedDataGenerator.EncMethod
        public void addSessionInfo(byte[] bArr) throws Exception {
            Cipher cipher = Cipher.getInstance(PGPUtil.getSymmetricCipherName(this.encAlgorithm) + "/CFB/NoPadding", PGPEncryptedDataGenerator.this.defProvider);
            cipher.init(1, this.key, new IvParameterSpec(new byte[cipher.getBlockSize()]), PGPEncryptedDataGenerator.this.rand);
            this.sessionInfo = cipher.doFinal(bArr, 0, bArr.length + (-2));
        }

        @Override // org.bouncycastle.bcpg.ContainedPacket
        public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
            bCPGOutputStream.writePacket(new SymmetricKeyEncSessionPacket(this.encAlgorithm, this.s2k, this.sessionInfo));
        }

        public Key getKey() {
            return this.key;
        }
    }

    public class PubMethod extends EncMethod {
        BigInteger[] data;
        PGPPublicKey pubKey;

        public PubMethod(PGPPublicKey pGPPublicKey) {
            super();
            this.pubKey = pGPPublicKey;
        }

        @Override // org.bouncycastle.openpgp.PGPEncryptedDataGenerator.EncMethod
        public void addSessionInfo(byte[] bArr) throws Exception {
            Provider provider;
            String str;
            int algorithm = this.pubKey.getAlgorithm();
            if (algorithm == 1 || algorithm == 2) {
                provider = PGPEncryptedDataGenerator.this.defProvider;
                str = "RSA/ECB/PKCS1Padding";
            } else {
                if (algorithm != 16) {
                    if (algorithm == 17) {
                        throw new PGPException("Can't use DSA for encryption.");
                    }
                    if (algorithm == 19) {
                        throw new PGPException("Can't use ECDSA for encryption.");
                    }
                    if (algorithm != 20) {
                        throw new PGPException("unknown asymmetric algorithm: " + this.pubKey.getAlgorithm());
                    }
                }
                provider = PGPEncryptedDataGenerator.this.defProvider;
                str = "ElGamal/ECB/PKCS1Padding";
            }
            Cipher cipher = Cipher.getInstance(str, provider);
            cipher.init(1, this.pubKey.getKey(PGPEncryptedDataGenerator.this.defProvider), PGPEncryptedDataGenerator.this.rand);
            byte[] bArrDoFinal = cipher.doFinal(bArr);
            int algorithm2 = this.pubKey.getAlgorithm();
            if (algorithm2 == 1 || algorithm2 == 2) {
                this.data = new BigInteger[]{new BigInteger(1, bArrDoFinal)};
                return;
            }
            if (algorithm2 != 16 && algorithm2 != 20) {
                throw new PGPException("unknown asymmetric algorithm: " + this.encAlgorithm);
            }
            int length = bArrDoFinal.length / 2;
            byte[] bArr2 = new byte[length];
            int length2 = bArrDoFinal.length / 2;
            byte[] bArr3 = new byte[length2];
            System.arraycopy(bArrDoFinal, 0, bArr2, 0, length);
            System.arraycopy(bArrDoFinal, length, bArr3, 0, length2);
            BigInteger[] bigIntegerArr = new BigInteger[2];
            this.data = bigIntegerArr;
            bigIntegerArr[0] = new BigInteger(1, bArr2);
            this.data[1] = new BigInteger(1, bArr3);
        }

        @Override // org.bouncycastle.bcpg.ContainedPacket
        public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
            bCPGOutputStream.writePacket(new PublicKeyEncSessionPacket(this.pubKey.getKeyID(), this.pubKey.getAlgorithm(), this.data));
        }
    }

    public PGPEncryptedDataGenerator(int i2, SecureRandom secureRandom, String str) {
        this(i2, secureRandom, Security.getProvider(str));
    }

    public PGPEncryptedDataGenerator(int i2, SecureRandom secureRandom, Provider provider) {
        this.withIntegrityPacket = false;
        this.oldFormat = false;
        this.methods = new ArrayList();
        this.defAlgorithm = i2;
        this.rand = secureRandom;
        this.defProvider = provider;
    }

    public PGPEncryptedDataGenerator(int i2, SecureRandom secureRandom, boolean z2, String str) {
        this.withIntegrityPacket = false;
        this.oldFormat = false;
        this.methods = new ArrayList();
        this.defAlgorithm = i2;
        this.rand = secureRandom;
        this.defProvider = Security.getProvider(str);
        this.oldFormat = z2;
    }

    public PGPEncryptedDataGenerator(int i2, SecureRandom secureRandom, boolean z2, Provider provider) {
        this.withIntegrityPacket = false;
        this.oldFormat = false;
        this.methods = new ArrayList();
        this.defAlgorithm = i2;
        this.rand = secureRandom;
        this.defProvider = provider;
        this.oldFormat = z2;
    }

    public PGPEncryptedDataGenerator(int i2, boolean z2, SecureRandom secureRandom, String str) {
        this(i2, z2, secureRandom, Security.getProvider(str));
    }

    public PGPEncryptedDataGenerator(int i2, boolean z2, SecureRandom secureRandom, Provider provider) {
        this.withIntegrityPacket = false;
        this.oldFormat = false;
        this.methods = new ArrayList();
        this.defAlgorithm = i2;
        this.rand = secureRandom;
        this.defProvider = provider;
        this.withIntegrityPacket = z2;
    }

    private void addCheckSum(byte[] bArr) {
        int i2 = 0;
        for (int i3 = 1; i3 != bArr.length - 2; i3++) {
            i2 += bArr[i3] & 255;
        }
        bArr[bArr.length - 2] = (byte) (i2 >> 8);
        bArr[bArr.length - 1] = (byte) i2;
    }

    private byte[] createSessionInfo(int i2, Key key) {
        byte[] encoded = key.getEncoded();
        byte[] bArr = new byte[encoded.length + 3];
        bArr[0] = (byte) i2;
        System.arraycopy(encoded, 0, bArr, 1, encoded.length);
        addCheckSum(bArr);
        return bArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private OutputStream open(OutputStream outputStream, long j2, byte[] bArr) throws IllegalStateException, NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, PGPException, InvalidAlgorithmParameterException {
        Key keyMakeRandomKey;
        if (this.cOut != null) {
            throw new IllegalStateException("generator already in open state");
        }
        if (this.methods.size() == 0) {
            throw new IllegalStateException("no encryption methods specified");
        }
        if (this.defProvider == null) {
            throw new IllegalStateException("provider resolves to null");
        }
        this.pOut = new BCPGOutputStream(outputStream);
        if (this.methods.size() == 1) {
            if (this.methods.get(0) instanceof PBEMethod) {
                keyMakeRandomKey = ((PBEMethod) this.methods.get(0)).getKey();
            } else {
                keyMakeRandomKey = PGPUtil.makeRandomKey(this.defAlgorithm, this.rand);
                try {
                    ((PubMethod) this.methods.get(0)).addSessionInfo(createSessionInfo(this.defAlgorithm, keyMakeRandomKey));
                } catch (Exception e2) {
                    throw new PGPException("exception encrypting session key", e2);
                }
            }
            this.pOut.writePacket((ContainedPacket) this.methods.get(0));
        } else {
            keyMakeRandomKey = PGPUtil.makeRandomKey(this.defAlgorithm, this.rand);
            byte[] bArrCreateSessionInfo = createSessionInfo(this.defAlgorithm, keyMakeRandomKey);
            for (int i2 = 0; i2 != this.methods.size(); i2++) {
                EncMethod encMethod = (EncMethod) this.methods.get(i2);
                try {
                    encMethod.addSessionInfo(bArrCreateSessionInfo);
                    this.pOut.writePacket(encMethod);
                } catch (Exception e3) {
                    throw new PGPException("exception encrypting session key", e3);
                }
            }
        }
        String symmetricCipherName = PGPUtil.getSymmetricCipherName(this.defAlgorithm);
        if (symmetricCipherName == null) {
            throw new PGPException("null cipher specified");
        }
        try {
            this.f27944c = this.withIntegrityPacket ? Cipher.getInstance(symmetricCipherName + "/CFB/NoPadding", this.defProvider) : Cipher.getInstance(symmetricCipherName + "/OpenPGPCFB/NoPadding", this.defProvider);
            this.f27944c.init(1, keyMakeRandomKey, new IvParameterSpec(new byte[this.f27944c.getBlockSize()]), this.rand);
            if (bArr == null) {
                if (this.withIntegrityPacket) {
                    BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(outputStream, 18, j2 + this.f27944c.getBlockSize() + 2 + 1 + 22);
                    this.pOut = bCPGOutputStream;
                    bCPGOutputStream.write(1);
                } else {
                    this.pOut = new BCPGOutputStream(outputStream, 9, j2 + this.f27944c.getBlockSize() + 2, this.oldFormat);
                }
            } else if (this.withIntegrityPacket) {
                BCPGOutputStream bCPGOutputStream2 = new BCPGOutputStream(outputStream, 18, bArr);
                this.pOut = bCPGOutputStream2;
                bCPGOutputStream2.write(1);
            } else {
                this.pOut = new BCPGOutputStream(outputStream, 9, bArr);
            }
            CipherOutputStream cipherOutputStream = new CipherOutputStream(this.pOut, this.f27944c);
            this.cOut = cipherOutputStream;
            if (this.withIntegrityPacket) {
                DigestOutputStream digestOutputStream = new DigestOutputStream(this.cOut, MessageDigest.getInstance(PGPUtil.getDigestName(2), this.defProvider));
                this.digestOut = digestOutputStream;
                cipherOutputStream = digestOutputStream;
            }
            int blockSize = this.f27944c.getBlockSize() + 2;
            byte[] bArr2 = new byte[blockSize];
            this.rand.nextBytes(bArr2);
            bArr2[blockSize - 1] = bArr2[blockSize - 3];
            bArr2[blockSize - 2] = bArr2[blockSize - 4];
            cipherOutputStream.write(bArr2);
            return new WrappedGeneratorStream(cipherOutputStream, this);
        } catch (Exception e4) {
            throw new PGPException("Exception creating cipher", e4);
        }
    }

    public void addMethod(PGPPublicKey pGPPublicKey) throws NoSuchProviderException, PGPException {
        if (!pGPPublicKey.isEncryptionKey()) {
            throw new IllegalArgumentException("passed in key not an encryption key!");
        }
        if (this.defProvider == null) {
            throw new NoSuchProviderException("unable to find provider.");
        }
        this.methods.add(new PubMethod(pGPPublicKey));
    }

    public void addMethod(char[] cArr) throws NoSuchProviderException, PGPException {
        addMethod(cArr, 2);
    }

    public void addMethod(char[] cArr, int i2) throws NoSuchProviderException, PGPException {
        if (this.defProvider == null) {
            throw new NoSuchProviderException("unable to find provider.");
        }
        byte[] bArr = new byte[8];
        this.rand.nextBytes(bArr);
        S2K s2k = new S2K(i2, bArr, 96);
        List list = this.methods;
        int i3 = this.defAlgorithm;
        list.add(new PBEMethod(i3, s2k, PGPUtil.makeKeyFromPassPhrase(i3, s2k, cArr, this.defProvider)));
    }

    @Override // org.bouncycastle.openpgp.StreamGenerator
    public void close() throws IOException {
        if (this.cOut != null) {
            if (this.digestOut != null) {
                new BCPGOutputStream(this.digestOut, 19, 20L).flush();
                this.digestOut.flush();
                this.cOut.write(this.digestOut.getMessageDigest().digest());
            }
            this.cOut.flush();
            try {
                this.pOut.write(this.f27944c.doFinal());
                this.pOut.finish();
                this.cOut = null;
                this.pOut = null;
            } catch (Exception e2) {
                throw new IOException(e2.toString());
            }
        }
    }

    public OutputStream open(OutputStream outputStream, long j2) throws IOException, PGPException {
        return open(outputStream, j2, null);
    }

    public OutputStream open(OutputStream outputStream, byte[] bArr) throws IOException, PGPException {
        return open(outputStream, 0L, bArr);
    }
}
