package com.easefun.polyv.livecommon.module.modules.previous.contract;

import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.modules.previous.presenter.data.PLVPreviousData;
import com.plv.livescenes.model.PLVPlaybackListVO;
import com.plv.livescenes.previous.model.PLVChapterDataVO;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPLVPreviousPlaybackContract {

    public interface IPreviousPlaybackPresenter {
        void changePlaybackVideoSeek(int position);

        void changePlaybackVideoVid(String vid);

        PLVPreviousData getData();

        void init();

        void onDestroy();

        void onPlayComplete();

        void onSeekChange(int position);

        void registerView(IPreviousPlaybackView view);

        void requestChapterDetail();

        void requestLoadMorePreviousVideo();

        void requestPreviousList();

        void unregisterView(IPreviousPlaybackView view);

        void updatePlaybackCurrentPosition(PLVPlayInfoVO playInfoVO);
    }

    public interface IPreviousPlaybackView {
        void chapterNoMoreData();

        void onPlayComplete();

        void onSeekChange(int position);

        void previousLoadModreData(PLVPlaybackListVO listVO);

        void previousLoadMoreError();

        void previousNoMoreData();

        void requestChapterError();

        void requestPreviousError();

        void setPresenter(IPreviousPlaybackPresenter presenter);

        void updateChapterList(List<PLVChapterDataVO> dataList);

        void updatePreviousVideoList(PLVPlaybackListVO playbackListInfo);
    }
}
