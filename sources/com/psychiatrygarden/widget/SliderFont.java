package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ConstantUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class SliderFont extends View {
    private static final String TAG = "SliderFont";
    private float[] fontSize;
    private String[] fontStr;

    /* renamed from: i, reason: collision with root package name */
    private int f16300i;
    private int mCenterX;
    private int mCenterY;
    private Context mContext;
    private int mFontSize;
    private int mHeight;
    private int mIndex;
    private float mLastX;
    private float mLastY;
    private int mOffsetLeft;
    private int mOffsetRight;
    private Paint mProgressPaint;
    private int mScreemWidth;
    private int mScreenHight;
    private int mSliderWidth;
    private int mSpec;
    private Drawable mThumb;
    private Paint mThumbPaint;
    private int mWidth;

    public SliderFont(Context context) {
        this(context, null);
    }

    private void init() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            this.mThumb = this.mContext.getResources().getDrawable(R.drawable.yuan_write_night);
        } else {
            this.mThumb = this.mContext.getResources().getDrawable(R.drawable.yuan_write);
        }
        this.mThumbPaint = new Paint();
        this.mProgressPaint = new Paint();
    }

    public int adJustCenter(float local) {
        int i2 = this.mSpec;
        int i3 = ((int) (local - ((this.mScreemWidth - this.mWidth) / 2))) / i2;
        this.mIndex = i3;
        if (i3 <= 0) {
            this.mIndex = 0;
        }
        int i4 = this.mIndex;
        float[] fArr = this.fontSize;
        if (i4 >= fArr.length - 1) {
            this.mIndex = fArr.length - 1;
        }
        this.mCenterX = (i2 * this.mIndex) + this.mOffsetLeft;
        invalidate();
        return this.mIndex;
    }

    public float getFontSize(int index) {
        return this.fontSize[index];
    }

    public int getIndex() {
        return this.mIndex;
    }

    public void move(float spec) {
        int i2 = (int) (this.mCenterX + spec);
        this.mCenterX = i2;
        int i3 = this.mOffsetLeft;
        if (i2 <= i3) {
            this.mCenterX = i3;
        }
        int i4 = this.mCenterX;
        int i5 = this.mWidth;
        if (i4 >= i5 - i3) {
            this.mCenterX = i5 - i3;
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            this.mProgressPaint.setColor(this.mContext.getResources().getColor(R.color.question_color_night));
        } else {
            this.mProgressPaint.setColor(this.mContext.getResources().getColor(R.color.zitishezhiyanse));
        }
        this.mProgressPaint.setStyle(Paint.Style.FILL);
        this.mProgressPaint.setTextSize(CommonUtil.dip2px(this.mContext, 12.0f));
        int i2 = 0;
        while (true) {
            this.f16300i = i2;
            if (this.f16300i >= this.fontSize.length) {
                Drawable drawable = this.mThumb;
                int i3 = this.mCenterX;
                int i4 = this.mHeight;
                drawable.setBounds((i3 - (i4 / 5)) + 5, (this.mCenterY - (i4 / 5)) + 5, (i3 + (i4 / 5)) - 5, (r4 + (i4 / 5)) - 5);
                this.mThumb.draw(canvas);
                return;
            }
            int i5 = this.mSpec;
            int i6 = this.mOffsetLeft;
            int i7 = this.mHeight;
            canvas.drawRect((i5 * r0) + i6, (i7 - 30) / 2, (i5 * r0) + 3.0f + i6, ((i7 - 30) / 2) + 30, this.mProgressPaint);
            if (this.fontStr[this.f16300i].length() > 1) {
                canvas.drawText(this.fontStr[this.f16300i], ((this.mSpec * r2) + (this.mOffsetLeft / 2)) - 10, ((this.mHeight - 30) / 2) - 20, this.mProgressPaint);
            } else {
                canvas.drawText(this.fontStr[this.f16300i], (this.mSpec * r2) + (this.mOffsetLeft / 2) + 3, ((this.mHeight - 30) / 2) - 20, this.mProgressPaint);
            }
            if (this.f16300i != this.fontSize.length - 1) {
                int i8 = this.mSpec;
                int i9 = this.mOffsetLeft;
                int i10 = this.mHeight;
                canvas.drawRect((i8 * r0) + i9, ((i10 - 30) / 2) + 15, (i8 * (r0 + 1)) + i9, ((i10 - 30) / 2) + 18.0f, this.mProgressPaint);
            }
            i2 = this.f16300i + 1;
        }
    }

    @Override // android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        this.mSliderWidth = (this.mWidth - getPaddingLeft()) - getPaddingRight();
        int paddingLeft = getPaddingLeft();
        this.mOffsetLeft = paddingLeft;
        int length = this.mSliderWidth / (this.fontSize.length - 1);
        this.mSpec = length;
        this.mCenterX = (length * this.mIndex) + paddingLeft;
        this.mCenterY = ((this.mHeight - 30) / 2) + 15;
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        if (mode == Integer.MIN_VALUE) {
            this.mWidth = 600;
        } else {
            this.mWidth = size;
        }
        if (mode2 == Integer.MIN_VALUE) {
            this.mHeight = 200;
        } else {
            this.mHeight = size2;
        }
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    public void setCenter(float center) {
        this.mCenterX = (int) (center - ((this.mScreemWidth - this.mWidth) / 2));
    }

    public void setScreemWidth(int screemWidth) {
        this.mScreemWidth = screemWidth;
    }

    public SliderFont(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderFont(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.f16300i = 0;
        this.mFontSize = 19;
        this.mSpec = 0;
        this.mIndex = ConstantUtil.mFontIndex;
        this.fontSize = new float[]{12.0f, 14.0f, 16.0f, 19.0f, 22.0f};
        this.fontStr = new String[]{"小", "中", "正常", "大", "超大"};
        this.mContext = context;
        init();
    }
}
