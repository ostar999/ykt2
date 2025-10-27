package com.psychiatrygarden.activity.chooseSchool.widget;

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
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes5.dex */
public class WaveChartViewNew extends View {
    private String bgColor;
    private String bgColor2;
    private float controlX;
    private float controlY;
    private List<Float> data;
    private float endX;
    private float endY;
    private float fillGap;
    private Paint fillPaint;
    private float gapBottom;
    private boolean isNight;
    private Paint linePaint;
    private List<PointF> mListBeZierPoint;
    private List<PointF> mListPoint;
    private float marginHorizontal;
    private int pointIndex;
    private Paint pointPaint;
    private float startX;
    private float startY;
    private Paint strokePaint;
    private float targetX;

    public WaveChartViewNew(Context context) {
        super(context);
        this.data = new ArrayList();
        this.pointIndex = 0;
        this.mListPoint = new ArrayList();
        this.mListBeZierPoint = new ArrayList();
        this.gapBottom = 40.0f;
        this.marginHorizontal = 10.0f;
        this.fillGap = 6.0f;
        this.isNight = false;
        this.bgColor = "#D9E7FF";
        this.bgColor2 = "#FFFFFF";
        init();
    }

    private void init() {
        boolean z2 = SkinManager.getCurrentSkinType(getContext()) == 1;
        this.isNight = z2;
        if (z2) {
            this.bgColor = "#162137";
            this.bgColor2 = "#121622";
        }
        Paint paint = new Paint(1);
        this.fillPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.strokePaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.strokePaint.setStrokeWidth(12.0f);
        Paint paint3 = new Paint(1);
        this.linePaint = paint3;
        paint3.setStyle(Paint.Style.STROKE);
        this.linePaint.setStrokeWidth(8.0f);
        Paint paint4 = new Paint(1);
        this.pointPaint = paint4;
        paint4.setStyle(Paint.Style.FILL);
        this.data.add(Float.valueOf(10.0f));
        this.data.add(Float.valueOf(20.0f));
        this.data.add(Float.valueOf(48.0f));
        this.data.add(Float.valueOf(70.0f));
        this.data.add(Float.valueOf(50.0f));
        this.data.add(Float.valueOf(80.0f));
        this.data.add(Float.valueOf(20.0f));
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<Float> list = this.data;
        if (list == null || list.size() < 2) {
            return;
        }
        float f2 = 2.0f;
        float width = getWidth() - (this.marginHorizontal * 2.0f);
        float height = getHeight();
        float size = width / (this.data.size() - 1);
        float fFloatValue = ((Float) Collections.max(this.data)).floatValue();
        float fFloatValue2 = ((Float) Collections.min(this.data)).floatValue();
        ArrayList arrayList = new ArrayList();
        if (fFloatValue == fFloatValue2) {
            float f3 = height - (0.4f * height);
            for (int i2 = 0; i2 < this.data.size(); i2++) {
                arrayList.add(new PointF((i2 * size) + this.marginHorizontal, f3 - this.gapBottom));
            }
        } else {
            for (int i3 = 0; i3 < this.data.size(); i3++) {
                arrayList.add(new PointF((i3 * size) + this.marginHorizontal, (height - (((this.data.get(i3).floatValue() - fFloatValue2) / (fFloatValue - fFloatValue2)) * (0.8f * height))) - this.gapBottom));
            }
        }
        Path path = new Path();
        Path path2 = new Path();
        path2.moveTo(((PointF) arrayList.get(0)).x, ((PointF) arrayList.get(0)).y);
        path.moveTo(((PointF) arrayList.get(0)).x, ((PointF) arrayList.get(0)).y - this.fillGap);
        this.startX = ((PointF) arrayList.get(0)).x;
        this.startY = ((PointF) arrayList.get(0)).y;
        this.mListPoint.add(new PointF(((PointF) arrayList.get(0)).x, ((PointF) arrayList.get(0)).y));
        int i4 = 1;
        while (i4 < arrayList.size() - 1) {
            PointF pointF = (PointF) arrayList.get(i4);
            PointF pointF2 = (PointF) arrayList.get(i4 - 1);
            float f4 = pointF.x;
            float f5 = pointF2.x;
            float f6 = (f4 + f5) / f2;
            float f7 = pointF2.y;
            float f8 = (f5 + f4) / f2;
            float f9 = pointF.y;
            path2.cubicTo(f6, f7, f8, f9, f4, f9);
            float f10 = this.fillGap;
            path.cubicTo(f6, f7 - f10, f8, f9 - f10, pointF.x, pointF.y - f10);
            this.mListPoint.add(new PointF(pointF.x, pointF.y));
            i4++;
            f2 = 2.0f;
        }
        path2.lineTo(((PointF) arrayList.get(arrayList.size() - 1)).x, ((PointF) arrayList.get(arrayList.size() - 1)).y);
        path.lineTo(((PointF) arrayList.get(arrayList.size() - 1)).x, ((PointF) arrayList.get(arrayList.size() - 1)).y - this.fillGap);
        Path path3 = new Path(path2);
        path3.lineTo(((PointF) arrayList.get(arrayList.size() - 1)).x, height);
        path3.lineTo(((PointF) arrayList.get(0)).x, height);
        path3.close();
        this.fillPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, height - this.fillGap, new int[]{Color.parseColor(this.bgColor2), Color.parseColor(this.bgColor2), Color.parseColor(this.bgColor), Color.parseColor(this.bgColor2)}, new float[]{0.1f, 0.1f, 0.15f, 0.8f}, Shader.TileMode.CLAMP));
        canvas.drawPath(path3, this.fillPaint);
        this.strokePaint.setShader(new LinearGradient(0.0f, 0.0f, width, 0.0f, new int[]{Color.argb(0, 154, R2.attr.actionModeShareDrawable, 255), Color.argb(128, 104, 188, 255), Color.argb(255, 54, 125, 255), Color.argb(128, 104, 188, 255), Color.argb(0, 154, R2.attr.actionModeShareDrawable, 255)}, new float[]{0.0f, 0.25f, 0.5f, 0.75f, 1.0f}, Shader.TileMode.CLAMP));
        canvas.drawPath(path2, this.strokePaint);
        int i5 = this.pointIndex;
        if (i5 < 0 || i5 >= arrayList.size()) {
            return;
        }
        PointF pointF3 = (PointF) arrayList.get(this.pointIndex);
        float f11 = pointF3.x;
        this.linePaint.setShader(new LinearGradient(f11, pointF3.y, f11, height, Color.argb(204, 30, 144, 255), Color.argb(0, 30, 144, 255), Shader.TileMode.CLAMP));
        float f12 = pointF3.x;
        canvas.drawLine(f12, pointF3.y, f12, height, this.linePaint);
        this.pointPaint.setShader(null);
        this.pointPaint.setColor(-1);
        canvas.drawCircle(pointF3.x, pointF3.y, 12.0f, this.pointPaint);
        this.pointPaint.setColor(Color.rgb(30, 144, 255));
        canvas.drawCircle(pointF3.x, pointF3.y, 8.0f, this.pointPaint);
    }

    public void setData(List<Float> newData) {
        this.data = newData;
        invalidate();
    }

    public void setPointIndex(int index) {
        this.pointIndex = index;
        invalidate();
    }

    public WaveChartViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.data = new ArrayList();
        this.pointIndex = 0;
        this.mListPoint = new ArrayList();
        this.mListBeZierPoint = new ArrayList();
        this.gapBottom = 40.0f;
        this.marginHorizontal = 10.0f;
        this.fillGap = 6.0f;
        this.isNight = false;
        this.bgColor = "#D9E7FF";
        this.bgColor2 = "#FFFFFF";
        init();
    }

    public WaveChartViewNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.data = new ArrayList();
        this.pointIndex = 0;
        this.mListPoint = new ArrayList();
        this.mListBeZierPoint = new ArrayList();
        this.gapBottom = 40.0f;
        this.marginHorizontal = 10.0f;
        this.fillGap = 6.0f;
        this.isNight = false;
        this.bgColor = "#D9E7FF";
        this.bgColor2 = "#FFFFFF";
        init();
    }
}
