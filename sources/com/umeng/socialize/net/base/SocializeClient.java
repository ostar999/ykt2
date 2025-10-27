package com.umeng.socialize.net.base;

import com.google.android.exoplayer2.C;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.UClient;
import com.umeng.socialize.net.utils.URequest;
import com.umeng.socialize.utils.SLog;

/* loaded from: classes6.dex */
public class SocializeClient extends UClient {
    public SocializeReseponse execute(URequest uRequest) throws InterruptedException {
        if (SocializeConstants.DEBUG_MODE) {
            try {
                Thread.sleep(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            } catch (InterruptedException e2) {
                SLog.error(e2);
            }
        }
        return (SocializeReseponse) super.execute(uRequest, uRequest.mResponseClz);
    }
}
