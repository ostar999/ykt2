package com.plv.livescenes.playback.chat;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class PLVChatPlaybackFootDataListener implements IPLVChatPlaybackCallDataListener {
    public int balanceTime;
    public boolean isOnlyCallSpeakData;
    public int maxCallDataCount;

    public PLVChatPlaybackFootDataListener() {
        this.balanceTime = 1000;
        this.maxCallDataCount = 10;
        this.isOnlyCallSpeakData = true;
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
    public void onData(List<PLVChatPlaybackData> list) {
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
    public void onDataCleared() {
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
    public void onDataInserted(int i2, int i3, List<PLVChatPlaybackData> list, boolean z2, int i4) {
        if (z2) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int size = list.size() - 1; size >= 0; size--) {
            PLVChatPlaybackData pLVChatPlaybackData = list.get(size);
            if (pLVChatPlaybackData.getRelativeTime() <= i4 - this.balanceTime) {
                break;
            }
            if (!this.isOnlyCallSpeakData || (pLVChatPlaybackData.getObjects() != null && pLVChatPlaybackData.getObjects().length != 0 && (pLVChatPlaybackData.getObjects()[0] instanceof CharSequence))) {
                arrayList.add(0, pLVChatPlaybackData);
                if (arrayList.size() >= this.maxCallDataCount) {
                    break;
                }
            }
        }
        if (arrayList.size() > 0) {
            onFootAtTimeDataInserted(arrayList);
        }
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
    public void onDataRemoved(int i2, int i3, List<PLVChatPlaybackData> list, boolean z2) {
    }

    public abstract void onFootAtTimeDataInserted(List<PLVChatPlaybackData> list);

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
    public void onHasNotAddedData() {
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
    public void onLoadPreviousFinish() {
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
    public void onManager(IPLVChatPlaybackManager iPLVChatPlaybackManager) {
    }

    public PLVChatPlaybackFootDataListener(int i2) {
        this.balanceTime = 1000;
        this.isOnlyCallSpeakData = true;
        this.maxCallDataCount = i2;
    }
}
