package com.catchpig.mvvm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.catchpig.mvvm.R;

/* loaded from: classes2.dex */
public class DotView extends View {
    int color;
    private Context mContext;
    Paint paint;
    float radius;

    public DotView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        this.paint.setAntiAlias(true);
        this.paint.setColor(this.color);
        canvas.drawCircle(width, height, this.radius, this.paint);
    }

    public void setDotColor(int i2) {
        this.color = i2;
        invalidate();
    }

    public DotView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint();
        this.color = 0;
        this.radius = 0.0f;
        this.mContext = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DotView);
        this.color = typedArrayObtainStyledAttributes.getInteger(R.styleable.DotView_dotColor, SupportMenu.CATEGORY_MASK);
        this.radius = typedArrayObtainStyledAttributes.getDimension(R.styleable.DotView_dotRadiusw, 10.0f);
        typedArrayObtainStyledAttributes.recycle();
    }
}
