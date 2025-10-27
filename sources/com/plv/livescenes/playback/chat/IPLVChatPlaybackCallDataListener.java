package com.plv.livescenes.playback.chat;

import java.util.List;

/* loaded from: classes5.dex */
public interface IPLVChatPlaybackCallDataListener {
    void onData(List<PLVChatPlaybackData> list);

    void onDataCleared();

    void onDataInserted(int i2, int i3, List<PLVChatPlaybackData> list, boolean z2, int i4);

    void onDataRemoved(int i2, int i3, List<PLVChatPlaybackData> list, boolean z2);

    void onHasNotAddedData();

    void onLoadPreviousFinish();

    void onManager(IPLVChatPlaybackManager iPLVChatPlaybackManager);
}
