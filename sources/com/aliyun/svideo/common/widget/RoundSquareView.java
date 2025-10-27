package com.aliyun.svideo.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.aliyun.svideo.common.R;

/* loaded from: classes2.dex */
public class RoundSquareView extends AppCompatImageView {
    private Bitmap mBitmap;
    private Paint paint;
    private Paint paint2;
    private int roundHeight;
    private int roundWidth;

    public RoundSquareView(Context context) {
        super(context);
        this.roundWidth = 5;
        this.roundHeight = 5;
        init(context, null);
    }

    private void drawLiftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0.0f, getHeight() - this.roundHeight);
        path.lineTo(0.0f, getHeight());
        path.lineTo(this.roundWidth, getHeight());
        path.arcTo(new RectF(0.0f, getHeight() - (this.roundHeight * 2), (this.roundWidth * 2) + 0, getHeight()), 90.0f, 90.0f);
        path.close();
        canvas.drawPath(path, this.paint);
    }

    private void drawLiftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0.0f, this.roundHeight);
        path.lineTo(0.0f, 0.0f);
        path.lineTo(this.roundWidth, 0.0f);
        path.arcTo(new RectF(0.0f, 0.0f, this.roundWidth * 2, this.roundHeight * 2), -90.0f, -90.0f);
        path.close();
        canvas.drawPath(path, this.paint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - this.roundWidth, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - this.roundHeight);
        path.arcTo(new RectF(getWidth() - (this.roundWidth * 2), getHeight() - (this.roundHeight * 2), getWidth(), getHeight()), 0.0f, 90.0f);
        path.close();
        canvas.drawPath(path, this.paint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), this.roundHeight);
        path.lineTo(getWidth(), 0.0f);
        path.lineTo(getWidth() - this.roundWidth, 0.0f);
        path.arcTo(new RectF(getWidth() - (this.roundWidth * 2), 0.0f, getWidth(), (this.roundHeight * 2) + 0), -90.0f, 90.0f);
        path.close();
        canvas.drawPath(path, this.paint);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.QuViewRoundAngleImageView);
            this.roundWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.QuViewRoundAngleImageView_roundWidth, this.roundWidth);
            this.roundHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.QuViewRoundAngleImageView_roundHeight, this.roundHeight);
        } else {
            float f2 = context.getResources().getDisplayMetrics().density;
            this.roundWidth = (int) (this.roundWidth * f2);
            this.roundHeight = (int) (this.roundHeight * f2);
        }
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(-1);
        this.paint.setAntiAlias(true);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        Paint paint2 = new Paint();
        this.paint2 = paint2;
        paint2.setXfermode(null);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.mBitmap == null) {
            this.mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas2 = new Canvas(this.mBitmap);
        super.draw(canvas2);
        drawLiftUp(canvas2);
        drawRightUp(canvas2);
        drawLiftDown(canvas2);
        drawRightDown(canvas2);
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.paint2);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mBitmap = null;
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i2);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, measuredWidth);
    }

    public RoundSquareView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.roundWidth = 5;
        this.roundHeight = 5;
        init(context, attributeSet);
    }

    public RoundSquareView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.roundWidth = 5;
        this.roundHeight = 5;
        init(context, attributeSet);
    }
}
