package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

/* loaded from: classes9.dex */
public class DERObjectIdentifier extends ASN1Object {
    String identifier;

    public DERObjectIdentifier(String str) {
        if (isValidIdentifier(str)) {
            this.identifier = str;
            return;
        }
        throw new IllegalArgumentException("string " + str + " not an OID");
    }

    public DERObjectIdentifier(byte[] bArr) {
        BigInteger bigIntegerOr;
        long j2;
        StringBuffer stringBuffer = new StringBuffer();
        int i2 = 1;
        boolean z2 = true;
        int i3 = 0;
        long j3 = 0;
        BigInteger bigInteger = null;
        while (i3 != bArr.length) {
            int i4 = bArr[i3] & 255;
            if (j3 < 36028797018963968L) {
                j3 = (j3 * 128) + (i4 & 127);
                if ((i4 & 128) == 0) {
                    if (z2) {
                        int i5 = ((int) j3) / 40;
                        if (i5 != 0) {
                            if (i5 != i2) {
                                stringBuffer.append('2');
                                j2 = 80;
                            } else {
                                stringBuffer.append('1');
                                j2 = 40;
                            }
                            j3 -= j2;
                        } else {
                            stringBuffer.append('0');
                        }
                        z2 = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(j3);
                    bigIntegerOr = bigInteger;
                    j3 = 0;
                } else {
                    bigIntegerOr = bigInteger;
                }
            } else {
                bigIntegerOr = (bigInteger == null ? BigInteger.valueOf(j3) : bigInteger).shiftLeft(7).or(BigInteger.valueOf(i4 & 127));
                if ((i4 & 128) == 0) {
                    stringBuffer.append('.');
                    stringBuffer.append(bigIntegerOr);
                    bigIntegerOr = null;
                    j3 = 0;
                }
            }
            i3++;
            bigInteger = bigIntegerOr;
            i2 = 1;
        }
        this.identifier = stringBuffer.toString();
    }

    public static DERObjectIdentifier getInstance(Object obj) {
        if (obj == null || (obj instanceof DERObjectIdentifier)) {
            return (DERObjectIdentifier) obj;
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static DERObjectIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        DERObject object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERObjectIdentifier)) ? getInstance(object) : new ASN1ObjectIdentifier(ASN1OctetString.getInstance(aSN1TaggedObject.getObject()).getOctets());
    }

    private static boolean isValidIdentifier(String str) {
        char cCharAt;
        if (str.length() < 3 || str.charAt(1) != '.' || (cCharAt = str.charAt(0)) < '0' || cCharAt > '2') {
            return false;
        }
        boolean z2 = false;
        for (int length = str.length() - 1; length >= 2; length--) {
            char cCharAt2 = str.charAt(length);
            if ('0' <= cCharAt2 && cCharAt2 <= '9') {
                z2 = true;
            } else {
                if (cCharAt2 != '.' || !z2) {
                    return false;
                }
                z2 = false;
            }
        }
        return z2;
    }

    private void writeField(OutputStream outputStream, long j2) throws IOException {
        byte[] bArr = new byte[9];
        int i2 = 8;
        bArr[8] = (byte) (((int) j2) & 127);
        while (j2 >= 128) {
            j2 >>= 7;
            i2--;
            bArr[i2] = (byte) ((((int) j2) & 127) | 128);
        }
        outputStream.write(bArr, i2, 9 - i2);
    }

    private void writeField(OutputStream outputStream, BigInteger bigInteger) throws IOException {
        int iBitLength = (bigInteger.bitLength() + 6) / 7;
        if (iBitLength == 0) {
            outputStream.write(0);
            return;
        }
        byte[] bArr = new byte[iBitLength];
        int i2 = iBitLength - 1;
        for (int i3 = i2; i3 >= 0; i3--) {
            bArr[i3] = (byte) ((bigInteger.intValue() & 127) | 128);
            bigInteger = bigInteger.shiftRight(7);
        }
        bArr[i2] = (byte) (bArr[i2] & 127);
        outputStream.write(bArr);
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean asn1Equals(DERObject dERObject) {
        if (dERObject instanceof DERObjectIdentifier) {
            return this.identifier.equals(((DERObjectIdentifier) dERObject).identifier);
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException, NumberFormatException {
        String strNextToken;
        OIDTokenizer oIDTokenizer = new OIDTokenizer(this.identifier);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DEROutputStream dEROutputStream2 = new DEROutputStream(byteArrayOutputStream);
        long j2 = (Integer.parseInt(oIDTokenizer.nextToken()) * 40) + Integer.parseInt(oIDTokenizer.nextToken());
        while (true) {
            writeField(byteArrayOutputStream, j2);
            while (oIDTokenizer.hasMoreTokens()) {
                strNextToken = oIDTokenizer.nextToken();
                if (strNextToken.length() < 18) {
                    break;
                } else {
                    writeField(byteArrayOutputStream, new BigInteger(strNextToken));
                }
            }
            dEROutputStream2.close();
            dEROutputStream.writeEncoded(6, byteArrayOutputStream.toByteArray());
            return;
            j2 = Long.parseLong(strNextToken);
        }
    }

    public String getId() {
        return this.identifier;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        return this.identifier.hashCode();
    }

    public String toString() {
        return getId();
    }
}
