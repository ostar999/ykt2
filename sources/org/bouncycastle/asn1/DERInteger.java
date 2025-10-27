package org.bouncycastle.asn1;

import java.io.IOException;
import java.math.BigInteger;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class DERInteger extends ASN1Object {
    byte[] bytes;

    public DERInteger(int i2) {
        this.bytes = BigInteger.valueOf(i2).toByteArray();
    }

    public DERInteger(BigInteger bigInteger) {
        this.bytes = bigInteger.toByteArray();
    }

    public DERInteger(byte[] bArr) {
        this.bytes = bArr;
    }

    public static DERInteger getInstance(Object obj) {
        if (obj == null || (obj instanceof DERInteger)) {
            return (DERInteger) obj;
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static DERInteger getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        DERObject object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERInteger)) ? getInstance(object) : new ASN1Integer(ASN1OctetString.getInstance(aSN1TaggedObject.getObject()).getOctets());
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean asn1Equals(DERObject dERObject) {
        if (dERObject instanceof DERInteger) {
            return Arrays.areEqual(this.bytes, ((DERInteger) dERObject).bytes);
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        dEROutputStream.writeEncoded(2, this.bytes);
    }

    public BigInteger getPositiveValue() {
        return new BigInteger(1, this.bytes);
    }

    public BigInteger getValue() {
        return new BigInteger(this.bytes);
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.bytes;
            if (i2 == bArr.length) {
                return i3;
            }
            i3 ^= (bArr[i2] & 255) << (i2 % 4);
            i2++;
        }
    }

    public String toString() {
        return getValue().toString();
    }
}
