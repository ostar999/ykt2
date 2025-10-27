package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/* loaded from: classes6.dex */
public class ScoreArcView extends View {
    private Paint arcPaint;
    private int centerX;
    private int centerY;
    private int currentScore;
    private final int[] gradientColors;
    private float innerRadius;
    private RectF innerRectF;
    private int maxScore;
    private float outerRadius;
    private RectF outerRectF;
    private int passScore;

    public ScoreArcView(Context context) {
        super(context);
        this.currentScore = 0;
        this.passScore = 60;
        this.maxScore = 100;
        this.gradientColors = new int[]{Color.parseColor("#FF7D5F"), Color.parseColor("#FFFB72"), Color.parseColor("#8DEE96"), Color.parseColor("#1CE2BA")};
        init();
    }

    private void drawGradientArc(Canvas canvas, float startAngleOffset, float sweepAngle, float[] colorPositions) {
        if (sweepAngle <= 0.0f) {
            return;
        }
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 2.0f * this.outerRadius, 0.0f, this.gradientColors, colorPositions, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        float f2 = startAngleOffset + 174.0f;
        matrix.setRotate(f2, this.centerX, this.centerY);
        float f3 = this.centerX;
        float f4 = this.outerRadius;
        matrix.postTranslate(f3 - f4, this.centerY - f4);
        linearGradient.setLocalMatrix(matrix);
        this.arcPaint.setShader(linearGradient);
        canvas.drawArc(this.outerRectF, f2, sweepAngle, false, this.arcPaint);
    }

    private void init() {
        Paint paint = new Paint(1);
        this.arcPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.arcPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.arcPaint.setShader(null);
        this.arcPaint.setColor(Color.parseColor("#F9FAFB"));
        this.arcPaint.setStrokeWidth(this.innerRadius * 0.1f);
        canvas.drawArc(this.innerRectF, 174.0f, 192.0f, false, this.arcPaint);
        float f2 = this.passScore;
        int i2 = this.maxScore;
        float f3 = (f2 / i2) * 192.0f;
        float f4 = (this.currentScore / i2) * 192.0f;
        this.arcPaint.setStrokeWidth(this.outerRadius * 0.12f);
        if (this.currentScore <= this.passScore) {
            drawGradientArc(canvas, f3 - 2.5f, 5.0f, new float[]{0.25f, 0.5f, 0.75f, 1.0f});
        } else {
            drawGradientArc(canvas, 0.0f, f3, new float[]{0.0f, 0.5f, 0.5f, 1.0f});
            drawGradientArc(canvas, f3, f4 - f3, new float[]{0.5f, 0.5f, 1.0f, 1.0f});
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        this.centerX = w2 / 2;
        this.centerY = h2 / 2;
        float fMin = Math.min(w2, h2) * 0.8f;
        this.innerRadius = 0.3f * fMin;
        this.outerRadius = fMin * 0.33f;
        int i2 = this.centerX;
        float f2 = this.innerRadius;
        int i3 = this.centerY;
        this.innerRectF = new RectF(i2 - f2, i3 - f2, i2 + f2, i3 + f2);
        int i4 = this.centerX;
        float f3 = this.outerRadius;
        int i5 = this.centerY;
        this.outerRectF = new RectF(i4 - f3, i5 - f3, i4 + f3, i5 + f3);
    }

    public ScoreArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.currentScore = 0;
        this.passScore = 60;
        this.maxScore = 100;
        this.gradientColors = new int[]{Color.parseColor("#FF7D5F"), Color.parseColor("#FFFB72"), Color.parseColor("#8DEE96"), Color.parseColor("#1CE2BA")};
        init();
    }

    public ScoreArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.currentScore = 0;
        this.passScore = 60;
        this.maxScore = 100;
        this.gradientColors = new int[]{Color.parseColor("#FF7D5F"), Color.parseColor("#FFFB72"), Color.parseColor("#8DEE96"), Color.parseColor("#1CE2BA")};
        init();
    }
}
