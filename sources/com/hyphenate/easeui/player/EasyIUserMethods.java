package com.hyphenate.easeui.player;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
interface EasyIUserMethods {
    void disableControls();

    void enableControls(boolean z2);

    @CheckResult
    int getCurrentPosition();

    @CheckResult
    int getDuration();

    void hideControls();

    @CheckResult
    boolean isControlsShown();

    @CheckResult
    boolean isPlaying();

    @CheckResult
    boolean isPrepared();

    void pause();

    void release();

    void reset();

    void seekTo(@IntRange(from = 0, to = 2147483647L) int i2);

    void setAutoFullscreen(boolean z2);

    void setAutoPlay(boolean z2);

    void setCallback(@NonNull EasyVideoCallback easyVideoCallback);

    void setHideControlsOnPlay(boolean z2);

    void setInitialPosition(@IntRange(from = 0, to = 2147483647L) int i2);

    void setLoop(boolean z2);

    void setPauseDrawable(@NonNull Drawable drawable);

    void setPauseDrawableRes(@DrawableRes int i2);

    void setPlayDrawable(@NonNull Drawable drawable);

    void setPlayDrawableRes(@DrawableRes int i2);

    void setProgressCallback(@NonNull EasyVideoProgressCallback easyVideoProgressCallback);

    void setSource(@NonNull Uri uri);

    void setThemeColor(@ColorInt int i2);

    void setThemeColorRes(@ColorRes int i2);

    void setVolume(@FloatRange(from = 0.0d, to = 1.0d) float f2, @FloatRange(from = 0.0d, to = 1.0d) float f3);

    void showControls();

    void start();

    void stop();

    void toggleControls();
}
