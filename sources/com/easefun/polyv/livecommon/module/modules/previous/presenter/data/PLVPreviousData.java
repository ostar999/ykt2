package com.easefun.polyv.livecommon.module.modules.previous.presenter.data;

import androidx.lifecycle.MutableLiveData;
import com.plv.livescenes.model.PLVPlaybackListVO;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPreviousData {
    private PLVPlaybackListVO.DataBean.ContentsBean previousDetail;
    private MutableLiveData<String> playbackVideoVidData = new MutableLiveData<>();
    private MutableLiveData<Integer> playBackVidoSeekData = new MutableLiveData<>();

    public MutableLiveData<Integer> getPlayBackVidoSeekData() {
        return this.playBackVidoSeekData;
    }

    public MutableLiveData<String> getPlaybackVideoVidData() {
        return this.playbackVideoVidData;
    }

    public PLVPlaybackListVO.DataBean.ContentsBean getPreviousDetail() {
        return this.previousDetail;
    }

    public void setPlayBackVidoSeekData(MutableLiveData<Integer> playBackVidoSeekData) {
        this.playBackVidoSeekData = playBackVidoSeekData;
    }

    public void setPlaybackVideoVidData(MutableLiveData<String> playbackVideoVidData) {
        this.playbackVideoVidData = playbackVideoVidData;
    }

    public void update(List<PLVPlaybackListVO.DataBean.ContentsBean> mPlaybackList, String vid) {
        for (PLVPlaybackListVO.DataBean.ContentsBean contentsBean : mPlaybackList) {
            if (contentsBean.getVideoPoolId().equals(vid)) {
                this.previousDetail = contentsBean;
            }
        }
    }
}
