package com.plv.business.api.common.mediacontrol;

/* loaded from: classes4.dex */
public interface IPLVMediaController<T> extends IMediaController {
    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    /* synthetic */ void hide();

    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    /* synthetic */ boolean isShowing();

    void onLongBuffering(String str);

    void onPrepared(T t2);

    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    /* synthetic */ void show();
}
