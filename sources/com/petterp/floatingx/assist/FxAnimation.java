package com.petterp.floatingx.assist;

import android.animation.Animator;
import android.widget.FrameLayout;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bJ\r\u0010\f\u001a\u00020\rH\u0000¢\u0006\u0002\b\u000eJ\u0012\u0010\u000f\u001a\u00020\u00042\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H&J\r\u0010\u0012\u001a\u00020\rH\u0000¢\u0006\u0002\b\u0013J\u0017\u0010\u0014\u001a\u00020\u00072\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0000¢\u0006\u0002\b\u0015J\u0012\u0010\u0016\u001a\u00020\u00042\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H&J\u0017\u0010\u0017\u001a\u00020\u00072\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0000¢\u0006\u0002\b\u0018R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0019"}, d2 = {"Lcom/petterp/floatingx/assist/FxAnimation;", "", "()V", "endAnimatorJob", "Landroid/animation/Animator;", "startAnimatorJob", "animatorDuration", "", "getAnimatorDuration", "(Landroid/animation/Animator;)J", "cancelAnimation", "", "endJobIsRunning", "", "endJobIsRunning$floatingx_release", "fromAnimator", "view", "Landroid/widget/FrameLayout;", "fromJobIsRunning", "fromJobIsRunning$floatingx_release", "fromStartAnimator", "fromStartAnimator$floatingx_release", "toAnimator", "toEndAnimator", "toEndAnimator$floatingx_release", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public abstract class FxAnimation {

    @Nullable
    private Animator endAnimatorJob;

    @Nullable
    private Animator startAnimatorJob;

    private final long getAnimatorDuration(Animator animator) {
        return animator.getDuration() + animator.getStartDelay();
    }

    public final void cancelAnimation() {
        Animator animator = this.startAnimatorJob;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.endAnimatorJob;
        if (animator2 != null) {
            animator2.cancel();
        }
        this.startAnimatorJob = null;
        this.endAnimatorJob = null;
    }

    public final /* synthetic */ boolean endJobIsRunning$floatingx_release() {
        Animator animator = this.endAnimatorJob;
        if (animator == null) {
            return false;
        }
        return animator.isRunning();
    }

    @NotNull
    public abstract Animator fromAnimator(@Nullable FrameLayout view);

    public final /* synthetic */ boolean fromJobIsRunning$floatingx_release() {
        Animator animator = this.startAnimatorJob;
        if (animator == null) {
            return false;
        }
        return animator.isRunning();
    }

    public final /* synthetic */ long fromStartAnimator$floatingx_release(FrameLayout view) {
        Animator animator = this.startAnimatorJob;
        if (animator != null) {
            animator.cancel();
        }
        Animator animatorFromAnimator = fromAnimator(view);
        this.startAnimatorJob = animatorFromAnimator;
        boolean z2 = false;
        if (animatorFromAnimator != null && animatorFromAnimator.isRunning()) {
            z2 = true;
        }
        if (z2) {
            Animator animator2 = this.startAnimatorJob;
            if (animator2 == null) {
                return 0L;
            }
            return animator2.getDuration();
        }
        Animator animator3 = this.startAnimatorJob;
        if (animator3 != null) {
            animator3.start();
        }
        Animator animator4 = this.startAnimatorJob;
        if (animator4 == null) {
            return 0L;
        }
        return getAnimatorDuration(animator4);
    }

    @NotNull
    public abstract Animator toAnimator(@Nullable FrameLayout view);

    public final /* synthetic */ long toEndAnimator$floatingx_release(FrameLayout view) {
        Animator animator = this.endAnimatorJob;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = toAnimator(view);
        this.endAnimatorJob = animator2;
        boolean z2 = false;
        if (animator2 != null && animator2.isRunning()) {
            z2 = true;
        }
        if (z2) {
            Animator animator3 = this.endAnimatorJob;
            if (animator3 == null) {
                return 0L;
            }
            return animator3.getDuration();
        }
        Animator animator4 = this.endAnimatorJob;
        if (animator4 != null) {
            animator4.start();
        }
        Animator animator5 = this.endAnimatorJob;
        if (animator5 == null) {
            return 0L;
        }
        return getAnimatorDuration(animator5);
    }
}
