package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatSeekBar;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class DotSeekBar extends AppCompatSeekBar implements View.OnTouchListener {
    private final Paint mCirclePaint;
    private OnIconClickListener mIconClickListener;
    private final List<Icon> mIcons;

    public static class Icon {
        public String id;
        public RectF mRectF;
        public int position;
        public float radius;
        public int time;

        public Icon(int time, float radius, int position, String id) {
            this.time = time;
            this.radius = radius;
            this.position = position;
            this.id = id;
            float f2 = time;
            this.mRectF = new RectF(f2 - (radius * 2.0f), 0.0f, f2, 0.0f);
        }
    }

    public interface OnIconClickListener {
        void onIconClick(Icon icon);
    }

    public DotSeekBar(Context context) {
        this(context, null);
    }

    private void init() {
        setOnTouchListener(this);
        this.mCirclePaint.setColor(-1);
        this.mCirclePaint.setStrokeWidth(TypedValue.applyDimension(1, 6.0f, getContext().getResources().getDisplayMetrics()));
        this.mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mCirclePaint.setAntiAlias(true);
    }

    public void addIcons(List<Icon> icons) {
        this.mIcons.clear();
        this.mIcons.addAll(icons);
        invalidate();
    }

    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Icon icon : this.mIcons) {
            if (icon.time <= getMax() && getMax() != 0) {
                canvas.drawCircle(icon.time - icon.radius, getHeight() / 2.0f, icon.radius, this.mCirclePaint);
                icon.mRectF.bottom = getHeight() / 2.0f;
            }
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v2, MotionEvent event) {
        if (event.getAction() != 1) {
            return false;
        }
        float x2 = event.getX();
        float y2 = event.getY();
        for (Icon icon : this.mIcons) {
            if (icon.mRectF.contains(x2, y2)) {
                OnIconClickListener onIconClickListener = this.mIconClickListener;
                if (onIconClickListener != null) {
                    onIconClickListener.onIconClick(icon);
                }
                return true;
            }
        }
        return false;
    }

    public void setOnIconClickListener(OnIconClickListener listener) {
        this.mIconClickListener = listener;
    }

    public DotSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIcons = new ArrayList();
        this.mCirclePaint = new Paint(1);
        init();
    }
}
