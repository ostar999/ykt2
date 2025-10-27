package org.eclipse.jetty.util.log;

import cn.hutool.core.text.StrPool;
import java.io.PrintStream;
import java.security.AccessControlException;
import java.util.Properties;
import k.a;
import kotlin.text.Typography;
import org.eclipse.jetty.util.DateCache;

/* loaded from: classes9.dex */
public class StdErrLog extends AbstractLogger {
    private static final String EOL = System.getProperty("line.separator");
    public static final int LEVEL_ALL = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARN = 3;
    private static final boolean __long;
    private static final Properties __props;
    private static final boolean __source;
    private static DateCache _dateCache;
    private final String _abbrevname;
    private int _configuredLevel;
    private boolean _hideStacks;
    private int _level;
    private final String _name;
    private boolean _printLongNames;
    private boolean _source;
    private PrintStream _stderr;

    static {
        Properties properties = new Properties();
        __props = properties;
        Properties properties2 = Log.__props;
        __source = Boolean.parseBoolean(properties2.getProperty("org.eclipse.jetty.util.log.SOURCE", properties2.getProperty("org.eclipse.jetty.util.log.stderr.SOURCE", a.f27524v)));
        __long = Boolean.parseBoolean(Log.__props.getProperty("org.eclipse.jetty.util.log.stderr.LONG", a.f27524v));
        properties.putAll(Log.__props);
        String[] strArr = {"DEBUG", "org.eclipse.jetty.util.log.DEBUG", "org.eclipse.jetty.util.log.stderr.DEBUG"};
        for (int i2 = 0; i2 < 3; i2++) {
            String str = strArr[i2];
            if (System.getProperty(str) != null) {
                System.err.printf("System Property [%s] has been deprecated! (Use org.eclipse.jetty.LEVEL=DEBUG instead)%n", str);
            }
        }
        try {
            _dateCache = new DateCache("yyyy-MM-dd HH:mm:ss");
        } catch (Exception e2) {
            e2.printStackTrace(System.err);
        }
    }

    public StdErrLog() {
        this(null);
    }

    public static String condensePackageString(String str) {
        String[] strArrSplit = str.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < strArrSplit.length - 1; i2++) {
            sb.append(strArrSplit[i2].charAt(0));
        }
        if (sb.length() > 0) {
            sb.append('.');
        }
        sb.append(strArrSplit[strArrSplit.length - 1]);
        return sb.toString();
    }

    private void escape(StringBuilder sb, String str) {
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (!Character.isISOControl(cCharAt)) {
                sb.append(cCharAt);
            } else if (cCharAt == '\n') {
                sb.append('|');
            } else if (cCharAt == '\r') {
                sb.append(Typography.less);
            } else {
                sb.append('?');
            }
        }
    }

    private void format(StringBuilder sb, String str, String str2, Object... objArr) {
        tag(sb, _dateCache.now(), _dateCache.lastMs(), str);
        format(sb, str2, objArr);
    }

    public static int getLevelId(String str, String str2) {
        if (str2 == null) {
            return -1;
        }
        String strTrim = str2.trim();
        if ("ALL".equalsIgnoreCase(strTrim)) {
            return 0;
        }
        if ("DEBUG".equalsIgnoreCase(strTrim)) {
            return 1;
        }
        if ("INFO".equalsIgnoreCase(strTrim)) {
            return 2;
        }
        if ("WARN".equalsIgnoreCase(strTrim)) {
            return 3;
        }
        System.err.println("Unknown StdErrLog level [" + str + "]=[" + strTrim + "], expecting only [ALL, DEBUG, INFO, WARN] as values.");
        return -1;
    }

    public static int getLoggingLevel(Properties properties, String str) {
        while (str != null && str.length() > 0) {
            int levelId = getLevelId(str + ".LEVEL", properties.getProperty(str + ".LEVEL"));
            if (levelId != -1) {
                return levelId;
            }
            int iLastIndexOf = str.lastIndexOf(46);
            str = iLastIndexOf >= 0 ? str.substring(0, iLastIndexOf) : null;
        }
        return getLevelId("log.LEVEL", properties.getProperty("log.LEVEL", "INFO"));
    }

    public static void setProperties(Properties properties) {
        Properties properties2 = __props;
        properties2.clear();
        properties2.putAll(properties);
    }

    private void tag(StringBuilder sb, String str, int i2, String str2) {
        sb.setLength(0);
        sb.append(str);
        if (i2 > 99) {
            sb.append('.');
        } else if (i2 > 9) {
            sb.append(".0");
        } else {
            sb.append(".00");
        }
        sb.append(i2);
        sb.append(str2);
        if (this._printLongNames) {
            sb.append(this._name);
        } else {
            sb.append(this._abbrevname);
        }
        sb.append(':');
        if (this._source) {
            for (StackTraceElement stackTraceElement : new Throwable().getStackTrace()) {
                String className = stackTraceElement.getClassName();
                if (!className.equals(StdErrLog.class.getName()) && !className.equals(Log.class.getName())) {
                    if (this._printLongNames || !className.startsWith("org.eclipse.jetty.")) {
                        sb.append(className);
                    } else {
                        sb.append(condensePackageString(className));
                    }
                    sb.append('#');
                    sb.append(stackTraceElement.getMethodName());
                    if (stackTraceElement.getFileName() != null) {
                        sb.append('(');
                        sb.append(stackTraceElement.getFileName());
                        sb.append(':');
                        sb.append(stackTraceElement.getLineNumber());
                        sb.append(')');
                    }
                    sb.append(':');
                    return;
                }
            }
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(String str, Object... objArr) {
        if (this._level <= 1) {
            StringBuilder sb = new StringBuilder(64);
            format(sb, ":DBUG:", str, objArr);
            PrintStream printStream = this._stderr;
            if (printStream == null) {
                printStream = System.err;
            }
            printStream.println(sb);
        }
    }

    public int getLevel() {
        return this._level;
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public String getName() {
        return this._name;
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void ignore(Throwable th) {
        if (this._level <= 0) {
            StringBuilder sb = new StringBuilder(64);
            format(sb, ":IGNORED:", "", th);
            PrintStream printStream = this._stderr;
            if (printStream == null) {
                printStream = System.err;
            }
            printStream.println(sb);
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(String str, Object... objArr) {
        if (this._level <= 2) {
            StringBuilder sb = new StringBuilder(64);
            format(sb, ":INFO:", str, objArr);
            PrintStream printStream = this._stderr;
            if (printStream == null) {
                printStream = System.err;
            }
            printStream.println(sb);
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public boolean isDebugEnabled() {
        return this._level <= 1;
    }

    public boolean isHideStacks() {
        return this._hideStacks;
    }

    public boolean isPrintLongNames() {
        return this._printLongNames;
    }

    public boolean isSource() {
        return this._source;
    }

    @Override // org.eclipse.jetty.util.log.AbstractLogger
    public Logger newLogger(String str) {
        StdErrLog stdErrLog = new StdErrLog(str);
        stdErrLog.setPrintLongNames(this._printLongNames);
        stdErrLog.setSource(this._source);
        stdErrLog._stderr = this._stderr;
        int i2 = this._level;
        if (i2 != this._configuredLevel) {
            stdErrLog._level = i2;
        }
        return stdErrLog;
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void setDebugEnabled(boolean z2) {
        if (z2) {
            this._level = 1;
            for (Logger logger : Log.getLoggers().values()) {
                if (logger.getName().startsWith(getName()) && (logger instanceof StdErrLog)) {
                    ((StdErrLog) logger).setLevel(1);
                }
            }
            return;
        }
        this._level = this._configuredLevel;
        for (Logger logger2 : Log.getLoggers().values()) {
            if (logger2.getName().startsWith(getName()) && (logger2 instanceof StdErrLog)) {
                StdErrLog stdErrLog = (StdErrLog) logger2;
                stdErrLog.setLevel(stdErrLog._configuredLevel);
            }
        }
    }

    public void setHideStacks(boolean z2) {
        this._hideStacks = z2;
    }

    public void setLevel(int i2) {
        this._level = i2;
    }

    public void setPrintLongNames(boolean z2) {
        this._printLongNames = z2;
    }

    public void setSource(boolean z2) {
        this._source = z2;
    }

    public void setStdErrStream(PrintStream printStream) {
        if (printStream == System.err) {
            printStream = null;
        }
        this._stderr = printStream;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StdErrLog:");
        sb.append(this._name);
        sb.append(":LEVEL=");
        int i2 = this._level;
        if (i2 == 0) {
            sb.append("ALL");
        } else if (i2 == 1) {
            sb.append("DEBUG");
        } else if (i2 == 2) {
            sb.append("INFO");
        } else if (i2 != 3) {
            sb.append("?");
        } else {
            sb.append("WARN");
        }
        return sb.toString();
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(String str, Object... objArr) {
        if (this._level <= 3) {
            StringBuilder sb = new StringBuilder(64);
            format(sb, ":WARN:", str, objArr);
            PrintStream printStream = this._stderr;
            if (printStream == null) {
                printStream = System.err;
            }
            printStream.println(sb);
        }
    }

    public StdErrLog(String str) {
        this(str, __props);
    }

    public StdErrLog(String str, Properties properties) {
        Properties properties2;
        this._level = 2;
        this._stderr = null;
        this._source = __source;
        this._printLongNames = __long;
        this._hideStacks = false;
        if (properties != null && properties != (properties2 = __props)) {
            properties2.putAll(properties);
        }
        str = str == null ? "" : str;
        this._name = str;
        this._abbrevname = condensePackageString(str);
        int loggingLevel = getLoggingLevel(properties, str);
        this._level = loggingLevel;
        this._configuredLevel = loggingLevel;
        try {
            this._source = Boolean.parseBoolean(properties.getProperty(str + ".SOURCE", Boolean.toString(this._source)));
        } catch (AccessControlException unused) {
            this._source = __source;
        }
    }

    private void format(StringBuilder sb, String str, String str2, Throwable th) {
        format(sb, str, str2, new Object[0]);
        if (isHideStacks()) {
            format(sb, String.valueOf(th), new Object[0]);
        } else {
            format(sb, th);
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(Throwable th) {
        debug("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(Throwable th) {
        info("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(Throwable th) {
        warn("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(String str, Throwable th) {
        if (this._level <= 1) {
            StringBuilder sb = new StringBuilder(64);
            format(sb, ":DBUG:", str, th);
            PrintStream printStream = this._stderr;
            if (printStream == null) {
                printStream = System.err;
            }
            printStream.println(sb);
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(String str, Throwable th) {
        if (this._level <= 2) {
            StringBuilder sb = new StringBuilder(64);
            format(sb, ":INFO:", str, th);
            PrintStream printStream = this._stderr;
            if (printStream == null) {
                printStream = System.err;
            }
            printStream.println(sb);
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(String str, Throwable th) {
        if (this._level <= 3) {
            StringBuilder sb = new StringBuilder(64);
            format(sb, ":WARN:", str, th);
            PrintStream printStream = this._stderr;
            if (printStream == null) {
                printStream = System.err;
            }
            printStream.println(sb);
        }
    }

    private void format(StringBuilder sb, String str, Object... objArr) {
        if (str == null) {
            str = "";
            for (int i2 = 0; i2 < objArr.length; i2++) {
                str = str + "{} ";
            }
        }
        int length = 0;
        for (Object obj : objArr) {
            int iIndexOf = str.indexOf(StrPool.EMPTY_JSON, length);
            if (iIndexOf < 0) {
                escape(sb, str.substring(length));
                sb.append(" ");
                sb.append(obj);
                length = str.length();
            } else {
                escape(sb, str.substring(length, iIndexOf));
                sb.append(String.valueOf(obj));
                length = iIndexOf + 2;
            }
        }
        escape(sb, str.substring(length));
    }

    private void format(StringBuilder sb, Throwable th) {
        if (th == null) {
            sb.append("null");
            return;
        }
        sb.append(EOL);
        format(sb, th.toString(), new Object[0]);
        StackTraceElement[] stackTrace = th.getStackTrace();
        for (int i2 = 0; stackTrace != null && i2 < stackTrace.length; i2++) {
            sb.append(EOL);
            sb.append("\tat ");
            format(sb, stackTrace[i2].toString(), new Object[0]);
        }
        Throwable cause = th.getCause();
        if (cause == null || cause == th) {
            return;
        }
        sb.append(EOL);
        sb.append("Caused by: ");
        format(sb, cause);
    }
}
