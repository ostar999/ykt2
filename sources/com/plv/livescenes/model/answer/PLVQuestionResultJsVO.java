package com.plv.livescenes.model.answer;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVQuestionResultJsVO implements PLVBaseVO {
    private String answerId;
    private String data;

    public PLVQuestionResultJsVO(String str, String str2) {
        this.answerId = str;
        this.data = str2;
    }

    public String getAnswerId() {
        return this.answerId;
    }

    public String getData() {
        return this.data;
    }

    public void setAnswerId(String str) {
        this.answerId = str;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String toString() {
        return "{\"answerId\":\"" + this.answerId + "\", \"data\":" + this.data + '}';
    }
}
