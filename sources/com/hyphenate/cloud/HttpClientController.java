package com.hyphenate.cloud;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EMFileHelper;
import com.hyphenate.util.EMLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes4.dex */
class HttpClientController {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8607a = "HttpClientController";

    /* renamed from: f, reason: collision with root package name */
    private static final String[] f8608f = {"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"};

    /* renamed from: g, reason: collision with root package name */
    private static int f8609g = 60000;

    /* renamed from: h, reason: collision with root package name */
    private static int f8610h = 60000;

    /* renamed from: i, reason: collision with root package name */
    private static final String f8611i = UUID.randomUUID().toString();

    /* renamed from: j, reason: collision with root package name */
    private static final String f8612j = "--";

    /* renamed from: k, reason: collision with root package name */
    private static final String f8613k = "\r\n";

    /* renamed from: l, reason: collision with root package name */
    private static final String f8614l = "Android";

    /* renamed from: b, reason: collision with root package name */
    private final Context f8615b;

    /* renamed from: c, reason: collision with root package name */
    private URL f8616c;

    /* renamed from: d, reason: collision with root package name */
    private HttpURLConnection f8617d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f8618e;

    public static class HttpParams {
        public boolean canRetry;
        public boolean isCheckSSL;
        public boolean isDefaultRetry;
        public boolean isDownloadFile;
        public boolean isNotUseToken;
        public boolean isTokenExceeded;
        public boolean isUploadFile;
        public int mConnectTimeout;
        public final Context mContext;
        public String mDownloadPath;
        public String mFileKey;
        public String mFilename;
        public String mLocalFileUri;
        public String mParamsString;
        public int mReadTimeout;
        public String mRequestMethod;
        public int mRetryTimes;
        public String mUrl;
        public int mPort = -1;
        public int fpaPort = 0;
        public boolean followRedirect = true;
        public Map<String, String> mHeaders = new HashMap();
        public Map<String, String> mParams = new HashMap();

        public HttpParams(Context context) {
            this.mContext = context;
        }

        public void addFile(HttpClientController httpClientController, OutputStream outputStream, HttpCallback httpCallback) throws IOException {
            if (this.isUploadFile) {
                httpClientController.a(this.mFileKey, EMFileHelper.getInstance().formatInUri(this.mLocalFileUri), this.mFilename, outputStream, httpCallback);
            }
        }

        public void apply(HttpClientController httpClientController) throws IOException {
            int i2 = this.fpaPort;
            if (i2 > 0) {
                httpClientController.a(this.mUrl, this.mPort, i2);
            } else {
                int i3 = this.mPort;
                if (i3 != -1) {
                    httpClientController.a(this.mUrl, i3);
                } else {
                    httpClientController.a(this.mUrl);
                }
            }
            httpClientController.b(this.mRequestMethod);
            if ("GET".equalsIgnoreCase(this.mRequestMethod)) {
                httpClientController.e();
            } else if ("DELETE".equalsIgnoreCase(this.mRequestMethod)) {
                httpClientController.g();
            } else {
                httpClientController.f();
            }
            httpClientController.a(this.mConnectTimeout);
            httpClientController.b(this.mReadTimeout);
            httpClientController.b();
            if (!this.isNotUseToken) {
                httpClientController.a();
            }
            httpClientController.a(this.followRedirect);
            if (this.isUploadFile) {
                httpClientController.c();
                httpClientController.d();
            }
            if (this.isDownloadFile) {
                if (TextUtils.isEmpty(this.mDownloadPath)) {
                    throw new FileNotFoundException("file download path is empty");
                }
                checkDownloadProperty();
            }
            httpClientController.c(this.mUrl);
            checkToken();
            httpClientController.a(this.mHeaders);
        }

        public void checkDownloadProperty() {
            if (this.isDownloadFile) {
                this.mHeaders.put("Authorization", "Bearer " + EMClient.getInstance().getOptions().getAccessToken());
                this.mHeaders.put("Accept", "application/octet-stream");
            }
        }

        public void checkToken() {
            if (this.mHeaders.keySet().contains("Authorization") && TextUtils.isEmpty(this.mHeaders.get("Authorization"))) {
                this.mHeaders.put("Authorization", "Bearer " + EMClient.getInstance().getOptions().getAccessToken());
            }
        }

        public HttpResponse getExceptionResponse(HttpClientController httpClientController, IOException iOException) throws IOException {
            if (httpClientController != null) {
                return httpClientController.a(iOException);
            }
            return null;
        }

        public String getRedirectionUrl(HttpClientController httpClientController) {
            return httpClientController.l();
        }

        public HttpResponse getResponse(HttpClientController httpClientController) throws IOException {
            return this.isDownloadFile ? httpClientController.k() : httpClientController.j();
        }
    }

    public HttpClientController(Context context) {
        this.f8615b = context;
    }

    private String a(InputStream inputStream) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return sb.toString();
                }
                sb.append(line + "\n");
            }
        } catch (Exception unused) {
            return null;
        }
    }

    private String a(String str, String str2, String str3, long j2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\r\n");
        stringBuffer.append(f8612j);
        stringBuffer.append(f8611i);
        stringBuffer.append("\r\n");
        stringBuffer.append("Content-Disposition: form-data; name=\"" + str + "\"; filename=\"" + str2 + "\"");
        stringBuffer.append("\r\n");
        StringBuilder sb = new StringBuilder();
        sb.append("Content-Type: ");
        sb.append(str3);
        stringBuffer.append(sb.toString());
        stringBuffer.append("\r\n");
        stringBuffer.append("Content-Length: " + j2);
        stringBuffer.append("\r\n");
        stringBuffer.append("\r\n");
        return stringBuffer.toString();
    }

    private void a(boolean z2, HttpResponse httpResponse) {
        if (this.f8617d == null || httpResponse == null || z2) {
            return;
        }
        String str = f8607a;
        EMLog.d(str, "response code: " + httpResponse.code);
        if (httpResponse.code != 200) {
            EMLog.d(str, "error message: " + httpResponse.content);
        }
    }

    private String b(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : map.keySet()) {
            stringBuffer.append(f8612j);
            stringBuffer.append(f8611i);
            stringBuffer.append("\r\n");
            stringBuffer.append("Content-Disposition: form-data; name=\"" + str + "\"");
            stringBuffer.append("\r\n");
            stringBuffer.append("Content-Type: text/plain");
            stringBuffer.append("\r\n");
            stringBuffer.append("Content-Length: " + map.get(str).length());
            stringBuffer.append("\r\n");
            stringBuffer.append("\r\n");
            stringBuffer.append(map.get(str));
            stringBuffer.append("\r\n");
        }
        return stringBuffer.toString();
    }

    private void b(boolean z2) throws IllegalStateException {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str) {
        HttpClientConfig.checkAndProcessSSL(str, this.f8617d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String l() {
        return this.f8617d.getHeaderField("Location");
    }

    private String m() {
        return "\r\n--" + f8611i + f8612j + "\r\n";
    }

    public HttpResponse a(Exception exc) throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        HttpURLConnection httpURLConnection = this.f8617d;
        if (httpURLConnection != null) {
            httpResponse.code = httpURLConnection.getResponseCode();
            httpResponse.contentLength = this.f8617d.getContentLength();
            httpResponse.errorStream = this.f8617d.getErrorStream();
            this.f8617d.disconnect();
        }
        httpResponse.exception = exc;
        return httpResponse;
    }

    public void a() {
        this.f8617d.setRequestProperty("Authorization", "Bearer " + EMClient.getInstance().getOptions().getAccessToken());
    }

    public void a(int i2) {
        if (i2 <= 0) {
            i2 = f8609g;
        }
        this.f8617d.setConnectTimeout(i2);
    }

    public void a(Uri uri, OutputStream outputStream, HttpCallback httpCallback) throws IOException {
        a("file", uri, null, outputStream, httpCallback);
    }

    public void a(String str) throws IOException {
        a(str, -1);
    }

    public void a(String str, int i2) throws IOException {
        a(str, i2, 0);
    }

    public void a(String str, int i2, int i3) throws IOException {
        URLConnection uRLConnectionOpenConnection;
        EMLog.d(f8607a, "setURL()-> fpaPort=" + i3);
        URL url = new URL(HttpClientConfig.processUrl(HttpClientConfig.getFileRemoteUrl(str)));
        String protocol = url.getProtocol();
        int port = url.getPort();
        if (port != -1) {
            i2 = port;
        }
        if (i3 > 0) {
            URL url2 = new URL(protocol, url.getHost(), url.getFile());
            this.f8616c = url2;
            uRLConnectionOpenConnection = url2.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", i3)));
        } else {
            URL url3 = new URL(protocol, url.getHost(), i2, url.getFile());
            this.f8616c = url3;
            uRLConnectionOpenConnection = url3.openConnection();
        }
        this.f8617d = (HttpURLConnection) uRLConnectionOpenConnection;
    }

    public void a(String str, Uri uri, String str2, OutputStream outputStream, HttpCallback httpCallback) throws IOException {
        if (TextUtils.isEmpty(str)) {
            str = "file";
        }
        String str3 = str;
        if (!EMFileHelper.getInstance().isFileExist(uri)) {
            throw new FileNotFoundException("file not exist");
        }
        String filename = EMFileHelper.getInstance().getFilename(uri);
        String fileMimeType = EMFileHelper.getInstance().getFileMimeType(uri);
        long fileLength = EMFileHelper.getInstance().getFileLength(uri);
        String str4 = !TextUtils.isEmpty(str2) ? str2 : filename;
        String filePath = EMFileHelper.getInstance().getFilePath(uri);
        InputStream inputStreamOpenInputStream = null;
        try {
            inputStreamOpenInputStream = (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) ? this.f8615b.getContentResolver().openInputStream(uri) : new FileInputStream(new File(filePath));
            long jAvailable = inputStreamOpenInputStream.available();
            if (jAvailable <= fileLength) {
                jAvailable = fileLength;
            }
            outputStream.write(a(str3, str4, fileMimeType, jAvailable).getBytes());
            byte[] bArr = new byte[2048];
            long j2 = 0;
            while (true) {
                int i2 = inputStreamOpenInputStream.read(bArr);
                if (i2 == -1) {
                    inputStreamOpenInputStream.close();
                    outputStream.write(m().getBytes());
                    outputStream.flush();
                    return;
                } else {
                    outputStream.write(bArr, 0, i2);
                    j2 += i2;
                    if (j2 > jAvailable) {
                        j2 = jAvailable;
                    }
                    if (httpCallback != null) {
                        httpCallback.onProgress(jAvailable, j2);
                    }
                }
            }
        } catch (Throwable th) {
            if (inputStreamOpenInputStream != null) {
                inputStreamOpenInputStream.close();
            }
            throw th;
        }
    }

    public void a(String str, OutputStream outputStream) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        outputStream.write(str.getBytes());
        outputStream.flush();
    }

    public void a(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.f8617d.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    public void a(Map<String, String> map, OutputStream outputStream) throws IOException {
        if (map == null || map.size() <= 0) {
            return;
        }
        String strB = b(map);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        outputStream.write(strB.getBytes());
        outputStream.flush();
    }

    public void a(boolean z2) {
        this.f8617d.setInstanceFollowRedirects(z2);
    }

    public void b() {
        this.f8617d.setRequestProperty("User-agent", HttpClientConfig.getDefaultUserAgent());
        this.f8617d.setRequestProperty("Connection", "Keep-Alive");
    }

    public void b(int i2) {
        if (i2 <= 0) {
            i2 = f8610h;
        }
        this.f8617d.setReadTimeout(i2);
    }

    public void b(String str) throws ProtocolException {
        this.f8617d.setRequestMethod(str);
    }

    public void c() {
        this.f8617d.setRequestProperty("Charset", "UTF-8");
        this.f8617d.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + f8611i);
    }

    public void d() {
        this.f8617d.setChunkedStreamingMode(0);
    }

    public void e() {
        this.f8617d.setDoInput(true);
    }

    public void f() {
        this.f8617d.setDoOutput(true);
        this.f8617d.setDoInput(true);
        this.f8617d.setUseCaches(false);
    }

    public void g() {
        this.f8617d.setDoOutput(true);
        this.f8617d.setDoInput(true);
        this.f8617d.setUseCaches(false);
    }

    public HttpURLConnection h() {
        return this.f8617d;
    }

    public HttpURLConnection i() throws IOException {
        b(false);
        this.f8617d.connect();
        return this.f8617d;
    }

    public HttpResponse j() throws IOException {
        InputStream errorStream;
        HttpResponse httpResponse = new HttpResponse();
        int responseCode = this.f8617d.getResponseCode();
        httpResponse.code = responseCode;
        if (responseCode == 200) {
            httpResponse.contentLength = this.f8617d.getContentLength();
            errorStream = this.f8617d.getInputStream();
            httpResponse.inputStream = errorStream;
        } else {
            errorStream = this.f8617d.getErrorStream();
            httpResponse.errorStream = errorStream;
        }
        httpResponse.content = a(errorStream);
        a(false, httpResponse);
        return httpResponse;
    }

    public HttpResponse k() throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.code = this.f8617d.getResponseCode();
        httpResponse.contentLength = this.f8617d.getContentLength();
        httpResponse.inputStream = this.f8617d.getInputStream();
        InputStream errorStream = this.f8617d.getErrorStream();
        httpResponse.errorStream = errorStream;
        if (httpResponse.code != 200) {
            httpResponse.content = a(errorStream);
        }
        a(false, httpResponse);
        return httpResponse;
    }
}
