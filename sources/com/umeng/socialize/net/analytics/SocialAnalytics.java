package com.umeng.socialize.net.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.am;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.net.dplus.DplusApi;
import com.umeng.socialize.net.utils.URequest;
import com.umeng.socialize.net.verify.VerifyReqeust;
import com.umeng.socialize.net.verify.VerifyResponse;
import com.umeng.socialize.uploadlog.UMLog;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeSpUtils;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class SocialAnalytics {

    /* renamed from: a, reason: collision with root package name */
    private static SocializeClient f23753a = new SocializeClient();

    /* renamed from: b, reason: collision with root package name */
    private static ExecutorService f23754b = Executors.newCachedThreadPool();

    public static void authendt(Context context, SHARE_MEDIA share_media, String str, boolean z2, String str2, String str3, Map<String, String> map) {
        DplusApi.uploadAuthend(context, share_media, str3, str, str2);
        if (map != null) {
            DplusApi.uploadAuth(context, map, z2, share_media, str3);
        }
        if (map != null) {
            a(context, share_media.toString().toLowerCase(), map);
        }
        verifyStats(context);
    }

    public static void authstart(Context context, SHARE_MEDIA share_media, String str, boolean z2, String str2) {
        DplusApi.uploadAuthStart(context, z2, share_media, str2);
    }

    public static void dauStats(Context context, boolean z2) {
        Bundle shareAndAuth = UMLog.getShareAndAuth();
        int i2 = (shareAndAuth.getBoolean("isjump") ? 33554432 : 0) | (shareAndAuth.getBoolean("share") ? 536870912 : 0) | (shareAndAuth.getBoolean("auth") ? 268435456 : 0) | (UMLog.isOpenShareEdit() ? 16777216 : 0);
        String shareBoardConfig = SocializeSpUtils.getShareBoardConfig(context);
        HashMap map = new HashMap();
        if (TextUtils.isEmpty(shareBoardConfig)) {
            DplusApi.uploadStatsDAU(context, null, i2);
            return;
        }
        String[] strArrSplit = shareBoardConfig.split(h.f3376b);
        if (strArrSplit.length == 2) {
            String str = strArrSplit[0];
            String str2 = strArrSplit[1];
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                return;
            }
            map.put("position", strArrSplit[1]);
            map.put(CommonNetImpl.MENUBG, strArrSplit[0]);
            DplusApi.uploadStatsDAU(context, map, i2);
        }
    }

    public static void getInfoendt(Context context, SHARE_MEDIA share_media, String str, String str2, String str3, Map<String, String> map) {
        DplusApi.uploadInfoend(context, share_media, str3, str, str2);
        if (map != null) {
            DplusApi.uploadUserInfo(context, map, share_media, str3);
        }
        if (map != null) {
            a(context, share_media.toString().toLowerCase(), map);
        }
        verifyStats(context);
    }

    public static void getInfostart(Context context, SHARE_MEDIA share_media, String str) {
        DplusApi.uploadInfoStart(context, share_media, str);
    }

    public static void log(final Context context, final String str, final String str2, final UMediaObject uMediaObject) {
        a(new Runnable() { // from class: com.umeng.socialize.net.analytics.SocialAnalytics.1
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsReqeust analyticsReqeust = new AnalyticsReqeust(context, str, str2);
                analyticsReqeust.setMedia(uMediaObject);
                analyticsReqeust.setReqType(1);
                AnalyticsResponse analyticsResponse = (AnalyticsResponse) SocialAnalytics.f23753a.execute(analyticsReqeust);
                if (analyticsResponse == null || !analyticsResponse.isOk()) {
                    SLog.debug(UmengText.NET.SHARESELFFAIL);
                } else {
                    SLog.debug(UmengText.NET.SHARESELFOK);
                }
            }
        });
    }

    public static void shareend(Context context, SHARE_MEDIA share_media, String str, String str2, String str3) {
        DplusApi.uploadStatsShareEnd(context, share_media, str3, str, str2);
    }

    public static void verifyStats(final Context context) {
        Set<String> stringSet = context.getSharedPreferences("umeng_verify", 0).getStringSet("verify_log", null);
        if (stringSet == null || stringSet.isEmpty()) {
            return;
        }
        a(new Runnable() { // from class: com.umeng.socialize.net.analytics.SocialAnalytics.2
            @Override // java.lang.Runnable
            public void run() {
                VerifyResponse verifyResponse = (VerifyResponse) SocialAnalytics.f23753a.execute(new VerifyReqeust(context, "https://ai.login.umeng.com/api/umed/event", VerifyResponse.class, URequest.RequestMethod.POST));
                if (verifyResponse == null || !verifyResponse.isOk()) {
                    SLog.debug("VerifyReqeust Fail");
                    return;
                }
                SLog.debug("VerifyReqeust Success");
                SharedPreferences.Editor editorEdit = context.getSharedPreferences("umeng_verify", 0).edit();
                editorEdit.remove("verify_log");
                editorEdit.commit();
            }
        });
    }

    private static void a(Context context, String str, Map<String, String> map) throws JSONException {
        if ("weixin".equals(str) || "sina".equals(str) || "qq".equals(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                String appkey = SocializeUtils.getAppkey(context);
                if (!TextUtils.isEmpty(appkey)) {
                    jSONObject.put("appkey", appkey);
                }
                String upperCase = Build.MODEL.replaceAll("\\s", "-").toUpperCase();
                if (!TextUtils.isEmpty(upperCase)) {
                    jSONObject.put("device_model", upperCase);
                }
                jSONObject.put("os", "android");
                String osVersion = DeviceConfig.getOsVersion();
                if (!TextUtils.isEmpty(osVersion)) {
                    jSONObject.put("os_version", osVersion);
                }
                jSONObject.put("sdk_version", "7.1.7");
                String uMId = UMUtils.getUMId(context);
                if (!TextUtils.isEmpty(uMId)) {
                    jSONObject.put("umid", uMId);
                }
                String str2 = DeviceConfig.getNetworkAccessMode(context)[0];
                if (!TextUtils.isEmpty(str2)) {
                    jSONObject.put(am.Q, str2);
                }
                String str3 = DeviceConfig.getNetworkAccessMode(context)[1];
                if (!TextUtils.isEmpty(str3)) {
                    jSONObject.put(am.R, str3);
                }
                jSONObject.put(am.P, "");
                String deviceId = DeviceConfig.getDeviceId(context);
                if (!TextUtils.isEmpty(deviceId)) {
                    jSONObject.put("device_id_type", "0");
                    jSONObject.put("device_id", deviceId);
                } else if (TextUtils.isEmpty("")) {
                    String androidID = DeviceConfig.getAndroidID(context);
                    if (!TextUtils.isEmpty(androidID)) {
                        jSONObject.put("device_id_type", "2");
                        jSONObject.put("device_id", androidID);
                    }
                } else {
                    jSONObject.put("device_id_type", "1");
                    jSONObject.put("device_id", "");
                }
                if ("weixin".equals(str)) {
                    jSONObject.put(SocialConstants.PARAM_SOURCE, "wechat");
                } else if ("sina".equals(str)) {
                    jSONObject.put(SocialConstants.PARAM_SOURCE, "weibo");
                } else {
                    jSONObject.put(SocialConstants.PARAM_SOURCE, str);
                }
                HashMap map2 = new HashMap();
                String str4 = map.get(CommonNetImpl.AID);
                if (!TextUtils.isEmpty(str4)) {
                    map2.put("appId", str4);
                }
                String str5 = map.get("openid");
                if (TextUtils.isEmpty(str5)) {
                    str5 = map.get("uid");
                }
                if (!TextUtils.isEmpty(str5)) {
                    map2.put("openId", str5);
                }
                String str6 = map.get("unionid");
                if (!TextUtils.isEmpty(str6)) {
                    str5 = str6;
                }
                if (!TextUtils.isEmpty(str5)) {
                    map2.put("unionId", str5);
                }
                String str7 = map.get("name");
                if (!TextUtils.isEmpty(str7)) {
                    map2.put("nickName", str7);
                }
                String str8 = map.get("gender");
                if (!TextUtils.isEmpty(str8)) {
                    if ("男".equals(str8)) {
                        map2.put(CommonNetImpl.SEX, "0");
                    } else if ("女".equals(str8)) {
                        map2.put(CommonNetImpl.SEX, "1");
                    } else {
                        map2.put(CommonNetImpl.SEX, str8);
                    }
                }
                jSONObject.put(AliyunLogCommon.LogLevel.INFO, new JSONObject(map2.toString()));
                jSONObject.put("timestamp", String.valueOf(System.currentTimeMillis()));
                Set<String> stringSet = context.getSharedPreferences("umeng_verify", 0).getStringSet("verify_log", null);
                HashSet hashSet = new HashSet();
                if (stringSet != null && !stringSet.isEmpty()) {
                    Iterator<String> it = stringSet.iterator();
                    while (it.hasNext()) {
                        hashSet.add(it.next());
                    }
                }
                hashSet.add(jSONObject.toString());
                SharedPreferences.Editor editorEdit = context.getSharedPreferences("umeng_verify", 0).edit();
                editorEdit.putStringSet("verify_log", hashSet);
                editorEdit.commit();
            } catch (Exception unused) {
            }
        }
    }

    private static void a(Runnable runnable) {
        ExecutorService executorService = f23754b;
        if (executorService == null || runnable == null) {
            return;
        }
        executorService.execute(runnable);
    }
}
