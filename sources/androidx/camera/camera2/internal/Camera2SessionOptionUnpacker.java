package androidx.camera.camera2.internal;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.impl.CameraEventCallbacks;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;

@RequiresApi(21)
/* loaded from: classes.dex */
final class Camera2SessionOptionUnpacker implements SessionConfig.OptionUnpacker {
    static final Camera2SessionOptionUnpacker INSTANCE = new Camera2SessionOptionUnpacker();

    @Override // androidx.camera.core.impl.SessionConfig.OptionUnpacker
    @OptIn(markerClass = {ExperimentalCamera2Interop.class})
    public void unpack(@NonNull UseCaseConfig<?> useCaseConfig, @NonNull SessionConfig.Builder builder) {
        SessionConfig defaultSessionConfig = useCaseConfig.getDefaultSessionConfig(null);
        Config configEmptyBundle = OptionsBundle.emptyBundle();
        int templateType = SessionConfig.defaultEmptySessionConfig().getTemplateType();
        if (defaultSessionConfig != null) {
            templateType = defaultSessionConfig.getTemplateType();
            builder.addAllDeviceStateCallbacks(defaultSessionConfig.getDeviceStateCallbacks());
            builder.addAllSessionStateCallbacks(defaultSessionConfig.getSessionStateCallbacks());
            builder.addAllRepeatingCameraCaptureCallbacks(defaultSessionConfig.getRepeatingCameraCaptureCallbacks());
            configEmptyBundle = defaultSessionConfig.getImplementationOptions();
        }
        builder.setImplementationOptions(configEmptyBundle);
        Camera2ImplConfig camera2ImplConfig = new Camera2ImplConfig(useCaseConfig);
        builder.setTemplateType(camera2ImplConfig.getCaptureRequestTemplate(templateType));
        builder.addDeviceStateCallback(camera2ImplConfig.getDeviceStateCallback(CameraDeviceStateCallbacks.createNoOpCallback()));
        builder.addSessionStateCallback(camera2ImplConfig.getSessionStateCallback(CameraCaptureSessionStateCallbacks.createNoOpCallback()));
        builder.addCameraCaptureCallback(CaptureCallbackContainer.create(camera2ImplConfig.getSessionCaptureCallback(Camera2CaptureCallbacks.createNoOpCallback())));
        MutableOptionsBundle mutableOptionsBundleCreate = MutableOptionsBundle.create();
        mutableOptionsBundleCreate.insertOption(Camera2ImplConfig.CAMERA_EVENT_CALLBACK_OPTION, camera2ImplConfig.getCameraEventCallback(CameraEventCallbacks.createEmptyCallback()));
        mutableOptionsBundleCreate.insertOption(Camera2ImplConfig.SESSION_PHYSICAL_CAMERA_ID_OPTION, camera2ImplConfig.getPhysicalCameraId(null));
        mutableOptionsBundleCreate.insertOption(Camera2ImplConfig.STREAM_USE_CASE_OPTION, Long.valueOf(camera2ImplConfig.getStreamUseCase(-1L)));
        builder.addImplementationOptions(mutableOptionsBundleCreate);
        builder.addImplementationOptions(camera2ImplConfig.getCaptureRequestOptions());
    }
}
