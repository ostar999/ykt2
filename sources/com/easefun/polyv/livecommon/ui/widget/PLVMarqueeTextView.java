package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import androidx.appcompat.widget.AppCompatTextView;
import com.easefun.polyv.livecommon.R;

/* loaded from: classes3.dex */
public class PLVMarqueeTextView extends AppCompatTextView {
    private static final int FIRST_SCROLL_DELAY_DEFAULT = 1000;
    private static final int ROLLING_INTERVAL_DEFAULT = 10000;
    public static final int SCROLL_FOREVER = 100;
    public static final int SCROLL_ONCE = 101;
    private boolean isStopToCenter;
    private boolean mFirst;
    private int mFirstScrollDelay;
    private boolean mPaused;
    private int mRollingInterval;
    private int mScrollMode;
    private Scroller mScroller;
    private int mXPaused;
    private OnGetRollDurationListener onGetRollDurationListener;
    private int rollDuration;
    private Runnable runnable;
    private boolean useTotalTime;

    public interface OnGetRollDurationListener {
        void onFirstGetRollDuration(int rollDuration);
    }

    public PLVMarqueeTextView(Context context) {
        this(context, null);
    }

    private void callOnFirstGetRollDuration(int rollDuration) {
        OnGetRollDurationListener onGetRollDurationListener = this.onGetRollDurationListener;
        if (onGetRollDurationListener != null) {
            onGetRollDurationListener.onFirstGetRollDuration(rollDuration);
            this.onGetRollDurationListener = null;
        }
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.PLVMarqueeTextView);
        this.mRollingInterval = typedArrayObtainStyledAttributes.getInt(R.styleable.PLVMarqueeTextView_scroll_interval, 10000);
        this.mScrollMode = typedArrayObtainStyledAttributes.getInt(R.styleable.PLVMarqueeTextView_scroll_mode, 100);
        this.mFirstScrollDelay = typedArrayObtainStyledAttributes.getInt(R.styleable.PLVMarqueeTextView_scroll_first_delay, 1000);
        typedArrayObtainStyledAttributes.recycle();
        setSingleLine();
        setEllipsize(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scroll() {
        final int i2;
        if (this.mXPaused == 0) {
            this.mXPaused = getWidth() * (-1);
        }
        int iCalculateScrollingLen = calculateScrollingLen();
        int i3 = iCalculateScrollingLen - this.mXPaused;
        double width = ((this.mRollingInterval * i3) * 1.0d) / iCalculateScrollingLen;
        if (iCalculateScrollingLen < getWidth()) {
            width /= getWidth() / iCalculateScrollingLen;
        }
        this.rollDuration = (int) width;
        if (!this.isStopToCenter || this.mXPaused >= 0) {
            i2 = i3;
        } else {
            int iAbs = iCalculateScrollingLen >= getWidth() ? Math.abs(this.mXPaused) : Math.abs(this.mXPaused) - ((getWidth() - iCalculateScrollingLen) / 2);
            this.rollDuration = (int) (this.rollDuration / ((i3 * 1.0f) / iAbs));
            i2 = iAbs;
        }
        if (this.isStopToCenter) {
            setGravity(3);
        }
        if (!this.useTotalTime && !this.isStopToCenter) {
            if (iCalculateScrollingLen > getWidth()) {
                this.rollDuration = (int) (this.mRollingInterval * (((iCalculateScrollingLen * 1.0f) / getWidth()) + 1.0f));
            } else {
                this.rollDuration = this.mRollingInterval;
            }
        }
        if (this.mFirst) {
            callOnFirstGetRollDuration(this.rollDuration);
            Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVMarqueeTextView.2
                @Override // java.lang.Runnable
                public void run() {
                    PLVMarqueeTextView.this.setVisibility(0);
                    PLVMarqueeTextView.this.mScroller.startScroll(PLVMarqueeTextView.this.mXPaused, 0, i2, 0, PLVMarqueeTextView.this.rollDuration);
                    PLVMarqueeTextView.this.invalidate();
                    PLVMarqueeTextView.this.mPaused = false;
                }
            };
            this.runnable = runnable;
            postDelayed(runnable, this.mFirstScrollDelay);
            return;
        }
        callOnFirstGetRollDuration(this.rollDuration);
        this.mScroller.startScroll(this.mXPaused, 0, i2, 0, this.rollDuration);
        invalidate();
        this.mPaused = false;
    }

    public int calculateScrollingLen() {
        TextPaint paint = getPaint();
        Rect rect = new Rect();
        String string = getText().toString();
        paint.getTextBounds(string, 0, string.length(), rect);
        return rect.width();
    }

    @Override // android.widget.TextView, android.view.View
    public void computeScroll() {
        super.computeScroll();
        Scroller scroller = this.mScroller;
        if (scroller == null) {
            return;
        }
        if (scroller.isFinished() && !this.mPaused) {
            if (this.mScrollMode == 101) {
                stopScroll();
                return;
            }
            this.mPaused = true;
            this.mXPaused = getWidth() * (-1);
            this.mFirst = false;
            resumeScroll();
        }
        Scroller scroller2 = this.mScroller;
        if (scroller2 == null || !scroller2.computeScrollOffset()) {
            return;
        }
        scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
        invalidate();
    }

    public int getRndDuration() {
        return this.mRollingInterval;
    }

    public int getRollDuration() {
        return this.rollDuration;
    }

    public int getScrollFirstDelay() {
        return this.mFirstScrollDelay;
    }

    public int getScrollMode() {
        return this.mScrollMode;
    }

    public boolean isPaused() {
        return this.mPaused;
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.runnable);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        Scroller scroller;
        super.onLayout(changed, left, top2, right, bottom);
        if (this.isStopToCenter && (scroller = this.mScroller) != null && scroller.isFinished()) {
            setGravity(17);
        }
    }

    public void pauseScroll() {
        Scroller scroller = this.mScroller;
        if (scroller == null || this.mPaused) {
            return;
        }
        this.mPaused = true;
        this.mXPaused = scroller.getCurrX();
        this.mScroller.abortAnimation();
    }

    public void resumeScroll() {
        if (this.mPaused) {
            setHorizontallyScrolling(true);
            if (this.mScroller == null) {
                Scroller scroller = new Scroller(getContext(), new LinearInterpolator(getContext(), null));
                this.mScroller = scroller;
                setScroller(scroller);
            }
            Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVMarqueeTextView.1
                @Override // java.lang.Runnable
                public void run() {
                    PLVMarqueeTextView.this.scroll();
                }
            };
            this.runnable = runnable;
            post(runnable);
        }
    }

    public void setOnGetRollDurationListener(OnGetRollDurationListener onGetRollDurationListener) {
        this.onGetRollDurationListener = onGetRollDurationListener;
    }

    public void setRndDuration(int duration) {
        this.mRollingInterval = duration;
    }

    public void setScrollFirstDelay(int delay) {
        this.mFirstScrollDelay = delay;
    }

    public void setScrollMode(int mode) {
        this.mScrollMode = mode;
    }

    public void setStopToCenter(boolean isStopToCenter) {
        this.isStopToCenter = isStopToCenter;
    }

    public void startScroll() {
        this.mPaused = true;
        this.mFirst = true;
        resumeScroll();
    }

    public void stopScroll() {
        Scroller scroller = this.mScroller;
        if (scroller == null) {
            return;
        }
        this.mPaused = true;
        if (!this.isStopToCenter) {
            scroller.startScroll(0, 0, 0, 0, 0);
        }
        this.mXPaused = 0;
    }

    public PLVMarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mXPaused = 0;
        this.mPaused = true;
        this.mFirst = true;
        initView(context, attrs);
    }
}
