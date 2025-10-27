package com.uuzuche.lib_zxing.activity;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.uuzuche.lib_zxing.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.camera.CameraManager;
import com.uuzuche.lib_zxing.decoding.CaptureActivityHandler;
import com.uuzuche.lib_zxing.decoding.InactivityTimer;
import com.uuzuche.lib_zxing.view.ViewfinderView;
import java.io.IOException;
import java.util.Vector;

/* loaded from: classes6.dex */
public class CaptureFragment extends Fragment implements SurfaceHolder.Callback {
    private static final float BEEP_VOLUME = 0.1f;
    private static final long VIBRATE_DURATION = 200;
    private CodeUtils.AnalyzeCallback analyzeCallback;

    @Nullable
    CameraInitCallBack callBack;
    private Camera camera;
    private String characterSet;
    private Vector<BarcodeFormat> decodeFormats;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private TextView tvMsg;
    private boolean vibrate;
    private ViewfinderView viewfinderView;
    private final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.uuzuche.lib_zxing.activity.CaptureFragment.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            int i2 = CameraManager.FRAME_HEIGHT / 2;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) CaptureFragment.this.tvMsg.getLayoutParams();
            layoutParams.topMargin = i2 + ((int) TypedValue.applyDimension(1, 16.0f, CaptureFragment.this.getResources().getDisplayMetrics()));
            CaptureFragment.this.tvMsg.setLayoutParams(layoutParams);
            CaptureFragment.this.surfaceView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    };
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() { // from class: com.uuzuche.lib_zxing.activity.CaptureFragment.2
        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException {
            mediaPlayer.seekTo(0);
        }
    };

    public interface CameraInitCallBack {
        void callBack(Exception exc);
    }

    private void initBeepSound() throws IllegalStateException, Resources.NotFoundException, IOException, IllegalArgumentException {
        if (this.playBeep && this.mediaPlayer == null) {
            getActivity().setVolumeControlStream(3);
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mediaPlayer = mediaPlayer;
            mediaPlayer.setAudioStreamType(3);
            this.mediaPlayer.setOnCompletionListener(this.beepListener);
            AssetFileDescriptor assetFileDescriptorOpenRawResourceFd = getResources().openRawResourceFd(R.raw.beep);
            try {
                this.mediaPlayer.setDataSource(assetFileDescriptorOpenRawResourceFd.getFileDescriptor(), assetFileDescriptorOpenRawResourceFd.getStartOffset(), assetFileDescriptorOpenRawResourceFd.getLength());
                assetFileDescriptorOpenRawResourceFd.close();
                this.mediaPlayer.setVolume(0.1f, 0.1f);
                this.mediaPlayer.prepare();
            } catch (IOException unused) {
                this.mediaPlayer = null;
            }
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            this.camera = CameraManager.get().getCamera();
            CameraInitCallBack cameraInitCallBack = this.callBack;
            if (cameraInitCallBack != null) {
                cameraInitCallBack.callBack(null);
            }
            if (this.handler == null) {
                this.handler = new CaptureActivityHandler(this, this.decodeFormats, this.characterSet, this.viewfinderView);
            }
        } catch (Exception e2) {
            CameraInitCallBack cameraInitCallBack2 = this.callBack;
            if (cameraInitCallBack2 != null) {
                cameraInitCallBack2.callBack(e2);
            }
        }
    }

    private void playBeepSoundAndVibrate() throws IllegalStateException {
        MediaPlayer mediaPlayer;
        if (this.playBeep && (mediaPlayer = this.mediaPlayer) != null) {
            mediaPlayer.start();
        }
        if (this.vibrate) {
            FragmentActivity activity = getActivity();
            getActivity();
            ((Vibrator) activity.getSystemService("vibrator")).vibrate(200L);
        }
    }

    public void drawViewfinder() {
        this.viewfinderView.drawViewfinder();
    }

    public CodeUtils.AnalyzeCallback getAnalyzeCallback() {
        return this.analyzeCallback;
    }

    public Handler getHandler() {
        return this.handler;
    }

    public void handleDecode(Result result, Bitmap bitmap) throws IllegalStateException {
        this.inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        if (result == null || TextUtils.isEmpty(result.getText())) {
            CodeUtils.AnalyzeCallback analyzeCallback = this.analyzeCallback;
            if (analyzeCallback != null) {
                analyzeCallback.onAnalyzeFailed();
                return;
            }
            return;
        }
        CodeUtils.AnalyzeCallback analyzeCallback2 = this.analyzeCallback;
        if (analyzeCallback2 != null) {
            analyzeCallback2.onAnalyzeSuccess(bitmap, result.getText());
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        CameraManager.init(getActivity().getApplication());
        this.hasSurface = false;
        this.inactivityTimer = new InactivityTimer(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        int i2;
        Bundle arguments = getArguments();
        View viewInflate = (arguments == null || (i2 = arguments.getInt(CodeUtils.LAYOUT_ID)) == -1) ? null : layoutInflater.inflate(i2, (ViewGroup) null);
        if (viewInflate == null) {
            viewInflate = layoutInflater.inflate(R.layout.fragment_capture, (ViewGroup) null);
        }
        this.viewfinderView = (ViewfinderView) viewInflate.findViewById(R.id.viewfinder_view);
        this.surfaceView = (SurfaceView) viewInflate.findViewById(R.id.preview_view);
        this.tvMsg = (TextView) viewInflate.findViewById(R.id.tv_msg);
        this.surfaceHolder = this.surfaceView.getHolder();
        this.viewfinderView.getViewTreeObserver().addOnGlobalLayoutListener(this.layoutListener);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.inactivityTimer.shutdown();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        CaptureActivityHandler captureActivityHandler = this.handler;
        if (captureActivityHandler != null) {
            captureActivityHandler.quitSynchronously();
            this.handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() throws IllegalStateException, Resources.NotFoundException, IOException, IllegalArgumentException {
        super.onResume();
        if (this.hasSurface) {
            initCamera(this.surfaceHolder);
        } else {
            this.surfaceHolder.addCallback(this);
            this.surfaceHolder.setType(3);
        }
        this.decodeFormats = null;
        this.characterSet = null;
        this.playBeep = true;
        FragmentActivity activity = getActivity();
        getActivity();
        if (((AudioManager) activity.getSystemService("audio")).getRingerMode() != 2) {
            this.playBeep = false;
        }
        initBeepSound();
        this.vibrate = true;
    }

    public void setAnalyzeCallback(CodeUtils.AnalyzeCallback analyzeCallback) {
        this.analyzeCallback = analyzeCallback;
    }

    public void setCameraInitCallBack(CameraInitCallBack cameraInitCallBack) {
        this.callBack = cameraInitCallBack;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (this.hasSurface) {
            return;
        }
        this.hasSurface = true;
        initCamera(surfaceHolder);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.hasSurface = false;
        Camera camera = this.camera;
        if (camera == null || camera == null || !CameraManager.get().isPreviewing()) {
            return;
        }
        if (!CameraManager.get().isUseOneShotPreviewCallback()) {
            this.camera.setPreviewCallback(null);
        }
        this.camera.stopPreview();
        CameraManager.get().getPreviewCallback().setHandler(null, 0);
        CameraManager.get().getAutoFocusCallback().setHandler(null, 0);
        CameraManager.get().setPreviewing(false);
    }
}
