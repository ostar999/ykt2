package com.tencent.rtmp.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/* loaded from: classes6.dex */
public class TXLogView extends LinearLayout {

    /* renamed from: a, reason: collision with root package name */
    StringBuffer f20772a;

    /* renamed from: b, reason: collision with root package name */
    private TextView f20773b;

    /* renamed from: c, reason: collision with root package name */
    private TextView f20774c;

    /* renamed from: d, reason: collision with root package name */
    private ScrollView f20775d;

    /* renamed from: e, reason: collision with root package name */
    private ScrollView f20776e;

    /* renamed from: f, reason: collision with root package name */
    private final int f20777f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f20778g;

    public TXLogView(Context context) {
        this(context, null);
    }

    public static int a(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public TXLogView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f20772a = new StringBuffer("");
        this.f20777f = 3000;
        this.f20778g = false;
        setOrientation(1);
        this.f20773b = new TextView(context);
        this.f20774c = new TextView(context);
        this.f20775d = new ScrollView(context);
        this.f20776e = new ScrollView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0);
        layoutParams.weight = 0.2f;
        this.f20775d.setLayoutParams(layoutParams);
        this.f20775d.setBackgroundColor(1627389951);
        this.f20775d.setVerticalScrollBarEnabled(true);
        this.f20775d.setScrollbarFadingEnabled(true);
        this.f20773b.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.f20773b.setTextSize(2, 11.0f);
        this.f20773b.setTextColor(-16777216);
        this.f20773b.setTypeface(Typeface.MONOSPACE, 1);
        this.f20773b.setLineSpacing(4.0f, 1.0f);
        this.f20773b.setPadding(a(context, 2.0f), a(context, 2.0f), a(context, 2.0f), a(context, 2.0f));
        this.f20775d.addView(this.f20773b);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, 0);
        layoutParams2.weight = 0.8f;
        layoutParams2.topMargin = a(context, 2.0f);
        this.f20776e.setLayoutParams(layoutParams2);
        this.f20776e.setBackgroundColor(1627389951);
        this.f20776e.setVerticalScrollBarEnabled(true);
        this.f20776e.setScrollbarFadingEnabled(true);
        this.f20774c.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.f20774c.setTextSize(2, 13.0f);
        this.f20774c.setTextColor(-16777216);
        this.f20774c.setPadding(a(context, 2.0f), a(context, 2.0f), a(context, 2.0f), a(context, 2.0f));
        this.f20776e.addView(this.f20774c);
        addView(this.f20775d);
        addView(this.f20776e);
        setVisibility(8);
    }
}
