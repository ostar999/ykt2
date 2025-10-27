package com.mobile.auth.v;

import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static ConcurrentHashMap<String, HostnameVerifier> f10563a = new ConcurrentHashMap<>();

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0197 A[Catch: all -> 0x0193, TryCatch #16 {all -> 0x0193, blocks: (B:97:0x018f, B:101:0x0197, B:103:0x019c, B:105:0x01a1, B:107:0x01a6), top: B:139:0x018f, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:103:0x019c A[Catch: all -> 0x0193, TryCatch #16 {all -> 0x0193, blocks: (B:97:0x018f, B:101:0x0197, B:103:0x019c, B:105:0x01a1, B:107:0x01a6), top: B:139:0x018f, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01a1 A[Catch: all -> 0x0193, TryCatch #16 {all -> 0x0193, blocks: (B:97:0x018f, B:101:0x0197, B:103:0x019c, B:105:0x01a1, B:107:0x01a6), top: B:139:0x018f, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01a6 A[Catch: all -> 0x0193, TRY_LEAVE, TryCatch #16 {all -> 0x0193, blocks: (B:97:0x018f, B:101:0x0197, B:103:0x019c, B:105:0x01a1, B:107:0x01a6), top: B:139:0x018f, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x01b9 A[Catch: all -> 0x01b5, TryCatch #20 {all -> 0x01b5, blocks: (B:113:0x01b1, B:117:0x01b9, B:119:0x01be, B:121:0x01c3, B:123:0x01c8), top: B:143:0x01b1, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01be A[Catch: all -> 0x01b5, TryCatch #20 {all -> 0x01b5, blocks: (B:113:0x01b1, B:117:0x01b9, B:119:0x01be, B:121:0x01c3, B:123:0x01c8), top: B:143:0x01b1, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01c3 A[Catch: all -> 0x01b5, TryCatch #20 {all -> 0x01b5, blocks: (B:113:0x01b1, B:117:0x01b9, B:119:0x01be, B:121:0x01c3, B:123:0x01c8), top: B:143:0x01b1, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01c8 A[Catch: all -> 0x01b5, TRY_LEAVE, TryCatch #20 {all -> 0x01b5, blocks: (B:113:0x01b1, B:117:0x01b9, B:119:0x01be, B:121:0x01c3, B:123:0x01c8), top: B:143:0x01b1, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0164 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x018f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01b1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x016c A[Catch: all -> 0x0168, TryCatch #5 {all -> 0x0168, blocks: (B:78:0x0164, B:82:0x016c, B:84:0x0171, B:86:0x0176, B:88:0x017b), top: B:133:0x0164, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0171 A[Catch: all -> 0x0168, TryCatch #5 {all -> 0x0168, blocks: (B:78:0x0164, B:82:0x016c, B:84:0x0171, B:86:0x0176, B:88:0x017b), top: B:133:0x0164, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0176 A[Catch: all -> 0x0168, TryCatch #5 {all -> 0x0168, blocks: (B:78:0x0164, B:82:0x016c, B:84:0x0171, B:86:0x0176, B:88:0x017b), top: B:133:0x0164, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x017b A[Catch: all -> 0x0168, TRY_LEAVE, TryCatch #5 {all -> 0x0168, blocks: (B:78:0x0164, B:82:0x016c, B:84:0x0171, B:86:0x0176, B:88:0x017b), top: B:133:0x0164, outer: #15 }] */
    /* JADX WARN: Type inference failed for: r10v0, types: [int] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v19 */
    /* JADX WARN: Type inference failed for: r10v22 */
    /* JADX WARN: Type inference failed for: r10v26 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r10v7, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r11v0, types: [android.net.Network] */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.net.URL] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v10, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v24 */
    /* JADX WARN: Type inference failed for: r7v3, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r7v30 */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v36 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v23 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v7, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r9v0, types: [int] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v22 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v7, types: [java.net.HttpURLConnection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r6, java.lang.String r7, java.lang.String r8, int r9, int r10, android.net.Network r11) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 474
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.v.a.a(android.content.Context, java.lang.String, java.lang.String, int, int, android.net.Network):java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01a8 A[Catch: all -> 0x01a4, TryCatch #3 {all -> 0x01a4, blocks: (B:105:0x01a0, B:109:0x01a8, B:111:0x01ad, B:113:0x01b2, B:115:0x01b7), top: B:129:0x01a0, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01ad A[Catch: all -> 0x01a4, TryCatch #3 {all -> 0x01a4, blocks: (B:105:0x01a0, B:109:0x01a8, B:111:0x01ad, B:113:0x01b2, B:115:0x01b7), top: B:129:0x01a0, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01b2 A[Catch: all -> 0x01a4, TryCatch #3 {all -> 0x01a4, blocks: (B:105:0x01a0, B:109:0x01a8, B:111:0x01ad, B:113:0x01b2, B:115:0x01b7), top: B:129:0x01a0, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01b7 A[Catch: all -> 0x01a4, TRY_LEAVE, TryCatch #3 {all -> 0x01a4, blocks: (B:105:0x01a0, B:109:0x01a8, B:111:0x01ad, B:113:0x01b2, B:115:0x01b7), top: B:129:0x01a0, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x017e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0150 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0158 A[Catch: all -> 0x0154, TryCatch #14 {all -> 0x0154, blocks: (B:71:0x0150, B:75:0x0158, B:77:0x015d, B:79:0x0162, B:81:0x0167), top: B:133:0x0150, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x015d A[Catch: all -> 0x0154, TryCatch #14 {all -> 0x0154, blocks: (B:71:0x0150, B:75:0x0158, B:77:0x015d, B:79:0x0162, B:81:0x0167), top: B:133:0x0150, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0162 A[Catch: all -> 0x0154, TryCatch #14 {all -> 0x0154, blocks: (B:71:0x0150, B:75:0x0158, B:77:0x015d, B:79:0x0162, B:81:0x0167), top: B:133:0x0150, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0167 A[Catch: all -> 0x0154, TRY_LEAVE, TryCatch #14 {all -> 0x0154, blocks: (B:71:0x0150, B:75:0x0158, B:77:0x015d, B:79:0x0162, B:81:0x0167), top: B:133:0x0150, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0186 A[Catch: all -> 0x0182, TryCatch #1 {all -> 0x0182, blocks: (B:89:0x017e, B:93:0x0186, B:95:0x018b, B:97:0x0190, B:99:0x0195), top: B:125:0x017e, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x018b A[Catch: all -> 0x0182, TryCatch #1 {all -> 0x0182, blocks: (B:89:0x017e, B:93:0x0186, B:95:0x018b, B:97:0x0190, B:99:0x0195), top: B:125:0x017e, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0190 A[Catch: all -> 0x0182, TryCatch #1 {all -> 0x0182, blocks: (B:89:0x017e, B:93:0x0186, B:95:0x018b, B:97:0x0190, B:99:0x0195), top: B:125:0x017e, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0195 A[Catch: all -> 0x0182, TRY_LEAVE, TryCatch #1 {all -> 0x0182, blocks: (B:89:0x017e, B:93:0x0186, B:95:0x018b, B:97:0x0190, B:99:0x0195), top: B:125:0x017e, outer: #24 }] */
    /* JADX WARN: Type inference failed for: r10v0, types: [int] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v17 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v20 */
    /* JADX WARN: Type inference failed for: r10v22 */
    /* JADX WARN: Type inference failed for: r10v24 */
    /* JADX WARN: Type inference failed for: r10v26 */
    /* JADX WARN: Type inference failed for: r10v27 */
    /* JADX WARN: Type inference failed for: r10v28 */
    /* JADX WARN: Type inference failed for: r10v29 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v30 */
    /* JADX WARN: Type inference failed for: r10v31 */
    /* JADX WARN: Type inference failed for: r10v32 */
    /* JADX WARN: Type inference failed for: r10v33 */
    /* JADX WARN: Type inference failed for: r10v34 */
    /* JADX WARN: Type inference failed for: r10v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.mobile.auth.v.c] */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v25, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v35 */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.io.OutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(com.mobile.auth.v.c r8, int r9, int r10) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 457
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.v.a.a(com.mobile.auth.v.c, int, int):java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0139 A[Catch: all -> 0x0135, TryCatch #17 {all -> 0x0135, blocks: (B:100:0x0131, B:104:0x0139, B:106:0x013e, B:108:0x0143, B:110:0x0148), top: B:130:0x0131, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:106:0x013e A[Catch: all -> 0x0135, TryCatch #17 {all -> 0x0135, blocks: (B:100:0x0131, B:104:0x0139, B:106:0x013e, B:108:0x0143, B:110:0x0148), top: B:130:0x0131, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0143 A[Catch: all -> 0x0135, TryCatch #17 {all -> 0x0135, blocks: (B:100:0x0131, B:104:0x0139, B:106:0x013e, B:108:0x0143, B:110:0x0148), top: B:130:0x0131, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0148 A[Catch: all -> 0x0135, TRY_LEAVE, TryCatch #17 {all -> 0x0135, blocks: (B:100:0x0131, B:104:0x0139, B:106:0x013e, B:108:0x0143, B:110:0x0148), top: B:130:0x0131, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x00e4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x010f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0131 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00ec A[Catch: all -> 0x00e8, TryCatch #6 {all -> 0x00e8, blocks: (B:66:0x00e4, B:70:0x00ec, B:72:0x00f1, B:74:0x00f6, B:76:0x00fb), top: B:122:0x00e4, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00f1 A[Catch: all -> 0x00e8, TryCatch #6 {all -> 0x00e8, blocks: (B:66:0x00e4, B:70:0x00ec, B:72:0x00f1, B:74:0x00f6, B:76:0x00fb), top: B:122:0x00e4, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00f6 A[Catch: all -> 0x00e8, TryCatch #6 {all -> 0x00e8, blocks: (B:66:0x00e4, B:70:0x00ec, B:72:0x00f1, B:74:0x00f6, B:76:0x00fb), top: B:122:0x00e4, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00fb A[Catch: all -> 0x00e8, TRY_LEAVE, TryCatch #6 {all -> 0x00e8, blocks: (B:66:0x00e4, B:70:0x00ec, B:72:0x00f1, B:74:0x00f6, B:76:0x00fb), top: B:122:0x00e4, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0117 A[Catch: all -> 0x0113, TryCatch #14 {all -> 0x0113, blocks: (B:84:0x010f, B:88:0x0117, B:90:0x011c, B:92:0x0121, B:94:0x0126), top: B:126:0x010f, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x011c A[Catch: all -> 0x0113, TryCatch #14 {all -> 0x0113, blocks: (B:84:0x010f, B:88:0x0117, B:90:0x011c, B:92:0x0121, B:94:0x0126), top: B:126:0x010f, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0121 A[Catch: all -> 0x0113, TryCatch #14 {all -> 0x0113, blocks: (B:84:0x010f, B:88:0x0117, B:90:0x011c, B:92:0x0121, B:94:0x0126), top: B:126:0x010f, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0126 A[Catch: all -> 0x0113, TRY_LEAVE, TryCatch #14 {all -> 0x0113, blocks: (B:84:0x010f, B:88:0x0117, B:90:0x011c, B:92:0x0121, B:94:0x0126), top: B:126:0x010f, outer: #13 }] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.net.URL] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.net.HttpURLConnection, java.net.URLConnection] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.mobile.auth.v.c] */
    /* JADX WARN: Type inference failed for: r6v10, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v26, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r6v28 */
    /* JADX WARN: Type inference failed for: r6v29 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v30 */
    /* JADX WARN: Type inference failed for: r6v31 */
    /* JADX WARN: Type inference failed for: r6v32 */
    /* JADX WARN: Type inference failed for: r6v33 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v0, types: [int] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v23, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v24 */
    /* JADX WARN: Type inference failed for: r7v25 */
    /* JADX WARN: Type inference failed for: r7v26 */
    /* JADX WARN: Type inference failed for: r7v27 */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v29 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r8v0, types: [int] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v16, types: [java.io.InputStreamReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(com.mobile.auth.v.c r6, int r7, int r8, int r9) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.v.a.a(com.mobile.auth.v.c, int, int, int):java.lang.String");
    }

    public static String a(String str) {
        try {
            try {
                return new URL(str).getHost();
            } catch (MalformedURLException e2) {
                e2.printStackTrace();
                return null;
            }
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0172 A[Catch: all -> 0x016e, TryCatch #16 {all -> 0x016e, blocks: (B:105:0x016a, B:109:0x0172, B:111:0x0177, B:113:0x017c, B:115:0x0181), top: B:134:0x016a, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0177 A[Catch: all -> 0x016e, TryCatch #16 {all -> 0x016e, blocks: (B:105:0x016a, B:109:0x0172, B:111:0x0177, B:113:0x017c, B:115:0x0181), top: B:134:0x016a, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x017c A[Catch: all -> 0x016e, TryCatch #16 {all -> 0x016e, blocks: (B:105:0x016a, B:109:0x0172, B:111:0x0177, B:113:0x017c, B:115:0x0181), top: B:134:0x016a, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0181 A[Catch: all -> 0x016e, TRY_LEAVE, TryCatch #16 {all -> 0x016e, blocks: (B:105:0x016a, B:109:0x0172, B:111:0x0177, B:113:0x017c, B:115:0x0181), top: B:134:0x016a, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x011d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0148 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x016a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0125 A[Catch: all -> 0x0121, TryCatch #1 {all -> 0x0121, blocks: (B:70:0x011d, B:74:0x0125, B:76:0x012a, B:78:0x012f, B:80:0x0134), top: B:125:0x011d, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x012a A[Catch: all -> 0x0121, TryCatch #1 {all -> 0x0121, blocks: (B:70:0x011d, B:74:0x0125, B:76:0x012a, B:78:0x012f, B:80:0x0134), top: B:125:0x011d, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x012f A[Catch: all -> 0x0121, TryCatch #1 {all -> 0x0121, blocks: (B:70:0x011d, B:74:0x0125, B:76:0x012a, B:78:0x012f, B:80:0x0134), top: B:125:0x011d, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0134 A[Catch: all -> 0x0121, TRY_LEAVE, TryCatch #1 {all -> 0x0121, blocks: (B:70:0x011d, B:74:0x0125, B:76:0x012a, B:78:0x012f, B:80:0x0134), top: B:125:0x011d, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0150 A[Catch: all -> 0x014c, TryCatch #13 {all -> 0x014c, blocks: (B:89:0x0148, B:93:0x0150, B:95:0x0155, B:97:0x015a, B:99:0x015f), top: B:130:0x0148, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0155 A[Catch: all -> 0x014c, TryCatch #13 {all -> 0x014c, blocks: (B:89:0x0148, B:93:0x0150, B:95:0x0155, B:97:0x015a, B:99:0x015f), top: B:130:0x0148, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x015a A[Catch: all -> 0x014c, TryCatch #13 {all -> 0x014c, blocks: (B:89:0x0148, B:93:0x0150, B:95:0x0155, B:97:0x015a, B:99:0x015f), top: B:130:0x0148, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x015f A[Catch: all -> 0x014c, TRY_LEAVE, TryCatch #13 {all -> 0x014c, blocks: (B:89:0x0148, B:93:0x0150, B:95:0x0155, B:97:0x015a, B:99:0x015f), top: B:130:0x0148, outer: #9 }] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v24 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v36 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r7v0, types: [int] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r8v0, types: [int] */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v22 */
    /* JADX WARN: Type inference failed for: r8v23 */
    /* JADX WARN: Type inference failed for: r8v24 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v9, types: [java.io.OutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String r5, java.lang.String r6, int r7, int r8) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 403
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.v.a.a(java.lang.String, java.lang.String, int, int):java.lang.String");
    }

    private static HostnameVerifier b(String str) {
        try {
            String strA = a(str);
            if (f10563a != null && !TextUtils.isEmpty(strA) && f10563a.containsKey(strA)) {
                return f10563a.get(strA);
            }
            HostnameVerifier hostnameVerifier = new HostnameVerifier() { // from class: com.mobile.auth.v.a.1
                @Override // javax.net.ssl.HostnameVerifier
                public boolean verify(String str2, SSLSession sSLSession) {
                    try {
                        return HttpsURLConnection.getDefaultHostnameVerifier().verify(str2, sSLSession);
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
            if (f10563a == null) {
                f10563a = new ConcurrentHashMap<>();
            }
            f10563a.put(strA, hostnameVerifier);
            return hostnameVerifier;
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
