package com.plv.livescenes.hiclass.vo;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVHCChangeLessonRequestVO {
    private String lessonId;
    private int status;

    public String getLessonId() {
        return this.lessonId;
    }

    public int getStatus() {
        return this.status;
    }

    public void setLessonId(String str) {
        this.lessonId = str;
    }

    public void setStatus(int i2) {
        this.status = i2;
    }

    public String toString() {
        return "PLVHCChangeLessonRequestVO{status=" + this.status + ", lessonId='" + this.lessonId + CharPool.SINGLE_QUOTE + '}';
    }
}
