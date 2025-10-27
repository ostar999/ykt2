package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshCourseDownloadStateEvent {
    private String courseId;
    private boolean isDownloading;

    public RefreshCourseDownloadStateEvent(boolean isDownloading, String courseId) {
        this.isDownloading = isDownloading;
        this.courseId = courseId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public boolean isDownloading() {
        return this.isDownloading;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setDownloading(boolean downloading) {
        this.isDownloading = downloading;
    }
}
