package com.beizi.fusion.work.splash;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ah;
import com.beizi.fusion.g.ai;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.m;
import com.beizi.fusion.g.n;
import com.beizi.fusion.g.u;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.widget.CircleProgressView;
import com.beizi.fusion.widget.SkipView;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.managers.status.SDKStatus;
import com.qq.e.comm.pi.IBidding;
import com.qq.e.comm.util.AdError;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public class f extends com.beizi.fusion.work.a {
    private long H;
    private long J;
    private boolean K;
    private CircleProgressView L;
    private AdSpacesBean.PositionBean M;
    private AdSpacesBean.PositionBean N;
    private List<View> O;
    private float P;
    private float Q;
    private AdSpacesBean.RenderViewBean R;
    private int S;
    private int T;
    private String U;
    private String V;
    private String W;

    /* renamed from: n, reason: collision with root package name */
    int f6153n;

    /* renamed from: o, reason: collision with root package name */
    long f6154o;

    /* renamed from: p, reason: collision with root package name */
    View.OnClickListener f6155p;

    /* renamed from: q, reason: collision with root package name */
    private Context f6156q;

    /* renamed from: r, reason: collision with root package name */
    private String f6157r;

    /* renamed from: s, reason: collision with root package name */
    private long f6158s;

    /* renamed from: t, reason: collision with root package name */
    private boolean f6159t;

    /* renamed from: u, reason: collision with root package name */
    private View f6160u;

    /* renamed from: v, reason: collision with root package name */
    private ViewGroup f6161v;

    /* renamed from: w, reason: collision with root package name */
    private ViewGroup f6162w;

    /* renamed from: x, reason: collision with root package name */
    private SplashAD f6163x;

    /* renamed from: y, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6164y;

    /* renamed from: z, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6165z = new ArrayList();
    private List<AdSpacesBean.RenderViewBean> A = new ArrayList();
    private boolean B = false;
    private boolean C = false;
    private boolean D = false;
    private boolean E = false;
    private boolean F = false;
    private long G = 5000;
    private int I = 0;

    public class a implements SplashADListener {

        /* renamed from: a, reason: collision with root package name */
        boolean f6170a;

        /* renamed from: b, reason: collision with root package name */
        boolean f6171b;

        /* renamed from: c, reason: collision with root package name */
        boolean f6172c;

        private a() {
            this.f6170a = false;
            this.f6171b = false;
            this.f6172c = false;
        }

        public void onADClicked() {
            Log.d("BeiZis", "showGdtSplash onAdClick()");
            if (((com.beizi.fusion.work.a) f.this).f5540d != null && ((com.beizi.fusion.work.a) f.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) f.this).f5540d.d(f.this.g());
                ((com.beizi.fusion.work.a) f.this).f5549m.sendEmptyMessageDelayed(2, (((com.beizi.fusion.work.a) f.this).f5548l + 5000) - System.currentTimeMillis());
            }
            if (this.f6171b) {
                return;
            }
            this.f6171b = true;
            f.this.K();
            f.this.an();
        }

        public void onADDismissed() {
            Log.d("BeiZis", "showGdtSplash onADDismissed()");
            if (((com.beizi.fusion.work.a) f.this).f5540d.q() != 2) {
                f.this.ah();
            }
            f.this.M();
        }

        public void onADExposure() {
            Log.d("BeiZis", "showGdtSplash onADExposure()");
            ((com.beizi.fusion.work.a) f.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
            if (this.f6170a) {
                return;
            }
            this.f6170a = true;
            f.this.aG();
            f.this.ag();
            f.this.J();
            f.this.am();
        }

        public void onADLoaded(long j2) {
            if (f.this.f6163x.getECPM() > 0) {
                f.this.a(r0.f6163x.getECPM());
            }
            if (u.f5253a) {
                f.this.f6163x.setDownloadConfirmListener(u.f5254b);
            }
            f.this.E();
            if (((com.beizi.fusion.work.a) f.this).f5546j.ordinal() >= com.beizi.fusion.f.a.ADSHOW.ordinal()) {
                int iOrdinal = ((com.beizi.fusion.work.a) f.this).f5546j.ordinal();
                String str = iOrdinal != 2 ? iOrdinal != 3 ? "other" : "fail" : "show";
                Message messageObtain = Message.obtain();
                messageObtain.obj = "ad status error " + str;
                f.this.a(messageObtain);
                return;
            }
            ((com.beizi.fusion.work.a) f.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
            f.g(f.this);
            if (((com.beizi.fusion.work.a) f.this).f5540d != null) {
                Log.d("BeiZis", "showGdtSplash onADLoaded:" + j2 + ",mAdLifeControl.getAdStatus() = " + ((com.beizi.fusion.work.a) f.this).f5540d.r() + ",gap = " + (j2 - SystemClock.elapsedRealtime()));
            }
            if (SystemClock.elapsedRealtime() >= j2 || !f.this.ac()) {
                f.this.S();
            } else {
                f.this.aL();
            }
        }

        public void onADPresent() {
            Log.d("BeiZis", "showGdtSplash onADPresent()");
            f.g(f.this);
            f.this.I();
        }

        public void onADTick(long j2) {
            if (!this.f6172c) {
                if (f.this.K) {
                    f fVar = f.this;
                    fVar.O = m.b(fVar.f6162w);
                }
                f.this.aS();
                this.f6172c = true;
            }
            if (f.this.K) {
                if (f.this.J > 0 && f.this.J <= f.this.G) {
                    if (f.this.B) {
                        if (f.this.H <= 0 || j2 <= f.this.H) {
                            f.this.F = false;
                            f.this.f6160u.setAlpha(1.0f);
                        } else {
                            f.this.F = true;
                            f.this.f6160u.setAlpha(0.2f);
                        }
                    }
                    if (f.this.J == f.this.G) {
                        f.this.f6160u.setEnabled(false);
                    } else {
                        f.this.f6160u.setEnabled(true);
                    }
                }
                f.this.g(Math.round(j2 / 1000.0f));
            }
            if (((com.beizi.fusion.work.a) f.this).f5540d == null || ((com.beizi.fusion.work.a) f.this).f5540d.q() == 2) {
                return;
            }
            ((com.beizi.fusion.work.a) f.this).f5540d.a(j2);
        }

        public void onNoAD(AdError adError) {
            Log.d("BeiZis", "showGdtSplash onNoAD:" + adError.getErrorMsg());
            f.this.b(adError.getErrorMsg(), adError.getErrorCode());
            if (f.this.I < 1 || ((com.beizi.fusion.work.a) f.this).f5540d == null) {
                return;
            }
            ((com.beizi.fusion.work.a) f.this).f5540d.s();
        }
    }

    public f(Context context, String str, long j2, View view, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.d.e eVar) {
        this.f6156q = context;
        this.f6157r = str;
        this.f6158s = j2;
        if (view != null) {
            view.setVisibility(8);
        }
        this.f6160u = null;
        this.f6161v = viewGroup;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f6162w = new SplashContainer(context);
        this.f6164y = list;
        x();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aL() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " splashWorkers:" + eVar.p().toString());
        ad();
        com.beizi.fusion.d.h hVar = this.f5543g;
        if (hVar == com.beizi.fusion.d.h.SUCCESS) {
            b();
            ai();
        } else if (hVar == com.beizi.fusion.d.h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    private void aM() {
        ViewGroup viewGroup = this.f6161v;
        if (viewGroup == null) {
            aD();
            return;
        }
        viewGroup.removeAllViews();
        this.f6161v.addView(this.f6162w);
        this.f6163x.showAd(this.f6162w);
        if (this.L != null) {
            this.f6161v.addView(this.L, new FrameLayout.LayoutParams(-2, -2));
        }
        aQ();
        if (this.K) {
            aO();
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
    private void aN() {
        boolean z2 = false;
        for (int i2 = 0; i2 < this.f6164y.size(); i2++) {
            AdSpacesBean.RenderViewBean renderViewBean = this.f6164y.get(i2);
            String type = renderViewBean.getType();
            if ("SKIPVIEW".equals(type)) {
                this.A.add(renderViewBean);
            } else if ("MATERIALVIEW".equals(type)) {
                this.f6165z.add(renderViewBean);
            }
        }
        if (this.A.size() > 0) {
            AdSpacesBean.RenderViewBean renderViewBean2 = this.A.get(0);
            this.R = renderViewBean2;
            if (renderViewBean2 != null) {
                this.N = renderViewBean2.getTapPosition();
                this.M = this.R.getLayerPosition();
                long skipUnavailableTime = this.R.getSkipUnavailableTime();
                if (skipUnavailableTime > 0) {
                    this.J = skipUnavailableTime;
                }
                this.S = this.R.getShowCountDown();
                this.T = this.R.getShowBorder();
                String skipText = this.R.getSkipText();
                this.U = skipText;
                if (TextUtils.isEmpty(skipText)) {
                    this.U = "跳过";
                }
                String textColor = this.R.getTextColor();
                this.V = textColor;
                if (TextUtils.isEmpty(textColor)) {
                    this.V = "#FFFFFF";
                }
                String countDownColor = this.R.getCountDownColor();
                this.W = countDownColor;
                if (TextUtils.isEmpty(countDownColor)) {
                    this.W = "#FFFFFF";
                }
                List<AdSpacesBean.PassPolicyBean> passPolicy = this.R.getPassPolicy();
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
                                this.C = ai.a(passPercent);
                                break;
                            case 1:
                                this.B = ai.a(passPercent);
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
                                        this.D = ai.a(passPercent);
                                    }
                                    if (width2 * height2 < width * height) {
                                        this.E = true;
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
        if (this.f6165z.size() > 0) {
            Collections.sort(this.f6165z, new Comparator<AdSpacesBean.RenderViewBean>() { // from class: com.beizi.fusion.work.splash.f.2
                @Override // java.util.Comparator
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public int compare(AdSpacesBean.RenderViewBean renderViewBean3, AdSpacesBean.RenderViewBean renderViewBean4) {
                    return renderViewBean4.getLevel() - renderViewBean3.getLevel();
                }
            });
        }
    }

    private void aO() {
        if (this.B) {
            U();
        }
        if (this.C) {
            V();
        }
        if (this.D) {
            W();
        }
        if (this.E) {
            X();
        }
        this.H = this.G - this.J;
        if (this.f6165z.size() > 0) {
            aT();
        }
    }

    private View aP() {
        View view;
        String str;
        this.f6155p = new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.f.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                View view3 = f.this.O != null ? (View) f.this.O.get(1) : null;
                if (f.this.D && view3 != null) {
                    m.a(view3);
                    return;
                }
                if (f.this.C && view3 != null) {
                    m.a(view3);
                    return;
                }
                if (f.this.B && view3 != null && f.this.F) {
                    m.a(view3);
                    return;
                }
                if (f.this.L != null) {
                    m.a(f.this.L);
                }
                f.this.N();
            }
        };
        if (this.K) {
            View view2 = this.f6160u;
            if (view2 != null) {
                view2.setVisibility(8);
                view2.setAlpha(0.0f);
            }
            SkipView skipView = new SkipView(this.f6156q);
            this.f6160u = skipView;
            skipView.setOnClickListener(this.f6155p);
            CircleProgressView circleProgressView = new CircleProgressView(this.f6156q);
            this.L = circleProgressView;
            circleProgressView.setAlpha(0.0f);
            view = this.L;
            str = "beizi";
        } else {
            view = this.f6160u;
            if (view != null) {
                CircleProgressView circleProgressView2 = new CircleProgressView(this.f6156q);
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

    private void aQ() {
        if (!this.K) {
            View view = this.f6160u;
            if (view != null) {
                view.setVisibility(0);
                this.f6160u.setAlpha(1.0f);
                return;
            }
            return;
        }
        if (this.M == null || this.R == null) {
            aR();
            return;
        }
        float f2 = this.P;
        float height = this.f6161v.getHeight();
        if (height == 0.0f) {
            height = this.Q - as.a(this.f6156q, 100.0f);
        }
        int width = (int) (f2 * this.M.getWidth() * 0.01d);
        if (this.M.getHeight() < 12.0d) {
            aR();
            return;
        }
        int height2 = (int) (width * this.M.getHeight() * 0.01d);
        int paddingHeight = (int) (height2 * this.R.getPaddingHeight() * 0.01d);
        if (paddingHeight < 0) {
            paddingHeight = 0;
        }
        ((SkipView) this.f6160u).setData(this.T, paddingHeight);
        g(5);
        this.f6161v.addView(this.f6160u, new FrameLayout.LayoutParams(width, height2));
        float centerX = (f2 * ((float) (this.M.getCenterX() * 0.01d))) - (width / 2);
        float centerY = (height * ((float) (this.M.getCenterY() * 0.01d))) - (height2 / 2);
        this.f6160u.setX(centerX);
        this.f6160u.setY(centerY);
        View view2 = this.f6160u;
        if (view2 != null) {
            view2.setVisibility(0);
        }
    }

    private void aR() {
        int i2 = (int) (this.P * 0.15d);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, (int) (i2 * 0.45d));
        layoutParams.gravity = 53;
        layoutParams.topMargin = as.a(this.f6156q, 20.0f);
        layoutParams.rightMargin = as.a(this.f6156q, 20.0f);
        ViewGroup viewGroup = this.f6161v;
        if (viewGroup != null) {
            viewGroup.addView(this.f6160u, layoutParams);
        }
        View view = this.f6160u;
        if (view != null) {
            this.S = 1;
            this.T = 1;
            ((SkipView) view).setData(1, 0);
            ((SkipView) this.f6160u).setText(String.format("跳过 %d", 5));
            this.f6160u.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aS() {
        float pivotX;
        float pivotY;
        float height;
        View view = this.f6160u;
        if (view == null) {
            return;
        }
        view.getLocationOnScreen(new int[2]);
        if (this.N != null) {
            float f2 = this.P;
            float height2 = this.f6161v.getHeight();
            if (height2 == 0.0f) {
                height2 = this.Q - as.a(this.f6156q, 100.0f);
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
            pivotX = (r2[0] + this.f6160u.getPivotX()) - (this.L.getWidth() / 2);
            pivotY = r2[1] + this.f6160u.getPivotY();
            height = this.L.getHeight() / 2;
        }
        this.L.setX(pivotX);
        this.L.setY(pivotY - height);
    }

    private void aT() {
        for (AdSpacesBean.RenderViewBean renderViewBean : this.f6165z) {
            AdSpacesBean.PositionBean layerPosition = renderViewBean.getLayerPosition();
            ImageView imageView = new ImageView(this.f6156q);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setVisibility(0);
            String imageUrl = renderViewBean.getImageUrl();
            if (!TextUtils.isEmpty(imageUrl) && imageUrl.contains("http")) {
                com.beizi.fusion.g.i.a(this.f6156q).a(imageUrl).a(imageView);
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.f.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    View view2 = f.this.O != null ? (View) f.this.O.get(1) : null;
                    if (view2 != null) {
                        m.a(view2);
                    }
                }
            });
            float width = this.f6161v.getWidth();
            float height = this.f6161v.getHeight();
            if (width == 0.0f) {
                width = this.P;
            }
            if (height == 0.0f) {
                height = this.Q - as.a(this.f6156q, 100.0f);
            }
            this.f6161v.addView(imageView, new FrameLayout.LayoutParams((int) (width * layerPosition.getWidth() * 0.01d), (int) (height * layerPosition.getHeight() * 0.01d)));
            float centerX = (float) (layerPosition.getCenterX() * 0.01d);
            float centerY = (height * ((float) (layerPosition.getCenterY() * 0.01d))) - (r6 / 2);
            imageView.setX((width * centerX) - (r5 / 2));
            imageView.setY(centerY);
        }
    }

    public static /* synthetic */ int g(f fVar) {
        int i2 = fVar.I;
        fVar.I = i2 + 1;
        return i2;
    }

    @Override // com.beizi.fusion.work.a
    public void aG() {
        SplashAD splashAD = this.f6163x;
        if (splashAD == null || splashAD.getECPM() <= 0 || this.f6159t) {
            return;
        }
        this.f6159t = true;
        ac.a("BeiZis", "channel == GDT竞价成功");
        ac.a("BeiZis", "channel == sendWinNoticeECPM" + this.f6163x.getECPM());
        SplashAD splashAD2 = this.f6163x;
        com.beizi.fusion.d.k.a((IBidding) splashAD2, splashAD2.getECPM());
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "GDT";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(int i2) {
        if (this.S != 1) {
            SpannableString spannableString = new SpannableString(this.U);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(this.V)), 0, this.U.length(), 33);
            ((SkipView) this.f6160u).setText(spannableString);
            return;
        }
        String strValueOf = String.valueOf(i2);
        String str = this.U + " ";
        String str2 = str + strValueOf;
        SpannableString spannableString2 = new SpannableString(str2);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.V)), 0, str.length(), 33);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor(this.W)), str2.indexOf(strValueOf), str2.length(), 33);
        ((SkipView) this.f6160u).setText(spannableString2);
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        StringBuilder sb = new StringBuilder();
        sb.append("enter handleBidAdLoaded isBidTypeC2S() = ");
        sb.append(at());
        sb.append(",mSplashAD != null ? ");
        sb.append(this.f6163x != null);
        ac.a("BeiZis", sb.toString());
        if (!F() || this.f6163x == null) {
            return;
        }
        aq();
        int iA = ah.a(this.f5541e.getPriceDict(), this.f6163x.getECPMLevel());
        if (iA == -1 || iA == -2) {
            c(3);
            Q();
            return;
        }
        ac.a("BeiZisBid", "gdt splash price = " + iA);
        a((double) iA);
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f6154o = System.currentTimeMillis();
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
                if (!as.a("com.qq.e.comm.managers.GDTAdSdk")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.f.1
                        @Override // java.lang.Runnable
                        public void run() {
                            f.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "GDT sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    com.beizi.fusion.d.k.a(this.f6156q, this.f5544h);
                    this.f5538b.s(SDKStatus.getIntegrationSDKVersion());
                    aB();
                    B();
                }
            }
        }
        this.f6153n = this.f5541e.getReqTimeOutType();
        long sleepTime = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            sleepTime = Math.max(sleepTime, this.f5542f.getHotRequestDelay());
        }
        List<AdSpacesBean.RenderViewBean> list = this.f6164y;
        boolean z2 = list != null && list.size() > 0;
        this.K = z2;
        if (z2) {
            aN();
        }
        u.f5253a = !n.a(this.f5541e.getDirectDownload());
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + sleepTime);
        if (sleepTime > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, sleepTime);
        } else {
            com.beizi.fusion.d.e eVar = this.f5540d;
            if (eVar != null && eVar.r() < 1 && this.f5540d.q() != 2) {
                p();
            }
        }
        this.P = as.m(this.f6156q);
        this.Q = as.n(this.f6156q);
    }

    @Override // com.beizi.fusion.work.a
    public void f(int i2) {
        SplashAD splashAD = this.f6163x;
        if (splashAD == null || splashAD.getECPM() <= 0 || this.f6159t) {
            return;
        }
        this.f6159t = true;
        ac.a("BeiZis", "channel == GDT竞价失败:" + i2);
        com.beizi.fusion.d.k.b((IBidding) this.f6163x, i2 != 1 ? 10001 : 1);
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        SplashAD splashAD = this.f6163x;
        if (splashAD == null) {
            return null;
        }
        int iA = ah.a(this.f5541e.getPriceDict(), splashAD.getECPMLevel());
        if (iA == -1 || iA == -2) {
            return null;
        }
        return iA + "";
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        long jCurrentTimeMillis = this.f6158s;
        int i2 = (int) jCurrentTimeMillis;
        int i3 = this.f6153n;
        if (i3 == 1) {
            i2 = 0;
        } else if (i3 == 2) {
            i2 = (int) jCurrentTimeMillis;
        } else if (i3 == 3) {
            jCurrentTimeMillis -= System.currentTimeMillis() - this.f6154o;
            i2 = (int) jCurrentTimeMillis;
        }
        int i4 = i2;
        ac.a("BeiZis", "reqTimeOutType = " + this.f6153n + ",timeOut = " + i4);
        aP();
        if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
            this.f6163x = new SplashAD((Activity) this.f6156q, this.f5545i, new a(), i4, aJ());
        } else {
            this.f6163x = new SplashAD((Activity) this.f6156q, this.f5545i, new a(), i4);
        }
        this.f6163x.fetchAdOnly();
    }

    private void b() {
        View view;
        View.OnClickListener onClickListener;
        if (this.K || (view = this.f6160u) == null || (onClickListener = this.f6155p) == null) {
            return;
        }
        view.setOnClickListener(onClickListener);
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
        Log.d("BeiZis", g() + " out make show ad");
        aM();
    }
}
