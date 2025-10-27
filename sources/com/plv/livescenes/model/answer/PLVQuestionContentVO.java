package com.plv.livescenes.model.answer;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVQuestionContentVO implements PLVBaseVO {
    private String answer;
    private String hash;
    private String option1;
    private String option2;
    private String option3;
    private String questionId;
    private String title;
    private String ts;
    private String type;

    public String getAnswer() {
        return this.answer;
    }

    public String getHash() {
        return this.hash;
    }

    public String getOption1() {
        return this.option1;
    }

    public String getOption2() {
        return this.option2;
    }

    public String getOption3() {
        return this.option3;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTs() {
        return this.ts;
    }

    public String getType() {
        return this.type;
    }

    public void setAnswer(String str) {
        this.answer = str;
    }

    public void setHash(String str) {
        this.hash = str;
    }

    public void setOption1(String str) {
        this.option1 = str;
    }

    public void setOption2(String str) {
        this.option2 = str;
    }

    public void setOption3(String str) {
        this.option3 = str;
    }

    public void setQuestionId(String str) {
        this.questionId = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setTs(String str) {
        this.ts = str;
    }

    public void setType(String str) {
        this.type = str;
    }
}
