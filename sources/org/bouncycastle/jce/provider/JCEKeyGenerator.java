package org.bouncycastle.jce.provider;

import com.yikaobang.yixue.R2;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.generators.DESKeyGenerator;

/* loaded from: classes9.dex */
public class JCEKeyGenerator extends KeyGeneratorSpi {
    protected String algName;
    protected int defaultKeySize;
    protected CipherKeyGenerator engine;
    protected int keySize;
    protected boolean uninitialised = true;

    public static class DES extends JCEKeyGenerator {
        public DES() {
            super("DES", 64, new DESKeyGenerator());
        }
    }

    public static class GOST28147 extends JCEKeyGenerator {
        public GOST28147() {
            super("GOST28147", 256, new CipherKeyGenerator());
        }
    }

    public static class HMACSHA1 extends JCEKeyGenerator {
        public HMACSHA1() {
            super("HMACSHA1", 160, new CipherKeyGenerator());
        }
    }

    public static class HMACSHA224 extends JCEKeyGenerator {
        public HMACSHA224() {
            super("HMACSHA224", 224, new CipherKeyGenerator());
        }
    }

    public static class HMACSHA256 extends JCEKeyGenerator {
        public HMACSHA256() {
            super("HMACSHA256", 256, new CipherKeyGenerator());
        }
    }

    public static class HMACSHA384 extends JCEKeyGenerator {
        public HMACSHA384() {
            super("HMACSHA384", R2.attr.arrow_right, new CipherKeyGenerator());
        }
    }

    public static class HMACSHA512 extends JCEKeyGenerator {
        public HMACSHA512() {
            super("HMACSHA512", 512, new CipherKeyGenerator());
        }
    }

    public static class HMACTIGER extends JCEKeyGenerator {
        public HMACTIGER() {
            super("HMACTIGER", 192, new CipherKeyGenerator());
        }
    }

    public static class MD2HMAC extends JCEKeyGenerator {
        public MD2HMAC() {
            super("HMACMD2", 128, new CipherKeyGenerator());
        }
    }

    public static class MD4HMAC extends JCEKeyGenerator {
        public MD4HMAC() {
            super("HMACMD4", 128, new CipherKeyGenerator());
        }
    }

    public static class MD5HMAC extends JCEKeyGenerator {
        public MD5HMAC() {
            super("HMACMD5", 128, new CipherKeyGenerator());
        }
    }

    public static class RC2 extends JCEKeyGenerator {
        public RC2() {
            super("RC2", 128, new CipherKeyGenerator());
        }
    }

    public static class RIPEMD128HMAC extends JCEKeyGenerator {
        public RIPEMD128HMAC() {
            super("HMACRIPEMD128", 128, new CipherKeyGenerator());
        }
    }

    public static class RIPEMD160HMAC extends JCEKeyGenerator {
        public RIPEMD160HMAC() {
            super("HMACRIPEMD160", 160, new CipherKeyGenerator());
        }
    }

    public JCEKeyGenerator(String str, int i2, CipherKeyGenerator cipherKeyGenerator) {
        this.algName = str;
        this.defaultKeySize = i2;
        this.keySize = i2;
        this.engine = cipherKeyGenerator;
    }

    @Override // javax.crypto.KeyGeneratorSpi
    public SecretKey engineGenerateKey() {
        if (this.uninitialised) {
            this.engine.init(new KeyGenerationParameters(new SecureRandom(), this.defaultKeySize));
            this.uninitialised = false;
        }
        return new SecretKeySpec(this.engine.generateKey(), this.algName);
    }

    @Override // javax.crypto.KeyGeneratorSpi
    public void engineInit(int i2, SecureRandom secureRandom) {
        try {
            this.engine.init(new KeyGenerationParameters(secureRandom, i2));
            this.uninitialised = false;
        } catch (IllegalArgumentException e2) {
            throw new InvalidParameterException(e2.getMessage());
        }
    }

    @Override // javax.crypto.KeyGeneratorSpi
    public void engineInit(SecureRandom secureRandom) {
        if (secureRandom != null) {
            this.engine.init(new KeyGenerationParameters(secureRandom, this.defaultKeySize));
            this.uninitialised = false;
        }
    }

    @Override // javax.crypto.KeyGeneratorSpi
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("Not Implemented");
    }
}
