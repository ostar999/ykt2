package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* loaded from: classes3.dex */
public class XShapeRenderer implements IShapeRenderer {
    @Override // com.github.mikephil.charting.renderer.scatter.IShapeRenderer
    public void renderShape(Canvas canvas, IScatterDataSet iScatterDataSet, ViewPortHandler viewPortHandler, float f2, float f3, Paint paint) {
        float scatterShapeSize = iScatterDataSet.getScatterShapeSize() / 2.0f;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.convertDpToPixel(1.0f));
        float f4 = f2 - scatterShapeSize;
        float f5 = f3 - scatterShapeSize;
        float f6 = f2 + scatterShapeSize;
        float f7 = scatterShapeSize + f3;
        canvas.drawLine(f4, f5, f6, f7, paint);
        canvas.drawLine(f6, f5, f4, f7, paint);
    }
}
