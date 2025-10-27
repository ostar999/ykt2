package com.mobile.auth.x;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.utils.k;
import com.mobile.auth.gatewayauth.utils.l;
import com.nirvana.tools.core.AppUtils;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class a extends Dialog {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference<Activity> f10581a;

    /* renamed from: b, reason: collision with root package name */
    private Animation f10582b;

    /* renamed from: c, reason: collision with root package name */
    private ImageView f10583c;

    /* renamed from: d, reason: collision with root package name */
    private LinearLayout f10584d;

    /* renamed from: e, reason: collision with root package name */
    private AuthUIConfig f10585e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f10586f;

    public a(@NonNull WeakReference<Activity> weakReference, int i2) {
        super(weakReference.get(), i2);
        this.f10586f = false;
        this.f10581a = weakReference;
    }

    public a(@NonNull WeakReference<Activity> weakReference, AuthUIConfig authUIConfig, boolean z2) {
        this(weakReference, AppUtils.getResID(weakReference.get().getApplicationContext(), "authsdk_loading_dialog", TtmlNode.TAG_STYLE));
        this.f10585e = authUIConfig;
        this.f10586f = z2;
    }

    private void a() {
        try {
            setContentView(AppUtils.getResID(getContext(), "authsdk_loading_dialog_layout", TtmlNode.TAG_LAYOUT));
            setCancelable(false);
            this.f10583c = (ImageView) findViewById(AppUtils.getResID(getContext(), "authsdk_iv_loading", "id"));
            this.f10584d = (LinearLayout) findViewById(AppUtils.getResID(getContext(), "authsdk_lly_loading", "id"));
            Drawable loadingImgDrawable = this.f10585e.getLoadingImgDrawable();
            if (loadingImgDrawable == null) {
                Drawable drawableC = k.c(getContext(), this.f10585e.getLoadingImgPath());
                if (drawableC != null) {
                    this.f10583c.setImageDrawable(drawableC);
                }
                this.f10582b = AnimationUtils.loadAnimation(this.f10581a.get(), AppUtils.getResID(getContext(), "authsdk_anim_loading", "anim"));
                this.f10582b.setInterpolator(new LinearInterpolator());
                this.f10583c.startAnimation(this.f10582b);
            } else {
                this.f10583c.setImageDrawable(loadingImgDrawable);
            }
            Drawable loadingBackgroundDrawable = this.f10585e.getLoadingBackgroundDrawable();
            if (loadingBackgroundDrawable == null && (loadingBackgroundDrawable = k.c(getContext(), this.f10585e.getLoadingBackgroundPath())) == null) {
                return;
            }
            LinearLayout linearLayout = this.f10584d;
            linearLayout.setBackgroundDrawable(loadingBackgroundDrawable);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        try {
            Animation animation = this.f10582b;
            if (animation != null) {
                animation.cancel();
            }
            super.dismiss();
            this.f10585e = null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            a();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Dialog
    public void show() {
        try {
            if (this.f10586f || this.f10585e.isStatusBarHidden()) {
                l.b(getWindow());
            }
            super.show();
            Animation animation = this.f10582b;
            if (animation != null) {
                animation.start();
                this.f10583c.startAnimation(this.f10582b);
            }
            if (this.f10586f || this.f10585e.isStatusBarHidden()) {
                l.a(getWindow());
                l.c(getWindow());
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
