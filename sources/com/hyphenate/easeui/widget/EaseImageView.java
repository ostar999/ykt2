package com.hyphenate.easeui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatImageView;
import com.hyphenate.easeui.R;

/* loaded from: classes4.dex */
public class EaseImageView extends AppCompatImageView {
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 1;
    private int borderColor;
    private int borderWidth;
    private int height;
    private int pressAlpha;
    private int pressColor;
    private Paint pressPaint;
    private int radius;
    private int shapeType;
    private int width;

    public enum ShapeType {
        NONE,
        ROUND,
        RECTANGLE
    }

    public EaseImageView(Context context) {
        super(context);
        init(context, null);
    }

    private void drawBorder(Canvas canvas) {
        if (this.borderWidth > 0) {
            Paint paint = new Paint();
            paint.setStrokeWidth(this.borderWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(this.borderColor);
            paint.setAntiAlias(true);
            int i2 = this.shapeType;
            if (i2 == 1) {
                int i3 = this.width;
                canvas.drawCircle(i3 / 2, this.height / 2, (i3 - this.borderWidth) / 2, paint);
            } else if (i2 == 2) {
                int i4 = this.borderWidth;
                RectF rectF = new RectF(i4 / 2, i4 / 2, getWidth() - (this.borderWidth / 2), getHeight() - (this.borderWidth / 2));
                int i5 = this.radius;
                canvas.drawRoundRect(rectF, i5, i5, paint);
            }
        }
    }

    private void drawDrawable(Canvas canvas, Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setAntiAlias(true);
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        canvas.saveLayer(0.0f, 0.0f, this.width, this.height, null);
        int i2 = this.shapeType;
        if (i2 == 1) {
            int i3 = this.width;
            canvas.drawCircle(i3 / 2, this.height / 2, (i3 / 2) - 1, paint);
        } else if (i2 == 2) {
            RectF rectF = new RectF(1.0f, 1.0f, getWidth() - 1, getHeight() - 1);
            int i4 = this.radius;
            canvas.drawRoundRect(rectF, i4 + 1, i4 + 1, paint);
        }
        paint.setXfermode(porterDuffXfermode);
        Matrix matrix = new Matrix();
        matrix.postScale(getWidth() / bitmap.getWidth(), getHeight() / bitmap.getHeight());
        canvas.drawBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true), 0.0f, 0.0f, paint);
        canvas.restore();
    }

    private void drawPress(Canvas canvas) {
        int i2 = this.shapeType;
        if (i2 == 1) {
            int i3 = this.width;
            canvas.drawCircle(i3 / 2, this.height / 2, (i3 / 2) - 1, this.pressPaint);
        } else if (i2 == 2) {
            RectF rectF = new RectF(1.0f, 1.0f, this.width - 1, this.height - 1);
            int i4 = this.radius;
            canvas.drawRoundRect(rectF, i4 + 1, i4 + 1, this.pressPaint);
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(Math.max(drawable.getIntrinsicWidth(), 2), Math.max(drawable.getIntrinsicHeight(), 2), BITMAP_CONFIG);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmapCreateBitmap;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.borderWidth = 0;
        this.borderColor = -570425345;
        this.pressAlpha = 66;
        this.pressColor = 1107296256;
        this.radius = 16;
        this.shapeType = 0;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EaseImageView);
            this.borderColor = typedArrayObtainStyledAttributes.getColor(R.styleable.EaseImageView_ease_border_color, this.borderColor);
            this.borderWidth = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.EaseImageView_ease_border_width, this.borderWidth);
            this.pressAlpha = typedArrayObtainStyledAttributes.getInteger(R.styleable.EaseImageView_ease_press_alpha, this.pressAlpha);
            this.pressColor = typedArrayObtainStyledAttributes.getColor(R.styleable.EaseImageView_ease_press_color, this.pressColor);
            this.radius = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.EaseImageView_ease_radius, this.radius);
            this.shapeType = typedArrayObtainStyledAttributes.getInteger(R.styleable.EaseImageView_ease_shape_type, this.shapeType);
            typedArrayObtainStyledAttributes.recycle();
        }
        Paint paint = new Paint();
        this.pressPaint = paint;
        paint.setAntiAlias(true);
        this.pressPaint.setStyle(Paint.Style.FILL);
        this.pressPaint.setColor(this.pressColor);
        this.pressPaint.setAlpha(0);
        this.pressPaint.setFlags(1);
        setDrawingCacheEnabled(true);
        setWillNotDraw(false);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        if (this.shapeType == 0) {
            super.onDraw(canvas);
            return;
        }
        Drawable drawable = getDrawable();
        if (drawable == null || getWidth() == 0 || getHeight() == 0) {
            return;
        }
        drawDrawable(canvas, getBitmapFromDrawable(drawable));
        if (isClickable()) {
            drawPress(canvas);
        }
        drawBorder(canvas);
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.width = i2;
        this.height = i3;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.pressPaint.setAlpha(this.pressAlpha);
            invalidate();
        } else if (action == 1 || action != 2) {
            this.pressPaint.setAlpha(0);
            invalidate();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setBorderColor(int i2) {
        this.borderColor = i2;
        invalidate();
    }

    public void setBorderWidth(int i2) {
        this.borderWidth = i2;
    }

    public void setPressAlpha(int i2) {
        this.pressAlpha = i2;
    }

    public void setPressColor(int i2) {
        this.pressColor = i2;
    }

    public void setRadius(int i2) {
        this.radius = i2;
        invalidate();
    }

    public void setShapeType(int i2) {
        this.shapeType = i2;
        invalidate();
    }

    public EaseImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public void setShapeType(ShapeType shapeType) {
        if (shapeType == null) {
            return;
        }
        this.shapeType = shapeType.ordinal();
        invalidate();
    }

    public EaseImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init(context, attributeSet);
    }
}
