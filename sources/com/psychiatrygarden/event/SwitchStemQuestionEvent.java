package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class SwitchStemQuestionEvent {
    private boolean nextPage;
    private String publicNumber;

    public SwitchStemQuestionEvent(boolean nextPage, String publicNumber) {
        this.nextPage = nextPage;
        this.publicNumber = publicNumber;
    }

    public String getPublicNumber() {
        return this.publicNumber;
    }

    public boolean isNextPage() {
        return this.nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public void setPublicNumber(String questionId) {
        this.publicNumber = questionId;
    }

    public SwitchStemQuestionEvent(boolean nextPage) {
        this.nextPage = nextPage;
    }
}
