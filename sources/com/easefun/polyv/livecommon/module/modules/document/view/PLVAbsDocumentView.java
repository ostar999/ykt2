package com.easefun.polyv.livecommon.module.modules.document.view;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMode;
import com.easefun.polyv.livecommon.module.modules.document.model.vo.PLVPptUploadLocalCacheVO;
import com.easefun.polyv.livescenes.document.model.PLVSPPTInfo;
import com.easefun.polyv.livescenes.document.model.PLVSPPTJsModel;
import com.easefun.polyv.livescenes.document.model.PLVSPPTPaintStatus;
import com.easefun.polyv.livescenes.document.model.PLVSPPTStatus;
import com.plv.livescenes.document.model.PLVPPTInfo;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVAbsDocumentView implements IPLVDocumentContract.View {
    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public boolean notifyFileConvertAnimateLoss(@NonNull List<PLVPptUploadLocalCacheVO> cacheVOS) {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public boolean notifyFileUploadNotSuccess(@NonNull List<PLVPptUploadLocalCacheVO> cacheVOS) {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onAssistantChangePptPage(int pageId) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onPptCoverList(@Nullable PLVSPPTInfo pptInfo) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onPptDelete(boolean success, @Nullable PLVPPTInfo.DataBean.ContentsBean deletedPptBean) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onPptPageChange(int autoId, int pageId) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onPptPageList(@Nullable PLVSPPTJsModel plvspptJsModel) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onPptPaintStatus(@Nullable PLVSPPTPaintStatus pptPaintStatus) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onPptStatusChange(PLVSPPTStatus pptStatus) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public boolean onRequestOpenPptView(int pptId, String pptName) {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onSetPermission(String type, boolean isGrant) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onSwitchShowMode(PLVDocumentMode showMode) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onUserPermissionChange() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public void onZoomValueChanged(String zoomValue) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract.View
    public boolean requestSelectUploadFileConvertType(Uri fileUri) {
        return false;
    }
}
