package com.easefun.polyv.livecommon.ui.widget.roundview;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.easefun.polyv.livecommon.R;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public class PLVRoundRectLayout extends RelativeLayout {
    public static final int MODE_ALL = 15;
    public static final int MODE_BOTTOM = 10;
    public static final int MODE_LEFT = 3;
    public static final int MODE_LEFT_BOTTOM = 2;
    public static final int MODE_LEFT_TOP = 1;
    public static final int MODE_NONE = 0;
    public static final int MODE_RIGHT = 12;
    public static final int MODE_RIGHT_BOTTOM = 8;
    public static final int MODE_RIGHT_TOP = 4;
    public static final int MODE_TOP = 5;
    private int mHeight;
    private int mLastRadius;
    private Path mPath;
    private int mRadius;
    private int mRoundMode;
    private int mWidth;
    private OnOrientationChangedListener onOrientationChangedListener;

    public interface OnOrientationChangedListener {
        void onChanged(boolean isPortrait);
    }

    public PLVRoundRectLayout(Context context) {
        this(context, null);
    }

    private void checkPathChanged() {
        if (getWidth() == this.mWidth && getHeight() == this.mHeight && this.mLastRadius == this.mRadius) {
            return;
        }
        this.mWidth = getWidth();
        this.mHeight = getHeight();
        this.mLastRadius = this.mRadius;
        this.mPath.reset();
        int i2 = this.mRoundMode;
        float f2 = (i2 & 1) != 0 ? this.mRadius : 0.0f;
        float f3 = (i2 & 2) != 0 ? this.mRadius : 0.0f;
        float f4 = (i2 & 4) != 0 ? this.mRadius : 0.0f;
        float f5 = (i2 & 8) != 0 ? this.mRadius : 0.0f;
        this.mPath.addRoundRect(new RectF(0.0f, 0.0f, this.mWidth, this.mHeight), new float[]{f2, f2, f4, f4, f5, f5, f3, f3}, Path.Direction.CCW);
    }

    private void init() {
        Path path = new Path();
        this.mPath = path;
        path.setFillType(Path.FillType.EVEN_ODD);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.mRoundMode == 0) {
            super.draw(canvas);
            return;
        }
        int iSave = canvas.save();
        checkPathChanged();
        canvas.clipPath(this.mPath);
        super.draw(canvas);
        canvas.restoreToCount(iSave);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        OnOrientationChangedListener onOrientationChangedListener = this.onOrientationChangedListener;
        if (onOrientationChangedListener != null) {
            onOrientationChangedListener.onChanged(newConfig.orientation != 2);
        }
    }

    public void setCornerRadius(int radius) {
        this.mRadius = radius;
    }

    public void setOnOrientationChangedListener(OnOrientationChangedListener li) {
        this.onOrientationChangedListener = li;
        li.onChanged(ScreenUtils.isPortrait());
    }

    public void setRoundMode(int roundMode) {
        this.mRoundMode = roundMode;
    }

    public PLVRoundRectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVRoundRectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mRoundMode = 15;
        setWillNotDraw(false);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.PLVRoundRectLayout, defStyleAttr, 0);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PLVRoundRectLayout_radius, 10);
        int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.PLVRoundRectLayout_mode, 15);
        typedArrayObtainStyledAttributes.recycle();
        this.mRoundMode = i2;
        setCornerRadius(dimensionPixelSize);
        init();
    }
}
