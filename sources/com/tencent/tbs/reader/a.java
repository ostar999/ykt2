package com.tencent.tbs.reader;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneComponent;
import com.tencent.tbs.one.TBSOneManager;

/* loaded from: classes6.dex */
public class a extends TBSOneCallback<TBSOneComponent> {

    /* renamed from: a, reason: collision with root package name */
    public final Handler f22264a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Context f22265b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ ITbsReaderCallback f22266c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ TBSOneManager f22267d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ c f22268e;

    /* renamed from: com.tencent.tbs.reader.a$a, reason: collision with other inner class name */
    public class RunnableC0371a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ ITbsReaderCallback f22269a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f22270b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ int f22271c;

        public RunnableC0371a(a aVar, ITbsReaderCallback iTbsReaderCallback, int i2, int i3) {
            this.f22269a = iTbsReaderCallback;
            this.f22270b = i2;
            this.f22271c = i3;
        }

        @Override // java.lang.Runnable
        public void run() {
            ITbsReaderCallback iTbsReaderCallback = this.f22269a;
            if (iTbsReaderCallback != null) {
                iTbsReaderCallback.onCallBackAction(7002, Integer.valueOf(this.f22270b), Integer.valueOf(this.f22271c));
            }
        }
    }

    public a(c cVar, Context context, ITbsReaderCallback iTbsReaderCallback, TBSOneManager tBSOneManager) {
        this.f22268e = cVar;
        this.f22265b = context;
        this.f22266c = iTbsReaderCallback;
        this.f22267d = tBSOneManager;
        this.f22264a = new Handler(context.getMainLooper());
    }

    public void a(ITbsReaderCallback iTbsReaderCallback, int i2, int i3) {
        this.f22264a.post(new RunnableC0371a(this, iTbsReaderCallback, i2, i3));
    }

    @Override // com.tencent.tbs.one.TBSOneCallback
    public void onCompleted(TBSOneComponent tBSOneComponent) {
        TBSOneComponent tBSOneComponent2 = tBSOneComponent;
        Log.d("ReaderEngine", "TBSOneCallback:onCompleted");
        ITbsReaderEntry iTbsReaderEntryA = this.f22268e.a(tBSOneComponent2);
        if (iTbsReaderEntryA == null) {
            a(this.f22266c, -1, -2);
        }
        this.f22268e.a(this.f22265b, new b(this, iTbsReaderEntryA, tBSOneComponent2));
    }

    @Override // com.tencent.tbs.one.TBSOneCallback
    public void onError(int i2, String str) {
        Log.e("ReaderEngine", "TBSOneCallback:onError:" + i2);
        String.format("tbs:onError,code=%d, des=%s", Integer.valueOf(i2), str);
        this.f22268e.c();
        ITbsReaderCallback iTbsReaderCallback = this.f22266c;
        if (iTbsReaderCallback != null) {
            a(iTbsReaderCallback, i2, -1);
        }
    }

    @Override // com.tencent.tbs.one.TBSOneCallback
    public void onProgressChanged(int i2, int i3) {
    }
}
