package com.easefun.polyv.mediasdk.example.widget.media;

import android.view.View;
import android.widget.MediaController;

/* loaded from: classes3.dex */
public interface IMediaController {
    void hide();

    boolean isShowing();

    void setAnchorView(View view);

    void setEnabled(boolean z2);

    void setMediaPlayer(MediaController.MediaPlayerControl mediaPlayerControl);

    void show();
}
