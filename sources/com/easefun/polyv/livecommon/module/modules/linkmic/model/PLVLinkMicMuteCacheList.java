package com.easefun.polyv.livecommon.module.modules.linkmic.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLinkMicMuteCacheList {
    private List<PLVLinkMicMuteCacheBean> muteCacheList = new LinkedList();

    private void addOrUpdateMuteCacheList(String linkMicId, boolean mute, boolean isAudio) {
        boolean z2 = false;
        for (PLVLinkMicMuteCacheBean pLVLinkMicMuteCacheBean : this.muteCacheList) {
            if (linkMicId.equals(pLVLinkMicMuteCacheBean.getLinkMicId())) {
                if (isAudio) {
                    pLVLinkMicMuteCacheBean.setMuteAudio(mute);
                } else {
                    pLVLinkMicMuteCacheBean.setMuteVideo(mute);
                }
                z2 = true;
            }
        }
        if (z2) {
            return;
        }
        PLVLinkMicMuteCacheBean pLVLinkMicMuteCacheBean2 = new PLVLinkMicMuteCacheBean(linkMicId);
        if (isAudio) {
            pLVLinkMicMuteCacheBean2.setMuteAudio(mute);
        } else {
            pLVLinkMicMuteCacheBean2.setMuteVideo(mute);
        }
        this.muteCacheList.add(pLVLinkMicMuteCacheBean2);
    }

    public void addOrUpdateAudioMuteCacheList(String linkMicId, boolean mute) {
        addOrUpdateMuteCacheList(linkMicId, mute, true);
    }

    public void addOrUpdateVideoMuteCacheList(String linkMicId, boolean mute) {
        addOrUpdateMuteCacheList(linkMicId, mute, false);
    }

    public void clear() {
        this.muteCacheList.clear();
    }

    public void updateUserMuteCacheWhenJoinList(PLVLinkMicItemDataBean dataBean) {
        Iterator<PLVLinkMicMuteCacheBean> it = this.muteCacheList.iterator();
        while (it.hasNext()) {
            PLVLinkMicMuteCacheBean next = it.next();
            if (next.getLinkMicId().equals(dataBean.getLinkMicId())) {
                dataBean.setMuteAudio(next.isMuteAudio());
                dataBean.setMuteVideo(next.isMuteVideo());
                it.remove();
            }
        }
    }
}
