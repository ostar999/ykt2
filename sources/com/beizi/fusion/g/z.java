package com.beizi.fusion.g;

import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.text.TextUtils;
import cn.hutool.core.math.Money;
import com.beizi.fusion.d.a.d;
import com.beizi.fusion.model.DevInfo;
import com.beizi.fusion.model.EnvInfo;
import com.beizi.fusion.model.JsonResolver;
import com.beizi.fusion.model.RequestInfo;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class z {

    /* renamed from: a, reason: collision with root package name */
    private static String f5275a = "HttpUtil";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ae A[Catch: IOException -> 0x00c9, TRY_ENTER, TryCatch #0 {IOException -> 0x00c9, blocks: (B:44:0x00ae, B:46:0x00b3, B:48:0x00b8, B:55:0x00c5, B:59:0x00cd, B:61:0x00d2, B:33:0x009b), top: B:77:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b3 A[Catch: IOException -> 0x00c9, TryCatch #0 {IOException -> 0x00c9, blocks: (B:44:0x00ae, B:46:0x00b3, B:48:0x00b8, B:55:0x00c5, B:59:0x00cd, B:61:0x00d2, B:33:0x009b), top: B:77:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b8 A[Catch: IOException -> 0x00c9, TRY_LEAVE, TryCatch #0 {IOException -> 0x00c9, blocks: (B:44:0x00ae, B:46:0x00b3, B:48:0x00b8, B:55:0x00c5, B:59:0x00cd, B:61:0x00d2, B:33:0x009b), top: B:77:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c5 A[Catch: IOException -> 0x00c9, TRY_ENTER, TryCatch #0 {IOException -> 0x00c9, blocks: (B:44:0x00ae, B:46:0x00b3, B:48:0x00b8, B:55:0x00c5, B:59:0x00cd, B:61:0x00d2, B:33:0x009b), top: B:77:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00cd A[Catch: IOException -> 0x00c9, TryCatch #0 {IOException -> 0x00c9, blocks: (B:44:0x00ae, B:46:0x00b3, B:48:0x00b8, B:55:0x00c5, B:59:0x00cd, B:61:0x00d2, B:33:0x009b), top: B:77:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00d2 A[Catch: IOException -> 0x00c9, TRY_LEAVE, TryCatch #0 {IOException -> 0x00c9, blocks: (B:44:0x00ae, B:46:0x00b3, B:48:0x00b8, B:55:0x00c5, B:59:0x00cd, B:61:0x00d2, B:33:0x009b), top: B:77:0x0006 }] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.StringBuffer] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v10, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String r6, java.lang.String r7) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.g.z.a(java.lang.String, java.lang.String):java.lang.String");
    }

    private static byte[] b(Context context, String str) {
        try {
            RequestInfo requestInfo = RequestInfo.getInstance(context);
            if (!requestInfo.isInit) {
                requestInfo.init();
            }
            DevInfo devInfo = requestInfo.getDevInfo();
            EnvInfo envInfo = requestInfo.getEnvInfo();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("version", "4.90.2.36");
            jSONObject.put("srcType", 1);
            jSONObject.put("timeStamp", System.currentTimeMillis());
            jSONObject.put("appid", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("sdkUID", devInfo.getSdkUID());
            jSONObject2.put("sdkUIDOrig", devInfo.getSdkUIDOrig());
            jSONObject2.put("os", devInfo.getOs());
            jSONObject2.put("platform", 2);
            jSONObject2.put("devType", Integer.valueOf(devInfo.getDevType()));
            jSONObject2.put(Constants.PHONE_BRAND, devInfo.getBrand());
            jSONObject2.put("model", devInfo.getModel());
            jSONObject2.put("resolution", devInfo.getResolution());
            jSONObject2.put("screenSize", devInfo.getScreenSize());
            jSONObject2.put("language", devInfo.getLanguage());
            jSONObject2.put("density", devInfo.getDensity());
            jSONObject2.put("root", devInfo.getRoot());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(com.alipay.sdk.app.statistic.c.f3111a, Integer.valueOf(envInfo.getNet()));
            jSONObject3.put("isp", Integer.valueOf(envInfo.getIsp()));
            jSONObject.putOpt("devInfo", jSONObject2);
            jSONObject.putOpt("envInfo", jSONObject3);
            String strA = d.a(aa.a(), jSONObject.toString());
            if (TextUtils.isEmpty(strA)) {
                return null;
            }
            return strA.getBytes();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x00fc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x00d6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ed A[Catch: IOException -> 0x0117, TRY_ENTER, TRY_LEAVE, TryCatch #2 {IOException -> 0x0117, blocks: (B:72:0x00ed, B:89:0x0113, B:47:0x00b4), top: B:114:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0113 A[Catch: IOException -> 0x0117, TRY_ENTER, TRY_LEAVE, TryCatch #2 {IOException -> 0x0117, blocks: (B:72:0x00ed, B:89:0x0113, B:47:0x00b4), top: B:114:0x0001 }] */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v38 */
    /* JADX WARN: Type inference failed for: r1v39 */
    /* JADX WARN: Type inference failed for: r1v40 */
    /* JADX WARN: Type inference failed for: r1v41 */
    /* JADX WARN: Type inference failed for: r1v42 */
    /* JADX WARN: Type inference failed for: r1v44 */
    /* JADX WARN: Type inference failed for: r1v45 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v24 */
    /* JADX WARN: Type inference failed for: r7v25 */
    /* JADX WARN: Type inference failed for: r7v26 */
    /* JADX WARN: Type inference failed for: r7v27 */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v29 */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v34 */
    /* JADX WARN: Type inference failed for: r7v35 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String r7, byte[] r8) {
        /*
            Method dump skipped, instructions count: 319
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.g.z.a(java.lang.String, byte[]):java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01ac A[Catch: IOException -> 0x01b0, TRY_ENTER, TRY_LEAVE, TryCatch #6 {IOException -> 0x01b0, blocks: (B:84:0x0186, B:100:0x01ac, B:58:0x0147), top: B:127:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0195 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x016f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0186 A[Catch: IOException -> 0x01b0, TRY_ENTER, TRY_LEAVE, TryCatch #6 {IOException -> 0x01b0, blocks: (B:84:0x0186, B:100:0x01ac, B:58:0x0147), top: B:127:0x0003 }] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v27 */
    /* JADX WARN: Type inference failed for: r0v28 */
    /* JADX WARN: Type inference failed for: r0v29 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r11v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r11v36 */
    /* JADX WARN: Type inference failed for: r11v37 */
    /* JADX WARN: Type inference failed for: r11v38 */
    /* JADX WARN: Type inference failed for: r11v39 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v40 */
    /* JADX WARN: Type inference failed for: r11v42 */
    /* JADX WARN: Type inference failed for: r11v43 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r11v9, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r12v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r12v1, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r12v15 */
    /* JADX WARN: Type inference failed for: r12v18 */
    /* JADX WARN: Type inference failed for: r12v19 */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r12v21 */
    /* JADX WARN: Type inference failed for: r12v22 */
    /* JADX WARN: Type inference failed for: r12v23 */
    /* JADX WARN: Type inference failed for: r12v24 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v4, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r12v7, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.net.HttpURLConnection, java.net.URLConnection] */
    /* JADX WARN: Type inference failed for: r3v28 */
    /* JADX WARN: Type inference failed for: r3v29 */
    /* JADX WARN: Type inference failed for: r3v30 */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v11, types: [java.lang.StringBuffer] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r10, java.lang.String r11, java.lang.String r12) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.g.z.a(android.content.Context, java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0196 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0171 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x018c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01aa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0167 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x015d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x017b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:144:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String r11, java.io.File r12) {
        /*
            Method dump skipped, instructions count: 435
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.g.z.a(java.lang.String, java.io.File):java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x00e3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0120 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00f6 A[Catch: IOException -> 0x0137, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x0137, blocks: (B:69:0x00f6, B:92:0x0133, B:51:0x00c1), top: B:99:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0133 A[Catch: IOException -> 0x0137, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x0137, blocks: (B:69:0x00f6, B:92:0x0133, B:51:0x00c1), top: B:99:0x0018 }] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v17, types: [java.io.InputStreamReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v7, types: [java.io.InputStreamReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r5, java.lang.String r6, java.lang.String r7, java.lang.Boolean r8) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.g.z.a(android.content.Context, java.lang.String, java.lang.String, java.lang.Boolean):java.lang.String");
    }

    private static byte[] a(Context context, String str) {
        try {
            RequestInfo requestInfo = RequestInfo.getInstance(context);
            if (!requestInfo.isInit) {
                requestInfo.init();
            }
            DevInfo devInfo = requestInfo.getDevInfo();
            EnvInfo envInfo = requestInfo.getEnvInfo();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appId", str);
            jSONObject.put("packageName", as.c(context));
            jSONObject.put("installTime", String.valueOf(as.a(context)));
            jSONObject.put("updateTime", String.valueOf(as.b(context)));
            jSONObject.put("uploadTime", String.valueOf(System.currentTimeMillis()));
            jSONObject.put("appVersion", as.d(context));
            jSONObject.put(com.heytap.mcssdk.constant.b.C, "4.90.2.36");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("sdkUID", devInfo.getSdkUID());
            jSONObject2.put("sdkUIDOrig", devInfo.getSdkUIDOrig());
            if (!TextUtils.isEmpty(devInfo.getOaid())) {
                jSONObject2.put("oaid", devInfo.getOaid());
            } else if (!TextUtils.isEmpty(requestInfo.getCustomOaid())) {
                jSONObject2.put("oaid", requestInfo.getCustomOaid());
            }
            jSONObject2.put("gaid", devInfo.getGaid());
            jSONObject2.put("os", devInfo.getOs());
            jSONObject2.put("platform", "2");
            jSONObject2.put("devType", devInfo.getDevType());
            jSONObject2.put(Constants.PHONE_BRAND, devInfo.getBrand());
            jSONObject2.put("model", devInfo.getModel());
            jSONObject2.put("resolution", devInfo.getResolution());
            jSONObject2.put("screenSize", devInfo.getScreenSize());
            jSONObject2.put("language", devInfo.getLanguage());
            jSONObject2.put("density", devInfo.getDensity());
            jSONObject2.put("root", devInfo.getRoot());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(com.alipay.sdk.app.statistic.c.f3111a, envInfo.getNet());
            jSONObject3.put("isp", envInfo.getIsp());
            jSONObject3.put("developerMode", as.h(context));
            jSONObject3.put("isUsb", as.i(context));
            jSONObject3.put("isDebugApk", as.g(context));
            jSONObject3.put("isDebugConnected", Debug.isDebuggerConnected());
            jSONObject3.put("isWifiProxy", as.j(context));
            jSONObject3.put("isVpn", as.b());
            jSONObject3.put("isSimulator", v.a().a(context));
            jSONObject.putOpt("devInfo", jSONObject2);
            jSONObject.putOpt("envInfo", jSONObject3);
            String strA = c.a(jSONObject.toString(), aa.b());
            if (TextUtils.isEmpty(strA)) {
                return null;
            }
            return strA.getBytes();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static String a(Context context, String str, String str2, boolean z2, List<String> list, List<d.k> list2, int i2) {
        try {
            RequestInfo requestInfo = RequestInfo.getInstance(context);
            if (!requestInfo.isInit) {
                requestInfo.init();
            }
            DevInfo devInfo = requestInfo.getDevInfo();
            EnvInfo envInfo = requestInfo.getEnvInfo();
            com.beizi.fusion.d.a.d dVar = new com.beizi.fusion.d.a.d();
            d.i iVar = new d.i();
            iVar.a(str);
            iVar.a(0);
            iVar.b(i2);
            iVar.c(1);
            ArrayList arrayList = new ArrayList();
            arrayList.add(Money.DEFAULT_CURRENCY_CODE);
            iVar.a(arrayList);
            iVar.b(list);
            iVar.d(1);
            ArrayList arrayList2 = new ArrayList();
            d.f fVar = new d.f();
            fVar.a("1");
            fVar.a(1);
            fVar.b(1);
            fVar.c(1);
            d.g gVar = new d.g();
            d.h hVar = new d.h();
            hVar.a(str2);
            hVar.b("AdScope");
            hVar.c("4.90.2.36");
            hVar.a(z2 ? 1 : 0);
            gVar.a(hVar);
            fVar.a(gVar);
            fVar.d(1);
            arrayList2.add(fVar);
            iVar.c(arrayList2);
            d.c cVar = new d.c();
            d.a aVar = new d.a();
            aVar.a(as.c(context));
            aVar.b(as.d(context));
            d.b bVar = new d.b();
            bVar.a(com.beizi.fusion.d.b.a().b());
            aVar.a(bVar);
            cVar.a(aVar);
            d.C0062d c0062d = new d.C0062d();
            c0062d.a(Integer.valueOf(devInfo.getDevType()).intValue());
            c0062d.a(envInfo.getUserAgent());
            c0062d.b(1);
            c0062d.a(envInfo.getUserAgent());
            c0062d.b(devInfo.getBrand());
            c0062d.c(devInfo.getModel());
            c0062d.c(2);
            c0062d.d(Build.VERSION.RELEASE);
            c0062d.a(as.n(context));
            c0062d.b(as.m(context));
            c0062d.e(devInfo.getDensityDpi());
            c0062d.f(devInfo.getDensity());
            c0062d.g(devInfo.getLanguage());
            c0062d.h(envInfo.getIsp());
            c0062d.i(envInfo.getNet());
            d.e eVar = new d.e();
            eVar.a(devInfo.getSdkUID());
            if (!TextUtils.isEmpty(devInfo.getOaid())) {
                eVar.b(devInfo.getOaid());
            } else if (!TextUtils.isEmpty(requestInfo.getCustomOaid())) {
                eVar.b(requestInfo.getCustomOaid());
            }
            c0062d.a(eVar);
            cVar.a(c0062d);
            iVar.a(cVar);
            d.j jVar = new d.j();
            jVar.a(list2);
            iVar.a(jVar);
            dVar.a(iVar);
            String strA = d.a(aa.a(), JsonResolver.toJson(dVar));
            if (TextUtils.isEmpty(strA)) {
                return null;
            }
            return strA;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
