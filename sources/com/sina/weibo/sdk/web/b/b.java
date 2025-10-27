package com.sina.weibo.sdk.web.b;

import android.content.Context;
import android.os.Bundle;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.web.WebData;

/* loaded from: classes6.dex */
public abstract class b {
    protected WebData aC;
    protected String aD;
    protected Context aa;

    public interface a {
        void onComplete();

        void onError(String str);
    }

    public b() {
    }

    public abstract void a(Bundle bundle);

    public void a(a aVar) {
    }

    public abstract void b(Bundle bundle);

    public abstract String getUrl();

    public final void readFromBundle(Bundle bundle) {
        this.aC = (WebData) bundle.getParcelable("web_data");
        this.aD = bundle.getString("_weibo_transaction");
        b(bundle);
    }

    public final void setContext(Context context) {
        this.aa = context;
    }

    public boolean w() {
        return false;
    }

    public final Bundle writeToBundle(Bundle bundle) {
        bundle.putParcelable("web_data", this.aC);
        int type = this.aC.getType();
        if (type == 1) {
            bundle.putInt("web_type", 1);
        } else if (type == 2) {
            bundle.putInt("web_type", 2);
        } else if (type == 3) {
            bundle.putInt("web_type", 3);
        }
        bundle.putString("_weibo_transaction", this.aD);
        a(bundle);
        return bundle;
    }

    public final WebData x() {
        return this.aC;
    }

    public b(AuthInfo authInfo, int i2, String str, String str2) {
        this.aC = new WebData(authInfo, i2, str, str2);
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        this.aD = sb.toString();
    }
}
