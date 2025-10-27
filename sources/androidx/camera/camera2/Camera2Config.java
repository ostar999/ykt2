package androidx.camera.camera2;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.camera2.internal.Camera2CameraFactory;
import androidx.camera.camera2.internal.Camera2DeviceSurfaceManager;
import androidx.camera.camera2.internal.Camera2UseCaseConfigFactory;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.InitializationException;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.CameraThreadConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import java.util.Set;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class Camera2Config {

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final class DefaultProvider implements CameraXConfig.Provider {
        @Override // androidx.camera.core.CameraXConfig.Provider
        @NonNull
        public CameraXConfig getCameraXConfig() {
            return Camera2Config.defaultConfig();
        }
    }

    private Camera2Config() {
    }

    @NonNull
    public static CameraXConfig defaultConfig() {
        CameraFactory.Provider provider = new CameraFactory.Provider() { // from class: m.a
            @Override // androidx.camera.core.impl.CameraFactory.Provider
            public final CameraFactory newInstance(Context context, CameraThreadConfig cameraThreadConfig, CameraSelector cameraSelector) {
                return new Camera2CameraFactory(context, cameraThreadConfig, cameraSelector);
            }
        };
        CameraDeviceSurfaceManager.Provider provider2 = new CameraDeviceSurfaceManager.Provider() { // from class: m.b
            @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager.Provider
            public final CameraDeviceSurfaceManager newInstance(Context context, Object obj, Set set) {
                return Camera2Config.lambda$defaultConfig$0(context, obj, set);
            }
        };
        return new CameraXConfig.Builder().setCameraFactoryProvider(provider).setDeviceSurfaceManagerProvider(provider2).setUseCaseConfigFactoryProvider(new UseCaseConfigFactory.Provider() { // from class: m.c
            @Override // androidx.camera.core.impl.UseCaseConfigFactory.Provider
            public final UseCaseConfigFactory newInstance(Context context) {
                return Camera2Config.lambda$defaultConfig$1(context);
            }
        }).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CameraDeviceSurfaceManager lambda$defaultConfig$0(Context context, Object obj, Set set) throws InitializationException {
        try {
            return new Camera2DeviceSurfaceManager(context, obj, set);
        } catch (CameraUnavailableException e2) {
            throw new InitializationException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ UseCaseConfigFactory lambda$defaultConfig$1(Context context) throws InitializationException {
        return new Camera2UseCaseConfigFactory(context);
    }
}
