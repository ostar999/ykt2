package com.plv.socket.event.seminar;

import com.plv.foundationsdk.model.PLVFoundationVO;
import com.plv.socket.event.PLVEventConstant;

/* loaded from: classes5.dex */
public class PLVCancelHelpEvent implements PLVFoundationVO {
    private final String EVENT = PLVEventConstant.Seminar.EVENT_CANCEL_HELP;

    public String toString() {
        return "PLVCancelHelpEvent{EVENT='cancelHelp'}";
    }
}
