package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/* loaded from: classes6.dex */
public class TbsDownloadUpload {

    /* renamed from: b, reason: collision with root package name */
    private static TbsDownloadUpload f20918b;

    /* renamed from: a, reason: collision with root package name */
    Map<String, Object> f20919a = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    private Context f20920c;

    /* renamed from: d, reason: collision with root package name */
    private int f20921d;

    /* renamed from: e, reason: collision with root package name */
    private int f20922e;

    /* renamed from: f, reason: collision with root package name */
    private int f20923f;

    /* renamed from: g, reason: collision with root package name */
    private int f20924g;

    /* renamed from: h, reason: collision with root package name */
    private int f20925h;

    /* renamed from: i, reason: collision with root package name */
    private int f20926i;
    public SharedPreferences mPreferences;

    public interface TbsUploadKey {
        public static final String KEY_LOCAL_CORE_VERSION = "tbs_local_core_version";
        public static final String KEY_NEEDDOWNLOAD_CODE = "tbs_needdownload_code";
        public static final String KEY_NEEDDOWNLOAD_RETURN = "tbs_needdownload_return";
        public static final String KEY_NEEDDOWNLOAD_SENT = "tbs_needdownload_sent";
        public static final String KEY_STARTDOWNLOAD_CODE = "tbs_startdownload_code";
        public static final String KEY_STARTDOWNLOAD_SENT = "tbs_startdownload_sent";
    }

    public TbsDownloadUpload(Context context) {
        this.mPreferences = context.getSharedPreferences("tbs_download_upload", 4);
        Context applicationContext = context.getApplicationContext();
        this.f20920c = applicationContext;
        if (applicationContext == null) {
            this.f20920c = context;
        }
    }

    private static File a(Context context, String str) throws IOException {
        File fileR = o.r(context);
        if (fileR == null) {
            return null;
        }
        File file = new File(fileR, str);
        if (file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static synchronized void clear() {
        f20918b = null;
    }

    public static synchronized TbsDownloadUpload getInstance() {
        return f20918b;
    }

    public static synchronized TbsDownloadUpload getInstance(Context context) {
        if (f20918b == null) {
            f20918b = new TbsDownloadUpload(context);
        }
        return f20918b;
    }

    public void clearUploadCode() {
        this.f20919a.put(TbsUploadKey.KEY_NEEDDOWNLOAD_CODE, 0);
        this.f20919a.put(TbsUploadKey.KEY_STARTDOWNLOAD_CODE, 0);
        this.f20919a.put(TbsUploadKey.KEY_NEEDDOWNLOAD_RETURN, 0);
        this.f20919a.put(TbsUploadKey.KEY_NEEDDOWNLOAD_SENT, 0);
        this.f20919a.put(TbsUploadKey.KEY_STARTDOWNLOAD_SENT, 0);
        this.f20919a.put(TbsUploadKey.KEY_LOCAL_CORE_VERSION, 0);
        writeTbsDownloadInfo();
    }

    public synchronized void commit() {
        writeTbsDownloadInfo();
    }

    public synchronized int getLocalCoreVersion() {
        return this.f20926i;
    }

    public synchronized int getNeedDownloadCode() {
        return this.f20921d;
    }

    public synchronized int getNeedDownloadReturn() {
        return this.f20923f;
    }

    public synchronized int getStartDownloadCode() {
        return this.f20922e;
    }

    public synchronized void readTbsDownloadInfo(Context context) {
        BufferedInputStream bufferedInputStream;
        Throwable th;
        File fileA;
        try {
            fileA = a(this.f20920c, "download_upload");
        } catch (Throwable th2) {
            bufferedInputStream = null;
            th = th2;
        }
        if (fileA == null) {
            return;
        }
        bufferedInputStream = new BufferedInputStream(new FileInputStream(fileA));
        try {
            Properties properties = new Properties();
            properties.load(bufferedInputStream);
            String property = properties.getProperty(TbsUploadKey.KEY_NEEDDOWNLOAD_CODE, "");
            if (!"".equals(property)) {
                this.f20921d = Math.max(Integer.parseInt(property), 0);
            }
            String property2 = properties.getProperty(TbsUploadKey.KEY_STARTDOWNLOAD_CODE, "");
            if (!"".equals(property2)) {
                this.f20922e = Math.max(Integer.parseInt(property2), 0);
            }
            String property3 = properties.getProperty(TbsUploadKey.KEY_NEEDDOWNLOAD_RETURN, "");
            if (!"".equals(property3)) {
                this.f20923f = Math.max(Integer.parseInt(property3), 0);
            }
            String property4 = properties.getProperty(TbsUploadKey.KEY_NEEDDOWNLOAD_SENT, "");
            if (!"".equals(property4)) {
                this.f20924g = Math.max(Integer.parseInt(property4), 0);
            }
            String property5 = properties.getProperty(TbsUploadKey.KEY_STARTDOWNLOAD_SENT, "");
            if (!"".equals(property5)) {
                this.f20925h = Math.max(Integer.parseInt(property5), 0);
            }
            String property6 = properties.getProperty(TbsUploadKey.KEY_LOCAL_CORE_VERSION, "");
            if (!"".equals(property6)) {
                this.f20926i = Math.max(Integer.parseInt(property6), 0);
            }
        } catch (Throwable th3) {
            th = th3;
            try {
                th.printStackTrace();
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (Exception e2) {
                        e = e2;
                        e.printStackTrace();
                    }
                }
            } finally {
            }
        }
        try {
            bufferedInputStream.close();
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00c9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[Catch: all -> 0x00d2, DONT_GENERATE, FINALLY_INSNS, SYNTHETIC, TRY_LEAVE, TryCatch #2 {, blocks: (B:3:0x0001, B:15:0x008a, B:19:0x0092, B:22:0x0097, B:18:0x008f, B:33:0x00aa, B:38:0x00b4, B:36:0x00af, B:46:0x00bf, B:51:0x00c9, B:55:0x00d1, B:54:0x00ce, B:49:0x00c4, B:31:0x00a5), top: B:63:0x0001, inners: #0, #1, #3, #8, #9 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void writeTbsDownloadInfo() {
        /*
            r10 = this;
            monitor-enter(r10)
            java.lang.String r0 = "TbsDownloadUpload"
            java.lang.String r1 = "writeTbsDownloadInfo #1"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch: java.lang.Throwable -> Ld2
            android.content.Context r0 = r10.f20920c     // Catch: java.lang.Throwable -> Ld2
            java.lang.String r1 = "download_upload"
            java.io.File r0 = a(r0, r1)     // Catch: java.lang.Throwable -> Ld2
            if (r0 != 0) goto L15
            monitor-exit(r10)
            return
        L15:
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> La3
            r2.<init>(r0)     // Catch: java.lang.Throwable -> La3
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> La3
            r3.<init>(r2)     // Catch: java.lang.Throwable -> La3
            java.util.Properties r2 = new java.util.Properties     // Catch: java.lang.Throwable -> L9f
            r2.<init>()     // Catch: java.lang.Throwable -> L9f
            r2.load(r3)     // Catch: java.lang.Throwable -> L9f
            java.util.Map<java.lang.String, java.lang.Object> r4 = r10.f20919a     // Catch: java.lang.Throwable -> L9f
            java.util.Set r4 = r4.keySet()     // Catch: java.lang.Throwable -> L9f
            java.util.Iterator r4 = r4.iterator()     // Catch: java.lang.Throwable -> L9f
        L32:
            boolean r5 = r4.hasNext()     // Catch: java.lang.Throwable -> L9f
            if (r5 == 0) goto L78
            java.lang.Object r5 = r4.next()     // Catch: java.lang.Throwable -> L9f
            java.lang.String r5 = (java.lang.String) r5     // Catch: java.lang.Throwable -> L9f
            java.util.Map<java.lang.String, java.lang.Object> r6 = r10.f20919a     // Catch: java.lang.Throwable -> L9f
            java.lang.Object r6 = r6.get(r5)     // Catch: java.lang.Throwable -> L9f
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L9f
            r7.<init>()     // Catch: java.lang.Throwable -> L9f
            java.lang.String r8 = ""
            r7.append(r8)     // Catch: java.lang.Throwable -> L9f
            r7.append(r6)     // Catch: java.lang.Throwable -> L9f
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L9f
            r2.setProperty(r5, r7)     // Catch: java.lang.Throwable -> L9f
            java.lang.String r7 = "TbsDownloadUpload"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L9f
            r8.<init>()     // Catch: java.lang.Throwable -> L9f
            java.lang.String r9 = "writeTbsDownloadInfo key is "
            r8.append(r9)     // Catch: java.lang.Throwable -> L9f
            r8.append(r5)     // Catch: java.lang.Throwable -> L9f
            java.lang.String r5 = " value is "
            r8.append(r5)     // Catch: java.lang.Throwable -> L9f
            r8.append(r6)     // Catch: java.lang.Throwable -> L9f
            java.lang.String r5 = r8.toString()     // Catch: java.lang.Throwable -> L9f
            com.tencent.smtt.utils.TbsLog.i(r7, r5)     // Catch: java.lang.Throwable -> L9f
            goto L32
        L78:
            java.util.Map<java.lang.String, java.lang.Object> r4 = r10.f20919a     // Catch: java.lang.Throwable -> L9f
            r4.clear()     // Catch: java.lang.Throwable -> L9f
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L9f
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L9f
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch: java.lang.Throwable -> L9f
            r0.<init>(r4)     // Catch: java.lang.Throwable -> L9f
            r2.store(r0, r1)     // Catch: java.lang.Throwable -> L9b
            r3.close()     // Catch: java.lang.Exception -> L8e java.lang.Throwable -> Ld2
            goto L92
        L8e:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> Ld2
        L92:
            r0.close()     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Ld2
            goto Lba
        L96:
            r0 = move-exception
        L97:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> Ld2
            goto Lba
        L9b:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto La1
        L9f:
            r0 = move-exception
            r2 = r1
        La1:
            r1 = r3
            goto La5
        La3:
            r0 = move-exception
            r2 = r1
        La5:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> Lbc
            if (r1 == 0) goto Lb2
            r1.close()     // Catch: java.lang.Exception -> Lae java.lang.Throwable -> Ld2
            goto Lb2
        Lae:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> Ld2
        Lb2:
            if (r2 == 0) goto Lba
            r2.close()     // Catch: java.lang.Exception -> Lb8 java.lang.Throwable -> Ld2
            goto Lba
        Lb8:
            r0 = move-exception
            goto L97
        Lba:
            monitor-exit(r10)
            return
        Lbc:
            r0 = move-exception
            if (r1 == 0) goto Lc7
            r1.close()     // Catch: java.lang.Exception -> Lc3 java.lang.Throwable -> Ld2
            goto Lc7
        Lc3:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> Ld2
        Lc7:
            if (r2 == 0) goto Ld1
            r2.close()     // Catch: java.lang.Exception -> Lcd java.lang.Throwable -> Ld2
            goto Ld1
        Lcd:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> Ld2
        Ld1:
            throw r0     // Catch: java.lang.Throwable -> Ld2
        Ld2:
            r0 = move-exception
            monitor-exit(r10)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsDownloadUpload.writeTbsDownloadInfo():void");
    }
}
