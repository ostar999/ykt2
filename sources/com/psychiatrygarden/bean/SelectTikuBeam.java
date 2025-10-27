package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class SelectTikuBeam implements Serializable {
    private String chapter_id;
    private String number_number;
    private String question_id;
    private String unit;

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getNumber_number() {
        return this.number_number;
    }

    public String getQuestion_id() {
        return this.question_id;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setNumber_number(String number_number) {
        this.number_number = number_number;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
