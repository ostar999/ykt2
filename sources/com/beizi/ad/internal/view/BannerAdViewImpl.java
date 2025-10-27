package com.beizi.ad.internal.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.beizi.ad.R;
import com.beizi.ad.a.a.i;
import com.beizi.ad.internal.animation.Animator;
import com.beizi.ad.internal.animation.TransitionDirection;
import com.beizi.ad.internal.animation.TransitionType;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.network.a;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.WebviewUtil;
import com.beizi.ad.internal.video.AdVideoView;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class BannerAdViewImpl extends AdViewImpl {
    private c A;
    private a B;

    /* renamed from: m, reason: collision with root package name */
    protected boolean f4608m;

    /* renamed from: n, reason: collision with root package name */
    protected int f4609n;

    /* renamed from: o, reason: collision with root package name */
    protected int f4610o;

    /* renamed from: p, reason: collision with root package name */
    private int f4611p;

    /* renamed from: q, reason: collision with root package name */
    private boolean f4612q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f4613r;

    /* renamed from: s, reason: collision with root package name */
    private BroadcastReceiver f4614s;

    /* renamed from: t, reason: collision with root package name */
    private boolean f4615t;

    /* renamed from: u, reason: collision with root package name */
    private boolean f4616u;

    /* renamed from: v, reason: collision with root package name */
    private boolean f4617v;

    /* renamed from: w, reason: collision with root package name */
    private Animator f4618w;

    /* renamed from: x, reason: collision with root package name */
    private boolean f4619x;

    /* renamed from: y, reason: collision with root package name */
    private AdWebView f4620y;

    /* renamed from: z, reason: collision with root package name */
    private boolean f4621z;

    /* renamed from: com.beizi.ad.internal.view.BannerAdViewImpl$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4623a;

        static {
            int[] iArr = new int[a.values().length];
            f4623a = iArr;
            try {
                iArr[a.TOP_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4623a[a.TOP_CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4623a[a.TOP_RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4623a[a.CENTER_LEFT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4623a[a.CENTER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4623a[a.CENTER_RIGHT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4623a[a.BOTTOM_LEFT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f4623a[a.BOTTOM_CENTER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f4623a[a.BOTTOM_RIGHT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public enum a {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        CENTER_LEFT,
        CENTER,
        CENTER_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT;

        public int a() {
            switch (AnonymousClass2.f4623a[ordinal()]) {
                case 1:
                    return 51;
                case 2:
                    return 49;
                case 3:
                    return 53;
                case 4:
                    return 19;
                case 5:
                default:
                    return 17;
                case 6:
                    return 21;
                case 7:
                    return 83;
                case 8:
                    return 81;
                case 9:
                    return 85;
            }
        }
    }

    public class b implements Animation.AnimationListener {

        /* renamed from: b, reason: collision with root package name */
        private final c f4635b;

        /* renamed from: c, reason: collision with root package name */
        private final Animator f4636c;

        public b(c cVar, Animator animator) {
            this.f4635b = cVar;
            this.f4636c = animator;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            animation.setAnimationListener(null);
            final c cVar = this.f4635b;
            final Animator animator = this.f4636c;
            if (cVar == null || animator == null) {
                return;
            }
            cVar.getView().getHandler().post(new Runnable() { // from class: com.beizi.ad.internal.view.BannerAdViewImpl.b.1
                @Override // java.lang.Runnable
                public void run() {
                    animator.clearAnimation();
                    cVar.destroy();
                    animator.setAnimation();
                }
            });
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }
    }

    public BannerAdViewImpl(Context context) {
        super(context);
        this.f4621z = true;
    }

    private void j() {
        this.f4612q = false;
        this.f4611p = -1;
        this.f4613r = false;
        this.f4619x = true;
    }

    private void k() {
        if (this.f4614s != null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        this.f4614s = new BroadcastReceiver() { // from class: com.beizi.ad.internal.view.BannerAdViewImpl.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                    BannerAdViewImpl.this.h();
                    HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.screen_off_stop));
                    return;
                }
                if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                    boolean z2 = true;
                    if (BannerAdViewImpl.this.f4611p > 0) {
                        BannerAdViewImpl.this.g();
                    } else if (BannerAdViewImpl.this.f4613r) {
                        BannerAdViewImpl.this.h();
                        BannerAdViewImpl.this.g();
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.screen_on_start));
                    }
                }
            }
        };
        try {
            i.c("BeiZisAd", "before registerReceiver");
            getContext().registerReceiver(this.f4614s, intentFilter);
        } catch (Throwable unused) {
            i.c("BeiZisAd", "ignore error");
        }
    }

    private void l() {
        if (this.f4611p > 0) {
            k();
        }
    }

    private void m() {
        StringBuilder sb = new StringBuilder();
        sb.append("enter dismantleBroadcast mReceiver == null ? ");
        sb.append(this.f4614s == null);
        i.c("BeiZisAd", sb.toString());
        if (this.f4614s == null) {
            return;
        }
        try {
            getContext().unregisterReceiver(this.f4614s);
            i.c("BeiZisAd", "after unregisterReceiver");
        } catch (IllegalArgumentException unused) {
            i.c("BeiZisAd", "got IllegalArgumentException");
        }
        this.f4614s = null;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void activityOnDestroy() {
        c cVar = this.A;
        if (cVar != null) {
            cVar.onDestroy();
            this.A = null;
        }
        i.c("BeiZisAd", "enter activityOnDestroy before dismantleBroadcast");
        m();
        if (this.mAdFetcher != null) {
            h();
        }
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void activityOnPause() {
        c cVar = this.A;
        if (cVar != null) {
            cVar.onPause();
        }
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void activityOnResume() {
        c cVar = this.A;
        if (cVar != null) {
            cVar.onResume();
        }
    }

    @Override // com.beizi.ad.AdLifeControl
    public void cancel() {
        com.beizi.ad.internal.c cVar = this.mAdFetcher;
        if (cVar != null) {
            cVar.a();
        }
        try {
            com.beizi.ad.a.b.a(getContext()).d();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void clickArea() {
        AdWebView adWebView = this.f4620y;
        if (adWebView != null) {
            adWebView.handleClickView(null, System.currentTimeMillis(), System.currentTimeMillis() + 10);
        }
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public boolean d() {
        return true;
    }

    public void disableFullClick(View.OnTouchListener onTouchListener) {
        AdWebView adWebView = this.f4620y;
        if (adWebView != null) {
            adWebView.setOnTouchListener(onTouchListener);
        }
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public boolean e() {
        return false;
    }

    @SuppressLint({"NewApi"})
    public void expandToFitScreenWidth(int i2, int i3, c cVar) {
        Display defaultDisplay = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int i4 = point.x;
        int iFloor = (int) Math.floor(i3 * (i4 / i2));
        this.f4609n = getLayoutParams().height;
        this.f4610o = getLayoutParams().width;
        if (getLayoutParams().width > 0 || getLayoutParams().width == -2) {
            getLayoutParams().width = i4;
        }
        getLayoutParams().height = iFloor;
        View view = cVar.getView();
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        } else {
            view.getLayoutParams().width = -1;
            view.getLayoutParams().height = -1;
        }
        view.invalidate();
        this.f4608m = true;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void f() {
    }

    public void g() {
        if (this.f4612q) {
            return;
        }
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.start));
        this.mAdFetcher.b();
        this.f4612q = true;
    }

    public a getAdAlignment() {
        if (this.B == null) {
            this.B = a.CENTER;
        }
        return this.B;
    }

    public int getAdHeight() {
        HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.get_height, this.f4481h.e()));
        return this.f4481h.e();
    }

    public int getAdWidth() {
        HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.get_width, this.f4481h.d()));
        return this.f4481h.d();
    }

    public int getAutoRefreshInterval() {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.get_period, this.f4611p));
        return this.f4611p;
    }

    public boolean getExpandsToFitScreenWidth() {
        return this.f4615t;
    }

    @Override // com.beizi.ad.internal.a
    public l getMediaType() {
        return this.f4474a != null ? l.SPLASH : l.BANNER;
    }

    public boolean getResizeAdToFitContainer() {
        return this.f4616u;
    }

    public boolean getShouldReloadOnResume() {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.get_should_resume, this.f4613r));
        return this.f4613r;
    }

    public TransitionDirection getTransitionDirection() {
        return this.f4618w.getTransitionDirection();
    }

    public long getTransitionDuration() {
        return this.f4618w.getTransitionDuration();
    }

    public TransitionType getTransitionType() {
        return this.f4618w.getTransitionType();
    }

    public void h() {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.stop));
        this.mAdFetcher.a();
        this.f4612q = false;
    }

    public void i() {
        this.f4608m = false;
        if (getLayoutParams() != null) {
            getLayoutParams().height = this.f4609n;
            getLayoutParams().width = this.f4610o;
        }
    }

    public boolean isAutoRefresh() {
        return this.f4621z;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public boolean loadAd(a.C0055a c0055a) {
        if (!super.loadAd(c0055a)) {
            return false;
        }
        this.f4612q = true;
        return true;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.f4477d) {
            this.f4477d = false;
            return;
        }
        if (!this.f4617v || z2) {
            com.beizi.ad.internal.g gVarA = com.beizi.ad.internal.g.a();
            int iH = (int) (((i4 - i2) / gVarA.h()) + 0.5f);
            int i6 = (int) (((i5 - i3) / gVarA.i()) + 0.5f);
            if (iH < this.f4481h.d() || (i6 < this.f4481h.e() && iH > 0 && i6 > 0)) {
                HaoboLog.e(HaoboLog.baseLogTag, HaoboLog.getString(R.string.adsize_too_big, iH, i6, this.f4481h.d(), this.f4481h.e()));
                c();
                com.beizi.ad.internal.c cVar = this.mAdFetcher;
                if (cVar != null) {
                    cVar.a();
                    return;
                }
                return;
            }
            this.f4481h.d(iH);
            this.f4481h.e(i6);
            if (!this.f4617v) {
                c();
            }
            this.f4617v = true;
        }
        if (this.f4612q) {
            k();
            if (this.f4613r) {
                g();
            }
        }
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.onWindowVisibilityChanged(i2);
        if (i2 != 0) {
            i.c("BeiZisAd", "enter onWindowVisibilityChanged before dismantleBroadcast");
            m();
            HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.hidden));
            if (this.mAdFetcher != null && this.f4612q) {
                h();
            }
            if (getChildAt(0) instanceof WebView) {
                WebviewUtil.onPause((WebView) getChildAt(0));
                return;
            }
            return;
        }
        k();
        HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.unhidden));
        if ((this.f4612q || this.f4613r || this.f4611p > 0) && !this.f4479f && !this.f4477d && !a() && this.mAdFetcher != null) {
            g();
        }
        this.f4479f = false;
        if (getChildAt(0) instanceof WebView) {
            WebviewUtil.onResume((WebView) getChildAt(0));
        }
    }

    public void resetContainerIfNeeded() {
        if (this.f4608m) {
            i();
        }
    }

    @SuppressLint({"NewApi"})
    public void resizeWebViewToFitContainer(int i2, int i3, c cVar) {
        int measuredWidth = getWidth() <= 0 ? getMeasuredWidth() : getWidth();
        int measuredHeight = getHeight() <= 0 ? getMeasuredHeight() : getHeight();
        if (measuredHeight <= 0 || measuredWidth <= 0) {
            HaoboLog.w(HaoboLog.baseLogTag, "Unable to resize ad to fit container because of failure to obtain the container size.");
            return;
        }
        float f2 = i2 / measuredWidth;
        float f3 = i3 / measuredHeight;
        View view = cVar.getView();
        if (f2 < f3) {
            measuredWidth = (i2 * measuredHeight) / i3;
            if (view instanceof WebView) {
                ((WebView) view).setInitialScale((int) Math.ceil((measuredHeight * 100) / i3));
            }
        } else {
            measuredHeight = (i3 * measuredWidth) / i2;
            if (view instanceof WebView) {
                ((WebView) view).setInitialScale((int) Math.ceil((measuredWidth * 100) / i2));
            }
        }
        if (view.getLayoutParams() == null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(measuredWidth, measuredHeight);
            layoutParams.gravity = 17;
            view.setLayoutParams(layoutParams);
        } else {
            view.getLayoutParams().width = measuredWidth;
            view.getLayoutParams().height = measuredHeight;
            ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity = 17;
        }
        view.invalidate();
    }

    public void setAdAlignment(a aVar) {
        this.B = aVar;
    }

    public void setAdSize(int i2, int i3) {
        HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.set_size, i2, i3));
        this.f4481h.b(i2);
        this.f4481h.c(i3);
    }

    public void setAutoRefresh(boolean z2) {
        this.f4621z = z2;
    }

    public void setAutoRefreshInterval(int i2) {
        if (i2 > 0) {
            this.f4611p = Math.max(10000, i2);
        } else {
            this.f4611p = i2;
        }
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.set_period, this.f4611p));
        com.beizi.ad.internal.c cVar = this.mAdFetcher;
        if (cVar != null) {
            cVar.a(this.f4611p);
        }
    }

    public void setExpandsToFitScreenWidth(boolean z2) {
        this.f4615t = z2;
    }

    public void setResizeAdToFitContainer(boolean z2) {
        this.f4616u = z2;
    }

    public void setScrollClick(View.OnTouchListener onTouchListener) {
        AdWebView adWebView = this.f4620y;
        if (adWebView != null) {
            adWebView.setOnTouchListener(onTouchListener);
        }
    }

    public void setShouldReloadOnResume(boolean z2) {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.set_should_resume, z2));
        this.f4613r = z2;
    }

    public void setTransitionDirection(TransitionDirection transitionDirection) {
        this.f4618w.setTransitionDirection(transitionDirection);
    }

    public void setTransitionDuration(long j2) {
        this.f4618w.setTransitionDuration(j2);
    }

    public void setTransitionType(TransitionType transitionType) {
        this.f4618w.setTransitionType(transitionType);
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void a(Context context, AttributeSet attributeSet) {
        this.f4611p = -1;
        this.f4608m = false;
        this.f4615t = false;
        this.f4616u = false;
        this.f4617v = false;
        this.f4618w = new Animator(getContext(), TransitionType.NONE, TransitionDirection.UP, 500L);
        View view = (View) getParent();
        if (view != null) {
            int measuredHeight = view.getMeasuredHeight();
            int measuredHeight2 = view.getMeasuredHeight();
            com.beizi.ad.internal.g gVarA = com.beizi.ad.internal.g.a();
            int i2 = (int) ((measuredHeight / gVarA.i()) + 0.5f);
            this.f4481h.d((int) ((measuredHeight2 / gVarA.h()) + 0.5f));
            this.f4481h.e(i2);
        }
        super.a(context, attributeSet);
        l();
        this.f4481h.a(this.f4474a != null ? l.SPLASH : l.BANNER);
        this.mAdFetcher.a(this.f4611p);
        if (this.f4619x) {
            this.mAdFetcher.b();
            this.f4612q = true;
        }
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void b(Context context, AttributeSet attributeSet) {
        j();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AdView);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        HaoboLog.v(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.found_n_in_xml, indexCount));
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R.styleable.AdView_adUnitId) {
                setAdUnitId(typedArrayObtainStyledAttributes.getString(index));
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.placement_id, typedArrayObtainStyledAttributes.getString(index)));
            } else if (index == R.styleable.AdView_auto_refresh_interval) {
                int i3 = typedArrayObtainStyledAttributes.getInt(index, -1);
                setAutoRefreshInterval(i3);
                if (i3 <= 0) {
                    this.f4619x = true;
                }
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.xml_set_period, i3));
            } else if (index == R.styleable.AdView_test) {
                com.beizi.ad.internal.g.a().f4181c = typedArrayObtainStyledAttributes.getBoolean(index, false);
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.xml_set_test, com.beizi.ad.internal.g.a().f4181c));
            } else if (index == R.styleable.AdView_adSize) {
                String string = typedArrayObtainStyledAttributes.getString(index);
                com.beizi.ad.a aVar = null;
                if (string != null && !string.isEmpty()) {
                    try {
                        aVar = (com.beizi.ad.a) com.beizi.ad.a.class.getDeclaredField(string).get(null);
                    } catch (Exception unused) {
                    }
                }
                if (aVar == null) {
                    aVar = com.beizi.ad.a.f3669g;
                }
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.xml_ad_size, aVar.toString()));
                setAdSize(aVar.b(), aVar.a());
            } else if (index == R.styleable.AdView_should_reload_on_resume) {
                setShouldReloadOnResume(typedArrayObtainStyledAttributes.getBoolean(index, false));
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.xml_set_should_reload, this.f4613r));
            } else if (index == R.styleable.AdView_opens_native_browser) {
                setOpensNativeBrowser(typedArrayObtainStyledAttributes.getBoolean(index, false));
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.xml_set_opens_native_browser, getOpensNativeBrowser()));
            } else if (index == R.styleable.AdView_expands_to_fit_screen_width) {
                setExpandsToFitScreenWidth(typedArrayObtainStyledAttributes.getBoolean(index, false));
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.xml_set_expands_to_full_screen_width, this.f4615t));
            } else if (index == R.styleable.AdView_resize_ad_to_fit_container) {
                setResizeAdToFitContainer(typedArrayObtainStyledAttributes.getBoolean(index, false));
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.xml_resize_ad_to_fit_container, this.f4616u));
            } else if (index == R.styleable.AdView_show_loading_indicator) {
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.show_loading_indicator_xml));
                setShowLoadingIndicator(typedArrayObtainStyledAttributes.getBoolean(index, true));
            } else if (index == R.styleable.AdView_transition_type) {
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.transition_type));
                setTransitionType(TransitionType.getTypeForInt(typedArrayObtainStyledAttributes.getInt(index, 0)));
            } else if (index == R.styleable.AdView_transition_direction) {
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.transition_direction));
                setTransitionDirection(TransitionDirection.getDirectionForInt(typedArrayObtainStyledAttributes.getInt(index, 0)));
            } else if (index == R.styleable.AdView_transition_duration) {
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.transition_duration));
                setTransitionDuration(typedArrayObtainStyledAttributes.getInt(index, 1000));
            } else if (index == R.styleable.AdView_load_landing_page_in_background) {
                setLoadsInBackground(typedArrayObtainStyledAttributes.getBoolean(index, true));
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(R.string.xml_load_landing_page_in_background, this.f4480g));
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public BannerAdViewImpl(Context context, ViewGroup viewGroup, View view) {
        super(context, viewGroup, view);
        this.f4621z = true;
    }

    public void clickArea(com.beizi.ad.c.c cVar, int i2) {
        AdWebView adWebView = this.f4620y;
        if (adWebView != null) {
            adWebView.handleClickView(cVar, System.currentTimeMillis(), System.currentTimeMillis() + 10, i2);
        }
    }

    public BannerAdViewImpl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4621z = true;
    }

    public BannerAdViewImpl(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f4621z = true;
    }

    public BannerAdViewImpl(Context context, int i2) {
        super(context);
        this.f4621z = true;
        setAutoRefreshInterval(i2);
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void a(c cVar) {
        int refreshInterval;
        if (cVar != null && !cVar.failed() && cVar.getView() != null) {
            if (this.f4476c == cVar) {
                return;
            }
            this.A = cVar;
            if (getTransitionType() == TransitionType.NONE) {
                removeAllViews();
                c cVar2 = this.f4476c;
                if (cVar2 != null) {
                    cVar2.destroy();
                }
                View view = cVar.getView();
                addView(view);
                if (view instanceof AdWebView) {
                    i.a("BeiZisAd", "set mAdWebView");
                    this.f4620y = (AdWebView) view;
                }
                if (view.getLayoutParams() != null) {
                    ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity = getAdAlignment().a();
                }
                if (getMediaType() != l.SPLASH || (cVar.getView() instanceof AdVideoView)) {
                    cVar.visible();
                }
            } else {
                if (cVar.getView().getLayoutParams() != null) {
                    ((FrameLayout.LayoutParams) cVar.getView().getLayoutParams()).gravity = getAdAlignment().a();
                    this.f4618w.setLayoutParams(cVar.getView().getLayoutParams());
                }
                if (getChildCount() != 0 && indexOfChild(this.f4618w) != -1) {
                    if (getMediaType() != l.SPLASH || (cVar.getView() instanceof AdVideoView)) {
                        cVar.visible();
                    }
                    this.f4618w.addView(cVar.getView());
                    this.f4618w.showNext();
                } else {
                    removeAllViews();
                    if (getMediaType() != l.SPLASH || (cVar.getView() instanceof AdVideoView)) {
                        cVar.visible();
                    }
                    addView(this.f4618w, 0);
                    this.f4618w.addView(cVar.getView());
                }
                c cVar3 = this.f4476c;
                if (cVar3 != null) {
                    if (cVar3.getView().getAnimation() != null) {
                        cVar3.getView().getAnimation().setAnimationListener(new b(cVar3, this.f4618w));
                    } else {
                        cVar3.destroy();
                    }
                }
            }
            b();
            if (this.f4474a == null && (refreshInterval = cVar.getRefreshInterval()) > 0 && this.f4621z) {
                setAutoRefreshInterval(refreshInterval * 1000);
            }
            this.f4476c = cVar;
            return;
        }
        getAdListener().onAdFailedToLoad(5);
        HaoboLog.e(HaoboLog.baseLogTag, "Loaded an ad with an invalid displayable");
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void a(com.beizi.ad.internal.b.e eVar) {
        a((c) eVar);
    }
}
