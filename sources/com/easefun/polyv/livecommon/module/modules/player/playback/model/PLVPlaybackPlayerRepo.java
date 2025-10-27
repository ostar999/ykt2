package com.easefun.polyv.livecommon.module.modules.player.playback.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.plv.foundationsdk.component.livedata.PLVAutoSaveLiveData;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.playback.vo.PLVPlaybackDataVO;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVPlaybackPlayerRepo {
    private final MutableLiveData<Map<String, PLVPlayInfoVO>> playbackVideoProgressMap = new PLVAutoSaveLiveData<Map<String, PLVPlayInfoVO>>("plv_playback_video_progress") { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackPlayerRepo.1
    };

    private static String createKeyByPlaybackData(@NonNull final PLVPlaybackDataVO playbackDataVO) {
        return playbackDataVO.getPlaybackListType().name() + playbackDataVO.getVideoPoolId();
    }

    @NonNull
    private Map<String, PLVPlayInfoVO> getPlaybackProgressMap() {
        return (Map) PLVSugarUtil.getOrDefault(this.playbackVideoProgressMap.getValue(), new HashMap());
    }

    @Nullable
    public PLVPlayInfoVO getPlaybackProgress(final PLVPlaybackDataVO playbackDataVO) {
        return getPlaybackProgressMap().get(createKeyByPlaybackData(playbackDataVO));
    }

    public void updatePlaybackProgress(@NonNull final PLVPlaybackDataVO playbackDataVO, @Nullable final PLVPlayInfoVO playInfoVO) {
        String strCreateKeyByPlaybackData = createKeyByPlaybackData(playbackDataVO);
        Map<String, PLVPlayInfoVO> playbackProgressMap = getPlaybackProgressMap();
        playbackProgressMap.put(strCreateKeyByPlaybackData, playInfoVO);
        this.playbackVideoProgressMap.postValue(playbackProgressMap);
    }
}
