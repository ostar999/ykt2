package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.sec.ECPrivateKeyStructure;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jce.interfaces.ECPointEncoder;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.provider.asymmetric.ec.EC5Util;
import org.bouncycastle.jce.provider.asymmetric.ec.ECUtil;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECCurve;

/* loaded from: classes9.dex */
public class JCEECPrivateKey implements ECPrivateKey, org.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    private String algorithm;
    private PKCS12BagAttributeCarrierImpl attrCarrier;

    /* renamed from: d, reason: collision with root package name */
    private BigInteger f27892d;
    private ECParameterSpec ecSpec;
    private DERBitString publicKey;
    private boolean withCompression;

    public JCEECPrivateKey() {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    public JCEECPrivateKey(String str, ECPrivateKeySpec eCPrivateKeySpec) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.f27892d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
    }

    public JCEECPrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.f27892d = eCPrivateKeyParameters.getD();
        this.ecSpec = null;
    }

    public JCEECPrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, JCEECPublicKey jCEECPublicKey, ECParameterSpec eCParameterSpec) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        this.algorithm = str;
        this.f27892d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), new ECPoint(parameters.getG().getX().toBigInteger(), parameters.getG().getY().toBigInteger()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.publicKey = getPublicKeyDetails(jCEECPublicKey);
    }

    public JCEECPrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, JCEECPublicKey jCEECPublicKey, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        this.algorithm = str;
        this.f27892d = eCPrivateKeyParameters.getD();
        this.ecSpec = eCParameterSpec == null ? new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), new ECPoint(parameters.getG().getX().toBigInteger(), parameters.getG().getY().toBigInteger()), parameters.getN(), parameters.getH().intValue()) : new ECParameterSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), new ECPoint(eCParameterSpec.getG().getX().toBigInteger(), eCParameterSpec.getG().getY().toBigInteger()), eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
        this.publicKey = getPublicKeyDetails(jCEECPublicKey);
    }

    public JCEECPrivateKey(String str, JCEECPrivateKey jCEECPrivateKey) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.f27892d = jCEECPrivateKey.f27892d;
        this.ecSpec = jCEECPrivateKey.ecSpec;
        this.withCompression = jCEECPrivateKey.withCompression;
        this.attrCarrier = jCEECPrivateKey.attrCarrier;
        this.publicKey = jCEECPrivateKey.publicKey;
    }

    public JCEECPrivateKey(String str, org.bouncycastle.jce.spec.ECPrivateKeySpec eCPrivateKeySpec) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.f27892d = eCPrivateKeySpec.getD();
        this.ecSpec = eCPrivateKeySpec.getParams() != null ? EC5Util.convertSpec(EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams()) : null;
    }

    public JCEECPrivateKey(ECPrivateKey eCPrivateKey) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.f27892d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
    }

    public JCEECPrivateKey(PrivateKeyInfo privateKeyInfo) {
        this.algorithm = "EC";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        populateFromPrivKeyInfo(privateKeyInfo);
    }

    private DERBitString getPublicKeyDetails(JCEECPublicKey jCEECPublicKey) {
        try {
            return SubjectPublicKeyInfo.getInstance(ASN1Object.fromByteArray(jCEECPublicKey.getEncoded())).getPublicKeyData();
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0104  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void populateFromPrivKeyInfo(org.bouncycastle.asn1.pkcs.PrivateKeyInfo r11) {
        /*
            r10 = this;
            org.bouncycastle.asn1.x9.X962Parameters r0 = new org.bouncycastle.asn1.x9.X962Parameters
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r11.getAlgorithmId()
            org.bouncycastle.asn1.DEREncodable r1 = r1.getParameters()
            org.bouncycastle.asn1.DERObject r1 = (org.bouncycastle.asn1.DERObject) r1
            r0.<init>(r1)
            boolean r1 = r0.isNamedCurve()
            if (r1 == 0) goto L9e
            org.bouncycastle.asn1.DERObject r0 = r0.getParameters()
            org.bouncycastle.asn1.DERObjectIdentifier r0 = (org.bouncycastle.asn1.DERObjectIdentifier) r0
            org.bouncycastle.asn1.x9.X9ECParameters r1 = org.bouncycastle.jce.provider.asymmetric.ec.ECUtil.getNamedCurveByOid(r0)
            if (r1 != 0) goto L62
            org.bouncycastle.crypto.params.ECDomainParameters r1 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getByOID(r0)
            org.bouncycastle.math.ec.ECCurve r2 = r1.getCurve()
            byte[] r3 = r1.getSeed()
            java.security.spec.EllipticCurve r6 = org.bouncycastle.jce.provider.asymmetric.ec.EC5Util.convertCurve(r2, r3)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r2 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            java.lang.String r5 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getName(r0)
            java.security.spec.ECPoint r7 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r0 = r1.getG()
            org.bouncycastle.math.ec.ECFieldElement r0 = r0.getX()
            java.math.BigInteger r0 = r0.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r3 = r1.getG()
            org.bouncycastle.math.ec.ECFieldElement r3 = r3.getY()
            java.math.BigInteger r3 = r3.toBigInteger()
            r7.<init>(r0, r3)
            java.math.BigInteger r8 = r1.getN()
            java.math.BigInteger r9 = r1.getH()
            r4 = r2
            r4.<init>(r5, r6, r7, r8, r9)
            goto Led
        L62:
            org.bouncycastle.math.ec.ECCurve r2 = r1.getCurve()
            byte[] r3 = r1.getSeed()
            java.security.spec.EllipticCurve r6 = org.bouncycastle.jce.provider.asymmetric.ec.EC5Util.convertCurve(r2, r3)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r2 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            java.lang.String r5 = org.bouncycastle.jce.provider.asymmetric.ec.ECUtil.getCurveName(r0)
            java.security.spec.ECPoint r7 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r0 = r1.getG()
            org.bouncycastle.math.ec.ECFieldElement r0 = r0.getX()
            java.math.BigInteger r0 = r0.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r3 = r1.getG()
            org.bouncycastle.math.ec.ECFieldElement r3 = r3.getY()
            java.math.BigInteger r3 = r3.toBigInteger()
            r7.<init>(r0, r3)
            java.math.BigInteger r8 = r1.getN()
            java.math.BigInteger r9 = r1.getH()
            r4 = r2
            r4.<init>(r5, r6, r7, r8, r9)
            goto Led
        L9e:
            boolean r1 = r0.isImplicitlyCA()
            if (r1 == 0) goto La8
            r0 = 0
            r10.ecSpec = r0
            goto Lef
        La8:
            org.bouncycastle.asn1.x9.X9ECParameters r1 = new org.bouncycastle.asn1.x9.X9ECParameters
            org.bouncycastle.asn1.DERObject r0 = r0.getParameters()
            org.bouncycastle.asn1.ASN1Sequence r0 = (org.bouncycastle.asn1.ASN1Sequence) r0
            r1.<init>(r0)
            org.bouncycastle.math.ec.ECCurve r0 = r1.getCurve()
            byte[] r2 = r1.getSeed()
            java.security.spec.EllipticCurve r0 = org.bouncycastle.jce.provider.asymmetric.ec.EC5Util.convertCurve(r0, r2)
            java.security.spec.ECParameterSpec r2 = new java.security.spec.ECParameterSpec
            java.security.spec.ECPoint r3 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r4 = r1.getG()
            org.bouncycastle.math.ec.ECFieldElement r4 = r4.getX()
            java.math.BigInteger r4 = r4.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r5 = r1.getG()
            org.bouncycastle.math.ec.ECFieldElement r5 = r5.getY()
            java.math.BigInteger r5 = r5.toBigInteger()
            r3.<init>(r4, r5)
            java.math.BigInteger r4 = r1.getN()
            java.math.BigInteger r1 = r1.getH()
            int r1 = r1.intValue()
            r2.<init>(r0, r3, r4, r1)
        Led:
            r10.ecSpec = r2
        Lef:
            org.bouncycastle.asn1.DERObject r0 = r11.getPrivateKey()
            boolean r0 = r0 instanceof org.bouncycastle.asn1.DERInteger
            if (r0 == 0) goto L104
            org.bouncycastle.asn1.DERObject r11 = r11.getPrivateKey()
            org.bouncycastle.asn1.DERInteger r11 = (org.bouncycastle.asn1.DERInteger) r11
            java.math.BigInteger r11 = r11.getValue()
            r10.f27892d = r11
            goto L11b
        L104:
            org.bouncycastle.asn1.sec.ECPrivateKeyStructure r0 = new org.bouncycastle.asn1.sec.ECPrivateKeyStructure
            org.bouncycastle.asn1.DERObject r11 = r11.getPrivateKey()
            org.bouncycastle.asn1.ASN1Sequence r11 = (org.bouncycastle.asn1.ASN1Sequence) r11
            r0.<init>(r11)
            java.math.BigInteger r11 = r0.getKey()
            r10.f27892d = r11
            org.bouncycastle.asn1.DERBitString r11 = r0.getPublicKey()
            r10.publicKey = r11
        L11b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.JCEECPrivateKey.populateFromPrivKeyInfo(org.bouncycastle.asn1.pkcs.PrivateKeyInfo):void");
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Object.fromByteArray((byte[]) objectInputStream.readObject())));
        this.algorithm = (String) objectInputStream.readObject();
        this.withCompression = objectInputStream.readBoolean();
        PKCS12BagAttributeCarrierImpl pKCS12BagAttributeCarrierImpl = new PKCS12BagAttributeCarrierImpl();
        this.attrCarrier = pKCS12BagAttributeCarrierImpl;
        pKCS12BagAttributeCarrierImpl.readObject(objectInputStream);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(getEncoded());
        objectOutputStream.writeObject(this.algorithm);
        objectOutputStream.writeBoolean(this.withCompression);
        this.attrCarrier.writeObject(objectOutputStream);
    }

    public org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        return eCParameterSpec != null ? EC5Util.convertSpec(eCParameterSpec, this.withCompression) : ProviderUtil.getEcImplicitlyCa();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof JCEECPrivateKey)) {
            return false;
        }
        JCEECPrivateKey jCEECPrivateKey = (JCEECPrivateKey) obj;
        return getD().equals(jCEECPrivateKey.getD()) && engineGetSpec().equals(jCEECPrivateKey.engineGetSpec());
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override // org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public DEREncodable getBagAttribute(DERObjectIdentifier dERObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(dERObjectIdentifier);
    }

    @Override // org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    @Override // org.bouncycastle.jce.interfaces.ECPrivateKey
    public BigInteger getD() {
        return this.f27892d;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        X962Parameters x962Parameters;
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec instanceof ECNamedCurveSpec) {
            DERObjectIdentifier namedCurveOid = ECUtil.getNamedCurveOid(((ECNamedCurveSpec) eCParameterSpec).getName());
            if (namedCurveOid == null) {
                namedCurveOid = new DERObjectIdentifier(((ECNamedCurveSpec) this.ecSpec).getName());
            }
            x962Parameters = new X962Parameters(namedCurveOid);
        } else if (eCParameterSpec == null) {
            x962Parameters = new X962Parameters(DERNull.INSTANCE);
        } else {
            ECCurve eCCurveConvertCurve = EC5Util.convertCurve(eCParameterSpec.getCurve());
            x962Parameters = new X962Parameters(new X9ECParameters(eCCurveConvertCurve, EC5Util.convertPoint(eCCurveConvertCurve, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf(this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
        }
        ECPrivateKeyStructure eCPrivateKeyStructure = this.publicKey != null ? new ECPrivateKeyStructure(getS(), this.publicKey, x962Parameters) : new ECPrivateKeyStructure(getS(), x962Parameters);
        return (this.algorithm.equals("ECGOST3410") ? new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, x962Parameters.getDERObject()), eCPrivateKeyStructure.getDERObject()) : new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, x962Parameters.getDERObject()), eCPrivateKeyStructure.getDERObject())).getDEREncoded();
    }

    @Override // java.security.Key
    public String getFormat() {
        return "PKCS#8";
    }

    @Override // org.bouncycastle.jce.interfaces.ECKey
    public org.bouncycastle.jce.spec.ECParameterSpec getParameters() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec == null) {
            return null;
        }
        return EC5Util.convertSpec(eCParameterSpec, this.withCompression);
    }

    @Override // java.security.interfaces.ECKey
    public ECParameterSpec getParams() {
        return this.ecSpec;
    }

    @Override // java.security.interfaces.ECPrivateKey
    public BigInteger getS() {
        return this.f27892d;
    }

    public int hashCode() {
        return getD().hashCode() ^ engineGetSpec().hashCode();
    }

    @Override // org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public void setBagAttribute(DERObjectIdentifier dERObjectIdentifier, DEREncodable dEREncodable) {
        this.attrCarrier.setBagAttribute(dERObjectIdentifier, dEREncodable);
    }

    @Override // org.bouncycastle.jce.interfaces.ECPointEncoder
    public void setPointFormat(String str) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("EC Private Key");
        stringBuffer.append(property);
        stringBuffer.append("             S: ");
        stringBuffer.append(this.f27892d.toString(16));
        stringBuffer.append(property);
        return stringBuffer.toString();
    }
}
