package com.mobile.auth.gatewayauth.detectnet;

import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public class d {
    public static c a(c cVar, String str) {
        String str2 = "unknown host";
        try {
            if (str.contains("0% packet loss")) {
                int iIndexOf = str.indexOf("/mdev = ");
                int iIndexOf2 = str.indexOf(" ms\n", iIndexOf);
                if (iIndexOf != -1 && iIndexOf2 != -1) {
                    String[] strArrSplit = str.substring(iIndexOf + 8, iIndexOf2).split("/");
                    cVar.a(true);
                    cVar.a((int) Float.parseFloat(strArrSplit[1]));
                    return cVar;
                }
                str2 = "Error: " + str;
            } else if (str.contains("100% packet loss")) {
                str2 = "100% packet loss";
            } else if (str.contains("% packet loss")) {
                str2 = "partial packet loss";
            } else if (!str.contains("unknown host")) {
                str2 = "unknown error in getPingStats";
            }
            cVar.b(str2);
            cVar.a(10004);
            return cVar;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00de A[Catch: all -> 0x0128, TRY_LEAVE, TryCatch #2 {all -> 0x0128, blocks: (B:3:0x0001, B:5:0x000c, B:7:0x0028, B:9:0x002e, B:10:0x0048, B:12:0x0059, B:17:0x0068, B:18:0x0088, B:29:0x00b0, B:33:0x00c1, B:34:0x00cb, B:36:0x00da, B:35:0x00cf, B:38:0x00de, B:39:0x00ec, B:41:0x00f2, B:45:0x00ff, B:44:0x00fc, B:28:0x0094, B:15:0x0062, B:48:0x0109), top: B:58:0x0001, inners: #0, #7 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.mobile.auth.gatewayauth.detectnet.c a(java.lang.String r10, int r11) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.gatewayauth.detectnet.d.a(java.lang.String, int):com.mobile.auth.gatewayauth.detectnet.c");
    }
}
