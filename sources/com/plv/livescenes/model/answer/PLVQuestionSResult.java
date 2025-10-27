package com.plv.livescenes.model.answer;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVQuestionSResult implements PLVBaseVO {
    private String EVENT;
    private String answer;
    private String channelId;
    private String name;
    private String option1;
    private String option2;
    private String questionId;
    private int times;
    private String tips1;
    private String tips2;
    private String type;

    public String getAnswer() {
        return this.answer;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public String getName() {
        return this.name;
    }

    public String getOption1() {
        return this.option1;
    }

    public String getOption2() {
        return this.option2;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public int getTimes() {
        return this.times;
    }

    public String getTips1() {
        return this.tips1;
    }

    public String getTips2() {
        return this.tips2;
    }

    public String getType() {
        return this.type;
    }

    public void setAnswer(String str) {
        this.answer = str;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setOption1(String str) {
        this.option1 = str;
    }

    public void setOption2(String str) {
        this.option2 = str;
    }

    public void setQuestionId(String str) {
        this.questionId = str;
    }

    public void setTimes(int i2) {
        this.times = i2;
    }

    public void setTips1(String str) {
        this.tips1 = str;
    }

    public void setTips2(String str) {
        this.tips2 = str;
    }

    public void setType(String str) {
        this.type = str;
    }
}
