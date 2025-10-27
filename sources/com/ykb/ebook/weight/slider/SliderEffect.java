package com.ykb.ebook.weight.slider;

import android.graphics.Canvas;
import android.graphics.RectF;
import androidx.exifinterface.media.ExifInterface;
import com.ykb.ebook.weight.slider.BaseSlider;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0010\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J-\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&¢\u0006\u0002\u0010\rJ-\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&¢\u0006\u0002\u0010\rJ-\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fH&¢\u0006\u0002\u0010\u0012J-\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&¢\u0006\u0002\u0010\rJ-\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&¢\u0006\u0002\u0010\u0016J-\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&¢\u0006\u0002\u0010\u0016J-\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fH&¢\u0006\u0002\u0010\u0019J-\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&¢\u0006\u0002\u0010\u0016J \u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&J \u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&J\u0015\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u001eJ\u0015\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u001eJ%\u0010 \u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u0005H&¢\u0006\u0002\u0010#J\b\u0010$\u001a\u00020\u0015H&¨\u0006%"}, d2 = {"Lcom/ykb/ebook/weight/slider/SliderEffect;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/ykb/ebook/weight/slider/BaseSlider;", "", "dispatchDrawInactiveTrackBefore", "", "slider", "canvas", "Landroid/graphics/Canvas;", "trackRect", "Landroid/graphics/RectF;", "yCenter", "", "(Lcom/ykb/ebook/weight/slider/BaseSlider;Landroid/graphics/Canvas;Landroid/graphics/RectF;F)Z", "dispatchDrawSecondaryTrackBefore", "dispatchDrawThumbBefore", "cx", "cy", "(Lcom/ykb/ebook/weight/slider/BaseSlider;Landroid/graphics/Canvas;FF)Z", "dispatchDrawTrackBefore", "drawInactiveTrackAfter", "", "(Lcom/ykb/ebook/weight/slider/BaseSlider;Landroid/graphics/Canvas;Landroid/graphics/RectF;F)V", "drawSecondaryTrackAfter", "drawThumbAfter", "(Lcom/ykb/ebook/weight/slider/BaseSlider;Landroid/graphics/Canvas;FF)V", "drawTrackAfter", "onDrawAfter", "onDrawBefore", "onStartTacking", "(Lcom/ykb/ebook/weight/slider/BaseSlider;)V", "onStopTacking", "onValueChanged", "value", "fromUser", "(Lcom/ykb/ebook/weight/slider/BaseSlider;FZ)V", "updateDirtyData", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public interface SliderEffect<T extends BaseSlider> {
    boolean dispatchDrawInactiveTrackBefore(@NotNull T slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    boolean dispatchDrawSecondaryTrackBefore(@NotNull T slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    boolean dispatchDrawThumbBefore(@NotNull T slider, @NotNull Canvas canvas, float cx, float cy);

    boolean dispatchDrawTrackBefore(@NotNull T slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    void drawInactiveTrackAfter(@NotNull T slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    void drawSecondaryTrackAfter(@NotNull T slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    void drawThumbAfter(@NotNull T slider, @NotNull Canvas canvas, float cx, float cy);

    void drawTrackAfter(@NotNull T slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    void onDrawAfter(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    void onDrawBefore(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    void onStartTacking(@NotNull T slider);

    void onStopTacking(@NotNull T slider);

    void onValueChanged(@NotNull T slider, float value, boolean fromUser);

    void updateDirtyData();
}
