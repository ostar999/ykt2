package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.just.agentweb.DefaultWebClient;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.TDialog;
import com.tencent.open.b.e;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.c;
import com.tencent.open.utils.d;
import com.tencent.open.utils.f;
import com.tencent.open.utils.i;
import com.tencent.open.utils.k;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class QQShare extends BaseApi {
    public static final int QQ_SHARE_SUMMARY_MAX_LENGTH = 512;
    public static final int QQ_SHARE_TITLE_MAX_LENGTH = 128;
    public static final String SHARE_TO_QQ_APP_NAME = "appName";
    public static final String SHARE_TO_QQ_ARK_INFO = "share_to_qq_ark_info";
    public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
    public static final String SHARE_TO_QQ_EXT_INT = "cflag";
    public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
    public static final int SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN = 1;
    public static final int SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE = 2;
    public static final String SHARE_TO_QQ_GAME_MESSAGE_EXT = "game_message_ext";
    public static final String SHARE_TO_QQ_GAME_TAG_NAME = "game_tag_name";
    public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
    public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
    public static final String SHARE_TO_QQ_KEY_TYPE = "req_type";
    public static final int SHARE_TO_QQ_MINI_PROGRAM = 7;
    public static final String SHARE_TO_QQ_MINI_PROGRAM_APPID = "mini_program_appid";
    public static final String SHARE_TO_QQ_MINI_PROGRAM_PATH = "mini_program_path";
    public static final String SHARE_TO_QQ_MINI_PROGRAM_TYPE = "mini_program_type";
    public static final String SHARE_TO_QQ_SITE = "site";
    public static final String SHARE_TO_QQ_SUMMARY = "summary";
    public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
    public static final String SHARE_TO_QQ_TITLE = "title";
    public static final int SHARE_TO_QQ_TYPE_AUDIO = 2;
    public static final int SHARE_TO_QQ_TYPE_DEFAULT = 1;
    public static final int SHARE_TO_QQ_TYPE_IMAGE = 5;
    public String mViaShareQQType;

    public QQShare(Context context, QQToken qQToken) {
        super(qQToken);
        this.mViaShareQQType = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Activity activity, Bundle bundle, IUiListener iUiListener) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i2;
        int i3;
        SLog.i("openSDK_LOG.QQShare", "doShareToQQ() -- start");
        StringBuffer stringBuffer = new StringBuffer("mqqapi://share/to_fri?src_type=app&version=1&file_type=news");
        String string = bundle.getString("imageUrl");
        String string2 = bundle.getString("title");
        String string3 = bundle.getString("summary");
        String string4 = bundle.getString("targetUrl");
        String string5 = bundle.getString("audio_url");
        int i4 = bundle.getInt("req_type", 1);
        String string6 = bundle.getString(SHARE_TO_QQ_ARK_INFO);
        String string7 = bundle.getString(SHARE_TO_QQ_MINI_PROGRAM_APPID);
        String string8 = bundle.getString(SHARE_TO_QQ_MINI_PROGRAM_PATH);
        String string9 = bundle.getString(SHARE_TO_QQ_MINI_PROGRAM_TYPE);
        int i5 = bundle.getInt("cflag", 0);
        String string10 = bundle.getString("share_qq_ext_str");
        String strA = k.a(activity);
        if (strA == null) {
            strA = bundle.getString("appName");
        }
        String str = strA;
        String string11 = bundle.getString("imageLocalUrl");
        ArrayList<String> stringArrayList = bundle.getStringArrayList("imageLocalUrlArray");
        String appId = this.f18095c.getAppId();
        String openIdWithCache = this.f18095c.getOpenIdWithCache();
        SLog.i("openSDK_LOG.QQShare", "doShareToQQ -- openid: " + openIdWithCache + ",appName=" + str);
        if (stringArrayList != null && stringArrayList.size() >= 2) {
            String str2 = stringArrayList.get(0);
            if (str2 == null) {
                str2 = "";
            }
            stringBuffer.append("&file_data=" + Base64.encodeToString(k.j(str2), 2));
            String str3 = stringArrayList.get(1);
            if (i4 == 7 && !TextUtils.isEmpty(str3) && i.c(activity, "8.3.3") < 0) {
                SLog.e("openSDK_LOG.QQShare", "doShareToQQ() share to mini program set file uri empty");
                str3 = null;
            }
            Uri uriA = k.a(activity, appId, str3);
            if (uriA != null) {
                stringBuffer.append("&file_uri=");
                stringBuffer.append(Base64.encodeToString(k.j(uriA.toString()), 2));
            }
        } else if (!TextUtils.isEmpty(string11)) {
            stringBuffer.append("&file_data=" + Base64.encodeToString(k.j(string11), 2));
        }
        if (!TextUtils.isEmpty(string)) {
            stringBuffer.append("&image_url=" + Base64.encodeToString(k.j(string), 2));
        }
        if (!TextUtils.isEmpty(string2)) {
            stringBuffer.append("&title=" + Base64.encodeToString(k.j(string2), 2));
        }
        if (!TextUtils.isEmpty(string3)) {
            stringBuffer.append("&description=" + Base64.encodeToString(k.j(string3), 2));
        }
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&share_id=" + appId);
        }
        if (!TextUtils.isEmpty(string4)) {
            stringBuffer.append("&url=" + Base64.encodeToString(k.j(string4), 2));
        }
        if (!TextUtils.isEmpty(str)) {
            if (str.length() > 20) {
                str = str.substring(0, 20) + "...";
            }
            stringBuffer.append("&app_name=" + Base64.encodeToString(k.j(str), 2));
        }
        if (!TextUtils.isEmpty(openIdWithCache)) {
            stringBuffer.append("&open_id=" + Base64.encodeToString(k.j(openIdWithCache), 2));
        }
        if (!TextUtils.isEmpty(string5)) {
            stringBuffer.append("&audioUrl=" + Base64.encodeToString(k.j(string5), 2));
        }
        stringBuffer.append("&req_type=" + Base64.encodeToString(k.j(String.valueOf(i4)), 2));
        if (!TextUtils.isEmpty(string7)) {
            stringBuffer.append("&mini_program_appid=" + Base64.encodeToString(k.j(String.valueOf(string7)), 2));
        }
        if (!TextUtils.isEmpty(string8)) {
            stringBuffer.append("&mini_program_path=" + Base64.encodeToString(k.j(String.valueOf(string8)), 2));
        }
        if (!TextUtils.isEmpty(string9)) {
            stringBuffer.append("&mini_program_type=" + Base64.encodeToString(k.j(String.valueOf(string9)), 2));
        }
        if (!TextUtils.isEmpty(string6)) {
            stringBuffer.append("&share_to_qq_ark_info=" + Base64.encodeToString(k.j(string6), 2));
        }
        if (!TextUtils.isEmpty(string10)) {
            stringBuffer.append("&share_qq_ext_str=" + Base64.encodeToString(k.j(string10), 2));
        }
        stringBuffer.append("&cflag=" + Base64.encodeToString(k.j(String.valueOf(i5)), 2));
        stringBuffer.append("&third_sd=" + Base64.encodeToString(k.j(String.valueOf(k.c())), 2));
        SLog.v("openSDK_LOG.QQShare", "doShareToQQ -- url: " + stringBuffer.toString());
        com.tencent.connect.a.a.a(f.a(), this.f18095c, "requireApi", "shareToNativeQQ");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(stringBuffer.toString()));
        intent.putExtra(Constants.PARAM_PKG_NAME, activity.getPackageName());
        if (k.f(activity, "4.6.0")) {
            SLog.i("openSDK_LOG.QQShare", "doShareToQQ, qqver below 4.6.");
            if (a(intent)) {
                UIListenerManager.getInstance().setListenerWithRequestcode(11103, iUiListener);
                a(activity, intent, 11103);
            }
            i3 = i5;
            i2 = 1;
        } else {
            SLog.i("openSDK_LOG.QQShare", "doShareToQQ, qqver greater than 4.6.");
            if (UIListenerManager.getInstance().setListnerWithAction("shareToQQ", iUiListener) != null) {
                SLog.i("openSDK_LOG.QQShare", "doShareToQQ, last listener is not null, cancel it.");
            }
            if (a(intent)) {
                i2 = 1;
                a(activity, 10103, intent, true);
            } else {
                i2 = 1;
            }
            i3 = i5;
        }
        String str4 = i3 == i2 ? Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE : Constants.VIA_REPORT_TYPE_SHARE_TO_QQ;
        if (a(intent)) {
            e.a().a(this.f18095c.getOpenId(), this.f18095c.getAppId(), Constants.VIA_SHARE_TO_QQ, str4, "3", "0", this.mViaShareQQType, "0", "1", "0");
            e.a().a(0, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
        } else {
            e.a().a(this.f18095c.getOpenId(), this.f18095c.getAppId(), Constants.VIA_SHARE_TO_QQ, str4, "3", "1", this.mViaShareQQType, "0", "1", "0");
            e.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "hasActivityForIntent fail");
        }
        SLog.i("openSDK_LOG.QQShare", "doShareToQQ() --end");
    }

    @Override // com.tencent.connect.common.BaseApi
    public void releaseResource() {
    }

    public void shareToQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        int i2;
        Bundle bundle2;
        String str;
        SLog.i("openSDK_LOG.QQShare", "shareToQQ() -- start.");
        String string = bundle.getString("imageUrl");
        String string2 = bundle.getString("title");
        String string3 = bundle.getString("summary");
        String string4 = bundle.getString("targetUrl");
        String string5 = bundle.getString("imageLocalUrl");
        String string6 = bundle.getString(SHARE_TO_QQ_MINI_PROGRAM_APPID);
        String string7 = bundle.getString(SHARE_TO_QQ_MINI_PROGRAM_PATH);
        int i3 = bundle.getInt("req_type", 1);
        SLog.i("openSDK_LOG.QQShare", "shareToQQ -- type: " + i3);
        if (i3 == 1) {
            this.mViaShareQQType = "1";
        } else if (i3 == 2) {
            this.mViaShareQQType = "3";
        } else if (i3 == 5) {
            this.mViaShareQQType = "2";
        } else if (i3 == 7) {
            this.mViaShareQQType = Constants.VIA_SHARE_TYPE_MINI_PROGRAM;
        }
        if (!k.a() && k.f(activity, "4.5.0")) {
            iUiListener.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
            SLog.e("openSDK_LOG.QQShare", "shareToQQ sdcard is null--end");
            e.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ sdcard is null");
            return;
        }
        if (i3 == 5) {
            if (k.f(activity, "4.3.0")) {
                iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_QQ_VERSION_ERROR, null));
                SLog.e("openSDK_LOG.QQShare", "shareToQQ, version below 4.3 is not support.");
                e.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, version below 4.3 is not support.");
                return;
            } else if (!k.i(string5)) {
                iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
                SLog.e("openSDK_LOG.QQShare", "shareToQQ -- error: 非法的图片地址!");
                e.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR);
                return;
            }
        }
        if (i3 != 5) {
            i2 = 7;
            if (i3 != 7) {
                if (TextUtils.isEmpty(string4) || !(string4.startsWith(DefaultWebClient.HTTP_SCHEME) || string4.startsWith(DefaultWebClient.HTTPS_SCHEME))) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_ERROR, null));
                    SLog.e("openSDK_LOG.QQShare", "shareToQQ, targetUrl is empty or illegal..");
                    e.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, targetUrl is empty or illegal..");
                    return;
                } else {
                    if (TextUtils.isEmpty(string2)) {
                        iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_TITLE_NULL_ERROR, null));
                        SLog.e("openSDK_LOG.QQShare", "shareToQQ, title is empty.");
                        e.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, title is empty.");
                        return;
                    }
                    i2 = 7;
                }
            }
        } else {
            i2 = 7;
        }
        if (i3 == i2) {
            if (TextUtils.isEmpty(string6) || TextUtils.isEmpty(string7) || TextUtils.isEmpty(string4) || TextUtils.isEmpty(this.f18095c.getAppId())) {
                iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_ERROR, "appid || path || url empty."));
                return;
            }
            if (!(i.c(activity, "8.0.8") >= 0 || i.d(activity, "3.1") >= 0 || i.a((Context) activity, Constants.PACKAGE_QQ_SPEED) != null)) {
                iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_QQ_VERSION_ERROR, "版本过低，不支持分享小程序"));
                return;
            } else if (TextUtils.isEmpty(string2) || TextUtils.isEmpty(string3)) {
                iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_ERROR, "title || summary empty."));
                return;
            }
        }
        if (!TextUtils.isEmpty(string) && !string.startsWith(DefaultWebClient.HTTP_SCHEME) && !string.startsWith(DefaultWebClient.HTTPS_SCHEME) && !new File(string).exists()) {
            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
            SLog.e("openSDK_LOG.QQShare", "shareToQQ, image url is emprty or illegal.");
            e.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, image url is emprty or illegal.");
            return;
        }
        if (TextUtils.isEmpty(string2) || string2.length() <= 128) {
            bundle2 = bundle;
            str = null;
        } else {
            str = null;
            bundle2 = bundle;
            bundle2.putString("title", k.a(string2, 128, (String) null, (String) null));
        }
        if (!TextUtils.isEmpty(string3) && string3.length() > 512) {
            bundle2.putString("summary", k.a(string3, 512, str, str));
        }
        if (k.a(activity, bundle2.getInt("cflag", 0) == 1)) {
            SLog.i("openSDK_LOG.QQShare", "shareToQQ, support share");
            b(activity, bundle, iUiListener);
        } else {
            try {
                SLog.w("openSDK_LOG.QQShare", "shareToQQ, don't support share, will show download dialog");
                new TDialog(activity, "", a(""), null, this.f18095c).show();
            } catch (RuntimeException e2) {
                SLog.e("openSDK_LOG.QQShare", " shareToQQ, TDialog.show not in main thread", e2);
                e2.printStackTrace();
                iUiListener.onError(new UiError(-6, Constants.MSG_NOT_CALL_ON_MAIN_THREAD, null));
            }
        }
        SLog.i("openSDK_LOG.QQShare", "shareToQQ() -- end.");
    }

    private void b(final Activity activity, final Bundle bundle, final IUiListener iUiListener) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        SLog.i("openSDK_LOG.QQShare", "shareToMobileQQ() -- start.");
        String string = bundle.getString("imageUrl");
        final String string2 = bundle.getString("title");
        final String string3 = bundle.getString("summary");
        SLog.v("openSDK_LOG.QQShare", "shareToMobileQQ -- imageUrl: " + string);
        if (TextUtils.isEmpty(string)) {
            if (bundle.getInt("req_type", 1) == 5) {
                c(activity, bundle, iUiListener);
            } else {
                d(activity, bundle, iUiListener);
            }
        } else if (!k.h(string)) {
            bundle.putString("imageUrl", null);
            if (k.f(activity, "4.3.0")) {
                SLog.d("openSDK_LOG.QQShare", "shareToMobileQQ -- QQ Version is < 4.3.0 ");
                d(activity, bundle, iUiListener);
            } else {
                SLog.d("openSDK_LOG.QQShare", "shareToMobileQQ -- QQ Version is > 4.3.0:isAppSpecificDir=" + k.n(string) + ",hasSDPermission:" + k.c());
                a.a(activity, string, new d() { // from class: com.tencent.connect.share.QQShare.2
                    @Override // com.tencent.open.utils.d
                    public void a(int i2, String str) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        if (i2 == 0) {
                            bundle.putString("imageLocalUrl", str);
                        } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                            IUiListener iUiListener2 = iUiListener;
                            if (iUiListener2 != null) {
                                iUiListener2.onError(new UiError(-6, Constants.MSG_SHARE_GETIMG_ERROR, null));
                                SLog.e("openSDK_LOG.QQShare", "shareToMobileQQ -- error: 获取分享图片失败!");
                            }
                            e.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, ((BaseApi) QQShare.this).f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_GETIMG_ERROR);
                            return;
                        }
                        QQShare.this.d(activity, bundle, iUiListener);
                    }

                    @Override // com.tencent.open.utils.d
                    public void a(int i2, ArrayList<String> arrayList) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        if (i2 == 0) {
                            bundle.putStringArrayList("imageLocalUrlArray", arrayList);
                        } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                            IUiListener iUiListener2 = iUiListener;
                            if (iUiListener2 != null) {
                                iUiListener2.onError(new UiError(-6, Constants.MSG_SHARE_GETIMG_ERROR, null));
                                SLog.e("openSDK_LOG.QQShare", "shareToMobileQQ -- error: 获取分享图片失败!");
                            }
                            e.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, ((BaseApi) QQShare.this).f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_GETIMG_ERROR);
                            return;
                        }
                        QQShare.this.d(activity, bundle, iUiListener);
                    }
                });
            }
        } else if (k.f(activity, "4.3.0")) {
            new c(activity).a(string, new d() { // from class: com.tencent.connect.share.QQShare.1
                @Override // com.tencent.open.utils.d
                public void a(int i2, String str) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    if (i2 == 0) {
                        bundle.putString("imageLocalUrl", str);
                    } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                        IUiListener iUiListener2 = iUiListener;
                        if (iUiListener2 != null) {
                            iUiListener2.onError(new UiError(-6, Constants.MSG_SHARE_GETIMG_ERROR, null));
                            SLog.e("openSDK_LOG.QQShare", "shareToMobileQQ -- error: 获取分享图片失败!");
                        }
                        e.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, ((BaseApi) QQShare.this).f18095c.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_GETIMG_ERROR);
                        return;
                    }
                    QQShare.this.d(activity, bundle, iUiListener);
                }

                @Override // com.tencent.open.utils.d
                public void a(int i2, ArrayList<String> arrayList) {
                }
            });
        } else {
            d(activity, bundle, iUiListener);
        }
        SLog.i("openSDK_LOG.QQShare", "shareToMobileQQ() -- end");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00d5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void c(android.app.Activity r9, android.os.Bundle r10, com.tencent.tauth.IUiListener r11) throws org.json.JSONException, java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.connect.share.QQShare.c(android.app.Activity, android.os.Bundle, com.tencent.tauth.IUiListener):void");
    }
}
