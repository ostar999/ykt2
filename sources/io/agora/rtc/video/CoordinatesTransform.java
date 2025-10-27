package io.agora.rtc.video;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

/* loaded from: classes8.dex */
public class CoordinatesTransform {
    public static RectF normalizedFaceRect(Rect rect, int displayOrientation, boolean isMirror) {
        Matrix matrix = new Matrix();
        prepareMatrix(matrix, isMirror, displayOrientation);
        RectF rectF = new RectF(rect);
        matrix.mapRect(rectF);
        return rectF;
    }

    private static void prepareMatrix(Matrix matrix, boolean mirror, int displayOrientation) {
        matrix.setScale(mirror ? -1.0f : 1.0f, 1.0f);
        matrix.postRotate(displayOrientation);
        matrix.postScale(5.0E-4f, 5.0E-4f);
        matrix.postTranslate(0.5f, 0.5f);
    }

    public static Rect sensorToNormalizedPreview(Rect transformRect, int previewWidth, int previewHeight, Rect cropRegion) {
        double d3;
        double d4;
        if (previewWidth > previewHeight) {
            d3 = previewWidth;
            d4 = previewHeight;
        } else {
            d3 = previewHeight;
            d4 = previewWidth;
        }
        double d5 = d3 / d4;
        double dWidth = cropRegion.width() / cropRegion.height();
        int iWidth = cropRegion.width();
        int iHeight = cropRegion.height();
        if (d5 > dWidth) {
            iHeight = (int) (iWidth / d5);
        } else {
            iWidth = (int) (iHeight * d5);
        }
        int iAbs = Math.abs(iWidth - cropRegion.width());
        int iAbs2 = Math.abs(iHeight - cropRegion.height());
        RectF rectF = new RectF(transformRect);
        Matrix matrix = new Matrix();
        matrix.postTranslate((-cropRegion.left) - (iAbs / 2), (-cropRegion.top) - (iAbs2 / 2));
        matrix.postTranslate((-iWidth) / 2, (-iHeight) / 2);
        matrix.postScale(2000.0f / iWidth, 2000.0f / iHeight);
        matrix.mapRect(rectF);
        Rect rect = new Rect();
        rectF.round(rect);
        return rect;
    }
}
