package org.bouncycastle.jce;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.jce.provider.ProviderUtil;
import org.bouncycastle.jce.provider.asymmetric.ec.ECUtil;

/* loaded from: classes9.dex */
public class ECKeyUtil {

    public static class UnexpectedException extends RuntimeException {
        private Throwable cause;

        public UnexpectedException(Throwable th) {
            super(th.toString());
            this.cause = th;
        }

        @Override // java.lang.Throwable
        public Throwable getCause() {
            return this.cause;
        }
    }

    public static PrivateKey privateToExplicitParameters(PrivateKey privateKey, String str) throws NoSuchAlgorithmException, IllegalArgumentException, NoSuchProviderException {
        Provider provider = Security.getProvider(str);
        if (provider != null) {
            return privateToExplicitParameters(privateKey, provider);
        }
        throw new NoSuchProviderException("cannot find provider: " + str);
    }

    public static PrivateKey privateToExplicitParameters(PrivateKey privateKey, Provider provider) throws NoSuchAlgorithmException, IllegalArgumentException, UnsupportedEncodingException {
        X9ECParameters x9ECParameters;
        try {
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(ASN1Object.fromByteArray(privateKey.getEncoded()));
            if (privateKeyInfo.getAlgorithmId().getObjectId().equals(CryptoProObjectIdentifiers.gostR3410_2001)) {
                throw new UnsupportedEncodingException("cannot convert GOST key to explicit parameters.");
            }
            X962Parameters x962Parameters = new X962Parameters((DERObject) privateKeyInfo.getAlgorithmId().getParameters());
            if (x962Parameters.isNamedCurve()) {
                X9ECParameters namedCurveByOid = ECUtil.getNamedCurveByOid((DERObjectIdentifier) x962Parameters.getParameters());
                x9ECParameters = new X9ECParameters(namedCurveByOid.getCurve(), namedCurveByOid.getG(), namedCurveByOid.getN(), namedCurveByOid.getH());
            } else {
                if (!x962Parameters.isImplicitlyCA()) {
                    return privateKey;
                }
                x9ECParameters = new X9ECParameters(ProviderUtil.getEcImplicitlyCa().getCurve(), ProviderUtil.getEcImplicitlyCa().getG(), ProviderUtil.getEcImplicitlyCa().getN(), ProviderUtil.getEcImplicitlyCa().getH());
            }
            return KeyFactory.getInstance(privateKey.getAlgorithm(), provider).generatePrivate(new PKCS8EncodedKeySpec(new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, new X962Parameters(x9ECParameters).getDERObject()), privateKeyInfo.getPrivateKey()).getEncoded()));
        } catch (IllegalArgumentException e2) {
            throw e2;
        } catch (NoSuchAlgorithmException e3) {
            throw e3;
        } catch (Exception e4) {
            throw new UnexpectedException(e4);
        }
    }

    public static PublicKey publicToExplicitParameters(PublicKey publicKey, String str) throws NoSuchAlgorithmException, IllegalArgumentException, NoSuchProviderException {
        Provider provider = Security.getProvider(str);
        if (provider != null) {
            return publicToExplicitParameters(publicKey, provider);
        }
        throw new NoSuchProviderException("cannot find provider: " + str);
    }

    public static PublicKey publicToExplicitParameters(PublicKey publicKey, Provider provider) throws NoSuchAlgorithmException, IllegalArgumentException {
        X9ECParameters x9ECParameters;
        try {
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(ASN1Object.fromByteArray(publicKey.getEncoded()));
            if (subjectPublicKeyInfo.getAlgorithmId().getObjectId().equals(CryptoProObjectIdentifiers.gostR3410_2001)) {
                throw new IllegalArgumentException("cannot convert GOST key to explicit parameters.");
            }
            X962Parameters x962Parameters = new X962Parameters((DERObject) subjectPublicKeyInfo.getAlgorithmId().getParameters());
            if (x962Parameters.isNamedCurve()) {
                X9ECParameters namedCurveByOid = ECUtil.getNamedCurveByOid((DERObjectIdentifier) x962Parameters.getParameters());
                x9ECParameters = new X9ECParameters(namedCurveByOid.getCurve(), namedCurveByOid.getG(), namedCurveByOid.getN(), namedCurveByOid.getH());
            } else {
                if (!x962Parameters.isImplicitlyCA()) {
                    return publicKey;
                }
                x9ECParameters = new X9ECParameters(ProviderUtil.getEcImplicitlyCa().getCurve(), ProviderUtil.getEcImplicitlyCa().getG(), ProviderUtil.getEcImplicitlyCa().getN(), ProviderUtil.getEcImplicitlyCa().getH());
            }
            return KeyFactory.getInstance(publicKey.getAlgorithm(), provider).generatePublic(new X509EncodedKeySpec(new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, new X962Parameters(x9ECParameters).getDERObject()), subjectPublicKeyInfo.getPublicKeyData().getBytes()).getEncoded()));
        } catch (IllegalArgumentException e2) {
            throw e2;
        } catch (NoSuchAlgorithmException e3) {
            throw e3;
        } catch (Exception e4) {
            throw new UnexpectedException(e4);
        }
    }
}
