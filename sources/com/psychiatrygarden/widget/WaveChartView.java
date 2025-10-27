package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class WaveChartView extends View {
    private List<Float> data;
    private Paint fillPaint;
    private Paint linePaint;
    private int pointIndex;
    private Paint pointPaint;
    private Paint strokePaint;

    public WaveChartView(Context context) {
        super(context);
        this.data = new ArrayList();
        this.pointIndex = 0;
        init();
    }

    private void init() {
        Paint paint = new Paint(1);
        this.fillPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.strokePaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.strokePaint.setStrokeWidth(3.0f);
        Paint paint3 = new Paint(1);
        this.linePaint = paint3;
        paint3.setStyle(Paint.Style.STROKE);
        this.linePaint.setStrokeWidth(2.0f);
        Paint paint4 = new Paint(1);
        this.pointPaint = paint4;
        paint4.setStyle(Paint.Style.FILL);
        List<Float> list = this.data;
        Float fValueOf = Float.valueOf(10.0f);
        list.add(fValueOf);
        this.data.add(Float.valueOf(20.0f));
        this.data.add(Float.valueOf(48.0f));
        this.data.add(Float.valueOf(30.0f));
        this.data.add(fValueOf);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<Float> list = this.data;
        if (list == null || list.size() < 2) {
            return;
        }
        float width = getWidth();
        float height = getHeight();
        float size = width / (this.data.size() - 1);
        float fFloatValue = ((Float) Collections.max(this.data)).floatValue();
        float fFloatValue2 = ((Float) Collections.min(this.data)).floatValue();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.data.size(); i2++) {
            arrayList.add(new PointF(i2 * size, height - (((this.data.get(i2).floatValue() - fFloatValue2) / (fFloatValue - fFloatValue2)) * (0.8f * height))));
        }
        Path path = new Path();
        path.moveTo(((PointF) arrayList.get(0)).x, ((PointF) arrayList.get(0)).y);
        int i3 = 0;
        while (i3 < arrayList.size() - 1) {
            int i4 = i3 + 1;
            path.quadTo(((PointF) arrayList.get(i3)).x, ((PointF) arrayList.get(i3)).y, (((PointF) arrayList.get(i3)).x + ((PointF) arrayList.get(i4)).x) / 2.0f, (((PointF) arrayList.get(i3)).y + ((PointF) arrayList.get(i4)).y) / 2.0f);
            i3 = i4;
        }
        path.lineTo(((PointF) arrayList.get(arrayList.size() - 1)).x, ((PointF) arrayList.get(arrayList.size() - 1)).y);
        Path path2 = new Path(path);
        path2.lineTo(((PointF) arrayList.get(arrayList.size() - 1)).x, height);
        path2.lineTo(((PointF) arrayList.get(0)).x, height);
        path2.close();
        this.fillPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, height, new int[]{Color.argb(77, 0, 128, 255), Color.argb(0, 0, 128, 255)}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
        canvas.drawPath(path2, this.fillPaint);
        this.strokePaint.setShader(new LinearGradient(0.0f, 0.0f, width, 0.0f, new int[]{Color.argb(0, 154, R2.attr.actionModeShareDrawable, 255), Color.argb(128, 104, 188, 255), Color.argb(255, 54, 125, 255), Color.argb(128, 104, 188, 255), Color.argb(0, 154, R2.attr.actionModeShareDrawable, 255)}, new float[]{0.0f, 0.25f, 0.5f, 0.75f, 1.0f}, Shader.TileMode.CLAMP));
        canvas.drawPath(path, this.strokePaint);
        int i5 = this.pointIndex;
        if (i5 < 0 || i5 >= arrayList.size()) {
            return;
        }
        PointF pointF = (PointF) arrayList.get(this.pointIndex);
        float f2 = pointF.x;
        this.linePaint.setShader(new LinearGradient(f2, pointF.y, f2, height, Color.argb(204, 30, 144, 255), Color.argb(0, 30, 144, 255), Shader.TileMode.CLAMP));
        float f3 = pointF.x;
        canvas.drawLine(f3, pointF.y, f3, height, this.linePaint);
        this.pointPaint.setShader(null);
        this.pointPaint.setColor(-1);
        canvas.drawCircle(pointF.x, pointF.y, 8.0f, this.pointPaint);
        this.pointPaint.setColor(Color.rgb(30, 144, 255));
        canvas.drawCircle(pointF.x, pointF.y, 4.0f, this.pointPaint);
    }

    public void setData(List<Float> newData) {
        this.data = newData;
        invalidate();
    }

    public void setPointIndex(int index) {
        this.pointIndex = index;
        invalidate();
    }

    public WaveChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.data = new ArrayList();
        this.pointIndex = 0;
        init();
    }

    public WaveChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.data = new ArrayList();
        this.pointIndex = 0;
        init();
    }
}
