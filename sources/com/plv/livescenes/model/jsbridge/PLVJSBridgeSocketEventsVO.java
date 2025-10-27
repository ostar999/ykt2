package com.plv.livescenes.model.jsbridge;

import com.plv.foundationsdk.model.PLVFoundationVO;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVJSBridgeSocketEventsVO implements PLVFoundationVO {
    private List<String> events;

    public List<String> getEvents() {
        return this.events;
    }

    public void setEvents(List<String> list) {
        this.events = list;
    }
}
