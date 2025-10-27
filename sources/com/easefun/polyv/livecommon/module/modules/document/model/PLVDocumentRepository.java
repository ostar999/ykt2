package com.easefun.polyv.livecommon.module.modules.document.model;

import android.content.Context;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livescenes.document.PLVSDocumentWebProcessor;
import com.easefun.polyv.livescenes.document.model.PLVSPPTInfo;
import com.easefun.polyv.livescenes.document.model.PLVSPPTJsModel;
import com.easefun.polyv.livescenes.document.model.PLVSPPTPaintStatus;
import com.easefun.polyv.livescenes.document.model.PLVSPPTStatus;
import com.easefun.polyv.livescenes.upload.IPLVSDocumentUploadManager;
import com.easefun.polyv.livescenes.upload.OnPLVSDocumentUploadListener;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.document.PLVDocumentWebProcessor;
import com.plv.livescenes.document.model.PLVPPTInfo;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.ppt.PLVOnSliceIDEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import java.io.File;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class PLVDocumentRepository {
    private static final String TAG = "PLVDocumentRepository";

    @Nullable
    private IPLVSDocumentUploadManager documentUploadManager;
    private WeakReference<PLVSDocumentWebProcessor> documentWebProcessorWeakReference;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private final MutableLiveData<PLVStatefulData<PLVSPPTInfo>> plvsPptInfoLiveData = new MutableLiveData<>();
    private final MutableLiveData<PLVStatefulData<PLVSPPTJsModel>> plvsPptJsModelLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> documentZoomValueLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> refreshPptMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<PLVSPPTStatus> plvsPptStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<PLVSPPTPaintStatus> plvsPptPaintStatusLiveData = new MutableLiveData<>();
    private PLVSPPTInfo cachePptInfo = null;
    private SparseArray<PLVSPPTJsModel> cachePptJsModel = new SparseArray<>();

    public PLVDocumentRepository(PLVSDocumentWebProcessor documentWebProcessor) {
        this.documentWebProcessorWeakReference = new WeakReference<>(documentWebProcessor);
    }

    private void initMsgListener() {
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.document.model.PLVDocumentRepository.1
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                if ("onSliceStart".equals(event) || "onSliceDraw".equals(event) || "onSliceControl".equals(event) || "onSliceOpen".equals(event) || "onSliceID".equals(event)) {
                    PLVCommonLog.d(PLVDocumentRepository.TAG, "receive ppt message: delay" + message);
                    if ("teacher".equals(PLVDocumentRepository.this.liveRoomDataManager.getConfig().getUser().getViewerType()) && "onSliceID".equals(event)) {
                        return;
                    }
                    PLVDocumentWebProcessor pLVDocumentWebProcessor = (PLVDocumentWebProcessor) PLVDocumentRepository.this.documentWebProcessorWeakReference.get();
                    if (pLVDocumentWebProcessor != null) {
                        pLVDocumentWebProcessor.getWebview().callMessage("refreshPPT", message);
                    }
                }
                if ("onSliceID".equals(event) && PLVDocumentRepository.this.liveRoomDataManager.isNeedStreamRecover()) {
                    PLVDocumentRepository.this.updatePPTStatusByOnSliceID(message);
                }
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener);
    }

    private void initWebProcessor() {
        PLVSDocumentWebProcessor pLVSDocumentWebProcessor = this.documentWebProcessorWeakReference.get();
        if (pLVSDocumentWebProcessor == null) {
            return;
        }
        pLVSDocumentWebProcessor.getWebview().registerProcessor(pLVSDocumentWebProcessor);
        pLVSDocumentWebProcessor.registerJSHandler(new PLVDocumentWebProcessor.CloudClassJSCallback() { // from class: com.easefun.polyv.livecommon.module.modules.document.model.PLVDocumentRepository.2
            @Override // com.plv.livescenes.document.PLVDocumentWebProcessor.CloudClassJSCallback
            public void getEditContent(PLVSPPTPaintStatus content) {
                super.getEditContent(content);
                PLVDocumentRepository.this.plvsPptPaintStatusLiveData.postValue(content);
            }

            @Override // com.plv.livescenes.document.PLVDocumentWebProcessor.CloudClassJSCallback
            public void getPPTChangeStatus(PLVSPPTStatus plvspptStatus) {
                super.getPPTChangeStatus(plvspptStatus);
                PLVDocumentRepository.this.plvsPptStatusLiveData.postValue(plvspptStatus);
            }

            @Override // com.plv.livescenes.document.PLVDocumentWebProcessor.CloudClassJSCallback
            public void getPPTImagesList(PLVSPPTJsModel jsModel) {
                super.getPPTImagesList(jsModel);
                PLVDocumentRepository.this.cachePptJsModel.put(jsModel.getAutoId(), jsModel);
                PLVDocumentRepository.this.plvsPptJsModelLiveData.postValue(PLVStatefulData.success(jsModel));
            }

            @Override // com.plv.livescenes.document.PLVDocumentWebProcessor.CloudClassJSCallback
            public void getUser(CallBackFunction function) {
                super.getUser(function);
            }

            @Override // com.plv.livescenes.document.PLVDocumentWebProcessor.CloudClassJSCallback
            public void onZoomChange(String zoomValue) {
                PLVDocumentRepository.this.documentZoomValueLiveData.postValue(zoomValue);
            }

            @Override // com.plv.livescenes.document.PLVDocumentWebProcessor.CloudClassJSCallback
            public void refreshPPT(String message) {
                super.refreshPPT(message);
                PLVDocumentRepository.this.refreshPptMessageLiveData.postValue(message);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePPTStatusByOnSliceID(String message) {
        PLVOnSliceIDEvent pLVOnSliceIDEvent = (PLVOnSliceIDEvent) PLVGsonUtil.fromJson(PLVOnSliceIDEvent.class, message);
        if (pLVOnSliceIDEvent == null || pLVOnSliceIDEvent.getData() == null) {
            return;
        }
        PLVOnSliceIDEvent.DataBean data = pLVOnSliceIDEvent.getData();
        PLVSPPTStatus pLVSPPTStatus = new PLVSPPTStatus();
        pLVSPPTStatus.setAutoId(data.getAutoId());
        pLVSPPTStatus.setStep(PLVFormatUtils.integerValueOf(data.getStep(), 0).intValue());
        pLVSPPTStatus.setPageId(data.getPageId());
        this.plvsPptStatusLiveData.postValue(pLVSPPTStatus);
    }

    public void deleteDocument(final int autoId) {
        PLVDocumentNetRepo.getInstance().deleteDocument(autoId);
    }

    public void destroy() {
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        this.cachePptInfo = null;
        this.cachePptJsModel.clear();
        this.cachePptJsModel = null;
        PLVDocumentNetRepo.getInstance().destroy();
    }

    @Nullable
    public PLVSPPTInfo getCachePptCoverList() {
        return this.cachePptInfo;
    }

    public LiveData<String> getDocumentZoomValueLiveData() {
        return this.documentZoomValueLiveData;
    }

    public LiveData<PLVStatefulData<PLVSPPTInfo>> getPptInfoLiveData() {
        return PLVDocumentNetRepo.getInstance().getPptInfoLiveData();
    }

    public LiveData<PLVStatefulData<PLVSPPTJsModel>> getPptJsModelLiveData() {
        return this.plvsPptJsModelLiveData;
    }

    public LiveData<PLVStatefulData<PLVPPTInfo.DataBean.ContentsBean>> getPptOnDeleteResponseLiveData() {
        return PLVDocumentNetRepo.getInstance().getPptOnDeleteResponseLiveData();
    }

    public LiveData<PLVSPPTPaintStatus> getPptPaintStatusLiveData() {
        return this.plvsPptPaintStatusLiveData;
    }

    public LiveData<PLVSPPTStatus> getPptStatusLiveData() {
        return this.plvsPptStatusLiveData;
    }

    public LiveData<String> getRefreshPptMessageLiveData() {
        return this.refreshPptMessageLiveData;
    }

    public void init(IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
        initMsgListener();
        initWebProcessor();
    }

    public void requestGetCachedPptPageList(int autoId) {
        PLVSPPTJsModel pLVSPPTJsModel = this.cachePptJsModel.get(autoId);
        if (pLVSPPTJsModel != null) {
            this.plvsPptJsModelLiveData.postValue(PLVStatefulData.success(pLVSPPTJsModel));
        } else {
            this.plvsPptJsModelLiveData.postValue(PLVStatefulData.error("没有缓存对应的文档列表，请先打开该PPT文档"));
        }
    }

    public void requestPptCoverList() {
        PLVDocumentNetRepo.getInstance().requestPptCoverList();
    }

    public void sendWebMessage(String event, String message) {
        PLVCommonLog.d(TAG, "event=" + event + " msg=" + message);
        PLVSDocumentWebProcessor pLVSDocumentWebProcessor = this.documentWebProcessorWeakReference.get();
        if (pLVSDocumentWebProcessor == null) {
            return;
        }
        pLVSDocumentWebProcessor.getWebview().callMessage(event, message.replace("\n", ""));
    }

    public void uploadPptFile(final Context context, final File file, final String type, final OnPLVSDocumentUploadListener listener) {
        PLVDocumentNetRepo.getInstance().uploadPptFile(context, file, type, listener);
    }

    public void deleteDocument(final String fileId) {
        PLVDocumentNetRepo.getInstance().deleteDocument(fileId);
    }

    public void requestPptCoverList(boolean forceRefresh) {
        PLVDocumentNetRepo.getInstance().requestPptCoverList(forceRefresh);
    }
}
