package com.beizi.fusion.g;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.widget.RegionClickView;

/* loaded from: classes2.dex */
public class aj {

    /* renamed from: d, reason: collision with root package name */
    private static String f5103d;

    /* renamed from: e, reason: collision with root package name */
    private static String f5104e;

    /* renamed from: f, reason: collision with root package name */
    private static String f5105f;

    /* renamed from: g, reason: collision with root package name */
    private static String f5106g;

    /* renamed from: a, reason: collision with root package name */
    private Context f5107a;

    /* renamed from: b, reason: collision with root package name */
    private a f5108b;

    /* renamed from: c, reason: collision with root package name */
    private AdSpacesBean.BuyerBean.RegionalClickViewBean f5109c = null;

    public interface a {
        void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8);
    }

    public aj(Context context) {
        this.f5107a = context;
    }

    public void a(AdSpacesBean.BuyerBean.RegionalClickViewBean regionalClickViewBean) {
        this.f5109c = regionalClickViewBean;
    }

    public View a(int i2, int i3, AdSpacesBean.BuyerBean.PercentPositionBean percentPositionBean, boolean z2) {
        if (this.f5107a == null || percentPositionBean == null) {
            return null;
        }
        ac.c("BeiZis", "adWidthDp = " + i2 + ",adHeightDp = " + i3);
        RegionClickView regionClickView = new RegionClickView(this.f5107a);
        AdSpacesBean.BuyerBean.RegionalClickViewBean regionalClickViewBean = this.f5109c;
        if (regionalClickViewBean != null) {
            regionClickView.setRegionalClickViewBean(regionalClickViewBean);
        }
        regionClickView.setLayoutParams(a(i2, i3, percentPositionBean));
        if (z2) {
            regionClickView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.g.aj.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (aj.this.f5108b != null) {
                        aj.this.f5108b.a(aj.f5103d, aj.f5104e, aj.f5105f, aj.f5106g, aj.f5103d, aj.f5104e, aj.f5105f, aj.f5106g);
                    }
                }
            });
            regionClickView.setOnTouchListener(new View.OnTouchListener() { // from class: com.beizi.fusion.g.aj.2
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    try {
                        if (motionEvent.getAction() != 0) {
                            return false;
                        }
                        String unused = aj.f5103d = motionEvent.getX() + "";
                        String unused2 = aj.f5104e = motionEvent.getY() + "";
                        String unused3 = aj.f5105f = motionEvent.getRawX() + "";
                        String unused4 = aj.f5106g = motionEvent.getRawY() + "";
                        return false;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return false;
                    }
                }
            });
        }
        return regionClickView;
    }

    @NonNull
    private ViewGroup.MarginLayoutParams a(int i2, int i3, AdSpacesBean.BuyerBean.PercentPositionBean percentPositionBean) throws NumberFormatException {
        int i4;
        int i5;
        int i6;
        int i7;
        String centerX = percentPositionBean.getCenterX();
        String centerY = percentPositionBean.getCenterY();
        String width = percentPositionBean.getWidth();
        String height = percentPositionBean.getHeight();
        float fL = as.l(this.f5107a);
        if (TextUtils.isEmpty(centerX) || "0".equals(centerX)) {
            centerX = "50%";
        }
        if (TextUtils.isEmpty(centerY) || "0".equals(centerY)) {
            ac.a("BeiZis", "screenHeightDp = " + fL + ",adHeightDp = " + i3);
            centerY = fL > ((float) i3) ? "63" : "188";
        }
        if (TextUtils.isEmpty(width) || "0".equals(width)) {
            width = "325";
        }
        if (TextUtils.isEmpty(height) || "0".equals(height)) {
            height = "65";
        }
        float fK = as.k(this.f5107a);
        if (centerX.endsWith("%")) {
            i4 = (Integer.parseInt(centerX.substring(0, centerX.indexOf("%"))) * i2) / 100;
        } else {
            i4 = Integer.parseInt(centerX);
        }
        if (centerY.endsWith("%")) {
            i5 = (Integer.parseInt(centerY.substring(0, centerY.indexOf("%"))) * i3) / 100;
        } else {
            i5 = i3 - Integer.parseInt(centerY);
            ac.a("BeiZis", "adHeightDp = " + i3 + ", centerYInt = " + i5);
        }
        int i8 = 400;
        if (width.endsWith("%")) {
            int i9 = Integer.parseInt(width.substring(0, width.indexOf("%")));
            if (fK >= 400.0f) {
                i6 = (i9 * 400) / 100;
                i8 = i6;
            } else {
                i8 = (((int) fK) * i9) / 100;
            }
        } else {
            i6 = Integer.parseInt(width);
            if (i6 < 400) {
                i8 = i6;
            }
        }
        if (height.endsWith("%")) {
            i7 = (Integer.parseInt(height.substring(0, height.indexOf("%"))) * i8) / 100;
        } else {
            i7 = Integer.parseInt(height);
        }
        int iA = as.a(this.f5107a, i8);
        int iA2 = as.a(this.f5107a, i7);
        int iA3 = as.a(this.f5107a, i4);
        int iA4 = as.a(this.f5107a, i5);
        ac.a("BeiZis", "widthInt = " + iA + ",heightInt = " + iA2);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(iA, iA2);
        ac.a("BeiZis", "centerYInt = " + iA4 + ",centerXInt = " + iA3 + ",adWidthDp = " + i2 + ",adHeightDp = " + i3);
        marginLayoutParams.topMargin = iA4 - (iA2 / 2);
        marginLayoutParams.leftMargin = iA3 - (iA / 2);
        return marginLayoutParams;
    }

    public void a(a aVar) {
        this.f5108b = aVar;
    }

    public void a() {
        this.f5107a = null;
        this.f5109c = null;
    }
}
