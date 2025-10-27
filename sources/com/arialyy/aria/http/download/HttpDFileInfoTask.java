package com.arialyy.aria.http.download;

import android.net.TrafficStats;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.common.CompleteInfo;
import com.arialyy.aria.core.common.RequestEnum;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.loader.IInfoTask;
import com.arialyy.aria.core.loader.ILoaderVisitor;
import com.arialyy.aria.core.processor.IHttpFileLenAdapter;
import com.arialyy.aria.exception.AriaHTTPException;
import com.arialyy.aria.http.ConnectionHelp;
import com.arialyy.aria.http.HttpTaskOption;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CheckUtil;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.FileUtil;
import com.arialyy.aria.util.RecordUtil;
import com.mobile.auth.gatewayauth.ResultCode;
import com.tencent.smtt.sdk.TbsVideoCacheTask;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* loaded from: classes2.dex */
final class HttpDFileInfoTask implements IInfoTask, Runnable {
    private static final String TAG = "HttpDFileInfoTask";
    private IInfoTask.Callback callback;
    private int mConnectTimeOut = AriaConfig.getInstance().getDConfig().getConnectTimeOut();
    private DownloadEntity mEntity;
    private DTaskWrapper mTaskWrapper;
    private HttpTaskOption taskOption;

    public static class FileLenAdapter implements IHttpFileLenAdapter {
        private FileLenAdapter() {
        }

        @Override // com.arialyy.aria.core.processor.IHttpFileLenAdapter
        public long handleFileLen(Map<String, List<String>> map) {
            if (map == null || map.isEmpty()) {
                ALog.e(HttpDFileInfoTask.TAG, "header为空，获取文件长度失败");
                return -1L;
            }
            List<String> list = map.get("Content-Length");
            if (list == null || list.isEmpty()) {
                return -1L;
            }
            String str = list.get(0);
            long j2 = TextUtils.isEmpty(str) ? -1L : Long.parseLong(str);
            if (j2 >= 0) {
                return j2;
            }
            List<String> list2 = map.get("Content-Range");
            if (list2 == null || list2.isEmpty()) {
                return -1L;
            }
            return Long.parseLong(str.substring(str.indexOf("/") + 1));
        }
    }

    public HttpDFileInfoTask(DTaskWrapper dTaskWrapper) {
        this.mTaskWrapper = dTaskWrapper;
        this.mEntity = dTaskWrapper.getEntity();
        this.taskOption = (HttpTaskOption) dTaskWrapper.getTaskOption();
    }

    private boolean checkLen(long j2) {
        if (j2 != this.mEntity.getFileSize()) {
            ALog.d(TAG, "长度不一致，任务为新任务");
            this.mTaskWrapper.setNewTask(true);
        }
        return true;
    }

    private void failDownload(AriaHTTPException ariaHTTPException, boolean z2) {
        IInfoTask.Callback callback = this.callback;
        if (callback != null) {
            callback.onFail(this.mEntity, ariaHTTPException, z2);
        }
    }

    private void handleConnect(HttpURLConnection httpURLConnection) throws NoSuchAlgorithmException, IOException, KeyManagementException {
        Map<String, String> params;
        boolean z2 = true;
        if (this.taskOption.getRequestEnum() == RequestEnum.POST && (params = this.taskOption.getParams()) != null) {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
            Set<String> setKeySet = params.keySet();
            StringBuilder sb = new StringBuilder();
            for (String str : setKeySet) {
                sb.append(str);
                sb.append("=");
                sb.append(URLEncoder.encode(params.get(str)));
                sb.append("&");
            }
            String string = sb.toString();
            outputStreamWriter.write(string.substring(0, string.length() - 1));
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
        IHttpFileLenAdapter fileLenAdapter = this.taskOption.getFileLenAdapter();
        if (fileLenAdapter == null) {
            fileLenAdapter = new FileLenAdapter();
        } else {
            ALog.d(TAG, "使用自定义adapter");
        }
        long jHandleFileLen = fileLenAdapter.handleFileLen(httpURLConnection.getHeaderFields());
        if (!FileUtil.checkMemorySpace(this.mEntity.getFilePath(), jHandleFileLen)) {
            failDownload(new AriaHTTPException(String.format("下载失败，内存空间不足；filePath: %s, url: %s", this.mEntity.getFilePath(), this.mEntity.getUrl())), false);
            return;
        }
        int responseCode = httpURLConnection.getResponseCode();
        if (TextUtils.isEmpty(this.mEntity.getMd5Code())) {
            this.mEntity.setMd5Code(httpURLConnection.getHeaderField("Content-MD5"));
        }
        String headerField = httpURLConnection.getHeaderField("Transfer-Encoding");
        boolean z3 = !TextUtils.isEmpty(headerField) && headerField.equals("chunked");
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        String headerField2 = httpURLConnection.getHeaderField("Content-Disposition");
        if (this.taskOption.isUseServerFileName()) {
            if (!TextUtils.isEmpty(headerField2)) {
                this.mEntity.setDisposition(CommonUtil.encryptBASE64(headerField2));
                handleContentDisposition(headerField2);
            } else if (this.taskOption.getFileNameAdapter() != null) {
                String strHandleFileName = this.taskOption.getFileNameAdapter().handleFileName(headerFields, this.mEntity.getKey());
                this.mEntity.setServerFileName(strHandleFileName);
                renameFile(strHandleFileName);
            }
        }
        CookieManager cookieManager = new CookieManager();
        List<String> list = headerFields.get("Set-Cookie");
        if (list != null) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse(it.next()).get(0));
            }
            this.taskOption.setCookieManager(cookieManager);
        }
        this.mTaskWrapper.setCode(responseCode);
        if (responseCode == 206) {
            if (!checkLen(jHandleFileLen) && !z3) {
                if (jHandleFileLen < 0) {
                    failDownload(new AriaHTTPException(String.format("任务下载失败，文件长度小于0， url: %s", this.mEntity.getUrl())), false);
                    return;
                }
                return;
            }
            this.mEntity.setFileSize(jHandleFileLen);
            this.mTaskWrapper.setSupportBP(true);
        } else if (responseCode == 200) {
            String headerField3 = httpURLConnection.getHeaderField("Content-Type");
            if (TextUtils.isEmpty(headerField3)) {
                return;
            }
            if (headerField3.equals("text/html")) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ConnectionHelp.convertInputStream(httpURLConnection)));
                StringBuilder sb2 = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        bufferedReader.close();
                        handleUrlReTurn(httpURLConnection, CommonUtil.getWindowReplaceUrl(sb2.toString()));
                        return;
                    }
                    sb2.append(line);
                }
            } else if (!checkLen(jHandleFileLen) && !z3) {
                if (jHandleFileLen < 0) {
                    failDownload(new AriaHTTPException(String.format("任务下载失败，文件长度小于0， url: %s", this.mEntity.getUrl())), false);
                }
                ALog.d(TAG, "len < 0");
                return;
            } else {
                this.mEntity.setFileSize(jHandleFileLen);
                this.mTaskWrapper.setNewTask(true);
                this.mTaskWrapper.setSupportBP(false);
            }
        } else {
            if (responseCode == 302 || responseCode == 301 || responseCode == 303 || responseCode == 201 || responseCode == 307) {
                handleUrlReTurn(httpURLConnection, httpURLConnection.getHeaderField("Location"));
            } else if (responseCode == 416) {
                ALog.w(TAG, "文件长度为0，不支持断点");
                this.mTaskWrapper.setSupportBP(false);
                this.mTaskWrapper.setNewTask(true);
            } else if (responseCode >= 400) {
                failDownload(new AriaHTTPException(String.format("任务下载失败，errorCode：%s, url: %s", Integer.valueOf(responseCode), this.mEntity.getUrl())), false);
            } else {
                failDownload(new AriaHTTPException(String.format("任务下载失败，errorCode：%s, errorMsg: %s, url: %s", Integer.valueOf(responseCode), httpURLConnection.getResponseMessage(), this.mEntity.getUrl())), !CheckUtil.httpIsBadRequest(responseCode));
            }
            z2 = false;
        }
        if (z2) {
            this.taskOption.setChunked(z3);
            if (this.callback != null) {
                this.callback.onSucceed(this.mEntity.getUrl(), new CompleteInfo(responseCode, this.mTaskWrapper));
            }
            this.mEntity.update();
        }
    }

    private void handleContentDisposition(String str) throws UnsupportedEncodingException {
        if (str.contains(h.f3376b)) {
            String[] strArrSplit = str.split(h.f3376b);
            if (!strArrSplit[0].equals("attachment")) {
                if (!strArrSplit[0].equals("form-data") || strArrSplit.length <= 2) {
                    ALog.w(TAG, "不识别的Content-Disposition参数");
                    return;
                }
                String[] strArrSplit2 = strArrSplit[2].split("=");
                if (strArrSplit2.length > 1) {
                    String strReplaceAll = URLDecoder.decode(strArrSplit2[1], "utf-8").replaceAll("\"", "");
                    this.mEntity.setServerFileName(strReplaceAll);
                    renameFile(strReplaceAll);
                    return;
                }
                return;
            }
            for (String str2 : strArrSplit) {
                if (str2.startsWith(TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME) && str2.contains("=")) {
                    String[] strArrSplit3 = str2.split("=");
                    if (strArrSplit3.length > 1) {
                        String strReplaceAll2 = URLDecoder.decode(strArrSplit3[1], "utf-8").replaceAll("\"", "");
                        this.mEntity.setServerFileName(strReplaceAll2);
                        renameFile(strReplaceAll2);
                        return;
                    }
                }
            }
        }
    }

    private void handleUrlReTurn(HttpURLConnection httpURLConnection, String str) throws NoSuchAlgorithmException, IOException, KeyManagementException {
        ALog.d(TAG, "30x跳转，新url为【" + str + "】");
        if (TextUtils.isEmpty(str) || str.equalsIgnoreCase("null")) {
            IInfoTask.Callback callback = this.callback;
            if (callback != null) {
                callback.onFail(this.mEntity, new AriaHTTPException("获取重定向链接失败"), false);
                return;
            }
            return;
        }
        if (str.startsWith("/")) {
            str = Uri.parse(this.mEntity.getUrl()).getHost() + str;
        }
        if (!CheckUtil.checkUrl(str)) {
            failDownload(new AriaHTTPException("下载失败，重定向url错误"), false);
            return;
        }
        this.taskOption.setRedirectUrl(str);
        this.mEntity.setRedirect(true);
        this.mEntity.setRedirectUrl(str);
        String headerField = httpURLConnection.getHeaderField("Set-Cookie");
        httpURLConnection.disconnect();
        HttpURLConnection httpURLConnectionHandleConnection = ConnectionHelp.handleConnection(ConnectionHelp.handleUrl(str, this.taskOption), this.taskOption);
        ConnectionHelp.setConnectParam(this.taskOption, httpURLConnectionHandleConnection);
        httpURLConnectionHandleConnection.setRequestProperty("Cookie", headerField);
        httpURLConnectionHandleConnection.setRequestProperty("Range", "bytes=0-");
        if (AriaConfig.getInstance().getDConfig().isUseHeadRequest()) {
            httpURLConnectionHandleConnection.setRequestMethod("HEAD");
        }
        httpURLConnectionHandleConnection.setConnectTimeout(this.mConnectTimeOut);
        httpURLConnectionHandleConnection.connect();
        handleConnect(httpURLConnectionHandleConnection);
        httpURLConnectionHandleConnection.disconnect();
    }

    private void renameFile(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.w(TAG, "重命名失败【服务器返回的文件名为空】");
            return;
        }
        ALog.d(TAG, String.format("文件重命名为：%s", str));
        File file = new File(this.mEntity.getFilePath());
        String str2 = file.getParent() + "/" + str;
        if (!CheckUtil.checkDPathConflicts(false, str2, this.mTaskWrapper.getRequestType())) {
            ALog.e(TAG, "文件重命名失败");
            return;
        }
        if (file.exists()) {
            Object[] objArr = new Object[1];
            objArr[0] = file.renameTo(new File(str2)) ? ResultCode.MSG_SUCCESS : ResultCode.MSG_FAILED;
            ALog.d(TAG, String.format("文件重命名%s", objArr));
        }
        this.mEntity.setFileName(str);
        this.mEntity.setFilePath(str2);
        RecordUtil.modifyTaskRecord(file.getPath(), str2, this.mEntity.getTaskType());
    }

    @Override // com.arialyy.aria.core.loader.ILoaderComponent
    public void accept(ILoaderVisitor iLoaderVisitor) {
        iLoaderVisitor.addComponent(this);
    }

    @Override // com.arialyy.aria.core.loader.IInfoTask, java.lang.Runnable
    public void run() throws SecurityException, IOException, IllegalArgumentException {
        Process.setThreadPriority(10);
        TrafficStats.setThreadStatsTag(UUID.randomUUID().toString().hashCode());
        HttpURLConnection httpURLConnectionHandleConnection = null;
        try {
            try {
                httpURLConnectionHandleConnection = ConnectionHelp.handleConnection(ConnectionHelp.handleUrl(this.mEntity.getUrl(), this.taskOption), this.taskOption);
                ConnectionHelp.setConnectParam(this.taskOption, httpURLConnectionHandleConnection);
                httpURLConnectionHandleConnection.setRequestProperty("Range", "bytes=0-");
                if (AriaConfig.getInstance().getDConfig().isUseHeadRequest()) {
                    ALog.d(TAG, "head请求");
                    httpURLConnectionHandleConnection.setRequestMethod("HEAD");
                }
                httpURLConnectionHandleConnection.setConnectTimeout(this.mConnectTimeOut);
                httpURLConnectionHandleConnection.connect();
                handleConnect(httpURLConnectionHandleConnection);
                try {
                    InputStream inputStream = httpURLConnectionHandleConnection.getInputStream();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    httpURLConnectionHandleConnection.disconnect();
                }
            } catch (IOException e3) {
                failDownload(new AriaHTTPException(String.format("下载失败，filePath: %s, url: %s", this.mEntity.getFilePath(), this.mEntity.getUrl()), e3), true);
                if (httpURLConnectionHandleConnection == null) {
                    return;
                }
                try {
                    InputStream inputStream2 = httpURLConnectionHandleConnection.getInputStream();
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                } catch (IOException e4) {
                    e = e4;
                    e.printStackTrace();
                    httpURLConnectionHandleConnection.disconnect();
                }
            }
            httpURLConnectionHandleConnection.disconnect();
        } catch (Throwable th) {
            if (httpURLConnectionHandleConnection != null) {
                try {
                    InputStream inputStream3 = httpURLConnectionHandleConnection.getInputStream();
                    if (inputStream3 != null) {
                        inputStream3.close();
                    }
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                httpURLConnectionHandleConnection.disconnect();
            }
            throw th;
        }
    }

    @Override // com.arialyy.aria.core.loader.IInfoTask
    public void setCallback(IInfoTask.Callback callback) {
        this.callback = callback;
    }
}
