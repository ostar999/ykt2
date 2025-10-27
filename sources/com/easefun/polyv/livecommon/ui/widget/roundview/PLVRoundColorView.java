package com.easefun.polyv.livecommon.ui.widget.roundview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import com.easefun.polyv.livecommon.R;

/* loaded from: classes3.dex */
public class PLVRoundColorView extends View {
    private AttributeSet attributeSet;

    @ColorInt
    private int backgroundColor;
    private final Paint backgroundPaint;
    private int backgroundRadius;

    @ColorInt
    private int mainColor;
    private final Paint mainPaint;

    @Px
    private int mainRadius;

    public PLVRoundColorView(Context context) {
        super(context);
        this.mainRadius = 0;
        this.mainColor = 0;
        this.backgroundColor = 0;
        this.mainPaint = new Paint();
        this.backgroundPaint = new Paint();
        initView();
    }

    private void initPaint() {
        this.mainPaint.setColor(this.mainColor);
        this.mainPaint.setStyle(Paint.Style.FILL);
        this.mainPaint.setAntiAlias(true);
        this.backgroundPaint.setColor(this.backgroundColor);
        this.backgroundPaint.setStyle(Paint.Style.FILL);
        this.backgroundPaint.setAntiAlias(true);
    }

    private void initView() {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(this.attributeSet, R.styleable.PLVRoundColorView);
        this.mainRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PLVRoundColorView_plvMainRadius, 0);
        this.mainColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVRoundColorView_plvMainColor, 0);
        this.backgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVRoundColorView_plvBackgroundColor, 0);
        typedArrayObtainStyledAttributes.recycle();
        initPaint();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, this.backgroundRadius, this.backgroundPaint);
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, this.mainRadius, this.mainPaint);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.backgroundRadius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2;
    }

    public void updateBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.backgroundPaint.setColor(backgroundColor);
        invalidate();
    }

    public void updateMainColor(@ColorInt int mainColor) {
        this.mainColor = mainColor;
        this.mainPaint.setColor(mainColor);
        invalidate();
    }

    public PLVRoundColorView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mainRadius = 0;
        this.mainColor = 0;
        this.backgroundColor = 0;
        this.mainPaint = new Paint();
        this.backgroundPaint = new Paint();
        this.attributeSet = attrs;
        initView();
    }

    public PLVRoundColorView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mainRadius = 0;
        this.mainColor = 0;
        this.backgroundColor = 0;
        this.mainPaint = new Paint();
        this.backgroundPaint = new Paint();
        this.attributeSet = attrs;
        initView();
    }
}
