package com.hjq.toast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import com.hjq.toast.config.IToast;
import com.hjq.toast.config.IToastStrategy;
import com.hjq.toast.config.IToastStyle;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class ToastStrategy implements IToastStrategy {
    private static final int DEFAULT_DELAY_TIMEOUT = 200;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    public static final int SHOW_STRATEGY_TYPE_IMMEDIATELY = 0;
    public static final int SHOW_STRATEGY_TYPE_QUEUE = 1;
    private Application mApplication;
    private final Object mCancelMessageToken;
    private volatile long mLastShowToastMillis;
    private final Object mShowMessageToken;
    private final int mShowStrategyType;
    private WeakReference<IToast> mToastReference;

    public class CancelToastRunnable implements Runnable {
        private CancelToastRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            IToast iToast = ToastStrategy.this.mToastReference != null ? (IToast) ToastStrategy.this.mToastReference.get() : null;
            if (iToast == null) {
                return;
            }
            iToast.cancel();
        }
    }

    public class ShowToastRunnable implements Runnable {
        private final ToastParams mToastParams;

        @Override // java.lang.Runnable
        public void run() {
            IToast iToast = ToastStrategy.this.mToastReference != null ? (IToast) ToastStrategy.this.mToastReference.get() : null;
            if (iToast != null) {
                iToast.cancel();
            }
            IToast iToastCreateToast = ToastStrategy.this.createToast(this.mToastParams.style);
            ToastStrategy.this.mToastReference = new WeakReference(iToastCreateToast);
            iToastCreateToast.setDuration(this.mToastParams.duration);
            iToastCreateToast.setText(this.mToastParams.text);
            iToastCreateToast.show();
        }

        private ShowToastRunnable(ToastParams toastParams) {
            this.mToastParams = toastParams;
        }
    }

    public ToastStrategy() {
        this(0);
    }

    @SuppressLint({"PrivateApi"})
    public boolean areNotificationsEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return ((NotificationManager) context.getSystemService(NotificationManager.class)).areNotificationsEnabled();
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        try {
            Class<?> cls = appOpsManager.getClass();
            Class<?> cls2 = Integer.TYPE;
            return ((Integer) cls.getMethod("checkOpNoThrow", cls2, cls2, String.class).invoke(appOpsManager, Integer.valueOf(((Integer) appOpsManager.getClass().getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(context.getApplicationInfo().uid), context.getPackageName())).intValue() == 0;
        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException e2) {
            e2.printStackTrace();
            return true;
        }
    }

    @Override // com.hjq.toast.config.IToastStrategy
    public void cancelToast() {
        Handler handler = HANDLER;
        handler.removeCallbacksAndMessages(this.mCancelMessageToken);
        handler.postAtTime(new CancelToastRunnable(), this.mCancelMessageToken, SystemClock.uptimeMillis());
    }

    @Override // com.hjq.toast.config.IToastStrategy
    public IToast createToast(IToastStyle<?> iToastStyle) {
        Activity foregroundActivity = ActivityStack.getInstance().getForegroundActivity();
        int i2 = Build.VERSION.SDK_INT;
        IToast globalToast = Settings.canDrawOverlays(this.mApplication) ? new GlobalToast(this.mApplication) : foregroundActivity != null ? new ActivityToast(foregroundActivity) : i2 == 25 ? new SafeToast(this.mApplication) : (i2 >= 29 || areNotificationsEnabled(this.mApplication)) ? new SystemToast(this.mApplication) : new NotificationToast(this.mApplication);
        if (isSupportToastStyle(globalToast) || !onlyShowSystemToastStyle()) {
            diyToastStyle(globalToast, iToastStyle);
        }
        return globalToast;
    }

    public void diyToastStyle(IToast iToast, IToastStyle<?> iToastStyle) {
        iToast.setView(iToastStyle.createView(this.mApplication));
        iToast.setGravity(iToastStyle.getGravity(), iToastStyle.getXOffset(), iToastStyle.getYOffset());
        iToast.setMargin(iToastStyle.getHorizontalMargin(), iToastStyle.getVerticalMargin());
    }

    public int generateToastWaitMillis(ToastParams toastParams) {
        int i2 = toastParams.duration;
        if (i2 == 0) {
            return 1000;
        }
        return i2 == 1 ? 1500 : 0;
    }

    @SuppressLint({"PrivateApi"})
    public boolean isChangeEnabledCompat(long j2) throws NoSuchMethodException, SecurityException {
        if (Build.VERSION.SDK_INT < 30) {
            return true;
        }
        try {
            Method method = Class.forName("android.app.compat.CompatChanges").getMethod("isChangeEnabled", Long.TYPE);
            method.setAccessible(true);
            return Boolean.parseBoolean(String.valueOf(method.invoke(null, Long.valueOf(j2))));
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return false;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return false;
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
            return false;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return false;
        }
    }

    public boolean isSupportToastStyle(IToast iToast) {
        return (iToast instanceof CustomToast) || Build.VERSION.SDK_INT < 30 || this.mApplication.getApplicationInfo().targetSdkVersion < 30;
    }

    public boolean onlyShowSystemToastStyle() {
        return isChangeEnabledCompat(147798919L);
    }

    @Override // com.hjq.toast.config.IToastStrategy
    public void registerStrategy(Application application) {
        this.mApplication = application;
        ActivityStack.getInstance().register(application);
    }

    @Override // com.hjq.toast.config.IToastStrategy
    public void showToast(ToastParams toastParams) {
        int i2 = this.mShowStrategyType;
        if (i2 == 0) {
            Handler handler = HANDLER;
            handler.removeCallbacksAndMessages(this.mShowMessageToken);
            handler.postAtTime(new ShowToastRunnable(toastParams), this.mShowMessageToken, SystemClock.uptimeMillis() + toastParams.delayMillis + 200);
            return;
        }
        if (i2 != 1) {
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis() + toastParams.delayMillis + 200;
        long jGenerateToastWaitMillis = generateToastWaitMillis(toastParams);
        if (jUptimeMillis < this.mLastShowToastMillis + jGenerateToastWaitMillis) {
            jUptimeMillis = this.mLastShowToastMillis + jGenerateToastWaitMillis;
        }
        HANDLER.postAtTime(new ShowToastRunnable(toastParams), this.mShowMessageToken, jUptimeMillis);
        this.mLastShowToastMillis = jUptimeMillis;
    }

    public ToastStrategy(int i2) {
        this.mShowMessageToken = new Object();
        this.mCancelMessageToken = new Object();
        this.mShowStrategyType = i2;
        if (i2 != 0 && i2 != 1) {
            throw new IllegalArgumentException("Please don't pass non-existent toast show strategy");
        }
    }
}
