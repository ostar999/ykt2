package com.plv.business.api.common.mediacontrol;

import android.view.View;
import android.widget.MediaController;

/* loaded from: classes4.dex */
interface IMediaController extends com.easefun.polyv.mediasdk.example.widget.media.IMediaController {
    @Override // com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    void hide();

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    boolean isShowing();

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    void setAnchorView(View view);

    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    void setEnabled(boolean z2);

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    @Deprecated
    void setMediaPlayer(MediaController.MediaPlayerControl mediaPlayerControl);

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    void show();
}
