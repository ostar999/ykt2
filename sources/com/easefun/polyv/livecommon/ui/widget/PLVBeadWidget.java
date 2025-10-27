package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVBeadWidget extends View {
    private int beadCount;
    private int beadMargin;
    private int beadRadius;
    private int curSelectedIndex;
    private Paint paintForSelected;
    private Paint paintForUnSelected;

    public PLVBeadWidget(Context context) {
        this(context, null);
    }

    private int getSelfHeight() {
        return (int) ((this.beadRadius * 2) + (this.paintForUnSelected.getStrokeWidth() * 2.0f));
    }

    private int getSelfWidth() {
        int strokeWidth = 0;
        for (int i2 = 0; i2 < this.beadCount; i2++) {
            strokeWidth = (int) (strokeWidth + (this.beadRadius * 2) + (this.paintForUnSelected.getStrokeWidth() * 2.0f));
            if (i2 != this.beadCount - 1) {
                strokeWidth += this.beadMargin;
            }
        }
        return strokeWidth;
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.PolyvBeadWidget);
        this.beadMargin = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.PolyvBeadWidget_bead_margin, 30.0f);
        this.beadRadius = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.PolyvBeadWidget_bead_radius, 10.0f);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.PolyvBeadWidget_selected_bead_color, -16777216);
        int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.PolyvBeadWidget_unselected_bead_color, -16776961);
        typedArrayObtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.paintForSelected = paint;
        paint.setColor(color);
        this.paintForSelected.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.paintForUnSelected = paint2;
        paint2.setColor(color2);
        this.paintForSelected.setAntiAlias(true);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float strokeWidth = this.beadRadius + this.paintForUnSelected.getStrokeWidth();
        float strokeWidth2 = this.beadRadius + this.paintForUnSelected.getStrokeWidth();
        if (this.curSelectedIndex == 0) {
            canvas.drawCircle(strokeWidth, strokeWidth2, this.beadRadius, this.paintForSelected);
        } else {
            canvas.drawCircle(strokeWidth, strokeWidth2, this.beadRadius, this.paintForUnSelected);
        }
        int i2 = 1;
        while (true) {
            int i3 = this.beadRadius;
            if (i2 >= i3) {
                return;
            }
            float strokeWidth3 = i3 + this.paintForUnSelected.getStrokeWidth() + this.beadMargin + this.paintForUnSelected.getStrokeWidth();
            int i4 = this.beadRadius;
            strokeWidth += strokeWidth3 + i4;
            if (i2 == this.curSelectedIndex) {
                canvas.drawCircle(strokeWidth, strokeWidth2, i4, this.paintForSelected);
            } else {
                canvas.drawCircle(strokeWidth, strokeWidth2, i4, this.paintForUnSelected);
            }
            i2++;
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        if (mode == Integer.MIN_VALUE && mode2 == Integer.MIN_VALUE) {
            setMeasuredDimension(getSelfWidth(), getSelfHeight());
        } else if (mode == Integer.MIN_VALUE) {
            setMeasuredDimension(getSelfWidth(), size2);
        } else if (mode2 == Integer.MIN_VALUE) {
            setMeasuredDimension(size, getSelfHeight());
        }
    }

    public void setBeadCount(int beadCount) {
        this.beadCount = beadCount;
        invalidate();
    }

    public void setBeadMargin(int beadMargin) {
        this.beadMargin = ConvertUtils.dp2px(beadMargin);
    }

    public void setBeadRadius(int beadRadius) {
        this.beadRadius = ConvertUtils.dp2px(beadRadius);
    }

    public void setCurrentSelectedIndex(int index) {
        this.curSelectedIndex = index;
        invalidate();
    }

    public PLVBeadWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVBeadWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
}
