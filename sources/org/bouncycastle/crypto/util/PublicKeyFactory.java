package org.bouncycastle.crypto.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.asn1.oiw.ElGamalParameter;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.DHParameter;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DSAParameter;
import org.bouncycastle.asn1.x509.RSAPublicKeyStructure;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.asn1.x9.DHDomainParameters;
import org.bouncycastle.asn1.x9.DHPublicKey;
import org.bouncycastle.asn1.x9.DHValidationParms;
import org.bouncycastle.asn1.x9.X962NamedCurves;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9ECPoint;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.crypto.params.DHValidationParameters;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;

/* loaded from: classes9.dex */
public class PublicKeyFactory {
    public static AsymmetricKeyParameter createKey(InputStream inputStream) throws IOException {
        return createKey(SubjectPublicKeyInfo.getInstance(new ASN1InputStream(inputStream).readObject()));
    }

    public static AsymmetricKeyParameter createKey(SubjectPublicKeyInfo subjectPublicKeyInfo) throws IOException {
        ECDomainParameters eCDomainParameters;
        AlgorithmIdentifier algorithmId = subjectPublicKeyInfo.getAlgorithmId();
        if (algorithmId.getObjectId().equals(PKCSObjectIdentifiers.rsaEncryption) || algorithmId.getObjectId().equals(X509ObjectIdentifiers.id_ea_rsa)) {
            RSAPublicKeyStructure rSAPublicKeyStructure = new RSAPublicKeyStructure((ASN1Sequence) subjectPublicKeyInfo.getPublicKey());
            return new RSAKeyParameters(false, rSAPublicKeyStructure.getModulus(), rSAPublicKeyStructure.getPublicExponent());
        }
        DSAParameters dSAParameters = null;
        if (algorithmId.getObjectId().equals(X9ObjectIdentifiers.dhpublicnumber)) {
            BigInteger value = DHPublicKey.getInstance(subjectPublicKeyInfo.getPublicKey()).getY().getValue();
            DHDomainParameters dHDomainParameters = DHDomainParameters.getInstance(subjectPublicKeyInfo.getAlgorithmId().getParameters());
            BigInteger value2 = dHDomainParameters.getP().getValue();
            BigInteger value3 = dHDomainParameters.getG().getValue();
            BigInteger value4 = dHDomainParameters.getQ().getValue();
            BigInteger value5 = dHDomainParameters.getJ() != null ? dHDomainParameters.getJ().getValue() : null;
            DHValidationParms validationParms = dHDomainParameters.getValidationParms();
            return new DHPublicKeyParameters(value, new DHParameters(value2, value3, value4, value5, validationParms != null ? new DHValidationParameters(validationParms.getSeed().getBytes(), validationParms.getPgenCounter().getValue().intValue()) : null));
        }
        if (algorithmId.getObjectId().equals(PKCSObjectIdentifiers.dhKeyAgreement)) {
            DHParameter dHParameter = new DHParameter((ASN1Sequence) subjectPublicKeyInfo.getAlgorithmId().getParameters());
            DERInteger dERInteger = (DERInteger) subjectPublicKeyInfo.getPublicKey();
            BigInteger l2 = dHParameter.getL();
            return new DHPublicKeyParameters(dERInteger.getValue(), new DHParameters(dHParameter.getP(), dHParameter.getG(), null, l2 != null ? l2.intValue() : 0));
        }
        if (algorithmId.getObjectId().equals(OIWObjectIdentifiers.elGamalAlgorithm)) {
            ElGamalParameter elGamalParameter = new ElGamalParameter((ASN1Sequence) subjectPublicKeyInfo.getAlgorithmId().getParameters());
            return new ElGamalPublicKeyParameters(((DERInteger) subjectPublicKeyInfo.getPublicKey()).getValue(), new ElGamalParameters(elGamalParameter.getP(), elGamalParameter.getG()));
        }
        if (algorithmId.getObjectId().equals(X9ObjectIdentifiers.id_dsa) || algorithmId.getObjectId().equals(OIWObjectIdentifiers.dsaWithSHA1)) {
            DERInteger dERInteger2 = (DERInteger) subjectPublicKeyInfo.getPublicKey();
            DEREncodable parameters = subjectPublicKeyInfo.getAlgorithmId().getParameters();
            if (parameters != null) {
                DSAParameter dSAParameter = DSAParameter.getInstance(parameters.getDERObject());
                dSAParameters = new DSAParameters(dSAParameter.getP(), dSAParameter.getQ(), dSAParameter.getG());
            }
            return new DSAPublicKeyParameters(dERInteger2.getValue(), dSAParameters);
        }
        if (!algorithmId.getObjectId().equals(X9ObjectIdentifiers.id_ecPublicKey)) {
            throw new RuntimeException("algorithm identifier in key not recognised");
        }
        X962Parameters x962Parameters = new X962Parameters((DERObject) subjectPublicKeyInfo.getAlgorithmId().getParameters());
        if (x962Parameters.isNamedCurve()) {
            DERObjectIdentifier dERObjectIdentifier = (DERObjectIdentifier) x962Parameters.getParameters();
            X9ECParameters byOID = X962NamedCurves.getByOID(dERObjectIdentifier);
            if (byOID == null && (byOID = SECNamedCurves.getByOID(dERObjectIdentifier)) == null && (byOID = NISTNamedCurves.getByOID(dERObjectIdentifier)) == null) {
                byOID = TeleTrusTNamedCurves.getByOID(dERObjectIdentifier);
            }
            eCDomainParameters = new ECDomainParameters(byOID.getCurve(), byOID.getG(), byOID.getN(), byOID.getH(), byOID.getSeed());
        } else {
            X9ECParameters x9ECParameters = new X9ECParameters((ASN1Sequence) x962Parameters.getParameters());
            eCDomainParameters = new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN(), x9ECParameters.getH(), x9ECParameters.getSeed());
        }
        return new ECPublicKeyParameters(new X9ECPoint(eCDomainParameters.getCurve(), new DEROctetString(subjectPublicKeyInfo.getPublicKeyData().getBytes())).getPoint(), eCDomainParameters);
    }

    public static AsymmetricKeyParameter createKey(byte[] bArr) throws IOException {
        return createKey(SubjectPublicKeyInfo.getInstance(ASN1Object.fromByteArray(bArr)));
    }
}
