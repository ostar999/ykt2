package com.plv.livescenes.upload.oss;

import android.os.Environment;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.upload.model.PLVPPTUploadOSSCallbackVO;
import java.io.File;
import java.util.HashMap;

/* loaded from: classes5.dex */
public class PLVUploadEngine {
    private static final String TAG = "PLVUploadEngine";
    private String bucketName;
    private String callback;
    private String objectKey;
    private OnPLVSUploadEngineListener onPLVSUploadEngineListener;
    private OSSClient ossClient;
    private String uploadFilePath;
    private OSSAsyncTask<ResumableUploadResult> uploadTask;

    public interface OnPLVSUploadEngineListener {
        void onFail(Throwable th);

        void onProgress(long j2, long j3);

        void onSuccess();
    }

    public PLVUploadEngine(OSSClient oSSClient, String str, String str2, String str3, String str4, OnPLVSUploadEngineListener onPLVSUploadEngineListener) {
        this.ossClient = oSSClient;
        this.bucketName = str;
        this.objectKey = str2;
        this.uploadFilePath = str3;
        this.callback = str4;
        this.onPLVSUploadEngineListener = onPLVSUploadEngineListener;
    }

    private ResumableUploadRequest createResumableUploadRequest() {
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/oss_record/";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        HashMap map = new HashMap();
        PLVPPTUploadOSSCallbackVO pLVPPTUploadOSSCallbackVO = (PLVPPTUploadOSSCallbackVO) PLVGsonUtil.fromJson(PLVPPTUploadOSSCallbackVO.class, this.callback);
        if (pLVPPTUploadOSSCallbackVO != null) {
            map.put("callbackUrl", pLVPPTUploadOSSCallbackVO.getCallbackUrl());
            map.put("callbackHost", pLVPPTUploadOSSCallbackVO.getCallbackHost());
            map.put("callbackBodyType", pLVPPTUploadOSSCallbackVO.getCallbackBodyType());
            map.put("callbackBody", pLVPPTUploadOSSCallbackVO.getCallbackBody());
        }
        ResumableUploadRequest resumableUploadRequest = new ResumableUploadRequest(this.bucketName, this.objectKey, this.uploadFilePath, str);
        resumableUploadRequest.setCallbackParam(map);
        resumableUploadRequest.setDeleteUploadOnCancelling(Boolean.FALSE);
        resumableUploadRequest.setProgressCallback(new OSSProgressCallback() { // from class: com.plv.livescenes.upload.oss.PLVUploadEngine.2
            @Override // com.alibaba.sdk.android.oss.callback.OSSProgressCallback
            public void onProgress(Object obj, long j2, long j3) {
                PLVUploadEngine.this.onPLVSUploadEngineListener.onProgress(j2, j3);
            }
        });
        return resumableUploadRequest;
    }

    public void cancelUpload() {
        OSSAsyncTask<ResumableUploadResult> oSSAsyncTask = this.uploadTask;
        if (oSSAsyncTask != null) {
            oSSAsyncTask.cancel();
        }
    }

    public void startUpload() {
        this.uploadTask = this.ossClient.asyncResumableUpload(createResumableUploadRequest(), new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() { // from class: com.plv.livescenes.upload.oss.PLVUploadEngine.1
            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onFailure(ResumableUploadRequest resumableUploadRequest, ClientException clientException, ServiceException serviceException) {
                PLVCommonLog.d(PLVUploadEngine.TAG, "文件上传失败");
                if (clientException != null) {
                    PLVCommonLog.exception(clientException);
                    if (clientException.isCanceledException().booleanValue()) {
                        return;
                    }
                    PLVUploadEngine.this.onPLVSUploadEngineListener.onFail(clientException);
                    return;
                }
                if (serviceException != null) {
                    PLVCommonLog.exception(serviceException);
                    PLVUploadEngine.this.onPLVSUploadEngineListener.onFail(serviceException);
                }
            }

            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onSuccess(ResumableUploadRequest resumableUploadRequest, ResumableUploadResult resumableUploadResult) {
                PLVCommonLog.d(PLVUploadEngine.TAG, "文件上传成功");
                PLVUploadEngine.this.onPLVSUploadEngineListener.onSuccess();
            }
        });
    }
}
