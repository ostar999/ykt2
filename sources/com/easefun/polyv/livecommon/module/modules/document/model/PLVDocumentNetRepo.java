package com.easefun.polyv.livecommon.module.modules.document.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livescenes.document.model.PLVSPPTInfo;
import com.easefun.polyv.livescenes.document.model.PLVSPPTJsModel;
import com.easefun.polyv.livescenes.upload.IPLVSDocumentUploadManager;
import com.easefun.polyv.livescenes.upload.OnPLVSDocumentUploadListener;
import com.easefun.polyv.livescenes.upload.manager.PLVSDocumentUploadClient;
import com.easefun.polyv.livescenes.upload.manager.PLVSDocumentUploadManagerFactory;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.utils.PLVReflectionUtil;
import com.plv.livescenes.document.PLVDocumentDataManager;
import com.plv.livescenes.document.model.PLVPPTInfo;
import java.io.File;
import java.util.Iterator;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class PLVDocumentNetRepo {
    private static PLVDocumentNetRepo INSTANCE = null;
    private static final String TAG = "PLVDocumentNetRepo";

    @Nullable
    private IPLVSDocumentUploadManager documentUploadManager;
    protected final MutableLiveData<PLVStatefulData<PLVSPPTInfo>> plvsPptInfoLiveData = new MutableLiveData<>();
    protected final MutableLiveData<PLVStatefulData<PLVPPTInfo.DataBean.ContentsBean>> pptOnDeleteResponseLiveData = new MutableLiveData<>();
    private PLVSPPTInfo cachePptInfo = null;
    private SparseArray<PLVSPPTJsModel> cachePptJsModel = new SparseArray<>();

    private PLVDocumentNetRepo() {
        init();
    }

    public static PLVDocumentNetRepo getInstance() {
        if (INSTANCE == null) {
            synchronized (PLVDocumentNetRepo.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVDocumentNetRepo();
                }
            }
        }
        return INSTANCE;
    }

    private void init() {
        initDocumentUploadManager();
    }

    private void initDocumentUploadManager() {
        PLVSDocumentUploadClient pLVSDocumentUploadClientCreateDocumentUploadManager = PLVSDocumentUploadManagerFactory.createDocumentUploadManager();
        this.documentUploadManager = pLVSDocumentUploadClientCreateDocumentUploadManager;
        pLVSDocumentUploadClientCreateDocumentUploadManager.init();
    }

    public void deleteDocument(final int autoId) {
        final PLVPPTInfo.DataBean.ContentsBean next;
        PLVSPPTInfo pLVSPPTInfo = this.cachePptInfo;
        if (pLVSPPTInfo == null || pLVSPPTInfo.getData() == null || this.cachePptInfo.getData().getContents() == null) {
            Log.w(TAG, "delete document failed. ppt list is null.");
            return;
        }
        Iterator<PLVPPTInfo.DataBean.ContentsBean> it = this.cachePptInfo.getData().getContents().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (next.getAutoId() == autoId) {
                    break;
                }
            }
        }
        if (next == null) {
            Log.w(TAG, "delete document failed. ppt bean is null.");
        } else {
            PLVDocumentDataManager.delDocument(next, new PLVrResponseCallback<ResponseBody>() { // from class: com.easefun.polyv.livecommon.module.modules.document.model.PLVDocumentNetRepo.2
                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onError(Throwable e2) {
                    PLVDocumentNetRepo.this.pptOnDeleteResponseLiveData.postValue(PLVStatefulData.error(e2.getMessage(), e2));
                }

                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onFinish() {
                }

                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onSuccess(ResponseBody responseBody) {
                    PLVDocumentNetRepo.this.pptOnDeleteResponseLiveData.postValue(PLVStatefulData.success(next));
                }
            });
        }
    }

    public void destroy() {
        IPLVSDocumentUploadManager iPLVSDocumentUploadManager = this.documentUploadManager;
        if (iPLVSDocumentUploadManager != null) {
            iPLVSDocumentUploadManager.destroy();
            this.documentUploadManager = null;
        }
        this.cachePptInfo = null;
        SparseArray<PLVSPPTJsModel> sparseArray = this.cachePptJsModel;
        if (sparseArray != null) {
            sparseArray.clear();
            this.cachePptJsModel = null;
        }
    }

    @Nullable
    public PLVSPPTInfo getCachePptCoverList() {
        return this.cachePptInfo;
    }

    public LiveData<PLVStatefulData<PLVSPPTInfo>> getPptInfoLiveData() {
        return this.plvsPptInfoLiveData;
    }

    public LiveData<PLVStatefulData<PLVPPTInfo.DataBean.ContentsBean>> getPptOnDeleteResponseLiveData() {
        return this.pptOnDeleteResponseLiveData;
    }

    public void requestPptCoverList() {
        requestPptCoverList(false);
    }

    public void uploadPptFile(final Context context, final File file, final String type, final OnPLVSDocumentUploadListener listener) {
        if (this.documentUploadManager == null) {
            initDocumentUploadManager();
            if (this.documentUploadManager == null) {
                return;
            }
        }
        this.documentUploadManager.startPollingConvertStatus();
        this.documentUploadManager.addUploadTask(context, type, file, listener);
    }

    public void requestPptCoverList(boolean forceRefresh) {
        PLVSPPTInfo pLVSPPTInfo;
        if (forceRefresh || (pLVSPPTInfo = this.cachePptInfo) == null) {
            PLVDocumentDataManager.getDocumentList(new PLVrResponseCallback<PLVPPTInfo>() { // from class: com.easefun.polyv.livecommon.module.modules.document.model.PLVDocumentNetRepo.1
                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onFinish() {
                }

                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onSuccess(PLVPPTInfo plvspptInfo) {
                    PLVDocumentNetRepo.this.cachePptInfo = (PLVSPPTInfo) PLVReflectionUtil.copyField(plvspptInfo, new PLVSPPTInfo());
                    PLVDocumentNetRepo pLVDocumentNetRepo = PLVDocumentNetRepo.this;
                    pLVDocumentNetRepo.plvsPptInfoLiveData.postValue(PLVStatefulData.success(pLVDocumentNetRepo.cachePptInfo));
                }
            });
        } else {
            this.plvsPptInfoLiveData.postValue(PLVStatefulData.success(pLVSPPTInfo));
        }
    }

    public void deleteDocument(final String fileId) {
        final PLVPPTInfo.DataBean.ContentsBean next;
        PLVSPPTInfo pLVSPPTInfo = this.cachePptInfo;
        if (pLVSPPTInfo != null && pLVSPPTInfo.getData() != null && this.cachePptInfo.getData().getContents() != null) {
            if (TextUtils.isEmpty(fileId)) {
                Log.w(TAG, "delete document failed. fileId is empty.");
                return;
            }
            Iterator<PLVPPTInfo.DataBean.ContentsBean> it = this.cachePptInfo.getData().getContents().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (fileId.equalsIgnoreCase(next.getFileId())) {
                        break;
                    }
                }
            }
            if (next == null) {
                Log.w(TAG, "delete document failed. ppt bean is null.");
                return;
            } else {
                PLVDocumentDataManager.delDocument(next, new PLVrResponseCallback<ResponseBody>() { // from class: com.easefun.polyv.livecommon.module.modules.document.model.PLVDocumentNetRepo.3
                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onError(Throwable e2) {
                        PLVDocumentNetRepo.this.pptOnDeleteResponseLiveData.postValue(PLVStatefulData.error(e2.getMessage(), e2));
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onFinish() {
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onSuccess(ResponseBody responseBody) {
                        PLVDocumentNetRepo.this.pptOnDeleteResponseLiveData.postValue(PLVStatefulData.success(next));
                    }
                });
                return;
            }
        }
        Log.w(TAG, "delete document failed. ppt list is null.");
    }
}
