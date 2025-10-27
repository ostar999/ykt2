package com.plv.livescenes.streamer.mix;

import com.plv.linkmic.PLVLinkMicWrapper;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;

/* loaded from: classes5.dex */
public class PLVStreamerMixOpFactory {
    public static IPLVStreamerMixOpManager newInstance(PLVLinkMicDataRepository pLVLinkMicDataRepository, PLVLinkMicWrapper pLVLinkMicWrapper) {
        return new PLVStreamerMixOpFilterDecorator(new PLVStreamerMixOpManagerWrapper(pLVLinkMicDataRepository), pLVLinkMicWrapper, PLVLinkMicConfig.getInstance().getRtcType());
    }
}
