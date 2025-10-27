package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

@KeepForSdk
/* loaded from: classes3.dex */
public class ProcessUtils {
    private static String zzhf;
    private static int zzhg;

    private ProcessUtils() {
    }

    @KeepForSdk
    @Nullable
    public static String getMyProcessName() {
        if (zzhf == null) {
            if (zzhg == 0) {
                zzhg = Process.myPid();
            }
            zzhf = zzd(zzhg);
        }
        return zzhf;
    }

    @Nullable
    private static String zzd(int i2) throws Throwable {
        Throwable th;
        BufferedReader bufferedReaderZzk;
        String strTrim = null;
        if (i2 <= 0) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder(25);
            sb.append("/proc/");
            sb.append(i2);
            sb.append("/cmdline");
            bufferedReaderZzk = zzk(sb.toString());
        } catch (IOException unused) {
            bufferedReaderZzk = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReaderZzk = null;
        }
        try {
            strTrim = bufferedReaderZzk.readLine().trim();
            IOUtils.closeQuietly(bufferedReaderZzk);
        } catch (IOException unused2) {
            IOUtils.closeQuietly(bufferedReaderZzk);
            return strTrim;
        } catch (Throwable th3) {
            th = th3;
            IOUtils.closeQuietly(bufferedReaderZzk);
            throw th;
        }
        return strTrim;
    }

    private static BufferedReader zzk(String str) throws IOException {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            return new BufferedReader(new FileReader(str));
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }
}
