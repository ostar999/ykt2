package com.plv.livescenes.model.answer;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVJSQuestionVO implements PLVBaseVO {
    private String answerId;
    private String data;
    private String questionId;

    public String getAnswerId() {
        return this.answerId;
    }

    public String getData() {
        return this.data;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public void setAnswerId(String str) {
        this.answerId = str;
    }

    public void setData(String str) {
        this.data = str;
    }

    public void setQuestionId(String str) {
        this.questionId = str;
    }
}
