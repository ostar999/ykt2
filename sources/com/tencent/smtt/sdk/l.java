package com.tencent.smtt.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsLogReport;
import com.tencent.smtt.utils.Apn;
import com.tencent.smtt.utils.FileUtil;
import com.tencent.smtt.utils.TbsLog;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
class l {

    /* renamed from: d, reason: collision with root package name */
    private static int f21212d = 5;

    /* renamed from: e, reason: collision with root package name */
    private static int f21213e = 1;

    /* renamed from: f, reason: collision with root package name */
    private static final String[] f21214f = {"tbs_downloading_com.tencent.mtt", "tbs_downloading_com.tencent.mm", "tbs_downloading_com.tencent.mobileqq", "tbs_downloading_com.tencent.tbs", "tbs_downloading_com.qzone"};
    private Handler A;
    private boolean D;

    /* renamed from: g, reason: collision with root package name */
    private Context f21218g;

    /* renamed from: h, reason: collision with root package name */
    private String f21219h;

    /* renamed from: i, reason: collision with root package name */
    private String f21220i;

    /* renamed from: j, reason: collision with root package name */
    private String f21221j;

    /* renamed from: k, reason: collision with root package name */
    private File f21222k;

    /* renamed from: l, reason: collision with root package name */
    private File f21223l;

    /* renamed from: m, reason: collision with root package name */
    private long f21224m;

    /* renamed from: p, reason: collision with root package name */
    private boolean f21227p;

    /* renamed from: q, reason: collision with root package name */
    private int f21228q;

    /* renamed from: r, reason: collision with root package name */
    private int f21229r;

    /* renamed from: s, reason: collision with root package name */
    private boolean f21230s;

    /* renamed from: t, reason: collision with root package name */
    private boolean f21231t;

    /* renamed from: u, reason: collision with root package name */
    private HttpURLConnection f21232u;

    /* renamed from: v, reason: collision with root package name */
    private String f21233v;

    /* renamed from: w, reason: collision with root package name */
    private final TbsLogReport.TbsLogInfo f21234w;

    /* renamed from: x, reason: collision with root package name */
    private String f21235x;

    /* renamed from: y, reason: collision with root package name */
    private int f21236y;

    /* renamed from: z, reason: collision with root package name */
    private boolean f21237z;

    /* renamed from: c, reason: collision with root package name */
    private boolean f21217c = false;

    /* renamed from: n, reason: collision with root package name */
    private int f21225n = 30000;

    /* renamed from: o, reason: collision with root package name */
    private int f21226o = 20000;
    private boolean B = false;
    private int C = f21212d;

    /* renamed from: a, reason: collision with root package name */
    String[] f21215a = null;

    /* renamed from: b, reason: collision with root package name */
    int f21216b = 0;

    public l(Context context) throws NullPointerException {
        Context applicationContext = context.getApplicationContext();
        this.f21218g = applicationContext;
        this.f21234w = TbsLogReport.getInstance(applicationContext).tbsLogInfo();
        this.f21233v = "tbs_downloading_" + this.f21218g.getPackageName();
        o.a();
        File fileR = o.r(this.f21218g);
        this.f21222k = fileR;
        if (fileR == null) {
            throw new NullPointerException("TbsCorePrivateDir is null!");
        }
        f();
        this.f21235x = null;
        this.f21236y = -1;
    }

    private long a(long j2, long j3) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.f21234w.setDownConsumeTime(jCurrentTimeMillis - j2);
        this.f21234w.setDownloadSize(j3);
        return jCurrentTimeMillis;
    }

    private static File a(Context context, int i2) {
        File file = new File(FileUtil.a(context, i2));
        if (file.exists() && file.isDirectory()) {
            if (new File(file, TbsDownloader.getOverSea(context) ? "x5.oversea.tbs.org" : TbsDownloader.getBackupFileName(false)).exists()) {
                return file;
            }
        }
        return null;
    }

    private String a(Throwable th) {
        String stackTraceString = Log.getStackTraceString(th);
        return stackTraceString.length() > 1024 ? stackTraceString.substring(0, 1024) : stackTraceString;
    }

    private void a(int i2, String str, boolean z2) {
        if (z2 || this.f21228q > this.C) {
            this.f21234w.setErrorCode(i2);
            this.f21234w.setFailDetail(str);
        }
    }

    private void a(long j2) throws InterruptedException {
        this.f21228q++;
        if (j2 <= 0) {
            try {
                j2 = m();
            } catch (Exception unused) {
                return;
            }
        }
        Thread.sleep(j2);
    }

    public static void a(Context context) {
        try {
            TbsLog.i(TbsDownloader.LOGTAG, "clearDecoupleDirOld #00");
            File fileA = o.a().a(context, context.getDir("tbs_64", 0));
            FileUtil.b(fileA);
            if (fileA != null) {
                TbsLog.i(TbsDownloader.LOGTAG, "clearDecoupleDirOld dir is " + fileA.getAbsolutePath());
            }
            File fileA2 = o.a().a(context, context.getDir("tbs", 0));
            FileUtil.b(fileA2);
            if (fileA2 != null) {
                TbsLog.i(TbsDownloader.LOGTAG, "clearDecoupleDirOld dir is " + fileA2.getAbsolutePath());
            }
        } catch (Throwable th) {
            TbsLog.i(TbsDownloader.LOGTAG, "clearDecoupleDirOld stack is " + Log.getStackTraceString(th));
        }
    }

    private void a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void a(File file, Context context) {
        synchronized (com.tencent.smtt.utils.a.class) {
            if (file != null) {
                if (file.exists()) {
                    if (TbsShareManager.isThirdPartyApp(context)) {
                        return;
                    }
                    try {
                        File fileC = c(context);
                        if (fileC != null) {
                            File file2 = new File(fileC, TbsDownloader.getOverSea(context) ? "x5.oversea.tbs.org" : TbsDownloader.getBackupFileName(false));
                            file2.delete();
                            FileUtil.b(file, file2);
                            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.backupTbsApk]tbsApk is " + file.getAbsolutePath());
                            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.backupTbsApk]backUpApk is " + file2.getAbsolutePath());
                            boolean zContains = file2.getName().contains("tbs.org");
                            boolean zContains2 = file2.getName().contains("x5.tbs.decouple");
                            if (zContains2 || zContains) {
                                File[] fileArrListFiles = fileC.listFiles();
                                Pattern patternCompile = Pattern.compile(com.tencent.smtt.utils.a.a(zContains2) + "(.*)");
                                int length = fileArrListFiles.length;
                                for (int i2 = 0; i2 < length; i2++) {
                                    File file3 = fileArrListFiles[i2];
                                    if (patternCompile.matcher(file3.getName()).find() && file3.isFile() && file3.exists()) {
                                        file3.delete();
                                    }
                                }
                                File file4 = new File(fileC, com.tencent.smtt.utils.a.a(zContains2) + StrPool.DOT + TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0));
                                if (file4.exists()) {
                                    TbsLog.e(TbsDownloader.LOGTAG, "[TbsApkDownloader.backupTbsApk]delete bacup config file error ");
                                    return;
                                }
                                file4.createNewFile();
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        }
    }

    private void a(String str) throws Exception {
        URL url = new URL(str);
        HttpURLConnection httpURLConnection = this.f21232u;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Throwable th) {
                TbsLog.e(TbsDownloader.LOGTAG, "[initHttpRequest] mHttpRequest.disconnect() Throwable:" + th.toString());
            }
        }
        HttpURLConnection httpURLConnection2 = (HttpURLConnection) url.openConnection();
        this.f21232u = httpURLConnection2;
        httpURLConnection2.setRequestProperty("User-Agent", TbsDownloader.b(this.f21218g));
        this.f21232u.setRequestProperty("Accept-Encoding", "identity");
        this.f21232u.setRequestMethod("GET");
        this.f21232u.setInstanceFollowRedirects(false);
        this.f21232u.setConnectTimeout(this.f21226o);
        this.f21232u.setReadTimeout(this.f21225n);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a6, code lost:
    
        if (r10 != r8) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(boolean r13, boolean r14, java.io.File r15) {
        /*
            Method dump skipped, instructions count: 527
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.l.a(boolean, boolean, java.io.File):boolean");
    }

    public static void b(Context context) {
        if (TbsShareManager.isThirdPartyApp(context) || com.tencent.smtt.utils.s.f(context)) {
            try {
                TbsLog.i(TbsDownloader.LOGTAG, "clearOldBackup #00");
                if (FileUtil.a(context)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(Environment.getExternalStorageDirectory());
                    String str = File.separator;
                    sb.append(str);
                    String string = sb.toString();
                    if (!string.equals("")) {
                        string = string + SocializeProtocolConstants.PROTOCOL_KEY_TENCENT + str + "tbs" + str + "backup" + str + context.getApplicationInfo().packageName;
                    }
                    File file = new File(string);
                    FileUtil.b(file);
                    TbsLog.i(TbsDownloader.LOGTAG, "clearOldBackup dir is " + file.getAbsolutePath());
                }
            } catch (Throwable th) {
                TbsLog.i(TbsDownloader.LOGTAG, "clearOldBackup stack is " + Log.getStackTraceString(th));
            }
        }
    }

    private boolean b(boolean z2, boolean z3) {
        return a(z2, z3, (File) null);
    }

    @TargetApi(8)
    public static File c(Context context) {
        try {
            File file = new File(FileUtil.a(context, 4));
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
            return file;
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLog.e(TbsDownloader.LOGTAG, "[TbsApkDownloader.backupApkPath] Exception is " + e2.getMessage());
            return null;
        }
    }

    @TargetApi(8)
    public static File d(Context context) {
        try {
            File fileA = a(context, 4);
            if (fileA == null) {
                fileA = a(context, 3);
            }
            if (fileA == null) {
                fileA = a(context, 2);
            }
            return fileA == null ? a(context, 1) : fileA;
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLog.e(TbsDownloader.LOGTAG, "[TbsApkDownloader.backupApkPath] Exception is " + e2.getMessage());
            return null;
        }
    }

    private void d(boolean z2) throws Throwable {
        Bundle bundleA;
        String str;
        File file;
        com.tencent.smtt.utils.s.a(this.f21218g);
        TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(this.f21218g);
        Map<String, Object> map = tbsDownloadConfig.mSyncMap;
        Boolean bool = Boolean.FALSE;
        map.put(TbsDownloadConfig.TbsConfigKey.KEY_FULL_PACKAGE, bool);
        tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD, bool);
        tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOAD_INTERRUPT_CODE_REASON, -123);
        tbsDownloadConfig.commit();
        this.f21234w.f20963a = 100;
        int i2 = tbsDownloadConfig.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_RESPONSECODE, 0);
        boolean zA = TbsDownloader.a(this.f21218g);
        try {
            int i3 = tbsDownloadConfig.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_CPUTYPE_OTHER_STABLE_CORE, 0);
            TbsLog.i(TbsDownloader.LOGTAG, "downloadSuccess #1  cpuTypeForOtherStableCore is " + i3 + " isDownloadDecoupleCore is " + zA);
            StringBuilder sb = new StringBuilder();
            sb.append("downloadSuccess #1  responseCode is ");
            sb.append(i2);
            TbsLog.i(TbsDownloader.LOGTAG, sb.toString());
            if (zA && com.tencent.smtt.utils.s.b(this.f21218g) && i2 != 3) {
                File[] fileArrListFiles = this.f21223l.listFiles();
                Pattern patternCompile = Pattern.compile(com.tencent.smtt.utils.a.a(false, i3));
                for (File file2 : fileArrListFiles) {
                    if (patternCompile.matcher(file2.getName()).find() && file2.isFile() && file2.exists()) {
                        file2.delete();
                    }
                }
                File file3 = new File(this.f21223l, com.tencent.smtt.utils.a.a(false, i3) + StrPool.DOT + tbsDownloadConfig.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0));
                if (!file3.exists()) {
                    file3.createNewFile();
                }
                try {
                    boolean zRenameTo = new File(this.f21223l, TbsDownloader.getBackupFileName(false) + StrPool.DOT + "tmp").renameTo(new File(this.f21223l, TbsDownloader.getBackupFileName(false, i3)));
                    String str2 = "none";
                    if (i3 == 0) {
                        if (com.tencent.smtt.utils.b.c()) {
                            str2 = "stable_64_tpatch_fail";
                        }
                        str2 = "stable_32_tpatch_fail";
                    } else if (i3 == 32) {
                        str2 = "stable_32_tpatch_fail";
                    } else if (i3 == 64) {
                        str2 = "stable_64_tpatch_fail";
                    }
                    m.a(this.f21218g).a(str2, 0);
                    TbsLog.i(TbsDownloader.LOGTAG, "downloadSuccess setStatus " + str2 + " is 0");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("downloadSuccess renameResult is ");
                    sb2.append(zRenameTo);
                    TbsLog.i(TbsDownloader.LOGTAG, sb2.toString());
                } catch (Throwable th) {
                    TbsLog.i(TbsDownloader.LOGTAG, "downloadSuccess stack is " + Log.getStackTraceString(th));
                }
            }
        } catch (Throwable th2) {
            TbsLog.i(TbsDownloader.LOGTAG, "downloadSuccess stack is " + Log.getStackTraceString(th2));
        }
        if (i2 == 5 || i2 == 3) {
            bundleA = a(i2, zA);
            if (bundleA == null) {
                str = "downloadSuccess RESPONSECODE_TPATCH bundle is null ";
                TbsLog.i(TbsDownloader.LOGTAG, str);
            } else {
                TbsLog.i(TbsDownloader.LOGTAG, "downloadSuccess RESPONSECODE_TPATCH bundle is " + bundleA);
                o.a().b(this.f21218g, bundleA);
            }
        } else if (i2 == 3 || i2 > 10000) {
            File fileC = c(this.f21218g);
            if (fileC != null) {
                bundleA = a(i2, fileC, zA);
                o.a().b(this.f21218g, bundleA);
            } else {
                b();
                tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD, Boolean.TRUE);
                tbsDownloadConfig.commit();
            }
        } else {
            int i4 = tbsDownloadConfig.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0);
            o.a().a(this.f21218g, new File(this.f21222k, "x5.tbs").getAbsolutePath(), i4);
            if (com.tencent.smtt.utils.s.b(this.f21218g)) {
                int i5 = TbsDownloadConfig.getInstance(this.f21218g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, 0);
                TbsLog.i(TbsDownloader.LOGTAG, "downloadSuccess tbsCorVer is " + i4 + " stableCoreVersionServer is " + i5);
                if (i4 == i5) {
                    TbsLog.i(TbsDownloader.LOGTAG, "downloadSuccess backup");
                    file = new File(this.f21222k, "x5.tbs");
                } else {
                    str = "downloadSuccess not backup";
                    TbsLog.i(TbsDownloader.LOGTAG, str);
                }
            } else {
                file = new File(this.f21222k, "x5.tbs");
            }
            a(file, this.f21218g);
        }
        a(this.f21218g);
        b(this.f21218g);
    }

    public static void e(Context context) {
        try {
            o.a();
            File fileR = o.r(context);
            new File(fileR, "x5.tbs").delete();
            new File(fileR, "x5.tbs.temp").delete();
            File fileC = c(context);
            if (fileC != null) {
                new File(fileC, TbsDownloader.getBackupFileName(false)).delete();
                new File(fileC, "x5.oversea.tbs.org").delete();
                File[] fileArrListFiles = fileC.listFiles();
                Pattern patternCompile = Pattern.compile(com.tencent.smtt.utils.a.a(true) + "(.*)");
                int length = fileArrListFiles.length;
                for (int i2 = 0; i2 < length; i2++) {
                    File file = fileArrListFiles[i2];
                    if (patternCompile.matcher(file.getName()).find() && file.isFile() && file.exists()) {
                        file.delete();
                    }
                }
                Pattern patternCompile2 = Pattern.compile(com.tencent.smtt.utils.a.a(false) + "(.*)");
                for (File file2 : fileArrListFiles) {
                    if (patternCompile2.matcher(file2.getName()).find() && file2.isFile() && file2.exists()) {
                        file2.delete();
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    private boolean e(boolean z2) {
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.deleteFile] isApk=" + z2);
        File file = z2 ? new File(this.f21222k, "x5.tbs") : new File(this.f21222k, "x5.tbs.temp");
        if (!file.exists()) {
            return true;
        }
        FileUtil.b(file);
        return true;
    }

    private void f() {
        this.f21228q = 0;
        this.f21229r = 0;
        this.f21224m = -1L;
        this.f21221j = null;
        this.f21227p = false;
        this.f21230s = false;
        this.f21231t = false;
        this.f21237z = false;
    }

    private void g() {
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.closeHttpRequest]");
        HttpURLConnection httpURLConnection = this.f21232u;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Throwable th) {
                TbsLog.e(TbsDownloader.LOGTAG, "[closeHttpRequest] mHttpRequest.disconnect() Throwable:" + th.toString());
            }
            this.f21232u = null;
        }
        int i2 = this.f21234w.f20963a;
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.closeHttpRequest] download finish code: " + i2);
        if (!this.f21230s && this.f21237z) {
            h();
        } else if (!this.f21217c) {
            TbsDownloader.f20927a = false;
        }
        QbSdk.f20830n.onDownloadFinish(i2);
    }

    private void h() {
        this.f21234w.setEventTime(System.currentTimeMillis());
        String apnInfo = Apn.getApnInfo(this.f21218g);
        if (apnInfo == null) {
            apnInfo = "";
        }
        int apnType = Apn.getApnType(this.f21218g);
        this.f21234w.setApn(apnInfo);
        this.f21234w.setNetworkType(apnType);
        if (apnType != this.f21236y || !apnInfo.equals(this.f21235x)) {
            this.f21234w.setNetworkChange(0);
        }
        TbsLogReport.TbsLogInfo tbsLogInfo = this.f21234w;
        int i2 = tbsLogInfo.f20963a;
        if ((i2 == 0 || i2 == 107) && tbsLogInfo.getDownFinalFlag() == 0 && (!Apn.isNetworkAvailable(this.f21218g) || !l())) {
            a(101, (String) null, true);
        }
        TbsLogReport.getInstance(this.f21218g).eventReport(TbsLogReport.EventType.TYPE_CDN_DOWNLOAD_STAT, this.f21234w);
        this.f21234w.resetArgs();
    }

    private void i() {
        int apnType = Apn.getApnType(this.f21218g);
        String apnInfo = Apn.getApnInfo(this.f21218g);
        String str = this.f21235x;
        if (str != null || this.f21236y != -1) {
            if (apnType == this.f21236y && apnInfo.equals(str)) {
                return;
            } else {
                this.f21234w.setNetworkChange(0);
            }
        }
        this.f21235x = apnInfo;
        this.f21236y = apnType;
    }

    private boolean j() {
        return new File(this.f21222k, "x5.tbs.temp").exists();
    }

    private long k() {
        File file = new File(this.f21222k, "x5.tbs.temp");
        if (file.exists()) {
            return file.length();
        }
        return 0L;
    }

    private boolean l() throws IOException {
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        Throwable th;
        InputStream inputStream;
        boolean z2 = false;
        try {
            inputStream = Runtime.getRuntime().exec("ping www.qq.com").getInputStream();
            try {
                inputStreamReader = new InputStreamReader(inputStream);
                try {
                    bufferedReader = new BufferedReader(inputStreamReader);
                    int i2 = 0;
                    do {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            if (line.contains("TTL") || line.contains(RemoteMessageConst.TTL)) {
                                z2 = true;
                                break;
                            }
                            i2++;
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                th.printStackTrace();
                                return z2;
                            } finally {
                                a(inputStream);
                                a(inputStreamReader);
                                a(bufferedReader);
                            }
                        }
                    } while (i2 < 5);
                } catch (Throwable th3) {
                    bufferedReader = null;
                    th = th3;
                }
            } catch (Throwable th4) {
                bufferedReader = null;
                th = th4;
                inputStreamReader = null;
            }
        } catch (Throwable th5) {
            inputStreamReader = null;
            bufferedReader = null;
            th = th5;
            inputStream = null;
        }
        return z2;
    }

    private long m() {
        int i2 = this.f21228q;
        return (i2 == 1 || i2 == 2) ? i2 * SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US : (i2 == 3 || i2 == 4) ? 100000L : 200000L;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean n() {
        /*
            r8 = this;
            android.content.Context r0 = r8.f21218g
            int r0 = com.tencent.smtt.utils.Apn.getApnType(r0)
            r1 = 3
            r2 = 1
            r3 = 0
            if (r0 != r1) goto Ld
            r0 = r2
            goto Le
        Ld:
            r0 = r3
        Le:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "[TbsApkDwonloader.detectWifiNetworkAvailable] isWifi="
            r1.append(r4)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r4 = "TbsDownload"
            com.tencent.smtt.utils.TbsLog.i(r4, r1)
            r1 = 0
            if (r0 == 0) goto L7c
            java.net.URL r0 = new java.net.URL     // Catch: java.lang.Throwable -> L6a
            java.lang.String r5 = "https://pms.mb.qq.com/rsp204"
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L6a
            java.net.URLConnection r0 = r0.openConnection()     // Catch: java.lang.Throwable -> L6a
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch: java.lang.Throwable -> L6a
            r0.setInstanceFollowRedirects(r3)     // Catch: java.lang.Throwable -> L68
            r5 = 10000(0x2710, float:1.4013E-41)
            r0.setConnectTimeout(r5)     // Catch: java.lang.Throwable -> L68
            r0.setReadTimeout(r5)     // Catch: java.lang.Throwable -> L68
            r0.setUseCaches(r3)     // Catch: java.lang.Throwable -> L68
            r0.getInputStream()     // Catch: java.lang.Throwable -> L68
            int r5 = r0.getResponseCode()     // Catch: java.lang.Throwable -> L68
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L68
            r6.<init>()     // Catch: java.lang.Throwable -> L68
            java.lang.String r7 = "[TbsApkDwonloader.detectWifiNetworkAvailable] responseCode="
            r6.append(r7)     // Catch: java.lang.Throwable -> L68
            r6.append(r5)     // Catch: java.lang.Throwable -> L68
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L68
            com.tencent.smtt.utils.TbsLog.i(r4, r6)     // Catch: java.lang.Throwable -> L68
            r4 = 204(0xcc, float:2.86E-43)
            if (r5 != r4) goto L63
            r4 = r2
            goto L64
        L63:
            r4 = r3
        L64:
            r0.disconnect()     // Catch: java.lang.Exception -> L7d
            goto L7d
        L68:
            r4 = move-exception
            goto L6c
        L6a:
            r4 = move-exception
            r0 = r1
        L6c:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L75
            if (r0 == 0) goto L7c
            r0.disconnect()     // Catch: java.lang.Exception -> L7c
            goto L7c
        L75:
            r1 = move-exception
            if (r0 == 0) goto L7b
            r0.disconnect()     // Catch: java.lang.Exception -> L7b
        L7b:
            throw r1
        L7c:
            r4 = r3
        L7d:
            if (r4 == 0) goto L82
            r8.B = r2
            goto L94
        L82:
            r8.B = r3
            android.os.Handler r0 = r8.A
            r2 = 150(0x96, float:2.1E-43)
            android.os.Message r0 = r0.obtainMessage(r2, r1)
            android.os.Handler r1 = r8.A
            r2 = 120000(0x1d4c0, double:5.9288E-319)
            r1.sendMessageDelayed(r0, r2)
        L94:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.l.n():boolean");
    }

    public Bundle a(int i2, File file, boolean z2) {
        File file2;
        if (z2) {
            file2 = new File(file, TbsDownloader.getBackupFileName(true));
        } else {
            file2 = new File(file, TbsDownloader.getOverSea(this.f21218g) ? "x5.oversea.tbs.org" : TbsDownloader.getBackupFileName(false));
        }
        int iA = com.tencent.smtt.utils.a.a(this.f21218g, file2);
        File file3 = new File(this.f21222k, "x5.tbs");
        String absolutePath = file3.exists() ? file3.getAbsolutePath() : null;
        int i3 = TbsDownloadConfig.getInstance(this.f21218g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0);
        Bundle bundle = new Bundle();
        bundle.putInt("operation", i2);
        bundle.putInt("old_core_ver", iA);
        bundle.putInt("new_core_ver", i3);
        bundle.putString("old_apk_location", file2.getAbsolutePath());
        bundle.putString("new_apk_location", absolutePath);
        bundle.putString("diff_file_location", absolutePath);
        return bundle;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.os.Bundle a(int r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 287
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.l.a(int, boolean):android.os.Bundle");
    }

    public void a() {
        this.f21230s = true;
        if (TbsShareManager.isThirdPartyApp(this.f21218g)) {
            TbsLogReport.TbsLogInfo tbsLogInfo = TbsLogReport.getInstance(this.f21218g).tbsLogInfo();
            tbsLogInfo.setErrorCode(-309);
            tbsLogInfo.setFailDetail(new Exception());
            TbsLogReport.getInstance(this.f21218g).eventReport(TbsLogReport.EventType.TYPE_DOWNLOAD, tbsLogInfo);
        }
    }

    public void a(int i2) {
        if (o.a().s(this.f21218g)) {
            o.a().c();
            try {
                File file = new File(this.f21222k, "x5.tbs");
                int iA = com.tencent.smtt.utils.a.a(this.f21218g, file);
                if (-1 == iA || (i2 > 0 && i2 == iA)) {
                    FileUtil.b(file);
                }
            } catch (Exception unused) {
            }
        }
    }

    public void a(boolean z2) {
        a(z2, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:132:0x03c2, code lost:
    
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).mSyncMap.put(r13, java.lang.Long.valueOf(r26));
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).commit();
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x03da, code lost:
    
        r12 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x04b0, code lost:
    
        if (r41 == false) goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x04b2, code lost:
    
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).mSyncMap.put(r13, java.lang.Long.valueOf(r26));
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x055c, code lost:
    
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).setDownloadInterruptCode(-315);
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x0567, code lost:
    
        if (r41 != false) goto L485;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x061d, code lost:
    
        a(113, "tbsApkFileSize=" + r6 + "  but contentLength=" + r40.f21224m, true);
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).setDownloadInterruptCode(-310);
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x064b, code lost:
    
        a(101, "WifiNetworkUnAvailable", true);
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).setDownloadInterruptCode(-304);
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x065e, code lost:
    
        if (r41 != false) goto L485;
     */
    /* JADX WARN: Code restructure failed: missing block: B:250:0x0660, code lost:
    
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).mSyncMap.put(r13, java.lang.Long.valueOf(r26));
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x073e, code lost:
    
        com.tencent.smtt.utils.TbsLog.i(com.tencent.smtt.sdk.TbsDownloader.LOGTAG, "STEP 1/2 begin downloading...Canceled!", true);
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).setDownloadInterruptCode(-309);
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x074f, code lost:
    
        r28 = r4;
        r36 = r5;
        r31 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:301:0x0778, code lost:
    
        if (r40.f21215a == null) goto L309;
     */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x077f, code lost:
    
        if (b(true, r12) != false) goto L309;
     */
    /* JADX WARN: Code restructure failed: missing block: B:304:0x0781, code lost:
    
        if (r41 != false) goto L308;
     */
    /* JADX WARN: Code restructure failed: missing block: B:306:0x0787, code lost:
    
        if (b(false) == false) goto L308;
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:0x0789, code lost:
    
        r28 = r4;
        r36 = r5;
        r5 = r26;
        r17 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:308:0x0793, code lost:
    
        r40.f21231t = true;
        r28 = r4;
        r36 = r5;
        r5 = r26;
        r16 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:309:0x079f, code lost:
    
        r40.f21231t = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:310:0x07a4, code lost:
    
        if (r40.f21215a == null) goto L312;
     */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x07a6, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:312:0x07a8, code lost:
    
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).setDownloadInterruptCode(-311);
     */
    /* JADX WARN: Code restructure failed: missing block: B:313:0x07b3, code lost:
    
        r28 = r4;
        r36 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:327:0x07df, code lost:
    
        com.tencent.smtt.utils.TbsLog.i(com.tencent.smtt.sdk.TbsDownloader.LOGTAG, "STEP 1/2 begin downloading...failed because you exceeded max flow!", true);
        r6 = new java.lang.StringBuilder();
        r6.append("downloadFlow=");
        r6.append(r7);
        r6.append(" downloadMaxflow=");
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x07f4, code lost:
    
        r9 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:329:0x07f6, code lost:
    
        r6.append(r9);
        a(112, r6.toString(), true);
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).setDownloadInterruptCode(-307);
     */
    /* JADX WARN: Code restructure failed: missing block: B:330:0x080e, code lost:
    
        r21 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:331:0x0811, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:332:0x0812, code lost:
    
        r14 = r4;
        r13 = r5;
        r7 = r0;
        r9 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:333:0x081d, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:334:0x081e, code lost:
    
        r14 = r4;
        r13 = r5;
        r5 = r7;
        r21 = r9;
        r4 = r31;
        r9 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x0870, code lost:
    
        r28 = r4;
        r36 = r5;
        r5 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:341:0x087a, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:342:0x087b, code lost:
    
        r9 = r3;
        r14 = r4;
        r13 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:343:0x0883, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:344:0x0884, code lost:
    
        r9 = r3;
        r14 = r4;
        r13 = r5;
        r5 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:368:0x0914, code lost:
    
        a();
        com.tencent.smtt.utils.TbsLog.i(com.tencent.smtt.sdk.TbsDownloader.LOGTAG, "Download is paused due to NOT_WIFI error!", false);
        a(111, null, true);
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).setDownloadInterruptCode(-304);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x014c, code lost:
    
        r2.setDownloadInterruptCode(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x014f, code lost:
    
        r12 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x0a1b, code lost:
    
        a(r9);
        a(r13);
        a(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:422:0x0a26, code lost:
    
        if (r40.f21231t != false) goto L424;
     */
    /* JADX WARN: Code restructure failed: missing block: B:423:0x0a28, code lost:
    
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).setDownloadInterruptCode(-319);
     */
    /* JADX WARN: Code restructure failed: missing block: B:424:0x0a33, code lost:
    
        if (r41 != false) goto L485;
     */
    /* JADX WARN: Code restructure failed: missing block: B:425:0x0a35, code lost:
    
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).mSyncMap.put(r4, java.lang.Long.valueOf(r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:440:0x0ab5, code lost:
    
        if (r41 == false) goto L425;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01e7, code lost:
    
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).mSyncMap.put(r13, java.lang.Long.valueOf(r5));
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r40.f21218g).commit();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:127:0x03af  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x06a0  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0707 A[Catch: all -> 0x09ef, IOException -> 0x09f9, TRY_ENTER, TryCatch #46 {IOException -> 0x09f9, all -> 0x09ef, blocks: (B:270:0x06b9, B:284:0x0711, B:283:0x0707), top: B:558:0x06b9 }] */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0a5f A[Catch: all -> 0x0b3c, TryCatch #14 {all -> 0x0b3c, blocks: (B:430:0x0a58, B:432:0x0a5f, B:436:0x0a67, B:438:0x0a6f, B:443:0x0abb, B:445:0x0ac4, B:446:0x0acf, B:451:0x0b00), top: B:539:0x0a58 }] */
    /* JADX WARN: Removed duplicated region for block: B:454:0x0b1c  */
    /* JADX WARN: Removed duplicated region for block: B:474:0x0b60 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:487:0x0bd5  */
    /* JADX WARN: Removed duplicated region for block: B:512:0x0c84  */
    /* JADX WARN: Removed duplicated region for block: B:519:0x0c9b  */
    /* JADX WARN: Removed duplicated region for block: B:592:0x03b5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:615:0x0afd A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:617:0x0bb3 A[EDGE_INSN: B:617:0x0bb3->B:482:0x0bb3 BREAK  A[LOOP:0: B:37:0x013e->B:618:0x013e], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11, types: [int] */
    /* JADX WARN: Type inference failed for: r2v63 */
    /* JADX WARN: Type inference failed for: r2v64 */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo] */
    /* JADX WARN: Type inference failed for: r40v0, types: [com.tencent.smtt.sdk.l] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(boolean r41, boolean r42) {
        /*
            Method dump skipped, instructions count: 3307
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.l.a(boolean, boolean):void");
    }

    public void b() {
        a();
        e(false);
        e(true);
    }

    public boolean b(boolean z2) {
        String[] strArr;
        int i2;
        if ((z2 && !n() && (!QbSdk.canDownloadWithoutWifi() || !Apn.isNetworkAvailable(this.f21218g))) || (strArr = this.f21215a) == null || (i2 = this.f21216b) < 0 || i2 >= strArr.length) {
            return false;
        }
        this.f21216b = i2 + 1;
        this.f21221j = strArr[i2];
        this.f21228q = 0;
        this.f21229r = 0;
        this.f21224m = -1L;
        this.f21227p = false;
        this.f21230s = false;
        this.f21231t = false;
        this.f21237z = false;
        return true;
    }

    public int c(boolean z2) {
        File fileC = c(this.f21218g);
        if (z2) {
            if (fileC == null) {
                return 0;
            }
            return com.tencent.smtt.utils.a.a(this.f21218g, new File(fileC, TbsDownloader.getBackupFileName(true)));
        }
        if (fileC == null) {
            return 0;
        }
        return com.tencent.smtt.utils.a.a(this.f21218g, new File(fileC, TbsDownloader.getOverSea(this.f21218g) ? "x5.oversea.tbs.org" : TbsDownloader.getBackupFileName(false)));
    }

    public boolean c() {
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.isDownloadForeground] mIsDownloadForeground=" + this.D);
        return this.D;
    }

    public void d() {
        TbsLog.i(TbsDownloader.LOGTAG, "pauseDownload,isPause=" + this.f21217c + "isDownloading=" + TbsDownloader.isDownloading());
        if (this.f21217c || !TbsDownloader.isDownloading()) {
            return;
        }
        a();
        this.f21217c = true;
        this.f21237z = false;
    }

    public void e() {
        TbsLog.i(TbsDownloader.LOGTAG, "resumeDownload,isPause=" + this.f21217c + "isDownloading=" + TbsDownloader.isDownloading());
        if (this.f21217c && TbsDownloader.isDownloading()) {
            this.f21217c = false;
            a(false);
        }
    }
}
