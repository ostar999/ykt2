package com.beizi.fusion.work.splash;

import android.app.Activity;
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
import com.beizi.fusion.d.v;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ai;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.m;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.widget.CircleProgressView;
import com.beizi.fusion.widget.SkipView;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.CSJAdError;
import com.bytedance.sdk.openadsdk.CSJSplashAd;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public class e extends com.beizi.fusion.work.a {
    private long F;
    private boolean G;
    private CircleProgressView H;
    private AdSpacesBean.PositionBean I;
    private AdSpacesBean.PositionBean J;
    private long K;
    private float L;
    private float M;
    private AdSpacesBean.RenderViewBean N;
    private int O;
    private int P;
    private String Q;
    private String R;
    private String S;
    private CSJSplashAd T;
    private long U;
    private int V;
    private int W;

    /* renamed from: n, reason: collision with root package name */
    private Context f6127n;

    /* renamed from: o, reason: collision with root package name */
    private String f6128o;

    /* renamed from: p, reason: collision with root package name */
    private long f6129p;

    /* renamed from: q, reason: collision with root package name */
    private View f6130q;

    /* renamed from: r, reason: collision with root package name */
    private ViewGroup f6131r;

    /* renamed from: s, reason: collision with root package name */
    private ViewGroup f6132s;

    /* renamed from: t, reason: collision with root package name */
    private TTAdNative f6133t;

    /* renamed from: u, reason: collision with root package name */
    private CountDownTimer f6134u;

    /* renamed from: v, reason: collision with root package name */
    private View f6135v;

    /* renamed from: w, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6136w;

    /* renamed from: x, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6137x = new ArrayList();

    /* renamed from: y, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6138y = new ArrayList();

    /* renamed from: z, reason: collision with root package name */
    private boolean f6139z = false;
    private boolean A = false;
    private boolean B = false;
    private boolean C = false;
    private boolean D = false;
    private long E = 5000;

    public e(Context context, String str, long j2, View view, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, List<AdSpacesBean.RenderViewBean> list, int i2, int i3, com.beizi.fusion.d.e eVar) {
        this.f6127n = context;
        this.f6128o = str;
        this.f6129p = j2;
        this.f6130q = view;
        this.f6131r = viewGroup;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f6132s = new SplashContainer(context);
        this.f6136w = list;
        this.V = i2;
        this.W = i3;
        x();
    }

    private void aL() {
        CountDownTimer countDownTimer = this.f6134u;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = new CountDownTimer(100 + this.E, 50L) { // from class: com.beizi.fusion.work.splash.e.6
            @Override // android.os.CountDownTimer
            public void onFinish() {
                e.this.ah();
                e.this.M();
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                if (((com.beizi.fusion.work.a) e.this).f5540d == null || ((com.beizi.fusion.work.a) e.this).f5540d.q() == 2) {
                    return;
                }
                ((com.beizi.fusion.work.a) e.this).f5540d.a(j2);
            }
        };
        this.f6134u = countDownTimer2;
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
    private void aM() {
        boolean z2 = false;
        for (int i2 = 0; i2 < this.f6136w.size(); i2++) {
            AdSpacesBean.RenderViewBean renderViewBean = this.f6136w.get(i2);
            String type = renderViewBean.getType();
            if ("SKIPVIEW".equals(type)) {
                this.f6138y.add(renderViewBean);
            } else if ("MATERIALVIEW".equals(type)) {
                this.f6137x.add(renderViewBean);
            }
        }
        this.F = 0L;
        if (this.f6138y.size() > 0) {
            AdSpacesBean.RenderViewBean renderViewBean2 = this.f6138y.get(0);
            this.N = renderViewBean2;
            if (renderViewBean2 != null) {
                this.J = renderViewBean2.getTapPosition();
                this.I = this.N.getLayerPosition();
                long delayDisplaySkipButton = this.N.getDelayDisplaySkipButton();
                this.K = delayDisplaySkipButton;
                if (delayDisplaySkipButton < 0) {
                    this.K = 0L;
                }
                long skipViewTotalTime = this.N.getSkipViewTotalTime();
                if (skipViewTotalTime > 0) {
                    this.E = skipViewTotalTime;
                }
                long skipUnavailableTime = this.N.getSkipUnavailableTime();
                if (skipUnavailableTime > 0) {
                    this.F = skipUnavailableTime;
                }
                this.O = this.N.getShowCountDown();
                this.P = this.N.getShowBorder();
                String skipText = this.N.getSkipText();
                this.Q = skipText;
                if (TextUtils.isEmpty(skipText)) {
                    this.Q = "跳过";
                }
                String textColor = this.N.getTextColor();
                this.R = textColor;
                if (TextUtils.isEmpty(textColor)) {
                    this.R = "#FFFFFF";
                }
                String countDownColor = this.N.getCountDownColor();
                this.S = countDownColor;
                if (TextUtils.isEmpty(countDownColor)) {
                    this.S = "#FFFFFF";
                }
                List<AdSpacesBean.PassPolicyBean> passPolicy = this.N.getPassPolicy();
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
                                this.f6139z = ai.a(passPercent);
                                break;
                            case 2:
                                AdSpacesBean.PositionBean positionBean = this.I;
                                if (positionBean != null && this.J != null) {
                                    double centerX = positionBean.getCenterX();
                                    double centerY = this.I.getCenterY();
                                    double width = this.I.getWidth();
                                    double height = this.I.getHeight();
                                    double centerX2 = this.J.getCenterX();
                                    double centerY2 = this.J.getCenterY();
                                    double width2 = this.J.getWidth();
                                    double height2 = this.J.getHeight();
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
        if (this.f6137x.size() > 0) {
            Collections.sort(this.f6137x, new Comparator<AdSpacesBean.RenderViewBean>() { // from class: com.beizi.fusion.work.splash.e.7
                @Override // java.util.Comparator
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public int compare(AdSpacesBean.RenderViewBean renderViewBean3, AdSpacesBean.RenderViewBean renderViewBean4) {
                    return renderViewBean4.getLevel() - renderViewBean3.getLevel();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aN() {
        if (this.f6139z) {
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
        aO();
        if (this.f6137x.size() > 0) {
            aT();
        }
    }

    private void aO() {
        CountDownTimer countDownTimer = this.f6134u;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        final long j2 = this.E - this.F;
        CountDownTimer countDownTimer2 = new CountDownTimer(this.E + 100, 50L) { // from class: com.beizi.fusion.work.splash.e.8

            /* renamed from: a, reason: collision with root package name */
            boolean f6149a = false;

            @Override // android.os.CountDownTimer
            public void onFinish() {
                e.this.ah();
                e.this.M();
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j3) {
                if (!this.f6149a) {
                    e.this.aS();
                    this.f6149a = true;
                }
                if (e.this.F > 0 && e.this.F <= e.this.E) {
                    if (e.this.f6139z) {
                        long j4 = j2;
                        if (j4 <= 0 || j3 <= j4) {
                            e.this.D = false;
                            e.this.f6130q.setAlpha(1.0f);
                        } else {
                            e.this.D = true;
                            e.this.f6130q.setAlpha(0.2f);
                        }
                    }
                    if (e.this.F == e.this.E) {
                        e.this.f6130q.setEnabled(false);
                    } else {
                        e.this.f6130q.setEnabled(true);
                    }
                }
                if (e.this.G && e.this.f6130q != null) {
                    e.this.g(Math.round(j3 / 1000.0f));
                }
                if (((com.beizi.fusion.work.a) e.this).f5540d == null || ((com.beizi.fusion.work.a) e.this).f5540d.q() == 2) {
                    return;
                }
                ((com.beizi.fusion.work.a) e.this).f5540d.a(j3);
            }
        };
        this.f6134u = countDownTimer2;
        countDownTimer2.start();
        aR();
    }

    private void aP() {
        ViewGroup viewGroup;
        if (!this.G) {
            View view = this.f6130q;
            if (view != null) {
                view.setVisibility(0);
                this.f6130q.setAlpha(1.0f);
                return;
            }
            return;
        }
        if (this.I == null || (viewGroup = this.f6131r) == null) {
            aQ();
            return;
        }
        float f2 = this.L;
        float height = viewGroup.getHeight();
        if (height == 0.0f) {
            height = this.M - as.a(this.f6127n, 100.0f);
        }
        int width = (int) (f2 * this.I.getWidth() * 0.01d);
        if (this.I.getHeight() < 12.0d) {
            aQ();
            return;
        }
        int height2 = (int) (width * this.I.getHeight() * 0.01d);
        int paddingHeight = (int) (height2 * this.N.getPaddingHeight() * 0.01d);
        if (paddingHeight < 0) {
            paddingHeight = 0;
        }
        ((SkipView) this.f6130q).setData(this.P, paddingHeight);
        g(5);
        this.f6132s.addView(this.f6130q, new FrameLayout.LayoutParams(width, height2));
        float centerX = (f2 * ((float) (this.I.getCenterX() * 0.01d))) - (width / 2);
        float centerY = (height * ((float) (this.I.getCenterY() * 0.01d))) - (height2 / 2);
        this.f6130q.setX(centerX);
        this.f6130q.setY(centerY);
        View view2 = this.f6130q;
        if (view2 != null) {
            view2.setVisibility(0);
        }
    }

    private void aQ() {
        int i2 = (int) (this.L * 0.15d);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, (int) (i2 * 0.45d));
        layoutParams.gravity = 53;
        layoutParams.topMargin = as.a(this.f6127n, 20.0f);
        layoutParams.rightMargin = as.a(this.f6127n, 20.0f);
        ViewGroup viewGroup = this.f6132s;
        if (viewGroup != null) {
            viewGroup.addView(this.f6130q, layoutParams);
        }
        View view = this.f6130q;
        if (view != null) {
            this.O = 1;
            this.P = 1;
            ((SkipView) view).setData(1, 0);
            ((SkipView) this.f6130q).setText(String.format("跳过 %d", 5));
            this.f6130q.setVisibility(0);
        }
    }

    private void aR() {
        CircleProgressView circleProgressView = new CircleProgressView(this.f6127n);
        this.H = circleProgressView;
        circleProgressView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.e.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                e.this.N();
                if (e.this.B && e.this.f6135v != null) {
                    m.a(e.this.f6135v);
                    return;
                }
                if (e.this.A && e.this.f6135v != null) {
                    m.a(e.this.f6135v);
                    return;
                }
                if (e.this.f6139z && e.this.f6135v != null && e.this.D) {
                    m.a(e.this.f6135v);
                    return;
                }
                if (e.this.f6134u != null) {
                    e.this.f6134u.cancel();
                }
                e.this.ah();
            }
        });
        this.H.setAlpha(0.0f);
        this.f6132s.addView(this.H, new FrameLayout.LayoutParams(-2, -2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aS() {
        float centerX;
        float centerY;
        this.f6130q.getLocationOnScreen(new int[2]);
        if (this.J != null) {
            float f2 = this.L;
            float height = this.f6131r != null ? r2.getHeight() : 0.0f;
            if (height == 0.0f) {
                height = this.M - as.a(this.f6127n, 100.0f);
            }
            int width = (int) (f2 * this.J.getWidth() * 0.01d);
            int height2 = (int) (width * this.J.getHeight() * 0.01d);
            ViewGroup.LayoutParams layoutParams = this.H.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height2;
            this.H.setLayoutParams(layoutParams);
            centerX = (f2 * ((float) (this.J.getCenterX() * 0.01d))) - (width / 2);
            centerY = (height * ((float) (this.J.getCenterY() * 0.01d))) - (height2 / 2);
        } else {
            float pivotX = (r1[0] + this.f6130q.getPivotX()) - (this.H.getWidth() / 2);
            float pivotY = (r1[1] + this.f6130q.getPivotY()) - (this.H.getHeight() / 2);
            centerX = pivotX;
            centerY = pivotY;
        }
        this.H.setX(centerX);
        this.H.setY(centerY);
    }

    private void aT() {
        float width;
        float fA;
        for (AdSpacesBean.RenderViewBean renderViewBean : this.f6137x) {
            AdSpacesBean.PositionBean layerPosition = renderViewBean.getLayerPosition();
            ImageView imageView = new ImageView(this.f6127n);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setVisibility(0);
            String imageUrl = renderViewBean.getImageUrl();
            if (!TextUtils.isEmpty(imageUrl) && imageUrl.contains("http")) {
                com.beizi.fusion.g.i.a(this.f6127n).a(imageUrl).a(imageView);
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.e.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (e.this.f6135v != null) {
                        m.a(e.this.f6135v);
                    }
                }
            });
            ViewGroup viewGroup = this.f6131r;
            if (viewGroup != null) {
                width = viewGroup.getWidth();
                fA = this.f6131r.getHeight();
            } else {
                width = 0.0f;
                fA = 0.0f;
            }
            if (width == 0.0f) {
                width = this.L;
            }
            if (fA == 0.0f) {
                fA = this.M - as.a(this.f6127n, 100.0f);
            }
            this.f6132s.addView(imageView, new FrameLayout.LayoutParams((int) (width * layerPosition.getWidth() * 0.01d), (int) (fA * layerPosition.getHeight() * 0.01d)));
            float centerX = (float) (layerPosition.getCenterX() * 0.01d);
            float centerY = (fA * ((float) (layerPosition.getCenterY() * 0.01d))) - (r6 / 2);
            imageView.setX((width * centerX) - (r4 / 2));
            imageView.setY(centerY);
        }
    }

    @Override // com.beizi.fusion.work.a
    public void aF() {
        e();
        B();
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "CSJ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(int i2) {
        if (this.O != 1) {
            SpannableString spannableString = new SpannableString(this.Q);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(this.R)), 0, this.Q.length(), 33);
            ((SkipView) this.f6130q).setText(spannableString);
            return;
        }
        String strValueOf = String.valueOf(i2);
        String str = this.Q + " ";
        String str2 = str + strValueOf;
        SpannableString spannableString2 = new SpannableString(str2);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.R)), 0, str.length(), 33);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.S)), str2.indexOf(strValueOf), str2.length(), 33);
        ((SkipView) this.f6130q).setText(spannableString2);
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null || this.f6127n == null) {
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
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.e.1
                        @Override // java.lang.Runnable
                        public void run() {
                            e.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "CSJ sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    v.a(this, this.f6127n, this.f5544h, this.f5541e.getDirectDownload());
                    this.f5538b.t(TTAdSdk.getAdManager().getSDKVersion());
                    aB();
                }
            }
        }
        this.U = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            this.U = Math.max(this.U, this.f5542f.getHotRequestDelay());
        }
        List<AdSpacesBean.RenderViewBean> list = this.f6136w;
        boolean z2 = list != null && list.size() > 0;
        this.G = z2;
        if (z2) {
            aM();
        }
        this.L = as.m(this.f6127n);
        this.M = as.n(this.f6127n);
    }

    @Override // com.beizi.fusion.work.a
    public void e() {
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.U);
        long j2 = this.U;
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
        b();
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
        this.f6135v = null;
        com.beizi.fusion.d.g.a().a(g(), false, false);
        if (aC()) {
            return;
        }
        if (this.V == 0) {
            this.V = (int) as.k(this.f6127n);
        }
        if (this.W == 0) {
            this.W = (int) as.l(this.f6127n);
        }
        int iA = as.a(this.f6127n, this.W);
        int iA2 = as.a(this.f6127n, this.V);
        ac.a("BeiZis", "splashWidthPx = " + iA2 + ",splashHeightPx = " + iA);
        this.f6133t = v.a().createAdNative(this.f6127n);
        com.beizi.fusion.d.e eVar = this.f5540d;
        this.f6133t.loadSplashAd((eVar == null || eVar.y()) ? new AdSlot.Builder().setCodeId(this.f5545i).setSupportDeepLink(true).setImageAcceptedSize(iA2, iA).setExpressViewAcceptedSize(this.V, this.W).build() : new AdSlot.Builder().setCodeId(this.f5545i).setSupportDeepLink(true).setImageAcceptedSize(iA2, iA).setExpressViewAcceptedSize(this.V, this.W).build(), new TTAdNative.CSJSplashAdListener() { // from class: com.beizi.fusion.work.splash.e.3
            public void onSplashLoadFail(CSJAdError cSJAdError) {
                Log.d("BeiZis", "showCsjSplash onSplashLoadFail code:" + cSJAdError.getCode() + com.alipay.sdk.util.h.f3376b + cSJAdError.getMsg());
                e.this.b(cSJAdError.getMsg(), cSJAdError.getCode());
            }

            public void onSplashLoadSuccess() {
                Log.d("BeiZis", "showCsjSplash onSplashLoadSuccess()");
            }

            public void onSplashRenderFail(CSJSplashAd cSJSplashAd, CSJAdError cSJAdError) {
                Log.d("BeiZis", "showCsjSplash onSplashRenderFail code:" + cSJAdError.getCode() + com.alipay.sdk.util.h.f3376b + cSJAdError.getMsg());
                e.this.b(cSJAdError.getMsg(), cSJAdError.getCode());
            }

            public void onSplashRenderSuccess(CSJSplashAd cSJSplashAd) {
                Log.d("BeiZis", "showCsjSplash onSplashRenderSuccess()");
                e.this.T = cSJSplashAd;
                ((com.beizi.fusion.work.a) e.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                e.this.E();
                if (cSJSplashAd == null) {
                    e.this.e(-991);
                    return;
                }
                if (e.this.ac()) {
                    e.this.a(cSJSplashAd);
                } else {
                    e.this.S();
                }
                cSJSplashAd.setSplashAdListener(new CSJSplashAd.SplashAdListener() { // from class: com.beizi.fusion.work.splash.e.3.1
                    public void onSplashAdClick(CSJSplashAd cSJSplashAd2) {
                        Log.d("BeiZis", "showCsjSplash onSplashAdClick()");
                        e.this.K();
                        if (((com.beizi.fusion.work.a) e.this).f5540d != null) {
                            if (((com.beizi.fusion.work.a) e.this).f5540d.q() != 2) {
                                ((com.beizi.fusion.work.a) e.this).f5540d.d(e.this.g());
                                ((com.beizi.fusion.work.a) e.this).f5549m.sendEmptyMessageDelayed(2, (((com.beizi.fusion.work.a) e.this).f5548l + 5000) - System.currentTimeMillis());
                            }
                            e.this.an();
                        }
                    }

                    public void onSplashAdClose(CSJSplashAd cSJSplashAd2, int i2) {
                        Log.d("BeiZis", "showCsjSplash onSplashAdClose()");
                        if (((com.beizi.fusion.work.a) e.this).f5540d != null && ((com.beizi.fusion.work.a) e.this).f5540d.q() != 2) {
                            e.this.ah();
                        }
                        if (i2 == 1) {
                            e.this.N();
                        }
                        e.this.M();
                    }

                    public void onSplashAdShow(CSJSplashAd cSJSplashAd2) {
                        Log.d("BeiZis", "showCsjSplash onSplashAdShow()");
                        ((com.beizi.fusion.work.a) e.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                        e.this.ag();
                        e.this.I();
                        e.this.J();
                        e.this.am();
                    }
                });
            }
        }, (int) this.f6129p);
    }

    private void b() {
        CSJSplashAd cSJSplashAd = this.T;
        if (cSJSplashAd != null) {
            this.f6135v = cSJSplashAd.getSplashView();
            if (as.a("com.bytedance.sdk.openadsdk.ISplashClickEyeListener")) {
                a(this.f6127n, this.T, this.f6135v);
            } else {
                Log.d("BeiZis", "CSJ sdk is not Support SplashClickEye");
            }
            if (this.f6132s != null && this.f6131r != null) {
                b(this.T);
                this.f6132s.removeAllViews();
                this.f6132s.addView(this.f6135v);
                aP();
                this.f6131r.removeAllViews();
                this.f6131r.addView(this.f6132s);
                return;
            }
            aD();
            return;
        }
        aD();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(CSJSplashAd cSJSplashAd) {
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

    private void a(Context context, CSJSplashAd cSJSplashAd, View view) {
        Activity activity = (Activity) context;
        if (activity == null || cSJSplashAd == null || view == null) {
            return;
        }
        c cVarA = c.a(context);
        cSJSplashAd.setSplashClickEyeListener(new CSJSplashAd.SplashClickEyeListener() { // from class: com.beizi.fusion.work.splash.e.2
            public void onSplashClickEyeClick() {
                Log.d("BeiZis", "showCsjSplash onSplashClickEyeClick() ");
            }

            public void onSplashClickEyeClose() {
                Log.d("BeiZis", "showCsjSplash onSplashClickEyeClose() ");
            }

            public void onSplashClickEyeReadyToShow(CSJSplashAd cSJSplashAd2) {
                Log.d("BeiZis", "showCsjSplash onSplashClickEyeReadyToShow() ");
                com.beizi.fusion.d.g.a().a(e.this.g(), true, true);
                boolean zB = com.beizi.fusion.d.g.a().b();
                if (((com.beizi.fusion.work.a) e.this).f5538b != null) {
                    ((com.beizi.fusion.work.a) e.this).f5538b.K(zB ? "2" : "0");
                    e.this.aB();
                }
            }
        });
        cVarA.a(context, cSJSplashAd, view, activity.getWindow().getDecorView());
    }

    private void b(CSJSplashAd cSJSplashAd) {
        String str;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.e.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (e.this.G && e.this.H != null) {
                    m.a(e.this.H);
                    return;
                }
                if (e.this.f6134u != null) {
                    e.this.f6134u.cancel();
                }
                e.this.ah();
            }
        };
        if (this.G) {
            if (cSJSplashAd != null) {
                cSJSplashAd.hideSkipButton();
            }
            View view = this.f6130q;
            if (view != null) {
                view.setVisibility(8);
                view.setAlpha(0.0f);
            }
            SkipView skipView = new SkipView(this.f6127n);
            this.f6130q = skipView;
            skipView.setOnClickListener(onClickListener);
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.e.5
                @Override // java.lang.Runnable
                public void run() {
                    e.this.aN();
                }
            }, this.K);
            str = "beizi";
        } else if (this.f6130q != null) {
            if (cSJSplashAd != null) {
                cSJSplashAd.hideSkipButton();
            }
            this.f6130q.setOnClickListener(onClickListener);
            aL();
            str = "app";
        } else {
            str = "buyer";
        }
        com.beizi.fusion.b.b bVar = this.f5538b;
        if (bVar != null) {
            bVar.r(str);
            aB();
        }
    }
}
