package com.umeng.socialize.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.tbs.one.BuildConfig;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class WeixinPreferences {
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_OPENID = "openid";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_REFRESH_TOKEN_TTL = "rt_expires_in";
    private static final String KEY_UID = "unionid";
    private String mAccessToken;
    private long mAccessTokenTTL;
    private String mOpenid;
    private String mRefreshToken;
    private long mRefreshTokenTTL;
    private String mUID;
    private SharedPreferences sharedPreferences;

    public WeixinPreferences(Context context, String str) {
        this.sharedPreferences = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(str + BuildConfig.FLAVOR, 0);
        this.sharedPreferences = sharedPreferences;
        this.mUID = sharedPreferences.getString("unionid", null);
        this.mOpenid = this.sharedPreferences.getString("openid", null);
        this.mAccessToken = this.sharedPreferences.getString("access_token", null);
        this.mAccessTokenTTL = this.sharedPreferences.getLong("expires_in", 0L);
        this.mRefreshToken = this.sharedPreferences.getString(KEY_REFRESH_TOKEN, null);
        this.mRefreshTokenTTL = this.sharedPreferences.getLong(KEY_REFRESH_TOKEN_TTL, 0L);
    }

    public void commit() {
        this.sharedPreferences.edit().putString("unionid", this.mUID).putString("openid", this.mOpenid).putString("access_token", this.mAccessToken).putString(KEY_REFRESH_TOKEN, this.mRefreshToken).putLong(KEY_REFRESH_TOKEN_TTL, this.mRefreshTokenTTL).putLong("expires_in", this.mAccessTokenTTL).commit();
    }

    public void delete() {
        this.sharedPreferences.edit().clear().commit();
        this.mAccessToken = "";
        this.mRefreshToken = "";
    }

    public String getAccessToken() {
        return this.mAccessToken;
    }

    public String getRefreshToken() {
        return this.mRefreshToken;
    }

    public String getUID() {
        return this.mUID;
    }

    public long getmAccessTokenTTL() {
        return this.mAccessTokenTTL;
    }

    public String getmOpenid() {
        return this.mOpenid;
    }

    public Map<String, String> getmap() {
        HashMap map = new HashMap();
        map.put("access_token", this.mAccessToken);
        map.put("unionid", this.mUID);
        map.put("openid", this.mOpenid);
        map.put(KEY_REFRESH_TOKEN, this.mRefreshToken);
        map.put("expires_in", String.valueOf(this.mAccessTokenTTL));
        return map;
    }

    public boolean isAccessTokenAvailable() {
        return (TextUtils.isEmpty(this.mAccessToken) || (((this.mAccessTokenTTL - System.currentTimeMillis()) > 0L ? 1 : ((this.mAccessTokenTTL - System.currentTimeMillis()) == 0L ? 0 : -1)) <= 0)) ? false : true;
    }

    public boolean isAuth() {
        return !TextUtils.isEmpty(getAccessToken());
    }

    public boolean isAuthValid() {
        return (TextUtils.isEmpty(this.mRefreshToken) || (((this.mRefreshTokenTTL - System.currentTimeMillis()) > 0L ? 1 : ((this.mRefreshTokenTTL - System.currentTimeMillis()) == 0L ? 0 : -1)) <= 0)) ? false : true;
    }

    public WeixinPreferences setBundle(Bundle bundle) {
        if (TextUtils.isEmpty(bundle.getString("unionid"))) {
            this.mUID = bundle.getString("unionid");
        }
        if (TextUtils.isEmpty(bundle.getString("openid"))) {
            this.mOpenid = bundle.getString("openid");
        }
        this.mAccessToken = bundle.getString("access_token");
        this.mRefreshToken = bundle.getString(KEY_REFRESH_TOKEN);
        String string = bundle.getString("expires_in");
        if (!TextUtils.isEmpty(string)) {
            this.mAccessTokenTTL = (Long.valueOf(string).longValue() * 1000) + System.currentTimeMillis();
        }
        long j2 = bundle.getLong("refresh_token_expires");
        if (j2 != 0) {
            this.mRefreshTokenTTL = (j2 * 1000) + System.currentTimeMillis();
        }
        commit();
        return this;
    }
}
