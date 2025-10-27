package com.beizi.fusion.work.h;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.beizi.ad.NativeAdListener;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.UnifiedCustomAd;
import com.beizi.ad.internal.nativead.NativeAdEventListener;
import com.beizi.ad.internal.nativead.NativeAdShownListener;
import com.beizi.ad.internal.nativead.NativeAdUtil;
import com.beizi.ad.internal.utilities.ImageManager;
import com.beizi.fusion.R;
import com.beizi.fusion.d.x;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ai;
import com.beizi.fusion.g.am;
import com.beizi.fusion.g.ao;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class b extends a implements View.OnClickListener, am.a, ao.a {
    private ViewGroup X;
    private FrameLayout Y;
    private UnifiedCustomAd Z;
    private NativeAdResponse aa;
    private AdSpacesBean.BuyerBean.ShakeViewBean ab;
    private AdSpacesBean.BuyerBean.ScrollClickBean ac;
    private List<View> ad;

    public b(Context context, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, int i2) {
        super(context, j2, buyerBean, forwardBean, eVar, i2);
        this.ad = new ArrayList();
    }

    private void be() {
        if (this.Z == null) {
            aD();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("shakeViewBean != null ? ");
        sb.append(this.ab != null);
        ac.a("BeiZis", sb.toString());
        ((a) this).f5786x.removeAllViews();
        ((a) this).f5786x.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.beizi.fusion.work.h.b.8
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() throws NumberFormatException {
                ViewGroup viewGroup = ((a) b.this).f5786x;
                if (viewGroup == null) {
                    return;
                }
                viewGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                b.this.bf();
                b.this.bg();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bf() throws NumberFormatException {
        AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean = this.ab;
        if (shakeViewBean == null || this.Q == null || shakeViewBean.getPosition() == null) {
            return;
        }
        com.beizi.fusion.b.b bVar = this.f5538b;
        if (bVar != null) {
            bVar.G(this.ab.getShakeViewUuid());
            aB();
        }
        AdSpacesBean.BuyerBean.OrderDataShakeViewBean orderDataShakeViewBeanA = a(this.ab.getOrderData(), this.Z.getAdUnitId());
        if (orderDataShakeViewBeanA != null) {
            this.Q.a(orderDataShakeViewBeanA.getShakeView());
        } else {
            this.Q.a(this.ab);
        }
        View viewA = this.Q.a(as.b(this.N, ((a) this).f5786x.getWidth()), as.b(this.N, ((a) this).f5786x.getHeight()), this.ab.getPosition());
        if (viewA != null) {
            ViewGroup.LayoutParams layoutParams = viewA.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(marginLayoutParams.width, marginLayoutParams.height);
                layoutParams2.leftMargin = marginLayoutParams.leftMargin;
                layoutParams2.topMargin = marginLayoutParams.topMargin;
                try {
                    ((a) this).f5786x.addView(viewA, layoutParams2);
                    this.Q.a(this);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bg() throws NumberFormatException {
        AdSpacesBean.BuyerBean.ScrollClickBean scrollClickBean = this.ac;
        if (scrollClickBean == null || this.R == null || scrollClickBean.getPosition() == null) {
            return;
        }
        com.beizi.fusion.b.b bVar = this.f5538b;
        if (bVar != null) {
            bVar.F(this.ac.getScrollClickUuid());
            aB();
        }
        AdSpacesBean.BuyerBean.OrderDataScrollViewOrderBean orderDataScrollViewOrderBeanB = b(this.ac.getOrderData(), this.Z.getAdUnitId());
        if (orderDataScrollViewOrderBeanB != null) {
            this.R.a(orderDataScrollViewOrderBeanB.getScrollClick());
        } else {
            this.R.a(this.ac);
        }
        View viewA = this.R.a(as.b(this.N, ((a) this).f5786x.getWidth()), as.b(this.N, ((a) this).f5786x.getHeight()), this.ac.getPosition());
        if (viewA != null) {
            ViewGroup.LayoutParams layoutParams = viewA.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(marginLayoutParams.width, marginLayoutParams.height);
                layoutParams2.leftMargin = marginLayoutParams.leftMargin;
                layoutParams2.topMargin = marginLayoutParams.topMargin;
                try {
                    ((a) this).f5786x.addView(viewA, layoutParams2);
                    this.R.a(this);
                    a(((a) this).f5786x, this.ac.getScrollDirection(), this.ac.getScrollDistance(), this);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.Z == null) {
            return;
        }
        aq();
    }

    @Override // com.beizi.fusion.work.h.a
    public int aL() {
        return R.layout.beizi_layout_unified_view;
    }

    @Override // com.beizi.fusion.work.h.a
    public void aM() {
        super.aM();
        this.X = (ViewGroup) ((a) this).f5777o.findViewById(R.id.fl_container_mask);
    }

    @Override // com.beizi.fusion.work.h.a
    public void aN() {
        if (!as.a("com.beizi.ad.BeiZi")) {
            z();
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.h.b.1
                @Override // java.lang.Runnable
                public void run() {
                    b.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                }
            }, 10L);
            Log.e("BeiZis", "BeiZi sdk not import , will do nothing");
            return;
        }
        A();
        x.a(this.N, this.f5544h);
        B();
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + ((a) this).H);
        long j2 = ((a) this).H;
        if (j2 > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, j2);
        } else {
            com.beizi.fusion.d.e eVar = this.f5540d;
            if (eVar != null && eVar.r() < 1 && this.f5540d.q() != 2) {
                p();
            }
        }
        this.Q = new ao(this.N);
        this.R = new am(this.N);
    }

    @Override // com.beizi.fusion.work.h.a
    public void aO() {
        this.ab = this.f5541e.getShakeView();
        this.ac = this.f5541e.getScrollClick();
        UnifiedCustomAd unifiedCustomAd = new UnifiedCustomAd(this.N, this.f5545i, new NativeAdListener() { // from class: com.beizi.fusion.work.h.b.2
            @Override // com.beizi.ad.NativeAdListener
            public void onAdClick() {
                Log.d("BeiZis", "showBeiZiUnifiedCustomAd onAdClick()");
            }

            @Override // com.beizi.ad.NativeAdListener
            public void onAdFailed(int i2) {
                Log.d("BeiZis", "showBeiZiUnifiedCustomAd onAdFailed: " + i2);
                b.this.b(String.valueOf(i2), i2);
            }

            @Override // com.beizi.ad.NativeAdListener
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {
                Log.d("BeiZis", "showBeiZiUnifiedCustomAd onAdLoaded()");
                b bVar = b.this;
                bVar.P = com.beizi.fusion.f.a.ADLOAD;
                if (bVar.Z.getPrice() != null) {
                    try {
                        b bVar2 = b.this;
                        bVar2.a(Double.parseDouble(bVar2.Z.getPrice()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                b.this.E();
                if (nativeAdResponse == null) {
                    b.this.e(-991);
                } else {
                    b.this.aa = nativeAdResponse;
                    b.this.aR();
                }
            }
        });
        this.Z = unifiedCustomAd;
        unifiedCustomAd.openAdInNativeBrowser(true);
        this.Z.loadAd();
    }

    @Override // com.beizi.fusion.work.h.a
    public void aT() {
        if (ai.a(this.U.getCec())) {
            c("regionalClick");
        } else {
            bd();
        }
    }

    @Override // com.beizi.fusion.work.h.a
    public void aU() {
        be();
        List<View> list = this.ad;
        if (list == null || list.size() <= 0) {
            NativeAdUtil.registerTracking(this.aa, this.Y, new NativeAdEventListener() { // from class: com.beizi.fusion.work.h.b.5
                @Override // com.beizi.ad.internal.nativead.NativeAdEventListener
                public void onAdWasClicked() {
                    b.this.aP();
                }

                @Override // com.beizi.ad.internal.nativead.NativeAdEventListener
                public void onAdWillLeaveApplication() {
                    Log.d("BeiZis", "showBeiZiUnifiedCustomAd onAdWillLeaveApplication");
                }
            });
        } else {
            NativeAdUtil.registerTracking(this.aa, this.Y, this.ad, new NativeAdEventListener() { // from class: com.beizi.fusion.work.h.b.4
                @Override // com.beizi.ad.internal.nativead.NativeAdEventListener
                public void onAdWasClicked() {
                    b.this.aP();
                }

                @Override // com.beizi.ad.internal.nativead.NativeAdEventListener
                public void onAdWillLeaveApplication() {
                    Log.d("BeiZis", "showBeiZiUnifiedCustomAd onAdWillLeaveApplication");
                }
            });
        }
        NativeAdUtil.registerShow(this.aa, this.Y, new NativeAdShownListener() { // from class: com.beizi.fusion.work.h.b.6
            @Override // com.beizi.ad.internal.nativead.NativeAdShownListener
            public void onAdShown() {
                b.this.aQ();
            }
        });
        if (ai.a(this.U.getRmc())) {
            new Handler().postDelayed(new Runnable() { // from class: com.beizi.fusion.work.h.b.7
                @Override // java.lang.Runnable
                public void run() {
                    b.this.c("optimize");
                }
            }, (long) ((Math.random() * 1000.0d) + 1000.0d));
        }
    }

    @Override // com.beizi.fusion.work.h.a
    public void aW() {
        ((a) this).f5782t.removeAllViews();
        ((a) this).f5782t.addView(this.Y, new FrameLayout.LayoutParams(-1, -1));
        this.X.setLayoutParams(((a) this).f5782t.getLayoutParams());
    }

    @Override // com.beizi.fusion.work.h.a
    public String aX() {
        return this.aa.getHeadline();
    }

    @Override // com.beizi.fusion.work.h.a
    public String aY() {
        return this.aa.getBody();
    }

    @Override // com.beizi.fusion.work.h.a
    public String aZ() {
        return this.aa.getIconUrl();
    }

    @Override // com.beizi.fusion.g.am.a
    public void b(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
    }

    @Override // com.beizi.fusion.work.h.a
    public String ba() {
        ArrayList<String> texts;
        String callToAction = this.aa.getCallToAction();
        return (!TextUtils.isEmpty(callToAction) || (texts = this.aa.getTexts()) == null || texts.size() < 3) ? callToAction : texts.get(2);
    }

    @Override // com.beizi.fusion.work.h.a
    public void bc() {
        if (this.aa == null) {
            e(-991);
        } else {
            ImageManager.with(null).getBitmap(this.aa.getImageUrl(), new ImageManager.BitmapLoadedListener() { // from class: com.beizi.fusion.work.h.b.3
                @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
                public void onBitmapLoadFailed() {
                    Log.d("BeiZis", "showBeiZiUnifiedCustomAd onBitmapLoadFailed");
                    b.this.b("sdk custom error ".concat("onBitmapLoadFailed"), 99991);
                }

                @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
                public void onBitmapLoaded(Bitmap bitmap) {
                    b bVar = b.this;
                    bVar.Y = NativeAdUtil.getCustomRenderView(bVar.N, bitmap, bVar.aa);
                    b.this.aS();
                }
            });
        }
    }

    @Override // com.beizi.fusion.g.am.a
    public void c_() {
        if (this.ac != null) {
            ac.a("BeiZis", "enter showBeiZiUnifiedCustomAd onScrollDistanceMeet  ");
            c("scroll");
        }
    }

    @Override // com.beizi.fusion.work.h.a, com.beizi.fusion.work.a
    public String g() {
        return "BEIZI";
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        c("regionalClick");
    }

    @Override // com.beizi.fusion.work.h.a, com.beizi.fusion.work.a
    public void q() {
        UnifiedCustomAd unifiedCustomAd = this.Z;
        if (unifiedCustomAd != null) {
            unifiedCustomAd.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str) {
        if (this.Z != null) {
            this.f5538b.O(str);
            aB();
            ac.a("BeiZis", "enter showBeiZiUnifiedCustomAd clickUnifiedAd clickEventType:" + str);
            this.Y.performClick();
        }
    }

    private AdSpacesBean.BuyerBean.OrderDataScrollViewOrderBean b(List<AdSpacesBean.BuyerBean.OrderDataScrollViewOrderBean> list, String str) {
        if (list != null && str != null) {
            for (AdSpacesBean.BuyerBean.OrderDataScrollViewOrderBean orderDataScrollViewOrderBean : list) {
                List<String> orderList = orderDataScrollViewOrderBean.getOrderList();
                if (orderList != null && orderList.contains(str)) {
                    return orderDataScrollViewOrderBean;
                }
            }
        }
        return null;
    }

    @Override // com.beizi.fusion.g.ao.a
    public void b() {
        ac.a("BeiZis", "enter showBeiZiUnifiedCustomAd onShakeHappened  ");
        c("shake");
    }

    @Override // com.beizi.fusion.work.h.a
    public void c(boolean z2) {
        boolean zA = ai.a(this.U.getSlc());
        boolean zA2 = ai.a(this.U.getSlac());
        if (z2 && zA) {
            c("regionalClick");
        } else if (!z2 && zA2) {
            c("regionalClick");
        } else {
            bd();
        }
    }

    @Override // com.beizi.fusion.work.h.a
    public void a(List<View> list) {
        List<String> clickView = this.U.getClickView();
        if (clickView != null && clickView.size() > 0) {
            if (!clickView.contains("bg") && !clickView.contains(com.umeng.analytics.pro.am.aw) && !clickView.contains("image")) {
                this.X.setVisibility(0);
            } else {
                this.X.setVisibility(8);
            }
            this.ad.clear();
            this.ad.addAll(list);
            return;
        }
        this.X.setVisibility(0);
    }

    private AdSpacesBean.BuyerBean.OrderDataShakeViewBean a(List<AdSpacesBean.BuyerBean.OrderDataShakeViewBean> list, String str) {
        if (list != null && str != null) {
            for (AdSpacesBean.BuyerBean.OrderDataShakeViewBean orderDataShakeViewBean : list) {
                List<String> orderList = orderDataShakeViewBean.getOrderList();
                if (orderList != null && orderList.contains(str)) {
                    return orderDataShakeViewBean;
                }
            }
        }
        return null;
    }
}
