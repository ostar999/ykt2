package com.angcyo.tablayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Landroid/animation/ValueAnimator;", "invoke"}, k = 3, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class DslTabLayout$_scrollAnimator$2 extends Lambda implements Function0<ValueAnimator> {
    final /* synthetic */ DslTabLayout this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DslTabLayout$_scrollAnimator$2(DslTabLayout dslTabLayout) {
        super(0);
        this.this$0 = dslTabLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$1$lambda$0(DslTabLayout this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        this$0._onAnimateValue(((Float) animatedValue).floatValue());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final ValueAnimator invoke() {
        ValueAnimator valueAnimator = new ValueAnimator();
        final DslTabLayout dslTabLayout = this.this$0;
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(dslTabLayout.getTabIndicatorAnimationDuration());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.angcyo.tablayout.d
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                DslTabLayout$_scrollAnimator$2.invoke$lambda$1$lambda$0(dslTabLayout, valueAnimator2);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.angcyo.tablayout.DslTabLayout$_scrollAnimator$2$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(@NotNull Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                dslTabLayout._onAnimateValue(1.0f);
                onAnimationEnd(animation);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(@NotNull Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                dslTabLayout._onAnimateEnd();
            }
        });
        return valueAnimator;
    }
}
