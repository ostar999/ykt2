package com.tencent.tauth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.fragment.app.Fragment;
import com.hyphenate.easeui.constants.EaseConstant;
import com.mobile.auth.gatewayauth.Constant;
import com.tencent.connect.api.QQAuthManage;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.auth.c;
import com.tencent.connect.avatar.QQAvatar;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.connect.emotion.QQEmotion;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.SocialConstants;
import com.tencent.open.a.a;
import com.tencent.open.apireq.IApiCallback;
import com.tencent.open.b.b;
import com.tencent.open.im.IM;
import com.tencent.open.log.SLog;
import com.tencent.open.log.Tracer;
import com.tencent.open.miniapp.MiniApp;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.f;
import com.tencent.open.utils.g;
import com.tencent.open.utils.i;
import com.tencent.open.utils.k;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class Tencent {
    public static final int REQUEST_LOGIN = 10001;

    /* renamed from: c, reason: collision with root package name */
    private static Tencent f21637c;

    /* renamed from: a, reason: collision with root package name */
    private final c f21638a;

    /* renamed from: b, reason: collision with root package name */
    private String f21639b;

    private Tencent(String str, Context context) {
        this.f21638a = c.a(str, context);
        b.a().a(str, context);
    }

    private static boolean a(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.tauth.AuthActivity"), 128);
            try {
                context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.connect.common.AssistActivity"), 128);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                SLog.e("openSDK_LOG.Tencent", "AndroidManifest.xml 没有检测到com.tencent.connect.common.AssistActivity\n" + ("没有在AndroidManifest.xml中检测到com.tencent.connect.common.AssistActivity,请加上com.tencent.connect.common.AssistActivity,详细信息请查看官网文档.\n配置示例如下: \n<activity\n     android:name=\"com.tencent.connect.common.AssistActivity\"\n     android:screenOrientation=\"behind\"\n     android:theme=\"@android:style/Theme.Translucent.NoTitleBar\"\n     android:configChanges=\"orientation|keyboardHidden\">\n</activity>"));
                return false;
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            SLog.e("openSDK_LOG.Tencent", "AndroidManifest.xml 没有检测到com.tencent.tauth.AuthActivity" + (("没有在AndroidManifest.xml中检测到com.tencent.tauth.AuthActivity,请加上com.tencent.tauth.AuthActivity,并配置<data android:scheme=\"tencent" + str + "\" />,详细信息请查看官网文档.") + "\n配置示例如下: \n<activity\n     android:name=\"com.tencent.tauth.AuthActivity\"\n     android:noHistory=\"true\"\n     android:launchMode=\"singleTask\">\n<intent-filter>\n    <action android:name=\"android.intent.action.VIEW\" />\n    <category android:name=\"android.intent.category.DEFAULT\" />\n    <category android:name=\"android.intent.category.BROWSABLE\" />\n    <data android:scheme=\"tencent" + str + "\" />\n</intent-filter>\n</activity>"));
            return false;
        }
    }

    public static synchronized Tencent createInstance(String str, Context context, String str2) {
        Tencent tencentCreateInstance;
        tencentCreateInstance = createInstance(str, context);
        SLog.i("openSDK_LOG.Tencent", "createInstance()  -- start, appId = " + str + ", authorities=" + str2);
        a("createInstance_authority", "appid", str, "authorities", str2);
        if (tencentCreateInstance != null) {
            tencentCreateInstance.f21639b = str2;
        } else {
            SLog.i("openSDK_LOG.Tencent", "null == tencent set mAuthorities fail");
        }
        return tencentCreateInstance;
    }

    public static synchronized String getAuthorities(String str) {
        a("getAuthorities", "appid", str);
        if (TextUtils.isEmpty(str)) {
            SLog.i("openSDK_LOG.Tencent", "TextUtils.isEmpty(appId)");
            return null;
        }
        Tencent tencent = f21637c;
        if (tencent != null) {
            return str.equals(tencent.getAppId()) ? f21637c.f21639b : "";
        }
        SLog.i("openSDK_LOG.Tencent", "sInstance == null");
        return null;
    }

    public static void handleResultData(Intent intent, IUiListener iUiListener) {
        StringBuilder sb = new StringBuilder();
        sb.append("handleResultData() data = null ? ");
        sb.append(intent == null);
        sb.append(", listener = null ? ");
        sb.append(iUiListener == null);
        SLog.i("openSDK_LOG.Tencent", sb.toString());
        a("handleResultData", new Object[0]);
        UIListenerManager.getInstance().handleDataToListener(intent, iUiListener);
    }

    public static boolean isSupportPushToQZone(Context context) {
        boolean z2 = i.c(context, "5.9.5") >= 0 || i.a(context, Constants.PACKAGE_QQ_SPEED) != null;
        SLog.i("openSDK_LOG.Tencent", "isSupportPushToQZone() support=" + z2);
        a("isSupportPushToQZone", Boolean.valueOf(z2));
        return z2;
    }

    public static boolean isSupportShareToQQ(Context context) {
        SLog.i("openSDK_LOG.Tencent", "isSupportShareToQQ()");
        boolean z2 = true;
        if (k.c(context) && i.a(context, Constants.PACKAGE_QQ_PAD) != null) {
            a("isSupportShareToQQ", Boolean.TRUE);
            return true;
        }
        if (i.c(context, "4.1") < 0 && i.a(context, Constants.PACKAGE_TIM) == null && i.a(context, Constants.PACKAGE_QQ_SPEED) == null) {
            z2 = false;
        }
        SLog.i("openSDK_LOG.Tencent", "isSupportShareToQQ() support=" + z2);
        a("isSupportShareToQQ", Boolean.valueOf(z2));
        return z2;
    }

    public static boolean onActivityResultData(int i2, int i3, Intent intent, IUiListener iUiListener) {
        StringBuilder sb = new StringBuilder();
        sb.append("onActivityResultData() reqcode = ");
        sb.append(i2);
        sb.append(", resultcode = ");
        sb.append(i3);
        sb.append(", data = null ? ");
        sb.append(intent == null);
        sb.append(", listener = null ? ");
        sb.append(iUiListener == null);
        SLog.i("openSDK_LOG.Tencent", sb.toString());
        a("onActivityResultData", Constant.LOGIN_ACTIVITY_REQUEST_CODE, Integer.valueOf(i2), "resultCode", Integer.valueOf(i3));
        return UIListenerManager.getInstance().onActivityResult(i2, i3, intent, iUiListener);
    }

    public static Map<String, String> parseMiniParameters(Intent intent) {
        String stringExtra;
        a("parseMiniParameters", new Object[0]);
        HashMap map = new HashMap();
        if (intent == null) {
            SLog.e("openSDK_LOG.Tencent", "parseMiniParameters null == intent");
            return map;
        }
        try {
            stringExtra = intent.getStringExtra("appParameter");
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.Tencent", "parseMiniParameters Exception", e2);
        }
        if (!TextUtils.isEmpty(stringExtra)) {
            SLog.d("openSDK_LOG.Tencent", "parseMiniParameters appParameter=" + stringExtra);
            JSONObject jSONObject = new JSONObject(stringExtra);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject.getString(next));
            }
            return map;
        }
        Uri data = intent.getData();
        if (data == null) {
            SLog.d("openSDK_LOG.Tencent", "parseMiniParameters uri==null");
            return map;
        }
        String string = data.toString();
        if (TextUtils.isEmpty(string)) {
            SLog.d("openSDK_LOG.Tencent", "parseMiniParameters uriStr isEmpty");
            return map;
        }
        String strSubstring = string.substring(string.lastIndexOf(63) + 1);
        if (TextUtils.isEmpty(strSubstring)) {
            SLog.d("openSDK_LOG.Tencent", "parseMiniParameters uriParam is empty");
            return map;
        }
        SLog.d("openSDK_LOG.Tencent", "parseMiniParameters uriParam=" + strSubstring);
        String[] strArrSplit = strSubstring.split("&");
        int length = strArrSplit.length;
        for (int i2 = 0; i2 < length; i2++) {
            String[] strArrSplit2 = strArrSplit[i2].split("=");
            if (strArrSplit2.length == 2) {
                map.put(strArrSplit2[0], strArrSplit2[1]);
            }
        }
        return map;
    }

    public static void setCustomLogger(Tracer tracer) {
        SLog.i("openSDK_LOG.Tencent", "setCustomLogger");
        a("setCustomLogger", new Object[0]);
        SLog.getInstance().setCustomLogger(tracer);
    }

    public void checkLogin(IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "checkLogin()");
        a("checkLogin", new Object[0]);
        this.f21638a.a(iUiListener);
    }

    public String getAccessToken() {
        String accessToken = this.f21638a.b().getAccessToken();
        SLog.i("openSDK_LOG.Tencent", "getAccessToken() accessToken = " + accessToken);
        a("getAccessToken", new Object[0]);
        return accessToken;
    }

    public String getAppId() {
        String appId = this.f21638a.b().getAppId();
        SLog.i("openSDK_LOG.Tencent", "getAppId() appid =" + appId);
        a("getAppId", appId);
        return appId;
    }

    public long getExpiresIn() {
        long expireTimeInSecond = this.f21638a.b().getExpireTimeInSecond();
        SLog.i("openSDK_LOG.Tencent", "getExpiresIn() expiresin= " + expireTimeInSecond);
        a("getExpiresIn", Long.valueOf(expireTimeInSecond));
        return expireTimeInSecond;
    }

    public String getOpenId() {
        String openId = this.f21638a.b().getOpenId();
        SLog.i("openSDK_LOG.Tencent", "getOpenId() openid= " + openId);
        a("getOpenId", new Object[0]);
        return openId;
    }

    public QQToken getQQToken() {
        SLog.i("openSDK_LOG.Tencent", "getQQToken()");
        a("getQQToken", new Object[0]);
        return this.f21638a.b();
    }

    @Deprecated
    public void handleLoginData(Intent intent, IUiListener iUiListener) {
        StringBuilder sb = new StringBuilder();
        sb.append("handleLoginData() data = null ? ");
        sb.append(intent == null);
        sb.append(", listener = null ? ");
        sb.append(iUiListener == null);
        SLog.i("openSDK_LOG.Tencent", sb.toString());
        a("handleLoginData", new Object[0]);
        UIListenerManager.getInstance().handleDataToListener(intent, iUiListener);
    }

    public void initSessionCache(JSONObject jSONObject) throws JSONException {
        a("initSessionCache", new Object[0]);
        try {
            String string = jSONObject.getString("access_token");
            String string2 = jSONObject.getString("expires_in");
            String string3 = jSONObject.getString("openid");
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
                setAccessToken(string, string2);
                setOpenId(string3);
            }
            SLog.i("openSDK_LOG.Tencent", "initSessionCache()");
        } catch (Exception e2) {
            SLog.i("QQToken", "initSessionCache " + e2.toString());
        }
    }

    public boolean isQQInstalled(Context context) throws PackageManager.NameNotFoundException {
        boolean zB = i.b(context);
        SLog.i("openSDK_LOG.Tencent", "isQQInstalled() installed=" + zB);
        a("isQQInstalled", Boolean.valueOf(zB));
        return zB;
    }

    public boolean isReady() {
        boolean z2 = isSessionValid() && getOpenId() != null;
        SLog.i("openSDK_LOG.Tencent", "isReady() --ready=" + z2);
        a("isReady", Boolean.valueOf(z2));
        return z2;
    }

    public boolean isSessionValid() {
        boolean zC = this.f21638a.c();
        SLog.i("openSDK_LOG.Tencent", "isSessionValid() isvalid =" + zC);
        a("isSessionValid", Boolean.valueOf(zC));
        return zC;
    }

    public boolean isSupportSSOLogin(Activity activity) {
        SLog.i("openSDK_LOG.Tencent", "isSupportSSOLogin()");
        boolean z2 = true;
        if (k.c(activity) && i.a((Context) activity, Constants.PACKAGE_QQ_PAD) != null) {
            a("isSupportSSOLogin", Boolean.TRUE);
            return true;
        }
        if (i.c(activity, "4.1") < 0 && i.d(activity, "1.1") < 0 && i.e(activity, "4.0.0") < 0) {
            z2 = false;
        }
        SLog.i("openSDK_LOG.Tencent", "isSupportSSOLogin() support=" + z2);
        a("isSupportSSOLogin", Boolean.valueOf(z2));
        return z2;
    }

    public JSONObject loadSession(String str) {
        JSONObject jSONObjectLoadSession = this.f21638a.b().loadSession(str);
        StringBuilder sb = new StringBuilder();
        sb.append("loadSession() appid ");
        sb.append(str);
        sb.append(", length=");
        sb.append(jSONObjectLoadSession != null ? jSONObjectLoadSession.length() : 0);
        SLog.i("openSDK_LOG.Tencent", sb.toString());
        a("loadSession", "appid", str);
        return jSONObjectLoadSession;
    }

    public int login(Activity activity, String str, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "login() with activity, scope is " + str);
        a("login_scope", Constants.PARAM_SCOPE, str);
        return this.f21638a.a(activity, str, iUiListener);
    }

    public int loginServerSide(Activity activity, String str, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "loginServerSide() with activity, scope = " + str + ",server_side");
        a("loginServerSide_activity", Constants.PARAM_SCOPE, str);
        return this.f21638a.a(activity, str + ",server_side", iUiListener);
    }

    public int loginWithOEM(Activity activity, String str, IUiListener iUiListener, boolean z2, String str2, String str3, String str4) {
        SLog.i("openSDK_LOG.Tencent", "loginWithOEM() with activity, scope = " + str);
        a("loginWithOEM", Constants.PARAM_SCOPE, str, "qrcode", Boolean.valueOf(z2), "registerChannel", str2, "installChannel", str3, "businessId", str4);
        return this.f21638a.a(activity, str, iUiListener, z2, str2, str3, str4);
    }

    public void logout(Context context) throws NumberFormatException {
        SLog.i("openSDK_LOG.Tencent", "logout()");
        a("logout", new Object[0]);
        this.f21638a.b().setAccessToken(null, "0");
        this.f21638a.b().setOpenId(null);
        this.f21638a.b().removeSession(this.f21638a.b().getAppId());
    }

    public void publishToQzone(Activity activity, Bundle bundle, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "publishToQzone()");
        a("publishToQzone", new Object[0]);
        new QzonePublish(activity, this.f21638a.b()).publishToQzone(activity, bundle, iUiListener);
    }

    public int reAuth(Activity activity, String str, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "reAuth() with activity, scope = " + str);
        a("reAuth", Constants.PARAM_SCOPE, str);
        return this.f21638a.b(activity, str, iUiListener);
    }

    public void reportDAU() {
        SLog.i("openSDK_LOG.Tencent", "reportDAU() ");
        a("reportDAU", new Object[0]);
        this.f21638a.a();
    }

    public JSONObject request(String str, Bundle bundle, String str2) throws JSONException, IOException, HttpUtils.NetworkUnavailableException, HttpUtils.HttpStatusException {
        SLog.i("openSDK_LOG.Tencent", "request()");
        a(SocialConstants.TYPE_REQUEST, "graphPath", str, "httpMethod", str2);
        return HttpUtils.request(this.f21638a.b(), f.a(), str, bundle, str2);
    }

    public void requestAsync(String str, Bundle bundle, String str2, IRequestListener iRequestListener) {
        SLog.i("openSDK_LOG.Tencent", "requestAsync()");
        a("requestAsync", "graphPath", str, "httpMethod", str2);
        HttpUtils.requestAsync(this.f21638a.b(), f.a(), str, bundle, str2, iRequestListener);
    }

    public void saveSession(JSONObject jSONObject) {
        StringBuilder sb = new StringBuilder();
        sb.append("saveSession() length=");
        sb.append(jSONObject != null ? jSONObject.length() : 0);
        SLog.i("openSDK_LOG.Tencent", sb.toString());
        a("saveSession", new Object[0]);
        this.f21638a.b().saveSession(jSONObject);
    }

    public void setAccessToken(String str, String str2) {
        SLog.i("openSDK_LOG.Tencent", "setAccessToken(), expiresIn = " + str2 + "");
        a("setAccessToken", new Object[0]);
        this.f21638a.a(str, str2);
    }

    public void setAvatar(Activity activity, Bundle bundle, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "setAvatar()");
        a("setAvatar", new Object[0]);
        String string = bundle.getString("picture");
        new QQAvatar(this.f21638a.b()).setAvatar(activity, Uri.parse(string), iUiListener, bundle.getInt(Constant.LOGIN_ACTIVITY_EXIT_ANIM));
    }

    public void setAvatarByQQ(Activity activity, Uri uri, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "setAvatarByQQ()");
        a("setAvatarByQQ", new Object[0]);
        new QQAvatar(this.f21638a.b()).setAvatarByQQ(activity, uri, iUiListener);
    }

    public void setDynamicAvatar(Activity activity, Uri uri, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "setDynamicAvatar()");
        a("setDynamicAvatar", new Object[0]);
        new QQAvatar(this.f21638a.b()).setDynamicAvatar(activity, uri, iUiListener);
    }

    public void setEmotions(Activity activity, ArrayList<Uri> arrayList, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "saveQQEmotions()");
        a("setEmotions", new Object[0]);
        new QQEmotion(this.f21638a.b()).setEmotions(activity, arrayList, iUiListener);
    }

    public void setOpenId(String str) {
        SLog.i("openSDK_LOG.Tencent", "setOpenId() --start");
        a("setOpenId", new Object[0]);
        this.f21638a.b(f.a(), str);
        SLog.i("openSDK_LOG.Tencent", "setOpenId() --end");
    }

    public void shareToQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "shareToQQ()");
        a("shareToQQ", new Object[0]);
        if (TextUtils.isEmpty(this.f21639b)) {
            iUiListener.onWarning(-19);
        }
        new QQShare(activity, this.f21638a.b()).shareToQQ(activity, bundle, iUiListener);
    }

    public void shareToQzone(Activity activity, Bundle bundle, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "shareToQzone()");
        a("shareToQzone", new Object[0]);
        new QzoneShare(activity, this.f21638a.b()).shareToQzone(activity, bundle, iUiListener);
    }

    public void startAuthManagePage(Activity activity, IApiCallback iApiCallback) {
        SLog.i("openSDK_LOG.Tencent", "startAuthManagePage");
        new QQAuthManage(this.f21638a, getQQToken()).gotoManagePage(activity, iApiCallback);
    }

    public int startIMAio(Activity activity, String str, String str2) {
        SLog.i("openSDK_LOG.Tencent", "startIMAio()");
        a("startIMAio", "uin", str, Constants.PARAM_PKG_NAME, str2);
        return startIMConversation(activity, IM.CHAT_TYPE_AIO, str, str2);
    }

    public int startIMAudio(Activity activity, String str, String str2) {
        SLog.i("openSDK_LOG.Tencent", "startIMAudio()");
        a("startIMAudio", "uin", str, Constants.PARAM_PKG_NAME, str2);
        return startIMConversation(activity, IM.CHAT_TYPE_AUDIO_CHAT, str, str2);
    }

    public int startIMConversation(Activity activity, String str, String str2, String str3) {
        a("startIMConversation", EaseConstant.EXTRA_CHAT_TYPE, str, "uin", str2, Constants.PARAM_PKG_NAME, str3);
        return new IM(getQQToken()).startIMConversation(activity, str, str2, str3);
    }

    public int startIMVideo(Activity activity, String str, String str2) {
        SLog.i("openSDK_LOG.Tencent", "startIMVideo()");
        a("startIMVideo", "uin", str, Constants.PARAM_PKG_NAME, str2);
        return startIMConversation(activity, IM.CHAT_TYPE_VIDEO_CHAT, str, str2);
    }

    public int startMiniApp(Activity activity, String str, String str2, String str3) {
        SLog.i("openSDK_LOG.Tencent", "startMiniApp()");
        a("startMiniApp", "miniAppId", str, "miniAppPath", str2, "miniAppVersion", str3);
        return new MiniApp(getQQToken()).startMiniApp(activity, MiniApp.MINIAPP_TYPE_NORMAL, str, "21", str2, str3);
    }

    public int login(Activity activity, IUiListener iUiListener, Map<String, Object> map) {
        SLog.i("openSDK_LOG.Tencent", "login activity with params");
        a("login_param", new Object[0]);
        return this.f21638a.a(activity, iUiListener, map);
    }

    public int loginServerSide(Fragment fragment, String str, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "loginServerSide() with fragment, scope = " + str + ",server_side");
        a("loginServerSide_fragment", Constants.PARAM_SCOPE, str);
        return this.f21638a.a(fragment, str + ",server_side", iUiListener, "");
    }

    public static synchronized Tencent createInstance(String str, Context context) {
        f.a(context.getApplicationContext());
        SLog.i("openSDK_LOG.Tencent", "createInstance()  -- start, appId = " + str);
        if (TextUtils.isEmpty(str)) {
            SLog.e("openSDK_LOG.Tencent", "appId should not be empty!");
            return null;
        }
        Tencent tencent = f21637c;
        if (tencent == null) {
            f21637c = new Tencent(str, context);
        } else if (!str.equals(tencent.getAppId())) {
            f21637c.logout(context);
            f21637c = new Tencent(str, context);
        }
        if (!a(context, str)) {
            return null;
        }
        a("createInstance", "appid", str);
        a.a().a(g.a(context, str));
        SLog.i("openSDK_LOG.Tencent", "createInstance()  -- end");
        return f21637c;
    }

    public int login(Activity activity, String str, IUiListener iUiListener, boolean z2) {
        SLog.i("openSDK_LOG.Tencent", "login() with activity, scope is " + str);
        a("login_qrcode", Constants.PARAM_SCOPE, str, "qrcode", Boolean.valueOf(z2));
        return this.f21638a.a(activity, str, iUiListener, z2);
    }

    public void setAvatar(Activity activity, Bundle bundle, IUiListener iUiListener, int i2, int i3) {
        SLog.i("openSDK_LOG.Tencent", "setAvatar()");
        a("setAvatar_anim", new Object[0]);
        bundle.putInt(Constant.LOGIN_ACTIVITY_EXIT_ANIM, i3);
        activity.overridePendingTransition(i2, 0);
        setAvatar(activity, bundle, iUiListener);
    }

    public int login(Fragment fragment, String str, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.Tencent", "login() with fragment, scope is " + str);
        a("login_fragment_scope", Constants.PARAM_SCOPE, str);
        return this.f21638a.a(fragment, str, iUiListener, "");
    }

    public int login(Fragment fragment, String str, IUiListener iUiListener, boolean z2) {
        SLog.i("openSDK_LOG.Tencent", "login() with fragment, scope is " + str);
        a("login_fragment_scope_qrcode", Constants.PARAM_SCOPE, str, "qrcode", Boolean.valueOf(z2));
        return this.f21638a.a(fragment, str, iUiListener, "", z2);
    }

    private static void a(String str, Object... objArr) {
        b.a().a(str, a(objArr));
    }

    private static String a(Object... objArr) {
        if (objArr == null || objArr.length == 0 || objArr.length % 2 != 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int length = objArr.length;
        for (int i2 = 0; i2 < length; i2 += 2) {
            if (i2 > 0) {
                sb.append('|');
            }
            sb.append(objArr[i2]);
            sb.append(':');
            sb.append(objArr[i2 + 1]);
        }
        return sb.toString();
    }

    private static void a(String str, Object obj) {
        b.a().a(str, obj);
    }
}
