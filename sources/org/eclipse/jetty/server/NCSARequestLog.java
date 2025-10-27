package org.eclipse.jetty.server;

import com.heytap.mcssdk.constant.a;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.http.Cookie;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.util.DateCache;
import org.eclipse.jetty.util.RolloverFileOutputStream;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class NCSARequestLog extends AbstractLifeCycle implements RequestLog {
    private static final Logger LOG = Log.getLogger((Class<?>) NCSARequestLog.class);
    private static ThreadLocal<StringBuilder> _buffers = new ThreadLocal<StringBuilder>() { // from class: org.eclipse.jetty.server.NCSARequestLog.1
        @Override // java.lang.ThreadLocal
        public StringBuilder initialValue() {
            return new StringBuilder(256);
        }
    };
    private boolean _closeOut;
    private transient OutputStream _fileOut;
    private String _filename;
    private transient PathMap _ignorePathMap;
    private String[] _ignorePaths;
    private transient DateCache _logDateCache;
    private transient OutputStream _out;
    private boolean _preferProxiedForAddress;
    private transient Writer _writer;
    private String _logDateFormat = "dd/MMM/yyyy:HH:mm:ss Z";
    private String _filenameDateFormat = null;
    private Locale _logLocale = Locale.getDefault();
    private String _logTimeZone = "GMT";
    private boolean _logLatency = false;
    private boolean _logCookies = false;
    private boolean _logServer = false;
    private boolean _logDispatch = false;
    private boolean _extended = true;
    private boolean _append = true;
    private int _retainDays = 31;

    public NCSARequestLog() {
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public synchronized void doStart() throws Exception {
        if (this._logDateFormat != null) {
            DateCache dateCache = new DateCache(this._logDateFormat, this._logLocale);
            this._logDateCache = dateCache;
            dateCache.setTimeZoneID(this._logTimeZone);
        }
        int i2 = 0;
        if (this._filename != null) {
            this._fileOut = new RolloverFileOutputStream(this._filename, this._append, this._retainDays, TimeZone.getTimeZone(this._logTimeZone), this._filenameDateFormat, null);
            this._closeOut = true;
            LOG.info("Opened " + getDatedFilename(), new Object[0]);
        } else {
            this._fileOut = System.err;
        }
        this._out = this._fileOut;
        String[] strArr = this._ignorePaths;
        if (strArr != null && strArr.length > 0) {
            this._ignorePathMap = new PathMap();
            while (true) {
                String[] strArr2 = this._ignorePaths;
                if (i2 >= strArr2.length) {
                    break;
                }
                PathMap pathMap = this._ignorePathMap;
                String str = strArr2[i2];
                pathMap.put(str, str);
                i2++;
            }
        } else {
            this._ignorePathMap = null;
        }
        synchronized (this) {
            this._writer = new OutputStreamWriter(this._out);
        }
        super.doStart();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0024 A[Catch: all -> 0x0032, TryCatch #0 {, blocks: (B:3:0x0001, B:4:0x0004, B:6:0x0008, B:10:0x0012, B:12:0x0016, B:14:0x001a, B:17:0x001f, B:18:0x0024, B:19:0x0030, B:9:0x000d), top: B:24:0x0001, inners: #1, #2 }] */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doStop() throws java.lang.Exception {
        /*
            r2 = this;
            monitor-enter(r2)
            super.doStop()     // Catch: java.lang.Throwable -> L32
            java.io.Writer r0 = r2._writer     // Catch: java.io.IOException -> Lc java.lang.Throwable -> L32
            if (r0 == 0) goto L12
            r0.flush()     // Catch: java.io.IOException -> Lc java.lang.Throwable -> L32
            goto L12
        Lc:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.NCSARequestLog.LOG     // Catch: java.lang.Throwable -> L32
            r1.ignore(r0)     // Catch: java.lang.Throwable -> L32
        L12:
            java.io.OutputStream r0 = r2._out     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L24
            boolean r1 = r2._closeOut     // Catch: java.lang.Throwable -> L32
            if (r1 == 0) goto L24
            r0.close()     // Catch: java.io.IOException -> L1e java.lang.Throwable -> L32
            goto L24
        L1e:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.NCSARequestLog.LOG     // Catch: java.lang.Throwable -> L32
            r1.ignore(r0)     // Catch: java.lang.Throwable -> L32
        L24:
            r0 = 0
            r2._out = r0     // Catch: java.lang.Throwable -> L32
            r2._fileOut = r0     // Catch: java.lang.Throwable -> L32
            r1 = 0
            r2._closeOut = r1     // Catch: java.lang.Throwable -> L32
            r2._logDateCache = r0     // Catch: java.lang.Throwable -> L32
            r2._writer = r0     // Catch: java.lang.Throwable -> L32
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L32
            return
        L32:
            r0 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L32
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.NCSARequestLog.doStop():void");
    }

    public String getDatedFilename() {
        OutputStream outputStream = this._fileOut;
        if (outputStream instanceof RolloverFileOutputStream) {
            return ((RolloverFileOutputStream) outputStream).getDatedFilename();
        }
        return null;
    }

    public String getFilename() {
        return this._filename;
    }

    public String getFilenameDateFormat() {
        return this._filenameDateFormat;
    }

    public String[] getIgnorePaths() {
        return this._ignorePaths;
    }

    public boolean getLogCookies() {
        return this._logCookies;
    }

    public String getLogDateFormat() {
        return this._logDateFormat;
    }

    public boolean getLogLatency() {
        return this._logLatency;
    }

    public Locale getLogLocale() {
        return this._logLocale;
    }

    public boolean getLogServer() {
        return this._logServer;
    }

    public String getLogTimeZone() {
        return this._logTimeZone;
    }

    public boolean getPreferProxiedForAddress() {
        return this._preferProxiedForAddress;
    }

    public int getRetainDays() {
        return this._retainDays;
    }

    public boolean isAppend() {
        return this._append;
    }

    public boolean isExtended() {
        return this._extended;
    }

    public boolean isLogDispatch() {
        return this._logDispatch;
    }

    @Override // org.eclipse.jetty.server.RequestLog
    public void log(Request request, Response response) {
        try {
            PathMap pathMap = this._ignorePathMap;
            if ((pathMap == null || pathMap.getMatch(request.getRequestURI()) == null) && this._fileOut != null) {
                StringBuilder sb = _buffers.get();
                sb.setLength(0);
                if (this._logServer) {
                    sb.append(request.getServerName());
                    sb.append(' ');
                }
                String header = this._preferProxiedForAddress ? request.getHeader("X-Forwarded-For") : null;
                if (header == null) {
                    header = request.getRemoteAddr();
                }
                sb.append(header);
                sb.append(" - ");
                Authentication authentication = request.getAuthentication();
                if (authentication instanceof Authentication.User) {
                    sb.append(((Authentication.User) authentication).getUserIdentity().getUserPrincipal().getName());
                } else {
                    sb.append(" - ");
                }
                sb.append(" [");
                DateCache dateCache = this._logDateCache;
                if (dateCache != null) {
                    sb.append(dateCache.format(request.getTimeStamp()));
                } else {
                    sb.append(request.getTimeStampBuffer().toString());
                }
                sb.append("] \"");
                sb.append(request.getMethod());
                sb.append(' ');
                sb.append(request.getUri().toString());
                sb.append(' ');
                sb.append(request.getProtocol());
                sb.append("\" ");
                if (request.getAsyncContinuation().isInitial()) {
                    int status = response.getStatus();
                    if (status <= 0) {
                        status = 404;
                    }
                    sb.append((char) (((status / 100) % 10) + 48));
                    sb.append((char) (((status / 10) % 10) + 48));
                    sb.append((char) ((status % 10) + 48));
                } else {
                    sb.append("Async");
                }
                long contentCount = response.getContentCount();
                if (contentCount >= 0) {
                    sb.append(' ');
                    if (contentCount > 99999) {
                        sb.append(contentCount);
                    } else {
                        if (contentCount > 9999) {
                            sb.append((char) (((contentCount / a.f7153q) % 10) + 48));
                        }
                        if (contentCount > 999) {
                            sb.append((char) (((contentCount / 1000) % 10) + 48));
                        }
                        if (contentCount > 99) {
                            sb.append((char) (((contentCount / 100) % 10) + 48));
                        }
                        if (contentCount > 9) {
                            sb.append((char) (((contentCount / 10) % 10) + 48));
                        }
                        sb.append((char) ((contentCount % 10) + 48));
                    }
                    sb.append(' ');
                } else {
                    sb.append(" - ");
                }
                if (this._extended) {
                    logExtended(request, response, sb);
                }
                if (this._logCookies) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies == null || cookies.length == 0) {
                        sb.append(" -");
                    } else {
                        sb.append(" \"");
                        for (int i2 = 0; i2 < cookies.length; i2++) {
                            if (i2 != 0) {
                                sb.append(';');
                            }
                            sb.append(cookies[i2].getName());
                            sb.append('=');
                            sb.append(cookies[i2].getValue());
                        }
                        sb.append('\"');
                    }
                }
                if (this._logDispatch || this._logLatency) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (this._logDispatch) {
                        long dispatchTime = request.getDispatchTime();
                        sb.append(' ');
                        if (dispatchTime == 0) {
                            dispatchTime = request.getTimeStamp();
                        }
                        sb.append(jCurrentTimeMillis - dispatchTime);
                    }
                    if (this._logLatency) {
                        sb.append(' ');
                        sb.append(jCurrentTimeMillis - request.getTimeStamp());
                    }
                }
                sb.append(StringUtil.__LINE_SEPARATOR);
                write(sb.toString());
            }
        } catch (IOException e2) {
            LOG.warn(e2);
        }
    }

    public void logExtended(Request request, Response response, StringBuilder sb) throws IOException {
        String header = request.getHeader("Referer");
        if (header == null) {
            sb.append("\"-\" ");
        } else {
            sb.append('\"');
            sb.append(header);
            sb.append("\" ");
        }
        String header2 = request.getHeader("User-Agent");
        if (header2 == null) {
            sb.append("\"-\" ");
            return;
        }
        sb.append('\"');
        sb.append(header2);
        sb.append('\"');
    }

    public void setAppend(boolean z2) {
        this._append = z2;
    }

    public void setExtended(boolean z2) {
        this._extended = z2;
    }

    public void setFilename(String str) {
        if (str != null) {
            str = str.trim();
            if (str.length() == 0) {
                str = null;
            }
        }
        this._filename = str;
    }

    public void setFilenameDateFormat(String str) {
        this._filenameDateFormat = str;
    }

    public void setIgnorePaths(String[] strArr) {
        this._ignorePaths = strArr;
    }

    public void setLogCookies(boolean z2) {
        this._logCookies = z2;
    }

    public void setLogDateFormat(String str) {
        this._logDateFormat = str;
    }

    public void setLogDispatch(boolean z2) {
        this._logDispatch = z2;
    }

    public void setLogLatency(boolean z2) {
        this._logLatency = z2;
    }

    public void setLogLocale(Locale locale) {
        this._logLocale = locale;
    }

    public void setLogServer(boolean z2) {
        this._logServer = z2;
    }

    public void setLogTimeZone(String str) {
        this._logTimeZone = str;
    }

    public void setPreferProxiedForAddress(boolean z2) {
        this._preferProxiedForAddress = z2;
    }

    public void setRetainDays(int i2) {
        this._retainDays = i2;
    }

    public void write(String str) throws IOException {
        synchronized (this) {
            Writer writer = this._writer;
            if (writer == null) {
                return;
            }
            writer.write(str);
            this._writer.flush();
        }
    }

    public NCSARequestLog(String str) {
        setFilename(str);
    }
}
