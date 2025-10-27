package org.wrtca.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Looper;
import c.h;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import com.plv.livescenes.log.linkmic.PLVLinkMicELog;
import java.util.Arrays;
import org.wrtca.api.Camera2Enumerator;
import org.wrtca.api.CameraEnumerator;
import org.wrtca.api.CameraVideoCapturer;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.api.VideoCapturer;
import org.wrtca.api.VideoFrame;
import org.wrtca.log.Logging;
import org.wrtca.record.MediaRecorderNative;
import org.wrtca.record.model.CameraParamObserver;
import org.wrtca.video.CameraSession;

@TargetApi(21)
/* loaded from: classes9.dex */
public abstract class CameraCapturer implements CameraVideoCapturer {
    private static final int MAX_OPEN_CAMERA_ATTEMPTS = 3;
    private static final int OPEN_CAMERA_DELAY_MS = 500;
    public static final int OPEN_CAMERA_TIMEOUT = 10000;
    private static final String TAG = "CameraCapturer";
    public Context applicationContext;
    private final CameraEnumerator cameraEnumerator;
    public String cameraName;
    private CameraVideoCapturer.CameraStatistics cameraStatistics;
    public Handler cameraThreadHandler;
    private VideoCapturer.CapturerObserver capturerObserver;
    private CameraSession currentSession;
    private final CameraVideoCapturer.CameraEventsHandler eventsHandler;
    private boolean firstFrameObserved;
    public int framerate;
    public int height;
    private CameraSession.CameraParam mCameraParam;
    public CameraParamObserver mParamObserver;
    private CameraVideoCapturer.MediaRecorderHandler mediaRecorderEventsHandler;
    private int openAttemptsRemaining;
    private boolean sessionOpening;
    private boolean skipFlag;
    public SurfaceTextureHelper surfaceHelper;
    private CameraVideoCapturer.CameraSwitchHandler switchEventsHandler;
    public final Handler uiThreadHandler;
    public int width;
    public final CameraSession.CreateSessionCallback createSessionCallback = new CameraSession.CreateSessionCallback() { // from class: org.wrtca.video.CameraCapturer.1
        @Override // org.wrtca.video.CameraSession.CreateSessionCallback
        public void onDone(CameraSession cameraSession) {
            CameraCapturer.this.checkIsOnCameraThread();
            h.a(CameraCapturer.TAG, "Create session done. Switch state: " + CameraCapturer.this.switchState + ". MediaRecorder state: " + CameraCapturer.this.mediaRecorderState);
            Logging.d(CameraCapturer.TAG, "Create session done. Switch state: " + CameraCapturer.this.switchState + ". MediaRecorder state: " + CameraCapturer.this.mediaRecorderState);
            CameraCapturer cameraCapturer = CameraCapturer.this;
            cameraCapturer.uiThreadHandler.removeCallbacks(cameraCapturer.openCameraTimeoutRunnable);
            synchronized (CameraCapturer.this.stateLock) {
                CameraCapturer.this.capturerObserver.onCapturerStarted(true);
                CameraCapturer.this.sessionOpening = false;
                CameraCapturer.this.currentSession = cameraSession;
                CameraCapturer cameraCapturer2 = CameraCapturer.this;
                cameraCapturer2.mCameraParam = cameraCapturer2.currentSession.requestCamera();
                CameraCapturer cameraCapturer3 = CameraCapturer.this;
                cameraCapturer3.cameraStatistics = new CameraVideoCapturer.CameraStatistics(cameraCapturer3.surfaceHelper, cameraCapturer3.eventsHandler);
                CameraCapturer.this.firstFrameObserved = false;
                CameraCapturer.this.stateLock.notifyAll();
                if (CameraCapturer.this.switchState == SwitchState.IN_PROGRESS) {
                    if (CameraCapturer.this.switchEventsHandler != null) {
                        CameraCapturer.this.switchEventsHandler.onCameraSwitchDone(CameraCapturer.this.cameraEnumerator.isFrontFacing(CameraCapturer.this.cameraName));
                        CameraCapturer.this.switchEventsHandler = null;
                    }
                    CameraCapturer.this.switchState = SwitchState.IDLE;
                } else if (CameraCapturer.this.switchState == SwitchState.PENDING) {
                    CameraCapturer.this.switchState = SwitchState.IDLE;
                    CameraCapturer cameraCapturer4 = CameraCapturer.this;
                    cameraCapturer4.switchCameraInternal(cameraCapturer4.skipFlag, CameraCapturer.this.switchEventsHandler);
                }
                MediaRecorderState mediaRecorderState = CameraCapturer.this.mediaRecorderState;
                MediaRecorderState mediaRecorderState2 = MediaRecorderState.IDLE_TO_ACTIVE;
                if (mediaRecorderState == mediaRecorderState2 || CameraCapturer.this.mediaRecorderState == MediaRecorderState.ACTIVE_TO_IDLE) {
                    if (CameraCapturer.this.mediaRecorderEventsHandler != null) {
                        CameraCapturer.this.mediaRecorderEventsHandler.onMediaRecorderSuccess();
                        CameraCapturer.this.mediaRecorderEventsHandler = null;
                    }
                    if (CameraCapturer.this.mediaRecorderState == mediaRecorderState2) {
                        CameraCapturer.this.mediaRecorderState = MediaRecorderState.ACTIVE;
                    } else {
                        CameraCapturer.this.mediaRecorderState = MediaRecorderState.IDLE;
                    }
                }
            }
        }

        @Override // org.wrtca.video.CameraSession.CreateSessionCallback
        public void onFailure(CameraSession.FailureType failureType, int i2, String str) {
            CameraCapturer.this.checkIsOnCameraThread();
            CameraCapturer cameraCapturer = CameraCapturer.this;
            cameraCapturer.uiThreadHandler.removeCallbacks(cameraCapturer.openCameraTimeoutRunnable);
            synchronized (CameraCapturer.this.stateLock) {
                CameraCapturer.this.capturerObserver.onCapturerStarted(false);
                CameraCapturer.access$1610(CameraCapturer.this);
                if (CameraCapturer.this.openAttemptsRemaining <= 0) {
                    Logging.w(CameraCapturer.TAG, "Opening camera failed, passing: " + str);
                    CameraCapturer.this.sessionOpening = false;
                    CameraCapturer.this.stateLock.notifyAll();
                    SwitchState switchState = CameraCapturer.this.switchState;
                    SwitchState switchState2 = SwitchState.IDLE;
                    if (switchState != switchState2) {
                        if (CameraCapturer.this.switchEventsHandler != null) {
                            CameraCapturer.this.switchEventsHandler.onCameraSwitchError(str);
                            CameraCapturer.this.switchEventsHandler = null;
                        }
                        CameraCapturer.this.switchState = switchState2;
                    }
                    MediaRecorderState mediaRecorderState = CameraCapturer.this.mediaRecorderState;
                    MediaRecorderState mediaRecorderState2 = MediaRecorderState.IDLE;
                    if (mediaRecorderState != mediaRecorderState2) {
                        if (CameraCapturer.this.mediaRecorderEventsHandler != null) {
                            CameraCapturer.this.mediaRecorderEventsHandler.onMediaRecorderError(str);
                            CameraCapturer.this.mediaRecorderEventsHandler = null;
                        }
                        CameraCapturer.this.mediaRecorderState = mediaRecorderState2;
                    }
                    if (failureType == CameraSession.FailureType.DISCONNECTED) {
                        CameraCapturer.this.eventsHandler.onCameraDisconnected();
                    } else {
                        CameraCapturer.this.eventsHandler.onCameraError(i2, str);
                    }
                } else {
                    Logging.w(CameraCapturer.TAG, "Opening camera failed, retry: " + str);
                    CameraCapturer.this.createSessionInternal(500, null);
                }
            }
        }
    };
    public final CameraSession.Events cameraSessionEventsHandler = new CameraSession.Events() { // from class: org.wrtca.video.CameraCapturer.2
        @Override // org.wrtca.video.CameraSession.Events
        public void onCameraClosed(CameraSession cameraSession) {
            CameraCapturer.this.checkIsOnCameraThread();
            synchronized (CameraCapturer.this.stateLock) {
                if (cameraSession != CameraCapturer.this.currentSession && CameraCapturer.this.currentSession != null) {
                    Logging.d(CameraCapturer.TAG, "onCameraClosed from another session.");
                    return;
                }
                CameraCapturer.this.eventsHandler.onCameraClosed();
                CameraParamObserver cameraParamObserver = CameraCapturer.this.mParamObserver;
                if (cameraParamObserver != null) {
                    cameraParamObserver.reportCameraClosed();
                }
            }
        }

        @Override // org.wrtca.video.CameraSession.Events
        public void onCameraDisconnected(CameraSession cameraSession) {
            CameraCapturer.this.checkIsOnCameraThread();
            synchronized (CameraCapturer.this.stateLock) {
                if (cameraSession != CameraCapturer.this.currentSession) {
                    Logging.w(CameraCapturer.TAG, "onCameraDisconnected from another session.");
                } else {
                    CameraCapturer.this.eventsHandler.onCameraDisconnected();
                    CameraCapturer.this.stopCapture();
                }
            }
        }

        @Override // org.wrtca.video.CameraSession.Events
        public void onCameraError(CameraSession cameraSession, String str) {
            CameraCapturer.this.checkIsOnCameraThread();
            synchronized (CameraCapturer.this.stateLock) {
                if (cameraSession == CameraCapturer.this.currentSession) {
                    CameraCapturer.this.eventsHandler.onCameraError(-1, str);
                    CameraCapturer.this.stopCapture();
                } else {
                    Logging.w(CameraCapturer.TAG, "onCameraError from another session: " + str);
                }
            }
        }

        @Override // org.wrtca.video.CameraSession.Events
        public void onCameraOpening() {
            CameraCapturer.this.checkIsOnCameraThread();
            synchronized (CameraCapturer.this.stateLock) {
                if (CameraCapturer.this.currentSession != null) {
                    Logging.w(CameraCapturer.TAG, "onCameraOpening while session was open.");
                } else {
                    CameraCapturer.this.eventsHandler.onCameraOpening(CameraCapturer.this.cameraName);
                }
            }
        }

        @Override // org.wrtca.video.CameraSession.Events
        public void onFrameCaptured(CameraSession cameraSession, VideoFrame videoFrame) {
            VideoFrame videoFrameOnCaptureFrame;
            CameraCapturer.this.checkIsOnCameraThread();
            synchronized (CameraCapturer.this.stateLock) {
                if (cameraSession != CameraCapturer.this.currentSession) {
                    Logging.w(CameraCapturer.TAG, "onTextureFrameCaptured from another session.");
                    return;
                }
                boolean z2 = true;
                if (CameraCapturer.this.eventsHandler != null) {
                    if (!CameraCapturer.this.firstFrameObserved) {
                        CameraCapturer.this.eventsHandler.onFirstFrameAvailable();
                        CameraCapturer.this.firstFrameObserved = true;
                    }
                    videoFrameOnCaptureFrame = CameraCapturer.this.eventsHandler.onCaptureFrame(cameraSession, videoFrame);
                } else {
                    videoFrameOnCaptureFrame = null;
                }
                CameraCapturer.this.cameraStatistics.addFrame();
                if (videoFrameOnCaptureFrame != null) {
                    if ((cameraSession instanceof Camera1Session) && (videoFrameOnCaptureFrame.getBuffer() instanceof NV21Buffer)) {
                        NV21Buffer nV21Buffer = (NV21Buffer) videoFrameOnCaptureFrame.getBuffer();
                        if (((Camera1Session) cameraSession).getInfo().facing != 1) {
                            z2 = false;
                        }
                        nV21Buffer.setNeedMirror(z2);
                    }
                    boolean z3 = videoFrameOnCaptureFrame.getBuffer() instanceof TextureBufferImpl;
                    CameraCapturer.this.capturerObserver.onFrameCaptured(videoFrameOnCaptureFrame);
                    videoFrameOnCaptureFrame.release();
                } else {
                    if ((cameraSession instanceof Camera1Session) && (videoFrame.getBuffer() instanceof NV21Buffer)) {
                        NV21Buffer nV21Buffer2 = (NV21Buffer) videoFrame.getBuffer();
                        if (((Camera1Session) cameraSession).getInfo().facing != 1) {
                            z2 = false;
                        }
                        nV21Buffer2.setNeedMirror(z2);
                    }
                    CameraCapturer.this.capturerObserver.onFrameCaptured(videoFrame);
                }
            }
        }

        @Override // org.wrtca.video.CameraSession.Events
        public void onFrameCaptured(CameraSession cameraSession, VideoFrame videoFrame, boolean z2) {
            CameraCapturer.this.checkIsOnCameraThread();
            synchronized (CameraCapturer.this.stateLock) {
                if (cameraSession != CameraCapturer.this.currentSession) {
                    Logging.w(CameraCapturer.TAG, "onTextureFrameCaptured from another session.");
                    return;
                }
                if (!CameraCapturer.this.firstFrameObserved) {
                    CameraCapturer.this.eventsHandler.onFirstFrameAvailable();
                    CameraCapturer.this.firstFrameObserved = true;
                }
                CameraCapturer.this.cameraStatistics.addFrame();
                if (videoFrame.getBuffer() instanceof NV21Buffer) {
                    ((NV21Buffer) videoFrame.getBuffer()).setNeedMirror(z2);
                }
                CameraCapturer.this.capturerObserver.onFrameCaptured(videoFrame);
            }
        }
    };
    public final Runnable openCameraTimeoutRunnable = new Runnable() { // from class: org.wrtca.video.CameraCapturer.3
        @Override // java.lang.Runnable
        public void run() {
            CameraCapturer.this.eventsHandler.onCameraError(-1, "Camera failed to start within timeout.");
        }
    };
    private final Object stateLock = new Object();
    private SwitchState switchState = SwitchState.IDLE;
    private MediaRecorderState mediaRecorderState = MediaRecorderState.IDLE;

    public enum MediaRecorderState {
        IDLE,
        IDLE_TO_ACTIVE,
        ACTIVE_TO_IDLE,
        ACTIVE
    }

    public enum SwitchState {
        IDLE,
        PENDING,
        IN_PROGRESS
    }

    public CameraCapturer(String str, CameraVideoCapturer.CameraEventsHandler cameraEventsHandler, CameraEnumerator cameraEnumerator) {
        this.eventsHandler = cameraEventsHandler == null ? new CameraVideoCapturer.CameraEventsHandler() { // from class: org.wrtca.video.CameraCapturer.4
            @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
            public void onCameraClosed() {
            }

            @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
            public void onCameraDisconnected() {
            }

            @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
            public void onCameraError(int i2, String str2) {
            }

            @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
            public void onCameraFreezed(String str2) {
            }

            @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
            public void onCameraOpening(String str2) {
            }

            @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
            public VideoFrame onCaptureFrame(CameraSession cameraSession, VideoFrame videoFrame) {
                return null;
            }

            @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
            public void onFirstFrameAvailable() {
            }
        } : cameraEventsHandler;
        this.cameraEnumerator = cameraEnumerator;
        this.cameraName = str;
        this.uiThreadHandler = new Handler(Looper.getMainLooper());
        String[] deviceNames = cameraEnumerator.getDeviceNames();
        if (deviceNames.length == 0) {
            throw new RuntimeException("No cameras attached.");
        }
        if (Arrays.asList(deviceNames).contains(this.cameraName)) {
            return;
        }
        throw new IllegalArgumentException("Camera name " + this.cameraName + " does not match any known camera device.");
    }

    public static /* synthetic */ int access$1610(CameraCapturer cameraCapturer) {
        int i2 = cameraCapturer.openAttemptsRemaining;
        cameraCapturer.openAttemptsRemaining = i2 - 1;
        return i2;
    }

    private boolean cameraIsSameSide(String str, String str2) {
        return this.cameraEnumerator.getFacingIndex(str) == this.cameraEnumerator.getFacingIndex(str2);
    }

    private boolean checkCameraNameValid(int i2) {
        String[] deviceNames = this.cameraEnumerator.getDeviceNames();
        String strValueOf = String.valueOf(i2);
        h.a(TAG, "current cameraName is: " + this.cameraName + " switch cameraNameIndex is: " + i2);
        try {
            if (!Arrays.asList(deviceNames).contains(strValueOf)) {
                h.a(TAG, "checkCameraNameValid cameraNameIndex: " + i2 + " is not existed.");
                return false;
            }
            String str = deviceNames[i2];
            h.a(TAG, "checkCameraNameValid cameraNameIndex is : " + i2 + " name is: " + str);
            CameraManager cameraManager = (CameraManager) this.applicationContext.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA);
            if (!Camera2Enumerator.isSupported(c.e.b())) {
                if (this.cameraName.equals(str)) {
                    h.a(TAG, "Camera1 ----- camera is the same as current camera, do not to switch.");
                    return false;
                }
                this.cameraName = str;
                h.a(TAG, "Ready to switch camera name is: " + this.cameraName + " camera index is: " + i2);
                return true;
            }
            if (Camera2Enumerator.getSupportedSizes(cameraManager.getCameraCharacteristics(str)).isEmpty()) {
                h.a(TAG, "Camera2 ----- no supported output sizes camera: " + str + " is not supported.");
                return false;
            }
            if (this.cameraName.equals(str)) {
                h.a(TAG, "Camera2 ----- camera is the same as current camera, do not to switch.");
                return false;
            }
            this.cameraName = str;
            h.a(TAG, "Ready to switch camera name is: " + this.cameraName + " camera index is: " + i2);
            return true;
        } catch (CameraAccessException e2) {
            h.a(TAG, "getCameraCharacteristics(): " + e2.getMessage());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkIsOnCameraThread() {
        if (Thread.currentThread() == this.cameraThreadHandler.getLooper().getThread()) {
            return;
        }
        Logging.e(TAG, "Check is on camera thread failed.");
        throw new RuntimeException("Not on camera thread.");
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00b5, code lost:
    
        r9.cameraName = r4;
        c.h.a(org.wrtca.video.CameraCapturer.TAG, "Ready to switch camera name is: " + r9.cameraName + " camera index is: " + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void getValidCameraName(boolean r10, java.lang.String[] r11) {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.wrtca.video.CameraCapturer.getValidCameraName(boolean, java.lang.String[]):void");
    }

    private void reportCameraSwitchError(String str, CameraVideoCapturer.CameraSwitchHandler cameraSwitchHandler) {
        Logging.e(TAG, str);
        if (cameraSwitchHandler != null) {
            cameraSwitchHandler.onCameraSwitchError(str);
        }
    }

    private void reportUpdateMediaRecorderError(String str, CameraVideoCapturer.MediaRecorderHandler mediaRecorderHandler) {
        checkIsOnCameraThread();
        Logging.e(TAG, str);
        if (mediaRecorderHandler != null) {
            mediaRecorderHandler.onMediaRecorderError(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCameraIdInternal(int i2, CameraVideoCapturer.CameraSwitchHandler cameraSwitchHandler) {
        Logging.d(TAG, "setCamera internal cameraId:" + i2);
        if (!checkCameraNameValid(i2)) {
            if (cameraSwitchHandler != null) {
                cameraSwitchHandler.onCameraSwitchError("No need to switch camera.");
                return;
            }
            return;
        }
        synchronized (this.stateLock) {
            if (this.switchState != SwitchState.IDLE) {
                reportCameraSwitchError("Camera switch already in progress.", cameraSwitchHandler);
                return;
            }
            if (this.mediaRecorderState != MediaRecorderState.IDLE) {
                reportCameraSwitchError("switchCamera: media recording is active", cameraSwitchHandler);
                return;
            }
            boolean z2 = this.sessionOpening;
            if (!z2 && this.currentSession == null) {
                reportCameraSwitchError("switchCamera: camera is not running.", cameraSwitchHandler);
                return;
            }
            this.switchEventsHandler = cameraSwitchHandler;
            if (z2) {
                this.switchState = SwitchState.PENDING;
                return;
            }
            this.switchState = SwitchState.IN_PROGRESS;
            Logging.d(TAG, "switchCamera: Stopping session");
            this.cameraStatistics.release();
            this.cameraStatistics = null;
            final CameraSession cameraSession = this.currentSession;
            this.cameraThreadHandler.post(new Runnable() { // from class: org.wrtca.video.CameraCapturer.14
                @Override // java.lang.Runnable
                public void run() {
                    cameraSession.stop();
                }
            });
            this.currentSession = null;
            this.sessionOpening = true;
            this.openAttemptsRemaining = 1;
            createSessionInternal(0, null);
            Logging.d(TAG, "setCamera done");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchCameraInternal(boolean z2, CameraVideoCapturer.CameraSwitchHandler cameraSwitchHandler) {
        Logging.d(TAG, "switchCamera internal");
        String[] deviceNames = this.cameraEnumerator.getDeviceNames();
        if (deviceNames.length < 2) {
            if (cameraSwitchHandler != null) {
                cameraSwitchHandler.onCameraSwitchError("No camera to switch to.");
                return;
            }
            return;
        }
        synchronized (this.stateLock) {
            if (this.switchState != SwitchState.IDLE) {
                reportCameraSwitchError("Camera switch already in progress.", cameraSwitchHandler);
                return;
            }
            if (this.mediaRecorderState != MediaRecorderState.IDLE) {
                reportCameraSwitchError("switchCamera: media recording is active", cameraSwitchHandler);
                return;
            }
            boolean z3 = this.sessionOpening;
            if (!z3 && this.currentSession == null) {
                reportCameraSwitchError("switchCamera: camera is not running.", cameraSwitchHandler);
                return;
            }
            this.switchEventsHandler = cameraSwitchHandler;
            this.skipFlag = z2;
            if (z3) {
                this.switchState = SwitchState.PENDING;
                return;
            }
            this.switchState = SwitchState.IN_PROGRESS;
            Logging.d(TAG, "switchCamera: Stopping session");
            this.cameraStatistics.release();
            this.cameraStatistics = null;
            final CameraSession cameraSession = this.currentSession;
            this.cameraThreadHandler.post(new Runnable() { // from class: org.wrtca.video.CameraCapturer.13
                @Override // java.lang.Runnable
                public void run() {
                    cameraSession.stop();
                }
            });
            this.currentSession = null;
            getValidCameraName(z2, deviceNames);
            this.sessionOpening = true;
            this.openAttemptsRemaining = 1;
            createSessionInternal(0, null);
            Logging.d(TAG, "switchCamera done");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void turnFlashLightOffInternal() {
        h.a(TAG, "turnFlashLightOffInternal");
        synchronized (this.stateLock) {
            if ((this.sessionOpening || this.currentSession != null) && !this.mCameraParam.isFrontCamera) {
                h.a(TAG, "start to turn light off ");
                if (c.e.b().getPackageManager().hasSystemFeature("android.hardware.camera.flash")) {
                    this.currentSession.setFlashLight(false);
                } else {
                    h.a(TAG, " Flash service is not available.");
                }
            } else {
                h.a(TAG, "Failed to turn light off. session is closed or back camera is off");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void turnFlashLightOnInternal() {
        h.a(TAG, "turnFlashLightOnInternal");
        synchronized (this.stateLock) {
            if ((this.sessionOpening || this.currentSession != null) && !this.mCameraParam.isFrontCamera) {
                h.a(TAG, "start to turn light on ");
                if (c.e.b().getPackageManager().hasSystemFeature("android.hardware.camera.flash")) {
                    this.currentSession.setFlashLight(true);
                } else {
                    h.a(TAG, " Flash service is not available.");
                }
            } else {
                h.a(TAG, "Failed to turn light on. session is closed or back camera is off");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0057 A[Catch: all -> 0x0040, TryCatch #0 {all -> 0x0040, blocks: (B:9:0x0039, B:17:0x004a, B:18:0x004f, B:15:0x0044, B:20:0x0051, B:22:0x0057, B:23:0x005c, B:25:0x005e, B:27:0x0062, B:28:0x0067, B:30:0x0069, B:32:0x006d, B:33:0x0072, B:35:0x0074, B:37:0x0078, B:39:0x007d, B:40:0x00a3, B:38:0x007b), top: B:45:0x0039 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005e A[Catch: all -> 0x0040, TryCatch #0 {all -> 0x0040, blocks: (B:9:0x0039, B:17:0x004a, B:18:0x004f, B:15:0x0044, B:20:0x0051, B:22:0x0057, B:23:0x005c, B:25:0x005e, B:27:0x0062, B:28:0x0067, B:30:0x0069, B:32:0x006d, B:33:0x0072, B:35:0x0074, B:37:0x0078, B:39:0x007d, B:40:0x00a3, B:38:0x007b), top: B:45:0x0039 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateMediaRecorderInternal(android.media.MediaRecorder r7, org.wrtca.api.CameraVideoCapturer.MediaRecorderHandler r8) {
        /*
            r6 = this;
            r6.checkIsOnCameraThread()
            r0 = 0
            r1 = 1
            if (r7 == 0) goto L9
            r2 = r1
            goto La
        L9:
            r2 = r0
        La:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "updateMediaRecoderInternal internal. State: "
            r3.append(r4)
            org.wrtca.video.CameraCapturer$MediaRecorderState r4 = r6.mediaRecorderState
            r3.append(r4)
            java.lang.String r4 = ". Switch state: "
            r3.append(r4)
            org.wrtca.video.CameraCapturer$SwitchState r4 = r6.switchState
            r3.append(r4)
            java.lang.String r4 = ". Add MediaRecorder: "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "CameraCapturer"
            org.wrtca.log.Logging.d(r4, r3)
            java.lang.Object r3 = r6.stateLock
            monitor-enter(r3)
            if (r2 == 0) goto L42
            org.wrtca.video.CameraCapturer$MediaRecorderState r4 = r6.mediaRecorderState     // Catch: java.lang.Throwable -> L40
            org.wrtca.video.CameraCapturer$MediaRecorderState r5 = org.wrtca.video.CameraCapturer.MediaRecorderState.IDLE     // Catch: java.lang.Throwable -> L40
            if (r4 != r5) goto L4a
            goto L42
        L40:
            r7 = move-exception
            goto Lac
        L42:
            if (r2 != 0) goto L51
            org.wrtca.video.CameraCapturer$MediaRecorderState r4 = r6.mediaRecorderState     // Catch: java.lang.Throwable -> L40
            org.wrtca.video.CameraCapturer$MediaRecorderState r5 = org.wrtca.video.CameraCapturer.MediaRecorderState.ACTIVE     // Catch: java.lang.Throwable -> L40
            if (r4 == r5) goto L51
        L4a:
            java.lang.String r7 = "Incorrect state for MediaRecorder update."
            r6.reportUpdateMediaRecorderError(r7, r8)     // Catch: java.lang.Throwable -> L40
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L40
            return
        L51:
            org.wrtca.video.CameraCapturer$SwitchState r4 = r6.switchState     // Catch: java.lang.Throwable -> L40
            org.wrtca.video.CameraCapturer$SwitchState r5 = org.wrtca.video.CameraCapturer.SwitchState.IDLE     // Catch: java.lang.Throwable -> L40
            if (r4 == r5) goto L5e
            java.lang.String r7 = "MediaRecorder update while camera is switching."
            r6.reportUpdateMediaRecorderError(r7, r8)     // Catch: java.lang.Throwable -> L40
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L40
            return
        L5e:
            org.wrtca.video.CameraSession r4 = r6.currentSession     // Catch: java.lang.Throwable -> L40
            if (r4 != 0) goto L69
            java.lang.String r7 = "MediaRecorder update while camera is closed."
            r6.reportUpdateMediaRecorderError(r7, r8)     // Catch: java.lang.Throwable -> L40
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L40
            return
        L69:
            boolean r4 = r6.sessionOpening     // Catch: java.lang.Throwable -> L40
            if (r4 == 0) goto L74
            java.lang.String r7 = "MediaRecorder update while camera is still opening."
            r6.reportUpdateMediaRecorderError(r7, r8)     // Catch: java.lang.Throwable -> L40
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L40
            return
        L74:
            r6.mediaRecorderEventsHandler = r8     // Catch: java.lang.Throwable -> L40
            if (r2 == 0) goto L7b
            org.wrtca.video.CameraCapturer$MediaRecorderState r8 = org.wrtca.video.CameraCapturer.MediaRecorderState.IDLE_TO_ACTIVE     // Catch: java.lang.Throwable -> L40
            goto L7d
        L7b:
            org.wrtca.video.CameraCapturer$MediaRecorderState r8 = org.wrtca.video.CameraCapturer.MediaRecorderState.ACTIVE_TO_IDLE     // Catch: java.lang.Throwable -> L40
        L7d:
            r6.mediaRecorderState = r8     // Catch: java.lang.Throwable -> L40
            java.lang.String r8 = "CameraCapturer"
            java.lang.String r2 = "updateMediaRecoder: Stopping session"
            org.wrtca.log.Logging.d(r8, r2)     // Catch: java.lang.Throwable -> L40
            org.wrtca.api.CameraVideoCapturer$CameraStatistics r8 = r6.cameraStatistics     // Catch: java.lang.Throwable -> L40
            r8.release()     // Catch: java.lang.Throwable -> L40
            r8 = 0
            r6.cameraStatistics = r8     // Catch: java.lang.Throwable -> L40
            org.wrtca.video.CameraSession r2 = r6.currentSession     // Catch: java.lang.Throwable -> L40
            android.os.Handler r4 = r6.cameraThreadHandler     // Catch: java.lang.Throwable -> L40
            org.wrtca.video.CameraCapturer$15 r5 = new org.wrtca.video.CameraCapturer$15     // Catch: java.lang.Throwable -> L40
            r5.<init>()     // Catch: java.lang.Throwable -> L40
            r4.post(r5)     // Catch: java.lang.Throwable -> L40
            r6.currentSession = r8     // Catch: java.lang.Throwable -> L40
            r6.sessionOpening = r1     // Catch: java.lang.Throwable -> L40
            r6.openAttemptsRemaining = r1     // Catch: java.lang.Throwable -> L40
            r6.createSessionInternal(r0, r7)     // Catch: java.lang.Throwable -> L40
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L40
            java.lang.String r7 = "CameraCapturer"
            java.lang.String r8 = "updateMediaRecoderInternal done"
            org.wrtca.log.Logging.d(r7, r8)
            return
        Lac:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L40
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.wrtca.video.CameraCapturer.updateMediaRecorderInternal(android.media.MediaRecorder, org.wrtca.api.CameraVideoCapturer$MediaRecorderHandler):void");
    }

    @Override // org.wrtca.api.CameraVideoCapturer
    public void addMediaRecorderToCamera(final MediaRecorder mediaRecorder, final CameraVideoCapturer.MediaRecorderHandler mediaRecorderHandler) {
        Logging.d(TAG, "addMediaRecorderToCamera");
        this.cameraThreadHandler.post(new Runnable() { // from class: org.wrtca.video.CameraCapturer.11
            @Override // java.lang.Runnable
            public void run() {
                CameraCapturer.this.updateMediaRecorderInternal(mediaRecorder, mediaRecorderHandler);
            }
        });
    }

    @Override // org.wrtca.api.VideoCapturer
    public void changeCaptureFormat(int i2, int i3, int i4) {
        Logging.d(TAG, "changeCaptureFormat: " + i2 + "x" + i3 + "@" + i4);
        synchronized (this.stateLock) {
            stopCapture();
            startCapture(i2, i3, i4);
        }
    }

    public abstract void createCameraSession(CameraSession.CreateSessionCallback createSessionCallback, CameraSession.Events events, Context context, SurfaceTextureHelper surfaceTextureHelper, MediaRecorder mediaRecorder, String str, int i2, int i3, int i4, CameraParamObserver cameraParamObserver);

    public void createSessionInternal(int i2, final MediaRecorder mediaRecorder) {
        this.uiThreadHandler.postDelayed(this.openCameraTimeoutRunnable, i2 + 10000);
        this.cameraThreadHandler.postDelayed(new Runnable() { // from class: org.wrtca.video.CameraCapturer.5
            @Override // java.lang.Runnable
            public void run() {
                CameraCapturer cameraCapturer = CameraCapturer.this;
                if (cameraCapturer.mParamObserver == null) {
                    cameraCapturer.mParamObserver = MediaRecorderNative.cameraParamObserver;
                }
                cameraCapturer.createCameraSession(cameraCapturer.createSessionCallback, cameraCapturer.cameraSessionEventsHandler, cameraCapturer.applicationContext, cameraCapturer.surfaceHelper, mediaRecorder, cameraCapturer.cameraName, cameraCapturer.width, cameraCapturer.height, cameraCapturer.framerate, cameraCapturer.mParamObserver);
            }
        }, i2);
    }

    @Override // org.wrtca.api.VideoCapturer
    public void dispose() {
        Logging.d(TAG, "dispose");
        stopCapture();
    }

    public String getCameraName() {
        String str;
        synchronized (this.stateLock) {
            str = this.cameraName;
        }
        return str;
    }

    @Override // org.wrtca.api.VideoCapturer
    public void initialize(SurfaceTextureHelper surfaceTextureHelper, Context context, VideoCapturer.CapturerObserver capturerObserver) {
        this.applicationContext = context;
        this.capturerObserver = capturerObserver;
        this.surfaceHelper = surfaceTextureHelper;
        this.cameraThreadHandler = surfaceTextureHelper == null ? null : surfaceTextureHelper.getHandler();
    }

    @Override // org.wrtca.api.VideoCapturer
    public boolean isScreencast() {
        return false;
    }

    public void printStackTrace() {
        Handler handler = this.cameraThreadHandler;
        Thread thread = handler != null ? handler.getLooper().getThread() : null;
        if (thread != null) {
            StackTraceElement[] stackTrace = thread.getStackTrace();
            if (stackTrace.length > 0) {
                Logging.d(TAG, "CameraCapturer stack trace:");
                for (StackTraceElement stackTraceElement : stackTrace) {
                    Logging.d(TAG, stackTraceElement.toString());
                }
            }
        }
    }

    @Override // org.wrtca.api.CameraVideoCapturer
    public void removeMediaRecorderFromCamera(final CameraVideoCapturer.MediaRecorderHandler mediaRecorderHandler) {
        Logging.d(TAG, "removeMediaRecorderFromCamera");
        this.cameraThreadHandler.post(new Runnable() { // from class: org.wrtca.video.CameraCapturer.12
            @Override // java.lang.Runnable
            public void run() {
                CameraCapturer.this.updateMediaRecorderInternal(null, mediaRecorderHandler);
            }
        });
    }

    public CameraSession.CameraParam requestCameraParam() {
        return this.mCameraParam;
    }

    @Override // org.wrtca.api.CameraVideoCapturer
    public void setCameraId(final int i2, final CameraVideoCapturer.CameraSwitchHandler cameraSwitchHandler) {
        Logging.d(TAG, PLVLinkMicELog.LinkMicTraceLogEvent.SWITCH_CAMERA);
        this.cameraThreadHandler.post(new Runnable() { // from class: org.wrtca.video.CameraCapturer.8
            @Override // java.lang.Runnable
            public void run() {
                CameraCapturer.this.setCameraIdInternal(i2, cameraSwitchHandler);
            }
        });
    }

    @Override // org.wrtca.api.VideoCapturer
    public void startCapture(int i2, int i3, int i4) {
        Logging.d(TAG, "startCapture: " + i2 + "x" + i3 + "@" + i4);
        if (this.applicationContext == null) {
            throw new RuntimeException("CameraCapturer must be initialized before calling startCapture.");
        }
        synchronized (this.stateLock) {
            if (!this.sessionOpening && this.currentSession == null) {
                this.width = i2;
                this.height = i3;
                this.framerate = i4;
                this.sessionOpening = true;
                this.openAttemptsRemaining = 3;
                createSessionInternal(0, null);
                return;
            }
            h.a(TAG, "Session already open");
            Logging.w(TAG, "Session already open");
        }
    }

    @Override // org.wrtca.api.VideoCapturer
    public void stopCapture() {
        Logging.d(TAG, "Stop capture");
        synchronized (this.stateLock) {
            while (this.sessionOpening) {
                Logging.d(TAG, "Stop capture: Waiting for session to open");
                try {
                    this.stateLock.wait();
                } catch (InterruptedException unused) {
                    Logging.w(TAG, "Stop capture interrupted while waiting for the session to open.");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            if (this.currentSession != null) {
                Logging.d(TAG, "Stop capture: Nulling session");
                this.cameraStatistics.release();
                this.cameraStatistics = null;
                final CameraSession cameraSession = this.currentSession;
                this.cameraThreadHandler.post(new Runnable() { // from class: org.wrtca.video.CameraCapturer.6
                    @Override // java.lang.Runnable
                    public void run() {
                        cameraSession.stop();
                    }
                });
                this.currentSession = null;
                this.mCameraParam = null;
                this.capturerObserver.onCapturerStopped();
            } else {
                Logging.d(TAG, "Stop capture: No session open");
            }
        }
        Logging.d(TAG, "Stop capture done");
    }

    @Override // org.wrtca.api.CameraVideoCapturer
    public void switchCamera(final boolean z2, final CameraVideoCapturer.CameraSwitchHandler cameraSwitchHandler) {
        Logging.d(TAG, PLVLinkMicELog.LinkMicTraceLogEvent.SWITCH_CAMERA);
        this.cameraThreadHandler.post(new Runnable() { // from class: org.wrtca.video.CameraCapturer.7
            @Override // java.lang.Runnable
            public void run() {
                CameraCapturer.this.switchCameraInternal(z2, cameraSwitchHandler);
            }
        });
    }

    @Override // org.wrtca.api.CameraVideoCapturer
    public void turnFlashLightOff() {
        Logging.d(TAG, "turnFlashLightOff");
        this.cameraThreadHandler.post(new Runnable() { // from class: org.wrtca.video.CameraCapturer.10
            @Override // java.lang.Runnable
            public void run() {
                CameraCapturer.this.turnFlashLightOffInternal();
            }
        });
    }

    @Override // org.wrtca.api.CameraVideoCapturer
    public void turnFlashLightOn() {
        Logging.d(TAG, "turnFlashLightOn");
        this.cameraThreadHandler.post(new Runnable() { // from class: org.wrtca.video.CameraCapturer.9
            @Override // java.lang.Runnable
            public void run() {
                CameraCapturer.this.turnFlashLightOnInternal();
            }
        });
    }
}
