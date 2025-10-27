package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class ExtendedKeyUsage extends ASN1Encodable {
    ASN1Sequence seq;
    Hashtable usageTable = new Hashtable();

    public ExtendedKeyUsage(Vector vector) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Enumeration enumerationElements = vector.elements();
        while (enumerationElements.hasMoreElements()) {
            DERObject dERObject = (DERObject) enumerationElements.nextElement();
            aSN1EncodableVector.add(dERObject);
            this.usageTable.put(dERObject, dERObject);
        }
        this.seq = new DERSequence(aSN1EncodableVector);
    }

    public ExtendedKeyUsage(ASN1Sequence aSN1Sequence) {
        this.seq = aSN1Sequence;
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            Object objNextElement = objects.nextElement();
            if (!(objNextElement instanceof DERObjectIdentifier)) {
                throw new IllegalArgumentException("Only DERObjectIdentifiers allowed in ExtendedKeyUsage.");
            }
            this.usageTable.put(objNextElement, objNextElement);
        }
    }

    public ExtendedKeyUsage(KeyPurposeId keyPurposeId) {
        this.seq = new DERSequence(keyPurposeId);
        this.usageTable.put(keyPurposeId, keyPurposeId);
    }

    public static ExtendedKeyUsage getInstance(Object obj) {
        if (obj instanceof ExtendedKeyUsage) {
            return (ExtendedKeyUsage) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new ExtendedKeyUsage((ASN1Sequence) obj);
        }
        if (obj instanceof X509Extension) {
            return getInstance(X509Extension.convertValueToObject((X509Extension) obj));
        }
        throw new IllegalArgumentException("Invalid ExtendedKeyUsage: " + obj.getClass().getName());
    }

    public static ExtendedKeyUsage getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    public Vector getUsages() {
        Vector vector = new Vector();
        Enumeration enumerationElements = this.usageTable.elements();
        while (enumerationElements.hasMoreElements()) {
            vector.addElement(enumerationElements.nextElement());
        }
        return vector;
    }

    public boolean hasKeyPurposeId(KeyPurposeId keyPurposeId) {
        return this.usageTable.get(keyPurposeId) != null;
    }

    public int size() {
        return this.usageTable.size();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return this.seq;
    }
}
