package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.tencent.connect.common.Constants;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.utils.FileUtil;
import com.tencent.smtt.utils.TbsLog;
import dalvik.system.DexClassLoader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
class o {

    /* renamed from: c, reason: collision with root package name */
    private static o f21243c;

    /* renamed from: e, reason: collision with root package name */
    private FileLock f21252e;

    /* renamed from: f, reason: collision with root package name */
    private FileOutputStream f21253f;

    /* renamed from: h, reason: collision with root package name */
    private static final ReentrantLock f21244h = new ReentrantLock();

    /* renamed from: i, reason: collision with root package name */
    private static final Lock f21245i = new ReentrantLock();

    /* renamed from: k, reason: collision with root package name */
    private static FileLock f21246k = null;

    /* renamed from: l, reason: collision with root package name */
    private static final ThreadLocal<Integer> f21247l = new ThreadLocal<Integer>() { // from class: com.tencent.smtt.sdk.o.1
        @Override // java.lang.ThreadLocal
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Integer initialValue() {
            return 0;
        }
    };

    /* renamed from: m, reason: collision with root package name */
    private static Handler f21248m = null;

    /* renamed from: a, reason: collision with root package name */
    static boolean f21241a = false;

    /* renamed from: b, reason: collision with root package name */
    static final FileFilter f21242b = new FileFilter() { // from class: com.tencent.smtt.sdk.o.2
        @Override // java.io.FileFilter
        public boolean accept(File file) {
            String name = file.getName();
            if (name == null || name.endsWith(".jar_is_first_load_dex_flag_file")) {
                return false;
            }
            int i2 = Build.VERSION.SDK_INT;
            if (name.endsWith(".dex")) {
                return false;
            }
            if (i2 < 26 || !name.endsWith(".prof")) {
                return i2 < 26 || !name.equals("oat");
            }
            return false;
        }
    };

    /* renamed from: n, reason: collision with root package name */
    private static int f21249n = 0;

    /* renamed from: o, reason: collision with root package name */
    private static boolean f21250o = false;

    /* renamed from: d, reason: collision with root package name */
    private int f21251d = 0;

    /* renamed from: g, reason: collision with root package name */
    private boolean f21254g = false;

    /* renamed from: j, reason: collision with root package name */
    private boolean f21255j = false;

    /* renamed from: p, reason: collision with root package name */
    private int f21256p = -1;

    private o() {
        if (f21248m == null) {
            f21248m = new Handler(n.a().getLooper()) { // from class: com.tencent.smtt.sdk.o.3
                @Override // android.os.Handler
                public void handleMessage(Message message) throws Throwable {
                    QbSdk.setTBSInstallingStatus(true);
                    int i2 = message.what;
                    if (i2 == 1) {
                        TbsLog.i("TbsInstaller", "TbsInstaller--handleMessage--MSG_INSTALL_TBS_CORE");
                        Object[] objArr = (Object[]) message.obj;
                        o.this.b((Context) objArr[0], (String) objArr[1], ((Integer) objArr[2]).intValue());
                        return;
                    }
                    if (i2 == 2) {
                        TbsLog.i("TbsInstaller", "TbsInstaller--handleMessage--MSG_COPY_TBS_CORE");
                        Object[] objArr2 = (Object[]) message.obj;
                        o.this.a((Context) objArr2[0], (Context) objArr2[1], ((Integer) objArr2[2]).intValue());
                    } else if (i2 == 3) {
                        TbsLog.i("TbsInstaller", "TbsInstaller--handleMessage--MSG_INSTALL_TBS_CORE_EX");
                        Object[] objArr3 = (Object[]) message.obj;
                        o.this.b((Context) objArr3[0], (Bundle) objArr3[1]);
                    } else {
                        if (i2 != 4) {
                            return;
                        }
                        TbsLog.i("TbsInstaller", "TbsInstaller--handleMessage--MSG_UNZIP_TBS_CORE");
                        Object[] objArr4 = (Object[]) message.obj;
                        o.this.a((Context) objArr4[0], (File) objArr4[1], ((Integer) objArr4[2]).intValue());
                        QbSdk.setTBSInstallingStatus(false);
                        super.handleMessage(message);
                    }
                }
            };
        }
    }

    private void A(Context context) throws IOException {
        TbsLog.i("TbsInstaller", "TbsInstaller--renameTbsTpatchCoreDir");
        File fileE = e(context, 5);
        File fileP = p(context);
        if (fileE == null || fileP == null) {
            return;
        }
        fileE.renameTo(fileP);
        TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead call #09");
        e(context, false);
    }

    private void B(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--clearNewTbsCore");
        File fileE = e(context, 0);
        if (fileE != null) {
            FileUtil.a(fileE, false);
        }
        m.a(context).c(0, 5);
        m.a(context).c(-1);
        QbSdk.a(context, "TbsInstaller::clearNewTbsCore forceSysWebViewInner!");
    }

    public static synchronized o a() {
        if (f21243c == null) {
            synchronized (o.class) {
                if (f21243c == null) {
                    f21243c = new o();
                }
            }
        }
        return f21243c;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x0092 -> B:52:0x00ac). Please report as a decompilation issue!!! */
    private void a(int i2, String str, Context context) throws IOException {
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        new File(str).delete();
        TbsLog.i("TbsInstaller", "Local tbs apk(" + str + ") is deleted!", true);
        File file = new File(QbSdk.getTbsFolderDir(context), "core_unzip_tmp");
        if (file.canRead()) {
            File file2 = new File(file, "tbs.conf");
            Properties properties = new Properties();
            BufferedOutputStream bufferedOutputStream2 = null;
            try {
                try {
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(file2));
                    try {
                        properties.load(bufferedInputStream);
                        bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream = null;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                properties.setProperty("tbs_local_installation", k.a.f27523u);
                properties.store(bufferedOutputStream, (String) null);
                TbsLog.i("TbsInstaller", "TBS_LOCAL_INSTALLATION is set!", true);
                try {
                    bufferedOutputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                bufferedInputStream.close();
            } catch (Throwable th3) {
                th = th3;
                bufferedOutputStream2 = bufferedOutputStream;
                try {
                    th.printStackTrace();
                    if (bufferedOutputStream2 != null) {
                        try {
                            bufferedOutputStream2.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                } finally {
                }
            }
        }
    }

    public static void a(Context context) {
        String str;
        if (u(context)) {
            return;
        }
        if (a(context, "core_unzip_tmp")) {
            TbsCoreLoadStat.getInstance().a(context, 417, new Throwable("TMP_TBS_UNZIP_FOLDER_NAME"));
            str = "TbsInstaller-UploadIfTempCoreExistConfError INFO_TEMP_CORE_EXIST_CONF_ERROR TMP_TBS_UNZIP_FOLDER_NAME";
        } else if (a(context, "core_share_backup_tmp")) {
            TbsCoreLoadStat.getInstance().a(context, 417, new Throwable("TMP_BACKUP_TBSCORE_FOLDER_NAME"));
            str = "TbsInstaller-UploadIfTempCoreExistConfError INFO_TEMP_CORE_EXIST_CONF_ERROR TMP_BACKUP_TBSCORE_FOLDER_NAME";
        } else {
            if (!a(context, "core_copy_tmp")) {
                return;
            }
            TbsCoreLoadStat.getInstance().a(context, 417, new Throwable("TMP_TBS_COPY_FOLDER_NAME"));
            str = "TbsInstaller-UploadIfTempCoreExistConfError INFO_TEMP_CORE_EXIST_CONF_ERROR TMP_TBS_COPY_FOLDER_NAME";
        }
        TbsLog.e("TbsInstaller", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:102:0x02af A[Catch: all -> 0x04d9, Exception -> 0x04dd, TryCatch #15 {Exception -> 0x04dd, all -> 0x04d9, blocks: (B:61:0x0217, B:63:0x0223, B:65:0x022c, B:80:0x0284, B:102:0x02af, B:103:0x02bd, B:105:0x02c0, B:107:0x02cc, B:109:0x02d8, B:111:0x02e5, B:113:0x02eb, B:116:0x02f8, B:119:0x030c, B:121:0x0312, B:122:0x032e, B:125:0x0360, B:128:0x0378, B:131:0x03a3, B:123:0x035b, B:84:0x028a, B:95:0x02a3, B:99:0x02a9), top: B:217:0x0217 }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x03ce A[Catch: Exception -> 0x04fb, all -> 0x0516, TryCatch #9 {Exception -> 0x04fb, blocks: (B:59:0x01e4, B:133:0x03b3, B:135:0x03bc, B:137:0x03c2, B:141:0x03d4, B:143:0x03dd, B:146:0x041b, B:140:0x03ce, B:147:0x041e, B:149:0x042a, B:150:0x0432, B:152:0x0440, B:158:0x0497, B:157:0x047f, B:151:0x0436, B:163:0x04a4, B:168:0x04ad, B:167:0x04aa, B:169:0x04ae, B:175:0x04e5, B:179:0x0501), top: B:208:0x01e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03dd A[Catch: Exception -> 0x04fb, all -> 0x0516, TryCatch #9 {Exception -> 0x04fb, blocks: (B:59:0x01e4, B:133:0x03b3, B:135:0x03bc, B:137:0x03c2, B:141:0x03d4, B:143:0x03dd, B:146:0x041b, B:140:0x03ce, B:147:0x041e, B:149:0x042a, B:150:0x0432, B:152:0x0440, B:158:0x0497, B:157:0x047f, B:151:0x0436, B:163:0x04a4, B:168:0x04ad, B:167:0x04aa, B:169:0x04ae, B:175:0x04e5, B:179:0x0501), top: B:208:0x01e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x041b A[Catch: Exception -> 0x04fb, all -> 0x0516, TryCatch #9 {Exception -> 0x04fb, blocks: (B:59:0x01e4, B:133:0x03b3, B:135:0x03bc, B:137:0x03c2, B:141:0x03d4, B:143:0x03dd, B:146:0x041b, B:140:0x03ce, B:147:0x041e, B:149:0x042a, B:150:0x0432, B:152:0x0440, B:158:0x0497, B:157:0x047f, B:151:0x0436, B:163:0x04a4, B:168:0x04ad, B:167:0x04aa, B:169:0x04ae, B:175:0x04e5, B:179:0x0501), top: B:208:0x01e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x042a A[Catch: Exception -> 0x04fb, all -> 0x0516, TryCatch #9 {Exception -> 0x04fb, blocks: (B:59:0x01e4, B:133:0x03b3, B:135:0x03bc, B:137:0x03c2, B:141:0x03d4, B:143:0x03dd, B:146:0x041b, B:140:0x03ce, B:147:0x041e, B:149:0x042a, B:150:0x0432, B:152:0x0440, B:158:0x0497, B:157:0x047f, B:151:0x0436, B:163:0x04a4, B:168:0x04ad, B:167:0x04aa, B:169:0x04ae, B:175:0x04e5, B:179:0x0501), top: B:208:0x01e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0436 A[Catch: Exception -> 0x04fb, all -> 0x0516, TryCatch #9 {Exception -> 0x04fb, blocks: (B:59:0x01e4, B:133:0x03b3, B:135:0x03bc, B:137:0x03c2, B:141:0x03d4, B:143:0x03dd, B:146:0x041b, B:140:0x03ce, B:147:0x041e, B:149:0x042a, B:150:0x0432, B:152:0x0440, B:158:0x0497, B:157:0x047f, B:151:0x0436, B:163:0x04a4, B:168:0x04ad, B:167:0x04aa, B:169:0x04ae, B:175:0x04e5, B:179:0x0501), top: B:208:0x01e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x02a3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x04a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:227:? A[Catch: Exception -> 0x04fb, all -> 0x0516, SYNTHETIC, TryCatch #9 {Exception -> 0x04fb, blocks: (B:59:0x01e4, B:133:0x03b3, B:135:0x03bc, B:137:0x03c2, B:141:0x03d4, B:143:0x03dd, B:146:0x041b, B:140:0x03ce, B:147:0x041e, B:149:0x042a, B:150:0x0432, B:152:0x0440, B:158:0x0497, B:157:0x047f, B:151:0x0436, B:163:0x04a4, B:168:0x04ad, B:167:0x04aa, B:169:0x04ae, B:175:0x04e5, B:179:0x0501), top: B:208:0x01e0 }] */
    @android.annotation.TargetApi(11)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(android.content.Context r21, android.content.Context r22, int r23) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.o.a(android.content.Context, android.content.Context, int):void");
    }

    private boolean a(Context context, File file, boolean z2) {
        StringBuilder sb;
        TbsDownloadConfig tbsDownloadConfig;
        int i2;
        TbsLog.i("TbsInstaller", "TbsInstaller-unzipTbs start isDecoupleCore is " + z2);
        if (FileUtil.c(file)) {
            try {
                File tbsFolderDir = QbSdk.getTbsFolderDir(context);
                File file2 = z2 ? new File(tbsFolderDir, "core_share_decouple") : new File(tbsFolderDir, "core_unzip_tmp");
                if (file2.exists() && !TbsDownloader.a(context)) {
                    FileUtil.b(file2);
                }
            } catch (Throwable th) {
                TbsLog.e("TbsInstaller", "TbsInstaller-unzipTbs -- delete unzip folder if exists exception" + Log.getStackTraceString(th));
            }
            File fileE = z2 ? e(context, 2) : e(context, 0);
            if (fileE != null) {
                try {
                    try {
                        try {
                            FileUtil.a(fileE);
                            if (z2) {
                                FileUtil.a(fileE, true);
                            }
                            boolean zA = FileUtil.a(file, fileE);
                            if (zA) {
                                zA = a(fileE, context);
                            }
                            if (z2) {
                                for (String str : fileE.list()) {
                                    File file3 = new File(fileE, str);
                                    if (file3.getName().endsWith(".dex")) {
                                        file3.delete();
                                    }
                                }
                                try {
                                    new File(r(context), "x5.tbs").delete();
                                } catch (Exception unused) {
                                }
                            }
                            if (zA) {
                                TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead call #05");
                                e(context, true);
                            } else {
                                FileUtil.b(fileE);
                                TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-522);
                                TbsLog.e("TbsInstaller", "copyFileIfChanged -- delete tmpTbsCoreUnzipDir#1! exist:" + fileE.exists());
                            }
                            return zA;
                        } catch (Exception e2) {
                            TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-523);
                            TbsLogReport.getInstance(context).setInstallErrorCode(207, e2);
                            if (fileE.exists()) {
                                try {
                                    FileUtil.b(fileE);
                                    TbsLog.e("TbsInstaller", "copyFileIfChanged -- delete tmpTbsCoreUnzipDir#2! exist:" + fileE.exists());
                                } catch (Throwable th2) {
                                    th = th2;
                                    sb = new StringBuilder();
                                    sb.append("copyFileIfChanged -- delete tmpTbsCoreUnzipDir#2! exception:");
                                    sb.append(Log.getStackTraceString(th));
                                    TbsLog.e("TbsInstaller", sb.toString());
                                    TbsLog.i("TbsInstaller", "TbsInstaller-unzipTbs done");
                                    com.tencent.smtt.utils.s.a(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
                                    return false;
                                }
                            }
                            TbsLog.i("TbsInstaller", "TbsInstaller-unzipTbs done");
                            com.tencent.smtt.utils.s.a(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
                            return false;
                        }
                    } catch (IOException e3) {
                        TbsDownloadConfig.getInstance(context).setInstallInterruptCode(-523);
                        TbsLogReport.getInstance(context).setInstallErrorCode(206, e3);
                        if (fileE.exists()) {
                            try {
                                FileUtil.b(fileE);
                                TbsLog.e("TbsInstaller", "copyFileIfChanged -- delete tmpTbsCoreUnzipDir#2! exist:" + fileE.exists());
                            } catch (Throwable th3) {
                                th = th3;
                                sb = new StringBuilder();
                                sb.append("copyFileIfChanged -- delete tmpTbsCoreUnzipDir#2! exception:");
                                sb.append(Log.getStackTraceString(th));
                                TbsLog.e("TbsInstaller", sb.toString());
                                TbsLog.i("TbsInstaller", "TbsInstaller-unzipTbs done");
                                com.tencent.smtt.utils.s.a(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
                                return false;
                            }
                        }
                        TbsLog.i("TbsInstaller", "TbsInstaller-unzipTbs done");
                        com.tencent.smtt.utils.s.a(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
                        return false;
                    }
                } finally {
                    TbsLog.i("TbsInstaller", "TbsInstaller-unzipTbs done");
                    com.tencent.smtt.utils.s.a(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
                }
            }
            TbsLogReport.getInstance(context).setInstallErrorCode(205, "tmp unzip dir is null!");
            tbsDownloadConfig = TbsDownloadConfig.getInstance(context);
            i2 = -521;
        } else {
            TbsLogReport.getInstance(context).setInstallErrorCode(204, "apk is invalid!");
            tbsDownloadConfig = TbsDownloadConfig.getInstance(context);
            i2 = -520;
        }
        tbsDownloadConfig.setInstallInterruptCode(i2);
        return false;
    }

    public static boolean a(Context context, String str) {
        StringBuilder sb;
        String str2;
        File file = new File(QbSdk.getTbsFolderDir(context), str);
        if (!file.exists()) {
            sb = new StringBuilder();
            str2 = "#1# ";
        } else {
            if (new File(file, "tbs.conf").exists()) {
                TbsLog.i("TbsInstaller", "isPrepareTbsCore", "#3# " + str);
                return true;
            }
            sb = new StringBuilder();
            str2 = "#2# ";
        }
        sb.append(str2);
        sb.append(str);
        TbsLog.i("TbsInstaller", "isPrepareTbsCore", sb.toString());
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0153 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(java.io.File r13, android.content.Context r14) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.o.a(java.io.File, android.content.Context):boolean");
    }

    public static void b() {
        f21247l.set(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0481 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @android.annotation.TargetApi(11)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void b(android.content.Context r19, java.lang.String r20, int r21) {
        /*
            Method dump skipped, instructions count: 1611
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.o.b(android.content.Context, java.lang.String, int):void");
    }

    private boolean b(Context context, File file) {
        return a(context, file, false);
    }

    private int c(Context context, Bundle bundle) throws Throwable {
        int i2;
        int iA;
        try {
            Bundle bundleA = QbSdk.a(context, bundle);
            TbsLog.i("TbsInstaller", "tpatch finished,ret is" + bundleA);
            int i3 = bundleA.getInt("patch_result");
            if (i3 != 0) {
                String string = bundle.getString("new_apk_location");
                if (!TextUtils.isEmpty(string)) {
                    FileUtil.b(new File(string));
                }
                TbsLogReport.getInstance(context).setInstallErrorCode(i3, "tpatch fail,patch error_code=" + i3);
                return 1;
            }
            String string2 = bundle.getString("new_apk_location");
            int i4 = bundle.getInt("new_core_ver");
            try {
                i2 = bundle.getInt("for_stable_core");
            } catch (Throwable unused) {
                i2 = 0;
            }
            if (i2 == 1) {
                File file = new File(FileUtil.a(context, "com.tencent.mm", 4, true));
                TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(context);
                int i5 = tbsDownloadConfig.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_CPUTYPE_OTHER_STABLE_CORE, 0);
                File[] fileArrListFiles = file.listFiles();
                Pattern patternCompile = Pattern.compile(com.tencent.smtt.utils.a.a(false, i5));
                for (File file2 : fileArrListFiles) {
                    if (patternCompile.matcher(file2.getName()).find() && file2.isFile() && file2.exists()) {
                        file2.delete();
                    }
                }
                File file3 = new File(file, com.tencent.smtt.utils.a.a(false, i5) + StrPool.DOT + tbsDownloadConfig.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0));
                if (!file3.exists()) {
                    file3.createNewFile();
                }
                iA = TbsShareManager.getTbsStableCoreVersion(context, i5);
            } else {
                iA = a(new File(string2));
            }
            TbsLog.i("TbsInstaller", "doTpatch wholeFile is " + i2 + " version is " + i4 + " patchVersion is " + iA);
            if (i4 == iA) {
                if (TbsDownloader.a(context)) {
                    TbsLog.i("TbsInstaller", "Tpatch decouple success!");
                    TbsLogReport.getInstance(context).setInstallErrorCode(237, "");
                } else {
                    TbsLog.i("TbsInstaller", "Tpatch success!");
                    TbsLogReport.getInstance(context).setInstallErrorCode(236, "");
                }
                return 0;
            }
            TbsLog.i("TbsInstaller", "version not equals!!!" + i4 + "patchVersion:" + iA);
            TbsLogReport.getInstance(context).setInstallErrorCode(240, "version=" + i4 + ",patchVersion=" + iA);
            return 1;
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLogReport.getInstance(context).setInstallErrorCode(239, "patch exception" + Log.getStackTraceString(e2));
            return 1;
        }
    }

    private boolean c(Context context, File file) {
        try {
            File[] fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.tencent.smtt.sdk.o.4
                @Override // java.io.FileFilter
                public boolean accept(File file2) {
                    return file2.getName().endsWith(".jar");
                }
            });
            int length = fileArrListFiles.length;
            ClassLoader classLoader = context.getClassLoader();
            for (int i2 = 0; i2 < length; i2++) {
                TbsLog.i("TbsInstaller", "jarFile: " + fileArrListFiles[i2].getAbsolutePath());
                new DexClassLoader(fileArrListFiles[i2].getAbsolutePath(), file.getAbsolutePath(), null, classLoader);
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLogReport.getInstance(context).setInstallErrorCode(209, e2.toString());
            TbsLog.i("TbsInstaller", "TbsInstaller-doTbsDexOpt done");
            return false;
        }
    }

    private synchronized boolean c(Context context, boolean z2) {
        TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromTpatch");
        boolean z3 = false;
        try {
        } catch (Throwable th) {
            TbsLogReport.getInstance(context).setInstallErrorCode(215, th.toString());
            QbSdk.a(context, "TbsInstaller::enableTbsCoreFromTpatch exception:" + Log.getStackTraceString(th));
        }
        if (!s(context)) {
            return false;
        }
        ReentrantLock reentrantLock = f21244h;
        boolean zTryLock = reentrantLock.tryLock();
        TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromTpatch Locked =" + zTryLock);
        if (zTryLock) {
            try {
                int iB = m.a(context).b("tpatch_status");
                int iA = a(false, context);
                TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromTpatch copyStatus =" + iB);
                TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromTpatch tbsCoreInstalledVer =" + iA);
                if (iB == 1) {
                    if (iA == 0) {
                        TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromTpatch tbsCoreInstalledVer = 0", true);
                    } else if (z2) {
                        TbsLog.i("TbsInstaller", "TbsInstaller-enableTbsCoreFromTpatch tbsCoreInstalledVer != 0", true);
                    }
                    x(context);
                    z3 = true;
                }
                reentrantLock.unlock();
            } catch (Throwable th2) {
                f21244h.unlock();
                throw th2;
            }
        }
        c();
        return z3;
    }

    private boolean d(Context context, File file) throws InterruptedException {
        try {
            File file2 = new File(file, "tbs_sdk_extension_dex.jar");
            File file3 = new File(file, "tbs_sdk_extension_dex.dex");
            new DexClassLoader(file2.getAbsolutePath(), file.getAbsolutePath(), null, context.getClassLoader());
            String strA = f.a(context, file3.getAbsolutePath());
            if (TextUtils.isEmpty(strA)) {
                TbsLogReport.getInstance(context).setInstallErrorCode(226, "can not find oat command");
                return false;
            }
            for (File file4 : file.listFiles(new FileFilter() { // from class: com.tencent.smtt.sdk.o.5
                @Override // java.io.FileFilter
                public boolean accept(File file5) {
                    return file5.getName().endsWith(".jar");
                }
            })) {
                String strSubstring = file4.getName().substring(0, r5.getName().length() - 4);
                Runtime.getRuntime().exec("/system/bin/dex2oat " + strA.replaceAll("tbs_sdk_extension_dex", strSubstring) + " --dex-location=" + a().p(context) + File.separator + strSubstring + ".jar").waitFor();
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLogReport.getInstance(context).setInstallErrorCode(226, e2);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0046 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0048 A[Catch: Exception -> 0x00c1, all -> 0x00db, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x00c1, blocks: (B:10:0x0040, B:14:0x0048, B:25:0x00b2, B:29:0x00bd, B:27:0x00b7, B:28:0x00bc), top: B:41:0x0040, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized boolean d(android.content.Context r8, boolean r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.String r0 = "enableTbsCoreFromUnzip"
            if (r8 == 0) goto L22
            java.lang.String r1 = "com.tencent.mm"
            android.content.Context r2 = r8.getApplicationContext()     // Catch: java.lang.Throwable -> Ldb
            android.content.pm.ApplicationInfo r2 = r2.getApplicationInfo()     // Catch: java.lang.Throwable -> Ldb
            java.lang.String r2 = r2.packageName     // Catch: java.lang.Throwable -> Ldb
            boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> Ldb
            if (r1 == 0) goto L22
            com.tencent.smtt.sdk.TbsLogReport r1 = com.tencent.smtt.sdk.TbsLogReport.getInstance(r8)     // Catch: java.lang.Throwable -> Ldb
            java.lang.String r2 = " "
            r3 = 229(0xe5, float:3.21E-43)
            r1.setInstallErrorCode(r3, r2)     // Catch: java.lang.Throwable -> Ldb
        L22:
            java.lang.String r1 = "TbsInstaller"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ldb
            r2.<init>()     // Catch: java.lang.Throwable -> Ldb
            java.lang.String r3 = "canRenameTmpDir ="
            r2.append(r3)     // Catch: java.lang.Throwable -> Ldb
            r2.append(r9)     // Catch: java.lang.Throwable -> Ldb
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Ldb
            com.tencent.smtt.utils.TbsLog.i(r1, r0, r2)     // Catch: java.lang.Throwable -> Ldb
            java.lang.String r1 = "TbsInstaller"
            java.lang.String r2 = "#1#"
            com.tencent.smtt.utils.TbsLog.i(r1, r0, r2)     // Catch: java.lang.Throwable -> Ldb
            r1 = 0
            boolean r2 = r7.s(r8)     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            if (r2 != 0) goto L48
            monitor-exit(r7)
            return r1
        L48:
            java.lang.String r2 = "TbsInstaller"
            java.lang.String r3 = "#2# getInstallFileLock Success!!"
            com.tencent.smtt.utils.TbsLog.i(r2, r0, r3)     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            java.util.concurrent.locks.ReentrantLock r2 = com.tencent.smtt.sdk.o.f21244h     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            boolean r3 = r2.tryLock()     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            java.lang.String r4 = "TbsInstaller"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            r5.<init>()     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            java.lang.String r6 = "locked="
            r5.append(r6)     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            r5.append(r3)     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            com.tencent.smtt.utils.TbsLog.i(r4, r0, r5)     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            if (r3 == 0) goto Lbd
            com.tencent.smtt.sdk.m r3 = com.tencent.smtt.sdk.m.a(r8)     // Catch: java.lang.Throwable -> Lb6
            int r3 = r3.c()     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r4 = "TbsInstaller"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb6
            r5.<init>()     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r6 = "TbsInstaller-enableTbsCoreFromUnzip installStatus="
            r5.append(r6)     // Catch: java.lang.Throwable -> Lb6
            r5.append(r3)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> Lb6
            com.tencent.smtt.utils.TbsLog.i(r4, r5)     // Catch: java.lang.Throwable -> Lb6
            int r4 = r7.a(r1, r8)     // Catch: java.lang.Throwable -> Lb6
            r5 = 2
            if (r3 != r5) goto Lb2
            java.lang.String r3 = "TbsInstaller"
            java.lang.String r5 = "#4# In Rename Logic"
            com.tencent.smtt.utils.TbsLog.i(r3, r0, r5)     // Catch: java.lang.Throwable -> Lb6
            r0 = 1
            if (r4 != 0) goto La8
            java.lang.String r9 = "TbsInstaller"
            java.lang.String r3 = "TbsInstaller-enableTbsCoreFromUnzip tbsCoreInstalledVer = 0"
            com.tencent.smtt.utils.TbsLog.i(r9, r3, r1)     // Catch: java.lang.Throwable -> Lb6
        La3:
            r7.w(r8)     // Catch: java.lang.Throwable -> Lb6
            r1 = r0
            goto Lb2
        La8:
            if (r9 == 0) goto Lb2
            java.lang.String r9 = "TbsInstaller"
            java.lang.String r3 = "TbsInstaller-enableTbsCoreFromUnzip tbsCoreInstalledVer != 0"
            com.tencent.smtt.utils.TbsLog.i(r9, r3, r1)     // Catch: java.lang.Throwable -> Lb6
            goto La3
        Lb2:
            r2.unlock()     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            goto Lbd
        Lb6:
            r9 = move-exception
            java.util.concurrent.locks.ReentrantLock r0 = com.tencent.smtt.sdk.o.f21244h     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            r0.unlock()     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            throw r9     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
        Lbd:
            r7.c()     // Catch: java.lang.Exception -> Lc1 java.lang.Throwable -> Ldb
            goto Ld9
        Lc1:
            r9 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ldb
            r0.<init>()     // Catch: java.lang.Throwable -> Ldb
            java.lang.String r2 = "TbsInstaller::enableTbsCoreFromUnzip Exception: "
            r0.append(r2)     // Catch: java.lang.Throwable -> Ldb
            r0.append(r9)     // Catch: java.lang.Throwable -> Ldb
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> Ldb
            com.tencent.smtt.sdk.QbSdk.a(r8, r0)     // Catch: java.lang.Throwable -> Ldb
            r9.printStackTrace()     // Catch: java.lang.Throwable -> Ldb
        Ld9:
            monitor-exit(r7)
            return r1
        Ldb:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.o.d(android.content.Context, boolean):boolean");
    }

    private void e(Context context, boolean z2) throws IOException {
        if (context == null) {
            TbsLogReport.getInstance(context).setInstallErrorCode(225, "setTmpFolderCoreToRead context is null");
            TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead inner return #01");
            return;
        }
        try {
            File file = new File(QbSdk.getTbsFolderDir(context), "tmp_folder_core_to_read.conf");
            if (!z2) {
                TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead inner tmp file delete #01");
                FileUtil.b(file);
            } else if (file.exists()) {
                TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead inner tmp file already exist #01");
            } else {
                TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead inner tmp file create #01");
                file.createNewFile();
            }
        } catch (Exception e2) {
            TbsLogReport.getInstance(context).setInstallErrorCode(225, "setTmpFolderCoreToRead Exception message is " + e2.getMessage() + " Exception cause is " + e2.getCause());
            TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead inner exception #01");
        }
    }

    private void f(Context context, int i2) throws IOException {
        TbsLog.i("TbsInstaller", "proceedTpatchStatus,result=" + i2);
        if (i2 == 0) {
            TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead call #03");
            e(context, true);
            m.a(context).b(TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0), 1);
        }
        QbSdk.setTBSInstallingStatus(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean g(android.content.Context r8, int r9) {
        /*
            r7 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "TbsInstaller-doTbsDexOpt start - dirMode: "
            r0.append(r1)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "TbsInstaller"
            com.tencent.smtt.utils.TbsLog.i(r1, r0)
            r0 = 0
            r2 = 1
            if (r9 == 0) goto L3e
            if (r9 == r2) goto L39
            r3 = 2
            if (r9 == r3) goto L34
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> La3
            r3.<init>()     // Catch: java.lang.Exception -> La3
            java.lang.String r4 = "doDexoptOrDexoat mode error: "
            r3.append(r4)     // Catch: java.lang.Exception -> La3
            r3.append(r9)     // Catch: java.lang.Exception -> La3
            java.lang.String r9 = r3.toString()     // Catch: java.lang.Exception -> La3
            com.tencent.smtt.utils.TbsLog.e(r1, r9)     // Catch: java.lang.Exception -> La3
            return r0
        L34:
            java.io.File r9 = r7.p(r8)     // Catch: java.lang.Exception -> La3
            goto L49
        L39:
            java.io.File r9 = r7.e(r8, r2)     // Catch: java.lang.Exception -> La3
            goto L49
        L3e:
            boolean r9 = com.tencent.smtt.sdk.TbsDownloader.a(r8)     // Catch: java.lang.Exception -> La3
            if (r9 == 0) goto L45
            return r2
        L45:
            java.io.File r9 = r7.e(r8, r0)     // Catch: java.lang.Exception -> La3
        L49:
            java.lang.String r3 = "java.vm.version"
            java.lang.String r3 = java.lang.System.getProperty(r3)     // Catch: java.lang.Throwable -> L5b
            if (r3 == 0) goto L65
            java.lang.String r4 = "2"
            boolean r3 = r3.startsWith(r4)     // Catch: java.lang.Throwable -> L5b
            if (r3 == 0) goto L65
            r3 = r2
            goto L66
        L5b:
            r3 = move-exception
            com.tencent.smtt.sdk.TbsLogReport r4 = com.tencent.smtt.sdk.TbsLogReport.getInstance(r8)     // Catch: java.lang.Exception -> La3
            r5 = 226(0xe2, float:3.17E-43)
            r4.setInstallErrorCode(r5, r3)     // Catch: java.lang.Exception -> La3
        L65:
            r3 = r0
        L66:
            int r4 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Exception -> La3
            r5 = 23
            if (r4 != r5) goto L6e
            r4 = r2
            goto L6f
        L6e:
            r4 = r0
        L6f:
            com.tencent.smtt.sdk.TbsDownloadConfig r5 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r8)     // Catch: java.lang.Exception -> La3
            android.content.SharedPreferences r5 = r5.mPreferences     // Catch: java.lang.Exception -> La3
            java.lang.String r6 = "tbs_stop_preoat"
            boolean r5 = r5.getBoolean(r6, r0)     // Catch: java.lang.Exception -> La3
            if (r3 == 0) goto L83
            if (r4 == 0) goto L83
            if (r5 != 0) goto L83
            r0 = r2
        L83:
            if (r0 == 0) goto L91
            boolean r0 = r7.d(r8, r9)     // Catch: java.lang.Exception -> La3
            if (r0 == 0) goto L91
            java.lang.String r9 = "doTbsDexOpt -- doDexoatForArtVm"
            com.tencent.smtt.utils.TbsLog.i(r1, r9)     // Catch: java.lang.Exception -> La3
            return r2
        L91:
            if (r3 == 0) goto L99
            java.lang.String r9 = "doTbsDexOpt -- is ART mode, skip!"
            com.tencent.smtt.utils.TbsLog.i(r1, r9)     // Catch: java.lang.Exception -> La3
            goto Lb4
        L99:
            java.lang.String r0 = "doTbsDexOpt -- doDexoptForDavlikVM"
            com.tencent.smtt.utils.TbsLog.i(r1, r0)     // Catch: java.lang.Exception -> La3
            boolean r8 = r7.c(r8, r9)     // Catch: java.lang.Exception -> La3
            return r8
        La3:
            r9 = move-exception
            r9.printStackTrace()
            com.tencent.smtt.sdk.TbsLogReport r8 = com.tencent.smtt.sdk.TbsLogReport.getInstance(r8)
            r0 = 209(0xd1, float:2.93E-43)
            java.lang.String r9 = r9.toString()
            r8.setInstallErrorCode(r0, r9)
        Lb4:
            java.lang.String r8 = "TbsInstaller-doTbsDexOpt done"
            com.tencent.smtt.utils.TbsLog.i(r1, r8)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.o.g(android.content.Context, int):boolean");
    }

    public static File r(Context context) {
        File file = new File(QbSdk.getTbsFolderDir(context), "core_private");
        if (file.isDirectory() || file.mkdir()) {
            return file;
        }
        return null;
    }

    private int t(Context context) {
        boolean z2 = m.a(context).d() == 1;
        boolean zA = TbsDownloader.a(context);
        return z2 ? zA ? 234 : 221 : zA ? 233 : 200;
    }

    private static boolean u(Context context) {
        String str;
        if (context == null) {
            str = "#1#";
        } else {
            try {
                if (new File(QbSdk.getTbsFolderDir(context), "tmp_folder_core_to_read.conf").exists()) {
                    TbsLog.i("TbsInstaller", "getTmpFolderCoreToRead", "#2#");
                    return true;
                }
                TbsLog.i("TbsInstaller", "getTmpFolderCoreToRead", "#3#");
                return false;
            } catch (Exception unused) {
                str = "#4#";
            }
        }
        TbsLog.i("TbsInstaller", "getTmpFolderCoreToRead", str);
        return true;
    }

    private boolean v(Context context) throws IOException {
        TbsLog.i("TbsInstaller", "Tbsinstaller getTbsCoreRenameFileLock #1 ");
        FileLock fileLockE = FileUtil.e(context);
        f21246k = fileLockE;
        if (fileLockE == null) {
            TbsLog.i("TbsInstaller", "getTbsCoreRenameFileLock## failed!");
            return false;
        }
        TbsLog.i("TbsInstaller", "Tbsinstaller getTbsCoreRenameFileLock true ");
        return true;
    }

    private void w(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromUnzip");
        if (!v(context)) {
            TbsLog.i("TbsInstaller", "get rename fileLock#4 ## failed!");
            return;
        }
        try {
            y(context);
            z(context);
            TbsLog.i("TbsInstaller", "after renameTbsCoreShareDir");
            TbsLog.i("TbsInstaller", "is thirdapp and not chmod");
            m.a(context).a(0);
            m.a(context).b(0);
            m.a(context).d(0);
            m.a(context).a("incrupdate_retry_num", 0);
            m.a(context).c(0, 3);
            m.a(context).a("");
            m.a(context).a("tpatch_num", 0);
            m.a(context).c(-1);
            if (!TbsShareManager.isThirdPartyApp(context)) {
                int i2 = TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, 0);
                if (i2 <= 0 || i2 == a().g(context) || i2 != a().h(context)) {
                    TbsLog.i("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromUnzip #1 deCoupleCoreVersion is " + i2 + " getTbsCoreShareDecoupleCoreVersion is " + a().g(context) + " getTbsCoreInstalledVerInNolock is " + a().h(context));
                } else {
                    m(context);
                }
            }
            f21247l.set(0);
            f21249n = 0;
        } catch (Throwable th) {
            th.printStackTrace();
            TbsLogReport.getInstance(context).setInstallErrorCode(219, "exception when renameing from unzip:" + th.toString());
            TbsLog.e("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromUnzip Exception", true);
        }
        f(context);
    }

    private void x(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromTpatch");
        if (!v(context)) {
            TbsLog.i("TbsInstaller", "get rename fileLock#4 ## failed!");
            return;
        }
        try {
            y(context);
            A(context);
            m.a(context).b(0, -1);
            m.a(context).a("tpatch_num", 0);
            f21247l.set(0);
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLogReport.getInstance(context).setInstallErrorCode(242, "exception when renameing from tpatch:" + e2.toString());
        }
        f(context);
    }

    private void y(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--deleteOldCore");
        FileUtil.a(p(context), false);
    }

    private void z(Context context) throws IOException {
        TbsLogReport tbsLogReport;
        int i2;
        TbsLog.i("TbsInstaller", "TbsInstaller--renameShareDir");
        File fileE = e(context, 0);
        File fileP = p(context);
        if (fileE == null || fileP == null) {
            TbsLog.i("TbsInstaller", "renameTbsCoreShareDir return,tmpTbsCoreUnzipDir=" + fileE + "tbsSharePath=" + fileP);
            return;
        }
        boolean zRenameTo = fileE.renameTo(fileP);
        TbsLog.i("TbsInstaller", "renameTbsCoreShareDir rename success=" + zRenameTo);
        com.tencent.smtt.utils.s.a("8is" + zRenameTo);
        if (context != null && "com.tencent.mm".equals(context.getApplicationContext().getApplicationInfo().packageName)) {
            if (zRenameTo) {
                tbsLogReport = TbsLogReport.getInstance(context);
                i2 = 230;
            } else {
                tbsLogReport = TbsLogReport.getInstance(context);
                i2 = 231;
            }
            tbsLogReport.setInstallErrorCode(i2, " ");
        }
        TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead call #07");
        e(context, false);
    }

    public int a(File file) throws Throwable {
        BufferedInputStream bufferedInputStream = null;
        try {
            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsVersion  tbsShareDir is " + file);
            File file2 = new File(file, "tbs.conf");
            if (!file2.exists()) {
                return 0;
            }
            Properties properties = new Properties();
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file2));
            try {
                properties.load(bufferedInputStream2);
                bufferedInputStream2.close();
                String property = properties.getProperty("tbs_core_version");
                if (property == null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException unused) {
                    }
                    return 0;
                }
                int i2 = Integer.parseInt(property);
                try {
                    bufferedInputStream2.close();
                } catch (IOException unused2) {
                }
                return i2;
            } catch (Exception unused3) {
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused4) {
                    }
                }
                return 0;
            } catch (Throwable th) {
                th = th;
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused5) {
                    }
                }
                throw th;
            }
        } catch (Exception unused6) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public int a(boolean z2, Context context) {
        if (z2 || f21247l.get().intValue() <= 0) {
            f21247l.set(Integer.valueOf(h(context)));
        }
        return f21247l.get().intValue();
    }

    public File a(Context context, int i2, boolean z2) {
        String str;
        String str2;
        File tbsFolderDir = QbSdk.getTbsFolderDir(context);
        switch (i2) {
            case 0:
                str = "core_unzip_tmp";
                break;
            case 1:
                str = "core_copy_tmp";
                break;
            case 2:
                str = "core_unzip_tmp_decouple";
                break;
            case 3:
                str = "core_share_backup";
                break;
            case 4:
                str = "core_share_backup_tmp";
                break;
            case 5:
                str = "tpatch_tmp";
                break;
            case 6:
                str = "tpatch_decouple_tmp";
                break;
            default:
                str = "";
                break;
        }
        TbsLog.i("TbsInstaller", "type=" + i2 + "needMakeDir=" + z2 + "folder=" + str);
        File file = new File(tbsFolderDir, str);
        if (!file.isDirectory()) {
            if (z2) {
                str2 = file.mkdir() ? "getCoreDir,no need mkdir" : "getCoreDir,mkdir false";
            }
            TbsLog.i("TbsInstaller", str2);
            return null;
        }
        return file;
    }

    public File a(Context context, Context context2) {
        File file = new File(QbSdk.getTbsFolderDir(context2), "core_share");
        if (file.isDirectory() || ((context != null && TbsShareManager.isThirdPartyApp(context)) || file.mkdir())) {
            return file;
        }
        TbsLog.i("TbsInstaller", "getTbsCoreShareDir,mkdir false");
        return null;
    }

    public File a(Context context, File file) {
        File file2 = new File(file, "core_share_decouple");
        if (file2.isDirectory() || file2.mkdir()) {
            return file2;
        }
        return null;
    }

    public void a(Context context, int i2) throws IOException {
        TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead call #01 ");
        e(context, true);
        m.a(context).c(i2, 2);
    }

    public void a(Context context, Bundle bundle) {
        if (bundle == null || context == null) {
            return;
        }
        Object[] objArr = {context, bundle};
        Message message = new Message();
        message.what = 3;
        message.obj = objArr;
        f21248m.sendMessage(message);
    }

    public void a(Context context, File file, int i2) throws Throwable {
        FileOutputStream fileOutputStreamB = FileUtil.b(context, true, "core_unzip.lock");
        FileLock fileLockA = FileUtil.a(context, fileOutputStreamB);
        if (fileLockA == null) {
            TbsLog.i("TbsInstaller", "can not get Core unzip FileLock,skip!!!");
            return;
        }
        TbsLog.i("TbsInstaller", "unzipTbsCoreToThirdAppTmpInThread #1");
        boolean zA = a(context, file, false);
        TbsLog.i("TbsInstaller", "unzipTbsCoreToThirdAppTmpInThread result is " + zA);
        com.tencent.smtt.utils.s.a(context, "copy_host_core_v3", ("coreVersionIs" + i2) + StrPool.UNDERLINE + "ret=is=" + zA);
        if (zA) {
            a().a(context, i2);
        }
        FileUtil.a(fileLockA, fileOutputStreamB);
    }

    public void a(Context context, String str, int i2) {
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCore tbsApkPath=" + str);
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCore tbsCoreTargetVer=" + i2);
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentProcessName=" + context.getApplicationInfo().processName);
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCore currentProcessId=" + Process.myPid());
        TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCore currentThreadName=" + Thread.currentThread().getName());
        Object[] objArr = {context, str, Integer.valueOf(i2)};
        Message message = new Message();
        message.what = 1;
        message.obj = objArr;
        f21248m.sendMessage(message);
    }

    public void a(Context context, boolean z2) {
        int iC;
        int iB;
        String strD;
        int iC2;
        int iB2;
        boolean z3 = true;
        if (z2) {
            this.f21255j = true;
        }
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentProcessName=" + context.getApplicationInfo().processName);
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentProcessId=" + Process.myPid());
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentThreadName=" + Thread.currentThread().getName());
        if (s(context)) {
            ReentrantLock reentrantLock = f21244h;
            if (reentrantLock.tryLock()) {
                try {
                    iC = m.a(context).c();
                    iB = m.a(context).b();
                    strD = m.a(context).d("install_apk_path");
                    iC2 = m.a(context).c("copy_core_ver");
                    iB2 = m.a(context).b("copy_status");
                    reentrantLock.unlock();
                } catch (Throwable th) {
                    f21244h.unlock();
                    throw th;
                }
            } else {
                strD = null;
                iC = -1;
                iB2 = -1;
                iB = 0;
                iC2 = 0;
            }
            c();
            TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore installStatus=" + iC);
            TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore tbsCoreInstallVer=" + iB);
            TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore tbsApkPath=" + strD);
            TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore tbsCoreCopyVer=" + iC2);
            TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore copyStatus=" + iB2);
            int i2 = TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_RESPONSECODE, 0);
            if (i2 != 1 && i2 != 2 && i2 != 4) {
                z3 = false;
            }
            if (!z3 && i2 != 0 && i2 != 5) {
                Bundle bundle = new Bundle();
                bundle.putInt("operation", 10001);
                a(context, bundle);
            }
            if (iC > -1 && iC < 2) {
                a(context, strD, iB);
            }
            if (iB2 == 0) {
                b(context, iC2);
            }
        }
    }

    public boolean a(Context context, File[] fileArr) {
        return false;
    }

    public int b(Context context) {
        if (!u(context)) {
            return 0;
        }
        if (a(context, "core_unzip_tmp")) {
            return -1;
        }
        if (a(context, "core_share_backup_tmp")) {
            return -2;
        }
        if (a(context, "core_copy_tmp")) {
            return -3;
        }
        return a(context, "tpatch_tmp") ? -4 : 1;
    }

    public int b(Context context, String str) {
        PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(str, 0);
        if (packageArchiveInfo != null) {
            return packageArchiveInfo.versionCode;
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x033a  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0605  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x060a  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0677  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x067b  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0701  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void b(android.content.Context r26, android.os.Bundle r27) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1905
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.o.b(android.content.Context, android.os.Bundle):void");
    }

    public void b(Context context, boolean z2) {
        String str;
        if (QbSdk.f20818b) {
            return;
        }
        TbsLog.i("TbsInstaller", "installTbsCoreIfNeeded", "#1# check local x5core prepared to install");
        if (TbsShareManager.isThirdPartyApp(context) && m.a(context).b("remove_old_core") == 1 && z2) {
            try {
                FileUtil.b(a().p(context));
                TbsLog.i("TbsInstaller", "installTbsCoreIfNeeded", "thirdAPP success--> delete old core_share Directory");
            } catch (Throwable th) {
                th.printStackTrace();
            }
            m.a(context).a("remove_old_core", 0);
        }
        if (u(context)) {
            TbsLog.i("TbsInstaller", "installTbsCoreIfNeeded", "#2# try to install tbs core from tmp dir");
            if (a(context, "core_unzip_tmp") && d(context, z2)) {
                str = "TbsInstaller-installTbsCoreIfNeeded, enableTbsCoreFromUnzip !!";
            } else {
                if (!a(context, "tpatch_tmp") || !c(context, z2)) {
                    TbsLog.i("TbsInstaller", "TbsInstaller-installTbsCoreIfNeeded, error !!", true);
                    return;
                }
                str = "TbsInstaller-installTbsCoreIfNeeded, enableTbsCoreFromTpatch !!";
            }
            TbsLog.i("TbsInstaller", str, true);
        }
    }

    public boolean b(Context context, int i2) {
        if (TbsDownloader.getOverSea(context)) {
            return false;
        }
        TbsLog.i("TbsInstaller", "TbsInstaller-installLocalTbsCore targetTbsCoreVer=" + i2);
        TbsLog.i("TbsInstaller", "TbsInstaller-continueInstallTbsCore currentProcessName=" + context.getApplicationInfo().processName);
        TbsLog.i("TbsInstaller", "TbsInstaller-installLocalTbsCore currentProcessId=" + Process.myPid());
        TbsLog.i("TbsInstaller", "TbsInstaller-installLocalTbsCore currentThreadName=" + Thread.currentThread().getName());
        Context contextC = c(context, i2);
        if (contextC == null) {
            TbsLog.i("TbsInstaller", "TbsInstaller--installLocalTbsCore copy from null");
            return false;
        }
        Object[] objArr = {contextC, context, Integer.valueOf(i2)};
        Message message = new Message();
        message.what = 2;
        message.obj = objArr;
        f21248m.sendMessage(message);
        return true;
    }

    @Deprecated
    public Context c(Context context, int i2) {
        return null;
    }

    public synchronized void c() {
        int i2 = this.f21251d;
        if (i2 <= 0) {
            TbsLog.i("TbsInstaller", "releaseTbsInstallingFileLock currentTbsFileLockStackCount=" + this.f21251d + "call stack:" + Log.getStackTraceString(new Throwable()));
            return;
        }
        if (i2 > 1) {
            TbsLog.i("TbsInstaller", "releaseTbsInstallingFileLock with skip");
            this.f21251d--;
        } else {
            if (i2 == 1) {
                TbsLog.i("TbsInstaller", "releaseTbsInstallingFileLock without skip");
                FileUtil.a(this.f21252e, this.f21253f);
                this.f21251d = 0;
            }
        }
    }

    public void c(Context context) throws IOException {
        TbsLog.i("TbsInstaller", "setTmpFolderCoreToRead call #02");
        e(context, true);
        m.a(context).c(g(context), 2);
    }

    public int d(Context context, int i2) {
        return a(e(context, i2));
    }

    public boolean d(Context context) throws IOException {
        File file = new File(p(context), "tbs.conf");
        boolean z2 = false;
        if (!file.exists()) {
            return false;
        }
        Properties properties = new Properties();
        BufferedInputStream bufferedInputStream = null;
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
            try {
                properties.load(bufferedInputStream2);
                boolean zBooleanValue = Boolean.valueOf(properties.getProperty("tbs_local_installation", k.a.f27524v)).booleanValue();
                if (zBooleanValue) {
                    try {
                        if (System.currentTimeMillis() - file.lastModified() > 259200000) {
                            z2 = true;
                        }
                    } catch (Throwable th) {
                        th = th;
                        z2 = zBooleanValue;
                        bufferedInputStream = bufferedInputStream2;
                        try {
                            th.printStackTrace();
                            return z2;
                        } finally {
                            if (bufferedInputStream != null) {
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }
                }
                TbsLog.i("TbsInstaller", "TBS_LOCAL_INSTALLATION is:" + zBooleanValue + " expired=" + z2);
                boolean z3 = zBooleanValue & (!z2);
                try {
                    bufferedInputStream2.close();
                    return z3;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return z3;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public File e(Context context, int i2) {
        return a(context, i2, true);
    }

    public void e(Context context) throws IOException {
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        try {
            File file = new File(p(context), "tbs.conf");
            Properties properties = new Properties();
            BufferedOutputStream bufferedOutputStream2 = null;
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                try {
                    properties.load(bufferedInputStream);
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                } catch (Throwable unused) {
                }
            } catch (Throwable unused2) {
                bufferedInputStream = null;
            }
            try {
                properties.setProperty("tbs_local_installation", k.a.f27524v);
                properties.store(bufferedOutputStream, (String) null);
                try {
                    bufferedOutputStream.close();
                } catch (IOException unused3) {
                }
            } catch (Throwable unused4) {
                bufferedOutputStream2 = bufferedOutputStream;
                if (bufferedOutputStream2 != null) {
                    try {
                        bufferedOutputStream2.close();
                    } catch (IOException unused5) {
                    }
                }
                if (bufferedInputStream == null) {
                    return;
                }
                bufferedInputStream.close();
            }
            bufferedInputStream.close();
        } catch (Throwable unused6) {
        }
    }

    public void f(Context context) {
        FileLock fileLock = f21246k;
        if (fileLock != null) {
            FileUtil.a(context, fileLock);
        }
    }

    public int g(Context context) throws Throwable {
        BufferedInputStream bufferedInputStream = null;
        try {
            File file = new File(o(context), "tbs.conf");
            if (!file.exists()) {
                return 0;
            }
            Properties properties = new Properties();
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
            try {
                properties.load(bufferedInputStream2);
                bufferedInputStream2.close();
                String property = properties.getProperty("tbs_core_version");
                if (property == null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException unused) {
                    }
                    return 0;
                }
                int i2 = Integer.parseInt(property);
                try {
                    bufferedInputStream2.close();
                } catch (IOException unused2) {
                }
                return i2;
            } catch (Exception unused3) {
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused4) {
                    }
                }
                return 0;
            } catch (Throwable th) {
                th = th;
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused5) {
                    }
                }
                throw th;
            }
        } catch (Exception unused6) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public int h(Context context) {
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                File file = new File(p(context), "tbs.conf");
                if (!file.exists()) {
                    return 0;
                }
                TbsLog.i("TbsInstaller", "getTbsCoreInstalledVerInNolock tbsPropFile is " + file.getAbsolutePath());
                Properties properties = new Properties();
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
                try {
                    properties.load(bufferedInputStream2);
                    bufferedInputStream2.close();
                    String property = properties.getProperty("tbs_core_version");
                    if (property == null) {
                        try {
                            bufferedInputStream2.close();
                        } catch (IOException e2) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock IOException=" + e2.toString());
                        }
                        return 0;
                    }
                    int i2 = Integer.parseInt(property);
                    if (f21249n == 0) {
                        f21249n = i2;
                    }
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException e3) {
                        TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock IOException=" + e3.toString());
                    }
                    return i2;
                } catch (Exception e4) {
                    e = e4;
                    bufferedInputStream = bufferedInputStream2;
                    TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock Exception=" + e.toString());
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e5) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock IOException=" + e5.toString());
                        }
                    }
                    return 0;
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream = bufferedInputStream2;
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e6) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock IOException=" + e6.toString());
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e7) {
            e = e7;
        }
    }

    public int i(Context context) {
        int i2 = f21249n;
        return i2 != 0 ? i2 : h(context);
    }

    public void j(Context context) {
        if (f21249n != 0) {
            return;
        }
        f21249n = h(context);
    }

    public boolean k(Context context) {
        return new File(p(context), "tbs.conf").exists();
    }

    public int l(Context context) throws Throwable {
        if (!s(context)) {
            return -1;
        }
        ReentrantLock reentrantLock = f21244h;
        boolean zTryLock = reentrantLock.tryLock();
        TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerWithLock locked=" + zTryLock);
        if (!zTryLock) {
            c();
            return 0;
        }
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                File file = new File(p(context), "tbs.conf");
                if (!file.exists()) {
                    try {
                        if (reentrantLock.isHeldByCurrentThread()) {
                            reentrantLock.unlock();
                        }
                    } catch (Throwable th) {
                        TbsLog.e("TbsInstaller", "TbsRenameLock.unlock exception: " + th);
                    }
                    c();
                    return 0;
                }
                Properties properties = new Properties();
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
                try {
                    properties.load(bufferedInputStream2);
                    bufferedInputStream2.close();
                    String property = properties.getProperty("tbs_core_version");
                    if (property == null) {
                        try {
                            bufferedInputStream2.close();
                        } catch (IOException e2) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerWithLock IOException=" + e2.toString());
                        }
                        try {
                            ReentrantLock reentrantLock2 = f21244h;
                            if (reentrantLock2.isHeldByCurrentThread()) {
                                reentrantLock2.unlock();
                            }
                        } catch (Throwable th2) {
                            TbsLog.e("TbsInstaller", "TbsRenameLock.unlock exception: " + th2);
                        }
                        c();
                        return 0;
                    }
                    ThreadLocal<Integer> threadLocal = f21247l;
                    threadLocal.set(Integer.valueOf(Integer.parseInt(property)));
                    int iIntValue = threadLocal.get().intValue();
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException e3) {
                        TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerWithLock IOException=" + e3.toString());
                    }
                    try {
                        ReentrantLock reentrantLock3 = f21244h;
                        if (reentrantLock3.isHeldByCurrentThread()) {
                            reentrantLock3.unlock();
                        }
                    } catch (Throwable th3) {
                        TbsLog.e("TbsInstaller", "TbsRenameLock.unlock exception: " + th3);
                    }
                    c();
                    return iIntValue;
                } catch (Exception e4) {
                    e = e4;
                    bufferedInputStream = bufferedInputStream2;
                    TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerWithLock Exception=" + e.toString());
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e5) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerWithLock IOException=" + e5.toString());
                        }
                    }
                    try {
                        ReentrantLock reentrantLock4 = f21244h;
                        if (reentrantLock4.isHeldByCurrentThread()) {
                            reentrantLock4.unlock();
                        }
                    } catch (Throwable th4) {
                        TbsLog.e("TbsInstaller", "TbsRenameLock.unlock exception: " + th4);
                    }
                    c();
                    return 0;
                } catch (Throwable th5) {
                    th = th5;
                    bufferedInputStream = bufferedInputStream2;
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e6) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerWithLock IOException=" + e6.toString());
                        }
                    }
                    try {
                        ReentrantLock reentrantLock5 = f21244h;
                        if (reentrantLock5.isHeldByCurrentThread()) {
                            reentrantLock5.unlock();
                        }
                    } catch (Throwable th6) {
                        TbsLog.e("TbsInstaller", "TbsRenameLock.unlock exception: " + th6);
                    }
                    c();
                    throw th;
                }
            } catch (Throwable th7) {
                th = th7;
            }
        } catch (Exception e7) {
            e = e7;
        }
    }

    public boolean m(Context context) {
        return true;
    }

    public void n(Context context) {
        TbsLog.i("TbsInstaller", "TbsInstaller--cleanStatusAndTmpDir");
        m.a(context).a(0);
        m.a(context).b(0);
        m.a(context).d(0);
        m.a(context).a("incrupdate_retry_num", 0);
        if (TbsDownloader.a(context)) {
            return;
        }
        m.a(context).c(0, -1);
        m.a(context).a("");
        m.a(context).a("copy_retry_num", 0);
        m.a(context).c(-1);
        m.a(context).a(0, -1);
        FileUtil.a(e(context, 0), true);
        FileUtil.a(e(context, 1), true);
    }

    public File o(Context context) {
        File file = new File(QbSdk.getTbsFolderDir(context), "core_share_decouple");
        if (file.isDirectory() || file.mkdir()) {
            return file;
        }
        return null;
    }

    public File p(Context context) {
        return a((Context) null, context);
    }

    public File q(Context context) {
        File file = new File(QbSdk.getTbsFolderDir(context), "share");
        if (file.isDirectory() || file.mkdir()) {
            return file;
        }
        return null;
    }

    public synchronized boolean s(Context context) {
        if (this.f21251d > 0) {
            TbsLog.i("TbsInstaller", "getTbsInstallingFileLock success,is cached= true");
            this.f21251d++;
            return true;
        }
        FileOutputStream fileOutputStreamB = FileUtil.b(context, true, "tbslock.txt");
        this.f21253f = fileOutputStreamB;
        if (fileOutputStreamB == null) {
            TbsLog.i("TbsInstaller", "getTbsInstallingFileLock get install fos failed");
            return false;
        }
        FileLock fileLockA = FileUtil.a(context, fileOutputStreamB);
        this.f21252e = fileLockA;
        if (fileLockA == null) {
            TbsLog.i("TbsInstaller", "getTbsInstallingFileLock tbsFileLockFileLock == null");
            return false;
        }
        TbsLog.i("TbsInstaller", "getTbsInstallingFileLock success,is cached= false");
        this.f21251d++;
        return true;
    }
}
