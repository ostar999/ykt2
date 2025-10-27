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
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.beizi.fusion.d.o;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ai;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.m;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.widget.CircleProgressView;
import com.beizi.fusion.widget.SkipView;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsSplashScreenAd;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public class k extends com.beizi.fusion.work.a {
    private long E;
    private boolean F;
    private CircleProgressView G;
    private AdSpacesBean.PositionBean H;
    private AdSpacesBean.PositionBean I;
    private long J;
    private float K;
    private float L;
    private AdSpacesBean.RenderViewBean M;
    private int N;
    private int O;
    private String P;
    private String Q;
    private String R;
    private ViewGroup T;

    /* renamed from: n, reason: collision with root package name */
    long f6241n;

    /* renamed from: o, reason: collision with root package name */
    private Context f6242o;

    /* renamed from: p, reason: collision with root package name */
    private String f6243p;

    /* renamed from: q, reason: collision with root package name */
    private long f6244q;

    /* renamed from: r, reason: collision with root package name */
    private ViewGroup f6245r;

    /* renamed from: s, reason: collision with root package name */
    private KsSplashScreenAd f6246s;

    /* renamed from: t, reason: collision with root package name */
    private CountDownTimer f6247t;

    /* renamed from: u, reason: collision with root package name */
    private View f6248u;

    /* renamed from: v, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6249v;

    /* renamed from: w, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6250w = new ArrayList();

    /* renamed from: x, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6251x = new ArrayList();

    /* renamed from: y, reason: collision with root package name */
    private boolean f6252y = false;

    /* renamed from: z, reason: collision with root package name */
    private boolean f6253z = false;
    private boolean A = false;
    private boolean B = false;
    private boolean C = false;
    private long D = 5000;
    private View S = null;

    public k(Context context, String str, long j2, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.d.e eVar) {
        this.f6242o = context;
        this.f6243p = str;
        this.f6244q = j2;
        this.f6245r = viewGroup;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.T = new SplashContainer(context);
        this.f6249v = list;
        x();
    }

    private void aL() {
        if (this.f6245r == null) {
            aD();
            return;
        }
        try {
            this.f6248u = a(this.f6246s);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.f6245r.removeAllViews();
        if (this.f6248u == null || this.T == null) {
            aD();
            return;
        }
        aM();
        this.T.removeAllViews();
        this.f6248u.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.T.addView(this.f6248u);
        aS();
        this.f6245r.removeAllViews();
        this.f6245r.addView(this.T);
    }

    private void aM() {
        String str;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.k.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (k.this.F && k.this.G != null) {
                    m.a(k.this.G);
                    return;
                }
                if (k.this.f6247t != null) {
                    k.this.f6247t.cancel();
                }
                k.this.ah();
            }
        };
        if (this.F) {
            View view = this.S;
            if (view != null) {
                view.setVisibility(8);
                view.setAlpha(0.0f);
            }
            SkipView skipView = new SkipView(this.f6242o);
            this.S = skipView;
            skipView.setOnClickListener(onClickListener);
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.k.6
                @Override // java.lang.Runnable
                public void run() {
                    k.this.aP();
                }
            }, this.J);
            str = "beizi";
        } else {
            View view2 = this.S;
            if (view2 != null) {
                view2.setOnClickListener(onClickListener);
                aN();
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

    private void aN() {
        CountDownTimer countDownTimer = this.f6247t;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = new CountDownTimer(100 + this.D, 50L) { // from class: com.beizi.fusion.work.splash.k.7
            @Override // android.os.CountDownTimer
            public void onFinish() {
                k.this.ah();
                k.this.M();
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                if (((com.beizi.fusion.work.a) k.this).f5540d == null || ((com.beizi.fusion.work.a) k.this).f5540d.q() == 2) {
                    return;
                }
                ((com.beizi.fusion.work.a) k.this).f5540d.a(j2);
            }
        };
        this.f6247t = countDownTimer2;
        countDownTimer2.start();
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
        for (int i2 = 0; i2 < this.f6249v.size(); i2++) {
            AdSpacesBean.RenderViewBean renderViewBean = this.f6249v.get(i2);
            String type = renderViewBean.getType();
            if ("SKIPVIEW".equals(type)) {
                this.f6251x.add(renderViewBean);
            } else if ("MATERIALVIEW".equals(type)) {
                this.f6250w.add(renderViewBean);
            }
        }
        this.E = 0L;
        if (this.f6251x.size() > 0) {
            AdSpacesBean.RenderViewBean renderViewBean2 = this.f6251x.get(0);
            this.M = renderViewBean2;
            if (renderViewBean2 != null) {
                this.I = renderViewBean2.getTapPosition();
                this.H = this.M.getLayerPosition();
                long delayDisplaySkipButton = this.M.getDelayDisplaySkipButton();
                this.J = delayDisplaySkipButton;
                if (delayDisplaySkipButton < 0) {
                    this.J = 0L;
                }
                long skipViewTotalTime = this.M.getSkipViewTotalTime();
                if (skipViewTotalTime > 0) {
                    this.D = skipViewTotalTime;
                }
                long skipUnavailableTime = this.M.getSkipUnavailableTime();
                if (skipUnavailableTime > 0) {
                    this.E = skipUnavailableTime;
                }
                this.N = this.M.getShowCountDown();
                this.O = this.M.getShowBorder();
                String skipText = this.M.getSkipText();
                this.P = skipText;
                if (TextUtils.isEmpty(skipText)) {
                    this.P = "跳过";
                }
                String textColor = this.M.getTextColor();
                this.Q = textColor;
                if (TextUtils.isEmpty(textColor)) {
                    this.Q = "#FFFFFF";
                }
                String countDownColor = this.M.getCountDownColor();
                this.R = countDownColor;
                if (TextUtils.isEmpty(countDownColor)) {
                    this.R = "#FFFFFF";
                }
                List<AdSpacesBean.PassPolicyBean> passPolicy = this.M.getPassPolicy();
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
                                this.f6253z = ai.a(passPercent);
                                break;
                            case 1:
                                this.f6252y = ai.a(passPercent);
                                break;
                            case 2:
                                AdSpacesBean.PositionBean positionBean = this.H;
                                if (positionBean != null && this.I != null) {
                                    double centerX = positionBean.getCenterX();
                                    double centerY = this.H.getCenterY();
                                    double width = this.H.getWidth();
                                    double height = this.H.getHeight();
                                    double centerX2 = this.I.getCenterX();
                                    double centerY2 = this.I.getCenterY();
                                    double width2 = this.I.getWidth();
                                    double height2 = this.I.getHeight();
                                    if ((centerX > 0.0d && centerX2 > 0.0d && centerX != centerX2) || ((centerY > 0.0d && centerY2 > 0.0d && centerY != centerY2) || ((width > 0.0d && width2 > 0.0d && width != width2) || (height > 0.0d && height2 > 0.0d && height != height2)))) {
                                        this.A = ai.a(passPercent);
                                    }
                                    if (width2 * height2 < width * height) {
                                        this.B = true;
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
        if (this.f6250w.size() > 0) {
            Collections.sort(this.f6250w, new Comparator<AdSpacesBean.RenderViewBean>() { // from class: com.beizi.fusion.work.splash.k.8
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
        if (this.f6252y) {
            U();
        }
        if (this.f6253z) {
            V();
        }
        if (this.A) {
            W();
        }
        if (this.B) {
            X();
        }
        aQ();
        if (this.f6250w.size() > 0) {
            aW();
        }
    }

    private void aQ() {
        CountDownTimer countDownTimer = this.f6247t;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        final long j2 = this.D - this.E;
        CountDownTimer countDownTimer2 = new CountDownTimer(this.D + 100, 50L) { // from class: com.beizi.fusion.work.splash.k.9

            /* renamed from: a, reason: collision with root package name */
            boolean f6263a = false;

            @Override // android.os.CountDownTimer
            public void onFinish() {
                k.this.ah();
                k.this.M();
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j3) {
                if (!this.f6263a) {
                    k.this.aV();
                    this.f6263a = true;
                }
                if (k.this.E > 0 && k.this.E <= k.this.D) {
                    if (k.this.f6252y) {
                        long j4 = j2;
                        if (j4 <= 0 || j3 <= j4) {
                            k.this.C = false;
                            k.this.S.setAlpha(1.0f);
                        } else {
                            k.this.C = true;
                            k.this.S.setAlpha(0.2f);
                        }
                    }
                    if (k.this.E == k.this.D) {
                        k.this.S.setEnabled(false);
                    } else {
                        k.this.S.setEnabled(true);
                    }
                }
                if (k.this.F && k.this.S != null) {
                    k.this.g(Math.round(j3 / 1000.0f));
                }
                if (((com.beizi.fusion.work.a) k.this).f5540d == null || ((com.beizi.fusion.work.a) k.this).f5540d.q() == 2) {
                    return;
                }
                ((com.beizi.fusion.work.a) k.this).f5540d.a(j3);
            }
        };
        this.f6247t = countDownTimer2;
        countDownTimer2.start();
        aU();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aR() {
        CountDownTimer countDownTimer = this.f6247t;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            ac.a("BeiZis", "enter cancel mCountDownTimer");
        }
    }

    private void aS() {
        ViewGroup viewGroup;
        if (!this.F) {
            View view = this.S;
            if (view != null) {
                view.setVisibility(0);
                this.S.setAlpha(1.0f);
                return;
            }
            return;
        }
        if (this.H == null || (viewGroup = this.f6245r) == null) {
            aT();
            return;
        }
        float f2 = this.K;
        float height = viewGroup.getHeight();
        if (height == 0.0f) {
            height = this.L - as.a(this.f6242o, 100.0f);
        }
        int width = (int) (f2 * this.H.getWidth() * 0.01d);
        if (this.H.getHeight() < 12.0d) {
            aT();
            return;
        }
        int height2 = (int) (width * this.H.getHeight() * 0.01d);
        int paddingHeight = (int) (height2 * this.M.getPaddingHeight() * 0.01d);
        if (paddingHeight < 0) {
            paddingHeight = 0;
        }
        ((SkipView) this.S).setData(this.O, paddingHeight);
        g(5);
        this.T.addView(this.S, new FrameLayout.LayoutParams(width, height2));
        float centerX = (f2 * ((float) (this.H.getCenterX() * 0.01d))) - (width / 2);
        float centerY = (height * ((float) (this.H.getCenterY() * 0.01d))) - (height2 / 2);
        this.S.setX(centerX);
        this.S.setY(centerY);
        View view2 = this.S;
        if (view2 != null) {
            view2.setVisibility(0);
        }
    }

    private void aT() {
        int i2 = (int) (this.K * 0.15d);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, (int) (i2 * 0.45d));
        layoutParams.gravity = 53;
        layoutParams.topMargin = as.a(this.f6242o, 20.0f);
        layoutParams.rightMargin = as.a(this.f6242o, 20.0f);
        ViewGroup viewGroup = this.T;
        if (viewGroup != null) {
            viewGroup.addView(this.S, layoutParams);
        }
        View view = this.S;
        if (view != null) {
            this.N = 1;
            this.O = 1;
            ((SkipView) view).setData(1, 0);
            ((SkipView) this.S).setText(String.format("跳过 %d", 5));
            this.S.setVisibility(0);
        }
    }

    private void aU() {
        CircleProgressView circleProgressView = new CircleProgressView(this.f6242o);
        this.G = circleProgressView;
        circleProgressView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.k.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                k.this.N();
                if (k.this.A && k.this.f6248u != null) {
                    k.this.aX();
                    return;
                }
                if (k.this.f6253z && k.this.f6248u != null) {
                    k.this.aX();
                    return;
                }
                if (k.this.f6252y && k.this.f6248u != null && k.this.C) {
                    k.this.aX();
                    return;
                }
                if (k.this.f6247t != null) {
                    k.this.f6247t.cancel();
                }
                k.this.ah();
            }
        });
        this.G.setAlpha(0.0f);
        this.T.addView(this.G, new FrameLayout.LayoutParams(-2, -2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aV() {
        float centerX;
        float centerY;
        this.S.getLocationOnScreen(new int[2]);
        if (this.I != null) {
            float f2 = this.K;
            float height = this.f6245r != null ? r2.getHeight() : 0.0f;
            if (height == 0.0f) {
                height = this.L - as.a(this.f6242o, 100.0f);
            }
            int width = (int) (f2 * this.I.getWidth() * 0.01d);
            int height2 = (int) (width * this.I.getHeight() * 0.01d);
            ViewGroup.LayoutParams layoutParams = this.G.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height2;
            this.G.setLayoutParams(layoutParams);
            centerX = (f2 * ((float) (this.I.getCenterX() * 0.01d))) - (width / 2);
            centerY = (height * ((float) (this.I.getCenterY() * 0.01d))) - (height2 / 2);
        } else {
            float pivotX = (r1[0] + this.S.getPivotX()) - (this.G.getWidth() / 2);
            float pivotY = (r1[1] + this.S.getPivotY()) - (this.G.getHeight() / 2);
            centerX = pivotX;
            centerY = pivotY;
        }
        this.G.setX(centerX);
        this.G.setY(centerY);
    }

    private void aW() {
        float width;
        float fA;
        for (AdSpacesBean.RenderViewBean renderViewBean : this.f6250w) {
            AdSpacesBean.PositionBean layerPosition = renderViewBean.getLayerPosition();
            ImageView imageView = new ImageView(this.f6242o);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setVisibility(0);
            String imageUrl = renderViewBean.getImageUrl();
            if (!TextUtils.isEmpty(imageUrl) && imageUrl.contains("http")) {
                com.beizi.fusion.g.i.a(this.f6242o).a(imageUrl).a(imageView);
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.k.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (k.this.f6248u != null) {
                        k.this.aX();
                    }
                }
            });
            ViewGroup viewGroup = this.f6245r;
            if (viewGroup != null) {
                width = viewGroup.getWidth();
                fA = this.f6245r.getHeight();
            } else {
                width = 0.0f;
                fA = 0.0f;
            }
            if (width == 0.0f) {
                width = this.K;
            }
            if (fA == 0.0f) {
                fA = this.L - as.a(this.f6242o, 100.0f);
            }
            this.T.addView(imageView, new FrameLayout.LayoutParams((int) (width * layerPosition.getWidth() * 0.01d), (int) (fA * layerPosition.getHeight() * 0.01d)));
            float centerX = (float) (layerPosition.getCenterX() * 0.01d);
            float centerY = (fA * ((float) (layerPosition.getCenterY() * 0.01d))) - (r6 / 2);
            imageView.setX((width * centerX) - (r4 / 2));
            imageView.setY(centerY);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aX() {
        if (this.f6248u == null) {
            return;
        }
        float fRandom = (int) ((Math.random() * 10.0d) + 1.0d);
        m.a(this.f6248u, this.f6248u.getPivotX() - fRandom, this.f6248u.getPivotY() - fRandom);
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "KUAISHOU";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(int i2) {
        if (this.N != 1) {
            SpannableString spannableString = new SpannableString(this.P);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(this.Q)), 0, this.P.length(), 33);
            ((SkipView) this.S).setText(spannableString);
            return;
        }
        String strValueOf = String.valueOf(i2);
        String str = this.P + " ";
        String str2 = str + strValueOf;
        SpannableString spannableString2 = new SpannableString(str2);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.Q)), 0, str.length(), 33);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.R)), str2.indexOf(strValueOf), str2.length(), 33);
        ((SkipView) this.S).setText(spannableString2);
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f6241n = System.currentTimeMillis();
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
                if (!as.a("com.kwad.sdk.api.KsAdSDK")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.k.1
                        @Override // java.lang.Runnable
                        public void run() {
                            k.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "Ks sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    o.a(this.f6242o, this.f5544h);
                    this.f5538b.u(KsAdSDK.getSDKVersion());
                    aB();
                    B();
                }
            }
        }
        long sleepTime = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            sleepTime = Math.max(sleepTime, this.f5542f.getHotRequestDelay());
        }
        List<AdSpacesBean.RenderViewBean> list = this.f6249v;
        boolean z2 = list != null && list.size() > 0;
        this.F = z2;
        if (z2) {
            aO();
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
        this.K = as.m(this.f6242o);
        this.L = as.n(this.f6242o);
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
        if (this.f6246s == null) {
            return null;
        }
        return this.f6246s.getECPM() + "";
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        KsScene ksSceneBuild = new KsScene.Builder(Long.parseLong(this.f5545i)).build();
        KsLoadManager loadManager = KsAdSDK.getLoadManager();
        if (loadManager == null) {
            Log.d("BeiZis", "showKsSplash onError:渠道广告请求对象为空");
            b("渠道广告请求异常", R2.drawable.ic_cut_success_pop_bg_night);
        } else {
            if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
                ksSceneBuild.setBidResponse(aJ());
            }
            loadManager.loadSplashScreenAd(ksSceneBuild, new KsLoadManager.SplashScreenAdListener() { // from class: com.beizi.fusion.work.splash.k.3
                public void onError(int i2, String str) {
                    Log.d("BeiZis", "showKsSplash onError:" + str);
                    k.this.b(str, i2);
                }

                public void onRequestResult(int i2) {
                }

                public void onSplashScreenAdLoad(@NonNull KsSplashScreenAd ksSplashScreenAd) {
                    k.this.E();
                    ((com.beizi.fusion.work.a) k.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                    k.this.f6246s = ksSplashScreenAd;
                    if (k.this.f6246s != null) {
                        k.this.a(r3.f6246s.getECPM());
                    }
                    if (k.this.ac()) {
                        k.this.b();
                    } else {
                        k.this.S();
                    }
                }
            });
        }
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

    private View a(KsSplashScreenAd ksSplashScreenAd) {
        if (ksSplashScreenAd == null) {
            return null;
        }
        return ksSplashScreenAd.getView(this.f6242o, new KsSplashScreenAd.SplashScreenAdInteractionListener() { // from class: com.beizi.fusion.work.splash.k.4
            public void onAdClicked() {
                Log.d("BeiZis", "showKsSplash onAdClick()");
                if (((com.beizi.fusion.work.a) k.this).f5540d != null && ((com.beizi.fusion.work.a) k.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) k.this).f5540d.d(k.this.g());
                    ((com.beizi.fusion.work.a) k.this).f5549m.sendEmptyMessageDelayed(2, (((com.beizi.fusion.work.a) k.this).f5548l + 5000) - System.currentTimeMillis());
                }
                k.this.K();
                k.this.an();
            }

            public void onAdShowEnd() {
                Log.d("BeiZis", "showKsSplash onADDismissed()");
                k.this.ah();
                k.this.M();
            }

            public void onAdShowError(int i2, String str) {
                Log.d("BeiZis", "showKsSplash onAdShowError:" + str);
                k.this.b(str, i2);
            }

            public void onAdShowStart() {
                Log.d("BeiZis", "showKsSplash onADPresent()");
                k.this.I();
                Log.d("BeiZis", "showKsSplash onADExposure()");
                ((com.beizi.fusion.work.a) k.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                k.this.ag();
                k.this.J();
                k.this.am();
            }

            public void onDownloadTipsDialogCancel() {
            }

            public void onDownloadTipsDialogDismiss() {
            }

            public void onDownloadTipsDialogShow() {
                k.this.aR();
                ((com.beizi.fusion.work.a) k.this).f5549m.removeMessages(2);
            }

            public void onSkippedAd() {
                Log.d("BeiZis", "showKsSplash onSkippedAd()");
                k.this.ah();
                k.this.N();
            }
        });
    }
}
