package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import org.eclipse.jetty.http.EncodedHttpURI;
import org.eclipse.jetty.http.Generator;
import org.eclipse.jetty.http.HttpBuffers;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpMethods;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersions;
import org.eclipse.jetty.http.Parser;
import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferCache;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.UncheckedPrintWriter;
import org.eclipse.jetty.server.nio.NIOConnector;
import org.eclipse.jetty.server.ssl.SslConnector;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

/* loaded from: classes9.dex */
public abstract class AbstractHttpConnection extends AbstractConnection {
    private static final int UNKNOWN = -2;
    private Object _associatedObject;
    private String _charset;
    protected final Connector _connector;
    private boolean _delayedHandling;
    private boolean _earlyEOF;
    private boolean _expect;
    private boolean _expect100Continue;
    private boolean _expect102Processing;
    protected final Generator _generator;
    private boolean _head;
    private boolean _host;
    protected volatile ServletInputStream _in;
    int _include;
    protected volatile Output _out;
    protected final Parser _parser;
    protected volatile PrintWriter _printWriter;
    protected final Request _request;
    protected final HttpFields _requestFields;
    private int _requests;
    protected final Response _response;
    protected final HttpFields _responseFields;
    protected final Server _server;
    protected final HttpURI _uri;
    private int _version;
    protected volatile OutputWriter _writer;
    private static final Logger LOG = Log.getLogger((Class<?>) AbstractHttpConnection.class);
    private static final ThreadLocal<AbstractHttpConnection> __currentConnection = new ThreadLocal<>();

    public class Output extends HttpOutput {
        public Output() {
            super(AbstractHttpConnection.this);
        }

        @Override // org.eclipse.jetty.server.HttpOutput
        public void close() throws IOException {
            if (isClosed()) {
                return;
            }
            if (AbstractHttpConnection.this.isIncluding() || this._generator.isCommitted()) {
                AbstractHttpConnection.this.flushResponse();
            } else {
                AbstractHttpConnection.this.commitResponse(true);
            }
            super.close();
        }

        @Override // org.eclipse.jetty.server.HttpOutput
        public void flush() throws IOException {
            if (!this._generator.isCommitted()) {
                AbstractHttpConnection.this.commitResponse(false);
            }
            super.flush();
        }

        @Override // org.eclipse.jetty.server.HttpOutput
        public void print(String str) throws IOException {
            if (isClosed()) {
                throw new IOException("Closed");
            }
            AbstractHttpConnection.this.getPrintWriter(null).print(str);
        }

        public void sendContent(Object obj) throws IOException {
            if (isClosed()) {
                throw new IOException("Closed");
            }
            if (this._generator.isWritten()) {
                throw new IllegalStateException("!empty");
            }
            Resource resource = null;
            if (obj instanceof HttpContent) {
                HttpContent httpContent = (HttpContent) obj;
                Buffer contentType = httpContent.getContentType();
                if (contentType != null) {
                    HttpFields httpFields = AbstractHttpConnection.this._responseFields;
                    Buffer buffer = HttpHeaders.CONTENT_TYPE_BUFFER;
                    if (!httpFields.containsKey(buffer)) {
                        String setCharacterEncoding = AbstractHttpConnection.this._response.getSetCharacterEncoding();
                        if (setCharacterEncoding == null) {
                            AbstractHttpConnection.this._responseFields.add(buffer, contentType);
                        } else if (contentType instanceof BufferCache.CachedBuffer) {
                            BufferCache.CachedBuffer associate = ((BufferCache.CachedBuffer) contentType).getAssociate(setCharacterEncoding);
                            if (associate != null) {
                                AbstractHttpConnection.this._responseFields.put(buffer, associate);
                            } else {
                                AbstractHttpConnection.this._responseFields.put(buffer, contentType + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(setCharacterEncoding, ";= "));
                            }
                        } else {
                            AbstractHttpConnection.this._responseFields.put(buffer, contentType + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(setCharacterEncoding, ";= "));
                        }
                    }
                }
                if (httpContent.getContentLength() > 0) {
                    AbstractHttpConnection.this._responseFields.putLongField(HttpHeaders.CONTENT_LENGTH_BUFFER, httpContent.getContentLength());
                }
                Buffer lastModified = httpContent.getLastModified();
                long jLastModified = httpContent.getResource().lastModified();
                if (lastModified != null) {
                    AbstractHttpConnection.this._responseFields.put(HttpHeaders.LAST_MODIFIED_BUFFER, lastModified);
                } else if (httpContent.getResource() != null && jLastModified != -1) {
                    AbstractHttpConnection.this._responseFields.putDateField(HttpHeaders.LAST_MODIFIED_BUFFER, jLastModified);
                }
                Buffer eTag = httpContent.getETag();
                if (eTag != null) {
                    AbstractHttpConnection.this._responseFields.put(HttpHeaders.ETAG_BUFFER, eTag);
                }
                Connector connector = AbstractHttpConnection.this._connector;
                Buffer directBuffer = (connector instanceof NIOConnector) && ((NIOConnector) connector).getUseDirectBuffers() && !(AbstractHttpConnection.this._connector instanceof SslConnector) ? httpContent.getDirectBuffer() : httpContent.getIndirectBuffer();
                obj = directBuffer == null ? httpContent.getInputStream() : directBuffer;
            } else if (obj instanceof Resource) {
                resource = (Resource) obj;
                AbstractHttpConnection.this._responseFields.putDateField(HttpHeaders.LAST_MODIFIED_BUFFER, resource.lastModified());
                obj = resource.getInputStream();
            }
            if (obj instanceof Buffer) {
                this._generator.addContent((Buffer) obj, true);
                AbstractHttpConnection.this.commitResponse(true);
                return;
            }
            if (!(obj instanceof InputStream)) {
                throw new IllegalArgumentException("unknown content type?");
            }
            InputStream inputStream = (InputStream) obj;
            try {
                int from = this._generator.getUncheckedBuffer().readFrom(inputStream, this._generator.prepareUncheckedAddContent());
                while (from >= 0) {
                    this._generator.completeUncheckedAddContent();
                    AbstractHttpConnection.this._out.flush();
                    from = this._generator.getUncheckedBuffer().readFrom(inputStream, this._generator.prepareUncheckedAddContent());
                }
                this._generator.completeUncheckedAddContent();
                AbstractHttpConnection.this._out.flush();
                if (resource != null) {
                    resource.release();
                } else {
                    inputStream.close();
                }
            } catch (Throwable th) {
                if (resource != null) {
                    resource.release();
                } else {
                    inputStream.close();
                }
                throw th;
            }
        }

        public void sendResponse(Buffer buffer) throws IOException {
            ((HttpGenerator) this._generator).sendResponse(buffer);
        }
    }

    public class OutputWriter extends HttpWriter {
        public OutputWriter() {
            super(AbstractHttpConnection.this._out);
        }
    }

    public class RequestHandler extends HttpParser.EventHandler {
        private RequestHandler() {
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void content(Buffer buffer) throws Throwable {
            AbstractHttpConnection.this.content(buffer);
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void earlyEOF() {
            AbstractHttpConnection.this.earlyEOF();
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void headerComplete() throws Throwable {
            AbstractHttpConnection.this.headerComplete();
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void messageComplete(long j2) throws Throwable {
            AbstractHttpConnection.this.messageComplete(j2);
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void parsedHeader(Buffer buffer, Buffer buffer2) throws IOException, IllegalArgumentException {
            AbstractHttpConnection.this.parsedHeader(buffer, buffer2);
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
            AbstractHttpConnection.this.startRequest(buffer, buffer2, buffer3);
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void startResponse(Buffer buffer, int i2, Buffer buffer2) {
            if (AbstractHttpConnection.LOG.isDebugEnabled()) {
                AbstractHttpConnection.LOG.debug("Bad request!: " + buffer + " " + i2 + " " + buffer2, new Object[0]);
            }
        }
    }

    public AbstractHttpConnection(Connector connector, EndPoint endPoint, Server server) {
        super(endPoint);
        this._version = -2;
        this._expect = false;
        this._expect100Continue = false;
        this._expect102Processing = false;
        this._head = false;
        this._host = false;
        this._delayedHandling = false;
        this._earlyEOF = false;
        String str = URIUtil.__CHARSET;
        this._uri = "UTF-8".equals(str) ? new HttpURI() : new EncodedHttpURI(str);
        this._connector = connector;
        HttpBuffers httpBuffers = (HttpBuffers) connector;
        this._parser = newHttpParser(httpBuffers.getRequestBuffers(), endPoint, new RequestHandler());
        this._requestFields = new HttpFields();
        this._responseFields = new HttpFields();
        this._request = new Request(this);
        this._response = new Response(this);
        HttpGenerator httpGeneratorNewHttpGenerator = newHttpGenerator(httpBuffers.getResponseBuffers(), endPoint);
        this._generator = httpGeneratorNewHttpGenerator;
        httpGeneratorNewHttpGenerator.setSendServerVersion(server.getSendServerVersion());
        this._server = server;
    }

    public static AbstractHttpConnection getCurrentConnection() {
        return __currentConnection.get();
    }

    public static void setCurrentConnection(AbstractHttpConnection abstractHttpConnection) {
        __currentConnection.set(abstractHttpConnection);
    }

    public void commitResponse(boolean z2) throws IOException {
        if (!this._generator.isCommitted()) {
            this._generator.setResponse(this._response.getStatus(), this._response.getReason());
            try {
                if (this._expect100Continue && this._response.getStatus() != 100) {
                    this._generator.setPersistent(false);
                }
                this._generator.completeHeader(this._responseFields, z2);
            } catch (RuntimeException e2) {
                LOG.warn("header full: " + e2, new Object[0]);
                this._response.reset();
                this._generator.reset();
                this._generator.setResponse(500, null);
                this._generator.completeHeader(this._responseFields, true);
                this._generator.complete();
                throw new HttpException(500);
            }
        }
        if (z2) {
            this._generator.complete();
        }
    }

    public void completeResponse() throws IOException {
        if (!this._generator.isCommitted()) {
            this._generator.setResponse(this._response.getStatus(), this._response.getReason());
            try {
                this._generator.completeHeader(this._responseFields, true);
            } catch (RuntimeException e2) {
                Logger logger = LOG;
                logger.warn("header full: " + e2, new Object[0]);
                logger.debug(e2);
                this._response.reset();
                this._generator.reset();
                this._generator.setResponse(500, null);
                this._generator.completeHeader(this._responseFields, true);
                this._generator.complete();
                throw new HttpException(500);
            }
        }
        this._generator.complete();
    }

    public void content(Buffer buffer) throws Throwable {
        if (this._delayedHandling) {
            this._delayedHandling = false;
            handleRequest();
        }
    }

    public void earlyEOF() {
        this._earlyEOF = true;
    }

    public void flushResponse() throws IOException {
        try {
            commitResponse(false);
            this._generator.flushBuffer();
        } catch (IOException e2) {
            if (!(e2 instanceof EofException)) {
                throw new EofException(e2);
            }
        }
    }

    public Object getAssociatedObject() {
        return this._associatedObject;
    }

    public Connector getConnector() {
        return this._connector;
    }

    public Generator getGenerator() {
        return this._generator;
    }

    public ServletInputStream getInputStream() throws IOException {
        if (this._expect100Continue) {
            if (((HttpParser) this._parser).getHeaderBuffer() == null || ((HttpParser) this._parser).getHeaderBuffer().length() < 2) {
                if (this._generator.isCommitted()) {
                    throw new IllegalStateException("Committed before 100 Continues");
                }
                ((HttpGenerator) this._generator).send1xx(100);
            }
            this._expect100Continue = false;
        }
        if (this._in == null) {
            this._in = new HttpInput(this);
        }
        return this._in;
    }

    public int getMaxIdleTime() {
        return (this._connector.isLowResources() && this._endp.getMaxIdleTime() == this._connector.getMaxIdleTime()) ? this._connector.getLowResourceMaxIdleTime() : this._endp.getMaxIdleTime() > 0 ? this._endp.getMaxIdleTime() : this._connector.getMaxIdleTime();
    }

    public ServletOutputStream getOutputStream() {
        if (this._out == null) {
            this._out = new Output();
        }
        return this._out;
    }

    public Parser getParser() {
        return this._parser;
    }

    public PrintWriter getPrintWriter(String str) {
        getOutputStream();
        if (this._writer == null) {
            this._writer = new OutputWriter();
            if (this._server.isUncheckedPrintWriter()) {
                this._printWriter = new UncheckedPrintWriter(this._writer);
            } else {
                this._printWriter = new PrintWriter(this._writer) { // from class: org.eclipse.jetty.server.AbstractHttpConnection.1
                    @Override // java.io.PrintWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
                    public void close() {
                        synchronized (((PrintWriter) this).lock) {
                            try {
                                ((PrintWriter) this).out.close();
                            } catch (IOException unused) {
                                setError();
                            }
                        }
                    }
                };
            }
        }
        this._writer.setCharacterEncoding(str);
        return this._printWriter;
    }

    public Request getRequest() {
        return this._request;
    }

    public HttpFields getRequestFields() {
        return this._requestFields;
    }

    public int getRequests() {
        return this._requests;
    }

    public boolean getResolveNames() {
        return this._connector.getResolveNames();
    }

    public Response getResponse() {
        return this._response;
    }

    public HttpFields getResponseFields() {
        return this._responseFields;
    }

    public Server getServer() {
        return this._server;
    }

    @Override // org.eclipse.jetty.io.Connection
    public abstract Connection handle() throws IOException;

    /* JADX WARN: Removed duplicated region for block: B:100:0x01e8 A[PHI: r0 r11
      0x01e8: PHI (r0v79 boolean) = (r0v52 boolean), (r0v58 boolean), (r0v87 boolean) binds: [B:99:0x01e6, B:88:0x01bd, B:78:0x0188] A[DONT_GENERATE, DONT_INLINE]
      0x01e8: PHI (r11v16 java.lang.Throwable) = (r11v7 java.lang.Throwable), (r11v6 java.lang.Throwable), (r11v17 java.lang.Throwable) binds: [B:99:0x01e6, B:88:0x01bd, B:78:0x0188] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01ea A[PHI: r0 r11
      0x01ea: PHI (r0v76 boolean) = 
      (r0v52 boolean)
      (r0v52 boolean)
      (r0v52 boolean)
      (r0v58 boolean)
      (r0v58 boolean)
      (r0v58 boolean)
      (r0v87 boolean)
      (r0v87 boolean)
      (r0v87 boolean)
     binds: [B:95:0x01dc, B:97:0x01e2, B:99:0x01e6, B:84:0x01b3, B:86:0x01b9, B:88:0x01bd, B:74:0x017e, B:76:0x0184, B:78:0x0188] A[DONT_GENERATE, DONT_INLINE]
      0x01ea: PHI (r11v14 java.lang.Throwable) = 
      (r11v7 java.lang.Throwable)
      (r11v7 java.lang.Throwable)
      (r11v7 java.lang.Throwable)
      (r11v6 java.lang.Throwable)
      (r11v6 java.lang.Throwable)
      (r11v6 java.lang.Throwable)
      (r11v17 java.lang.Throwable)
      (r11v17 java.lang.Throwable)
      (r11v17 java.lang.Throwable)
     binds: [B:95:0x01dc, B:97:0x01e2, B:99:0x01e6, B:84:0x01b3, B:86:0x01b9, B:88:0x01bd, B:74:0x017e, B:76:0x0184, B:78:0x0188] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0069 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:243:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleRequest() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 936
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AbstractHttpConnection.handleRequest():void");
    }

    public void headerComplete() throws Throwable {
        if (this._endp.isOutputShutdown()) {
            this._endp.close();
            return;
        }
        this._requests++;
        this._generator.setVersion(this._version);
        int i2 = this._version;
        if (i2 == 10) {
            this._generator.setHead(this._head);
            if (this._parser.isPersistent()) {
                this._responseFields.add(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.KEEP_ALIVE_BUFFER);
                this._generator.setPersistent(true);
            } else if (HttpMethods.CONNECT.equals(this._request.getMethod())) {
                this._generator.setPersistent(true);
                this._parser.setPersistent(true);
            }
            if (this._server.getSendDateHeader()) {
                this._generator.setDate(this._request.getTimeStampBuffer());
            }
        } else if (i2 == 11) {
            this._generator.setHead(this._head);
            if (!this._parser.isPersistent()) {
                this._responseFields.add(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.CLOSE_BUFFER);
                this._generator.setPersistent(false);
            }
            if (this._server.getSendDateHeader()) {
                this._generator.setDate(this._request.getTimeStampBuffer());
            }
            if (!this._host) {
                LOG.debug("!host {}", this);
                this._generator.setResponse(400, null);
                this._responseFields.put(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.CLOSE_BUFFER);
                this._generator.completeHeader(this._responseFields, true);
                this._generator.complete();
                return;
            }
            if (this._expect) {
                LOG.debug("!expectation {}", this);
                this._generator.setResponse(417, null);
                this._responseFields.put(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.CLOSE_BUFFER);
                this._generator.completeHeader(this._responseFields, true);
                this._generator.complete();
                return;
            }
        }
        String str = this._charset;
        if (str != null) {
            this._request.setCharacterEncodingUnchecked(str);
        }
        if ((((HttpParser) this._parser).getContentLength() > 0 || ((HttpParser) this._parser).isChunking()) && !this._expect100Continue) {
            this._delayedHandling = true;
        } else {
            handleRequest();
        }
    }

    public void include() {
        this._include++;
    }

    public void included() {
        this._include--;
        if (this._out != null) {
            this._out.reopen();
        }
    }

    public boolean isConfidential(Request request) {
        Connector connector = this._connector;
        return connector != null && connector.isConfidential(request);
    }

    public boolean isEarlyEOF() {
        return this._earlyEOF;
    }

    public boolean isExpecting100Continues() {
        return this._expect100Continue;
    }

    public boolean isExpecting102Processing() {
        return this._expect102Processing;
    }

    @Override // org.eclipse.jetty.io.Connection
    public boolean isIdle() {
        return this._generator.isIdle() && (this._parser.isIdle() || this._delayedHandling);
    }

    public boolean isIncluding() {
        return this._include > 0;
    }

    public boolean isIntegral(Request request) {
        Connector connector = this._connector;
        return connector != null && connector.isIntegral(request);
    }

    public boolean isResponseCommitted() {
        return this._generator.isCommitted();
    }

    @Override // org.eclipse.jetty.io.Connection
    public boolean isSuspended() {
        return this._request.getAsyncContinuation().isSuspended();
    }

    public void messageComplete(long j2) throws Throwable {
        if (this._delayedHandling) {
            this._delayedHandling = false;
            handleRequest();
        }
    }

    public HttpGenerator newHttpGenerator(Buffers buffers, EndPoint endPoint) {
        return new HttpGenerator(buffers, endPoint);
    }

    public HttpParser newHttpParser(Buffers buffers, EndPoint endPoint, HttpParser.EventHandler eventHandler) {
        return new HttpParser(buffers, endPoint, eventHandler);
    }

    @Override // org.eclipse.jetty.io.Connection
    public void onClose() {
        LOG.debug("closed {}", this);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parsedHeader(org.eclipse.jetty.io.Buffer r8, org.eclipse.jetty.io.Buffer r9) throws java.io.IOException, java.lang.IllegalArgumentException {
        /*
            r7 = this;
            org.eclipse.jetty.http.HttpHeaders r0 = org.eclipse.jetty.http.HttpHeaders.CACHE
            int r0 = r0.getOrdinal(r8)
            r1 = 16
            if (r0 == r1) goto L88
            r1 = 21
            if (r0 == r1) goto L81
            r1 = 24
            r2 = 1
            if (r0 == r1) goto L21
            r1 = 27
            if (r0 == r1) goto L1d
            r1 = 40
            if (r0 == r1) goto L81
            goto L94
        L1d:
            r7._host = r2
            goto L94
        L21:
            int r0 = r7._version
            r1 = 11
            if (r0 < r1) goto L94
            org.eclipse.jetty.http.HttpHeaderValues r0 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            org.eclipse.jetty.io.Buffer r9 = r0.lookup(r9)
            int r0 = r0.getOrdinal(r9)
            r1 = 6
            if (r0 == r1) goto L7a
            r3 = 7
            if (r0 == r3) goto L73
            java.lang.String r0 = r9.toString()
            java.lang.String r4 = ","
            java.lang.String[] r0 = r0.split(r4)
            r4 = 0
        L42:
            if (r0 == 0) goto L94
            int r5 = r0.length
            if (r4 >= r5) goto L94
            org.eclipse.jetty.http.HttpHeaderValues r5 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            r6 = r0[r4]
            java.lang.String r6 = r6.trim()
            org.eclipse.jetty.io.BufferCache$CachedBuffer r5 = r5.get(r6)
            if (r5 != 0) goto L58
            r7._expect = r2
            goto L70
        L58:
            int r5 = r5.getOrdinal()
            if (r5 == r1) goto L6a
            if (r5 == r3) goto L63
            r7._expect = r2
            goto L70
        L63:
            org.eclipse.jetty.http.Generator r5 = r7._generator
            boolean r5 = r5 instanceof org.eclipse.jetty.http.HttpGenerator
            r7._expect102Processing = r5
            goto L70
        L6a:
            org.eclipse.jetty.http.Generator r5 = r7._generator
            boolean r5 = r5 instanceof org.eclipse.jetty.http.HttpGenerator
            r7._expect100Continue = r5
        L70:
            int r4 = r4 + 1
            goto L42
        L73:
            org.eclipse.jetty.http.Generator r0 = r7._generator
            boolean r0 = r0 instanceof org.eclipse.jetty.http.HttpGenerator
            r7._expect102Processing = r0
            goto L94
        L7a:
            org.eclipse.jetty.http.Generator r0 = r7._generator
            boolean r0 = r0 instanceof org.eclipse.jetty.http.HttpGenerator
            r7._expect100Continue = r0
            goto L94
        L81:
            org.eclipse.jetty.http.HttpHeaderValues r0 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            org.eclipse.jetty.io.Buffer r9 = r0.lookup(r9)
            goto L94
        L88:
            org.eclipse.jetty.io.BufferCache r0 = org.eclipse.jetty.http.MimeTypes.CACHE
            org.eclipse.jetty.io.Buffer r9 = r0.lookup(r9)
            java.lang.String r0 = org.eclipse.jetty.http.MimeTypes.getCharsetFromContentType(r9)
            r7._charset = r0
        L94:
            org.eclipse.jetty.http.HttpFields r0 = r7._requestFields
            r0.add(r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AbstractHttpConnection.parsedHeader(org.eclipse.jetty.io.Buffer, org.eclipse.jetty.io.Buffer):void");
    }

    public void reset() throws IOException {
        this._parser.reset();
        this._parser.returnBuffers();
        this._requestFields.clear();
        this._request.recycle();
        this._generator.reset();
        this._generator.returnBuffers();
        this._responseFields.clear();
        this._response.recycle();
        this._uri.clear();
        this._writer = null;
        this._earlyEOF = false;
    }

    public void setAssociatedObject(Object obj) {
        this._associatedObject = obj;
    }

    public void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        Buffer bufferAsImmutableBuffer = buffer2.asImmutableBuffer();
        this._host = false;
        this._expect = false;
        this._expect100Continue = false;
        this._expect102Processing = false;
        this._delayedHandling = false;
        this._charset = null;
        if (this._request.getTimeStamp() == 0) {
            this._request.setTimeStamp(System.currentTimeMillis());
        }
        this._request.setMethod(buffer.toString());
        try {
            this._head = false;
            int ordinal = HttpMethods.CACHE.getOrdinal(buffer);
            if (ordinal == 3) {
                this._head = true;
                this._uri.parse(bufferAsImmutableBuffer.array(), bufferAsImmutableBuffer.getIndex(), bufferAsImmutableBuffer.length());
            } else if (ordinal != 8) {
                this._uri.parse(bufferAsImmutableBuffer.array(), bufferAsImmutableBuffer.getIndex(), bufferAsImmutableBuffer.length());
            } else {
                this._uri.parseConnect(bufferAsImmutableBuffer.array(), bufferAsImmutableBuffer.getIndex(), bufferAsImmutableBuffer.length());
            }
            this._request.setUri(this._uri);
            if (buffer3 == null) {
                this._request.setProtocol("");
                this._version = 9;
                return;
            }
            BufferCache bufferCache = HttpVersions.CACHE;
            BufferCache.CachedBuffer cachedBuffer = bufferCache.get(buffer3);
            if (cachedBuffer == null) {
                throw new HttpException(400, null);
            }
            int ordinal2 = bufferCache.getOrdinal(cachedBuffer);
            this._version = ordinal2;
            if (ordinal2 <= 0) {
                this._version = 10;
            }
            this._request.setProtocol(cachedBuffer.toString());
        } catch (Exception e2) {
            LOG.debug(e2);
            if (!(e2 instanceof HttpException)) {
                throw new HttpException(400, null, e2);
            }
            throw ((HttpException) e2);
        }
    }

    @Override // org.eclipse.jetty.io.AbstractConnection
    public String toString() {
        return String.format("%s,g=%s,p=%s,r=%d", super.toString(), this._generator, this._parser, Integer.valueOf(this._requests));
    }

    public AbstractHttpConnection(Connector connector, EndPoint endPoint, Server server, Parser parser, Generator generator, Request request) {
        super(endPoint);
        this._version = -2;
        this._expect = false;
        this._expect100Continue = false;
        this._expect102Processing = false;
        this._head = false;
        this._host = false;
        this._delayedHandling = false;
        this._earlyEOF = false;
        String str = URIUtil.__CHARSET;
        this._uri = str.equals("UTF-8") ? new HttpURI() : new EncodedHttpURI(str);
        this._connector = connector;
        this._parser = parser;
        this._requestFields = new HttpFields();
        this._responseFields = new HttpFields();
        this._request = request;
        this._response = new Response(this);
        this._generator = generator;
        generator.setSendServerVersion(server.getSendServerVersion());
        this._server = server;
    }
}
