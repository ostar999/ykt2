package com.arialyy.aria.http.upload;

import android.text.TextUtils;
import com.arialyy.aria.core.common.SubThreadConfig;
import com.arialyy.aria.core.upload.UploadEntity;
import com.arialyy.aria.exception.AriaHTTPException;
import com.arialyy.aria.http.BaseHttpThreadTaskAdapter;
import com.arialyy.aria.http.ConnectionHelp;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.BandwidthLimiter;
import com.arialyy.aria.util.CommonUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes2.dex */
final class HttpUThreadTaskAdapter extends BaseHttpThreadTaskAdapter {
    private final String BOUNDARY;
    private final String LINE_END;
    private final String PREFIX;
    private HttpURLConnection mHttpConn;
    private OutputStream mOutputStream;

    public HttpUThreadTaskAdapter(SubThreadConfig subThreadConfig) {
        super(subThreadConfig);
        this.BOUNDARY = UUID.randomUUID().toString();
        this.PREFIX = "--";
        this.LINE_END = "\r\n";
    }

    private void addFormField(PrintWriter printWriter, String str, String str2) {
        printWriter.append("--").append((CharSequence) this.BOUNDARY).append("\r\n");
        printWriter.append("Content-Disposition: form-data; name=\"").append((CharSequence) str).append("\"").append("\r\n");
        printWriter.append("Content-Type: text/plain; charset=").append((CharSequence) this.mTaskOption.getCharSet()).append("\r\n");
        printWriter.append("\r\n");
        printWriter.append((CharSequence) str2).append("\r\n");
        printWriter.flush();
    }

    private void fail(AriaHTTPException ariaHTTPException) throws IOException {
        try {
            fail(ariaHTTPException, false);
            OutputStream outputStream = this.mOutputStream;
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private String finish(PrintWriter printWriter) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        int responseCode = this.mHttpConn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ConnectionHelp.convertInputStream(this.mHttpConn)));
            while (getThreadTask().isLive() && (line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            this.mHttpConn.disconnect();
            complete();
        } else {
            String str = "response msg: " + this.mHttpConn.getResponseMessage() + "，code: " + responseCode;
            ALog.e(this.TAG, str);
            fail(new AriaHTTPException(str), false);
            sb.append(responseCode);
        }
        printWriter.flush();
        printWriter.close();
        this.mOutputStream.close();
        return sb.toString();
    }

    private String getContentType() {
        return "multipart/form-data";
    }

    private String getUserAgent() {
        return (this.mTaskOption.getHeaders() == null || TextUtils.isEmpty(this.mTaskOption.getHeaders().get("User-Agent"))) ? "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)" : this.mTaskOption.getHeaders().get("User-Agent");
    }

    private void uploadFile(PrintWriter printWriter, String str, File file) throws IOException {
        printWriter.append("--").append((CharSequence) this.BOUNDARY).append("\r\n");
        printWriter.append("Content-Disposition: form-data; name=\"").append((CharSequence) str).append("\"; filename=\"").append((CharSequence) getEntity().getFileName()).append("\"").append("\r\n");
        printWriter.append("Content-Type: ").append((CharSequence) URLConnection.guessContentTypeFromName(getEntity().getFileName())).append("\r\n");
        printWriter.append("Content-Transfer-Encoding: binary").append("\r\n");
        printWriter.append("\r\n");
        printWriter.flush();
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[4096];
        while (true) {
            int i2 = fileInputStream.read(bArr);
            if (i2 == -1) {
                break;
            }
            this.mOutputStream.write(bArr, 0, i2);
            progress(i2);
            if (getThreadTask().isBreak()) {
                break;
            }
            BandwidthLimiter bandwidthLimiter = this.mSpeedBandUtil;
            if (bandwidthLimiter != null) {
                bandwidthLimiter.limitNextBytes(i2);
            }
        }
        this.mOutputStream.flush();
        fileInputStream.close();
        printWriter.append("\r\n").flush();
        printWriter.append("--").append((CharSequence) this.BOUNDARY).append("--").append("\r\n").flush();
    }

    @Override // com.arialyy.aria.core.task.AbsThreadTaskAdapter
    public void handlerThreadTask() throws IOException {
        File file = new File(getEntity().getFilePath());
        if (!file.exists()) {
            fail(new AriaHTTPException(String.format("上传失败，文件不存在；filePath: %s, url: %s", getEntity().getFilePath(), getEntity().getUrl())));
            return;
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(CommonUtil.convertUrl(getThreadConfig().url)).openConnection();
            this.mHttpConn = httpURLConnection;
            httpURLConnection.setRequestMethod(this.mTaskOption.getRequestEnum().name);
            this.mHttpConn.setUseCaches(false);
            this.mHttpConn.setDoOutput(true);
            this.mHttpConn.setDoInput(true);
            this.mHttpConn.setRequestProperty("Connection", "Keep-Alive");
            this.mHttpConn.setRequestProperty("Content-Type", getContentType() + "; boundary=" + this.BOUNDARY);
            this.mHttpConn.setRequestProperty("User-Agent", getUserAgent());
            this.mHttpConn.setConnectTimeout(getTaskConfig().getConnectTimeOut());
            this.mHttpConn.setReadTimeout(getTaskConfig().getIOTimeOut());
            this.mHttpConn.setChunkedStreamingMode(getTaskConfig().getBuffSize());
            for (String str : this.mTaskOption.getHeaders().keySet()) {
                this.mHttpConn.setRequestProperty(str, this.mTaskOption.getHeaders().get(str));
            }
            this.mOutputStream = this.mHttpConn.getOutputStream();
            PrintWriter printWriter = new PrintWriter((Writer) new OutputStreamWriter(this.mOutputStream, this.mTaskOption.getCharSet()), true);
            Map<String, String> params = this.mTaskOption.getParams();
            if (params != null && !params.isEmpty()) {
                for (String str2 : params.keySet()) {
                    addFormField(printWriter, str2, params.get(str2));
                }
            }
            for (String str3 : this.mTaskOption.getFormFields().keySet()) {
                addFormField(printWriter, str3, this.mTaskOption.getFormFields().get(str3));
            }
            uploadFile(printWriter, this.mTaskOption.getAttachment(), file);
            getEntity().setResponseStr(finish(printWriter));
        } catch (Exception e2) {
            e2.printStackTrace();
            fail(new AriaHTTPException(String.format("上传失败，filePath: %s, url: %s", getEntity().getFilePath(), getEntity().getUrl()), e2));
        }
    }

    @Override // com.arialyy.aria.http.BaseHttpThreadTaskAdapter
    public UploadEntity getEntity() {
        return (UploadEntity) super.getEntity();
    }
}
