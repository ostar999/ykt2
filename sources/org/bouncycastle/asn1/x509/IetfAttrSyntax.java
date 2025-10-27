package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTF8String;

/* loaded from: classes9.dex */
public class IetfAttrSyntax extends ASN1Encodable {
    public static final int VALUE_OCTETS = 1;
    public static final int VALUE_OID = 2;
    public static final int VALUE_UTF8 = 3;
    GeneralNames policyAuthority;
    int valueChoice;
    Vector values = new Vector();

    /* JADX WARN: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public IetfAttrSyntax(org.bouncycastle.asn1.ASN1Sequence r6) {
        /*
            r5 = this;
            r5.<init>()
            r0 = 0
            r5.policyAuthority = r0
            java.util.Vector r0 = new java.util.Vector
            r0.<init>()
            r5.values = r0
            r0 = -1
            r5.valueChoice = r0
            r0 = 0
            org.bouncycastle.asn1.DEREncodable r1 = r6.getObjectAt(r0)
            boolean r1 = r1 instanceof org.bouncycastle.asn1.ASN1TaggedObject
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L29
            org.bouncycastle.asn1.DEREncodable r1 = r6.getObjectAt(r0)
            org.bouncycastle.asn1.ASN1TaggedObject r1 = (org.bouncycastle.asn1.ASN1TaggedObject) r1
            org.bouncycastle.asn1.x509.GeneralNames r0 = org.bouncycastle.asn1.x509.GeneralNames.getInstance(r1, r0)
        L25:
            r5.policyAuthority = r0
            r0 = r3
            goto L38
        L29:
            int r1 = r6.size()
            if (r1 != r2) goto L38
            org.bouncycastle.asn1.DEREncodable r0 = r6.getObjectAt(r0)
            org.bouncycastle.asn1.x509.GeneralNames r0 = org.bouncycastle.asn1.x509.GeneralNames.getInstance(r0)
            goto L25
        L38:
            org.bouncycastle.asn1.DEREncodable r1 = r6.getObjectAt(r0)
            boolean r1 = r1 instanceof org.bouncycastle.asn1.ASN1Sequence
            if (r1 == 0) goto L88
            org.bouncycastle.asn1.DEREncodable r6 = r6.getObjectAt(r0)
            org.bouncycastle.asn1.ASN1Sequence r6 = (org.bouncycastle.asn1.ASN1Sequence) r6
            java.util.Enumeration r6 = r6.getObjects()
        L4a:
            boolean r0 = r6.hasMoreElements()
            if (r0 == 0) goto L87
            java.lang.Object r0 = r6.nextElement()
            org.bouncycastle.asn1.DERObject r0 = (org.bouncycastle.asn1.DERObject) r0
            boolean r1 = r0 instanceof org.bouncycastle.asn1.DERObjectIdentifier
            if (r1 == 0) goto L5c
            r1 = r2
            goto L67
        L5c:
            boolean r1 = r0 instanceof org.bouncycastle.asn1.DERUTF8String
            if (r1 == 0) goto L62
            r1 = 3
            goto L67
        L62:
            boolean r1 = r0 instanceof org.bouncycastle.asn1.DEROctetString
            if (r1 == 0) goto L7f
            r1 = r3
        L67:
            int r4 = r5.valueChoice
            if (r4 >= 0) goto L6d
            r5.valueChoice = r1
        L6d:
            int r4 = r5.valueChoice
            if (r1 != r4) goto L77
            java.util.Vector r1 = r5.values
            r1.addElement(r0)
            goto L4a
        L77:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Mix of value types in IetfAttrSyntax"
            r6.<init>(r0)
            throw r6
        L7f:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Bad value type encoding IetfAttrSyntax"
            r6.<init>(r0)
            throw r6
        L87:
            return
        L88:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Non-IetfAttrSyntax encoding"
            r6.<init>(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x509.IetfAttrSyntax.<init>(org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public GeneralNames getPolicyAuthority() {
        return this.policyAuthority;
    }

    public int getValueType() {
        return this.valueChoice;
    }

    public Object[] getValues() {
        int i2 = 0;
        if (getValueType() == 1) {
            int size = this.values.size();
            ASN1OctetString[] aSN1OctetStringArr = new ASN1OctetString[size];
            while (i2 != size) {
                aSN1OctetStringArr[i2] = (ASN1OctetString) this.values.elementAt(i2);
                i2++;
            }
            return aSN1OctetStringArr;
        }
        if (getValueType() == 2) {
            int size2 = this.values.size();
            DERObjectIdentifier[] dERObjectIdentifierArr = new DERObjectIdentifier[size2];
            while (i2 != size2) {
                dERObjectIdentifierArr[i2] = (DERObjectIdentifier) this.values.elementAt(i2);
                i2++;
            }
            return dERObjectIdentifierArr;
        }
        int size3 = this.values.size();
        DERUTF8String[] dERUTF8StringArr = new DERUTF8String[size3];
        while (i2 != size3) {
            dERUTF8StringArr[i2] = (DERUTF8String) this.values.elementAt(i2);
            i2++;
        }
        return dERUTF8StringArr;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.policyAuthority != null) {
            aSN1EncodableVector.add(new DERTaggedObject(0, this.policyAuthority));
        }
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        Enumeration enumerationElements = this.values.elements();
        while (enumerationElements.hasMoreElements()) {
            aSN1EncodableVector2.add((ASN1Encodable) enumerationElements.nextElement());
        }
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        return new DERSequence(aSN1EncodableVector);
    }
}
