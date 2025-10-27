package com.hyphenate.easeui.widget;

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
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.hyphenate.easeui.R;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class EaseRoundImageView extends AppCompatImageView {
    private Context context;
    private Paint paint;
    private Paint paint2;
    private int roundHeight;
    private int roundWidth;

    public static class CanvasLegacy {
        static final int CLIP_SAVE_FLAG;
        static final int CLIP_TO_LAYER_SAVE_FLAG;
        static final int FULL_COLOR_LAYER_SAVE_FLAG;
        static final int HAS_ALPHA_LAYER_SAVE_FLAG;
        static final int MATRIX_SAVE_FLAG;
        private static final Method SAVE;

        static {
            try {
                MATRIX_SAVE_FLAG = ((Integer) Canvas.class.getField("MATRIX_SAVE_FLAG").get(null)).intValue();
                CLIP_SAVE_FLAG = ((Integer) Canvas.class.getField("CLIP_SAVE_FLAG").get(null)).intValue();
                HAS_ALPHA_LAYER_SAVE_FLAG = ((Integer) Canvas.class.getField("HAS_ALPHA_LAYER_SAVE_FLAG").get(null)).intValue();
                FULL_COLOR_LAYER_SAVE_FLAG = ((Integer) Canvas.class.getField("FULL_COLOR_LAYER_SAVE_FLAG").get(null)).intValue();
                CLIP_TO_LAYER_SAVE_FLAG = ((Integer) Canvas.class.getField("CLIP_TO_LAYER_SAVE_FLAG").get(null)).intValue();
                Class cls = Float.TYPE;
                SAVE = Canvas.class.getMethod("saveLayer", cls, cls, cls, cls, Paint.class, Integer.TYPE);
            } catch (Throwable th) {
                throw sneakyThrow(th);
            }
        }

        public static void saveLayer(Canvas canvas, float f2, float f3, float f4, float f5, @Nullable Paint paint, int i2) {
            try {
                SAVE.invoke(canvas, Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f5), paint, Integer.valueOf(i2));
            } catch (Throwable th) {
                throw sneakyThrow(th);
            }
        }

        private static RuntimeException sneakyThrow(Throwable th) {
            if (th != null) {
                return (RuntimeException) sneakyThrow0(th);
            }
            throw new NullPointerException("t");
        }

        private static <T extends Throwable> T sneakyThrow0(Throwable th) throws Throwable {
            throw th;
        }
    }

    public EaseRoundImageView(Context context) {
        this(context, null);
    }

    private void drawLiftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0.0f, getHeight() - this.roundHeight);
        path.lineTo(0.0f, getHeight());
        path.lineTo(this.roundWidth, getHeight());
        path.arcTo(new RectF(0.0f, getHeight() - (this.roundHeight * 2), this.roundWidth * 2, getHeight()), 90.0f, 90.0f);
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
        this.context = context;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EaseRoundImageView);
            this.roundWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.EaseRoundImageView_roundWidth, this.roundWidth);
            this.roundHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.EaseRoundImageView_roundHeight, this.roundHeight);
            typedArrayObtainStyledAttributes.recycle();
        }
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(-1);
        this.paint.setAntiAlias(true);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setColor(-1);
        this.paint.setAntiAlias(true);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        Paint paint3 = new Paint();
        this.paint2 = paint3;
        paint3.setXfermode(null);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmapCreateBitmap);
        super.draw(canvas2);
        drawLiftUp(canvas2);
        drawLiftDown(canvas2);
        drawRightUp(canvas2);
        drawRightDown(canvas2);
        canvas.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, this.paint2);
        bitmapCreateBitmap.recycle();
    }

    public EaseRoundImageView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EaseRoundImageView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.roundWidth = 20;
        this.roundHeight = 20;
        init(context, attributeSet);
    }
}
