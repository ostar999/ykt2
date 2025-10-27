package com.aliyun.player.alivcplayerexpand.widget;

import android.view.Surface;
import android.view.View;

/* loaded from: classes2.dex */
public interface IRenderView {

    public interface IRenderCallback {
        void onSurfaceChanged(int i2, int i3);

        void onSurfaceCreate(Surface surface);

        void onSurfaceDestroyed();
    }

    void addRenderCallback(IRenderCallback iRenderCallback);

    View getView();
}
