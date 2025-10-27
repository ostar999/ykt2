package com.plv.livescenes.model;

/* loaded from: classes5.dex */
public class PLVInteractiveCallbackVO {
    public static final String EVENT_ANSWER = "ANSWER_TEST_QUESTION";
    public static final String EVENT_LOTTERY = "LOTTERY";
    public static final String EVENT_QUESTIONNAIRE = "ANSWER_QUESTIONNAIRE";
    public static final String EVENT_SIGN = "TO_SIGN_IN";
    private String EVENT;
    private int code;

    public PLVInteractiveCallbackVO(String str, int i2) {
        this.EVENT = str;
        this.code = i2;
    }

    public int getCode() {
        return this.code;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public PLVInteractiveCallbackVO() {
    }
}
