package org.bouncycastle.asn1.x509;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public abstract class X509NameEntryConverter {
    public boolean canBePrintable(String str) {
        return DERPrintableString.isPrintableString(str);
    }

    public DERObject convertHexEncoded(String str, int i2) throws IOException {
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
        return new ASN1InputStream(bArr).readObject();
    }

    public abstract DERObject getConvertedValue(DERObjectIdentifier dERObjectIdentifier, String str);
}
