package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes9.dex */
public class DERGeneralString extends ASN1Object implements DERString {
    private String string;

    public DERGeneralString(String str) {
        this.string = str;
    }

    public DERGeneralString(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i2 = 0; i2 != length; i2++) {
            cArr[i2] = (char) (bArr[i2] & 255);
        }
        this.string = new String(cArr);
    }

    public static DERGeneralString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERGeneralString)) {
            return (DERGeneralString) obj;
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static DERGeneralString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        DERObject object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERGeneralString)) ? getInstance(object) : new DERGeneralString(((ASN1OctetString) object).getOctets());
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean asn1Equals(DERObject dERObject) {
        if (dERObject instanceof DERGeneralString) {
            return getString().equals(((DERGeneralString) dERObject).getString());
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        dEROutputStream.writeEncoded(27, getOctets());
    }

    public byte[] getOctets() {
        char[] charArray = this.string.toCharArray();
        byte[] bArr = new byte[charArray.length];
        for (int i2 = 0; i2 != charArray.length; i2++) {
            bArr[i2] = (byte) charArray[i2];
        }
        return bArr;
    }

    @Override // org.bouncycastle.asn1.ASN1String
    public String getString() {
        return this.string;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        return getString().hashCode();
    }

    public String toString() {
        return this.string;
    }
}
