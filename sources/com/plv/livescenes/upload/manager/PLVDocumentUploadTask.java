package com.plv.livescenes.upload.manager;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import com.easefun.polyv.livescenes.log.upload.PLVSDocumentUploadELog;
import com.easefun.polyv.livescenes.upload.oss.PLVSUploadEngine;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.livescenes.log.upload.PLVDocumentUploadELog;
import com.plv.livescenes.upload.manager.PLVDocumentUploadException;
import com.plv.livescenes.upload.model.PLVPPTUploadTokenVO;
import com.plv.livescenes.upload.oss.PLVOSSClientCreator;
import com.plv.livescenes.upload.oss.PLVUploadEngine;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;

/* loaded from: classes5.dex */
class PLVDocumentUploadTask {
    private static final String TAG = "PLVDocumentUploadTask";
    private Disposable initDisposable;
    private OnPLVSDocumentUploadTaskListener onPLVSDocumentUploadTaskListener;
    private OnPLVSDocumentUploadTaskPreparedListener onPreparedListener;
    private PLVDocumentDataSource tokenFetcher;
    private PLVSUploadEngine uploadEngine;

    public interface OnPLVSDocumentUploadTaskListener {
        void onDocumentConverting();

        void onDocumentExist();

        void onFail(PLVDocumentUploadException pLVDocumentUploadException);

        void onProgress(long j2, long j3);

        void onSuccess();
    }

    public interface OnPLVSDocumentUploadTaskPreparedListener {
        void onPrepared();
    }

    public PLVDocumentUploadTask(final Context context, final String str, final String str2, final String str3, final String str4, PLVDocumentDataSource pLVDocumentDataSource, OnPLVSDocumentUploadTaskListener onPLVSDocumentUploadTaskListener) {
        this.onPLVSDocumentUploadTaskListener = onPLVSDocumentUploadTaskListener;
        this.tokenFetcher = pLVDocumentDataSource;
        this.initDisposable = Observable.just(1).observeOn(Schedulers.io()).map(new Function<Integer, PLVPPTUploadTokenVO>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadTask.3
            @Override // io.reactivex.functions.Function
            public PLVPPTUploadTokenVO apply(Integer num) throws Exception {
                PLVDocumentUploadTask.this.sendTrackLog(PLVDocumentUploadELog.DocumentUploadEvent.GET_TOKEN, "filePath=" + str4 + " type=" + str2 + " fileId=" + str3);
                PLVDocumentUploadTask pLVDocumentUploadTask = PLVDocumentUploadTask.this;
                return pLVDocumentUploadTask.getUploadToken(str, str2, str3, pLVDocumentUploadTask.onPLVSDocumentUploadTaskListener, false);
            }
        }).subscribe(new Consumer<PLVPPTUploadTokenVO>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadTask.1
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPPTUploadTokenVO pLVPPTUploadTokenVO) throws Exception {
                String convertStatus;
                PLVDocumentUploadTask.this.sendTrackLog(PLVDocumentUploadELog.DocumentUploadEvent.GET_TOKEN_SUCCESS, "filePath=" + str4 + " type=" + str2 + " fileId=" + str3);
                convertStatus = pLVPPTUploadTokenVO.getData().getConvertStatus();
                if (convertStatus == null) {
                    convertStatus = "";
                }
                switch (convertStatus) {
                    case "normal":
                        PLVDocumentUploadTask.this.onPLVSDocumentUploadTaskListener.onDocumentExist();
                        PLVDocumentUploadTask.this.initDisposable.dispose();
                        break;
                    case "waitUpload":
                        PLVDocumentUploadTask.this.uploadEngine = new PLVSUploadEngine(PLVOSSClientCreator.createOSSClient(context, pLVPPTUploadTokenVO.getData().getEndpoint(), new PLVOSSClientCreator.OnTokenNeedRefresh() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadTask.1.1
                            @Override // com.plv.livescenes.upload.oss.PLVOSSClientCreator.OnTokenNeedRefresh
                            public PLVOSSClientCreator.STSToken refreshToken() throws IOException {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                PLVDocumentUploadTask pLVDocumentUploadTask = PLVDocumentUploadTask.this;
                                PLVPPTUploadTokenVO uploadToken = pLVDocumentUploadTask.getUploadToken(str, str2, str3, pLVDocumentUploadTask.onPLVSDocumentUploadTaskListener, true);
                                PLVOSSClientCreator.STSToken sTSToken = new PLVOSSClientCreator.STSToken(uploadToken.getData().getAccessId(), uploadToken.getData().getAccessKey(), uploadToken.getData().getToken(), uploadToken.getData().getExpiration());
                                PLVCommonLog.d(PLVDocumentUploadTask.TAG, sTSToken.toString());
                                PLVDocumentUploadTask.this.sendTrackLog(PLVDocumentUploadELog.DocumentUploadEvent.REFRESH_STS_TOKEN, "polyvToken=" + uploadToken.toString() + " \nstsToken=" + sTSToken.toString());
                                return sTSToken;
                            }
                        }), pLVPPTUploadTokenVO.getData().getBucket(), pLVPPTUploadTokenVO.getData().getObject(), str4, pLVPPTUploadTokenVO.getData().getCallback(), new PLVUploadEngine.OnPLVSUploadEngineListener() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadTask.1.2
                            @Override // com.plv.livescenes.upload.oss.PLVUploadEngine.OnPLVSUploadEngineListener
                            public void onFail(Throwable th) {
                                PLVDocumentUploadTask.this.onPLVSDocumentUploadTaskListener.onFail(new PLVDocumentUploadException(th.getMessage(), th, 4, PLVDocumentUploadException.Type.TYPE_UPLOAD_PROCESS));
                            }

                            @Override // com.plv.livescenes.upload.oss.PLVUploadEngine.OnPLVSUploadEngineListener
                            public void onProgress(long j2, long j3) {
                                PLVDocumentUploadTask.this.onPLVSDocumentUploadTaskListener.onProgress(j2, j3);
                            }

                            @Override // com.plv.livescenes.upload.oss.PLVUploadEngine.OnPLVSUploadEngineListener
                            public void onSuccess() {
                                PLVDocumentUploadTask.this.onPLVSDocumentUploadTaskListener.onSuccess();
                            }
                        });
                        PLVDocumentUploadTask.this.onPreparedListener.onPrepared();
                        break;
                    case "waitConvert":
                        PLVDocumentUploadTask.this.onPreparedListener.onPrepared();
                        PLVDocumentUploadTask.this.onPLVSDocumentUploadTaskListener.onDocumentConverting();
                        PLVDocumentUploadTask.this.initDisposable.dispose();
                        break;
                    default:
                        PLVDocumentUploadTask.this.onPLVSDocumentUploadTaskListener.onFail(new PLVDocumentUploadException("不支持的转码类型=" + convertStatus, new Throwable(), 3, PLVDocumentUploadException.Type.TYPE_TOKEN));
                        break;
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadTask.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                th.printStackTrace();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public PLVPPTUploadTokenVO getUploadToken(String str, String str2, String str3, OnPLVSDocumentUploadTaskListener onPLVSDocumentUploadTaskListener, boolean z2) throws IOException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            IOException iOException = new IOException();
            if (z2) {
                onPLVSDocumentUploadTaskListener.onFail(new PLVDocumentUploadException("参数不可为空", iOException, 5, PLVDocumentUploadException.Type.TYPE_UPLOAD_PROCESS));
                throw iOException;
            }
            onPLVSDocumentUploadTaskListener.onFail(new PLVDocumentUploadException("参数不可为空", iOException, 1, PLVDocumentUploadException.Type.TYPE_TOKEN));
            throw iOException;
        }
        try {
            PLVPPTUploadTokenVO uploadToken = this.tokenFetcher.getUploadToken(str, str2, str3);
            if (uploadToken.getCode() == 200) {
                return uploadToken;
            }
            IOException iOException2 = new IOException();
            if (z2) {
                onPLVSDocumentUploadTaskListener.onFail(new PLVDocumentUploadException(uploadToken.toString(), iOException2, 5, PLVDocumentUploadException.Type.TYPE_UPLOAD_PROCESS));
                throw iOException2;
            }
            onPLVSDocumentUploadTaskListener.onFail(new PLVDocumentUploadException(uploadToken.toString(), iOException2, 2, PLVDocumentUploadException.Type.TYPE_TOKEN));
            throw iOException2;
        } catch (Exception e2) {
            onPLVSDocumentUploadTaskListener.onFail(new PLVDocumentUploadException("同步获取token请求失败", e2, 2, PLVDocumentUploadException.Type.TYPE_TOKEN));
            throw new IOException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTrackLog(String str, String str2) {
        PLVELogsService.getInstance().addStaticsLog(PLVSDocumentUploadELog.class, str, str2, new String[0]);
    }

    public void cancel() {
        this.initDisposable.dispose();
        PLVSUploadEngine pLVSUploadEngine = this.uploadEngine;
        if (pLVSUploadEngine != null) {
            pLVSUploadEngine.cancelUpload();
        }
    }

    public void setOnPreparedListener(OnPLVSDocumentUploadTaskPreparedListener onPLVSDocumentUploadTaskPreparedListener) {
        this.onPreparedListener = onPLVSDocumentUploadTaskPreparedListener;
        if (this.uploadEngine != null) {
            onPLVSDocumentUploadTaskPreparedListener.onPrepared();
        }
    }

    public void start() {
        PLVSUploadEngine pLVSUploadEngine = this.uploadEngine;
        if (pLVSUploadEngine != null) {
            pLVSUploadEngine.startUpload();
        } else {
            PLVCommonLog.w(TAG, "uploadEngine == null");
        }
    }
}
