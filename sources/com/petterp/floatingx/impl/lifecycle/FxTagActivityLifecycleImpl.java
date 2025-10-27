package com.petterp.floatingx.impl.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.petterp.floatingx.listener.IFxProxyTagActivityLifecycle;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001a\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u000f"}, d2 = {"Lcom/petterp/floatingx/impl/lifecycle/FxTagActivityLifecycleImpl;", "Lcom/petterp/floatingx/listener/IFxProxyTagActivityLifecycle;", "()V", "onCreated", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "bundle", "Landroid/os/Bundle;", "onDestroyed", "onPaused", "onResumes", "onSaveInstanceState", "onStarted", "onStopped", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public class FxTagActivityLifecycleImpl implements IFxProxyTagActivityLifecycle {
    @Override // com.petterp.floatingx.listener.IFxProxyTagActivityLifecycle
    public void onCreated(@NotNull Activity activity, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // com.petterp.floatingx.listener.IFxProxyTagActivityLifecycle
    public void onDestroyed(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // com.petterp.floatingx.listener.IFxProxyTagActivityLifecycle
    public void onPaused(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // com.petterp.floatingx.listener.IFxProxyTagActivityLifecycle
    public void onResumes(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // com.petterp.floatingx.listener.IFxProxyTagActivityLifecycle
    public void onSaveInstanceState(@NotNull Activity activity, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // com.petterp.floatingx.listener.IFxProxyTagActivityLifecycle
    public void onStarted(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // com.petterp.floatingx.listener.IFxProxyTagActivityLifecycle
    public void onStopped(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }
}
