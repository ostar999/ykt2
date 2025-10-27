package com.beizi.fusion;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.RequiresPermission;
import com.beizi.fusion.d.g;
import com.beizi.fusion.d.u;

/* loaded from: classes2.dex */
public class SplashAd {

    /* renamed from: a, reason: collision with root package name */
    private u f4725a;

    /* renamed from: b, reason: collision with root package name */
    private ViewGroup f4726b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f4727c = false;

    public static class SplashClickEye {
        public SplashClickEye(Activity activity, String str) {
            g.a().a(activity, str);
        }
    }

    public interface SplashClickEyeListener {
        void isSupportSplashClickEye(boolean z2);

        void onSplashClickEyeAnimationFinish();
    }

    @RequiresPermission("android.permission.INTERNET")
    public SplashAd(Context context, View view, String str, AdListener adListener, long j2) {
        this.f4725a = new u(context, str, view, adListener, j2);
        FrameLayout frameLayout = new FrameLayout(context);
        this.f4726b = frameLayout;
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    }

    public void cancel(Context context) {
        u uVar = this.f4725a;
        if (uVar != null) {
            uVar.l();
        }
    }

    public int getECPM() {
        u uVar = this.f4725a;
        if (uVar != null) {
            return uVar.D();
        }
        return -1;
    }

    public void loadAd(int i2, int i3) throws InterruptedException {
        u uVar = this.f4725a;
        if (uVar == null || this.f4726b == null) {
            return;
        }
        uVar.d(i2);
        this.f4725a.e(i3);
        this.f4725a.a(this.f4726b);
    }

    public void reportNotShow() {
        u uVar = this.f4725a;
        if (uVar != null) {
            uVar.E();
        }
    }

    public void setSplashClickEyeListener(SplashClickEyeListener splashClickEyeListener) {
        g.a().a(splashClickEyeListener);
    }

    public void setSupportRegionClick(boolean z2) {
        u uVar = this.f4725a;
        if (uVar != null) {
            uVar.a(z2);
        }
    }

    public void show(ViewGroup viewGroup) {
        ViewGroup viewGroup2;
        if (this.f4727c) {
            return;
        }
        if (viewGroup == null) {
            Log.e("BeiZis", "parent can't be null !");
        } else {
            if (this.f4725a == null || (viewGroup2 = this.f4726b) == null) {
                return;
            }
            viewGroup.addView(viewGroup2);
            this.f4725a.C();
            this.f4727c = true;
        }
    }

    @Deprecated
    public void loadAd() throws InterruptedException {
        ViewGroup viewGroup;
        u uVar = this.f4725a;
        if (uVar == null || (viewGroup = this.f4726b) == null) {
            return;
        }
        uVar.a(viewGroup);
    }
}
