package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.oiw.ElGamalParameter;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.spec.ElGamalParameterSpec;
import org.bouncycastle.jce.spec.ElGamalPrivateKeySpec;

/* loaded from: classes9.dex */
public class JCEElGamalPrivateKey implements ElGamalPrivateKey, DHPrivateKey, PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 4819350091141529678L;
    private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
    ElGamalParameterSpec elSpec;

    /* renamed from: x, reason: collision with root package name */
    BigInteger f27894x;

    public JCEElGamalPrivateKey() {
    }

    public JCEElGamalPrivateKey(DHPrivateKey dHPrivateKey) {
        this.f27894x = dHPrivateKey.getX();
        this.elSpec = new ElGamalParameterSpec(dHPrivateKey.getParams().getP(), dHPrivateKey.getParams().getG());
    }

    public JCEElGamalPrivateKey(DHPrivateKeySpec dHPrivateKeySpec) {
        this.f27894x = dHPrivateKeySpec.getX();
        this.elSpec = new ElGamalParameterSpec(dHPrivateKeySpec.getP(), dHPrivateKeySpec.getG());
    }

    public JCEElGamalPrivateKey(PrivateKeyInfo privateKeyInfo) {
        ElGamalParameter elGamalParameter = new ElGamalParameter((ASN1Sequence) privateKeyInfo.getAlgorithmId().getParameters());
        this.f27894x = ((DERInteger) privateKeyInfo.getPrivateKey()).getValue();
        this.elSpec = new ElGamalParameterSpec(elGamalParameter.getP(), elGamalParameter.getG());
    }

    public JCEElGamalPrivateKey(ElGamalPrivateKeyParameters elGamalPrivateKeyParameters) {
        this.f27894x = elGamalPrivateKeyParameters.getX();
        this.elSpec = new ElGamalParameterSpec(elGamalPrivateKeyParameters.getParameters().getP(), elGamalPrivateKeyParameters.getParameters().getG());
    }

    public JCEElGamalPrivateKey(ElGamalPrivateKey elGamalPrivateKey) {
        this.f27894x = elGamalPrivateKey.getX();
        this.elSpec = elGamalPrivateKey.getParameters();
    }

    public JCEElGamalPrivateKey(ElGamalPrivateKeySpec elGamalPrivateKeySpec) {
        this.f27894x = elGamalPrivateKeySpec.getX();
        this.elSpec = new ElGamalParameterSpec(elGamalPrivateKeySpec.getParams().getP(), elGamalPrivateKeySpec.getParams().getG());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.f27894x = (BigInteger) objectInputStream.readObject();
        this.elSpec = new ElGamalParameterSpec((BigInteger) objectInputStream.readObject(), (BigInteger) objectInputStream.readObject());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(getX());
        objectOutputStream.writeObject(this.elSpec.getP());
        objectOutputStream.writeObject(this.elSpec.getG());
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return "ElGamal";
    }

    @Override // org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public DEREncodable getBagAttribute(DERObjectIdentifier dERObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(dERObjectIdentifier);
    }

    @Override // org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return new PrivateKeyInfo(new AlgorithmIdentifier(OIWObjectIdentifiers.elGamalAlgorithm, new ElGamalParameter(this.elSpec.getP(), this.elSpec.getG()).getDERObject()), new DERInteger(getX())).getDEREncoded();
    }

    @Override // java.security.Key
    public String getFormat() {
        return "PKCS#8";
    }

    @Override // org.bouncycastle.jce.interfaces.ElGamalKey
    public ElGamalParameterSpec getParameters() {
        return this.elSpec;
    }

    @Override // javax.crypto.interfaces.DHKey
    public DHParameterSpec getParams() {
        return new DHParameterSpec(this.elSpec.getP(), this.elSpec.getG());
    }

    @Override // org.bouncycastle.jce.interfaces.ElGamalPrivateKey, javax.crypto.interfaces.DHPrivateKey
    public BigInteger getX() {
        return this.f27894x;
    }

    @Override // org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public void setBagAttribute(DERObjectIdentifier dERObjectIdentifier, DEREncodable dEREncodable) {
        this.attrCarrier.setBagAttribute(dERObjectIdentifier, dEREncodable);
    }
}
