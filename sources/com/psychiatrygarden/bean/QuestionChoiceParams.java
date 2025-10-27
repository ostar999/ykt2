package com.psychiatrygarden.bean;

import com.psychiatrygarden.utils.Constants;

/* loaded from: classes5.dex */
public class QuestionChoiceParams {
    private String answerMode = Constants.ANSWER_MODE.PRACTICE_MODE;
    private String category;
    private String category_id;
    private String chapter_id;
    private String chapter_title;
    private String externalsources;
    private String identity_id;
    private boolean isNewCom;
    private boolean isNewComzantong;
    private String is_show_number;
    private String module_type;
    private int position;
    private com.psychiatrygarden.activity.online.bean.QuestionDetailBean question_bean;
    private String total;
    private String unit_id;

    public String getAnswerMode() {
        return this.answerMode;
    }

    public String getCategory() {
        return this.category;
    }

    public String getCategory_id() {
        return this.category_id;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getChapter_title() {
        return this.chapter_title;
    }

    public String getExternalsources() {
        return this.externalsources;
    }

    public String getIdentity_id() {
        return this.identity_id;
    }

    public String getIs_show_number() {
        return this.is_show_number;
    }

    public String getModule_type() {
        return this.module_type;
    }

    public int getPosition() {
        return this.position;
    }

    public com.psychiatrygarden.activity.online.bean.QuestionDetailBean getQuestion_bean() {
        return this.question_bean;
    }

    public String getTotal() {
        return this.total;
    }

    public String getUnit_id() {
        return this.unit_id;
    }

    public boolean isNewCom() {
        return this.isNewCom;
    }

    public boolean isNewComzantong() {
        return this.isNewComzantong;
    }

    public void setAnswerMode(String answerMode) {
        this.answerMode = answerMode;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public void setExternalsources(String externalsources) {
        this.externalsources = externalsources;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public void setIs_show_number(String is_show_number) {
        this.is_show_number = is_show_number;
    }

    public void setModule_type(String module_type) {
        this.module_type = module_type;
    }

    public void setNewCom(boolean newCom) {
        this.isNewCom = newCom;
    }

    public void setNewComzantong(boolean newComzantong) {
        this.isNewComzantong = newComzantong;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setQuestion_bean(com.psychiatrygarden.activity.online.bean.QuestionDetailBean question_bean) {
        this.question_bean = question_bean;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }
}
