package com.beizi.fusion.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.beizi.fusion.g.as;

/* loaded from: classes2.dex */
public class SkipView extends TextView {

    /* renamed from: a, reason: collision with root package name */
    private int f5424a;

    /* renamed from: b, reason: collision with root package name */
    private TextPaint f5425b;

    /* renamed from: c, reason: collision with root package name */
    private float f5426c;

    /* renamed from: d, reason: collision with root package name */
    private float f5427d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f5428e;

    /* renamed from: f, reason: collision with root package name */
    private RectF f5429f;

    /* renamed from: g, reason: collision with root package name */
    private RectF f5430g;

    /* renamed from: h, reason: collision with root package name */
    private int f5431h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f5432i;

    /* renamed from: j, reason: collision with root package name */
    private SparseIntArray f5433j;

    /* renamed from: k, reason: collision with root package name */
    private final a f5434k;

    public interface a {
        int a(int i2, RectF rectF);
    }

    public SkipView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f5427d = 20.0f;
        this.f5429f = new RectF();
        this.f5432i = true;
        this.f5434k = new a() { // from class: com.beizi.fusion.widget.SkipView.1
            @Override // com.beizi.fusion.widget.SkipView.a
            @TargetApi(16)
            public int a(int i3, RectF rectF) {
                SkipView.this.f5425b.setTextSize(i3);
                String string = SkipView.this.getText().toString();
                SkipView.this.f5429f.bottom = SkipView.this.f5425b.getFontSpacing();
                SkipView.this.f5429f.right = SkipView.this.f5425b.measureText(string);
                SkipView.this.f5429f.offsetTo(0.0f, 0.0f);
                return rectF.contains(SkipView.this.f5429f) ? -1 : 1;
            }
        };
        init(context);
    }

    public void init(Context context) {
        setGravity(17);
        setLines(1);
        setMaxLines(1);
        setTextColor(-1);
        a(context, 0);
        TextPaint textPaint = new TextPaint();
        this.f5425b = textPaint;
        textPaint.set(getPaint());
        this.f5426c = getTextSize();
        this.f5430g = new RectF();
        this.f5433j = new SparseIntArray();
        this.f5428e = true;
        setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 53));
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        SparseIntArray sparseIntArray = this.f5433j;
        if (sparseIntArray != null) {
            sparseIntArray.clear();
        }
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 == i4 && i3 == i5) {
            return;
        }
        a();
    }

    @Override // android.widget.TextView
    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        super.onTextChanged(charSequence, i2, i3, i4);
        a();
    }

    public void setData(int i2, int i3) {
        a(getContext(), i2);
        this.f5424a = i3;
    }

    private void a(Context context, int i2) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor("#80000000"));
        if (i2 == 1) {
            gradientDrawable.setStroke(1, Color.parseColor("#C0C0C0"));
        }
        gradientDrawable.setCornerRadius(as.a(context, 45.0f));
        setBackgroundDrawable(gradientDrawable);
    }

    private static int b(int i2, int i3, a aVar, RectF rectF) {
        int i4 = i3 - 1;
        int i5 = i2;
        while (i2 <= i4) {
            i5 = (i2 + i4) >>> 1;
            int iA = aVar.a(i5, rectF);
            if (iA >= 0) {
                if (iA <= 0) {
                    break;
                }
                i5--;
                i4 = i5;
            } else {
                int i6 = i5 + 1;
                i5 = i2;
                i2 = i6;
            }
        }
        return i5;
    }

    public SkipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f5427d = 20.0f;
        this.f5429f = new RectF();
        this.f5432i = true;
        this.f5434k = new a() { // from class: com.beizi.fusion.widget.SkipView.1
            @Override // com.beizi.fusion.widget.SkipView.a
            @TargetApi(16)
            public int a(int i3, RectF rectF) {
                SkipView.this.f5425b.setTextSize(i3);
                String string = SkipView.this.getText().toString();
                SkipView.this.f5429f.bottom = SkipView.this.f5425b.getFontSpacing();
                SkipView.this.f5429f.right = SkipView.this.f5425b.measureText(string);
                SkipView.this.f5429f.offsetTo(0.0f, 0.0f);
                return rectF.contains(SkipView.this.f5429f) ? -1 : 1;
            }
        };
        init(context);
    }

    private void a() {
        a(getText().toString());
    }

    private void a(String str) {
        if (this.f5428e) {
            int i2 = (int) this.f5427d;
            int measuredHeight = ((getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop()) - this.f5424a;
            int measuredWidth = ((getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight()) - as.a(getContext(), 8.0f);
            this.f5431h = measuredWidth;
            RectF rectF = this.f5430g;
            rectF.right = measuredWidth;
            rectF.bottom = measuredHeight;
            super.setTextSize(0, a(i2, (int) this.f5426c, this.f5434k, rectF));
        }
    }

    public SkipView(Context context) {
        super(context);
        this.f5427d = 20.0f;
        this.f5429f = new RectF();
        this.f5432i = true;
        this.f5434k = new a() { // from class: com.beizi.fusion.widget.SkipView.1
            @Override // com.beizi.fusion.widget.SkipView.a
            @TargetApi(16)
            public int a(int i3, RectF rectF) {
                SkipView.this.f5425b.setTextSize(i3);
                String string = SkipView.this.getText().toString();
                SkipView.this.f5429f.bottom = SkipView.this.f5425b.getFontSpacing();
                SkipView.this.f5429f.right = SkipView.this.f5425b.measureText(string);
                SkipView.this.f5429f.offsetTo(0.0f, 0.0f);
                return rectF.contains(SkipView.this.f5429f) ? -1 : 1;
            }
        };
        init(context);
    }

    private int a(int i2, int i3, a aVar, RectF rectF) {
        if (!this.f5432i) {
            return b(i2, i3, aVar, rectF);
        }
        String string = getText().toString();
        int length = string == null ? 0 : string.length();
        int i4 = this.f5433j.get(length);
        if (i4 != 0) {
            return i4;
        }
        int iB = b(i2, i3, aVar, rectF);
        this.f5433j.put(length, iB);
        return iB;
    }
}
