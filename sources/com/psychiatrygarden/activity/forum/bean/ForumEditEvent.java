package com.psychiatrygarden.activity.forum.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ForumEditEvent implements Serializable {
    public String content;
    public String mEventStr;

    public ForumEditEvent(String mEventStr, String content) {
        this.mEventStr = mEventStr;
        this.content = content;
    }
}
