package com.beizi.fusion.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.beizi.fusion.R;

/* loaded from: classes2.dex */
public class ShakeView extends LinearLayout {

    /* renamed from: a, reason: collision with root package name */
    ImageView f5419a;

    /* renamed from: b, reason: collision with root package name */
    TextView f5420b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f5421c;

    /* renamed from: d, reason: collision with root package name */
    private String f5422d;

    /* renamed from: e, reason: collision with root package name */
    private AnimationDrawable f5423e;

    public ShakeView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f5421c = false;
        init(context);
    }

    public void init(Context context) {
        if (this.f5421c) {
            return;
        }
        this.f5421c = true;
        ImageView imageView = new ImageView(context);
        this.f5419a = imageView;
        imageView.setBackgroundResource(R.drawable.anim_shake);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        setOrientation(1);
        addView(this.f5419a, layoutParams);
        this.f5423e = (AnimationDrawable) this.f5419a.getBackground();
    }

    public void setTitleText(String str) {
        this.f5422d = str;
        TextView textView = this.f5420b;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void startShake() {
        AnimationDrawable animationDrawable = this.f5423e;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    public void stopShake() {
        AnimationDrawable animationDrawable = this.f5423e;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    public void updateTwistRollAnim() {
        removeAllViews();
        ImageView imageView = new ImageView(getContext());
        this.f5419a = imageView;
        imageView.setBackgroundResource(R.drawable.beizi_twist_roll);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        setOrientation(1);
        addView(this.f5419a, layoutParams);
        this.f5423e = (AnimationDrawable) this.f5419a.getBackground();
    }

    public ShakeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f5421c = false;
        init(context);
    }

    public ShakeView(Context context) {
        super(context);
        this.f5421c = false;
        init(context);
    }
}
