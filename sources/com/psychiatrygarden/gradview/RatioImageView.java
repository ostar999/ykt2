package com.psychiatrygarden.gradview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class RatioImageView extends AppCompatImageView {
    private Paint backgroundPaint;
    private Bitmap bitmap;

    /* renamed from: h, reason: collision with root package name */
    private int f16177h;
    private int mButtom;
    private Context mContext;
    private float mRatio;
    private RectF mRectF;
    private int mRight;
    private Path pathBackground;
    private Path pathText;
    private String str;
    private Paint textPaint;

    /* renamed from: w, reason: collision with root package name */
    private int f16178w;

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mRatio = 0.0f;
        this.mRight = 0;
        this.mButtom = 0;
        this.mContext = context;
        init();
    }

    private void calculatePath(Canvas canvas) {
        RectF rectF = new RectF();
        this.mRectF = rectF;
        rectF.left = this.mRight - CommonUtil.dip2px(getContext(), 35.0f);
        this.mRectF.top = this.mButtom - CommonUtil.dip2px(getContext(), 20.0f);
        this.mRectF.right = this.mRight - CommonUtil.dip2px(getContext(), 5.0f);
        this.mRectF.bottom = this.mButtom - CommonUtil.dip2px(getContext(), 5.0f);
        canvas.drawRoundRect(this.mRectF, 20.0f, 20.0f, this.backgroundPaint);
        Paint.FontMetrics fontMetrics = this.textPaint.getFontMetrics();
        RectF rectF2 = this.mRectF;
        canvas.drawText(this.str, rectF2.centerX(), (((rectF2.bottom + rectF2.top) - fontMetrics.bottom) - fontMetrics.top) / 2.0f, this.textPaint);
    }

    private void init() {
        this.pathText = new Path();
        this.pathBackground = new Path();
        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dayibofang);
        Paint paint = new Paint();
        this.textPaint = paint;
        paint.setTextSize(CommonUtil.dip2px(getContext(), 10.0f));
        this.textPaint.setFakeBoldText(true);
        this.textPaint.setColor(-1);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        Paint paint2 = new Paint();
        this.backgroundPaint = paint2;
        paint2.setColor(-7829368);
        this.backgroundPaint.setAlpha(180);
        this.backgroundPaint.setAntiAlias(true);
        this.backgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            canvas.drawColor(this.mContext.getResources().getColor(R.color.tran_img_color));
        }
        if (this.str.equals("none")) {
            return;
        }
        if (!this.str.equals("video")) {
            calculatePath(canvas);
            return;
        }
        canvas.drawBitmap(this.bitmap, (getWidth() - this.bitmap.getWidth()) / 2, (getHeight() - this.bitmap.getHeight()) / 2, this.backgroundPaint);
    }

    @Override // android.view.View
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    @Override // android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        this.mRight = right;
        this.mButtom = bottom;
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        float f2 = this.mRatio;
        if (f2 != 0.0f) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (size / f2), 1073741824);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        event.getAction();
        return super.onTouchEvent(event);
    }

    public void setRatio(float ratio) {
        this.mRatio = ratio;
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRatio = 0.0f;
        this.mRight = 0;
        this.mButtom = 0;
        this.mContext = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        this.mRatio = typedArrayObtainStyledAttributes.getFloat(0, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        init();
    }

    public RatioImageView(Context context, String str) {
        super(context);
        this.mRatio = 0.0f;
        this.mRight = 0;
        this.mButtom = 0;
        this.mContext = context;
        this.str = str;
        init();
    }

    public RatioImageView(Context context) {
        super(context);
        this.mRatio = 0.0f;
        this.mRight = 0;
        this.mButtom = 0;
        this.mContext = context;
        this.str = this.str;
        init();
    }
}
