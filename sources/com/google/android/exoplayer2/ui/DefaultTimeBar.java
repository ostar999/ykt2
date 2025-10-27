package com.google.android.exoplayer2.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes3.dex */
public class DefaultTimeBar extends View implements TimeBar {
    private static final String ACCESSIBILITY_CLASS_NAME = "android.widget.SeekBar";
    public static final int BAR_GRAVITY_BOTTOM = 1;
    public static final int BAR_GRAVITY_CENTER = 0;
    public static final int DEFAULT_AD_MARKER_COLOR = -1291845888;
    public static final int DEFAULT_AD_MARKER_WIDTH_DP = 4;
    public static final int DEFAULT_BAR_HEIGHT_DP = 4;
    public static final int DEFAULT_BUFFERED_COLOR = -855638017;
    private static final int DEFAULT_INCREMENT_COUNT = 20;
    public static final int DEFAULT_PLAYED_AD_MARKER_COLOR = 872414976;
    public static final int DEFAULT_PLAYED_COLOR = -1;
    public static final int DEFAULT_SCRUBBER_COLOR = -1;
    public static final int DEFAULT_SCRUBBER_DISABLED_SIZE_DP = 0;
    public static final int DEFAULT_SCRUBBER_DRAGGED_SIZE_DP = 16;
    public static final int DEFAULT_SCRUBBER_ENABLED_SIZE_DP = 12;
    public static final int DEFAULT_TOUCH_TARGET_HEIGHT_DP = 26;
    public static final int DEFAULT_UNPLAYED_COLOR = 872415231;
    private static final int FINE_SCRUB_RATIO = 3;
    private static final int FINE_SCRUB_Y_THRESHOLD_DP = -50;
    private static final float HIDDEN_SCRUBBER_SCALE = 0.0f;
    private static final float SHOWN_SCRUBBER_SCALE = 1.0f;
    private static final long STOP_SCRUBBING_TIMEOUT_MS = 1000;
    private int adGroupCount;

    @Nullable
    private long[] adGroupTimesMs;
    private final Paint adMarkerPaint;
    private final int adMarkerWidth;
    private final int barGravity;
    private final int barHeight;
    private final Rect bufferedBar;
    private final Paint bufferedPaint;
    private long bufferedPosition;
    private final float density;
    private long duration;
    private final int fineScrubYThreshold;
    private final StringBuilder formatBuilder;
    private final Formatter formatter;
    private int keyCountIncrement;
    private long keyTimeIncrement;
    private int lastCoarseScrubXPosition;
    private Rect lastExclusionRectangle;
    private final CopyOnWriteArraySet<TimeBar.OnScrubListener> listeners;

    @Nullable
    private boolean[] playedAdGroups;
    private final Paint playedAdMarkerPaint;
    private final Paint playedPaint;
    private long position;
    private final Rect progressBar;
    private long scrubPosition;
    private final Rect scrubberBar;
    private final int scrubberDisabledSize;
    private final int scrubberDraggedSize;

    @Nullable
    private final Drawable scrubberDrawable;
    private final int scrubberEnabledSize;
    private final int scrubberPadding;
    private boolean scrubberPaddingDisabled;
    private final Paint scrubberPaint;
    private float scrubberScale;
    private ValueAnimator scrubberScalingAnimator;
    private boolean scrubbing;
    private final Rect seekBounds;
    private final Runnable stopScrubbingRunnable;
    private final Point touchPosition;
    private final int touchTargetHeight;
    private final Paint unplayedPaint;

    public DefaultTimeBar(Context context) {
        this(context, null);
    }

    private static int dpToPx(float f2, int i2) {
        return (int) ((i2 * f2) + 0.5f);
    }

    private void drawPlayhead(Canvas canvas) {
        if (this.duration <= 0) {
            return;
        }
        Rect rect = this.scrubberBar;
        int iConstrainValue = Util.constrainValue(rect.right, rect.left, this.progressBar.right);
        int iCenterY = this.scrubberBar.centerY();
        if (this.scrubberDrawable == null) {
            canvas.drawCircle(iConstrainValue, iCenterY, (int) ((((this.scrubbing || isFocused()) ? this.scrubberDraggedSize : isEnabled() ? this.scrubberEnabledSize : this.scrubberDisabledSize) * this.scrubberScale) / 2.0f), this.scrubberPaint);
            return;
        }
        int intrinsicWidth = ((int) (r2.getIntrinsicWidth() * this.scrubberScale)) / 2;
        int intrinsicHeight = ((int) (this.scrubberDrawable.getIntrinsicHeight() * this.scrubberScale)) / 2;
        this.scrubberDrawable.setBounds(iConstrainValue - intrinsicWidth, iCenterY - intrinsicHeight, iConstrainValue + intrinsicWidth, iCenterY + intrinsicHeight);
        this.scrubberDrawable.draw(canvas);
    }

    private void drawTimeBar(Canvas canvas) {
        int iHeight = this.progressBar.height();
        int iCenterY = this.progressBar.centerY() - (iHeight / 2);
        int i2 = iHeight + iCenterY;
        if (this.duration <= 0) {
            Rect rect = this.progressBar;
            canvas.drawRect(rect.left, iCenterY, rect.right, i2, this.unplayedPaint);
            return;
        }
        Rect rect2 = this.bufferedBar;
        int i3 = rect2.left;
        int i4 = rect2.right;
        int iMax = Math.max(Math.max(this.progressBar.left, i4), this.scrubberBar.right);
        int i5 = this.progressBar.right;
        if (iMax < i5) {
            canvas.drawRect(iMax, iCenterY, i5, i2, this.unplayedPaint);
        }
        int iMax2 = Math.max(i3, this.scrubberBar.right);
        if (i4 > iMax2) {
            canvas.drawRect(iMax2, iCenterY, i4, i2, this.bufferedPaint);
        }
        if (this.scrubberBar.width() > 0) {
            Rect rect3 = this.scrubberBar;
            canvas.drawRect(rect3.left, iCenterY, rect3.right, i2, this.playedPaint);
        }
        if (this.adGroupCount == 0) {
            return;
        }
        long[] jArr = (long[]) Assertions.checkNotNull(this.adGroupTimesMs);
        boolean[] zArr = (boolean[]) Assertions.checkNotNull(this.playedAdGroups);
        int i6 = this.adMarkerWidth / 2;
        for (int i7 = 0; i7 < this.adGroupCount; i7++) {
            int iWidth = ((int) ((this.progressBar.width() * Util.constrainValue(jArr[i7], 0L, this.duration)) / this.duration)) - i6;
            Rect rect4 = this.progressBar;
            canvas.drawRect(rect4.left + Math.min(rect4.width() - this.adMarkerWidth, Math.max(0, iWidth)), iCenterY, r10 + this.adMarkerWidth, i2, zArr[i7] ? this.playedAdMarkerPaint : this.adMarkerPaint);
        }
    }

    private long getPositionIncrement() {
        long j2 = this.keyTimeIncrement;
        if (j2 != C.TIME_UNSET) {
            return j2;
        }
        long j3 = this.duration;
        if (j3 == C.TIME_UNSET) {
            return 0L;
        }
        return j3 / this.keyCountIncrement;
    }

    private String getProgressText() {
        return Util.getStringForTime(this.formatBuilder, this.formatter, this.position);
    }

    private long getScrubberPosition() {
        if (this.progressBar.width() <= 0 || this.duration == C.TIME_UNSET) {
            return 0L;
        }
        return (this.scrubberBar.width() * this.duration) / this.progressBar.width();
    }

    private boolean isInSeekBar(float f2, float f3) {
        return this.seekBounds.contains((int) f2, (int) f3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        stopScrubbing(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ValueAnimator valueAnimator) {
        this.scrubberScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate(this.seekBounds);
    }

    private void positionScrubber(float f2) {
        Rect rect = this.scrubberBar;
        Rect rect2 = this.progressBar;
        rect.right = Util.constrainValue((int) f2, rect2.left, rect2.right);
    }

    private static int pxToDp(float f2, int i2) {
        return (int) (i2 / f2);
    }

    private Point resolveRelativeTouchPosition(MotionEvent motionEvent) {
        this.touchPosition.set((int) motionEvent.getX(), (int) motionEvent.getY());
        return this.touchPosition;
    }

    private boolean scrubIncrementally(long j2) {
        long j3 = this.duration;
        if (j3 <= 0) {
            return false;
        }
        long j4 = this.scrubbing ? this.scrubPosition : this.position;
        long jConstrainValue = Util.constrainValue(j4 + j2, 0L, j3);
        if (jConstrainValue == j4) {
            return false;
        }
        if (this.scrubbing) {
            updateScrubbing(jConstrainValue);
        } else {
            startScrubbing(jConstrainValue);
        }
        update();
        return true;
    }

    private boolean setDrawableLayoutDirection(Drawable drawable) {
        return Util.SDK_INT >= 23 && setDrawableLayoutDirection(drawable, getLayoutDirection());
    }

    @RequiresApi(29)
    private void setSystemGestureExclusionRectsV29(int i2, int i3) {
        Rect rect = this.lastExclusionRectangle;
        if (rect != null && rect.width() == i2 && this.lastExclusionRectangle.height() == i3) {
            return;
        }
        Rect rect2 = new Rect(0, 0, i2, i3);
        this.lastExclusionRectangle = rect2;
        setSystemGestureExclusionRects(Collections.singletonList(rect2));
    }

    private void startScrubbing(long j2) {
        this.scrubPosition = j2;
        this.scrubbing = true;
        setPressed(true);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        Iterator<TimeBar.OnScrubListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onScrubStart(this, j2);
        }
    }

    private void stopScrubbing(boolean z2) {
        removeCallbacks(this.stopScrubbingRunnable);
        this.scrubbing = false;
        setPressed(false);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
        invalidate();
        Iterator<TimeBar.OnScrubListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onScrubStop(this, this.scrubPosition, z2);
        }
    }

    private void update() {
        this.bufferedBar.set(this.progressBar);
        this.scrubberBar.set(this.progressBar);
        long j2 = this.scrubbing ? this.scrubPosition : this.position;
        if (this.duration > 0) {
            int iWidth = (int) ((this.progressBar.width() * this.bufferedPosition) / this.duration);
            Rect rect = this.bufferedBar;
            Rect rect2 = this.progressBar;
            rect.right = Math.min(rect2.left + iWidth, rect2.right);
            int iWidth2 = (int) ((this.progressBar.width() * j2) / this.duration);
            Rect rect3 = this.scrubberBar;
            Rect rect4 = this.progressBar;
            rect3.right = Math.min(rect4.left + iWidth2, rect4.right);
        } else {
            Rect rect5 = this.bufferedBar;
            int i2 = this.progressBar.left;
            rect5.right = i2;
            this.scrubberBar.right = i2;
        }
        invalidate(this.seekBounds);
    }

    private void updateDrawableState() {
        Drawable drawable = this.scrubberDrawable;
        if (drawable != null && drawable.isStateful() && this.scrubberDrawable.setState(getDrawableState())) {
            invalidate();
        }
    }

    private void updateScrubbing(long j2) {
        if (this.scrubPosition == j2) {
            return;
        }
        this.scrubPosition = j2;
        Iterator<TimeBar.OnScrubListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onScrubMove(this, j2);
        }
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void addListener(TimeBar.OnScrubListener onScrubListener) {
        Assertions.checkNotNull(onScrubListener);
        this.listeners.add(onScrubListener);
    }

    @Override // android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public long getPreferredUpdateDelay() {
        int iPxToDp = pxToDp(this.density, this.progressBar.width());
        if (iPxToDp != 0) {
            long j2 = this.duration;
            if (j2 != 0 && j2 != C.TIME_UNSET) {
                return j2 / iPxToDp;
            }
        }
        return Long.MAX_VALUE;
    }

    public void hideScrubber(boolean z2) {
        if (this.scrubberScalingAnimator.isStarted()) {
            this.scrubberScalingAnimator.cancel();
        }
        this.scrubberPaddingDisabled = z2;
        this.scrubberScale = 0.0f;
        invalidate(this.seekBounds);
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.scrubberDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        canvas.save();
        drawTimeBar(canvas);
        drawPlayhead(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    public void onFocusChanged(boolean z2, int i2, @Nullable Rect rect) {
        super.onFocusChanged(z2, i2, rect);
        if (!this.scrubbing || z2) {
            return;
        }
        stopScrubbing(false);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (accessibilityEvent.getEventType() == 4) {
            accessibilityEvent.getText().add(getProgressText());
        }
        accessibilityEvent.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ACCESSIBILITY_CLASS_NAME);
        accessibilityNodeInfo.setContentDescription(getProgressText());
        if (this.duration <= 0) {
            return;
        }
        if (Util.SDK_INT >= 21) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
        } else {
            accessibilityNodeInfo.addAction(4096);
            accessibilityNodeInfo.addAction(8192);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:11:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0027  */
    @Override // android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(int r5, android.view.KeyEvent r6) {
        /*
            r4 = this;
            boolean r0 = r4.isEnabled()
            if (r0 == 0) goto L30
            long r0 = r4.getPositionIncrement()
            r2 = 66
            r3 = 1
            if (r5 == r2) goto L27
            switch(r5) {
                case 21: goto L13;
                case 22: goto L14;
                case 23: goto L27;
                default: goto L12;
            }
        L12:
            goto L30
        L13:
            long r0 = -r0
        L14:
            boolean r0 = r4.scrubIncrementally(r0)
            if (r0 == 0) goto L30
            java.lang.Runnable r5 = r4.stopScrubbingRunnable
            r4.removeCallbacks(r5)
            java.lang.Runnable r5 = r4.stopScrubbingRunnable
            r0 = 1000(0x3e8, double:4.94E-321)
            r4.postDelayed(r5, r0)
            return r3
        L27:
            boolean r0 = r4.scrubbing
            if (r0 == 0) goto L30
            r5 = 0
            r4.stopScrubbing(r5)
            return r3
        L30:
            boolean r5 = super.onKeyDown(r5, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.DefaultTimeBar.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    @Override // android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int paddingBottom;
        int iMax;
        int i6 = i4 - i2;
        int i7 = i5 - i3;
        int paddingLeft = getPaddingLeft();
        int paddingRight = i6 - getPaddingRight();
        int i8 = this.scrubberPaddingDisabled ? 0 : this.scrubberPadding;
        if (this.barGravity == 1) {
            paddingBottom = (i7 - getPaddingBottom()) - this.touchTargetHeight;
            int paddingBottom2 = i7 - getPaddingBottom();
            int i9 = this.barHeight;
            iMax = (paddingBottom2 - i9) - Math.max(i8 - (i9 / 2), 0);
        } else {
            paddingBottom = (i7 - this.touchTargetHeight) / 2;
            iMax = (i7 - this.barHeight) / 2;
        }
        this.seekBounds.set(paddingLeft, paddingBottom, paddingRight, this.touchTargetHeight + paddingBottom);
        Rect rect = this.progressBar;
        Rect rect2 = this.seekBounds;
        rect.set(rect2.left + i8, iMax, rect2.right - i8, this.barHeight + iMax);
        if (Util.SDK_INT >= 29) {
            setSystemGestureExclusionRectsV29(i6, i7);
        }
        update();
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i3);
        if (mode == 0) {
            size = this.touchTargetHeight;
        } else if (mode != 1073741824) {
            size = Math.min(this.touchTargetHeight, size);
        }
        setMeasuredDimension(View.MeasureSpec.getSize(i2), size);
        updateDrawableState();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i2) {
        Drawable drawable = this.scrubberDrawable;
        if (drawable == null || !setDrawableLayoutDirection(drawable, i2)) {
            return;
        }
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004e  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            boolean r0 = r7.isEnabled()
            r1 = 0
            if (r0 == 0) goto L76
            long r2 = r7.duration
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 > 0) goto L10
            goto L76
        L10:
            android.graphics.Point r0 = r7.resolveRelativeTouchPosition(r8)
            int r2 = r0.x
            int r0 = r0.y
            int r3 = r8.getAction()
            r4 = 1
            if (r3 == 0) goto L5d
            r5 = 3
            if (r3 == r4) goto L4e
            r6 = 2
            if (r3 == r6) goto L28
            if (r3 == r5) goto L4e
            goto L76
        L28:
            boolean r8 = r7.scrubbing
            if (r8 == 0) goto L76
            int r8 = r7.fineScrubYThreshold
            if (r0 >= r8) goto L3a
            int r8 = r7.lastCoarseScrubXPosition
            int r2 = r2 - r8
            int r2 = r2 / r5
            int r8 = r8 + r2
            float r8 = (float) r8
            r7.positionScrubber(r8)
            goto L40
        L3a:
            r7.lastCoarseScrubXPosition = r2
            float r8 = (float) r2
            r7.positionScrubber(r8)
        L40:
            long r0 = r7.getScrubberPosition()
            r7.updateScrubbing(r0)
            r7.update()
            r7.invalidate()
            return r4
        L4e:
            boolean r0 = r7.scrubbing
            if (r0 == 0) goto L76
            int r8 = r8.getAction()
            if (r8 != r5) goto L59
            r1 = r4
        L59:
            r7.stopScrubbing(r1)
            return r4
        L5d:
            float r8 = (float) r2
            float r0 = (float) r0
            boolean r0 = r7.isInSeekBar(r8, r0)
            if (r0 == 0) goto L76
            r7.positionScrubber(r8)
            long r0 = r7.getScrubberPosition()
            r7.startScrubbing(r0)
            r7.update()
            r7.invalidate()
            return r4
        L76:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.DefaultTimeBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i2, @Nullable Bundle bundle) {
        if (super.performAccessibilityAction(i2, bundle)) {
            return true;
        }
        if (this.duration <= 0) {
            return false;
        }
        if (i2 == 8192) {
            if (scrubIncrementally(-getPositionIncrement())) {
                stopScrubbing(false);
            }
        } else {
            if (i2 != 4096) {
                return false;
            }
            if (scrubIncrementally(getPositionIncrement())) {
                stopScrubbing(false);
            }
        }
        sendAccessibilityEvent(4);
        return true;
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void removeListener(TimeBar.OnScrubListener onScrubListener) {
        this.listeners.remove(onScrubListener);
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setAdGroupTimesMs(@Nullable long[] jArr, @Nullable boolean[] zArr, int i2) {
        Assertions.checkArgument(i2 == 0 || !(jArr == null || zArr == null));
        this.adGroupCount = i2;
        this.adGroupTimesMs = jArr;
        this.playedAdGroups = zArr;
        update();
    }

    public void setAdMarkerColor(@ColorInt int i2) {
        this.adMarkerPaint.setColor(i2);
        invalidate(this.seekBounds);
    }

    public void setBufferedColor(@ColorInt int i2) {
        this.bufferedPaint.setColor(i2);
        invalidate(this.seekBounds);
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setBufferedPosition(long j2) {
        if (this.bufferedPosition == j2) {
            return;
        }
        this.bufferedPosition = j2;
        update();
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setDuration(long j2) {
        if (this.duration == j2) {
            return;
        }
        this.duration = j2;
        if (this.scrubbing && j2 == C.TIME_UNSET) {
            stopScrubbing(true);
        }
        update();
    }

    @Override // android.view.View, com.google.android.exoplayer2.ui.TimeBar
    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        if (!this.scrubbing || z2) {
            return;
        }
        stopScrubbing(true);
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setKeyCountIncrement(int i2) {
        Assertions.checkArgument(i2 > 0);
        this.keyCountIncrement = i2;
        this.keyTimeIncrement = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setKeyTimeIncrement(long j2) {
        Assertions.checkArgument(j2 > 0);
        this.keyCountIncrement = -1;
        this.keyTimeIncrement = j2;
    }

    public void setPlayedAdMarkerColor(@ColorInt int i2) {
        this.playedAdMarkerPaint.setColor(i2);
        invalidate(this.seekBounds);
    }

    public void setPlayedColor(@ColorInt int i2) {
        this.playedPaint.setColor(i2);
        invalidate(this.seekBounds);
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setPosition(long j2) {
        if (this.position == j2) {
            return;
        }
        this.position = j2;
        setContentDescription(getProgressText());
        update();
    }

    public void setScrubberColor(@ColorInt int i2) {
        this.scrubberPaint.setColor(i2);
        invalidate(this.seekBounds);
    }

    public void setUnplayedColor(@ColorInt int i2) {
        this.unplayedPaint.setColor(i2);
        invalidate(this.seekBounds);
    }

    public void showScrubber() {
        if (this.scrubberScalingAnimator.isStarted()) {
            this.scrubberScalingAnimator.cancel();
        }
        this.scrubberPaddingDisabled = false;
        this.scrubberScale = 1.0f;
        invalidate(this.seekBounds);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private static boolean setDrawableLayoutDirection(Drawable drawable, int i2) {
        return Util.SDK_INT >= 23 && drawable.setLayoutDirection(i2);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, attributeSet);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet, int i2, @Nullable AttributeSet attributeSet2) {
        this(context, attributeSet, i2, attributeSet2, 0);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet, int i2, @Nullable AttributeSet attributeSet2, int i3) {
        super(context, attributeSet, i2);
        this.seekBounds = new Rect();
        this.progressBar = new Rect();
        this.bufferedBar = new Rect();
        this.scrubberBar = new Rect();
        Paint paint = new Paint();
        this.playedPaint = paint;
        Paint paint2 = new Paint();
        this.bufferedPaint = paint2;
        Paint paint3 = new Paint();
        this.unplayedPaint = paint3;
        Paint paint4 = new Paint();
        this.adMarkerPaint = paint4;
        Paint paint5 = new Paint();
        this.playedAdMarkerPaint = paint5;
        Paint paint6 = new Paint();
        this.scrubberPaint = paint6;
        paint6.setAntiAlias(true);
        this.listeners = new CopyOnWriteArraySet<>();
        this.touchPosition = new Point();
        float f2 = context.getResources().getDisplayMetrics().density;
        this.density = f2;
        this.fineScrubYThreshold = dpToPx(f2, FINE_SCRUB_Y_THRESHOLD_DP);
        int iDpToPx = dpToPx(f2, 4);
        int iDpToPx2 = dpToPx(f2, 26);
        int iDpToPx3 = dpToPx(f2, 4);
        int iDpToPx4 = dpToPx(f2, 12);
        int iDpToPx5 = dpToPx(f2, 0);
        int iDpToPx6 = dpToPx(f2, 16);
        if (attributeSet2 != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet2, R.styleable.DefaultTimeBar, i2, i3);
            try {
                Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.DefaultTimeBar_scrubber_drawable);
                this.scrubberDrawable = drawable;
                if (drawable != null) {
                    setDrawableLayoutDirection(drawable);
                    iDpToPx2 = Math.max(drawable.getMinimumHeight(), iDpToPx2);
                }
                this.barHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_bar_height, iDpToPx);
                this.touchTargetHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_touch_target_height, iDpToPx2);
                this.barGravity = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_bar_gravity, 0);
                this.adMarkerWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_ad_marker_width, iDpToPx3);
                this.scrubberEnabledSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_scrubber_enabled_size, iDpToPx4);
                this.scrubberDisabledSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_scrubber_disabled_size, iDpToPx5);
                this.scrubberDraggedSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_scrubber_dragged_size, iDpToPx6);
                int i4 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_played_color, -1);
                int i5 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_scrubber_color, -1);
                int i6 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_buffered_color, DEFAULT_BUFFERED_COLOR);
                int i7 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_unplayed_color, DEFAULT_UNPLAYED_COLOR);
                int i8 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_ad_marker_color, DEFAULT_AD_MARKER_COLOR);
                int i9 = typedArrayObtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_played_ad_marker_color, DEFAULT_PLAYED_AD_MARKER_COLOR);
                paint.setColor(i4);
                paint6.setColor(i5);
                paint2.setColor(i6);
                paint3.setColor(i7);
                paint4.setColor(i8);
                paint5.setColor(i9);
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        } else {
            this.barHeight = iDpToPx;
            this.touchTargetHeight = iDpToPx2;
            this.barGravity = 0;
            this.adMarkerWidth = iDpToPx3;
            this.scrubberEnabledSize = iDpToPx4;
            this.scrubberDisabledSize = iDpToPx5;
            this.scrubberDraggedSize = iDpToPx6;
            paint.setColor(-1);
            paint6.setColor(-1);
            paint2.setColor(DEFAULT_BUFFERED_COLOR);
            paint3.setColor(DEFAULT_UNPLAYED_COLOR);
            paint4.setColor(DEFAULT_AD_MARKER_COLOR);
            paint5.setColor(DEFAULT_PLAYED_AD_MARKER_COLOR);
            this.scrubberDrawable = null;
        }
        StringBuilder sb = new StringBuilder();
        this.formatBuilder = sb;
        this.formatter = new Formatter(sb, Locale.getDefault());
        this.stopScrubbingRunnable = new Runnable() { // from class: com.google.android.exoplayer2.ui.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f6931c.lambda$new$0();
            }
        };
        Drawable drawable2 = this.scrubberDrawable;
        if (drawable2 != null) {
            this.scrubberPadding = (drawable2.getMinimumWidth() + 1) / 2;
        } else {
            this.scrubberPadding = (Math.max(this.scrubberDisabledSize, Math.max(this.scrubberEnabledSize, this.scrubberDraggedSize)) + 1) / 2;
        }
        this.scrubberScale = 1.0f;
        ValueAnimator valueAnimator = new ValueAnimator();
        this.scrubberScalingAnimator = valueAnimator;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.c
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f6933c.lambda$new$1(valueAnimator2);
            }
        });
        this.duration = C.TIME_UNSET;
        this.keyTimeIncrement = C.TIME_UNSET;
        this.keyCountIncrement = 20;
        setFocusable(true);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    public void hideScrubber(long j2) {
        if (this.scrubberScalingAnimator.isStarted()) {
            this.scrubberScalingAnimator.cancel();
        }
        this.scrubberScalingAnimator.setFloatValues(this.scrubberScale, 0.0f);
        this.scrubberScalingAnimator.setDuration(j2);
        this.scrubberScalingAnimator.start();
    }

    public void showScrubber(long j2) {
        if (this.scrubberScalingAnimator.isStarted()) {
            this.scrubberScalingAnimator.cancel();
        }
        this.scrubberPaddingDisabled = false;
        this.scrubberScalingAnimator.setFloatValues(this.scrubberScale, 1.0f);
        this.scrubberScalingAnimator.setDuration(j2);
        this.scrubberScalingAnimator.start();
    }
}
