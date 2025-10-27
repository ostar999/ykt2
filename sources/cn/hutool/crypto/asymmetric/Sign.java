package cn.hutool.crypto.asymmetric;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.SecureUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Collection;
import java.util.Set;

/* loaded from: classes.dex */
public class Sign extends BaseAsymmetric<Sign> {
    private static final long serialVersionUID = 1;
    protected Signature signature;

    public Sign(SignAlgorithm signAlgorithm) {
        this(signAlgorithm, (byte[]) null, (byte[]) null);
    }

    public String digestHex(InputStream inputStream, int i2) {
        return HexUtil.encodeHexStr(sign(inputStream, i2));
    }

    public Signature getSignature() {
        return this.signature;
    }

    public Sign setCertificate(Certificate certificate) {
        boolean[] keyUsage;
        if (certificate instanceof X509Certificate) {
            X509Certificate x509Certificate = (X509Certificate) certificate;
            Set<String> criticalExtensionOIDs = x509Certificate.getCriticalExtensionOIDs();
            if (CollUtil.isNotEmpty((Collection<?>) criticalExtensionOIDs) && criticalExtensionOIDs.contains("2.5.29.15") && (keyUsage = x509Certificate.getKeyUsage()) != null && !keyUsage[0]) {
                throw new CryptoException("Wrong key usage");
            }
        }
        this.publicKey = certificate.getPublicKey();
        return this;
    }

    public Sign setParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        try {
            this.signature.setParameter(algorithmParameterSpec);
            return this;
        } catch (InvalidAlgorithmParameterException e2) {
            throw new CryptoException(e2);
        }
    }

    public Sign setSignature(Signature signature) {
        this.signature = signature;
        return this;
    }

    public byte[] sign(String str, Charset charset) {
        return sign(CharSequenceUtil.bytes(str, charset));
    }

    public String signHex(String str, Charset charset) {
        return HexUtil.encodeHexStr(sign(str, charset));
    }

    public boolean verify(byte[] bArr, byte[] bArr2) {
        this.lock.lock();
        try {
            try {
                this.signature.initVerify(this.publicKey);
                this.signature.update(bArr);
                return this.signature.verify(bArr2);
            } catch (Exception e2) {
                throw new CryptoException(e2);
            }
        } finally {
            this.lock.unlock();
        }
    }

    public Sign(String str) {
        this(str, (byte[]) null, (byte[]) null);
    }

    @Override // cn.hutool.crypto.asymmetric.BaseAsymmetric
    public Sign init(String str, PrivateKey privateKey, PublicKey publicKey) {
        this.signature = SecureUtil.createSignature(str);
        super.init(str, privateKey, publicKey);
        return this;
    }

    public byte[] sign(String str) {
        return sign(str, CharsetUtil.CHARSET_UTF_8);
    }

    public String signHex(String str) {
        return signHex(str, CharsetUtil.CHARSET_UTF_8);
    }

    public Sign(SignAlgorithm signAlgorithm, String str, String str2) {
        this(signAlgorithm.getValue(), SecureUtil.decode(str), SecureUtil.decode(str2));
    }

    public byte[] sign(byte[] bArr) {
        return sign(new ByteArrayInputStream(bArr), -1);
    }

    public String signHex(byte[] bArr) {
        return HexUtil.encodeHexStr(sign(bArr));
    }

    public Sign(SignAlgorithm signAlgorithm, byte[] bArr, byte[] bArr2) {
        this(signAlgorithm.getValue(), bArr, bArr2);
    }

    public byte[] sign(InputStream inputStream) {
        return sign(inputStream, 8192);
    }

    public String signHex(InputStream inputStream) {
        return HexUtil.encodeHexStr(sign(inputStream));
    }

    public Sign(SignAlgorithm signAlgorithm, KeyPair keyPair) {
        this(signAlgorithm.getValue(), keyPair);
    }

    public byte[] sign(InputStream inputStream, int i2) {
        if (i2 < 1) {
            i2 = 8192;
        }
        byte[] bArr = new byte[i2];
        this.lock.lock();
        try {
            try {
                this.signature.initSign(this.privateKey);
                try {
                    int i3 = inputStream.read(bArr, 0, i2);
                    while (i3 > -1) {
                        this.signature.update(bArr, 0, i3);
                        i3 = inputStream.read(bArr, 0, i2);
                    }
                    return this.signature.sign();
                } catch (Exception e2) {
                    throw new CryptoException(e2);
                }
            } catch (Exception e3) {
                throw new CryptoException(e3);
            }
        } finally {
            this.lock.unlock();
        }
    }

    public Sign(SignAlgorithm signAlgorithm, PrivateKey privateKey, PublicKey publicKey) {
        this(signAlgorithm.getValue(), privateKey, publicKey);
    }

    public Sign(String str, String str2, String str3) {
        this(str, Base64.decode(str2), Base64.decode(str3));
    }

    public Sign(String str, byte[] bArr, byte[] bArr2) {
        this(str, SecureUtil.generatePrivateKey(str, bArr), SecureUtil.generatePublicKey(str, bArr2));
    }

    public Sign(String str, KeyPair keyPair) {
        this(str, keyPair.getPrivate(), keyPair.getPublic());
    }

    public Sign(String str, PrivateKey privateKey, PublicKey publicKey) {
        super(str, privateKey, publicKey);
    }
}
