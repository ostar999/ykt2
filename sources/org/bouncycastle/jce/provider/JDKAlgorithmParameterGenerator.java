package org.bouncycastle.jce.provider;

import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import org.bouncycastle.crypto.generators.DHParametersGenerator;
import org.bouncycastle.crypto.generators.DSAParametersGenerator;
import org.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import org.bouncycastle.crypto.generators.GOST3410ParametersGenerator;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.GOST3410Parameters;
import org.bouncycastle.jce.spec.GOST3410ParameterSpec;
import org.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

/* loaded from: classes9.dex */
public abstract class JDKAlgorithmParameterGenerator extends AlgorithmParameterGeneratorSpi {
    protected SecureRandom random;
    protected int strength = 1024;

    public static class DES extends JDKAlgorithmParameterGenerator {
        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
            byte[] bArr = new byte[8];
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("DES", BouncyCastleProvider.PROVIDER_NAME);
                algorithmParameters.init(new IvParameterSpec(bArr));
                return algorithmParameters;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DES parameter generation.");
        }
    }

    public static class DH extends JDKAlgorithmParameterGenerator {

        /* renamed from: l, reason: collision with root package name */
        private int f27896l = 0;

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
            DHParametersGenerator dHParametersGenerator = new DHParametersGenerator();
            SecureRandom secureRandom = this.random;
            if (secureRandom != null) {
                dHParametersGenerator.init(this.strength, 20, secureRandom);
            } else {
                dHParametersGenerator.init(this.strength, 20, new SecureRandom());
            }
            DHParameters dHParametersGenerateParameters = dHParametersGenerator.generateParameters();
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("DH", BouncyCastleProvider.PROVIDER_NAME);
                algorithmParameters.init(new DHParameterSpec(dHParametersGenerateParameters.getP(), dHParametersGenerateParameters.getG(), this.f27896l));
                return algorithmParameters;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            if (!(algorithmParameterSpec instanceof DHGenParameterSpec)) {
                throw new InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
            }
            DHGenParameterSpec dHGenParameterSpec = (DHGenParameterSpec) algorithmParameterSpec;
            this.strength = dHGenParameterSpec.getPrimeSize();
            this.f27896l = dHGenParameterSpec.getExponentSize();
            this.random = secureRandom;
        }
    }

    public static class DSA extends JDKAlgorithmParameterGenerator {
        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
            DSAParametersGenerator dSAParametersGenerator = new DSAParametersGenerator();
            SecureRandom secureRandom = this.random;
            if (secureRandom != null) {
                dSAParametersGenerator.init(this.strength, 20, secureRandom);
            } else {
                dSAParametersGenerator.init(this.strength, 20, new SecureRandom());
            }
            DSAParameters dSAParametersGenerateParameters = dSAParametersGenerator.generateParameters();
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("DSA", BouncyCastleProvider.PROVIDER_NAME);
                algorithmParameters.init(new DSAParameterSpec(dSAParametersGenerateParameters.getP(), dSAParametersGenerateParameters.getQ(), dSAParametersGenerateParameters.getG()));
                return algorithmParameters;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }

        @Override // org.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator, java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(int i2, SecureRandom secureRandom) {
            if (i2 < 512 || i2 > 1024 || i2 % 64 != 0) {
                throw new InvalidParameterException("strength must be from 512 - 1024 and a multiple of 64");
            }
            this.strength = i2;
            this.random = secureRandom;
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DSA parameter generation.");
        }
    }

    public static class ElGamal extends JDKAlgorithmParameterGenerator {

        /* renamed from: l, reason: collision with root package name */
        private int f27897l = 0;

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
            ElGamalParametersGenerator elGamalParametersGenerator = new ElGamalParametersGenerator();
            SecureRandom secureRandom = this.random;
            if (secureRandom != null) {
                elGamalParametersGenerator.init(this.strength, 20, secureRandom);
            } else {
                elGamalParametersGenerator.init(this.strength, 20, new SecureRandom());
            }
            ElGamalParameters elGamalParametersGenerateParameters = elGamalParametersGenerator.generateParameters();
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("ElGamal", BouncyCastleProvider.PROVIDER_NAME);
                algorithmParameters.init(new DHParameterSpec(elGamalParametersGenerateParameters.getP(), elGamalParametersGenerateParameters.getG(), this.f27897l));
                return algorithmParameters;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            if (!(algorithmParameterSpec instanceof DHGenParameterSpec)) {
                throw new InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
            }
            DHGenParameterSpec dHGenParameterSpec = (DHGenParameterSpec) algorithmParameterSpec;
            this.strength = dHGenParameterSpec.getPrimeSize();
            this.f27897l = dHGenParameterSpec.getExponentSize();
            this.random = secureRandom;
        }
    }

    public static class GOST3410 extends JDKAlgorithmParameterGenerator {
        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
            GOST3410ParametersGenerator gOST3410ParametersGenerator = new GOST3410ParametersGenerator();
            SecureRandom secureRandom = this.random;
            if (secureRandom != null) {
                gOST3410ParametersGenerator.init(this.strength, 2, secureRandom);
            } else {
                gOST3410ParametersGenerator.init(this.strength, 2, new SecureRandom());
            }
            GOST3410Parameters gOST3410ParametersGenerateParameters = gOST3410ParametersGenerator.generateParameters();
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("GOST3410", BouncyCastleProvider.PROVIDER_NAME);
                algorithmParameters.init(new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(gOST3410ParametersGenerateParameters.getP(), gOST3410ParametersGenerateParameters.getQ(), gOST3410ParametersGenerateParameters.getA())));
                return algorithmParameters;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for GOST3410 parameter generation.");
        }
    }

    public static class RC2 extends JDKAlgorithmParameterGenerator {
        RC2ParameterSpec spec = null;

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
            if (this.spec != null) {
                try {
                    AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("RC2", BouncyCastleProvider.PROVIDER_NAME);
                    algorithmParameters.init(this.spec);
                    return algorithmParameters;
                } catch (Exception e2) {
                    throw new RuntimeException(e2.getMessage());
                }
            }
            byte[] bArr = new byte[8];
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters algorithmParameters2 = AlgorithmParameters.getInstance("RC2", BouncyCastleProvider.PROVIDER_NAME);
                algorithmParameters2.init(new IvParameterSpec(bArr));
                return algorithmParameters2;
            } catch (Exception e3) {
                throw new RuntimeException(e3.getMessage());
            }
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            if (!(algorithmParameterSpec instanceof RC2ParameterSpec)) {
                throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for RC2 parameter generation.");
            }
            this.spec = (RC2ParameterSpec) algorithmParameterSpec;
        }
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    public void engineInit(int i2, SecureRandom secureRandom) {
        this.strength = i2;
        this.random = secureRandom;
    }
}
