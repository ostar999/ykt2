package com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.usecase;

import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheListMergeUseCase {
    @Nullable
    private static PLVPlaybackCacheVideoVO findPlaybackCacheInList(final List<PLVPlaybackCacheVideoVO> voList, final String id) {
        if (id == null) {
            return null;
        }
        for (PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO : voList) {
            if (id.equals(pLVPlaybackCacheVideoVO.getVideoPoolId())) {
                return pLVPlaybackCacheVideoVO;
            }
        }
        return null;
    }

    private boolean reduce(final List<PLVPlaybackCacheVideoVO> voList, final PLVPlaybackCacheVideoVO vo, final List<PLVPlaybackCacheDownloadStatusEnum> fitStatusList) {
        if (voList != null && vo != null) {
            PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVOFindPlaybackCacheInList = findPlaybackCacheInList(voList, vo.getVideoPoolId());
            if (pLVPlaybackCacheVideoVOFindPlaybackCacheInList != null) {
                vo.mergeFrom(pLVPlaybackCacheVideoVOFindPlaybackCacheInList);
            }
            if (fitStatusList.contains(vo.getDownloadStatusEnum())) {
                if (pLVPlaybackCacheVideoVOFindPlaybackCacheInList != null) {
                    voList.set(voList.indexOf(pLVPlaybackCacheVideoVOFindPlaybackCacheInList), vo);
                } else {
                    voList.add(vo);
                }
                return true;
            }
            if (pLVPlaybackCacheVideoVOFindPlaybackCacheInList != null) {
                voList.remove(pLVPlaybackCacheVideoVOFindPlaybackCacheInList);
                return true;
            }
        }
        return false;
    }

    public boolean reduceDownloadedList(final List<PLVPlaybackCacheVideoVO> voList, final PLVPlaybackCacheVideoVO vo) {
        return reduce(voList, vo, PLVSugarUtil.listOf(PLVPlaybackCacheDownloadStatusEnum.DOWNLOADED));
    }

    public boolean reduceDownloadingList(final List<PLVPlaybackCacheVideoVO> voList, final PLVPlaybackCacheVideoVO vo) {
        return reduce(voList, vo, PLVSugarUtil.listOf(PLVPlaybackCacheDownloadStatusEnum.WAITING, PLVPlaybackCacheDownloadStatusEnum.PAUSING, PLVPlaybackCacheDownloadStatusEnum.DOWNLOADING, PLVPlaybackCacheDownloadStatusEnum.DOWNLOAD_FAIL));
    }
}
