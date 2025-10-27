package com.aliyun.player.alivcplayerexpand.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatSeekBar;
import com.aliyun.player.alivcplayerexpand.bean.SummaryPoint;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DotSeekBar extends AppCompatSeekBar implements View.OnTouchListener {
    private final Paint mCirclePaint;
    private OnIconClickListener mIconClickListener;
    private final List<SummaryPoint> mIcons;

    public interface OnIconClickListener {
        void onIconClick(SummaryPoint summaryPoint);
    }

    public DotSeekBar(Context context) {
        this(context, null);
    }

    private float calculateCirclePosition(int i2) {
        return ((i2 / getMax()) * ((getWidth() - getPaddingLeft()) - getPaddingRight())) + getPaddingLeft();
    }

    private void init() {
        setOnTouchListener(this);
        this.mCirclePaint.setColor(-1);
        this.mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mCirclePaint.setAntiAlias(true);
    }

    public void addIcons(List<SummaryPoint> list) {
        this.mIcons.clear();
        this.mIcons.addAll(list);
        invalidate();
    }

    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
        for (SummaryPoint summaryPoint : this.mIcons) {
            if (summaryPoint.time * 1000 <= getMax() && getMax() != 0) {
                float max = (((summaryPoint.time * 1000.0f) / getMax()) * width) + getPaddingLeft();
                float f2 = summaryPoint.radius;
                if (max <= f2) {
                    max = f2;
                }
                canvas.drawCircle(max, getHeight() / 2.0f, summaryPoint.radius, this.mCirclePaint);
            }
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 1) {
            return false;
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        for (SummaryPoint summaryPoint : this.mIcons) {
            float fCalculateCirclePosition = calculateCirclePosition(summaryPoint.time * 1000);
            float f2 = summaryPoint.radius;
            if (Math.abs(x2 - fCalculateCirclePosition) <= f2 && Math.abs(y2 - (getHeight() / 2.0f)) <= f2) {
                OnIconClickListener onIconClickListener = this.mIconClickListener;
                if (onIconClickListener != null) {
                    onIconClickListener.onIconClick(summaryPoint);
                    Log.d("onTouch", "point ms = " + (summaryPoint.time * 1000));
                }
                return true;
            }
        }
        return false;
    }

    public void setOnIconClickListener(OnIconClickListener onIconClickListener) {
        this.mIconClickListener = onIconClickListener;
    }

    public DotSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DotSeekBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mIcons = new ArrayList();
        this.mCirclePaint = new Paint(1);
        init();
    }
}
