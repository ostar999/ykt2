package com.petterp.floatingx.impl.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.petterp.floatingx.FloatingX;
import com.petterp.floatingx.impl.control.FxAppControlImpl;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\tH\u0002J\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u000fH\u0016J\u0010\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0019"}, d2 = {"Lcom/petterp/floatingx/impl/lifecycle/FxLifecycleCallbackImpl;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "()V", "fxList", "", "Lcom/petterp/floatingx/impl/control/FxAppControlImpl;", "getFxList", "()Ljava/util/Collection;", "isFxsNotAllow", "", "onActivityCreated", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "savedInstanceState", "Landroid/os/Bundle;", "onActivityDestroyed", "onActivityPaused", "onActivityResumed", "onActivitySaveInstanceState", "outState", "onActivityStarted", "onActivityStopped", "updateTopActivity", "Companion", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxLifecycleCallbackImpl implements Application.ActivityLifecycleCallbacks {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private static WeakReference<Activity> topActivity;

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u0004\u0018\u00010\u0005J\r\u0010\u000b\u001a\u00020\fH\u0000¢\u0006\u0002\b\rJ\u0017\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0005H\u0000¢\u0006\u0002\b\u0010R\"\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lcom/petterp/floatingx/impl/lifecycle/FxLifecycleCallbackImpl$Companion;", "", "()V", "topActivity", "Ljava/lang/ref/WeakReference;", "Landroid/app/Activity;", "getTopActivity$floatingx_release", "()Ljava/lang/ref/WeakReference;", "setTopActivity$floatingx_release", "(Ljava/lang/ref/WeakReference;)V", "getTopActivity", "releaseTopActivity", "", "releaseTopActivity$floatingx_release", "updateTopActivity", PushConstants.INTENT_ACTIVITY_NAME, "updateTopActivity$floatingx_release", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final /* synthetic */ Activity getTopActivity() {
            WeakReference<Activity> topActivity$floatingx_release = getTopActivity$floatingx_release();
            if (topActivity$floatingx_release == null) {
                return null;
            }
            return topActivity$floatingx_release.get();
        }

        @Nullable
        public final WeakReference<Activity> getTopActivity$floatingx_release() {
            return FxLifecycleCallbackImpl.topActivity;
        }

        public final /* synthetic */ void releaseTopActivity$floatingx_release() {
            WeakReference<Activity> topActivity$floatingx_release = getTopActivity$floatingx_release();
            if (topActivity$floatingx_release != null) {
                topActivity$floatingx_release.clear();
            }
            setTopActivity$floatingx_release(null);
        }

        public final void setTopActivity$floatingx_release(@Nullable WeakReference<Activity> weakReference) {
            FxLifecycleCallbackImpl.topActivity = weakReference;
        }

        public final /* synthetic */ void updateTopActivity$floatingx_release(Activity activity) {
            if (activity != null) {
                WeakReference<Activity> topActivity$floatingx_release = getTopActivity$floatingx_release();
                if ((topActivity$floatingx_release == null ? null : topActivity$floatingx_release.get()) == activity) {
                    return;
                }
                setTopActivity$floatingx_release(new WeakReference<>(activity));
            }
        }
    }

    private final Collection<FxAppControlImpl> getFxList() {
        return FloatingX.INSTANCE.getFxList$floatingx_release().values();
    }

    private final boolean isFxsNotAllow() {
        return getFxList().isEmpty();
    }

    private final void updateTopActivity(Activity activity) {
        WeakReference<Activity> weakReference = topActivity;
        if (Intrinsics.areEqual(weakReference == null ? null : weakReference.get(), activity)) {
            return;
        }
        topActivity = new WeakReference<>(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(@NotNull Activity activity, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (isFxsNotAllow()) {
            return;
        }
        Iterator<FxAppControlImpl> it = getFxList().iterator();
        while (it.hasNext()) {
            it.next().onActivityCreated(activity, savedInstanceState);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (isFxsNotAllow()) {
            return;
        }
        Iterator<FxAppControlImpl> it = getFxList().iterator();
        while (it.hasNext()) {
            it.next().onActivityDestroyed(activity);
        }
        WeakReference<Activity> weakReference = topActivity;
        if ((weakReference == null ? null : weakReference.get()) == activity) {
            WeakReference<Activity> weakReference2 = topActivity;
            if (weakReference2 != null) {
                weakReference2.clear();
            }
            topActivity = null;
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (isFxsNotAllow()) {
            return;
        }
        Iterator<FxAppControlImpl> it = getFxList().iterator();
        while (it.hasNext()) {
            it.next().onActivityPaused(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        updateTopActivity(activity);
        if (isFxsNotAllow()) {
            return;
        }
        Iterator<FxAppControlImpl> it = getFxList().iterator();
        while (it.hasNext()) {
            it.next().onActivityResumed(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle outState) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(outState, "outState");
        if (isFxsNotAllow()) {
            return;
        }
        Iterator<FxAppControlImpl> it = getFxList().iterator();
        while (it.hasNext()) {
            it.next().onActivitySaveInstanceState(activity, outState);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (isFxsNotAllow()) {
            return;
        }
        Iterator<FxAppControlImpl> it = getFxList().iterator();
        while (it.hasNext()) {
            it.next().onActivityStarted(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (isFxsNotAllow()) {
            return;
        }
        Iterator<FxAppControlImpl> it = getFxList().iterator();
        while (it.hasNext()) {
            it.next().onActivityStopped(activity);
        }
    }
}
