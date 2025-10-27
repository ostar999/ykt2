package com.beizi.ad.internal.view;

import android.R;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.beizi.ad.AdActivity;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.network.a;
import com.beizi.ad.internal.utilities.HaoboLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes2.dex */
public class InterstitialAdViewImpl extends AdViewImpl {
    public static final String INTENT_KEY_CLOSE_BUTTON_DELAY = "CLOSE_BUTTON_DELAY";
    public static final String INTENT_KEY_TIME = "TIME";
    public static InterstitialAdViewImpl INTERSTITIALADVIEW_TO_USE = null;
    public static final long MAX_AGE = 270000;

    /* renamed from: m, reason: collision with root package name */
    protected boolean f4646m;

    /* renamed from: n, reason: collision with root package name */
    protected boolean f4647n;

    /* renamed from: o, reason: collision with root package name */
    private int f4648o;

    /* renamed from: p, reason: collision with root package name */
    private int f4649p;

    /* renamed from: q, reason: collision with root package name */
    private boolean f4650q;

    /* renamed from: r, reason: collision with root package name */
    private Queue<e> f4651r;

    /* renamed from: s, reason: collision with root package name */
    private AdActivity.a f4652s;

    public InterstitialAdViewImpl(Context context, boolean z2, boolean z3) {
        super(context);
        this.f4648o = 0;
        this.f4649p = 10000;
        this.f4651r = new LinkedList();
        this.f4652s = null;
        this.f4646m = false;
        this.f4647n = false;
        this.f4483j = z2;
        this.f4484k = z3;
        if (z2) {
            this.f4648o = -16777216;
        } else if (z3) {
            this.f4648o = 0;
        } else {
            this.f4648o = Color.argb(51, 0, 0, 0);
        }
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void a(Context context, AttributeSet attributeSet) {
        super.a(context, attributeSet);
        this.mAdFetcher.a(-1);
        this.f4481h.a(l.INTERSTITIAL);
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int top2 = displayMetrics.heightPixels;
        int i2 = displayMetrics.widthPixels;
        try {
            Activity activity = (Activity) context;
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(new Rect());
            top2 -= activity.getWindow().findViewById(R.id.content).getTop() + 0;
        } catch (ClassCastException unused) {
        }
        com.beizi.ad.internal.g gVarA = com.beizi.ad.internal.g.a();
        int i3 = (int) ((top2 / gVarA.i()) + 0.5f);
        this.f4481h.d((int) ((i2 / gVarA.h()) + 0.5f));
        this.f4481h.e(i3);
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void activityOnDestroy() {
        this.f4646m = true;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void activityOnPause() {
        this.f4647n = true;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void activityOnResume() {
        this.f4647n = false;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void b(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.beizi.ad.R.styleable.AdView);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        HaoboLog.v(HaoboLog.xmlLogTag, HaoboLog.getString(com.beizi.ad.R.string.found_n_in_xml, indexCount));
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == com.beizi.ad.R.styleable.AdView_adUnitId) {
                setAdUnitId(typedArrayObtainStyledAttributes.getString(index));
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(com.beizi.ad.R.string.placement_id, typedArrayObtainStyledAttributes.getString(index)));
            } else if (index == com.beizi.ad.R.styleable.AdView_test) {
                com.beizi.ad.internal.g.a().f4181c = typedArrayObtainStyledAttributes.getBoolean(index, false);
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(com.beizi.ad.R.string.xml_set_test, com.beizi.ad.internal.g.a().f4181c));
            } else if (index == com.beizi.ad.R.styleable.AdView_opens_native_browser) {
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(com.beizi.ad.R.string.xml_set_opens_native_browser));
                setOpensNativeBrowser(typedArrayObtainStyledAttributes.getBoolean(index, false));
            } else if (index == com.beizi.ad.R.styleable.AdView_show_loading_indicator) {
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(com.beizi.ad.R.string.show_loading_indicator_xml));
                setShowLoadingIndicator(typedArrayObtainStyledAttributes.getBoolean(index, true));
            } else if (index == com.beizi.ad.R.styleable.AdView_load_landing_page_in_background) {
                setLoadsInBackground(typedArrayObtainStyledAttributes.getBoolean(index, true));
                HaoboLog.d(HaoboLog.xmlLogTag, HaoboLog.getString(com.beizi.ad.R.string.xml_load_landing_page_in_background, this.f4480g));
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.beizi.ad.AdLifeControl
    public void cancel() {
        com.beizi.ad.internal.c cVar = this.mAdFetcher;
        if (cVar != null) {
            cVar.a();
        }
        INTERSTITIALADVIEW_TO_USE = null;
        this.f4651r.clear();
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public boolean d() {
        return false;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void destroy() {
        super.destroy();
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(com.beizi.ad.R.string.destroy_int));
        com.beizi.ad.internal.c cVar = this.mAdFetcher;
        if (cVar != null) {
            cVar.a();
        }
        this.f4651r.clear();
        INTERSTITIALADVIEW_TO_USE = null;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public boolean e() {
        return true;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void f() {
        AdActivity.a aVar = this.f4652s;
        if (aVar != null) {
            aVar.d();
        }
    }

    public void g() {
        AdActivity.a aVar = this.f4652s;
        if (aVar != null) {
            aVar.e();
        }
    }

    public AdActivity.a getAdImplementation() {
        return this.f4652s;
    }

    public Queue<e> getAdQueue() {
        return this.f4651r;
    }

    public int getBackgroundColor() {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(com.beizi.ad.R.string.get_bg));
        return this.f4648o;
    }

    public int getCloseButtonDelay() {
        return this.f4649p;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public int getCreativeHeight() {
        return -1;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public int getCreativeWidth() {
        return -1;
    }

    @Override // com.beizi.ad.internal.a
    public l getMediaType() {
        return l.INTERSTITIAL;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public boolean isLoaded() {
        if (!a(System.currentTimeMillis())) {
            return false;
        }
        e eVarPeek = this.f4651r.peek();
        if (eVarPeek == null || !eVarPeek.b() || eVarPeek.c() == null) {
            return true;
        }
        return eVarPeek.c().c();
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public boolean loadAd(a.C0055a c0055a) {
        com.beizi.ad.internal.c cVar;
        getAdParameters().a(false);
        this.f4482i = c0055a;
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(com.beizi.ad.R.string.load_ad_int));
        if (!isReadyToStart() || (cVar = this.mAdFetcher) == null) {
            return false;
        }
        cVar.a();
        this.mAdFetcher.b();
        this.loadCount = 1;
        this.clickCount = 0;
        return true;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl, com.beizi.ad.AdLifeControl
    public void onCreateLifeCycle() {
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl, com.beizi.ad.AdLifeControl
    public void onDestoryLifeCycle() {
        com.beizi.ad.internal.c cVar = this.mAdFetcher;
        if (cVar != null) {
            cVar.a();
        }
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl, com.beizi.ad.AdLifeControl
    public void onPauseLifeCycle() {
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl, com.beizi.ad.AdLifeControl
    public void onRestartLifeCycle() {
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl, com.beizi.ad.AdLifeControl
    public void onResumeLifeCycle() {
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl, com.beizi.ad.AdLifeControl
    public void onStartLifeCycle() {
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl, com.beizi.ad.AdLifeControl
    public void onStopLifeCycle() {
    }

    public void setAdImplementation(AdActivity.a aVar) {
        this.f4652s = aVar;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(com.beizi.ad.R.string.set_bg));
        this.f4648o = i2;
    }

    public void setCloseButtonDelay(int i2) {
        this.f4649p = Math.min(i2, 10000);
    }

    public void setDismissOnClick(boolean z2) {
        this.f4650q = z2;
    }

    public boolean shouldDismissOnClick() {
        return this.f4650q;
    }

    public int show() {
        HaoboLog.d(HaoboLog.publicFunctionsLogTag, HaoboLog.getString(com.beizi.ad.R.string.show_int));
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean zA = a(jCurrentTimeMillis);
        e eVarPeek = this.f4651r.peek();
        if (eVarPeek != null && eVarPeek.b() && eVarPeek.c() != null) {
            eVarPeek.c().d();
            this.f4651r.poll();
            return this.f4651r.size();
        }
        if (!zA || this.f4646m) {
            HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(com.beizi.ad.R.string.empty_queue));
            return this.f4651r.size();
        }
        Class clsA = AdActivity.a();
        Intent intent = new Intent(getContext(), (Class<?>) clsA);
        intent.putExtra("ACTIVITY_TYPE", "INTERSTITIAL");
        intent.putExtra(INTENT_KEY_TIME, jCurrentTimeMillis);
        intent.putExtra(INTENT_KEY_CLOSE_BUTTON_DELAY, this.f4649p);
        INTERSTITIALADVIEW_TO_USE = this;
        try {
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            INTERSTITIALADVIEW_TO_USE = null;
            HaoboLog.e(HaoboLog.baseLogTag, HaoboLog.getString(com.beizi.ad.R.string.adactivity_missing, clsA.getName()));
        }
        return this.f4651r.size() - 1;
    }

    public InterstitialAdViewImpl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4648o = 0;
        this.f4649p = 10000;
        this.f4651r = new LinkedList();
        this.f4652s = null;
        this.f4646m = false;
        this.f4647n = false;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void a(c cVar) {
        if (b(cVar)) {
            c cVar2 = this.f4476c;
            if (cVar2 != null) {
                cVar2.destroy();
            }
            if (!this.f4646m && !this.f4647n) {
                this.f4476c = cVar;
                this.f4651r.add(new d(cVar, Long.valueOf(System.currentTimeMillis()), false, null));
            } else if (cVar != null) {
                cVar.destroy();
            }
        }
    }

    public InterstitialAdViewImpl(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f4648o = 0;
        this.f4649p = 10000;
        this.f4651r = new LinkedList();
        this.f4652s = null;
        this.f4646m = false;
        this.f4647n = false;
    }

    @Override // com.beizi.ad.internal.view.AdViewImpl
    public void a(com.beizi.ad.internal.b.e eVar) {
        if (b(eVar)) {
            c cVar = this.f4476c;
            if (cVar != null) {
                cVar.destroy();
            }
            if (!this.f4646m && !this.f4647n) {
                this.f4476c = eVar;
                this.f4651r.add(new d(eVar, Long.valueOf(System.currentTimeMillis()), true, eVar.a()));
            } else if (eVar != null) {
                eVar.destroy();
            }
        }
    }

    private boolean b(c cVar) {
        if (cVar != null && !cVar.failed()) {
            return true;
        }
        HaoboLog.e(HaoboLog.baseLogTag, "Loaded an ad with an invalid displayable");
        return false;
    }

    private boolean a(long j2) {
        boolean z2;
        ArrayList arrayList = new ArrayList();
        for (e eVar : this.f4651r) {
            if (eVar != null && j2 - eVar.a() <= MAX_AGE && j2 - eVar.a() >= 0 && (!eVar.b() || !eVar.c().e())) {
                z2 = true;
                break;
            }
            arrayList.add(eVar);
        }
        z2 = false;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.f4651r.remove((e) it.next());
        }
        return z2;
    }
}
