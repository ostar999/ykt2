package com.plv.livescenes.hiclass.vo;

import androidx.annotation.Nullable;
import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVHCStudentVerifyRequestVO {
    private String clientType = "ANDROID";

    @Nullable
    private String code;
    private String courseCode;
    private Long lessonId;

    @Nullable
    private String name;

    @Nullable
    private String studentCode;

    @Nullable
    public String getCode() {
        return this.code;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public Long getLessonId() {
        return this.lessonId;
    }

    @Nullable
    public String getName() {
        return this.name;
    }

    @Nullable
    public String getStudentCode() {
        return this.studentCode;
    }

    public void setCode(@Nullable String str) {
        this.code = str;
    }

    public void setCourseCode(String str) {
        this.courseCode = str;
    }

    public void setLessonId(Long l2) {
        this.lessonId = l2;
    }

    public void setName(@Nullable String str) {
        this.name = str;
    }

    public void setStudentCode(@Nullable String str) {
        this.studentCode = str;
    }

    public String toString() {
        return "PLVHCStudentVerifyRequestVO{clientType='" + this.clientType + CharPool.SINGLE_QUOTE + ", courseCode='" + this.courseCode + CharPool.SINGLE_QUOTE + ", lessonId=" + this.lessonId + ", name='" + this.name + CharPool.SINGLE_QUOTE + ", code='" + this.code + CharPool.SINGLE_QUOTE + ", studentCode='" + this.studentCode + CharPool.SINGLE_QUOTE + '}';
    }
}
