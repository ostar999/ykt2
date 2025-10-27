package com.luck.lib.camerax;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.luck.lib.camerax.listener.OnSimpleXPermissionDeniedListener;
import com.luck.lib.camerax.listener.OnSimpleXPermissionDescriptionListener;
import com.luck.lib.camerax.utils.FileUtils;

/* loaded from: classes4.dex */
public class SimpleCameraX {
    public static final String EXTRA_AUTO_ROTATION = "com.luck.lib.camerax.isAutoRotation";
    public static final String EXTRA_CAMERA_AROUND_STATE = "com.luck.lib.camerax.CameraAroundState";
    public static final String EXTRA_CAMERA_FILE_NAME = "com.luck.lib.camerax.CameraFileName";
    public static final String EXTRA_CAMERA_IMAGE_FORMAT = "com.luck.lib.camerax.CameraImageFormat";
    public static final String EXTRA_CAMERA_IMAGE_FORMAT_FOR_Q = "com.luck.lib.camerax.CameraImageFormatForQ";
    public static final String EXTRA_CAMERA_MODE = "com.luck.lib.camerax.CameraMode";
    public static final String EXTRA_CAMERA_VIDEO_FORMAT = "com.luck.lib.camerax.CameraVideoFormat";
    public static final String EXTRA_CAMERA_VIDEO_FORMAT_FOR_Q = "com.luck.lib.camerax.CameraVideoFormatForQ";
    public static final String EXTRA_CAPTURE_LOADING_COLOR = "com.luck.lib.camerax.CaptureLoadingColor";
    public static final String EXTRA_DISPLAY_RECORD_CHANGE_TIME = "com.luck.lib.camerax.DisplayRecordChangeTime";
    public static final String EXTRA_MANUAL_FOCUS = "com.luck.lib.camerax.isManualFocus";
    public static final String EXTRA_OUTPUT_PATH_DIR = "com.luck.lib.camerax.OutputPathDir";
    private static final String EXTRA_PREFIX = "com.luck.lib.camerax";
    public static final String EXTRA_RECORD_VIDEO_MAX_SECOND = "com.luck.lib.camerax.RecordVideoMaxSecond";
    public static final String EXTRA_RECORD_VIDEO_MIN_SECOND = "com.luck.lib.camerax.RecordVideoMinSecond";
    public static final String EXTRA_VIDEO_BIT_RATE = "com.luck.lib.camerax.VideoBitRate";
    public static final String EXTRA_VIDEO_FRAME_RATE = "com.luck.lib.camerax.VideoFrameRate";
    public static final String EXTRA_ZOOM_PREVIEW = "com.luck.lib.camerax.isZoomPreview";
    private final Intent mCameraIntent = new Intent();
    private final Bundle mCameraBundle = new Bundle();

    private SimpleCameraX() {
    }

    public static String getOutputPath(Intent intent) {
        Uri uri = (Uri) intent.getParcelableExtra("output");
        return uri == null ? "" : FileUtils.isContent(uri.toString()) ? uri.toString() : uri.getPath();
    }

    public static SimpleCameraX of() {
        return new SimpleCameraX();
    }

    public static void putOutputUri(Intent intent, Uri uri) {
        intent.putExtra("output", uri);
    }

    public Intent getIntent(@NonNull Context context) {
        this.mCameraIntent.setClass(context, PictureCameraActivity.class);
        this.mCameraIntent.putExtras(this.mCameraBundle);
        return this.mCameraIntent;
    }

    public SimpleCameraX isAutoRotation(boolean z2) {
        this.mCameraBundle.putBoolean(EXTRA_AUTO_ROTATION, z2);
        return this;
    }

    public SimpleCameraX isDisplayRecordChangeTime(boolean z2) {
        this.mCameraBundle.putBoolean(EXTRA_DISPLAY_RECORD_CHANGE_TIME, z2);
        return this;
    }

    public SimpleCameraX isManualFocusCameraPreview(boolean z2) {
        this.mCameraBundle.putBoolean(EXTRA_MANUAL_FOCUS, z2);
        return this;
    }

    public SimpleCameraX isZoomCameraPreview(boolean z2) {
        this.mCameraBundle.putBoolean(EXTRA_ZOOM_PREVIEW, z2);
        return this;
    }

    public SimpleCameraX setCameraAroundState(boolean z2) {
        this.mCameraBundle.putBoolean(EXTRA_CAMERA_AROUND_STATE, z2);
        return this;
    }

    public SimpleCameraX setCameraImageFormat(String str) {
        this.mCameraBundle.putString(EXTRA_CAMERA_IMAGE_FORMAT, str);
        return this;
    }

    public SimpleCameraX setCameraImageFormatForQ(String str) {
        this.mCameraBundle.putString(EXTRA_CAMERA_IMAGE_FORMAT_FOR_Q, str);
        return this;
    }

    public SimpleCameraX setCameraMode(int i2) {
        this.mCameraBundle.putInt(EXTRA_CAMERA_MODE, i2);
        return this;
    }

    public SimpleCameraX setCameraOutputFileName(String str) {
        this.mCameraBundle.putString(EXTRA_CAMERA_FILE_NAME, str);
        return this;
    }

    public SimpleCameraX setCameraVideoFormat(String str) {
        this.mCameraBundle.putString(EXTRA_CAMERA_VIDEO_FORMAT, str);
        return this;
    }

    public SimpleCameraX setCameraVideoFormatForQ(String str) {
        this.mCameraBundle.putString(EXTRA_CAMERA_VIDEO_FORMAT_FOR_Q, str);
        return this;
    }

    public SimpleCameraX setCaptureLoadingColor(int i2) {
        this.mCameraBundle.putInt(EXTRA_CAPTURE_LOADING_COLOR, i2);
        return this;
    }

    public SimpleCameraX setImageEngine(CameraImageEngine cameraImageEngine) {
        CustomCameraConfig.imageEngine = cameraImageEngine;
        return this;
    }

    public SimpleCameraX setOutputPathDir(String str) {
        this.mCameraBundle.putString(EXTRA_OUTPUT_PATH_DIR, str);
        return this;
    }

    public SimpleCameraX setPermissionDeniedListener(OnSimpleXPermissionDeniedListener onSimpleXPermissionDeniedListener) {
        CustomCameraConfig.deniedListener = onSimpleXPermissionDeniedListener;
        return this;
    }

    public SimpleCameraX setPermissionDescriptionListener(OnSimpleXPermissionDescriptionListener onSimpleXPermissionDescriptionListener) {
        CustomCameraConfig.explainListener = onSimpleXPermissionDescriptionListener;
        return this;
    }

    public SimpleCameraX setRecordVideoMaxSecond(int i2) {
        this.mCameraBundle.putInt(EXTRA_RECORD_VIDEO_MAX_SECOND, (i2 * 1000) + 500);
        return this;
    }

    public SimpleCameraX setRecordVideoMinSecond(int i2) {
        this.mCameraBundle.putInt(EXTRA_RECORD_VIDEO_MIN_SECOND, i2 * 1000);
        return this;
    }

    public SimpleCameraX setVideoBitRate(int i2) {
        this.mCameraBundle.putInt(EXTRA_VIDEO_BIT_RATE, i2);
        return this;
    }

    public SimpleCameraX setVideoFrameRate(int i2) {
        this.mCameraBundle.putInt(EXTRA_VIDEO_FRAME_RATE, i2);
        return this;
    }

    public void start(@NonNull Activity activity, int i2) {
        if (CustomCameraConfig.imageEngine == null) {
            throw new NullPointerException("Missing ImageEngine,please implement SimpleCamerax.setImageEngine");
        }
        activity.startActivityForResult(getIntent(activity), i2);
    }

    public void start(@NonNull Context context, @NonNull Fragment fragment, int i2) {
        if (CustomCameraConfig.imageEngine != null) {
            fragment.startActivityForResult(getIntent(context), i2);
            return;
        }
        throw new NullPointerException("Missing ImageEngine,please implement SimpleCamerax.setImageEngine");
    }
}
