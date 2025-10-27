package com.tencent.smtt.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsLogReport;
import com.tencent.smtt.utils.Apn;
import com.tencent.smtt.utils.FileUtil;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.g;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class TbsDownloader {
    public static final boolean DEBUG_DISABLE_DOWNLOAD = false;
    public static boolean DOWNLOAD_OVERSEA_TBS = false;
    public static final String LOGTAG = "TbsDownload";
    public static final String TBS_METADATA = "com.tencent.mm.BuildInfo.CLIENT_VERSION";

    /* renamed from: a, reason: collision with root package name */
    static boolean f20927a = false;

    /* renamed from: b, reason: collision with root package name */
    private static String f20928b = null;

    /* renamed from: c, reason: collision with root package name */
    private static Context f20929c = null;

    /* renamed from: d, reason: collision with root package name */
    private static Handler f20930d = null;

    /* renamed from: e, reason: collision with root package name */
    private static String f20931e = null;

    /* renamed from: f, reason: collision with root package name */
    private static final Object f20932f = new byte[0];

    /* renamed from: g, reason: collision with root package name */
    private static l f20933g = null;

    /* renamed from: h, reason: collision with root package name */
    private static HandlerThread f20934h = null;

    /* renamed from: i, reason: collision with root package name */
    private static int f20935i = 0;

    /* renamed from: j, reason: collision with root package name */
    private static boolean f20936j = false;

    /* renamed from: k, reason: collision with root package name */
    private static String f20937k = "";

    /* renamed from: l, reason: collision with root package name */
    private static String f20938l = "";

    /* renamed from: m, reason: collision with root package name */
    private static boolean f20939m = false;

    /* renamed from: n, reason: collision with root package name */
    private static boolean f20940n = false;

    /* renamed from: o, reason: collision with root package name */
    private static JSONObject f20941o = null;

    /* renamed from: p, reason: collision with root package name */
    private static JSONObject f20942p = null;

    /* renamed from: q, reason: collision with root package name */
    private static boolean f20943q = false;

    /* renamed from: r, reason: collision with root package name */
    private static int f20944r = 0;

    /* renamed from: s, reason: collision with root package name */
    private static int f20945s = 0;

    /* renamed from: t, reason: collision with root package name */
    private static JSONObject f20946t = null;

    /* renamed from: u, reason: collision with root package name */
    private static long f20947u = -1;

    public interface TbsDownloaderCallback {
        void onNeedDownloadFinish(boolean z2, int i2);
    }

    private static String a(String str) {
        return str == null ? "" : str;
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0362 A[Catch: Exception -> 0x0389, TryCatch #0 {Exception -> 0x0389, blocks: (B:80:0x0217, B:82:0x0251, B:83:0x0257, B:87:0x0283, B:91:0x0290, B:94:0x029e, B:95:0x02a7, B:99:0x02e5, B:103:0x02f0, B:105:0x02f9, B:107:0x0301, B:110:0x030b, B:112:0x0311, B:114:0x0320, B:116:0x032c, B:113:0x0316, B:104:0x02f5, B:117:0x0333, B:119:0x0349, B:121:0x0354, B:123:0x0358, B:126:0x0362, B:129:0x036a, B:130:0x036f, B:132:0x0377, B:135:0x0381), top: B:141:0x0217 }] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x036a A[Catch: Exception -> 0x0389, TryCatch #0 {Exception -> 0x0389, blocks: (B:80:0x0217, B:82:0x0251, B:83:0x0257, B:87:0x0283, B:91:0x0290, B:94:0x029e, B:95:0x02a7, B:99:0x02e5, B:103:0x02f0, B:105:0x02f9, B:107:0x0301, B:110:0x030b, B:112:0x0311, B:114:0x0320, B:116:0x032c, B:113:0x0316, B:104:0x02f5, B:117:0x0333, B:119:0x0349, B:121:0x0354, B:123:0x0358, B:126:0x0362, B:129:0x036a, B:130:0x036f, B:132:0x0377, B:135:0x0381), top: B:141:0x0217 }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0377 A[Catch: Exception -> 0x0389, TryCatch #0 {Exception -> 0x0389, blocks: (B:80:0x0217, B:82:0x0251, B:83:0x0257, B:87:0x0283, B:91:0x0290, B:94:0x029e, B:95:0x02a7, B:99:0x02e5, B:103:0x02f0, B:105:0x02f9, B:107:0x0301, B:110:0x030b, B:112:0x0311, B:114:0x0320, B:116:0x032c, B:113:0x0316, B:104:0x02f5, B:117:0x0333, B:119:0x0349, B:121:0x0354, B:123:0x0358, B:126:0x0362, B:129:0x036a, B:130:0x036f, B:132:0x0377, B:135:0x0381), top: B:141:0x0217 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0381 A[Catch: Exception -> 0x0389, TRY_LEAVE, TryCatch #0 {Exception -> 0x0389, blocks: (B:80:0x0217, B:82:0x0251, B:83:0x0257, B:87:0x0283, B:91:0x0290, B:94:0x029e, B:95:0x02a7, B:99:0x02e5, B:103:0x02f0, B:105:0x02f9, B:107:0x0301, B:110:0x030b, B:112:0x0311, B:114:0x0320, B:116:0x032c, B:113:0x0316, B:104:0x02f5, B:117:0x0333, B:119:0x0349, B:121:0x0354, B:123:0x0358, B:126:0x0362, B:129:0x036a, B:130:0x036f, B:132:0x0377, B:135:0x0381), top: B:141:0x0217 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00bc A[Catch: Exception -> 0x0387, TryCatch #1 {Exception -> 0x0387, blocks: (B:17:0x0075, B:19:0x007d, B:21:0x0086, B:23:0x0098, B:24:0x009e, B:37:0x00e0, B:39:0x00f0, B:41:0x00f8, B:43:0x0105, B:65:0x0193, B:73:0x01a8, B:75:0x01c4, B:77:0x01fd, B:78:0x0203, B:69:0x019e, B:45:0x0113, B:48:0x012a, B:50:0x0136, B:52:0x0146, B:54:0x0156, B:56:0x0162, B:58:0x0180, B:46:0x011e, B:25:0x00a3, B:27:0x00b5, B:28:0x00bc, B:30:0x00ce, B:33:0x00d7), top: B:143:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00f0 A[Catch: Exception -> 0x0387, TryCatch #1 {Exception -> 0x0387, blocks: (B:17:0x0075, B:19:0x007d, B:21:0x0086, B:23:0x0098, B:24:0x009e, B:37:0x00e0, B:39:0x00f0, B:41:0x00f8, B:43:0x0105, B:65:0x0193, B:73:0x01a8, B:75:0x01c4, B:77:0x01fd, B:78:0x0203, B:69:0x019e, B:45:0x0113, B:48:0x012a, B:50:0x0136, B:52:0x0146, B:54:0x0156, B:56:0x0162, B:58:0x0180, B:46:0x011e, B:25:0x00a3, B:27:0x00b5, B:28:0x00bc, B:30:0x00ce, B:33:0x00d7), top: B:143:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0105 A[Catch: Exception -> 0x0387, TryCatch #1 {Exception -> 0x0387, blocks: (B:17:0x0075, B:19:0x007d, B:21:0x0086, B:23:0x0098, B:24:0x009e, B:37:0x00e0, B:39:0x00f0, B:41:0x00f8, B:43:0x0105, B:65:0x0193, B:73:0x01a8, B:75:0x01c4, B:77:0x01fd, B:78:0x0203, B:69:0x019e, B:45:0x0113, B:48:0x012a, B:50:0x0136, B:52:0x0146, B:54:0x0156, B:56:0x0162, B:58:0x0180, B:46:0x011e, B:25:0x00a3, B:27:0x00b5, B:28:0x00bc, B:30:0x00ce, B:33:0x00d7), top: B:143:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01c4 A[Catch: Exception -> 0x0387, TryCatch #1 {Exception -> 0x0387, blocks: (B:17:0x0075, B:19:0x007d, B:21:0x0086, B:23:0x0098, B:24:0x009e, B:37:0x00e0, B:39:0x00f0, B:41:0x00f8, B:43:0x0105, B:65:0x0193, B:73:0x01a8, B:75:0x01c4, B:77:0x01fd, B:78:0x0203, B:69:0x019e, B:45:0x0113, B:48:0x012a, B:50:0x0136, B:52:0x0146, B:54:0x0156, B:56:0x0162, B:58:0x0180, B:46:0x011e, B:25:0x00a3, B:27:0x00b5, B:28:0x00bc, B:30:0x00ce, B:33:0x00d7), top: B:143:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0251 A[Catch: Exception -> 0x0389, TryCatch #0 {Exception -> 0x0389, blocks: (B:80:0x0217, B:82:0x0251, B:83:0x0257, B:87:0x0283, B:91:0x0290, B:94:0x029e, B:95:0x02a7, B:99:0x02e5, B:103:0x02f0, B:105:0x02f9, B:107:0x0301, B:110:0x030b, B:112:0x0311, B:114:0x0320, B:116:0x032c, B:113:0x0316, B:104:0x02f5, B:117:0x0333, B:119:0x0349, B:121:0x0354, B:123:0x0358, B:126:0x0362, B:129:0x036a, B:130:0x036f, B:132:0x0377, B:135:0x0381), top: B:141:0x0217 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x028d  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x029e A[Catch: Exception -> 0x0389, TRY_ENTER, TryCatch #0 {Exception -> 0x0389, blocks: (B:80:0x0217, B:82:0x0251, B:83:0x0257, B:87:0x0283, B:91:0x0290, B:94:0x029e, B:95:0x02a7, B:99:0x02e5, B:103:0x02f0, B:105:0x02f9, B:107:0x0301, B:110:0x030b, B:112:0x0311, B:114:0x0320, B:116:0x032c, B:113:0x0316, B:104:0x02f5, B:117:0x0333, B:119:0x0349, B:121:0x0354, B:123:0x0358, B:126:0x0362, B:129:0x036a, B:130:0x036f, B:132:0x0377, B:135:0x0381), top: B:141:0x0217 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x02e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static org.json.JSONObject a(boolean r18, boolean r19, boolean r20) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 932
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsDownloader.a(boolean, boolean, boolean):org.json.JSONObject");
    }

    private static void a(int i2) {
        String str;
        int i3;
        try {
            TbsLog.i(LOGTAG, "sendRequestForOtherStableCore cpuType is " + i2 + " mJsonDataQuery is " + f20941o + " mJsonDataDownloadUpdate is " + f20942p);
            if (!com.tencent.smtt.utils.s.b(f20929c)) {
                TbsLog.i(LOGTAG, "sendRequestForOtherStableCore isStableCoreForHostEnable is false and return ");
                return;
            }
            if (i2 < 32) {
                return;
            }
            if (f20941o == null) {
                f20941o = f20942p;
            }
            if (f20941o == null) {
                return;
            }
            if (System.currentTimeMillis() - TbsDownloadConfig.getInstance(f20929c).mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_LAST_DOWNLOAD_STABLE_CORE_OTHER_CPU, 0L) < TbsDownloadConfig.getInstance(f20929c).getRetryInterval() * 1000) {
                TbsLog.i(LOGTAG, "sendRequestForOtherStableCore less than and return ");
                return;
            }
            if (i2 == 64) {
                f20941o.put("REQUEST_64", 1);
            } else if (i2 == 32) {
                f20941o.remove("REQUEST_64");
            }
            f20941o.put("DOWNLOADDECOUPLECORE", 1);
            if (i2 == 64) {
                if (m.a(f20929c).c("stable_64_tpatch_fail") == 1) {
                    str = "sendRequestForOtherStableCore stable_64_last_tpatch_fail and set REQUEST_TPATCH  0 ";
                    TbsLog.i(LOGTAG, str);
                    i3 = 0;
                }
                i3 = 1;
            } else {
                if (m.a(f20929c).c("stable_32_tpatch_fail") == 1) {
                    str = "sendRequestForOtherStableCore stable_32_last_tpatch_fail and set REQUEST_TPATCH  0 ";
                    TbsLog.i(LOGTAG, str);
                    i3 = 0;
                }
                i3 = 1;
            }
            f20941o.put("REQUEST_TPATCH", i3);
            int tbsStableCoreVersion = TbsShareManager.getTbsStableCoreVersion(f20929c, i2);
            f20941o.put("TBSDV", o.a().h(f20929c) % 10000);
            f20941o.put("TBSBACKUPV", tbsStableCoreVersion);
            if (i3 == 0) {
                f20941o.put("TBSDV", 0);
                f20941o.put("TBSBACKUPV", 0);
            }
            if (tbsStableCoreVersion > 0) {
                f20941o.put("FUNCTION", 1);
            } else {
                f20941o.put("FUNCTION", 0);
            }
            f20941o.put("TBSV", 0);
            String strG = com.tencent.smtt.utils.o.a(f20929c).g();
            TbsLog.i(LOGTAG, "[TbsDownloader.sendRequestForOtherStableCore] postUrl=" + strG);
            TbsLog.i(LOGTAG, "[TbsDownloader.sendRequestForOtherStableCore] mJsonDataQuery=" + f20941o.toString());
            JSONObject jSONObject = f20941o;
            f20946t = jSONObject;
            String strA = com.tencent.smtt.utils.g.a(strG, jSONObject.toString().getBytes("utf-8"), new g.a() { // from class: com.tencent.smtt.sdk.TbsDownloader.4
                @Override // com.tencent.smtt.utils.g.a
                public void a(int i4) {
                }
            }, false);
            TbsLog.i(LOGTAG, "[TbsDownloader.sendRequestForOtherStableCore] response=" + strA);
            JSONObject jSONObject2 = new JSONObject(strA);
            TbsDownloadConfig.getInstance(f20929c).mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_LAST_DOWNLOAD_STABLE_CORE_OTHER_CPU, Long.valueOf(System.currentTimeMillis()));
            TbsDownloadConfig.getInstance(f20929c).commit();
            String string = jSONObject2.getString("DOWNLOADURL");
            long j2 = jSONObject2.getLong("TBSAPKFILESIZE");
            String string2 = jSONObject2.getString("PKGMD5");
            int i4 = jSONObject2.getInt("TBSAPKSERVERVERSION");
            int i5 = jSONObject2.getInt("RESPONSECODE");
            f20943q = false;
            if (com.tencent.smtt.utils.s.b(f20929c)) {
                int tbsStableCoreVersion2 = TbsShareManager.getTbsStableCoreVersion(f20929c, i2);
                if (tbsStableCoreVersion2 < i4) {
                    if (a(i4, tbsStableCoreVersion2, i2)) {
                        TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(f20929c);
                        tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_CPUTYPE_OTHER_STABLE_CORE, Integer.valueOf(i2));
                        tbsDownloadConfig.commit();
                        TbsLog.i(LOGTAG, "sendRequestForOtherStableCore stable core " + i4 + " copy from old backup and return ");
                        return;
                    }
                    TbsDownloadConfig tbsDownloadConfig2 = TbsDownloadConfig.getInstance(f20929c);
                    tbsDownloadConfig2.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_TBSDOWNLOADURL, string);
                    tbsDownloadConfig2.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_TBSAPKFILESIZE, Long.valueOf(j2));
                    tbsDownloadConfig2.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_CPUTYPE_OTHER_STABLE_CORE, Integer.valueOf(i2));
                    Map<String, Object> map = tbsDownloadConfig2.mSyncMap;
                    if (string2 == null) {
                        string2 = "";
                    }
                    map.put(TbsDownloadConfig.TbsConfigKey.KEY_TBSAPK_MD5, string2);
                    tbsDownloadConfig2.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 1);
                    tbsDownloadConfig2.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, Integer.valueOf(i4));
                    tbsDownloadConfig2.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_RESPONSECODE, Integer.valueOf(i5));
                    tbsDownloadConfig2.commit();
                    TbsLog.i(LOGTAG, "sendRequestForOtherStableCore downloadUrl is " + string, true);
                    if (!TextUtils.isEmpty(string)) {
                        f20933g.a(false, true);
                    }
                } else if (TextUtils.isEmpty(string) && i4 == 0 && j2 > 0) {
                    f20943q = true;
                }
            }
            b(i2);
            if (f20943q) {
                int tbsStableCoreVersion3 = TbsShareManager.getTbsStableCoreVersion(f20929c, i2);
                f20945s = 0;
                TbsLog.i(LOGTAG, "other cpu stable core is flowed and copyFromOldBackupDone is " + a(f20944r, tbsStableCoreVersion3, i2) + " tbsDownloadVersion is " + i4 + " oldBackupCoreVersion is " + f20945s + " localStableCore is " + tbsStableCoreVersion3 + " cpuType is " + i2);
            }
        } catch (Throwable unused) {
        }
    }

    private static void a(boolean z2, TbsDownloaderCallback tbsDownloaderCallback, boolean z3) {
        TbsLog.i(LOGTAG, "[TbsDownloader.queryConfig]");
        f20930d.removeMessages(100);
        Message messageObtain = Message.obtain(f20930d, 100);
        if (tbsDownloaderCallback != null) {
            messageObtain.obj = tbsDownloaderCallback;
        }
        messageObtain.arg1 = z2 ? 1 : 0;
        messageObtain.arg2 = z3 ? 1 : 0;
        messageObtain.sendToTarget();
    }

    private static boolean a(int i2, int i3, int i4) {
        int iB;
        try {
            TbsLog.i(LOGTAG, "copyFromOldBackupDone newCoreVersion is " + i2 + " localStableCoreVersion is " + i3 + " cpuType is " + i4);
            if (i2 > 0 && i2 != i3) {
                File file = new File(FileUtil.a(f20929c, 3), getBackupFileName(false, i4));
                if (file.exists() && file.canRead()) {
                    iB = com.tencent.smtt.utils.a.b(file);
                    if (iB <= 0) {
                        iB = com.tencent.smtt.utils.a.a(f20929c, file);
                    }
                } else {
                    iB = -1;
                }
                f20945s = iB;
                TbsLog.i(LOGTAG, "copyFromOldBackupDone oldBackupCoreVersion is " + iB);
                if (i2 == iB) {
                    File file2 = new File(FileUtil.a(f20929c, "com.tencent.mm", 4, true));
                    boolean zB = FileUtil.b(file, new File(file2, getBackupFileName(false, i4)));
                    TbsLog.i(LOGTAG, "copyFromOldBackupDone #01 result is " + zB);
                    if (zB) {
                        File[] fileArrListFiles = file2.listFiles();
                        Pattern patternCompile = Pattern.compile(com.tencent.smtt.utils.a.a(false, i4));
                        for (File file3 : fileArrListFiles) {
                            if (patternCompile.matcher(file3.getName()).find() && file3.isFile() && file3.exists()) {
                                file3.delete();
                            }
                        }
                        File file4 = new File(file2, com.tencent.smtt.utils.a.a(false, i4) + StrPool.DOT + i2);
                        if (!file4.exists()) {
                            file4.createNewFile();
                        }
                    }
                    return zB;
                }
            }
        } catch (Throwable th) {
            TbsLog.i(LOGTAG, "stack is " + Log.getStackTraceString(th));
        }
        TbsLog.i(LOGTAG, "copyFromOldBackupDone result is false  #10");
        return false;
    }

    public static boolean a(Context context) {
        return TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) == 1;
    }

    public static boolean a(Context context, int i2) {
        return Build.VERSION.SDK_INT > 28 && context.getApplicationInfo().targetSdkVersion > 28 && i2 > 0 && i2 < 45114;
    }

    private static boolean a(Context context, boolean z2) {
        int i2;
        TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(context);
        int i3 = Build.VERSION.SDK_INT;
        if (!tbsDownloadConfig.mPreferences.contains(TbsDownloadConfig.TbsConfigKey.KEY_IS_OVERSEA)) {
            if (z2 && !"com.tencent.mm".equals(context.getApplicationInfo().packageName)) {
                TbsLog.i(LOGTAG, "needDownload-oversea is true, but not WX");
                z2 = false;
            }
            tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_IS_OVERSEA, Boolean.valueOf(z2));
            tbsDownloadConfig.commit();
            f20939m = z2;
            TbsLog.i(LOGTAG, "needDownload-first-called--isoversea = " + z2);
        }
        if (getOverSea(context)) {
            TbsLog.i(LOGTAG, "needDownload- return false,  because of  version is " + i3 + ", and overea");
            i2 = -103;
        } else {
            String string = tbsDownloadConfig.mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_DEVICE_CPUABI, null);
            f20931e = string;
            if (TextUtils.isEmpty(string) || com.tencent.smtt.utils.b.a(f20931e)) {
                return true;
            }
            TbsLog.e(LOGTAG, "can not support x86 devices!!");
            i2 = TXEAudioDef.TXE_AUDIO_PLAY_ERR_REPEAT_OPTION;
        }
        tbsDownloadConfig.setDownloadInterruptCode(i2);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x024c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(android.content.Context r22, boolean r23, boolean r24) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 626
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsDownloader.a(android.content.Context, boolean, boolean):boolean");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:14|(1:18)|19|(2:142|20)|(1:22)(2:24|(1:26)(15:28|29|32|146|33|34|(1:36)(1:37)|38|(1:40)(1:41)|42|1a9|53|(3:55|(1:57)(1:58)|59)(9:(1:64)|65|(1:67)|68|(6:70|(1:72)|73|(1:75)|76|(2:78|148))|79|(1:81)|82|(2:87|(2:89|90)(5:91|(1:93)|94|(3:96|(1:98)(1:99)|100)(1:101)|(4:129|(1:(1:133)(1:(1:135)(1:136)))|137|149)(7:107|(1:109)(1:110)|111|(1:113)|114|(1:116)|(5:(1:123)|124|(1:126)|127|128)(1:121))))(1:86))|60|61))|23|29|32|146|33|34|(0)(0)|38|(0)(0)|42|1a9) */
    /* JADX WARN: Removed duplicated region for block: B:36:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01aa  */
    @android.annotation.TargetApi(11)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(java.lang.String r31, int r32, boolean r33, boolean r34, boolean r35) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 1242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsDownloader.a(java.lang.String, int, boolean, boolean, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String b(android.content.Context r7) {
        /*
            java.lang.String r0 = "ISO8859-1"
            java.lang.String r1 = "UTF-8"
            java.lang.String r2 = com.tencent.smtt.sdk.TbsDownloader.f20928b
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto Lf
            java.lang.String r7 = com.tencent.smtt.sdk.TbsDownloader.f20928b
            return r7
        Lf:
            java.util.Locale r2 = java.util.Locale.getDefault()
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
            java.lang.String r4 = android.os.Build.VERSION.RELEASE
            java.lang.String r5 = new java.lang.String     // Catch: java.lang.Exception -> L24
            byte[] r6 = r4.getBytes(r1)     // Catch: java.lang.Exception -> L24
            r5.<init>(r6, r0)     // Catch: java.lang.Exception -> L24
            r4 = r5
        L24:
            java.lang.String r5 = "1.0"
            if (r4 != 0) goto L2c
        L28:
            r3.append(r5)
            goto L35
        L2c:
            int r6 = r4.length()
            if (r6 <= 0) goto L28
            r3.append(r4)
        L35:
            java.lang.String r4 = "; "
            r3.append(r4)
            java.lang.String r5 = r2.getLanguage()
            if (r5 == 0) goto L57
            java.lang.String r5 = r5.toLowerCase()
            r3.append(r5)
            java.lang.String r2 = r2.getCountry()
            if (r2 == 0) goto L5c
            java.lang.String r5 = "-"
            r3.append(r5)
            java.lang.String r2 = r2.toLowerCase()
            goto L59
        L57:
            java.lang.String r2 = "en"
        L59:
            r3.append(r2)
        L5c:
            java.lang.String r2 = "REL"
            java.lang.String r5 = android.os.Build.VERSION.CODENAME
            boolean r2 = r2.equals(r5)
            if (r2 == 0) goto L86
            java.lang.String r7 = com.tencent.smtt.utils.s.c(r7)
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Exception -> L74
            byte[] r1 = r7.getBytes(r1)     // Catch: java.lang.Exception -> L74
            r2.<init>(r1, r0)     // Catch: java.lang.Exception -> L74
            r7 = r2
        L74:
            if (r7 != 0) goto L7a
            r3.append(r4)
            goto L86
        L7a:
            int r0 = r7.length()
            if (r0 <= 0) goto L86
            r3.append(r4)
            r3.append(r7)
        L86:
            java.lang.String r7 = android.os.Build.ID
            java.lang.String r0 = ""
            if (r7 != 0) goto L8d
            r7 = r0
        L8d:
            java.lang.String r1 = "[一-龥]"
            java.lang.String r7 = r7.replaceAll(r1, r0)
            java.lang.String r0 = " Build/"
            if (r7 != 0) goto La0
            r3.append(r0)
            java.lang.String r7 = "00"
        L9c:
            r3.append(r7)
            goto Laa
        La0:
            int r1 = r7.length()
            if (r1 <= 0) goto Laa
            r3.append(r0)
            goto L9c
        Laa:
            r7 = 1
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r0 = 0
            r7[r0] = r3
            java.lang.String r0 = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 Mobile Safari/533.1"
            java.lang.String r7 = java.lang.String.format(r0, r7)
            com.tencent.smtt.sdk.TbsDownloader.f20928b = r7
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsDownloader.b(android.content.Context):java.lang.String");
    }

    private static void b(int i2) {
        JSONObject jSONObject = f20946t;
        if (jSONObject == null) {
            return;
        }
        try {
            jSONObject.put("FUNCTION", 2);
            String strG = com.tencent.smtt.utils.o.a(f20929c).g();
            TbsLog.i(LOGTAG, "[TbsDownloader.resetOtherCpuStableCore] postUrl=" + strG);
            TbsLog.i(LOGTAG, "[TbsDownloader.resetOtherCpuStableCore] mJsonDataQueryReset=" + f20946t.toString());
            String strA = com.tencent.smtt.utils.g.a(strG, f20946t.toString().getBytes("utf-8"), new g.a() { // from class: com.tencent.smtt.sdk.TbsDownloader.5
                @Override // com.tencent.smtt.utils.g.a
                public void a(int i3) {
                }
            }, false);
            TbsLog.i(LOGTAG, "[TbsDownloader.resetOtherCpuStableCore] response=" + strA);
            f20944r = 0;
            JSONObject jSONObject2 = new JSONObject(strA);
            if (jSONObject2.getInt("RESETDECOUPLECORE") != 1) {
                f20944r = jSONObject2.getInt("DECOUPLECOREVERSION");
                return;
            }
            Context packageContext = TbsShareManager.getPackageContext(f20929c, "com.tencent.mm", false);
            File file = packageContext == null ? new File(FileUtil.a(f20929c, "com.tencent.mm", 4, true)) : new File(FileUtil.a(packageContext, 4));
            TbsLog.i(LOGTAG, "[TbsDownloader.resetOtherCpuStableCore] resetDecoupleCore mFilePathStable is " + file.getAbsolutePath(), true);
            File[] fileArrListFiles = file.listFiles();
            Pattern patternCompile = Pattern.compile(com.tencent.smtt.utils.a.a(false, i2));
            for (File file2 : fileArrListFiles) {
                if (patternCompile.matcher(file2.getName()).find() && file2.isFile() && file2.exists()) {
                    TbsLog.i(LOGTAG, "resetOtherCpuStableCore file is " + file2.getAbsolutePath(), true);
                    file2.delete();
                }
            }
            File file3 = new File(file, getBackupFileName(false, i2));
            TbsLog.i(LOGTAG, "resetOtherCpuStableCore file is " + file3.getAbsolutePath(), true);
            file3.delete();
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(final boolean z2, boolean z3, boolean z4, boolean z5) throws Throwable {
        boolean z6;
        boolean zA;
        String strD;
        final TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(f20929c);
        l.b(f20929c);
        Map<String, Object> map = QbSdk.f20831o;
        if (map != null && map.containsKey(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD) && QbSdk.f20831o.get(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD).equals(k.a.f27524v)) {
            TbsLog.i(LOGTAG, "[TbsDownloader.sendRequest] -- SET_SENDREQUEST_AND_UPLOAD is false");
            tbsDownloadConfig.setDownloadInterruptCode(-131);
            return false;
        }
        TbsLog.i(LOGTAG, "[TbsDownloader.sendRequest]isQuery: " + z2 + " forDecoupleCore is " + z4);
        if (o.a().d(f20929c)) {
            TbsLog.i(LOGTAG, "[TbsDownloader.sendRequest] -- isTbsLocalInstalled!");
            tbsDownloadConfig.setDownloadInterruptCode(-132);
            return false;
        }
        if (f20931e == null) {
            String strA = com.tencent.smtt.utils.b.a();
            f20931e = strA;
            tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_DEVICE_CPUABI, strA);
            tbsDownloadConfig.commit();
        }
        if (!TextUtils.isEmpty(f20931e) && !com.tencent.smtt.utils.b.a(f20931e)) {
            tbsDownloadConfig.setDownloadInterruptCode(TXEAudioDef.TXE_AUDIO_PLAY_ERR_REPEAT_OPTION);
            TbsLog.i(LOGTAG, "TbsDownloader sendRequest cpu is invalid:" + f20931e);
            return false;
        }
        tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONNAME, com.tencent.smtt.utils.b.d(f20929c));
        tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONCODE, Integer.valueOf(com.tencent.smtt.utils.b.e(f20929c)));
        tbsDownloadConfig.commit();
        JSONObject jSONObjectA = a(z2, z3, z4);
        int iOptInt = jSONObjectA.optInt("TBSV", -1);
        if (iOptInt != -1) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (TbsShareManager.isThirdPartyApp(f20929c)) {
                tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_COUNT_REQUEST_FAIL_IN_24HOURS, Long.valueOf(jCurrentTimeMillis - tbsDownloadConfig.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_REQUEST_FAIL, 0L) < tbsDownloadConfig.getRetryInterval() * 1000 ? tbsDownloadConfig.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_COUNT_REQUEST_FAIL_IN_24HOURS, 0L) + 1 : 1L));
            }
            tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_REQUEST_FAIL, Long.valueOf(jCurrentTimeMillis));
            tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONNAME, com.tencent.smtt.utils.b.d(f20929c));
            tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONCODE, Integer.valueOf(com.tencent.smtt.utils.b.e(f20929c)));
            tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_APP_METADATA, com.tencent.smtt.utils.b.a(f20929c, TBS_METADATA));
            tbsDownloadConfig.commit();
        }
        if (iOptInt == -1 && !z4) {
            tbsDownloadConfig.setDownloadInterruptCode(-113);
            return false;
        }
        try {
            String strG = com.tencent.smtt.utils.o.a(f20929c).g();
            TbsLog.i(LOGTAG, "[TbsDownloader.sendRequest] postUrl=" + strG);
            if (z2) {
                try {
                    f20941o = jSONObjectA;
                } catch (Throwable th) {
                    th = th;
                    zA = false;
                    TbsLog.i(LOGTAG, "sendrequest return false " + Log.getStackTraceString(th));
                    th.printStackTrace();
                    tbsDownloadConfig.setDownloadInterruptCode(TXEAudioDef.TXE_AUDIO_RECORD_ERR_CUR_RECORDER_INVALID);
                    return zA;
                }
            } else {
                f20942p = jSONObjectA;
            }
            String strA2 = null;
            if (com.tencent.smtt.utils.s.d(f20929c)) {
                try {
                    int i2 = jSONObjectA.getInt("FUNCTION");
                    if (i2 == 0 && !TextUtils.isEmpty(com.tencent.smtt.utils.o.a(f20929c).f())) {
                        strD = com.tencent.smtt.utils.o.a(f20929c).f();
                    } else if (i2 == 1 && !TextUtils.isEmpty(com.tencent.smtt.utils.o.a(f20929c).e())) {
                        strD = com.tencent.smtt.utils.o.a(f20929c).e();
                    } else if (i2 == 2 && !TextUtils.isEmpty(com.tencent.smtt.utils.o.a(f20929c).d())) {
                        strD = com.tencent.smtt.utils.o.a(f20929c).d();
                    }
                    strA2 = strD;
                } catch (Throwable unused) {
                }
            }
            if (strA2 != null) {
                z6 = false;
            } else {
                if (z2) {
                    return true;
                }
                z6 = false;
                try {
                    strA2 = com.tencent.smtt.utils.g.a(strG, jSONObjectA.toString().getBytes("utf-8"), new g.a() { // from class: com.tencent.smtt.sdk.TbsDownloader.3
                        @Override // com.tencent.smtt.utils.g.a
                        public void a(int i3) {
                            TbsDownloadConfig tbsDownloadConfig2;
                            int i4;
                            tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_LAST_CHECK, Long.valueOf(System.currentTimeMillis()));
                            tbsDownloadConfig.commit();
                            TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.sendRequest] httpResponseCode=" + i3);
                            if (TbsShareManager.isThirdPartyApp(TbsDownloader.f20929c) && i3 == 200) {
                                tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_LAST_REQUEST_SUCCESS, Long.valueOf(System.currentTimeMillis()));
                                tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_REQUEST_FAIL, 0L);
                                tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_COUNT_REQUEST_FAIL_IN_24HOURS, 0L);
                                tbsDownloadConfig.commit();
                            }
                            if (i3 >= 300) {
                                if (z2) {
                                    tbsDownloadConfig2 = tbsDownloadConfig;
                                    i4 = -107;
                                } else {
                                    tbsDownloadConfig2 = tbsDownloadConfig;
                                    i4 = -207;
                                }
                                tbsDownloadConfig2.setDownloadInterruptCode(i4);
                            }
                        }
                    }, false);
                    if (strA2 != null && strA2.contains("HttpError")) {
                        TbsLogReport.TbsLogInfo tbsLogInfo = TbsLogReport.getInstance(f20929c).tbsLogInfo();
                        tbsLogInfo.setErrorCode(-129);
                        tbsLogInfo.setFailDetail(strA2);
                        TbsLogReport.getInstance(f20929c).eventReport(TbsLogReport.EventType.TYPE_DOWNLOAD, tbsLogInfo);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    zA = z6;
                    TbsLog.i(LOGTAG, "sendrequest return false " + Log.getStackTraceString(th));
                    th.printStackTrace();
                    tbsDownloadConfig.setDownloadInterruptCode(TXEAudioDef.TXE_AUDIO_RECORD_ERR_CUR_RECORDER_INVALID);
                    return zA;
                }
            }
            zA = a(strA2, iOptInt, z2, z3, z5);
        } catch (Throwable th3) {
            th = th3;
            z6 = false;
        }
        try {
            TbsLog.i(LOGTAG, "sendrequest return false #2");
            return zA;
        } catch (Throwable th4) {
            th = th4;
            TbsLog.i(LOGTAG, "sendrequest return false " + Log.getStackTraceString(th));
            th.printStackTrace();
            tbsDownloadConfig.setDownloadInterruptCode(TXEAudioDef.TXE_AUDIO_RECORD_ERR_CUR_RECORDER_INVALID);
            return zA;
        }
    }

    private static void c(int i2) throws IOException {
        File file = new File(o.r(f20929c), "tbs_switch_disable_" + i2);
        if (file.exists()) {
            return;
        }
        try {
            TbsLog.i(LOGTAG, "setTbsCoreDisabledBySwitch status: " + file.createNewFile());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @TargetApi(11)
    public static void c(Context context) {
        TbsDownloadConfig.getInstance(context).clear();
        TbsLogReport.getInstance(context).clear();
        l.e(context);
        context.getSharedPreferences("tbs_extension_config", 4).edit().clear().commit();
        context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 4).edit().clear().commit();
    }

    private static boolean c() {
        String str;
        TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded ");
        if (TbsShareManager.isThirdPartyApp(f20929c)) {
            return false;
        }
        TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded #1");
        l.a(f20929c);
        l.b(f20929c);
        if (a(f20929c) || f20930d == null) {
            return false;
        }
        TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded #2");
        if (System.currentTimeMillis() - TbsDownloadConfig.getInstance(f20929c).mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_LAST_DOWNLOAD_DECOUPLE_CORE, 0L) < TbsDownloadConfig.getInstance(f20929c).getRetryInterval() * 1000) {
            return false;
        }
        TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded #3");
        int i2 = TbsDownloadConfig.getInstance(f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, 0);
        int tbsStableCoreVersion = com.tencent.smtt.utils.s.b(f20929c) ? TbsShareManager.getTbsStableCoreVersion(f20929c, 0) : o.a().g(f20929c);
        TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded coreVersionForThirdApp is " + tbsStableCoreVersion + " deCoupleCoreVersion is " + i2);
        if (i2 <= 0 || i2 <= tbsStableCoreVersion) {
            str = "startDecoupleCoreIfNeeded no need, deCoupleCoreVersion is " + i2 + " localStableCoreVersion is " + tbsStableCoreVersion;
        } else {
            if (a(i2, tbsStableCoreVersion, 0)) {
                TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(f20929c);
                tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_CPUTYPE_OTHER_STABLE_CORE, 0);
                tbsDownloadConfig.commit();
                TbsLog.i(LOGTAG, "startDecoupleCoreIfNeededImpl stable core " + i2 + " copy from old backup and return ");
                return false;
            }
            if (TbsDownloadConfig.getInstance(f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0) != i2 || TbsDownloadConfig.getInstance(f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V_TYPE, 0) == 1) {
                TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded #4");
                f20927a = true;
                f20930d.removeMessages(108);
                Message messageObtain = Message.obtain(f20930d, 108, QbSdk.f20830n);
                messageObtain.arg1 = 0;
                messageObtain.sendToTarget();
                TbsDownloadConfig.getInstance(f20929c).mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_LAST_DOWNLOAD_DECOUPLE_CORE, Long.valueOf(System.currentTimeMillis()));
                return true;
            }
            str = "startDecoupleCoreIfNeeded no need, KEY_TBS_DOWNLOAD_V is " + TbsDownloadConfig.getInstance(f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0) + " deCoupleCoreVersion is " + i2 + " KEY_TBS_DOWNLOAD_V_TYPE is " + TbsDownloadConfig.getInstance(f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V_TYPE, 0);
        }
        TbsLog.i(LOGTAG, str);
        return false;
    }

    private static synchronized void d() {
        if (f20934h == null) {
            f20934h = n.a();
            try {
                f20933g = new l(f20929c);
                f20930d = new Handler(f20934h.getLooper()) { // from class: com.tencent.smtt.sdk.TbsDownloader.2
                    @Override // android.os.Handler
                    public void handleMessage(Message message) throws Throwable {
                        int i2 = message.what;
                        if (i2 == 109) {
                            if (TbsDownloader.f20933g != null) {
                                TbsDownloader.f20933g.e();
                                return;
                            }
                            return;
                        }
                        switch (i2) {
                            case 100:
                                boolean z2 = message.arg1 == 1;
                                boolean zB = TbsDownloader.b(true, false, false, message.arg2 == 1);
                                Object obj = message.obj;
                                if (obj != null && (obj instanceof TbsDownloaderCallback)) {
                                    TbsLog.i(TbsDownloader.LOGTAG, "needDownload-onNeedDownloadFinish needStartDownload=" + zB);
                                    String str = (TbsDownloader.f20929c == null || TbsDownloader.f20929c.getApplicationContext() == null || TbsDownloader.f20929c.getApplicationContext().getApplicationInfo() == null) ? "" : TbsDownloader.f20929c.getApplicationContext().getApplicationInfo().packageName;
                                    if (!zB || z2) {
                                        ((TbsDownloaderCallback) message.obj).onNeedDownloadFinish(zB, TbsDownloadConfig.getInstance(TbsDownloader.f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0));
                                    } else if ("com.tencent.mm".equals(str) || "com.tencent.mobileqq".equals(str)) {
                                        TbsLog.i(TbsDownloader.LOGTAG, "needDownload-onNeedDownloadFinish in mm or QQ callback needStartDownload = " + zB);
                                        ((TbsDownloaderCallback) message.obj).onNeedDownloadFinish(zB, TbsDownloadConfig.getInstance(TbsDownloader.f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0));
                                    }
                                }
                                if (TbsShareManager.isThirdPartyApp(TbsDownloader.f20929c) && zB) {
                                    TbsDownloader.startDownload(TbsDownloader.f20929c);
                                    break;
                                }
                                break;
                            case 101:
                                TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(TbsDownloader.f20929c);
                                if (Apn.getApnType(TbsDownloader.f20929c) != 3 && !QbSdk.canDownloadWithoutWifi()) {
                                    TbsLog.i(TbsDownloader.LOGTAG, "not wifi,no need send request");
                                    tbsDownloadConfig.setDownloadInterruptCode(-220);
                                    QbSdk.f20830n.onDownloadFinish(111);
                                    QbSdk.f20830n.onInstallFinish(243);
                                    break;
                                } else {
                                    String str2 = "tbs_download_lock_file" + TbsDownloadConfig.getInstance(TbsDownloader.f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0) + ".txt";
                                    TbsLog.i(TbsDownloader.LOGTAG, "---getting download file lock...");
                                    FileOutputStream fileOutputStreamB = FileUtil.b(TbsDownloader.f20929c, true, str2);
                                    if (fileOutputStreamB == null) {
                                        tbsDownloadConfig.setDownloadInterruptCode(-204);
                                        QbSdk.f20830n.onDownloadFinish(153);
                                        QbSdk.f20830n.onInstallFinish(243);
                                        TbsLog.w(TbsDownloader.LOGTAG, "download file-lock file io exception");
                                        break;
                                    } else {
                                        FileLock fileLockA = FileUtil.a(TbsDownloader.f20929c, fileOutputStreamB);
                                        if (fileLockA == null) {
                                            tbsDownloadConfig.setDownloadInterruptCode(-203);
                                            QbSdk.f20830n.onDownloadFinish(177);
                                            QbSdk.f20830n.onInstallFinish(243);
                                            TbsLog.i(TbsDownloader.LOGTAG, "download file-lock locked, core is downloading");
                                            break;
                                        } else {
                                            boolean z3 = message.arg1 == 1;
                                            if (TbsDownloader.b(false, z3, false, true)) {
                                                if (z3 && o.a().b(TbsDownloader.f20929c, TbsDownloadConfig.getInstance(TbsDownloader.f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0))) {
                                                    TbsLog.i(TbsDownloader.LOGTAG, "needStartDownload, but try local install core firstly");
                                                    QbSdk.f20830n.onDownloadFinish(122);
                                                    QbSdk.f20830n.onInstallFinish(243);
                                                    tbsDownloadConfig.setDownloadInterruptCode(-213);
                                                } else if (tbsDownloadConfig.mPreferences.getBoolean(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD, false)) {
                                                    TbsDownloadConfig.getInstance(TbsDownloader.f20929c).setDownloadInterruptCode(-215);
                                                    TbsLog.i(TbsDownloader.LOGTAG, "start download in apk downloader...");
                                                    TbsDownloader.f20933g.a(z3, false);
                                                } else {
                                                    tbsDownloadConfig.setDownloadInterruptCode(-133);
                                                    QbSdk.f20830n.onDownloadFinish(154);
                                                }
                                                TbsLog.i(TbsDownloader.LOGTAG, "------freeDownloadFileLock...");
                                                FileUtil.a(fileLockA, fileOutputStreamB);
                                                break;
                                            } else {
                                                int currentDownloadInterruptCode = TbsDownloadConfig.getInstance(TbsDownloader.f20929c).getCurrentDownloadInterruptCode();
                                                TbsLog.i(TbsDownloader.LOGTAG, "No need to download, code is " + currentDownloadInterruptCode);
                                                QbSdk.f20830n.onDownloadFinish(currentDownloadInterruptCode);
                                            }
                                            QbSdk.f20830n.onInstallFinish(243);
                                            TbsLog.i(TbsDownloader.LOGTAG, "------freeDownloadFileLock...");
                                            FileUtil.a(fileLockA, fileOutputStreamB);
                                        }
                                    }
                                }
                                break;
                            case 102:
                                TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.handleMessage] MSG_REPORT_DOWNLOAD_STAT");
                                int iL = o.a().l(TbsDownloader.f20929c);
                                TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.handleMessage] localTbsVersion=" + iL);
                                TbsDownloader.f20933g.a(iL);
                                TbsLogReport.getInstance(TbsDownloader.f20929c).dailyReport();
                                break;
                            case 103:
                                TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.handleMessage] MSG_CONTINUEINSTALL_TBSCORE");
                                if (message.arg1 == 0) {
                                    o.a().a((Context) message.obj, true);
                                    break;
                                }
                                break;
                            case 104:
                                TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.handleMessage] MSG_UPLOAD_TBSLOG");
                                TbsLogReport.getInstance(TbsDownloader.f20929c).reportTbsLog();
                                break;
                        }
                    }
                };
            } catch (Exception unused) {
                f20936j = true;
                TbsLog.e(LOGTAG, "TbsApkDownloader init has Exception");
            }
        }
    }

    private static boolean e() {
        try {
            return TbsDownloadConfig.getInstance(f20929c).mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_LAST_THIRDAPP_SENDREQUEST_COREVERSION, "").equals(f().toString());
        } catch (Exception unused) {
            return false;
        }
    }

    private static JSONArray f() {
        if (!TbsShareManager.isThirdPartyApp(f20929c)) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        int tbsVersion = QbSdk.getTbsVersion(f20929c);
        if (tbsVersion > 0) {
            jSONArray.put(tbsVersion);
        }
        return jSONArray;
    }

    private static boolean g() {
        int i2;
        TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(f20929c);
        if (tbsDownloadConfig.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOAD_SUCCESS_RETRYTIMES, 0) >= tbsDownloadConfig.getDownloadSuccessMaxRetrytimes()) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needStartDownload] out of success retrytimes", true);
            i2 = -115;
        } else if (tbsDownloadConfig.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOAD_FAILED_RETRYTIMES, 0) >= tbsDownloadConfig.getDownloadFailedMaxRetrytimes()) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needStartDownload] out of failed retrytimes", true);
            i2 = -116;
        } else {
            if (FileUtil.b(f20929c)) {
                if (System.currentTimeMillis() - tbsDownloadConfig.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_TBSDOWNLOAD_STARTTIME, 0L) <= 86400000) {
                    long j2 = tbsDownloadConfig.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_TBSDOWNLOAD_FLOW, 0L);
                    TbsLog.i(LOGTAG, "[TbsDownloader.needStartDownload] downloadFlow=" + j2);
                    if (j2 >= tbsDownloadConfig.getDownloadMaxflow()) {
                        TbsLog.i(LOGTAG, "[TbsDownloader.needStartDownload] failed because you exceeded max flow!", true);
                        i2 = -120;
                    }
                }
                return true;
            }
            TbsLog.i(LOGTAG, "[TbsDownloader.needStartDownload] local rom freespace limit", true);
            i2 = -117;
        }
        tbsDownloadConfig.setDownloadInterruptCode(i2);
        return false;
    }

    public static String getBackupFileName(boolean z2) {
        return getBackupFileName(z2, 0);
    }

    public static String getBackupFileName(boolean z2, int i2) {
        boolean zC = i2 == 64 ? true : i2 == 32 ? false : com.tencent.smtt.utils.b.c();
        return z2 ? zC ? "x5.tbs.decouple.64" : "x5.tbs.decouple" : zC ? "x5.tbs.org.64" : "x5.tbs.org";
    }

    public static int getCoreShareDecoupleCoreVersion() {
        return o.a().g(f20929c);
    }

    public static int getCoreShareDecoupleCoreVersionByContext(Context context) {
        return o.a().g(context);
    }

    public static int getNextPostInterval(Context context) {
        TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(context);
        long j2 = tbsDownloadConfig.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_LAST_CHECK, 0L);
        return Math.max((int) (((tbsDownloadConfig.getRetryInterval() * 1000) - (System.currentTimeMillis() - j2)) / 1000), 0);
    }

    public static synchronized boolean getOverSea(Context context) {
        if (!f20940n) {
            f20940n = true;
            TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(context);
            if (tbsDownloadConfig.mPreferences.contains(TbsDownloadConfig.TbsConfigKey.KEY_IS_OVERSEA)) {
                f20939m = tbsDownloadConfig.mPreferences.getBoolean(TbsDownloadConfig.TbsConfigKey.KEY_IS_OVERSEA, false);
                TbsLog.i(LOGTAG, "[TbsDownloader.getOverSea]  first called. sOverSea = " + f20939m);
            }
            TbsLog.i(LOGTAG, "[TbsDownloader.getOverSea]  sOverSea = " + f20939m);
        }
        return f20939m;
    }

    public static long getRetryIntervalInSeconds() {
        return f20947u;
    }

    public static HandlerThread getsTbsHandlerThread() {
        return f20934h;
    }

    private static void h() {
        File fileR = o.r(f20929c);
        if (fileR == null) {
            return;
        }
        File[] fileArrListFiles = fileR.listFiles();
        Pattern patternCompile = Pattern.compile("tbs_switch_disable_(.*)");
        for (File file : fileArrListFiles) {
            if (patternCompile.matcher(file.getName()).find() && file.isFile() && file.exists() && file.canRead()) {
                TbsLog.i(LOGTAG, "clearTbsCoreDisableFlagFiles: " + file.getName() + "; res: " + file.delete());
            }
        }
    }

    private static void i() throws IOException {
        File file = new File(o.r(f20929c), "switch_disable_check");
        if (file.exists()) {
            return;
        }
        try {
            TbsLog.i(LOGTAG, "addSwitchDisableCheckFlag status: " + file.createNewFile());
        } catch (IOException e2) {
            TbsLog.i(LOGTAG, "" + e2);
        }
    }

    public static boolean isDownloadForeground() {
        l lVar = f20933g;
        return lVar != null && lVar.c();
    }

    public static synchronized boolean isDownloading() {
        TbsLog.i(LOGTAG, "[TbsDownloader.isDownloading] is " + f20927a);
        return f20927a;
    }

    public static boolean isTbsCoreDisabledBySwitch(Context context, int i2) {
        return new File(o.r(context), "tbs_switch_disable_" + i2).exists();
    }

    public static boolean needDownload(Context context, boolean z2) {
        return needDownload(context, z2, false, true, null);
    }

    public static boolean needDownload(Context context, boolean z2, boolean z3, TbsDownloaderCallback tbsDownloaderCallback) {
        return needDownload(context, z2, z3, true, tbsDownloaderCallback);
    }

    public static boolean needDownload(Context context, boolean z2, boolean z3, boolean z4, TbsDownloaderCallback tbsDownloaderCallback) throws Throwable {
        int i2;
        TbsShareManager.mHasQueryed = true;
        Context applicationContext = context.getApplicationContext();
        f20929c = applicationContext;
        TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(applicationContext);
        tbsDownloadConfig.setDownloadInterruptCode(-100);
        com.tencent.smtt.utils.s.a(context, "need_download", "");
        com.tencent.smtt.utils.s.a("1");
        TbsLog.initIfNeed(context);
        TbsLog.i(LOGTAG, "needDownload,process=" + QbSdk.getCurrentProcessName(context) + "stack=" + Log.getStackTraceString(new Throwable()));
        o.a().b(context, g.f21179a == 0);
        int iB = o.a().b(context);
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload],renameRet=" + iB);
        if (iB != 0) {
            TbsLogReport.TbsLogInfo tbsLogInfo = TbsLogReport.getInstance(context).tbsLogInfo();
            tbsLogInfo.f20963a = 129;
            tbsLogInfo.setFailDetail("code=2" + iB);
        }
        if (iB < 0) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload],needReNameFile=" + iB);
            tbsDownloadConfig.setDownloadInterruptCode(-128);
            if (tbsDownloaderCallback != null) {
                tbsDownloaderCallback.onNeedDownloadFinish(false, 0);
            }
            return false;
        }
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload] oversea=" + z2 + ",isDownloadForeground=" + z3);
        if (o.f21241a) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#1 Static Installing, return false");
            tbsDownloadConfig.setDownloadInterruptCode(-130);
            if (tbsDownloaderCallback != null) {
                tbsDownloaderCallback.onNeedDownloadFinish(false, 0);
            }
            return false;
        }
        TbsLog.app_extra(LOGTAG, context);
        if (!a(f20929c, z2)) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#2 Not shouldDoNeedDownload, return false");
            if (tbsDownloaderCallback != null) {
                tbsDownloaderCallback.onNeedDownloadFinish(false, 0);
            }
            return false;
        }
        d();
        if (f20936j) {
            tbsDownloadConfig.setDownloadInterruptCode(TXEAudioDef.TXE_AUDIO_PLAY_ERR_NOT_CREATE_JIT);
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#3 TbsApkDownloader init Exception, return false");
            if (tbsDownloaderCallback != null) {
                tbsDownloaderCallback.onNeedDownloadFinish(false, 0);
            }
            return false;
        }
        boolean zA = a(f20929c, z3, false);
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload],needSendRequest=" + zA);
        if (zA) {
            a(z3, tbsDownloaderCallback, z4);
            i2 = -114;
        } else {
            i2 = TbsCommonCode.DOWNLOAD_NO_NEED_REQUEST;
        }
        tbsDownloadConfig.setDownloadInterruptCode(i2);
        f20930d.removeMessages(102);
        Message.obtain(f20930d, 102).sendToTarget();
        boolean zContains = tbsDownloadConfig.mPreferences.contains(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD);
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload] hasNeedDownloadKey=" + zContains);
        boolean z5 = (zContains || TbsShareManager.isThirdPartyApp(context)) ? tbsDownloadConfig.mPreferences.getBoolean(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD, false) : true;
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#4,needDownload=" + z5 + ",hasNeedDownloadKey=" + zContains);
        if (!z5) {
            int iL = o.a().l(f20929c);
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#7,tbsLocalVersion=" + iL + ",needSendRequest=" + zA);
            if (zA || iL <= 0) {
                f20930d.removeMessages(103);
                if (iL <= 0 && !zA) {
                    Message.obtain(f20930d, 103, 0, 0, f20929c).sendToTarget();
                }
            }
        } else if (g()) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload] NEEDDOWNLOAD_WILL_STARTDOWNLOAD");
        } else {
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#5,set needDownload = false");
            z5 = false;
        }
        if (!zA && tbsDownloaderCallback != null) {
            tbsDownloaderCallback.onNeedDownloadFinish(z5, 0);
        }
        return z5;
    }

    public static boolean needDownloadDecoupleCore() {
        int i2;
        if (TbsShareManager.isThirdPartyApp(f20929c) || a(f20929c)) {
            return false;
        }
        return System.currentTimeMillis() - TbsDownloadConfig.getInstance(f20929c).mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_LAST_DOWNLOAD_DECOUPLE_CORE, 0L) >= TbsDownloadConfig.getInstance(f20929c).getRetryInterval() * 1000 && (i2 = TbsDownloadConfig.getInstance(f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, 0)) > 0 && i2 != o.a().g(f20929c) && TbsDownloadConfig.getInstance(f20929c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0) != i2;
    }

    public static void pauseDownload() {
        TbsLog.i(LOGTAG, "called pauseDownload,downloader=" + f20933g);
        l lVar = f20933g;
        if (lVar != null) {
            lVar.d();
        }
    }

    public static void resumeDownload() {
        TbsLog.i(LOGTAG, "called resumeDownload,downloader=" + f20933g);
        Handler handler = f20930d;
        if (handler != null) {
            handler.removeMessages(109);
            f20930d.sendEmptyMessage(109);
        }
    }

    public static void setAppContext(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return;
        }
        f20929c = context.getApplicationContext();
    }

    public static void setRetryIntervalInSeconds(Context context, long j2) {
        if (context == null) {
            return;
        }
        if (context.getApplicationInfo().packageName.equals("com.tencent.qqlive")) {
            f20947u = j2;
        }
        TbsLog.i(LOGTAG, "mRetryIntervalInSeconds is " + f20947u);
    }

    @Deprecated
    public static boolean startDecoupleCoreIfNeeded() {
        TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded closeStableCore ");
        return false;
    }

    public static void startDownload(Context context) {
        startDownload(context, false);
    }

    public static synchronized void startDownload(Context context, boolean z2) {
        int i2 = 1;
        if (TbsShareManager.isThirdPartyApp(context)) {
            int i3 = f20935i + 1;
            f20935i = i3;
            if (i3 > 1) {
                TbsLog.w(LOGTAG, "[Warning] for privacy security, TBS Only allow startDownload 1 times each process");
                QbSdk.f20830n.onDownloadFinish(127);
                return;
            }
        }
        Context applicationContext = context.getApplicationContext();
        f20929c = applicationContext;
        TbsLog.initIfNeed(applicationContext);
        TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(f20929c);
        tbsDownloadConfig.setDownloadInterruptCode(-200);
        TbsLog.i(LOGTAG, "[TbsDownloader.startDownload] sAppContext=" + f20929c);
        if (o.f21241a) {
            tbsDownloadConfig.setDownloadInterruptCode(-130);
            QbSdk.f20830n.onDownloadFinish(151);
            return;
        }
        o.a().b(context, g.f21179a == 0);
        int iB = o.a().b(context);
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload],renameRet=" + iB);
        if (iB < 0) {
            tbsDownloadConfig.setDownloadInterruptCode(-128);
            QbSdk.f20830n.onDownloadFinish(152);
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload],needReNameFile=" + iB);
            return;
        }
        f20927a = true;
        d();
        if (f20936j) {
            tbsDownloadConfig.setDownloadInterruptCode(-202);
            QbSdk.f20830n.onDownloadFinish(121);
            return;
        }
        if (z2) {
            stopDownload();
        }
        f20930d.removeMessages(101);
        f20930d.removeMessages(100);
        Message messageObtain = Message.obtain(f20930d, 101, QbSdk.f20830n);
        if (!z2) {
            i2 = 0;
        }
        messageObtain.arg1 = i2;
        messageObtain.sendToTarget();
    }

    public static void stopDownload() {
        if (f20936j) {
            return;
        }
        TbsLog.i(LOGTAG, "[TbsDownloader.stopDownload]");
        l lVar = f20933g;
        if (lVar != null) {
            lVar.a();
        }
        Handler handler = f20930d;
        if (handler != null) {
            handler.removeMessages(100);
            f20930d.removeMessages(101);
            f20930d.removeMessages(108);
        }
    }
}
