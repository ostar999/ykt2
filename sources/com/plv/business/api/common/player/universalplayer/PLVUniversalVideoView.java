package com.plv.business.api.common.player.universalplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.business.api.common.player.PLVBaseVideoView;
import com.plv.business.api.common.player.PLVListenerNotifyerDecorator;
import com.plv.business.api.common.player.PLVPlayerOptionParamVO;
import com.plv.business.api.common.player.PLVVideoViewListener;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes4.dex */
public class PLVUniversalVideoView extends PLVBaseVideoView implements IPLVUniversalVideoView {
    public PLVUniversalVideoView(@NonNull Context context) {
        super(context);
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public boolean canMove() {
        return false;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public Handler createHandler() {
        return null;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public PLVVideoViewListener createListener() {
        return new PLVVideoViewListener();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public IPLVVideoViewNotifyer createNotifyer(IPLVVideoViewNotifyer iPLVVideoViewNotifyer) {
        return new PLVListenerNotifyerDecorator(iPLVVideoViewNotifyer) { // from class: com.plv.business.api.common.player.universalplayer.PLVUniversalVideoView.1
        };
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public String getSDKVersion() {
        return super.getSDKVersion();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public ArrayList<PLVPlayerOptionParamVO> initOptionParameters() {
        return super.initOptionParameters();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isInPlaybackStateEx() {
        return isInPlaybackState();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void onNetWorkRecover() {
        super.onNetWorkRecover();
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnDanmuServerOpenListener(IPLVVideoViewListenerEvent.OnDanmuServerOpenListener onDanmuServerOpenListener) {
        this.plvListener.setOnDanmuServerOpenListener(onDanmuServerOpenListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetLogoListener(IPLVVideoViewListenerEvent.OnGetLogoListener onGetLogoListener) {
        this.plvListener.setOnGetLogoListener(onGetLogoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetMarqueeVoListener(IPLVVideoViewListenerEvent.OnGetMarqueeVoListener onGetMarqueeVoListener) {
        this.plvListener.setOnGetMarqueeVoListener(onGetMarqueeVoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetWatermarkVOListener(IPLVVideoViewListenerEvent.OnGetWatermarkVoListener onGetWatermarkVoListener) {
        this.plvListener.setOnGetWatermarkVOListener(onGetWatermarkVoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnPPTShowListener(IPLVVideoViewListenerEvent.OnPPTShowListener onPPTShowListener) {
        this.plvListener.setOnPPTShowListener(onPPTShowListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoLoadSlowListener(IPLVVideoViewListenerEvent.OnVideoLoadSlowListener onVideoLoadSlowListener) {
        this.plvListener.setOnVideoLoadSlowListener(onVideoLoadSlowListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoViewRestartListener(IPLVVideoViewListenerEvent.OnVideoViewRestart onVideoViewRestart) {
        this.plvListener.setOnVideoViewRestartListener(onVideoViewRestart);
    }

    @Override // com.plv.business.api.common.player.universalplayer.IPLVUniversalVideoView
    public void setVideoPath(String str) {
        setVideoURI(Uri.parse(str));
    }

    @Override // com.plv.business.api.common.player.universalplayer.IPLVUniversalVideoView
    public void setVideoURI(Uri uri) {
        if (prepare(false)) {
            this.ijkVideoView.setVideoURI(uri, this.headers);
        }
    }

    public PLVUniversalVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PLVUniversalVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    @Override // com.plv.business.api.common.player.universalplayer.IPLVUniversalVideoView
    public void setVideoURI(Uri uri, Map<String, String> map) {
        if (prepare(false)) {
            this.ijkVideoView.setVideoURI(uri, map);
        }
    }
}
