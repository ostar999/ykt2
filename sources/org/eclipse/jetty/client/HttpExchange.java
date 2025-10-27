package org.eclipse.jetty.client;

import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpSchemes;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersions;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferCache;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* loaded from: classes9.dex */
public class HttpExchange {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final Logger LOG = Log.getLogger((Class<?>) HttpExchange.class);
    public static final int STATUS_CANCELLED = 11;
    public static final int STATUS_CANCELLING = 10;
    public static final int STATUS_COMPLETED = 7;
    public static final int STATUS_EXCEPTED = 9;
    public static final int STATUS_EXPIRED = 8;
    public static final int STATUS_PARSING_CONTENT = 6;
    public static final int STATUS_PARSING_HEADERS = 5;
    public static final int STATUS_SENDING_REQUEST = 3;
    public static final int STATUS_START = 0;
    public static final int STATUS_WAITING_FOR_COMMIT = 2;
    public static final int STATUS_WAITING_FOR_CONNECTION = 1;
    public static final int STATUS_WAITING_FOR_RESPONSE = 4;
    private Address _address;
    private volatile AbstractHttpConnection _connection;
    boolean _onDone;
    boolean _onRequestCompleteDone;
    boolean _onResponseCompleteDone;
    private Buffer _requestContent;
    private InputStream _requestContentSource;
    private volatile Timeout.Task _timeoutTask;
    private String _uri;
    private String _method = "GET";
    private Buffer _scheme = HttpSchemes.HTTP_BUFFER;
    private int _version = 11;
    private final HttpFields _requestFields = new HttpFields();
    private AtomicInteger _status = new AtomicInteger(0);
    private boolean _retryStatus = false;
    private boolean _configureListeners = true;
    private HttpEventListener _listener = new Listener();
    private Address _localAddress = null;
    private long _timeout = -1;
    private long _lastStateChange = System.currentTimeMillis();
    private long _sent = -1;
    private int _lastState = -1;
    private int _lastStatePeriod = -1;

    @Deprecated
    public static class CachedExchange extends org.eclipse.jetty.client.CachedExchange {
        public CachedExchange(boolean z2) {
            super(z2);
        }
    }

    @Deprecated
    public static class ContentExchange extends org.eclipse.jetty.client.ContentExchange {
    }

    public class Listener implements HttpEventListener {
        private Listener() {
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onConnectionFailed(Throwable th) {
            try {
                HttpExchange.this.onConnectionFailed(th);
            } finally {
                HttpExchange.this.done();
            }
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onException(Throwable th) {
            try {
                HttpExchange.this.onException(th);
            } finally {
                HttpExchange.this.done();
            }
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onExpire() {
            try {
                HttpExchange.this.onExpire();
            } finally {
                HttpExchange.this.done();
            }
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onRequestCommitted() throws IOException {
            HttpExchange.this.onRequestCommitted();
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onRequestComplete() throws IOException {
            try {
                HttpExchange.this.onRequestComplete();
                synchronized (HttpExchange.this) {
                    HttpExchange httpExchange = HttpExchange.this;
                    httpExchange._onRequestCompleteDone = true;
                    boolean z2 = httpExchange._onDone | httpExchange._onResponseCompleteDone;
                    httpExchange._onDone = z2;
                    if (z2) {
                        httpExchange.disassociate();
                    }
                    HttpExchange.this.notifyAll();
                }
            } catch (Throwable th) {
                synchronized (HttpExchange.this) {
                    HttpExchange httpExchange2 = HttpExchange.this;
                    httpExchange2._onRequestCompleteDone = true;
                    boolean z3 = httpExchange2._onDone | httpExchange2._onResponseCompleteDone;
                    httpExchange2._onDone = z3;
                    if (z3) {
                        httpExchange2.disassociate();
                    }
                    HttpExchange.this.notifyAll();
                    throw th;
                }
            }
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseComplete() throws IOException {
            try {
                HttpExchange.this.onResponseComplete();
                synchronized (HttpExchange.this) {
                    HttpExchange httpExchange = HttpExchange.this;
                    httpExchange._onResponseCompleteDone = true;
                    boolean z2 = httpExchange._onDone | httpExchange._onRequestCompleteDone;
                    httpExchange._onDone = z2;
                    if (z2) {
                        httpExchange.disassociate();
                    }
                    HttpExchange.this.notifyAll();
                }
            } catch (Throwable th) {
                synchronized (HttpExchange.this) {
                    HttpExchange httpExchange2 = HttpExchange.this;
                    httpExchange2._onResponseCompleteDone = true;
                    boolean z3 = httpExchange2._onDone | httpExchange2._onRequestCompleteDone;
                    httpExchange2._onDone = z3;
                    if (z3) {
                        httpExchange2.disassociate();
                    }
                    HttpExchange.this.notifyAll();
                    throw th;
                }
            }
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseContent(Buffer buffer) throws IOException {
            HttpExchange.this.onResponseContent(buffer);
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException {
            HttpExchange.this.onResponseHeader(buffer, buffer2);
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseHeaderComplete() throws IOException {
            HttpExchange.this.onResponseHeaderComplete();
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseStatus(Buffer buffer, int i2, Buffer buffer2) throws IOException {
            HttpExchange.this.onResponseStatus(buffer, i2, buffer2);
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onRetry() {
            HttpExchange.this.setRetryStatus(true);
            try {
                HttpExchange.this.onRetry();
            } catch (IOException e2) {
                HttpExchange.LOG.debug(e2);
            }
        }
    }

    private void abort() {
        AbstractHttpConnection abstractHttpConnection = this._connection;
        try {
            if (abstractHttpConnection != null) {
                try {
                    abstractHttpConnection.close();
                } catch (IOException e2) {
                    LOG.debug(e2);
                }
            }
        } finally {
            disassociate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void done() {
        synchronized (this) {
            disassociate();
            this._onDone = true;
            notifyAll();
        }
    }

    private boolean setStatusExpired(int i2, int i3) {
        boolean zCompareAndSet = this._status.compareAndSet(i3, i2);
        if (zCompareAndSet) {
            getEventListener().onExpire();
        }
        return zCompareAndSet;
    }

    public static String toState(int i2) {
        switch (i2) {
            case 0:
                return "START";
            case 1:
                return "CONNECTING";
            case 2:
                return "CONNECTED";
            case 3:
                return "SENDING";
            case 4:
                return "WAITING";
            case 5:
                return "HEADERS";
            case 6:
                return "CONTENT";
            case 7:
                return "COMPLETED";
            case 8:
                return "EXPIRED";
            case 9:
                return "EXCEPTED";
            case 10:
                return "CANCELLING";
            case 11:
                return "CANCELLED";
            default:
                return "UNKNOWN";
        }
    }

    public void addRequestHeader(String str, String str2) throws IllegalArgumentException {
        getRequestFields().add(str, str2);
    }

    public void associate(AbstractHttpConnection abstractHttpConnection) {
        if (abstractHttpConnection.getEndPoint().getLocalAddr() != null) {
            this._localAddress = new Address(abstractHttpConnection.getEndPoint().getLocalAddr(), abstractHttpConnection.getEndPoint().getLocalPort());
        }
        this._connection = abstractHttpConnection;
        if (getStatus() == 10) {
            abort();
        }
    }

    public void cancel() {
        setStatus(10);
        abort();
    }

    public void cancelTimeout(HttpClient httpClient) {
        Timeout.Task task = this._timeoutTask;
        if (task != null) {
            httpClient.cancel(task);
        }
        this._timeoutTask = null;
    }

    public boolean configureListeners() {
        return this._configureListeners;
    }

    public AbstractHttpConnection disassociate() {
        AbstractHttpConnection abstractHttpConnection = this._connection;
        this._connection = null;
        if (getStatus() == 10) {
            setStatus(11);
        }
        return abstractHttpConnection;
    }

    public void expire(HttpDestination httpDestination) {
        AbstractHttpConnection abstractHttpConnection = this._connection;
        if (getStatus() < 7) {
            setStatus(8);
        }
        httpDestination.exchangeExpired(this);
        if (abstractHttpConnection != null) {
            abstractHttpConnection.exchangeExpired(this);
        }
    }

    public Address getAddress() {
        return this._address;
    }

    public HttpEventListener getEventListener() {
        return this._listener;
    }

    public Address getLocalAddress() {
        return this._localAddress;
    }

    public String getMethod() {
        return this._method;
    }

    public Buffer getRequestContent() {
        return this._requestContent;
    }

    public Buffer getRequestContentChunk(Buffer buffer) throws IOException {
        synchronized (this) {
            if (this._requestContentSource != null) {
                if (buffer == null) {
                    buffer = new ByteArrayBuffer(8192);
                }
                int i2 = this._requestContentSource.read(buffer.array(), buffer.putIndex(), buffer.space());
                if (i2 >= 0) {
                    buffer.setPutIndex(buffer.putIndex() + i2);
                    return buffer;
                }
            }
            return null;
        }
    }

    public InputStream getRequestContentSource() {
        return this._requestContentSource;
    }

    public HttpFields getRequestFields() {
        return this._requestFields;
    }

    public String getRequestURI() {
        return this._uri;
    }

    public boolean getRetryStatus() {
        return this._retryStatus;
    }

    public Buffer getScheme() {
        return this._scheme;
    }

    public int getStatus() {
        return this._status.get();
    }

    public long getTimeout() {
        return this._timeout;
    }

    @Deprecated
    public String getURI() {
        return getRequestURI();
    }

    public int getVersion() {
        return this._version;
    }

    public boolean isAssociated() {
        return this._connection != null;
    }

    public boolean isDone() {
        boolean z2;
        synchronized (this) {
            z2 = this._onDone;
        }
        return z2;
    }

    public void onConnectionFailed(Throwable th) {
        LOG.warn("CONNECTION FAILED " + this, th);
    }

    public void onException(Throwable th) {
        LOG.warn(Log.EXCEPTION + this, th);
    }

    public void onExpire() {
        LOG.warn("EXPIRED " + this, new Object[0]);
    }

    public void onRequestCommitted() throws IOException {
    }

    public void onRequestComplete() throws IOException {
    }

    public void onResponseComplete() throws IOException {
    }

    public void onResponseContent(Buffer buffer) throws IOException {
    }

    public void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException {
    }

    public void onResponseHeaderComplete() throws IOException {
    }

    public void onResponseStatus(Buffer buffer, int i2, Buffer buffer2) throws IOException {
    }

    public void onRetry() throws IOException {
        InputStream inputStream = this._requestContentSource;
        if (inputStream != null) {
            if (!inputStream.markSupported()) {
                throw new IOException("Unsupported retry attempt");
            }
            this._requestContent = null;
            this._requestContentSource.reset();
        }
    }

    public Connection onSwitchProtocol(EndPoint endPoint) throws IOException {
        return null;
    }

    public void reset() {
        synchronized (this) {
            this._timeoutTask = null;
            this._onRequestCompleteDone = false;
            this._onResponseCompleteDone = false;
            this._onDone = false;
            setStatus(0);
        }
    }

    public void scheduleTimeout(final HttpDestination httpDestination) {
        this._timeoutTask = new Timeout.Task() { // from class: org.eclipse.jetty.client.HttpExchange.1
            @Override // org.eclipse.jetty.util.thread.Timeout.Task
            public void expired() {
                HttpExchange.this.expire(httpDestination);
            }
        };
        HttpClient httpClient = httpDestination.getHttpClient();
        long timeout = getTimeout();
        if (timeout > 0) {
            httpClient.schedule(this._timeoutTask, timeout);
        } else {
            httpClient.schedule(this._timeoutTask);
        }
    }

    public void setAddress(Address address) {
        this._address = address;
    }

    public void setConfigureListeners(boolean z2) {
        this._configureListeners = z2;
    }

    public void setEventListener(HttpEventListener httpEventListener) {
        this._listener = httpEventListener;
    }

    public void setMethod(String str) {
        this._method = str;
    }

    public void setRequestContent(Buffer buffer) {
        this._requestContent = buffer;
    }

    public void setRequestContentSource(InputStream inputStream) {
        this._requestContentSource = inputStream;
        if (inputStream == null || !inputStream.markSupported()) {
            return;
        }
        this._requestContentSource.mark(Integer.MAX_VALUE);
    }

    public void setRequestContentType(String str) {
        getRequestFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, str);
    }

    public void setRequestHeader(String str, String str2) {
        getRequestFields().put(str, str2);
    }

    public void setRequestURI(String str) {
        this._uri = str;
    }

    public void setRetryStatus(boolean z2) {
        this._retryStatus = z2;
    }

    public void setScheme(Buffer buffer) {
        this._scheme = buffer;
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00bc A[Catch: IOException -> 0x0171, TryCatch #1 {IOException -> 0x0171, blocks: (B:3:0x0001, B:5:0x000a, B:7:0x001b, B:10:0x0025, B:89:0x015b, B:90:0x0170, B:16:0x0032, B:24:0x0048, B:25:0x004c, B:28:0x0058, B:30:0x005f, B:31:0x0067, B:33:0x006c, B:34:0x0074, B:35:0x007a, B:41:0x0090, B:42:0x0098, B:43:0x009e, B:51:0x00b6, B:52:0x00bc, B:54:0x00c6, B:56:0x00ca, B:57:0x00d1, B:58:0x00d6, B:65:0x00ec, B:66:0x00f1, B:68:0x00fa, B:70:0x00fe, B:71:0x0103, B:75:0x0110, B:77:0x0115, B:78:0x011a), top: B:95:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f1 A[Catch: IOException -> 0x0171, TryCatch #1 {IOException -> 0x0171, blocks: (B:3:0x0001, B:5:0x000a, B:7:0x001b, B:10:0x0025, B:89:0x015b, B:90:0x0170, B:16:0x0032, B:24:0x0048, B:25:0x004c, B:28:0x0058, B:30:0x005f, B:31:0x0067, B:33:0x006c, B:34:0x0074, B:35:0x007a, B:41:0x0090, B:42:0x0098, B:43:0x009e, B:51:0x00b6, B:52:0x00bc, B:54:0x00c6, B:56:0x00ca, B:57:0x00d1, B:58:0x00d6, B:65:0x00ec, B:66:0x00f1, B:68:0x00fa, B:70:0x00fe, B:71:0x0103, B:75:0x0110, B:77:0x0115, B:78:0x011a), top: B:95:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0103 A[Catch: IOException -> 0x0171, TryCatch #1 {IOException -> 0x0171, blocks: (B:3:0x0001, B:5:0x000a, B:7:0x001b, B:10:0x0025, B:89:0x015b, B:90:0x0170, B:16:0x0032, B:24:0x0048, B:25:0x004c, B:28:0x0058, B:30:0x005f, B:31:0x0067, B:33:0x006c, B:34:0x0074, B:35:0x007a, B:41:0x0090, B:42:0x0098, B:43:0x009e, B:51:0x00b6, B:52:0x00bc, B:54:0x00c6, B:56:0x00ca, B:57:0x00d1, B:58:0x00d6, B:65:0x00ec, B:66:0x00f1, B:68:0x00fa, B:70:0x00fe, B:71:0x0103, B:75:0x0110, B:77:0x0115, B:78:0x011a), top: B:95:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x011a A[Catch: IOException -> 0x0171, TRY_LEAVE, TryCatch #1 {IOException -> 0x0171, blocks: (B:3:0x0001, B:5:0x000a, B:7:0x001b, B:10:0x0025, B:89:0x015b, B:90:0x0170, B:16:0x0032, B:24:0x0048, B:25:0x004c, B:28:0x0058, B:30:0x005f, B:31:0x0067, B:33:0x006c, B:34:0x0074, B:35:0x007a, B:41:0x0090, B:42:0x0098, B:43:0x009e, B:51:0x00b6, B:52:0x00bc, B:54:0x00c6, B:56:0x00ca, B:57:0x00d1, B:58:0x00d6, B:65:0x00ec, B:66:0x00f1, B:68:0x00fa, B:70:0x00fe, B:71:0x0103, B:75:0x0110, B:77:0x0115, B:78:0x011a), top: B:95:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0123 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean setStatus(int r8) {
        /*
            Method dump skipped, instructions count: 492
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.client.HttpExchange.setStatus(int):boolean");
    }

    public void setTimeout(long j2) {
        this._timeout = j2;
    }

    @Deprecated
    public void setURI(String str) {
        setRequestURI(str);
    }

    public void setURL(String str) {
        setURI(URI.create(str));
    }

    public void setVersion(int i2) {
        this._version = i2;
    }

    public String toString() {
        String state = toState(getStatus());
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = jCurrentTimeMillis - this._lastStateChange;
        String str = this._lastState >= 0 ? String.format("%s@%x=%s//%s%s#%s(%dms)->%s(%dms)", getClass().getSimpleName(), Integer.valueOf(hashCode()), this._method, this._address, this._uri, toState(this._lastState), Integer.valueOf(this._lastStatePeriod), state, Long.valueOf(j2)) : String.format("%s@%x=%s//%s%s#%s(%dms)", getClass().getSimpleName(), Integer.valueOf(hashCode()), this._method, this._address, this._uri, state, Long.valueOf(j2));
        if (getStatus() < 3 || this._sent <= 0) {
            return str;
        }
        return str + "sent=" + (jCurrentTimeMillis - this._sent) + "ms";
    }

    public int waitForDone() throws InterruptedException {
        int i2;
        synchronized (this) {
            while (!isDone()) {
                wait();
            }
            i2 = this._status.get();
        }
        return i2;
    }

    @Deprecated
    public void waitForStatus(int i2) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    public void addRequestHeader(Buffer buffer, Buffer buffer2) throws IllegalArgumentException {
        getRequestFields().add(buffer, buffer2);
    }

    public void setRequestHeader(Buffer buffer, Buffer buffer2) {
        getRequestFields().put(buffer, buffer2);
    }

    public void setScheme(String str) {
        if (str != null) {
            if ("http".equalsIgnoreCase(str)) {
                setScheme(HttpSchemes.HTTP_BUFFER);
            } else if ("https".equalsIgnoreCase(str)) {
                setScheme(HttpSchemes.HTTPS_BUFFER);
            } else {
                setScheme(new ByteArrayBuffer(str));
            }
        }
    }

    public void setURI(URI uri) {
        if (!uri.isAbsolute()) {
            throw new IllegalArgumentException("!Absolute URI: " + uri);
        }
        if (uri.isOpaque()) {
            throw new IllegalArgumentException("Opaque URI: " + uri);
        }
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("URI = {}", uri.toASCIIString());
        }
        String scheme = uri.getScheme();
        int port = uri.getPort();
        if (port <= 0) {
            port = "https".equalsIgnoreCase(scheme) ? R2.attr.banner_indicator_selected_color : 80;
        }
        setScheme(scheme);
        setAddress(new Address(uri.getHost(), port));
        String completePath = new HttpURI(uri).getCompletePath();
        if (completePath == null) {
            completePath = "/";
        }
        setRequestURI(completePath);
    }

    public void setVersion(String str) {
        BufferCache.CachedBuffer cachedBuffer = HttpVersions.CACHE.get(str);
        if (cachedBuffer == null) {
            this._version = 10;
        } else {
            this._version = cachedBuffer.getOrdinal();
        }
    }

    @Deprecated
    public boolean isDone(int i2) {
        return isDone();
    }
}
