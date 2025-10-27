package com.hyphenate.cloud;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.adapter.EMARHttpCallback;
import com.hyphenate.cloud.HttpClientController;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMFileHelper;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EMPrivateConstant;
import com.hyphenate.util.NetUtils;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class HttpClientManager {
    private static int HIGH_SPEED_DOWNLOAD_BUF_SIZE = 30720;
    public static String Method_DELETE = "DELETE";
    public static String Method_GET = "GET";
    public static String Method_POST = "POST";
    public static String Method_PUT = "PUT";
    private static final int REQUEST_FAILED_CODE = 408;
    private static final String TAG = "HttpClientManager";
    private static volatile boolean isRetring = false;
    public static final int max_retries_times_on_connection_refused = 3;
    private static final int max_retry_times_on_connection_refused = 20;
    private static final int retriveInterval = 120000;
    private static volatile long retrivedTokenTime;

    public static class Builder {

        /* renamed from: p, reason: collision with root package name */
        private final HttpClientController.HttpParams f8619p;

        public Builder() {
            this(EMClient.getInstance().getContext());
        }

        public Builder(Context context) {
            this.f8619p = new HttpClientController.HttpParams(context);
        }

        private void asyncExecuteFile(final HttpCallback httpCallback) {
            new Thread() { // from class: com.hyphenate.cloud.HttpClientManager.Builder.3
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    Builder.this.executeFile(httpCallback);
                }
            }.start();
        }

        private void asyncExecuteNormal(final HttpCallback httpCallback) {
            new Thread() { // from class: com.hyphenate.cloud.HttpClientManager.Builder.2
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    Builder.this.executeNormal(httpCallback);
                }
            }.start();
        }

        private HttpResponse download(HttpResponse httpResponse, HttpCallback httpCallback) throws IllegalStateException, IOException {
            String filePath = EMFileHelper.getInstance().getFilePath(this.f8619p.mDownloadPath);
            if (!TextUtils.isEmpty(filePath)) {
                File file = new File(filePath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            if ((this.f8619p.mDownloadPath.startsWith("content") ? HttpClientManager.onDownloadCompleted(httpResponse, EMFileHelper.getInstance().formatInUri(this.f8619p.mLocalFileUri), httpCallback) : HttpClientManager.onDownloadCompleted(httpResponse, filePath, httpCallback)) > 0) {
                if (httpCallback != null) {
                    EMLog.e(HttpClientManager.TAG, "download successfully");
                    httpCallback.onSuccess("download successfully");
                }
                return httpResponse;
            }
            if (httpCallback != null) {
                httpCallback.onError(408, "downloaded content size is zero!");
            }
            httpResponse.code = 408;
            httpResponse.content = "downloaded content size is zero!";
            return httpResponse;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public HttpResponse executeFile(HttpCallback httpCallback) {
            HttpResponse httpResponse;
            try {
                httpResponse = executePrivate(httpCallback);
            } catch (IOException e2) {
                e = e2;
                httpResponse = null;
            }
            try {
                int i2 = httpResponse.code;
                if (i2 != 401) {
                    return httpResponse;
                }
                if (System.currentTimeMillis() - EMHttpClient.getInstance().chatConfig().n() <= 600000) {
                    if (httpCallback != null) {
                        httpCallback.onError(i2, "unauthorized file");
                    }
                    httpResponse.content = "unauthorized file";
                }
                HttpClientController.HttpParams httpParams = this.f8619p;
                if (!httpParams.canRetry || httpParams.mRetryTimes <= 0) {
                    return httpResponse;
                }
                String strL = EMHttpClient.getInstance().chatConfig().l();
                HttpClientController.HttpParams httpParams2 = this.f8619p;
                httpParams2.mUrl = HttpClientManager.getNewHost(httpParams2.mUrl, strL);
                this.f8619p.mRetryTimes--;
                return executeFile(httpCallback);
            } catch (IOException e3) {
                e = e3;
                String message = e.getMessage() != null ? e.getMessage() : this.f8619p.isUploadFile ? "failed to upload the file" : "failed to download file";
                EMLog.e(HttpClientManager.TAG, "error execute:" + message);
                if (message.toLowerCase().contains(EMPrivateConstant.CONNECTION_REFUSED) && NetUtils.hasNetwork(this.f8619p.mContext)) {
                    HttpClientController.HttpParams httpParams3 = this.f8619p;
                    if (!httpParams3.isDefaultRetry) {
                        httpParams3.isDefaultRetry = true;
                        httpParams3.mRetryTimes = 20;
                        httpParams3.canRetry = true;
                        String strL2 = EMHttpClient.getInstance().chatConfig().l();
                        HttpClientController.HttpParams httpParams4 = this.f8619p;
                        httpParams4.mUrl = HttpClientManager.getNewHost(httpParams4.mUrl, strL2);
                    } else if (httpParams3.canRetry && httpParams3.mRetryTimes > 0) {
                        String strL3 = EMHttpClient.getInstance().chatConfig().l();
                        HttpClientController.HttpParams httpParams5 = this.f8619p;
                        httpParams5.mUrl = HttpClientManager.getNewHost(httpParams5.mUrl, strL3);
                        this.f8619p.mRetryTimes--;
                    }
                    return executeFile(httpCallback);
                }
                if (httpResponse == null) {
                    httpResponse = new HttpResponse();
                }
                if (httpResponse.code == 0) {
                    httpResponse.code = 408;
                }
                httpResponse.content = message;
                if (httpCallback != null) {
                    httpCallback.onError(httpResponse.code, message);
                }
                return httpResponse;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public HttpResponse executeNormal(HttpCallback httpCallback) {
            HttpResponse httpResponse;
            int i2;
            int i3;
            try {
                httpResponse = executePrivate(httpCallback);
                try {
                    if (httpResponse.code != 200) {
                        HttpClientController.HttpParams httpParams = this.f8619p;
                        if (httpParams.canRetry && (i3 = httpParams.mRetryTimes) > 0) {
                            httpParams.mRetryTimes = i3 - 1;
                            return executeNormal(httpCallback);
                        }
                    }
                    return httpResponse;
                } catch (IOException e2) {
                    e = e2;
                    String message = e.getMessage() != null ? e.getMessage() : "failed to request";
                    EMLog.e(HttpClientManager.TAG, "error execute:" + message);
                    HttpClientController.HttpParams httpParams2 = this.f8619p;
                    if (httpParams2.canRetry && (i2 = httpParams2.mRetryTimes) > 0) {
                        httpParams2.mRetryTimes = i2 - 1;
                        return executeNormal(httpCallback);
                    }
                    if (httpResponse == null) {
                        httpResponse = new HttpResponse();
                    }
                    if (httpResponse.code == 0) {
                        httpResponse.code = 408;
                    }
                    httpResponse.content = message;
                    if (httpCallback != null) {
                        httpCallback.onError(httpResponse.code, message);
                    }
                    return httpResponse;
                }
            } catch (IOException e3) {
                e = e3;
                httpResponse = null;
            }
        }

        private HttpResponse executePrivate(HttpCallback httpCallback) throws IOException {
            boolean z2;
            int i2;
            try {
                HttpClientController httpClientControllerBuild = build();
                HttpURLConnection httpURLConnectionI = httpClientControllerBuild.i();
                if (httpURLConnectionI.getDoOutput()) {
                    DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnectionI.getOutputStream());
                    httpClientControllerBuild.a(this.f8619p.mParamsString, dataOutputStream);
                    httpClientControllerBuild.a(this.f8619p.mParams, dataOutputStream);
                    try {
                        this.f8619p.addFile(httpClientControllerBuild, dataOutputStream, httpCallback);
                        z2 = false;
                    } catch (IOException e2) {
                        if (TextUtils.isEmpty(e2.getMessage()) || !e2.getMessage().contains("Connection reset")) {
                            throw e2;
                        }
                        z2 = true;
                    } finally {
                        dataOutputStream.close();
                    }
                } else {
                    z2 = false;
                }
                HttpResponse response = this.f8619p.getResponse(httpClientControllerBuild);
                if (z2 && (i2 = response.code) != 413) {
                    if (httpCallback != null) {
                        httpCallback.onError(i2, "Connection reset but not 413");
                    }
                    response.content = "Connection reset but not 413";
                }
                if (response.code == 401) {
                    EMClient.getInstance().notifyTokenExpired(response.content);
                }
                int i3 = response.code;
                if (i3 == 200) {
                    if (this.f8619p.isDownloadFile) {
                        return download(response, httpCallback);
                    }
                    if (httpCallback != null) {
                        httpCallback.onSuccess(response.content);
                    }
                } else if (httpCallback != null) {
                    httpCallback.onError(i3, response.content);
                }
                return response;
            } catch (IOException e3) {
                EMLog.e(HttpClientManager.TAG, "error message = " + e3.getMessage());
                throw e3;
            } catch (IllegalStateException e4) {
                EMLog.e(HttpClientManager.TAG, "error message = " + e4.getMessage());
                throw e4;
            }
        }

        public void asyncExecute(HttpCallback httpCallback) {
            HttpClientController.HttpParams httpParams = this.f8619p;
            if (httpParams.isUploadFile || httpParams.isDownloadFile) {
                asyncExecuteFile(httpCallback);
            } else {
                asyncExecuteNormal(httpCallback);
            }
        }

        public HttpClientController build() throws IOException {
            HttpClientController httpClientController = new HttpClientController(this.f8619p.mContext);
            this.f8619p.apply(httpClientController);
            return httpClientController;
        }

        public Builder checkAndProcessSSL(boolean z2) {
            this.f8619p.isCheckSSL = z2;
            return this;
        }

        public Builder delete() {
            this.f8619p.mRequestMethod = "DELETE";
            return this;
        }

        public Builder downloadFile(String str) {
            HttpClientController.HttpParams httpParams = this.f8619p;
            httpParams.mRequestMethod = "GET";
            httpParams.mDownloadPath = str;
            httpParams.isDownloadFile = true;
            return this;
        }

        public int execute(StringBuilder sb, final EMARHttpCallback eMARHttpCallback) {
            HttpResponse httpResponseExecutePrivate = null;
            try {
                httpResponseExecutePrivate = executePrivate(new HttpCallback() { // from class: com.hyphenate.cloud.HttpClientManager.Builder.1
                    @Override // com.hyphenate.cloud.HttpCallback
                    public void onError(int i2, String str) {
                    }

                    @Override // com.hyphenate.cloud.HttpCallback
                    public void onProgress(long j2, long j3) {
                        EMARHttpCallback eMARHttpCallback2 = eMARHttpCallback;
                        if (eMARHttpCallback2 != null) {
                            eMARHttpCallback2.onProgress(j2, j3);
                        }
                    }

                    @Override // com.hyphenate.cloud.HttpCallback
                    public void onSuccess(String str) {
                    }
                });
                int i2 = httpResponseExecutePrivate.code;
                if (sb != null) {
                    try {
                        sb.append(httpResponseExecutePrivate.content);
                    } catch (Exception unused) {
                        EMLog.e(HttpClientManager.TAG, "json parse exception remotefilepath:" + this.f8619p.mUrl);
                    }
                }
                return i2;
            } catch (IOException e2) {
                String message = e2.getMessage() != null ? e2.getMessage() : "failed to upload the files";
                EMLog.e(HttpClientManager.TAG, "error asyncExecute:" + message);
                if (message.toLowerCase().contains(EMPrivateConstant.CONNECTION_REFUSED) && NetUtils.hasNetwork(this.f8619p.mContext)) {
                    HttpClientController.HttpParams httpParams = this.f8619p;
                    if (httpParams.canRetry && httpParams.mRetryTimes > 0) {
                        String strL = EMHttpClient.getInstance().chatConfig().l();
                        HttpClientController.HttpParams httpParams2 = this.f8619p;
                        httpParams2.mUrl = HttpClientManager.getNewHost(httpParams2.mUrl, strL);
                        HttpClientController.HttpParams httpParams3 = this.f8619p;
                        httpParams3.mRetryTimes--;
                        EMLog.d(HttpClientManager.TAG, "重试中。。。");
                        return execute(sb, eMARHttpCallback);
                    }
                }
                if (sb != null) {
                    try {
                        sb.append(message);
                    } catch (Exception unused2) {
                    }
                }
                if (httpResponseExecutePrivate != null) {
                    return httpResponseExecutePrivate.code;
                }
                EMLog.e(HttpClientManager.TAG, e2.getMessage());
                return 408;
            }
        }

        public HttpResponse execute() {
            return execute(null);
        }

        public HttpResponse execute(HttpCallback httpCallback) {
            HttpClientController.HttpParams httpParams = this.f8619p;
            return (httpParams.isUploadFile || httpParams.isDownloadFile) ? executeFile(httpCallback) : executeNormal(httpCallback);
        }

        public Builder followRedirect(boolean z2) {
            this.f8619p.followRedirect = z2;
            return this;
        }

        public Builder get() {
            this.f8619p.mRequestMethod = "GET";
            return this;
        }

        public Builder isCanRetry(boolean z2) {
            this.f8619p.canRetry = z2;
            return this;
        }

        public Builder post() {
            this.f8619p.mRequestMethod = "POST";
            return this;
        }

        public Builder put() {
            this.f8619p.mRequestMethod = "PUT";
            return this;
        }

        public Builder setConnectTimeout(int i2) {
            this.f8619p.mConnectTimeout = i2;
            return this;
        }

        public Builder setDownloadPath(String str) {
            this.f8619p.mDownloadPath = str;
            return this;
        }

        public Builder setFileKey(String str) {
            this.f8619p.mFileKey = str;
            return this;
        }

        public Builder setFilename(String str) {
            this.f8619p.mFilename = str;
            return this;
        }

        public Builder setFpaPort(int i2) {
            this.f8619p.fpaPort = i2;
            return this;
        }

        public Builder setHeader(String str, String str2) {
            this.f8619p.mHeaders.put(str, str2);
            return this;
        }

        public Builder setHeaders(Map<String, String> map) {
            this.f8619p.mHeaders.putAll(map);
            return this;
        }

        public Builder setLocalFilePath(String str) {
            this.f8619p.mLocalFileUri = str;
            return this;
        }

        public Builder setParam(String str, String str2) {
            this.f8619p.mParams.put(str, str2);
            return this;
        }

        public Builder setParams(String str) {
            this.f8619p.mParamsString = str;
            return this;
        }

        public Builder setParams(Map<String, String> map) {
            this.f8619p.mParams.putAll(map);
            return this;
        }

        public Builder setReadTimeout(int i2) {
            this.f8619p.mReadTimeout = i2;
            return this;
        }

        public Builder setRequestMethod(@NonNull String str) {
            this.f8619p.mRequestMethod = str;
            return this;
        }

        public Builder setRetryTimes(int i2) {
            HttpClientController.HttpParams httpParams = this.f8619p;
            httpParams.canRetry = true;
            httpParams.mRetryTimes = i2;
            return this;
        }

        public Builder setUrl(@NonNull String str) {
            this.f8619p.mUrl = str;
            return this;
        }

        public Builder setUrl(@NonNull String str, int i2) {
            HttpClientController.HttpParams httpParams = this.f8619p;
            httpParams.mUrl = str;
            httpParams.mPort = i2;
            return this;
        }

        public Builder uploadFile(String str) {
            HttpClientController.HttpParams httpParams = this.f8619p;
            httpParams.mRequestMethod = "POST";
            httpParams.mLocalFileUri = str;
            httpParams.isUploadFile = true;
            return this;
        }

        public Builder uploadFile(String str, String str2, String str3) {
            HttpClientController.HttpParams httpParams = this.f8619p;
            httpParams.mRequestMethod = "POST";
            httpParams.mLocalFileUri = str;
            httpParams.mFilename = str2;
            httpParams.mFileKey = str3;
            httpParams.isUploadFile = true;
            return this;
        }

        public Builder withToken(boolean z2) {
            this.f8619p.isNotUseToken = !z2;
            return this;
        }
    }

    public static int downloadFile(String str, int i2, String str2, Map<String, String> map, StringBuilder sb, EMARHttpCallback eMARHttpCallback) {
        return new Builder(EMClient.getInstance().getContext()).downloadFile(str2).setConnectTimeout(30000).setUrl(str).setFpaPort(i2).setHeaders(map).execute(sb, eMARHttpCallback);
    }

    public static int downloadFile(String str, String str2, Map<String, String> map, EMARHttpCallback eMARHttpCallback) {
        return new Builder(EMClient.getInstance().getContext()).downloadFile(str2).setConnectTimeout(30000).setUrl(str).setHeaders(map).execute(null, eMARHttpCallback);
    }

    public static String getNewHost(String str, String str2) {
        return HttpClientConfig.getNewHost(str, str2);
    }

    public static HttpResponse httpExecute(String str, int i2, Map<String, String> map, String str2, String str3) throws IOException {
        return httpExecute(str, i2, map, str2, str3, HttpClientConfig.getTimeout(map));
    }

    public static HttpResponse httpExecute(String str, int i2, Map<String, String> map, String str2, String str3, int i3) throws IOException {
        return new Builder(EMClient.getInstance().getContext()).setRequestMethod(str3).setUrl(str).setFpaPort(i2).setConnectTimeout(i3).setHeaders(map).withToken(false).setParams(str2).execute();
    }

    public static HttpResponse httpExecute(String str, Map<String, String> map, String str2, String str3) throws IOException {
        return httpExecute(str, map, str2, str3, HttpClientConfig.getTimeout(map));
    }

    public static HttpResponse httpExecute(String str, Map<String, String> map, String str2, String str3, int i2) throws IOException {
        return new Builder(EMClient.getInstance().getContext()).setRequestMethod(str3).setUrl(str).setConnectTimeout(i2).setHeaders(map).withToken(false).setParams(str2).execute();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long onDownloadCompleted(HttpResponse httpResponse, Uri uri, HttpCallback httpCallback) throws Exception {
        long j2 = httpResponse.contentLength;
        InputStream inputStream = httpResponse.inputStream;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(EMClient.getInstance().getContext().getContentResolver().openFileDescriptor(uri, "w").getFileDescriptor());
            byte[] bArr = new byte[HIGH_SPEED_DOWNLOAD_BUF_SIZE];
            long j3 = 0;
            int i2 = 0;
            while (true) {
                try {
                    try {
                        int i3 = inputStream.read(bArr);
                        if (i3 == -1) {
                            EMLog.d(TAG, "download by uri fileExistByUri = " + EMFileHelper.getInstance().isFileExist(uri));
                            long jAvailable = inputStream.available();
                            fileOutputStream.close();
                            inputStream.close();
                            return jAvailable;
                        }
                        j3 += i3;
                        int i4 = (int) ((100 * j3) / j2);
                        EMLog.d(TAG, i4 + "");
                        if (i4 == 100 || i4 > i2 + 5) {
                            if (httpCallback != null) {
                                httpCallback.onProgress(j2, j3);
                            }
                            i2 = i4;
                        }
                        fileOutputStream.write(bArr, 0, i3);
                        EMLog.d(TAG, "执行写入操作 count = " + i3);
                    } catch (IOException e2) {
                        EMLog.e(TAG, e2.getMessage());
                        throw e2;
                    }
                } catch (Throwable th) {
                    fileOutputStream.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    throw th;
                }
            }
        } catch (Exception e3) {
            EMLog.e(TAG, e3.getMessage());
            inputStream.close();
            throw e3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long onDownloadCompleted(HttpResponse httpResponse, String str, HttpCallback httpCallback) throws IllegalStateException, IOException {
        long j2 = httpResponse.contentLength;
        InputStream inputStream = httpResponse.inputStream;
        EMLog.d(TAG, "inputStream length = " + inputStream.available());
        File file = new File(str);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            byte[] bArr = new byte[HIGH_SPEED_DOWNLOAD_BUF_SIZE];
            long j3 = 0;
            int i2 = 0;
            while (true) {
                try {
                    try {
                        int i3 = inputStream.read(bArr);
                        if (i3 == -1) {
                            return file.length();
                        }
                        j3 += i3;
                        int i4 = (int) ((100 * j3) / j2);
                        EMLog.d(TAG, i4 + "");
                        if (i4 == 100 || i4 > i2 + 5) {
                            if (httpCallback != null) {
                                httpCallback.onProgress(j2, j3);
                            }
                            i2 = i4;
                        }
                        fileOutputStream.write(bArr, 0, i3);
                    } finally {
                        fileOutputStream.close();
                        inputStream.close();
                    }
                } catch (IOException e2) {
                    EMLog.e(TAG, e2.getMessage());
                    throw e2;
                }
            }
        } catch (IOException e3) {
            EMLog.e(TAG, e3.getMessage());
            inputStream.close();
            throw e3;
        }
    }

    public static Pair<Integer, String> sendDeleteRequest(String str, Map<String, String> map) throws HyphenateException {
        return sendRequestWithToken(str, null, map, Method_DELETE);
    }

    public static Pair<Integer, String> sendGetRequest(String str, Map<String, String> map) throws HyphenateException {
        return sendRequestWithToken(str, null, map, Method_GET);
    }

    public static Pair<Integer, String> sendHttpRequestWithRetryToken(String str, Map<String, String> map, String str2, String str3) throws HyphenateException, IOException {
        return sendRequest(str, map, str2, str3);
    }

    public static Pair<Integer, String> sendPostRequest(String str, String str2, Map<String, String> map) throws HyphenateException {
        return sendRequestWithToken(str, str2, map, Method_POST);
    }

    public static Pair<Integer, String> sendPutRequest(String str, String str2, Map<String, String> map) throws HyphenateException {
        return sendRequestWithToken(str, str2, map, Method_PUT);
    }

    public static Pair<Integer, String> sendRequest(String str, Map<String, String> map, String str2, String str3) throws HyphenateException, IOException {
        HttpResponse httpResponseExecute = new Builder(EMClient.getInstance().getContext()).setRequestMethod(str3).setUrl(str).setHeaders(map).setParams(str2).execute();
        if (httpResponseExecute != null) {
            return new Pair<>(Integer.valueOf(httpResponseExecute.code), httpResponseExecute.content);
        }
        return null;
    }

    public static Pair<Integer, String> sendRequestWithToken(String str, String str2, String str3) throws HyphenateException {
        HashMap map = new HashMap();
        map.put("Authorization", "Bearer " + EMClient.getInstance().getOptions().getAccessToken());
        try {
            return sendHttpRequestWithRetryToken(str, map, str2, str3);
        } catch (IOException e2) {
            String string = " send request : " + str + " failed!";
            if (e2.toString() != null) {
                string = e2.toString();
            }
            EMLog.d(TAG, string);
            throw new HyphenateException(1, string);
        }
    }

    public static Pair<Integer, String> sendRequestWithToken(String str, String str2, Map<String, String> map, String str3) throws HyphenateException {
        if (map == null) {
            map = new HashMap<>();
        }
        if (TextUtils.isEmpty(map.get("Authorization"))) {
            map.put("Authorization", "Bearer " + EMClient.getInstance().getOptions().getAccessToken());
        }
        try {
            return sendHttpRequestWithRetryToken(str, map, str2, str3);
        } catch (IOException e2) {
            String string = " send request : " + str + " failed!";
            if (e2.toString() != null) {
                string = e2.toString();
            }
            EMLog.d(TAG, string);
            throw new HyphenateException(1, string);
        }
    }

    public static int uploadFile(String str, String str2, int i2, String str3, Map<String, String> map, StringBuilder sb, EMARHttpCallback eMARHttpCallback) {
        int iExecute = new Builder(EMClient.getInstance().getContext()).uploadFile(str).setUrl(str2).setFpaPort(i2).setFilename(str3).setConnectTimeout(30000).setHeaders(map).setHeader("app", EMClient.getInstance().getOptions().getAppKey()).setHeader("id", EMClient.getInstance().getCurrentUser()).execute(sb, eMARHttpCallback);
        EMLog.d(TAG, "upload code = " + iExecute);
        return iExecute;
    }
}
