package com.plv.business.api.auxiliary;

import android.widget.ImageView;
import com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent;
import com.plv.business.api.common.player.PLVPlayError;
import com.plv.business.api.common.player.PLVVideoViewListener;

/* loaded from: classes4.dex */
public class PLVAuxiliaryVideoViewListener extends PLVVideoViewListener implements IPLVAuxiliaryListenerNotifyer, IPLVAuxiliaryVideoViewListenerBinder {
    private IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener auxliaryVideoViewPlayStatusListener;
    private IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener onAuxiliaryPlayEndListener;
    private IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewCountdownListener onSubVideoViewCountdownListener;
    private IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewLoadImage onSubVideoViewLoadImage;

    @Override // com.plv.business.api.common.player.PLVVideoViewListener
    public void clearAllListener() {
        super.clearAllListener();
        this.auxliaryVideoViewPlayStatusListener = null;
        this.onSubVideoViewCountdownListener = null;
        this.onAuxiliaryPlayEndListener = null;
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryListenerNotifyer
    public void nontifyAuxiliaryonVisibilityChange(boolean z2) {
        IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewCountdownListener iPLVOnSubVideoViewCountdownListener = this.onSubVideoViewCountdownListener;
        if (iPLVOnSubVideoViewCountdownListener != null) {
            iPLVOnSubVideoViewCountdownListener.onVisibilityChange(z2);
        }
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryListenerNotifyer
    public void notifyAuxiliaryOnLoadImage(String str, ImageView imageView, String str2) {
        IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewLoadImage iPLVOnSubVideoViewLoadImage = this.onSubVideoViewLoadImage;
        if (iPLVOnSubVideoViewLoadImage != null) {
            iPLVOnSubVideoViewLoadImage.onLoad(str, imageView, str2);
        }
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryListenerNotifyer
    public void notifyAuxiliaryPlayAfterEndListener() {
        IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener iPLVOnAuxiliaryPlayEndListener = this.onAuxiliaryPlayEndListener;
        if (iPLVOnAuxiliaryPlayEndListener != null) {
            iPLVOnAuxiliaryPlayEndListener.onAfterEnd();
        }
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryListenerNotifyer
    public void notifyAuxiliaryPlayBeforeEndListener(boolean z2) {
        IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener iPLVOnAuxiliaryPlayEndListener = this.onAuxiliaryPlayEndListener;
        if (iPLVOnAuxiliaryPlayEndListener != null) {
            iPLVOnAuxiliaryPlayEndListener.onBeforeEnd(z2);
        }
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryListenerNotifyer
    public void notifyAuxiliaryPlayCompletion(int i2) {
        IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener iPLVAuxliaryVideoViewPlayStatusListener = this.auxliaryVideoViewPlayStatusListener;
        if (iPLVAuxliaryVideoViewPlayStatusListener != null) {
            iPLVAuxliaryVideoViewPlayStatusListener.onCompletion(i2);
        }
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryListenerNotifyer
    public void notifyAuxiliaryPlayError(PLVPlayError pLVPlayError) {
        IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener iPLVAuxliaryVideoViewPlayStatusListener = this.auxliaryVideoViewPlayStatusListener;
        if (iPLVAuxliaryVideoViewPlayStatusListener != null) {
            iPLVAuxliaryVideoViewPlayStatusListener.onError(pLVPlayError);
        }
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryListenerNotifyer
    public void notifyAuxiliaryonCountdown(int i2, int i3, int i4) {
        IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewCountdownListener iPLVOnSubVideoViewCountdownListener = this.onSubVideoViewCountdownListener;
        if (iPLVOnSubVideoViewCountdownListener != null) {
            iPLVOnSubVideoViewCountdownListener.onCountdown(i2, i3, i4);
        }
        IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener iPLVAuxliaryVideoViewPlayStatusListener = this.auxliaryVideoViewPlayStatusListener;
        if (iPLVAuxliaryVideoViewPlayStatusListener != null) {
            iPLVAuxliaryVideoViewPlayStatusListener.onCountdown(i2, i3, i4);
        }
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerBinder
    public void setOnAuxiliaryPlayEndListener(IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener iPLVOnAuxiliaryPlayEndListener) {
        this.onAuxiliaryPlayEndListener = iPLVOnAuxiliaryPlayEndListener;
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerBinder
    public void setOnSubVideoViewCountdownListener(IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewCountdownListener iPLVOnSubVideoViewCountdownListener) {
        this.onSubVideoViewCountdownListener = iPLVOnSubVideoViewCountdownListener;
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerBinder
    public void setOnSubVideoViewLoadImage(IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewLoadImage iPLVOnSubVideoViewLoadImage) {
        this.onSubVideoViewLoadImage = iPLVOnSubVideoViewLoadImage;
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerBinder
    public void setOnSubVideoViewPlayStatusListener(IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener iPLVAuxliaryVideoViewPlayStatusListener) {
        this.auxliaryVideoViewPlayStatusListener = iPLVAuxliaryVideoViewPlayStatusListener;
    }
}
