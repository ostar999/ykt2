package com.psychiatrygarden.event;

import java.util.List;

/* loaded from: classes5.dex */
public class RefreshCourseDownloadedEvent {
    private List<String> vidList;

    public RefreshCourseDownloadedEvent(List<String> vidList) {
        this.vidList = vidList;
    }

    public List<String> getVidList() {
        return this.vidList;
    }

    public void setVidList(List<String> vidList) {
        this.vidList = vidList;
    }
}
