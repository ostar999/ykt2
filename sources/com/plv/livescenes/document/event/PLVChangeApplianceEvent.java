package com.plv.livescenes.document.event;

import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVChangeApplianceEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "changeAppliance";
    private Appliance appliance;

    public enum Appliance {
        ARROW("arrow"),
        CHOICE("choice"),
        ELLIPSE("ellipse"),
        ERASER(PLVDocumentMarkToolType.ERASER),
        FREE_LINE("freeLine"),
        LASER_PEN("laserPen"),
        MOVE("move"),
        POLYGON("polygon"),
        RECT("rect"),
        STRAIGHT_LINE("straightLine"),
        TEXT("text");

        private final String applianceName;

        Appliance(String str) {
            this.applianceName = str;
        }
    }

    public PLVChangeApplianceEvent() {
    }

    public static PLVChangeApplianceEvent fromJson(String str) {
        return (PLVChangeApplianceEvent) new Gson().fromJson(str, PLVChangeApplianceEvent.class);
    }

    public Appliance getAppliances() {
        return this.appliance;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public PLVChangeApplianceEvent setAppliances(Appliance appliance) {
        this.appliance = appliance;
        return this;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }

    public PLVChangeApplianceEvent(Appliance appliance) {
        this.appliance = appliance;
    }
}
