package com.plv.business.api.auxiliary;

import com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder;

/* loaded from: classes4.dex */
public interface IPLVAuxiliaryVideoViewListenerBinder extends IPLVVideoViewListenerBinder {
    void setOnAuxiliaryPlayEndListener(IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener iPLVOnAuxiliaryPlayEndListener);

    void setOnSubVideoViewCountdownListener(IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewCountdownListener iPLVOnSubVideoViewCountdownListener);

    void setOnSubVideoViewLoadImage(IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewLoadImage iPLVOnSubVideoViewLoadImage);

    void setOnSubVideoViewPlayStatusListener(IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener iPLVAuxliaryVideoViewPlayStatusListener);
}
