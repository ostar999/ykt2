package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshPaperListEvent {
    private int currentCount;
    private int errorCount;
    private String paperId;
    private int rightCount;

    public RefreshPaperListEvent(String paperId, int errorCount, int rightCount) {
        this.paperId = paperId;
        this.errorCount = errorCount;
        this.rightCount = rightCount;
    }

    public int getCurrentCount() {
        return this.currentCount;
    }

    public int getErrorCount() {
        return this.errorCount;
    }

    public String getPaperId() {
        return this.paperId;
    }

    public int getRightCount() {
        return this.rightCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public void setRightCount(int rightCount) {
        this.rightCount = rightCount;
    }

    public RefreshPaperListEvent(String paperId) {
        this.paperId = paperId;
    }
}
