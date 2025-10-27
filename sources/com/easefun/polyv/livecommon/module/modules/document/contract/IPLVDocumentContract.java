package com.easefun.polyv.livecommon.module.modules.document.contract;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMode;
import com.easefun.polyv.livecommon.module.modules.document.model.vo.PLVPptUploadLocalCacheVO;
import com.easefun.polyv.livescenes.document.PLVSDocumentWebProcessor;
import com.easefun.polyv.livescenes.document.model.PLVSPPTInfo;
import com.easefun.polyv.livescenes.document.model.PLVSPPTJsModel;
import com.easefun.polyv.livescenes.document.model.PLVSPPTPaintStatus;
import com.easefun.polyv.livescenes.document.model.PLVSPPTStatus;
import com.easefun.polyv.livescenes.upload.OnPLVSDocumentUploadListener;
import com.plv.livescenes.document.model.PLVPPTInfo;
import java.io.File;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPLVDocumentContract {

    public interface Presenter {
        void changeColor(String colorString);

        void changeMarkToolType(String markToolType);

        void changePpt(int autoId);

        void changePptPage(int autoId, int pageId);

        void changePptToLastStep();

        void changePptToNextStep();

        void changeTextContent(String content);

        void changeToWhiteBoard();

        void changeWhiteBoardPage(int pageId);

        void checkUploadFileStatus();

        void deleteDocument(int autoId);

        void deleteDocument(String fileId);

        void destroy();

        void enableMarkTool(boolean enable);

        void init(LifecycleOwner lifecycleOwner, IPLVLiveRoomDataManager liveRoomDataManager, PLVSDocumentWebProcessor documentWebProcessor);

        void notifyStreamStatus(boolean isStreamStarted);

        void onSelectUploadFile(Uri fileUri);

        void registerView(View view);

        @Deprecated
        void removeUploadCache(int autoId);

        void removeUploadCache(String fileId);

        @Deprecated
        void removeUploadCache(List<PLVPptUploadLocalCacheVO> localCacheVOS);

        void requestGetPptCoverList();

        void requestGetPptCoverList(boolean forceRefresh);

        void requestGetPptPageList(int autoId);

        void requestOpenPptView(int pptId, String pptName);

        void resetZoom();

        void restartUploadFromCache(Context context, String fileId, OnPLVSDocumentUploadListener listener);

        void switchShowMode(PLVDocumentMode showMode);

        void uploadFile(Context context, File uploadFile, String convertType, OnPLVSDocumentUploadListener listener);
    }

    public interface View {
        boolean notifyFileConvertAnimateLoss(@NonNull List<PLVPptUploadLocalCacheVO> cacheVOS);

        boolean notifyFileUploadNotSuccess(@NonNull List<PLVPptUploadLocalCacheVO> cacheVOS);

        void onAssistantChangePptPage(int pageId);

        void onPptCoverList(@Nullable PLVSPPTInfo pptInfo);

        void onPptDelete(boolean success, @Nullable PLVPPTInfo.DataBean.ContentsBean deletedPptBean);

        void onPptPageChange(int autoId, int pageId);

        void onPptPageList(@Nullable PLVSPPTJsModel plvspptJsModel);

        void onPptPaintStatus(@Nullable PLVSPPTPaintStatus pptPaintStatus);

        void onPptStatusChange(PLVSPPTStatus pptStatus);

        boolean onRequestOpenPptView(int pptId, String pptName);

        void onSetPermission(String type, boolean isGrant);

        void onSwitchShowMode(PLVDocumentMode showMode);

        void onUserPermissionChange();

        void onZoomValueChanged(String zoomValue);

        boolean requestSelectUploadFileConvertType(Uri fileUri);
    }
}
