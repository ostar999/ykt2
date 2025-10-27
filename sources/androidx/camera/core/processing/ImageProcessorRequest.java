package androidx.camera.core.processing;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProcessor;
import androidx.camera.core.ImageProxy;
import java.util.List;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public class ImageProcessorRequest implements ImageProcessor.Request {

    @NonNull
    private final List<ImageProxy> mImageProxies;
    private final int mOutputFormat;

    public ImageProcessorRequest(@NonNull List<ImageProxy> list, int i2) {
        this.mImageProxies = list;
        this.mOutputFormat = i2;
    }

    @Override // androidx.camera.core.ImageProcessor.Request
    @NonNull
    public List<ImageProxy> getInputImages() {
        return this.mImageProxies;
    }

    @Override // androidx.camera.core.ImageProcessor.Request
    public int getOutputFormat() {
        return this.mOutputFormat;
    }
}
