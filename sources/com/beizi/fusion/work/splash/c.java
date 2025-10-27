package com.beizi.fusion.work.splash;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import com.beizi.fusion.g.as;
import com.bytedance.sdk.openadsdk.CSJSplashAd;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: l, reason: collision with root package name */
    private static volatile c f6078l;

    /* renamed from: a, reason: collision with root package name */
    private int f6079a;

    /* renamed from: b, reason: collision with root package name */
    private int f6080b;

    /* renamed from: c, reason: collision with root package name */
    private int f6081c;

    /* renamed from: d, reason: collision with root package name */
    private int f6082d;

    /* renamed from: e, reason: collision with root package name */
    private int f6083e;

    /* renamed from: f, reason: collision with root package name */
    private int f6084f;

    /* renamed from: g, reason: collision with root package name */
    private CSJSplashAd f6085g;

    /* renamed from: h, reason: collision with root package name */
    private View f6086h;

    /* renamed from: i, reason: collision with root package name */
    private int[] f6087i = new int[2];

    /* renamed from: j, reason: collision with root package name */
    private int f6088j;

    /* renamed from: k, reason: collision with root package name */
    private int f6089k;

    public interface a {
        void a();

        void a(int i2);
    }

    private c(Context context) {
        Context contextA = context == null ? com.beizi.fusion.g.g.a() : context.getApplicationContext();
        b(contextA);
        this.f6081c = as.a(contextA, 16.0f);
        this.f6082d = as.a(contextA, 100.0f);
        this.f6083e = 1;
        this.f6084f = 300;
    }

    public static c a(Context context) {
        if (f6078l == null) {
            synchronized (c.class) {
                if (f6078l == null) {
                    f6078l = new c(context);
                }
            }
        }
        return f6078l;
    }

    private void b(Context context) {
        int iMin = Math.min(as.p(context), as.o(context));
        CSJSplashAd cSJSplashAd = this.f6085g;
        if (cSJSplashAd != null && cSJSplashAd.getSplashClickEyeSizeToDp() != null) {
            this.f6079a = as.a(context, this.f6085g.getSplashClickEyeSizeToDp()[0]);
            this.f6080b = as.a(context, this.f6085g.getSplashClickEyeSizeToDp()[1]);
        } else {
            this.f6079a = Math.round(iMin * 0.3f);
            this.f6080b = Math.round((r3 * 16) / 9);
        }
    }

    public void a(Context context, CSJSplashAd cSJSplashAd, View view, View view2) {
        Context applicationContext;
        this.f6085g = cSJSplashAd;
        this.f6086h = view;
        view.getLocationOnScreen(this.f6087i);
        this.f6088j = view2.getWidth();
        this.f6089k = view2.getHeight();
        if (context == null) {
            applicationContext = com.beizi.fusion.g.g.a();
        } else {
            applicationContext = context.getApplicationContext();
        }
        b(applicationContext);
    }

    public CSJSplashAd b() {
        return this.f6085g;
    }

    public void a() {
        this.f6085g = null;
        this.f6086h = null;
    }

    public ViewGroup a(ViewGroup viewGroup, ViewGroup viewGroup2, a aVar) {
        View view;
        if (viewGroup == null || viewGroup2 == null || this.f6085g == null || (view = this.f6086h) == null) {
            return null;
        }
        return a(view, viewGroup, viewGroup2, aVar);
    }

    public ViewGroup a(final View view, ViewGroup viewGroup, final ViewGroup viewGroup2, final a aVar) {
        if (view == null || viewGroup2 == null) {
            return null;
        }
        final int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        Context context = viewGroup2.getContext();
        int width = view.getWidth();
        int height = view.getHeight();
        int width2 = viewGroup.getWidth();
        int height2 = viewGroup.getHeight();
        if (width2 == 0) {
            width2 = this.f6088j;
        }
        if (height2 == 0) {
            height2 = this.f6089k;
        }
        int i2 = this.f6079a;
        float f2 = i2 / width;
        int i3 = this.f6080b;
        float f3 = i3 / height;
        final float f4 = this.f6083e == 0 ? this.f6081c : (width2 - this.f6081c) - i2;
        final float f5 = (height2 - this.f6082d) - i3;
        as.a(view);
        viewGroup.addView(view, new FrameLayout.LayoutParams(width, height));
        final FrameLayout frameLayout = new FrameLayout(context);
        view.setPivotX(0.0f);
        view.setPivotY(0.0f);
        view.animate().scaleX(f2).scaleY(f3).x(f4).y(f5).setInterpolator(new OvershootInterpolator(0.0f)).setDuration(this.f6084f).setListener(new Animator.AnimatorListener() { // from class: com.beizi.fusion.work.splash.c.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                as.a(view);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
                view.setX(0.0f);
                view.setY(0.0f);
                viewGroup2.getLocationOnScreen(new int[2]);
                float f6 = f4 - r5[0];
                int[] iArr2 = iArr;
                float f7 = (f5 - r5[1]) + iArr2[1];
                frameLayout.addView(view, -1, -1);
                viewGroup2.addView(frameLayout, new FrameLayout.LayoutParams(c.this.f6079a, c.this.f6080b));
                frameLayout.setTranslationX(f6 + iArr2[0]);
                frameLayout.setTranslationY(f7);
                a aVar2 = aVar;
                if (aVar2 != null) {
                    aVar2.a();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                a aVar2 = aVar;
                if (aVar2 != null) {
                    aVar2.a(c.this.f6084f);
                }
            }
        });
        return frameLayout;
    }
}
