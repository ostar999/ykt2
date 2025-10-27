package com.plv.business.api.common.player;

import androidx.annotation.NonNull;
import com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer;

/* loaded from: classes4.dex */
public class PLVListenerNotifyerDecorator implements IPLVVideoViewNotifyer {
    private IPLVVideoViewNotifyer videoViewNotifyer;

    public PLVListenerNotifyerDecorator(@NonNull IPLVVideoViewNotifyer iPLVVideoViewNotifyer) {
        this.videoViewNotifyer = iPLVVideoViewNotifyer;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnBufferingUpdate(int i2) {
        this.videoViewNotifyer.notifyOnBufferingUpdate(i2);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnCompletion() {
        this.videoViewNotifyer.notifyOnCompletion();
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public boolean notifyOnError(int i2, int i3) {
        this.videoViewNotifyer.notifyOnError(i2, i3);
        return true;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public boolean notifyOnInfo(int i2, int i3) {
        this.videoViewNotifyer.notifyOnInfo(i2, i3);
        return true;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnPrepared() {
        this.videoViewNotifyer.notifyOnPrepared();
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnPreparing() {
        this.videoViewNotifyer.notifyOnPreparing();
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnSEIRefresh(int i2, byte[] bArr) {
        this.videoViewNotifyer.notifyOnSEIRefresh(i2, bArr);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnSeekComplete() {
        this.videoViewNotifyer.notifyOnSeekComplete();
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnVideoSizeChanged(int i2, int i3, int i4, int i5) {
        this.videoViewNotifyer.notifyOnVideoSizeChanged(i2, i3, i4, i5);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public boolean notifyOnError(PLVPlayError pLVPlayError) {
        this.videoViewNotifyer.notifyOnError(pLVPlayError);
        return true;
    }
}
