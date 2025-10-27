package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class UpdateCommentPraiseEvent {
    private String objId;
    private boolean priase;
    private String questionId;

    public UpdateCommentPraiseEvent(String objId, boolean priase) {
        this.objId = objId;
        this.priase = priase;
    }

    public String getObjId() {
        return this.objId;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public boolean isPriase() {
        return this.priase;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public UpdateCommentPraiseEvent(String objId, String questionId, boolean priase) {
        this.objId = objId;
        this.priase = priase;
        this.questionId = questionId;
    }

    public UpdateCommentPraiseEvent(String objId) {
        this.objId = objId;
    }
}
