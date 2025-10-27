package com.beizi.fusion.d;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.beizi.fusion.SplashAd;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.work.splash.c;
import com.bytedance.sdk.openadsdk.CSJSplashAd;
import java.lang.ref.SoftReference;
import java.util.List;

/* loaded from: classes2.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private static volatile g f4960a;

    /* renamed from: c, reason: collision with root package name */
    private static SplashAd.SplashClickEyeListener f4961c;

    /* renamed from: b, reason: collision with root package name */
    private Context f4962b;

    /* renamed from: d, reason: collision with root package name */
    private boolean f4963d = false;

    public static class a implements CSJSplashAd.SplashClickEyeListener {

        /* renamed from: a, reason: collision with root package name */
        boolean f4968a = true;

        /* renamed from: b, reason: collision with root package name */
        private SoftReference<View> f4969b;

        /* renamed from: c, reason: collision with root package name */
        private SoftReference<CSJSplashAd> f4970c;

        /* renamed from: d, reason: collision with root package name */
        private Context f4971d;

        public a(Context context, View view, CSJSplashAd cSJSplashAd) {
            this.f4969b = new SoftReference<>(view);
            this.f4970c = new SoftReference<>(cSJSplashAd);
            this.f4971d = context;
        }

        public void onSplashClickEyeClick() {
            Log.d("BeiZis", "showCsjSplash ClickEye onSplashClickEyeClick() ");
        }

        public void onSplashClickEyeClose() {
            Log.d("BeiZis", "showCsjSplash ClickEye onSplashClickEyeClose() ");
            SoftReference<View> softReference = this.f4969b;
            if (softReference != null && softReference.get() != null) {
                this.f4969b.get().setVisibility(8);
                as.a(this.f4969b.get());
                this.f4969b = null;
                this.f4970c = null;
            }
            if (g.f4961c != null && this.f4968a) {
                g.f4961c.onSplashClickEyeAnimationFinish();
                this.f4968a = false;
            }
            com.beizi.fusion.work.splash.c.a(this.f4971d).a();
        }

        public void onSplashClickEyeReadyToShow(CSJSplashAd cSJSplashAd) {
            Log.d("BeiZis", "showCsjSplash ClickEye onSplashClickEyeReadyToShow() ");
        }
    }

    private g() {
    }

    public static g a() {
        if (f4960a == null) {
            synchronized (g.class) {
                if (f4960a == null) {
                    f4960a = new g();
                }
            }
        }
        return f4960a;
    }

    public boolean b() {
        return this.f4963d;
    }

    private View b(final Activity activity) {
        final com.beizi.fusion.work.splash.c cVarA = com.beizi.fusion.work.splash.c.a(this.f4962b);
        final CSJSplashAd cSJSplashAdB = cVarA.b();
        return cVarA.a((ViewGroup) activity.getWindow().getDecorView(), (ViewGroup) activity.findViewById(R.id.content), new c.a() { // from class: com.beizi.fusion.d.g.1
            @Override // com.beizi.fusion.work.splash.c.a
            public void a() {
                CSJSplashAd cSJSplashAd = cSJSplashAdB;
                if (cSJSplashAd != null) {
                    cSJSplashAd.showSplashClickEyeView((ViewGroup) activity.findViewById(R.id.content));
                    cVarA.a();
                }
            }

            @Override // com.beizi.fusion.work.splash.c.a
            public void a(int i2) {
            }
        });
    }

    public void a(SplashAd.SplashClickEyeListener splashClickEyeListener) {
        f4961c = splashClickEyeListener;
    }

    public void a(String str, boolean z2, boolean z3) {
        this.f4963d = z2;
        SplashAd.SplashClickEyeListener splashClickEyeListener = f4961c;
        if (splashClickEyeListener == null || !z3) {
            return;
        }
        splashClickEyeListener.isSupportSplashClickEye(z2);
    }

    public void a(Activity activity, String str) {
        if (activity == null) {
            return;
        }
        Context applicationContext = activity.getApplicationContext();
        this.f4962b = applicationContext;
        AdSpacesBean adSpacesBeanA = com.beizi.fusion.c.a.a(applicationContext, str, "2");
        if (adSpacesBeanA != null) {
            AdSpacesBean.ComponentBean component = adSpacesBeanA.getComponent();
            List<AdSpacesBean.BuyerBean> buyer = adSpacesBeanA.getBuyer();
            List<AdSpacesBean.ForwardBean> listA = com.beizi.fusion.f.b.a(component, buyer, str);
            if (listA.size() > 0) {
                for (int i2 = 0; i2 < listA.size(); i2++) {
                    AdSpacesBean.ForwardBean forwardBean = listA.get(i2);
                    String buyerId = forwardBean.getBuyerId();
                    if (com.beizi.fusion.f.b.a(buyerId, buyer, forwardBean.getBuyerSpaceUuId()) != null && buyerId.equals("CSJ")) {
                        a(activity);
                        return;
                    }
                }
            }
        }
    }

    private void a(Activity activity) {
        if (activity == null) {
            return;
        }
        Context applicationContext = activity.getApplicationContext();
        this.f4962b = applicationContext;
        com.beizi.fusion.work.splash.c cVarA = com.beizi.fusion.work.splash.c.a(applicationContext);
        if (!b()) {
            Log.d("BeiZis", "showCsjSplash is not Support Splash Click Eye");
            return;
        }
        View viewB = b(activity);
        CSJSplashAd cSJSplashAdB = cVarA.b();
        a aVar = new a(this.f4962b, viewB, cSJSplashAdB);
        if (cSJSplashAdB != null) {
            cSJSplashAdB.setSplashClickEyeListener(aVar);
        }
    }
}
