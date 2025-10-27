package com.tencent.open;

import android.app.Activity;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import java.security.NoSuchAlgorithmException;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class SocialApi {

    /* renamed from: a, reason: collision with root package name */
    private SocialApiIml f20461a;

    public SocialApi(QQToken qQToken) {
        this.f20461a = new SocialApiIml(qQToken);
    }

    public void ask(Activity activity, Bundle bundle, IUiListener iUiListener) throws JSONException, NoSuchAlgorithmException {
        this.f20461a.ask(activity, bundle, iUiListener);
    }

    public void gift(Activity activity, Bundle bundle, IUiListener iUiListener) throws JSONException, NoSuchAlgorithmException {
        this.f20461a.gift(activity, bundle, iUiListener);
    }

    public void invite(Activity activity, Bundle bundle, IUiListener iUiListener) throws JSONException, NoSuchAlgorithmException {
        this.f20461a.invite(activity, bundle, iUiListener);
    }

    public void story(Activity activity, Bundle bundle, IUiListener iUiListener) throws JSONException, NoSuchAlgorithmException {
        this.f20461a.story(activity, bundle, iUiListener);
    }
}
