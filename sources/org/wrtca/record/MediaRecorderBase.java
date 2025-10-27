package org.wrtca.record;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import core.interfaces.RecordListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import k.a;
import kotlinx.coroutines.DebugKt;
import org.wrtca.record.model.BaseMediaBitrateConfig;
import org.wrtca.record.model.MediaObject;
import org.wrtca.util.DeviceUtils;
import org.wrtca.util.FFMpegUtils;
import org.wrtca.util.FileUtils;
import org.wrtca.util.StringUtils;
import org.wrtca.video.RtcFFmpegBridge;

/* loaded from: classes9.dex */
public abstract class MediaRecorderBase implements SurfaceHolder.Callback, Camera.PreviewCallback, MediaRecorder {
    public static final int AUDIO_RECORD_ERROR_CREATE_FAILED = 3;
    public static final int AUDIO_RECORD_ERROR_GET_MIN_BUFFER_SIZE_NOT_SUPPORT = 2;
    public static final int AUDIO_RECORD_ERROR_SAMPLERATE_NOT_SUPPORT = 1;
    public static final int AUDIO_RECORD_ERROR_UNKNOWN = 0;
    public static int CAPTURE_THUMBNAILS_TIME = 1;
    public static int MAX_FRAME_RATE = 20;
    public static final int MEDIA_ERROR_CAMERA_AUTO_FOCUS = 103;
    public static final int MEDIA_ERROR_CAMERA_PREVIEW = 102;
    public static final int MEDIA_ERROR_CAMERA_SET_PREVIEW_DISPLAY = 101;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MESSAGE_ENCODE_COMPLETE = 2;
    public static final int MESSAGE_ENCODE_ERROR = 3;
    public static final int MESSAGE_ENCODE_PROGRESS = 1;
    public static final int MESSAGE_ENCODE_START = 0;
    public static int MIN_FRAME_RATE = 8;
    public static boolean NEED_FULL_SCREEN = false;
    public static int SMALL_VIDEO_HEIGHT = 480;
    public static int SMALL_VIDEO_WIDTH = 360;
    public static final int VIDEO_BITRATE_HIGH = 2048;
    public static final int VIDEO_BITRATE_MEDIUM = 1536;
    public static final int VIDEO_BITRATE_NORMAL = 1024;
    public static int mSupportedPreviewWidth;
    public static int mVideoBitrate;
    public Camera camera;
    public BaseMediaBitrateConfig compressConfig;
    public AudioRecorder mAudioRecorder;
    public MediaObject mMediaObject;
    public OnEncodeListener mOnEncodeListener;
    public OnErrorListener mOnErrorListener;
    public OnPreparedListener mOnPreparedListener;
    public boolean mPrepared;
    public RecordListener mRecordListener;
    public volatile boolean mRecording;
    public boolean mStartPreview;
    public List<Camera.Size> mSupportedPreviewSizes;
    public boolean mSurfaceCreated;
    public SurfaceHolder mSurfaceHolder;
    public Camera.Parameters mParameters = null;
    public int mFrameRate = MAX_FRAME_RATE;
    public int mCameraId = 0;
    public volatile long mPreviewFrameCallCount = 0;
    private String mFrameRateCmd = "";

    public interface OnEncodeListener {
        void onEncodeComplete();

        void onEncodeError();

        void onEncodeProgress(int i2);

        void onEncodeStart();
    }

    public interface OnErrorListener {
        void onAudioError(int i2, String str);

        void onVideoError(int i2, int i3);
    }

    public interface OnPreparedListener {
        void onPrepared();
    }

    private void checkFullWidth(int i2, int i3) {
        if (NEED_FULL_SCREEN) {
            SMALL_VIDEO_WIDTH = i2;
        } else {
            SMALL_VIDEO_WIDTH = i3;
        }
    }

    private String getAutoFocusMode() {
        Camera.Parameters parameters = this.mParameters;
        if (parameters == null) {
            return null;
        }
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        String str = Build.MODEL;
        if ((str.startsWith("GT-I950") || str.endsWith("SCH-I959") || str.endsWith("MEIZU MX3")) && isSupported(supportedFocusModes, "continuous-picture")) {
            return "continuous-picture";
        }
        if (isSupported(supportedFocusModes, "continuous-video")) {
            return "continuous-video";
        }
        if (isSupported(supportedFocusModes, "auto")) {
            return "auto";
        }
        return null;
    }

    @SuppressLint({"NewApi"})
    @TargetApi(9)
    public static boolean isSupportFrontCamera() {
        return DeviceUtils.hasGingerbread() && 2 == Camera.getNumberOfCameras();
    }

    private boolean isSupported(List<String> list, String str) {
        return list != null && list.contains(str);
    }

    private boolean setFlashMode(String str) {
        if (this.mParameters == null || this.camera == null) {
            return false;
        }
        try {
            if (!"torch".equals(str) && !DebugKt.DEBUG_PROPERTY_VALUE_OFF.equals(str)) {
                return true;
            }
            this.mParameters.setFlashMode(str);
            this.camera.setParameters(this.mParameters);
            return true;
        } catch (Exception e2) {
            Log.e("jianxi", "setFlashMode", e2);
            return false;
        }
    }

    private void stopAllRecord() throws IOException {
        this.mRecording = false;
        MediaObject mediaObject = this.mMediaObject;
        if (mediaObject == null || mediaObject.getMedaParts() == null) {
            return;
        }
        Iterator<MediaObject.MediaPart> it = this.mMediaObject.getMedaParts().iterator();
        while (it.hasNext()) {
            MediaObject.MediaPart next = it.next();
            if (next != null && next.recording) {
                next.recording = false;
                long jCurrentTimeMillis = System.currentTimeMillis();
                next.endTime = jCurrentTimeMillis;
                int i2 = (int) (jCurrentTimeMillis - next.startTime);
                next.duration = i2;
                next.cutStartTime = 0;
                next.cutEndTime = i2;
                if (new File(next.mediaPath).length() < 1) {
                    this.mMediaObject.removePart(next, true);
                }
            }
        }
    }

    public boolean autoFocus(Camera.AutoFocusCallback autoFocusCallback) {
        Camera camera = this.camera;
        if (camera != null) {
            try {
                camera.cancelAutoFocus();
                if (this.mParameters != null) {
                    String autoFocusMode = getAutoFocusMode();
                    if (StringUtils.isNotEmpty(autoFocusMode)) {
                        this.mParameters.setFocusMode(autoFocusMode);
                        this.camera.setParameters(this.mParameters);
                    }
                }
                this.camera.autoFocus(autoFocusCallback);
                return true;
            } catch (Exception e2) {
                OnErrorListener onErrorListener = this.mOnErrorListener;
                if (onErrorListener != null) {
                    onErrorListener.onVideoError(103, 0);
                }
                Log.e("jianxi", "autoFocus", e2);
            }
        }
        return false;
    }

    public Boolean doCompress(boolean z2) {
        String str;
        BaseMediaBitrateConfig baseMediaBitrateConfig = this.compressConfig;
        if (baseMediaBitrateConfig == null) {
            boolean zCaptureThumbnails = FFMpegUtils.captureThumbnails(this.mMediaObject.getOutputTempVideoPath(), this.mMediaObject.getOutputVideoThumbPath(), String.valueOf(CAPTURE_THUMBNAILS_TIME));
            FileUtils.deleteCacheFile2TS(this.mMediaObject.getOutputDirectory());
            return Boolean.valueOf(zCaptureThumbnails && z2);
        }
        String str2 = baseMediaBitrateConfig.getMode() == 2 ? "" : " -vbr 4 ";
        String scaleWH = getScaleWH();
        if (TextUtils.isEmpty(scaleWH)) {
            str = "";
        } else {
            str = "-s " + scaleWH;
        }
        String.format("ffmpeg -threads 16 -i %s -c:v libx264 %s %s %s -c:a libfdk_aac %s %s %s %s", this.mMediaObject.getOutputTempVideoPath(), getBitrateModeCommand(this.compressConfig, "", false), getBitrateCrfSize(this.compressConfig, "-crf 28", false), getBitrateVelocity(this.compressConfig, "-preset:v ultrafast", false), str2, getFrameRateCmd(), str, this.mMediaObject.getOutputTempTranscodingVideoPath());
        FFMpegUtils.captureThumbnails(this.mMediaObject.getOutputTempTranscodingVideoPath(), this.mMediaObject.getOutputVideoThumbPath(), String.valueOf(CAPTURE_THUMBNAILS_TIME));
        FileUtils.deleteCacheFile(this.mMediaObject.getOutputDirectory());
        return Boolean.FALSE;
    }

    public String getBitrateCrfSize(BaseMediaBitrateConfig baseMediaBitrateConfig, String str, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        return (baseMediaBitrateConfig == null || baseMediaBitrateConfig.getMode() != 3 || baseMediaBitrateConfig.getCrfSize() <= 0) ? str : z2 ? String.format("-crf \"%d\" ", Integer.valueOf(baseMediaBitrateConfig.getCrfSize())) : String.format("-crf %d ", Integer.valueOf(baseMediaBitrateConfig.getCrfSize()));
    }

    public String getBitrateModeCommand(BaseMediaBitrateConfig baseMediaBitrateConfig, String str, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        if (baseMediaBitrateConfig != null) {
            if (baseMediaBitrateConfig.getMode() == 1) {
                return z2 ? String.format(" -x264opts \"bitrate=%d:vbv-maxrate=%d\" ", Integer.valueOf(baseMediaBitrateConfig.getBitrate()), Integer.valueOf(baseMediaBitrateConfig.getMaxBitrate())) : String.format(" -x264opts bitrate=%d:vbv-maxrate=%d ", Integer.valueOf(baseMediaBitrateConfig.getBitrate()), Integer.valueOf(baseMediaBitrateConfig.getMaxBitrate()));
            }
            if (baseMediaBitrateConfig.getMode() == 2) {
                return z2 ? String.format(" -x264opts \"bitrate=%d:vbv-bufsize=%d:nal_hrd=cbr\" ", Integer.valueOf(baseMediaBitrateConfig.getBitrate()), Integer.valueOf(baseMediaBitrateConfig.getBufSize())) : String.format(" -x264opts bitrate=%d:vbv-bufsize=%d:nal_hrd=cbr ", Integer.valueOf(baseMediaBitrateConfig.getBitrate()), Integer.valueOf(baseMediaBitrateConfig.getBufSize()));
            }
        }
        return str;
    }

    public String getBitrateVelocity(BaseMediaBitrateConfig baseMediaBitrateConfig, String str, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        return (baseMediaBitrateConfig == null || TextUtils.isEmpty(baseMediaBitrateConfig.getVelocity())) ? str : z2 ? String.format("-preset \"%s\" ", baseMediaBitrateConfig.getVelocity()) : String.format("-preset %s ", baseMediaBitrateConfig.getVelocity());
    }

    public String getFrameRateCmd() {
        return this.mFrameRateCmd;
    }

    public boolean getRecordState() {
        return this.mRecording;
    }

    public String getScaleWH() {
        return "";
    }

    public boolean isFrontCamera() {
        return this.mCameraId == 1;
    }

    @SuppressLint({"NewApi"})
    @TargetApi(14)
    public boolean manualFocus(Camera.AutoFocusCallback autoFocusCallback, List<Camera.Area> list) {
        if (this.camera != null && list != null && this.mParameters != null && DeviceUtils.hasICS()) {
            try {
                this.camera.cancelAutoFocus();
                if (this.mParameters.getMaxNumFocusAreas() > 0) {
                    this.mParameters.setFocusAreas(list);
                }
                if (this.mParameters.getMaxNumMeteringAreas() > 0) {
                    this.mParameters.setMeteringAreas(list);
                }
                this.mParameters.setFocusMode("macro");
                this.camera.setParameters(this.mParameters);
                this.camera.autoFocus(autoFocusCallback);
                return true;
            } catch (Exception e2) {
                OnErrorListener onErrorListener = this.mOnErrorListener;
                if (onErrorListener != null) {
                    onErrorListener.onVideoError(103, 0);
                }
                Log.e("jianxi", "autoFocus", e2);
            }
        }
        return false;
    }

    @Override // org.wrtca.record.MediaRecorder
    public void onAudioError(int i2, String str) {
        OnErrorListener onErrorListener = this.mOnErrorListener;
        if (onErrorListener != null) {
            onErrorListener.onAudioError(i2, str);
        }
    }

    @Override // android.hardware.Camera.PreviewCallback
    public void onPreviewFrame(byte[] bArr, Camera camera) {
        camera.addCallbackBuffer(bArr);
    }

    public void onStartPreviewSuccess() {
    }

    public void prepare() throws IOException {
        this.mPrepared = true;
        if (this.mSurfaceCreated) {
            startPreview();
        }
    }

    public void prepareCameraParaments() {
        boolean z2;
        Camera.Parameters parameters = this.mParameters;
        if (parameters == null) {
            return;
        }
        List<Integer> supportedPreviewFrameRates = parameters.getSupportedPreviewFrameRates();
        boolean z3 = false;
        if (supportedPreviewFrameRates != null) {
            if (supportedPreviewFrameRates.contains(Integer.valueOf(MAX_FRAME_RATE))) {
                this.mFrameRate = MAX_FRAME_RATE;
            } else {
                Collections.sort(supportedPreviewFrameRates);
                int size = supportedPreviewFrameRates.size() - 1;
                while (true) {
                    if (size < 0) {
                        z2 = false;
                        break;
                    } else {
                        if (supportedPreviewFrameRates.get(size).intValue() <= MAX_FRAME_RATE) {
                            this.mFrameRate = supportedPreviewFrameRates.get(size).intValue();
                            z2 = true;
                            break;
                        }
                        size--;
                    }
                }
                if (!z2) {
                    this.mFrameRate = supportedPreviewFrameRates.get(0).intValue();
                }
            }
        }
        this.mParameters.setPreviewFrameRate(this.mFrameRate);
        int size2 = this.mSupportedPreviewSizes.size() - 1;
        while (true) {
            if (size2 < 0) {
                break;
            }
            Camera.Size size3 = this.mSupportedPreviewSizes.get(size2);
            if (size3.height == SMALL_VIDEO_HEIGHT) {
                int i2 = size3.width;
                mSupportedPreviewWidth = i2;
                checkFullWidth(i2, SMALL_VIDEO_WIDTH);
                z3 = true;
                break;
            }
            size2--;
        }
        if (!z3) {
            Log.e(getClass().getSimpleName(), "传入高度不支持或未找到对应宽度,请按照要求重新设置，否则会出现一些严重问题");
            mSupportedPreviewWidth = 640;
            checkFullWidth(640, 360);
            SMALL_VIDEO_HEIGHT = 480;
        }
        this.mParameters.setPreviewSize(mSupportedPreviewWidth, SMALL_VIDEO_HEIGHT);
        this.mParameters.setPreviewFormat(IjkMediaPlayer.SDL_FCC_YV12);
        String autoFocusMode = getAutoFocusMode();
        if (StringUtils.isNotEmpty(autoFocusMode)) {
            this.mParameters.setFocusMode(autoFocusMode);
        }
        if (isSupported(this.mParameters.getSupportedWhiteBalance(), "auto")) {
            this.mParameters.setWhiteBalance("auto");
        }
        if (a.f27523u.equals(this.mParameters.get("video-stabilization-supported"))) {
            this.mParameters.set("video-stabilization", a.f27523u);
        }
        if (DeviceUtils.isDevice("GT-N7100", "GT-I9308", "GT-I9300")) {
            return;
        }
        this.mParameters.set("cam_mode", 1);
        this.mParameters.set("cam-mode", 1);
    }

    @Override // org.wrtca.record.MediaRecorder
    public void receiveAudioData(byte[] bArr, int i2) {
    }

    public void release() throws IOException {
        RtcFFmpegBridge.nativeRelease();
        stopAllRecord();
        stopPreview();
        AudioRecorder audioRecorder = this.mAudioRecorder;
        if (audioRecorder != null) {
            audioRecorder.interrupt();
            this.mAudioRecorder = null;
        }
        this.mSurfaceHolder = null;
        this.mPrepared = false;
        this.mSurfaceCreated = false;
    }

    public void setMediaObject(MediaObject mediaObject) {
        this.mMediaObject = mediaObject;
    }

    public void setOnEncodeListener(OnEncodeListener onEncodeListener) {
        this.mOnEncodeListener = onEncodeListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public MediaObject setOutputDirectory(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mMediaObject = new MediaObject("", mVideoBitrate);
        } else {
            File file = new File(str);
            if (!file.exists() && file.isDirectory()) {
                file.mkdirs();
            }
            if (file.exists()) {
                this.mMediaObject = new MediaObject(str, mVideoBitrate);
            }
        }
        return this.mMediaObject;
    }

    public void setPreviewCallback() {
        Camera.Size previewSize = this.mParameters.getPreviewSize();
        if (previewSize == null) {
            this.camera.setPreviewCallback(this);
            return;
        }
        int i2 = ((previewSize.width * previewSize.height) * 3) / 2;
        try {
            this.camera.addCallbackBuffer(new byte[i2]);
            this.camera.addCallbackBuffer(new byte[i2]);
            this.camera.addCallbackBuffer(new byte[i2]);
            this.camera.setPreviewCallbackWithBuffer(this);
        } catch (OutOfMemoryError e2) {
            Log.e("jianxi", "startPreview...setPreviewCallback...", e2);
        }
        Log.e("jianxi", "startPreview...setPreviewCallbackWithBuffer...width:" + previewSize.width + " height:" + previewSize.height);
    }

    public void setRecordListener(RecordListener recordListener) {
        this.mRecordListener = recordListener;
    }

    public void setRecordState(boolean z2) {
        this.mRecording = z2;
    }

    public void setStopDate() {
        MediaObject.MediaPart currentPart;
        MediaObject mediaObject = this.mMediaObject;
        if (mediaObject == null || (currentPart = mediaObject.getCurrentPart()) == null || !currentPart.recording) {
            return;
        }
        currentPart.recording = false;
        long jCurrentTimeMillis = System.currentTimeMillis();
        currentPart.endTime = jCurrentTimeMillis;
        int i2 = (int) (jCurrentTimeMillis - currentPart.startTime);
        currentPart.duration = i2;
        currentPart.cutStartTime = 0;
        currentPart.cutEndTime = i2;
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        if (surfaceHolder != null) {
            surfaceHolder.addCallback(this);
            if (DeviceUtils.hasHoneycomb()) {
                return;
            }
            surfaceHolder.setType(3);
        }
    }

    public void setTranscodingFrameRate(int i2) {
        this.mFrameRateCmd = String.format(" -r %d", Integer.valueOf(i2));
    }

    public void setVideoBitRate(int i2) {
        if (i2 > 0) {
            mVideoBitrate = i2;
        }
    }

    public void startPreview() throws IOException {
        if (this.mStartPreview || this.mSurfaceHolder == null || !this.mPrepared) {
            return;
        }
        this.mStartPreview = true;
        try {
            int i2 = this.mCameraId;
            if (i2 == 0) {
                this.camera = Camera.open();
            } else {
                this.camera = Camera.open(i2);
            }
            this.camera.setDisplayOrientation(90);
            try {
                this.camera.setPreviewDisplay(this.mSurfaceHolder);
            } catch (IOException e2) {
                OnErrorListener onErrorListener = this.mOnErrorListener;
                if (onErrorListener != null) {
                    onErrorListener.onVideoError(101, 0);
                }
                Log.e("jianxi", "setPreviewDisplay fail " + e2.getMessage());
            }
            Camera.Parameters parameters = this.camera.getParameters();
            this.mParameters = parameters;
            this.mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
            prepareCameraParaments();
            this.camera.setParameters(this.mParameters);
            setPreviewCallback();
            this.camera.startPreview();
            onStartPreviewSuccess();
            OnPreparedListener onPreparedListener = this.mOnPreparedListener;
            if (onPreparedListener != null) {
                onPreparedListener.onPrepared();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            OnErrorListener onErrorListener2 = this.mOnErrorListener;
            if (onErrorListener2 != null) {
                onErrorListener2.onVideoError(102, 0);
            }
            Log.e("jianxi", "startPreview fail :" + e3.getMessage());
        }
    }

    public void stopPreview() {
        Camera camera = this.camera;
        if (camera != null) {
            try {
                camera.stopPreview();
                this.camera.setPreviewCallback(null);
                this.camera.release();
            } catch (Exception unused) {
                Log.e("jianxi", "stopPreview...");
            }
            this.camera = null;
        }
        this.mStartPreview = false;
    }

    @Override // org.wrtca.record.MediaRecorder
    public void stopRecord() {
        this.mRecording = false;
        setStopDate();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
        this.mSurfaceHolder = surfaceHolder;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) throws IOException {
        this.mSurfaceHolder = surfaceHolder;
        this.mSurfaceCreated = true;
        if (!this.mPrepared || this.mStartPreview) {
            return;
        }
        startPreview();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = null;
        this.mSurfaceCreated = false;
    }

    public void switchCamera(int i2) throws IOException {
        if (i2 == 0 || i2 == 1) {
            this.mCameraId = i2;
            stopPreview();
            startPreview();
        }
    }

    public void testPreviewFrameCallCount() {
        new CountDownTimer(60000L, 1000L) { // from class: org.wrtca.record.MediaRecorderBase.1
            @Override // android.os.CountDownTimer
            public void onFinish() {
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                Log.e("[Vitamio Recorder]", "testFrameRate..." + MediaRecorderBase.this.mPreviewFrameCallCount);
                MediaRecorderBase.this.mPreviewFrameCallCount = 0L;
            }
        }.start();
    }

    public boolean toggleFlashMode() {
        Camera.Parameters parameters = this.mParameters;
        if (parameters == null) {
            return false;
        }
        try {
            String flashMode = parameters.getFlashMode();
            if (!TextUtils.isEmpty(flashMode) && !DebugKt.DEBUG_PROPERTY_VALUE_OFF.equals(flashMode)) {
                setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
                return true;
            }
            setFlashMode("torch");
            return true;
        } catch (Exception e2) {
            Log.e("jianxi", "toggleFlashMode", e2);
            return false;
        }
    }

    public void switchCamera() throws IOException {
        if (this.mCameraId == 0) {
            switchCamera(1);
        } else {
            switchCamera(0);
        }
    }
}
