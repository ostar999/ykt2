package org.bouncycastle.jce.provider.symmetric;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.misc.CAST5CBCParameters;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.CAST5Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JCEBlockCipher;
import org.bouncycastle.jce.provider.JCEKeyGenerator;
import org.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator;
import org.bouncycastle.jce.provider.JDKAlgorithmParameters;

/* loaded from: classes9.dex */
public final class CAST5 {

    public static class AlgParamGen extends JDKAlgorithmParameterGenerator {
        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
            byte[] bArr = new byte[8];
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("CAST5", BouncyCastleProvider.PROVIDER_NAME);
                algorithmParameters.init(new IvParameterSpec(bArr));
                return algorithmParameters;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for CAST5 parameter generation.");
        }
    }

    public static class AlgParams extends JDKAlgorithmParameters {
        private byte[] iv;
        private int keyLength = 128;

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded() {
            byte[] bArr = this.iv;
            byte[] bArr2 = new byte[bArr.length];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            return bArr2;
        }

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded(String str) throws IOException {
            if (isASN1FormatString(str)) {
                return new CAST5CBCParameters(engineGetEncoded(), this.keyLength).getEncoded();
            }
            if (str.equals("RAW")) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
            if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
                throw new InvalidParameterSpecException("IvParameterSpec required to initialise a CAST5 parameters algorithm parameters object");
            }
            this.iv = ((IvParameterSpec) algorithmParameterSpec).getIV();
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr) throws IOException {
            byte[] bArr2 = new byte[bArr.length];
            this.iv = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr, String str) throws IOException {
            if (isASN1FormatString(str)) {
                CAST5CBCParameters cAST5CBCParameters = CAST5CBCParameters.getInstance(new ASN1InputStream(bArr).readObject());
                this.keyLength = cAST5CBCParameters.getKeyLength();
                this.iv = cAST5CBCParameters.getIV();
            } else {
                if (!str.equals("RAW")) {
                    throw new IOException("Unknown parameters format in IV parameters object");
                }
                engineInit(bArr);
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "CAST5 Parameters";
        }

        @Override // org.bouncycastle.jce.provider.JDKAlgorithmParameters
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
            if (cls == IvParameterSpec.class) {
                return new IvParameterSpec(this.iv);
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to CAST5 parameters object.");
        }
    }

    public static class CBC extends JCEBlockCipher {
        public CBC() {
            super(new CBCBlockCipher(new CAST5Engine()), 64);
        }
    }

    public static class ECB extends JCEBlockCipher {
        public ECB() {
            super(new CAST5Engine());
        }
    }

    public static class KeyGen extends JCEKeyGenerator {
        public KeyGen() {
            super("CAST5", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends HashMap {
        public Mappings() {
            put("AlgorithmParameters.CAST5", "org.bouncycastle.jce.provider.symmetric.CAST5$AlgParams");
            put("Alg.Alias.AlgorithmParameters.1.2.840.113533.7.66.10", "CAST5");
            put("AlgorithmParameterGenerator.CAST5", "org.bouncycastle.jce.provider.symmetric.CAST5$AlgParamGen");
            put("Alg.Alias.AlgorithmParameterGenerator.1.2.840.113533.7.66.10", "CAST5");
            put("Cipher.CAST5", "org.bouncycastle.jce.provider.symmetric.CAST5$ECB");
            put("Cipher.1.2.840.113533.7.66.10", "org.bouncycastle.jce.provider.symmetric.CAST5$CBC");
            put("KeyGenerator.CAST5", "org.bouncycastle.jce.provider.symmetric.CAST5$KeyGen");
            put("Alg.Alias.KeyGenerator.1.2.840.113533.7.66.10", "CAST5");
        }
    }

    private CAST5() {
    }
}
