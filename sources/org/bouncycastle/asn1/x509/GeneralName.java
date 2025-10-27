package org.bouncycastle.asn1.x509;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.util.StringTokenizer;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.util.IPAddress;

/* loaded from: classes9.dex */
public class GeneralName extends ASN1Encodable implements ASN1Choice {
    public static final int dNSName = 2;
    public static final int directoryName = 4;
    public static final int ediPartyName = 5;
    public static final int iPAddress = 7;
    public static final int otherName = 0;
    public static final int registeredID = 8;
    public static final int rfc822Name = 1;
    public static final int uniformResourceIdentifier = 6;
    public static final int x400Address = 3;
    DEREncodable obj;
    int tag;

    public GeneralName(int i2, String str) throws NumberFormatException {
        DEREncodable dERIA5String;
        this.tag = i2;
        if (i2 == 1 || i2 == 2 || i2 == 6) {
            dERIA5String = new DERIA5String(str);
        } else if (i2 == 8) {
            dERIA5String = new DERObjectIdentifier(str);
        } else {
            if (i2 != 4) {
                if (i2 != 7) {
                    throw new IllegalArgumentException("can't process String for tag: " + i2);
                }
                byte[] generalNameEncoding = toGeneralNameEncoding(str);
                if (generalNameEncoding == null) {
                    throw new IllegalArgumentException("IP Address is invalid");
                }
                this.obj = new DEROctetString(generalNameEncoding);
                return;
            }
            dERIA5String = new X509Name(str);
        }
        this.obj = dERIA5String;
    }

    public GeneralName(int i2, ASN1Encodable aSN1Encodable) {
        this.obj = aSN1Encodable;
        this.tag = i2;
    }

    public GeneralName(DERObject dERObject, int i2) {
        this.obj = dERObject;
        this.tag = i2;
    }

    public GeneralName(X500Name x500Name) {
        this.obj = x500Name;
        this.tag = 4;
    }

    public GeneralName(X509Name x509Name) {
        this.obj = x509Name;
        this.tag = 4;
    }

    private void copyInts(int[] iArr, byte[] bArr, int i2) {
        for (int i3 = 0; i3 != iArr.length; i3++) {
            int i4 = i3 * 2;
            int i5 = iArr[i3];
            bArr[i4 + i2] = (byte) (i5 >> 8);
            bArr[i4 + 1 + i2] = (byte) i5;
        }
    }

    public static GeneralName getInstance(Object obj) {
        if (obj == null || (obj instanceof GeneralName)) {
            return (GeneralName) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) obj;
            int tagNo = aSN1TaggedObject.getTagNo();
            switch (tagNo) {
                case 0:
                    return new GeneralName(tagNo, ASN1Sequence.getInstance(aSN1TaggedObject, false));
                case 1:
                    return new GeneralName(tagNo, DERIA5String.getInstance(aSN1TaggedObject, false));
                case 2:
                    return new GeneralName(tagNo, DERIA5String.getInstance(aSN1TaggedObject, false));
                case 3:
                    throw new IllegalArgumentException("unknown tag: " + tagNo);
                case 4:
                    return new GeneralName(tagNo, X509Name.getInstance(aSN1TaggedObject, true));
                case 5:
                    return new GeneralName(tagNo, ASN1Sequence.getInstance(aSN1TaggedObject, false));
                case 6:
                    return new GeneralName(tagNo, DERIA5String.getInstance(aSN1TaggedObject, false));
                case 7:
                    return new GeneralName(tagNo, ASN1OctetString.getInstance(aSN1TaggedObject, false));
                case 8:
                    return new GeneralName(tagNo, DERObjectIdentifier.getInstance(aSN1TaggedObject, false));
            }
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Object.fromByteArray((byte[]) obj));
            } catch (IOException unused) {
                throw new IllegalArgumentException("unable to parse encoded general name");
            }
        }
        throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public static GeneralName getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1TaggedObject.getInstance(aSN1TaggedObject, true));
    }

    private void parseIPv4(String str, byte[] bArr, int i2) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, "./");
        int i3 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            bArr[i3 + i2] = (byte) Integer.parseInt(stringTokenizer.nextToken());
            i3++;
        }
    }

    private void parseIPv4Mask(String str, byte[] bArr, int i2) throws NumberFormatException {
        int i3 = Integer.parseInt(str);
        for (int i4 = 0; i4 != i3; i4++) {
            int i5 = (i4 / 8) + i2;
            bArr[i5] = (byte) (bArr[i5] | (1 << (i4 % 8)));
        }
    }

    private int[] parseIPv6(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ":", true);
        int[] iArr = new int[8];
        if (str.charAt(0) == ':' && str.charAt(1) == ':') {
            stringTokenizer.nextToken();
        }
        int i2 = -1;
        int i3 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            if (strNextToken.equals(":")) {
                iArr[i3] = 0;
                int i4 = i3;
                i3++;
                i2 = i4;
            } else if (strNextToken.indexOf(46) < 0) {
                int i5 = i3 + 1;
                iArr[i3] = Integer.parseInt(strNextToken, 16);
                if (stringTokenizer.hasMoreTokens()) {
                    stringTokenizer.nextToken();
                }
                i3 = i5;
            } else {
                StringTokenizer stringTokenizer2 = new StringTokenizer(strNextToken, StrPool.DOT);
                int i6 = i3 + 1;
                iArr[i3] = (Integer.parseInt(stringTokenizer2.nextToken()) << 8) | Integer.parseInt(stringTokenizer2.nextToken());
                i3 = i6 + 1;
                iArr[i6] = Integer.parseInt(stringTokenizer2.nextToken()) | (Integer.parseInt(stringTokenizer2.nextToken()) << 8);
            }
        }
        if (i3 != 8) {
            int i7 = i3 - i2;
            int i8 = 8 - i7;
            System.arraycopy(iArr, i2, iArr, i8, i7);
            while (i2 != i8) {
                iArr[i2] = 0;
                i2++;
            }
        }
        return iArr;
    }

    private int[] parseMask(String str) throws NumberFormatException {
        int[] iArr = new int[8];
        int i2 = Integer.parseInt(str);
        for (int i3 = 0; i3 != i2; i3++) {
            int i4 = i3 / 16;
            iArr[i4] = iArr[i4] | (1 << (i3 % 16));
        }
        return iArr;
    }

    private byte[] toGeneralNameEncoding(String str) throws NumberFormatException {
        if (IPAddress.isValidIPv6WithNetmask(str) || IPAddress.isValidIPv6(str)) {
            int iIndexOf = str.indexOf(47);
            if (iIndexOf < 0) {
                byte[] bArr = new byte[16];
                copyInts(parseIPv6(str), bArr, 0);
                return bArr;
            }
            byte[] bArr2 = new byte[32];
            copyInts(parseIPv6(str.substring(0, iIndexOf)), bArr2, 0);
            String strSubstring = str.substring(iIndexOf + 1);
            copyInts(strSubstring.indexOf(58) > 0 ? parseIPv6(strSubstring) : parseMask(strSubstring), bArr2, 16);
            return bArr2;
        }
        if (!IPAddress.isValidIPv4WithNetmask(str) && !IPAddress.isValidIPv4(str)) {
            return null;
        }
        int iIndexOf2 = str.indexOf(47);
        if (iIndexOf2 < 0) {
            byte[] bArr3 = new byte[4];
            parseIPv4(str, bArr3, 0);
            return bArr3;
        }
        byte[] bArr4 = new byte[8];
        parseIPv4(str.substring(0, iIndexOf2), bArr4, 0);
        String strSubstring2 = str.substring(iIndexOf2 + 1);
        if (strSubstring2.indexOf(46) > 0) {
            parseIPv4(strSubstring2, bArr4, 4);
        } else {
            parseIPv4Mask(strSubstring2, bArr4, 4);
        }
        return bArr4;
    }

    public DEREncodable getName() {
        return this.obj;
    }

    public int getTagNo() {
        return this.tag;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return this.tag == 4 ? new DERTaggedObject(true, this.tag, this.obj) : new DERTaggedObject(false, this.tag, this.obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            r3 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            int r1 = r3.tag
            r0.append(r1)
            java.lang.String r1 = ": "
            r0.append(r1)
            int r1 = r3.tag
            r2 = 1
            if (r1 == r2) goto L2f
            r2 = 2
            if (r1 == r2) goto L2f
            r2 = 4
            if (r1 == r2) goto L24
            r2 = 6
            if (r1 == r2) goto L2f
            org.bouncycastle.asn1.DEREncodable r1 = r3.obj
            java.lang.String r1 = r1.toString()
            goto L39
        L24:
            org.bouncycastle.asn1.DEREncodable r1 = r3.obj
            org.bouncycastle.asn1.x509.X509Name r1 = org.bouncycastle.asn1.x509.X509Name.getInstance(r1)
            java.lang.String r1 = r1.toString()
            goto L39
        L2f:
            org.bouncycastle.asn1.DEREncodable r1 = r3.obj
            org.bouncycastle.asn1.DERIA5String r1 = org.bouncycastle.asn1.DERIA5String.getInstance(r1)
            java.lang.String r1 = r1.getString()
        L39:
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x509.GeneralName.toString():java.lang.String");
    }
}
