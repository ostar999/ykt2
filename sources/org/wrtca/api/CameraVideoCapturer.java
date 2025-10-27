package org.wrtca.api;

import android.media.MediaRecorder;
import com.google.android.exoplayer2.ExoPlayer;
import org.wrtca.log.Logging;
import org.wrtca.video.CameraSession;

/* loaded from: classes9.dex */
public interface CameraVideoCapturer extends VideoCapturer {

    public interface CameraEventsHandler {
        void onCameraClosed();

        void onCameraDisconnected();

        void onCameraError(int i2, String str);

        void onCameraFreezed(String str);

        void onCameraOpening(String str);

        VideoFrame onCaptureFrame(CameraSession cameraSession, VideoFrame videoFrame);

        void onFirstFrameAvailable();
    }

    public static class CameraStatistics {
        private static final int CAMERA_FREEZE_REPORT_TIMOUT_MS = 4000;
        private static final int CAMERA_OBSERVER_PERIOD_MS = 2000;
        private static final String TAG = "CameraStatistics";
        private final Runnable cameraObserver;
        private final CameraEventsHandler eventsHandler;
        private int frameCount;
        private int freezePeriodCount;
        private final SurfaceTextureHelper surfaceTextureHelper;

        public CameraStatistics(SurfaceTextureHelper surfaceTextureHelper, CameraEventsHandler cameraEventsHandler) {
            Runnable runnable = new Runnable() { // from class: org.wrtca.api.CameraVideoCapturer.CameraStatistics.1
                @Override // java.lang.Runnable
                public void run() {
                    Math.round((CameraStatistics.this.frameCount * 1000.0f) / 2000.0f);
                    if (CameraStatistics.this.frameCount == 0) {
                        CameraStatistics.access$104(CameraStatistics.this);
                        if (CameraStatistics.this.freezePeriodCount * 2000 >= 4000 && CameraStatistics.this.eventsHandler != null) {
                            Logging.e(CameraStatistics.TAG, "Camera freezed.");
                            if (CameraStatistics.this.surfaceTextureHelper.isTextureInUse()) {
                                CameraStatistics.this.eventsHandler.onCameraFreezed("Camera failure. Client must return video buffers.");
                                return;
                            } else {
                                CameraStatistics.this.eventsHandler.onCameraFreezed("Camera failure.");
                                return;
                            }
                        }
                    } else {
                        CameraStatistics.this.freezePeriodCount = 0;
                    }
                    CameraStatistics.this.frameCount = 0;
                    CameraStatistics.this.surfaceTextureHelper.getHandler().postDelayed(this, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            };
            this.cameraObserver = runnable;
            if (surfaceTextureHelper == null) {
                throw new IllegalArgumentException("SurfaceTextureHelper is null");
            }
            this.surfaceTextureHelper = surfaceTextureHelper;
            this.eventsHandler = cameraEventsHandler;
            this.frameCount = 0;
            this.freezePeriodCount = 0;
            surfaceTextureHelper.getHandler().postDelayed(runnable, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }

        public static /* synthetic */ int access$104(CameraStatistics cameraStatistics) {
            int i2 = cameraStatistics.freezePeriodCount + 1;
            cameraStatistics.freezePeriodCount = i2;
            return i2;
        }

        private void checkThread() {
            if (Thread.currentThread() != this.surfaceTextureHelper.getHandler().getLooper().getThread()) {
                throw new IllegalStateException("Wrong thread");
            }
        }

        public void addFrame() {
            checkThread();
            this.frameCount++;
        }

        public void release() {
            this.surfaceTextureHelper.getHandler().removeCallbacks(this.cameraObserver);
        }
    }

    public interface CameraSwitchHandler {
        void onCameraSwitchDone(boolean z2);

        void onCameraSwitchError(String str);
    }

    public interface MediaRecorderHandler {
        void onMediaRecorderError(String str);

        void onMediaRecorderSuccess();
    }

    void addMediaRecorderToCamera(MediaRecorder mediaRecorder, MediaRecorderHandler mediaRecorderHandler);

    void removeMediaRecorderFromCamera(MediaRecorderHandler mediaRecorderHandler);

    void setCameraId(int i2, CameraSwitchHandler cameraSwitchHandler);

    void switchCamera(boolean z2, CameraSwitchHandler cameraSwitchHandler);

    void turnFlashLightOff();

    void turnFlashLightOn();
}
