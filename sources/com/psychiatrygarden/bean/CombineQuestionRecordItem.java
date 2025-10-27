package com.psychiatrygarden.bean;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;

/* loaded from: classes5.dex */
public class CombineQuestionRecordItem {

    @SerializedName("answer_count")
    @JsonAdapter(QuestionBankNewBean.String2IntAdapter.class)
    private int current;

    @SerializedName("wrong_count")
    private int errorCount;
    private boolean isEdit;
    private boolean isSelect;
    private String mode;

    @SerializedName("paper_id")
    private String paperId;
    private String question_cate_str;
    private String question_category_id;

    @SerializedName("right_count")
    private int rightCount;

    @SerializedName("create_time")
    private String time;
    private String title;

    @SerializedName("question_count")
    @JsonAdapter(QuestionBankNewBean.String2IntAdapter.class)
    private int totalCount;
    private String type;

    public int getCurrent() {
        return this.current;
    }

    public int getErrorCount() {
        return this.errorCount;
    }

    public String getMode() {
        return this.mode;
    }

    public String getModeText() {
        return "1".equals(this.mode) ? "练习模式" : "测试模式";
    }

    public String getPaperId() {
        return this.paperId;
    }

    public String getQuestion_cate_st() {
        return this.question_cate_str;
    }

    public String getQuestion_cate_str() {
        return this.question_cate_str;
    }

    public String getQuestion_category_id() {
        return this.question_category_id;
    }

    public int getRightCount() {
        return this.rightCount;
    }

    public String getTime() {
        return this.time;
    }

    public String getTitle() {
        return this.title;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public String getType() {
        return this.type;
    }

    public boolean isEdit() {
        return this.isEdit;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setEdit(boolean edit) {
        this.isEdit = edit;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public void setQuestion_cate_st(String question_cate_st) {
        this.question_cate_str = question_cate_st;
    }

    public void setQuestion_cate_str(String question_cate_str) {
        this.question_cate_str = question_cate_str;
    }

    public void setQuestion_category_id(String question_category_id) {
        this.question_category_id = question_category_id;
    }

    public void setRightCount(int rightCount) {
        this.rightCount = rightCount;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setType(String type) {
        this.type = type;
    }
}
