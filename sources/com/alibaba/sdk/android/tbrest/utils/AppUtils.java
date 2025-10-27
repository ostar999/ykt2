package com.alibaba.sdk.android.tbrest.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public class AppUtils {

    public interface ReaderListener {
        boolean onReadLine(String str);
    }

    public static void closeQuietly(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e2) {
                LogUtil.e("close.", e2);
            }
        }
    }

    public static String dumpMeminfo(Context context) {
        Integer numValueOf;
        String str;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            if (activityManager != null) {
                activityManager.getMemoryInfo(memoryInfo);
                numValueOf = Integer.valueOf((int) (memoryInfo.threshold >> 10));
            } else {
                numValueOf = null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("JavaTotal:");
            sb.append(Runtime.getRuntime().totalMemory());
            sb.append(" JavaFree:");
            sb.append(Runtime.getRuntime().freeMemory());
            sb.append(" NativeHeap:");
            sb.append(Debug.getNativeHeapSize());
            sb.append(" NativeAllocated:");
            sb.append(Debug.getNativeHeapAllocatedSize());
            sb.append(" NativeFree:");
            sb.append(Debug.getNativeHeapFreeSize());
            sb.append(" threshold:");
            if (numValueOf != null) {
                str = numValueOf + " KB";
            } else {
                str = "not valid";
            }
            sb.append(str);
            return sb.toString();
        } catch (Exception e2) {
            LogUtil.e("dumpMeminfo", e2);
            return "";
        }
    }

    public static String dumpStorage(Context context) {
        boolean zEquals;
        StringBuilder sb = new StringBuilder();
        try {
            zEquals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e2) {
            LogUtil.w("hasSDCard", e2);
            zEquals = false;
        }
        try {
        } catch (Exception e3) {
            LogUtil.w("installSDCard", e3);
        }
        boolean z2 = (context.getApplicationInfo().flags & 262144) != 0;
        sb.append("hasSDCard: " + zEquals + "\n");
        sb.append("installSDCard: " + z2 + "\n");
        try {
            File rootDirectory = Environment.getRootDirectory();
            if (rootDirectory != null) {
                long[] sizes = getSizes(rootDirectory.getAbsolutePath());
                sb.append("RootDirectory: " + rootDirectory.getAbsolutePath() + " ");
                sb.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", Long.valueOf(sizes[0]), Long.valueOf(sizes[1]), Long.valueOf(sizes[2])));
            }
            File dataDirectory = Environment.getDataDirectory();
            if (dataDirectory != null) {
                long[] sizes2 = getSizes(dataDirectory.getAbsolutePath());
                sb.append("DataDirectory: " + dataDirectory.getAbsolutePath() + " ");
                sb.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", Long.valueOf(sizes2[0]), Long.valueOf(sizes2[1]), Long.valueOf(sizes2[2])));
            }
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            if (dataDirectory != null) {
                sb.append("ExternalStorageDirectory: " + externalStorageDirectory.getAbsolutePath() + " ");
                long[] sizes3 = getSizes(externalStorageDirectory.getAbsolutePath());
                sb.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", Long.valueOf(sizes3[0]), Long.valueOf(sizes3[1]), Long.valueOf(sizes3[2])));
            }
            File downloadCacheDirectory = Environment.getDownloadCacheDirectory();
            if (downloadCacheDirectory != null) {
                sb.append("DownloadCacheDirectory: " + downloadCacheDirectory.getAbsolutePath() + " ");
                long[] sizes4 = getSizes(downloadCacheDirectory.getAbsolutePath());
                sb.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", Long.valueOf(sizes4[0]), Long.valueOf(sizes4[1]), Long.valueOf(sizes4[2])));
            }
        } catch (Exception e4) {
            LogUtil.e("getSizes", e4);
        }
        return sb.toString();
    }

    public static String dumpThread(Thread thread) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(String.format("Thread Name: '%s'\n", thread.getName()));
            sb.append(String.format("\"%s\" prio=%d tid=%d %s\n", thread.getName(), Integer.valueOf(thread.getPriority()), Long.valueOf(thread.getId()), thread.getState()));
            for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
                sb.append(String.format("\tat %s\n", stackTraceElement.toString()));
            }
        } catch (Exception e2) {
            LogUtil.e("dumpThread", e2);
        }
        return sb.toString();
    }

    public static String getGMT8Time(long j2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return simpleDateFormat.format(new Date(j2));
        } catch (Exception e2) {
            LogUtil.e("getGMT8Time", e2);
            return "";
        }
    }

    public static String getMeminfo() {
        return readFully(new File("/proc/meminfo")).trim();
    }

    public static String getMyProcessNameByAppProcessInfo(Context context) {
        if (context == null) {
            return null;
        }
        int iMyPid = Process.myPid();
        try {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == iMyPid) {
                    return runningAppProcessInfo.processName;
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getMyProcessNameByCmdline() {
        try {
            return readLine("/proc/self/cmdline").trim();
        } catch (Exception e2) {
            LogUtil.e("get my process name by cmd line failure .", e2);
            return null;
        }
    }

    public static String getMyStatus() {
        return readFully(new File("/proc/self/status")).trim();
    }

    private static long[] getSizes(String str) {
        long[] jArr = {-1, -1, -1};
        try {
            StatFs statFs = new StatFs(str);
            long blockSizeLong = statFs.getBlockSizeLong();
            long blockCountLong = statFs.getBlockCountLong();
            long freeBlocksLong = statFs.getFreeBlocksLong();
            long availableBlocksLong = statFs.getAvailableBlocksLong();
            jArr[0] = blockCountLong * blockSizeLong;
            jArr[1] = freeBlocksLong * blockSizeLong;
            jArr[2] = blockSizeLong * availableBlocksLong;
        } catch (Exception e2) {
            LogUtil.e("getSizes", e2);
        }
        return jArr;
    }

    public static Boolean isBackgroundRunning(Context context) {
        try {
            return Integer.parseInt(readLine("/proc/self/oom_adj").trim()) == 0 ? Boolean.FALSE : Boolean.TRUE;
        } catch (Exception unused) {
            return Boolean.FALSE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v9 */
    public static String readFully(File file) throws Throwable {
        FileInputStream fileInputStream;
        Exception e2;
        InputStreamReader inputStreamReader;
        StringBuilder sb = new StringBuilder();
        ?? r12 = 0;
        r12 = 0;
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Exception e3) {
                fileInputStream = null;
                e2 = e3;
                inputStreamReader = null;
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
            }
            try {
                inputStreamReader = new InputStreamReader(fileInputStream);
            } catch (Exception e4) {
                e2 = e4;
                inputStreamReader = null;
            } catch (Throwable th2) {
                th = th2;
                closeQuietly(r12);
                closeQuietly(fileInputStream);
                throw th;
            }
            try {
                char[] cArr = new char[4096];
                while (true) {
                    int i2 = inputStreamReader.read(cArr);
                    if (-1 == i2) {
                        break;
                    }
                    sb.append(cArr, 0, i2);
                }
            } catch (Exception e5) {
                e2 = e5;
                LogUtil.e("readFully.", e2);
                closeQuietly(inputStreamReader);
                closeQuietly(fileInputStream);
                return sb.toString();
            }
            closeQuietly(inputStreamReader);
            closeQuietly(fileInputStream);
            return sb.toString();
        } catch (Throwable th3) {
            th = th3;
            r12 = file;
        }
    }

    public static String readLine(String str) {
        return readLine(new File(str));
    }

    public static String readLineAndDel(File file) throws Throwable {
        try {
            String line = readLine(file);
            file.delete();
            return line;
        } catch (Exception e2) {
            LogUtil.e("readLineAndDel", e2);
            return null;
        }
    }

    public static List<String> readLines(File file, int i2) throws Throwable {
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                int i3 = 0;
                while (true) {
                    try {
                        String line = bufferedReader2.readLine();
                        if (line == null) {
                            break;
                        }
                        i3++;
                        arrayList.add(line);
                        if (i2 > 0 && i3 >= i2) {
                            break;
                        }
                    } catch (IOException e2) {
                        e = e2;
                        bufferedReader = bufferedReader2;
                        LogUtil.e("readLine", e);
                        closeQuietly(bufferedReader);
                        return arrayList;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        closeQuietly(bufferedReader);
                        throw th;
                    }
                }
                closeQuietly(bufferedReader2);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
        }
        return arrayList;
    }

    public static boolean writeFile(File file, String str) {
        return writeFile(file, str, false);
    }

    public static String readLine(File file) throws Throwable {
        List<String> lines = readLines(file, 1);
        return !lines.isEmpty() ? lines.get(0) : "";
    }

    public static boolean writeFile(File file, String str, boolean z2) throws Throwable {
        FileWriter fileWriter = null;
        try {
            try {
                FileWriter fileWriter2 = new FileWriter(file, z2);
                try {
                    fileWriter2.write(str);
                    fileWriter2.flush();
                    closeQuietly(fileWriter2);
                    return true;
                } catch (IOException e2) {
                    e = e2;
                    fileWriter = fileWriter2;
                    LogUtil.e("writeFile", e);
                    closeQuietly(fileWriter);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    fileWriter = fileWriter2;
                    closeQuietly(fileWriter);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
        }
    }

    public static void readLine(File file, ReaderListener readerListener) throws Throwable {
        BufferedReader bufferedReader;
        String line;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } catch (IOException e2) {
                e = e2;
            }
            do {
                try {
                    line = bufferedReader.readLine();
                } catch (IOException e3) {
                    e = e3;
                    bufferedReader2 = bufferedReader;
                    LogUtil.e("readLine", e);
                    closeQuietly(bufferedReader2);
                    return;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader2 = bufferedReader;
                    closeQuietly(bufferedReader2);
                    throw th;
                }
                if (line == null) {
                    closeQuietly(bufferedReader);
                    return;
                }
            } while (!readerListener.onReadLine(line));
            closeQuietly(bufferedReader);
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
