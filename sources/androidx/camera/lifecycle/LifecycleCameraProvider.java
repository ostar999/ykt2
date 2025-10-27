package androidx.camera.lifecycle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.UseCase;

@RequiresApi(21)
/* loaded from: classes.dex */
interface LifecycleCameraProvider extends CameraProvider {
    boolean isBound(@NonNull UseCase useCase);

    void unbind(@NonNull UseCase... useCaseArr);

    void unbindAll();
}
