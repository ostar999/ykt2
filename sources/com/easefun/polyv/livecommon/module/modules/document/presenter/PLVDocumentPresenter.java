package com.easefun.polyv.livecommon.module.modules.document.presenter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract;
import com.easefun.polyv.livecommon.module.modules.document.model.PLVDocumentRepository;
import com.easefun.polyv.livecommon.module.modules.document.model.PLVPptUploadLocalRepository;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMode;
import com.easefun.polyv.livecommon.module.modules.document.model.vo.PLVPptUploadLocalCacheVO;
import com.easefun.polyv.livescenes.document.PLVSDocumentWebProcessor;
import com.easefun.polyv.livescenes.document.model.PLVSAssistantInfo;
import com.easefun.polyv.livescenes.document.model.PLVSChangePPTInfo;
import com.easefun.polyv.livescenes.document.model.PLVSEditTextInfo;
import com.easefun.polyv.livescenes.document.model.PLVSPPTJsModel;
import com.easefun.polyv.livescenes.document.model.PLVSPPTPaintStatus;
import com.easefun.polyv.livescenes.document.model.PLVSPPTStatus;
import com.easefun.polyv.livescenes.upload.OnPLVSDocumentUploadListener;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.access.PLVUserAbility;
import com.plv.livescenes.access.PLVUserAbilityManager;
import com.plv.livescenes.access.PLVUserRole;
import com.plv.livescenes.document.PLVDocumentWebProcessor;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import com.plv.socket.eventbus.ppt.PLVOnSliceStartEventBus;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.user.PLVSocketUserBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVDocumentPresenter implements IPLVDocumentContract.Presenter {
    public static final int AUTO_ID_WHITE_BOARD = 0;
    private static PLVDocumentPresenter INSTANCE = null;
    private static final String TAG = "PLVDocumentPresenter";

    @Nullable
    private PLVUserAbilityManager.OnUserAbilityChangedListener onUserAbilityChangeCallback;

    @Nullable
    private PLVUserAbilityManager.OnUserRoleChangedListener onUserRoleChangedListener;

    @Nullable
    private PLVDocumentRepository plvDocumentRepository;

    @Nullable
    private PLVPptUploadLocalRepository plvPptUploadLocalRepository;
    private boolean isInitialized = false;
    private final List<WeakReference<IPLVDocumentContract.View>> viewWeakReferenceList = new ArrayList();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isStreamStarted = false;
    private boolean isGuest = false;

    private PLVDocumentPresenter() {
    }

    private boolean checkInitialized() {
        if (!this.isInitialized || this.plvPptUploadLocalRepository == null || this.plvDocumentRepository == null) {
            Log.w(TAG, "Call PLVLSDocumentPresenter.init() first!");
        }
        return this.isInitialized;
    }

    public static IPLVDocumentContract.Presenter getInstance() {
        if (INSTANCE == null) {
            synchronized (PLVDocumentPresenter.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVDocumentPresenter();
                }
            }
        }
        return INSTANCE;
    }

    private void initOnUserAbilityChangeListener() {
        this.onUserAbilityChangeCallback = new PLVUserAbilityManager.OnUserAbilityChangedListener() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentPresenter.1
            @Override // com.plv.livescenes.access.PLVUserAbilityManager.OnUserAbilityChangedListener
            public void onUserAbilitiesChanged(@NonNull List<PLVUserAbility> addedAbilities, @NonNull List<PLVUserAbility> removedAbilities) {
                Iterator it = PLVDocumentPresenter.this.viewWeakReferenceList.iterator();
                while (it.hasNext()) {
                    IPLVDocumentContract.View view = (IPLVDocumentContract.View) ((WeakReference) it.next()).get();
                    if (view != null) {
                        view.onUserPermissionChange();
                    }
                }
                PLVDocumentPresenter.this.enableMarkTool(PLVUserAbilityManager.myAbility().hasAbility(PLVUserAbility.STREAMER_DOCUMENT_ALLOW_USE_PAINT));
            }
        };
        PLVUserAbilityManager.myAbility().addUserAbilityChangeListener(new WeakReference<>(this.onUserAbilityChangeCallback));
    }

    private void initOnUserRoleChangeListener() {
        this.onUserRoleChangedListener = new PLVUserAbilityManager.OnUserRoleChangedListener() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentPresenter.2
            @Override // com.plv.livescenes.access.PLVUserAbilityManager.OnUserRoleChangedListener
            public void onUserRoleAdded(PLVUserRole role) {
                PLVDocumentPresenter.this.updateDocumentPermission();
            }

            @Override // com.plv.livescenes.access.PLVUserAbilityManager.OnUserRoleChangedListener
            public void onUserRoleRemoved(PLVUserRole role) {
                PLVDocumentPresenter.this.updateDocumentPermission();
            }
        };
        PLVUserAbilityManager.myAbility().addUserRoleChangeListener(new WeakReference<>(this.onUserRoleChangedListener));
    }

    private void initRepository(IPLVLiveRoomDataManager liveRoomDataManager, PLVSDocumentWebProcessor documentWebProcessor) {
        PLVDocumentRepository pLVDocumentRepository = new PLVDocumentRepository(documentWebProcessor);
        this.plvDocumentRepository = pLVDocumentRepository;
        pLVDocumentRepository.init(liveRoomDataManager);
        PLVSocketUserBean pLVSocketUserBean = new PLVSocketUserBean();
        pLVSocketUserBean.setUserId(liveRoomDataManager.getConfig().getUser().getViewerId());
        pLVSocketUserBean.setNick(liveRoomDataManager.getConfig().getUser().getViewerName());
        pLVSocketUserBean.setPic(liveRoomDataManager.getConfig().getUser().getViewerAvatar());
        this.plvDocumentRepository.sendWebMessage("setUser", PLVGsonUtil.toJson(pLVSocketUserBean));
        updateDocumentPermission();
        if (!this.isGuest) {
            this.plvDocumentRepository.sendWebMessage(PLVDocumentWebProcessor.CHANGEPPT, "{\"autoId\":0,\"isCamClosed\":0}");
        }
        this.plvDocumentRepository.sendWebMessage("setPaintStatus", "{\"status\":\"open\"}");
        this.plvPptUploadLocalRepository = new PLVPptUploadLocalRepository();
    }

    private void initSocketListener() {
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentPresenter.3
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                PLVSAssistantInfo pLVSAssistantInfo;
                if (PLVEventConstant.Ppt.ON_ASSISTANT_CONTROL.equals(event) && (pLVSAssistantInfo = (PLVSAssistantInfo) PLVGsonUtil.fromJson(PLVSAssistantInfo.class, message)) != null) {
                    Iterator it = PLVDocumentPresenter.this.viewWeakReferenceList.iterator();
                    while (it.hasNext()) {
                        IPLVDocumentContract.View view = (IPLVDocumentContract.View) ((WeakReference) it.next()).get();
                        if (view != null) {
                            view.onAssistantChangePptPage(pLVSAssistantInfo.getData().getPageId());
                        }
                    }
                }
            }
        });
    }

    private void observeDocumentZoomValueChanged(LifecycleOwner lifecycleOwner) {
        this.plvDocumentRepository.getDocumentZoomValueLiveData().observe(lifecycleOwner, new Observer<String>() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentPresenter.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable String value) {
                if (value == null) {
                    return;
                }
                Iterator it = PLVDocumentPresenter.this.viewWeakReferenceList.iterator();
                while (it.hasNext()) {
                    IPLVDocumentContract.View view = (IPLVDocumentContract.View) ((WeakReference) it.next()).get();
                    if (view != null) {
                        view.onZoomValueChanged(value);
                    }
                }
            }
        });
    }

    private void observeOnSliceStartEvent() {
        this.compositeDisposable.add(PLVOnSliceStartEventBus.get().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVOnSliceStartEvent>() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentPresenter.9
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVOnSliceStartEvent plvOnSliceStartEvent) {
                if (PLVDocumentPresenter.this.plvDocumentRepository != null) {
                    PLVDocumentPresenter.this.plvDocumentRepository.sendWebMessage(PLVDocumentWebProcessor.ONSLICESTART, PLVGsonUtil.toJson(plvOnSliceStartEvent));
                }
            }
        }));
    }

    private void observePptJsModel(LifecycleOwner lifecycleOwner) {
        this.plvDocumentRepository.getPptJsModelLiveData().observe(lifecycleOwner, new Observer<PLVStatefulData<PLVSPPTJsModel>>() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentPresenter.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<PLVSPPTJsModel> plvsPptJsModel) {
                if (plvsPptJsModel == null || !plvsPptJsModel.isSuccess()) {
                    return;
                }
                Iterator it = PLVDocumentPresenter.this.viewWeakReferenceList.iterator();
                while (it.hasNext()) {
                    IPLVDocumentContract.View view = (IPLVDocumentContract.View) ((WeakReference) it.next()).get();
                    if (view != null) {
                        view.onPptPageList(plvsPptJsModel.getData());
                    }
                }
            }
        });
    }

    private void observePptPaintStatus(LifecycleOwner lifecycleOwner) {
        this.plvDocumentRepository.getPptPaintStatusLiveData().observe(lifecycleOwner, new Observer<PLVSPPTPaintStatus>() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentPresenter.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVSPPTPaintStatus plvspptPaintStatus) {
                Iterator it = PLVDocumentPresenter.this.viewWeakReferenceList.iterator();
                while (it.hasNext()) {
                    IPLVDocumentContract.View view = (IPLVDocumentContract.View) ((WeakReference) it.next()).get();
                    if (view != null) {
                        view.onPptPaintStatus(plvspptPaintStatus);
                    }
                }
            }
        });
    }

    private void observePptStatus(LifecycleOwner lifecycleOwner) {
        this.plvDocumentRepository.getPptStatusLiveData().observe(lifecycleOwner, new Observer<PLVSPPTStatus>() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentPresenter.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVSPPTStatus plvspptStatus) {
                if (plvspptStatus == null) {
                    return;
                }
                Iterator it = PLVDocumentPresenter.this.viewWeakReferenceList.iterator();
                while (it.hasNext()) {
                    IPLVDocumentContract.View view = (IPLVDocumentContract.View) ((WeakReference) it.next()).get();
                    if (view != null) {
                        view.onPptPageChange(plvspptStatus.getAutoId(), plvspptStatus.getPageId());
                        view.onPptStatusChange(plvspptStatus);
                    }
                }
            }
        });
    }

    private void observeRefreshPptMessage(LifecycleOwner lifecycleOwner) {
        this.plvDocumentRepository.getRefreshPptMessageLiveData().observe(lifecycleOwner, new Observer<String>() { // from class: com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentPresenter.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable String message) {
                if (PLVDocumentPresenter.this.isStreamStarted) {
                    PLVSocketWrapper.getInstance().emit("message", message);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDocumentPermission() {
        if (this.plvDocumentRepository == null) {
            return;
        }
        String str = (PLVUserAbilityManager.myAbility().hasRole(PLVUserRole.STREAMER_TEACHER) || PLVUserAbilityManager.myAbility().hasRole(PLVUserRole.STREAMER_GRANTED_SPEAKER_USER)) ? "speaker" : PLVUserAbilityManager.myAbility().hasRole(PLVUserRole.STREAMER_GRANTED_PAINT_USER) ? "paint" : "whatever";
        this.plvDocumentRepository.sendWebMessage("setPaintPermission", "{\"userType\":\"" + str + "\"}");
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void changeColor(String colorString) {
        if (checkInitialized()) {
            this.plvDocumentRepository.sendWebMessage("changeColor", colorString);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void changeMarkToolType(String markToolType) {
        if (checkInitialized()) {
            if (PLVDocumentMarkToolType.CLEAR.equals(markToolType)) {
                this.plvDocumentRepository.sendWebMessage(PLVDocumentWebProcessor.DELETEALLPAINT, "");
                return;
            }
            if (PLVDocumentMarkToolType.ERASER.equals(markToolType)) {
                this.plvDocumentRepository.sendWebMessage("toDelete", "");
                return;
            }
            if (PLVDocumentMarkToolType.BRUSH.equals(markToolType) || PLVDocumentMarkToolType.ARROW.equals(markToolType) || "text".equals(markToolType)) {
                this.plvDocumentRepository.sendWebMessage(PLVDocumentWebProcessor.SETDRAWTYPE, "{\"type\":\"" + markToolType + "\"}");
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void changePpt(int autoId) {
        if (checkInitialized() && !PLVUserAbilityManager.myAbility().notHasAbility(PLVUserAbility.STREAMER_DOCUMENT_ALLOW_OPEN_PPT)) {
            this.plvDocumentRepository.sendWebMessage(PLVDocumentWebProcessor.CHANGEPPT, "{\"autoId\":" + autoId + "}");
            changePptPage(autoId, 0);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void changePptPage(int autoId, int pageId) {
        if (checkInitialized() && !PLVUserAbilityManager.myAbility().notHasAbility(PLVUserAbility.STREAMER_DOCUMENT_ALLOW_TURN_PAGE)) {
            this.plvDocumentRepository.sendWebMessage(PLVDocumentWebProcessor.CHANGEPPT, PLVGsonUtil.toJson(new PLVSChangePPTInfo(autoId, pageId)));
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void changePptToLastStep() {
        if (checkInitialized() && !PLVUserAbilityManager.myAbility().notHasAbility(PLVUserAbility.STREAMER_DOCUMENT_ALLOW_TURN_PAGE)) {
            this.plvDocumentRepository.sendWebMessage("changePPTPage", "{\"type\":\"gotoPreviousStep\"}");
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void changePptToNextStep() {
        if (checkInitialized() && !PLVUserAbilityManager.myAbility().notHasAbility(PLVUserAbility.STREAMER_DOCUMENT_ALLOW_TURN_PAGE)) {
            this.plvDocumentRepository.sendWebMessage("changePPTPage", "{\"type\":\"gotoNextStep\"}");
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void changeTextContent(String content) {
        if (checkInitialized()) {
            this.plvDocumentRepository.sendWebMessage(PLVDocumentWebProcessor.FILLEDITTEXT, PLVGsonUtil.toJson(new PLVSEditTextInfo(content)));
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void changeToWhiteBoard() {
        if (checkInitialized() && !PLVUserAbilityManager.myAbility().notHasAbility(PLVUserAbility.STREAMER_DOCUMENT_ALLOW_SWITCH_PPT_WHITEBOARD)) {
            changeWhiteBoardPage(0);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void changeWhiteBoardPage(int pageId) {
        if (checkInitialized() && !PLVUserAbilityManager.myAbility().notHasAbility(PLVUserAbility.STREAMER_DOCUMENT_ALLOW_TURN_PAGE)) {
            this.plvDocumentRepository.sendWebMessage(PLVDocumentWebProcessor.CHANGEPPT, PLVGsonUtil.toJson(new PLVSChangePPTInfo(0, pageId)));
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void checkUploadFileStatus() {
        PLVDocumentNetPresenter.getInstance().checkUploadFileStatus();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void deleteDocument(int autoId) {
        PLVDocumentNetPresenter.getInstance().deleteDocument(autoId);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void destroy() {
        this.compositeDisposable.dispose();
        this.onUserAbilityChangeCallback = null;
        this.onUserRoleChangedListener = null;
        PLVDocumentRepository pLVDocumentRepository = this.plvDocumentRepository;
        if (pLVDocumentRepository != null) {
            pLVDocumentRepository.destroy();
        }
        PLVDocumentNetPresenter.getInstance().destroy();
        this.isInitialized = false;
        this.viewWeakReferenceList.clear();
        INSTANCE = null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void enableMarkTool(boolean enable) {
        if (checkInitialized()) {
            boolean zHasAbility = PLVUserAbilityManager.myAbility().hasAbility(PLVUserAbility.STREAMER_DOCUMENT_ALLOW_USE_PAINT);
            if (enable && zHasAbility) {
                this.plvDocumentRepository.sendWebMessage("setPaintStatus", "{\"status\":\"open\"}");
            } else {
                this.plvDocumentRepository.sendWebMessage("setPaintStatus", "{\"status\":\"close\"}");
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void init(LifecycleOwner lifecycleOwner, IPLVLiveRoomDataManager liveRoomDataManager, PLVSDocumentWebProcessor documentWebProcessor) {
        this.isGuest = liveRoomDataManager.getConfig().getUser().getViewerType().equals("guest");
        initRepository(liveRoomDataManager, documentWebProcessor);
        initOnUserAbilityChangeListener();
        initOnUserRoleChangeListener();
        initSocketListener();
        observeRefreshPptMessage(lifecycleOwner);
        observePptJsModel(lifecycleOwner);
        observePptStatus(lifecycleOwner);
        observePptPaintStatus(lifecycleOwner);
        observeDocumentZoomValueChanged(lifecycleOwner);
        observeOnSliceStartEvent();
        this.isInitialized = true;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void notifyStreamStatus(boolean isStreamStarted) {
        this.isStreamStarted = isStreamStarted;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void onSelectUploadFile(Uri fileUri) {
        PLVDocumentNetPresenter.getInstance().onSelectUploadFile(fileUri);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void registerView(IPLVDocumentContract.View view) {
        PLVDocumentNetPresenter.getInstance().registerView(view);
        this.viewWeakReferenceList.add(new WeakReference<>(view));
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void removeUploadCache(int autoId) {
        PLVDocumentNetPresenter.getInstance().removeUploadCache(autoId);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void requestGetPptCoverList() {
        PLVDocumentNetPresenter.getInstance().requestGetPptCoverList();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void requestGetPptPageList(int autoId) {
        if (checkInitialized()) {
            this.plvDocumentRepository.requestGetCachedPptPageList(autoId);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void requestOpenPptView(int pptId, String pptName) {
        Iterator<WeakReference<IPLVDocumentContract.View>> it = this.viewWeakReferenceList.iterator();
        while (it.hasNext()) {
            IPLVDocumentContract.View view = it.next().get();
            if (view != null && view.onRequestOpenPptView(pptId, pptName)) {
                return;
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void resetZoom() {
        if (checkInitialized()) {
            this.plvDocumentRepository.sendWebMessage(PLVDocumentWebProcessor.TO_ZOOM_RESET, "");
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void restartUploadFromCache(Context context, String fileId, OnPLVSDocumentUploadListener listener) {
        PLVDocumentNetPresenter.getInstance().restartUploadFromCache(context, fileId, listener);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void switchShowMode(PLVDocumentMode showMode) {
        Iterator<WeakReference<IPLVDocumentContract.View>> it = this.viewWeakReferenceList.iterator();
        while (it.hasNext()) {
            IPLVDocumentContract.View view = it.next().get();
            if (view != null) {
                view.onSwitchShowMode(showMode);
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void uploadFile(Context context, File uploadFile, final String convertType, final OnPLVSDocumentUploadListener listener) {
        PLVDocumentNetPresenter.getInstance().uploadFile(context, uploadFile, convertType, listener);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void deleteDocument(String fileId) {
        PLVDocumentNetPresenter.getInstance().deleteDocument(fileId);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void removeUploadCache(List<PLVPptUploadLocalCacheVO> localCacheVOS) {
        PLVDocumentNetPresenter.getInstance().removeUploadCache(localCacheVOS);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void requestGetPptCoverList(boolean forceRefresh) {
        PLVDocumentNetPresenter.getInstance().requestGetPptCoverList(forceRefresh);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.Presenter
    public void removeUploadCache(String fileId) {
        PLVDocumentNetPresenter.getInstance().removeUploadCache(fileId);
    }
}
