package com.ta.a.a;

/* loaded from: classes6.dex */
public class a {
    public static b a(String str) {
        b bVar = new b();
        if (str == null || str.length() != 24) {
            bVar.a(false);
        } else {
            try {
                byte[] bArrDecode = com.ta.utdid2.a.a.b.decode(str, 2);
                if (bArrDecode.length == 18) {
                    byte[] bArr = new byte[4];
                    System.arraycopy(bArrDecode, 0, bArr, 0, 4);
                    byte b3 = bArrDecode[8];
                    try {
                        bVar.a(true);
                        bVar.setTimestamp(com.ta.a.e.b.a(bArr));
                        bVar.setVersion(b3);
                        return bVar;
                    } catch (Exception unused) {
                    }
                }
            } catch (Exception unused2) {
                bVar.a(false);
                return bVar;
            }
        }
        return bVar;
    }
}
