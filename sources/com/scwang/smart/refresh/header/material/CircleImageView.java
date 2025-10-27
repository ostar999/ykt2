package com.scwang.smart.refresh.header.material;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.widget.ImageView;
import androidx.annotation.ColorInt;

@SuppressLint({"ViewConstructor", "AppCompatCustomView"})
/* loaded from: classes6.dex */
public class CircleImageView extends ImageView {
    protected static final int FILL_SHADOW_COLOR = 1023410176;
    protected static final int KEY_SHADOW_COLOR = 503316480;
    protected static final int SHADOW_ELEVATION = 4;
    protected static final float SHADOW_RADIUS = 3.5f;
    protected static final float X_OFFSET = 0.0f;
    protected static final float Y_OFFSET = 1.75f;
    int mShadowRadius;

    public class OvalShadow extends OvalShape {
        private RadialGradient mRadialGradient;
        private Paint mShadowPaint = new Paint();

        public OvalShadow(int i2) {
            CircleImageView.this.mShadowRadius = i2;
            updateRadialGradient((int) super.rect().width());
        }

        private void updateRadialGradient(int i2) {
            float f2 = i2 / 2.0f;
            RadialGradient radialGradient = new RadialGradient(f2, f2, CircleImageView.this.mShadowRadius, new int[]{CircleImageView.FILL_SHADOW_COLOR, 0}, (float[]) null, Shader.TileMode.CLAMP);
            this.mRadialGradient = radialGradient;
            this.mShadowPaint.setShader(radialGradient);
        }

        @Override // android.graphics.drawable.shapes.OvalShape, android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        public void draw(Canvas canvas, Paint paint) {
            CircleImageView circleImageView = CircleImageView.this;
            float width = circleImageView.getWidth() / 2.0f;
            float height = circleImageView.getHeight() / 2.0f;
            canvas.drawCircle(width, height, width, this.mShadowPaint);
            canvas.drawCircle(width, height, width - CircleImageView.this.mShadowRadius, paint);
        }

        @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        public void onResize(float f2, float f3) {
            super.onResize(f2, f3);
            updateRadialGradient((int) f2);
        }
    }

    public CircleImageView(Context context, int i2) {
        super(context);
        float f2 = getResources().getDisplayMetrics().density;
        this.mShadowRadius = (int) (SHADOW_RADIUS * f2);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        setElevation(f2 * 4.0f);
        shapeDrawable.getPaint().setColor(i2);
        setBackground(shapeDrawable);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
    }

    @Override // android.view.View
    public void setBackgroundColor(@ColorInt int i2) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(i2);
        }
    }
}
