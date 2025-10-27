package com.petterp.floatingx.util;

import android.R;
import android.app.Activity;
import android.widget.FrameLayout;
import com.petterp.floatingx.impl.lifecycle.FxLifecycleCallbackImpl;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u00018@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00018@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\"\u001a\u0010\b\u001a\u0004\u0018\u00010\u0005*\u00020\u00018@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"topActivity", "Landroid/app/Activity;", "getTopActivity", "()Landroid/app/Activity;", "contentView", "Landroid/widget/FrameLayout;", "getContentView", "(Landroid/app/Activity;)Landroid/widget/FrameLayout;", "decorView", "getDecorView", "floatingx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxUiExtKt {
    @Nullable
    public static final FrameLayout getContentView(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        try {
            return (FrameLayout) activity.getWindow().getDecorView().findViewById(R.id.content);
        } catch (Exception unused) {
            return null;
        }
    }

    @Nullable
    public static final FrameLayout getDecorView(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        try {
            return (FrameLayout) activity.getWindow().getDecorView();
        } catch (Exception unused) {
            return null;
        }
    }

    @Nullable
    public static final Activity getTopActivity() {
        return FxLifecycleCallbackImpl.INSTANCE.getTopActivity();
    }
}
