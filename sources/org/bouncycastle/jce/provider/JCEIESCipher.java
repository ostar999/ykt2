package org.bouncycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.interfaces.DHPrivateKey;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.agreement.DHBasicAgreement;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.IESEngine;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.IESParameters;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.interfaces.IESKey;
import org.bouncycastle.jce.provider.asymmetric.ec.ECUtil;
import org.bouncycastle.jce.spec.IESParameterSpec;

/* loaded from: classes9.dex */
public class JCEIESCipher extends WrapCipherSpi {
    private IESEngine cipher;
    private int state = -1;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private AlgorithmParameters engineParam = null;
    private IESParameterSpec engineParams = null;
    private Class[] availableSpecs = {IESParameterSpec.class};

    public static class BrokenECIES extends JCEIESCipher {
        public BrokenECIES() {
            super(new IESEngine(new ECDHBasicAgreement(), new BrokenKDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public static class BrokenIES extends JCEIESCipher {
        public BrokenIES() {
            super(new IESEngine(new DHBasicAgreement(), new BrokenKDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public static class ECIES extends JCEIESCipher {
        public ECIES() {
            super(new IESEngine(new ECDHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public static class IES extends JCEIESCipher {
        public IES() {
            super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public JCEIESCipher(IESEngine iESEngine) {
        this.cipher = iESEngine;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws BadPaddingException, IllegalBlockSizeException {
        if (i3 != 0) {
            this.buffer.write(bArr, i2, i3);
        }
        try {
            byte[] byteArray = this.buffer.toByteArray();
            this.buffer.reset();
            byte[] bArrProcessBlock = this.cipher.processBlock(byteArray, 0, byteArray.length);
            System.arraycopy(bArrProcessBlock, 0, bArr2, i4, bArrProcessBlock.length);
            return bArrProcessBlock.length;
        } catch (InvalidCipherTextException e2) {
            throw new BadPaddingException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i2, int i3) throws BadPaddingException, IllegalBlockSizeException {
        if (i3 != 0) {
            this.buffer.write(bArr, i2, i3);
        }
        try {
            byte[] byteArray = this.buffer.toByteArray();
            this.buffer.reset();
            return this.cipher.processBlock(byteArray, 0, byteArray.length);
        } catch (InvalidCipherTextException e2) {
            throw new BadPaddingException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineGetBlockSize() {
        return 0;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public byte[] engineGetIV() {
        return null;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineGetKeySize(Key key) {
        BigInteger d3;
        if (!(key instanceof IESKey)) {
            throw new IllegalArgumentException("must be passed IE key");
        }
        IESKey iESKey = (IESKey) key;
        if (iESKey.getPrivate() instanceof DHPrivateKey) {
            d3 = ((DHPrivateKey) iESKey.getPrivate()).getX();
        } else {
            if (!(iESKey.getPrivate() instanceof ECPrivateKey)) {
                throw new IllegalArgumentException("not an IE key!");
            }
            d3 = ((ECPrivateKey) iESKey.getPrivate()).getD();
        }
        return d3.bitLength();
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineGetOutputSize(int i2) {
        int i3 = this.state;
        if (i3 == 1 || i3 == 3) {
            return this.buffer.size() + i2 + 20;
        }
        if (i3 == 2 || i3 == 4) {
            return (this.buffer.size() + i2) - 20;
        }
        throw new IllegalStateException("cipher not initialised");
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
        if (this.engineParam == null && this.engineParams != null) {
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("IES", BouncyCastleProvider.PROVIDER_NAME);
                this.engineParam = algorithmParameters;
                algorithmParameters.init(this.engineParams);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.engineParam;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameterSpec parameterSpec = null;
        if (algorithmParameters != null) {
            int i3 = 0;
            while (true) {
                Class[] clsArr = this.availableSpecs;
                if (i3 == clsArr.length) {
                    break;
                }
                try {
                    parameterSpec = algorithmParameters.getParameterSpec(clsArr[i3]);
                    break;
                } catch (Exception unused) {
                    i3++;
                }
            }
            if (parameterSpec == null) {
                throw new InvalidAlgorithmParameterException("can't handle parameter " + algorithmParameters.toString());
            }
        }
        this.engineParam = algorithmParameters;
        engineInit(i2, key, parameterSpec, secureRandom);
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        if (i2 == 1 || i2 == 3) {
            try {
                engineInit(i2, key, (AlgorithmParameterSpec) null, secureRandom);
                return;
            } catch (InvalidAlgorithmParameterException unused) {
            }
        }
        throw new IllegalArgumentException("can't handle null parameter spec in IES");
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        AsymmetricKeyParameter asymmetricKeyParameterGeneratePublicKeyParameter;
        AsymmetricKeyParameter asymmetricKeyParameterGeneratePrivateKeyParameter;
        if (!(key instanceof IESKey)) {
            throw new InvalidKeyException("must be passed IES key");
        }
        if (algorithmParameterSpec == null && (i2 == 1 || i2 == 3)) {
            byte[] bArr = new byte[16];
            byte[] bArr2 = new byte[16];
            if (secureRandom == null) {
                secureRandom = new SecureRandom();
            }
            secureRandom.nextBytes(bArr);
            secureRandom.nextBytes(bArr2);
            algorithmParameterSpec = new IESParameterSpec(bArr, bArr2, 128);
        } else if (!(algorithmParameterSpec instanceof IESParameterSpec)) {
            throw new InvalidAlgorithmParameterException("must be passed IES parameters");
        }
        IESKey iESKey = (IESKey) key;
        if (iESKey.getPublic() instanceof ECPublicKey) {
            asymmetricKeyParameterGeneratePublicKeyParameter = ECUtil.generatePublicKeyParameter(iESKey.getPublic());
            asymmetricKeyParameterGeneratePrivateKeyParameter = ECUtil.generatePrivateKeyParameter(iESKey.getPrivate());
        } else {
            asymmetricKeyParameterGeneratePublicKeyParameter = DHUtil.generatePublicKeyParameter(iESKey.getPublic());
            asymmetricKeyParameterGeneratePrivateKeyParameter = DHUtil.generatePrivateKeyParameter(iESKey.getPrivate());
        }
        IESParameterSpec iESParameterSpec = (IESParameterSpec) algorithmParameterSpec;
        this.engineParams = iESParameterSpec;
        IESParameters iESParameters = new IESParameters(iESParameterSpec.getDerivationV(), this.engineParams.getEncodingV(), this.engineParams.getMacKeySize());
        this.state = i2;
        this.buffer.reset();
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 != 4) {
                        System.out.println("eeek!");
                        return;
                    }
                }
            }
            this.cipher.init(false, asymmetricKeyParameterGeneratePrivateKeyParameter, asymmetricKeyParameterGeneratePublicKeyParameter, iESParameters);
            return;
        }
        this.cipher.init(true, asymmetricKeyParameterGeneratePrivateKeyParameter, asymmetricKeyParameterGeneratePublicKeyParameter, iESParameters);
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineSetMode(String str) {
        throw new IllegalArgumentException("can't support mode " + str);
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineSetPadding(String str) throws NoSuchPaddingException {
        throw new NoSuchPaddingException(str + " unavailable with RSA.");
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        this.buffer.write(bArr, i2, i3);
        return 0;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] bArr, int i2, int i3) {
        this.buffer.write(bArr, i2, i3);
        return null;
    }
}
