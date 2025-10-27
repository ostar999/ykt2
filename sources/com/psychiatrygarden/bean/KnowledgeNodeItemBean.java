package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes5.dex */
public class KnowledgeNodeItemBean {
    private String accuracy;
    private String activity_id;
    private String answer_count;
    private String category;
    private String category_str;
    private boolean continueDo;
    private String frequency;
    private String frequency_str;
    private String id;

    @SerializedName("have_stat")
    private String is_do;
    private String is_permission;
    private String label;
    private String name;
    private String question_count;
    private String star;
    private String star_str;

    public String getAccuracy() {
        return this.accuracy;
    }

    public String getActivity_id() {
        return this.activity_id;
    }

    public String getAnswer_count() {
        return this.answer_count;
    }

    public String getCategory() {
        return this.category;
    }

    public String getCategory_str() {
        return this.category_str;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public String getFrequency_str() {
        return this.frequency_str;
    }

    public String getId() {
        return this.id;
    }

    public String getIs_do() {
        return this.is_do;
    }

    public String getIs_permission() {
        return this.is_permission;
    }

    public String getLabel() {
        return this.label;
    }

    public String getName() {
        return this.name;
    }

    public String getQuestion_count() {
        return this.question_count;
    }

    public String getStar() {
        return this.star;
    }

    public String getStar_str() {
        return this.star_str;
    }

    public boolean isContinueDo() {
        return this.continueDo;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public void setAnswer_count(String answer_count) {
        this.answer_count = answer_count;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategory_str(String category_str) {
        this.category_str = category_str;
    }

    public void setContinueDo(boolean continueDo) {
        this.continueDo = continueDo;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setFrequency_str(String frequency_str) {
        this.frequency_str = frequency_str;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIs_do(String is_do) {
        this.is_do = is_do;
    }

    public void setIs_permission(String is_permission) {
        this.is_permission = is_permission;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestion_count(String question_count) {
        this.question_count = question_count;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public void setStar_str(String star_str) {
        this.star_str = star_str;
    }
}
