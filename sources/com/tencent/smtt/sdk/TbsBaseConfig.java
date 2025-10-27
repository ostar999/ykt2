package com.tencent.smtt.sdk;

import android.content.Context;
import android.util.Log;
import com.tencent.smtt.utils.TbsLog;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/* loaded from: classes6.dex */
public abstract class TbsBaseConfig {
    public static final String TAG = "TbsBaseConfig";

    /* renamed from: a, reason: collision with root package name */
    Map<String, String> f20912a;

    /* renamed from: b, reason: collision with root package name */
    private Context f20913b;

    private static File a(Context context, String str) throws IOException {
        o.a();
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

    public void clear() {
        this.f20912a.clear();
        commit();
    }

    public synchronized void commit() {
        writeTbsDownloadInfo();
    }

    public abstract String getConfigFileName();

    public Map<String, String> getPVCLocal() {
        return this.f20912a;
    }

    public void init(Context context) {
        this.f20912a = new HashMap();
        Context applicationContext = context.getApplicationContext();
        this.f20913b = applicationContext;
        if (applicationContext == null) {
            this.f20913b = context;
        }
        refreshSyncMap(context);
    }

    public synchronized void refreshSyncMap(Context context) {
        BufferedInputStream bufferedInputStream;
        Throwable th;
        String str;
        String str2;
        File fileA;
        try {
            fileA = a(this.f20913b, getConfigFileName());
            TbsLog.i(TAG, "refreshSyncMap propFile is " + fileA);
        } catch (Throwable th2) {
            bufferedInputStream = null;
            th = th2;
        }
        if (fileA == null) {
            return;
        }
        this.f20912a.clear();
        bufferedInputStream = new BufferedInputStream(new FileInputStream(fileA));
        try {
            Properties properties = new Properties();
            properties.load(bufferedInputStream);
            for (String str3 : properties.stringPropertyNames()) {
                this.f20912a.put(str3, properties.getProperty(str3));
            }
            TbsLog.i(TAG, "refreshSyncMap pv mSyncMap is " + this.f20912a.toString());
        } catch (Throwable th3) {
            th = th3;
            try {
                TbsLog.i(TAG, "refreshSyncMap stack is " + Log.getStackTraceString(th));
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (Exception e2) {
                        str = TAG;
                        str2 = "refreshSyncMap stack is " + Log.getStackTraceString(e2);
                        TbsLog.i(str, str2);
                    }
                }
            } finally {
            }
        }
        try {
            bufferedInputStream.close();
        } catch (Exception e3) {
            str = TAG;
            str2 = "refreshSyncMap stack is " + Log.getStackTraceString(e3);
            TbsLog.i(str, str2);
        }
    }

    public void update(Map<String, String> map) {
        this.f20912a.clear();
        this.f20912a.putAll(map);
        commit();
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x0158 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x019d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[Catch: all -> 0x01be, DONT_GENERATE, FINALLY_INSNS, SYNTHETIC, TRY_LEAVE, TryCatch #9 {, blocks: (B:3:0x0001, B:16:0x00cb, B:20:0x00eb, B:23:0x00f1, B:24:0x0109, B:19:0x00d0, B:35:0x0136, B:40:0x0158, B:43:0x015d, B:38:0x013b, B:49:0x017b, B:54:0x019d, B:58:0x01bd, B:57:0x01a2, B:52:0x0180, B:33:0x0119), top: B:80:0x0001, inners: #0, #1, #2, #4, #5, #7, #10 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void writeTbsDownloadInfo() {
        /*
            Method dump skipped, instructions count: 449
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsBaseConfig.writeTbsDownloadInfo():void");
    }
}
