package androidx.camera.view.transform;

import android.graphics.Matrix;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.view.TransformExperimental;

@RequiresApi(21)
@TransformExperimental
/* loaded from: classes.dex */
public final class ImageProxyTransformFactory {
    private boolean mUsingCropRect;
    private boolean mUsingRotationDegrees;

    private RectF getCropRect(@NonNull ImageProxy imageProxy) {
        return this.mUsingCropRect ? new RectF(imageProxy.getCropRect()) : new RectF(0.0f, 0.0f, imageProxy.getWidth(), imageProxy.getHeight());
    }

    public static RectF getRotatedCropRect(RectF rectF, int i2) {
        return TransformUtils.is90or270(i2) ? new RectF(0.0f, 0.0f, rectF.height(), rectF.width()) : new RectF(0.0f, 0.0f, rectF.width(), rectF.height());
    }

    private int getRotationDegrees(@NonNull ImageProxy imageProxy) {
        if (this.mUsingRotationDegrees) {
            return imageProxy.getImageInfo().getRotationDegrees();
        }
        return 0;
    }

    @NonNull
    public OutputTransform getOutputTransform(@NonNull ImageProxy imageProxy) {
        int rotationDegrees = getRotationDegrees(imageProxy);
        RectF cropRect = getCropRect(imageProxy);
        Matrix rectToRect = TransformUtils.getRectToRect(cropRect, getRotatedCropRect(cropRect, rotationDegrees), rotationDegrees);
        rectToRect.preConcat(TransformUtils.getNormalizedToBuffer(imageProxy.getCropRect()));
        return new OutputTransform(rectToRect, TransformUtils.rectToSize(imageProxy.getCropRect()));
    }

    public boolean isUsingCropRect() {
        return this.mUsingCropRect;
    }

    public boolean isUsingRotationDegrees() {
        return this.mUsingRotationDegrees;
    }

    public void setUsingCropRect(boolean z2) {
        this.mUsingCropRect = z2;
    }

    public void setUsingRotationDegrees(boolean z2) {
        this.mUsingRotationDegrees = z2;
    }
}
