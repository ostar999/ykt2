package com.mobile.auth.c;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* loaded from: classes4.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9649a = "i";

    public static h a(Context context, HttpURLConnection httpURLConnection) {
        String str;
        try {
            h hVar = new h();
            try {
                Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
                List<String> list = headerFields.get("Log-Level");
                if (list != null && !list.isEmpty()) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        String str2 = list.get(0);
                        if (!TextUtils.isEmpty(str2)) {
                            com.mobile.auth.b.e.b(context, str2);
                        }
                    }
                }
                List<String> list2 = headerFields.get(AliyunLogKey.KEY_DEVICE_MODEL);
                if (list2 != null && !list2.isEmpty() && (str = list2.get(0)) != null && (str.equals("1") || str.equals("2"))) {
                    String strH = k.h(context);
                    if (!TextUtils.isEmpty(strH) && !strH.equals(str)) {
                        hVar.f9648b = true;
                    }
                }
                List<String> list3 = headerFields.get("p-ikgx");
                if (list3 != null && !list3.isEmpty()) {
                    String str3 = list3.get(0);
                    if (!TextUtils.isEmpty(str3)) {
                        hVar.f9647a = str3;
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return hVar;
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 18, insn: 0x0199: MOVE (r7 I:??[OBJECT, ARRAY]) = (r18 I:??[OBJECT, ARRAY]), block:B:97:0x0199 */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01e1 A[Catch: all -> 0x02da, TRY_ENTER, TryCatch #19 {all -> 0x02da, blocks: (B:90:0x018b, B:92:0x0190, B:135:0x028d, B:107:0x01e6, B:105:0x01e1, B:114:0x022a, B:144:0x02d2, B:146:0x02d7), top: B:171:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01e6 A[Catch: all -> 0x02da, PHI: r2
      0x01e6: PHI (r2v18 java.io.InputStream) = (r2v11 java.io.InputStream), (r2v12 java.io.InputStream), (r2v19 java.io.InputStream) binds: [B:106:0x01e4, B:115:0x022d, B:136:0x0290] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #19 {all -> 0x02da, blocks: (B:90:0x018b, B:92:0x0190, B:135:0x028d, B:107:0x01e6, B:105:0x01e1, B:114:0x022a, B:144:0x02d2, B:146:0x02d7), top: B:171:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x022a A[Catch: all -> 0x02da, TRY_ENTER, TRY_LEAVE, TryCatch #19 {all -> 0x02da, blocks: (B:90:0x018b, B:92:0x0190, B:135:0x028d, B:107:0x01e6, B:105:0x01e1, B:114:0x022a, B:144:0x02d2, B:146:0x02d7), top: B:171:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0239 A[Catch: all -> 0x0230, TRY_ENTER, TryCatch #13 {all -> 0x0230, blocks: (B:124:0x0239, B:125:0x0273, B:127:0x0279, B:129:0x027e, B:128:0x027c, B:103:0x01a5, B:112:0x01ee), top: B:171:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0279 A[Catch: all -> 0x0230, TryCatch #13 {all -> 0x0230, blocks: (B:124:0x0239, B:125:0x0273, B:127:0x0279, B:129:0x027e, B:128:0x027c, B:103:0x01a5, B:112:0x01ee), top: B:171:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x027c A[Catch: all -> 0x0230, TryCatch #13 {all -> 0x0230, blocks: (B:124:0x0239, B:125:0x0273, B:127:0x0279, B:129:0x027e, B:128:0x027c, B:103:0x01a5, B:112:0x01ee), top: B:171:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0288 A[Catch: all -> 0x028b, TRY_LEAVE, TryCatch #10 {all -> 0x028b, blocks: (B:131:0x0283, B:133:0x0288), top: B:175:0x0283 }] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x028d A[Catch: all -> 0x02da, TRY_ENTER, TRY_LEAVE, TryCatch #19 {all -> 0x02da, blocks: (B:90:0x018b, B:92:0x0190, B:135:0x028d, B:107:0x01e6, B:105:0x01e1, B:114:0x022a, B:144:0x02d2, B:146:0x02d7), top: B:171:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02d2 A[Catch: all -> 0x02da, TRY_ENTER, TryCatch #19 {all -> 0x02da, blocks: (B:90:0x018b, B:92:0x0190, B:135:0x028d, B:107:0x01e6, B:105:0x01e1, B:114:0x022a, B:144:0x02d2, B:146:0x02d7), top: B:171:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x02d7 A[Catch: all -> 0x02da, TRY_LEAVE, TryCatch #19 {all -> 0x02da, blocks: (B:90:0x018b, B:92:0x0190, B:135:0x028d, B:107:0x01e6, B:105:0x01e1, B:114:0x022a, B:144:0x02d2, B:146:0x02d7), top: B:171:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x02e4 A[Catch: all -> 0x02e7, TRY_LEAVE, TryCatch #3 {all -> 0x02e7, blocks: (B:151:0x02df, B:153:0x02e4), top: B:166:0x02df }] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x02df A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0283 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x018b A[Catch: all -> 0x02da, TRY_ENTER, TryCatch #19 {all -> 0x02da, blocks: (B:90:0x018b, B:92:0x0190, B:135:0x028d, B:107:0x01e6, B:105:0x01e1, B:114:0x022a, B:144:0x02d2, B:146:0x02d7), top: B:171:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0190 A[Catch: all -> 0x02da, TRY_LEAVE, TryCatch #19 {all -> 0x02da, blocks: (B:90:0x018b, B:92:0x0190, B:135:0x028d, B:107:0x01e6, B:105:0x01e1, B:114:0x022a, B:144:0x02d2, B:146:0x02d7), top: B:171:0x0028 }] */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.net.Network] */
    /* JADX WARN: Type inference failed for: r2v13, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.io.BufferedReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.mobile.auth.c.n a(android.content.Context r19, java.lang.String r20, java.lang.String r21, android.net.Network r22, java.lang.String r23, java.lang.String r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 757
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.c.i.a(android.content.Context, java.lang.String, java.lang.String, android.net.Network, java.lang.String, java.lang.String, boolean):com.mobile.auth.c.n");
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00f4 A[Catch: Exception -> 0x00f0, all -> 0x0116, TryCatch #0 {Exception -> 0x00f0, blocks: (B:55:0x00ec, B:59:0x00f4, B:61:0x00f9), top: B:83:0x00ec }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00f9 A[Catch: Exception -> 0x00f0, all -> 0x0116, TRY_LEAVE, TryCatch #0 {Exception -> 0x00f0, blocks: (B:55:0x00ec, B:59:0x00f4, B:61:0x00f9), top: B:83:0x00ec }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00ec A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r16, java.lang.String r17, android.net.Network r18) {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.c.i.a(android.content.Context, java.lang.String, android.net.Network):java.lang.String");
    }

    public static final HostnameVerifier a() {
        try {
            return new HostnameVerifier() { // from class: com.mobile.auth.c.i.1
                @Override // javax.net.ssl.HostnameVerifier
                public boolean verify(String str, SSLSession sSLSession) {
                    try {
                        HostnameVerifier defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
                        if (!defaultHostnameVerifier.verify("id6.me", sSLSession)) {
                            if (!defaultHostnameVerifier.verify("card.e.189.cn", sSLSession)) {
                                return false;
                            }
                        }
                        return true;
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                            return false;
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                            return false;
                        }
                    }
                }
            };
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
}
