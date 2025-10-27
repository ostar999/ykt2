package com.hjq.toast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
final class ToastImpl {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private final Runnable mCancelRunnable;
    private boolean mGlobalShow;
    private final String mPackageName;
    private boolean mShow;
    private final Runnable mShowRunnable;
    private final CustomToast mToast;
    private WindowLifecycle mWindowLifecycle;

    /* renamed from: com.hjq.toast.ToastImpl$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0() {
            ToastImpl.this.cancel();
        }

        @Override // java.lang.Runnable
        @SuppressLint({"WrongConstant"})
        public void run() {
            WindowManager windowManager = ToastImpl.this.mWindowLifecycle.getWindowManager();
            if (windowManager == null) {
                return;
            }
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.height = -2;
            layoutParams.width = -2;
            layoutParams.format = -3;
            layoutParams.flags = 152;
            layoutParams.packageName = ToastImpl.this.mPackageName;
            layoutParams.gravity = ToastImpl.this.mToast.getGravity();
            layoutParams.x = ToastImpl.this.mToast.getXOffset();
            layoutParams.y = ToastImpl.this.mToast.getYOffset();
            layoutParams.verticalMargin = ToastImpl.this.mToast.getVerticalMargin();
            layoutParams.horizontalMargin = ToastImpl.this.mToast.getHorizontalMargin();
            layoutParams.windowAnimations = ToastImpl.this.mToast.getAnimationsId();
            if (ToastImpl.this.mGlobalShow) {
                if (Build.VERSION.SDK_INT >= 26) {
                    layoutParams.type = R2.attr.index_change_img;
                    layoutParams.flags &= -17;
                } else {
                    layoutParams.type = 2003;
                }
            }
            try {
                windowManager.addView(ToastImpl.this.mToast.getView(), layoutParams);
                ToastImpl.HANDLER.postDelayed(new Runnable() { // from class: com.hjq.toast.a
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f7287c.lambda$run$0();
                    }
                }, ToastImpl.this.mToast.getDuration() == 1 ? ToastImpl.this.mToast.getLongDuration() : ToastImpl.this.mToast.getShortDuration());
                ToastImpl.this.mWindowLifecycle.register(ToastImpl.this);
                ToastImpl.this.setShow(true);
                ToastImpl toastImpl = ToastImpl.this;
                toastImpl.trySendAccessibilityEvent(toastImpl.mToast.getView());
            } catch (WindowManager.BadTokenException | IllegalStateException e2) {
                e2.printStackTrace();
            }
        }
    }

    public ToastImpl(Activity activity, CustomToast customToast) {
        this((Context) activity, customToast);
        this.mGlobalShow = false;
        this.mWindowLifecycle = new WindowLifecycle(activity);
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trySendAccessibilityEvent(View view) {
        Context context = view.getContext();
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(64);
            accessibilityEventObtain.setClassName(Toast.class.getName());
            accessibilityEventObtain.setPackageName(context.getPackageName());
            view.dispatchPopulateAccessibilityEvent(accessibilityEventObtain);
            accessibilityManager.sendAccessibilityEvent(accessibilityEventObtain);
        }
    }

    public void cancel() {
        if (isShow()) {
            Handler handler = HANDLER;
            handler.removeCallbacks(this.mShowRunnable);
            if (isMainThread()) {
                this.mCancelRunnable.run();
            } else {
                handler.removeCallbacks(this.mCancelRunnable);
                handler.post(this.mCancelRunnable);
            }
        }
    }

    public boolean isShow() {
        return this.mShow;
    }

    public void setShow(boolean z2) {
        this.mShow = z2;
    }

    public void show() {
        if (isShow()) {
            return;
        }
        if (isMainThread()) {
            this.mShowRunnable.run();
            return;
        }
        Handler handler = HANDLER;
        handler.removeCallbacks(this.mShowRunnable);
        handler.post(this.mShowRunnable);
    }

    public ToastImpl(Application application, CustomToast customToast) {
        this((Context) application, customToast);
        this.mGlobalShow = true;
        this.mWindowLifecycle = new WindowLifecycle(application);
    }

    private ToastImpl(Context context, CustomToast customToast) {
        this.mShowRunnable = new AnonymousClass1();
        this.mCancelRunnable = new Runnable() { // from class: com.hjq.toast.ToastImpl.2
            @Override // java.lang.Runnable
            public void run() {
                WindowManager windowManager;
                try {
                    try {
                        windowManager = ToastImpl.this.mWindowLifecycle.getWindowManager();
                    } catch (IllegalArgumentException e2) {
                        e2.printStackTrace();
                    }
                    if (windowManager == null) {
                        return;
                    }
                    windowManager.removeViewImmediate(ToastImpl.this.mToast.getView());
                } finally {
                    ToastImpl.this.mWindowLifecycle.unregister();
                    ToastImpl.this.setShow(false);
                }
            }
        };
        this.mToast = customToast;
        this.mPackageName = context.getPackageName();
    }
}
