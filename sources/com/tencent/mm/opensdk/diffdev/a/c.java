package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;

/* loaded from: classes6.dex */
class c extends AsyncTask<Void, Void, a> {

    /* renamed from: a, reason: collision with root package name */
    private String f20440a;

    /* renamed from: b, reason: collision with root package name */
    private String f20441b;

    /* renamed from: c, reason: collision with root package name */
    private OAuthListener f20442c;

    /* renamed from: d, reason: collision with root package name */
    private int f20443d;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public OAuthErrCode f20444a;

        /* renamed from: b, reason: collision with root package name */
        public String f20445b;

        /* renamed from: c, reason: collision with root package name */
        public int f20446c;
    }

    public c(String str, OAuthListener oAuthListener) {
        this.f20440a = str;
        this.f20442c = oAuthListener;
        this.f20441b = String.format("https://long.open.weixin.qq.com/connect/l/qrconnect?f=json&uuid=%s", str);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00d3 A[Catch: Exception -> 0x00d8, TryCatch #0 {Exception -> 0x00d8, blocks: (B:20:0x008f, B:22:0x009f, B:26:0x00b8, B:28:0x00bc, B:29:0x00ca, B:33:0x00d5, B:30:0x00cd, B:31:0x00d0, B:32:0x00d3), top: B:63:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x016c A[SYNTHETIC] */
    @Override // android.os.AsyncTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.tencent.mm.opensdk.diffdev.a.c.a doInBackground(java.lang.Void[] r14) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 432
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mm.opensdk.diffdev.a.c.doInBackground(java.lang.Object[]):java.lang.Object");
    }

    @Override // android.os.AsyncTask
    public void onPostExecute(a aVar) {
        a aVar2 = aVar;
        this.f20442c.onAuthFinish(aVar2.f20444a, aVar2.f20445b);
    }
}
