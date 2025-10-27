package com.hyphenate.easeui.player;

/* loaded from: classes4.dex */
public interface EasyVideoCallback {
    void onBuffering(int i2);

    void onClickVideoFrame(EasyVideoPlayer easyVideoPlayer);

    void onCompletion(EasyVideoPlayer easyVideoPlayer);

    void onError(EasyVideoPlayer easyVideoPlayer, Exception exc);

    void onPaused(EasyVideoPlayer easyVideoPlayer);

    void onPrepared(EasyVideoPlayer easyVideoPlayer);

    void onPreparing(EasyVideoPlayer easyVideoPlayer);

    void onStarted(EasyVideoPlayer easyVideoPlayer);
}
