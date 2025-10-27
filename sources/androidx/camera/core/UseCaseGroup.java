package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class UseCaseGroup {

    @NonNull
    private final List<CameraEffect> mEffects;

    @NonNull
    private final List<UseCase> mUseCases;

    @Nullable
    private final ViewPort mViewPort;

    public static final class Builder {
        private ViewPort mViewPort;
        private final List<UseCase> mUseCases = new ArrayList();
        private final List<CameraEffect> mEffects = new ArrayList();

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder addEffect(@NonNull CameraEffect cameraEffect) {
            this.mEffects.add(cameraEffect);
            return this;
        }

        @NonNull
        public Builder addUseCase(@NonNull UseCase useCase) {
            this.mUseCases.add(useCase);
            return this;
        }

        @NonNull
        public UseCaseGroup build() {
            Preconditions.checkArgument(!this.mUseCases.isEmpty(), "UseCase must not be empty.");
            return new UseCaseGroup(this.mViewPort, this.mUseCases, this.mEffects);
        }

        @NonNull
        public Builder setViewPort(@NonNull ViewPort viewPort) {
            this.mViewPort = viewPort;
            return this;
        }
    }

    public UseCaseGroup(@Nullable ViewPort viewPort, @NonNull List<UseCase> list, @NonNull List<CameraEffect> list2) {
        this.mViewPort = viewPort;
        this.mUseCases = list;
        this.mEffects = list2;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public List<CameraEffect> getEffects() {
        return this.mEffects;
    }

    @NonNull
    public List<UseCase> getUseCases() {
        return this.mUseCases;
    }

    @Nullable
    public ViewPort getViewPort() {
        return this.mViewPort;
    }
}
