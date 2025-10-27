package com.ykb.ebook.weight.slider;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.FloatRange;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\b\u0010\u000b\u001a\u00020\fH\u0007J\u001a\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0005\u001a\u00020\u0006J\u001a\u0010\u0010\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0011\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/ykb/ebook/weight/slider/ThumbValueAnimation;", "Landroid/animation/ValueAnimator;", "()V", "handler", "Landroid/os/Handler;", "isThumbHidden", "", "executeHide", "", "animated", "executeShow", "getAnimatedValueAbsolute", "", "hide", "delayMillis", "", "show", "toggle", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ThumbValueAnimation extends ValueAnimator {
    public static final long DEFAULT_DURATION = 300;

    @NotNull
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean isThumbHidden;

    public ThumbValueAnimation() {
        setFloatValues(1.0f, 0.0f);
        setDuration(300L);
    }

    private final void executeHide(boolean animated) {
        this.isThumbHidden = true;
        if (animated) {
            super.start();
        } else {
            setCurrentPlayTime(getDuration());
        }
    }

    private final void executeShow(boolean animated) {
        this.isThumbHidden = false;
        if (animated) {
            super.reverse();
        } else {
            setCurrentPlayTime(0L);
        }
    }

    public static /* synthetic */ void hide$default(ThumbValueAnimation thumbValueAnimation, boolean z2, long j2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        if ((i2 & 2) != 0) {
            j2 = 0;
        }
        thumbValueAnimation.hide(z2, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hide$lambda$0(ThumbValueAnimation this$0, boolean z2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.executeHide(z2);
    }

    public static /* synthetic */ void show$default(ThumbValueAnimation thumbValueAnimation, boolean z2, long j2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        if ((i2 & 2) != 0) {
            j2 = 0;
        }
        thumbValueAnimation.show(z2, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void show$lambda$1(ThumbValueAnimation this$0, boolean z2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.executeShow(z2);
    }

    public static /* synthetic */ void toggle$default(ThumbValueAnimation thumbValueAnimation, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        thumbValueAnimation.toggle(z2);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public final float getAnimatedValueAbsolute() {
        return Float.parseFloat(getAnimatedValue().toString());
    }

    public final void hide(final boolean animated, long delayMillis) {
        this.handler.removeCallbacksAndMessages(null);
        if (isRunning()) {
            end();
        }
        if (this.isThumbHidden) {
            cancel();
        } else {
            this.handler.postDelayed(new Runnable() { // from class: com.ykb.ebook.weight.slider.c
                @Override // java.lang.Runnable
                public final void run() {
                    ThumbValueAnimation.hide$lambda$0(this.f26527c, animated);
                }
            }, delayMillis);
        }
    }

    public final boolean isThumbHidden() {
        return this.isThumbHidden && !isRunning();
    }

    public final void show(final boolean animated, long delayMillis) {
        this.handler.removeCallbacksAndMessages(null);
        if (isRunning()) {
            end();
        }
        if (this.isThumbHidden) {
            this.handler.postDelayed(new Runnable() { // from class: com.ykb.ebook.weight.slider.d
                @Override // java.lang.Runnable
                public final void run() {
                    ThumbValueAnimation.show$lambda$1(this.f26529c, animated);
                }
            }, delayMillis);
        } else {
            cancel();
        }
    }

    public final void toggle(boolean animated) {
        if (this.isThumbHidden) {
            show$default(this, animated, 0L, 2, null);
        } else {
            hide$default(this, animated, 0L, 2, null);
        }
    }
}
