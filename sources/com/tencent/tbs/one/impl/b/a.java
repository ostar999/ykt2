package com.tencent.tbs.one.impl.b;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.optional.TBSOneDebugPlugin;

/* loaded from: classes6.dex */
public final class a extends FrameLayout {

    /* renamed from: a, reason: collision with root package name */
    public final String f21784a;

    /* renamed from: b, reason: collision with root package name */
    public Handler f21785b;

    /* renamed from: c, reason: collision with root package name */
    public TextView f21786c;

    public a(Context context, String str) {
        super(context);
        this.f21785b = new Handler(Looper.getMainLooper());
        this.f21784a = str;
        this.f21786c = new TextView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        addView(this.f21786c, layoutParams);
        com.tencent.tbs.one.impl.common.a.a(context, new TBSOneCallback<TBSOneDebugPlugin>() { // from class: com.tencent.tbs.one.impl.b.a.1
            @Override // com.tencent.tbs.one.TBSOneCallback
            public final /* synthetic */ void onCompleted(TBSOneDebugPlugin tBSOneDebugPlugin) {
                final TBSOneDebugPlugin tBSOneDebugPlugin2 = tBSOneDebugPlugin;
                a.a(a.this, new Runnable() { // from class: com.tencent.tbs.one.impl.b.a.1.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        a.a(a.this);
                        View viewCreateDebuggingView = tBSOneDebugPlugin2.createDebuggingView(a.this.getContext(), a.this.f21784a);
                        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
                        layoutParams2.gravity = 17;
                        a.this.addView(viewCreateDebuggingView, layoutParams2);
                        a.a(a.this, "加载调试组件成功");
                    }
                });
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onError(int i2, String str2) {
                a.a(a.this, new Runnable() { // from class: com.tencent.tbs.one.impl.b.a.1.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        a.a(a.this);
                        a.a(a.this, "加载调试组件失败，请稍后重试");
                    }
                });
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onProgressChanged(int i2, final int i3) {
                a.a(a.this, new Runnable() { // from class: com.tencent.tbs.one.impl.b.a.1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        a.a(a.this, String.format("已加载 %d", Integer.valueOf(i3)));
                    }
                });
            }
        });
    }

    public static /* synthetic */ void a(a aVar) {
        aVar.removeView(aVar.f21786c);
    }

    public static /* synthetic */ void a(a aVar, Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            aVar.f21785b.post(runnable);
        }
    }

    public static /* synthetic */ void a(a aVar, String str) {
        Toast.makeText(aVar.getContext(), str, 0).show();
    }
}
