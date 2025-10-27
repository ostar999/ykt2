package com.petterp.floatingx.listener.control;

import android.app.Activity;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\b"}, d2 = {"Lcom/petterp/floatingx/listener/control/IFxAppControl;", "Lcom/petterp/floatingx/listener/control/IFxControl;", "detach", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "getBindActivity", "show", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public interface IFxAppControl extends IFxControl {
    void detach(@NotNull Activity activity);

    @Nullable
    Activity getBindActivity();

    void show(@NotNull Activity activity);
}
