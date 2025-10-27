package org.bouncycastle.jce.provider.asymmetric.ec;

import cn.hutool.crypto.KeyUtil;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.jce.provider.JCEECPrivateKey;
import org.bouncycastle.jce.provider.JCEECPublicKey;
import org.bouncycastle.jce.provider.JDKKeyFactory;
import org.bouncycastle.jce.provider.ProviderUtil;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;

/* loaded from: classes9.dex */
public class KeyFactory extends JDKKeyFactory {
    String algorithm;

    public static class EC extends KeyFactory {
        public EC() {
            super("EC");
        }
    }

    public static class ECDH extends KeyFactory {
        public ECDH() {
            super("ECDH");
        }
    }

    public static class ECDHC extends KeyFactory {
        public ECDHC() {
            super("ECDHC");
        }
    }

    public static class ECDSA extends KeyFactory {
        public ECDSA() {
            super("ECDSA");
        }
    }

    public static class ECGOST3410 extends KeyFactory {
        public ECGOST3410() {
            super("ECGOST3410");
        }
    }

    public static class ECMQV extends KeyFactory {
        public ECMQV() {
            super("ECMQV");
        }
    }

    public KeyFactory(String str) {
        this.algorithm = str;
    }

    @Override // org.bouncycastle.jce.provider.JDKKeyFactory, java.security.KeyFactorySpi
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            try {
                return new JCEECPrivateKey(this.algorithm, (JCEECPrivateKey) JDKKeyFactory.createPrivateKeyFromDERStream(((PKCS8EncodedKeySpec) keySpec).getEncoded()));
            } catch (Exception e2) {
                throw new InvalidKeySpecException(e2.toString());
            }
        }
        if (keySpec instanceof ECPrivateKeySpec) {
            return new JCEECPrivateKey(this.algorithm, (ECPrivateKeySpec) keySpec);
        }
        if (keySpec instanceof java.security.spec.ECPrivateKeySpec) {
            return new JCEECPrivateKey(this.algorithm, (java.security.spec.ECPrivateKeySpec) keySpec);
        }
        throw new InvalidKeySpecException("Unknown KeySpec type: " + keySpec.getClass().getName());
    }

    @Override // org.bouncycastle.jce.provider.JDKKeyFactory, java.security.KeyFactorySpi
    public PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec instanceof X509EncodedKeySpec) {
            try {
                return new JCEECPublicKey(this.algorithm, (JCEECPublicKey) JDKKeyFactory.createPublicKeyFromDERStream(((X509EncodedKeySpec) keySpec).getEncoded()));
            } catch (Exception e2) {
                throw new InvalidKeySpecException(e2.toString());
            }
        }
        if (keySpec instanceof ECPublicKeySpec) {
            return new JCEECPublicKey(this.algorithm, (ECPublicKeySpec) keySpec);
        }
        if (keySpec instanceof java.security.spec.ECPublicKeySpec) {
            return new JCEECPublicKey(this.algorithm, (java.security.spec.ECPublicKeySpec) keySpec);
        }
        throw new InvalidKeySpecException("Unknown KeySpec type: " + keySpec.getClass().getName());
    }

    @Override // org.bouncycastle.jce.provider.JDKKeyFactory, java.security.KeyFactorySpi
    public KeySpec engineGetKeySpec(Key key, Class cls) throws InvalidKeySpecException {
        if (cls.isAssignableFrom(PKCS8EncodedKeySpec.class) && key.getFormat().equals("PKCS#8")) {
            return new PKCS8EncodedKeySpec(key.getEncoded());
        }
        if (cls.isAssignableFrom(X509EncodedKeySpec.class) && key.getFormat().equals(KeyUtil.CERT_TYPE_X509)) {
            return new X509EncodedKeySpec(key.getEncoded());
        }
        if (cls.isAssignableFrom(java.security.spec.ECPublicKeySpec.class) && (key instanceof ECPublicKey)) {
            ECPublicKey eCPublicKey = (ECPublicKey) key;
            if (eCPublicKey.getParams() != null) {
                return new java.security.spec.ECPublicKeySpec(eCPublicKey.getW(), eCPublicKey.getParams());
            }
            ECParameterSpec ecImplicitlyCa = ProviderUtil.getEcImplicitlyCa();
            return new java.security.spec.ECPublicKeySpec(eCPublicKey.getW(), EC5Util.convertSpec(EC5Util.convertCurve(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getSeed()), ecImplicitlyCa));
        }
        if (cls.isAssignableFrom(java.security.spec.ECPrivateKeySpec.class) && (key instanceof ECPrivateKey)) {
            ECPrivateKey eCPrivateKey = (ECPrivateKey) key;
            if (eCPrivateKey.getParams() != null) {
                return new java.security.spec.ECPrivateKeySpec(eCPrivateKey.getS(), eCPrivateKey.getParams());
            }
            ECParameterSpec ecImplicitlyCa2 = ProviderUtil.getEcImplicitlyCa();
            return new java.security.spec.ECPrivateKeySpec(eCPrivateKey.getS(), EC5Util.convertSpec(EC5Util.convertCurve(ecImplicitlyCa2.getCurve(), ecImplicitlyCa2.getSeed()), ecImplicitlyCa2));
        }
        throw new RuntimeException("not implemented yet " + key + " " + cls);
    }

    @Override // org.bouncycastle.jce.provider.JDKKeyFactory, java.security.KeyFactorySpi
    public Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof ECPublicKey) {
            return new JCEECPublicKey((ECPublicKey) key);
        }
        if (key instanceof ECPrivateKey) {
            return new JCEECPrivateKey((ECPrivateKey) key);
        }
        throw new InvalidKeyException("key type unknown");
    }
}
