package org.bouncycastle.asn1.util;

import java.io.FileInputStream;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERObject;

/* loaded from: classes9.dex */
public class Dump {
    public static void main(String[] strArr) throws Exception {
        ASN1InputStream aSN1InputStream = new ASN1InputStream(new FileInputStream(strArr[0]));
        while (true) {
            DERObject object = aSN1InputStream.readObject();
            if (object == null) {
                return;
            } else {
                System.out.println(ASN1Dump.dumpAsString(object));
            }
        }
    }
}
