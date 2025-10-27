package com.aliyun.player.alivcplayerexpand.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import com.aliyun.player.alivcplayerexpand.widget.IRenderView;

/* loaded from: classes2.dex */
public class TextureRenderView extends TextureView implements IRenderView, TextureView.SurfaceTextureListener {
    private IRenderView.IRenderCallback mRenderCallback;
    private Surface mSurface;

    public TextureRenderView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Log.e("AliLivePlayerView", "init: TextureRenderView");
        setSurfaceTextureListener(this);
    }

    @Override // com.aliyun.player.alivcplayerexpand.widget.IRenderView
    public void addRenderCallback(IRenderView.IRenderCallback iRenderCallback) {
        this.mRenderCallback = iRenderCallback;
    }

    @Override // com.aliyun.player.alivcplayerexpand.widget.IRenderView
    public View getView() {
        return this;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
        Surface surface = new Surface(surfaceTexture);
        this.mSurface = surface;
        IRenderView.IRenderCallback iRenderCallback = this.mRenderCallback;
        if (iRenderCallback != null) {
            iRenderCallback.onSurfaceCreate(surface);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mSurface.release();
        IRenderView.IRenderCallback iRenderCallback = this.mRenderCallback;
        if (iRenderCallback == null) {
            return false;
        }
        iRenderCallback.onSurfaceDestroyed();
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
        IRenderView.IRenderCallback iRenderCallback = this.mRenderCallback;
        if (iRenderCallback != null) {
            iRenderCallback.onSurfaceChanged(i2, i3);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public TextureRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public TextureRenderView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init(context);
    }
}
