package top.defaults.logger;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.text.StrPool;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.tencent.rtmp.sharp.jni.QLog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/* loaded from: classes9.dex */
public final class Logger {
    private static final String DEFAULT_TAG = "TopDefaultsLogger";
    private static SparseArray<String> PRIORITY_MAP = null;
    private static ExecutorService executorService = null;
    private static int level = 2;
    private static String logFilePath = null;
    private static int logFileSizeInMegabytes = 2;
    private static BufferedWriter logWriter = null;
    private static final String prevLogFileSuffix = "-prev";
    private static TimerTask scheduledCloseTask = null;
    private static TimerTask scheduledFlushTask = null;
    private static String tagPrefix = "TopDefaultsLogger";
    private static Timer timer;

    public static class LogWriterRunnable implements Runnable {
        private final String line;

        public LogWriterRunnable(String str) {
            this.line = str;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            try {
                if (Logger.logWriter == null) {
                    BufferedWriter unused = Logger.logWriter = new BufferedWriter(new FileWriter(Logger.logFilePath, true));
                }
                Logger.logWriter.append((CharSequence) this.line);
                Logger.logWriter.newLine();
            } catch (IOException unused2) {
            }
            File file = new File(Logger.logFilePath);
            if (file.length() >= Logger.logFileSizeInMegabytes * 1024 * 1024) {
                file.renameTo(new File(Logger.logFilePath + Logger.prevLogFileSuffix));
                try {
                    Logger.logWriter.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                BufferedWriter unused3 = Logger.logWriter = null;
            }
        }
    }

    static {
        SparseArray<String> sparseArray = new SparseArray<>();
        PRIORITY_MAP = sparseArray;
        sparseArray.append(2, ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        PRIORITY_MAP.append(3, QLog.TAG_REPORTLEVEL_DEVELOPER);
        PRIORITY_MAP.append(4, "I");
        PRIORITY_MAP.append(5, "W");
        PRIORITY_MAP.append(6, "E");
        PRIORITY_MAP.append(7, "X");
    }

    public static void d(String str, Object... objArr) {
        dWithTag(realTag(), str, objArr);
    }

    public static void dWithTag(String str, String str2, Object... objArr) {
        log(3, str, str2, objArr);
    }

    public static void e(String str, Object... objArr) {
        eWithTag(realTag(), str, objArr);
    }

    public static void eWithTag(String str, String str2, Object... objArr) {
        log(6, str, str2, objArr);
    }

    private static String formatMessage(String str, Object[] objArr) {
        return (objArr == null || objArr.length <= 0) ? str : String.format(str, objArr);
    }

    public static int getLevel() {
        return level;
    }

    private static String getLineInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return ".(" + stackTrace[5].getFileName() + ":" + stackTrace[5].getLineNumber() + ")";
    }

    private static String getLineInfoBypassTimber() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int stackOffsetBypassTimber = getStackOffsetBypassTimber(stackTrace);
        if (stackOffsetBypassTimber < 0) {
            return "";
        }
        return ".(" + stackTrace[stackOffsetBypassTimber].getFileName() + ":" + stackTrace[stackOffsetBypassTimber].getLineNumber() + ")";
    }

    private static int getStackOffsetBypassTimber(StackTraceElement[] stackTraceElementArr) {
        for (int i2 = 6; i2 < stackTraceElementArr.length; i2++) {
            if (!stackTraceElementArr[i2].getClassName().startsWith("timber.log.Timber")) {
                return i2;
            }
        }
        return -1;
    }

    public static void i(String str, Object... objArr) {
        iWithTag(realTag(), str, objArr);
    }

    public static void iWithTag(String str, String str2, Object... objArr) {
        log(4, str, str2, objArr);
    }

    private static void log(int i2, String str, String str2, Object... objArr) {
        if (level <= i2 || Log.isLoggable(tagPrefix, 3)) {
            String message = formatMessage(str2, objArr);
            if (i2 == 7) {
                Log.wtf(str, message);
            } else {
                Log.println(i2, str, message);
            }
            try {
                writeLogFile(priorityAbbr(i2) + "/" + str + StrPool.TAB + message);
            } catch (Exception unused) {
            }
        }
    }

    public static void logThreadFinish() {
        dWithTag(realTag(), "<<<<<<<< " + Thread.currentThread().getClass() + " finished running <<<<<<<<", new Object[0]);
    }

    public static void logThreadStart() {
        dWithTag(realTag(), ">>>>>>>> " + Thread.currentThread().getClass() + " start running >>>>>>>>", new Object[0]);
    }

    public static void logWithTimber(int i2, String str, String str2) {
        String strRealTimberTag = realTimberTag(str);
        switch (i2) {
            case 2:
                vWithTag(strRealTimberTag, str2, new Object[0]);
                break;
            case 3:
                dWithTag(strRealTimberTag, str2, new Object[0]);
                break;
            case 4:
                iWithTag(strRealTimberTag, str2, new Object[0]);
                break;
            case 5:
                wWithTag(strRealTimberTag, str2, new Object[0]);
                break;
            case 6:
                eWithTag(strRealTimberTag, str2, new Object[0]);
                break;
            case 7:
                wtfWithTag(strRealTimberTag, str2, new Object[0]);
                break;
        }
    }

    private static String priorityAbbr(int i2) {
        return PRIORITY_MAP.get(i2);
    }

    private static String realTag() {
        return tagPrefix + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + getLineInfo();
    }

    private static String realTimberTag(String str) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(str)) {
            str = tagPrefix;
        }
        sb.append(str);
        sb.append(HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
        sb.append(getLineInfoBypassTimber());
        return sb.toString();
    }

    public static void setLevel(int i2) {
        level = i2;
    }

    public static void setLogFile(String str) {
        logFilePath = str;
        if (str == null) {
            executorService.shutdown();
            timer.cancel();
            return;
        }
        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor();
        }
        if (timer == null) {
            timer = new Timer();
        }
    }

    public static void setLogFileMaxSizeInMegabytes(int i2) {
        logFileSizeInMegabytes = i2;
    }

    public static void setTagPrefix(String str) {
        tagPrefix = str;
    }

    public static void v(String str, Object... objArr) {
        vWithTag(realTag(), str, objArr);
    }

    public static void vWithTag(String str, String str2, Object... objArr) {
        log(2, str, str2, objArr);
    }

    public static void w(String str, Object... objArr) {
        wWithTag(realTag(), str, objArr);
    }

    public static void wWithTag(String str, String str2, Object... objArr) {
        log(5, str, str2, objArr);
    }

    private static void writeLogFile(String str) {
        if (logFilePath != null) {
            executorService.submit(new LogWriterRunnable(new SimpleDateFormat(DatePattern.NORM_DATETIME_MS_PATTERN, Locale.US).format(new Date()) + " " + str));
            TimerTask timerTask = scheduledFlushTask;
            if (timerTask != null) {
                timerTask.cancel();
            }
            TimerTask timerTask2 = new TimerTask() { // from class: top.defaults.logger.Logger.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    Logger.executorService.submit(new FutureTask(new Callable<Void>() { // from class: top.defaults.logger.Logger.1.1
                        @Override // java.util.concurrent.Callable
                        public Void call() throws Exception {
                            Logger.logWriter.flush();
                            return null;
                        }
                    }));
                }
            };
            scheduledFlushTask = timerTask2;
            timer.schedule(timerTask2, 1000L);
            TimerTask timerTask3 = scheduledCloseTask;
            if (timerTask3 != null) {
                timerTask3.cancel();
            }
            TimerTask timerTask4 = new TimerTask() { // from class: top.defaults.logger.Logger.2
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    Logger.executorService.submit(new FutureTask(new Callable<Void>() { // from class: top.defaults.logger.Logger.2.1
                        @Override // java.util.concurrent.Callable
                        public Void call() throws Exception {
                            Logger.logWriter.close();
                            BufferedWriter unused = Logger.logWriter = null;
                            return null;
                        }
                    }));
                }
            };
            scheduledCloseTask = timerTask4;
            timer.schedule(timerTask4, 60000L);
        }
    }

    public static void wtf(String str, Object... objArr) {
        wtfWithTag(realTag(), str, objArr);
    }

    public static void wtfWithTag(String str, String str2, Object... objArr) {
        log(7, str, str2, objArr);
    }
}
