package org.bouncycastle.jce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.NoekeonEngine;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JCEBlockCipher;
import org.bouncycastle.jce.provider.JCEKeyGenerator;
import org.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator;
import org.bouncycastle.jce.provider.JDKAlgorithmParameters;

/* loaded from: classes9.dex */
public final class Noekeon {

    public static class AlgParamGen extends JDKAlgorithmParameterGenerator {
        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
            byte[] bArr = new byte[16];
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("Noekeon", BouncyCastleProvider.PROVIDER_NAME);
                algorithmParameters.init(new IvParameterSpec(bArr));
                return algorithmParameters;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for Noekeon parameter generation.");
        }
    }

    public static class AlgParams extends JDKAlgorithmParameters.IVAlgorithmParameters {
        @Override // org.bouncycastle.jce.provider.JDKAlgorithmParameters.IVAlgorithmParameters, java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "Noekeon IV";
        }
    }

    public static class ECB extends JCEBlockCipher {
        public ECB() {
            super(new NoekeonEngine());
        }
    }

    public static class KeyGen extends JCEKeyGenerator {
        public KeyGen() {
            super("Noekeon", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends HashMap {
        public Mappings() {
            put("AlgorithmParameters.NOEKEON", "org.bouncycastle.jce.provider.symmetric.Noekeon$AlgParams");
            put("AlgorithmParameterGenerator.NOEKEON", "org.bouncycastle.jce.provider.symmetric.Noekeon$AlgParamGen");
            put("Cipher.NOEKEON", "org.bouncycastle.jce.provider.symmetric.Noekeon$ECB");
            put("KeyGenerator.NOEKEON", "org.bouncycastle.jce.provider.symmetric.Noekeon$KeyGen");
        }
    }

    private Noekeon() {
    }
}
