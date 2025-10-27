package com.psychiatrygarden.utils;

import android.os.Environment;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.umeng.analytics.pro.am;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes6.dex */
public class LogUtils {
    public static final int LOGTYPE_ALL = 2;
    public static final int LOGTYPE_FILE = 1;
    public static final int LOGTYPE_LOGCAT = 0;
    public static final int LOGTYPE_NONE = 3;
    private static final String file_idle = "/";
    private static String file_name = "";
    private static String log_fullpath = "";
    private static int log_type = 0;
    private static String path_name = "XiYiLog";
    private static String rootdir = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");

    private static String GetLogPath() {
        if (path_name.equals("")) {
            return rootdir + "/";
        }
        String str = rootdir + "/" + path_name + "/";
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            return str;
        } catch (Exception unused) {
            return rootdir + "/";
        }
    }

    private static boolean InitEnvironment(String preFix) throws IOException {
        if (!hasSdcard()) {
            return false;
        }
        String strGetLogPath = GetLogPath();
        String str = preFix + StrPool.DOT + DATE_FORMAT_DATE.format(new Date()) + ".txt";
        if (file_name.equals("") || !file_name.equals(str)) {
            file_name = str;
        }
        File file = new File(strGetLogPath + file_name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException unused) {
                return false;
            }
        }
        log_fullpath = strGetLogPath + file_name;
        return true;
    }

    public static void SetFileName(String filename) {
        file_name = filename;
    }

    public static void SetLogPath(String logpath) {
        path_name = logpath;
    }

    public static void SetLogType(int logtype) {
        log_type = logtype;
    }

    private static void WriteFileLog(String content, String type) throws Throwable {
        FileWriter fileWriter;
        String str = DEFAULT_DATE_FORMAT.format(new Date());
        FileWriter fileWriter2 = null;
        try {
            try {
                fileWriter = new FileWriter(log_fullpath, true);
            } catch (Exception unused) {
            } catch (Throwable th) {
                th = th;
            }
            try {
                fileWriter.write(str + " " + type + " " + content + "\r\n");
                fileWriter.close();
                fileWriter.close();
            } catch (Exception unused2) {
                fileWriter2 = fileWriter;
                if (fileWriter2 != null) {
                    fileWriter2.close();
                }
            } catch (Throwable th2) {
                th = th2;
                fileWriter2 = fileWriter;
                if (fileWriter2 != null) {
                    try {
                        fileWriter2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public static void d(String tag, String content) {
        int i2 = log_type;
        if (i2 == 3) {
            return;
        }
        if ((i2 == 1 || i2 == 2) && !InitEnvironment(tag)) {
            return;
        }
        int i3 = log_type;
        if (i3 == 0) {
            Log.d(tag, content);
            return;
        }
        if (i3 == 1) {
            WriteFileLog(content, "d");
        } else {
            if (i3 != 2) {
                return;
            }
            Log.d(tag, content);
            WriteFileLog(content, "d");
        }
    }

    public static void e(String tag, String content) {
        int i2 = log_type;
        if (i2 == 3) {
            return;
        }
        if ((i2 == 1 || i2 == 2) && !InitEnvironment(tag)) {
            return;
        }
        int i3 = log_type;
        if (i3 == 0) {
            Log.e(tag, content);
            return;
        }
        if (i3 == 1) {
            WriteFileLog(content, AliyunLogKey.KEY_EVENT);
        } else {
            if (i3 != 2) {
                return;
            }
            Log.e(tag, content);
            WriteFileLog(content, AliyunLogKey.KEY_EVENT);
        }
    }

    public static void ee(String tag, String content) throws Throwable {
        if (InitEnvironment(tag)) {
            WriteFileLog(content, AliyunLogKey.KEY_EVENT);
        }
    }

    private static boolean hasSdcard() {
        try {
            return Environment.getExternalStorageState().equals("mounted");
        } catch (Exception unused) {
            return false;
        }
    }

    public static void i(String tag, String content) throws Throwable {
        int i2 = log_type;
        if (i2 == 3) {
            return;
        }
        if ((i2 == 1 || i2 == 2) && !InitEnvironment(tag)) {
            return;
        }
        int i3 = log_type;
        if (i3 == 0) {
            Log.i(tag, content);
            return;
        }
        if (i3 == 1) {
            WriteFileLog(content, am.aC);
        } else {
            if (i3 != 2) {
                return;
            }
            Log.i(tag, content);
            WriteFileLog(content, am.aC);
        }
    }

    public static void v(String tag, String content) throws Throwable {
        int i2 = log_type;
        if (i2 == 3) {
            return;
        }
        if ((i2 == 1 || i2 == 2) && !InitEnvironment(tag)) {
            return;
        }
        int i3 = log_type;
        if (i3 == 0) {
            Log.v(tag, content);
            return;
        }
        if (i3 == 1) {
            WriteFileLog(content, "v");
        } else {
            if (i3 != 2) {
                return;
            }
            Log.v(tag, content);
            WriteFileLog(content, "v");
        }
    }

    public static void w(String tag, String content) throws Throwable {
        int i2 = log_type;
        if (i2 == 3) {
            return;
        }
        if ((i2 == 1 || i2 == 2) && !InitEnvironment(tag)) {
            return;
        }
        int i3 = log_type;
        if (i3 == 0) {
            Log.w(tag, content);
            return;
        }
        if (i3 == 1) {
            WriteFileLog(content, "w");
        } else {
            if (i3 != 2) {
                return;
            }
            Log.w(tag, content);
            WriteFileLog(content, "w");
        }
    }
}
