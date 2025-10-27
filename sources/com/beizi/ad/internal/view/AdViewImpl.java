package com.beizi.ad.internal.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.beizi.ad.AdActivity;
import com.beizi.ad.AdLifeControl;
import com.beizi.ad.AdListener;
import com.beizi.ad.AppEventListener;
import com.beizi.ad.R;
import com.beizi.ad.RewardedVideoAdListener;
import com.beizi.ad.c.e;
import com.beizi.ad.internal.i;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.network.a;
import com.beizi.ad.internal.o;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.SPUtils;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.utilities.ViewUtil;
import com.beizi.ad.internal.utilities.WebviewUtil;
import com.beizi.ad.internal.video.AdVideoView;
import com.beizi.ad.internal.view.AdWebView;
import com.beizi.ad.internal.view.f;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public abstract class AdViewImpl extends FrameLayout implements AdLifeControl, com.beizi.ad.internal.a {
    private static FrameLayout T;
    private static f U;
    private static AdWebView.b V;
    private AppCompatTextView A;
    private ImageView B;
    private CountDownTimer C;
    private AppCompatImageView D;
    private long E;
    private GestureDetector F;
    private float G;
    private float H;
    private float I;
    private float J;
    private FrameLayout K;
    private FrameLayout L;
    private String M;
    private String N;
    private String O;
    private String P;
    private String Q;
    private AdWebView R;
    private boolean S;

    /* renamed from: a, reason: collision with root package name */
    protected ViewGroup f4474a;

    /* renamed from: b, reason: collision with root package name */
    protected a f4475b;
    public int bottomPadding;

    /* renamed from: c, reason: collision with root package name */
    protected com.beizi.ad.internal.view.c f4476c;
    public int clickCount;

    /* renamed from: d, reason: collision with root package name */
    protected boolean f4477d;

    /* renamed from: e, reason: collision with root package name */
    protected boolean f4478e;

    /* renamed from: f, reason: collision with root package name */
    protected boolean f4479f;

    /* renamed from: g, reason: collision with root package name */
    protected boolean f4480g;

    /* renamed from: h, reason: collision with root package name */
    protected com.beizi.ad.internal.d f4481h;

    /* renamed from: i, reason: collision with root package name */
    protected a.C0055a f4482i;

    /* renamed from: j, reason: collision with root package name */
    protected boolean f4483j;

    /* renamed from: k, reason: collision with root package name */
    protected boolean f4484k;

    /* renamed from: l, reason: collision with root package name */
    int f4485l;
    public int leftPadding;
    public int loadCount;

    /* renamed from: m, reason: collision with root package name */
    private View f4486m;
    public com.beizi.ad.internal.c mAdFetcher;

    /* renamed from: n, reason: collision with root package name */
    private int f4487n;

    /* renamed from: o, reason: collision with root package name */
    private int f4488o;

    /* renamed from: p, reason: collision with root package name */
    private String f4489p;

    /* renamed from: q, reason: collision with root package name */
    private AdListener f4490q;

    /* renamed from: r, reason: collision with root package name */
    private RewardedVideoAdListener f4491r;
    public int rightPadding;

    /* renamed from: s, reason: collision with root package name */
    private AppEventListener f4492s;
    public ServerResponse serverResponse;

    /* renamed from: t, reason: collision with root package name */
    private c f4493t;
    public int topPadding;

    /* renamed from: u, reason: collision with root package name */
    private final Handler f4494u;

    /* renamed from: v, reason: collision with root package name */
    private b f4495v;

    /* renamed from: w, reason: collision with root package name */
    private boolean f4496w;

    /* renamed from: x, reason: collision with root package name */
    private boolean f4497x;

    /* renamed from: y, reason: collision with root package name */
    private boolean f4498y;

    /* renamed from: z, reason: collision with root package name */
    private AppCompatTextView f4499z;

    /* renamed from: com.beizi.ad.internal.view.AdViewImpl$11, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass11 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4503a;

        static {
            int[] iArr = new int[f.a.values().length];
            f4503a = iArr;
            try {
                iArr[f.a.bottom_center.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4503a[f.a.bottom_left.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4503a[f.a.bottom_right.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4503a[f.a.center.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4503a[f.a.top_center.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4503a[f.a.top_left.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4503a[f.a.top_right.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public EnumC0059a f4538a = EnumC0059a.UNCHANGE;

        /* renamed from: b, reason: collision with root package name */
        public boolean f4539b = false;

        /* renamed from: com.beizi.ad.internal.view.AdViewImpl$a$a, reason: collision with other inner class name */
        public enum EnumC0059a {
            UNCHANGE,
            STATE_PREPARE_CHANGE,
            STATE_BACKGROUND,
            FINISHCLOSE
        }

        public boolean a() {
            return this.f4539b;
        }

        public void b() {
            this.f4538a = EnumC0059a.UNCHANGE;
        }

        public EnumC0059a c() {
            return this.f4538a;
        }

        public void a(boolean z2) {
            this.f4539b = z2;
        }

        public synchronized void a(EnumC0059a enumC0059a) {
            EnumC0059a enumC0059a2 = EnumC0059a.STATE_PREPARE_CHANGE;
            if (enumC0059a == enumC0059a2 && this.f4538a == EnumC0059a.UNCHANGE) {
                this.f4538a = enumC0059a2;
            }
            EnumC0059a enumC0059a3 = EnumC0059a.STATE_BACKGROUND;
            if (enumC0059a == enumC0059a3 && this.f4538a == enumC0059a2) {
                this.f4538a = enumC0059a3;
            }
            EnumC0059a enumC0059a4 = EnumC0059a.FINISHCLOSE;
            if (enumC0059a == enumC0059a4 && this.f4538a == enumC0059a3 && this.f4539b) {
                this.f4538a = enumC0059a4;
            }
        }
    }

    public class b implements com.beizi.ad.internal.b {

        /* renamed from: b, reason: collision with root package name */
        private Handler f4546b;

        /* renamed from: c, reason: collision with root package name */
        private com.beizi.ad.internal.network.b f4547c;

        public b(Handler handler) {
            this.f4546b = handler;
        }

        @Override // com.beizi.ad.internal.b
        public void b() {
            this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.8
                @Override // java.lang.Runnable
                public void run() {
                    AdViewImpl adViewImpl = AdViewImpl.this;
                    if (adViewImpl.f4483j) {
                        if (adViewImpl.f4491r != null) {
                            AdViewImpl.this.f4491r.onRewardedVideoAdClosed();
                        }
                    } else if (adViewImpl.f4490q != null) {
                        AdViewImpl.this.f4490q.onAdClosed();
                        AdViewImpl.this.f4475b.b();
                    }
                }
            });
        }

        @Override // com.beizi.ad.internal.b
        public void c() {
            this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.7
                @Override // java.lang.Runnable
                public void run() {
                    AdViewImpl adViewImpl = AdViewImpl.this;
                    if (adViewImpl.f4483j) {
                        if (adViewImpl.f4491r != null) {
                            AdViewImpl.this.f4491r.onRewardedVideoAdOpened();
                        }
                    } else if (adViewImpl.f4490q != null) {
                        AdViewImpl.this.f4490q.onAdOpened();
                    }
                }
            });
        }

        @Override // com.beizi.ad.internal.b
        public void d() {
            this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.9
                @Override // java.lang.Runnable
                public void run() {
                    AdViewImpl adViewImpl = AdViewImpl.this;
                    if (adViewImpl.f4483j || adViewImpl.f4490q == null) {
                        return;
                    }
                    AdViewImpl.this.f4475b.a(a.EnumC0059a.STATE_PREPARE_CHANGE);
                    Log.i("BeiZisAd", "enter BeiZi ad click");
                    AdViewImpl.this.f4490q.onAdClicked();
                }
            });
        }

        @Override // com.beizi.ad.internal.b
        public void e() {
            this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.10
                @Override // java.lang.Runnable
                public void run() {
                    if (AdViewImpl.this.f4490q != null) {
                        AdViewImpl.this.f4490q.onAdRequest();
                    }
                }
            });
        }

        @Override // com.beizi.ad.internal.b
        public void f() {
            this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.2
                @Override // java.lang.Runnable
                public void run() {
                    AdViewImpl adViewImpl = AdViewImpl.this;
                    if (!adViewImpl.f4483j || adViewImpl.f4491r == null) {
                        return;
                    }
                    AdViewImpl.this.f4491r.onRewardedVideoStarted();
                }
            });
        }

        @Override // com.beizi.ad.internal.b
        public void a(final com.beizi.ad.internal.network.b bVar) {
            this.f4547c = bVar;
            if (bVar.a().equals(l.BANNER) || bVar.a().equals(l.INTERSTITIAL) || bVar.a().equals(l.SPLASH)) {
                this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AdViewImpl.this.setCreativeWidth(bVar.c().getCreativeWidth());
                        AdViewImpl.this.setCreativeHeight(bVar.c().getCreativeHeight());
                        AdViewImpl.this.setAdExtInfo(bVar.e());
                        AdViewImpl.this.setPrice(bVar.f());
                        AdViewImpl.this.setAdId(bVar.g());
                        if (bVar.b()) {
                            try {
                                AdViewImpl.this.a((com.beizi.ad.internal.b.e) bVar.c());
                            } catch (ClassCastException unused) {
                                HaoboLog.e(HaoboLog.baseLogTag, "The SDK shouldn't fail downcasts to MediatedDisplayable in AdView");
                            }
                        } else {
                            AdViewImpl.this.a(bVar.c());
                        }
                        AdViewImpl adViewImpl = AdViewImpl.this;
                        if (adViewImpl.f4483j) {
                            if (adViewImpl.f4491r != null) {
                                AdViewImpl.this.f4491r.onRewardedVideoAdLoaded();
                            }
                        } else {
                            if (adViewImpl.f4490q == null || bVar.a().equals(l.SPLASH)) {
                                return;
                            }
                            Log.e("BeiZisAd", "enter BeiZi ad load");
                            AdViewImpl.this.f4490q.onAdLoaded();
                            if (bVar.d() != null) {
                                AdViewImpl.this.setLandingPageUrl(bVar.d().getLandingPageUrl());
                            }
                        }
                    }
                });
            } else {
                a(0);
            }
        }

        @Override // com.beizi.ad.internal.b
        public void a() {
            AdViewImpl.this.E = System.currentTimeMillis();
            this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.5
                @Override // java.lang.Runnable
                public void run() {
                    AdViewImpl adViewImpl = AdViewImpl.this;
                    if (adViewImpl.f4483j) {
                        if (adViewImpl.f4491r != null) {
                            AdViewImpl.this.f4491r.onRewardedVideoAdShown();
                        }
                    } else if (adViewImpl.f4490q != null) {
                        Log.e("BeiZisAd", "enter BeiZi ad show");
                        AdViewImpl.this.f4490q.onAdShown();
                    }
                }
            });
        }

        @Override // com.beizi.ad.internal.b
        public void a(final int i2) {
            this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.6
                @Override // java.lang.Runnable
                public void run() {
                    AdViewImpl adViewImpl = AdViewImpl.this;
                    if (adViewImpl.f4483j) {
                        if (adViewImpl.f4491r != null) {
                            AdViewImpl.this.f4491r.onRewardedVideoAdFailedToLoad(i2);
                        }
                    } else if (adViewImpl.f4490q != null) {
                        AdViewImpl.this.f4490q.onAdFailedToLoad(i2);
                    }
                }
            });
        }

        @Override // com.beizi.ad.internal.b
        public void a(final String str, final String str2) {
            this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.11
                @Override // java.lang.Runnable
                public void run() {
                    if (AdViewImpl.this.f4492s != null) {
                        AdViewImpl.this.f4492s.onAppEvent(str, str2);
                    }
                }
            });
        }

        @Override // com.beizi.ad.internal.b
        public void a(final String str, final int i2) {
            this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.3
                @Override // java.lang.Runnable
                public void run() {
                    AdViewImpl adViewImpl = AdViewImpl.this;
                    if (!adViewImpl.f4483j || adViewImpl.f4491r == null) {
                        return;
                    }
                    AdViewImpl.this.f4491r.onRewarded(new o(str, i2));
                }
            });
        }

        @Override // com.beizi.ad.internal.b
        public void a(final long j2) {
            this.f4546b.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.b.4
                @Override // java.lang.Runnable
                public void run() {
                    if (AdViewImpl.this.f4490q == null || b.this.f4547c == null || !b.this.f4547c.a().equals(l.SPLASH)) {
                        return;
                    }
                    AdViewImpl.this.f4490q.onAdTick(j2);
                }
            });
        }
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        public static final ArrayList<Pair<String, c>> f4566a = new ArrayList<>();
    }

    public AdViewImpl(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public static FrameLayout getMRAIDFullscreenContainer() {
        return T;
    }

    public static f getMRAIDFullscreenImplementation() {
        return U;
    }

    public static AdWebView.b getMRAIDFullscreenListener() {
        return V;
    }

    public static void setMRAIDFullscreenContainer(FrameLayout frameLayout) {
        T = frameLayout;
    }

    public static void setMRAIDFullscreenImplementation(f fVar) {
        U = fVar;
    }

    public static void setMRAIDFullscreenListener(AdWebView.b bVar) {
        V = bVar;
    }

    public abstract void a(com.beizi.ad.internal.b.e eVar);

    public abstract void a(com.beizi.ad.internal.view.c cVar);

    public abstract void activityOnDestroy();

    public abstract void activityOnPause();

    public abstract void activityOnResume();

    public void addBannerCloseBtn() {
        ViewUtil.removeChildFromParent(this.B);
        ImageView imageViewCreateImageCloseButton = ViewUtil.createImageCloseButton(getContext());
        this.B = imageViewCreateImageCloseButton;
        imageViewCreateImageCloseButton.setVisibility(0);
        this.B.setEnabled(true);
        this.B.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.view.AdViewImpl.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AdViewImpl.this.getAdDispatcher().b();
            }
        });
    }

    public void addCloseButton(int i2, int i3, int i4, final View view, final boolean z2) {
        final int i5;
        ViewUtil.removeChildFromParent(this.A);
        ViewUtil.removeChildFromParent(this.f4499z);
        CountDownTimer countDownTimer = this.C;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this.A = ViewUtil.createCloseButton(getContext(), this.leftPadding, this.topPadding, this.rightPadding, this.bottomPadding);
        int i6 = 0;
        if (i4 != -1) {
            this.f4499z = ViewUtil.createCountDown(getContext(), i4, this.leftPadding, this.topPadding, this.rightPadding, this.bottomPadding);
            if (i3 > 0) {
                this.A.setEnabled(false);
                i5 = i4 - i3;
            } else {
                if (i3 == -1) {
                    this.A.setVisibility(8);
                }
                i5 = 0;
            }
            CountDownTimer countDownTimer2 = new CountDownTimer(i4 * 1000, 50L) { // from class: com.beizi.ad.internal.view.AdViewImpl.3
                @Override // android.os.CountDownTimer
                public void onFinish() {
                    AdViewImpl.this.f4499z.setText("0");
                    if (!AdViewImpl.this.e()) {
                        AdViewImpl.this.f4475b.a(true);
                        if (AdViewImpl.this.f4475b.a() && (AdViewImpl.this.f4475b.c() == a.EnumC0059a.UNCHANGE || AdViewImpl.this.f4475b.c() == a.EnumC0059a.STATE_PREPARE_CHANGE)) {
                            AdViewImpl.this.getAdDispatcher().b();
                        }
                        if (AdViewImpl.this.f4474a == null) {
                            HaoboLog.e(HaoboLog.jsLogTag, "Should not close banner!");
                            return;
                        }
                        return;
                    }
                    if (!z2) {
                        AdViewImpl.this.getAdDispatcher().b();
                        Activity activity = (Activity) AdViewImpl.this.a(view);
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }
                        activity.finish();
                        return;
                    }
                    View view2 = view;
                    if (view2 instanceof AdWebView) {
                        if (((AdWebView) view2).loadAdBy(1)) {
                            ((com.beizi.ad.internal.a.b) ((InterstitialAdViewImpl) AdViewImpl.this).getAdImplementation()).g();
                        }
                    } else if ((view2 instanceof AdVideoView) && ((AdVideoView) view2).getAdWebView().loadAdBy(1)) {
                        ((com.beizi.ad.internal.a.b) ((InterstitialAdViewImpl) AdViewImpl.this).getAdImplementation()).g();
                    }
                }

                @Override // android.os.CountDownTimer
                public void onTick(long j2) {
                    int i7 = (int) ((j2 / 1000) + 1);
                    int i8 = i5;
                    if (i8 > 0 && i7 <= i8) {
                        AdViewImpl.this.A.setEnabled(true);
                    }
                    AdViewImpl.this.f4499z.setText(Integer.toString(i7));
                }
            };
            this.C = countDownTimer2;
            countDownTimer2.start();
        } else {
            if (i3 == -1 || i2 == -1) {
                if (i2 != -1) {
                    this.f4499z = ViewUtil.createCountDown(getContext(), i2, this.leftPadding, this.topPadding, this.rightPadding, this.bottomPadding);
                    CountDownTimer countDownTimer3 = new CountDownTimer(i2 * 1000, 50L) { // from class: com.beizi.ad.internal.view.AdViewImpl.5
                        @Override // android.os.CountDownTimer
                        public void onFinish() {
                            AdViewImpl.this.f4499z.setText("0");
                            AdViewImpl.this.f4499z.setVisibility(8);
                        }

                        @Override // android.os.CountDownTimer
                        public void onTick(long j2) {
                            AdViewImpl.this.f4499z.setText(Integer.toString((int) ((j2 / 1000) + 1)));
                        }
                    };
                    this.C = countDownTimer3;
                    countDownTimer3.start();
                    ViewParent parent = e() ? view.getParent() : getParent();
                    if (parent instanceof FrameLayout) {
                        ((FrameLayout) parent).addView(this.f4499z);
                        return;
                    }
                    return;
                }
                return;
            }
            this.f4499z = ViewUtil.createCountDown(getContext(), i2, this.leftPadding, this.topPadding, this.rightPadding, this.bottomPadding);
            if (i3 > 0) {
                this.A.setEnabled(false);
                i6 = i2 - i3;
            }
            final int i7 = i6;
            CountDownTimer countDownTimer4 = new CountDownTimer(i2 * 1000, 50L) { // from class: com.beizi.ad.internal.view.AdViewImpl.4
                @Override // android.os.CountDownTimer
                public void onFinish() {
                    AdViewImpl.this.f4499z.setText("0");
                    AdViewImpl.this.f4499z.setVisibility(8);
                }

                @Override // android.os.CountDownTimer
                public void onTick(long j2) {
                    int i8 = (int) ((j2 / 1000) + 1);
                    AdViewImpl.this.f4499z.setText(Integer.toString(i8));
                    int i9 = i7;
                    if (i9 <= 0 || i8 > i9) {
                        return;
                    }
                    AdViewImpl.this.A.setEnabled(true);
                }
            };
            this.C = countDownTimer4;
            countDownTimer4.start();
        }
        this.A.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.view.AdViewImpl.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (!AdViewImpl.this.e()) {
                    AdViewImpl.this.getAdDispatcher().b();
                    AdViewImpl.this.C.cancel();
                    if (AdViewImpl.this.f4474a != null) {
                        HaoboLog.e(HaoboLog.jsLogTag, "Should not close banner!");
                        return;
                    }
                    return;
                }
                if (!z2) {
                    AdViewImpl.this.getAdDispatcher().b();
                    Activity activity = (Activity) AdViewImpl.this.a(view);
                    if (activity == null || activity.isFinishing()) {
                        return;
                    }
                    activity.onBackPressed();
                    activity.finish();
                    return;
                }
                View view3 = view;
                if (view3 instanceof AdWebView) {
                    if (((AdWebView) view3).loadAdBy(1)) {
                        ((com.beizi.ad.internal.a.b) ((InterstitialAdViewImpl) AdViewImpl.this).getAdImplementation()).g();
                    }
                } else if ((view3 instanceof AdVideoView) && ((AdVideoView) view3).getAdWebView().loadAdBy(1)) {
                    ((com.beizi.ad.internal.a.b) ((InterstitialAdViewImpl) AdViewImpl.this).getAdImplementation()).g();
                }
            }
        });
        ViewParent parent2 = e() ? view.getParent() : getParent();
        if (parent2 instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) parent2;
            frameLayout.addView(this.A);
            frameLayout.addView(this.f4499z);
        }
    }

    public void addInterstitialCloseButton(int i2, int i3, final View view, final boolean z2) {
        ViewUtil.removeChildFromParent(this.f4499z);
        if (i3 != -1) {
            this.f4499z = ViewUtil.createInterstitialCountDown(getContext(), i3);
            final int i4 = i2 > 0 ? i3 - i2 : 0;
            CountDownTimer countDownTimer = new CountDownTimer(i3 * 1000, 50L) { // from class: com.beizi.ad.internal.view.AdViewImpl.16
                @Override // android.os.CountDownTimer
                public void onFinish() {
                    AdViewImpl.this.f4499z.setText("0");
                    if (!AdViewImpl.this.e()) {
                        AdViewImpl.this.getAdDispatcher().b();
                        if (AdViewImpl.this.f4474a == null) {
                            HaoboLog.e(HaoboLog.jsLogTag, "Should not close banner!");
                            return;
                        }
                        return;
                    }
                    if (!z2) {
                        AdViewImpl.this.getAdDispatcher().b();
                        Activity activity = (Activity) AdViewImpl.this.a(view);
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }
                        activity.finish();
                        return;
                    }
                    View view2 = view;
                    if (view2 instanceof AdWebView) {
                        if (((AdWebView) view2).loadAdBy(1)) {
                            ((com.beizi.ad.internal.a.b) ((InterstitialAdViewImpl) AdViewImpl.this).getAdImplementation()).g();
                        }
                    } else if ((view2 instanceof AdVideoView) && ((AdVideoView) view2).getAdWebView().loadAdBy(1)) {
                        ((com.beizi.ad.internal.a.b) ((InterstitialAdViewImpl) AdViewImpl.this).getAdImplementation()).g();
                    }
                }

                @Override // android.os.CountDownTimer
                public void onTick(long j2) {
                    AdViewImpl.this.f4499z.setText(Integer.toString((int) ((j2 / 1000) + 1)));
                }
            };
            this.C = countDownTimer;
            countDownTimer.start();
        } else {
            if (i2 == -1) {
                return;
            }
            this.f4499z = ViewUtil.createInterstitialCountDown(getContext(), i2);
            CountDownTimer countDownTimer2 = new CountDownTimer(i2 * 1000, 50L) { // from class: com.beizi.ad.internal.view.AdViewImpl.17
                @Override // android.os.CountDownTimer
                public void onFinish() {
                    AdViewImpl.this.f4499z.setText("");
                    AdViewImpl.this.f4499z.setBackgroundResource(R.mipmap.ad_close);
                    AdViewImpl.this.f4499z.setVisibility(0);
                    AdViewImpl.this.f4499z.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.view.AdViewImpl.17.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view2) {
                            if (AdViewImpl.this.e()) {
                                AnonymousClass17 anonymousClass17 = AnonymousClass17.this;
                                if (!z2) {
                                    AdViewImpl.this.getAdDispatcher().b();
                                    AnonymousClass17 anonymousClass172 = AnonymousClass17.this;
                                    Activity activity = (Activity) AdViewImpl.this.a(view);
                                    if (activity == null || activity.isFinishing()) {
                                        return;
                                    }
                                    activity.finish();
                                    return;
                                }
                                View view3 = view;
                                if (view3 instanceof AdWebView) {
                                    if (((AdWebView) view3).loadAdBy(1)) {
                                        ((com.beizi.ad.internal.a.b) ((InterstitialAdViewImpl) AdViewImpl.this).getAdImplementation()).g();
                                    }
                                } else if ((view3 instanceof AdVideoView) && ((AdVideoView) view3).getAdWebView().loadAdBy(1)) {
                                    ((com.beizi.ad.internal.a.b) ((InterstitialAdViewImpl) AdViewImpl.this).getAdImplementation()).g();
                                }
                            }
                        }
                    });
                }

                @Override // android.os.CountDownTimer
                public void onTick(long j2) {
                    AdViewImpl.this.f4499z.setText(Integer.toString((int) ((j2 / 1000) + 1)));
                }
            };
            this.C = countDownTimer2;
            countDownTimer2.start();
        }
        ViewParent parent = e() ? view.getParent() : getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).addView(this.f4499z);
        }
    }

    public void addMuteButton(final AdVideoView adVideoView, boolean z2) {
        ViewUtil.removeChildFromParent(this.D);
        AppCompatImageView appCompatImageViewCreateMuteButton = ViewUtil.createMuteButton(getContext(), z2);
        this.D = appCompatImageViewCreateMuteButton;
        appCompatImageViewCreateMuteButton.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.view.AdViewImpl.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AdViewImpl.this.D.setImageResource(adVideoView.toggleMute() ? R.drawable.voice_off : R.drawable.voice_on);
            }
        });
        ViewParent parent = e() ? adVideoView.getParent() : getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).addView(this.D);
        }
    }

    public void addSkipView(int i2, View view) {
        ViewUtil.removeChildFromParent(this.A);
        ViewUtil.removeChildFromParent(this.f4499z);
        CountDownTimer countDownTimer = this.C;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (i2 <= 0) {
            i2 = 5;
        }
        CountDownTimer countDownTimer2 = new CountDownTimer(i2 * 1000, 50L) { // from class: com.beizi.ad.internal.view.AdViewImpl.19
            @Override // android.os.CountDownTimer
            public void onFinish() {
                AdViewImpl.this.f4475b.a(true);
                if (AdViewImpl.this.f4475b.a() && (AdViewImpl.this.f4475b.c() == a.EnumC0059a.UNCHANGE || AdViewImpl.this.f4475b.c() == a.EnumC0059a.STATE_PREPARE_CHANGE)) {
                    AdViewImpl.this.getAdDispatcher().b();
                }
                if (AdViewImpl.this.f4474a == null) {
                    HaoboLog.e(HaoboLog.jsLogTag, "Should not close banner!");
                }
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                AdViewImpl.this.getAdDispatcher().a(j2);
            }
        };
        this.C = countDownTimer2;
        countDownTimer2.start();
        view.setVisibility(0);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.view.AdViewImpl.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                AdViewImpl.this.getAdDispatcher().b();
                AdViewImpl.this.C.cancel();
                if (AdViewImpl.this.f4474a != null) {
                    HaoboLog.e(HaoboLog.jsLogTag, "Should not close banner!");
                }
            }
        });
    }

    public abstract void b(Context context, AttributeSet attributeSet);

    public void clearAdRequest() {
        this.f4482i = null;
    }

    public void createAdLogo(ServerResponse.AdLogoInfo adLogoInfo, ServerResponse.AdLogoInfo adLogoInfo2) {
        ViewUtil.removeChildFromParent(this.K);
        ViewUtil.removeChildFromParent(this.L);
        if (!TextUtils.isEmpty(adLogoInfo.getAdurl())) {
            this.K = ViewUtil.createAdImageView(new MutableContextWrapper(getContext()), adLogoInfo);
        }
        if (TextUtils.isEmpty(adLogoInfo2.getAdurl())) {
            return;
        }
        this.L = ViewUtil.createLogoImageView(new MutableContextWrapper(getContext()), adLogoInfo2);
    }

    public abstract boolean d();

    public void destroy() {
        HaoboLog.d(HaoboLog.baseLogTag, "called destroy() on AdView");
        com.beizi.ad.internal.view.c cVar = this.f4476c;
        if (cVar != null) {
            cVar.destroy();
            this.f4476c = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.F.onTouchEvent(motionEvent);
        return super.dispatchTouchEvent(motionEvent);
    }

    public abstract boolean e();

    public abstract void f();

    public void finalize() {
        try {
            super.finalize();
        } catch (Throwable unused) {
        }
        com.beizi.ad.internal.c cVar = this.mAdFetcher;
        if (cVar != null) {
            cVar.a();
        }
    }

    public com.beizi.ad.internal.b getAdDispatcher() {
        return this.f4495v;
    }

    public String getAdId() {
        return this.O;
    }

    public AdListener getAdListener() {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.get_ad_listener));
        return this.f4490q;
    }

    public com.beizi.ad.internal.d getAdParameters() {
        return this.f4481h;
    }

    public a.C0055a getAdRequest() {
        return this.f4482i;
    }

    public com.beizi.ad.a getAdSize() {
        return new com.beizi.ad.a(this.f4487n, this.f4488o);
    }

    public String getAdUnitId() {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.get_placement_id, this.f4481h.c()));
        return this.f4481h.c();
    }

    public AppEventListener getAppEventListener() {
        return this.f4492s;
    }

    public c getBrowserStyle() {
        return this.f4493t;
    }

    public int getContainerHeight() {
        return this.f4481h.g();
    }

    public int getContainerWidth() {
        return this.f4481h.f();
    }

    public int getCreativeHeight() {
        return this.f4488o;
    }

    public int getCreativeWidth() {
        return this.f4487n;
    }

    public String getLandingPageUrl() {
        return this.N;
    }

    public boolean getLoadsInBackground() {
        return this.f4480g;
    }

    public String getMediationAdapterClassName() {
        return null;
    }

    public Handler getMyHandler() {
        return this.f4494u;
    }

    public boolean getOpensNativeBrowser() {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.get_opens_native_browser, this.f4481h.h()));
        return this.f4481h.h();
    }

    public String getPrice() {
        return this.M;
    }

    public RewardedVideoAdListener getRewaredVideoAdListener() {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.get_rewarded_video_ad_listener));
        return this.f4491r;
    }

    public boolean getShowLoadingIndicator() {
        return this.f4497x;
    }

    public ViewGroup getSplashParent() {
        return this.f4474a;
    }

    public void isLoadToShow(AdWebView adWebView) {
        this.R = adWebView;
        this.S = true;
        if (this.f4490q != null) {
            Log.e("BeiZisAd", "enter BeiZi ad load");
            this.f4490q.onAdLoaded();
        }
    }

    public boolean isLoaded() {
        return this.S;
    }

    public boolean isLoading() {
        return this.f4498y;
    }

    @Override // com.beizi.ad.internal.a
    public boolean isReadyToStart() {
        if (a()) {
            HaoboLog.e(HaoboLog.baseLogTag, HaoboLog.getString(R.string.already_expanded));
            return false;
        }
        com.beizi.ad.internal.d dVar = this.f4481h;
        return (dVar == null || !dVar.j() || this.f4482i == null) ? false : true;
    }

    public boolean isRewardedVideo() {
        return this.f4483j;
    }

    public boolean loadAd(a.C0055a c0055a) {
        com.beizi.ad.internal.c cVar;
        com.beizi.ad.internal.c cVar2;
        this.f4482i = c0055a;
        if (!isReadyToStart()) {
            AdListener adListener = this.f4490q;
            if (adListener != null) {
                adListener.onAdFailedToLoad(4);
            }
            return false;
        }
        if (getWindowVisibility() == 0 && (cVar2 = this.mAdFetcher) != null) {
            cVar2.a();
            this.mAdFetcher.c();
            this.mAdFetcher.b();
            this.f4498y = true;
            this.loadCount = 1;
            this.clickCount = 0;
            return true;
        }
        if (this.f4474a != null && (cVar = this.mAdFetcher) != null) {
            cVar.a();
            this.mAdFetcher.c();
            this.mAdFetcher.b();
            this.f4498y = true;
            this.loadCount = 1;
            this.clickCount = 0;
        }
        return false;
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onCreateLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onDestoryLifeCycle() {
        com.beizi.ad.internal.c cVar = this.mAdFetcher;
        if (cVar != null) {
            cVar.a();
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onPauseLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onRestartLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onResumeLifeCycle() {
        a aVar = this.f4475b;
        a.EnumC0059a enumC0059a = a.EnumC0059a.FINISHCLOSE;
        aVar.a(enumC0059a);
        if (this.f4475b.c() == enumC0059a) {
            getAdDispatcher().b();
        }
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onStartLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onStopLifeCycle() {
        this.f4475b.a(a.EnumC0059a.STATE_BACKGROUND);
    }

    @Override // com.beizi.ad.AdLifeControl
    public void openAdInNativeBrowser(boolean z2) {
        setOpensNativeBrowser(z2);
    }

    public void pingClick(String str) {
        if (StringUtil.isEmpty(str)) {
            return;
        }
        new i(str).execute(new Void[0]);
    }

    public void pingConvert(String str) {
        if (StringUtil.isEmpty(str)) {
            return;
        }
        new i(str).execute(new Void[0]);
    }

    public void setAdExtInfo(String str) {
        this.f4489p = str;
    }

    public void setAdId(String str) {
        this.O = str;
    }

    public void setAdListener(AdListener adListener) {
        if (this.f4483j) {
            HaoboLog.e(HaoboLog.publicFunctionsLogTag, "setAdListener() called on RewardedVideoAd");
        } else {
            HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.set_ad_listener));
            this.f4490q = adListener;
        }
    }

    public void setAdUnitId(String str) {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.set_placement_id, str));
        this.f4481h.a(str);
    }

    public void setAppEventListener(AppEventListener appEventListener) {
        this.f4492s = appEventListener;
    }

    public void setBrowserStyle(c cVar) {
        this.f4493t = cVar;
    }

    public void setCloseButtonPadding(int i2, int i3, int i4, int i5) {
        this.leftPadding = i2;
        this.topPadding = i3;
        this.rightPadding = i4;
        this.bottomPadding = i5;
    }

    public void setCreativeHeight(int i2) {
        this.f4488o = i2;
    }

    public void setCreativeWidth(int i2) {
        this.f4487n = i2;
    }

    public void setLandingPageUrl(String str) {
        this.N = str;
    }

    public void setLoadsInBackground(boolean z2) {
        this.f4480g = z2;
    }

    public void setOpensNativeBrowser(boolean z2) {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.set_opens_native_browser, z2));
        this.f4481h.b(z2);
    }

    public void setPrice(String str) {
        this.M = str;
    }

    public void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {
        if (!this.f4483j) {
            HaoboLog.e(HaoboLog.publicFunctionsLogTag, "setRewardedVideoAdListener() called on non-RewardedVideoAd");
        } else {
            HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(R.string.set_rewarded_video_ad_listener));
            this.f4491r = rewardedVideoAdListener;
        }
    }

    public void setShouldResizeParent(boolean z2) {
        this.f4496w = z2;
    }

    public void setShowLoadingIndicator(boolean z2) {
        this.f4497x = z2;
    }

    public void showAd() {
        ViewGroup viewGroup = this.f4474a;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            this.f4474a.addView(this);
        }
        AdWebView adWebView = this.R;
        ServerResponse serverResponse = adWebView.ad;
        if (serverResponse == null || serverResponse.mMediaType != l.SPLASH || this.f4486m == null) {
            int showCloseBtnTime = adWebView.getShowCloseBtnTime();
            int autoCloseTime = this.R.getAutoCloseTime();
            AdWebView adWebView2 = this.R;
            addCloseButton(-1, showCloseBtnTime, autoCloseTime, adWebView2, adWebView2.ad.getAdType() == e.a.ADP_IVIDEO);
        } else {
            addSkipView(adWebView.getAutoCloseTime(), this.f4486m);
        }
        if (this.f4490q != null) {
            Log.e("BeiZisAd", "enter BeiZi ad show");
            this.f4490q.onAdShown();
            this.R.post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.13
                @Override // java.lang.Runnable
                public void run() {
                    AdViewImpl.this.R.ad.handleView(AdViewImpl.this.R, AdViewImpl.this.f4481h.a());
                }
            });
        }
    }

    public void showAdLogo(View view) {
        ViewUtil.removeChildFromParent(this.K);
        ViewUtil.removeChildFromParent(this.L);
        ViewParent parent = e() ? view.getParent() : this;
        if (parent instanceof FrameLayout) {
            FrameLayout frameLayout = this.K;
            if (frameLayout != null) {
                ((FrameLayout) parent).addView(frameLayout, new FrameLayout.LayoutParams(85, 42, 83));
                this.K.setVisibility(0);
            }
            FrameLayout frameLayout2 = this.L;
            if (frameLayout2 != null) {
                ((FrameLayout) parent).addView(frameLayout2, new FrameLayout.LayoutParams(42, 42, 85));
                this.L.setVisibility(0);
                this.L.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.view.AdViewImpl.14
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        AdViewImpl.this.g();
                    }
                });
            }
        }
    }

    public void showBannerCloseBtn(View view) {
        ImageView imageView;
        if (!(view instanceof FrameLayout) || (imageView = this.B) == null) {
            return;
        }
        ((FrameLayout) view).addView(imageView);
    }

    public AdViewImpl(Context context, ViewGroup viewGroup, View view) {
        super(context, null, 0);
        this.f4474a = null;
        this.f4486m = null;
        this.f4489p = "";
        this.f4475b = new a();
        this.f4494u = new Handler(Looper.getMainLooper()) { // from class: com.beizi.ad.internal.view.AdViewImpl.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
            }
        };
        this.f4477d = false;
        this.f4478e = false;
        this.f4479f = false;
        this.f4480g = true;
        this.f4496w = false;
        this.f4497x = true;
        this.f4481h = null;
        this.f4482i = null;
        this.f4498y = false;
        this.f4483j = false;
        this.f4484k = false;
        this.S = false;
        this.f4485l = 0;
        this.f4474a = viewGroup;
        this.f4486m = view;
        a(context, (AttributeSet) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        WebView webView = new WebView(new MutableContextWrapper(getContext()));
        WebviewUtil.setWebViewSettings(webView);
        String strA = com.beizi.ad.a.a.b.a("aHR0cDovL3Nka2RvYy5iZWl6aS5iaXovIy96aC1jbi9ndWlkZS9Vc2VQcml2YWN5");
        if (!TextUtils.isEmpty(strA)) {
            webView.loadUrl(strA);
        }
        a(webView, context);
    }

    public void b() {
        getVisibility();
    }

    public void c() {
        getVisibility();
    }

    public void a(Context context, AttributeSet attributeSet) {
        setBackgroundColor(0);
        this.f4495v = new b(this.f4494u);
        this.f4481h = new com.beizi.ad.internal.d(context, StringUtil.createRequestId());
        this.F = new GestureDetector(new GestureDetector.OnGestureListener() { // from class: com.beizi.ad.internal.view.AdViewImpl.12
            @Override // android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                return false;
            }

            @Override // android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent motionEvent) {
            }

            @Override // android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                return false;
            }

            @Override // android.view.GestureDetector.OnGestureListener
            public void onShowPress(MotionEvent motionEvent) {
            }

            @Override // android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                AdViewImpl.this.G = motionEvent.getX();
                AdViewImpl.this.H = motionEvent.getY();
                AdViewImpl.this.I = motionEvent.getRawX();
                AdViewImpl.this.J = motionEvent.getRawY();
                AdViewImpl adViewImpl = AdViewImpl.this;
                int i2 = adViewImpl.clickCount;
                int i3 = adViewImpl.loadCount;
                return true;
            }
        });
        try {
            HaoboLog.setErrorContext(getContext().getApplicationContext());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        HaoboLog.v(HaoboLog.baseLogTag, HaoboLog.getString(R.string.appid, com.beizi.ad.internal.g.a().d()));
        setPadding(0, 0, 0, 0);
        this.mAdFetcher = new com.beizi.ad.internal.c(this);
        if (attributeSet != null) {
            b(context, attributeSet);
        }
        this.P = (String) SPUtils.get(this.f4481h.b(), "urlTemplate", "https://api.htp.ad-scope.com.cn/mb/sdk/event/v1?extInfo=wBLQeP8bmq6AuJ5DaZyc5xQQU_92OWkSUdy_6V4n2RA3Mbgehw6J67ZfwcDQCmj3uTyhCkrT8nMAsQ&requestUuid=__REQUESTUUID__&eventType=__EVENTTYPE__&appID=__APPID__&spaceID=__SPACEID__&channelID=__CHANNELID__&channelAppID=__CHANNELAPPID__&channelSpaceID=__CHANNELSPACEID__&ts=__TS__&ip=__IP__&netType=__NETTYPE__&carrier=__CARRIER__&errInfo=__ERRINFO__&sdkExtInfo=__SDKEXTINFO__&imei=__IMEI__&androidID=__ANDROIDID__&idfa=__IDFA__&idfv=__IDFV__&mac=__MAC__&uid=__UID__&sdkVersion=__SDKVERSION__&appVerison=__APPVERSION__");
        this.Q = (String) SPUtils.get(this.f4481h.b(), "eventsList", "[1, 2, 3, 4, 5, 6]");
    }

    public boolean a() {
        return this.f4478e;
    }

    public Context a(View view) {
        if (view.getContext() instanceof MutableContextWrapper) {
            return ((MutableContextWrapper) view.getContext()).getBaseContext();
        }
        return getContext();
    }

    private void a(WebView webView, Context context) {
        Intent intent = new Intent(context, (Class<?>) AdActivity.a());
        intent.setFlags(268435456);
        intent.putExtra("ACTIVITY_TYPE", "BROWSER");
        com.beizi.ad.internal.a.a.f3988a.add(webView);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            com.beizi.ad.internal.a.a.f3988a.remove();
        }
    }

    public AdViewImpl(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AdViewImpl(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f4474a = null;
        this.f4486m = null;
        this.f4489p = "";
        this.f4475b = new a();
        this.f4494u = new Handler(Looper.getMainLooper()) { // from class: com.beizi.ad.internal.view.AdViewImpl.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
            }
        };
        this.f4477d = false;
        this.f4478e = false;
        this.f4479f = false;
        this.f4480g = true;
        this.f4496w = false;
        this.f4497x = true;
        this.f4481h = null;
        this.f4482i = null;
        this.f4498y = false;
        this.f4483j = false;
        this.f4484k = false;
        this.S = false;
        this.f4485l = 0;
        a(context, attributeSet);
    }

    public void a(int i2, int i3, f fVar) {
        ViewUtil.removeChildFromParent(this.A);
        this.A = null;
        AdWebView adWebView = fVar.f4659b;
        if (adWebView.f4567a) {
            ViewUtil.removeChildFromParent(adWebView);
            if (fVar.d() != null) {
                fVar.d().addView(fVar.f4659b, 0);
            }
            if (fVar.c() != null) {
                fVar.c().finish();
            }
            if (getMediaType().equals(l.BANNER) && (fVar.f4659b.getContext() instanceof MutableContextWrapper)) {
                ((MutableContextWrapper) fVar.f4659b.getContext()).setBaseContext(getContext());
            }
        }
        T = null;
        U = null;
        V = null;
        a(i2, i3);
        this.f4479f = true;
        this.f4478e = false;
    }

    private void a(int i2, int i3) {
        this.f4477d = true;
        if (getLayoutParams() != null) {
            if (getLayoutParams().width > 0) {
                getLayoutParams().width = i2;
            }
            if (getLayoutParams().height > 0) {
                getLayoutParams().height = i3;
            }
        }
        if (this.f4496w && (getParent() instanceof View)) {
            View view = (View) getParent();
            if (view.getLayoutParams() != null) {
                if (view.getLayoutParams().width > 0) {
                    view.getLayoutParams().width = i2;
                }
                if (view.getLayoutParams().height > 0) {
                    view.getLayoutParams().height = i3;
                }
            }
        }
    }

    public void a(final f fVar, boolean z2, AdWebView.b bVar) {
        fVar.a((ViewGroup) fVar.f4659b.getParent());
        FrameLayout frameLayout = new FrameLayout(getContext());
        ViewUtil.removeChildFromParent(fVar.f4659b);
        frameLayout.addView(fVar.f4659b);
        if (this.A == null) {
            AppCompatTextView appCompatTextViewCreateCloseButton = ViewUtil.createCloseButton(getContext());
            this.A = appCompatTextViewCreateCloseButton;
            appCompatTextViewCreateCloseButton.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.view.AdViewImpl.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    fVar.a();
                }
            });
        }
        frameLayout.addView(this.A);
        T = frameLayout;
        U = fVar;
        V = bVar;
        Class clsA = AdActivity.a();
        try {
            Intent intent = new Intent(getContext(), (Class<?>) clsA);
            intent.putExtra("ACTIVITY_TYPE", ServerResponse.EXTRAS_KEY_MRAID);
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            HaoboLog.e(HaoboLog.baseLogTag, HaoboLog.getString(R.string.adactivity_missing, clsA.getName()));
            T = null;
            U = null;
            V = null;
        }
    }

    public void a(int i2, int i3, boolean z2, final f fVar, AdWebView.b bVar) {
        a(i2, i3);
        AppCompatTextView appCompatTextViewCreateCloseButton = ViewUtil.createCloseButton(getContext());
        this.A = appCompatTextViewCreateCloseButton;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) appCompatTextViewCreateCloseButton.getLayoutParams();
        if (!fVar.f4659b.f4567a && getChildAt(0) != null) {
            layoutParams.rightMargin = (getMeasuredWidth() - getChildAt(0).getMeasuredWidth()) / 2;
        }
        this.A.setLayoutParams(layoutParams);
        this.A.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.view.AdViewImpl.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                fVar.a();
            }
        });
        if (fVar.f4659b.f4567a) {
            a(fVar, z2, bVar);
        } else {
            addView(this.A);
        }
        this.f4478e = true;
    }

    public void a(int i2, int i3, int i4, int i5, f.a aVar, boolean z2, final f fVar) {
        a(i2, i3);
        ViewUtil.removeChildFromParent(this.A);
        if (this.f4485l <= 0) {
            this.f4485l = (int) (fVar.f4659b.getContext().getResources().getDisplayMetrics().density * 50.0f);
        }
        this.A = new AppCompatTextView(getContext()) { // from class: com.beizi.ad.internal.view.AdViewImpl.9
            @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
            @SuppressLint({"NewApi", "DrawAllocation"})
            public void onLayout(boolean z3, int i6, int i7, int i8, int i9) {
                Activity activity;
                boolean z4;
                Point point;
                int i10;
                int i11;
                int[] iArr = new int[2];
                getLocationOnScreen(iArr);
                Point point2 = new Point(0, 0);
                try {
                    activity = (Activity) fVar.f4659b.getContext();
                    z4 = true;
                } catch (ClassCastException unused) {
                    activity = null;
                    z4 = false;
                }
                if (z4) {
                    activity.getWindowManager().getDefaultDisplay().getSize(point2);
                }
                int[] iArr2 = new int[2];
                if (AdViewImpl.this.getMediaType().equals(l.INTERSTITIAL)) {
                    InterstitialAdViewImpl.INTERSTITIALADVIEW_TO_USE.measure(0, 0);
                    InterstitialAdViewImpl.INTERSTITIALADVIEW_TO_USE.getLocationOnScreen(iArr2);
                    point = new Point(InterstitialAdViewImpl.INTERSTITIALADVIEW_TO_USE.getMeasuredWidth(), InterstitialAdViewImpl.INTERSTITIALADVIEW_TO_USE.getMeasuredHeight());
                } else {
                    AdViewImpl.this.measure(0, 0);
                    AdViewImpl.this.getLocationOnScreen(iArr2);
                    point = new Point(AdViewImpl.this.getMeasuredWidth(), AdViewImpl.this.getMeasuredHeight());
                }
                int i12 = point.x;
                int i13 = AdViewImpl.this.f4485l;
                int iMin = i12 - i13;
                int iMin2 = point.y - i13;
                if (z4) {
                    iMin = (iArr2[0] + Math.min(point2.x, i12)) - AdViewImpl.this.f4485l;
                    iMin2 = (iArr2[1] + Math.min(point2.y, point.y)) - AdViewImpl.this.f4485l;
                    i10 = iArr2[0];
                    i11 = iArr2[1];
                } else {
                    i10 = 0;
                    i11 = 0;
                }
                int i14 = iArr[0];
                if (i14 + 1 >= i10 && i14 - 1 <= iMin) {
                    int i15 = iArr[1];
                    if (i15 + 1 >= i11 && i15 - 1 <= iMin2) {
                        return;
                    }
                }
                final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2, 53);
                layoutParams.setMargins(40, 40, 40, 40);
                post(new Runnable() { // from class: com.beizi.ad.internal.view.AdViewImpl.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        setLayoutParams(layoutParams);
                    }
                });
                AdViewImpl.this.A.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_close_background));
                AdViewImpl.this.A.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.button_text_selector));
                AdViewImpl.this.A.setTextSize(2, 16.0f);
                AdViewImpl.this.A.setText(R.string.skip_ad);
            }
        };
        int i6 = this.f4485l;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i6, i6, 17);
        int i7 = this.f4485l;
        int i8 = (i3 / 2) - (i7 / 2);
        int i9 = (i2 / 2) - (i7 / 2);
        int i10 = AnonymousClass11.f4503a[aVar.ordinal()];
        if (i10 == 1) {
            layoutParams.topMargin = i8;
        } else if (i10 == 2) {
            layoutParams.rightMargin = i9;
            layoutParams.topMargin = i8;
        } else if (i10 == 3) {
            layoutParams.leftMargin = i9;
            layoutParams.topMargin = i8;
        } else if (i10 == 5) {
            layoutParams.bottomMargin = i8;
        } else if (i10 == 6) {
            layoutParams.rightMargin = i9;
            layoutParams.bottomMargin = i8;
        } else if (i10 == 7) {
            layoutParams.leftMargin = i9;
            layoutParams.bottomMargin = i8;
        }
        this.A.setLayoutParams(layoutParams);
        this.A.setBackgroundColor(0);
        this.A.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.view.AdViewImpl.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                fVar.a();
            }
        });
        if (fVar.f4659b.getParent() != null) {
            ((ViewGroup) fVar.f4659b.getParent()).addView(this.A);
        }
    }
}
