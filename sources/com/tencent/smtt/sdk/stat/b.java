package com.tencent.smtt.sdk.stat;

import MTT.ThirdAppInfoNew;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.beizi.ad.internal.view.InterstitialAdViewImpl;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsCoreLoadStat;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsLogReport;
import com.tencent.smtt.sdk.TbsPVConfig;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.h;
import com.tencent.smtt.utils.l;
import com.tencent.smtt.utils.o;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static byte[] f21286a;

    /* renamed from: b, reason: collision with root package name */
    private static ThirdAppInfoNew f21287b;

    /* renamed from: c, reason: collision with root package name */
    private static Map<String, String> f21288c;

    static {
        try {
            f21286a = "65dRa93L".getBytes("utf-8");
        } catch (UnsupportedEncodingException unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001e A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String a(android.content.Context r2) {
        /*
            r2 = 0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L1b
            r0.<init>()     // Catch: java.lang.Throwable -> L1b
            java.lang.String r2 = "cpuabi"
            boolean r1 = com.tencent.smtt.utils.b.c()     // Catch: java.lang.Throwable -> L1a
            if (r1 == 0) goto L11
            java.lang.String r1 = "64"
            goto L13
        L11:
            java.lang.String r1 = "32"
        L13:
            r0.put(r2, r1)     // Catch: java.lang.Throwable -> L1a
            com.tencent.smtt.utils.s.b()     // Catch: java.lang.Throwable -> L1a
            goto L1c
        L1a:
            r2 = r0
        L1b:
            r0 = r2
        L1c:
            if (r0 != 0) goto L21
            java.lang.String r2 = ""
            goto L25
        L21:
            java.lang.String r2 = r0.toString()
        L25:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.stat.b.a(android.content.Context):java.lang.String");
    }

    public static void a(ThirdAppInfoNew thirdAppInfoNew, Context context) {
        a(thirdAppInfoNew, context, null, null);
    }

    public static void a(final ThirdAppInfoNew thirdAppInfoNew, final Context context, final String str, final String str2) {
        new Thread("HttpUtils") { // from class: com.tencent.smtt.sdk.stat.b.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws ProtocolException, JSONException {
                String strSubstring;
                String strSubstring2;
                String str3;
                String string;
                com.tencent.smtt.utils.b.b(context, thirdAppInfoNew.sGuid);
                thirdAppInfoNew.sCpu = com.tencent.smtt.utils.b.a();
                JSONObject jSONObjectC = null;
                if (b.f21286a == null) {
                    try {
                        b.f21286a = "65dRa93L".getBytes("utf-8");
                    } catch (UnsupportedEncodingException unused) {
                        b.f21286a = null;
                        TbsLog.e("sdkreport", "Post failed -- get POST_DATA_KEY failed!");
                    }
                }
                if (b.f21286a == null) {
                    TbsLog.e("sdkreport", "Post failed -- POST_DATA_KEY is null!");
                    return;
                }
                String string2 = TbsDownloadConfig.getInstance(context).mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_DESkEY_TOKEN, "");
                if (TextUtils.isEmpty(string2)) {
                    strSubstring = "";
                    strSubstring2 = strSubstring;
                } else {
                    strSubstring2 = string2.substring(0, string2.indexOf("&"));
                    strSubstring = string2.substring(string2.indexOf("&") + 1, string2.length());
                }
                boolean z2 = TextUtils.isEmpty(strSubstring2) || strSubstring2.length() != 96 || TextUtils.isEmpty(strSubstring) || strSubstring.length() != 24;
                try {
                    o oVarA = o.a();
                    if (z2) {
                        str3 = oVarA.b() + h.a().b();
                    } else {
                        str3 = oVarA.i() + strSubstring2;
                    }
                    TbsLog.i("sdkreport", "Post post_url is " + str3);
                    TbsLog.i("sdkreport", "Post getDESString is " + h.c());
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str3).openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setConnectTimeout(20000);
                    httpURLConnection.setRequestProperty("Connection", "close");
                    try {
                        jSONObjectC = b.c(thirdAppInfoNew, context);
                    } catch (Exception e2) {
                        TbsLog.i(e2);
                    }
                    if (jSONObjectC == null) {
                        TbsLog.e("sdkreport", "post -- jsonData is null!");
                        return;
                    }
                    try {
                        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                            try {
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put(str, str2);
                                string = jSONObject.toString();
                            } catch (Throwable unused2) {
                                string = "";
                            }
                            jSONObjectC.put("EXT_INFO", string);
                            jSONObjectC.put("PV", 0);
                        }
                    } catch (Exception e3) {
                        TbsLog.i(e3);
                    }
                    try {
                        TbsLog.i("sdkreport", "Post jsonData.toString() is " + jSONObjectC.toString());
                        byte[] bytes = jSONObjectC.toString().getBytes("utf-8");
                        byte[] bArrA = z2 ? h.a().a(bytes) : h.a(bytes, strSubstring);
                        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bArrA.length));
                        try {
                            OutputStream outputStream = httpURLConnection.getOutputStream();
                            outputStream.write(bArrA);
                            outputStream.flush();
                            if (httpURLConnection.getResponseCode() == 200) {
                                TbsLog.i("SDKPVReport", "Post successful!");
                                TbsLog.i("SDKPVReport", "SIGNATURE is " + jSONObjectC.getString("SIGNATURE"));
                                b.b(context, b.b(httpURLConnection, strSubstring, z2));
                                return;
                            }
                            TbsLog.e("SDKPVReport", "Post failed -- not 200 code is " + httpURLConnection.getResponseCode());
                            TbsLogReport.TbsLogInfo tbsLogInfo = TbsLogReport.getInstance(context).tbsLogInfo();
                            tbsLogInfo.setErrorCode(126);
                            tbsLogInfo.setFailDetail("" + httpURLConnection.getResponseCode());
                            TbsLogReport.getInstance(context).eventReport(TbsLogReport.EventType.TYPE_PV_UPLOAD_STAT, tbsLogInfo);
                        } catch (Throwable th) {
                            TbsLog.e("SDKPVReport", "Post failed -- exceptions:" + th.getMessage());
                            TbsLogReport.TbsLogInfo tbsLogInfo2 = TbsLogReport.getInstance(context).tbsLogInfo();
                            tbsLogInfo2.setErrorCode(126);
                            tbsLogInfo2.setFailDetail(th);
                            TbsLogReport.getInstance(context).eventReport(TbsLogReport.EventType.TYPE_PV_UPLOAD_STAT, tbsLogInfo2);
                        }
                    } catch (Throwable unused3) {
                    }
                } catch (IOException e4) {
                    TbsLog.e("sdkreport", "Post failed -- IOException:" + e4);
                } catch (AssertionError e5) {
                    TbsLog.e("sdkreport", "Post failed -- AssertionError:" + e5);
                } catch (Exception e6) {
                    TbsLog.e("sdkreport", "Post failed -- Exception:" + e6);
                } catch (NoClassDefFoundError e7) {
                    TbsLog.e("sdkreport", "Post failed -- NoClassDefFoundError:" + e7);
                }
            }
        }.start();
    }

    private static void a(Context context, int i2) {
        TbsLog.i("HttpUtils", "updatePVConfig command is " + i2);
        if (i2 == 1) {
            TbsPVConfig.getInstance(context).clear();
        }
        if (i2 == 2) {
            TbsPVConfig.getInstance(context).update(f21288c);
        }
    }

    public static void a(Context context, String str, String str2) {
        if (f21287b == null) {
            ThirdAppInfoNew thirdAppInfoNew = new ThirdAppInfoNew();
            thirdAppInfoNew.sAppName = context.getApplicationContext().getApplicationInfo().packageName;
            o.a(context);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            thirdAppInfoNew.sTime = simpleDateFormat.format(Calendar.getInstance().getTime());
            thirdAppInfoNew.sVersionCode = com.tencent.smtt.utils.b.e(context);
            String strA = com.tencent.smtt.utils.b.a(context, TbsDownloader.TBS_METADATA);
            if (!TextUtils.isEmpty(strA)) {
                thirdAppInfoNew.sMetaData = strA;
            }
            thirdAppInfoNew.sGuid = "";
            thirdAppInfoNew.sQua2 = l.a(context, "" + QbSdk.getTbsVersion(context));
            thirdAppInfoNew.sLc = "";
            String strJ = com.tencent.smtt.utils.b.j(context);
            String strH = com.tencent.smtt.utils.b.h(context);
            String strI = com.tencent.smtt.utils.b.i(context);
            String strK = com.tencent.smtt.utils.b.k(context);
            if (strH != null && !"".equals(strH)) {
                thirdAppInfoNew.sImei = strH;
            }
            if (strI != null && !"".equals(strI)) {
                thirdAppInfoNew.sImsi = strI;
            }
            if (!TextUtils.isEmpty(strK)) {
                thirdAppInfoNew.sAndroidID = strK;
            }
            if (strJ != null && !"".equals(strJ)) {
                thirdAppInfoNew.sMac = strJ;
            }
            thirdAppInfoNew.iPv = 0L;
            if (QbSdk.getTbsVersion(context) > 0) {
                thirdAppInfoNew.iCoreType = 1;
            } else {
                thirdAppInfoNew.iCoreType = 0;
                thirdAppInfoNew.localCoreVersion = QbSdk.getTbsVersion(context);
            }
            thirdAppInfoNew.sAppVersionName = context.getApplicationInfo().packageName;
            thirdAppInfoNew.sAppSignature = b(context);
            f21287b = thirdAppInfoNew;
        }
        a(f21287b, context, str, str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00b9 A[Catch: all -> 0x0172, TryCatch #1 {all -> 0x0172, blocks: (B:24:0x0074, B:26:0x00b9, B:27:0x00bb, B:29:0x00bf, B:31:0x00ca, B:33:0x00fe, B:35:0x0104, B:37:0x0108, B:39:0x010e, B:40:0x0110, B:42:0x0116, B:44:0x011a, B:46:0x0120, B:47:0x0122, B:50:0x012f, B:52:0x0135, B:55:0x013d, B:63:0x014d, B:65:0x0157, B:66:0x015f, B:53:0x0139, B:56:0x0140, B:59:0x0146, B:30:0x00c4), top: B:73:0x0074 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00bf A[Catch: all -> 0x0172, TryCatch #1 {all -> 0x0172, blocks: (B:24:0x0074, B:26:0x00b9, B:27:0x00bb, B:29:0x00bf, B:31:0x00ca, B:33:0x00fe, B:35:0x0104, B:37:0x0108, B:39:0x010e, B:40:0x0110, B:42:0x0116, B:44:0x011a, B:46:0x0120, B:47:0x0122, B:50:0x012f, B:52:0x0135, B:55:0x013d, B:63:0x014d, B:65:0x0157, B:66:0x015f, B:53:0x0139, B:56:0x0140, B:59:0x0146, B:30:0x00c4), top: B:73:0x0074 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c4 A[Catch: all -> 0x0172, TryCatch #1 {all -> 0x0172, blocks: (B:24:0x0074, B:26:0x00b9, B:27:0x00bb, B:29:0x00bf, B:31:0x00ca, B:33:0x00fe, B:35:0x0104, B:37:0x0108, B:39:0x010e, B:40:0x0110, B:42:0x0116, B:44:0x011a, B:46:0x0120, B:47:0x0122, B:50:0x012f, B:52:0x0135, B:55:0x013d, B:63:0x014d, B:65:0x0157, B:66:0x015f, B:53:0x0139, B:56:0x0140, B:59:0x0146, B:30:0x00c4), top: B:73:0x0074 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0116 A[Catch: all -> 0x0172, TryCatch #1 {all -> 0x0172, blocks: (B:24:0x0074, B:26:0x00b9, B:27:0x00bb, B:29:0x00bf, B:31:0x00ca, B:33:0x00fe, B:35:0x0104, B:37:0x0108, B:39:0x010e, B:40:0x0110, B:42:0x0116, B:44:0x011a, B:46:0x0120, B:47:0x0122, B:50:0x012f, B:52:0x0135, B:55:0x013d, B:63:0x014d, B:65:0x0157, B:66:0x015f, B:53:0x0139, B:56:0x0140, B:59:0x0146, B:30:0x00c4), top: B:73:0x0074 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0157 A[Catch: all -> 0x0172, TryCatch #1 {all -> 0x0172, blocks: (B:24:0x0074, B:26:0x00b9, B:27:0x00bb, B:29:0x00bf, B:31:0x00ca, B:33:0x00fe, B:35:0x0104, B:37:0x0108, B:39:0x010e, B:40:0x0110, B:42:0x0116, B:44:0x011a, B:46:0x0120, B:47:0x0122, B:50:0x012f, B:52:0x0135, B:55:0x013d, B:63:0x014d, B:65:0x0157, B:66:0x015f, B:53:0x0139, B:56:0x0140, B:59:0x0146, B:30:0x00c4), top: B:73:0x0074 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.content.Context r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, int r11, boolean r12, long r13, boolean r15) {
        /*
            Method dump skipped, instructions count: 375
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.stat.b.a(android.content.Context, java.lang.String, java.lang.String, java.lang.String, int, boolean, long, boolean):void");
    }

    private static boolean a(Map<String, String> map, Map<String, String> map2) {
        if (map.size() != map2.size()) {
            return false;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!(entry.getValue() == null ? "" : entry.getValue()).equals(map2.get(entry.getKey()) != null ? map2.get(entry.getKey()) : "")) {
                return false;
            }
        }
        return true;
    }

    private static String b(Context context) throws NoSuchAlgorithmException {
        try {
            byte[] byteArray = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray();
            if (byteArray != null) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                messageDigest.update(byteArray);
                byte[] bArrDigest = messageDigest.digest();
                if (bArrDigest != null) {
                    StringBuilder sb = new StringBuilder("");
                    if (bArrDigest.length <= 0) {
                        return null;
                    }
                    for (int i2 = 0; i2 < bArrDigest.length; i2++) {
                        String upperCase = Integer.toHexString(bArrDigest[i2] & 255).toUpperCase();
                        if (i2 > 0) {
                            sb.append(":");
                        }
                        if (upperCase.length() < 2) {
                            sb.append(0);
                        }
                        sb.append(upperCase);
                    }
                    return sb.toString();
                }
            }
        } catch (Exception e2) {
            TbsLog.i(e2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(16:(2:72|3)|(14:(1:12)|80|14|15|84|16|(2:17|(1:19)(1:85))|(1:21)(2:22|23)|78|24|68|28|54|55)(1:7)|8|80|14|15|84|16|(3:17|(0)(0)|19)|(0)(0)|78|24|68|28|54|55) */
    /* JADX WARN: Can't wrap try/catch for region: R(17:72|3|(14:(1:12)|80|14|15|84|16|(2:17|(1:19)(1:85))|(1:21)(2:22|23)|78|24|68|28|54|55)(1:7)|8|80|14|15|84|16|(3:17|(0)(0)|19)|(0)(0)|78|24|68|28|54|55) */
    /* JADX WARN: Can't wrap try/catch for region: R(20:0|2|72|3|(14:(1:12)|80|14|15|84|16|(2:17|(1:19)(1:85))|(1:21)(2:22|23)|78|24|68|28|54|55)(1:7)|8|80|14|15|84|16|(3:17|(0)(0)|19)|(0)(0)|78|24|68|28|54|55|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0081, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0082, code lost:
    
        com.tencent.smtt.utils.TbsLog.i(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0089, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x008a, code lost:
    
        com.tencent.smtt.utils.TbsLog.i(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008e, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008f, code lost:
    
        r1 = r6;
        r6 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0092, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0093, code lost:
    
        r1 = r6;
        r6 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0096, code lost:
    
        r6 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x009d, code lost:
    
        com.tencent.smtt.utils.TbsLog.i(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a0, code lost:
    
        if (r1 != null) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00a2, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a6, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a7, code lost:
    
        com.tencent.smtt.utils.TbsLog.i(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00aa, code lost:
    
        if (r2 != null) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00ac, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00b0, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00b1, code lost:
    
        com.tencent.smtt.utils.TbsLog.i(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00b4, code lost:
    
        r7 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00d4, code lost:
    
        if (r1 != 0) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00d6, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00da, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00db, code lost:
    
        com.tencent.smtt.utils.TbsLog.i(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00de, code lost:
    
        if (r2 != null) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00e0, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00e4, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00e5, code lost:
    
        com.tencent.smtt.utils.TbsLog.i(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00e8, code lost:
    
        throw r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:?, code lost:
    
        throw r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:?, code lost:
    
        throw r6;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0042 A[Catch: all -> 0x008e, Exception -> 0x0092, LOOP:0: B:17:0x003b->B:19:0x0042, LOOP_END, TryCatch #11 {Exception -> 0x0092, all -> 0x008e, blocks: (B:16:0x0039, B:17:0x003b, B:19:0x0042, B:21:0x005d, B:22:0x006f), top: B:84:0x0039 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005d A[Catch: all -> 0x008e, Exception -> 0x0092, TryCatch #11 {Exception -> 0x0092, all -> 0x008e, blocks: (B:16:0x0039, B:17:0x003b, B:19:0x0042, B:21:0x005d, B:22:0x006f), top: B:84:0x0039 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006f A[Catch: all -> 0x008e, Exception -> 0x0092, TRY_LEAVE, TryCatch #11 {Exception -> 0x0092, all -> 0x008e, blocks: (B:16:0x0039, B:17:0x003b, B:19:0x0042, B:21:0x005d, B:22:0x006f), top: B:84:0x0039 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x005b A[EDGE_INSN: B:85:0x005b->B:20:0x005b BREAK  A[LOOP:0: B:17:0x003b->B:19:0x0042], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String b(java.net.HttpURLConnection r6, java.lang.String r7, boolean r8) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.stat.b.b(java.net.HttpURLConnection, java.lang.String, boolean):java.lang.String");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                a(context, 1);
                return;
            }
            f21288c = new HashMap();
            for (String str2 : str.split("\\|")) {
                try {
                    String[] strArrSplit = str2.split("=");
                    if (strArrSplit.length == 2) {
                        b(context, strArrSplit[0], strArrSplit[1]);
                    }
                } catch (Exception e2) {
                    TbsLog.i(e2);
                }
            }
            int iC = c(context);
            TbsLog.i("HttpUtils", "readResponse, after processSwitchKeyValue mMapFromResponse is " + f21288c.toString() + " commandForUpdatePVC is " + iC);
            a(context, iC);
        } catch (Exception e3) {
            TbsLog.i(e3);
        }
    }

    private static void b(Context context, String str, String str2) {
        TbsLog.i("HttpUtils", "PV Config Receive. Key: " + str + ", value: " + str2);
        if ("reset".equals(str) && k.a.f27523u.equals(str2)) {
            QbSdk.reset(context);
        } else if ("resetCfg24".equals(str) && k.a.f27523u.equals(str2)) {
            d(context);
        } else {
            f21288c.put(str, str2);
        }
    }

    private static int c(Context context) {
        Map<String, String> map = f21288c;
        if (map == null || map.size() == 0) {
            return 1;
        }
        Map<String, String> pVCLocal = TbsPVConfig.getInstance(context).getPVCLocal();
        TbsLog.i("HttpUtils", "getCommandForUpdatePVC, mMapPVCLocal is " + pVCLocal.toString());
        return (pVCLocal.size() != 0 && a(f21288c, pVCLocal)) ? 0 : 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static JSONObject c(ThirdAppInfoNew thirdAppInfoNew, Context context) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("APPNAME", thirdAppInfoNew.sAppName);
            jSONObject.put(InterstitialAdViewImpl.INTENT_KEY_TIME, thirdAppInfoNew.sTime);
            jSONObject.put("QUA2", thirdAppInfoNew.sQua2);
            jSONObject.put("LC", thirdAppInfoNew.sLc);
            jSONObject.put("GUID", thirdAppInfoNew.sGuid);
            jSONObject.put("IMEI", thirdAppInfoNew.sImei);
            jSONObject.put("IMSI", thirdAppInfoNew.sImsi);
            jSONObject.put("MAC", thirdAppInfoNew.sMac);
            jSONObject.put("PV", thirdAppInfoNew.iPv);
            jSONObject.put("CORETYPE", thirdAppInfoNew.iCoreType);
            jSONObject.put("APPVN", thirdAppInfoNew.sAppVersionName);
            jSONObject.put("APPMETADATA", thirdAppInfoNew.sMetaData);
            jSONObject.put("VERSION_CODE", thirdAppInfoNew.sVersionCode);
            jSONObject.put("CPU", thirdAppInfoNew.sCpu);
            String str = thirdAppInfoNew.sAppSignature;
            if (str == null) {
                str = "0";
            }
            jSONObject.put("SIGNATURE", str);
            String strA = a(context);
            TbsLog.i("sdkreport", "addInfo is " + strA);
            if (!TextUtils.isEmpty(strA)) {
                jSONObject.put("EXT_INFO", strA);
            }
            jSONObject.put("PROTOCOL_VERSION", 3);
            jSONObject.put("ANDROID_ID", thirdAppInfoNew.sAndroidID);
            jSONObject.put("HOST_COREVERSION", 0);
            jSONObject.put("DECOUPLE_COREVERSION", 0);
            jSONObject.put("WIFICONNECTEDTIME", thirdAppInfoNew.sWifiConnectedTime);
            jSONObject.put("CORE_EXIST", thirdAppInfoNew.localCoreVersion);
            int loadErrorCode = TbsCoreLoadStat.getLoadErrorCode();
            if (thirdAppInfoNew.localCoreVersion <= 0) {
                jSONObject.put("TBS_ERROR_CODE", TbsDownloadConfig.getInstance(context).getDownloadInterruptCode());
            } else {
                jSONObject.put("TBS_ERROR_CODE", loadErrorCode);
            }
            if (loadErrorCode == -1) {
                TbsLog.e("sdkreport", "ATTENTION: Load errorCode missed!");
            }
            try {
                if (QbSdk.getTID() != null && (thirdAppInfoNew.sAppName.equals("com.tencent.mobileqq") || thirdAppInfoNew.sAppName.equals("com.tencent.mm"))) {
                    String tid = QbSdk.getTID();
                    jSONObject.put("TID", tid);
                    jSONObject.put("TIDTYPE", 0);
                }
            } catch (Exception unused) {
            }
            return jSONObject;
        } catch (Exception unused2) {
            TbsLog.e("sdkreport", "getPostData exception!");
            return null;
        }
    }

    private static void d(Context context) {
        SharedPreferences.Editor editorEdit = TbsDownloadConfig.getInstance(context).mPreferences.edit();
        editorEdit.putLong(TbsDownloadConfig.TbsConfigKey.KEY_LAST_CHECK, 0L);
        editorEdit.apply();
    }
}
