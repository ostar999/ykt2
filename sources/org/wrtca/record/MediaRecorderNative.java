package org.wrtca.record;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import c.h;
import core.interfaces.AudioResample;
import core.interfaces.RecordListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.wrtca.record.MediaRecorderBase;
import org.wrtca.record.model.CameraParamObserver;
import org.wrtca.record.model.MediaObject;
import org.wrtca.util.FFMpegUtils;
import org.wrtca.video.RtcFFmpegBridge;

/* loaded from: classes9.dex */
public class MediaRecorderNative extends MediaRecorderBase implements MediaRecorder.OnErrorListener, RtcFFmpegBridge.FFmpegStateListener {
    private static final String TAG = "MediaRecorderNative";
    private static final String VIDEO_SUFFIX = ".mp4";
    public static CameraParamObserver cameraParamObserver = new CameraParamObserver() { // from class: org.wrtca.record.MediaRecorderNative.1
        private int mCameraHeight;
        private int mCameraWidth;
        private int mDeviceOrientation;
        private int mFps;
        private boolean mIsFrontCamera;

        @Override // org.wrtca.record.model.CameraParamObserver
        public int getDirection() {
            return this.mDeviceOrientation;
        }

        @Override // org.wrtca.record.model.CameraParamObserver
        public int getFps() {
            return this.mFps;
        }

        @Override // org.wrtca.record.model.CameraParamObserver
        public int getHeight() {
            return this.mCameraHeight;
        }

        @Override // org.wrtca.record.model.CameraParamObserver
        public int getWidth() {
            return this.mCameraWidth;
        }

        @Override // org.wrtca.record.model.CameraParamObserver
        public boolean isFrontCamera() {
            return this.mIsFrontCamera;
        }

        @Override // org.wrtca.record.model.CameraParamObserver
        public void reportCameraClosed() {
            int unused = MediaRecorderNative.out_width = 0;
            int unused2 = MediaRecorderNative.out_height = 0;
            int unused3 = MediaRecorderNative.out_fps = 0;
            int unused4 = MediaRecorderNative.device_orientation = 0;
            this.mIsFrontCamera = true;
            h.a(MediaRecorderNative.TAG, "MediaRecorderNative report cameraClosed set all value to 0");
        }

        @Override // org.wrtca.record.model.CameraParamObserver
        public void reportCameraOpenParam(int i2, int i3, int i4, int i5) {
            this.mCameraWidth = i2;
            this.mCameraHeight = i3;
            this.mFps = i5;
            this.mDeviceOrientation = i4;
            int unused = MediaRecorderNative.out_width = i2;
            int unused2 = MediaRecorderNative.out_height = i3;
            int unused3 = MediaRecorderNative.out_fps = i5;
            int unused4 = MediaRecorderNative.device_orientation = i4;
            h.a(MediaRecorderNative.TAG, "MediaRecorderNative report cameraOpen");
        }
    };
    private static int device_orientation;
    private static int out_fps;
    private static int out_height;
    private static int out_width;

    public void activityStop() {
        RtcFFmpegBridge.unregisterFFmpegStateListener(this);
    }

    @Override // org.wrtca.video.RtcFFmpegBridge.FFmpegStateListener
    public void allRecordEnd() {
        final boolean zCaptureThumbnails = FFMpegUtils.captureThumbnails(this.mMediaObject.getOutputTempTranscodingVideoPath(), this.mMediaObject.getOutputVideoThumbPath(), String.valueOf(MediaRecorderBase.CAPTURE_THUMBNAILS_TIME));
        if (this.mOnEncodeListener != null) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: org.wrtca.record.MediaRecorderNative.2
                @Override // java.lang.Runnable
                public void run() {
                    if (zCaptureThumbnails) {
                        MediaRecorderNative.this.mOnEncodeListener.onEncodeComplete();
                    } else {
                        MediaRecorderNative.this.mOnEncodeListener.onEncodeError();
                    }
                }
            }, 0L);
        }
    }

    @Override // android.media.MediaRecorder.OnErrorListener
    public void onError(android.media.MediaRecorder mediaRecorder, int i2, int i3) {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.reset();
            } catch (IllegalStateException e2) {
                Log.w("jianxi", "stopRecord", e2);
            } catch (Exception e3) {
                Log.w("jianxi", "stopRecord", e3);
            }
        }
        MediaRecorderBase.OnErrorListener onErrorListener = this.mOnErrorListener;
        if (onErrorListener != null) {
            onErrorListener.onVideoError(i2, i3);
        }
    }

    @Override // org.wrtca.record.MediaRecorderBase, android.hardware.Camera.PreviewCallback
    public void onPreviewFrame(byte[] bArr, Camera camera) {
        if (this.mRecording) {
            RtcFFmpegBridge.nativeEncodeFrame2H264(bArr);
            this.mPreviewFrameCallCount++;
        }
        super.onPreviewFrame(bArr, camera);
    }

    @Override // org.wrtca.record.MediaRecorderBase
    public void onStartPreviewSuccess() {
    }

    @Override // org.wrtca.record.MediaRecorderBase, org.wrtca.record.MediaRecorder
    public void receiveAudioData(byte[] bArr, int i2) {
        if (!this.mRecording || i2 <= 0) {
            return;
        }
        RtcFFmpegBridge.nativeEncodeFrame2AAC(bArr);
    }

    @Override // org.wrtca.record.MediaRecorderBase
    public void setRecordListener(RecordListener recordListener) {
        super.setRecordListener(recordListener);
        RtcFFmpegBridge.registerRTCRecordListener(recordListener);
    }

    @Override // org.wrtca.record.MediaRecorder
    public void startAudioResample(AudioResample audioResample, FileOutputStream fileOutputStream) {
        if (audioResample != null) {
            RtcFFmpegBridge.nativeSwitchAudioResample(true);
            RtcFFmpegBridge.setPcmOutputStream(fileOutputStream);
            RtcFFmpegBridge.registerRTCAudioResampleListener(audioResample);
        }
    }

    @Override // org.wrtca.record.MediaRecorder
    public MediaObject.MediaPart startRecord(int i2, String str, long j2) {
        int i3;
        int i4;
        String strSubstring = str;
        try {
            h.a(TAG, "mMediaObject: " + this.mMediaObject + "key: " + strSubstring);
            if (this.mMediaObject != null && (i3 = out_height) != 0 && (i4 = out_width) != 0 && out_fps != 0) {
                int i5 = i4 * i3 > 307200 ? 1000000 : 500000;
                RtcFFmpegBridge.period = j2;
                File file = new File(strSubstring);
                String path = file.getParentFile().getPath();
                String outputDirectory = this.mMediaObject.getOutputDirectory();
                if (i2 == 0) {
                    if (strSubstring.endsWith(".mp4")) {
                        strSubstring = strSubstring.substring(strSubstring.lastIndexOf("/") + 1, strSubstring.indexOf(".mp4"));
                    }
                    this.mMediaObject.setKey(strSubstring);
                    if (!TextUtils.isEmpty(outputDirectory) || TextUtils.isEmpty(strSubstring)) {
                        if (!TextUtils.isEmpty(outputDirectory) && !path.equals(outputDirectory)) {
                            File file2 = new File(path);
                            if (file2.exists() || file2.mkdirs()) {
                                this.mMediaObject.setOutputVideoPath(file2.getPath());
                                this.mMediaObject.setOutputDirectory(file2.getPath());
                            }
                        }
                    } else if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
                        this.mMediaObject.setOutputVideoPath(file.getParentFile().getPath());
                        this.mMediaObject.setOutputDirectory(file.getParentFile().getPath());
                    }
                    if (!TextUtils.isEmpty(this.mMediaObject.getOutputDirectory()) && !TextUtils.isEmpty(this.mMediaObject.getKeyName())) {
                        h.a(TAG, "MediaRecorderNative startRecord path: " + this.mMediaObject.getOutputDirectory() + " name: " + this.mMediaObject.getKeyName() + " width : " + out_width + " height: " + out_height + " fps: " + out_fps);
                        String outputDirectory2 = this.mMediaObject.getOutputDirectory();
                        String keyName = this.mMediaObject.getKeyName();
                        int i6 = device_orientation;
                        int i7 = out_width;
                        int i8 = out_height;
                        RtcFFmpegBridge.nativePrepareFFmpegEncoder(outputDirectory2, keyName, i6, i7, i8, i7, i8, out_fps, (long) i5);
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        MediaObject mediaObject = this.mMediaObject;
        if (mediaObject == null) {
            return null;
        }
        MediaObject.MediaPart mediaPartBuildMediaPart = mediaObject.buildMediaPart(this.mCameraId, ".mp4");
        String.format("filename = \"%s\"; ", mediaPartBuildMediaPart.mediaPath);
        this.mRecording = true;
        return mediaPartBuildMediaPart;
    }

    @Override // org.wrtca.record.MediaRecorder
    public void stopAudioResample() throws IOException {
        RtcFFmpegBridge.clearRTCAudioResampleListener();
        RtcFFmpegBridge.flushPcmFileAndClearStream();
        RtcFFmpegBridge.nativeSwitchAudioResample(false);
    }

    @Override // org.wrtca.record.MediaRecorderBase, org.wrtca.record.MediaRecorder
    public void stopRecord() {
        super.stopRecord();
        MediaRecorderBase.OnEncodeListener onEncodeListener = this.mOnEncodeListener;
        if (onEncodeListener != null) {
            onEncodeListener.onEncodeStart();
        }
        h.a(TAG, " sdk stop local record: ");
        RtcFFmpegBridge.nativeRecordEnd();
    }
}
