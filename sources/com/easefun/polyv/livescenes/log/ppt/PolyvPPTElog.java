package com.easefun.polyv.livescenes.log.ppt;

import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.livescenes.log.ppt.PLVPPTElog;

@Deprecated
/* loaded from: classes3.dex */
public class PolyvPPTElog extends PLVPPTElog {

    @Deprecated
    public interface PPTEvent extends PLVPPTElog.PPTEvent {
    }

    public PolyvPPTElog(PLVLogFileBase pLVLogFileBase) {
        super(pLVLogFileBase);
    }

    public PolyvPPTElog(PLVLogFileBase pLVLogFileBase, String str) {
        super(pLVLogFileBase, str);
    }
}
