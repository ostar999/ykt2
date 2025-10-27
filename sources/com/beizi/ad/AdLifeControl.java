package com.beizi.ad;

/* loaded from: classes2.dex */
public interface AdLifeControl {
    void cancel();

    void onCreateLifeCycle();

    void onDestoryLifeCycle();

    void onPauseLifeCycle();

    void onRestartLifeCycle();

    void onResumeLifeCycle();

    void onStartLifeCycle();

    void onStopLifeCycle();

    void openAdInNativeBrowser(boolean z2);
}
