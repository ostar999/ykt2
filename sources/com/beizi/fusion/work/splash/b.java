package com.beizi.fusion.work.splash;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.beizi.ad.AdListener;
import com.beizi.ad.SplashAd;
import com.beizi.fusion.d.x;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ai;
import com.beizi.fusion.g.aj;
import com.beizi.fusion.g.ak;
import com.beizi.fusion.g.am;
import com.beizi.fusion.g.ao;
import com.beizi.fusion.g.aq;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.m;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.update.ShakeArcView;
import com.beizi.fusion.widget.CircleProgressView;
import com.beizi.fusion.widget.ScrollClickView;
import com.beizi.fusion.widget.SkipView;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.tencent.tbs.one.BuildConfig;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public class b extends com.beizi.fusion.work.a implements aj.a, am.a, ao.a {
    private long I;
    private long J;
    private boolean K;
    private CircleProgressView L;
    private AdSpacesBean.PositionBean M;
    private AdSpacesBean.PositionBean N;
    private float O;
    private float P;
    private AdSpacesBean.RenderViewBean Q;
    private int R;
    private int S;
    private String T;
    private String U;
    private String V;
    private AdSpacesBean.BuyerBean.CoolShakeViewBean W;
    private ao ab;
    private aj ac;
    private am ad;
    private AdSpacesBean.BuyerBean.RollViewBean ae;
    private AdSpacesBean.BuyerBean.CoolRollViewBean af;
    private ak ag;

    /* renamed from: n, reason: collision with root package name */
    AdSpacesBean.BuyerBean.ShakeViewBean f6039n;

    /* renamed from: o, reason: collision with root package name */
    AdSpacesBean.BuyerBean.RegionalClickViewBean f6040o;

    /* renamed from: p, reason: collision with root package name */
    AdSpacesBean.BuyerBean.ScrollClickBean f6041p;

    /* renamed from: q, reason: collision with root package name */
    AdSpacesBean.BuyerBean.FullScreenClickBean f6042q;

    /* renamed from: r, reason: collision with root package name */
    View.OnClickListener f6043r;

    /* renamed from: s, reason: collision with root package name */
    private Context f6044s;

    /* renamed from: t, reason: collision with root package name */
    private String f6045t;

    /* renamed from: u, reason: collision with root package name */
    private long f6046u;

    /* renamed from: v, reason: collision with root package name */
    private SplashAd f6047v;

    /* renamed from: w, reason: collision with root package name */
    private ViewGroup f6048w;

    /* renamed from: x, reason: collision with root package name */
    private ViewGroup f6049x;

    /* renamed from: y, reason: collision with root package name */
    private View f6050y;

    /* renamed from: z, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6051z;
    private List<AdSpacesBean.RenderViewBean> A = new ArrayList();
    private List<AdSpacesBean.RenderViewBean> B = new ArrayList();
    private boolean C = false;
    private boolean D = false;
    private boolean E = false;
    private boolean F = false;
    private boolean G = false;
    private long H = 5000;
    private String X = null;
    private boolean Y = false;
    private int Z = -1;
    private String aa = BuildConfig.FLAVOR;

    public b(Context context, String str, long j2, View view, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.d.e eVar) {
        this.f6044s = context;
        this.f6045t = str;
        this.f6046u = j2;
        this.f6048w = viewGroup;
        this.f5541e = buyerBean;
        this.f5542f = forwardBean;
        this.f5540d = eVar;
        this.f6049x = new SplashContainer(context);
        this.f6050y = view;
        this.f6051z = list;
        x();
    }

    private void aL() {
        View view;
        View.OnClickListener onClickListener;
        if (this.K || (view = this.f6050y) == null || (onClickListener = this.f6043r) == null) {
            return;
        }
        view.setOnClickListener(onClickListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aM() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", "splashWorkers:" + eVar.p().toString());
        ad();
        com.beizi.fusion.d.h hVar = this.f5543g;
        if (hVar == com.beizi.fusion.d.h.SUCCESS) {
            aL();
            this.f5540d.a(g(), (View) null);
        } else if (hVar == com.beizi.fusion.d.h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    private void aN() {
        ViewGroup viewGroup;
        SplashAd splashAd = this.f6047v;
        if (splashAd == null || !splashAd.isLoaded() || (viewGroup = this.f6048w) == null) {
            aD();
            return;
        }
        viewGroup.removeAllViews();
        this.f6048w.addView(this.f6049x);
        StringBuilder sb = new StringBuilder();
        sb.append("shakeViewBean != null ? ");
        sb.append(this.f6039n != null);
        sb.append(",regionalClickViewBean != null ? ");
        sb.append(this.f6040o != null);
        sb.append(",fullScreenClickBean != null ? ");
        sb.append(this.f6042q != null);
        ac.a("BeiZis", sb.toString());
        this.f6049x.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.beizi.fusion.work.splash.b.4

            /* renamed from: a, reason: collision with root package name */
            boolean f6058a;

            /* renamed from: b, reason: collision with root package name */
            boolean f6059b;

            public void a(boolean z2) {
                if (z2 || b.this.f6047v == null) {
                    return;
                }
                b.this.f6047v.disableFullClick(new View.OnTouchListener() { // from class: com.beizi.fusion.work.splash.b.4.1
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
            }

            /* JADX WARN: Removed duplicated region for block: B:39:0x0117  */
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onGlobalLayout() {
                /*
                    Method dump skipped, instructions count: 358
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.work.splash.b.AnonymousClass4.onGlobalLayout():void");
            }
        });
        this.f6047v.showAd();
        if (this.L != null) {
            this.f6048w.addView(this.L, new FrameLayout.LayoutParams(-2, -2));
        }
        aS();
        if (this.K) {
            aQ();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aO() {
        try {
            AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean = this.f6039n;
            if (shakeViewBean == null || this.ab == null || shakeViewBean.getPosition() == null) {
                return;
            }
            com.beizi.fusion.b.b bVar = this.f5538b;
            if (bVar != null) {
                bVar.G(this.f6039n.getShakeViewUuid());
                aB();
            }
            AdSpacesBean.BuyerBean.OrderDataShakeViewBean orderDataShakeViewBeanA = a(this.f6039n.getOrderData(), this.f6047v.getAdId());
            AdSpacesBean.BuyerBean.ShakeViewBean shakeView = (orderDataShakeViewBeanA == null || orderDataShakeViewBeanA.getShakeView() == null) ? null : orderDataShakeViewBeanA.getShakeView();
            View viewA = this.ab.a(as.b(this.f6044s, this.f6049x.getWidth()), as.b(this.f6044s, this.f6049x.getHeight()), this.f6039n.getPosition());
            if (viewA != null) {
                ViewGroup.LayoutParams layoutParams = viewA.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(marginLayoutParams.width, marginLayoutParams.height);
                    layoutParams2.leftMargin = marginLayoutParams.leftMargin;
                    layoutParams2.topMargin = marginLayoutParams.topMargin;
                    this.f6048w.addView(viewA, layoutParams2);
                }
            }
            a(shakeView);
            this.ab.a(this);
            a(shakeView, viewA);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    private void aP() {
        boolean z2 = false;
        for (int i2 = 0; i2 < this.f6051z.size(); i2++) {
            AdSpacesBean.RenderViewBean renderViewBean = this.f6051z.get(i2);
            String type = renderViewBean.getType();
            if ("SKIPVIEW".equals(type)) {
                this.B.add(renderViewBean);
            } else if ("MATERIALVIEW".equals(type)) {
                this.A.add(renderViewBean);
            }
        }
        if (this.B.size() > 0) {
            AdSpacesBean.RenderViewBean renderViewBean2 = this.B.get(0);
            this.Q = renderViewBean2;
            if (renderViewBean2 != null) {
                this.N = renderViewBean2.getTapPosition();
                this.M = this.Q.getLayerPosition();
                long skipViewTotalTime = this.Q.getSkipViewTotalTime();
                if (skipViewTotalTime > 0) {
                    this.H = skipViewTotalTime;
                }
                long skipUnavailableTime = this.Q.getSkipUnavailableTime();
                if (skipUnavailableTime > 0) {
                    this.J = skipUnavailableTime;
                }
                this.R = this.Q.getShowCountDown();
                this.S = this.Q.getShowBorder();
                String skipText = this.Q.getSkipText();
                this.T = skipText;
                if (TextUtils.isEmpty(skipText)) {
                    this.T = "跳过";
                }
                String textColor = this.Q.getTextColor();
                this.U = textColor;
                if (TextUtils.isEmpty(textColor)) {
                    this.U = "#FFFFFF";
                }
                String countDownColor = this.Q.getCountDownColor();
                this.V = countDownColor;
                if (TextUtils.isEmpty(countDownColor)) {
                    this.V = "#FFFFFF";
                }
                List<AdSpacesBean.PassPolicyBean> passPolicy = this.Q.getPassPolicy();
                if (passPolicy != null && passPolicy.size() > 0) {
                    for (AdSpacesBean.PassPolicyBean passPolicyBean : passPolicy) {
                        String passType = passPolicyBean.getPassType();
                        int passPercent = passPolicyBean.getPassPercent();
                        passType.hashCode();
                        ?? r7 = -1;
                        r7 = -1;
                        r7 = -1;
                        r7 = -1;
                        switch (passType.hashCode()) {
                            case 601561940:
                                if (passType.equals("RANDOMPASS")) {
                                    r7 = z2;
                                    break;
                                }
                                break;
                            case 1028793094:
                                if (passType.equals("WAITPASS")) {
                                    r7 = 1;
                                    break;
                                }
                                break;
                            case 1122973890:
                                if (passType.equals("LAYERPASS")) {
                                    r7 = 2;
                                    break;
                                }
                                break;
                        }
                        switch (r7) {
                            case 0:
                                this.D = ai.a(passPercent);
                                break;
                            case 1:
                                this.C = ai.a(passPercent);
                                break;
                            case 2:
                                AdSpacesBean.PositionBean positionBean = this.M;
                                if (positionBean != null && this.N != null) {
                                    double centerX = positionBean.getCenterX();
                                    double centerY = this.M.getCenterY();
                                    double width = this.M.getWidth();
                                    double height = this.M.getHeight();
                                    double centerX2 = this.N.getCenterX();
                                    double centerY2 = this.N.getCenterY();
                                    double width2 = this.N.getWidth();
                                    double height2 = this.N.getHeight();
                                    if ((centerX > 0.0d && centerX2 > 0.0d && centerX != centerX2) || ((centerY > 0.0d && centerY2 > 0.0d && centerY != centerY2) || ((width > 0.0d && width2 > 0.0d && width != width2) || (height > 0.0d && height2 > 0.0d && height != height2)))) {
                                        this.E = ai.a(passPercent);
                                    }
                                    if (width2 * height2 < width * height) {
                                        this.F = true;
                                        break;
                                    } else {
                                        break;
                                    }
                                } else {
                                    break;
                                }
                        }
                        z2 = false;
                    }
                }
            }
        }
        if (this.A.size() > 0) {
            Collections.sort(this.A, new Comparator<AdSpacesBean.RenderViewBean>() { // from class: com.beizi.fusion.work.splash.b.8
                @Override // java.util.Comparator
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public int compare(AdSpacesBean.RenderViewBean renderViewBean3, AdSpacesBean.RenderViewBean renderViewBean4) {
                    return renderViewBean4.getLevel() - renderViewBean3.getLevel();
                }
            });
        }
    }

    private void aQ() {
        if (this.C) {
            U();
        }
        if (this.D) {
            V();
        }
        if (this.E) {
            W();
        }
        if (this.F) {
            X();
        }
        this.I = this.H - this.J;
        if (this.A.size() > 0) {
            aV();
        }
    }

    private View aR() {
        View view;
        String str;
        this.f6043r = new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.b.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (b.this.E && b.this.f6049x != null) {
                    b bVar = b.this;
                    bVar.a(bVar.f6049x);
                    return;
                }
                if (b.this.D && b.this.f6049x != null) {
                    b bVar2 = b.this;
                    bVar2.a(bVar2.f6049x);
                } else if (b.this.C && b.this.f6049x != null && b.this.G) {
                    b bVar3 = b.this;
                    bVar3.a(bVar3.f6049x);
                } else {
                    if (b.this.L != null) {
                        m.a(b.this.L);
                    }
                    b.this.N();
                }
            }
        };
        if (this.K) {
            View view2 = this.f6050y;
            if (view2 != null) {
                view2.setVisibility(8);
                view2.setAlpha(0.0f);
            }
            SkipView skipView = new SkipView(this.f6044s);
            this.f6050y = skipView;
            skipView.setOnClickListener(this.f6043r);
            CircleProgressView circleProgressView = new CircleProgressView(this.f6044s);
            this.L = circleProgressView;
            circleProgressView.setAlpha(0.0f);
            view = this.L;
            str = "beizi";
        } else {
            view = this.f6050y;
            if (view != null) {
                CircleProgressView circleProgressView2 = new CircleProgressView(this.f6044s);
                this.L = circleProgressView2;
                circleProgressView2.setAlpha(0.0f);
                view = this.L;
                str = "app";
            } else {
                str = "buyer";
            }
        }
        com.beizi.fusion.b.b bVar = this.f5538b;
        if (bVar != null) {
            bVar.r(str);
            aB();
        }
        return view;
    }

    private void aS() {
        if (!this.K) {
            View view = this.f6050y;
            if (view != null) {
                view.setVisibility(0);
                this.f6050y.setAlpha(1.0f);
                return;
            }
            return;
        }
        if (this.M == null || this.Q == null) {
            aT();
            return;
        }
        float f2 = this.O;
        float height = this.f6048w.getHeight();
        if (height == 0.0f) {
            height = this.P - as.a(this.f6044s, 100.0f);
        }
        int width = (int) (f2 * this.M.getWidth() * 0.01d);
        if (this.M.getHeight() < 12.0d) {
            aT();
            return;
        }
        int height2 = (int) (width * this.M.getHeight() * 0.01d);
        int paddingHeight = (int) (height2 * this.Q.getPaddingHeight() * 0.01d);
        if (paddingHeight < 0) {
            paddingHeight = 0;
        }
        ((SkipView) this.f6050y).setData(this.S, paddingHeight);
        g(5);
        this.f6048w.addView(this.f6050y, new FrameLayout.LayoutParams(width, height2));
        float centerX = (f2 * ((float) (this.M.getCenterX() * 0.01d))) - (width / 2);
        float centerY = (height * ((float) (this.M.getCenterY() * 0.01d))) - (height2 / 2);
        this.f6050y.setX(centerX);
        this.f6050y.setY(centerY);
        View view2 = this.f6050y;
        if (view2 != null) {
            view2.setVisibility(0);
        }
    }

    private void aT() {
        int i2 = (int) (this.O * 0.15d);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, (int) (i2 * 0.45d));
        layoutParams.gravity = 53;
        layoutParams.topMargin = as.a(this.f6044s, 20.0f);
        layoutParams.rightMargin = as.a(this.f6044s, 20.0f);
        ViewGroup viewGroup = this.f6048w;
        if (viewGroup != null) {
            viewGroup.addView(this.f6050y, layoutParams);
        }
        View view = this.f6050y;
        if (view != null) {
            this.R = 1;
            this.S = 1;
            ((SkipView) view).setData(1, 0);
            ((SkipView) this.f6050y).setText(String.format("跳过 %d", 5));
            this.f6050y.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aU() {
        float pivotX;
        float pivotY;
        float height;
        View view = this.f6050y;
        if (view == null) {
            return;
        }
        view.getLocationOnScreen(new int[2]);
        if (this.N != null) {
            float f2 = this.O;
            float height2 = this.f6048w.getHeight();
            if (height2 == 0.0f) {
                height2 = this.P - as.a(this.f6044s, 100.0f);
            }
            int width = (int) (f2 * this.N.getWidth() * 0.01d);
            int height3 = (int) (width * this.N.getHeight() * 0.01d);
            ViewGroup.LayoutParams layoutParams = this.L.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height3;
            this.L.setLayoutParams(layoutParams);
            pivotX = (f2 * ((float) (this.N.getCenterX() * 0.01d))) - (width / 2);
            pivotY = height2 * ((float) (this.N.getCenterY() * 0.01d));
            height = height3 / 2;
        } else {
            pivotX = (r2[0] + this.f6050y.getPivotX()) - (this.L.getWidth() / 2);
            pivotY = r2[1] + this.f6050y.getPivotY();
            height = this.L.getHeight() / 2;
        }
        this.L.setX(pivotX);
        this.L.setY(pivotY - height);
    }

    private void aV() {
        for (AdSpacesBean.RenderViewBean renderViewBean : this.A) {
            AdSpacesBean.PositionBean layerPosition = renderViewBean.getLayerPosition();
            ImageView imageView = new ImageView(this.f6044s);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setVisibility(0);
            String imageUrl = renderViewBean.getImageUrl();
            if (!TextUtils.isEmpty(imageUrl) && imageUrl.contains("http")) {
                com.beizi.fusion.g.i.a(this.f6044s).a(imageUrl).a(imageView);
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.b.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (b.this.f6049x != null) {
                        b bVar = b.this;
                        bVar.a(bVar.f6049x);
                    }
                }
            });
            float width = this.f6048w.getWidth();
            float height = this.f6048w.getHeight();
            if (width == 0.0f) {
                width = this.O;
            }
            if (height == 0.0f) {
                height = this.P - as.a(this.f6044s, 100.0f);
            }
            this.f6048w.addView(imageView, new FrameLayout.LayoutParams((int) (width * layerPosition.getWidth() * 0.01d), (int) (height * layerPosition.getHeight() * 0.01d)));
            float centerX = (float) (layerPosition.getCenterX() * 0.01d);
            float centerY = (height * ((float) (layerPosition.getCenterY() * 0.01d))) - (r6 / 2);
            imageView.setX((width * centerX) - (r5 / 2));
            imageView.setY(centerY);
        }
    }

    private void aW() {
        SplashAd splashAd = this.f6047v;
        if (splashAd != null) {
            splashAd.adClick();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aX() {
        try {
            AdSpacesBean.BuyerBean.RegionalClickViewBean regionalClickViewBean = this.f6040o;
            if (regionalClickViewBean == null || this.ac == null || regionalClickViewBean.getPosition() == null) {
                return;
            }
            com.beizi.fusion.b.b bVar = this.f5538b;
            if (bVar != null) {
                bVar.J(this.f6040o.getRegionalClickUuid());
                aB();
            }
            AdSpacesBean.BuyerBean.OrderDataRegionalClickViewBean orderDataRegionalClickViewBeanB = b(this.f6040o.getOrderData(), this.f6047v.getAdId());
            if (orderDataRegionalClickViewBeanB != null) {
                this.ac.a(orderDataRegionalClickViewBeanB.getRegionalClickView());
            } else {
                this.ac.a(this.f6040o);
            }
            View viewA = this.ac.a(as.b(this.f6044s, this.f6049x.getWidth()), as.b(this.f6044s, this.f6049x.getHeight()), this.f6040o.getPosition(), true);
            if (viewA != null) {
                ViewGroup.LayoutParams layoutParams = viewA.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(marginLayoutParams.width, marginLayoutParams.height);
                    layoutParams2.leftMargin = marginLayoutParams.leftMargin;
                    layoutParams2.topMargin = marginLayoutParams.topMargin;
                    try {
                        this.f6048w.addView(viewA, layoutParams2);
                        this.ac.a(this);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aY() {
        try {
            AdSpacesBean.BuyerBean.ScrollClickBean scrollClickBean = this.f6041p;
            if (scrollClickBean == null || this.ad == null || scrollClickBean.getPosition() == null) {
                return;
            }
            com.beizi.fusion.b.b bVar = this.f5538b;
            if (bVar != null) {
                bVar.F(this.f6041p.getScrollClickUuid());
                aB();
            }
            AdSpacesBean.BuyerBean.OrderDataScrollViewOrderBean orderDataScrollViewOrderBeanC = c(this.f6041p.getOrderData(), this.f6047v.getAdId());
            if (orderDataScrollViewOrderBeanC != null) {
                this.ad.a(orderDataScrollViewOrderBeanC.getScrollClick());
            } else {
                this.ad.a(this.f6041p);
            }
            View viewA = this.ad.a(as.b(this.f6044s, this.f6049x.getWidth()), as.b(this.f6044s, this.f6049x.getHeight()), this.f6041p.getPosition());
            if (viewA != null) {
                ViewGroup.LayoutParams layoutParams = viewA.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(marginLayoutParams.width, marginLayoutParams.height);
                    layoutParams2.leftMargin = marginLayoutParams.leftMargin;
                    layoutParams2.topMargin = marginLayoutParams.topMargin;
                    try {
                        this.f6048w.addView(viewA, layoutParams2);
                        this.ad.a(this);
                        a(this.f6041p.getScrollDirection(), this.f6041p.getScrollDistance(), this);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aZ() {
        try {
            AdSpacesBean.BuyerBean.RollViewBean rollView = this.f5541e.getRollView();
            this.ae = rollView;
            if (rollView == null) {
                return;
            }
            AdSpacesBean.BuyerBean.OrderDataRollViewBean orderDataRollViewBeanE = e(rollView.getOrderData(), this.f6047v.getAdId());
            AdSpacesBean.BuyerBean.RollViewBean rollView2 = (orderDataRollViewBeanE == null || orderDataRollViewBeanE.getRollView() == null) ? null : orderDataRollViewBeanE.getRollView();
            if (rollView2 == null) {
                rollView2 = this.ae;
            }
            if (rollView2.getPosition() == null) {
                return;
            }
            com.beizi.fusion.b.b bVar = this.f5538b;
            if (bVar != null) {
                bVar.H(rollView2.getRollViewUuid());
                aB();
            }
            if (this.ag == null) {
                this.ag = new ak(this.f6044s);
            }
            this.ag.a(this.f6048w, as.b(this.f6044s, this.f6049x.getWidth()), as.b(this.f6044s, this.f6049x.getHeight()), rollView2);
            AdSpacesBean.BuyerBean.RollViewBean rollViewBean = this.ae;
            if (rollViewBean != null) {
                this.af = rollViewBean.getCoolRollView();
            }
            a(rollView2);
            this.ag.a();
            final int isClick = rollView2.getIsClick();
            this.ag.a(new ak.a() { // from class: com.beizi.fusion.work.splash.b.2
                @Override // com.beizi.fusion.g.ak.a
                public void a() {
                    if (b.this.Y && b.this.af != null) {
                        aq.a(b.this.f6044s, b.this.X, Long.valueOf(System.currentTimeMillis()));
                    }
                    b.this.aa = PLVDanmakuInfo.FONTMODE_ROLL;
                    ((com.beizi.fusion.work.a) b.this).f5538b.O(PLVDanmakuInfo.FONTMODE_ROLL);
                    b.this.aB();
                    ac.a("BeiZis", "enter onRollHappened  ");
                    b.this.a("", "", "", "", "", "", "", "", 5);
                }

                @Override // com.beizi.fusion.g.ak.a
                public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
                    if (isClick == 1) {
                        b.this.aa = "regionalClick";
                        ((com.beizi.fusion.work.a) b.this).f5538b.O("regionalClick");
                        b.this.aB();
                        ac.a("BeiZis", "enter onClickHappened  ");
                        b.this.a(str, str2, str3, str4, str5, str6, str7, str8, 0);
                        b.this.ag.c();
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.beizi.fusion.g.am.a
    public void c_() {
        this.aa = "scroll";
        this.f5538b.O("scroll");
        aB();
        ac.a("BeiZis", "enter onScrollDistanceMeet ");
        aW();
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "BEIZI";
    }

    private boolean c(long j2) {
        try {
            return System.currentTimeMillis() - this.f6044s.getPackageManager().getPackageInfo(this.f6044s.getPackageName(), 0).firstInstallTime < j2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private AdSpacesBean.BuyerBean.OrderDataRollViewBean e(List<AdSpacesBean.BuyerBean.OrderDataRollViewBean> list, String str) {
        if (list != null && str != null) {
            for (AdSpacesBean.BuyerBean.OrderDataRollViewBean orderDataRollViewBean : list) {
                List<String> orderList = orderDataRollViewBean.getOrderList();
                if (orderList != null && orderList.contains(str)) {
                    return orderDataRollViewBean;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(int i2) {
        if (this.R != 1) {
            SpannableString spannableString = new SpannableString(this.T);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(this.U)), 0, this.T.length(), 33);
            ((SkipView) this.f6050y).setText(spannableString);
            return;
        }
        String strValueOf = String.valueOf(i2);
        String str = this.T + " ";
        String str2 = str + strValueOf;
        SpannableString spannableString2 = new SpannableString(str2);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.U)), 0, str.length(), 33);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.V)), str2.indexOf(strValueOf), str2.length(), 33);
        ((SkipView) this.f6050y).setText(spannableString2);
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f6047v == null) {
            return;
        }
        aq();
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        this.X = "splash_cool_" + this.f5545i;
        ac.b("BeiZis", "AdWorker chanel = " + this.f5539c);
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.beizi.ad.BeiZi")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "BeiZi sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    x.a(this.f6044s, this.f5544h);
                    B();
                }
            }
        }
        long sleepTime = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            sleepTime = Math.max(sleepTime, this.f5542f.getHotRequestDelay());
        }
        List<AdSpacesBean.RenderViewBean> list = this.f6051z;
        boolean z2 = list != null && list.size() > 0;
        this.K = z2;
        if (z2) {
            aP();
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + sleepTime);
        if (sleepTime > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, sleepTime);
        } else {
            com.beizi.fusion.d.e eVar = this.f5540d;
            if (eVar != null && eVar.r() < 1 && this.f5540d.q() != 2) {
                p();
            }
        }
        this.O = as.m(this.f6044s);
        this.P = as.n(this.f6044s);
        this.ab = new ao(this.f6044s);
        this.ac = new aj(this.f6044s);
        this.ad = new am(this.f6044s);
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
        Log.d("BeiZis", g() + " out make show ad");
        try {
            aN();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        SplashAd splashAd = this.f6047v;
        if (splashAd == null) {
            return null;
        }
        return splashAd.getPrice();
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        View viewAR = aR();
        AdSpacesBean.BuyerBean.ShakeViewBean shakeView = this.f5541e.getShakeView();
        this.f6039n = shakeView;
        if (shakeView != null) {
            this.W = shakeView.getCoolShakeView();
        }
        this.f6040o = this.f5541e.getRegionalClickView();
        this.f6042q = this.f5541e.getFullScreenClick();
        this.f6041p = this.f5541e.getScrollClick();
        SplashAd splashAd = new SplashAd(this.f6044s, this.f6049x, viewAR, new AdListener() { // from class: com.beizi.fusion.work.splash.b.3

            /* renamed from: a, reason: collision with root package name */
            boolean f6056a = false;

            /* JADX WARN: Code restructure failed: missing block: B:13:0x0034, code lost:
            
                r0 = com.beizi.fusion.g.ai.a(java.lang.Integer.parseInt(r5.getRate()));
             */
            /* JADX WARN: Removed duplicated region for block: B:24:0x0051  */
            /* JADX WARN: Removed duplicated region for block: B:29:0x0078 A[ADDED_TO_REGION] */
            /* JADX WARN: Removed duplicated region for block: B:34:0x0088  */
            @Override // com.beizi.ad.AdListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onAdClicked() {
                /*
                    r8 = this;
                    java.lang.String r0 = "showBeiZiSplash onAdClicked()"
                    java.lang.String r1 = "BeiZis"
                    android.util.Log.d(r1, r0)
                    r0 = 0
                    com.beizi.fusion.work.splash.b r2 = com.beizi.fusion.work.splash.b.this     // Catch: java.lang.Exception -> L48
                    com.beizi.fusion.model.AdSpacesBean$BuyerBean r2 = com.beizi.fusion.work.splash.b.t(r2)     // Catch: java.lang.Exception -> L48
                    java.util.List r2 = r2.getCallBackStrategy()     // Catch: java.lang.Exception -> L48
                    if (r2 == 0) goto L46
                    int r3 = r2.size()     // Catch: java.lang.Exception -> L48
                    if (r3 <= 0) goto L46
                    r3 = r0
                L1b:
                    r4 = 1
                    int r5 = r2.size()     // Catch: java.lang.Exception -> L44
                    if (r3 >= r5) goto L4d
                    java.lang.Object r5 = r2.get(r3)     // Catch: java.lang.Exception -> L44
                    com.beizi.fusion.model.AdSpacesBean$CallBackStrategyBean r5 = (com.beizi.fusion.model.AdSpacesBean.CallBackStrategyBean) r5     // Catch: java.lang.Exception -> L44
                    java.lang.String r6 = "290.300"
                    java.lang.String r7 = r5.getEventCode()     // Catch: java.lang.Exception -> L44
                    boolean r6 = r6.equalsIgnoreCase(r7)     // Catch: java.lang.Exception -> L44
                    if (r6 == 0) goto L41
                    java.lang.String r2 = r5.getRate()     // Catch: java.lang.Exception -> L44
                    int r2 = java.lang.Integer.parseInt(r2)     // Catch: java.lang.Exception -> L44
                    boolean r0 = com.beizi.fusion.g.ai.a(r2)     // Catch: java.lang.Exception -> L44
                    goto L4d
                L41:
                    int r3 = r3 + 1
                    goto L1b
                L44:
                    r2 = move-exception
                    goto L4a
                L46:
                    r2 = r0
                    goto L4f
                L48:
                    r2 = move-exception
                    r4 = r0
                L4a:
                    r2.printStackTrace()
                L4d:
                    r2 = r0
                    r0 = r4
                L4f:
                    if (r0 == 0) goto L71
                    com.beizi.fusion.work.splash.b r3 = com.beizi.fusion.work.splash.b.this
                    com.beizi.fusion.model.AdSpacesBean$BuyerBean r3 = com.beizi.fusion.work.splash.b.u(r3)
                    if (r3 == 0) goto L71
                    com.beizi.fusion.work.splash.b r3 = com.beizi.fusion.work.splash.b.this
                    com.beizi.fusion.b.b r3 = com.beizi.fusion.work.splash.b.w(r3)
                    com.beizi.fusion.work.splash.b r4 = com.beizi.fusion.work.splash.b.this
                    com.beizi.fusion.model.AdSpacesBean$BuyerBean r4 = com.beizi.fusion.work.splash.b.v(r4)
                    java.lang.String r4 = r4.getCallBackStrategyUuid()
                    r3.P(r4)
                    com.beizi.fusion.work.splash.b r3 = com.beizi.fusion.work.splash.b.this
                    com.beizi.fusion.work.splash.b.x(r3)
                L71:
                    com.beizi.fusion.work.splash.b r3 = com.beizi.fusion.work.splash.b.this
                    com.beizi.fusion.work.splash.b.y(r3)
                    if (r0 == 0) goto L80
                    if (r2 != 0) goto L80
                    java.lang.String r0 = "strategy not pass"
                    android.util.Log.e(r1, r0)
                    return
                L80:
                    com.beizi.fusion.work.splash.b r0 = com.beizi.fusion.work.splash.b.this
                    com.beizi.fusion.d.e r0 = com.beizi.fusion.work.splash.b.z(r0)
                    if (r0 == 0) goto L9c
                    com.beizi.fusion.work.splash.b r0 = com.beizi.fusion.work.splash.b.this
                    com.beizi.fusion.d.e r0 = com.beizi.fusion.work.splash.b.A(r0)
                    com.beizi.fusion.work.splash.b r1 = com.beizi.fusion.work.splash.b.this
                    java.lang.String r1 = r1.g()
                    r0.d(r1)
                    com.beizi.fusion.work.splash.b r0 = com.beizi.fusion.work.splash.b.this
                    com.beizi.fusion.work.splash.b.B(r0)
                L9c:
                    com.beizi.fusion.work.splash.b r0 = com.beizi.fusion.work.splash.b.this
                    com.beizi.fusion.work.splash.b.C(r0)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.work.splash.b.AnonymousClass3.onAdClicked():void");
            }

            @Override // com.beizi.ad.AdListener
            public void onAdClosed() {
                Log.d("BeiZis", "showBeiZiSplash onAdClosed()");
                if (((com.beizi.fusion.work.a) b.this).f5540d != null) {
                    ((com.beizi.fusion.work.a) b.this).f5540d.c(b.this.g());
                }
                b.this.M();
                if (b.this.ab != null) {
                    b.this.ab.c();
                }
                if (b.this.ac != null) {
                    b.this.ac.a();
                }
                if (b.this.ad != null) {
                    b.this.ad.b();
                }
                if (b.this.ag != null) {
                    b.this.ag.b();
                }
            }

            @Override // com.beizi.ad.AdListener
            public void onAdFailedToLoad(int i2) {
                Log.d("BeiZis", "showBeiZiSplash onAdFailedToLoad:" + i2);
                b.this.b(String.valueOf(i2), i2);
            }

            @Override // com.beizi.ad.AdListener
            public void onAdLoaded() {
                Log.d("BeiZis", "showBeiZiSplash onAdLoaded:" + System.currentTimeMillis());
                ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                if (b.this.f6047v.getPrice() != null) {
                    try {
                        b bVar = b.this;
                        bVar.a(Double.parseDouble(bVar.f6047v.getPrice()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                b.this.E();
                if (b.this.ac()) {
                    b.this.aM();
                } else {
                    b.this.S();
                }
            }

            @Override // com.beizi.ad.AdListener
            public void onAdShown() {
                Log.d("BeiZis", "showBeiZiSplash onAdShown()");
                ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) b.this).f5540d != null) {
                    if (((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) b.this).f5540d.b(b.this.g());
                    }
                    b.this.am();
                }
                b.this.I();
                b.this.Y();
                b.this.J();
            }

            @Override // com.beizi.ad.AdListener
            public void onAdTick(long j2) {
                if (!this.f6056a) {
                    b.this.aU();
                    this.f6056a = true;
                }
                if (b.this.K) {
                    if (b.this.J > 0 && b.this.J <= b.this.H) {
                        if (b.this.C) {
                            if (b.this.I <= 0 || j2 <= b.this.I) {
                                b.this.G = false;
                                b.this.f6050y.setAlpha(1.0f);
                            } else {
                                b.this.G = true;
                                b.this.f6050y.setAlpha(0.2f);
                            }
                        }
                        if (b.this.J == b.this.H) {
                            b.this.f6050y.setEnabled(false);
                        } else {
                            b.this.f6050y.setEnabled(true);
                        }
                    }
                    b.this.g(Math.round(j2 / 1000.0f));
                }
                if (((com.beizi.fusion.work.a) b.this).f5540d == null || ((com.beizi.fusion.work.a) b.this).f5540d.q() == 2) {
                    return;
                }
                ((com.beizi.fusion.work.a) b.this).f5540d.a(j2);
            }
        }, this.f5545i);
        this.f6047v = splashAd;
        splashAd.setCloseButtonPadding(10, 20, 10, 10);
        this.f6047v.openAdInNativeBrowser(true);
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        super.q();
        SplashAd splashAd = this.f6047v;
        if (splashAd != null) {
            splashAd.cancel();
            this.f6047v = null;
        }
    }

    private boolean b(long j2) {
        long jLongValue = ((Long) aq.b(this.f6044s, this.X, 0L)).longValue();
        return jLongValue != 0 && System.currentTimeMillis() - jLongValue < j2;
    }

    private AdSpacesBean.BuyerBean.OrderDataScrollViewOrderBean c(List<AdSpacesBean.BuyerBean.OrderDataScrollViewOrderBean> list, String str) {
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

    private boolean b(AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean) {
        if (shakeViewBean != null) {
            AdSpacesBean.BuyerBean.AliaseShakeViewBean aliaseShakeView = shakeViewBean.getAliaseShakeView();
            if (aliaseShakeView != null && aliaseShakeView.getPassivationTime() > 0 && (aliaseShakeView.getShakeCount() > 0 || aliaseShakeView.getRotatCount() > 0)) {
                return true;
            }
        } else {
            AdSpacesBean.BuyerBean.AliaseShakeViewBean aliaseShakeView2 = this.f6039n.getAliaseShakeView();
            if (this.f6039n.getAliaseShakeView() != null && aliaseShakeView2.getPassivationTime() > 0 && (aliaseShakeView2.getShakeCount() > 0 || aliaseShakeView2.getRotatCount() > 0)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.beizi.fusion.work.a
    public void a(Message message) {
        com.beizi.fusion.b.b bVar = this.f5538b;
        if (bVar != null) {
            bVar.i(String.valueOf(message.obj));
            this.f5538b.m(String.valueOf(message.obj));
            aB();
            H();
        }
    }

    private void a(AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean) {
        final AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean2 = shakeViewBean != null ? shakeViewBean : this.f6039n;
        AdSpacesBean.BuyerBean.CoolShakeViewBean coolShakeViewBean = this.W;
        if (coolShakeViewBean == null) {
            if (b(shakeViewBean)) {
                AdSpacesBean.BuyerBean.AliaseShakeViewBean aliaseShakeView = shakeViewBean2.getAliaseShakeView();
                if (aliaseShakeView != null) {
                    this.ab.a(aliaseShakeView);
                    new Handler().postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.b.5
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.Y = true;
                            b.this.ab.a(shakeViewBean2);
                        }
                    }, ai.b(aliaseShakeView.getPassivationTime()));
                    return;
                }
                return;
            }
            this.Y = true;
            this.ab.a(shakeViewBean2);
            return;
        }
        if (b(coolShakeViewBean.getCoolTime())) {
            this.Z = this.W.getFeedback();
            this.ab.a(this.W);
            return;
        }
        if (c(this.W.getUserProtectTime())) {
            this.Z = this.W.getFeedback();
            this.ab.a(this.W);
        } else {
            if (b(shakeViewBean)) {
                AdSpacesBean.BuyerBean.AliaseShakeViewBean aliaseShakeView2 = shakeViewBean2.getAliaseShakeView();
                if (aliaseShakeView2 != null) {
                    this.ab.a(aliaseShakeView2);
                    new Handler().postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.b.6
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.Y = true;
                            b.this.ab.a(shakeViewBean2);
                        }
                    }, ai.b(aliaseShakeView2.getPassivationTime()));
                    return;
                }
                return;
            }
            this.Y = true;
            this.ab.a(shakeViewBean2);
        }
    }

    private AdSpacesBean.BuyerBean.OrderDataRegionalClickViewBean b(List<AdSpacesBean.BuyerBean.OrderDataRegionalClickViewBean> list, String str) {
        if (list != null && str != null) {
            for (AdSpacesBean.BuyerBean.OrderDataRegionalClickViewBean orderDataRegionalClickViewBean : list) {
                List<String> orderList = orderDataRegionalClickViewBean.getOrderList();
                if (orderList != null && orderList.contains(str)) {
                    return orderDataRegionalClickViewBean;
                }
            }
        }
        return null;
    }

    @Override // com.beizi.fusion.g.ao.a
    public void b() {
        if (this.Y && this.W != null) {
            aq.a(this.f6044s, this.X, Long.valueOf(System.currentTimeMillis()));
        }
        this.aa = "shake";
        this.f5538b.O("shake");
        aB();
        ac.a("BeiZis", "enter onShakeHappened  ");
        a("", "", "", "", "", "", "", "", 2);
    }

    @Override // com.beizi.fusion.g.am.a
    public void b(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.aa = "scroll";
        this.f5538b.O("scroll");
        aB();
        ac.a("BeiZis", "enter onScrollDistanceMeetByPosition ");
        a(str, str2, str3, str4, str5, str6, str7, str8, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AdSpacesBean.BuyerBean.OrderDataFullScreenClickBean d(List<AdSpacesBean.BuyerBean.OrderDataFullScreenClickBean> list, String str) {
        if (list != null && str != null) {
            for (AdSpacesBean.BuyerBean.OrderDataFullScreenClickBean orderDataFullScreenClickBean : list) {
                List<String> orderList = orderDataFullScreenClickBean.getOrderList();
                if (orderList != null && orderList.contains(str)) {
                    return orderDataFullScreenClickBean;
                }
            }
        }
        return null;
    }

    private void a(AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean, View view) {
        if (this.Z == -1) {
            this.Z = this.f6039n.getFeedback();
            if (shakeViewBean != null) {
                this.Z = shakeViewBean.getFeedback();
            }
        }
        if (this.Z == 0 || view == null || this.ab == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ShakeArcView shakeArcView = new ShakeArcView(this.f6044s);
            int i2 = (int) (r9.width * 0.5d);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(i2, i2);
            layoutParams2.leftMargin = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + (((r9.width - i2) - 12) / 2);
            layoutParams2.topMargin = (int) (r9.topMargin + (r9.height * 0.08d));
            this.f6048w.addView(shakeArcView, layoutParams2);
            this.ab.a(shakeArcView, this.Z);
        }
    }

    private void a(final String str, int i2, final am.a aVar) {
        final int iA = as.a(this.f6044s, i2);
        this.f6047v.setScrollClick(new View.OnTouchListener() { // from class: com.beizi.fusion.work.splash.b.7

            /* renamed from: a, reason: collision with root package name */
            float f6066a;

            /* renamed from: b, reason: collision with root package name */
            float f6067b;

            /* renamed from: c, reason: collision with root package name */
            float f6068c;

            /* renamed from: d, reason: collision with root package name */
            float f6069d;

            /* renamed from: e, reason: collision with root package name */
            float f6070e;

            /* renamed from: f, reason: collision with root package name */
            float f6071f;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                am.a aVar2;
                am.a aVar3;
                am.a aVar4;
                am.a aVar5;
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.f6066a = motionEvent.getX();
                    this.f6067b = motionEvent.getY();
                    this.f6068c = motionEvent.getX();
                    this.f6069d = motionEvent.getY();
                    this.f6070e = motionEvent.getRawX();
                    this.f6071f = motionEvent.getRawY();
                } else if (action == 1) {
                    ac.b("ScrollClickUtil", "mCurPosX = " + this.f6068c + ",mCurPosY = " + this.f6069d + ",mPosX = " + this.f6066a + ",mPosY = " + this.f6067b);
                    float f2 = this.f6069d;
                    float f3 = this.f6067b;
                    if (f2 - f3 <= 0.0f || Math.abs(f2 - f3) <= iA) {
                        float f4 = this.f6069d;
                        float f5 = this.f6067b;
                        if (f4 - f5 >= 0.0f || Math.abs(f4 - f5) <= iA) {
                            float f6 = this.f6068c;
                            float f7 = this.f6066a;
                            if (f6 - f7 >= 0.0f || Math.abs(f6 - f7) <= iA) {
                                float f8 = this.f6068c;
                                float f9 = this.f6066a;
                                if (f8 - f9 < 0.0f && Math.abs(f8 - f9) > iA && "right".equalsIgnoreCase(str) && (aVar2 = aVar) != null) {
                                    aVar2.b(this.f6066a + "", this.f6067b + "", this.f6070e + "", this.f6071f + "", motionEvent.getX() + "", motionEvent.getY() + "", motionEvent.getRawX() + "", motionEvent.getRawY() + "");
                                }
                            } else if ("left".equalsIgnoreCase(str) && (aVar3 = aVar) != null) {
                                aVar3.b(this.f6066a + "", this.f6067b + "", this.f6070e + "", this.f6071f + "", motionEvent.getX() + "", motionEvent.getY() + "", motionEvent.getRawX() + "", motionEvent.getRawY() + "");
                            }
                        } else if ("up".equalsIgnoreCase(str) && (aVar4 = aVar) != null) {
                            aVar4.b(this.f6066a + "", this.f6067b + "", this.f6070e + "", this.f6071f + "", motionEvent.getX() + "", motionEvent.getY() + "", motionEvent.getRawX() + "", motionEvent.getRawY() + "");
                        }
                    } else if (ScrollClickView.DIR_DOWN.equalsIgnoreCase(str) && (aVar5 = aVar) != null) {
                        aVar5.b(this.f6066a + "", this.f6067b + "", this.f6070e + "", this.f6071f + "", motionEvent.getX() + "", motionEvent.getY() + "", motionEvent.getRawX() + "", motionEvent.getRawY() + "");
                    }
                } else if (action == 2) {
                    this.f6068c = motionEvent.getX();
                    this.f6069d = motionEvent.getY();
                }
                return true;
            }
        });
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

    /* JADX INFO: Access modifiers changed from: private */
    public void a(View view) {
        float fRandom = (int) ((Math.random() * 10.0d) + 1.0d);
        m.a(view, view.getPivotX() - fRandom, view.getPivotY() - fRandom);
    }

    @Override // com.beizi.fusion.g.aj.a
    public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.aa = "regionalClick";
        this.f5538b.O("regionalClick");
        aB();
        ac.a("BeiZis", "enter onRegionClickByPosition ");
        a(str, str2, str3, str4, str5, str6, str7, str8, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i2) {
        SplashAd splashAd = this.f6047v;
        if (splashAd != null) {
            splashAd.adClick(str, str2, str3, str4, str5, str6, str7, str8, i2);
        }
    }

    private void a(AdSpacesBean.BuyerBean.RollViewBean rollViewBean) {
        AdSpacesBean.BuyerBean.CoolRollViewBean coolRollViewBean = this.af;
        if (coolRollViewBean == null) {
            this.Y = true;
            this.ag.a(rollViewBean);
        } else if (b(coolRollViewBean.getCoolTime())) {
            this.ag.a(this.af);
        } else if (c(this.af.getUserProtectTime())) {
            this.ag.a(this.af);
        } else {
            this.Y = true;
            this.ag.a(rollViewBean);
        }
    }
}
