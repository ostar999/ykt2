package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.agreement.DHBasicAgreement;
import org.bouncycastle.crypto.generators.DHBasicKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DHKeyGenerationParameters;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes9.dex */
class TlsDHKeyExchange implements TlsKeyExchange {
    protected static final BigInteger ONE = BigInteger.valueOf(1);
    protected static final BigInteger TWO = BigInteger.valueOf(2);
    protected TlsAgreementCredentials agreementCredentials;
    protected TlsClientContext context;
    protected int keyExchange;
    protected TlsSigner tlsSigner;
    protected AsymmetricKeyParameter serverPublicKey = null;
    protected DHPublicKeyParameters dhAgreeServerPublicKey = null;
    protected DHPrivateKeyParameters dhAgreeClientPrivateKey = null;

    public TlsDHKeyExchange(TlsClientContext tlsClientContext, int i2) {
        TlsSigner tlsDSSSigner = null;
        if (i2 == 3) {
            tlsDSSSigner = new TlsDSSSigner();
        } else if (i2 == 5) {
            tlsDSSSigner = new TlsRSASigner();
        } else if (i2 != 7 && i2 != 9) {
            throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
        this.tlsSigner = tlsDSSSigner;
        this.context = tlsClientContext;
        this.keyExchange = i2;
    }

    public boolean areCompatibleParameters(DHParameters dHParameters, DHParameters dHParameters2) {
        return dHParameters.getP().equals(dHParameters2.getP()) && dHParameters.getG().equals(dHParameters2.getG());
    }

    public byte[] calculateDHBasicAgreement(DHPublicKeyParameters dHPublicKeyParameters, DHPrivateKeyParameters dHPrivateKeyParameters) {
        DHBasicAgreement dHBasicAgreement = new DHBasicAgreement();
        dHBasicAgreement.init(this.dhAgreeClientPrivateKey);
        return BigIntegers.asUnsignedByteArray(dHBasicAgreement.calculateAgreement(this.dhAgreeServerPublicKey));
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void generateClientKeyExchange(OutputStream outputStream) throws IOException {
        if (this.agreementCredentials != null) {
            TlsUtils.writeUint24(0, outputStream);
        } else {
            generateEphemeralClientKeyExchange(this.dhAgreeServerPublicKey.getParameters(), outputStream);
        }
    }

    public AsymmetricCipherKeyPair generateDHKeyPair(DHParameters dHParameters) {
        DHBasicKeyPairGenerator dHBasicKeyPairGenerator = new DHBasicKeyPairGenerator();
        dHBasicKeyPairGenerator.init(new DHKeyGenerationParameters(this.context.getSecureRandom(), dHParameters));
        return dHBasicKeyPairGenerator.generateKeyPair();
    }

    public void generateEphemeralClientKeyExchange(DHParameters dHParameters, OutputStream outputStream) throws IOException {
        AsymmetricCipherKeyPair asymmetricCipherKeyPairGenerateDHKeyPair = generateDHKeyPair(dHParameters);
        this.dhAgreeClientPrivateKey = (DHPrivateKeyParameters) asymmetricCipherKeyPairGenerateDHKeyPair.getPrivate();
        byte[] bArrAsUnsignedByteArray = BigIntegers.asUnsignedByteArray(((DHPublicKeyParameters) asymmetricCipherKeyPairGenerateDHKeyPair.getPublic()).getY());
        TlsUtils.writeUint24(bArrAsUnsignedByteArray.length + 2, outputStream);
        TlsUtils.writeOpaque16(bArrAsUnsignedByteArray, outputStream);
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public byte[] generatePremasterSecret() throws IOException {
        TlsAgreementCredentials tlsAgreementCredentials = this.agreementCredentials;
        return tlsAgreementCredentials != null ? tlsAgreementCredentials.generateAgreement(this.dhAgreeServerPublicKey) : calculateDHBasicAgreement(this.dhAgreeServerPublicKey, this.dhAgreeClientPrivateKey);
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processClientCredentials(TlsCredentials tlsCredentials) throws IOException {
        if (tlsCredentials instanceof TlsAgreementCredentials) {
            this.agreementCredentials = (TlsAgreementCredentials) tlsCredentials;
        } else if (!(tlsCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert((short) 80);
        }
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processServerCertificate(Certificate certificate) throws IOException {
        int i2;
        X509CertificateStructure x509CertificateStructure = certificate.certs[0];
        try {
            AsymmetricKeyParameter asymmetricKeyParameterCreateKey = PublicKeyFactory.createKey(x509CertificateStructure.getSubjectPublicKeyInfo());
            this.serverPublicKey = asymmetricKeyParameterCreateKey;
            TlsSigner tlsSigner = this.tlsSigner;
            if (tlsSigner == null) {
                try {
                    this.dhAgreeServerPublicKey = validateDHPublicKey((DHPublicKeyParameters) asymmetricKeyParameterCreateKey);
                    i2 = 8;
                } catch (ClassCastException unused) {
                    throw new TlsFatalAlert((short) 46);
                }
            } else {
                if (!tlsSigner.isValidPublicKey(asymmetricKeyParameterCreateKey)) {
                    throw new TlsFatalAlert((short) 46);
                }
                i2 = 128;
            }
            TlsUtils.validateKeyUsage(x509CertificateStructure, i2);
        } catch (RuntimeException unused2) {
            throw new TlsFatalAlert((short) 43);
        }
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processServerKeyExchange(InputStream inputStream) throws IOException {
        throw new TlsFatalAlert((short) 10);
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void skipClientCredentials() throws IOException {
        this.agreementCredentials = null;
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void skipServerCertificate() throws IOException {
        throw new TlsFatalAlert((short) 10);
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void skipServerKeyExchange() throws IOException {
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void validateCertificateRequest(CertificateRequest certificateRequest) throws IOException {
        for (short s2 : certificateRequest.getCertificateTypes()) {
            if (s2 != 1 && s2 != 2 && s2 != 3 && s2 != 4 && s2 != 64) {
                throw new TlsFatalAlert((short) 47);
            }
        }
    }

    public DHPublicKeyParameters validateDHPublicKey(DHPublicKeyParameters dHPublicKeyParameters) throws IOException {
        BigInteger y2 = dHPublicKeyParameters.getY();
        DHParameters parameters = dHPublicKeyParameters.getParameters();
        BigInteger p2 = parameters.getP();
        BigInteger g2 = parameters.getG();
        if (!p2.isProbablePrime(2)) {
            throw new TlsFatalAlert((short) 47);
        }
        BigInteger bigInteger = TWO;
        if (g2.compareTo(bigInteger) < 0 || g2.compareTo(p2.subtract(bigInteger)) > 0) {
            throw new TlsFatalAlert((short) 47);
        }
        if (y2.compareTo(bigInteger) < 0 || y2.compareTo(p2.subtract(ONE)) > 0) {
            throw new TlsFatalAlert((short) 47);
        }
        return dHPublicKeyParameters;
    }
}
