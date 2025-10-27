package com.blankj.utilcode.util;

import android.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.SystemClock;
import android.util.Log;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import com.blankj.utilcode.util.ShadowUtils;

/* loaded from: classes2.dex */
public class ClickUtils {
    private static final long DEBOUNCING_DEFAULT_VALUE = 1000;
    private static final float PRESSED_BG_ALPHA_DEFAULT_VALUE = 0.9f;
    private static final int PRESSED_BG_ALPHA_STYLE = 4;
    private static final float PRESSED_BG_DARK_DEFAULT_VALUE = 0.9f;
    private static final int PRESSED_BG_DARK_STYLE = 5;
    private static final float PRESSED_VIEW_ALPHA_DEFAULT_VALUE = 0.8f;
    private static final int PRESSED_VIEW_ALPHA_SRC_TAG = -3;
    private static final int PRESSED_VIEW_ALPHA_TAG = -2;
    private static final float PRESSED_VIEW_SCALE_DEFAULT_VALUE = -0.06f;
    private static final int PRESSED_VIEW_SCALE_TAG = -1;
    private static final long TIP_DURATION = 2000;
    private static int sClickCount;
    private static long sLastClickMillis;

    public interface Back2HomeFriendlyListener {
        public static final Back2HomeFriendlyListener DEFAULT = new Back2HomeFriendlyListener() { // from class: com.blankj.utilcode.util.ClickUtils.Back2HomeFriendlyListener.1
            @Override // com.blankj.utilcode.util.ClickUtils.Back2HomeFriendlyListener
            public void dismiss() {
                UtilsBridge.toastCancel();
            }

            @Override // com.blankj.utilcode.util.ClickUtils.Back2HomeFriendlyListener
            public void show(CharSequence charSequence, long j2) {
                UtilsBridge.toastShowShort(charSequence);
            }
        };

        void dismiss();

        void show(CharSequence charSequence, long j2);
    }

    public static class ClickDrawableWrapper extends ShadowUtils.DrawableWrapper {
        private BitmapDrawable mBitmapDrawable;
        private Paint mColorPaint;

        public ClickDrawableWrapper(Drawable drawable) {
            super(drawable);
            this.mBitmapDrawable = null;
            this.mColorPaint = null;
            if (drawable instanceof ColorDrawable) {
                Paint paint = new Paint(5);
                this.mColorPaint = paint;
                paint.setColor(((ColorDrawable) drawable).getColor());
            }
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.mBitmapDrawable == null) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(getBounds().width(), getBounds().height(), Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(bitmapCreateBitmap);
                if (this.mColorPaint != null) {
                    canvas2.drawRect(getBounds(), this.mColorPaint);
                } else {
                    super.draw(canvas2);
                }
                BitmapDrawable bitmapDrawable = new BitmapDrawable(Resources.getSystem(), bitmapCreateBitmap);
                this.mBitmapDrawable = bitmapDrawable;
                bitmapDrawable.setBounds(getBounds());
            }
            this.mBitmapDrawable.draw(canvas);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public void setAlpha(int i2) {
            super.setAlpha(i2);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            super.setColorFilter(colorFilter);
        }
    }

    public static abstract class OnDebouncingClickListener implements View.OnClickListener {
        private static final Runnable ENABLE_AGAIN = new Runnable() { // from class: com.blankj.utilcode.util.ClickUtils.OnDebouncingClickListener.1
            @Override // java.lang.Runnable
            public void run() {
                boolean unused = OnDebouncingClickListener.mEnabled = true;
            }
        };
        private static boolean mEnabled = true;
        private long mDuration;
        private boolean mIsGlobal;

        public OnDebouncingClickListener() {
            this(true, 1000L);
        }

        private static boolean isValid(@NonNull View view, long j2) {
            return UtilsBridge.isValid(view, j2);
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (!this.mIsGlobal) {
                if (isValid(view, this.mDuration)) {
                    onDebouncingClick(view);
                }
            } else if (mEnabled) {
                mEnabled = false;
                view.postDelayed(ENABLE_AGAIN, this.mDuration);
                onDebouncingClick(view);
            }
        }

        public abstract void onDebouncingClick(View view);

        public OnDebouncingClickListener(boolean z2) {
            this(z2, 1000L);
        }

        public OnDebouncingClickListener(long j2) {
            this(true, j2);
        }

        public OnDebouncingClickListener(boolean z2, long j2) {
            this.mIsGlobal = z2;
            this.mDuration = j2;
        }
    }

    public static abstract class OnMultiClickListener implements View.OnClickListener {
        private static final long INTERVAL_DEFAULT_VALUE = 666;
        private int mClickCount;
        private final long mClickInterval;
        private long mLastClickTime;
        private final int mTriggerClickCount;

        public OnMultiClickListener(int i2) {
            this(i2, INTERVAL_DEFAULT_VALUE);
        }

        public abstract void onBeforeTriggerClick(View view, int i2);

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (this.mTriggerClickCount <= 1) {
                onTriggerClick(view);
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.mLastClickTime < this.mClickInterval) {
                int i2 = this.mClickCount + 1;
                this.mClickCount = i2;
                int i3 = this.mTriggerClickCount;
                if (i2 == i3) {
                    onTriggerClick(view);
                } else if (i2 < i3) {
                    onBeforeTriggerClick(view, i2);
                } else {
                    this.mClickCount = 1;
                    onBeforeTriggerClick(view, 1);
                }
            } else {
                this.mClickCount = 1;
                onBeforeTriggerClick(view, 1);
            }
            this.mLastClickTime = jCurrentTimeMillis;
        }

        public abstract void onTriggerClick(View view);

        public OnMultiClickListener(int i2, long j2) {
            this.mTriggerClickCount = i2;
            this.mClickInterval = j2;
        }
    }

    public static class OnUtilsTouchListener implements View.OnTouchListener {

        public static class LazyHolder {
            private static final OnUtilsTouchListener INSTANCE = new OnUtilsTouchListener();

            private LazyHolder() {
            }
        }

        public static OnUtilsTouchListener getInstance() {
            return LazyHolder.INSTANCE;
        }

        private void processAlpha(View view, boolean z2) {
            Object tag = view.getTag(z2 ? -2 : -3);
            if (tag instanceof Float) {
                view.setAlpha(((Float) tag).floatValue());
            }
        }

        private void processScale(View view, boolean z2) {
            Object tag = view.getTag(-1);
            if (tag instanceof Float) {
                float fFloatValue = z2 ? 1.0f + ((Float) tag).floatValue() : 1.0f;
                view.animate().scaleX(fFloatValue).scaleY(fFloatValue).setDuration(200L).start();
            }
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == 0) {
                processScale(view, true);
                processAlpha(view, true);
            } else if (action == 1 || action == 3) {
                processScale(view, false);
                processAlpha(view, false);
            }
            return false;
        }

        private OnUtilsTouchListener() {
        }
    }

    private ClickUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static void applyDebouncing(View[] viewArr, boolean z2, @IntRange(from = 0) long j2, final View.OnClickListener onClickListener) {
        if (viewArr == null || viewArr.length == 0 || onClickListener == null) {
            return;
        }
        for (View view : viewArr) {
            if (view != null) {
                view.setOnClickListener(new OnDebouncingClickListener(z2, j2) { // from class: com.blankj.utilcode.util.ClickUtils.1
                    @Override // com.blankj.utilcode.util.ClickUtils.OnDebouncingClickListener
                    public void onDebouncingClick(View view2) {
                        onClickListener.onClick(view2);
                    }
                });
            }
        }
    }

    public static void applyGlobalDebouncing(View view, View.OnClickListener onClickListener) {
        applyGlobalDebouncing(new View[]{view}, onClickListener);
    }

    public static void applyPressedBgAlpha(View view) {
        applyPressedBgAlpha(view, 0.9f);
    }

    public static void applyPressedBgDark(View view) {
        applyPressedBgDark(view, 0.9f);
    }

    private static void applyPressedBgStyle(View view, int i2, float f2) {
        if (view == null) {
            return;
        }
        Drawable background = view.getBackground();
        int i3 = -i2;
        Object tag = view.getTag(i3);
        if (tag instanceof Drawable) {
            ViewCompat.setBackground(view, (Drawable) tag);
            return;
        }
        Drawable drawableCreateStyleDrawable = createStyleDrawable(background, i2, f2);
        ViewCompat.setBackground(view, drawableCreateStyleDrawable);
        view.setTag(i3, drawableCreateStyleDrawable);
    }

    public static void applyPressedViewAlpha(View... viewArr) {
        applyPressedViewAlpha(viewArr, (float[]) null);
    }

    public static void applyPressedViewScale(View... viewArr) {
        applyPressedViewScale(viewArr, (float[]) null);
    }

    public static void applySingleDebouncing(View view, View.OnClickListener onClickListener) {
        applySingleDebouncing(new View[]{view}, onClickListener);
    }

    public static void back2HomeFriendly(CharSequence charSequence) {
        back2HomeFriendly(charSequence, 2000L, Back2HomeFriendlyListener.DEFAULT);
    }

    private static Drawable createAlphaDrawable(Drawable drawable, float f2) {
        ClickDrawableWrapper clickDrawableWrapper = new ClickDrawableWrapper(drawable);
        clickDrawableWrapper.setAlpha((int) (f2 * 255.0f));
        return clickDrawableWrapper;
    }

    private static Drawable createDarkDrawable(Drawable drawable, float f2) {
        ClickDrawableWrapper clickDrawableWrapper = new ClickDrawableWrapper(drawable);
        clickDrawableWrapper.setColorFilter(getDarkColorFilter(f2));
        return clickDrawableWrapper;
    }

    private static Drawable createStyleDrawable(Drawable drawable, int i2, float f2) {
        if (drawable == null) {
            drawable = new ColorDrawable(0);
        }
        if (drawable.getConstantState() == null) {
            return drawable;
        }
        Drawable drawableMutate = drawable.getConstantState().newDrawable().mutate();
        if (i2 == 4) {
            drawableMutate = createAlphaDrawable(drawableMutate, f2);
        } else if (i2 == 5) {
            drawableMutate = createDarkDrawable(drawableMutate, f2);
        }
        Drawable drawableCreateAlphaDrawable = createAlphaDrawable(drawable.getConstantState().newDrawable().mutate(), 0.5f);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_pressed}, drawableMutate);
        stateListDrawable.addState(new int[]{-16842910}, drawableCreateAlphaDrawable);
        stateListDrawable.addState(StateSet.WILD_CARD, drawable);
        return stateListDrawable;
    }

    public static void expandClickArea(@NonNull View view, int i2) {
        expandClickArea(view, i2, i2, i2, i2);
    }

    private static ColorMatrixColorFilter getDarkColorFilter(float f2) {
        return new ColorMatrixColorFilter(new ColorMatrix(new float[]{f2, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f2, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f2, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 0.0f}));
    }

    public static void applyGlobalDebouncing(View view, @IntRange(from = 0) long j2, View.OnClickListener onClickListener) {
        applyGlobalDebouncing(new View[]{view}, j2, onClickListener);
    }

    public static void applyPressedBgAlpha(View view, float f2) {
        applyPressedBgStyle(view, 4, f2);
    }

    public static void applyPressedBgDark(View view, float f2) {
        applyPressedBgStyle(view, 5, f2);
    }

    public static void applyPressedViewAlpha(View[] viewArr, float[] fArr) {
        if (viewArr == null || viewArr.length == 0) {
            return;
        }
        for (int i2 = 0; i2 < viewArr.length; i2++) {
            if (fArr == null || i2 >= fArr.length) {
                applyPressedViewAlpha(viewArr[i2], PRESSED_VIEW_ALPHA_DEFAULT_VALUE);
            } else {
                applyPressedViewAlpha(viewArr[i2], fArr[i2]);
            }
        }
    }

    public static void applyPressedViewScale(View[] viewArr, float[] fArr) {
        if (viewArr == null || viewArr.length == 0) {
            return;
        }
        for (int i2 = 0; i2 < viewArr.length; i2++) {
            if (fArr == null || i2 >= fArr.length) {
                applyPressedViewScale(viewArr[i2], PRESSED_VIEW_SCALE_DEFAULT_VALUE);
            } else {
                applyPressedViewScale(viewArr[i2], fArr[i2]);
            }
        }
    }

    public static void applySingleDebouncing(View view, @IntRange(from = 0) long j2, View.OnClickListener onClickListener) {
        applySingleDebouncing(new View[]{view}, j2, onClickListener);
    }

    public static void back2HomeFriendly(@NonNull CharSequence charSequence, long j2, @NonNull Back2HomeFriendlyListener back2HomeFriendlyListener) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (Math.abs(jElapsedRealtime - sLastClickMillis) >= j2) {
            sClickCount = 1;
            back2HomeFriendlyListener.show(charSequence, j2);
            sLastClickMillis = jElapsedRealtime;
            return;
        }
        int i2 = sClickCount + 1;
        sClickCount = i2;
        if (i2 == 2) {
            UtilsBridge.startHomeActivity();
            back2HomeFriendlyListener.dismiss();
            sLastClickMillis = 0L;
        }
    }

    public static void expandClickArea(@NonNull final View view, final int i2, final int i3, final int i4, final int i5) {
        final View view2 = (View) view.getParent();
        if (view2 == null) {
            Log.e("ClickUtils", "expandClickArea must have parent view.");
        } else {
            view2.post(new Runnable() { // from class: com.blankj.utilcode.util.ClickUtils.2
                @Override // java.lang.Runnable
                public void run() {
                    Rect rect = new Rect();
                    view.getHitRect(rect);
                    rect.top -= i2;
                    rect.bottom += i5;
                    rect.left -= i3;
                    rect.right += i4;
                    view2.setTouchDelegate(new TouchDelegate(rect, view));
                }
            });
        }
    }

    public static void applyGlobalDebouncing(View[] viewArr, View.OnClickListener onClickListener) {
        applyGlobalDebouncing(viewArr, 1000L, onClickListener);
    }

    public static void applySingleDebouncing(View[] viewArr, View.OnClickListener onClickListener) {
        applySingleDebouncing(viewArr, 1000L, onClickListener);
    }

    public static void applyGlobalDebouncing(View[] viewArr, @IntRange(from = 0) long j2, View.OnClickListener onClickListener) {
        applyDebouncing(viewArr, true, j2, onClickListener);
    }

    public static void applySingleDebouncing(View[] viewArr, @IntRange(from = 0) long j2, View.OnClickListener onClickListener) {
        applyDebouncing(viewArr, false, j2, onClickListener);
    }

    public static void applyPressedViewAlpha(View view, float f2) {
        if (view == null) {
            return;
        }
        view.setTag(-2, Float.valueOf(f2));
        view.setTag(-3, Float.valueOf(view.getAlpha()));
        view.setClickable(true);
        view.setOnTouchListener(OnUtilsTouchListener.getInstance());
    }

    public static void applyPressedViewScale(View view, float f2) {
        if (view == null) {
            return;
        }
        view.setTag(-1, Float.valueOf(f2));
        view.setClickable(true);
        view.setOnTouchListener(OnUtilsTouchListener.getInstance());
    }
}
