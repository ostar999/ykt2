package com.luck.lib.camerax;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.display.DisplayManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.camera2.interop.Camera2CameraInfo;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.ZoomState;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import com.google.common.util.concurrent.ListenableFuture;
import com.hjq.permissions.Permission;
import com.luck.lib.camerax.listener.CameraListener;
import com.luck.lib.camerax.listener.CameraXOrientationEventListener;
import com.luck.lib.camerax.listener.CameraXPreviewViewTouchListener;
import com.luck.lib.camerax.listener.CaptureListener;
import com.luck.lib.camerax.listener.ClickListener;
import com.luck.lib.camerax.listener.ImageCallbackListener;
import com.luck.lib.camerax.listener.OnSimpleXPermissionDescriptionListener;
import com.luck.lib.camerax.listener.TypeListener;
import com.luck.lib.camerax.permissions.PermissionChecker;
import com.luck.lib.camerax.permissions.PermissionResultCallback;
import com.luck.lib.camerax.permissions.SimpleXPermissionUtil;
import com.luck.lib.camerax.utils.CameraUtils;
import com.luck.lib.camerax.utils.DensityUtil;
import com.luck.lib.camerax.utils.FileUtils;
import com.luck.lib.camerax.utils.SimpleXSpUtils;
import com.luck.lib.camerax.widget.CaptureLayout;
import com.luck.lib.camerax.widget.FocusImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes4.dex */
public class CustomCameraView extends RelativeLayout implements CameraXOrientationEventListener.OnOrientationChangedListener {
    private static final double RATIO_16_9_VALUE = 1.7777777777777777d;
    private static final double RATIO_4_3_VALUE = 1.3333333333333333d;
    private static final int TYPE_FLASH_AUTO = 33;
    private static final int TYPE_FLASH_OFF = 35;
    private static final int TYPE_FLASH_ON = 34;
    private Activity activity;
    private int buttonFeatures;
    private int displayId;
    private DisplayListener displayListener;
    private DisplayManager displayManager;
    private FocusImageView focusImageView;
    private String imageFormat;
    private String imageFormatForQ;
    private boolean isAutoRotation;
    private boolean isDisplayRecordTime;
    private boolean isManualFocus;
    private boolean isZoomPreview;
    private int lensFacing;
    private CameraControl mCameraControl;
    private CameraInfo mCameraInfo;
    private CameraListener mCameraListener;
    private PreviewView mCameraPreviewView;
    private ProcessCameraProvider mCameraProvider;
    private CaptureLayout mCaptureLayout;
    private ImageView mFlashLamp;
    private ImageAnalysis mImageAnalyzer;
    private ImageCallbackListener mImageCallbackListener;
    private ImageCapture mImageCapture;
    private ImageView mImagePreview;
    private View mImagePreviewBg;
    private MediaPlayer mMediaPlayer;
    private ClickListener mOnClickListener;
    private ImageView mSwitchCamera;
    private TextureView mTextureView;
    private VideoCapture mVideoCapture;
    private Executor mainExecutor;
    private CameraXOrientationEventListener orientationEventListener;
    private String outPutCameraDir;
    private String outPutCameraFileName;
    private long recordTime;
    private int recordVideoMinSecond;
    private final TextureView.SurfaceTextureListener surfaceTextureListener;
    private TextView tvCurrentTime;
    private int typeFlash;
    private int useCameraCases;
    private int videoBitRate;
    private String videoFormat;
    private String videoFormatForQ;
    private int videoFrameRate;

    /* renamed from: com.luck.lib.camerax.CustomCameraView$3, reason: invalid class name */
    public class AnonymousClass3 implements CaptureListener {
        public AnonymousClass3() {
        }

        @Override // com.luck.lib.camerax.listener.CaptureListener
        public void changeTime(long j2) {
            if (CustomCameraView.this.isDisplayRecordTime && CustomCameraView.this.tvCurrentTime.getVisibility() == 0) {
                Locale locale = Locale.getDefault();
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                String str = String.format(locale, "%02d:%02d", Long.valueOf(timeUnit.toMinutes(j2)), Long.valueOf(timeUnit.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(timeUnit.toMinutes(j2))));
                if (!TextUtils.equals(str, CustomCameraView.this.tvCurrentTime.getText())) {
                    CustomCameraView.this.tvCurrentTime.setText(str);
                }
                if (TextUtils.equals("00:00", CustomCameraView.this.tvCurrentTime.getText())) {
                    CustomCameraView.this.tvCurrentTime.setVisibility(8);
                }
            }
        }

        @Override // com.luck.lib.camerax.listener.CaptureListener
        public void recordEnd(long j2) {
            CustomCameraView.this.recordTime = j2;
            try {
                CustomCameraView.this.mVideoCapture.lambda$stopRecording$5();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // com.luck.lib.camerax.listener.CaptureListener
        public void recordError() {
            if (CustomCameraView.this.mCameraListener != null) {
                CustomCameraView.this.mCameraListener.onError(0, "An unknown error", null);
            }
        }

        @Override // com.luck.lib.camerax.listener.CaptureListener
        public void recordShort(long j2) {
            CustomCameraView.this.recordTime = j2;
            CustomCameraView.this.mSwitchCamera.setVisibility(0);
            CustomCameraView.this.mFlashLamp.setVisibility(0);
            CustomCameraView.this.tvCurrentTime.setVisibility(8);
            CustomCameraView.this.mCaptureLayout.resetCaptureLayout();
            CustomCameraView.this.mCaptureLayout.setTextWithAnimation(CustomCameraView.this.getContext().getString(R.string.picture_recording_time_is_short));
            try {
                CustomCameraView.this.mVideoCapture.lambda$stopRecording$5();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // com.luck.lib.camerax.listener.CaptureListener
        public void recordStart() throws IllegalStateException {
            if (!CustomCameraView.this.mCameraProvider.isBound(CustomCameraView.this.mVideoCapture)) {
                CustomCameraView.this.bindCameraVideoUseCases();
            }
            CustomCameraView.this.useCameraCases = 4;
            CustomCameraView.this.mSwitchCamera.setVisibility(4);
            CustomCameraView.this.mFlashLamp.setVisibility(4);
            CustomCameraView.this.tvCurrentTime.setVisibility(CustomCameraView.this.isDisplayRecordTime ? 0 : 8);
            CustomCameraView.this.mVideoCapture.lambda$startRecording$0(new VideoCapture.OutputFileOptions.Builder(CustomCameraView.this.isSaveExternal() ? FileUtils.createTempFile(CustomCameraView.this.getContext(), true) : FileUtils.createCameraFile(CustomCameraView.this.getContext(), 2, CustomCameraView.this.outPutCameraFileName, CustomCameraView.this.videoFormat, CustomCameraView.this.outPutCameraDir)).build(), CustomCameraView.this.mainExecutor, new VideoCapture.OnVideoSavedCallback() { // from class: com.luck.lib.camerax.CustomCameraView.3.1
                @Override // androidx.camera.core.VideoCapture.OnVideoSavedCallback
                public void onError(int i2, @NonNull @NotNull String str, @Nullable @org.jetbrains.annotations.Nullable Throwable th) {
                    if (CustomCameraView.this.mCameraListener != null) {
                        if (i2 == 6 || i2 == 2) {
                            AnonymousClass3.this.recordShort(0L);
                        } else {
                            CustomCameraView.this.mCameraListener.onError(i2, str, th);
                        }
                    }
                }

                @Override // androidx.camera.core.VideoCapture.OnVideoSavedCallback
                public void onVideoSaved(@NonNull @NotNull VideoCapture.OutputFileResults outputFileResults) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                    if (CustomCameraView.this.recordTime < (CustomCameraView.this.recordVideoMinSecond <= 0 ? 1500L : CustomCameraView.this.recordVideoMinSecond) || outputFileResults.getSavedUri() == null) {
                        return;
                    }
                    Uri savedUri = outputFileResults.getSavedUri();
                    SimpleCameraX.putOutputUri(CustomCameraView.this.activity.getIntent(), savedUri);
                    String string = FileUtils.isContent(savedUri.toString()) ? savedUri.toString() : savedUri.getPath();
                    CustomCameraView.this.mTextureView.setVisibility(0);
                    CustomCameraView.this.tvCurrentTime.setVisibility(8);
                    if (CustomCameraView.this.mTextureView.isAvailable()) {
                        CustomCameraView.this.startVideoPlay(string);
                    } else {
                        CustomCameraView.this.mTextureView.setSurfaceTextureListener(CustomCameraView.this.surfaceTextureListener);
                    }
                }
            });
        }

        @Override // com.luck.lib.camerax.listener.CaptureListener
        public void recordZoom(float f2) {
        }

        @Override // com.luck.lib.camerax.listener.CaptureListener
        public void takePictures() {
            if (!CustomCameraView.this.mCameraProvider.isBound(CustomCameraView.this.mImageCapture)) {
                CustomCameraView.this.bindCameraImageUseCases();
            }
            CustomCameraView.this.useCameraCases = 1;
            CustomCameraView.this.mCaptureLayout.setButtonCaptureEnabled(false);
            CustomCameraView.this.mSwitchCamera.setVisibility(4);
            CustomCameraView.this.mFlashLamp.setVisibility(4);
            CustomCameraView.this.tvCurrentTime.setVisibility(8);
            ImageCapture.Metadata metadata = new ImageCapture.Metadata();
            metadata.setReversedHorizontal(CustomCameraView.this.isReversedHorizontal());
            ImageCapture.OutputFileOptions outputFileOptionsBuild = new ImageCapture.OutputFileOptions.Builder(CustomCameraView.this.isSaveExternal() ? FileUtils.createTempFile(CustomCameraView.this.getContext(), false) : FileUtils.createCameraFile(CustomCameraView.this.getContext(), 1, CustomCameraView.this.outPutCameraFileName, CustomCameraView.this.imageFormat, CustomCameraView.this.outPutCameraDir)).setMetadata(metadata).build();
            ImageCapture imageCapture = CustomCameraView.this.mImageCapture;
            Executor executor = CustomCameraView.this.mainExecutor;
            CustomCameraView customCameraView = CustomCameraView.this;
            imageCapture.lambda$takePicture$4(outputFileOptionsBuild, executor, new MyImageResultCallback(customCameraView, customCameraView.mImagePreview, CustomCameraView.this.mImagePreviewBg, CustomCameraView.this.mCaptureLayout, CustomCameraView.this.mImageCallbackListener, CustomCameraView.this.mCameraListener));
        }
    }

    public class DisplayListener implements DisplayManager.DisplayListener {
        private DisplayListener() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i2) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i2) {
            if (i2 == CustomCameraView.this.displayId) {
                if (CustomCameraView.this.mImageCapture != null) {
                    CustomCameraView.this.mImageCapture.setTargetRotation(CustomCameraView.this.mCameraPreviewView.getDisplay().getRotation());
                }
                if (CustomCameraView.this.mImageAnalyzer != null) {
                    CustomCameraView.this.mImageAnalyzer.setTargetRotation(CustomCameraView.this.mCameraPreviewView.getDisplay().getRotation());
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i2) {
        }
    }

    public static class MyImageResultCallback implements ImageCapture.OnImageSavedCallback {
        private final WeakReference<CameraListener> mCameraListenerReference;
        private final WeakReference<CustomCameraView> mCameraViewLayoutReference;
        private final WeakReference<CaptureLayout> mCaptureLayoutReference;
        private final WeakReference<ImageCallbackListener> mImageCallbackListenerReference;
        private final WeakReference<View> mImagePreviewBgReference;
        private final WeakReference<ImageView> mImagePreviewReference;

        public MyImageResultCallback(CustomCameraView customCameraView, ImageView imageView, View view, CaptureLayout captureLayout, ImageCallbackListener imageCallbackListener, CameraListener cameraListener) {
            this.mCameraViewLayoutReference = new WeakReference<>(customCameraView);
            this.mImagePreviewReference = new WeakReference<>(imageView);
            this.mImagePreviewBgReference = new WeakReference<>(view);
            this.mCaptureLayoutReference = new WeakReference<>(captureLayout);
            this.mImageCallbackListenerReference = new WeakReference<>(imageCallbackListener);
            this.mCameraListenerReference = new WeakReference<>(cameraListener);
        }

        @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
        public void onError(@NonNull ImageCaptureException imageCaptureException) {
            if (this.mCaptureLayoutReference.get() != null) {
                this.mCaptureLayoutReference.get().setButtonCaptureEnabled(true);
            }
            if (this.mCameraListenerReference.get() != null) {
                this.mCameraListenerReference.get().onError(imageCaptureException.getImageCaptureError(), imageCaptureException.getMessage(), imageCaptureException.getCause());
            }
        }

        @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
            Uri savedUri = outputFileResults.getSavedUri();
            if (savedUri != null) {
                CustomCameraView customCameraView = this.mCameraViewLayoutReference.get();
                if (customCameraView != null) {
                    customCameraView.stopCheckOrientation();
                }
                ImageView imageView = this.mImagePreviewReference.get();
                if (imageView != null) {
                    SimpleCameraX.putOutputUri(((Activity) imageView.getContext()).getIntent(), savedUri);
                    imageView.setVisibility(0);
                    if (customCameraView != null && customCameraView.isAutoRotation) {
                        int targetRotation = customCameraView.getTargetRotation();
                        if (targetRotation == 1 || targetRotation == 3) {
                            imageView.setAdjustViewBounds(true);
                        } else {
                            imageView.setAdjustViewBounds(false);
                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }
                        View view = this.mImagePreviewBgReference.get();
                        if (view != null) {
                            view.animate().alpha(1.0f).setDuration(220L).start();
                        }
                    }
                    ImageCallbackListener imageCallbackListener = this.mImageCallbackListenerReference.get();
                    if (imageCallbackListener != null) {
                        imageCallbackListener.onLoadImage(FileUtils.isContent(savedUri.toString()) ? savedUri.toString() : savedUri.getPath(), imageView);
                    }
                }
                CaptureLayout captureLayout = this.mCaptureLayoutReference.get();
                if (captureLayout != null) {
                    captureLayout.setButtonCaptureEnabled(true);
                    captureLayout.startTypeBtnAnimator();
                }
            }
        }
    }

    public CustomCameraView(Context context) {
        super(context);
        this.typeFlash = 35;
        this.displayId = -1;
        this.useCameraCases = 1;
        this.lensFacing = 1;
        this.recordTime = 0L;
        this.surfaceTextureListener = new TextureView.SurfaceTextureListener() { // from class: com.luck.lib.camerax.CustomCameraView.9
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                CustomCameraView.this.startVideoPlay(SimpleCameraX.getOutputPath(CustomCameraView.this.activity.getIntent()));
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        };
        initView();
    }

    private int aspectRatio(int i2, int i3) {
        double dMax = Math.max(i2, i3) / Math.min(i2, i3);
        return Math.abs(dMax - RATIO_4_3_VALUE) <= Math.abs(dMax - RATIO_16_9_VALUE) ? 0 : 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindCameraImageUseCases() {
        try {
            int iAspectRatio = aspectRatio(DensityUtil.getScreenWidth(getContext()), DensityUtil.getScreenHeight(getContext()));
            int rotation = this.mCameraPreviewView.getDisplay().getRotation();
            CameraSelector cameraSelectorBuild = new CameraSelector.Builder().requireLensFacing(this.lensFacing).build();
            Preview previewBuild = new Preview.Builder().setTargetAspectRatio(iAspectRatio).setTargetRotation(rotation).build();
            buildImageCapture();
            this.mImageAnalyzer = new ImageAnalysis.Builder().setTargetAspectRatio(iAspectRatio).setTargetRotation(rotation).build();
            this.mCameraProvider.unbindAll();
            Camera cameraBindToLifecycle = this.mCameraProvider.bindToLifecycle((LifecycleOwner) getContext(), cameraSelectorBuild, previewBuild, this.mImageCapture, this.mImageAnalyzer);
            previewBuild.setSurfaceProvider(this.mCameraPreviewView.getSurfaceProvider());
            setFlashMode();
            this.mCameraInfo = cameraBindToLifecycle.getCameraInfo();
            this.mCameraControl = cameraBindToLifecycle.getCameraControl();
            initCameraPreviewListener();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindCameraUseCases() {
        ProcessCameraProvider processCameraProvider = this.mCameraProvider;
        if (processCameraProvider != null && isBackCameraLevel3Device(processCameraProvider)) {
            if (2 == this.buttonFeatures) {
                bindCameraVideoUseCases();
                return;
            } else {
                bindCameraImageUseCases();
                return;
            }
        }
        int i2 = this.buttonFeatures;
        if (i2 == 1) {
            bindCameraImageUseCases();
        } else if (i2 != 2) {
            bindCameraWithUserCases();
        } else {
            bindCameraVideoUseCases();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindCameraVideoUseCases() {
        try {
            CameraSelector cameraSelectorBuild = new CameraSelector.Builder().requireLensFacing(this.lensFacing).build();
            Preview previewBuild = new Preview.Builder().setTargetRotation(this.mCameraPreviewView.getDisplay().getRotation()).build();
            buildVideoCapture();
            this.mCameraProvider.unbindAll();
            Camera cameraBindToLifecycle = this.mCameraProvider.bindToLifecycle((LifecycleOwner) getContext(), cameraSelectorBuild, previewBuild, this.mVideoCapture);
            previewBuild.setSurfaceProvider(this.mCameraPreviewView.getSurfaceProvider());
            this.mCameraInfo = cameraBindToLifecycle.getCameraInfo();
            this.mCameraControl = cameraBindToLifecycle.getCameraControl();
            initCameraPreviewListener();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void bindCameraWithUserCases() {
        try {
            CameraSelector cameraSelectorBuild = new CameraSelector.Builder().requireLensFacing(this.lensFacing).build();
            Preview previewBuild = new Preview.Builder().setTargetRotation(this.mCameraPreviewView.getDisplay().getRotation()).build();
            buildImageCapture();
            buildVideoCapture();
            UseCaseGroup.Builder builder = new UseCaseGroup.Builder();
            builder.addUseCase(previewBuild);
            builder.addUseCase(this.mImageCapture);
            builder.addUseCase(this.mVideoCapture);
            UseCaseGroup useCaseGroupBuild = builder.build();
            this.mCameraProvider.unbindAll();
            Camera cameraBindToLifecycle = this.mCameraProvider.bindToLifecycle((LifecycleOwner) getContext(), cameraSelectorBuild, useCaseGroupBuild);
            previewBuild.setSurfaceProvider(this.mCameraPreviewView.getSurfaceProvider());
            setFlashMode();
            this.mCameraInfo = cameraBindToLifecycle.getCameraInfo();
            this.mCameraControl = cameraBindToLifecycle.getCameraControl();
            initCameraPreviewListener();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void buildImageCapture() {
        this.mImageCapture = new ImageCapture.Builder().setCaptureMode(1).setTargetAspectRatio(aspectRatio(DensityUtil.getScreenWidth(getContext()), DensityUtil.getScreenHeight(getContext()))).setTargetRotation(this.mCameraPreviewView.getDisplay().getRotation()).build();
    }

    @SuppressLint({"RestrictedApi"})
    private void buildVideoCapture() {
        VideoCapture.Builder builder = new VideoCapture.Builder();
        builder.setTargetRotation(this.mCameraPreviewView.getDisplay().getRotation());
        int i2 = this.videoFrameRate;
        if (i2 > 0) {
            builder.setVideoFrameRate(i2);
        }
        int i3 = this.videoBitRate;
        if (i3 > 0) {
            builder.setBitRate(i3);
        }
        this.mVideoCapture = builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTargetRotation() {
        return this.mImageCapture.getTargetRotation();
    }

    private void initCameraPreviewListener() {
        final LiveData<ZoomState> zoomState = this.mCameraInfo.getZoomState();
        CameraXPreviewViewTouchListener cameraXPreviewViewTouchListener = new CameraXPreviewViewTouchListener(getContext());
        cameraXPreviewViewTouchListener.setCustomTouchListener(new CameraXPreviewViewTouchListener.CustomTouchListener() { // from class: com.luck.lib.camerax.CustomCameraView.8
            @Override // com.luck.lib.camerax.listener.CameraXPreviewViewTouchListener.CustomTouchListener
            public void click(float f2, float f3) {
                if (CustomCameraView.this.isManualFocus) {
                    FocusMeteringAction focusMeteringActionBuild = new FocusMeteringAction.Builder(CustomCameraView.this.mCameraPreviewView.getMeteringPointFactory().createPoint(f2, f3), 1).setAutoCancelDuration(3L, TimeUnit.SECONDS).build();
                    if (CustomCameraView.this.mCameraInfo.isFocusMeteringSupported(focusMeteringActionBuild)) {
                        CustomCameraView.this.mCameraControl.cancelFocusAndMetering();
                        CustomCameraView.this.focusImageView.setDisappear(false);
                        CustomCameraView.this.focusImageView.startFocus(new Point((int) f2, (int) f3));
                        final ListenableFuture<FocusMeteringResult> listenableFutureStartFocusAndMetering = CustomCameraView.this.mCameraControl.startFocusAndMetering(focusMeteringActionBuild);
                        listenableFutureStartFocusAndMetering.addListener(new Runnable() { // from class: com.luck.lib.camerax.CustomCameraView.8.1
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    FocusMeteringResult focusMeteringResult = (FocusMeteringResult) listenableFutureStartFocusAndMetering.get();
                                    CustomCameraView.this.focusImageView.setDisappear(true);
                                    if (focusMeteringResult.isFocusSuccessful()) {
                                        CustomCameraView.this.focusImageView.onFocusSuccess();
                                    } else {
                                        CustomCameraView.this.focusImageView.onFocusFailed();
                                    }
                                } catch (Exception unused) {
                                }
                            }
                        }, CustomCameraView.this.mainExecutor);
                    }
                }
            }

            @Override // com.luck.lib.camerax.listener.CameraXPreviewViewTouchListener.CustomTouchListener
            public void doubleClick(float f2, float f3) {
                if (!CustomCameraView.this.isZoomPreview || zoomState.getValue() == null) {
                    return;
                }
                if (((ZoomState) zoomState.getValue()).getZoomRatio() > ((ZoomState) zoomState.getValue()).getMinZoomRatio()) {
                    CustomCameraView.this.mCameraControl.setLinearZoom(0.0f);
                } else {
                    CustomCameraView.this.mCameraControl.setLinearZoom(0.5f);
                }
            }

            @Override // com.luck.lib.camerax.listener.CameraXPreviewViewTouchListener.CustomTouchListener
            public void zoom(float f2) {
                if (!CustomCameraView.this.isZoomPreview || zoomState.getValue() == null) {
                    return;
                }
                CustomCameraView.this.mCameraControl.setZoomRatio(((ZoomState) zoomState.getValue()).getZoomRatio() * f2);
            }
        });
        this.mCameraPreviewView.setOnTouchListener(cameraXPreviewViewTouchListener);
    }

    private void initView() {
        View.inflate(getContext(), R.layout.picture_camera_view, this);
        this.activity = (Activity) getContext();
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.picture_color_black));
        this.mCameraPreviewView = (PreviewView) findViewById(R.id.cameraPreviewView);
        this.mTextureView = (TextureView) findViewById(R.id.video_play_preview);
        this.focusImageView = (FocusImageView) findViewById(R.id.focus_view);
        this.mImagePreview = (ImageView) findViewById(R.id.cover_preview);
        this.mImagePreviewBg = findViewById(R.id.cover_preview_bg);
        this.mSwitchCamera = (ImageView) findViewById(R.id.image_switch);
        this.mFlashLamp = (ImageView) findViewById(R.id.image_flash);
        this.mCaptureLayout = (CaptureLayout) findViewById(R.id.capture_layout);
        this.tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        this.mSwitchCamera.setImageResource(R.drawable.picture_ic_camera);
        this.displayManager = (DisplayManager) getContext().getSystemService("display");
        DisplayListener displayListener = new DisplayListener();
        this.displayListener = displayListener;
        this.displayManager.registerDisplayListener(displayListener, null);
        this.mainExecutor = ContextCompat.getMainExecutor(getContext());
        this.mCameraPreviewView.post(new Runnable() { // from class: com.luck.lib.camerax.CustomCameraView.1
            @Override // java.lang.Runnable
            public void run() {
                Display display;
                if (CustomCameraView.this.mCameraPreviewView == null || (display = CustomCameraView.this.mCameraPreviewView.getDisplay()) == null) {
                    return;
                }
                CustomCameraView.this.displayId = display.getDisplayId();
            }
        });
        this.mFlashLamp.setOnClickListener(new View.OnClickListener() { // from class: com.luck.lib.camerax.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f8884c.lambda$initView$0(view);
            }
        });
        this.mSwitchCamera.setOnClickListener(new View.OnClickListener() { // from class: com.luck.lib.camerax.CustomCameraView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomCameraView.this.toggleCamera();
            }
        });
        this.mCaptureLayout.setCaptureListener(new AnonymousClass3());
        this.mCaptureLayout.setTypeListener(new TypeListener() { // from class: com.luck.lib.camerax.CustomCameraView.4
            @Override // com.luck.lib.camerax.listener.TypeListener
            public void cancel() {
                CustomCameraView.this.onCancelMedia();
            }

            @Override // com.luck.lib.camerax.listener.TypeListener
            public void confirm() throws IllegalStateException, FileNotFoundException {
                String outputPath = SimpleCameraX.getOutputPath(CustomCameraView.this.activity.getIntent());
                if (CustomCameraView.this.isSaveExternal()) {
                    CustomCameraView customCameraView = CustomCameraView.this;
                    outputPath = customCameraView.isMergeExternalStorageState(customCameraView.activity, outputPath);
                } else if (CustomCameraView.this.isImageCaptureEnabled() && CustomCameraView.this.isReversedHorizontal()) {
                    File fileCreateCameraFile = FileUtils.createCameraFile(CustomCameraView.this.getContext(), 1, CustomCameraView.this.outPutCameraFileName, CustomCameraView.this.imageFormat, CustomCameraView.this.outPutCameraDir);
                    if (FileUtils.copyPath(CustomCameraView.this.activity, outputPath, fileCreateCameraFile.getAbsolutePath())) {
                        outputPath = fileCreateCameraFile.getAbsolutePath();
                        SimpleCameraX.putOutputUri(CustomCameraView.this.activity.getIntent(), Uri.fromFile(fileCreateCameraFile));
                    }
                }
                if (!CustomCameraView.this.isImageCaptureEnabled()) {
                    CustomCameraView.this.stopVideoPlay();
                    if (CustomCameraView.this.mCameraListener != null) {
                        CustomCameraView.this.mCameraListener.onRecordSuccess(outputPath);
                        return;
                    }
                    return;
                }
                CustomCameraView.this.mImagePreview.setVisibility(4);
                CustomCameraView.this.mImagePreviewBg.setAlpha(0.0f);
                if (CustomCameraView.this.mCameraListener != null) {
                    CustomCameraView.this.mCameraListener.onPictureSuccess(outputPath);
                }
            }
        });
        this.mCaptureLayout.setLeftClickListener(new ClickListener() { // from class: com.luck.lib.camerax.CustomCameraView.5
            @Override // com.luck.lib.camerax.listener.ClickListener
            public void onClick() {
                if (CustomCameraView.this.mOnClickListener != null) {
                    CustomCameraView.this.mOnClickListener.onClick();
                }
            }
        });
    }

    @SuppressLint({"UnsafeOptInUsageError"})
    private boolean isBackCameraLevel3Device(ProcessCameraProvider processCameraProvider) {
        if (Build.VERSION.SDK_INT >= 24) {
            List<CameraInfo> listFilter = CameraSelector.DEFAULT_BACK_CAMERA.filter(processCameraProvider.getAvailableCameraInfos());
            if (!listFilter.isEmpty()) {
                return Objects.equals(Camera2CameraInfo.from(listFilter.get(0)).getCameraCharacteristic(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL), 2);
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isImageCaptureEnabled() {
        return this.useCameraCases == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String isMergeExternalStorageState(Activity activity, String str) throws FileNotFoundException {
        Uri uriInsert;
        try {
            if (isImageCaptureEnabled() && isReversedHorizontal()) {
                File fileCreateTempFile = FileUtils.createTempFile(activity, false);
                if (FileUtils.copyPath(activity, str, fileCreateTempFile.getAbsolutePath())) {
                    str = fileCreateTempFile.getAbsolutePath();
                }
            }
            if (isImageCaptureEnabled()) {
                uriInsert = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CameraUtils.buildImageContentValues(this.outPutCameraFileName, this.imageFormatForQ));
            } else {
                uriInsert = getContext().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, CameraUtils.buildVideoContentValues(this.outPutCameraFileName, this.videoFormatForQ));
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        if (uriInsert == null) {
            return str;
        }
        if (FileUtils.writeFileFromIS(new FileInputStream(str), getContext().getContentResolver().openOutputStream(uriInsert))) {
            FileUtils.deleteFile(getContext(), str);
            SimpleCameraX.putOutputUri(activity.getIntent(), uriInsert);
            return uriInsert.toString();
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isReversedHorizontal() {
        return this.lensFacing == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSaveExternal() {
        return Build.VERSION.SDK_INT >= 29 && TextUtils.isEmpty(this.outPutCameraDir);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int i2 = this.typeFlash + 1;
        this.typeFlash = i2;
        if (i2 > 35) {
            this.typeFlash = 33;
        }
        setFlashMode();
    }

    private void resetState() {
        if (isImageCaptureEnabled()) {
            this.mImagePreview.setVisibility(4);
            this.mImagePreviewBg.setAlpha(0.0f);
        } else {
            try {
                this.mVideoCapture.lambda$stopRecording$5();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.mSwitchCamera.setVisibility(0);
        this.mFlashLamp.setVisibility(0);
        this.mCaptureLayout.resetCaptureLayout();
    }

    private void setFlashMode() {
        if (this.mImageCapture == null) {
        }
        switch (this.typeFlash) {
            case 33:
                this.mFlashLamp.setImageResource(R.drawable.picture_ic_flash_auto);
                this.mImageCapture.setFlashMode(0);
                break;
            case 34:
                this.mFlashLamp.setImageResource(R.drawable.picture_ic_flash_on);
                this.mImageCapture.setFlashMode(1);
                break;
            case 35:
                this.mFlashLamp.setImageResource(R.drawable.picture_ic_flash_off);
                this.mImageCapture.setFlashMode(2);
                break;
        }
    }

    private void startCheckOrientation() {
        CameraXOrientationEventListener cameraXOrientationEventListener = this.orientationEventListener;
        if (cameraXOrientationEventListener != null) {
            cameraXOrientationEventListener.star();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startVideoPlay(String str) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        try {
            MediaPlayer mediaPlayer = this.mMediaPlayer;
            if (mediaPlayer == null) {
                this.mMediaPlayer = new MediaPlayer();
            } else {
                mediaPlayer.reset();
            }
            if (FileUtils.isContent(str)) {
                this.mMediaPlayer.setDataSource(getContext(), Uri.parse(str));
            } else {
                this.mMediaPlayer.setDataSource(str);
            }
            this.mMediaPlayer.setSurface(new Surface(this.mTextureView.getSurfaceTexture()));
            this.mMediaPlayer.setVideoScalingMode(1);
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() { // from class: com.luck.lib.camerax.CustomCameraView.10
                @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
                public void onVideoSizeChanged(MediaPlayer mediaPlayer2, int i2, int i3) {
                    CustomCameraView.this.updateVideoViewSize(r1.mMediaPlayer.getVideoWidth(), CustomCameraView.this.mMediaPlayer.getVideoHeight());
                }
            });
            this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.luck.lib.camerax.CustomCameraView.11
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer2) throws IllegalStateException {
                    CustomCameraView.this.mMediaPlayer.start();
                }
            });
            this.mMediaPlayer.setLooping(true);
            this.mMediaPlayer.prepareAsync();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopVideoPlay() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        this.mTextureView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVideoViewSize(float f2, float f3) {
        if (f2 > f3) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, (int) ((f3 / f2) * getWidth()));
            layoutParams.addRule(13, -1);
            this.mTextureView.setLayoutParams(layoutParams);
        }
    }

    public void buildUseCameraCases() {
        final ListenableFuture<ProcessCameraProvider> processCameraProvider = ProcessCameraProvider.getInstance(getContext());
        processCameraProvider.addListener(new Runnable() { // from class: com.luck.lib.camerax.CustomCameraView.7
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                try {
                    CustomCameraView.this.mCameraProvider = (ProcessCameraProvider) processCameraProvider.get();
                    CustomCameraView.this.bindCameraUseCases();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }, this.mainExecutor);
    }

    public void onCancelMedia() {
        FileUtils.deleteFile(getContext(), SimpleCameraX.getOutputPath(this.activity.getIntent()));
        stopVideoPlay();
        resetState();
        startCheckOrientation();
    }

    @Override // android.view.View
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        buildUseCameraCases();
    }

    public void onDestroy() {
        this.displayManager.unregisterDisplayListener(this.displayListener);
        stopCheckOrientation();
        this.focusImageView.destroy();
    }

    @Override // com.luck.lib.camerax.listener.CameraXOrientationEventListener.OnOrientationChangedListener
    public void onOrientationChanged(int i2) {
        ImageCapture imageCapture = this.mImageCapture;
        if (imageCapture != null) {
            imageCapture.setTargetRotation(i2);
        }
        ImageAnalysis imageAnalysis = this.mImageAnalyzer;
        if (imageAnalysis != null) {
            imageAnalysis.setTargetRotation(i2);
        }
    }

    public void setCameraConfig(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }
        boolean z2 = extras.getBoolean(SimpleCameraX.EXTRA_CAMERA_AROUND_STATE, false);
        this.buttonFeatures = extras.getInt(SimpleCameraX.EXTRA_CAMERA_MODE, 0);
        this.lensFacing = !z2 ? 1 : 0;
        this.outPutCameraDir = extras.getString(SimpleCameraX.EXTRA_OUTPUT_PATH_DIR);
        this.outPutCameraFileName = extras.getString(SimpleCameraX.EXTRA_CAMERA_FILE_NAME);
        this.videoFrameRate = extras.getInt(SimpleCameraX.EXTRA_VIDEO_FRAME_RATE);
        this.videoBitRate = extras.getInt(SimpleCameraX.EXTRA_VIDEO_BIT_RATE);
        this.isManualFocus = extras.getBoolean(SimpleCameraX.EXTRA_MANUAL_FOCUS);
        this.isZoomPreview = extras.getBoolean(SimpleCameraX.EXTRA_ZOOM_PREVIEW);
        this.isAutoRotation = extras.getBoolean(SimpleCameraX.EXTRA_AUTO_ROTATION);
        int i2 = extras.getInt(SimpleCameraX.EXTRA_RECORD_VIDEO_MAX_SECOND, CustomCameraConfig.DEFAULT_MAX_RECORD_VIDEO);
        this.recordVideoMinSecond = extras.getInt(SimpleCameraX.EXTRA_RECORD_VIDEO_MIN_SECOND, 1500);
        this.imageFormat = extras.getString(SimpleCameraX.EXTRA_CAMERA_IMAGE_FORMAT, ".jpeg");
        this.imageFormatForQ = extras.getString(SimpleCameraX.EXTRA_CAMERA_IMAGE_FORMAT_FOR_Q, "image/jpeg");
        this.videoFormat = extras.getString(SimpleCameraX.EXTRA_CAMERA_VIDEO_FORMAT, ".mp4");
        this.videoFormatForQ = extras.getString(SimpleCameraX.EXTRA_CAMERA_VIDEO_FORMAT_FOR_Q, "video/mp4");
        int i3 = extras.getInt(SimpleCameraX.EXTRA_CAPTURE_LOADING_COLOR, -8552961);
        this.isDisplayRecordTime = extras.getBoolean(SimpleCameraX.EXTRA_DISPLAY_RECORD_CHANGE_TIME, false);
        this.mCaptureLayout.setButtonFeatures(this.buttonFeatures);
        if (i2 > 0) {
            setRecordVideoMaxTime(i2);
        }
        int i4 = this.recordVideoMinSecond;
        if (i4 > 0) {
            setRecordVideoMinTime(i4);
        }
        Locale locale = Locale.getDefault();
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        long j2 = i2;
        this.tvCurrentTime.setText(String.format(locale, "%02d:%02d", Long.valueOf(timeUnit.toMinutes(j2)), Long.valueOf(timeUnit.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(timeUnit.toMinutes(j2)))));
        if (this.isAutoRotation && this.buttonFeatures != 2) {
            this.orientationEventListener = new CameraXOrientationEventListener(getContext(), this);
            startCheckOrientation();
        }
        setCaptureLoadingColor(i3);
        setProgressColor(i3);
        if (PermissionChecker.checkSelfPermission(getContext(), new String[]{Permission.CAMERA})) {
            buildUseCameraCases();
            return;
        }
        if (CustomCameraConfig.explainListener != null && !SimpleXSpUtils.getBoolean(getContext(), Permission.CAMERA, false)) {
            CustomCameraConfig.explainListener.onPermissionDescription(getContext(), this, Permission.CAMERA);
        }
        PermissionChecker.getInstance().requestPermissions(this.activity, new String[]{Permission.CAMERA}, new PermissionResultCallback() { // from class: com.luck.lib.camerax.CustomCameraView.6
            @Override // com.luck.lib.camerax.permissions.PermissionResultCallback
            public void onDenied() {
                if (CustomCameraConfig.deniedListener == null) {
                    SimpleXPermissionUtil.goIntentSetting(CustomCameraView.this.activity, 1102);
                    return;
                }
                SimpleXSpUtils.putBoolean(CustomCameraView.this.getContext(), Permission.CAMERA, true);
                CustomCameraConfig.deniedListener.onDenied(CustomCameraView.this.getContext(), Permission.CAMERA, 1102);
                OnSimpleXPermissionDescriptionListener onSimpleXPermissionDescriptionListener = CustomCameraConfig.explainListener;
                if (onSimpleXPermissionDescriptionListener != null) {
                    onSimpleXPermissionDescriptionListener.onDismiss(CustomCameraView.this);
                }
            }

            @Override // com.luck.lib.camerax.permissions.PermissionResultCallback
            public void onGranted() {
                CustomCameraView.this.buildUseCameraCases();
                OnSimpleXPermissionDescriptionListener onSimpleXPermissionDescriptionListener = CustomCameraConfig.explainListener;
                if (onSimpleXPermissionDescriptionListener != null) {
                    onSimpleXPermissionDescriptionListener.onDismiss(CustomCameraView.this);
                }
            }
        });
    }

    public void setCameraListener(CameraListener cameraListener) {
        this.mCameraListener = cameraListener;
    }

    public void setCaptureLoadingColor(int i2) {
        this.mCaptureLayout.setCaptureLoadingColor(i2);
    }

    public void setImageCallbackListener(ImageCallbackListener imageCallbackListener) {
        this.mImageCallbackListener = imageCallbackListener;
    }

    public void setOnCancelClickListener(ClickListener clickListener) {
        this.mOnClickListener = clickListener;
    }

    public void setProgressColor(int i2) {
        this.mCaptureLayout.setProgressColor(i2);
    }

    public void setRecordVideoMaxTime(int i2) {
        this.mCaptureLayout.setDuration(i2);
    }

    public void setRecordVideoMinTime(int i2) {
        this.mCaptureLayout.setMinDuration(i2);
    }

    public void stopCheckOrientation() {
        CameraXOrientationEventListener cameraXOrientationEventListener = this.orientationEventListener;
        if (cameraXOrientationEventListener != null) {
            cameraXOrientationEventListener.stop();
        }
    }

    public void toggleCamera() {
        this.lensFacing = this.lensFacing == 0 ? 1 : 0;
        bindCameraUseCases();
    }

    public CustomCameraView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.typeFlash = 35;
        this.displayId = -1;
        this.useCameraCases = 1;
        this.lensFacing = 1;
        this.recordTime = 0L;
        this.surfaceTextureListener = new TextureView.SurfaceTextureListener() { // from class: com.luck.lib.camerax.CustomCameraView.9
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                CustomCameraView.this.startVideoPlay(SimpleCameraX.getOutputPath(CustomCameraView.this.activity.getIntent()));
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        };
        initView();
    }

    public CustomCameraView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.typeFlash = 35;
        this.displayId = -1;
        this.useCameraCases = 1;
        this.lensFacing = 1;
        this.recordTime = 0L;
        this.surfaceTextureListener = new TextureView.SurfaceTextureListener() { // from class: com.luck.lib.camerax.CustomCameraView.9
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i22, int i3) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                CustomCameraView.this.startVideoPlay(SimpleCameraX.getOutputPath(CustomCameraView.this.activity.getIntent()));
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i22, int i3) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        };
        initView();
    }
}
