package androidx.camera.core;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public interface ImageProcessor {

    public interface Request {
        @NonNull
        @AnyThread
        List<ImageProxy> getInputImages();

        @AnyThread
        int getOutputFormat();
    }

    public interface Response {
        @Nullable
        @AnyThread
        ImageProxy getOutputImage();
    }

    @NonNull
    Response process(@NonNull Request request);
}
