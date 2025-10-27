package com.sina.weibo.sdk.share;

import android.content.Context;
import android.os.AsyncTask;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public final class d extends AsyncTask<WeiboMultiMessage, Void, c> {
    private WeakReference<Context> C;
    private b D;

    public d(Context context, b bVar) {
        this.C = new WeakReference<>(context);
        this.D = bVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x012b  */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.sina.weibo.sdk.share.c doInBackground(com.sina.weibo.sdk.api.WeiboMultiMessage... r13) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.share.d.doInBackground(com.sina.weibo.sdk.api.WeiboMultiMessage[]):com.sina.weibo.sdk.share.c");
    }

    @Override // android.os.AsyncTask
    public final /* synthetic */ void onPostExecute(c cVar) {
        c cVar2 = cVar;
        super.onPostExecute(cVar2);
        b bVar = this.D;
        if (bVar != null) {
            bVar.a(cVar2);
        }
    }

    @Override // android.os.AsyncTask
    public final void onPreExecute() {
        super.onPreExecute();
    }
}
