package org.wrtca.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes9.dex */
public class Logging {
    private static final Logger fallbackLogger = createFallbackLogger();
    private static volatile boolean loggingEnabled;

    /* renamed from: org.wrtca.log.Logging$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$org$wrtca$log$Logging$Severity;

        static {
            int[] iArr = new int[Severity.values().length];
            $SwitchMap$org$wrtca$log$Logging$Severity = iArr;
            try {
                iArr[Severity.LS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$wrtca$log$Logging$Severity[Severity.LS_WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$wrtca$log$Logging$Severity[Severity.LS_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum Severity {
        LS_SENSITIVE,
        LS_VERBOSE,
        LS_INFO,
        LS_WARNING,
        LS_ERROR,
        LS_NONE
    }

    @Deprecated
    public enum TraceLevel {
        TRACE_NONE(0),
        TRACE_STATEINFO(1),
        TRACE_WARNING(2),
        TRACE_ERROR(4),
        TRACE_CRITICAL(8),
        TRACE_APICALL(16),
        TRACE_DEFAULT(255),
        TRACE_MODULECALL(32),
        TRACE_MEMORY(256),
        TRACE_TIMER(512),
        TRACE_STREAM(1024),
        TRACE_DEBUG(2048),
        TRACE_INFO(4096),
        TRACE_TERSEINFO(8192),
        TRACE_ALL(65535);

        public final int level;

        TraceLevel(int i2) {
            this.level = i2;
        }
    }

    private static Logger createFallbackLogger() throws SecurityException {
        Logger logger = Logger.getLogger("RTC.Logging");
        logger.setLevel(Level.ALL);
        return logger;
    }

    public static void d(String str, String str2) {
        log(Severity.LS_INFO, str, str2);
    }

    public static void e(String str, String str2) {
        log(Severity.LS_ERROR, str, str2);
    }

    public static void enableLogThreads() {
        nativeEnableLogThreads();
    }

    public static void enableLogTimeStamps() {
        nativeEnableLogTimeStamps();
    }

    public static synchronized void enableLogToDebugOutput(Severity severity) {
        nativeEnableLogToDebugOutput(severity.ordinal());
        loggingEnabled = true;
    }

    @Deprecated
    public static void enableTracing(String str, EnumSet<TraceLevel> enumSet) {
    }

    private static String getStackTraceString(Throwable th) {
        if (th == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static void log(Severity severity, String str, String str2) {
        if (loggingEnabled) {
            nativeLog(severity.ordinal(), str, str2);
            return;
        }
        int i2 = AnonymousClass1.$SwitchMap$org$wrtca$log$Logging$Severity[severity.ordinal()];
        Level level = i2 != 1 ? i2 != 2 ? i2 != 3 ? Level.FINE : Level.INFO : Level.WARNING : Level.SEVERE;
        fallbackLogger.log(level, str + ": " + str2);
    }

    private static native void nativeEnableLogThreads();

    private static native void nativeEnableLogTimeStamps();

    private static native void nativeEnableLogToDebugOutput(int i2);

    private static native void nativeLog(int i2, String str, String str2);

    public static void v(String str, String str2) {
        log(Severity.LS_VERBOSE, str, str2);
    }

    public static void w(String str, String str2) {
        log(Severity.LS_WARNING, str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        Severity severity = Severity.LS_ERROR;
        log(severity, str, str2);
        log(severity, str, th.toString());
        log(severity, str, getStackTraceString(th));
    }

    public static void w(String str, String str2, Throwable th) {
        Severity severity = Severity.LS_WARNING;
        log(severity, str, str2);
        log(severity, str, th.toString());
        log(severity, str, getStackTraceString(th));
    }
}
