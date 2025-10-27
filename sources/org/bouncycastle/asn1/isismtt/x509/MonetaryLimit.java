package org.bouncycastle.asn1.isismtt.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class MonetaryLimit extends ASN1Encodable {
    DERInteger amount;
    DERPrintableString currency;
    DERInteger exponent;

    public MonetaryLimit(String str, int i2, int i3) {
        this.currency = new DERPrintableString(str, true);
        this.amount = new DERInteger(i2);
        this.exponent = new DERInteger(i3);
    }

    private MonetaryLimit(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 3) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.currency = DERPrintableString.getInstance(objects.nextElement());
        this.amount = DERInteger.getInstance(objects.nextElement());
        this.exponent = DERInteger.getInstance(objects.nextElement());
    }

    public static MonetaryLimit getInstance(Object obj) {
        if (obj == null || (obj instanceof MonetaryLimit)) {
            return (MonetaryLimit) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new MonetaryLimit(ASN1Sequence.getInstance(obj));
        }
        throw new IllegalArgumentException("unknown object in getInstance");
    }

    public BigInteger getAmount() {
        return this.amount.getValue();
    }

    public String getCurrency() {
        return this.currency.getString();
    }

    public BigInteger getExponent() {
        return this.exponent.getValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.currency);
        aSN1EncodableVector.add(this.amount);
        aSN1EncodableVector.add(this.exponent);
        return new DERSequence(aSN1EncodableVector);
    }
}
