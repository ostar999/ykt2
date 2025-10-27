package org.slf4j.impl;

import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.plv.foundationsdk.web.PLVWebview;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import k.a;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.Util;

/* loaded from: classes9.dex */
public class SimpleLogger extends MarkerIgnoringBase {
    private static final String CONFIGURATION_FILE = "simplelogger.properties";
    public static final String DATE_TIME_FORMAT_KEY = "org.slf4j.simpleLogger.dateTimeFormat";
    public static final String DEFAULT_LOG_LEVEL_KEY = "org.slf4j.simpleLogger.defaultLogLevel";
    public static final String LEVEL_IN_BRACKETS_KEY = "org.slf4j.simpleLogger.levelInBrackets";
    public static final String LOG_FILE_KEY = "org.slf4j.simpleLogger.logFile";
    public static final String LOG_KEY_PREFIX = "org.slf4j.simpleLogger.log.";
    private static final int LOG_LEVEL_DEBUG = 10;
    private static final int LOG_LEVEL_ERROR = 40;
    private static final int LOG_LEVEL_INFO = 20;
    private static final int LOG_LEVEL_TRACE = 0;
    private static final int LOG_LEVEL_WARN = 30;
    public static final String SHOW_DATE_TIME_KEY = "org.slf4j.simpleLogger.showDateTime";
    public static final String SHOW_LOG_NAME_KEY = "org.slf4j.simpleLogger.showLogName";
    public static final String SHOW_SHORT_LOG_NAME_KEY = "org.slf4j.simpleLogger.showShortLogName";
    public static final String SHOW_THREAD_NAME_KEY = "org.slf4j.simpleLogger.showThreadName";
    public static final String SYSTEM_PREFIX = "org.slf4j.simpleLogger.";
    public static final String WARN_LEVEL_STRING_KEY = "org.slf4j.simpleLogger.warnLevelString";
    private static final long serialVersionUID = -632788891211436180L;
    protected int currentLogLevel;
    private transient String shortLogName = null;
    private static long START_TIME = System.currentTimeMillis();
    private static final Properties SIMPLE_LOGGER_PROPS = new Properties();
    private static boolean INITIALIZED = false;
    private static int DEFAULT_LOG_LEVEL = 20;
    private static boolean SHOW_DATE_TIME = false;
    private static String DATE_TIME_FORMAT_STR = null;
    private static DateFormat DATE_FORMATTER = null;
    private static boolean SHOW_THREAD_NAME = true;
    private static boolean SHOW_LOG_NAME = true;
    private static boolean SHOW_SHORT_LOG_NAME = false;
    private static String LOG_FILE = "System.err";
    private static PrintStream TARGET_STREAM = null;
    private static boolean LEVEL_IN_BRACKETS = false;
    private static String WARN_LEVEL_STRING = "WARN";

    public SimpleLogger(String str) {
        this.currentLogLevel = 20;
        this.name = str;
        String strRecursivelyComputeLevelString = recursivelyComputeLevelString();
        if (strRecursivelyComputeLevelString != null) {
            this.currentLogLevel = stringToLevel(strRecursivelyComputeLevelString);
        } else {
            this.currentLogLevel = DEFAULT_LOG_LEVEL;
        }
    }

    private String computeShortName() {
        String str = this.name;
        return str.substring(str.lastIndexOf(StrPool.DOT) + 1);
    }

    private static PrintStream computeTargetStream(String str) {
        if ("System.err".equalsIgnoreCase(str)) {
            return System.err;
        }
        if ("System.out".equalsIgnoreCase(str)) {
            return System.out;
        }
        try {
            return new PrintStream(new FileOutputStream(str));
        } catch (FileNotFoundException e2) {
            Util.report("Could not open [" + str + "]. Defaulting to System.err", e2);
            return System.err;
        }
    }

    private void formatAndLog(int i2, String str, Object obj, Object obj2) {
        if (isLevelEnabled(i2)) {
            FormattingTuple formattingTuple = MessageFormatter.format(str, obj, obj2);
            log(i2, formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    private static boolean getBooleanProperty(String str, boolean z2) {
        String stringProperty = getStringProperty(str);
        return stringProperty == null ? z2 : a.f27523u.equalsIgnoreCase(stringProperty);
    }

    private String getFormattedDate() {
        String str;
        Date date = new Date();
        synchronized (DATE_FORMATTER) {
            str = DATE_FORMATTER.format(date);
        }
        return str;
    }

    private static String getStringProperty(String str) {
        String property;
        try {
            property = System.getProperty(str);
        } catch (SecurityException unused) {
            property = null;
        }
        return property == null ? SIMPLE_LOGGER_PROPS.getProperty(str) : property;
    }

    public static void init() throws IOException {
        if (INITIALIZED) {
            return;
        }
        INITIALIZED = true;
        loadProperties();
        String stringProperty = getStringProperty(DEFAULT_LOG_LEVEL_KEY, null);
        if (stringProperty != null) {
            DEFAULT_LOG_LEVEL = stringToLevel(stringProperty);
        }
        SHOW_LOG_NAME = getBooleanProperty(SHOW_LOG_NAME_KEY, SHOW_LOG_NAME);
        SHOW_SHORT_LOG_NAME = getBooleanProperty(SHOW_SHORT_LOG_NAME_KEY, SHOW_SHORT_LOG_NAME);
        SHOW_DATE_TIME = getBooleanProperty(SHOW_DATE_TIME_KEY, SHOW_DATE_TIME);
        SHOW_THREAD_NAME = getBooleanProperty(SHOW_THREAD_NAME_KEY, SHOW_THREAD_NAME);
        DATE_TIME_FORMAT_STR = getStringProperty(DATE_TIME_FORMAT_KEY, DATE_TIME_FORMAT_STR);
        LEVEL_IN_BRACKETS = getBooleanProperty(LEVEL_IN_BRACKETS_KEY, LEVEL_IN_BRACKETS);
        WARN_LEVEL_STRING = getStringProperty(WARN_LEVEL_STRING_KEY, WARN_LEVEL_STRING);
        String stringProperty2 = getStringProperty(LOG_FILE_KEY, LOG_FILE);
        LOG_FILE = stringProperty2;
        TARGET_STREAM = computeTargetStream(stringProperty2);
        if (DATE_TIME_FORMAT_STR != null) {
            try {
                DATE_FORMATTER = new SimpleDateFormat(DATE_TIME_FORMAT_STR);
            } catch (IllegalArgumentException e2) {
                Util.report("Bad date format in simplelogger.properties; will output relative time", e2);
            }
        }
    }

    private static void loadProperties() throws IOException {
        InputStream inputStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() { // from class: org.slf4j.impl.SimpleLogger.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.security.PrivilegedAction
            public InputStream run() {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                return contextClassLoader != null ? contextClassLoader.getResourceAsStream(SimpleLogger.CONFIGURATION_FILE) : ClassLoader.getSystemResourceAsStream(SimpleLogger.CONFIGURATION_FILE);
            }
        });
        if (inputStream != null) {
            try {
                SIMPLE_LOGGER_PROPS.load(inputStream);
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    private void log(int i2, String str, Throwable th) {
        if (isLevelEnabled(i2)) {
            StringBuilder sb = new StringBuilder(32);
            if (SHOW_DATE_TIME) {
                if (DATE_FORMATTER != null) {
                    sb.append(getFormattedDate());
                    sb.append(' ');
                } else {
                    sb.append(System.currentTimeMillis() - START_TIME);
                    sb.append(' ');
                }
            }
            if (SHOW_THREAD_NAME) {
                sb.append('[');
                sb.append(Thread.currentThread().getName());
                sb.append("] ");
            }
            if (LEVEL_IN_BRACKETS) {
                sb.append('[');
            }
            if (i2 == 0) {
                sb.append("TRACE");
            } else if (i2 == 10) {
                sb.append("DEBUG");
            } else if (i2 == 20) {
                sb.append("INFO");
            } else if (i2 == 30) {
                sb.append(WARN_LEVEL_STRING);
            } else if (i2 == 40) {
                sb.append(PLVWebview.MESSAGE_ERROR);
            }
            if (LEVEL_IN_BRACKETS) {
                sb.append(']');
            }
            sb.append(' ');
            if (SHOW_SHORT_LOG_NAME) {
                if (this.shortLogName == null) {
                    this.shortLogName = computeShortName();
                }
                sb.append(String.valueOf(this.shortLogName));
                sb.append(" - ");
            } else if (SHOW_LOG_NAME) {
                sb.append(String.valueOf(this.name));
                sb.append(" - ");
            }
            sb.append(str);
            write(sb, th);
        }
    }

    private static int stringToLevel(String str) {
        if ("trace".equalsIgnoreCase(str)) {
            return 0;
        }
        if ("debug".equalsIgnoreCase(str)) {
            return 10;
        }
        if (AliyunLogCommon.LogLevel.INFO.equalsIgnoreCase(str)) {
            return 20;
        }
        if (AliyunLogCommon.LogLevel.WARN.equalsIgnoreCase(str)) {
            return 30;
        }
        return "error".equalsIgnoreCase(str) ? 40 : 20;
    }

    @Override // org.slf4j.Logger
    public void debug(String str) {
        log(10, str, null);
    }

    @Override // org.slf4j.Logger
    public void error(String str) {
        log(40, str, null);
    }

    @Override // org.slf4j.Logger
    public void info(String str) {
        log(20, str, null);
    }

    @Override // org.slf4j.Logger
    public boolean isDebugEnabled() {
        return isLevelEnabled(10);
    }

    @Override // org.slf4j.Logger
    public boolean isErrorEnabled() {
        return isLevelEnabled(40);
    }

    @Override // org.slf4j.Logger
    public boolean isInfoEnabled() {
        return isLevelEnabled(20);
    }

    public boolean isLevelEnabled(int i2) {
        return i2 >= this.currentLogLevel;
    }

    @Override // org.slf4j.Logger
    public boolean isTraceEnabled() {
        return isLevelEnabled(0);
    }

    @Override // org.slf4j.Logger
    public boolean isWarnEnabled() {
        return isLevelEnabled(30);
    }

    public String recursivelyComputeLevelString() {
        String strSubstring = this.name;
        int length = strSubstring.length();
        String stringProperty = null;
        while (stringProperty == null && length > -1) {
            strSubstring = strSubstring.substring(0, length);
            stringProperty = getStringProperty(LOG_KEY_PREFIX + strSubstring, null);
            length = String.valueOf(strSubstring).lastIndexOf(StrPool.DOT);
        }
        return stringProperty;
    }

    @Override // org.slf4j.Logger
    public void trace(String str) {
        log(0, str, null);
    }

    @Override // org.slf4j.Logger
    public void warn(String str) {
        log(30, str, null);
    }

    public void write(StringBuilder sb, Throwable th) {
        TARGET_STREAM.println(sb.toString());
        if (th != null) {
            th.printStackTrace(TARGET_STREAM);
        }
        TARGET_STREAM.flush();
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj) {
        formatAndLog(10, str, obj, null);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object obj) {
        formatAndLog(40, str, obj, null);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj) {
        formatAndLog(20, str, obj, null);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj) {
        formatAndLog(0, str, obj, null);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj) {
        formatAndLog(30, str, obj, null);
    }

    private static String getStringProperty(String str, String str2) {
        String stringProperty = getStringProperty(str);
        return stringProperty == null ? str2 : stringProperty;
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj, Object obj2) {
        formatAndLog(10, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object obj, Object obj2) {
        formatAndLog(40, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj, Object obj2) {
        formatAndLog(20, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj, Object obj2) {
        formatAndLog(0, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj, Object obj2) {
        formatAndLog(30, str, obj, obj2);
    }

    private void formatAndLog(int i2, String str, Object... objArr) {
        if (isLevelEnabled(i2)) {
            FormattingTuple formattingTupleArrayFormat = MessageFormatter.arrayFormat(str, objArr);
            log(i2, formattingTupleArrayFormat.getMessage(), formattingTupleArrayFormat.getThrowable());
        }
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object... objArr) {
        formatAndLog(10, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object... objArr) {
        formatAndLog(40, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object... objArr) {
        formatAndLog(20, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object... objArr) {
        formatAndLog(0, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object... objArr) {
        formatAndLog(30, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Throwable th) {
        log(10, str, th);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Throwable th) {
        log(40, str, th);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Throwable th) {
        log(20, str, th);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Throwable th) {
        log(0, str, th);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Throwable th) {
        log(30, str, th);
    }

    public void log(LoggingEvent loggingEvent) {
        int i2 = loggingEvent.getLevel().toInt();
        if (isLevelEnabled(i2)) {
            log(i2, MessageFormatter.arrayFormat(loggingEvent.getMessage(), loggingEvent.getArgumentArray(), loggingEvent.getThrowable()).getMessage(), loggingEvent.getThrowable());
        }
    }
}
