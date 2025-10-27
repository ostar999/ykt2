package com.sina.weibo.sdk.a;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.e;
import com.tencent.connect.common.Constants;

/* loaded from: classes6.dex */
public final class e extends c<Void, Void, String> {
    private com.sina.weibo.sdk.net.c<String> ab;
    private Throwable ac;
    private Oauth2AccessToken af;
    private String appKey;

    public e(String str, Oauth2AccessToken oauth2AccessToken, com.sina.weibo.sdk.net.c<String> cVar) {
        this.appKey = str;
        this.af = oauth2AccessToken;
        this.ab = cVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.sina.weibo.sdk.a.c
    /* renamed from: p, reason: merged with bridge method [inline-methods] */
    public String n() {
        try {
            e.a aVar = new e.a();
            aVar.f17185j = "https://api.weibo.com/oauth2/access_token";
            return new com.sina.weibo.sdk.net.b().a(aVar.b(Constants.PARAM_CLIENT_ID, this.appKey).b(com.heytap.mcssdk.constant.b.f7201z, this.appKey).b("grant_type", "refresh_token").b("refresh_token", this.af.getRefreshToken()).g()).h();
        } catch (Throwable th) {
            th.printStackTrace();
            this.ac = th;
            return null;
        }
    }

    @Override // com.sina.weibo.sdk.a.c
    public final /* synthetic */ void onPostExecute(String str) {
        String str2 = str;
        Throwable th = this.ac;
        if (th != null) {
            com.sina.weibo.sdk.net.c<String> cVar = this.ab;
            if (cVar != null) {
                cVar.onError(th);
                return;
            }
            return;
        }
        com.sina.weibo.sdk.net.c<String> cVar2 = this.ab;
        if (cVar2 != null) {
            cVar2.a(str2);
        }
    }
}
