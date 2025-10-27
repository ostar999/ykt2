package com.hjq.http.callback;

import android.text.TextUtils;
import com.hjq.http.EasyLog;
import com.hjq.http.EasyUtils;
import com.hjq.http.exception.MD5Exception;
import com.hjq.http.exception.NullBodyException;
import com.hjq.http.lifecycle.HttpLifecycleManager;
import com.hjq.http.listener.OnDownloadListener;
import com.hjq.http.model.FileWrapper;
import com.hjq.http.request.BaseRequest;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes4.dex */
public final class DownloadCallback extends BaseCallback {
    private static final String FILE_MD5_REGEX = "^[\\w]{32}$";
    private final BaseRequest<?> mBaseRequest;
    private long mDownloadByte;
    private int mDownloadProgress;
    private FileWrapper mFile;
    private OnDownloadListener mListener;
    private String mMd5;
    private long mTotalByte;

    public DownloadCallback(BaseRequest<?> baseRequest) {
        super(baseRequest);
        this.mBaseRequest = baseRequest;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFailure$4(Exception exc) {
        if (this.mListener == null || !HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            return;
        }
        this.mListener.onError(this.mFile, exc);
        this.mListener.onEnd(this.mFile);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResponse$1() {
        if (this.mListener == null || !HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            return;
        }
        this.mListener.onComplete(this.mFile);
        this.mListener.onEnd(this.mFile);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResponse$2() {
        if (this.mListener == null || !HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            return;
        }
        this.mListener.onByte(this.mFile, this.mTotalByte, this.mDownloadByte);
        int progressProgress = EasyUtils.getProgressProgress(this.mTotalByte, this.mDownloadByte);
        if (progressProgress != this.mDownloadProgress) {
            this.mDownloadProgress = progressProgress;
            this.mListener.onProgress(this.mFile, progressProgress);
            EasyLog.print(this.mFile.getPath() + " 正在下载，总字节：" + this.mTotalByte + "，已下载：" + this.mDownloadByte + "，进度：" + progressProgress + " %");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResponse$3() {
        if (this.mListener == null || !HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            return;
        }
        this.mListener.onComplete(this.mFile);
        this.mListener.onEnd(this.mFile);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$0() {
        if (this.mListener == null || !HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            return;
        }
        this.mListener.onStart(this.mFile);
    }

    @Override // com.hjq.http.callback.BaseCallback
    public void onFailure(Exception exc) {
        final Exception excRequestFail = this.mBaseRequest.getRequestHandler().requestFail(this.mBaseRequest.getLifecycleOwner(), this.mBaseRequest.getRequestApi(), exc);
        EasyLog.print(excRequestFail);
        EasyUtils.post(new Runnable() { // from class: w0.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f28271c.lambda$onFailure$4(excRequestFail);
            }
        });
    }

    @Override // com.hjq.http.callback.BaseCallback
    public void onResponse(Response response) throws Exception {
        if (this.mMd5 == null) {
            String strHeader = response.header("Content-MD5");
            if (!TextUtils.isEmpty(strHeader) && strHeader.matches(FILE_MD5_REGEX)) {
                this.mMd5 = strHeader;
            }
        }
        File parentFile = this.mFile.getParentFile();
        if (parentFile != null) {
            FileWrapper.createFolder(parentFile);
        }
        ResponseBody responseBodyBody = response.body();
        if (responseBodyBody == null) {
            throw new NullBodyException("The response body is empty");
        }
        long contentLength = responseBodyBody.getContentLength();
        this.mTotalByte = contentLength;
        if (contentLength < 0) {
            this.mTotalByte = 0L;
        }
        if (!TextUtils.isEmpty(this.mMd5) && this.mFile.isFile() && this.mMd5.equalsIgnoreCase(FileWrapper.getFileMd5(this.mFile.openInputStream()))) {
            EasyUtils.post(new Runnable() { // from class: w0.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28268c.lambda$onResponse$1();
                }
            });
            return;
        }
        this.mDownloadByte = 0L;
        byte[] bArr = new byte[8192];
        InputStream inputStreamByteStream = responseBodyBody.byteStream();
        OutputStream outputStreamOpenOutputStream = this.mFile.openOutputStream();
        while (true) {
            int i2 = inputStreamByteStream.read(bArr);
            if (i2 == -1) {
                break;
            }
            this.mDownloadByte += i2;
            outputStreamOpenOutputStream.write(bArr, 0, i2);
            EasyUtils.post(new Runnable() { // from class: w0.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28269c.lambda$onResponse$2();
                }
            });
        }
        EasyUtils.closeStream(inputStreamByteStream);
        EasyUtils.closeStream(outputStreamOpenOutputStream);
        String fileMd5 = FileWrapper.getFileMd5(this.mFile.openInputStream());
        if (!TextUtils.isEmpty(this.mMd5) && !this.mMd5.equalsIgnoreCase(fileMd5)) {
            throw new MD5Exception("MD5 verify failure", fileMd5);
        }
        EasyUtils.post(new Runnable() { // from class: w0.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f28270c.lambda$onResponse$3();
            }
        });
    }

    @Override // com.hjq.http.callback.BaseCallback
    public void onStart(Call call) {
        EasyUtils.post(new Runnable() { // from class: w0.f
            @Override // java.lang.Runnable
            public final void run() {
                this.f28273c.lambda$onStart$0();
            }
        });
    }

    public DownloadCallback setFile(FileWrapper fileWrapper) {
        this.mFile = fileWrapper;
        return this;
    }

    public DownloadCallback setListener(OnDownloadListener onDownloadListener) {
        this.mListener = onDownloadListener;
        return this;
    }

    public DownloadCallback setMd5(String str) {
        this.mMd5 = str;
        return this;
    }
}
