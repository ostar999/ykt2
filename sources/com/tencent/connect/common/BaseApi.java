package com.tencent.connect.common;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import androidx.fragment.app.Fragment;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.auth.c;
import com.tencent.connect.common.Constants;
import com.tencent.open.TDialog;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.f;
import com.tencent.open.utils.i;
import com.tencent.open.utils.k;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public abstract class BaseApi {
    public static String businessId = null;
    public static String installChannel = null;
    public static boolean isOEM = false;
    public static String registerChannel;

    /* renamed from: b, reason: collision with root package name */
    protected c f18094b;

    /* renamed from: c, reason: collision with root package name */
    protected QQToken f18095c;

    public class TempRequestListener implements IRequestListener {

        /* renamed from: b, reason: collision with root package name */
        private final IUiListener f18097b;

        /* renamed from: c, reason: collision with root package name */
        private final Handler f18098c;

        public TempRequestListener(IUiListener iUiListener) {
            this.f18097b = iUiListener;
            this.f18098c = new Handler(f.a().getMainLooper()) { // from class: com.tencent.connect.common.BaseApi.TempRequestListener.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (message.what == 0) {
                        TempRequestListener.this.f18097b.onComplete(message.obj);
                    } else {
                        TempRequestListener.this.f18097b.onError(new UiError(message.what, (String) message.obj, null));
                    }
                }
            };
        }

        @Override // com.tencent.tauth.IRequestListener
        public void onComplete(JSONObject jSONObject) {
            Message messageObtainMessage = this.f18098c.obtainMessage();
            messageObtainMessage.obj = jSONObject;
            messageObtainMessage.what = 0;
            this.f18098c.sendMessage(messageObtainMessage);
        }

        @Override // com.tencent.tauth.IRequestListener
        public void onHttpStatusException(HttpUtils.HttpStatusException httpStatusException) {
            Message messageObtainMessage = this.f18098c.obtainMessage();
            messageObtainMessage.obj = httpStatusException.getMessage();
            messageObtainMessage.what = -9;
            this.f18098c.sendMessage(messageObtainMessage);
        }

        @Override // com.tencent.tauth.IRequestListener
        public void onIOException(IOException iOException) {
            Message messageObtainMessage = this.f18098c.obtainMessage();
            messageObtainMessage.obj = iOException.getMessage();
            messageObtainMessage.what = -2;
            this.f18098c.sendMessage(messageObtainMessage);
        }

        @Override // com.tencent.tauth.IRequestListener
        public void onJSONException(JSONException jSONException) {
            Message messageObtainMessage = this.f18098c.obtainMessage();
            messageObtainMessage.obj = jSONException.getMessage();
            messageObtainMessage.what = -4;
            this.f18098c.sendMessage(messageObtainMessage);
        }

        @Override // com.tencent.tauth.IRequestListener
        public void onMalformedURLException(MalformedURLException malformedURLException) {
            Message messageObtainMessage = this.f18098c.obtainMessage();
            messageObtainMessage.obj = malformedURLException.getMessage();
            messageObtainMessage.what = -3;
            this.f18098c.sendMessage(messageObtainMessage);
        }

        @Override // com.tencent.tauth.IRequestListener
        public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException networkUnavailableException) {
            Message messageObtainMessage = this.f18098c.obtainMessage();
            messageObtainMessage.obj = networkUnavailableException.getMessage();
            messageObtainMessage.what = -10;
            this.f18098c.sendMessage(messageObtainMessage);
        }

        @Override // com.tencent.tauth.IRequestListener
        public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
            Message messageObtainMessage = this.f18098c.obtainMessage();
            messageObtainMessage.obj = socketTimeoutException.getMessage();
            messageObtainMessage.what = -8;
            this.f18098c.sendMessage(messageObtainMessage);
        }

        @Override // com.tencent.tauth.IRequestListener
        public void onUnknowException(Exception exc) {
            Message messageObtainMessage = this.f18098c.obtainMessage();
            messageObtainMessage.obj = exc.getMessage();
            messageObtainMessage.what = -6;
            this.f18098c.sendMessage(messageObtainMessage);
        }
    }

    public BaseApi(c cVar, QQToken qQToken) {
        this.f18094b = cVar;
        this.f18095c = qQToken;
    }

    public Bundle a() {
        Bundle bundle = new Bundle();
        bundle.putString("format", AliyunVodHttpCommon.Format.FORMAT_JSON);
        bundle.putString("status_os", Build.VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        bundle.putString("status_version", Build.VERSION.SDK);
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        bundle.putString("sdkp", am.av);
        QQToken qQToken = this.f18095c;
        if (qQToken != null && qQToken.isSessionValid()) {
            bundle.putString("access_token", this.f18095c.getAccessToken());
            bundle.putString("oauth_consumer_key", this.f18095c.getAppId());
            bundle.putString("openid", this.f18095c.getOpenId());
        }
        SharedPreferences sharedPreferences = f.a().getSharedPreferences(Constants.PREFERENCE_PF, 0);
        if (isOEM) {
            bundle.putString("pf", "desktop_m_qq-" + installChannel + "-android-" + registerChannel + "-" + businessId);
        } else {
            bundle.putString("pf", sharedPreferences.getString("pf", Constants.DEFAULT_PF));
        }
        return bundle;
    }

    public Bundle b() {
        Bundle bundle = new Bundle();
        bundle.putString("appid", this.f18095c.getAppId());
        if (this.f18095c.isSessionValid()) {
            bundle.putString(Constants.PARAM_KEY_STR, this.f18095c.getAccessToken());
            bundle.putString(Constants.PARAM_KEY_TYPE, "0x80");
        }
        String openId = this.f18095c.getOpenId();
        if (openId != null) {
            bundle.putString("hopenid", openId);
        }
        bundle.putString("platform", "androidqz");
        SharedPreferences sharedPreferences = f.a().getSharedPreferences(Constants.PREFERENCE_PF, 0);
        if (isOEM) {
            bundle.putString("pf", "desktop_m_qq-" + installChannel + "-android-" + registerChannel + "-" + businessId);
        } else {
            bundle.putString("pf", sharedPreferences.getString("pf", Constants.DEFAULT_PF));
            bundle.putString("pf", Constants.DEFAULT_PF);
        }
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        bundle.putString("sdkp", am.av);
        return bundle;
    }

    public Intent c(String str) {
        Intent intent = new Intent();
        Intent intentB = b(str);
        if (intentB == null || intentB.getComponent() == null) {
            return null;
        }
        intent.setClassName(intentB.getComponent().getPackageName(), "com.tencent.open.agent.AgentActivity");
        return intent;
    }

    public void releaseResource() {
    }

    public BaseApi(QQToken qQToken) {
        this(null, qQToken);
    }

    public String a(String str) {
        Bundle bundleA = a();
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            bundleA.putString("need_version", str);
        }
        sb.append("https://openmobile.qq.com/oauth2.0/m_jump_by_version?");
        sb.append(HttpUtils.encodeUrl(bundleA));
        return sb.toString();
    }

    public Intent b(String str) {
        Intent intent = new Intent();
        if (k.c(f.a())) {
            intent.setClassName(Constants.PACKAGE_QQ_PAD, str);
            if (i.a(f.a(), intent)) {
                return intent;
            }
        }
        intent.setClassName("com.tencent.mobileqq", str);
        if (i.a(f.a(), intent)) {
            return intent;
        }
        intent.setClassName(Constants.PACKAGE_TIM, str);
        if (i.a(f.a(), intent)) {
            return intent;
        }
        intent.setClassName(Constants.PACKAGE_QQ_SPEED, str);
        if (i.a(f.a(), intent)) {
            return intent;
        }
        return null;
    }

    public void a(StringBuilder sb, Activity activity) {
        if (sb.indexOf("?") < 0) {
            sb.append("?");
        } else {
            sb.append("&");
        }
        sb.append(Constants.JumpUrlConstants.URL_KEY_SRC);
        sb.append("=");
        sb.append("app");
        String appId = this.f18095c.getAppId();
        String openId = this.f18095c.getOpenId();
        if (!TextUtils.isEmpty(appId)) {
            a(sb, "app_id", appId);
        }
        if (!TextUtils.isEmpty(openId)) {
            a(sb, Constants.JumpUrlConstants.URL_KEY_OPENID, k.l(openId));
        }
        String strA = k.a(activity);
        if (!TextUtils.isEmpty(strA)) {
            if (strA.length() > 20) {
                strA = strA.substring(0, 20) + "...";
            }
            a(sb, "app_name", k.l(strA));
        }
        a(sb, "sdk_version", k.l(Constants.SDK_VERSION));
    }

    public void a(StringBuilder sb, String str, String str2) {
        sb.append("&");
        sb.append(str);
        sb.append("=");
        sb.append(k.f(str2));
    }

    private Intent a(Activity activity, Intent intent, Map<String, Object> map) {
        Intent intent2 = new Intent(activity.getApplicationContext(), (Class<?>) AssistActivity.class);
        intent2.putExtra("is_login", true);
        intent2.putExtra(AssistActivity.EXTRA_INTENT, intent);
        if (map == null) {
            return intent2;
        }
        try {
            if (map.containsKey(Constants.KEY_RESTORE_LANDSCAPE)) {
                intent2.putExtra(Constants.KEY_RESTORE_LANDSCAPE, ((Boolean) map.get(Constants.KEY_RESTORE_LANDSCAPE)).booleanValue());
            }
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.BaseApi", "Exception", e2);
        }
        return intent2;
    }

    public void a(Activity activity, int i2, Intent intent, boolean z2) {
        Intent intent2 = new Intent(activity.getApplicationContext(), (Class<?>) AssistActivity.class);
        if (z2) {
            intent2.putExtra("is_qq_mobile_share", true);
        }
        intent2.putExtra(AssistActivity.EXTRA_INTENT, intent);
        activity.startActivityForResult(intent2, i2);
    }

    public void a(Activity activity, Intent intent, int i2) {
        a(activity, intent, i2, (Map<String, Object>) null);
    }

    public void a(Activity activity, Intent intent, int i2, Map<String, Object> map) {
        intent.putExtra(Constants.KEY_REQUEST_CODE, i2);
        activity.startActivityForResult(a(activity, intent, map), i2);
    }

    public void a(Fragment fragment, Intent intent, int i2, Map<String, Object> map) {
        intent.putExtra(Constants.KEY_REQUEST_CODE, i2);
        fragment.startActivityForResult(a(fragment.getActivity(), intent, map), i2);
    }

    public boolean a(Intent intent) {
        if (intent != null) {
            return i.a(f.a(), intent);
        }
        return false;
    }

    public void a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        SLog.i("openSDK_LOG.BaseApi", "--handleDownloadLastestQQ");
        new TDialog(activity, "", "https://imgcache.qq.com/ptlogin/static/qzsjump.html?" + HttpUtils.encodeUrl(bundle), null, this.f18095c).show();
    }
}
