package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheMemoryDataSource {
    private final Map<String, PLVPlaybackCacheVideoVO> playbackCacheVideoVOMap = new ConcurrentHashMap();

    public PLVPlaybackCacheVideoVO getCacheVideoById(String id) {
        return this.playbackCacheVideoVOMap.get(id);
    }

    public void putCacheVideo(PLVPlaybackCacheVideoVO vo) {
        this.playbackCacheVideoVOMap.put(vo.getVideoPoolId(), vo);
    }

    public void putCacheVideos(@NonNull List<PLVPlaybackCacheVideoVO> vos) {
        Iterator<PLVPlaybackCacheVideoVO> it = vos.iterator();
        while (it.hasNext()) {
            putCacheVideo(it.next());
        }
    }

    public void removeCacheVideo(PLVPlaybackCacheVideoVO vo) {
        this.playbackCacheVideoVOMap.remove(vo.getVideoPoolId());
    }
}
