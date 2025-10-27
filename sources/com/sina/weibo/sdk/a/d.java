package com.sina.weibo.sdk.a;

import android.content.Context;
import com.sina.weibo.sdk.net.HttpManager;
import com.sina.weibo.sdk.net.e;
import com.umeng.socialize.net.dplus.CommonNetImpl;

/* loaded from: classes6.dex */
public final class d extends c<Void, Void, String> {
    private Context aa;
    private com.sina.weibo.sdk.net.c<String> ab;
    private Throwable ac;
    private String ad;
    private String ae;
    private String appKey;

    public d(Context context, String str, String str2, String str3, com.sina.weibo.sdk.net.c<String> cVar) {
        this.aa = context;
        this.ad = str;
        this.appKey = str2;
        this.ae = str3;
        this.ab = cVar;
    }

    private String e(String str) {
        return HttpManager.a(this.aa, com.sina.weibo.sdk.b.e.q(), this.ae, this.appKey, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.sina.weibo.sdk.a.c
    /* renamed from: p, reason: merged with bridge method [inline-methods] */
    public String n() {
        try {
            String strValueOf = String.valueOf(System.currentTimeMillis() / 1000);
            e.a aVar = new e.a();
            aVar.f17185j = "https://service.weibo.com/share/mobilesdk_uppic.php";
            return new com.sina.weibo.sdk.net.b().a(aVar.a(CommonNetImpl.AID, com.sina.weibo.sdk.b.e.q()).a("oauth_timestamp", strValueOf).a("oauth_sign", e(strValueOf)).b(com.heytap.mcssdk.constant.b.f7201z, this.appKey).b(CommonNetImpl.AID, com.sina.weibo.sdk.b.e.q()).b("oauth_timestamp", strValueOf).b("oauth_sign", e(strValueOf)).b("img", this.ad).g()).h();
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
