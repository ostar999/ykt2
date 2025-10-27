package com.beizi.fusion.g;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.widget.ScrollClickView;

/* loaded from: classes2.dex */
public class am {

    /* renamed from: j, reason: collision with root package name */
    private static AdSpacesBean.BuyerBean.ScrollClickBean f5138j;

    /* renamed from: a, reason: collision with root package name */
    ScrollClickView f5139a;

    /* renamed from: b, reason: collision with root package name */
    int f5140b;

    /* renamed from: c, reason: collision with root package name */
    int f5141c;

    /* renamed from: d, reason: collision with root package name */
    private Context f5142d;

    /* renamed from: e, reason: collision with root package name */
    private int f5143e;

    /* renamed from: f, reason: collision with root package name */
    private int f5144f;

    /* renamed from: g, reason: collision with root package name */
    private a f5145g = null;

    /* renamed from: h, reason: collision with root package name */
    private boolean f5146h = false;

    /* renamed from: i, reason: collision with root package name */
    private int f5147i = 200;

    public interface a {
        void b(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8);

        void c_();
    }

    public am(Context context) {
        this.f5142d = context;
    }

    private void c() {
        if (((Boolean) c(this.f5144f).second).booleanValue()) {
            y.a(new Runnable() { // from class: com.beizi.fusion.g.am.1
                @Override // java.lang.Runnable
                public void run() {
                    am.this.a();
                }
            }, this.f5143e + (((Integer) r0.first).intValue() * 10));
        }
    }

    public void b(int i2) {
        this.f5144f = i2;
        c();
    }

    public void a(AdSpacesBean.BuyerBean.ScrollClickBean scrollClickBean) {
        if (scrollClickBean == null) {
            return;
        }
        f5138j = scrollClickBean;
        a(scrollClickBean.getRandomClickTime());
        b(scrollClickBean.getRandomClickNum());
    }

    public void b() {
        this.f5146h = false;
        ScrollClickView scrollClickView = this.f5139a;
        if (scrollClickView != null) {
            scrollClickView.stopAnim();
        }
        this.f5145g = null;
        this.f5142d = null;
        this.f5139a = null;
        this.f5147i = 200;
    }

    public void a(int i2) {
        this.f5143e = i2;
    }

    public static Pair<Integer, Boolean> c(int i2) {
        int iRandom = (int) ((Math.random() * 100.0d) + 1.0d);
        if (iRandom <= i2) {
            return new Pair<>(Integer.valueOf(iRandom), Boolean.TRUE);
        }
        return new Pair<>(Integer.valueOf(iRandom), Boolean.FALSE);
    }

    public void a(a aVar) {
        this.f5145g = aVar;
    }

    public void a() {
        StringBuilder sb = new StringBuilder();
        sb.append("enter callBackShakeHappened and mShakeStateListener != null ? ");
        sb.append(this.f5145g != null);
        sb.append(",!isCallBack = ");
        sb.append(!this.f5146h);
        ac.a("ScrollClickUtil", sb.toString());
        if (this.f5145g == null || this.f5146h) {
            return;
        }
        ac.a("ScrollClickUtil", "callback onShakeHappened()");
        this.f5145g.b("100", "200", "105", "206", "100", "200", "105", "206");
        this.f5146h = true;
        ScrollClickView scrollClickView = this.f5139a;
        if (scrollClickView != null) {
            scrollClickView.stopAnim();
        }
    }

    public View a(final int i2, final int i3, AdSpacesBean.BuyerBean.ScrollClickPositionBean scrollClickPositionBean) throws NumberFormatException {
        int i4;
        int i5;
        ac.a("ScrollClickUtil", "enter getScrollClick");
        if (this.f5142d == null || scrollClickPositionBean == null) {
            return null;
        }
        ScrollClickView scrollClickView = new ScrollClickView(this.f5142d);
        this.f5139a = scrollClickView;
        AdSpacesBean.BuyerBean.ScrollClickBean scrollClickBean = f5138j;
        if (scrollClickBean != null) {
            scrollClickView.setScrollDirection(scrollClickBean.getScrollDirection());
            this.f5139a.setTitleText(f5138j.getTitle());
            this.f5139a.setTitleFont(f5138j.getTitleFont());
            this.f5139a.setDetailText(f5138j.getDetails());
            this.f5139a.setDetailsFont(f5138j.getDetailsFont());
            AdSpacesBean.BuyerBean.ScrollClickPositionBean position = f5138j.getPosition();
            String width = position.getWidth();
            String height = position.getHeight();
            if (width.endsWith("%")) {
                i4 = (Integer.parseInt(width.substring(0, width.indexOf("%"))) * i2) / 100;
            } else {
                i4 = Integer.parseInt(width);
            }
            if (height.endsWith("%")) {
                i5 = (Integer.parseInt(height.substring(0, height.indexOf("%"))) * i4) / 100;
            } else {
                i5 = Integer.parseInt(height);
            }
            this.f5139a.setHandWidth(i4);
            this.f5139a.setScrollbarHeight(i5);
            this.f5139a.buildRealView();
        }
        String top2 = scrollClickPositionBean.getTop();
        String centerX = scrollClickPositionBean.getCenterX();
        if (TextUtils.isEmpty(centerX) || "0".equals(centerX)) {
            centerX = "50%";
        }
        if (TextUtils.isEmpty(top2) || "0".equals(top2)) {
            top2 = "50%";
        }
        as.k(this.f5142d);
        if (centerX.endsWith("%")) {
            this.f5140b = (Integer.parseInt(centerX.substring(0, centerX.indexOf("%"))) * i2) / 100;
        } else {
            this.f5140b = Integer.parseInt(centerX);
        }
        if (top2.endsWith("%")) {
            this.f5141c = (Integer.parseInt(top2.substring(0, top2.indexOf("%"))) * i3) / 100;
        } else {
            this.f5141c = Integer.parseInt(top2);
        }
        this.f5140b = as.a(this.f5142d, this.f5140b);
        this.f5141c = as.a(this.f5142d, this.f5141c);
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        ac.a("ScrollClickUtil", "topInt = " + this.f5141c + ",centerXInt = " + this.f5140b + ",adWidthDp = " + i2 + ",adHeightDp = " + i3);
        this.f5139a.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.beizi.fusion.g.am.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ScrollClickView scrollClickView2 = am.this.f5139a;
                if (scrollClickView2 == null) {
                    return;
                }
                scrollClickView2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int measuredWidth = am.this.f5139a.getMeasuredWidth();
                am amVar = am.this;
                if (amVar.f5141c == 0) {
                    amVar.f5141c = as.a(amVar.f5142d, i3) / 2;
                }
                am amVar2 = am.this;
                if (amVar2.f5140b == 0) {
                    amVar2.f5140b = as.a(amVar2.f5142d, i2) / 2;
                }
                FrameLayout.LayoutParams layoutParams2 = layoutParams;
                am amVar3 = am.this;
                layoutParams2.topMargin = amVar3.f5141c;
                layoutParams2.leftMargin = amVar3.f5140b - (measuredWidth / 2);
                amVar3.f5139a.setLayoutParams(layoutParams2);
                ac.a("ScrollClickUtil", "topMargin = " + layoutParams.topMargin + ",leftMargin = " + layoutParams.leftMargin + ",scrollViewWidthInt = " + measuredWidth);
            }
        });
        this.f5139a.setLayoutParams(layoutParams);
        this.f5139a.postDelayed(new Runnable() { // from class: com.beizi.fusion.g.am.3
            @Override // java.lang.Runnable
            public void run() {
                am.this.f5139a.startAnim();
            }
        }, 10L);
        return this.f5139a;
    }
}
