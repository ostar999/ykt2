package com.easefun.polyv.livecommon.module.modules.document.presenter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract;
import com.easefun.polyv.livecommon.module.modules.document.model.PLVDocumentNetRepo;
import com.easefun.polyv.livecommon.module.modules.document.model.PLVPptUploadLocalRepository;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVPptUploadStatus;
import com.easefun.polyv.livecommon.module.modules.document.model.vo.PLVPptUploadLocalCacheVO;
import com.easefun.polyv.livescenes.document.model.PLVSPPTInfo;
import com.easefun.polyv.livescenes.upload.OnPLVSDocumentUploadListener;
import com.plv.livescenes.document.model.PLVPPTInfo;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVDocumentNetPresenter {
    private static PLVDocumentNetPresenter INSTANCE = null;
    private static final String TAG = "PLVDocumentNetPresenter";
    private PLVDocumentNetRepo plvDocumentRepository;

    @Nullable
    private PLVPptUploadLocalRepository plvPptUploadLocalRepository;
    private Observer<PLVStatefulData<PLVSPPTInfo>> pptInfoObserver;
    private Observer<PLVStatefulData<PLVPPTInfo.DataBean.ContentsBean>> pptOnDeleteObserver;
    private boolean isInitialized = false;
    private final List<WeakReference<IPLVDocumentContract.View>> viewWeakReferenceList = new ArrayList();

    private PLVDocumentNetPresenter() {
        init();
    }

    private boolean checkInitialized() {
        if (!this.isInitialized || this.plvPptUploadLocalRepository == null || this.plvDocumentRepository == null) {
            Log.w(TAG, "Call PLVLSDocumentPresenter.init() first!");
        }
        return this.isInitialized;
    }

    public static PLVDocumentNetPresenter getInstance() {
        if (INSTANCE == null) {
            synchronized (PLVDocumentNetPresenter.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVDocumentNetPresenter();
                }
            }
        }
        return INSTANCE;
    }

    @Nullable
    private PLVPPTInfo.DataBean.ContentsBean getPptContentsBeanFromAutoId(int autoId) {
        if (!checkInitialized()) {
            return null;
        }
        PLVSPPTInfo cachePptCoverList = this.plvDocumentRepository.getCachePptCoverList();
        if (cachePptCoverList == null || cachePptCoverList.getData() == null || cachePptCoverList.getData().getContents() == null) {
            Log.w(TAG, "cache ppt cover list is null.");
            return null;
        }
        for (PLVPPTInfo.DataBean.ContentsBean contentsBean : cachePptCoverList.getData().getContents()) {
            if (contentsBean.getAutoId() == autoId) {
                return contentsBean;
            }
        }
        return null;
    }

    private void init() {
        initRepository();
        observePptInfo();
        observePptOnDeleteResponse();
        this.isInitialized = true;
    }

    private void initRepository() {
        this.plvDocumentRepository = PLVDocumentNetRepo.getInstance();
        this.plvPptUploadLocalRepository = new PLVPptUploadLocalRepository();
    }

    private void observePptInfo() {
        LiveData<PLVStatefulData<PLVSPPTInfo>> pptInfoLiveData = this.plvDocumentRepository.getPptInfoLiveData();
        Observer<PLVStatefulData<PLVSPPTInfo>> observer = new Observer<PLVStatefulData<PLVSPPTInfo>>() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentNetPresenter.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<PLVSPPTInfo> plvspptInfo) {
                if (plvspptInfo == null || !plvspptInfo.isSuccess()) {
                    return;
                }
                Iterator it = PLVDocumentNetPresenter.this.viewWeakReferenceList.iterator();
                while (it.hasNext()) {
                    IPLVDocumentContract.View view = (IPLVDocumentContract.View) ((WeakReference) it.next()).get();
                    if (view != null) {
                        view.onPptCoverList(plvspptInfo.getData());
                    }
                }
            }
        };
        this.pptInfoObserver = observer;
        pptInfoLiveData.observeForever(observer);
    }

    private void observePptOnDeleteResponse() {
        LiveData<PLVStatefulData<PLVPPTInfo.DataBean.ContentsBean>> pptOnDeleteResponseLiveData = this.plvDocumentRepository.getPptOnDeleteResponseLiveData();
        Observer<PLVStatefulData<PLVPPTInfo.DataBean.ContentsBean>> observer = new Observer<PLVStatefulData<PLVPPTInfo.DataBean.ContentsBean>>() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentNetPresenter.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<PLVPPTInfo.DataBean.ContentsBean> response) {
                if (response == null) {
                    return;
                }
                Iterator it = PLVDocumentNetPresenter.this.viewWeakReferenceList.iterator();
                while (it.hasNext()) {
                    IPLVDocumentContract.View view = (IPLVDocumentContract.View) ((WeakReference) it.next()).get();
                    if (view != null) {
                        view.onPptDelete(response.isSuccess(), response.getData());
                    }
                }
            }
        };
        this.pptOnDeleteObserver = observer;
        pptOnDeleteResponseLiveData.observeForever(observer);
    }

    public void checkUploadFileStatus() {
        IPLVDocumentContract.View view;
        if (checkInitialized()) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (PLVPptUploadLocalCacheVO pLVPptUploadLocalCacheVO : this.plvPptUploadLocalRepository.listCache()) {
                if (pLVPptUploadLocalCacheVO.getStatus() == null) {
                    this.plvPptUploadLocalRepository.removeCache(pLVPptUploadLocalCacheVO.getFileId());
                } else {
                    if (!PLVPptUploadStatus.isStatusUploadSuccess(pLVPptUploadLocalCacheVO.getStatus())) {
                        if (new File(pLVPptUploadLocalCacheVO.getFilePath()).exists()) {
                            arrayList.add(pLVPptUploadLocalCacheVO);
                        } else {
                            Log.i(TAG, "上次上传失败的文件已经不存在");
                            this.plvPptUploadLocalRepository.removeCache(pLVPptUploadLocalCacheVO.getFileId());
                        }
                    }
                    if (7 == pLVPptUploadLocalCacheVO.getStatus().intValue()) {
                        arrayList2.add(pLVPptUploadLocalCacheVO);
                    } else {
                        this.plvPptUploadLocalRepository.removeCache(pLVPptUploadLocalCacheVO.getFileId());
                    }
                }
            }
            if (arrayList.size() > 0) {
                Iterator<WeakReference<IPLVDocumentContract.View>> it = this.viewWeakReferenceList.iterator();
                while (it.hasNext() && ((view = it.next().get()) == null || !view.notifyFileUploadNotSuccess(arrayList))) {
                }
            }
            if (arrayList2.size() > 0) {
                Iterator<WeakReference<IPLVDocumentContract.View>> it2 = this.viewWeakReferenceList.iterator();
                while (it2.hasNext()) {
                    IPLVDocumentContract.View view2 = it2.next().get();
                    if (view2 != null && view2.notifyFileConvertAnimateLoss(arrayList2)) {
                        return;
                    }
                }
            }
        }
    }

    public void deleteDocument(int autoId) {
        if (checkInitialized()) {
            this.plvDocumentRepository.deleteDocument(autoId);
        }
    }

    public void destroy() {
        PLVDocumentNetRepo pLVDocumentNetRepo = this.plvDocumentRepository;
        if (pLVDocumentNetRepo != null) {
            if (this.pptInfoObserver != null) {
                pLVDocumentNetRepo.getPptInfoLiveData().removeObserver(this.pptInfoObserver);
            }
            if (this.pptOnDeleteObserver != null) {
                this.plvDocumentRepository.getPptOnDeleteResponseLiveData().removeObserver(this.pptOnDeleteObserver);
            }
            this.plvDocumentRepository.destroy();
        }
        this.isInitialized = false;
        this.viewWeakReferenceList.clear();
        INSTANCE = null;
    }

    public void onSelectUploadFile(Uri fileUri) {
        Iterator<WeakReference<IPLVDocumentContract.View>> it = this.viewWeakReferenceList.iterator();
        while (it.hasNext()) {
            IPLVDocumentContract.View view = it.next().get();
            if (view != null && view.requestSelectUploadFileConvertType(fileUri)) {
                return;
            }
        }
    }

    public void registerView(IPLVDocumentContract.View view) {
        this.viewWeakReferenceList.add(new WeakReference<>(view));
    }

    public void removeUploadCache(int autoId) {
        PLVPPTInfo.DataBean.ContentsBean pptContentsBeanFromAutoId;
        if (checkInitialized() && (pptContentsBeanFromAutoId = getPptContentsBeanFromAutoId(autoId)) != null) {
            this.plvPptUploadLocalRepository.removeCache(pptContentsBeanFromAutoId.getFileId());
        }
    }

    public void requestGetPptCoverList() {
        requestGetPptCoverList(false);
    }

    public void restartUploadFromCache(Context context, String fileId, OnPLVSDocumentUploadListener listener) {
        PLVPptUploadLocalCacheVO cache;
        if (checkInitialized() && (cache = this.plvPptUploadLocalRepository.getCache(fileId)) != null) {
            File file = new File(cache.getFilePath());
            if (file.exists()) {
                uploadFile(context, file, cache.getConvertType(), listener);
            } else {
                Log.w(TAG, "file is not exist.");
            }
        }
    }

    public void uploadFile(Context context, File uploadFile, final String convertType, final OnPLVSDocumentUploadListener listener) {
        if (checkInitialized()) {
            final PLVPptUploadLocalCacheVO pLVPptUploadLocalCacheVO = new PLVPptUploadLocalCacheVO();
            pLVPptUploadLocalCacheVO.setStatus(0);
            pLVPptUploadLocalCacheVO.setFileName(uploadFile.getName());
            pLVPptUploadLocalCacheVO.setFilePath(uploadFile.getAbsolutePath());
            pLVPptUploadLocalCacheVO.setConvertType(convertType);
            this.plvDocumentRepository.uploadPptFile(context, uploadFile, convertType, new OnPLVSDocumentUploadListener() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentNetPresenter.3
                @Override // com.plv.livescenes.upload.OnPLVDocumentUploadListener
                public void onConvertFailed(PLVPPTInfo.DataBean.ContentsBean documentBean, int errorCode, String msg, Throwable throwable) {
                    OnPLVSDocumentUploadListener onPLVSDocumentUploadListener = listener;
                    if (onPLVSDocumentUploadListener != null) {
                        onPLVSDocumentUploadListener.onConvertFailed(documentBean, errorCode, msg, throwable);
                    }
                    pLVPptUploadLocalCacheVO.setStatus(6);
                    PLVDocumentNetPresenter.this.plvPptUploadLocalRepository.saveCache(pLVPptUploadLocalCacheVO);
                }

                @Override // com.plv.livescenes.upload.OnPLVDocumentUploadListener
                public void onConvertSuccess(PLVPPTInfo.DataBean.ContentsBean documentBean) {
                    OnPLVSDocumentUploadListener onPLVSDocumentUploadListener = listener;
                    if (onPLVSDocumentUploadListener != null) {
                        onPLVSDocumentUploadListener.onConvertSuccess(documentBean);
                    }
                    if (convertType.equals(documentBean.getConvertType())) {
                        pLVPptUploadLocalCacheVO.setStatus(8);
                    } else {
                        pLVPptUploadLocalCacheVO.setStatus(7);
                    }
                    PLVDocumentNetPresenter.this.plvPptUploadLocalRepository.saveCache(pLVPptUploadLocalCacheVO);
                    if (pLVPptUploadLocalCacheVO.getStatus().intValue() == 7) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(pLVPptUploadLocalCacheVO);
                        Iterator it = PLVDocumentNetPresenter.this.viewWeakReferenceList.iterator();
                        while (it.hasNext()) {
                            IPLVDocumentContract.View view = (IPLVDocumentContract.View) ((WeakReference) it.next()).get();
                            if (view != null && view.notifyFileConvertAnimateLoss(arrayList)) {
                                return;
                            }
                        }
                    }
                }

                @Override // com.plv.livescenes.upload.OnPLVDocumentUploadListener
                public void onDocumentConverting(PLVPPTInfo.DataBean.ContentsBean documentBean) {
                    OnPLVSDocumentUploadListener onPLVSDocumentUploadListener = listener;
                    if (onPLVSDocumentUploadListener != null) {
                        onPLVSDocumentUploadListener.onDocumentConverting(documentBean);
                    }
                    pLVPptUploadLocalCacheVO.setStatus(5);
                    PLVDocumentNetPresenter.this.plvPptUploadLocalRepository.saveCache(pLVPptUploadLocalCacheVO);
                }

                @Override // com.plv.livescenes.upload.OnPLVDocumentUploadListener
                public void onDocumentExist(PLVPPTInfo.DataBean.ContentsBean documentBean) {
                    OnPLVSDocumentUploadListener onPLVSDocumentUploadListener = listener;
                    if (onPLVSDocumentUploadListener != null) {
                        onPLVSDocumentUploadListener.onDocumentExist(documentBean);
                    }
                    PLVDocumentNetPresenter.this.plvPptUploadLocalRepository.removeCache(pLVPptUploadLocalCacheVO.getFileId());
                }

                @Override // com.plv.livescenes.upload.OnPLVDocumentUploadListener
                public void onPrepared(PLVPPTInfo.DataBean.ContentsBean documentBean) {
                    OnPLVSDocumentUploadListener onPLVSDocumentUploadListener = listener;
                    if (onPLVSDocumentUploadListener != null) {
                        onPLVSDocumentUploadListener.onPrepared(documentBean);
                    }
                    pLVPptUploadLocalCacheVO.setFileId(documentBean.getFileId());
                    pLVPptUploadLocalCacheVO.setStatus(1);
                    PLVDocumentNetPresenter.this.plvPptUploadLocalRepository.saveCache(pLVPptUploadLocalCacheVO);
                }

                @Override // com.plv.livescenes.upload.OnPLVDocumentUploadListener
                public void onUploadFailed(@Nullable PLVPPTInfo.DataBean.ContentsBean documentBean, int errorCode, String msg, Throwable throwable) {
                    OnPLVSDocumentUploadListener onPLVSDocumentUploadListener = listener;
                    if (onPLVSDocumentUploadListener != null) {
                        onPLVSDocumentUploadListener.onUploadFailed(documentBean, errorCode, msg, throwable);
                    }
                    pLVPptUploadLocalCacheVO.setStatus(3);
                    PLVDocumentNetPresenter.this.plvPptUploadLocalRepository.saveCache(pLVPptUploadLocalCacheVO);
                }

                @Override // com.plv.livescenes.upload.OnPLVDocumentUploadListener
                public void onUploadProgress(PLVPPTInfo.DataBean.ContentsBean documentBean, int progress) {
                    OnPLVSDocumentUploadListener onPLVSDocumentUploadListener = listener;
                    if (onPLVSDocumentUploadListener != null) {
                        onPLVSDocumentUploadListener.onUploadProgress(documentBean, progress);
                    }
                    pLVPptUploadLocalCacheVO.setStatus(2);
                    PLVDocumentNetPresenter.this.plvPptUploadLocalRepository.saveCache(pLVPptUploadLocalCacheVO);
                }

                @Override // com.plv.livescenes.upload.OnPLVDocumentUploadListener
                public void onUploadSuccess(PLVPPTInfo.DataBean.ContentsBean documentBean) {
                    OnPLVSDocumentUploadListener onPLVSDocumentUploadListener = listener;
                    if (onPLVSDocumentUploadListener != null) {
                        onPLVSDocumentUploadListener.onUploadSuccess(documentBean);
                    }
                    pLVPptUploadLocalCacheVO.setStatus(4);
                    PLVDocumentNetPresenter.this.plvPptUploadLocalRepository.saveCache(pLVPptUploadLocalCacheVO);
                    PLVDocumentNetPresenter.this.plvDocumentRepository.requestPptCoverList(true);
                }
            });
        }
    }

    public void requestGetPptCoverList(boolean forceRefresh) {
        if (checkInitialized()) {
            this.plvDocumentRepository.requestPptCoverList(forceRefresh);
        }
    }

    public void deleteDocument(String fileId) {
        if (checkInitialized()) {
            this.plvDocumentRepository.deleteDocument(fileId);
        }
    }

    public void removeUploadCache(List<PLVPptUploadLocalCacheVO> localCacheVOS) {
        if (checkInitialized() && localCacheVOS != null) {
            Iterator<PLVPptUploadLocalCacheVO> it = localCacheVOS.iterator();
            while (it.hasNext()) {
                this.plvPptUploadLocalRepository.removeCache(it.next().getFileId());
            }
        }
    }

    public void removeUploadCache(String fileId) {
        if (checkInitialized()) {
            this.plvPptUploadLocalRepository.removeCache(fileId);
        }
    }
}
