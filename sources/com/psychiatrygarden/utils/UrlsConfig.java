package com.psychiatrygarden.utils;

import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.db.SharePreferencesUtils;

/* loaded from: classes6.dex */
public class UrlsConfig {
    public static final String HASHCODE = "111111";
    public static final String PHONE_ID = "1";
    public static final String QQ_APPID = "1105244934";
    public static final String QQ_APPSECRET = "I8RNjmGJ4Js2Yrjt";
    public static final String WX_APPID = "wx876cf7af39cb868d";
    public static final String WX_APPSECRET = "752010482c87c824b94b6af0468a1768";
    public static final String shareInvitationCodeUrl = "";
    public static final String shareRegisterCodeUrl = "";
    public static final String shareImageUrl = SharePreferencesUtils.readStrConfig(CommonParameter.SHARE_IMG_URL, ProjectApp.instance(), "http://ykb-experience.oss-cn-hangzhou.aliyuncs.com/experience/shareImages/10.png");
    public static String shareUrl = "https://m.yikaobang.com.cn/download/yikaobang.html";
    public static final String shareTitle = SharePreferencesUtils.readStrConfig(CommonParameter.SHARE_TITLE, ProjectApp.instance(), "医考帮医学考研");
    public static final String shareContent = SharePreferencesUtils.readStrConfig(CommonParameter.SHARE_CONTENT, ProjectApp.instance(), "深度解析一战为何失利？二战如何零风险。");
}
