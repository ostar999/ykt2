package com.easefun.polyv.livecommon.module.modules.player;

import android.view.View;
import android.widget.MediaController;
import com.easefun.polyv.businesssdk.api.common.meidacontrol.IPolyvMediaController;
import com.easefun.polyv.livescenes.video.PolyvLiveVideoView;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
public class PLVEmptyMediaController implements IPolyvMediaController<PolyvLiveVideoView> {
    private static final String TAG = "PLVEmptyMediaController";

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void hide() {
        PLVCommonLog.d(TAG, "hide");
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public boolean isShowing() {
        return false;
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController
    public void onLongBuffering(String tip) {
        PLVCommonLog.d(TAG, "onLongBuffering");
    }

    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void setAnchorView(View view) {
        PLVCommonLog.d(TAG, "setAnchorView");
    }

    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void setEnabled(boolean enabled) {
        PLVCommonLog.d(TAG, "setEnabled:" + enabled);
    }

    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void setMediaPlayer(MediaController.MediaPlayerControl player) {
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void show() {
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController
    public void onPrepared(PolyvLiveVideoView mp) {
        PLVCommonLog.d(TAG, "onPrepared");
    }
}
