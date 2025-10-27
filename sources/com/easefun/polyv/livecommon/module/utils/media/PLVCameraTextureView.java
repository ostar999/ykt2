package com.easefun.polyv.livecommon.module.utils.media;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.TextureView;
import com.easefun.polyv.livecommon.module.utils.media.PLVCameraConfiguration;
import com.easefun.polyv.livecommon.module.utils.media.PLVCameraHolder;
import com.easefun.polyv.livecommon.module.utils.media.exception.PLVCameraDisabledException;
import com.easefun.polyv.livecommon.module.utils.media.exception.PLVCameraHardwareException;
import com.easefun.polyv.livecommon.module.utils.media.exception.PLVCameraNotSupportException;
import com.easefun.polyv.livecommon.module.utils.media.exception.PLVNoCameraException;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import java.io.IOException;

/* loaded from: classes3.dex */
public class PLVCameraTextureView extends TextureView implements TextureView.SurfaceTextureListener {
    private boolean curCameraFront;
    private boolean isCallCameraStart;
    private boolean isSurfaceTextureAvailable;
    private PLVCameraListener mCameraOpenListener;
    private Handler mHandler;

    public PLVCameraTextureView(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postOpenCameraError(final Throwable t2, final int error) {
        if (this.mCameraOpenListener != null) {
            this.mHandler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.media.PLVCameraTextureView.3
                @Override // java.lang.Runnable
                public void run() {
                    if (PLVCameraTextureView.this.mCameraOpenListener != null) {
                        PLVCameraTextureView.this.mCameraOpenListener.onOpenFail(t2, error);
                    }
                }
            });
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.isSurfaceTextureAvailable = true;
        if (this.isCallCameraStart) {
            startCamera();
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    public void release() {
        this.mHandler.removeCallbacksAndMessages(null);
        PLVCameraHolder.instance().releaseCamera();
        PLVCameraHolder.instance().release();
    }

    public void setCameraOpenListener(PLVCameraListener cameraOpenListener) {
        this.mCameraOpenListener = cameraOpenListener;
    }

    public void startCamera() {
        if (this.isSurfaceTextureAvailable) {
            this.mHandler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.media.PLVCameraTextureView.2
                @Override // java.lang.Runnable
                public void run() throws IOException {
                    try {
                        PLVCameraUtils.checkCameraService(PLVCameraTextureView.this.getContext());
                        PLVCameraHolder.instance().setConfiguration(new PLVCameraConfiguration.Builder().setOrientation(ScreenUtils.isLandscape() ? PLVCameraConfiguration.Orientation.LANDSCAPE : PLVCameraConfiguration.Orientation.PORTRAIT).setPreview(PLVCameraTextureView.this.getHeight(), PLVCameraTextureView.this.getWidth()).build());
                        PLVCameraHolder.State state = PLVCameraHolder.instance().getState();
                        PLVCameraHolder.instance().setSurfaceTexture(PLVCameraTextureView.this.getSurfaceTexture());
                        if (state != PLVCameraHolder.State.PREVIEW) {
                            try {
                                PLVCameraHolder.instance().openCamera();
                                PLVCameraHolder.instance().startPreview();
                                if (PLVCameraTextureView.this.mCameraOpenListener != null) {
                                    PLVCameraTextureView.this.mHandler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.media.PLVCameraTextureView.2.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            PLVCameraTextureView.this.mCameraOpenListener.onOpenSuccess();
                                        }
                                    });
                                }
                            } catch (PLVCameraHardwareException e2) {
                                e2.printStackTrace();
                                PLVCameraTextureView.this.postOpenCameraError(e2, 4);
                            } catch (PLVCameraNotSupportException e3) {
                                e3.printStackTrace();
                                PLVCameraTextureView.this.postOpenCameraError(e3, 1);
                            }
                        }
                    } catch (PLVCameraDisabledException e4) {
                        PLVCameraTextureView.this.postOpenCameraError(e4, 3);
                        e4.printStackTrace();
                    } catch (PLVNoCameraException e5) {
                        PLVCameraTextureView.this.postOpenCameraError(e5, 2);
                        e5.printStackTrace();
                    }
                }
            });
        } else {
            this.isCallCameraStart = true;
        }
    }

    public void startPreview() {
        PLVCameraHolder.instance().startPreview();
    }

    public void stopPreview() {
        PLVCameraHolder.instance().stopPreview();
    }

    public boolean switchCamera(boolean front) {
        return switchCamera(front, null);
    }

    public PLVCameraTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public boolean switchCamera(boolean front, Runnable runnable) {
        if (this.curCameraFront == front || !PLVCameraHolder.instance().switchCamera(runnable)) {
            return false;
        }
        this.curCameraFront = front;
        if (this.mCameraOpenListener == null) {
            return true;
        }
        this.mHandler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.media.PLVCameraTextureView.1
            @Override // java.lang.Runnable
            public void run() {
                PLVCameraTextureView.this.mCameraOpenListener.onCameraChange();
            }
        });
        return true;
    }

    public PLVCameraTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.curCameraFront = true;
        this.mHandler = new Handler(Looper.getMainLooper());
        setSurfaceTextureListener(this);
    }
}
