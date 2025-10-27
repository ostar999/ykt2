package com.huawei.hms.utils;

import android.content.Context;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.huawei.hms.support.log.HMSLog;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes4.dex */
public abstract class FileUtil {
    public static final String LOCAL_REPORT_FILE = "hms/HwMobileServiceReport.txt";
    public static final String LOCAL_REPORT_FILE_CONFIG = "hms/config.txt";
    public static final long LOCAL_REPORT_FILE_MAX_SIZE = 10240;

    /* renamed from: a, reason: collision with root package name */
    public static boolean f8117a = false;

    /* renamed from: b, reason: collision with root package name */
    public static ScheduledExecutorService f8118b = Executors.newSingleThreadScheduledExecutor();

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ File f8119a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ long f8120b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ String f8121c;

        public a(File file, long j2, String str) {
            this.f8119a = file;
            this.f8120b = j2;
            this.f8121c = str;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            File file = this.f8119a;
            if (file == null) {
                HMSLog.e("FileUtil", "In writeFile Failed to get local file.");
                return;
            }
            File parentFile = file.getParentFile();
            if (parentFile == null || !(parentFile.mkdirs() || parentFile.isDirectory())) {
                HMSLog.e("FileUtil", "In writeFile, Failed to create directory.");
                return;
            }
            RandomAccessFile randomAccessFile = null;
            try {
                try {
                    long length = this.f8119a.length();
                    if (length > this.f8120b) {
                        String canonicalPath = this.f8119a.getCanonicalPath();
                        if (!this.f8119a.delete()) {
                            HMSLog.e("FileUtil", "last file delete failed.");
                        }
                        randomAccessFile = new RandomAccessFile(new File(canonicalPath), InternalZipConstants.WRITE_MODE);
                    } else {
                        RandomAccessFile randomAccessFile2 = new RandomAccessFile(this.f8119a, InternalZipConstants.WRITE_MODE);
                        try {
                            randomAccessFile2.seek(length);
                            randomAccessFile = randomAccessFile2;
                        } catch (IOException e2) {
                            e = e2;
                            randomAccessFile = randomAccessFile2;
                            HMSLog.e("FileUtil", "writeFile exception:", e);
                            IOUtils.closeQuietly(randomAccessFile);
                        } catch (Throwable th) {
                            th = th;
                            randomAccessFile = randomAccessFile2;
                            IOUtils.closeQuietly(randomAccessFile);
                            throw th;
                        }
                    }
                    randomAccessFile.writeBytes(this.f8121c + System.getProperty("line.separator"));
                } catch (IOException e3) {
                    e = e3;
                }
                IOUtils.closeQuietly(randomAccessFile);
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    public static boolean verifyHash(String str, File file) throws Throwable {
        byte[] bArrDigest = SHA256.digest(file);
        return bArrDigest != null && HEX.encodeHexString(bArrDigest, true).equalsIgnoreCase(str);
    }

    public static void writeFile(File file, String str, long j2) {
        f8118b.execute(new a(file, j2, str));
    }

    public static void writeFileReport(Context context, File file, File file2, String str, long j2, int i2) {
        if (file != null && file.isFile() && file.exists()) {
            if (!f8117a) {
                if (file2 != null && file2.exists() && !file2.delete()) {
                    HMSLog.e("FileUtil", "file delete failed.");
                }
                f8117a = true;
            }
            writeFile(file2, str + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + j2 + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + i2, LOCAL_REPORT_FILE_MAX_SIZE);
        }
    }
}
