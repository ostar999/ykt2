package core.interfaces;

/* loaded from: classes8.dex */
public interface CameraEventListener {
    void onCameraClosed();

    void onCameraDisconnected();

    void onCameraError(int i2, String str);

    void onCameraFreezed(String str);

    void onCameraOpening(String str);
}
