package com.catchpig.mvvm.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.catchpig.utils.ext.LogExtKt;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0016J\"\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0018\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0016J\"\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0018\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\nH\u0016J\u0018\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J*\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0018\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u001e"}, d2 = {"Lcom/catchpig/mvvm/lifecycle/FragmentLifeCycleCallbacksImpl;", "Landroidx/fragment/app/FragmentManager$FragmentLifecycleCallbacks;", "()V", "onFragmentActivityCreated", "", "fm", "Landroidx/fragment/app/FragmentManager;", "f", "Landroidx/fragment/app/Fragment;", "savedInstanceState", "Landroid/os/Bundle;", "onFragmentAttached", d.R, "Landroid/content/Context;", "onFragmentCreated", "onFragmentDestroyed", "onFragmentDetached", "onFragmentPaused", "onFragmentPreAttached", "onFragmentPreCreated", "onFragmentResumed", "onFragmentSaveInstanceState", "outState", "onFragmentStarted", "onFragmentStopped", "onFragmentViewCreated", "v", "Landroid/view/View;", "onFragmentViewDestroyed", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public class FragmentLifeCycleCallbacksImpl extends FragmentManager.FragmentLifecycleCallbacks {

    @NotNull
    private static final String TAG = "FragmentLifeCycleCallbacksImpl";

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentActivityCreated(@NotNull FragmentManager fm, @NotNull Fragment f2, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onActivityCreated", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentAttached(@NotNull FragmentManager fm, @NotNull Fragment f2, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        Intrinsics.checkNotNullParameter(context, "context");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onAttached", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentCreated(@NotNull FragmentManager fm, @NotNull Fragment f2, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onCreated", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentDestroyed(@NotNull FragmentManager fm, @NotNull Fragment f2) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onDestroyed", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentDetached(@NotNull FragmentManager fm, @NotNull Fragment f2) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onDetached", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentPaused(@NotNull FragmentManager fm, @NotNull Fragment f2) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onPaused", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentPreAttached(@NotNull FragmentManager fm, @NotNull Fragment f2, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        Intrinsics.checkNotNullParameter(context, "context");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onPreAttached", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentPreCreated(@NotNull FragmentManager fm, @NotNull Fragment f2, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onPreCreated", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentResumed(@NotNull FragmentManager fm, @NotNull Fragment f2) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onResumed", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentSaveInstanceState(@NotNull FragmentManager fm, @NotNull Fragment f2, @NotNull Bundle outState) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        Intrinsics.checkNotNullParameter(outState, "outState");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onSaveInstanceState", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentStarted(@NotNull FragmentManager fm, @NotNull Fragment f2) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onStarted", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentStopped(@NotNull FragmentManager fm, @NotNull Fragment f2) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onStopped", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentViewCreated(@NotNull FragmentManager fm, @NotNull Fragment f2, @NotNull View v2, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        Intrinsics.checkNotNullParameter(v2, "v");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onViewCreated", TAG);
    }

    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
    public void onFragmentViewDestroyed(@NotNull FragmentManager fm, @NotNull Fragment f2) {
        Intrinsics.checkNotNullParameter(fm, "fm");
        Intrinsics.checkNotNullParameter(f2, "f");
        LogExtKt.logd(f2.getClass().getSimpleName() + "->onViewDestroyed", TAG);
    }
}
