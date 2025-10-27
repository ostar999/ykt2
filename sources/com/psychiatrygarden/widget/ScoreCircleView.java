package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ScoreCircleView extends View {
    private static final float DEFAULT_CIRCLE_DIAMETER_DP = 64.0f;
    private static final float LABEL_TEXT_SIZE_SP = 10.0f;
    private static final float MIN_MARGIN_RATIO = 0.15f;
    private static final float SCORE_TEXT_SIZE_SP = 16.0f;
    private static final float STROKE_WIDTH_DP = 8.0f;
    private Paint arcPaint;
    private float circleDiameterPx;
    private Paint circlePaint;
    private float currentScore;
    private int[] gradientColors;
    private Rect labelTextBounds;
    private Paint labelTextPaint;
    private float labelTextSizePx;
    private String scoreText;
    private Rect scoreTextBounds;
    private Paint scoreTextPaint;
    private float scoreTextSizePx;
    private float strokeWidthPx;
    private float totalScore;
    private static final int BASE_CIRCLE_COLOR = Color.parseColor("#F2F4F6");
    private static final int SCORE_TEXT_COLOR = Color.parseColor("#141516");
    private static final int LABEL_TEXT_COLOR = Color.parseColor("#909499");

    public ScoreCircleView(Context context) {
        super(context);
        this.totalScore = 600.0f;
        this.currentScore = 0.0f;
        this.scoreText = "";
        this.circlePaint = new Paint(1);
        this.arcPaint = new Paint(1);
        this.scoreTextPaint = new Paint(1);
        this.labelTextPaint = new Paint(1);
        this.scoreTextBounds = new Rect();
        this.labelTextBounds = new Rect();
        init();
    }

    private void init() {
        this.gradientColors = new int[]{Color.parseColor("#FF7D5F"), Color.parseColor("#FF7D5F"), Color.parseColor("#FF7D5F"), Color.parseColor("#FF7D5F"), Color.parseColor("#FFFB72"), Color.parseColor("#8DEE96"), Color.parseColor("#8DEE96")};
        this.circlePaint.setStyle(Paint.Style.STROKE);
        this.circlePaint.setColor(BASE_CIRCLE_COLOR);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            this.circlePaint.setColor(getContext().getColor(R.color.new_bg_two_color_night));
        }
        this.arcPaint.setStyle(Paint.Style.STROKE);
        this.arcPaint.setStrokeCap(Paint.Cap.ROUND);
        this.scoreTextPaint.setColor(SCORE_TEXT_COLOR);
        this.scoreTextPaint.setTextAlign(Paint.Align.CENTER);
        this.scoreTextPaint.setFakeBoldText(true);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            this.scoreTextPaint.setColor(getContext().getColor(R.color.first_txt_color_night));
        }
        this.labelTextPaint.setColor(LABEL_TEXT_COLOR);
        this.labelTextPaint.setTextAlign(Paint.Align.CENTER);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            this.labelTextPaint.setColor(getContext().getColor(R.color.third_txt_color_night));
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() / 2.0f;
        float height = getHeight() / 2.0f;
        float f2 = this.circleDiameterPx;
        float f3 = this.strokeWidthPx;
        float f4 = (f2 - f3) / 2.0f;
        this.circlePaint.setStrokeWidth(f3);
        this.arcPaint.setStrokeWidth(this.strokeWidthPx);
        canvas.drawCircle(width, height, f4, this.circlePaint);
        float f5 = this.currentScore;
        if (f5 > 0.0f) {
            float f6 = (f5 / this.totalScore) * 360.0f;
            float f7 = height - f4;
            float f8 = height + f4;
            RectF rectF = new RectF(width - f4, f7, width + f4, f8);
            this.arcPaint.setShader(new LinearGradient(0.0f, f7, 0.0f, f8, this.gradientColors, (float[]) null, Shader.TileMode.CLAMP));
            canvas.drawArc(rectF, -90.0f, f6, false, this.arcPaint);
        }
        float fHeight = height - (((this.scoreTextBounds.height() + this.labelTextBounds.height()) + (this.labelTextSizePx * 0.5f)) / 2.0f);
        canvas.drawText(this.scoreText, width, this.scoreTextBounds.height() + fHeight, this.scoreTextPaint);
        canvas.drawText("预估分", width, fHeight + this.scoreTextBounds.height() + (this.labelTextSizePx * 0.5f) + this.labelTextBounds.height(), this.labelTextPaint);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Resources resources = getResources();
        this.strokeWidthPx = TypedValue.applyDimension(1, 8.0f, resources.getDisplayMetrics());
        this.scoreTextSizePx = TypedValue.applyDimension(2, 16.0f, resources.getDisplayMetrics());
        this.labelTextSizePx = TypedValue.applyDimension(2, LABEL_TEXT_SIZE_SP, resources.getDisplayMetrics());
        this.scoreTextPaint.setTextSize(this.scoreTextSizePx);
        this.labelTextPaint.setTextSize(this.labelTextSizePx);
        Paint paint = this.scoreTextPaint;
        String str = this.scoreText;
        paint.getTextBounds(str, 0, str.length(), this.scoreTextBounds);
        this.labelTextPaint.getTextBounds("预估分", 0, 3, this.labelTextBounds);
        float fHeight = (((this.scoreTextBounds.height() + this.labelTextBounds.height()) + (this.labelTextSizePx * 0.5f)) / 0.7f) + (this.strokeWidthPx * 2.0f);
        float fApplyDimension = TypedValue.applyDimension(1, DEFAULT_CIRCLE_DIAMETER_DP, resources.getDisplayMetrics());
        this.circleDiameterPx = fApplyDimension;
        float fMax = Math.max(fApplyDimension, fHeight);
        this.circleDiameterPx = fMax;
        int paddingLeft = (int) (fMax + getPaddingLeft() + getPaddingRight());
        setMeasuredDimension(paddingLeft, paddingLeft);
    }

    public void setScore(int currentScore, int totalScore) {
        this.currentScore = Math.min(currentScore, totalScore);
        this.scoreText = String.valueOf(currentScore);
        this.totalScore = totalScore;
        requestLayout();
        invalidate();
    }

    public ScoreCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.totalScore = 600.0f;
        this.currentScore = 0.0f;
        this.scoreText = "";
        this.circlePaint = new Paint(1);
        this.arcPaint = new Paint(1);
        this.scoreTextPaint = new Paint(1);
        this.labelTextPaint = new Paint(1);
        this.scoreTextBounds = new Rect();
        this.labelTextBounds = new Rect();
        init();
    }

    public ScoreCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.totalScore = 600.0f;
        this.currentScore = 0.0f;
        this.scoreText = "";
        this.circlePaint = new Paint(1);
        this.arcPaint = new Paint(1);
        this.scoreTextPaint = new Paint(1);
        this.labelTextPaint = new Paint(1);
        this.scoreTextBounds = new Rect();
        this.labelTextBounds = new Rect();
        init();
    }
}
