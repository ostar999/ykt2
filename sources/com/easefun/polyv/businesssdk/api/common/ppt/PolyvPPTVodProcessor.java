package com.easefun.polyv.businesssdk.api.common.ppt;

import com.plv.business.api.common.ppt.PLVPPTVodProcessor;
import com.plv.business.web.PLVWebview;

@Deprecated
/* loaded from: classes3.dex */
public class PolyvPPTVodProcessor extends PLVPPTVodProcessor {

    public interface PolyvVideoPPTCallback extends PLVPPTVodProcessor.PLVVideoPPTCallback {
    }

    public PolyvPPTVodProcessor(PLVWebview pLVWebview) {
        super(pLVWebview);
    }
}
