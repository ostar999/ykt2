package com.umeng.socialize.bean;

/* loaded from: classes6.dex */
public enum UmengErrorCode {
    UnKnowCode(2000),
    AuthorizeFailed(2002),
    ShareFailed(2003),
    RequestForUserProfileFailed(2004),
    ShareDataNil(2004),
    ShareDataTypeIllegal(2004),
    NotInstall(2008);


    /* renamed from: a, reason: collision with root package name */
    private final int f23662a;

    UmengErrorCode(int i2) {
        this.f23662a = i2;
    }

    private String a() {
        return "错误码：" + this.f23662a + " 错误信息：";
    }

    public String getMessage() {
        if (this == UnKnowCode) {
            return a() + "未知错误----";
        }
        if (this == AuthorizeFailed) {
            return a() + "授权失败----";
        }
        if (this == ShareFailed) {
            return a() + "分享失败----";
        }
        if (this == RequestForUserProfileFailed) {
            return a() + "获取用户资料失败----";
        }
        if (this == ShareDataNil) {
            return a() + "分享内容为空";
        }
        if (this == ShareDataTypeIllegal) {
            return a() + "分享内容不合法----";
        }
        if (this != NotInstall) {
            return "unkonw";
        }
        return a() + "没有安装应用";
    }
}
