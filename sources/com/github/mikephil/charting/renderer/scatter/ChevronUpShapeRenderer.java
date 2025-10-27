package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* loaded from: classes3.dex */
public class ChevronUpShapeRenderer implements IShapeRenderer {
    @Override // com.github.mikephil.charting.renderer.scatter.IShapeRenderer
    public void renderShape(Canvas canvas, IScatterDataSet iScatterDataSet, ViewPortHandler viewPortHandler, float f2, float f3, Paint paint) {
        float scatterShapeSize = iScatterDataSet.getScatterShapeSize() / 2.0f;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.convertDpToPixel(1.0f));
        float f4 = scatterShapeSize * 2.0f;
        float f5 = f3 - f4;
        canvas.drawLine(f2, f5, f2 + f4, f3, paint);
        canvas.drawLine(f2, f5, f2 - f4, f3, paint);
    }
}
