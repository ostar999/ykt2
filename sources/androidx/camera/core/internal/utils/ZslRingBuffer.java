package androidx.camera.core.internal.utils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageInfo;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraCaptureResults;
import androidx.camera.core.internal.utils.RingBuffer;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class ZslRingBuffer extends ArrayRingBuffer<ImageProxy> {
    public ZslRingBuffer(int i2, @NonNull RingBuffer.OnRemoveCallback<ImageProxy> onRemoveCallback) {
        super(i2, onRemoveCallback);
    }

    private boolean isValidZslFrame(@NonNull ImageInfo imageInfo) {
        CameraCaptureResult cameraCaptureResultRetrieveCameraCaptureResult = CameraCaptureResults.retrieveCameraCaptureResult(imageInfo);
        return (cameraCaptureResultRetrieveCameraCaptureResult.getAfState() == CameraCaptureMetaData.AfState.LOCKED_FOCUSED || cameraCaptureResultRetrieveCameraCaptureResult.getAfState() == CameraCaptureMetaData.AfState.PASSIVE_FOCUSED) && cameraCaptureResultRetrieveCameraCaptureResult.getAeState() == CameraCaptureMetaData.AeState.CONVERGED && cameraCaptureResultRetrieveCameraCaptureResult.getAwbState() == CameraCaptureMetaData.AwbState.CONVERGED;
    }

    @Override // androidx.camera.core.internal.utils.ArrayRingBuffer, androidx.camera.core.internal.utils.RingBuffer
    public void enqueue(@NonNull ImageProxy imageProxy) {
        if (isValidZslFrame(imageProxy.getImageInfo())) {
            super.enqueue((ZslRingBuffer) imageProxy);
        } else {
            this.mOnRemoveCallback.onRemove(imageProxy);
        }
    }
}
