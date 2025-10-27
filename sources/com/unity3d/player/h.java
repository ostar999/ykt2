package com.unity3d.player;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.PixelCopy;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
final class h implements Application.ActivityLifecycleCallbacks {

    /* renamed from: b, reason: collision with root package name */
    Activity f24105b;

    /* renamed from: a, reason: collision with root package name */
    WeakReference f24104a = new WeakReference(null);

    /* renamed from: c, reason: collision with root package name */
    a f24106c = null;

    public class a extends View implements PixelCopy.OnPixelCopyFinishedListener {

        /* renamed from: a, reason: collision with root package name */
        Bitmap f24107a;

        public a(Context context) {
            super(context);
        }

        public final void a(SurfaceView surfaceView) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(surfaceView.getWidth(), surfaceView.getHeight(), Bitmap.Config.ARGB_8888);
            this.f24107a = bitmapCreateBitmap;
            PixelCopy.request(surfaceView, bitmapCreateBitmap, this, new Handler(Looper.getMainLooper()));
        }

        @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
        public final void onPixelCopyFinished(int i2) {
            if (i2 == 0) {
                setBackground(new LayerDrawable(new Drawable[]{new ColorDrawable(-16777216), new BitmapDrawable(getResources(), this.f24107a)}));
            }
        }
    }

    public h(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            this.f24105b = activity;
            activity.getApplication().registerActivityLifecycleCallbacks(this);
        }
    }

    public final void a() {
        Activity activity = this.f24105b;
        if (activity != null) {
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }
    }

    public final void a(SurfaceView surfaceView) {
        if (PlatformSupport.NOUGAT_SUPPORT && this.f24106c == null) {
            a aVar = new a(this.f24105b);
            this.f24106c = aVar;
            aVar.a(surfaceView);
        }
    }

    public final void a(ViewGroup viewGroup) {
        a aVar = this.f24106c;
        if (aVar == null || aVar.getParent() != null) {
            return;
        }
        viewGroup.addView(this.f24106c);
        viewGroup.bringChildToFront(this.f24106c);
    }

    public final void b() {
        this.f24106c = null;
    }

    public final void b(ViewGroup viewGroup) {
        a aVar = this.f24106c;
        if (aVar == null || aVar.getParent() == null) {
            return;
        }
        viewGroup.removeView(this.f24106c);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        this.f24104a = new WeakReference(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }
}
