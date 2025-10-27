package com.tencent.smtt.sdk.ui.dialog.widget;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.widget.Button;

/* loaded from: classes6.dex */
public class b extends Button {

    /* renamed from: a, reason: collision with root package name */
    private int f21374a;

    /* renamed from: b, reason: collision with root package name */
    private int f21375b;

    /* renamed from: c, reason: collision with root package name */
    private float f21376c;

    /* renamed from: d, reason: collision with root package name */
    private float f21377d;

    /* renamed from: e, reason: collision with root package name */
    private float f21378e;

    /* renamed from: f, reason: collision with root package name */
    private float f21379f;

    /* renamed from: g, reason: collision with root package name */
    private c f21380g;

    /* renamed from: h, reason: collision with root package name */
    private c f21381h;

    /* renamed from: i, reason: collision with root package name */
    private c f21382i;

    public b(Context context, float f2, float f3, float f4, float f5, int i2) {
        super(context);
        this.f21380g = null;
        this.f21381h = null;
        this.f21382i = null;
        this.f21376c = f2;
        this.f21377d = f3;
        this.f21378e = f4;
        this.f21379f = f5;
        this.f21374a = i2;
        this.f21375b = Color.parseColor("#D0D0D0");
        a();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public b(Context context, int i2, int i3) {
        float f2 = i2;
        this(context, f2, f2, f2, f2, i3);
    }

    public void a() {
        c cVar = new c(this.f21374a, this.f21376c, this.f21377d, this.f21378e, this.f21379f);
        this.f21380g = cVar;
        cVar.a(getWidth(), getHeight());
        c cVar2 = new c(1342177280 | (16777215 & this.f21374a), this.f21376c, this.f21377d, this.f21378e, this.f21379f);
        this.f21381h = cVar2;
        cVar2.a(getWidth(), getHeight());
        c cVar3 = new c(this.f21375b, this.f21376c, this.f21377d, this.f21378e, this.f21379f);
        this.f21382i = cVar3;
        cVar3.a(getWidth(), getHeight());
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_enabled, -16842919}, this.f21380g);
        stateListDrawable.addState(new int[]{R.attr.state_enabled, R.attr.state_pressed}, this.f21381h);
        stateListDrawable.addState(new int[]{-16842910}, this.f21382i);
        setBackgroundDrawable(stateListDrawable);
    }

    @Override // android.widget.TextView, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        c cVar = this.f21380g;
        if (cVar != null) {
            cVar.a(i4 - i2, i5 - i3);
        }
        c cVar2 = this.f21381h;
        if (cVar2 != null) {
            cVar2.a(i4 - i2, i5 - i3);
        }
        c cVar3 = this.f21382i;
        if (cVar3 != null) {
            cVar3.a(i4 - i2, i5 - i3);
        }
    }
}
