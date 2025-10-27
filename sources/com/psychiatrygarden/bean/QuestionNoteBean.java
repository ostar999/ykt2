package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionNoteBean implements Serializable {
    private String content;
    private String ctime;
    private String id;
    private List<ImagesBean> img;
    private String question_id;

    public String getContent() {
        return this.content;
    }

    public String getCtime() {
        return this.ctime;
    }

    public String getId() {
        return this.id;
    }

    public List<ImagesBean> getImg() {
        return this.img;
    }

    public String getQuestion_id() {
        return this.question_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImg(List<ImagesBean> img) {
        this.img = img;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }
}
