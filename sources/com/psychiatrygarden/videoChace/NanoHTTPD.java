package com.psychiatrygarden.videoChace;

import cn.hutool.core.date.DatePattern;
import com.alipay.sdk.packet.d;
import com.alipay.sdk.util.h;
import com.plv.foundationsdk.web.PLVWebview;
import com.tencent.smtt.sdk.TbsVideoCacheTask;
import com.yikaobang.yixue.R2;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import net.lingala.zip4j.util.InternalZipConstants;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpVersions;

/* loaded from: classes6.dex */
public abstract class NanoHTTPD {
    public static final String MIME_HTML = "text/html";
    public static final String MIME_PLAINTEXT = "text/plain";
    protected static Map<String, String> MIME_TYPES = null;
    private static final String QUERY_STRING_PARAMETER = "NanoHttpd.QUERY_STRING";
    public static final int SOCKET_READ_TIMEOUT = 5000;
    protected AsyncRunner asyncRunner;
    private final String hostname;
    private final int myPort;
    private volatile ServerSocket myServerSocket;
    private Thread myThread;
    private ServerSocketFactory serverSocketFactory;
    private TempFileManagerFactory tempFileManagerFactory;
    private static final String CONTENT_DISPOSITION_REGEX = "([ |\t]*Content-Disposition[ |\t]*:)(.*)";
    private static final Pattern CONTENT_DISPOSITION_PATTERN = Pattern.compile(CONTENT_DISPOSITION_REGEX, 2);
    private static final String CONTENT_TYPE_REGEX = "([ |\t]*content-type[ |\t]*:)(.*)";
    private static final Pattern CONTENT_TYPE_PATTERN = Pattern.compile(CONTENT_TYPE_REGEX, 2);
    private static final String CONTENT_DISPOSITION_ATTRIBUTE_REGEX = "[ |\t]*([a-zA-Z]*)[ |\t]*=[ |\t]*['|\"]([^\"^']*)['|\"]";
    private static final Pattern CONTENT_DISPOSITION_ATTRIBUTE_PATTERN = Pattern.compile(CONTENT_DISPOSITION_ATTRIBUTE_REGEX);
    private static final Logger LOG = Logger.getLogger(NanoHTTPD.class.getName());

    public interface AsyncRunner {
        void closeAll();

        void closed(ClientHandler clientHandler);

        void exec(ClientHandler code);
    }

    public class ClientHandler implements Runnable {
        private final Socket acceptSocket;
        private final InputStream inputStream;

        public void close() throws IOException {
            NanoHTTPD.safeClose(this.inputStream);
            NanoHTTPD.safeClose(this.acceptSocket);
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            OutputStream outputStream = null;
            try {
                try {
                    outputStream = this.acceptSocket.getOutputStream();
                    HTTPSession hTTPSession = NanoHTTPD.this.new HTTPSession(NanoHTTPD.this.tempFileManagerFactory.create(), this.inputStream, outputStream, this.acceptSocket.getInetAddress());
                    while (!this.acceptSocket.isClosed()) {
                        hTTPSession.execute();
                    }
                } catch (Exception e2) {
                    if ((!(e2 instanceof SocketException) || !"NanoHttpd Shutdown".equals(e2.getMessage())) && !(e2 instanceof SocketTimeoutException)) {
                        NanoHTTPD.LOG.log(Level.SEVERE, "Communication with the client broken, or an bug in the handler code", (Throwable) e2);
                    }
                }
            } finally {
                NanoHTTPD.safeClose(outputStream);
                NanoHTTPD.safeClose(this.inputStream);
                NanoHTTPD.safeClose(this.acceptSocket);
                NanoHTTPD.this.asyncRunner.closed(this);
            }
        }

        private ClientHandler(InputStream inputStream, Socket acceptSocket) {
            this.inputStream = inputStream;
            this.acceptSocket = acceptSocket;
        }
    }

    public static class ContentType {
        private static final String ASCII_ENCODING = "US-ASCII";
        private static final String MULTIPART_FORM_DATA_HEADER = "multipart/form-data";
        private final String boundary;
        private final String contentType;
        private final String contentTypeHeader;
        private final String encoding;
        private static final String CONTENT_REGEX = "[ |\t]*([^/^ ^;^,]+/[^ ^;^,]+)";
        private static final Pattern MIME_PATTERN = Pattern.compile(CONTENT_REGEX, 2);
        private static final String CHARSET_REGEX = "[ |\t]*(charset)[ |\t]*=[ |\t]*['|\"]?([^\"^'^;^,]*)['|\"]?";
        private static final Pattern CHARSET_PATTERN = Pattern.compile(CHARSET_REGEX, 2);
        private static final String BOUNDARY_REGEX = "[ |\t]*(boundary)[ |\t]*=[ |\t]*['|\"]?([^\"^'^;^,]*)['|\"]?";
        private static final Pattern BOUNDARY_PATTERN = Pattern.compile(BOUNDARY_REGEX, 2);

        public ContentType(String contentTypeHeader) {
            this.contentTypeHeader = contentTypeHeader;
            if (contentTypeHeader != null) {
                this.contentType = getDetailFromContentHeader(contentTypeHeader, MIME_PATTERN, "", 1);
                this.encoding = getDetailFromContentHeader(contentTypeHeader, CHARSET_PATTERN, null, 2);
            } else {
                this.contentType = "";
                this.encoding = "UTF-8";
            }
            if ("multipart/form-data".equalsIgnoreCase(this.contentType)) {
                this.boundary = getDetailFromContentHeader(contentTypeHeader, BOUNDARY_PATTERN, null, 2);
            } else {
                this.boundary = null;
            }
        }

        private String getDetailFromContentHeader(String contentTypeHeader, Pattern pattern, String defaultValue, int group) {
            Matcher matcher = pattern.matcher(contentTypeHeader);
            return matcher.find() ? matcher.group(group) : defaultValue;
        }

        public String getBoundary() {
            return this.boundary;
        }

        public String getContentType() {
            return this.contentType;
        }

        public String getContentTypeHeader() {
            return this.contentTypeHeader;
        }

        public String getEncoding() {
            String str = this.encoding;
            return str == null ? "US-ASCII" : str;
        }

        public boolean isMultipart() {
            return "multipart/form-data".equalsIgnoreCase(this.contentType);
        }

        public ContentType tryUTF8() {
            if (this.encoding != null) {
                return this;
            }
            return new ContentType(this.contentTypeHeader + "; charset=UTF-8");
        }
    }

    public static class Cookie {

        /* renamed from: e, reason: collision with root package name */
        private final String f16255e;

        /* renamed from: n, reason: collision with root package name */
        private final String f16256n;

        /* renamed from: v, reason: collision with root package name */
        private final String f16257v;

        public Cookie(String name, String value) {
            this(name, value, 30);
        }

        public static String getHTTPTime(int days) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.HTTP_DATETIME_PATTERN, Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            calendar.add(5, days);
            return simpleDateFormat.format(calendar.getTime());
        }

        public String getHTTPHeader() {
            return String.format("%s=%s; expires=%s", this.f16256n, this.f16257v, this.f16255e);
        }

        public Cookie(String name, String value, int numDays) {
            this.f16256n = name;
            this.f16257v = value;
            this.f16255e = getHTTPTime(numDays);
        }

        public Cookie(String name, String value, String expires) {
            this.f16256n = name;
            this.f16257v = value;
            this.f16255e = expires;
        }
    }

    public class CookieHandler implements Iterable<String> {
        private final HashMap<String, String> cookies = new HashMap<>();
        private final ArrayList<Cookie> queue = new ArrayList<>();

        public CookieHandler(Map<String, String> httpHeaders) {
            String str = httpHeaders.get("cookie");
            if (str != null) {
                for (String str2 : str.split(h.f3376b)) {
                    String[] strArrSplit = str2.trim().split("=");
                    if (strArrSplit.length == 2) {
                        this.cookies.put(strArrSplit[0], strArrSplit[1]);
                    }
                }
            }
        }

        public void delete(String name) {
            set(name, "-delete-", -30);
        }

        @Override // java.lang.Iterable
        public Iterator<String> iterator() {
            return this.cookies.keySet().iterator();
        }

        public String read(String name) {
            return this.cookies.get(name);
        }

        public void set(Cookie cookie) {
            this.queue.add(cookie);
        }

        public void unloadQueue(Response response) {
            Iterator<Cookie> it = this.queue.iterator();
            while (it.hasNext()) {
                response.addHeader("Set-Cookie", it.next().getHTTPHeader());
            }
        }

        public void set(String name, String value, int expires) {
            this.queue.add(new Cookie(name, value, Cookie.getHTTPTime(expires)));
        }
    }

    public static class DefaultAsyncRunner implements AsyncRunner {
        private long requestCount;
        private final List<ClientHandler> running = Collections.synchronizedList(new ArrayList());

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.AsyncRunner
        public void closeAll() throws IOException {
            Iterator it = new ArrayList(this.running).iterator();
            while (it.hasNext()) {
                ((ClientHandler) it.next()).close();
            }
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.AsyncRunner
        public void closed(ClientHandler clientHandler) {
            this.running.remove(clientHandler);
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.AsyncRunner
        public void exec(ClientHandler clientHandler) {
            this.requestCount++;
            Thread thread = new Thread(clientHandler);
            thread.setDaemon(true);
            thread.setName("NanoHttpd Request Processor (#" + this.requestCount + ")");
            this.running.add(clientHandler);
            thread.start();
        }

        public List<ClientHandler> getRunning() {
            return this.running;
        }
    }

    public static class DefaultServerSocketFactory implements ServerSocketFactory {
        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.ServerSocketFactory
        public ServerSocket create() throws IOException {
            return new ServerSocket();
        }
    }

    public static class DefaultTempFile implements TempFile {
        private final File file;
        private final OutputStream fstream;

        public DefaultTempFile(File tempdir) throws IOException {
            File fileCreateTempFile = File.createTempFile("NanoHTTPD-", "", tempdir);
            this.file = fileCreateTempFile;
            this.fstream = new FileOutputStream(fileCreateTempFile);
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.TempFile
        public void delete() throws Exception {
            NanoHTTPD.safeClose(this.fstream);
            if (!this.file.delete()) {
                throw new Exception("could not delete temporary file");
            }
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.TempFile
        public String getName() {
            return this.file.getAbsolutePath();
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.TempFile
        public OutputStream open() throws Exception {
            return this.fstream;
        }
    }

    public static class DefaultTempFileManager implements TempFileManager {
        private final List<TempFile> tempFiles;
        private final File tmpdir;

        public DefaultTempFileManager() {
            File file = new File(System.getProperty("java.io.tmpdir"));
            this.tmpdir = file;
            if (!file.exists()) {
                file.mkdirs();
            }
            this.tempFiles = new ArrayList();
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.TempFileManager
        public void clear() {
            Iterator<TempFile> it = this.tempFiles.iterator();
            while (it.hasNext()) {
                try {
                    it.next().delete();
                } catch (Exception e2) {
                    NanoHTTPD.LOG.log(Level.WARNING, "could not delete file ", (Throwable) e2);
                }
            }
            this.tempFiles.clear();
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.TempFileManager
        public TempFile createTempFile(String filename_hint) throws Exception {
            DefaultTempFile defaultTempFile = new DefaultTempFile(this.tmpdir);
            this.tempFiles.add(defaultTempFile);
            return defaultTempFile;
        }
    }

    public class DefaultTempFileManagerFactory implements TempFileManagerFactory {
        private DefaultTempFileManagerFactory() {
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.TempFileManagerFactory
        public TempFileManager create() {
            return new DefaultTempFileManager();
        }
    }

    public interface IHTTPSession {
        void execute() throws IOException;

        CookieHandler getCookies();

        Map<String, String> getHeaders();

        InputStream getInputStream();

        Method getMethod();

        Map<String, String> getParms();

        String getQueryParameterString();

        String getRemoteHostName();

        String getRemoteIpAddress();

        String getUri();

        void parseBody(Map<String, String> files) throws ResponseException, IOException;
    }

    public enum Method {
        GET,
        PUT,
        POST,
        DELETE,
        HEAD,
        OPTIONS,
        TRACE,
        CONNECT,
        PATCH,
        PROPFIND,
        PROPPATCH,
        MKCOL,
        MOVE,
        COPY,
        LOCK,
        UNLOCK;

        public static Method lookup(String method) {
            if (method == null) {
                return null;
            }
            try {
                return valueOf(method);
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
    }

    public static class Response implements Closeable {
        private boolean chunkedTransfer;
        private long contentLength;
        private InputStream data;
        private boolean encodeAsGzip;
        private boolean keepAlive;
        private String mimeType;
        private Method requestMethod;
        private IStatus status;
        private final Map<String, String> header = new HashMap<String, String>() { // from class: com.psychiatrygarden.videoChace.NanoHTTPD.Response.1
            @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
            public String put(String key, String value) {
                Response.this.lowerCaseHeader.put(key == null ? key : key.toLowerCase(), value);
                return (String) super.put((AnonymousClass1) key, value);
            }
        };
        private final Map<String, String> lowerCaseHeader = new HashMap();

        public static class ChunkedOutputStream extends FilterOutputStream {
            public ChunkedOutputStream(OutputStream out) {
                super(out);
            }

            public void finish() throws IOException {
                ((FilterOutputStream) this).out.write("0\r\n\r\n".getBytes());
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream
            public void write(int b3) throws IOException {
                write(new byte[]{(byte) b3}, 0, 1);
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream
            public void write(byte[] b3) throws IOException {
                write(b3, 0, b3.length);
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream
            public void write(byte[] b3, int off, int len) throws IOException {
                if (len == 0) {
                    return;
                }
                ((FilterOutputStream) this).out.write(String.format("%x\r\n", Integer.valueOf(len)).getBytes());
                ((FilterOutputStream) this).out.write(b3, off, len);
                ((FilterOutputStream) this).out.write("\r\n".getBytes());
            }
        }

        public interface IStatus {
            String getDescription();

            int getRequestStatus();
        }

        public enum Status implements IStatus {
            SWITCH_PROTOCOL(101, "Switching Protocols"),
            OK(200, PLVWebview.MESSAGE_OK),
            CREATED(201, "Created"),
            ACCEPTED(202, "Accepted"),
            NO_CONTENT(204, "No Content"),
            PARTIAL_CONTENT(206, "Partial Content"),
            MULTI_STATUS(207, "Multi-Status"),
            REDIRECT(301, "Moved Permanently"),
            FOUND(302, "Found"),
            REDIRECT_SEE_OTHER(303, "See Other"),
            NOT_MODIFIED(304, "Not Modified"),
            TEMPORARY_REDIRECT(307, "Temporary Redirect"),
            BAD_REQUEST(400, "Bad Request"),
            UNAUTHORIZED(401, "Unauthorized"),
            FORBIDDEN(403, "Forbidden"),
            NOT_FOUND(404, "Not Found"),
            METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
            NOT_ACCEPTABLE(406, "Not Acceptable"),
            REQUEST_TIMEOUT(408, "Request Timeout"),
            CONFLICT(409, "Conflict"),
            GONE(410, "Gone"),
            LENGTH_REQUIRED(411, "Length Required"),
            PRECONDITION_FAILED(412, "Precondition Failed"),
            PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
            UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
            RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
            EXPECTATION_FAILED(417, "Expectation Failed"),
            TOO_MANY_REQUESTS(R2.attr.badgeTextColor, "Too Many Requests"),
            INTERNAL_ERROR(500, "Internal Server Error"),
            NOT_IMPLEMENTED(501, "Not Implemented"),
            SERVICE_UNAVAILABLE(503, "Service Unavailable"),
            UNSUPPORTED_HTTP_VERSION(505, "HTTP Version Not Supported");

            private final String description;
            private final int requestStatus;

            Status(int requestStatus, String description) {
                this.requestStatus = requestStatus;
                this.description = description;
            }

            public static Status lookup(int requestStatus) {
                for (Status status : values()) {
                    if (status.getRequestStatus() == requestStatus) {
                        return status;
                    }
                }
                return null;
            }

            @Override // com.psychiatrygarden.videoChace.NanoHTTPD.Response.IStatus
            public String getDescription() {
                return "" + this.requestStatus + " " + this.description;
            }

            @Override // com.psychiatrygarden.videoChace.NanoHTTPD.Response.IStatus
            public int getRequestStatus() {
                return this.requestStatus;
            }
        }

        public Response(IStatus status, String mimeType, InputStream data, long totalBytes) {
            this.status = status;
            this.mimeType = mimeType;
            if (data == null) {
                this.data = new ByteArrayInputStream(new byte[0]);
                this.contentLength = 0L;
            } else {
                this.data = data;
                this.contentLength = totalBytes;
            }
            this.chunkedTransfer = this.contentLength < 0;
            this.keepAlive = true;
        }

        private void sendBody(OutputStream outputStream, long pending) throws IOException {
            byte[] bArr = new byte[(int) 16384];
            boolean z2 = pending == -1;
            while (true) {
                if (pending <= 0 && !z2) {
                    return;
                }
                int i2 = this.data.read(bArr, 0, (int) (z2 ? 16384L : Math.min(pending, 16384L)));
                if (i2 <= 0) {
                    return;
                }
                outputStream.write(bArr, 0, i2);
                if (!z2) {
                    pending -= i2;
                }
            }
        }

        private void sendBodyWithCorrectEncoding(OutputStream outputStream, long pending) throws IOException {
            if (!this.encodeAsGzip) {
                sendBody(outputStream, pending);
                return;
            }
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(outputStream);
            sendBody(gZIPOutputStream, -1L);
            gZIPOutputStream.finish();
        }

        private void sendBodyWithCorrectTransferAndEncoding(OutputStream outputStream, long pending) throws IOException {
            if (this.requestMethod == Method.HEAD || !this.chunkedTransfer) {
                sendBodyWithCorrectEncoding(outputStream, pending);
                return;
            }
            ChunkedOutputStream chunkedOutputStream = new ChunkedOutputStream(outputStream);
            sendBodyWithCorrectEncoding(chunkedOutputStream, -1L);
            chunkedOutputStream.finish();
        }

        public void addHeader(String name, String value) {
            this.header.put(name, value);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            InputStream inputStream = this.data;
            if (inputStream != null) {
                inputStream.close();
            }
        }

        public void closeConnection(boolean close) {
            if (close) {
                this.header.put("connection", "close");
            } else {
                this.header.remove("connection");
            }
        }

        public InputStream getData() {
            return this.data;
        }

        public String getHeader(String name) {
            return this.lowerCaseHeader.get(name.toLowerCase());
        }

        public String getMimeType() {
            return this.mimeType;
        }

        public Method getRequestMethod() {
            return this.requestMethod;
        }

        public IStatus getStatus() {
            return this.status;
        }

        public boolean isCloseConnection() {
            return "close".equals(getHeader("connection"));
        }

        public void printHeader(PrintWriter pw, String key, String value) {
            pw.append((CharSequence) key).append(": ").append((CharSequence) value).append("\r\n");
        }

        public void send(OutputStream outputStream) throws IOException, NumberFormatException {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                if (this.status == null) {
                    throw new Error("sendResponse(): Status can't be null.");
                }
                PrintWriter printWriter = new PrintWriter((Writer) new BufferedWriter(new OutputStreamWriter(outputStream, new ContentType(this.mimeType).getEncoding())), false);
                printWriter.append("HTTP/1.1 ").append((CharSequence) this.status.getDescription()).append(" \r\n");
                String str = this.mimeType;
                if (str != null) {
                    printHeader(printWriter, "Content-Type", str);
                }
                if (getHeader("date") == null) {
                    printHeader(printWriter, "Date", simpleDateFormat.format(new Date()));
                }
                for (Map.Entry<String, String> entry : this.header.entrySet()) {
                    printHeader(printWriter, entry.getKey(), entry.getValue());
                }
                if (getHeader("connection") == null) {
                    printHeader(printWriter, "Connection", this.keepAlive ? HttpHeaderValues.KEEP_ALIVE : "close");
                }
                if (getHeader("content-length") != null) {
                    this.encodeAsGzip = false;
                }
                if (this.encodeAsGzip) {
                    printHeader(printWriter, "Content-Encoding", HttpHeaderValues.GZIP);
                    setChunkedTransfer(true);
                }
                long jSendContentLengthHeaderIfNotAlreadyPresent = this.data != null ? this.contentLength : 0L;
                if (this.requestMethod != Method.HEAD && this.chunkedTransfer) {
                    printHeader(printWriter, "Transfer-Encoding", "chunked");
                } else if (!this.encodeAsGzip) {
                    jSendContentLengthHeaderIfNotAlreadyPresent = sendContentLengthHeaderIfNotAlreadyPresent(printWriter, jSendContentLengthHeaderIfNotAlreadyPresent);
                }
                printWriter.append("\r\n");
                printWriter.flush();
                sendBodyWithCorrectTransferAndEncoding(outputStream, jSendContentLengthHeaderIfNotAlreadyPresent);
                outputStream.flush();
                NanoHTTPD.safeClose(this.data);
            } catch (IOException e2) {
                NanoHTTPD.LOG.log(Level.SEVERE, "Could not send response to the client", (Throwable) e2);
            }
        }

        public long sendContentLengthHeaderIfNotAlreadyPresent(PrintWriter pw, long defaultSize) throws NumberFormatException {
            String header = getHeader("content-length");
            if (header != null) {
                try {
                    defaultSize = Long.parseLong(header);
                } catch (NumberFormatException unused) {
                    NanoHTTPD.LOG.severe("content-length was no number " + header);
                }
            }
            pw.print("Content-Length: " + defaultSize + "\r\n");
            return defaultSize;
        }

        public void setChunkedTransfer(boolean chunkedTransfer) {
            this.chunkedTransfer = chunkedTransfer;
        }

        public void setData(InputStream data) {
            this.data = data;
        }

        public void setGzipEncoding(boolean encodeAsGzip) {
            this.encodeAsGzip = encodeAsGzip;
        }

        public void setKeepAlive(boolean useKeepAlive) {
            this.keepAlive = useKeepAlive;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public void setRequestMethod(Method requestMethod) {
            this.requestMethod = requestMethod;
        }

        public void setStatus(IStatus status) {
            this.status = status;
        }
    }

    public static class SecureServerSocketFactory implements ServerSocketFactory {
        private String[] sslProtocols;
        private SSLServerSocketFactory sslServerSocketFactory;

        public SecureServerSocketFactory(SSLServerSocketFactory sslServerSocketFactory, String[] sslProtocols) {
            this.sslServerSocketFactory = sslServerSocketFactory;
            this.sslProtocols = sslProtocols;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.ServerSocketFactory
        public ServerSocket create() throws IOException {
            SSLServerSocket sSLServerSocket = (SSLServerSocket) this.sslServerSocketFactory.createServerSocket();
            String[] strArr = this.sslProtocols;
            if (strArr != null) {
                sSLServerSocket.setEnabledProtocols(strArr);
            } else {
                sSLServerSocket.setEnabledProtocols(sSLServerSocket.getSupportedProtocols());
            }
            sSLServerSocket.setUseClientMode(false);
            sSLServerSocket.setWantClientAuth(false);
            sSLServerSocket.setNeedClientAuth(false);
            return sSLServerSocket;
        }
    }

    public class ServerRunnable implements Runnable {
        private IOException bindException;
        private boolean hasBinded;
        private final int timeout;

        @Override // java.lang.Runnable
        public void run() throws IOException {
            try {
                NanoHTTPD.this.myServerSocket.bind(NanoHTTPD.this.hostname != null ? new InetSocketAddress(NanoHTTPD.this.hostname, NanoHTTPD.this.myPort) : new InetSocketAddress(NanoHTTPD.this.myPort));
                this.hasBinded = true;
                do {
                    try {
                        Socket socketAccept = NanoHTTPD.this.myServerSocket.accept();
                        int i2 = this.timeout;
                        if (i2 > 0) {
                            socketAccept.setSoTimeout(i2);
                        }
                        InputStream inputStream = socketAccept.getInputStream();
                        NanoHTTPD nanoHTTPD = NanoHTTPD.this;
                        nanoHTTPD.asyncRunner.exec(nanoHTTPD.createClientHandler(socketAccept, inputStream));
                    } catch (IOException e2) {
                        NanoHTTPD.LOG.log(Level.FINE, "Communication with the client broken", (Throwable) e2);
                    }
                } while (!NanoHTTPD.this.myServerSocket.isClosed());
            } catch (IOException e3) {
                this.bindException = e3;
            }
        }

        private ServerRunnable(int timeout) {
            this.hasBinded = false;
            this.timeout = timeout;
        }
    }

    public interface ServerSocketFactory {
        ServerSocket create() throws IOException;
    }

    public interface TempFile {
        void delete() throws Exception;

        String getName();

        OutputStream open() throws Exception;
    }

    public interface TempFileManager {
        void clear();

        TempFile createTempFile(String filename_hint) throws Exception;
    }

    public interface TempFileManagerFactory {
        TempFileManager create();
    }

    public NanoHTTPD(int port) {
        this(null, port);
    }

    public static Map<String, List<String>> decodeParameters(Map<String, String> parms) {
        return decodeParameters(parms.get(QUERY_STRING_PARAMETER));
    }

    public static String decodePercent(String str) {
        try {
            return URLDecoder.decode(str, "UTF8");
        } catch (UnsupportedEncodingException e2) {
            LOG.log(Level.WARNING, "Encoding not supported, ignored", (Throwable) e2);
            return null;
        }
    }

    public static String getMimeTypeForFile(String uri) {
        int iLastIndexOf = uri.lastIndexOf(46);
        String str = iLastIndexOf >= 0 ? mimeTypes().get(uri.substring(iLastIndexOf + 1).toLowerCase()) : null;
        return str == null ? "application/octet-stream" : str;
    }

    private static void loadMimeTypes(Map<String, String> result, String resourceName) throws IOException {
        try {
            Enumeration<URL> resources = NanoHTTPD.class.getClassLoader().getResources(resourceName);
            while (resources.hasMoreElements()) {
                URL urlNextElement = resources.nextElement();
                Properties properties = new Properties();
                InputStream inputStreamOpenStream = null;
                try {
                    try {
                        inputStreamOpenStream = urlNextElement.openStream();
                        properties.load(urlNextElement.openStream());
                    } catch (IOException e2) {
                        LOG.log(Level.SEVERE, "could not load mimetypes from " + urlNextElement, (Throwable) e2);
                    }
                    safeClose(inputStreamOpenStream);
                    result.putAll(properties);
                } catch (Throwable th) {
                    safeClose(inputStreamOpenStream);
                    throw th;
                }
            }
        } catch (IOException unused) {
            LOG.log(Level.INFO, "no mime types available at " + resourceName);
        }
    }

    public static SSLServerSocketFactory makeSSLSocketFactory(KeyStore loadedKeyStore, KeyManager[] keyManagers) throws NoSuchAlgorithmException, IOException, KeyStoreException, KeyManagementException {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(loadedKeyStore);
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(keyManagers, trustManagerFactory.getTrustManagers(), null);
            return sSLContext.getServerSocketFactory();
        } catch (Exception e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public static Map<String, String> mimeTypes() throws IOException {
        if (MIME_TYPES == null) {
            HashMap map = new HashMap();
            MIME_TYPES = map;
            loadMimeTypes(map, "META-INF/nanohttpd/default-mimetypes.properties");
            loadMimeTypes(MIME_TYPES, "META-INF/nanohttpd/mimetypes.properties");
            if (MIME_TYPES.isEmpty()) {
                LOG.log(Level.WARNING, "no mime types found in the classpath! please provide mimetypes.properties");
            }
        }
        return MIME_TYPES;
    }

    public static Response newChunkedResponse(Response.IStatus status, String mimeType, InputStream data) {
        return new Response(status, mimeType, data, -1L);
    }

    public static Response newFixedLengthResponse(Response.IStatus status, String mimeType, InputStream data, long totalBytes) {
        return new Response(status, mimeType, data, totalBytes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void safeClose(Object closeable) throws IOException {
        if (closeable != null) {
            try {
                if (closeable instanceof Closeable) {
                    ((Closeable) closeable).close();
                } else if (closeable instanceof Socket) {
                    ((Socket) closeable).close();
                } else {
                    if (!(closeable instanceof ServerSocket)) {
                        throw new IllegalArgumentException("Unknown object to close");
                    }
                    ((ServerSocket) closeable).close();
                }
            } catch (IOException e2) {
                LOG.log(Level.SEVERE, "Could not close", (Throwable) e2);
            }
        }
    }

    public synchronized void closeAllConnections() {
        stop();
    }

    public ClientHandler createClientHandler(final Socket finalAccept, final InputStream inputStream) {
        return new ClientHandler(inputStream, finalAccept);
    }

    public ServerRunnable createServerRunnable(final int timeout) {
        return new ServerRunnable(timeout);
    }

    public String getHostname() {
        return this.hostname;
    }

    public final int getListeningPort() {
        if (this.myServerSocket == null) {
            return -1;
        }
        return this.myServerSocket.getLocalPort();
    }

    public ServerSocketFactory getServerSocketFactory() {
        return this.serverSocketFactory;
    }

    public TempFileManagerFactory getTempFileManagerFactory() {
        return this.tempFileManagerFactory;
    }

    public final boolean isAlive() {
        return wasStarted() && !this.myServerSocket.isClosed() && this.myThread.isAlive();
    }

    public void makeSecure(SSLServerSocketFactory sslServerSocketFactory, String[] sslProtocols) {
        this.serverSocketFactory = new SecureServerSocketFactory(sslServerSocketFactory, sslProtocols);
    }

    public Response serve(IHTTPSession session) {
        HashMap map = new HashMap();
        Method method = session.getMethod();
        if (Method.PUT.equals(method) || Method.POST.equals(method)) {
            try {
                session.parseBody(map);
            } catch (ResponseException e2) {
                return newFixedLengthResponse(e2.getStatus(), "text/plain", e2.getMessage());
            } catch (IOException e3) {
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "SERVER INTERNAL ERROR: IOException: " + e3.getMessage());
            }
        }
        Map<String, String> parms = session.getParms();
        parms.put(QUERY_STRING_PARAMETER, session.getQueryParameterString());
        return serve(session.getUri(), method, session.getHeaders(), parms, map);
    }

    public void setAsyncRunner(AsyncRunner asyncRunner) {
        this.asyncRunner = asyncRunner;
    }

    public void setServerSocketFactory(ServerSocketFactory serverSocketFactory) {
        this.serverSocketFactory = serverSocketFactory;
    }

    public void setTempFileManagerFactory(TempFileManagerFactory tempFileManagerFactory) {
        this.tempFileManagerFactory = tempFileManagerFactory;
    }

    public void start() throws IOException {
        start(5000);
    }

    public void stop() throws InterruptedException {
        try {
            safeClose(this.myServerSocket);
            this.asyncRunner.closeAll();
            Thread thread = this.myThread;
            if (thread != null) {
                thread.join();
            }
        } catch (Exception e2) {
            LOG.log(Level.SEVERE, "Could not stop all connections", (Throwable) e2);
        }
    }

    public boolean useGzipWhenAccepted(Response r2) {
        return r2.getMimeType() != null && r2.getMimeType().toLowerCase().contains("text/");
    }

    public final boolean wasStarted() {
        return (this.myServerSocket == null || this.myThread == null) ? false : true;
    }

    public static final class ResponseException extends Exception {
        private static final long serialVersionUID = 6569838532917408380L;
        private final Response.Status status;

        public ResponseException(Response.Status status, String message) {
            super(message);
            this.status = status;
        }

        public Response.Status getStatus() {
            return this.status;
        }

        public ResponseException(Response.Status status, String message, Exception e2) {
            super(message, e2);
            this.status = status;
        }
    }

    public NanoHTTPD(String hostname, int port) {
        this.serverSocketFactory = new DefaultServerSocketFactory();
        this.hostname = hostname;
        this.myPort = port;
        setTempFileManagerFactory(new DefaultTempFileManagerFactory());
        setAsyncRunner(new DefaultAsyncRunner());
    }

    public static Map<String, List<String>> decodeParameters(String queryString) {
        HashMap map = new HashMap();
        if (queryString != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(queryString, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String strNextToken = stringTokenizer.nextToken();
                int iIndexOf = strNextToken.indexOf(61);
                String strTrim = (iIndexOf >= 0 ? decodePercent(strNextToken.substring(0, iIndexOf)) : decodePercent(strNextToken)).trim();
                if (!map.containsKey(strTrim)) {
                    map.put(strTrim, new ArrayList());
                }
                String strDecodePercent = iIndexOf >= 0 ? decodePercent(strNextToken.substring(iIndexOf + 1)) : null;
                if (strDecodePercent != null) {
                    ((List) map.get(strTrim)).add(strDecodePercent);
                }
            }
        }
        return map;
    }

    public static Response newFixedLengthResponse(Response.IStatus status, String mimeType, String txt) throws UnsupportedEncodingException {
        byte[] bytes;
        ContentType contentType = new ContentType(mimeType);
        if (txt == null) {
            return newFixedLengthResponse(status, mimeType, new ByteArrayInputStream(new byte[0]), 0L);
        }
        try {
            if (!Charset.forName(contentType.getEncoding()).newEncoder().canEncode(txt)) {
                contentType = contentType.tryUTF8();
            }
            bytes = txt.getBytes(contentType.getEncoding());
        } catch (UnsupportedEncodingException e2) {
            LOG.log(Level.SEVERE, "encoding problem, responding nothing", (Throwable) e2);
            bytes = new byte[0];
        }
        return newFixedLengthResponse(status, contentType.getContentTypeHeader(), new ByteArrayInputStream(bytes), bytes.length);
    }

    public void start(final int timeout) throws IOException {
        start(timeout, true);
    }

    public void start(final int timeout, boolean daemon) throws IOException {
        this.myServerSocket = getServerSocketFactory().create();
        this.myServerSocket.setReuseAddress(true);
        ServerRunnable serverRunnableCreateServerRunnable = createServerRunnable(timeout);
        Thread thread = new Thread(serverRunnableCreateServerRunnable);
        this.myThread = thread;
        thread.setDaemon(daemon);
        this.myThread.setName("NanoHttpd Main Listener");
        this.myThread.start();
        while (!serverRunnableCreateServerRunnable.hasBinded && serverRunnableCreateServerRunnable.bindException == null) {
            try {
                Thread.sleep(10L);
            } catch (Throwable unused) {
            }
        }
        if (serverRunnableCreateServerRunnable.bindException != null) {
            throw serverRunnableCreateServerRunnable.bindException;
        }
    }

    public class HTTPSession implements IHTTPSession {
        public static final int BUFSIZE = 8192;
        public static final int MAX_HEADER_SIZE = 1024;
        private static final int MEMORY_STORE_LIMIT = 1024;
        private static final int REQUEST_BUFFER_LEN = 512;
        private CookieHandler cookies;
        private Map<String, String> headers;
        private final BufferedInputStream inputStream;
        private Method method;
        private final OutputStream outputStream;
        private Map<String, String> parms;
        private String protocolVersion;
        private String queryParameterString;
        private String remoteHostname;
        private String remoteIp;
        private int rlen;
        private int splitbyte;
        private final TempFileManager tempFileManager;
        private String uri;

        public HTTPSession(TempFileManager tempFileManager, InputStream inputStream, OutputStream outputStream) {
            this.tempFileManager = tempFileManager;
            this.inputStream = new BufferedInputStream(inputStream, 8192);
            this.outputStream = outputStream;
        }

        private void decodeHeader(BufferedReader in, Map<String, String> pre, Map<String, String> parms, Map<String, String> headers) throws ResponseException, IOException {
            String strDecodePercent;
            try {
                String line = in.readLine();
                if (line == null) {
                    return;
                }
                StringTokenizer stringTokenizer = new StringTokenizer(line);
                if (!stringTokenizer.hasMoreTokens()) {
                    throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
                }
                pre.put("method", stringTokenizer.nextToken());
                if (!stringTokenizer.hasMoreTokens()) {
                    throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
                }
                String strNextToken = stringTokenizer.nextToken();
                int iIndexOf = strNextToken.indexOf(63);
                if (iIndexOf >= 0) {
                    decodeParms(strNextToken.substring(iIndexOf + 1), parms);
                    strDecodePercent = NanoHTTPD.decodePercent(strNextToken.substring(0, iIndexOf));
                } else {
                    strDecodePercent = NanoHTTPD.decodePercent(strNextToken);
                }
                if (stringTokenizer.hasMoreTokens()) {
                    this.protocolVersion = stringTokenizer.nextToken();
                } else {
                    this.protocolVersion = HttpVersions.HTTP_1_1;
                    NanoHTTPD.LOG.log(Level.FINE, "no protocol version specified, strange. Assuming HTTP/1.1.");
                }
                String line2 = in.readLine();
                while (line2 != null && !line2.trim().isEmpty()) {
                    int iIndexOf2 = line2.indexOf(58);
                    if (iIndexOf2 >= 0) {
                        headers.put(line2.substring(0, iIndexOf2).trim().toLowerCase(Locale.US), line2.substring(iIndexOf2 + 1).trim());
                    }
                    line2 = in.readLine();
                }
                pre.put("uri", strDecodePercent);
            } catch (IOException e2) {
                throw new ResponseException(Response.Status.INTERNAL_ERROR, "SERVER INTERNAL ERROR: IOException: " + e2.getMessage(), e2);
            }
        }

        private void decodeMultipartFormData(ContentType contentType, ByteBuffer fbuf, Map<String, String> parms, Map<String, String> files) throws Throwable {
            int i2;
            try {
                int[] boundaryPositions = getBoundaryPositions(fbuf, contentType.getBoundary().getBytes());
                int i3 = 2;
                if (boundaryPositions.length < 2) {
                    throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but contains less than two boundary strings.");
                }
                int i4 = 1024;
                byte[] bArr = new byte[1024];
                int i5 = 0;
                int i6 = 0;
                int i7 = 0;
                while (i6 < boundaryPositions.length - 1) {
                    fbuf.position(boundaryPositions[i6]);
                    int iRemaining = fbuf.remaining() < i4 ? fbuf.remaining() : i4;
                    fbuf.get(bArr, i5, iRemaining);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bArr, i5, iRemaining), Charset.forName(contentType.getEncoding())), iRemaining);
                    String line = bufferedReader.readLine();
                    if (line == null || !line.contains(contentType.getBoundary())) {
                        throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but chunk does not start with boundary.");
                    }
                    String line2 = bufferedReader.readLine();
                    String strTrim = null;
                    int i8 = i3;
                    String strGroup = null;
                    String strGroup2 = null;
                    while (line2 != null && line2.trim().length() > 0) {
                        Matcher matcher = NanoHTTPD.CONTENT_DISPOSITION_PATTERN.matcher(line2);
                        if (matcher.matches()) {
                            Matcher matcher2 = NanoHTTPD.CONTENT_DISPOSITION_ATTRIBUTE_PATTERN.matcher(matcher.group(i3));
                            while (matcher2.find()) {
                                String str = strGroup2;
                                String strGroup3 = matcher2.group(1);
                                if ("name".equalsIgnoreCase(strGroup3)) {
                                    strGroup = matcher2.group(2);
                                } else if (TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME.equalsIgnoreCase(strGroup3)) {
                                    strGroup2 = matcher2.group(2);
                                    if (!strGroup2.isEmpty()) {
                                        if (i7 > 0) {
                                            i7++;
                                            strGroup = strGroup + String.valueOf(i7);
                                        } else {
                                            i7++;
                                        }
                                    }
                                }
                                strGroup2 = str;
                            }
                        }
                        Matcher matcher3 = NanoHTTPD.CONTENT_TYPE_PATTERN.matcher(line2);
                        if (matcher3.matches()) {
                            i2 = 2;
                            strTrim = matcher3.group(2).trim();
                        } else {
                            i2 = 2;
                        }
                        line2 = bufferedReader.readLine();
                        i8++;
                        i3 = i2;
                    }
                    int i9 = i3;
                    int iScipOverNewLine = 0;
                    while (true) {
                        int i10 = i8 - 1;
                        if (i8 <= 0) {
                            break;
                        }
                        iScipOverNewLine = scipOverNewLine(bArr, iScipOverNewLine);
                        i8 = i10;
                    }
                    if (iScipOverNewLine >= iRemaining - 4) {
                        throw new ResponseException(Response.Status.INTERNAL_ERROR, "Multipart header size exceeds MAX_HEADER_SIZE.");
                    }
                    int i11 = boundaryPositions[i6] + iScipOverNewLine;
                    i6++;
                    int i12 = boundaryPositions[i6] - 4;
                    fbuf.position(i11);
                    if (strTrim == null) {
                        byte[] bArr2 = new byte[i12 - i11];
                        fbuf.get(bArr2);
                        parms.put(strGroup, new String(bArr2, contentType.getEncoding()));
                    } else {
                        String strSaveTmpFile = saveTmpFile(fbuf, i11, i12 - i11, strGroup2);
                        if (files.containsKey(strGroup)) {
                            int i13 = i9;
                            while (true) {
                                if (!files.containsKey(strGroup + i13)) {
                                    break;
                                } else {
                                    i13++;
                                }
                            }
                            files.put(strGroup + i13, strSaveTmpFile);
                        } else {
                            files.put(strGroup, strSaveTmpFile);
                        }
                        parms.put(strGroup, strGroup2);
                    }
                    i3 = i9;
                    i4 = 1024;
                    i5 = 0;
                }
            } catch (ResponseException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new ResponseException(Response.Status.INTERNAL_ERROR, e3.toString());
            }
        }

        private void decodeParms(String parms, Map<String, String> p2) {
            if (parms == null) {
                this.queryParameterString = "";
                return;
            }
            this.queryParameterString = parms;
            StringTokenizer stringTokenizer = new StringTokenizer(parms, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String strNextToken = stringTokenizer.nextToken();
                int iIndexOf = strNextToken.indexOf(61);
                if (iIndexOf >= 0) {
                    p2.put(NanoHTTPD.decodePercent(strNextToken.substring(0, iIndexOf)).trim(), NanoHTTPD.decodePercent(strNextToken.substring(iIndexOf + 1)));
                } else {
                    p2.put(NanoHTTPD.decodePercent(strNextToken).trim(), "");
                }
            }
        }

        private int findHeaderEnd(final byte[] buf, int rlen) {
            int i2;
            int i3 = 0;
            while (true) {
                int i4 = i3 + 1;
                if (i4 >= rlen) {
                    return 0;
                }
                byte b3 = buf[i3];
                if (b3 == 13 && buf[i4] == 10 && (i2 = i3 + 3) < rlen && buf[i3 + 2] == 13 && buf[i2] == 10) {
                    return i3 + 4;
                }
                if (b3 == 10 && buf[i4] == 10) {
                    return i3 + 2;
                }
                i3 = i4;
            }
        }

        private int[] getBoundaryPositions(ByteBuffer b3, byte[] boundary) {
            int[] iArr = new int[0];
            if (b3.remaining() < boundary.length) {
                return iArr;
            }
            int length = boundary.length + 4096;
            byte[] bArr = new byte[length];
            int iRemaining = b3.remaining() < length ? b3.remaining() : length;
            b3.get(bArr, 0, iRemaining);
            int length2 = iRemaining - boundary.length;
            int i2 = 0;
            do {
                for (int i3 = 0; i3 < length2; i3++) {
                    for (int i4 = 0; i4 < boundary.length && bArr[i3 + i4] == boundary[i4]; i4++) {
                        if (i4 == boundary.length - 1) {
                            int[] iArr2 = new int[iArr.length + 1];
                            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                            iArr2[iArr.length] = i2 + i3;
                            iArr = iArr2;
                        }
                    }
                }
                i2 += length2;
                System.arraycopy(bArr, length - boundary.length, bArr, 0, boundary.length);
                length2 = length - boundary.length;
                if (b3.remaining() < length2) {
                    length2 = b3.remaining();
                }
                b3.get(bArr, boundary.length, length2);
            } while (length2 > 0);
            return iArr;
        }

        private RandomAccessFile getTmpBucket() {
            try {
                return new RandomAccessFile(this.tempFileManager.createTempFile(null).getName(), InternalZipConstants.WRITE_MODE);
            } catch (Exception e2) {
                throw new Error(e2);
            }
        }

        private String saveTmpFile(ByteBuffer b3, int offset, int len, String filename_hint) throws Throwable {
            if (len <= 0) {
                return "";
            }
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    TempFile tempFileCreateTempFile = this.tempFileManager.createTempFile(filename_hint);
                    ByteBuffer byteBufferDuplicate = b3.duplicate();
                    FileOutputStream fileOutputStream2 = new FileOutputStream(tempFileCreateTempFile.getName());
                    try {
                        FileChannel channel = fileOutputStream2.getChannel();
                        byteBufferDuplicate.position(offset).limit(offset + len);
                        channel.write(byteBufferDuplicate.slice());
                        String name = tempFileCreateTempFile.getName();
                        NanoHTTPD.safeClose(fileOutputStream2);
                        return name;
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        throw new Error(e);
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        NanoHTTPD.safeClose(fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        }

        private int scipOverNewLine(byte[] partHeaderBuff, int index) {
            while (partHeaderBuff[index] != 10) {
                index++;
            }
            return index + 1;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public void execute() throws IOException {
            byte[] bArr;
            boolean z2;
            int i2;
            Response responseServe = null;
            try {
                try {
                    try {
                        try {
                            try {
                                bArr = new byte[8192];
                                z2 = false;
                                this.splitbyte = 0;
                                this.rlen = 0;
                                this.inputStream.mark(8192);
                                try {
                                    i2 = this.inputStream.read(bArr, 0, 8192);
                                } catch (SSLException e2) {
                                    throw e2;
                                } catch (IOException unused) {
                                    NanoHTTPD.safeClose(this.inputStream);
                                    NanoHTTPD.safeClose(this.outputStream);
                                    throw new SocketException("NanoHttpd Shutdown");
                                }
                            } catch (SocketException e3) {
                                throw e3;
                            } catch (IOException e4) {
                                NanoHTTPD.newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "SERVER INTERNAL ERROR: IOException: " + e4.getMessage()).send(this.outputStream);
                                NanoHTTPD.safeClose(this.outputStream);
                            }
                        } catch (SSLException e5) {
                            NanoHTTPD.newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "SSL PROTOCOL FAILURE: " + e5.getMessage()).send(this.outputStream);
                            NanoHTTPD.safeClose(this.outputStream);
                        }
                    } catch (ResponseException e6) {
                        NanoHTTPD.newFixedLengthResponse(e6.getStatus(), "text/plain", e6.getMessage()).send(this.outputStream);
                        NanoHTTPD.safeClose(this.outputStream);
                    }
                    if (i2 == -1) {
                        NanoHTTPD.safeClose(this.inputStream);
                        NanoHTTPD.safeClose(this.outputStream);
                        throw new SocketException("NanoHttpd Shutdown");
                    }
                    while (i2 > 0) {
                        int i3 = this.rlen + i2;
                        this.rlen = i3;
                        int iFindHeaderEnd = findHeaderEnd(bArr, i3);
                        this.splitbyte = iFindHeaderEnd;
                        if (iFindHeaderEnd > 0) {
                            break;
                        }
                        BufferedInputStream bufferedInputStream = this.inputStream;
                        int i4 = this.rlen;
                        i2 = bufferedInputStream.read(bArr, i4, 8192 - i4);
                    }
                    if (this.splitbyte < this.rlen) {
                        this.inputStream.reset();
                        this.inputStream.skip(this.splitbyte);
                    }
                    this.parms = new HashMap();
                    Map<String, String> map = this.headers;
                    if (map == null) {
                        this.headers = new HashMap();
                    } else {
                        map.clear();
                    }
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bArr, 0, this.rlen)));
                    HashMap map2 = new HashMap();
                    decodeHeader(bufferedReader, map2, this.parms, this.headers);
                    String str = this.remoteIp;
                    if (str != null) {
                        this.headers.put("remote-addr", str);
                        this.headers.put("http-client-ip", this.remoteIp);
                    }
                    Method methodLookup = Method.lookup(map2.get("method"));
                    this.method = methodLookup;
                    if (methodLookup == null) {
                        throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Syntax error. HTTP verb " + map2.get("method") + " unhandled.");
                    }
                    this.uri = map2.get("uri");
                    this.cookies = NanoHTTPD.this.new CookieHandler(this.headers);
                    String str2 = this.headers.get("connection");
                    boolean z3 = HttpVersions.HTTP_1_1.equals(this.protocolVersion) && (str2 == null || !str2.matches("(?i).*close.*"));
                    responseServe = NanoHTTPD.this.serve(this);
                    if (responseServe == null) {
                        throw new ResponseException(Response.Status.INTERNAL_ERROR, "SERVER INTERNAL ERROR: Serve() returned a null response.");
                    }
                    String str3 = this.headers.get("accept-encoding");
                    this.cookies.unloadQueue(responseServe);
                    responseServe.setRequestMethod(this.method);
                    if (NanoHTTPD.this.useGzipWhenAccepted(responseServe) && str3 != null && str3.contains(HttpHeaderValues.GZIP)) {
                        z2 = true;
                    }
                    responseServe.setGzipEncoding(z2);
                    responseServe.setKeepAlive(z3);
                    responseServe.send(this.outputStream);
                    if (!z3 || responseServe.isCloseConnection()) {
                        throw new SocketException("NanoHttpd Shutdown");
                    }
                } catch (SocketTimeoutException e7) {
                    throw e7;
                }
            } finally {
                NanoHTTPD.safeClose(null);
                this.tempFileManager.clear();
            }
        }

        public long getBodySize() {
            if (this.headers.containsKey("content-length")) {
                return Long.parseLong(this.headers.get("content-length"));
            }
            if (this.splitbyte < this.rlen) {
                return r1 - r0;
            }
            return 0L;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public CookieHandler getCookies() {
            return this.cookies;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public final Map<String, String> getHeaders() {
            return this.headers;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public final InputStream getInputStream() {
            return this.inputStream;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public final Method getMethod() {
            return this.method;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public final Map<String, String> getParms() {
            return this.parms;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public String getQueryParameterString() {
            return this.queryParameterString;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public String getRemoteHostName() {
            return this.remoteHostname;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public String getRemoteIpAddress() {
            return this.remoteIp;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public final String getUri() {
            return this.uri;
        }

        @Override // com.psychiatrygarden.videoChace.NanoHTTPD.IHTTPSession
        public void parseBody(Map<String, String> files) throws Throwable {
            RandomAccessFile tmpBucket;
            ByteArrayOutputStream byteArrayOutputStream;
            DataOutput dataOutputStream;
            ByteBuffer map;
            RandomAccessFile randomAccessFile = null;
            try {
                long bodySize = getBodySize();
                if (bodySize < 1024) {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    tmpBucket = null;
                } else {
                    tmpBucket = getTmpBucket();
                    byteArrayOutputStream = null;
                    dataOutputStream = tmpBucket;
                }
                try {
                    byte[] bArr = new byte[512];
                    while (this.rlen >= 0 && bodySize > 0) {
                        int i2 = this.inputStream.read(bArr, 0, (int) Math.min(bodySize, 512L));
                        this.rlen = i2;
                        bodySize -= i2;
                        if (i2 > 0) {
                            dataOutputStream.write(bArr, 0, i2);
                        }
                    }
                    if (byteArrayOutputStream != null) {
                        map = ByteBuffer.wrap(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
                    } else {
                        map = tmpBucket.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, tmpBucket.length());
                        tmpBucket.seek(0L);
                    }
                    if (Method.POST.equals(this.method)) {
                        ContentType contentType = new ContentType(this.headers.get(d.f3288d));
                        if (!contentType.isMultipart()) {
                            byte[] bArr2 = new byte[map.remaining()];
                            map.get(bArr2);
                            String strTrim = new String(bArr2, contentType.getEncoding()).trim();
                            if ("application/x-www-form-urlencoded".equalsIgnoreCase(contentType.getContentType())) {
                                decodeParms(strTrim, this.parms);
                            } else if (strTrim.length() != 0) {
                                files.put("postData", strTrim);
                            }
                        } else {
                            if (contentType.getBoundary() == null) {
                                throw new ResponseException(Response.Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html");
                            }
                            decodeMultipartFormData(contentType, map, this.parms, files);
                        }
                    } else if (Method.PUT.equals(this.method)) {
                        files.put("content", saveTmpFile(map, 0, map.limit(), null));
                    }
                    NanoHTTPD.safeClose(tmpBucket);
                } catch (Throwable th) {
                    th = th;
                    randomAccessFile = tmpBucket;
                    NanoHTTPD.safeClose(randomAccessFile);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        public HTTPSession(TempFileManager tempFileManager, InputStream inputStream, OutputStream outputStream, InetAddress inetAddress) {
            this.tempFileManager = tempFileManager;
            this.inputStream = new BufferedInputStream(inputStream, 8192);
            this.outputStream = outputStream;
            this.remoteIp = (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) ? "127.0.0.1" : inetAddress.getHostAddress().toString();
            this.remoteHostname = (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) ? "localhost" : inetAddress.getHostName().toString();
            this.headers = new HashMap();
        }
    }

    public static SSLServerSocketFactory makeSSLSocketFactory(KeyStore loadedKeyStore, KeyManagerFactory loadedKeyFactory) throws IOException {
        try {
            return makeSSLSocketFactory(loadedKeyStore, loadedKeyFactory.getKeyManagers());
        } catch (Exception e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public static SSLServerSocketFactory makeSSLSocketFactory(String keyAndTrustStoreClasspathPath, char[] passphrase) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream resourceAsStream = NanoHTTPD.class.getResourceAsStream(keyAndTrustStoreClasspathPath);
            if (resourceAsStream != null) {
                keyStore.load(resourceAsStream, passphrase);
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, passphrase);
                return makeSSLSocketFactory(keyStore, keyManagerFactory);
            }
            throw new IOException("Unable to load keystore from classpath: " + keyAndTrustStoreClasspathPath);
        } catch (Exception e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public static Response newFixedLengthResponse(String msg) {
        return newFixedLengthResponse(Response.Status.OK, "text/html", msg);
    }

    @Deprecated
    public Response serve(String uri, Method method, Map<String, String> headers, Map<String, String> parms, Map<String, String> files) {
        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not Found");
    }
}
