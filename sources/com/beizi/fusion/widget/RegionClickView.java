package com.beizi.fusion.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;

/* loaded from: classes2.dex */
public class RegionClickView extends LinearLayout {

    /* renamed from: a, reason: collision with root package name */
    GradientDrawable f5393a;

    /* renamed from: b, reason: collision with root package name */
    TextView f5394b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f5395c;

    public RegionClickView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f5395c = false;
        init(context);
    }

    private void a(Context context, int i2) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        this.f5393a = gradientDrawable;
        gradientDrawable.setColor(Color.parseColor("#80000000"));
        if (i2 == 1) {
            this.f5393a.setStroke(1, Color.parseColor("#E8E8E8"));
        }
        this.f5393a.setCornerRadius(as.a(context, 30.0f));
        setBackgroundDrawable(this.f5393a);
    }

    public void init(Context context) {
        if (this.f5395c) {
            return;
        }
        this.f5395c = true;
        a(context, 1);
        TextView textView = new TextView(context);
        this.f5394b = textView;
        textView.setLines(1);
        this.f5394b.setTextSize(2, 18.0f);
        this.f5394b.setTextColor(Color.parseColor("#949494"));
        this.f5394b.setText("点击跳转详情页或第三方应用     >");
        this.f5394b.setGravity(17);
        setGravity(17);
        setOrientation(1);
        addView(this.f5394b);
    }

    public void setBackGroundAlpha(double d3) {
        GradientDrawable gradientDrawable = this.f5393a;
        if (gradientDrawable == null || d3 <= 0.0d) {
            return;
        }
        gradientDrawable.setAlpha((int) (d3 * 255.0d));
    }

    public void setBackgroundColor(String str) {
        GradientDrawable gradientDrawable = this.f5393a;
        if (gradientDrawable == null || str == null) {
            return;
        }
        try {
            gradientDrawable.setColor(Color.parseColor(str));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setRegionalClickViewBean(AdSpacesBean.BuyerBean.RegionalClickViewBean regionalClickViewBean) {
        if (regionalClickViewBean == null) {
            return;
        }
        setTitle(regionalClickViewBean.getTitle());
        setTitleColor(regionalClickViewBean.getTitleColor());
        setBackGroundAlpha(regionalClickViewBean.getBackgroundAlpha());
        setBackgroundColor(regionalClickViewBean.getBackgroundColor());
    }

    public void setTitle(String str) {
        TextView textView = this.f5394b;
        if (textView == null || str == null) {
            return;
        }
        try {
            textView.setText(str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setTitleColor(String str) {
        TextView textView = this.f5394b;
        if (textView == null || str == null) {
            return;
        }
        try {
            textView.setTextColor(Color.parseColor(str));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public RegionClickView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f5395c = false;
        init(context);
    }

    public RegionClickView(Context context) {
        super(context);
        this.f5395c = false;
        init(context);
    }
}
