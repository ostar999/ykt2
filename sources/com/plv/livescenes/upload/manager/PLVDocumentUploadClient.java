package com.plv.livescenes.upload.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.IPLVErrorCodeSender;
import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.foundationsdk.log.elog.PLVErrorCodeSender;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.log.elog.logcode.ppt.PLVErrorCodePPTRequest;
import com.plv.foundationsdk.log.elog.logcode.upload.PLVErrorCodeUploadProcessError;
import com.plv.foundationsdk.log.elog.logcode.upload.PLVErrorCodeUploadSDKInit;
import com.plv.foundationsdk.log.elog.logcode.upload.PLVErrorCodeUploadToken;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.livescenes.document.PLVDocumentDataManager;
import com.plv.livescenes.document.model.PLVPPTInfo;
import com.plv.livescenes.hiclass.PLVHiClassGlobalConfig;
import com.plv.livescenes.log.PLVElogEntityCreator;
import com.plv.livescenes.log.upload.PLVDocumentUploadELog;
import com.plv.livescenes.upload.IPLVDocumentUploadManager;
import com.plv.livescenes.upload.OnPLVDocumentUploadListener;
import com.plv.livescenes.upload.PLVDocumentUploadConstant;
import com.plv.livescenes.upload.manager.PLVDocumentUploadException;
import com.plv.livescenes.upload.manager.PLVDocumentUploadTask;
import com.plv.livescenes.upload.model.PLVPPTConvertStatusVO;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import com.plv.thirdpart.blankj.utilcode.util.FileUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes5.dex */
public class PLVDocumentUploadClient implements IPLVDocumentUploadManager {
    private static final int ERROR_FILE_FORMAT_NOT_SUPPORT = 100;
    private static final int ERROR_NOT_INIT_SDK = 101;
    private static final int MAX_RECURSION_NUMBER = 20;
    private static final int MAX_TASKS = 3;
    private static final int POLLING_INTERVAL = 5000;
    private static final String TAG = "PLVDocumentUploadClient";
    private Disposable getConvertStatusDisposable;
    private Disposable pollingTimerDisposable;
    private ConcurrentHashMap<PLVPPTInfo.DataBean.ContentsBean, PLVDocumentUploadTask> uploaderMaps = new ConcurrentHashMap<>(3);
    private ConcurrentHashMap<PLVPPTInfo.DataBean.ContentsBean, OnPLVDocumentUploadListener> fileIdsToPollingStatus = new ConcurrentHashMap<>(3);
    private PLVDocumentDataSource dataSource = new PLVDocumentDataSource();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private IPLVErrorCodeSender errorCodeSender = new PLVErrorCodeSender();

    /* renamed from: com.plv.livescenes.upload.manager.PLVDocumentUploadClient$6, reason: invalid class name */
    public class AnonymousClass6 implements Consumer<Integer> {
        final /* synthetic */ Context val$context;
        final /* synthetic */ OnPLVDocumentUploadListener val$listener;
        final /* synthetic */ String val$type;
        final /* synthetic */ File val$uploadFile;

        public AnonymousClass6(Context context, File file, OnPLVDocumentUploadListener onPLVDocumentUploadListener, String str) {
            this.val$context = context;
            this.val$uploadFile = file;
            this.val$listener = onPLVDocumentUploadListener;
            this.val$type = str;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Integer num) throws Exception {
            try {
                File fileEnsureFileLocatedInScopedStorageOfPlv = PLVDocumentUploadClient.this.ensureFileLocatedInScopedStorageOfPlv(this.val$context, this.val$uploadFile);
                String name = fileEnsureFileLocatedInScopedStorageOfPlv.getName();
                String lowerCase = EncryptUtils.encryptMD5File2String(fileEnsureFileLocatedInScopedStorageOfPlv).toLowerCase();
                String absolutePath = fileEnsureFileLocatedInScopedStorageOfPlv.getAbsolutePath();
                String str = lowerCase + PolyvLiveSDKClient.getInstance().getChannelId() + this.val$type;
                final PLVPPTInfo.DataBean.ContentsBean contentsBean = new PLVPPTInfo.DataBean.ContentsBean();
                PLVDocumentUploadClient.this.initItemData(str, fileEnsureFileLocatedInScopedStorageOfPlv, contentsBean);
                final PLVDocumentUploadTask pLVDocumentUploadTask = new PLVDocumentUploadTask(this.val$context, name, this.val$type, str, absolutePath, PLVDocumentUploadClient.this.dataSource, new PLVDocumentUploadTask.OnPLVSDocumentUploadTaskListener() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.6.1
                    @Override // com.plv.livescenes.upload.manager.PLVDocumentUploadTask.OnPLVSDocumentUploadTaskListener
                    public void onDocumentConverting() {
                        PLVDocumentUploadClient.this.mainHandler.post(new Runnable() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.6.1.4
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                AnonymousClass6.this.val$listener.onDocumentConverting(contentsBean);
                                PLVDocumentUploadClient.this.uploaderMaps.remove(contentsBean);
                                ConcurrentHashMap concurrentHashMap = PLVDocumentUploadClient.this.fileIdsToPollingStatus;
                                AnonymousClass1 anonymousClass12 = AnonymousClass1.this;
                                concurrentHashMap.put(contentsBean, AnonymousClass6.this.val$listener);
                            }
                        });
                    }

                    @Override // com.plv.livescenes.upload.manager.PLVDocumentUploadTask.OnPLVSDocumentUploadTaskListener
                    public void onDocumentExist() {
                        PLVDocumentUploadClient.this.mainHandler.post(new Runnable() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.6.1.3
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                AnonymousClass6.this.val$listener.onDocumentExist(contentsBean);
                                AnonymousClass1 anonymousClass12 = AnonymousClass1.this;
                                PLVDocumentUploadClient pLVDocumentUploadClient = PLVDocumentUploadClient.this;
                                PLVPPTInfo.DataBean.ContentsBean contentsBean2 = contentsBean;
                                pLVDocumentUploadClient.notifyUploadProcessError(contentsBean2, 3, contentsBean2.toString(), new Throwable(), AnonymousClass6.this.val$listener);
                                AnonymousClass1 anonymousClass13 = AnonymousClass1.this;
                                PLVDocumentUploadClient.this.deleteUploadFileInScopedStorage(contentsBean);
                                PLVDocumentUploadClient.this.uploaderMaps.remove(contentsBean);
                            }
                        });
                    }

                    @Override // com.plv.livescenes.upload.manager.PLVDocumentUploadTask.OnPLVSDocumentUploadTaskListener
                    public void onFail(final PLVDocumentUploadException pLVDocumentUploadException) {
                        PLVDocumentUploadClient.this.mainHandler.post(new Runnable() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.6.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                int i2 = AnonymousClass9.$SwitchMap$com$plv$livescenes$upload$manager$PLVDocumentUploadException$Type[pLVDocumentUploadException.getType().ordinal()];
                                if (i2 == 1) {
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    PLVDocumentUploadClient.this.notifyTokenError(contentsBean, pLVDocumentUploadException.getCode(), pLVDocumentUploadException.getMessage(), new Throwable(pLVDocumentUploadException.getCause()), AnonymousClass6.this.val$listener);
                                } else if (i2 == 2) {
                                    AnonymousClass1 anonymousClass12 = AnonymousClass1.this;
                                    PLVDocumentUploadClient.this.notifyUploadProcessError(contentsBean, pLVDocumentUploadException.getCode(), pLVDocumentUploadException.getMessage(), new Throwable(pLVDocumentUploadException.getCause()), AnonymousClass6.this.val$listener);
                                }
                                PLVDocumentUploadClient.this.uploaderMaps.remove(contentsBean);
                            }
                        });
                    }

                    @Override // com.plv.livescenes.upload.manager.PLVDocumentUploadTask.OnPLVSDocumentUploadTaskListener
                    public void onProgress(final long j2, final long j3) {
                        PLVDocumentUploadClient.this.mainHandler.post(new Runnable() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.6.1.5
                            @Override // java.lang.Runnable
                            public void run() {
                                int i2 = (int) ((j2 / j3) * 100.0f);
                                contentsBean.setProgress(i2);
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                AnonymousClass6.this.val$listener.onUploadProgress(contentsBean, i2);
                            }
                        });
                    }

                    @Override // com.plv.livescenes.upload.manager.PLVDocumentUploadTask.OnPLVSDocumentUploadTaskListener
                    public void onSuccess() {
                        PLVDocumentUploadClient.this.mainHandler.post(new Runnable() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.6.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                AnonymousClass6.this.val$listener.onUploadSuccess(contentsBean);
                                PLVDocumentUploadClient.this.uploaderMaps.remove(contentsBean);
                                ConcurrentHashMap concurrentHashMap = PLVDocumentUploadClient.this.fileIdsToPollingStatus;
                                AnonymousClass1 anonymousClass12 = AnonymousClass1.this;
                                concurrentHashMap.put(contentsBean, AnonymousClass6.this.val$listener);
                                AnonymousClass1 anonymousClass13 = AnonymousClass1.this;
                                PLVDocumentUploadClient.this.sendTrackLog(PLVDocumentUploadELog.DocumentUploadEvent.UPLOAD_SUCCESS, contentsBean.toString());
                            }
                        });
                    }
                });
                pLVDocumentUploadTask.setOnPreparedListener(new PLVDocumentUploadTask.OnPLVSDocumentUploadTaskPreparedListener() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.6.2
                    @Override // com.plv.livescenes.upload.manager.PLVDocumentUploadTask.OnPLVSDocumentUploadTaskPreparedListener
                    public void onPrepared() {
                        PLVDocumentUploadClient.this.mainHandler.post(new Runnable() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.6.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                AnonymousClass6.this.val$listener.onPrepared(contentsBean);
                            }
                        });
                        pLVDocumentUploadTask.start();
                        PLVDocumentUploadClient.this.sendTrackLog(PLVDocumentUploadELog.DocumentUploadEvent.UPLOAD_BEGIN, contentsBean.toString());
                    }
                });
                PLVDocumentUploadClient.this.uploaderMaps.put(contentsBean, pLVDocumentUploadTask);
            } catch (IOException e2) {
                PLVDocumentUploadClient.this.notifyUploadProcessError(null, 1, e2.getMessage(), e2, this.val$listener);
            }
        }
    }

    /* renamed from: com.plv.livescenes.upload.manager.PLVDocumentUploadClient$7, reason: invalid class name */
    public class AnonymousClass7 implements Consumer<Long> {
        public AnonymousClass7() {
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Long l2) throws Exception {
            if (PLVDocumentUploadClient.this.checkInit() != null) {
                PLVDocumentUploadClient.this.fileIdsToPollingStatus.clear();
                return;
            }
            PLVDocumentUploadClient pLVDocumentUploadClient = PLVDocumentUploadClient.this;
            pLVDocumentUploadClient.dispose(pLVDocumentUploadClient.getConvertStatusDisposable);
            if (PLVDocumentUploadClient.this.fileIdsToPollingStatus.isEmpty()) {
                return;
            }
            ArrayList arrayList = new ArrayList(PLVDocumentUploadClient.this.fileIdsToPollingStatus.size());
            Iterator it = PLVDocumentUploadClient.this.fileIdsToPollingStatus.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add(((PLVPPTInfo.DataBean.ContentsBean) it.next()).getFileId());
            }
            PLVDocumentUploadClient pLVDocumentUploadClient2 = PLVDocumentUploadClient.this;
            pLVDocumentUploadClient2.getConvertStatusDisposable = pLVDocumentUploadClient2.dataSource.getDocumentUploadConvertStatus(arrayList).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<PLVPPTConvertStatusVO>>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.7.1
                @Override // io.reactivex.functions.Consumer
                public void accept(List<PLVPPTConvertStatusVO> list) throws Exception {
                    if (PLVDocumentUploadClient.this.checkInit() != null) {
                        PLVDocumentUploadClient.this.fileIdsToPollingStatus.clear();
                        return;
                    }
                    for (PLVPPTConvertStatusVO pLVPPTConvertStatusVO : list) {
                        final String fileId = pLVPPTConvertStatusVO.getFileId();
                        String convertStatus = pLVPPTConvertStatusVO.getConvertStatus();
                        final Iterator it2 = PLVDocumentUploadClient.this.fileIdsToPollingStatus.entrySet().iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                Map.Entry entry = (Map.Entry) it2.next();
                                final PLVPPTInfo.DataBean.ContentsBean contentsBean = (PLVPPTInfo.DataBean.ContentsBean) entry.getKey();
                                final OnPLVDocumentUploadListener onPLVDocumentUploadListener = (OnPLVDocumentUploadListener) entry.getValue();
                                if (fileId.equals(contentsBean.getFileId())) {
                                    convertStatus.hashCode();
                                    switch (convertStatus) {
                                        case "normal":
                                            PLVDocumentDataManager.getDocumentList(new PLVrResponseCallback<PLVPPTInfo>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.7.1.1
                                                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                                                public void onError(Throwable th) {
                                                    super.onError(th);
                                                }

                                                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                                                public void onFailure(PLVResponseBean<PLVPPTInfo> pLVResponseBean) {
                                                    super.onFailure(pLVResponseBean);
                                                }

                                                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                                                public void onFinish() {
                                                }

                                                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                                                public void onSuccess(PLVPPTInfo pLVPPTInfo) {
                                                    for (PLVPPTInfo.DataBean.ContentsBean contentsBean2 : pLVPPTInfo.getData().getContents()) {
                                                        if (fileId.equalsIgnoreCase(contentsBean2.getFileId())) {
                                                            contentsBean.setStatus(contentsBean2.getStatus());
                                                            contentsBean.setTotalPage(contentsBean2.getTotalPage());
                                                            contentsBean.setAutoId(contentsBean2.getAutoId());
                                                            contentsBean.setFileType(contentsBean2.getFileType());
                                                            contentsBean.setChannelId(contentsBean2.getChannelId());
                                                            contentsBean.setPreviewImage(contentsBean2.getPreviewImage());
                                                            contentsBean.setPreviewBigImage(contentsBean2.getPreviewBigImage());
                                                            contentsBean.setFileName(contentsBean2.getFileName());
                                                            contentsBean.setConvertType(contentsBean2.getConvertType());
                                                            contentsBean.setFileUrl(contentsBean2.getFileUrl());
                                                            contentsBean.setType(contentsBean2.getType());
                                                            contentsBean.setFileId(contentsBean2.getFileId());
                                                            onPLVDocumentUploadListener.onConvertSuccess(contentsBean);
                                                            PLVDocumentUploadClient.this.deleteUploadFileInScopedStorage(contentsBean);
                                                            it2.remove();
                                                            return;
                                                        }
                                                    }
                                                }
                                            });
                                            break;
                                        case "failUpload":
                                            PLVDocumentUploadClient.this.notifyUploadProcessError(contentsBean, 4, "轮询文档状态查到的上传失败", new Throwable(), onPLVDocumentUploadListener);
                                            it2.remove();
                                            break;
                                        case "failConvert":
                                            it2.remove();
                                            PLVDocumentUploadClient.this.notifyDocumentConvertError(contentsBean, 9, "文档转码失败", new Throwable(), onPLVDocumentUploadListener);
                                            PLVDocumentUploadClient.this.deleteUploadFileInScopedStorage(contentsBean);
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }, new Consumer<Throwable>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.7.2
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                    PLVDocumentUploadClient.this.notifyDocumentConvertError(null, 8, th.getMessage(), new Throwable(), null);
                }
            });
        }
    }

    /* renamed from: com.plv.livescenes.upload.manager.PLVDocumentUploadClient$9, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$com$plv$livescenes$upload$manager$PLVDocumentUploadException$Type;

        static {
            int[] iArr = new int[PLVDocumentUploadException.Type.values().length];
            $SwitchMap$com$plv$livescenes$upload$manager$PLVDocumentUploadException$Type = iArr;
            try {
                iArr[PLVDocumentUploadException.Type.TYPE_TOKEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$plv$livescenes$upload$manager$PLVDocumentUploadException$Type[PLVDocumentUploadException.Type.TYPE_UPLOAD_PROCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Throwable checkInit() {
        if (initCheckForLive() || initCheckForHiClass()) {
            return null;
        }
        Throwable th = new Throwable();
        this.errorCodeSender.submitError(PLVErrorCodeUploadSDKInit.class, 1, "", th);
        return th;
    }

    private boolean checkIsFileValidate(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        String[] strArr = {"pptx", "ppt", "pdf", "doc", "docx", "xls", "xlsx", "wps", "jpg", "jpeg", "png"};
        boolean z2 = false;
        for (int i2 = 0; i2 < 11; i2++) {
            if (file.getName().endsWith(strArr[i2])) {
                z2 = true;
            }
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteUploadFileInScopedStorage(PLVPPTInfo.DataBean.ContentsBean contentsBean) {
        String filePath = contentsBean.getFilePath();
        FileUtils.deleteFile(filePath);
        PLVCommonLog.d(TAG, "deleteUploadFileInScopedStorage:" + filePath);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public File ensureFileLocatedInScopedStorageOfPlv(Context context, File file) throws IOException {
        return ensureFileLocatedInScopedStorageOfPlv(context, file, 0);
    }

    private boolean initCheckForHiClass() {
        return (TextUtils.isEmpty(PLVHiClassGlobalConfig.getToken()) || PLVHiClassGlobalConfig.getLessonId() == 0) ? false : true;
    }

    private boolean initCheckForLive() {
        return (TextUtils.isEmpty(PolyvLiveSDKClient.getInstance().getAppId()) || TextUtils.isEmpty(PolyvLiveSDKClient.getInstance().getChannelId()) || TextUtils.isEmpty(PolyvLiveSDKClient.getInstance().getAppSecret())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initItemData(String str, File file, PLVPPTInfo.DataBean.ContentsBean contentsBean) {
        contentsBean.setFileId(str);
        int iLastIndexOf = file.getName().lastIndexOf(StrPool.DOT);
        String strSubstring = file.getName().substring(iLastIndexOf);
        String strSubstring2 = file.getName().substring(0, iLastIndexOf);
        contentsBean.setFileType(strSubstring);
        contentsBean.setFileName(strSubstring2);
        contentsBean.setFilePath(file.getAbsolutePath());
        contentsBean.setStatus(PLVDocumentUploadConstant.ConvertStatus.UPLOADING_LOCAL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDocumentConvertError(PLVPPTInfo.DataBean.ContentsBean contentsBean, int i2, String str, Throwable th, @Nullable OnPLVDocumentUploadListener onPLVDocumentUploadListener) {
        IPLVErrorCodeSender.SubmitResult submitResultSubmitError = this.errorCodeSender.submitError(PLVErrorCodePPTRequest.class, i2, str, th);
        if (onPLVDocumentUploadListener != null) {
            onPLVDocumentUploadListener.onConvertFailed(contentsBean, submitResultSubmitError.getIntactErrorCode(), th.getMessage(), new Throwable(submitResultSubmitError.getCounterPartMsgOfCode(), th));
        }
    }

    private void notifyError(PLVPPTInfo.DataBean.ContentsBean contentsBean, int i2, String str, Throwable th, Class<? extends PLVErrorCodeInfoBase> cls, OnPLVDocumentUploadListener onPLVDocumentUploadListener) {
        IPLVErrorCodeSender.SubmitResult submitResultSubmitError = this.errorCodeSender.submitError(cls, i2, str, th);
        if (onPLVDocumentUploadListener != null) {
            onPLVDocumentUploadListener.onUploadFailed(contentsBean, submitResultSubmitError.getIntactErrorCode(), th.getMessage(), new Throwable(submitResultSubmitError.getCounterPartMsgOfCode(), th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTokenError(PLVPPTInfo.DataBean.ContentsBean contentsBean, int i2, String str, Throwable th, OnPLVDocumentUploadListener onPLVDocumentUploadListener) {
        notifyError(contentsBean, i2, str, th, PLVErrorCodeUploadToken.class, onPLVDocumentUploadListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyUploadProcessError(PLVPPTInfo.DataBean.ContentsBean contentsBean, int i2, String str, Throwable th, OnPLVDocumentUploadListener onPLVDocumentUploadListener) {
        notifyError(contentsBean, i2, str, th, PLVErrorCodeUploadProcessError.class, onPLVDocumentUploadListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTrackLog(String str, String str2) {
        PLVELogsService.getInstance().addStaticsLog(PLVDocumentUploadELog.class, str, str2, new String[0]);
    }

    @Override // com.plv.livescenes.upload.IPLVDocumentUploadManager
    public void addUploadTask(Context context, String str, File file, final OnPLVDocumentUploadListener onPLVDocumentUploadListener) {
        if (onPLVDocumentUploadListener == null) {
            return;
        }
        final Throwable thCheckInit = checkInit();
        if (thCheckInit != null) {
            this.mainHandler.post(new Runnable() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.2
                @Override // java.lang.Runnable
                public void run() {
                    onPLVDocumentUploadListener.onUploadFailed(null, 101, thCheckInit.getMessage(), thCheckInit);
                }
            });
        } else {
            if (this.uploaderMaps.size() >= 3) {
                return;
            }
            if (checkIsFileValidate(file)) {
                Observable.just(1).observeOn(Schedulers.io()).doOnNext(new AnonymousClass6(context, file, onPLVDocumentUploadListener, str)).subscribe(new Consumer<Integer>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.4
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Integer num) throws Exception {
                    }
                }, new Consumer<Throwable>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.5
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Throwable th) throws Exception {
                    }
                });
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.3
                    @Override // java.lang.Runnable
                    public void run() {
                        onPLVDocumentUploadListener.onUploadFailed(null, 100, "文档格式不支持", new Throwable());
                    }
                });
            }
        }
    }

    @Override // com.plv.livescenes.upload.IPLVDocumentUploadManager
    public void destroy() {
        Iterator<PLVDocumentUploadTask> it = this.uploaderMaps.values().iterator();
        while (it.hasNext()) {
            it.next().cancel();
        }
        this.uploaderMaps.clear();
        this.fileIdsToPollingStatus.clear();
        stopPollingConvertStatus();
        dispose(this.pollingTimerDisposable);
    }

    @Override // com.plv.livescenes.upload.IPLVDocumentUploadManager
    public void init() {
        this.errorCodeSender.setElogVOCreator(new IPLVErrorCodeSender.ELogVOCreator() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.1
            @Override // com.plv.foundationsdk.log.elog.IPLVErrorCodeSender.ELogVOCreator
            public <T extends PLVErrorCodeInfoBase> PLVStatisticsBase createElogVO(Class<T> cls, int i2, String str, PLVLogFileBase pLVLogFileBase) {
                return PLVElogEntityCreator.createLiveEntity(cls, i2, str, pLVLogFileBase);
            }
        });
    }

    @Override // com.plv.livescenes.upload.IPLVDocumentUploadManager
    public void removeUploadTask(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (PLVPPTInfo.DataBean.ContentsBean contentsBean : this.uploaderMaps.keySet()) {
            if (str.equals(contentsBean.getFileId())) {
                deleteUploadFileInScopedStorage(contentsBean);
                PLVDocumentUploadTask pLVDocumentUploadTaskRemove = this.uploaderMaps.remove(contentsBean);
                if (pLVDocumentUploadTaskRemove != null) {
                    pLVDocumentUploadTaskRemove.cancel();
                    return;
                }
                return;
            }
        }
    }

    @Override // com.plv.livescenes.upload.IPLVDocumentUploadManager
    public void startPollingConvertStatus() {
        dispose(this.pollingTimerDisposable);
        this.pollingTimerDisposable = PLVRxTimer.timer(5000, new AnonymousClass7());
    }

    @Override // com.plv.livescenes.upload.IPLVDocumentUploadManager
    public void stopPollingConvertStatus() {
        dispose(this.pollingTimerDisposable);
        dispose(this.getConvertStatusDisposable);
    }

    @WorkerThread
    private File ensureFileLocatedInScopedStorageOfPlv(Context context, File file, int i2) throws IOException {
        String str = TAG;
        PLVCommonLog.d(str, "ensureFileLocatedInScopedStorageOfPlv recurse " + i2 + " times");
        if (i2 == 20) {
            return file;
        }
        File file2 = new File(context.getCacheDir(), "polyv-document-upload-root");
        if (!FileUtils.createOrExistsDir(file2)) {
            throw new IOException(file2.getAbsolutePath() + "无效");
        }
        String name = file.getName();
        if (i2 > 0) {
            int iIndexOf = name.indexOf(StrPool.DOT);
            name = name.substring(0, iIndexOf) + "$" + i2 + name.substring(iIndexOf);
        }
        File file3 = new File(file2, name);
        if (file3.exists()) {
            return EncryptUtils.encryptMD5File2String(file3).equals(EncryptUtils.encryptMD5File2String(file)) ? file3 : ensureFileLocatedInScopedStorageOfPlv(context, file, i2 + 1);
        }
        if (FileUtils.copyFile(file, file3, new FileUtils.OnReplaceListener() { // from class: com.plv.livescenes.upload.manager.PLVDocumentUploadClient.8
            @Override // com.plv.thirdpart.blankj.utilcode.util.FileUtils.OnReplaceListener
            public boolean onReplace() {
                return false;
            }
        })) {
            PLVCommonLog.d(str, file + " --> " + file3 + " 拷贝成功");
            return file3;
        }
        PLVCommonLog.e(str, file + " --> " + file3 + " 拷贝失败");
        return file;
    }
}
