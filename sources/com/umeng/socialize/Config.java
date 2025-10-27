package com.umeng.socialize;

/* loaded from: classes6.dex */
public class Config {
    public static String Descriptor = null;
    public static String EntityKey = "-1";
    public static String EntityName = "share";

    @Deprecated
    public static int KaKaoLoginType = 0;

    @Deprecated
    public static int LinkedInProfileScope = 0;

    @Deprecated
    public static int LinkedInShareCode = 0;
    public static final int MINIPTOGRAM_TYPE_RELEASE = 0;
    public static String MORE_TITLE = null;

    @Deprecated
    public static boolean OpenEditor = true;

    @Deprecated
    public static String QQAPPNAME = null;

    @Deprecated
    public static int QQWITHQZONE = 0;
    public static String SessionId = null;

    /* renamed from: a, reason: collision with root package name */
    private static int f23560a = 0;

    @Deprecated
    public static String appName = null;
    public static int connectionTimeOut = 0;
    public static boolean isFacebookRead = false;
    public static boolean isJumptoAppStore = false;

    @Deprecated
    public static boolean isNeedAuth = false;
    public static Boolean isUmengQQ = null;
    public static Boolean isUmengSina = null;
    public static Boolean isUmengWx = null;
    public static final boolean mEncrypt = false;
    public static int readSocketTimeOut;
    public static String shareType;

    static {
        Boolean bool = Boolean.TRUE;
        isUmengSina = bool;
        isUmengWx = bool;
        isUmengQQ = bool;
        Descriptor = "com.umeng.share";
        SessionId = null;
        QQWITHQZONE = 2;
        QQAPPNAME = "";
        shareType = "native";
        KaKaoLoginType = 0;
        MORE_TITLE = "分享";
        LinkedInProfileScope = 0;
        LinkedInShareCode = 0;
        connectionTimeOut = 30000;
        readSocketTimeOut = 30000;
        isNeedAuth = false;
        isJumptoAppStore = false;
        isFacebookRead = false;
        f23560a = 0;
    }

    public static int getMINITYPE() {
        return f23560a;
    }

    public static void setMiniPreView() {
        f23560a = 2;
    }

    public static void setMiniTest() {
        f23560a = 1;
    }
}
