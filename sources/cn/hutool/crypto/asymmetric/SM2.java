package cn.hutool.crypto.asymmetric;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.ECKeyUtil;
import cn.hutool.crypto.SecureUtil;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.DSAEncoding;
import org.bouncycastle.crypto.signers.PlainDSAEncoding;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.crypto.signers.StandardDSAEncoding;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SM2 extends AbstractAsymmetricCrypto<SM2> {
    private static final String ALGORITHM_SM2 = "SM2";
    private static final long serialVersionUID = 1;
    private Digest digest;
    private DSAEncoding encoding;
    protected SM2Engine engine;
    private SM2Engine.Mode mode;
    private ECPrivateKeyParameters privateKeyParams;
    private ECPublicKeyParameters publicKeyParams;
    protected SM2Signer signer;

    /* renamed from: cn.hutool.crypto.asymmetric.SM2$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$crypto$asymmetric$KeyType;

        static {
            int[] iArr = new int[KeyType.values().length];
            $SwitchMap$cn$hutool$crypto$asymmetric$KeyType = iArr;
            try {
                iArr[KeyType.PublicKey.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$crypto$asymmetric$KeyType[KeyType.PrivateKey.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public SM2() {
        this((byte[]) null, (byte[]) null);
    }

    private CipherParameters getCipherParameters(KeyType keyType) throws IllegalArgumentException {
        int i2 = AnonymousClass1.$SwitchMap$cn$hutool$crypto$asymmetric$KeyType[keyType.ordinal()];
        if (i2 == 1) {
            Assert.notNull(this.publicKeyParams, "PublicKey must be not null !", new Object[0]);
            return this.publicKeyParams;
        }
        if (i2 != 2) {
            return null;
        }
        Assert.notNull(this.privateKeyParams, "PrivateKey must be not null !", new Object[0]);
        return this.privateKeyParams;
    }

    private SM2Engine getEngine() throws IllegalArgumentException {
        if (this.engine == null) {
            Assert.notNull(this.digest, "digest must be not null !", new Object[0]);
            this.engine = new SM2Engine(this.digest, this.mode);
        }
        this.digest.reset();
        return this.engine;
    }

    private SM2Signer getSigner() throws IllegalArgumentException {
        if (this.signer == null) {
            Assert.notNull(this.digest, "digest must be not null !", new Object[0]);
            this.signer = new SM2Signer(this.encoding, this.digest);
        }
        this.digest.reset();
        return this.signer;
    }

    public byte[] decrypt(byte[] bArr) throws CryptoException {
        return decrypt(bArr, KeyType.PrivateKey);
    }

    public byte[] encrypt(byte[] bArr) throws CryptoException {
        return encrypt(bArr, KeyType.PublicKey);
    }

    public byte[] getD() {
        return BigIntegers.asUnsignedByteArray(32, getDBigInteger());
    }

    public BigInteger getDBigInteger() {
        return this.privateKeyParams.getD();
    }

    public String getDHex() {
        return new String(Hex.encode(getD()));
    }

    public byte[] getQ(boolean z2) {
        return this.publicKeyParams.getQ().getEncoded(z2);
    }

    public SM2 init() {
        if (this.privateKeyParams == null && this.publicKeyParams == null) {
            super.initKeys();
            this.privateKeyParams = BCUtil.toParams(this.privateKey);
            this.publicKeyParams = BCUtil.toParams(this.publicKey);
        }
        return this;
    }

    @Override // cn.hutool.crypto.asymmetric.BaseAsymmetric
    public SM2 initKeys() {
        return this;
    }

    public SM2 setDigest(Digest digest) {
        this.digest = digest;
        this.engine = null;
        this.signer = null;
        return this;
    }

    public SM2 setEncoding(DSAEncoding dSAEncoding) {
        this.encoding = dSAEncoding;
        this.signer = null;
        return this;
    }

    public SM2 setMode(SM2Engine.Mode mode) {
        this.mode = mode;
        this.engine = null;
        return this;
    }

    public SM2 setPrivateKeyParams(ECPrivateKeyParameters eCPrivateKeyParameters) {
        this.privateKeyParams = eCPrivateKeyParameters;
        return this;
    }

    public SM2 setPublicKeyParams(ECPublicKeyParameters eCPublicKeyParameters) {
        this.publicKeyParams = eCPublicKeyParameters;
        return this;
    }

    public byte[] sign(byte[] bArr) {
        return sign(bArr, null);
    }

    public String signHex(String str) {
        return signHex(str, null);
    }

    public SM2 usePlainEncoding() {
        return setEncoding(PlainDSAEncoding.INSTANCE);
    }

    public boolean verify(byte[] bArr, byte[] bArr2) {
        return verify(bArr, bArr2, null);
    }

    public boolean verifyHex(String str, String str2) {
        return verifyHex(str, str2, null);
    }

    public SM2(String str, String str2) {
        this(SecureUtil.decode(str), SecureUtil.decode(str2));
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricDecryptor
    public byte[] decrypt(byte[] bArr, KeyType keyType) throws CryptoException {
        if (KeyType.PrivateKey == keyType) {
            return decrypt(bArr, getCipherParameters(keyType));
        }
        throw new IllegalArgumentException("Decrypt is only support by private key");
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public byte[] encrypt(byte[] bArr, KeyType keyType) throws CryptoException {
        if (KeyType.PublicKey == keyType) {
            return encrypt(bArr, new ParametersWithRandom(getCipherParameters(keyType)));
        }
        throw new IllegalArgumentException("Encrypt is only support by public key");
    }

    @Override // cn.hutool.crypto.asymmetric.BaseAsymmetric
    public SM2 setPrivateKey(PrivateKey privateKey) {
        super.setPrivateKey(privateKey);
        this.privateKeyParams = BCUtil.toParams(privateKey);
        return this;
    }

    @Override // cn.hutool.crypto.asymmetric.BaseAsymmetric
    public SM2 setPublicKey(PublicKey publicKey) {
        super.setPublicKey(publicKey);
        this.publicKeyParams = BCUtil.toParams(publicKey);
        return this;
    }

    public byte[] sign(byte[] bArr, byte[] bArr2) throws IllegalArgumentException {
        this.lock.lock();
        SM2Signer signer = getSigner();
        try {
            try {
                ParametersWithRandom parametersWithRandom = new ParametersWithRandom(getCipherParameters(KeyType.PrivateKey));
                if (bArr2 != null) {
                    parametersWithRandom = new ParametersWithID(parametersWithRandom, bArr2);
                }
                signer.init(true, parametersWithRandom);
                signer.update(bArr, 0, bArr.length);
                return signer.generateSignature();
            } catch (org.bouncycastle.crypto.CryptoException e2) {
                throw new CryptoException(e2);
            }
        } finally {
            this.lock.unlock();
        }
    }

    public String signHex(String str, String str2) {
        return HexUtil.encodeHexStr(sign(HexUtil.decodeHex(str), HexUtil.decodeHex(str2)));
    }

    public boolean verify(byte[] bArr, byte[] bArr2, byte[] bArr3) throws IllegalArgumentException {
        this.lock.lock();
        SM2Signer signer = getSigner();
        try {
            CipherParameters cipherParameters = getCipherParameters(KeyType.PublicKey);
            if (bArr3 != null) {
                cipherParameters = new ParametersWithID(cipherParameters, bArr3);
            }
            signer.init(false, cipherParameters);
            signer.update(bArr, 0, bArr.length);
            return signer.verifySignature(bArr2);
        } finally {
            this.lock.unlock();
        }
    }

    public boolean verifyHex(String str, String str2, String str3) {
        return verify(HexUtil.decodeHex(str), HexUtil.decodeHex(str2), HexUtil.decodeHex(str3));
    }

    public SM2(byte[] bArr, byte[] bArr2) {
        this(ECKeyUtil.decodePrivateKeyParams(bArr), ECKeyUtil.decodePublicKeyParams(bArr2));
    }

    public byte[] decrypt(byte[] bArr, CipherParameters cipherParameters) throws CryptoException, IllegalArgumentException {
        this.lock.lock();
        SM2Engine engine = getEngine();
        try {
            try {
                engine.init(false, cipherParameters);
                return engine.processBlock(bArr, 0, bArr.length);
            } catch (InvalidCipherTextException e2) {
                throw new CryptoException(e2);
            }
        } finally {
            this.lock.unlock();
        }
    }

    public byte[] encrypt(byte[] bArr, CipherParameters cipherParameters) throws CryptoException, IllegalArgumentException {
        this.lock.lock();
        SM2Engine engine = getEngine();
        try {
            try {
                engine.init(true, cipherParameters);
                return engine.processBlock(bArr, 0, bArr.length);
            } catch (InvalidCipherTextException e2) {
                throw new CryptoException(e2);
            }
        } finally {
            this.lock.unlock();
        }
    }

    public SM2(PrivateKey privateKey, PublicKey publicKey) {
        this(BCUtil.toParams(privateKey), BCUtil.toParams(publicKey));
        if (privateKey != null) {
            this.privateKey = privateKey;
        }
        if (publicKey != null) {
            this.publicKey = publicKey;
        }
    }

    public SM2(String str, String str2, String str3) {
        this(BCUtil.toSm2Params(str), BCUtil.toSm2Params(str2, str3));
    }

    public SM2(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this(BCUtil.toSm2Params(bArr), BCUtil.toSm2Params(bArr2, bArr3));
    }

    public SM2(ECPrivateKeyParameters eCPrivateKeyParameters, ECPublicKeyParameters eCPublicKeyParameters) {
        super(ALGORITHM_SM2, null, null);
        this.encoding = StandardDSAEncoding.INSTANCE;
        this.digest = new SM3Digest();
        this.mode = SM2Engine.Mode.C1C3C2;
        this.privateKeyParams = eCPrivateKeyParameters;
        this.publicKeyParams = eCPublicKeyParameters;
        init();
    }
}
