package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* loaded from: classes3.dex */
public class TriangleShapeRenderer implements IShapeRenderer {
    protected Path mTrianglePathBuffer = new Path();

    @Override // com.github.mikephil.charting.renderer.scatter.IShapeRenderer
    public void renderShape(Canvas canvas, IScatterDataSet iScatterDataSet, ViewPortHandler viewPortHandler, float f2, float f3, Paint paint) {
        float scatterShapeSize = iScatterDataSet.getScatterShapeSize();
        float f4 = scatterShapeSize / 2.0f;
        float fConvertDpToPixel = (scatterShapeSize - (Utils.convertDpToPixel(iScatterDataSet.getScatterShapeHoleRadius()) * 2.0f)) / 2.0f;
        int scatterShapeHoleColor = iScatterDataSet.getScatterShapeHoleColor();
        paint.setStyle(Paint.Style.FILL);
        Path path = this.mTrianglePathBuffer;
        path.reset();
        float f5 = f3 - f4;
        path.moveTo(f2, f5);
        float f6 = f2 + f4;
        float f7 = f3 + f4;
        path.lineTo(f6, f7);
        float f8 = f2 - f4;
        path.lineTo(f8, f7);
        double d3 = scatterShapeSize;
        if (d3 > 0.0d) {
            path.lineTo(f2, f5);
            float f9 = f8 + fConvertDpToPixel;
            float f10 = f7 - fConvertDpToPixel;
            path.moveTo(f9, f10);
            path.lineTo(f6 - fConvertDpToPixel, f10);
            path.lineTo(f2, f5 + fConvertDpToPixel);
            path.lineTo(f9, f10);
        }
        path.close();
        canvas.drawPath(path, paint);
        path.reset();
        if (d3 <= 0.0d || scatterShapeHoleColor == 1122867) {
            return;
        }
        paint.setColor(scatterShapeHoleColor);
        path.moveTo(f2, f5 + fConvertDpToPixel);
        float f11 = f7 - fConvertDpToPixel;
        path.lineTo(f6 - fConvertDpToPixel, f11);
        path.lineTo(f8 + fConvertDpToPixel, f11);
        path.close();
        canvas.drawPath(path, paint);
        path.reset();
    }
}
