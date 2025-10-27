package com.beizi.fusion.work.h;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.beizi.fusion.R;
import com.beizi.fusion.d.h;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.am;
import com.beizi.fusion.g.ao;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.i;
import com.beizi.fusion.g.r;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.CoordinateBean;
import com.beizi.fusion.widget.ScrollClickView;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class a extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {
    protected ImageView A;
    protected ImageView B;
    protected TextView C;
    protected TextView D;
    protected TextView E;
    protected TextView F;
    protected TextView G;
    protected long H;
    protected float I;
    protected float J;
    protected Context N;
    protected Activity O;
    protected ao Q;
    protected am R;
    protected CountDownTimer S;
    protected AdSpacesBean.RenderViewBean T;
    protected AdSpacesBean.BuyerBean.RenderAds U;
    protected List<AdSpacesBean.RenderViewBean> V;
    protected List<Pair<String, Integer>> W;

    /* renamed from: n, reason: collision with root package name */
    protected View f5776n;

    /* renamed from: o, reason: collision with root package name */
    protected View f5777o;

    /* renamed from: p, reason: collision with root package name */
    protected View f5778p;

    /* renamed from: q, reason: collision with root package name */
    protected ViewGroup f5779q;

    /* renamed from: r, reason: collision with root package name */
    protected ViewGroup f5780r;

    /* renamed from: s, reason: collision with root package name */
    protected ViewGroup f5781s;

    /* renamed from: t, reason: collision with root package name */
    protected ViewGroup f5782t;

    /* renamed from: u, reason: collision with root package name */
    protected ViewGroup f5783u;

    /* renamed from: v, reason: collision with root package name */
    protected ViewGroup f5784v;

    /* renamed from: w, reason: collision with root package name */
    protected ViewGroup f5785w;

    /* renamed from: x, reason: collision with root package name */
    protected ViewGroup f5786x;

    /* renamed from: y, reason: collision with root package name */
    protected ImageView f5787y;

    /* renamed from: z, reason: collision with root package name */
    protected ImageView f5788z;
    protected boolean K = false;
    protected boolean L = false;
    protected boolean M = false;
    protected com.beizi.fusion.f.a P = com.beizi.fusion.f.a.ADDEFAULT;

    public a(Context context, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, int i2) {
        this.N = context;
        this.H = j2;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5547k = i2;
        this.f5542f = forwardBean;
        this.I = as.m(context);
        this.J = as.n(context);
        aM();
        x();
    }

    private void b(Activity activity) {
        if (activity == null || this.f5778p == null) {
            return;
        }
        View decorView = activity.getWindow().getDecorView();
        this.f5776n = decorView;
        if (decorView instanceof FrameLayout) {
            as.a(this.f5778p);
            ((FrameLayout) this.f5776n).addView(this.f5778p);
            b(this.f5780r);
        }
    }

    private void be() {
        List<String> clickView = this.U.getClickView();
        ArrayList arrayList = new ArrayList();
        if (clickView != null && clickView.size() > 0) {
            if (clickView.contains("bg")) {
                arrayList.add(this.f5779q);
                arrayList.add(this.f5781s);
                arrayList.add(this.f5785w);
            } else if (clickView.contains(com.umeng.analytics.pro.am.aw)) {
                arrayList.add(this.f5781s);
            } else {
                if (clickView.contains("image")) {
                    arrayList.add(this.f5782t);
                }
                if (clickView.contains("title")) {
                    arrayList.add(this.D);
                }
                if (clickView.contains("desc")) {
                    arrayList.add(this.E);
                }
                if (clickView.contains(RemoteMessageConst.Notification.ICON)) {
                    arrayList.add(this.A);
                }
                if (clickView.contains(TypedValues.AttributesType.S_TARGET)) {
                    arrayList.add(this.f5784v);
                }
            }
        }
        a(arrayList);
    }

    private void bf() {
        ((FrameLayout) this.f5776n).removeView(this.f5778p);
    }

    private void c(int i2, int i3) {
        a(this.f5782t, this.U.getImageCoordinate(), i2, i3);
        aW();
    }

    private void e(int i2, int i3) {
        a(this.E, this.U.getDescCoordinate(), i2, i3);
        if (TextUtils.isEmpty(aY())) {
            return;
        }
        this.E.setText(aY());
    }

    private void f(int i2, int i3) {
        a(this.A, this.U.getIconCoordinate(), i2, i3);
        if (this.A.getVisibility() != 0 || TextUtils.isEmpty(aZ())) {
            return;
        }
        i.a(this.N).a(aZ(), new i.a() { // from class: com.beizi.fusion.work.h.a.2
            @Override // com.beizi.fusion.g.i.a
            public void a() {
            }

            @Override // com.beizi.fusion.g.i.a
            public void a(Bitmap bitmap) {
                a.this.A.setImageBitmap(bitmap);
            }
        });
    }

    private void g(int i2, int i3) {
        a(this.f5784v, this.U.getActionCoordinate(), i2, i3);
        if (TextUtils.isEmpty(ba())) {
            return;
        }
        this.F.setText(ba());
    }

    private void h(int i2, int i3) {
        a(this.f5783u, this.U.getCloseCoordinate(), i2, i3);
    }

    private void i(int i2, int i3) throws NumberFormatException {
        b(this.f5785w, this.U.getScrollCoordinate(), i2, i3);
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        try {
            if (this.M) {
                return;
            }
            this.M = true;
            this.O = activity;
            b(activity);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void a(List<View> list) {
    }

    public int aL() {
        return -1;
    }

    public void aM() {
        if (aL() == -1) {
            return;
        }
        View viewInflate = LayoutInflater.from(this.N).inflate(aL(), (ViewGroup) null);
        this.f5777o = viewInflate;
        this.f5779q = (ViewGroup) viewInflate.findViewById(R.id.rl_bg_container);
        this.f5780r = (ViewGroup) this.f5777o.findViewById(R.id.rl_anim_container);
        this.f5781s = (ViewGroup) this.f5777o.findViewById(R.id.rl_container);
        this.f5782t = (ViewGroup) this.f5777o.findViewById(R.id.fl_img_container);
        this.f5787y = (ImageView) this.f5777o.findViewById(R.id.iv_imageview);
        this.f5783u = (ViewGroup) this.f5777o.findViewById(R.id.rl_close);
        this.C = (TextView) this.f5777o.findViewById(R.id.tv_close);
        this.f5788z = (ImageView) this.f5777o.findViewById(R.id.iv_close);
        this.D = (TextView) this.f5777o.findViewById(R.id.tv_title);
        this.E = (TextView) this.f5777o.findViewById(R.id.tv_desc);
        this.A = (ImageView) this.f5777o.findViewById(R.id.iv_icon);
        this.f5784v = (ViewGroup) this.f5777o.findViewById(R.id.rl_action);
        this.F = (TextView) this.f5777o.findViewById(R.id.tv_action);
        this.f5785w = (ViewGroup) this.f5777o.findViewById(R.id.rl_slide_down_container);
        this.G = (TextView) this.f5777o.findViewById(R.id.tv_slide_down_title);
        this.B = (ImageView) this.f5777o.findViewById(R.id.iv_slide_down_arrow);
        this.f5786x = (ViewGroup) this.f5777o.findViewById(R.id.fl_event_container);
    }

    public void aN() {
    }

    public void aO() {
    }

    public void aP() {
        Log.d("BeiZis", "showUnifiedCustomAd Callback --> onADClicked()");
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar != null && eVar.q() != 2) {
            this.f5540d.d(g());
        }
        if (this.L) {
            return;
        }
        this.L = true;
        K();
        an();
        if (this.f5547k != 2) {
            bd();
        }
    }

    public void aQ() {
        Log.d("BeiZis", "showUnifiedCustomAd Callback --> onAdShow()");
        this.P = com.beizi.fusion.f.a.ADSHOW;
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar != null && eVar.q() != 2) {
            this.f5540d.b(g());
        }
        if (this.K) {
            return;
        }
        this.K = true;
        aG();
        bb();
        I();
        J();
        am();
    }

    public void aR() {
        try {
            if (ac()) {
                b();
            } else {
                S();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void aS() {
        if (this.U != null) {
            a((int) this.I, (int) this.J);
            this.f5788z.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.h.a.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    a.this.aT();
                }
            });
        }
        this.f5778p = this.f5777o;
        aU();
    }

    public void aT() {
    }

    public void aU() {
    }

    public void aV() {
    }

    public void aW() {
    }

    public String aX() {
        return "";
    }

    public String aY() {
        return "";
    }

    public String aZ() {
        return "";
    }

    public String ba() {
        return "";
    }

    public void bb() {
        AdSpacesBean.BuyerBean.RenderAds renderAds;
        if (this.C == null || this.f5788z == null || (renderAds = this.U) == null) {
            return;
        }
        if (renderAds.getAutoClose() == 0 && this.U.getMinTime() == 0) {
            this.C.setVisibility(8);
            this.f5788z.setVisibility(0);
        } else {
            this.f5788z.setVisibility(8);
            this.C.setVisibility(0);
            a(this.U.getAutoClose(), this.U.getMinTime(), this.U.getMaxTime());
        }
        this.f5783u.setVisibility(0);
    }

    public void bc() {
    }

    public void bd() {
        Log.d("BeiZis", "UnifiedCustomAd onADClosed()");
        ao aoVar = this.Q;
        if (aoVar != null) {
            aoVar.c();
        }
        am amVar = this.R;
        if (amVar != null) {
            amVar.b();
        }
        ah();
        M();
        c(this.O);
    }

    public void c(boolean z2) {
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null || aL() == -1) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        List<AdSpacesBean.RenderViewBean> renderView = this.f5541e.getRenderView();
        this.V = renderView;
        if (renderView != null && renderView.size() > 0) {
            AdSpacesBean.RenderViewBean renderViewBean = this.V.get(0);
            this.T = renderViewBean;
            this.W = r.a(renderViewBean.getDpLinkUrlList());
        }
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                aN();
            }
        }
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "";
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.P;
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        this.U = this.f5541e.getRenderAds();
        aO();
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f5778p;
    }

    private int c(String str, int i2) {
        if (str.contains("%")) {
            return (i2 * ((int) Float.parseFloat(str.substring(0, str.indexOf("%"))))) / 100;
        }
        return as.a(this.N, Float.parseFloat(str));
    }

    private void a(int i2, int i3) throws NumberFormatException {
        a(this.f5779q, this.U.getBgCoordinate(), i2, i3);
        int i4 = this.f5779q.getLayoutParams().width;
        int i5 = this.f5779q.getLayoutParams().height;
        b(i4, i5);
        i(i4, i5);
    }

    private void b(View view) {
        if (view != null) {
            view.setVisibility(8);
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
            translateAnimation.setDuration(500L);
            view.setVisibility(0);
            view.startAnimation(translateAnimation);
        }
    }

    private void c(Activity activity) {
        if (activity != null) {
            if (this.f5776n == null) {
                this.f5776n = activity.getWindow().getDecorView();
            }
            if (this.f5776n instanceof FrameLayout) {
                bf();
            }
        }
        q();
    }

    private void a(final int i2, final int i3, final int i4) {
        CountDownTimer countDownTimer = this.S;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = new CountDownTimer(600 + (i4 * 1000), 1000L) { // from class: com.beizi.fusion.work.h.a.3
            @Override // android.os.CountDownTimer
            public void onFinish() {
                if (i2 == 1) {
                    a.this.bd();
                }
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                a aVar = a.this;
                TextView textView = aVar.C;
                if (textView == null || aVar.f5788z == null) {
                    return;
                }
                int i5 = (int) (j2 / 1000.0f);
                if (i4 - i5 < i3) {
                    textView.setText(String.valueOf(i5));
                } else {
                    textView.setVisibility(8);
                    a.this.f5788z.setVisibility(0);
                }
            }
        };
        this.S = countDownTimer2;
        countDownTimer2.start();
    }

    private void b() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " NativeAdWorker:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            bc();
            if (this.f5778p != null) {
                this.f5540d.a(g(), this.f5778p);
                return;
            } else {
                this.f5540d.b(R2.drawable.ic_coupon_middle);
                return;
            }
        }
        if (hVar == h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    private void d(int i2, int i3) {
        a(this.D, this.U.getTitleCoordinate(), i2, i3);
        if (TextUtils.isEmpty(aX())) {
            return;
        }
        this.D.setText(aX());
    }

    private void a(View view, String str, int i2, int i3) {
        RelativeLayout.LayoutParams layoutParams;
        boolean z2;
        if (view == null) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            view.setVisibility(8);
            return;
        }
        if (str.equals("-1:-1:-1:-1:-1:-1:-1:-1:-1")) {
            view.setVisibility(8);
            return;
        }
        CoordinateBean coordinate = CoordinateBean.getCoordinate(str);
        if (coordinate == null) {
            view.setVisibility(8);
            return;
        }
        int[] iArrA = a(coordinate, i2, i3);
        int[] iArrA2 = a(coordinate, i2, i3, iArrA);
        boolean z3 = view instanceof TextView;
        if (z3) {
            layoutParams = new RelativeLayout.LayoutParams(iArrA2[0], -2);
        } else {
            layoutParams = new RelativeLayout.LayoutParams(iArrA2[0], iArrA2[1]);
        }
        layoutParams.setMargins(iArrA[0], iArrA[1], iArrA[2], iArrA[3]);
        if (!coordinate.getTop().equals("-1") || coordinate.getBottom().equals("-1")) {
            z2 = false;
        } else {
            layoutParams.addRule(12, -1);
            z2 = true;
        }
        if (coordinate.getLeft().equals("-1") && !coordinate.getRight().equals("-1")) {
            layoutParams.addRule(11, -1);
        }
        view.setLayoutParams(layoutParams);
        if (!coordinate.getFontOrCorner().equals("-1")) {
            if (z3) {
                ((TextView) view).setTextSize(Float.parseFloat(coordinate.getFontOrCorner()));
            } else if (view.getBackground() instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
                if (view != this.f5783u && view != this.f5784v) {
                    int iA = as.a(this.N, Float.parseFloat(coordinate.getFontOrCorner()));
                    if (z2) {
                        float f2 = iA;
                        gradientDrawable.setCornerRadii(new float[]{f2, f2, f2, f2, 0.0f, 0.0f, 0.0f, 0.0f});
                    } else {
                        gradientDrawable.setCornerRadius(iA);
                    }
                } else {
                    gradientDrawable.setCornerRadius(iArrA2[1]);
                }
            }
        }
        if (coordinate.getColor().equals("-1")) {
            return;
        }
        if (z3) {
            ((TextView) view).setTextColor(Color.parseColor(coordinate.getColor()));
        } else if (view != this.f5786x) {
            if (view.getBackground() instanceof GradientDrawable) {
                ((GradientDrawable) view.getBackground()).setColor(Color.parseColor(coordinate.getColor()));
            } else {
                view.setBackgroundColor(Color.parseColor(coordinate.getColor()));
            }
        }
    }

    private void b(int i2, int i3) {
        String adCoordinate = this.U.getAdCoordinate();
        a(this.f5781s, adCoordinate, i2, i3);
        a(this.f5786x, adCoordinate, i2, i3);
        int i4 = this.f5781s.getLayoutParams().width;
        int i5 = this.f5781s.getLayoutParams().height;
        c(i4, i5);
        d(i4, i5);
        e(i4, i5);
        f(i4, i5);
        g(i4, i5);
        h(i4, i5);
        aV();
        be();
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x027c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(android.view.View r18, java.lang.String r19, int r20, int r21) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 734
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.work.h.a.b(android.view.View, java.lang.String, int, int):void");
    }

    private int[] a(CoordinateBean coordinateBean, int i2, int i3, int[] iArr) {
        int iC;
        int iC2;
        int[] iArr2 = new int[2];
        if (coordinateBean.getWidth().equals("-1")) {
            iC = (i2 - iArr[0]) - iArr[2];
        } else {
            iC = c(coordinateBean.getWidth(), i2);
        }
        if (!coordinateBean.getScale().equals("-1") && !coordinateBean.getScale().equals("0")) {
            iC2 = (int) (iC / Float.parseFloat(coordinateBean.getScale()));
        } else if (coordinateBean.getHeight().equals("-1")) {
            iC2 = (i3 - iArr[1]) - iArr[3];
        } else {
            iC2 = c(coordinateBean.getHeight(), i3);
        }
        iArr2[0] = iC;
        iArr2[1] = iC2;
        return iArr2;
    }

    private int[] a(CoordinateBean coordinateBean, int i2, int i3) {
        int[] iArr = new int[4];
        String left = coordinateBean.getLeft();
        int iC = (left.equals("0%") || left.equals("0") || left.equals("-1")) ? 0 : c(left, i2);
        String top2 = coordinateBean.getTop();
        int iC2 = (top2.equals("0%") || top2.equals("0") || top2.equals("-1")) ? 0 : c(top2, i3);
        String right = coordinateBean.getRight();
        int iC3 = (right.equals("0%") || right.equals("0") || right.equals("-1")) ? 0 : c(right, i2);
        String bottom = coordinateBean.getBottom();
        int iC4 = (bottom.equals("0%") || bottom.equals("0") || bottom.equals("-1")) ? 0 : c(bottom, i3);
        iArr[0] = iC;
        iArr[1] = iC2;
        iArr[2] = iC3;
        iArr[3] = iC4;
        return iArr;
    }

    public void a(View view) {
        a(view, "", 30, (am.a) null);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void a(View view, final String str, int i2, final am.a aVar) {
        final int iA = as.a(this.N, i2);
        final boolean z2 = view == this.f5779q;
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.beizi.fusion.work.h.a.4

            /* renamed from: a, reason: collision with root package name */
            float f5795a;

            /* renamed from: b, reason: collision with root package name */
            float f5796b;

            /* renamed from: c, reason: collision with root package name */
            float f5797c;

            /* renamed from: d, reason: collision with root package name */
            float f5798d;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                am.a aVar2;
                am.a aVar3;
                am.a aVar4;
                am.a aVar5;
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.f5795a = motionEvent.getX();
                    this.f5796b = motionEvent.getY();
                    this.f5797c = motionEvent.getX();
                    this.f5798d = motionEvent.getY();
                    if (z2) {
                        a.this.f5779q.onTouchEvent(motionEvent);
                    } else {
                        a.this.f5781s.dispatchTouchEvent(motionEvent);
                    }
                } else if (action == 1) {
                    ac.b("SlideClickUtil", "mCurPosX = " + this.f5797c + ",mCurPosY = " + this.f5798d + ",mPosX = " + this.f5795a + ",mPosY = " + this.f5796b);
                    float f2 = this.f5798d;
                    float f3 = this.f5796b;
                    float f4 = f2 - f3;
                    int i3 = iA;
                    if (f4 > i3) {
                        if (!TextUtils.isEmpty(a.this.U.getScrollCoordinate()) && !a.this.U.getScrollCoordinate().equals("-1:-1:-1:-1:-1:-1:-1:-1:-1")) {
                            a.this.c(z2);
                        } else if (ScrollClickView.DIR_DOWN.equalsIgnoreCase(str) && (aVar5 = aVar) != null) {
                            aVar5.c_();
                        }
                    } else if (f3 - f2 <= i3) {
                        float f5 = this.f5795a;
                        float f6 = this.f5797c;
                        if (f5 - f6 > i3) {
                            if ("left".equalsIgnoreCase(str) && (aVar3 = aVar) != null) {
                                aVar3.c_();
                            }
                        } else if (f6 - f5 > i3) {
                            if ("right".equalsIgnoreCase(str) && (aVar2 = aVar) != null) {
                                aVar2.c_();
                            }
                        } else if (z2) {
                            a.this.f5779q.onTouchEvent(motionEvent);
                        } else {
                            a.this.f5781s.dispatchTouchEvent(motionEvent);
                        }
                    } else if ("up".equalsIgnoreCase(str) && (aVar4 = aVar) != null) {
                        aVar4.c_();
                    }
                } else if (action == 2) {
                    this.f5797c = motionEvent.getX();
                    this.f5798d = motionEvent.getY();
                }
                return true;
            }
        });
    }
}
