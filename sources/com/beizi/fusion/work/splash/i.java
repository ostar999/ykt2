package com.beizi.fusion.work.splash;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ai;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.m;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.widget.CircleProgressView;
import com.beizi.fusion.widget.SkipView;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.listeners.NativeAdEventListener;
import com.inmobi.sdk.InMobiSdk;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public class i extends com.beizi.fusion.work.a {
    private long F;
    private long G;
    private boolean H;
    private CircleProgressView I;
    private AdSpacesBean.PositionBean J;
    private AdSpacesBean.PositionBean K;
    private List<View> L;
    private float M;
    private float N;
    private AdSpacesBean.RenderViewBean O;
    private int P;
    private int Q;
    private String R;
    private String S;
    private String T;
    private InMobiNative U;
    private Long V;
    private CountDownTimer W;
    private boolean Y;
    private NativeAdEventListener Z;

    /* renamed from: n, reason: collision with root package name */
    int f6200n;

    /* renamed from: o, reason: collision with root package name */
    long f6201o;

    /* renamed from: p, reason: collision with root package name */
    View f6202p;

    /* renamed from: q, reason: collision with root package name */
    private Context f6203q;

    /* renamed from: r, reason: collision with root package name */
    private String f6204r;

    /* renamed from: s, reason: collision with root package name */
    private long f6205s;

    /* renamed from: t, reason: collision with root package name */
    private View f6206t;

    /* renamed from: u, reason: collision with root package name */
    private ViewGroup f6207u;

    /* renamed from: v, reason: collision with root package name */
    private ViewGroup f6208v;

    /* renamed from: w, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6209w;

    /* renamed from: x, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6210x = new ArrayList();

    /* renamed from: y, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6211y = new ArrayList();

    /* renamed from: z, reason: collision with root package name */
    private boolean f6212z = false;
    private boolean A = false;
    private boolean B = false;
    private boolean C = false;
    private boolean D = false;
    private long E = 5000;
    private boolean X = false;

    public i(Context context, String str, long j2, View view, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.d.e eVar) {
        this.Y = false;
        this.f6203q = context;
        this.f6204r = str;
        this.f6205s = j2;
        if (this.f6206t == null) {
            this.f6206t = new SkipView(context);
            this.Y = true;
        } else {
            this.f6206t = view;
        }
        this.f6207u = viewGroup;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f6208v = new SplashContainer(context);
        this.f6209w = list;
        x();
    }

    private void aL() {
        StringBuilder sb = new StringBuilder();
        sb.append("finalShowAd mParent != null ? ");
        sb.append(this.f6207u != null);
        sb.append(",!mIsLoadAdView = ");
        sb.append(!this.X);
        ac.c("BeiZis", sb.toString());
        if (this.f6207u == null) {
            aD();
            return;
        }
        if (!this.X) {
            this.f6206t.setVisibility(8);
        }
        aM();
        this.f6208v.removeAllViews();
        ac.c("BeiZis", "mAdContainer.getWidth() = " + this.f6208v.getWidth());
        InMobiNative inMobiNative = this.U;
        Context context = this.f6203q;
        ViewGroup viewGroup = this.f6208v;
        View primaryViewOfWidth = inMobiNative.getPrimaryViewOfWidth(context, viewGroup, viewGroup, viewGroup.getWidth());
        this.f6208v.addView(primaryViewOfWidth);
        if (primaryViewOfWidth instanceof ViewGroup) {
            final ViewGroup viewGroup2 = (ViewGroup) primaryViewOfWidth;
            viewGroup2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.beizi.fusion.work.splash.i.3
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    ViewGroup viewGroup3 = viewGroup2;
                    if (viewGroup3 == null) {
                        return;
                    }
                    viewGroup3.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    ViewGroup.LayoutParams layoutParams = viewGroup2.getLayoutParams();
                    int i2 = layoutParams.height;
                    int height = i.this.f6208v.getHeight();
                    ac.c("BeiZis", "adsHeight = " + i2 + ",containerHeight = " + height);
                    if (i2 < height) {
                        layoutParams.height = height;
                    }
                }
            });
        }
        this.f6207u.removeAllViews();
        this.f6207u.addView(this.f6208v);
        this.X = true;
        this.f6206t.setVisibility(0);
        if (this.I != null) {
            this.f6207u.addView(this.I, new FrameLayout.LayoutParams(-2, -2));
        }
        aR();
        if (this.H) {
            aP();
        }
    }

    private void aM() {
        aN();
        if (this.Y && (this.f6206t instanceof CircleProgressView)) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 53;
            layoutParams.topMargin = as.a(this.f6203q, 20.0f);
            layoutParams.rightMargin = as.a(this.f6203q, 20.0f);
            this.f6208v.addView(this.f6206t, layoutParams);
        }
    }

    private void aN() {
        StringBuilder sb = new StringBuilder();
        sb.append("addCloseButton mSkipView != null ? ");
        sb.append(this.f6206t != null);
        ac.a("BeiZis", sb.toString());
        if (this.f6206t != null) {
            CountDownTimer countDownTimer = this.W;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            CountDownTimer countDownTimer2 = new CountDownTimer(this.E, 50L) { // from class: com.beizi.fusion.work.splash.i.4

                /* renamed from: a, reason: collision with root package name */
                boolean f6217a = false;

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    i.this.M();
                    ((com.beizi.fusion.work.a) i.this).f5540d.c(i.this.g());
                }

                @Override // android.os.CountDownTimer
                public void onTick(long j2) {
                    if (!this.f6217a) {
                        if (i.this.H) {
                            i iVar = i.this;
                            iVar.L = m.b(iVar.f6208v);
                        }
                        i.this.aT();
                        this.f6217a = true;
                    }
                    if (i.this.H) {
                        if (i.this.G > 0 && i.this.G <= i.this.E) {
                            if (i.this.f6212z) {
                                if (i.this.F <= 0 || j2 <= i.this.F) {
                                    i.this.D = false;
                                    i.this.f6206t.setAlpha(1.0f);
                                } else {
                                    i.this.D = true;
                                    i.this.f6206t.setAlpha(0.2f);
                                }
                            }
                            if (i.this.G == i.this.E) {
                                i.this.f6206t.setEnabled(false);
                            } else {
                                i.this.f6206t.setEnabled(true);
                            }
                        }
                        i.this.g(Math.round(j2 / 1000.0f));
                    }
                    if (i.this.X) {
                        if (i.this.G == i.this.E) {
                            i.this.f6206t.setEnabled(false);
                        } else {
                            i.this.f6206t.setEnabled(true);
                        }
                    }
                    int i2 = (int) ((5 * j2) / i.this.E);
                    if (i.this.f6206t instanceof SkipView) {
                        ((SkipView) i.this.f6206t).setText(String.format("跳过 %d", Integer.valueOf(i2)));
                    } else {
                        if (((com.beizi.fusion.work.a) i.this).f5540d == null || ((com.beizi.fusion.work.a) i.this).f5540d.q() == 2) {
                            return;
                        }
                        ((com.beizi.fusion.work.a) i.this).f5540d.a(j2);
                    }
                }
            };
            this.W = countDownTimer2;
            countDownTimer2.start();
            this.f6206t.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.i.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    i.this.M();
                    i.this.W.cancel();
                    ((com.beizi.fusion.work.a) i.this).f5540d.c(i.this.g());
                }
            });
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
    private void aO() {
        boolean z2 = false;
        for (int i2 = 0; i2 < this.f6209w.size(); i2++) {
            AdSpacesBean.RenderViewBean renderViewBean = this.f6209w.get(i2);
            String type = renderViewBean.getType();
            if ("SKIPVIEW".equals(type)) {
                this.f6211y.add(renderViewBean);
            } else if ("MATERIALVIEW".equals(type)) {
                this.f6210x.add(renderViewBean);
            }
        }
        if (this.f6211y.size() > 0) {
            AdSpacesBean.RenderViewBean renderViewBean2 = this.f6211y.get(0);
            this.O = renderViewBean2;
            if (renderViewBean2 != null) {
                this.K = renderViewBean2.getTapPosition();
                this.J = this.O.getLayerPosition();
                long skipUnavailableTime = this.O.getSkipUnavailableTime();
                if (skipUnavailableTime > 0) {
                    this.G = skipUnavailableTime;
                }
                this.P = this.O.getShowCountDown();
                this.Q = this.O.getShowBorder();
                String skipText = this.O.getSkipText();
                this.R = skipText;
                if (TextUtils.isEmpty(skipText)) {
                    this.R = "跳过";
                }
                String textColor = this.O.getTextColor();
                this.S = textColor;
                if (TextUtils.isEmpty(textColor)) {
                    this.S = "#FFFFFF";
                }
                String countDownColor = this.O.getCountDownColor();
                this.T = countDownColor;
                if (TextUtils.isEmpty(countDownColor)) {
                    this.T = "#FFFFFF";
                }
                List<AdSpacesBean.PassPolicyBean> passPolicy = this.O.getPassPolicy();
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
                                this.A = ai.a(passPercent);
                                break;
                            case 1:
                                this.f6212z = ai.a(passPercent);
                                break;
                            case 2:
                                AdSpacesBean.PositionBean positionBean = this.J;
                                if (positionBean != null && this.K != null) {
                                    double centerX = positionBean.getCenterX();
                                    double centerY = this.J.getCenterY();
                                    double width = this.J.getWidth();
                                    double height = this.J.getHeight();
                                    double centerX2 = this.K.getCenterX();
                                    double centerY2 = this.K.getCenterY();
                                    double width2 = this.K.getWidth();
                                    double height2 = this.K.getHeight();
                                    if ((centerX > 0.0d && centerX2 > 0.0d && centerX != centerX2) || ((centerY > 0.0d && centerY2 > 0.0d && centerY != centerY2) || ((width > 0.0d && width2 > 0.0d && width != width2) || (height > 0.0d && height2 > 0.0d && height != height2)))) {
                                        this.B = ai.a(passPercent);
                                    }
                                    if (width2 * height2 < width * height) {
                                        this.C = true;
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
        if (this.f6210x.size() > 0) {
            Collections.sort(this.f6210x, new Comparator<AdSpacesBean.RenderViewBean>() { // from class: com.beizi.fusion.work.splash.i.6
                @Override // java.util.Comparator
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public int compare(AdSpacesBean.RenderViewBean renderViewBean3, AdSpacesBean.RenderViewBean renderViewBean4) {
                    return renderViewBean4.getLevel() - renderViewBean3.getLevel();
                }
            });
        }
    }

    private void aP() {
        if (this.f6212z) {
            U();
        }
        if (this.A) {
            V();
        }
        if (this.B) {
            W();
        }
        if (this.C) {
            X();
        }
        this.F = this.E - this.G;
        if (this.f6210x.size() > 0) {
            aU();
        }
    }

    private View aQ() {
        String str;
        View view;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.i.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                View view3 = i.this.L != null ? (View) i.this.L.get(1) : null;
                if (i.this.B && view3 != null) {
                    m.a(view3);
                    return;
                }
                if (i.this.A && view3 != null) {
                    m.a(view3);
                    return;
                }
                if (i.this.f6212z && view3 != null && i.this.D) {
                    m.a(view3);
                    return;
                }
                if (i.this.I != null) {
                    m.a(i.this.I);
                }
                i.this.N();
            }
        };
        if (this.H) {
            View view2 = this.f6206t;
            if (view2 != null) {
                view2.setVisibility(8);
                view2.setAlpha(0.0f);
            }
            SkipView skipView = new SkipView(this.f6203q);
            this.f6206t = skipView;
            skipView.setOnClickListener(onClickListener);
            CircleProgressView circleProgressView = new CircleProgressView(this.f6203q);
            this.I = circleProgressView;
            circleProgressView.setAlpha(0.0f);
            view = this.I;
            str = "beizi";
        } else {
            View view3 = this.f6206t;
            if (view3 != null) {
                view3.setOnClickListener(onClickListener);
                CircleProgressView circleProgressView2 = new CircleProgressView(this.f6203q);
                this.I = circleProgressView2;
                circleProgressView2.setAlpha(0.0f);
                view = this.I;
                str = "app";
            } else {
                str = "buyer";
                view = view3;
            }
        }
        com.beizi.fusion.b.b bVar = this.f5538b;
        if (bVar != null) {
            bVar.r(str);
            aB();
        }
        return view;
    }

    private void aR() {
        ac.a("BeiZis", "enter checkDisplaySkipView mNeedRender = " + this.H);
        if (!this.H) {
            aS();
            return;
        }
        if (this.J == null || this.O == null) {
            aS();
            return;
        }
        float f2 = this.M;
        float height = this.f6207u.getHeight();
        if (height == 0.0f) {
            height = this.N - as.a(this.f6203q, 100.0f);
        }
        int width = (int) (f2 * this.J.getWidth() * 0.01d);
        if (this.J.getHeight() < 12.0d) {
            aS();
            return;
        }
        int height2 = (int) (width * this.J.getHeight() * 0.01d);
        int paddingHeight = (int) (height2 * this.O.getPaddingHeight() * 0.01d);
        if (paddingHeight < 0) {
            paddingHeight = 0;
        }
        ((SkipView) this.f6206t).setData(this.Q, paddingHeight);
        g(5);
        this.f6207u.addView(this.f6206t, new FrameLayout.LayoutParams(width, height2));
        float centerX = (f2 * ((float) (this.J.getCenterX() * 0.01d))) - (width / 2);
        float centerY = (height * ((float) (this.J.getCenterY() * 0.01d))) - (height2 / 2);
        this.f6206t.setX(centerX);
        this.f6206t.setY(centerY);
        View view = this.f6206t;
        if (view != null) {
            view.setVisibility(0);
        }
    }

    private void aS() {
        int i2 = (int) (this.M * 0.15d);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, (int) (i2 * 0.45d));
        layoutParams.gravity = 53;
        layoutParams.topMargin = as.a(this.f6203q, 20.0f);
        layoutParams.rightMargin = as.a(this.f6203q, 20.0f);
        ViewGroup viewGroup = this.f6207u;
        if (viewGroup != null) {
            viewGroup.addView(this.f6206t, layoutParams);
        }
        View view = this.f6206t;
        if (view != null) {
            this.P = 1;
            this.Q = 1;
            ((SkipView) view).setData(1, 0);
            ((SkipView) this.f6206t).setText(String.format("跳过 %d", 5));
            this.f6206t.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aT() {
        float pivotX;
        float pivotY;
        float height;
        View view = this.f6206t;
        if (view == null) {
            return;
        }
        view.getLocationOnScreen(new int[2]);
        if (this.K != null) {
            float f2 = this.M;
            float height2 = this.f6207u.getHeight();
            if (height2 == 0.0f) {
                height2 = this.N - as.a(this.f6203q, 100.0f);
            }
            int width = (int) (f2 * this.K.getWidth() * 0.01d);
            int height3 = (int) (width * this.K.getHeight() * 0.01d);
            ViewGroup.LayoutParams layoutParams = this.I.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height3;
            this.I.setLayoutParams(layoutParams);
            pivotX = (f2 * ((float) (this.K.getCenterX() * 0.01d))) - (width / 2);
            pivotY = height2 * ((float) (this.K.getCenterY() * 0.01d));
            height = height3 / 2;
        } else {
            pivotX = (r2[0] + this.f6206t.getPivotX()) - (this.I.getWidth() / 2);
            pivotY = r2[1] + this.f6206t.getPivotY();
            height = this.I.getHeight() / 2;
        }
        this.I.setX(pivotX);
        this.I.setY(pivotY - height);
    }

    private void aU() {
        for (AdSpacesBean.RenderViewBean renderViewBean : this.f6210x) {
            AdSpacesBean.PositionBean layerPosition = renderViewBean.getLayerPosition();
            ImageView imageView = new ImageView(this.f6203q);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setVisibility(0);
            String imageUrl = renderViewBean.getImageUrl();
            if (!TextUtils.isEmpty(imageUrl) && imageUrl.contains("http")) {
                com.beizi.fusion.g.i.a(this.f6203q).a(imageUrl).a(imageView);
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.i.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    View view2 = i.this.L != null ? (View) i.this.L.get(1) : null;
                    if (view2 != null) {
                        m.a(view2);
                    }
                }
            });
            float width = this.f6207u.getWidth();
            float height = this.f6207u.getHeight();
            if (width == 0.0f) {
                width = this.M;
            }
            if (height == 0.0f) {
                height = this.N - as.a(this.f6203q, 100.0f);
            }
            this.f6207u.addView(imageView, new FrameLayout.LayoutParams((int) (width * layerPosition.getWidth() * 0.01d), (int) (height * layerPosition.getHeight() * 0.01d)));
            float centerX = (float) (layerPosition.getCenterX() * 0.01d);
            float centerY = (height * ((float) (layerPosition.getCenterY() * 0.01d))) - (r6 / 2);
            imageView.setX((width * centerX) - (r5 / 2));
            imageView.setY(centerY);
        }
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "INMOBI";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(int i2) {
        if (this.P != 1) {
            SpannableString spannableString = new SpannableString(this.R);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(this.S)), 0, this.R.length(), 33);
            ((SkipView) this.f6206t).setText(spannableString);
            return;
        }
        String strValueOf = String.valueOf(i2);
        String str = this.R + " ";
        String str2 = str + strValueOf;
        SpannableString spannableString2 = new SpannableString(str2);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.S)), 0, str.length(), 33);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.T)), str2.indexOf(strValueOf), str2.length(), 33);
        ((SkipView) this.f6206t).setText(spannableString2);
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.U == null) {
            return;
        }
        aq();
        this.f5541e.setAvgPrice(this.U.getAdBid());
        ac.a("BeiZisBid", "inmobi splash price = " + this.U.getAdBid());
        com.beizi.fusion.b.b bVar = this.f5538b;
        if (bVar != null) {
            bVar.M(String.valueOf(this.f5541e.getAvgPrice()));
            aB();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        boolean z2;
        if (this.f5540d == null) {
            return;
        }
        this.f6201o = System.currentTimeMillis();
        this.f5544h = this.f5541e.getAppId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        ac.b("BeiZis", "AdWorker chanel = " + this.f5539c);
        boolean z3 = false;
        try {
            this.V = Long.valueOf(this.f5541e.getSpaceId());
            z2 = false;
        } catch (Exception unused) {
            this.f5546j = com.beizi.fusion.f.a.ADFAIL;
            this.f5549m.sendMessage(this.f5549m.obtainMessage(3, "Inmobi spaceId pattern problem!"));
            z2 = true;
        }
        if (z2) {
            return;
        }
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.inmobi.sdk.InMobiSdk")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.i.1
                        @Override // java.lang.Runnable
                        public void run() {
                            i.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "IMB sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    InMobiSdk.init(this.f6203q, this.f5544h);
                    InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
                    this.f5538b.w(InMobiSdk.getVersion());
                    aB();
                    B();
                }
            }
        }
        this.f6200n = this.f5541e.getReqTimeOutType();
        long sleepTime = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            sleepTime = Math.max(sleepTime, this.f5542f.getHotRequestDelay());
        }
        List<AdSpacesBean.RenderViewBean> list = this.f6209w;
        if (list != null && list.size() > 0) {
            z3 = true;
        }
        this.H = z3;
        if (z3) {
            aO();
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.V + "===" + sleepTime);
        if (sleepTime > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, sleepTime);
        } else {
            com.beizi.fusion.d.e eVar = this.f5540d;
            if (eVar != null && eVar.r() < 1 && this.f5540d.q() != 2) {
                p();
            }
        }
        this.M = as.m(this.f6203q);
        this.N = as.n(this.f6203q);
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
        Log.d("BeiZis", g() + " out make show ad");
        aL();
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        if (this.U == null) {
            return null;
        }
        return this.U.getAdBid() + "";
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        this.f6202p = aQ();
        this.Z = new NativeAdEventListener() { // from class: com.beizi.fusion.work.splash.i.2
            public void onAdClicked(@NonNull InMobiNative inMobiNative) {
                Log.d("BeiZis", "showInSplash onAdClicked:");
                if (((com.beizi.fusion.work.a) i.this).f5540d != null && ((com.beizi.fusion.work.a) i.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) i.this).f5540d.d(i.this.g());
                }
                i.this.K();
                i.this.an();
            }

            public void onAdImpressed(@NonNull InMobiNative inMobiNative) {
                Log.d("BeiZis", "showInSplash onAdImpressed");
                ((com.beizi.fusion.work.a) i.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                i.this.ag();
                i.this.J();
                i.this.am();
            }

            public void onAdLoadFailed(@NonNull InMobiNative inMobiNative, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                Log.d("BeiZis", "showInSplash onAdFailed:" + inMobiAdRequestStatus.getMessage());
                i.this.b(inMobiAdRequestStatus.getMessage(), inMobiAdRequestStatus.getStatusCode().ordinal());
            }

            public void onAdLoadSucceeded(@NonNull InMobiNative inMobiNative) {
                Log.d("BeiZis", "showInSplash onAdLoadSucceeded:");
                i.this.E();
                ((com.beizi.fusion.work.a) i.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                ac.a("BeiZisBid", "showInSplash inMobiNative.getAdBid() = " + inMobiNative.getAdBid() + ",mBuyerBean = " + ((com.beizi.fusion.work.a) i.this).f5541e);
                if (i.this.ac()) {
                    i.this.b();
                } else {
                    i.this.S();
                }
            }

            public void onUserWillLeaveApplication(InMobiNative inMobiNative) {
                Log.d("BeiZis", "showInSplash onUserWillLeaveApplication");
            }
        };
        InMobiNative inMobiNative = new InMobiNative(this.f6203q, this.V.longValue(), this.Z);
        this.U = inMobiNative;
        inMobiNative.setDownloaderEnabled(true);
        this.U.load();
        ac.a("BeiZis", "inmobi start load");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " splashWorkers:" + eVar.p().toString());
        ad();
        com.beizi.fusion.d.h hVar = this.f5543g;
        if (hVar == com.beizi.fusion.d.h.SUCCESS) {
            ai();
            return;
        }
        if (hVar == com.beizi.fusion.d.h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }
}
