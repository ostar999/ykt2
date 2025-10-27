package com.alibaba.sdk.android.logger;

import android.util.Log;
import cn.hutool.core.text.StrPool;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes2.dex */
public class BaseSdkLogApi {

    /* renamed from: a, reason: collision with root package name */
    private static final LogLevel f2833a = LogLevel.WARN;

    /* renamed from: b, reason: collision with root package name */
    private static final ILogger f2834b = new c(null);

    /* renamed from: d, reason: collision with root package name */
    private LogLevel f2836d;

    /* renamed from: h, reason: collision with root package name */
    private String f2840h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f2841i;

    /* renamed from: c, reason: collision with root package name */
    private boolean f2835c = true;

    /* renamed from: e, reason: collision with root package name */
    private ILogger f2837e = f2834b;

    /* renamed from: f, reason: collision with root package name */
    private ArrayList<ILogger> f2838f = new ArrayList<>();

    /* renamed from: g, reason: collision with root package name */
    private b f2839g = new b(this, null);

    /* renamed from: com.alibaba.sdk.android.logger.BaseSdkLogApi$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2842a;

        static {
            int[] iArr = new int[LogLevel.values().length];
            f2842a = iArr;
            try {
                iArr[LogLevel.DEBUG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2842a[LogLevel.INFO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2842a[LogLevel.WARN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2842a[LogLevel.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class a implements ILogger {

        /* renamed from: a, reason: collision with root package name */
        private ILogger f2843a;

        /* renamed from: b, reason: collision with root package name */
        private boolean f2844b;

        /* renamed from: c, reason: collision with root package name */
        private SimpleDateFormat f2845c;

        private a(ILogger iLogger, boolean z2) {
            this.f2843a = iLogger;
            this.f2844b = z2;
            if (z2) {
                this.f2845c = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS");
            }
        }

        public /* synthetic */ a(ILogger iLogger, boolean z2, AnonymousClass1 anonymousClass1) {
            this(iLogger, z2);
        }

        private String a() {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            for (int i2 = 1; i2 < stackTrace.length; i2++) {
                if (!stackTrace[i2].getClassName().startsWith("com.alibaba.sdk.android.logger")) {
                    return "(" + stackTrace[i2].getFileName() + ":" + stackTrace[i2].getLineNumber() + ")";
                }
            }
            return "";
        }

        @Override // com.alibaba.sdk.android.logger.ILogger
        public void print(LogLevel logLevel, String str, String str2) {
            if (this.f2844b) {
                str2 = StrPool.BRACKET_START + this.f2845c.format(new Date()) + StrPool.BRACKET_END + str2 + a();
            }
            this.f2843a.print(logLevel, str, str2);
        }
    }

    public class b implements ILogger {
        private b() {
        }

        public /* synthetic */ b(BaseSdkLogApi baseSdkLogApi, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.alibaba.sdk.android.logger.ILogger
        public void print(LogLevel logLevel, String str, String str2) {
            if (BaseSdkLogApi.this.a(logLevel) && BaseSdkLogApi.this.f2837e != null) {
                try {
                    BaseSdkLogApi.this.f2837e.print(logLevel, str, str2);
                } catch (Throwable unused) {
                }
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(BaseSdkLogApi.this.f2838f);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    ((ILogger) it.next()).print(logLevel, str, str2);
                } catch (Throwable unused2) {
                }
            }
        }
    }

    public static class c implements ILogger {
        private c() {
        }

        public /* synthetic */ c(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.alibaba.sdk.android.logger.ILogger
        public void print(LogLevel logLevel, String str, String str2) {
            int i2 = AnonymousClass1.f2842a[logLevel.ordinal()];
            if (i2 == 1) {
                Log.d(str, str2);
                return;
            }
            if (i2 == 2) {
                Log.i(str, str2);
            } else if (i2 == 3) {
                Log.w(str, str2);
            } else {
                if (i2 != 4) {
                    return;
                }
                Log.e(str, str2);
            }
        }
    }

    public static class d implements ILog {

        /* renamed from: a, reason: collision with root package name */
        private final String f2847a;

        /* renamed from: b, reason: collision with root package name */
        private ILogger f2848b;

        public d(String str, ILogger iLogger) {
            this.f2847a = str;
            this.f2848b = iLogger;
        }

        @Override // com.alibaba.sdk.android.logger.ILog
        public void d(String str) {
            this.f2848b.print(LogLevel.DEBUG, this.f2847a, str);
        }

        @Override // com.alibaba.sdk.android.logger.ILog
        public void e(String str) {
            e(str, null);
        }

        @Override // com.alibaba.sdk.android.logger.ILog
        public void e(String str, Throwable th) {
            ILogger iLogger = this.f2848b;
            LogLevel logLevel = LogLevel.ERROR;
            iLogger.print(logLevel, this.f2847a, str);
            if (th != null) {
                this.f2848b.print(logLevel, this.f2847a, Log.getStackTraceString(th));
            }
        }

        @Override // com.alibaba.sdk.android.logger.ILog
        public void i(String str) {
            this.f2848b.print(LogLevel.INFO, this.f2847a, str);
        }

        @Override // com.alibaba.sdk.android.logger.ILog
        public void w(String str) {
            w(str, null);
        }

        @Override // com.alibaba.sdk.android.logger.ILog
        public void w(String str, Throwable th) {
            ILogger iLogger = this.f2848b;
            LogLevel logLevel = LogLevel.WARN;
            iLogger.print(logLevel, this.f2847a, str);
            if (th != null) {
                this.f2848b.print(logLevel, this.f2847a, Log.getStackTraceString(th));
            }
        }
    }

    public BaseSdkLogApi(String str, boolean z2) {
        this.f2836d = f2833a;
        this.f2840h = str;
        if (str == null) {
            this.f2840h = ServletHandler.__DEFAULT_SERVLET;
        }
        this.f2841i = z2;
        if (z2) {
            this.f2836d = LogLevel.DEBUG;
        }
    }

    private String a(Object obj) {
        String simpleName;
        if (obj == null) {
            simpleName = "";
        } else if (obj instanceof Class) {
            simpleName = ((Class) obj).getSimpleName();
        } else if (obj instanceof String) {
            simpleName = (String) obj;
        } else {
            simpleName = obj.getClass().getSimpleName() + "@" + obj.hashCode();
        }
        return this.f2840h + StrPool.UNDERLINE + simpleName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(LogLevel logLevel) {
        return this.f2835c && logLevel.ordinal() >= this.f2836d.ordinal();
    }

    public void addILogger(ILogger iLogger) {
        if (iLogger != null) {
            this.f2838f.add(iLogger);
        }
    }

    public void enable(boolean z2) {
        this.f2835c = z2;
    }

    public ILog getLogger(Object obj) {
        return new d(a(obj), new a(this.f2839g, this.f2841i, null));
    }

    public void removeILogger(ILogger iLogger) {
        if (iLogger != null) {
            this.f2838f.remove(iLogger);
        }
    }

    public void setILogger(ILogger iLogger) {
        if (iLogger == null) {
            iLogger = f2834b;
        }
        this.f2837e = iLogger;
    }

    public void setLevel(LogLevel logLevel) {
        this.f2836d = logLevel;
    }
}
