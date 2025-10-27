package com.easefun.polyv.livecommon.module.modules.previous.view;

import com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract;
import com.plv.livescenes.model.PLVPlaybackListVO;
import com.plv.livescenes.previous.model.PLVChapterDataVO;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVAbsPreviousView implements IPLVPreviousPlaybackContract.IPreviousPlaybackView {
    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void chapterNoMoreData() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void onPlayComplete() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void onSeekChange(int position) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void previousLoadModreData(PLVPlaybackListVO listVO) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void previousLoadMoreError() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void previousNoMoreData() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void requestChapterError() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void requestPreviousError() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void setPresenter(IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter presenter) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void updateChapterList(List<PLVChapterDataVO> dataList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackView
    public void updatePreviousVideoList(PLVPlaybackListVO playbackListInfo) {
    }
}
