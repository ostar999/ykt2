package com.plv.livescenes.hiclass.vo;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVHCTeacherLoginRequestVO {
    private final String clientType = "ANDROID";
    private String code;
    private Long lessonId;
    private String mobile;
    private String passwd;
    private String userId;

    public String getCode() {
        return this.code;
    }

    public Long getLessonId() {
        return this.lessonId;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public void setLessonId(Long l2) {
        this.lessonId = l2;
    }

    public void setMobile(String str) {
        this.mobile = str;
    }

    public void setPasswd(String str) {
        this.passwd = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVHCTeacherLoginRequestVO{code='" + this.code + CharPool.SINGLE_QUOTE + ", lessonId='" + this.lessonId + CharPool.SINGLE_QUOTE + ", mobile='" + this.mobile + CharPool.SINGLE_QUOTE + ", passwd='" + this.passwd + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + '}';
    }
}
