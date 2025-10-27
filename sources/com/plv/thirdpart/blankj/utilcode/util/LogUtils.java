package com.plv.thirdpart.blankj.utilcode.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.IntRange;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.text.StrPool;
import com.mobile.auth.BuildConfig;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public final class LogUtils {
    public static final int A = 7;
    private static final String ARGS = "args";
    private static final String BOTTOM_BORDER = "╚═══════════════════════════════════════════════════════════════════════════════════════════════════";
    public static final int D = 3;
    public static final int E = 6;
    private static final int FILE = 16;
    public static final int I = 4;
    private static final int JSON = 32;
    private static final String LEFT_BORDER = "║ ";
    private static final int MAX_LEN = 4000;
    private static final String NULL = "null";
    private static final String SPLIT_BORDER = "╟───────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final String TOP_BORDER = "╔═══════════════════════════════════════════════════════════════════════════════════════════════════";
    public static final int V = 2;
    public static final int W = 5;
    private static final int XML = 48;
    private static String sDefaultDir;
    private static String sDir;
    private static ExecutorService sExecutor;
    private static final char[] T = {'V', 'D', 'I', 'W', 'E', 'A'};
    private static String sFilePrefix = "util";
    private static boolean sLogSwitch = true;
    private static boolean sLog2ConsoleSwitch = true;
    private static String sGlobalTag = null;
    private static boolean sTagIsSpace = true;
    private static boolean sLogHeadSwitch = true;
    private static boolean sLog2FileSwitch = false;
    private static boolean sLogBorderSwitch = true;
    private static int sConsoleFilter = 2;
    private static int sFileFilter = 2;
    private static int sStackDeep = 1;
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ", Locale.getDefault());
    private static final Config CONFIG = new Config();

    public static class Config {
        public Config setBorderSwitch(boolean z2) {
            boolean unused = LogUtils.sLogBorderSwitch = z2;
            return this;
        }

        public Config setConsoleFilter(int i2) {
            int unused = LogUtils.sConsoleFilter = i2;
            return this;
        }

        public Config setConsoleSwitch(boolean z2) {
            boolean unused = LogUtils.sLog2ConsoleSwitch = z2;
            return this;
        }

        public Config setDir(String str) {
            if (LogUtils.isSpace(str)) {
                String unused = LogUtils.sDir = null;
            } else {
                if (!str.endsWith(LogUtils.FILE_SEP)) {
                    str = str + LogUtils.FILE_SEP;
                }
                String unused2 = LogUtils.sDir = str;
            }
            return this;
        }

        public Config setFileFilter(int i2) {
            int unused = LogUtils.sFileFilter = i2;
            return this;
        }

        public Config setFilePrefix(String str) {
            if (LogUtils.isSpace(str)) {
                String unused = LogUtils.sFilePrefix = "util";
            } else {
                String unused2 = LogUtils.sFilePrefix = str;
            }
            return this;
        }

        public Config setGlobalTag(String str) {
            if (LogUtils.isSpace(str)) {
                String unused = LogUtils.sGlobalTag = "";
                boolean unused2 = LogUtils.sTagIsSpace = true;
            } else {
                String unused3 = LogUtils.sGlobalTag = str;
                boolean unused4 = LogUtils.sTagIsSpace = false;
            }
            return this;
        }

        public Config setLog2FileSwitch(boolean z2) {
            boolean unused = LogUtils.sLog2FileSwitch = z2;
            return this;
        }

        public Config setLogHeadSwitch(boolean z2) {
            boolean unused = LogUtils.sLogHeadSwitch = z2;
            return this;
        }

        public Config setLogSwitch(boolean z2) {
            boolean unused = LogUtils.sLogSwitch = z2;
            return this;
        }

        public Config setStackDeep(@IntRange(from = 1) int i2) {
            int unused = LogUtils.sStackDeep = i2;
            return this;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("switch: ");
            sb.append(LogUtils.sLogSwitch);
            sb.append(LogUtils.LINE_SEP);
            sb.append("console: ");
            sb.append(LogUtils.sLog2ConsoleSwitch);
            sb.append(LogUtils.LINE_SEP);
            sb.append("tag: ");
            sb.append(LogUtils.sTagIsSpace ? "null" : LogUtils.sGlobalTag);
            sb.append(LogUtils.LINE_SEP);
            sb.append("head: ");
            sb.append(LogUtils.sLogHeadSwitch);
            sb.append(LogUtils.LINE_SEP);
            sb.append("file: ");
            sb.append(LogUtils.sLog2FileSwitch);
            sb.append(LogUtils.LINE_SEP);
            sb.append("dir: ");
            sb.append(LogUtils.sDir == null ? LogUtils.sDefaultDir : LogUtils.sDir);
            sb.append(LogUtils.LINE_SEP);
            sb.append("filePrefix");
            sb.append(LogUtils.sFilePrefix);
            sb.append(LogUtils.LINE_SEP);
            sb.append("border: ");
            sb.append(LogUtils.sLogBorderSwitch);
            sb.append(LogUtils.LINE_SEP);
            sb.append("consoleFilter: ");
            sb.append(LogUtils.T[LogUtils.sConsoleFilter - 2]);
            sb.append(LogUtils.LINE_SEP);
            sb.append("fileFilter: ");
            sb.append(LogUtils.T[LogUtils.sFileFilter - 2]);
            sb.append(LogUtils.LINE_SEP);
            sb.append("stackDeep: ");
            sb.append(LogUtils.sStackDeep);
            return sb.toString();
        }

        private Config() {
            String externalStorageState;
            if (LogUtils.sDefaultDir != null) {
                return;
            }
            try {
                externalStorageState = Environment.getExternalStorageState();
            } catch (Exception e2) {
                e2.printStackTrace();
                externalStorageState = "";
            }
            if (!"mounted".equals(externalStorageState) || Utils.getApp().getExternalCacheDir() == null) {
                String unused = LogUtils.sDefaultDir = Utils.getApp().getCacheDir() + LogUtils.FILE_SEP + BuildConfig.FLAVOR_type + LogUtils.FILE_SEP;
                return;
            }
            String unused2 = LogUtils.sDefaultDir = Utils.getApp().getExternalCacheDir() + LogUtils.FILE_SEP + BuildConfig.FLAVOR_type + LogUtils.FILE_SEP;
        }

        public Config setDir(File file) {
            String str;
            if (file == null) {
                str = null;
            } else {
                str = file.getAbsolutePath() + LogUtils.FILE_SEP;
            }
            String unused = LogUtils.sDir = str;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    public static class TagHead {
        String[] consoleHead;
        String fileHead;
        String tag;

        public TagHead(String str, String[] strArr, String str2) {
            this.tag = str;
            this.consoleHead = strArr;
            this.fileHead = str2;
        }
    }

    private LogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void a(Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(7, sGlobalTag, objArr);
    }

    public static void aTag(String str, Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(7, str, objArr);
    }

    private static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? !file.mkdirs() : !file.isDirectory());
    }

    private static boolean createOrExistsFile(String str) throws PackageManager.NameNotFoundException, IOException {
        File file = new File(str);
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            boolean zCreateNewFile = file.createNewFile();
            if (zCreateNewFile) {
                printDeviceInfo(str);
            }
            return zCreateNewFile;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void d(Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(3, sGlobalTag, objArr);
    }

    public static void dTag(String str, Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(3, str, objArr);
    }

    public static void e(Object... objArr) {
        log(6, sGlobalTag, objArr);
    }

    public static void eTag(String str, Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(6, str, objArr);
    }

    public static void file(Object obj) throws JSONException, TransformerException, IllegalArgumentException {
        log(19, sGlobalTag, obj);
    }

    private static String formatJson(String str) throws JSONException {
        try {
            if (str.startsWith(StrPool.DELIM_START)) {
                str = new JSONObject(str).toString(4);
            } else if (str.startsWith(StrPool.BRACKET_START)) {
                str = new JSONArray(str).toString(4);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return str;
    }

    private static String formatXml(String str) throws TransformerException, IllegalArgumentException {
        try {
            StreamSource streamSource = new StreamSource(new StringReader(str));
            StreamResult streamResult = new StreamResult(new StringWriter());
            Transformer transformerNewTransformer = TransformerFactory.newInstance().newTransformer();
            transformerNewTransformer.setOutputProperty("indent", "yes");
            transformerNewTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformerNewTransformer.transform(streamSource, streamResult);
            return streamResult.getWriter().toString().replaceFirst(">", ">" + LINE_SEP);
        } catch (Exception e2) {
            e2.printStackTrace();
            return str;
        }
    }

    public static Config getConfig() {
        return CONFIG;
    }

    public static void i(Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(4, sGlobalTag, objArr);
    }

    public static void iTag(String str, Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(4, str, objArr);
    }

    private static boolean input2File(final String str, final String str2) {
        if (sExecutor == null) {
            sExecutor = Executors.newSingleThreadExecutor();
        }
        try {
            return ((Boolean) sExecutor.submit(new Callable<Boolean>() { // from class: com.plv.thirdpart.blankj.utilcode.util.LogUtils.1
                /* JADX WARN: Can't rename method to resolve collision */
                /* JADX WARN: Removed duplicated region for block: B:35:0x003c A[EXC_TOP_SPLITTER, SYNTHETIC] */
                @Override // java.util.concurrent.Callable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public java.lang.Boolean call() throws java.lang.Exception {
                    /*
                        r6 = this;
                        r0 = 0
                        java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch: java.lang.Throwable -> L20 java.io.IOException -> L25
                        java.io.FileWriter r2 = new java.io.FileWriter     // Catch: java.lang.Throwable -> L20 java.io.IOException -> L25
                        java.lang.String r3 = r1     // Catch: java.lang.Throwable -> L20 java.io.IOException -> L25
                        r4 = 1
                        r2.<init>(r3, r4)     // Catch: java.lang.Throwable -> L20 java.io.IOException -> L25
                        r1.<init>(r2)     // Catch: java.lang.Throwable -> L20 java.io.IOException -> L25
                        java.lang.String r0 = r2     // Catch: java.io.IOException -> L1e java.lang.Throwable -> L39
                        r1.write(r0)     // Catch: java.io.IOException -> L1e java.lang.Throwable -> L39
                        java.lang.Boolean r0 = java.lang.Boolean.TRUE     // Catch: java.io.IOException -> L1e java.lang.Throwable -> L39
                        r1.close()     // Catch: java.io.IOException -> L19
                        goto L1d
                    L19:
                        r1 = move-exception
                        r1.printStackTrace()
                    L1d:
                        return r0
                    L1e:
                        r0 = move-exception
                        goto L29
                    L20:
                        r1 = move-exception
                        r5 = r1
                        r1 = r0
                        r0 = r5
                        goto L3a
                    L25:
                        r1 = move-exception
                        r5 = r1
                        r1 = r0
                        r0 = r5
                    L29:
                        r0.printStackTrace()     // Catch: java.lang.Throwable -> L39
                        java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch: java.lang.Throwable -> L39
                        if (r1 == 0) goto L38
                        r1.close()     // Catch: java.io.IOException -> L34
                        goto L38
                    L34:
                        r1 = move-exception
                        r1.printStackTrace()
                    L38:
                        return r0
                    L39:
                        r0 = move-exception
                    L3a:
                        if (r1 == 0) goto L44
                        r1.close()     // Catch: java.io.IOException -> L40
                        goto L44
                    L40:
                        r1 = move-exception
                        r1.printStackTrace()
                    L44:
                        throw r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.plv.thirdpart.blankj.utilcode.util.LogUtils.AnonymousClass1.call():java.lang.Boolean");
                }
            }).get()).booleanValue();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
            return false;
        } catch (ExecutionException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static void json(String str) throws JSONException, TransformerException, IllegalArgumentException {
        log(35, sGlobalTag, str);
    }

    private static void log(int i2, String str, Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        if (sLogSwitch) {
            if (sLog2ConsoleSwitch || sLog2FileSwitch) {
                int i3 = i2 & 15;
                int i4 = i2 & 240;
                if (i3 >= sConsoleFilter || i3 >= sFileFilter) {
                    TagHead tagHeadProcessTagAndHead = processTagAndHead(str);
                    String strProcessBody = processBody(i4, objArr);
                    if (sLog2ConsoleSwitch && i3 >= sConsoleFilter && i4 != 16) {
                        print2Console(i3, tagHeadProcessTagAndHead.tag, tagHeadProcessTagAndHead.consoleHead, strProcessBody);
                    }
                    if ((sLog2FileSwitch || i4 == 16) && i3 >= sFileFilter) {
                        print2File(i3, tagHeadProcessTagAndHead.tag, tagHeadProcessTagAndHead.fileHead + strProcessBody);
                    }
                }
            }
        }
    }

    private static void print2Console(int i2, String str, String[] strArr, String str2) {
        printBorder(i2, str, true);
        printHead(i2, str, strArr);
        printMsg(i2, str, str2);
        printBorder(i2, str, false);
    }

    private static void print2File(int i2, String str, String str2) {
        String str3 = FORMAT.format(new Date(System.currentTimeMillis()));
        String strSubstring = str3.substring(0, 5);
        String strSubstring2 = str3.substring(6);
        StringBuilder sb = new StringBuilder();
        String str4 = sDir;
        if (str4 == null) {
            str4 = sDefaultDir;
        }
        sb.append(str4);
        sb.append(sFilePrefix);
        sb.append("-");
        sb.append(strSubstring);
        sb.append(".txt");
        String string = sb.toString();
        if (!createOrExistsFile(string)) {
            Log.e(str, "log to " + string + " failed!");
            return;
        }
        if (input2File(strSubstring2 + T[i2 - 2] + "/" + str + str2 + LINE_SEP, string)) {
            Log.d(str, "log to " + string + " success!");
            return;
        }
        Log.e(str, "log to " + string + " failed!");
    }

    private static void printBorder(int i2, String str, boolean z2) {
        if (sLogBorderSwitch) {
            Log.println(i2, str, z2 ? TOP_BORDER : BOTTOM_BORDER);
        }
    }

    private static void printDeviceInfo(String str) throws PackageManager.NameNotFoundException {
        String str2 = "";
        int i2 = 0;
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(Utils.getApp().getPackageName(), 0);
            if (packageInfo != null) {
                str2 = packageInfo.versionName;
                i2 = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        input2File("************* Log Head ****************\nDevice Manufacturer: " + Build.MANUFACTURER + "\nDevice Model       : " + Build.MODEL + "\nAndroid Version    : " + Build.VERSION.RELEASE + "\nAndroid SDK        : " + Build.VERSION.SDK_INT + "\nApp VersionName    : " + str2 + "\nApp VersionCode    : " + i2 + "\n************* Log Head ****************\n\n", str);
    }

    private static void printHead(int i2, String str, String[] strArr) {
        if (strArr != null) {
            for (String str2 : strArr) {
                if (sLogBorderSwitch) {
                    str2 = LEFT_BORDER + str2;
                }
                Log.println(i2, str, str2);
            }
            if (sLogBorderSwitch) {
                Log.println(i2, str, SPLIT_BORDER);
            }
        }
    }

    private static void printMsg(int i2, String str, String str2) {
        int length = str2.length();
        int i3 = length / 4000;
        if (i3 <= 0) {
            printSubMsg(i2, str, str2);
            return;
        }
        int i4 = 0;
        int i5 = 0;
        while (i4 < i3) {
            int i6 = i5 + 4000;
            printSubMsg(i2, str, str2.substring(i5, i6));
            i4++;
            i5 = i6;
        }
        if (i5 != length) {
            printSubMsg(i2, str, str2.substring(i5, length));
        }
    }

    private static void printSubMsg(int i2, String str, String str2) {
        if (!sLogBorderSwitch) {
            Log.println(i2, str, str2);
            return;
        }
        for (String str3 : str2.split(LINE_SEP)) {
            Log.println(i2, str, LEFT_BORDER + str3);
        }
    }

    private static String processBody(int i2, Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        String xml;
        if (objArr == null) {
            return "null";
        }
        if (objArr.length == 1) {
            Object obj = objArr[0];
            String string = obj != null ? obj.toString() : "null";
            if (i2 == 32) {
                xml = formatJson(string);
            } else {
                if (i2 != 48) {
                    return string;
                }
                xml = formatXml(string);
            }
            return xml;
        }
        StringBuilder sb = new StringBuilder();
        int length = objArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            Object obj2 = objArr[i3];
            sb.append("args");
            sb.append(StrPool.BRACKET_START);
            sb.append(i3);
            sb.append(StrPool.BRACKET_END);
            sb.append(" = ");
            sb.append(obj2 == null ? "null" : obj2.toString());
            sb.append(LINE_SEP);
        }
        return sb.toString();
    }

    private static TagHead processTagAndHead(String str) {
        String str2;
        String strSubstring;
        String str3;
        if (sTagIsSpace || sLogHeadSwitch) {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            StackTraceElement stackTraceElement = stackTrace[3];
            String fileName = stackTraceElement.getFileName();
            if (fileName == null) {
                strSubstring = stackTraceElement.getClassName();
                String[] strArrSplit = strSubstring.split("\\.");
                if (strArrSplit.length > 0) {
                    strSubstring = strArrSplit[strArrSplit.length - 1];
                }
                int iIndexOf = strSubstring.indexOf(36);
                if (iIndexOf != -1) {
                    strSubstring = strSubstring.substring(0, iIndexOf);
                }
                str2 = strSubstring + FileNameUtil.EXT_JAVA;
            } else {
                int iIndexOf2 = fileName.indexOf(46);
                str2 = fileName;
                strSubstring = iIndexOf2 == -1 ? fileName : fileName.substring(0, iIndexOf2);
            }
            if (!sTagIsSpace || !isSpace(str)) {
                strSubstring = str;
            }
            if (sLogHeadSwitch) {
                String name = Thread.currentThread().getName();
                String string = new Formatter().format("%s, %s(%s:%d)", name, stackTraceElement.getMethodName(), str2, Integer.valueOf(stackTraceElement.getLineNumber())).toString();
                String str4 = " [" + string + "]: ";
                int i2 = sStackDeep;
                if (i2 <= 1) {
                    return new TagHead(strSubstring, new String[]{string}, str4);
                }
                int iMin = Math.min(i2, stackTrace.length - 3);
                String[] strArr = new String[iMin];
                strArr[0] = string;
                int length = name.length() + 2;
                String string2 = new Formatter().format("%" + length + "s", "").toString();
                for (int i3 = 1; i3 < iMin; i3++) {
                    StackTraceElement stackTraceElement2 = stackTrace[i3 + 3];
                    strArr[i3] = new Formatter().format("%s%s(%s:%d)", string2, stackTraceElement2.getMethodName(), stackTraceElement2.getFileName(), Integer.valueOf(stackTraceElement2.getLineNumber())).toString();
                }
                return new TagHead(strSubstring, strArr, str4);
            }
            str3 = strSubstring;
        } else {
            str3 = sGlobalTag;
        }
        return new TagHead(str3, null, ": ");
    }

    public static void v(Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(2, sGlobalTag, objArr);
    }

    public static void vTag(String str, Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(2, str, objArr);
    }

    public static void w(Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(5, sGlobalTag, objArr);
    }

    public static void wTag(String str, Object... objArr) throws JSONException, TransformerException, IllegalArgumentException {
        log(5, str, objArr);
    }

    public static void xml(String str) throws JSONException, TransformerException, IllegalArgumentException {
        log(51, sGlobalTag, str);
    }

    public static void file(int i2, Object obj) throws JSONException, TransformerException, IllegalArgumentException {
        log(i2 | 16, sGlobalTag, obj);
    }

    public static void json(int i2, String str) throws JSONException, TransformerException, IllegalArgumentException {
        log(i2 | 32, sGlobalTag, str);
    }

    public static void xml(int i2, String str) throws JSONException, TransformerException, IllegalArgumentException {
        log(i2 | 48, sGlobalTag, str);
    }

    public static void file(String str, Object obj) throws JSONException, TransformerException, IllegalArgumentException {
        log(19, str, obj);
    }

    public static void json(String str, String str2) throws JSONException, TransformerException, IllegalArgumentException {
        log(35, str, str2);
    }

    public static void xml(String str, String str2) throws JSONException, TransformerException, IllegalArgumentException {
        log(51, str, str2);
    }

    public static void file(int i2, String str, Object obj) throws JSONException, TransformerException, IllegalArgumentException {
        log(i2 | 16, str, obj);
    }

    public static void json(int i2, String str, String str2) throws JSONException, TransformerException, IllegalArgumentException {
        log(i2 | 32, str, str2);
    }

    public static void xml(int i2, String str, String str2) throws JSONException, TransformerException, IllegalArgumentException {
        log(i2 | 48, str, str2);
    }
}
