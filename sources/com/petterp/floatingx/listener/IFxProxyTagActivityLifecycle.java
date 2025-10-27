package com.petterp.floatingx.listener;

import android.app.Activity;
import android.os.Bundle;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u001a\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u000e"}, d2 = {"Lcom/petterp/floatingx/listener/IFxProxyTagActivityLifecycle;", "", "onCreated", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "bundle", "Landroid/os/Bundle;", "onDestroyed", "onPaused", "onResumes", "onSaveInstanceState", "onStarted", "onStopped", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public interface IFxProxyTagActivityLifecycle {
    void onCreated(@NotNull Activity activity, @Nullable Bundle bundle);

    void onDestroyed(@NotNull Activity activity);

    void onPaused(@NotNull Activity activity);

    void onResumes(@NotNull Activity activity);

    void onSaveInstanceState(@NotNull Activity activity, @Nullable Bundle bundle);

    void onStarted(@NotNull Activity activity);

    void onStopped(@NotNull Activity activity);
}
