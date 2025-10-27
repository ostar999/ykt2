package com.plv.livescenes.playback.chat;

/* loaded from: classes5.dex */
public interface IPLVChatPlaybackManager {
    void addOnCallDataListener(IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener);

    void destroy();

    void loadPrevious();

    void removeOnCallDataListener(IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener);

    void seek(int i2);

    void setOnGetDataListener(IPLVChatPlaybackGetDataListener iPLVChatPlaybackGetDataListener);

    void start(String str, String str2);
}
