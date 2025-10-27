package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVSetGroupLeaderEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "setGroupLeader";
    private boolean isLeader;

    public PLVSetGroupLeaderEvent(boolean z2) {
        this.isLeader = z2;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public boolean isLeader() {
        return this.isLeader;
    }

    public void setLeader(boolean z2) {
        this.isLeader = z2;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }
}
