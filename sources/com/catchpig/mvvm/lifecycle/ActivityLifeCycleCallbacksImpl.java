package com.catchpig.mvvm.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.aliyun.player.aliyunplayerbase.bean.AliyunVideoListBean;
import com.catchpig.mvvm.manager.KTActivityManager;
import com.catchpig.utils.ext.LogExtKt;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000  2\u00020\u0001:\u0002 !B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eJ\u001a\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u0017H\u0016J\u0010\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u000e\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u001e\u0010\u000b\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/catchpig/mvvm/lifecycle/ActivityLifeCycleCallbacksImpl;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "()V", AliyunVideoListBean.STATUS_CENSOR_WAIT, "Ljava/lang/Runnable;", "handler", "Landroid/os/Handler;", "isBackground", "", "()Z", "<set-?>", "isForeground", "listeners", "", "Lcom/catchpig/mvvm/lifecycle/ActivityLifeCycleCallbacksImpl$Listener;", "paused", "addListener", "", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "onActivityCreated", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "savedInstanceState", "Landroid/os/Bundle;", "onActivityDestroyed", "onActivityPaused", "onActivityResumed", "onActivitySaveInstanceState", "outState", "onActivityStarted", "onActivityStopped", "removeListener", "Companion", "Listener", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nActivityLifeCycleCallbacksImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActivityLifeCycleCallbacksImpl.kt\ncom/catchpig/mvvm/lifecycle/ActivityLifeCycleCallbacksImpl\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,144:1\n1#2:145\n*E\n"})
/* loaded from: classes2.dex */
public final class ActivityLifeCycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    public static final long CHECK_DELAY = 100;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String TAG = "ActivityManagerLifeCycleCallbacksImpl";

    @Nullable
    private static ActivityLifeCycleCallbacksImpl instance;

    @Nullable
    private Runnable check;
    private boolean isForeground;
    private boolean paused = true;

    @NotNull
    private final Handler handler = new Handler();

    @NotNull
    private final List<Listener> listeners = new CopyOnWriteArrayList();

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&¨\u0006\u0005"}, d2 = {"Lcom/catchpig/mvvm/lifecycle/ActivityLifeCycleCallbacksImpl$Listener;", "", "onBecameBackground", "", "onBecameForeground", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface Listener {
        void onBecameBackground();

        void onBecameForeground();
    }

    public final void addListener(@NotNull Listener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listeners.add(listener);
    }

    public final boolean isBackground() {
        return !this.isForeground;
    }

    /* renamed from: isForeground, reason: from getter */
    public final boolean getIsForeground() {
        return this.isForeground;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(@NotNull Activity activity, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        LogExtKt.logd(activity.getClass().getSimpleName() + "->onCreate", TAG);
        KTActivityManager.INSTANCE.getInstance().addActivity(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        LogExtKt.logd(activity.getClass().getSimpleName() + "->onActivityDestroyed", TAG);
        KTActivityManager.INSTANCE.getInstance().removeActivity(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        LogExtKt.logd(activity.getClass().getSimpleName() + "->onPause", TAG);
        this.paused = true;
        Runnable runnable = this.check;
        if (runnable != null) {
            Handler handler = this.handler;
            Intrinsics.checkNotNull(runnable);
            handler.removeCallbacks(runnable);
        }
        Handler handler2 = this.handler;
        Runnable runnable2 = new Runnable() { // from class: com.catchpig.mvvm.lifecycle.ActivityLifeCycleCallbacksImpl.onActivityPaused.1
            @Override // java.lang.Runnable
            public void run() {
                if (ActivityLifeCycleCallbacksImpl.this.getIsForeground() && ActivityLifeCycleCallbacksImpl.this.paused) {
                    ActivityLifeCycleCallbacksImpl.this.isForeground = false;
                    Iterator it = ActivityLifeCycleCallbacksImpl.this.listeners.iterator();
                    while (it.hasNext()) {
                        try {
                            ((Listener) it.next()).onBecameBackground();
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        };
        this.check = runnable2;
        handler2.postDelayed(runnable2, 100L);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        LogExtKt.logd(activity.getClass().getSimpleName() + "->onResume", TAG);
        this.paused = false;
        boolean z2 = this.isForeground ^ true;
        this.isForeground = true;
        Runnable runnable = this.check;
        if (runnable != null) {
            Handler handler = this.handler;
            Intrinsics.checkNotNull(runnable);
            handler.removeCallbacks(runnable);
        }
        if (z2) {
            Iterator<Listener> it = this.listeners.iterator();
            while (it.hasNext()) {
                try {
                    it.next().onBecameForeground();
                } catch (Exception unused) {
                }
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle outState) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(outState, "outState");
        LogExtKt.logd(activity.getClass().getSimpleName() + "->onActivitySaveInstanceState", TAG);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        LogExtKt.logd(activity.getClass().getSimpleName() + "->onStart", TAG);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        LogExtKt.logd(activity.getClass().getSimpleName() + "->onStop", TAG);
    }

    public final void removeListener(@NotNull Listener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listeners.remove(listener);
    }

    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u0004\u0018\u00010\nJ\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\rH\u0086\u0002J\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086\u0002J\u0010\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/catchpig/mvvm/lifecycle/ActivityLifeCycleCallbacksImpl$Companion;", "", "()V", "CHECK_DELAY", "", "TAG", "", "getTAG", "()Ljava/lang/String;", "instance", "Lcom/catchpig/mvvm/lifecycle/ActivityLifeCycleCallbacksImpl;", "get", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "ctx", "Landroid/content/Context;", "init", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Nullable
        public final ActivityLifeCycleCallbacksImpl get(@NotNull Application application) {
            Intrinsics.checkNotNullParameter(application, "application");
            if (ActivityLifeCycleCallbacksImpl.instance == null) {
                init(application);
            }
            return ActivityLifeCycleCallbacksImpl.instance;
        }

        @NotNull
        public final String getTAG() {
            return ActivityLifeCycleCallbacksImpl.TAG;
        }

        @Nullable
        public final ActivityLifeCycleCallbacksImpl init(@NotNull Application application) {
            Intrinsics.checkNotNullParameter(application, "application");
            if (ActivityLifeCycleCallbacksImpl.instance == null) {
                ActivityLifeCycleCallbacksImpl.instance = new ActivityLifeCycleCallbacksImpl();
                application.registerActivityLifecycleCallbacks(ActivityLifeCycleCallbacksImpl.instance);
            }
            return ActivityLifeCycleCallbacksImpl.instance;
        }

        @Nullable
        public final ActivityLifeCycleCallbacksImpl get(@NotNull Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            if (ActivityLifeCycleCallbacksImpl.instance == null) {
                Context applicationContext = ctx.getApplicationContext();
                if (applicationContext instanceof Application) {
                    init((Application) applicationContext);
                }
                throw new IllegalStateException("Foreground is not initialised and cannot obtain the Application object");
            }
            return ActivityLifeCycleCallbacksImpl.instance;
        }

        @Nullable
        public final ActivityLifeCycleCallbacksImpl get() {
            if (ActivityLifeCycleCallbacksImpl.instance != null) {
                return ActivityLifeCycleCallbacksImpl.instance;
            }
            throw new IllegalStateException("Foreground is not initialised - invoke at least once with parameterised init/get".toString());
        }
    }
}
