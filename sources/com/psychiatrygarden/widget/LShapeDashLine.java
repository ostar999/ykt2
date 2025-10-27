package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;

/* loaded from: classes6.dex */
public class LShapeDashLine extends View {
    private static final int COLOR = -1118482;
    private static final float DASH_GAP_DP = 3.0f;
    private static final float HEIGHT_DP = 56.0f;
    private static final float WIDTH_DP = 14.0f;
    private float dashGapPx;
    private float dashLengthPx;
    private boolean drawShapeL;
    private int height;
    private Paint paint;
    private Path path;

    public LShapeDashLine(Context context) {
        this(context, null);
    }

    private void init() {
        float fApplyDimension = TypedValue.applyDimension(1, 3.0f, getResources().getDisplayMetrics());
        this.dashGapPx = fApplyDimension;
        this.dashLengthPx = fApplyDimension;
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setColor(COLOR);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            this.paint.setColor(Color.parseColor("#2C303E"));
        }
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(SizeUtil.dp2px(getContext(), 1));
        this.paint.setPathEffect(new DashPathEffect(new float[]{this.dashLengthPx, this.dashGapPx}, 0.0f));
        this.height = SizeUtil.dp2px(getContext(), 15);
        this.path = new Path();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.path.reset();
        float height = getHeight() - SizeUtil.dp2px(getContext(), 2);
        this.path.moveTo(0.0f, 0.0f);
        this.path.lineTo(0.0f, height);
        this.path.moveTo(SizeUtil.dp2px(getContext(), 2), height);
        this.path.lineTo(getWidth(), height);
        canvas.drawPath(this.path, this.paint);
    }

    public void setHeight(int height, boolean drawShapeL) {
        this.height = height;
        this.drawShapeL = drawShapeL;
        if (height <= 0) {
            return;
        }
        requestLayout();
    }

    public LShapeDashLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LShapeDashLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.height = 0;
        init();
    }
}
