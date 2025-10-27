package com.easefun.polyv.livecommon.ui.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.appcompat.widget.AppCompatImageView;
import com.easefun.polyv.livecommon.R;

/* loaded from: classes3.dex */
public class PLVRedPointImageView extends AppCompatImageView {
    private static final int POS_LEFT_BOTTOM = 3;
    private static final int POS_LEFT_TOP = 0;
    private static final int POS_RIGHT_BOTTOM = 2;
    private static final int POS_RIGHT_TOP = 1;
    private boolean drawRedPoint;
    private int lastMeasureHeight;
    private int lastMeasureWidth;

    @ColorInt
    private int redPointColor;

    @Px
    private int redPointMarginHorizontal;

    @Px
    private int redPointMarginVertical;
    private final Paint redPointPaint;
    private int redPointPos;

    @Px
    private int redPointRadius;
    private final RectF redPointRect;

    public PLVRedPointImageView(Context context) {
        super(context);
        this.redPointPos = 0;
        this.redPointMarginHorizontal = 0;
        this.redPointMarginVertical = 0;
        this.redPointRadius = 0;
        this.redPointColor = 0;
        this.drawRedPoint = false;
        this.redPointRect = new RectF();
        this.redPointPaint = new Paint();
        init(null);
    }

    private void init(@Nullable AttributeSet attrs) {
        parseAttrs(attrs);
        updateRedPointPaint();
        this.redPointPaint.setAntiAlias(true);
    }

    private void parseAttrs(@Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.PLVRedPointImageView);
        this.redPointPos = typedArrayObtainStyledAttributes.getInt(R.styleable.PLVRedPointImageView_plvRedPointPos, this.redPointPos);
        this.redPointMarginHorizontal = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PLVRedPointImageView_plvRedPointMarginHorizontal, this.redPointMarginHorizontal);
        this.redPointMarginVertical = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PLVRedPointImageView_plvRedPointMarginVertical, this.redPointMarginVertical);
        this.redPointRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PLVRedPointImageView_plvRedPointRadius, this.redPointRadius);
        this.redPointColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVRedPointImageView_plvRedPointColor, this.redPointColor);
        this.drawRedPoint = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PLVRedPointImageView_plvDrawRedPoint, this.drawRedPoint);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void updateRedPointPaint() {
        this.redPointPaint.setColor(this.redPointColor);
    }

    private void updateRedPointRect() {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int i8 = this.redPointPos;
        if (i8 == 0) {
            int i9 = this.redPointMarginHorizontal;
            i2 = this.redPointRadius;
            i3 = i9 + i2;
            i4 = this.redPointMarginVertical;
        } else {
            if (i8 != 1) {
                if (i8 == 2) {
                    int i10 = measuredWidth - this.redPointMarginHorizontal;
                    i6 = this.redPointRadius;
                    i3 = i10 - i6;
                    i7 = this.redPointMarginVertical;
                } else {
                    if (i8 != 3) {
                        i3 = 0;
                        i5 = 0;
                        RectF rectF = this.redPointRect;
                        int i11 = this.redPointRadius;
                        rectF.set(i3 - i11, i5 - i11, i3 + i11, i5 + i11);
                    }
                    int i12 = this.redPointMarginHorizontal;
                    i6 = this.redPointRadius;
                    i3 = i12 + i6;
                    i7 = this.redPointMarginVertical;
                }
                i5 = (measuredHeight - i7) - i6;
                RectF rectF2 = this.redPointRect;
                int i112 = this.redPointRadius;
                rectF2.set(i3 - i112, i5 - i112, i3 + i112, i5 + i112);
            }
            int i13 = measuredWidth - this.redPointMarginHorizontal;
            i2 = this.redPointRadius;
            i3 = i13 - i2;
            i4 = this.redPointMarginVertical;
        }
        i5 = i2 + i4;
        RectF rectF22 = this.redPointRect;
        int i1122 = this.redPointRadius;
        rectF22.set(i3 - i1122, i5 - i1122, i3 + i1122, i5 + i1122);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.drawRedPoint) {
            canvas.drawOval(this.redPointRect, this.redPointPaint);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.lastMeasureWidth == getMeasuredWidth() && this.lastMeasureHeight == getMeasuredHeight()) {
            return;
        }
        this.lastMeasureWidth = getMeasuredWidth();
        this.lastMeasureHeight = getMeasuredHeight();
        updateRedPointRect();
    }

    public void setDrawRedPoint(boolean drawRedPoint) {
        this.drawRedPoint = drawRedPoint;
        invalidate();
    }

    public void setRedPointColor(@ColorInt int redPointColor) {
        this.redPointColor = redPointColor;
        updateRedPointPaint();
        invalidate();
    }

    public PLVRedPointImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.redPointPos = 0;
        this.redPointMarginHorizontal = 0;
        this.redPointMarginVertical = 0;
        this.redPointRadius = 0;
        this.redPointColor = 0;
        this.drawRedPoint = false;
        this.redPointRect = new RectF();
        this.redPointPaint = new Paint();
        init(attrs);
    }

    public PLVRedPointImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.redPointPos = 0;
        this.redPointMarginHorizontal = 0;
        this.redPointMarginVertical = 0;
        this.redPointRadius = 0;
        this.redPointColor = 0;
        this.drawRedPoint = false;
        this.redPointRect = new RectF();
        this.redPointPaint = new Paint();
        init(attrs);
    }
}
