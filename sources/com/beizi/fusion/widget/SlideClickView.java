package com.beizi.fusion.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.beizi.fusion.R;

/* loaded from: classes2.dex */
public class SlideClickView extends LinearLayout {

    /* renamed from: a, reason: collision with root package name */
    private boolean f5436a;

    /* renamed from: b, reason: collision with root package name */
    private ImageView f5437b;

    /* renamed from: c, reason: collision with root package name */
    private TextView f5438c;

    /* renamed from: d, reason: collision with root package name */
    private String f5439d;

    /* renamed from: e, reason: collision with root package name */
    private int f5440e;

    /* renamed from: f, reason: collision with root package name */
    private int f5441f;

    /* renamed from: g, reason: collision with root package name */
    private int f5442g;

    /* renamed from: h, reason: collision with root package name */
    private AnimationDrawable f5443h;

    public SlideClickView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f5436a = false;
        this.f5441f = 45;
        this.f5442g = 45;
        init(context);
    }

    private void a() {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        this.f5443h = animationDrawable;
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.slide_down_one), 600);
        this.f5443h.addFrame(getResources().getDrawable(R.drawable.slide_down_two), 600);
        this.f5443h.addFrame(getResources().getDrawable(R.drawable.slide_down_three), 600);
        this.f5443h.setOneShot(false);
        ImageView imageView = this.f5437b;
        if (imageView != null) {
            imageView.setImageDrawable(this.f5443h);
        }
    }

    public void init(Context context) {
        if (this.f5436a) {
            return;
        }
        this.f5436a = true;
        TextView textView = new TextView(context);
        this.f5438c = textView;
        textView.setGravity(17);
        this.f5437b = new ImageView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        setOrientation(1);
        setGravity(17);
        addView(this.f5438c, layoutParams);
        addView(this.f5437b, layoutParams2);
        a();
    }

    public void setImageWidthAndHeight(int i2, int i3) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i3);
        ImageView imageView = this.f5437b;
        if (imageView != null) {
            imageView.setLayoutParams(layoutParams);
        }
    }

    public void setTitleFont(int i2) {
        TextView textView;
        this.f5440e = i2;
        if (i2 == 0 || (textView = this.f5438c) == null) {
            return;
        }
        textView.setTextSize(2, i2);
    }

    public void setTitleText(String str) {
        TextView textView;
        this.f5439d = str;
        if (TextUtils.isEmpty(str) || (textView = this.f5438c) == null) {
            return;
        }
        textView.setText(str);
        this.f5438c.setTypeface(Typeface.DEFAULT, 1);
        this.f5438c.setTextColor(Color.parseColor("#FFFFFFFF"));
        this.f5438c.setShadowLayer(5.0f, 1.0f, 1.0f, Color.parseColor("#80000000"));
    }

    public void startAnim() {
        AnimationDrawable animationDrawable = this.f5443h;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    public void stopAnim() {
        AnimationDrawable animationDrawable = this.f5443h;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    public SlideClickView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f5436a = false;
        this.f5441f = 45;
        this.f5442g = 45;
        init(context);
    }

    public SlideClickView(Context context) {
        super(context);
        this.f5436a = false;
        this.f5441f = 45;
        this.f5442g = 45;
        init(context);
    }
}
