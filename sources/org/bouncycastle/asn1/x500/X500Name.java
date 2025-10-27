package org.bouncycastle.asn1.x500;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.X509Name;

/* loaded from: classes9.dex */
public class X500Name extends ASN1Encodable implements ASN1Choice {
    private static X500NameStyle defaultStyle = BCStyle.INSTANCE;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private RDN[] rdns;
    private X500NameStyle style;

    public X500Name(String str) {
        this(defaultStyle, str);
    }

    private X500Name(ASN1Sequence aSN1Sequence) {
        this(defaultStyle, aSN1Sequence);
    }

    public X500Name(X500NameStyle x500NameStyle, String str) {
        this(x500NameStyle.fromString(str));
        this.style = x500NameStyle;
    }

    private X500Name(X500NameStyle x500NameStyle, ASN1Sequence aSN1Sequence) {
        this.style = x500NameStyle;
        this.rdns = new RDN[aSN1Sequence.size()];
        Enumeration objects = aSN1Sequence.getObjects();
        int i2 = 0;
        while (objects.hasMoreElements()) {
            this.rdns[i2] = RDN.getInstance(objects.nextElement());
            i2++;
        }
    }

    public X500Name(X500NameStyle x500NameStyle, X500Name x500Name) {
        this.rdns = x500Name.rdns;
        this.style = x500NameStyle;
    }

    public X500Name(X500NameStyle x500NameStyle, RDN[] rdnArr) {
        this.rdns = rdnArr;
        this.style = x500NameStyle;
    }

    public X500Name(RDN[] rdnArr) {
        this(defaultStyle, rdnArr);
    }

    public static X500NameStyle getDefaultStyle() {
        return defaultStyle;
    }

    public static X500Name getInstance(Object obj) {
        if (obj instanceof X500Name) {
            return (X500Name) obj;
        }
        if (obj instanceof X509Name) {
            return new X500Name(ASN1Sequence.getInstance(((X509Name) obj).getDERObject()));
        }
        if (obj != null) {
            return new X500Name(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static X500Name getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, true));
    }

    public static void setDefaultStyle(X500NameStyle x500NameStyle) {
        if (x500NameStyle == null) {
            throw new NullPointerException("cannot set style to null");
        }
        defaultStyle = x500NameStyle;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X500Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (getDERObject().equals(((DEREncodable) obj).getDERObject())) {
            return true;
        }
        try {
            return this.style.areEqual(this, new X500Name(ASN1Sequence.getInstance(((DEREncodable) obj).getDERObject())));
        } catch (Exception unused) {
            return false;
        }
    }

    public RDN[] getRDNs() {
        RDN[] rdnArr = this.rdns;
        int length = rdnArr.length;
        RDN[] rdnArr2 = new RDN[length];
        System.arraycopy(rdnArr, 0, rdnArr2, 0, length);
        return rdnArr2;
    }

    public RDN[] getRDNs(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        int i2;
        RDN[] rdnArr = new RDN[this.rdns.length];
        int i3 = 0;
        int i4 = 0;
        while (true) {
            RDN[] rdnArr2 = this.rdns;
            if (i3 == rdnArr2.length) {
                RDN[] rdnArr3 = new RDN[i4];
                System.arraycopy(rdnArr, 0, rdnArr3, 0, i4);
                return rdnArr3;
            }
            RDN rdn = rdnArr2[i3];
            if (!rdn.isMultiValued()) {
                if (rdn.getFirst().getType().equals(aSN1ObjectIdentifier)) {
                    i2 = i4 + 1;
                    rdnArr[i4] = rdn;
                    i4 = i2;
                    break;
                    break;
                }
            } else {
                AttributeTypeAndValue[] typesAndValues = rdn.getTypesAndValues();
                for (int i5 = 0; i5 != typesAndValues.length; i5++) {
                    if (typesAndValues[i5].getType().equals(aSN1ObjectIdentifier)) {
                        i2 = i4 + 1;
                        rdnArr[i4] = rdn;
                        i4 = i2;
                        break;
                    }
                }
            }
            i3++;
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        int iCalculateHashCode = this.style.calculateHashCode(this);
        this.hashCodeValue = iCalculateHashCode;
        return iCalculateHashCode;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return new DERSequence(this.rdns);
    }

    public String toString() {
        return this.style.toString(this);
    }
}
