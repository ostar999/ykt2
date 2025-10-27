package org.bouncycastle.asn1;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.slf4j.Marker;

/* loaded from: classes9.dex */
public class DERGeneralizedTime extends ASN1Object {
    String time;

    public DERGeneralizedTime(String str) {
        this.time = str;
        try {
            getDate();
        } catch (ParseException e2) {
            throw new IllegalArgumentException("invalid date string: " + e2.getMessage());
        }
    }

    public DERGeneralizedTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.time = simpleDateFormat.format(date);
    }

    public DERGeneralizedTime(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i2 = 0; i2 != length; i2++) {
            cArr[i2] = (char) (bArr[i2] & 255);
        }
        this.time = new String(cArr);
    }

    private String calculateGMTOffset() {
        String str;
        TimeZone timeZone = TimeZone.getDefault();
        int rawOffset = timeZone.getRawOffset();
        if (rawOffset < 0) {
            rawOffset = -rawOffset;
            str = "-";
        } else {
            str = Marker.ANY_NON_NULL_MARKER;
        }
        int i2 = rawOffset / 3600000;
        int i3 = (rawOffset - (((i2 * 60) * 60) * 1000)) / 60000;
        try {
            if (timeZone.useDaylightTime() && timeZone.inDaylightTime(getDate())) {
                i2 += str.equals(Marker.ANY_NON_NULL_MARKER) ? 1 : -1;
            }
        } catch (ParseException unused) {
        }
        return "GMT" + str + convert(i2) + ":" + convert(i3);
    }

    private String convert(int i2) {
        if (i2 >= 10) {
            return Integer.toString(i2);
        }
        return "0" + i2;
    }

    public static DERGeneralizedTime getInstance(Object obj) {
        if (obj == null || (obj instanceof DERGeneralizedTime)) {
            return (DERGeneralizedTime) obj;
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static DERGeneralizedTime getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        DERObject object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERGeneralizedTime)) ? getInstance(object) : new DERGeneralizedTime(((ASN1OctetString) object).getOctets());
    }

    private byte[] getOctets() {
        char[] charArray = this.time.toCharArray();
        byte[] bArr = new byte[charArray.length];
        for (int i2 = 0; i2 != charArray.length; i2++) {
            bArr[i2] = (byte) charArray[i2];
        }
        return bArr;
    }

    private boolean hasFractionalSeconds() {
        return this.time.indexOf(46) == 14;
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean asn1Equals(DERObject dERObject) {
        if (dERObject instanceof DERGeneralizedTime) {
            return this.time.equals(((DERGeneralizedTime) dERObject).time);
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        dEROutputStream.writeEncoded(24, getOctets());
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x008a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Date getDate() throws java.text.ParseException {
        /*
            Method dump skipped, instructions count: 295
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.DERGeneralizedTime.getDate():java.util.Date");
    }

    public String getTime() {
        if (this.time.charAt(r0.length() - 1) == 'Z') {
            StringBuilder sb = new StringBuilder();
            sb.append(this.time.substring(0, r1.length() - 1));
            sb.append("GMT+00:00");
            return sb.toString();
        }
        int length = this.time.length() - 5;
        char cCharAt = this.time.charAt(length);
        if (cCharAt == '-' || cCharAt == '+') {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.time.substring(0, length));
            sb2.append("GMT");
            int i2 = length + 3;
            sb2.append(this.time.substring(length, i2));
            sb2.append(":");
            sb2.append(this.time.substring(i2));
            return sb2.toString();
        }
        int length2 = this.time.length() - 3;
        char cCharAt2 = this.time.charAt(length2);
        if (cCharAt2 != '-' && cCharAt2 != '+') {
            return this.time + calculateGMTOffset();
        }
        return this.time.substring(0, length2) + "GMT" + this.time.substring(length2) + ":00";
    }

    public String getTimeString() {
        return this.time;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        return this.time.hashCode();
    }
}
