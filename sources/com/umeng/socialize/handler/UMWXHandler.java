package com.umeng.socialize.handler;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.StringName;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.media.WeiXinShareContent;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText;
import com.umeng.socialize.utils.UrlUtil;
import com.umeng.socialize.weixin.net.WXAuthUtils;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class UMWXHandler extends UMSSOHandler {
    private static final String ERRMSG = "errmsg";
    private static final String ERRORCODE = "errcode";
    private static final String ERROR_CODE_TOKEN_ACCESS_FAIL = "42002";
    private static final String ERROR_CODE_TOKEN_FAIL = "40001";
    private static final String ERROR_CODE_TOKEN_REFESH_FAIL = "40030";
    private static final String HEADIMGURL = "headimgurl";
    private static final String LANGUAGE = "language";
    private static final String NICKNAME = "nickname";
    private static final String PRIVILEGE = "privilege";
    private static final int REFRESH_TOKEN_EXPIRES = 604800;
    private static final String REFRESH_TOKEN_EXPIRES_KEY = "refresh_token_expires";
    private static final int RESP_TYPE_AUTH = 1;
    private static final int RESP_TYPE_SHARE = 2;
    private static final String SEX = "sex";
    private static final String TAG = "UMWXHandler";
    private static String sScope = "snsapi_userinfo,snsapi_friend,snsapi_message";
    private PlatformConfig.APPIDPlatform config;
    private Context mAppContext;
    private UMAuthListener mAuthListener;
    private String mFileProvider;
    private IWXAPI mWXApi;
    private UMShareListener umShareListener;
    private WeixinPreferences weixinPreferences;
    private String VERSION = "7.1.7";
    private SHARE_MEDIA mTarget = SHARE_MEDIA.WEIXIN;
    private IWXAPIEventHandler mEventHandler = new IWXAPIEventHandler() { // from class: com.umeng.socialize.handler.UMWXHandler.17
        @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
        public void onReq(BaseReq baseReq) {
        }

        @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
        public void onResp(BaseResp baseResp) throws PackageManager.NameNotFoundException {
            int type = baseResp.getType();
            if (type == 1) {
                UMWXHandler.this.onAuthCallback((SendAuth.Resp) baseResp);
            } else {
                if (type != 2) {
                    return;
                }
                UMWXHandler.this.onShareCallback((SendMessageToWX.Resp) baseResp);
            }
        }
    };

    /* renamed from: com.umeng.socialize.handler.UMWXHandler$18, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass18 {
        static final /* synthetic */ int[] $SwitchMap$com$umeng$socialize$bean$SHARE_MEDIA;

        static {
            int[] iArr = new int[SHARE_MEDIA.values().length];
            $SwitchMap$com$umeng$socialize$bean$SHARE_MEDIA = iArr;
            try {
                iArr[SHARE_MEDIA.WEIXIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$umeng$socialize$bean$SHARE_MEDIA[SHARE_MEDIA.WEIXIN_CIRCLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$umeng$socialize$bean$SHARE_MEDIA[SHARE_MEDIA.WEIXIN_FAVORITE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private String buildTransaction(String str) {
        if (str == null) {
            return String.valueOf(System.currentTimeMillis());
        }
        return str + System.currentTimeMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchUserInfo(final UMAuthListener uMAuthListener) throws IOException {
        String openid = getOpenid();
        final String strRequest = WXAuthUtils.request("https://api.weixin.qq.com/sns/userinfo?access_token=" + getAccessToken() + "&openid=" + openid + "&lang=zh_CN");
        if (TextUtils.isEmpty(strRequest) || strRequest.startsWith("##")) {
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.6
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler.this.getAuthListener(uMAuthListener).onError(SHARE_MEDIA.WEIXIN, 2, new Throwable(UmengErrorCode.RequestForUserProfileFailed.getMessage() + strRequest));
                }
            });
            return;
        }
        final Map<String, String> userInfo = parseUserInfo(strRequest);
        if (userInfo == null) {
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.7
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler.this.getAuthListener(uMAuthListener).onError(SHARE_MEDIA.WEIXIN, 2, new Throwable(UmengErrorCode.RequestForUserProfileFailed.getMessage() + strRequest));
                }
            });
            return;
        }
        if (!userInfo.containsKey(ERRORCODE)) {
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.9
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler.this.getAuthListener(uMAuthListener).onComplete(SHARE_MEDIA.WEIXIN, 2, userInfo);
                }
            });
        } else if (!userInfo.get(ERRORCODE).equals(ERROR_CODE_TOKEN_FAIL)) {
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.8
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler.this.getAuthListener(uMAuthListener).onError(SHARE_MEDIA.WEIXIN, 2, new Throwable(UmengErrorCode.RequestForUserProfileFailed.getMessage() + ((String) userInfo.get(UMWXHandler.ERRORCODE))));
                }
            });
        } else {
            weixinPreferencesDelete();
            authorize(uMAuthListener);
        }
    }

    private String getAccessToken() {
        WeixinPreferences weixinPreferences = this.weixinPreferences;
        return weixinPreferences != null ? weixinPreferences.getAccessToken() : "";
    }

    private void getAuthWithCode(String str, final UMAuthListener uMAuthListener) throws PackageManager.NameNotFoundException {
        final StringBuilder sb = new StringBuilder();
        String str2 = this.config.appkey;
        if (str2 != null && !str2.isEmpty()) {
            sb.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
            sb.append("appid=");
            sb.append(this.config.appId);
            sb.append("&secret=");
            sb.append(this.config.appkey);
            sb.append("&code=");
            sb.append(str);
            sb.append("&grant_type=authorization_code");
            QueuedWork.runInBack(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.3
                @Override // java.lang.Runnable
                public void run() throws IOException {
                    String strRequest = WXAuthUtils.request(sb.toString());
                    try {
                        final Map<String, String> mapJsonToMap = SocializeUtils.jsonToMap(strRequest);
                        if (mapJsonToMap == null || mapJsonToMap.size() == 0) {
                            UMWXHandler.this.getMap();
                        }
                        UMWXHandler.this.setBundle(UMWXHandler.this.parseAuthData(strRequest));
                        QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (mapJsonToMap.get(UMWXHandler.ERRORCODE) != null) {
                                    AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                                    UMWXHandler.this.getAuthListener(uMAuthListener).onError(SHARE_MEDIA.WEIXIN, 0, new Throwable(UmengErrorCode.AuthorizeFailed.getMessage() + ((String) mapJsonToMap.get(UMWXHandler.ERRMSG))));
                                } else {
                                    AnonymousClass3 anonymousClass32 = AnonymousClass3.this;
                                    UMWXHandler.this.getAuthListener(uMAuthListener).onComplete(SHARE_MEDIA.WEIXIN, 0, mapJsonToMap);
                                }
                                mapJsonToMap.put(CommonNetImpl.AID, UMWXHandler.this.config.appId);
                                mapJsonToMap.put("as", UMWXHandler.this.config.appkey);
                                Map map = mapJsonToMap;
                                map.put("uid", map.get("openid"));
                                Map map2 = mapJsonToMap;
                                map2.put("unionid", map2.get("unionid"));
                            }
                        });
                    } catch (Exception e2) {
                        SLog.error(e2);
                    }
                }
            }, true);
            return;
        }
        sb.append("https://oauth2.umeng.com/oauth/token/acquire?");
        String appkey = SocializeUtils.getAppkey(getContext());
        sb.append("appkey=");
        sb.append(appkey);
        sb.append("&source=");
        sb.append("wechat");
        sb.append("&appId=");
        sb.append(this.config.appId);
        sb.append("&code=");
        sb.append(str);
        QueuedWork.runInBack(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.4
            @Override // java.lang.Runnable
            public void run() throws JSONException, IOException {
                String strRequest = WXAuthUtils.request(sb.toString());
                try {
                    final HashMap map = new HashMap();
                    JSONObject jSONObjectJsonObjectExt = SocializeUtils.jsonObjectExt(strRequest);
                    if (jSONObjectJsonObjectExt != null) {
                        if (jSONObjectJsonObjectExt.getInt("code") == 200) {
                            jSONObjectJsonObjectExt = jSONObjectJsonObjectExt.getJSONObject("data");
                        }
                        Iterator<String> itKeys = jSONObjectJsonObjectExt.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            map.put(next, jSONObjectJsonObjectExt.get(next) + "");
                        }
                    }
                    if (map.size() == 0) {
                        UMWXHandler.this.getMap();
                    }
                    if (jSONObjectJsonObjectExt != null) {
                        UMWXHandler.this.setBundle(UMWXHandler.this.parseAuthData(jSONObjectJsonObjectExt.toString()));
                    }
                    QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (map.get(UMWXHandler.ERRORCODE) == null && map.get("code") == null) {
                                AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                                UMWXHandler.this.getAuthListener(uMAuthListener).onComplete(SHARE_MEDIA.WEIXIN, 0, map);
                            } else {
                                Throwable th = new Throwable(UmengErrorCode.AuthorizeFailed.getMessage() + ((String) map.get(UMWXHandler.ERRMSG)));
                                AnonymousClass4 anonymousClass42 = AnonymousClass4.this;
                                UMWXHandler.this.getAuthListener(uMAuthListener).onError(SHARE_MEDIA.WEIXIN, 0, th);
                            }
                            map.put(CommonNetImpl.AID, UMWXHandler.this.config.appId);
                            map.put("as", UMWXHandler.this.config.appkey);
                            Map map2 = map;
                            map2.put("uid", map2.get("openid"));
                            Map map3 = map;
                            map3.put("unionid", map3.get("unionid"));
                        }
                    });
                } catch (Exception e2) {
                    SLog.error(e2);
                }
            }
        }, true);
    }

    private Map<String, String> getAuthWithRefreshToken(String str) {
        Map<String, String> mapJsonToMap;
        try {
            mapJsonToMap = SocializeUtils.jsonToMap(WXAuthUtils.request("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + this.config.appId + "&grant_type=refresh_token&refresh_token=" + str));
            try {
                mapJsonToMap.put("unionid", getUid());
            } catch (Exception e2) {
                e = e2;
                SLog.error(e);
                return mapJsonToMap;
            }
        } catch (Exception e3) {
            e = e3;
            mapJsonToMap = null;
        }
        return mapJsonToMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, String> getMap() {
        WeixinPreferences weixinPreferences = this.weixinPreferences;
        if (weixinPreferences != null) {
            return weixinPreferences.getmap();
        }
        return null;
    }

    private String getOpenid() {
        WeixinPreferences weixinPreferences = this.weixinPreferences;
        return weixinPreferences != null ? weixinPreferences.getmOpenid() : "";
    }

    private String getRefreshToken() {
        WeixinPreferences weixinPreferences = this.weixinPreferences;
        return weixinPreferences != null ? weixinPreferences.getRefreshToken() : "";
    }

    private String getUid() {
        WeixinPreferences weixinPreferences = this.weixinPreferences;
        return weixinPreferences != null ? weixinPreferences.getUID() : "";
    }

    private long getmAccessTokenTTL() {
        WeixinPreferences weixinPreferences = this.weixinPreferences;
        if (weixinPreferences != null) {
            return weixinPreferences.getmAccessTokenTTL();
        }
        return 0L;
    }

    private boolean isAbleShareEmoji(SHARE_MEDIA share_media, WeiXinShareContent weiXinShareContent) {
        if (weiXinShareContent.getmStyle() == 64) {
            return (share_media == SHARE_MEDIA.WEIXIN_CIRCLE || share_media == SHARE_MEDIA.WEIXIN_FAVORITE) ? false : true;
        }
        return true;
    }

    private boolean isAccessTokenAvailable() {
        WeixinPreferences weixinPreferences = this.weixinPreferences;
        if (weixinPreferences != null) {
            return weixinPreferences.isAccessTokenAvailable();
        }
        return false;
    }

    private boolean isAuthValid() {
        WeixinPreferences weixinPreferences = this.weixinPreferences;
        if (weixinPreferences != null) {
            return weixinPreferences.isAuthValid();
        }
        return false;
    }

    private void loadOauthData(String str) {
        setBundle(parseAuthData(WXAuthUtils.request(str)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAuthCallback(SendAuth.Resp resp) throws PackageManager.NameNotFoundException {
        int i2 = resp.errCode;
        if (i2 == 0) {
            getAuthWithCode(resp.code, this.mAuthListener);
            return;
        }
        if (i2 == -2) {
            getAuthListener(this.mAuthListener).onCancel(SHARE_MEDIA.WEIXIN, 0);
            return;
        }
        if (i2 == -6) {
            getAuthListener(this.mAuthListener).onError(SHARE_MEDIA.WEIXIN, 0, new Throwable(UmengErrorCode.AuthorizeFailed.getMessage() + UmengText.errorWithUrl(UmengText.AUTH.AUTH_DENIED, UrlUtil.WX_ERROR_SIGN)));
            return;
        }
        CharSequence charSequenceConcat = TextUtils.concat("weixin auth error (", String.valueOf(i2), "):", resp.errStr);
        getAuthListener(this.mAuthListener).onError(SHARE_MEDIA.WEIXIN, 0, new Throwable(UmengErrorCode.AuthorizeFailed.getMessage() + ((Object) charSequenceConcat)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle parseAuthData(String str) {
        Bundle bundle = new Bundle();
        if (TextUtils.isEmpty(str)) {
            return bundle;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                bundle.putString(next, jSONObject.optString(next));
            }
            bundle.putLong(REFRESH_TOKEN_EXPIRES_KEY, 604800L);
            bundle.putString("accessToken", bundle.getString("access_token"));
            bundle.putString("expiration", bundle.getString("expires_in"));
            bundle.putString("refreshToken", bundle.getString("refresh_token"));
            bundle.putString("uid", bundle.getString("unionid"));
        } catch (JSONException e2) {
            SLog.error(e2);
        }
        return bundle;
    }

    private Map<String, String> parseUserInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return Collections.emptyMap();
        }
        HashMap map = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(ERRORCODE)) {
                map.put(ERRORCODE, jSONObject.getString(ERRORCODE));
                map.put(ERRMSG, jSONObject.getString(ERRMSG));
                return map;
            }
            map.put("openid", jSONObject.optString("openid"));
            map.put("screen_name", jSONObject.optString("nickname"));
            map.put("name", jSONObject.optString("nickname"));
            map.put("language", jSONObject.optString("language"));
            map.put("city", jSONObject.optString("city"));
            map.put("province", jSONObject.optString("province"));
            map.put(am.O, jSONObject.optString(am.O));
            map.put("profile_image_url", jSONObject.optString(HEADIMGURL));
            map.put("iconurl", jSONObject.optString(HEADIMGURL));
            map.put("unionid", jSONObject.optString("unionid"));
            map.put("uid", jSONObject.optString("unionid"));
            map.put("gender", getGender(jSONObject.optString("sex")));
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(PRIVILEGE);
            int length = jSONArrayOptJSONArray == null ? 0 : jSONArrayOptJSONArray.length();
            if (length > 0) {
                String[] strArr = new String[length];
                for (int i2 = 0; i2 < length; i2++) {
                    strArr[i2] = jSONArrayOptJSONArray.get(i2).toString();
                }
                map.put(PRIVILEGE, strArr.toString());
            }
            map.put("access_token", getAccessToken());
            map.put("refreshToken", getRefreshToken());
            map.put("expires_in", String.valueOf(getmAccessTokenTTL()));
            map.put("accessToken", getAccessToken());
            map.put("refreshToken", getRefreshToken());
            map.put("expiration", String.valueOf(getmAccessTokenTTL()));
            return map;
        } catch (JSONException e2) {
            SLog.error(e2);
            return Collections.emptyMap();
        }
    }

    private void runInMainThread(Runnable runnable) {
        QueuedWork.runInMain(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBundle(Bundle bundle) {
        WeixinPreferences weixinPreferences = this.weixinPreferences;
        if (weixinPreferences != null) {
            weixinPreferences.setBundle(bundle).commit();
        }
    }

    private boolean shareTo(WeiXinShareContent weiXinShareContent) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(weiXinShareContent.getStrStyle());
        req.message = weiXinShareContent.getWxMediaMessage(this.mAppContext, checkVersionValid() && checkAndroidNotBelowN(), this.mFileProvider);
        int i2 = AnonymousClass18.$SwitchMap$com$umeng$socialize$bean$SHARE_MEDIA[this.mTarget.ordinal()];
        if (i2 == 1) {
            req.scene = 0;
        } else if (i2 == 2) {
            req.scene = 1;
        } else if (i2 != 3) {
            req.scene = 2;
        } else {
            req.scene = 2;
        }
        WXMediaMessage wXMediaMessage = req.message;
        if (wXMediaMessage == null) {
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.14
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler uMWXHandler = UMWXHandler.this;
                    uMWXHandler.getShareListener(uMWXHandler.umShareListener).onError(UMWXHandler.this.mTarget, new Throwable(UmengErrorCode.UnKnowCode.getMessage() + "message = null"));
                }
            });
            return false;
        }
        if (wXMediaMessage.mediaObject == null) {
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.15
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler uMWXHandler = UMWXHandler.this;
                    uMWXHandler.getShareListener(uMWXHandler.umShareListener).onError(UMWXHandler.this.mTarget, new Throwable(UmengErrorCode.UnKnowCode.getMessage() + "mediaobject = null"));
                }
            });
            return false;
        }
        boolean zSendReq = this.mWXApi.sendReq(req);
        if (!zSendReq) {
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.16
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler uMWXHandler = UMWXHandler.this;
                    uMWXHandler.getShareListener(uMWXHandler.umShareListener).onError(UMWXHandler.this.mTarget, new Throwable(UmengErrorCode.UnKnowCode.getMessage() + UmengText.SHARE.SHARE_CONTENT_FAIL));
                }
            });
        }
        return zSendReq;
    }

    private void weixinPreferencesDelete() {
        WeixinPreferences weixinPreferences = this.weixinPreferences;
        if (weixinPreferences != null) {
            weixinPreferences.delete();
        }
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public void authorize(final UMAuthListener uMAuthListener) {
        PlatformConfig.APPIDPlatform aPPIDPlatform = this.config;
        if (aPPIDPlatform != null) {
            this.mTarget = aPPIDPlatform.getName();
        }
        this.mAuthListener = uMAuthListener;
        if (!isInstall()) {
            if (Config.isJumptoAppStore) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("http://log.umsns.com/link/weixin/download/"));
                this.mWeakAct.get().startActivity(intent);
            }
            runInMainThread(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler.this.getAuthListener(uMAuthListener).onError(UMWXHandler.this.mTarget, 0, new Throwable(UmengErrorCode.NotInstall.getMessage()));
                }
            });
            return;
        }
        if (!isAuthValid()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = sScope;
            req.state = "none";
            this.mWXApi.sendReq(req);
            return;
        }
        String refreshToken = getRefreshToken();
        loadOauthData("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + this.config.appId + "&grant_type=refresh_token&refresh_token=" + refreshToken);
        getRefreshToken();
        final Map<String, String> authWithRefreshToken = getAuthWithRefreshToken(refreshToken);
        if (!authWithRefreshToken.containsKey(ERRORCODE) || (!authWithRefreshToken.get(ERRORCODE).equals(ERROR_CODE_TOKEN_ACCESS_FAIL) && !authWithRefreshToken.get(ERRORCODE).equals(ERROR_CODE_TOKEN_REFESH_FAIL))) {
            runInMainThread(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler uMWXHandler = UMWXHandler.this;
                    uMWXHandler.getAuthListener(uMWXHandler.mAuthListener).onComplete(SHARE_MEDIA.WEIXIN, 0, authWithRefreshToken);
                }
            });
        } else {
            weixinPreferencesDelete();
            authorize(uMAuthListener);
        }
    }

    public boolean checkAndroidNotBelowN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public boolean checkVersionValid() {
        return this.mWXApi.getWXAppSupportAPI() >= 654314752;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public void deleteAuth(final UMAuthListener uMAuthListener) {
        weixinPreferencesDelete();
        QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.5
            @Override // java.lang.Runnable
            public void run() {
                UMWXHandler.this.getAuthListener(uMAuthListener).onComplete(SHARE_MEDIA.WEIXIN, 1, null);
            }
        });
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public String getGender(Object obj) {
        String str = StringName.male;
        String str2 = StringName.female;
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (obj.equals("m") || obj.equals("1") || obj.equals(UmengText.MAN)) ? str : (obj.equals("f") || obj.equals("2") || obj.equals(UmengText.WOMAN)) ? str2 : obj.toString();
        }
        if (!(obj instanceof Integer)) {
            return obj.toString();
        }
        Integer num = (Integer) obj;
        return num.intValue() == 1 ? str : num.intValue() == 2 ? str2 : obj.toString();
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public void getPlatformInfo(final UMAuthListener uMAuthListener) {
        if (getShareConfig().isNeedAuthOnGetUserInfo()) {
            weixinPreferencesDelete();
        }
        authorize(new UMAuthListener() { // from class: com.umeng.socialize.handler.UMWXHandler.10
            @Override // com.umeng.socialize.UMAuthListener
            public void onCancel(SHARE_MEDIA share_media, int i2) {
                UMWXHandler.this.getAuthListener(uMAuthListener).onCancel(share_media, i2);
            }

            @Override // com.umeng.socialize.UMAuthListener
            public void onComplete(SHARE_MEDIA share_media, int i2, Map<String, String> map) {
                QueuedWork.runInBack(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.10.1
                    @Override // java.lang.Runnable
                    public void run() throws IOException {
                        AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                        UMWXHandler.this.fetchUserInfo(uMAuthListener);
                    }
                }, true);
            }

            @Override // com.umeng.socialize.UMAuthListener
            public void onError(SHARE_MEDIA share_media, int i2, Throwable th) {
                UMWXHandler.this.getAuthListener(uMAuthListener).onError(share_media, i2, th);
            }

            @Override // com.umeng.socialize.UMAuthListener
            public void onStart(SHARE_MEDIA share_media) {
            }
        });
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public int getRequestCode() {
        return 10086;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public String getSDKVersion() {
        return "3.1.1";
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public String getVersion() {
        return this.VERSION;
    }

    public IWXAPI getWXApi() {
        return this.mWXApi;
    }

    public IWXAPIEventHandler getWXEventHandler() {
        return this.mEventHandler;
    }

    public boolean isAbleShareMin(SHARE_MEDIA share_media, WeiXinShareContent weiXinShareContent) {
        if (weiXinShareContent.getmStyle() == 128) {
            return (share_media == SHARE_MEDIA.WEIXIN_CIRCLE || share_media == SHARE_MEDIA.WEIXIN_FAVORITE) ? false : true;
        }
        return true;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public boolean isAuthorize() {
        return this.weixinPreferences.isAuth();
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public boolean isHasAuthListener() {
        return this.mAuthListener != null;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public boolean isInstall() {
        IWXAPI iwxapi = this.mWXApi;
        return iwxapi != null && iwxapi.isWXAppInstalled();
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public boolean isSupport() {
        return this.mWXApi.getWXAppSupportAPI() >= 553779201;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public boolean isSupportAuth() {
        return true;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public void onCreate(Context context, PlatformConfig.Platform platform) {
        super.onCreate(context, platform);
        Context applicationContext = context.getApplicationContext();
        this.mAppContext = applicationContext;
        this.weixinPreferences = new WeixinPreferences(applicationContext, "weixin");
        PlatformConfig.APPIDPlatform aPPIDPlatform = (PlatformConfig.APPIDPlatform) platform;
        this.config = aPPIDPlatform;
        if (TextUtils.isEmpty(aPPIDPlatform.fileProvider)) {
            SLog.E(UmengText.WX.WEIXIN_FILE_PROVIDER_ERROR);
        } else {
            this.mFileProvider = this.config.fileProvider;
        }
        IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(context.getApplicationContext(), this.config.appId, getShareConfig().getOpenWXAnalytics());
        this.mWXApi = iwxapiCreateWXAPI;
        iwxapiCreateWXAPI.registerApp(this.config.appId);
    }

    public void onShareCallback(SendMessageToWX.Resp resp) {
        int i2 = resp.errCode;
        if (i2 == -6) {
            getShareListener(this.umShareListener).onError(this.mTarget, new Throwable(UmengErrorCode.ShareFailed.getMessage() + UmengText.errorWithUrl(UmengText.AUTH.AUTH_DENIED, UrlUtil.WX_ERROR_SIGN)));
            return;
        }
        if (i2 == -5) {
            getShareListener(this.umShareListener).onError(this.mTarget, new Throwable(UmengErrorCode.ShareFailed.getMessage() + UmengText.SHARE.VERSION_NOT_SUPPORT));
            return;
        }
        if (i2 != -3) {
            if (i2 == -2) {
                getShareListener(this.umShareListener).onCancel(this.mTarget);
                return;
            }
            if (i2 != -1) {
                if (i2 == 0) {
                    new HashMap().put("uid", resp.openId);
                    getShareListener(this.umShareListener).onResult(this.mTarget);
                    return;
                }
                getShareListener(this.umShareListener).onError(this.mTarget, new Throwable(UmengErrorCode.ShareFailed.getMessage() + "code:" + resp.errCode + "msg:" + resp.errStr));
                return;
            }
        }
        getShareListener(this.umShareListener).onError(this.mTarget, new Throwable(UmengErrorCode.ShareFailed.getMessage() + resp.errStr));
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public void release() {
        super.release();
        this.mAuthListener = null;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public void setAuthListener(UMAuthListener uMAuthListener) {
        super.setAuthListener(uMAuthListener);
        this.mAuthListener = uMAuthListener;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public boolean share(ShareContent shareContent, final UMShareListener uMShareListener) {
        PlatformConfig.APPIDPlatform aPPIDPlatform = this.config;
        if (aPPIDPlatform != null) {
            this.mTarget = aPPIDPlatform.getName();
        }
        if (!isInstall()) {
            if (Config.isJumptoAppStore) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(SocializeConstants.DOWN_URL_WX));
                this.mWeakAct.get().startActivity(intent);
            }
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.11
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler.this.getShareListener(uMShareListener).onError(UMWXHandler.this.mTarget, new Throwable(UmengErrorCode.NotInstall.getMessage()));
                }
            });
            return false;
        }
        WeiXinShareContent weiXinShareContent = new WeiXinShareContent(shareContent);
        UMShareConfig uMShareConfig = this.mShareConfig;
        if (uMShareConfig != null) {
            weiXinShareContent.setCompressListener(uMShareConfig.getCompressListener());
        }
        if (!isAbleShareEmoji(this.mTarget, weiXinShareContent)) {
            QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.12
                @Override // java.lang.Runnable
                public void run() {
                    UMWXHandler.this.getShareListener(uMShareListener).onError(UMWXHandler.this.mTarget, new Throwable(UmengErrorCode.ShareDataTypeIllegal.getMessage() + UmengText.WX.WX_CIRCLE_NOT_SUPPORT_EMOJ));
                }
            });
            return false;
        }
        if (isAbleShareMin(this.mTarget, weiXinShareContent)) {
            this.umShareListener = uMShareListener;
            return shareTo(weiXinShareContent);
        }
        QueuedWork.runInMain(new Runnable() { // from class: com.umeng.socialize.handler.UMWXHandler.13
            @Override // java.lang.Runnable
            public void run() {
                UMWXHandler.this.getShareListener(uMShareListener).onError(UMWXHandler.this.mTarget, new Throwable(UmengErrorCode.ShareDataTypeIllegal.getMessage() + UmengText.WX.WX_CIRCLE_NOT_SUPPORT_MIN));
            }
        });
        return false;
    }
}
