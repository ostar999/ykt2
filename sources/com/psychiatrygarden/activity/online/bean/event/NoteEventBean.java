package com.psychiatrygarden.activity.online.bean.event;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class NoteEventBean implements Serializable {
    public String content;
    public boolean img;
    public String question_id;

    public NoteEventBean(String content, boolean img, String question_id) {
        this.content = content;
        this.img = img;
        this.question_id = question_id;
    }
}
