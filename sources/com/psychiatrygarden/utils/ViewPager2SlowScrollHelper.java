package com.psychiatrygarden.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0014H\u0002J\u000e\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cJ\"\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/psychiatrygarden/utils/ViewPager2SlowScrollHelper;", "", "vp", "Landroidx/viewpager2/widget/ViewPager2;", "duration", "", "(Landroidx/viewpager2/widget/ViewPager2;J)V", "getDuration", "()J", "setDuration", "(J)V", "getRelativeScrollPositionMethod", "Ljava/lang/reflect/Method;", "mAccessibilityProvider", "mScrollEventAdapter", "notifyProgrammaticScrollMethod", "onSetNewCurrentItemMethod", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getSlowLinearSmoothScroller", "Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "replaceDecelerateInterpolator", "", "linearSmoothScroller", "setCurrentItem", "item", "", "smoothScrollToPosition", "layoutManager", "Landroidx/recyclerview/widget/RecyclerView$LayoutManager;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ViewPager2SlowScrollHelper {
    private long duration;

    @NotNull
    private final Method getRelativeScrollPositionMethod;

    @NotNull
    private final Object mAccessibilityProvider;

    @NotNull
    private final Object mScrollEventAdapter;

    @NotNull
    private final Method notifyProgrammaticScrollMethod;

    @NotNull
    private final Method onSetNewCurrentItemMethod;

    @NotNull
    private final RecyclerView recyclerView;

    @NotNull
    private final ViewPager2 vp;

    public ViewPager2SlowScrollHelper(@NotNull ViewPager2 vp, long j2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(vp, "vp");
        this.vp = vp;
        this.duration = j2;
        Field declaredField = ViewPager2.class.getDeclaredField("mRecyclerView");
        declaredField.setAccessible(true);
        Object obj = declaredField.get(vp);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView");
        RecyclerView recyclerView = (RecyclerView) obj;
        this.recyclerView = recyclerView;
        recyclerView.getLayoutManager();
        Field declaredField2 = ViewPager2.class.getDeclaredField("mAccessibilityProvider");
        declaredField2.setAccessible(true);
        Object obj2 = declaredField2.get(vp);
        Intrinsics.checkNotNullExpressionValue(obj2, "mAccessibilityProviderField.get(vp)");
        this.mAccessibilityProvider = obj2;
        Method declaredMethod = obj2.getClass().getDeclaredMethod("onSetNewCurrentItem", new Class[0]);
        Intrinsics.checkNotNullExpressionValue(declaredMethod, "mAccessibilityProvider.j…od(\"onSetNewCurrentItem\")");
        this.onSetNewCurrentItemMethod = declaredMethod;
        declaredMethod.setAccessible(true);
        Field declaredField3 = ViewPager2.class.getDeclaredField("mScrollEventAdapter");
        declaredField3.setAccessible(true);
        Object obj3 = declaredField3.get(vp);
        Intrinsics.checkNotNullExpressionValue(obj3, "mScrollEventAdapterField.get(vp)");
        this.mScrollEventAdapter = obj3;
        Method declaredMethod2 = obj3.getClass().getDeclaredMethod("getRelativeScrollPosition", new Class[0]);
        Intrinsics.checkNotNullExpressionValue(declaredMethod2, "mScrollEventAdapter.java…tRelativeScrollPosition\")");
        this.getRelativeScrollPositionMethod = declaredMethod2;
        declaredMethod2.setAccessible(true);
        Method declaredMethod3 = obj3.getClass().getDeclaredMethod("notifyProgrammaticScroll", Integer.TYPE, Boolean.TYPE);
        Intrinsics.checkNotNullExpressionValue(declaredMethod3, "mScrollEventAdapter.java…ean::class.java\n        )");
        this.notifyProgrammaticScrollMethod = declaredMethod3;
        declaredMethod3.setAccessible(true);
    }

    private final RecyclerView.SmoothScroller getSlowLinearSmoothScroller(Context context) {
        return new LinearSmoothScroller(context) { // from class: com.psychiatrygarden.utils.ViewPager2SlowScrollHelper.getSlowLinearSmoothScroller.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public float calculateSpeedPerPixel(@Nullable DisplayMetrics displayMetrics) {
                return this.getDuration() / this.vp.getWidth();
            }
        };
    }

    private final void replaceDecelerateInterpolator(RecyclerView.SmoothScroller linearSmoothScroller) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Field declaredField = LinearSmoothScroller.class.getDeclaredField("mDecelerateInterpolator");
        declaredField.setAccessible(true);
        declaredField.set(linearSmoothScroller, new DecelerateInterpolator() { // from class: com.psychiatrygarden.utils.ViewPager2SlowScrollHelper.replaceDecelerateInterpolator.1
            @Override // android.view.animation.DecelerateInterpolator, android.animation.TimeInterpolator
            public float getInterpolation(float input) {
                return input;
            }
        });
    }

    private final void smoothScrollToPosition(int item, Context context, RecyclerView.LayoutManager layoutManager) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        RecyclerView.SmoothScroller slowLinearSmoothScroller = getSlowLinearSmoothScroller(context);
        replaceDecelerateInterpolator(slowLinearSmoothScroller);
        slowLinearSmoothScroller.setTargetPosition(item);
        if (layoutManager != null) {
            layoutManager.startSmoothScroll(slowLinearSmoothScroller);
        }
    }

    public final long getDuration() {
        return this.duration;
    }

    public final void setCurrentItem(int item) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, InvocationTargetException {
        RecyclerView.Adapter adapter = this.vp.getAdapter();
        Intrinsics.checkNotNull(adapter, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.Adapter<*>");
        if (adapter.getItemCount() <= 0) {
            return;
        }
        int iCoerceAtMost = RangesKt___RangesKt.coerceAtMost(RangesKt___RangesKt.coerceAtLeast(item, 0), adapter.getItemCount() - 1);
        if ((iCoerceAtMost == this.vp.getCurrentItem() && this.vp.getScrollState() == 0) || iCoerceAtMost == this.vp.getCurrentItem()) {
            return;
        }
        this.vp.setCurrentItem(iCoerceAtMost);
        this.onSetNewCurrentItemMethod.invoke(this.mAccessibilityProvider, new Object[0]);
        this.notifyProgrammaticScrollMethod.invoke(this.mScrollEventAdapter, Integer.valueOf(iCoerceAtMost), Boolean.TRUE);
        Context context = this.vp.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "vp.context");
        smoothScrollToPosition(iCoerceAtMost, context, this.recyclerView.getLayoutManager());
    }

    public final void setDuration(long j2) {
        this.duration = j2;
    }
}
