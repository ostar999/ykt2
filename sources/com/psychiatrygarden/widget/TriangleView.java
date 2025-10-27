package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class TriangleView extends View {
    private int mHeight;
    private Paint mPaint;
    private int mWidth;
    private Path path;
    private int triangleColor;
    private String triangleDirection;

    public TriangleView(Context context) {
        this(context, null);
    }

    private void init() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(this.triangleColor);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.path = new Path();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.YkbTriangleView);
        this.triangleColor = typedArrayObtainStyledAttributes.getColor(0, getResources().getColor(R.color.black));
        this.triangleDirection = typedArrayObtainStyledAttributes.getString(1);
        typedArrayObtainStyledAttributes.recycle();
        init();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.path.reset();
        String str = this.triangleDirection;
        str.hashCode();
        switch (str) {
            case "bottom":
                this.path.lineTo(this.mWidth, 0.0f);
                this.path.lineTo(this.mWidth / 2.0f, this.mHeight);
                this.path.close();
                break;
            case "top":
                this.path.moveTo(0.0f, this.mHeight);
                this.path.lineTo(this.mWidth, this.mHeight);
                this.path.lineTo(this.mWidth / 2.0f, 0.0f);
                this.path.close();
                break;
            case "left":
                this.path.moveTo(this.mWidth, 0.0f);
                this.path.lineTo(this.mWidth, this.mHeight);
                this.path.lineTo(0.0f, this.mHeight / 2.0f);
                this.path.close();
                break;
            case "right":
                this.path.lineTo(0.0f, this.mHeight);
                this.path.lineTo(this.mWidth, this.mHeight / 2.0f);
                this.path.close();
                break;
        }
        canvas.drawPath(this.path, this.mPaint);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = View.MeasureSpec.getSize(heightMeasureSpec);
    }

    public TriangleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mWidth = 0;
        this.mHeight = 0;
        this.triangleDirection = PLVDanmakuInfo.FONTMODE_TOP;
        initAttr(context, attrs);
    }
}
