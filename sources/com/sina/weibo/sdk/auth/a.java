package com.sina.weibo.sdk.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.BuildConfig;
import com.sina.weibo.sdk.b.a;
import com.sina.weibo.sdk.b.c;
import com.sina.weibo.sdk.b.e;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.net.h;
import com.sina.weibo.sdk.web.WebActivity;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.net.dplus.CommonNetImpl;

/* loaded from: classes6.dex */
public final class a {

    /* renamed from: e, reason: collision with root package name */
    public WbAuthListener f17173e;

    public final void a(Activity activity) {
        c.a("WBSsoTag", "startClientAuth()");
        try {
            a.C0313a c0313aE = com.sina.weibo.sdk.b.a.e(activity);
            Intent intent = new Intent();
            if (c0313aE == null) {
                intent.setClassName(BuildConfig.APPLICATION_ID, "com.sina.weibo.SSOActivity");
            } else {
                intent.setClassName(c0313aE.packageName, c0313aE.ag);
            }
            AuthInfo authInfoB = com.sina.weibo.sdk.a.b();
            intent.putExtra(com.heytap.mcssdk.constant.b.f7201z, authInfoB.getAppKey());
            intent.putExtra("redirectUri", authInfoB.getRedirectUrl());
            intent.putExtra(Constants.PARAM_SCOPE, authInfoB.getScope());
            intent.putExtra("packagename", authInfoB.getPackageName());
            intent.putExtra("key_hash", authInfoB.getHash());
            intent.putExtra("_weibo_command_type", 3);
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            intent.putExtra("_weibo_transaction", sb.toString());
            if (activity == null) {
                this.f17173e.onError(new UiError(-1, "activity is null", ""));
                return;
            }
            if (!com.sina.weibo.sdk.b.a.a(activity, intent)) {
                this.f17173e.onError(new UiError(-2, "your app is illegal", ""));
                return;
            }
            authInfoB.getAppKey();
            intent.putExtra(CommonNetImpl.AID, e.q());
            activity.startActivityForResult(intent, HandlerRequestCode.SINA_AUTH_REQUEST_CODE);
            c.a("WBSsoTag", "start SsoActivity ");
        } catch (Exception e2) {
            e2.printStackTrace();
            c.b("WBSsoTag", e2.getMessage());
            this.f17173e.onError(new UiError(-3, "occur exception", e2.getMessage()));
        }
    }

    public final void b(Activity activity) {
        h hVar = new h();
        AuthInfo authInfoB = com.sina.weibo.sdk.a.b();
        if (authInfoB == null) {
            return;
        }
        hVar.put(Constants.PARAM_CLIENT_ID, authInfoB.getAppKey());
        hVar.put("redirect_uri", authInfoB.getRedirectUrl());
        hVar.put(Constants.PARAM_SCOPE, authInfoB.getScope());
        hVar.put("packagename", authInfoB.getPackageName());
        hVar.put("key_hash", authInfoB.getHash());
        hVar.put("response_type", "code");
        hVar.put("version", "0041005000");
        hVar.put("luicode", "10000360");
        hVar.put("lfid", "OP_" + authInfoB.getAppKey());
        Oauth2AccessToken accessToken = AccessTokenHelper.readAccessToken(activity);
        if (accessToken != null) {
            String accessToken2 = accessToken.getAccessToken();
            if (!TextUtils.isEmpty(accessToken.getAccessToken())) {
                hVar.put("trans_token", accessToken2);
                hVar.put("trans_access_token", accessToken2);
            }
        }
        authInfoB.getAppKey();
        String strQ = e.q();
        if (!TextUtils.isEmpty(strQ)) {
            hVar.put(CommonNetImpl.AID, strQ);
        }
        String str = "https://open.weibo.cn/oauth2/authorize?" + hVar.i();
        if (this.f17173e != null) {
            b bVarD = b.d();
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            String string = sb.toString();
            bVarD.a(string, this.f17173e);
            Intent intent = new Intent(activity, (Class<?>) WebActivity.class);
            com.sina.weibo.sdk.web.b.a aVar = new com.sina.weibo.sdk.web.b.a(authInfoB, str, string);
            Bundle bundle = new Bundle();
            aVar.writeToBundle(bundle);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }
    }
}
