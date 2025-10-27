package com.plv.foundationsdk.log;

import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.utils.PLVUtils;
import com.plv.thirdpart.blankj.utilcode.util.CloseUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes4.dex */
public class PLVCommonLog {
    private static final int ASSERT = 7;
    private static final int DEBUG = 3;
    private static int DEBUG_ALL = 7;
    private static boolean DEBUG_MODEL = false;
    private static final int ERROR = 6;
    public static final int FROM_LOGCAT = 4;
    private static final int INFO = 4;
    private static final String LOG_LAST_FILE = "PLVCommonLog_log_last.txt";
    private static final int LOG_LEVEL = 2;
    private static long LOG_MAXSIZE = 3145728;
    private static final String LOG_NOW_FILE = "PLVCommonLog_log_now.txt";
    private static final String LOG_TEMP_FILE = "PLVCommonLog_log.txt";
    private static final String TAG = "PLVCommonLog";
    public static final int TO_CONSOLE = 1;
    public static final int TO_FILE = 2;
    public static final int TO_NONE = 0;
    private static final int VERBOSE = 2;
    private static final int WARN = 5;
    private static String mAppPath;
    private static long mFileSize;
    private static OutputStream mLogStream;
    private static PaintLogThread mPaintLogThread;
    private static final Object[] Lock = new Object[0];
    private static final ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    private static final StringBuilder mBuffer = new StringBuilder();

    public static class PaintLogThread extends Thread {
        int mEmptyMsg;
        Process mProcess;
        boolean mStop = false;

        /* JADX WARN: Removed duplicated region for block: B:33:0x0087 A[PHI: r0
          0x0087: PHI (r0v5 java.lang.Process) = (r0v4 java.lang.Process), (r0v7 java.lang.Process) binds: [B:32:0x0085, B:16:0x0069] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x00a2  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() throws java.lang.Throwable {
            /*
                r8 = this;
                java.lang.String r0 = "close paint log"
                java.lang.String r1 = "PLVCommonLog"
                java.lang.String r2 = "EXCEPTION"
                r3 = 0
                java.util.ArrayList r4 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                r4.<init>()     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.String r5 = "logcat"
                r4.add(r5)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.String r5 = "-d"
                r4.add(r5)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.String r5 = "time"
                r4.add(r5)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.String r5 = "-s"
                r4.add(r5)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.String r5 = "tag:W"
                r4.add(r5)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.Runtime r5 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                int r6 = r4.size()     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.String[] r6 = new java.lang.String[r6]     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.Object[] r4 = r4.toArray(r6)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.String[] r4 = (java.lang.String[]) r4     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.Process r4 = r5.exec(r4)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                r8.mProcess = r4     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.lang.Process r6 = r8.mProcess     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                java.io.InputStream r6 = r6.getInputStream()     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                r5.<init>(r6)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                r4.<init>(r5)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
            L4b:
                boolean r5 = r8.mStop     // Catch: java.io.IOException -> L6c java.lang.Throwable -> L90
                if (r5 != 0) goto L5c
                java.lang.String r5 = r4.readLine()     // Catch: java.io.IOException -> L6c java.lang.Throwable -> L90
                if (r5 == 0) goto L5c
                java.lang.String r6 = "SysLog"
                r7 = 2
                com.plv.foundationsdk.log.PLVCommonLog.access$000(r6, r5, r7)     // Catch: java.io.IOException -> L6c java.lang.Throwable -> L90
                goto L4b
            L5c:
                android.util.Log.d(r1, r0)
                r4.close()     // Catch: java.io.IOException -> L63
                goto L67
            L63:
                r0 = move-exception
                android.util.Log.e(r2, r2, r0)
            L67:
                java.lang.Process r0 = r8.mProcess
                if (r0 == 0) goto L8a
                goto L87
            L6c:
                r5 = move-exception
                goto L73
            L6e:
                r5 = move-exception
                r4 = r3
                goto L91
            L71:
                r5 = move-exception
                r4 = r3
            L73:
                android.util.Log.e(r2, r2, r5)     // Catch: java.lang.Throwable -> L90
                android.util.Log.d(r1, r0)
                if (r4 == 0) goto L83
                r4.close()     // Catch: java.io.IOException -> L7f
                goto L83
            L7f:
                r0 = move-exception
                android.util.Log.e(r2, r2, r0)
            L83:
                java.lang.Process r0 = r8.mProcess
                if (r0 == 0) goto L8a
            L87:
                r0.destroy()
            L8a:
                r8.mProcess = r3
                com.plv.foundationsdk.log.PLVCommonLog.access$102(r3)
                return
            L90:
                r5 = move-exception
            L91:
                android.util.Log.d(r1, r0)
                if (r4 == 0) goto L9e
                r4.close()     // Catch: java.io.IOException -> L9a
                goto L9e
            L9a:
                r0 = move-exception
                android.util.Log.e(r2, r2, r0)
            L9e:
                java.lang.Process r0 = r8.mProcess
                if (r0 == 0) goto La5
                r0.destroy()
            La5:
                r8.mProcess = r3
                com.plv.foundationsdk.log.PLVCommonLog.access$102(r3)
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.plv.foundationsdk.log.PLVCommonLog.PaintLogThread.run():void");
        }

        public void shutdown() {
            this.mStop = true;
            Process process = this.mProcess;
            if (process != null) {
                process.destroy();
                this.mProcess = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void LogToFile(String str, String str2, int i2) {
        synchronized (Lock) {
            OutputStream outputStreamOpenLogFileOutStream = openLogFileOutStream();
            try {
                if (outputStreamOpenLogFileOutStream != null) {
                    try {
                        byte[] bytes = getLogStr(str, str2).getBytes("utf-8");
                        if (mFileSize < LOG_MAXSIZE) {
                            outputStreamOpenLogFileOutStream.write(bytes);
                            outputStreamOpenLogFileOutStream.write("\r\n".getBytes());
                            outputStreamOpenLogFileOutStream.flush();
                            mFileSize += bytes.length;
                        } else {
                            closeLogFileOutStream();
                            renameLogFile();
                        }
                    } catch (UnsupportedEncodingException e2) {
                        Log.e(TAG, "LogToFile: " + e2.getMessage());
                    } catch (IOException e3) {
                        Log.e(TAG, "LogToFile: " + e3.getMessage());
                    }
                }
            } finally {
                closeLogFileOutStream();
            }
        }
    }

    public static void backLogFile() {
        File fileOpenAbsoluteFile;
        synchronized (Lock) {
            try {
                closeLogFileOutStream();
                fileOpenAbsoluteFile = openAbsoluteFile(LOG_NOW_FILE);
            } catch (IOException e2) {
                Log.w(TAG, "backLogFile fail:" + e2.toString());
                Log.e(PLVLogType.EXCEPTION, PLVLogType.EXCEPTION, e2);
            }
            if (fileOpenAbsoluteFile == null) {
                throw new IOException("destFile == null");
            }
            if (fileOpenAbsoluteFile.exists() && !fileOpenAbsoluteFile.delete()) {
                d(TAG, " backLogFile ,delete destFile fail");
            }
            try {
                if (!fileOpenAbsoluteFile.createNewFile()) {
                    d(TAG, "backLogFile,createNewFile fail");
                }
                File fileOpenAbsoluteFile2 = openAbsoluteFile(LOG_LAST_FILE);
                File fileOpenAbsoluteFile3 = openAbsoluteFile(LOG_TEMP_FILE);
                if (fileOpenAbsoluteFile2 == null || fileOpenAbsoluteFile3 == null) {
                    throw new IOException("src1 or  src2 == null");
                }
                copyFile(fileOpenAbsoluteFile2, fileOpenAbsoluteFile3, fileOpenAbsoluteFile, true);
                openLogFileOutStream();
            } catch (IOException e3) {
                Log.e(PLVLogType.EXCEPTION, PLVLogType.EXCEPTION, e3);
            }
        }
    }

    private static void closeLogFileOutStream() throws IOException {
        try {
            OutputStream outputStream = mLogStream;
            if (outputStream != null) {
                outputStream.close();
                mLogStream = null;
                mFileSize = 0L;
            }
        } catch (IOException e2) {
            Log.e(TAG, "closeLogFileOutStream: " + e2.getMessage());
        }
    }

    private static void copyFile(File file, File file2, File file3, boolean z2) throws Throwable {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        if (file3.exists() && !file3.delete()) {
            d(TAG, " copyFile ,copy file fail");
        }
        FileInputStream fileInputStream3 = null;
        try {
            fileOutputStream = new FileOutputStream(file3);
            try {
                byte[] bArr = new byte[10240];
                long j2 = 0;
                if (file.exists()) {
                    long length = file.length();
                    fileInputStream2 = new FileInputStream(file);
                    long j3 = 0;
                    while (j3 < length) {
                        try {
                            int i2 = fileInputStream2.read(bArr);
                            fileOutputStream.write(bArr, 0, i2);
                            j3 += i2;
                        } catch (Throwable th) {
                            th = th;
                            fileInputStream = null;
                            fileInputStream3 = fileInputStream2;
                            CloseUtils.closeIO(fileInputStream3, fileInputStream, fileOutputStream);
                            throw th;
                        }
                    }
                } else {
                    fileInputStream2 = null;
                }
                if (file2.exists()) {
                    long length2 = file2.length();
                    fileInputStream = new FileInputStream(file2);
                    while (j2 < length2) {
                        try {
                            int i3 = fileInputStream.read(bArr);
                            fileOutputStream.write(bArr, 0, i3);
                            j2 += i3;
                        } catch (Throwable th2) {
                            th = th2;
                            fileInputStream3 = fileInputStream2;
                            CloseUtils.closeIO(fileInputStream3, fileInputStream, fileOutputStream);
                            throw th;
                        }
                    }
                    fileInputStream3 = fileInputStream;
                }
                CloseUtils.closeIO(fileInputStream2, fileInputStream3, fileOutputStream);
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
            fileInputStream = null;
        }
    }

    public static void d(String str, String str2, Object... objArr) {
        if (DEBUG_MODEL) {
            d(str, String.format(str2, objArr));
        }
    }

    public static void e(String str, String str2) {
        log(str, str2, 6);
    }

    public static void exception(Throwable th) {
        if (th == null) {
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
        Log.e(TAG, "exception: " + th.getMessage(), th);
        printWriter.close();
        log(PLVLogType.EXCEPTION, new String(byteArrayOutputStream.toByteArray()), 6);
    }

    public static String format(String str, Object... objArr) {
        if (objArr == null) {
            objArr = new Object[1];
        }
        int length = objArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            Object obj = objArr[i2];
            str = str.replaceFirst("\\{\\}", obj == null ? "null" : obj.toString());
        }
        return str;
    }

    public static int getDebugAll() {
        return DEBUG_ALL;
    }

    private static String getLogStr(String str, String str2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        StringBuilder sb = mBuffer;
        sb.setLength(0);
        sb.append(StrPool.BRACKET_START);
        sb.append(str);
        sb.append(" : ");
        sb.append(calendar.get(2) + 1);
        sb.append("-");
        sb.append(calendar.get(5));
        sb.append(" ");
        sb.append(calendar.get(11));
        sb.append(":");
        sb.append(calendar.get(12));
        sb.append(":");
        sb.append(calendar.get(13));
        sb.append(":");
        sb.append(calendar.get(14));
        sb.append("] ");
        sb.append(str2);
        return sb.toString();
    }

    public static void i(String str, String str2) {
        log(str, str2, 4);
    }

    public static void init() {
        synchronized (Lock) {
            mAppPath = PLVUtils.getExternalFilePath("PolyvLog");
            File file = new File(mAppPath);
            if (!file.exists()) {
                file.mkdir();
            }
        }
        if ((getDebugAll() & 4) == 0 || mPaintLogThread != null) {
            return;
        }
        PaintLogThread paintLogThread = new PaintLogThread();
        mPaintLogThread = paintLogThread;
        paintLogThread.start();
    }

    public static void initPath(String str) {
        synchronized (Lock) {
            mAppPath = str;
            File file = new File(mAppPath);
            if (!file.exists()) {
                file.mkdir();
            }
        }
        if ((getDebugAll() & 4) == 0 || mPaintLogThread != null) {
            return;
        }
        PaintLogThread paintLogThread = new PaintLogThread();
        mPaintLogThread = paintLogThread;
        paintLogThread.start();
    }

    private static void log(final String str, final String str2, final int i2) {
        if (str == null) {
            str = "TAG_NULL";
        }
        if (str2 == null) {
            str2 = "MSG_NULL";
        }
        if (i2 >= 2) {
            if ((getDebugAll() & 1) != 0) {
                logToConsole(str, str2, i2);
            }
            if ((2 & getDebugAll()) != 0) {
                mExecutorService.submit(new Runnable() { // from class: com.plv.foundationsdk.log.PLVCommonLog.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PLVCommonLog.LogToFile(str, str2, i2);
                    }
                });
            }
        }
    }

    private static void logToConsole(String str, String str2, int i2) {
        if (i2 == 2) {
            Log.v(str, str2);
            return;
        }
        if (i2 == 3) {
            Log.d(str, str2);
            return;
        }
        if (i2 == 4) {
            Log.i(str, str2);
        } else if (i2 == 5) {
            Log.w(str, str2);
        } else {
            if (i2 != 6) {
                return;
            }
            Log.e(str, str2);
        }
    }

    private static File openAbsoluteFile(String str) {
        String str2 = mAppPath;
        if (str2 == null || str2.length() == 0) {
            return null;
        }
        return new File(mAppPath + File.separator + str);
    }

    private static OutputStream openLogFileOutStream() {
        File fileOpenAbsoluteFile;
        if (mLogStream == null) {
            try {
                String str = mAppPath;
                if (str == null || str.length() == 0 || (fileOpenAbsoluteFile = openAbsoluteFile(LOG_TEMP_FILE)) == null) {
                    return null;
                }
                if (fileOpenAbsoluteFile.exists()) {
                    mLogStream = new FileOutputStream(fileOpenAbsoluteFile, true);
                    mFileSize = fileOpenAbsoluteFile.length();
                } else {
                    mLogStream = new FileOutputStream(fileOpenAbsoluteFile);
                    mFileSize = 0L;
                }
            } catch (FileNotFoundException e2) {
                Log.e(TAG, "openLogFileOutStream: " + e2.getMessage());
            }
        }
        return mLogStream;
    }

    private static void renameLogFile() {
        synchronized (Lock) {
            File fileOpenAbsoluteFile = openAbsoluteFile(LOG_TEMP_FILE);
            File fileOpenAbsoluteFile2 = openAbsoluteFile(LOG_LAST_FILE);
            if (fileOpenAbsoluteFile2 == null) {
                return;
            }
            try {
                if (fileOpenAbsoluteFile2.exists() && !fileOpenAbsoluteFile2.delete()) {
                    d(TAG, "renameLogFile ,delete destFile fail");
                }
                if (fileOpenAbsoluteFile != null && !fileOpenAbsoluteFile.renameTo(fileOpenAbsoluteFile2)) {
                    d(TAG, " renameLogFile ,renameTo destFile fail");
                }
            } catch (SecurityException e2) {
                Log.e(TAG, "renameLogFile: " + e2.getMessage());
            }
        }
    }

    public static void setDebug(boolean z2) {
        DEBUG_MODEL = z2;
    }

    public static void setDebugAll(int i2) {
        DEBUG_ALL = i2;
    }

    public static void setLogFileMaxSize(long j2) {
        LOG_MAXSIZE = j2;
    }

    public static void v(String str, String str2) {
        log(str, str2, 2);
    }

    public static void w(String str, String str2) {
        log(str, str2, 5);
    }

    private static boolean zip(File file, File file2) throws Throwable {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        if (!file.exists()) {
            return false;
        }
        if (!file2.getParentFile().exists()) {
            file2.getParentFile().mkdir();
        }
        ZipOutputStream zipOutputStream = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    fileOutputStream = new FileOutputStream(file2);
                    try {
                        ZipOutputStream zipOutputStream2 = new ZipOutputStream(fileOutputStream);
                        try {
                            byte[] bArr = new byte[1024];
                            zipOutputStream2.putNextEntry(new ZipEntry(file.getName()));
                            while (true) {
                                int i2 = fileInputStream.read(bArr);
                                if (i2 <= 0) {
                                    CloseUtils.closeIO(zipOutputStream2, fileInputStream, fileOutputStream);
                                    return true;
                                }
                                zipOutputStream2.write(bArr, 0, i2);
                            }
                        } catch (FileNotFoundException e2) {
                            e = e2;
                            zipOutputStream = zipOutputStream2;
                            Log.e(PLVLogType.EXCEPTION, PLVLogType.EXCEPTION, e);
                            CloseUtils.closeIO(zipOutputStream, fileInputStream, fileOutputStream);
                            return false;
                        } catch (IOException e3) {
                            e = e3;
                            zipOutputStream = zipOutputStream2;
                            Log.e(PLVLogType.EXCEPTION, PLVLogType.EXCEPTION, e);
                            CloseUtils.closeIO(zipOutputStream, fileInputStream, fileOutputStream);
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            zipOutputStream = zipOutputStream2;
                            CloseUtils.closeIO(zipOutputStream, fileInputStream, fileOutputStream);
                            throw th;
                        }
                    } catch (FileNotFoundException e4) {
                        e = e4;
                    } catch (IOException e5) {
                        e = e5;
                    }
                } catch (FileNotFoundException e6) {
                    e = e6;
                    fileOutputStream = null;
                } catch (IOException e7) {
                    e = e7;
                    fileOutputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = null;
                }
            } catch (FileNotFoundException e8) {
                e = e8;
                fileInputStream = null;
                fileOutputStream = null;
            } catch (IOException e9) {
                e = e9;
                fileInputStream = null;
                fileOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
                fileOutputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static boolean zipLogFile(String str) {
        backLogFile();
        File fileOpenAbsoluteFile = openAbsoluteFile(str);
        if (fileOpenAbsoluteFile == null) {
            return false;
        }
        if (fileOpenAbsoluteFile.exists() && !fileOpenAbsoluteFile.delete()) {
            d(TAG, " zipLogFile ,delete destFile fail");
        }
        try {
            if (!fileOpenAbsoluteFile.createNewFile()) {
                d(TAG, " zipLogFile ,delete createNewFile fail");
            }
            File fileOpenAbsoluteFile2 = openAbsoluteFile(LOG_NOW_FILE);
            if (fileOpenAbsoluteFile2 == null) {
                return false;
            }
            return zip(fileOpenAbsoluteFile2, fileOpenAbsoluteFile);
        } catch (IOException e2) {
            Log.e(PLVLogType.EXCEPTION, PLVLogType.EXCEPTION, e2);
            return false;
        }
    }

    public static void d(String str, String str2) {
        if (DEBUG_MODEL) {
            log(str, str2, 3);
        }
    }
}
