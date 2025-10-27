package com.plv.socket.event.seminar;

import com.plv.foundationsdk.model.PLVFoundationVO;
import com.plv.socket.event.PLVEventConstant;

/* loaded from: classes5.dex */
public class PLVGroupRequestHelpEvent implements PLVFoundationVO {
    private final String EVENT = PLVEventConstant.Seminar.EVENT_GROUP_REQUEST_HELP;

    public String toString() {
        return "PLVGroupRequestHelpEvent{EVENT='groupRequestHelp'}";
    }
}
