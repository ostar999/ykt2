package cn.hutool.crypto.asymmetric;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.BaseAsymmetric;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes.dex */
public class BaseAsymmetric<T extends BaseAsymmetric<T>> implements Serializable {
    private static final long serialVersionUID = 1;
    protected String algorithm;
    protected final Lock lock = new ReentrantLock();
    protected PrivateKey privateKey;
    protected PublicKey publicKey;

    /* renamed from: cn.hutool.crypto.asymmetric.BaseAsymmetric$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$crypto$asymmetric$KeyType;

        static {
            int[] iArr = new int[KeyType.values().length];
            $SwitchMap$cn$hutool$crypto$asymmetric$KeyType = iArr;
            try {
                iArr[KeyType.PrivateKey.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$crypto$asymmetric$KeyType[KeyType.PublicKey.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public BaseAsymmetric(String str, PrivateKey privateKey, PublicKey publicKey) {
        init(str, privateKey, publicKey);
    }

    public Key getKeyByType(KeyType keyType) {
        int i2 = AnonymousClass1.$SwitchMap$cn$hutool$crypto$asymmetric$KeyType[keyType.ordinal()];
        if (i2 == 1) {
            PrivateKey privateKey = this.privateKey;
            if (privateKey != null) {
                return privateKey;
            }
            throw new NullPointerException("Private key must not null when use it !");
        }
        if (i2 == 2) {
            PublicKey publicKey = this.publicKey;
            if (publicKey != null) {
                return publicKey;
            }
            throw new NullPointerException("Public key must not null when use it !");
        }
        throw new CryptoException("Unsupported key type: " + keyType);
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public String getPrivateKeyBase64() {
        PrivateKey privateKey = getPrivateKey();
        if (privateKey == null) {
            return null;
        }
        return Base64.encode(privateKey.getEncoded());
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public String getPublicKeyBase64() {
        PublicKey publicKey = getPublicKey();
        if (publicKey == null) {
            return null;
        }
        return Base64.encode(publicKey.getEncoded());
    }

    public T init(String str, PrivateKey privateKey, PublicKey publicKey) {
        this.algorithm = str;
        if (privateKey == null && publicKey == null) {
            initKeys();
        } else {
            if (privateKey != null) {
                this.privateKey = privateKey;
            }
            if (publicKey != null) {
                this.publicKey = publicKey;
            }
        }
        return this;
    }

    public T initKeys() {
        KeyPair keyPairGenerateKeyPair = KeyUtil.generateKeyPair(this.algorithm);
        this.publicKey = keyPairGenerateKeyPair.getPublic();
        this.privateKey = keyPairGenerateKeyPair.getPrivate();
        return this;
    }

    public T setKey(Key key) throws IllegalArgumentException {
        Assert.notNull(key, "key must be not null !", new Object[0]);
        if (key instanceof PublicKey) {
            return (T) setPublicKey((PublicKey) key);
        }
        if (key instanceof PrivateKey) {
            return (T) setPrivateKey((PrivateKey) key);
        }
        throw new CryptoException("Unsupported key type: {}", key.getClass());
    }

    public T setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public T setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
        return this;
    }
}
