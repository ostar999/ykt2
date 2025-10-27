package com.ykb.ebook.weight.slider;

import android.graphics.Canvas;
import android.graphics.RectF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\r\b\u0016\u0018\u0000 \u001f2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001fB\u0005¢\u0006\u0002\u0010\u0003J(\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J(\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fH\u0016J(\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J(\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J(\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J(\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fH\u0016J(\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0002H\u0016J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0002H\u0016J \u0010\u001b\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u0005H\u0016J\b\u0010\u001e\u001a\u00020\u0013H\u0016¨\u0006 "}, d2 = {"Lcom/ykb/ebook/weight/slider/BaseEffect;", "Lcom/ykb/ebook/weight/slider/SliderEffect;", "Lcom/ykb/ebook/weight/slider/NiftySlider;", "()V", "dispatchDrawInactiveTrackBefore", "", "slider", "canvas", "Landroid/graphics/Canvas;", "trackRect", "Landroid/graphics/RectF;", "yCenter", "", "dispatchDrawSecondaryTrackBefore", "dispatchDrawThumbBefore", "cx", "cy", "dispatchDrawTrackBefore", "drawInactiveTrackAfter", "", "drawSecondaryTrackAfter", "drawThumbAfter", "drawTrackAfter", "onDrawAfter", "onDrawBefore", "onStartTacking", "onStopTacking", "onValueChanged", "value", "fromUser", "updateDirtyData", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public class BaseEffect implements SliderEffect<NiftySlider> {
    public static final int HIGH_QUALITY_FLAGS = 5;

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public boolean dispatchDrawInactiveTrackBefore(@NotNull NiftySlider slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(slider, "slider");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        return false;
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public boolean dispatchDrawSecondaryTrackBefore(@NotNull NiftySlider slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(slider, "slider");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        return false;
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public boolean dispatchDrawThumbBefore(@NotNull NiftySlider slider, @NotNull Canvas canvas, float cx, float cy) {
        Intrinsics.checkNotNullParameter(slider, "slider");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        return false;
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public boolean dispatchDrawTrackBefore(@NotNull NiftySlider slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(slider, "slider");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        return false;
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public void drawInactiveTrackAfter(@NotNull NiftySlider slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(slider, "slider");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public void drawSecondaryTrackAfter(@NotNull NiftySlider slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(slider, "slider");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public void drawThumbAfter(@NotNull NiftySlider slider, @NotNull Canvas canvas, float cx, float cy) {
        Intrinsics.checkNotNullParameter(slider, "slider");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public void drawTrackAfter(@NotNull NiftySlider slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(slider, "slider");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public void onDrawAfter(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public void onDrawBefore(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public void onStartTacking(@NotNull NiftySlider slider) {
        Intrinsics.checkNotNullParameter(slider, "slider");
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public void onStopTacking(@NotNull NiftySlider slider) {
        Intrinsics.checkNotNullParameter(slider, "slider");
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public void onValueChanged(@NotNull NiftySlider slider, float value, boolean fromUser) {
        Intrinsics.checkNotNullParameter(slider, "slider");
    }

    @Override // com.ykb.ebook.weight.slider.SliderEffect
    public void updateDirtyData() {
    }
}
