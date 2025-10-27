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
import com.beizi.fusion.d.v;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ai;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.m;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.widget.CircleProgressView;
import com.beizi.fusion.widget.SkipView;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public class d extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {
    private long G;
    private boolean H;
    private CircleProgressView I;
    private AdSpacesBean.PositionBean J;
    private AdSpacesBean.PositionBean K;
    private long L;
    private float M;
    private float N;
    private float O;
    private float P;
    private float Q;
    private float R;
    private AdSpacesBean.RenderViewBean S;
    private int T;
    private int U;
    private String V;
    private String W;
    private String X;
    private long Y;

    /* renamed from: n, reason: collision with root package name */
    private Context f6098n;

    /* renamed from: o, reason: collision with root package name */
    private String f6099o;

    /* renamed from: p, reason: collision with root package name */
    private long f6100p;

    /* renamed from: q, reason: collision with root package name */
    private View f6101q;

    /* renamed from: r, reason: collision with root package name */
    private ViewGroup f6102r;

    /* renamed from: s, reason: collision with root package name */
    private ViewGroup f6103s;

    /* renamed from: t, reason: collision with root package name */
    private TTAdNative f6104t;

    /* renamed from: u, reason: collision with root package name */
    private TTNativeExpressAd f6105u;

    /* renamed from: v, reason: collision with root package name */
    private CountDownTimer f6106v;

    /* renamed from: w, reason: collision with root package name */
    private View f6107w;

    /* renamed from: x, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6108x;

    /* renamed from: y, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6109y = new ArrayList();

    /* renamed from: z, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6110z = new ArrayList();
    private boolean A = false;
    private boolean B = false;
    private boolean C = false;
    private boolean D = false;
    private boolean E = false;
    private long F = 5000;

    public d(Context context, String str, long j2, View view, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.d.e eVar) {
        this.f6098n = context;
        this.f6099o = str;
        this.f6100p = j2;
        this.f6101q = view;
        this.f6102r = viewGroup;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f6103s = new SplashContainer(context);
        this.f6108x = list;
        x();
    }

    private void aL() {
        CountDownTimer countDownTimer = this.f6106v;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = new CountDownTimer(100 + this.F, 50L) { // from class: com.beizi.fusion.work.splash.d.6
            @Override // android.os.CountDownTimer
            public void onFinish() {
                if (((com.beizi.fusion.work.a) d.this).f5540d == null) {
                    return;
                }
                ((com.beizi.fusion.work.a) d.this).f5540d.c(d.this.g());
                d.this.M();
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                if (d.this.H && d.this.f6101q != null) {
                    d.this.g(Math.round(j2 / 1000.0f));
                }
                if (((com.beizi.fusion.work.a) d.this).f5540d == null || ((com.beizi.fusion.work.a) d.this).f5540d.q() == 2) {
                    return;
                }
                ((com.beizi.fusion.work.a) d.this).f5540d.a(j2);
            }
        };
        this.f6106v = countDownTimer2;
        countDownTimer2.start();
    }

    private void aM() {
        ViewGroup viewGroup = this.f6103s;
        if (viewGroup == null || this.f6102r == null || this.f6107w == null) {
            aD();
            return;
        }
        viewGroup.removeAllViews();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) this.Q, (int) this.R);
        if (this.R < 1200.0f) {
            layoutParams.gravity = 16;
        }
        this.f6103s.addView(this.f6107w, layoutParams);
        final ViewGroup viewGroup2 = this.f6102r;
        if (viewGroup2 instanceof ViewGroup) {
            viewGroup2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.beizi.fusion.work.splash.d.7
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    if (viewGroup2 == null) {
                        return;
                    }
                    float height = d.this.f6102r.getHeight();
                    if (d.this.R > height) {
                        float f2 = height / d.this.R;
                        d.this.f6107w.setPivotY(0.0f);
                        d.this.f6107w.setScaleY(f2);
                    }
                    viewGroup2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
        aN();
        aR();
        this.f6102r.removeAllViews();
        this.f6102r.addView(this.f6103s);
    }

    private void aN() {
        String str;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.d.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (d.this.H && d.this.I != null) {
                    m.a(d.this.I);
                    return;
                }
                if (d.this.f6106v != null) {
                    d.this.f6106v.cancel();
                }
                if (((com.beizi.fusion.work.a) d.this).f5540d != null) {
                    ((com.beizi.fusion.work.a) d.this).f5540d.c(d.this.g());
                }
            }
        };
        if (this.H) {
            View view = this.f6101q;
            if (view != null) {
                view.setVisibility(8);
                view.setAlpha(0.0f);
            }
            SkipView skipView = new SkipView(this.f6098n);
            this.f6101q = skipView;
            skipView.setOnClickListener(onClickListener);
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.d.9
                @Override // java.lang.Runnable
                public void run() {
                    d.this.aP();
                }
            }, this.L);
            str = "beizi";
        } else {
            View view2 = this.f6101q;
            if (view2 != null) {
                view2.setOnClickListener(onClickListener);
                aL();
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
        for (int i2 = 0; i2 < this.f6108x.size(); i2++) {
            AdSpacesBean.RenderViewBean renderViewBean = this.f6108x.get(i2);
            String type = renderViewBean.getType();
            if ("SKIPVIEW".equals(type)) {
                this.f6110z.add(renderViewBean);
            } else if ("MATERIALVIEW".equals(type)) {
                this.f6109y.add(renderViewBean);
            }
        }
        this.G = 0L;
        if (this.f6110z.size() > 0) {
            AdSpacesBean.RenderViewBean renderViewBean2 = this.f6110z.get(0);
            this.S = renderViewBean2;
            if (renderViewBean2 != null) {
                this.K = renderViewBean2.getTapPosition();
                this.J = this.S.getLayerPosition();
                long delayDisplaySkipButton = this.S.getDelayDisplaySkipButton();
                this.L = delayDisplaySkipButton;
                if (delayDisplaySkipButton < 0) {
                    this.L = 0L;
                }
                long skipViewTotalTime = this.S.getSkipViewTotalTime();
                if (skipViewTotalTime > 0) {
                    this.F = skipViewTotalTime;
                }
                long skipUnavailableTime = this.S.getSkipUnavailableTime();
                if (skipUnavailableTime > 0) {
                    this.G = skipUnavailableTime;
                }
                this.T = this.S.getShowCountDown();
                this.U = this.S.getShowBorder();
                String skipText = this.S.getSkipText();
                this.V = skipText;
                if (TextUtils.isEmpty(skipText)) {
                    this.V = "跳过";
                }
                String textColor = this.S.getTextColor();
                this.W = textColor;
                if (TextUtils.isEmpty(textColor)) {
                    this.W = "#FFFFFF";
                }
                String countDownColor = this.S.getCountDownColor();
                this.X = countDownColor;
                if (TextUtils.isEmpty(countDownColor)) {
                    this.X = "#FFFFFF";
                }
                List<AdSpacesBean.PassPolicyBean> passPolicy = this.S.getPassPolicy();
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
                                this.B = ai.a(passPercent);
                                break;
                            case 1:
                                this.A = ai.a(passPercent);
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
                                        this.C = ai.a(passPercent);
                                    }
                                    if (width2 * height2 < width * height) {
                                        this.D = true;
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
        if (this.f6109y.size() > 0) {
            Collections.sort(this.f6109y, new Comparator<AdSpacesBean.RenderViewBean>() { // from class: com.beizi.fusion.work.splash.d.10
                @Override // java.util.Comparator
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public int compare(AdSpacesBean.RenderViewBean renderViewBean3, AdSpacesBean.RenderViewBean renderViewBean4) {
                    return renderViewBean4.getLevel() - renderViewBean3.getLevel();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aP() {
        if (this.A) {
            U();
        }
        if (this.B) {
            V();
        }
        if (this.C) {
            W();
        }
        if (this.D) {
            X();
        }
        aQ();
        if (this.f6109y.size() > 0) {
            aV();
        }
    }

    private void aQ() {
        CountDownTimer countDownTimer = this.f6106v;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        final long j2 = this.F - this.G;
        CountDownTimer countDownTimer2 = new CountDownTimer(this.F + 100, 50L) { // from class: com.beizi.fusion.work.splash.d.11

            /* renamed from: a, reason: collision with root package name */
            boolean f6113a = false;

            @Override // android.os.CountDownTimer
            public void onFinish() {
                if (((com.beizi.fusion.work.a) d.this).f5540d == null) {
                    return;
                }
                ((com.beizi.fusion.work.a) d.this).f5540d.c(d.this.g());
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j3) {
                if (!this.f6113a) {
                    d.this.aU();
                    this.f6113a = true;
                }
                if (d.this.G > 0 && d.this.G <= d.this.F) {
                    if (d.this.A) {
                        long j4 = j2;
                        if (j4 <= 0 || j3 <= j4) {
                            d.this.E = false;
                            d.this.f6101q.setAlpha(1.0f);
                        } else {
                            d.this.E = true;
                            d.this.f6101q.setAlpha(0.2f);
                        }
                    }
                    if (d.this.G == d.this.F) {
                        d.this.f6101q.setEnabled(false);
                    } else {
                        d.this.f6101q.setEnabled(true);
                    }
                }
                if (d.this.H && d.this.f6101q != null) {
                    ((SkipView) d.this.f6101q).setText(String.format("跳过 %d", Integer.valueOf(Math.round(j3 / 1000.0f))));
                }
                if (((com.beizi.fusion.work.a) d.this).f5540d == null || ((com.beizi.fusion.work.a) d.this).f5540d.q() == 2) {
                    return;
                }
                ((com.beizi.fusion.work.a) d.this).f5540d.a(j3);
            }
        };
        this.f6106v = countDownTimer2;
        countDownTimer2.start();
        aT();
    }

    private void aR() {
        ViewGroup viewGroup;
        if (!this.H) {
            View view = this.f6101q;
            if (view != null) {
                view.setVisibility(0);
                this.f6101q.setAlpha(1.0f);
                return;
            }
            return;
        }
        if (this.J == null || (viewGroup = this.f6102r) == null) {
            aS();
            return;
        }
        float f2 = this.M;
        float height = viewGroup.getHeight();
        if (height == 0.0f) {
            height = this.N - as.a(this.f6098n, 100.0f);
        }
        int width = (int) (f2 * this.J.getWidth() * 0.01d);
        int height2 = (int) (width * this.J.getHeight() * 0.01d);
        int paddingHeight = (int) (height2 * this.S.getPaddingHeight() * 0.01d);
        if (paddingHeight < 0) {
            paddingHeight = 0;
        }
        ((SkipView) this.f6101q).setData(this.U, paddingHeight);
        g(5);
        this.f6103s.addView(this.f6101q, new FrameLayout.LayoutParams(width, height2));
        float centerX = (f2 * ((float) (this.J.getCenterX() * 0.01d))) - (width / 2);
        float centerY = (height * ((float) (this.J.getCenterY() * 0.01d))) - (height2 / 2);
        this.f6101q.setX(centerX);
        this.f6101q.setY(centerY);
        View view2 = this.f6101q;
        if (view2 != null) {
            view2.setVisibility(0);
        }
    }

    private void aS() {
        int i2 = (int) (this.M * 0.15d);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, (int) (i2 * 0.45d));
        layoutParams.gravity = 53;
        layoutParams.topMargin = as.a(this.f6098n, 20.0f);
        layoutParams.rightMargin = as.a(this.f6098n, 20.0f);
        ViewGroup viewGroup = this.f6103s;
        if (viewGroup != null) {
            viewGroup.addView(this.f6101q, layoutParams);
        }
        View view = this.f6101q;
        if (view != null) {
            this.T = 1;
            this.U = 1;
            ((SkipView) view).setData(1, 0);
            ((SkipView) this.f6101q).setText(String.format("跳过 %d", 5));
            this.f6101q.setVisibility(0);
        }
    }

    private void aT() {
        CircleProgressView circleProgressView = new CircleProgressView(this.f6098n);
        this.I = circleProgressView;
        circleProgressView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.d.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                d.this.N();
                if (d.this.C && d.this.f6107w != null) {
                    d.this.aW();
                    return;
                }
                if (d.this.B && d.this.f6107w != null) {
                    d.this.aW();
                    return;
                }
                if (d.this.A && d.this.f6107w != null && d.this.E) {
                    d.this.aW();
                    return;
                }
                if (d.this.f6106v != null) {
                    d.this.f6106v.cancel();
                }
                if (((com.beizi.fusion.work.a) d.this).f5540d != null) {
                    ((com.beizi.fusion.work.a) d.this).f5540d.c(d.this.g());
                }
            }
        });
        this.I.setAlpha(0.0f);
        this.f6103s.addView(this.I, new FrameLayout.LayoutParams(-2, -2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aU() {
        float centerX;
        float centerY;
        this.f6101q.getLocationOnScreen(new int[2]);
        if (this.K != null) {
            float f2 = this.M;
            float height = this.f6102r != null ? r2.getHeight() : 0.0f;
            if (height == 0.0f) {
                height = this.N - as.a(this.f6098n, 100.0f);
            }
            int width = (int) (f2 * this.K.getWidth() * 0.01d);
            int height2 = (int) (width * this.K.getHeight() * 0.01d);
            ViewGroup.LayoutParams layoutParams = this.I.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height2;
            this.I.setLayoutParams(layoutParams);
            centerX = (f2 * ((float) (this.K.getCenterX() * 0.01d))) - (width / 2);
            centerY = (height * ((float) (this.K.getCenterY() * 0.01d))) - (height2 / 2);
        } else {
            float pivotX = (r1[0] + this.f6101q.getPivotX()) - (this.I.getWidth() / 2);
            float pivotY = (r1[1] + this.f6101q.getPivotY()) - (this.I.getHeight() / 2);
            centerX = pivotX;
            centerY = pivotY;
        }
        this.I.setX(centerX);
        this.I.setY(centerY);
    }

    private void aV() {
        float width;
        float fA;
        for (AdSpacesBean.RenderViewBean renderViewBean : this.f6109y) {
            AdSpacesBean.PositionBean layerPosition = renderViewBean.getLayerPosition();
            ImageView imageView = new ImageView(this.f6098n);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setVisibility(0);
            String imageUrl = renderViewBean.getImageUrl();
            if (!TextUtils.isEmpty(imageUrl) && imageUrl.contains("http")) {
                com.beizi.fusion.g.i.a(this.f6098n).a(imageUrl).a(imageView);
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.d.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (d.this.f6107w != null) {
                        d.this.aW();
                    }
                }
            });
            ViewGroup viewGroup = this.f6102r;
            if (viewGroup != null) {
                width = viewGroup.getWidth();
                fA = this.f6102r.getHeight();
            } else {
                width = 0.0f;
                fA = 0.0f;
            }
            if (width == 0.0f) {
                width = this.M;
            }
            if (fA == 0.0f) {
                fA = this.N - as.a(this.f6098n, 100.0f);
            }
            this.f6102r.addView(imageView, new FrameLayout.LayoutParams((int) (width * layerPosition.getWidth() * 0.01d), (int) (fA * layerPosition.getHeight() * 0.01d)));
            float centerX = (float) (layerPosition.getCenterX() * 0.01d);
            float centerY = (fA * ((float) (layerPosition.getCenterY() * 0.01d))) - (r6 / 2);
            imageView.setX((width * centerX) - (r4 / 2));
            imageView.setY(centerY);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aW() {
        float fRandom = (int) ((Math.random() * 10.0d) + 1.0d);
        m.a(this.f6107w, this.f6107w.getPivotX() - fRandom, this.f6107w.getPivotY() - fRandom);
    }

    @Override // com.beizi.fusion.work.a
    public void aF() {
        B();
        e();
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "CSJ_NST";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(int i2) {
        if (this.T != 1) {
            SpannableString spannableString = new SpannableString(this.V);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(this.W)), 0, this.V.length(), 33);
            ((SkipView) this.f6101q).setText(spannableString);
            return;
        }
        String strValueOf = String.valueOf(i2);
        String str = this.V + " ";
        String str2 = str + strValueOf;
        SpannableString spannableString2 = new SpannableString(str2);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.W)), 0, str.length(), 33);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.X)), str2.indexOf(strValueOf), str2.length(), 33);
        ((SkipView) this.f6101q).setText(spannableString2);
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        ac.b("BeiZis", "AdWorker chanel = " + this.f5539c);
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.bytedance.sdk.openadsdk.TTAdNative")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.d.1
                        @Override // java.lang.Runnable
                        public void run() {
                            d.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "CSJ sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    v.a(this, this.f6098n, this.f5544h, this.f5541e.getDirectDownload());
                    this.f5538b.t(TTAdSdk.getAdManager().getSDKVersion());
                    aB();
                }
            }
        }
        this.Y = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            this.Y = Math.max(this.Y, this.f5542f.getHotRequestDelay());
        }
        List<AdSpacesBean.RenderViewBean> list = this.f6108x;
        boolean z2 = list != null && list.size() > 0;
        this.H = z2;
        if (z2) {
            aO();
        }
        this.M = as.m(this.f6098n);
        this.N = as.n(this.f6098n);
    }

    @Override // com.beizi.fusion.work.a
    public void e() {
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.Y);
        long j2 = this.Y;
        if (j2 > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, j2);
            return;
        }
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null || eVar.r() >= 1 || this.f5540d.q() == 2) {
            return;
        }
        p();
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
        Log.d("BeiZis", g() + " out make show ad");
        aM();
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        this.f6107w = null;
        if (aC()) {
            return;
        }
        this.f6104t = v.a().createAdNative(this.f6098n);
        this.O = as.k(this.f6098n);
        this.P = 0.0f;
        this.f6104t.loadNativeExpressAd(new AdSlot.Builder().setCodeId(this.f5545i).setSupportDeepLink(true).setAdCount(1).setExpressViewAcceptedSize(this.O, this.P).build(), new TTAdNative.NativeExpressAdListener() { // from class: com.beizi.fusion.work.splash.d.4
            public void onError(int i2, String str) {
                Log.d("BeiZis", "showCsjNSTSplash Callback --> onError:" + str);
                d.this.b(str, i2);
            }

            public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
                Log.d("BeiZis", "showCsjNSTSplash Callback --> onNativeExpressAdLoad()");
                ((com.beizi.fusion.work.a) d.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                d.this.E();
                if (list == null || list.size() == 0) {
                    d.this.e(-991);
                    return;
                }
                d.this.f6105u = list.get(0);
                d dVar = d.this;
                dVar.a(dVar.f6105u);
                d.this.f6105u.render();
            }
        });
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        TTNativeExpressAd tTNativeExpressAd = this.f6105u;
        if (tTNativeExpressAd != null) {
            tTNativeExpressAd.destroy();
        }
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f6107w;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", "splashWorkers:" + eVar.p().toString());
        ad();
        com.beizi.fusion.d.h hVar = this.f5543g;
        if (hVar == com.beizi.fusion.d.h.SUCCESS) {
            this.f5540d.a(g(), (View) null);
            return;
        }
        if (hVar == com.beizi.fusion.d.h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(TTNativeExpressAd tTNativeExpressAd) {
        tTNativeExpressAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() { // from class: com.beizi.fusion.work.splash.d.5

            /* renamed from: a, reason: collision with root package name */
            boolean f6119a = false;

            /* renamed from: b, reason: collision with root package name */
            boolean f6120b = false;

            public void onAdClicked(View view, int i2) {
                Log.d("BeiZis", "showCsjNSTSplash Callback --> onAdClicked()");
                if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) d.this).f5540d.d(d.this.g());
                }
                if (this.f6120b) {
                    return;
                }
                this.f6120b = true;
                d.this.K();
                d.this.an();
            }

            public void onAdShow(View view, int i2) {
                Log.d("BeiZis", "showCsjNSTSplash Callback --> onAdShow()");
                ((com.beizi.fusion.work.a) d.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) d.this).f5540d.b(d.this.g());
                }
                if (this.f6119a) {
                    return;
                }
                this.f6119a = true;
                d.this.I();
                d.this.J();
                d.this.am();
            }

            public void onRenderFail(View view, String str, int i2) {
                Log.d("BeiZis", "showCsjNSTSplash Callback --> onRenderFail()");
                d.this.b(str, i2);
            }

            public void onRenderSuccess(View view, float f2, float f3) {
                Log.d("BeiZis", "showCsjNSTSplash Callback --> onRenderSuccess() width == " + f2 + ", height == " + f3);
                d dVar = d.this;
                dVar.Q = (float) as.a(dVar.f6098n, f2);
                d dVar2 = d.this;
                dVar2.R = (float) as.a(dVar2.f6098n, f3);
                d.this.f6107w = view;
                if (d.this.ac()) {
                    d.this.b();
                } else {
                    d.this.S();
                }
            }
        });
    }
}
