package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVToggleOperationStatusEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "toggleOperationStatus";
    private Boolean clearStatus;
    private Boolean deleteStatus;
    private Boolean redoStatus;
    private Boolean undoStatus;

    public static PLVToggleOperationStatusEvent fromJson(String str) {
        return (PLVToggleOperationStatusEvent) new Gson().fromJson(str, PLVToggleOperationStatusEvent.class);
    }

    public Boolean getClearStatus() {
        return this.clearStatus;
    }

    public Boolean getDeleteStatus() {
        return this.deleteStatus;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public Boolean getRedoStatus() {
        return this.redoStatus;
    }

    public Boolean getUndoStatus() {
        return this.undoStatus;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }
}
