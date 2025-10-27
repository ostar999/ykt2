package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@RequiresApi(api = 21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class CameraEffect {
    public static final int IMAGE_CAPTURE = 4;
    public static final int PREVIEW = 1;
    public static final int VIDEO_CAPTURE = 2;

    @Nullable
    private final ImageProcessor mImageProcessor;

    @NonNull
    private final Executor mProcessorExecutor;

    @Nullable
    private final SurfaceProcessor mSurfaceProcessor;
    private final int mTargets;

    public static class Builder {

        @Nullable
        private ImageProcessor mImageProcessor;

        @Nullable
        private Executor mProcessorExecutor;

        @Nullable
        private SurfaceProcessor mSurfaceProcessor;
        private final int mTargets;

        public Builder(int i2) {
            this.mTargets = i2;
        }

        @NonNull
        public CameraEffect build() {
            Preconditions.checkState(this.mProcessorExecutor != null, "Must have a executor");
            Preconditions.checkState((this.mImageProcessor != null) ^ (this.mSurfaceProcessor != null), "Must have one and only one processor");
            SurfaceProcessor surfaceProcessor = this.mSurfaceProcessor;
            return surfaceProcessor != null ? new CameraEffect(this.mTargets, this.mProcessorExecutor, surfaceProcessor) : new CameraEffect(this.mTargets, this.mProcessorExecutor, this.mImageProcessor);
        }

        @NonNull
        public Builder setImageProcessor(@NonNull Executor executor, @NonNull ImageProcessor imageProcessor) {
            this.mProcessorExecutor = executor;
            this.mImageProcessor = imageProcessor;
            return this;
        }

        @NonNull
        public Builder setSurfaceProcessor(@NonNull Executor executor, @NonNull SurfaceProcessor surfaceProcessor) {
            this.mProcessorExecutor = executor;
            this.mSurfaceProcessor = surfaceProcessor;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface Targets {
    }

    public CameraEffect(int i2, @NonNull Executor executor, @NonNull ImageProcessor imageProcessor) {
        this.mTargets = i2;
        this.mProcessorExecutor = executor;
        this.mSurfaceProcessor = null;
        this.mImageProcessor = imageProcessor;
    }

    @Nullable
    public ImageProcessor getImageProcessor() {
        return this.mImageProcessor;
    }

    @NonNull
    public Executor getProcessorExecutor() {
        return this.mProcessorExecutor;
    }

    @Nullable
    public SurfaceProcessor getSurfaceProcessor() {
        return this.mSurfaceProcessor;
    }

    public int getTargets() {
        return this.mTargets;
    }

    public CameraEffect(int i2, @NonNull Executor executor, @NonNull SurfaceProcessor surfaceProcessor) {
        this.mTargets = i2;
        this.mProcessorExecutor = executor;
        this.mSurfaceProcessor = surfaceProcessor;
        this.mImageProcessor = null;
    }
}
