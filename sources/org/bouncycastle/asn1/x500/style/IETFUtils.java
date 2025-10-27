package org.bouncycastle.asn1.x500.style;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.X500NameStyle;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes9.dex */
public class IETFUtils {
    public static void appendTypeAndValue(StringBuffer stringBuffer, AttributeTypeAndValue attributeTypeAndValue, Hashtable hashtable) {
        String id = (String) hashtable.get(attributeTypeAndValue.getType());
        if (id == null) {
            id = attributeTypeAndValue.getType().getId();
        }
        stringBuffer.append(id);
        stringBuffer.append('=');
        stringBuffer.append(valueToString(attributeTypeAndValue.getValue()));
    }

    private static String bytesToString(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i2 = 0; i2 != length; i2++) {
            cArr[i2] = (char) (bArr[i2] & 255);
        }
        return new String(cArr);
    }

    public static String canonicalize(String str) {
        String lowerCase = Strings.toLowerCase(str.trim());
        if (lowerCase.length() > 0 && lowerCase.charAt(0) == '#') {
            DEREncodable dEREncodableDecodeObject = decodeObject(lowerCase);
            if (dEREncodableDecodeObject instanceof ASN1String) {
                lowerCase = Strings.toLowerCase(((ASN1String) dEREncodableDecodeObject).getString().trim());
            }
        }
        return stripInternalSpaces(lowerCase);
    }

    public static ASN1ObjectIdentifier decodeAttrName(String str, Hashtable hashtable) {
        if (Strings.toUpperCase(str).startsWith("OID.")) {
            return new ASN1ObjectIdentifier(str.substring(4));
        }
        if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
            return new ASN1ObjectIdentifier(str);
        }
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) hashtable.get(Strings.toLowerCase(str));
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        throw new IllegalArgumentException("Unknown object id - " + str + " - passed to distinguished name");
    }

    private static ASN1Object decodeObject(String str) {
        try {
            return ASN1Object.fromByteArray(Hex.decode(str.substring(1)));
        } catch (IOException e2) {
            throw new IllegalStateException("unknown encoding in name: " + e2);
        }
    }

    public static RDN[] rDNsFromString(String str, X500NameStyle x500NameStyle) {
        X500NameTokenizer x500NameTokenizer = new X500NameTokenizer(str);
        X500NameBuilder x500NameBuilder = new X500NameBuilder(x500NameStyle);
        while (x500NameTokenizer.hasMoreTokens()) {
            String strNextToken = x500NameTokenizer.nextToken();
            int iIndexOf = strNextToken.indexOf(61);
            if (iIndexOf == -1) {
                throw new IllegalArgumentException("badly formated directory string");
            }
            String strSubstring = strNextToken.substring(0, iIndexOf);
            String strSubstring2 = strNextToken.substring(iIndexOf + 1);
            ASN1ObjectIdentifier aSN1ObjectIdentifierAttrNameToOID = x500NameStyle.attrNameToOID(strSubstring);
            if (strSubstring2.indexOf(43) > 0) {
                X500NameTokenizer x500NameTokenizer2 = new X500NameTokenizer(strSubstring2, '+');
                String strNextToken2 = x500NameTokenizer2.nextToken();
                Vector vector = new Vector();
                Vector vector2 = new Vector();
                while (true) {
                    vector.addElement(aSN1ObjectIdentifierAttrNameToOID);
                    vector2.addElement(strNextToken2);
                    if (!x500NameTokenizer2.hasMoreTokens()) {
                        break;
                    }
                    String strNextToken3 = x500NameTokenizer2.nextToken();
                    int iIndexOf2 = strNextToken3.indexOf(61);
                    String strSubstring3 = strNextToken3.substring(0, iIndexOf2);
                    strNextToken2 = strNextToken3.substring(iIndexOf2 + 1);
                    aSN1ObjectIdentifierAttrNameToOID = x500NameStyle.attrNameToOID(strSubstring3);
                }
                x500NameBuilder.addMultiValuedRDN(toOIDArray(vector), toValueArray(vector2));
            } else {
                x500NameBuilder.addRDN(aSN1ObjectIdentifierAttrNameToOID, strSubstring2);
            }
        }
        return x500NameBuilder.build().getRDNs();
    }

    public static String stripInternalSpaces(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() != 0) {
            char cCharAt = str.charAt(0);
            stringBuffer.append(cCharAt);
            int i2 = 1;
            while (i2 < str.length()) {
                char cCharAt2 = str.charAt(i2);
                if (cCharAt != ' ' || cCharAt2 != ' ') {
                    stringBuffer.append(cCharAt2);
                }
                i2++;
                cCharAt = cCharAt2;
            }
        }
        return stringBuffer.toString();
    }

    private static ASN1ObjectIdentifier[] toOIDArray(Vector vector) {
        int size = vector.size();
        ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new ASN1ObjectIdentifier[size];
        for (int i2 = 0; i2 != size; i2++) {
            aSN1ObjectIdentifierArr[i2] = (ASN1ObjectIdentifier) vector.elementAt(i2);
        }
        return aSN1ObjectIdentifierArr;
    }

    private static String[] toValueArray(Vector vector) {
        int size = vector.size();
        String[] strArr = new String[size];
        for (int i2 = 0; i2 != size; i2++) {
            strArr[i2] = (String) vector.elementAt(i2);
        }
        return strArr;
    }

    public static ASN1Encodable valueFromHexString(String str, int i2) throws IOException {
        String lowerCase = Strings.toLowerCase(str);
        int length = (lowerCase.length() - i2) / 2;
        byte[] bArr = new byte[length];
        for (int i3 = 0; i3 != length; i3++) {
            int i4 = (i3 * 2) + i2;
            char cCharAt = lowerCase.charAt(i4);
            char cCharAt2 = lowerCase.charAt(i4 + 1);
            if (cCharAt < 'a') {
                bArr[i3] = (byte) ((cCharAt - '0') << 4);
            } else {
                bArr[i3] = (byte) (((cCharAt - 'a') + 10) << 4);
            }
            if (cCharAt2 < 'a') {
                bArr[i3] = (byte) (((byte) (cCharAt2 - '0')) | bArr[i3]);
            } else {
                bArr[i3] = (byte) (((byte) ((cCharAt2 - 'a') + 10)) | bArr[i3]);
            }
        }
        return ASN1Object.fromByteArray(bArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String valueToString(org.bouncycastle.asn1.ASN1Encodable r8) {
        /*
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            boolean r1 = r8 instanceof org.bouncycastle.asn1.ASN1String
            java.lang.String r2 = "\\"
            r3 = 35
            r4 = 0
            if (r1 == 0) goto L2d
            boolean r1 = r8 instanceof org.bouncycastle.asn1.DERUniversalString
            if (r1 != 0) goto L2d
            org.bouncycastle.asn1.ASN1String r8 = (org.bouncycastle.asn1.ASN1String) r8
            java.lang.String r8 = r8.getString()
            int r1 = r8.length()
            if (r1 <= 0) goto L4e
            char r1 = r8.charAt(r4)
            if (r1 != r3) goto L4e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r2)
            goto L47
        L2d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r5 = "#"
            r1.append(r5)
            org.bouncycastle.asn1.DERObject r8 = r8.getDERObject()
            byte[] r8 = r8.getDEREncoded()
            byte[] r8 = org.bouncycastle.util.encoders.Hex.encode(r8)
            java.lang.String r8 = bytesToString(r8)
        L47:
            r1.append(r8)
            java.lang.String r8 = r1.toString()
        L4e:
            r0.append(r8)
            int r8 = r0.length()
            int r1 = r0.length()
            r5 = 92
            r6 = 2
            r7 = 1
            if (r1 < r6) goto L6c
            char r1 = r0.charAt(r4)
            if (r1 != r5) goto L6c
            char r1 = r0.charAt(r7)
            if (r1 != r3) goto L6c
            r4 = r6
        L6c:
            if (r4 == r8) goto Lb5
            char r1 = r0.charAt(r4)
            r3 = 44
            if (r1 == r3) goto Lac
            char r1 = r0.charAt(r4)
            r3 = 34
            if (r1 == r3) goto Lac
            char r1 = r0.charAt(r4)
            if (r1 == r5) goto Lac
            char r1 = r0.charAt(r4)
            r3 = 43
            if (r1 == r3) goto Lac
            char r1 = r0.charAt(r4)
            r3 = 61
            if (r1 == r3) goto Lac
            char r1 = r0.charAt(r4)
            r3 = 60
            if (r1 == r3) goto Lac
            char r1 = r0.charAt(r4)
            r3 = 62
            if (r1 == r3) goto Lac
            char r1 = r0.charAt(r4)
            r3 = 59
            if (r1 != r3) goto Lb3
        Lac:
            r0.insert(r4, r2)
            int r4 = r4 + 1
            int r8 = r8 + 1
        Lb3:
            int r4 = r4 + r7
            goto L6c
        Lb5:
            java.lang.String r8 = r0.toString()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x500.style.IETFUtils.valueToString(org.bouncycastle.asn1.ASN1Encodable):java.lang.String");
    }
}
