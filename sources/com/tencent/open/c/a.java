package com.tencent.open.c;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.RelativeLayout;

/* loaded from: classes6.dex */
public class a extends RelativeLayout {

    /* renamed from: a, reason: collision with root package name */
    private static final String f20576a = "com.tencent.open.c.a";

    /* renamed from: b, reason: collision with root package name */
    private Rect f20577b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f20578c;

    /* renamed from: d, reason: collision with root package name */
    private InterfaceC0349a f20579d;

    /* renamed from: com.tencent.open.c.a$a, reason: collision with other inner class name */
    public interface InterfaceC0349a {
        void a();

        void a(int i2);
    }

    public a(Context context) {
        super(context);
        this.f20577b = null;
        this.f20578c = false;
        this.f20579d = null;
        this.f20577b = new Rect();
    }

    public void a(InterfaceC0349a interfaceC0349a) {
        this.f20579d = interfaceC0349a;
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        int size = View.MeasureSpec.getSize(i3);
        Activity activity = (Activity) getContext();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(this.f20577b);
        int height = (activity.getWindowManager().getDefaultDisplay().getHeight() - this.f20577b.top) - size;
        InterfaceC0349a interfaceC0349a = this.f20579d;
        if (interfaceC0349a != null && size != 0) {
            if (height > 100) {
                interfaceC0349a.a((Math.abs(this.f20577b.height()) - getPaddingBottom()) - getPaddingTop());
            } else {
                interfaceC0349a.a();
            }
        }
        super.onMeasure(i2, i3);
    }
}
