package com.easefun.polyv.livecommon.ui.widget.roundview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.appcompat.widget.AppCompatImageView;
import com.easefun.polyv.livecommon.R;

/* loaded from: classes3.dex */
public class PLVRoundImageView extends AppCompatImageView {
    public static final int MODE_ALL = 1;
    public static final int MODE_BOTTOM = 5;
    public static final int MODE_LEFT = 2;
    public static final int MODE_NONE = 0;
    public static final int MODE_RIGHT = 4;
    public static final int MODE_TOP = 3;
    private int mHeight;
    private int mLastRadius;
    private Path mPath;
    private int mRadius;
    private int mRoundMode;
    private int mWidth;

    public PLVRoundImageView(Context context) {
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
        if (i2 == 1) {
            Path path = this.mPath;
            RectF rectF = new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
            int i3 = this.mRadius;
            path.addRoundRect(rectF, i3, i3, Path.Direction.CW);
            return;
        }
        if (i2 == 2) {
            Path path2 = this.mPath;
            RectF rectF2 = new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
            int i4 = this.mRadius;
            path2.addRoundRect(rectF2, new float[]{i4, i4, 0.0f, 0.0f, 0.0f, 0.0f, i4, i4}, Path.Direction.CW);
            return;
        }
        if (i2 == 3) {
            Path path3 = this.mPath;
            RectF rectF3 = new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
            int i5 = this.mRadius;
            path3.addRoundRect(rectF3, new float[]{i5, i5, i5, i5, 0.0f, 0.0f, 0.0f, 0.0f}, Path.Direction.CW);
            return;
        }
        if (i2 == 4) {
            Path path4 = this.mPath;
            RectF rectF4 = new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
            int i6 = this.mRadius;
            path4.addRoundRect(rectF4, new float[]{0.0f, 0.0f, i6, i6, i6, i6, 0.0f, 0.0f}, Path.Direction.CW);
            return;
        }
        if (i2 != 5) {
            return;
        }
        Path path5 = this.mPath;
        RectF rectF5 = new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
        int i7 = this.mRadius;
        path5.addRoundRect(rectF5, new float[]{0.0f, 0.0f, 0.0f, 0.0f, i7, i7, i7, i7}, Path.Direction.CW);
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

    public void setCornerRadius(int radius) {
        this.mRadius = radius;
    }

    public void setRoundMode(int roundMode) {
        this.mRoundMode = roundMode;
    }

    public PLVRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mRoundMode = 1;
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.PLVRoundImageView, defStyleAttr, 0);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PLVRoundImageView_radius_iv, 10);
        int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.PLVRoundImageView_mode_iv, 1);
        typedArrayObtainStyledAttributes.recycle();
        this.mRoundMode = i2;
        this.mRadius = dimensionPixelSize;
        init();
        if (this.mRoundMode == 1) {
            this.mRoundMode = 0;
            setOutlineProvider(new ViewOutlineProvider() { // from class: com.easefun.polyv.livecommon.ui.widget.roundview.PLVRoundImageView.1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, PLVRoundImageView.this.getWidth(), PLVRoundImageView.this.getHeight(), PLVRoundImageView.this.mRadius);
                }
            });
            setClipToOutline(true);
        }
    }
}
