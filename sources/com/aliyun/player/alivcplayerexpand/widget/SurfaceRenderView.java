package com.aliyun.player.alivcplayerexpand.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.aliyun.player.alivcplayerexpand.widget.IRenderView;

/* loaded from: classes2.dex */
public class SurfaceRenderView extends SurfaceView implements IRenderView, SurfaceHolder.Callback {
    private IRenderView.IRenderCallback mRenderCallback;

    public SurfaceRenderView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setSecure(false);
        Log.e("AliLivePlayerView", "init: SurfaceRenderView");
        getHolder().addCallback(this);
    }

    @Override // com.aliyun.player.alivcplayerexpand.widget.IRenderView
    public void addRenderCallback(IRenderView.IRenderCallback iRenderCallback) {
        this.mRenderCallback = iRenderCallback;
    }

    @Override // com.aliyun.player.alivcplayerexpand.widget.IRenderView
    public View getView() {
        return this;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
        IRenderView.IRenderCallback iRenderCallback = this.mRenderCallback;
        if (iRenderCallback != null) {
            iRenderCallback.onSurfaceChanged(i3, i4);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        IRenderView.IRenderCallback iRenderCallback = this.mRenderCallback;
        if (iRenderCallback != null) {
            iRenderCallback.onSurfaceCreate(surfaceHolder.getSurface());
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Surface surface = surfaceHolder.getSurface();
        if (surface != null) {
            surface.release();
        }
        IRenderView.IRenderCallback iRenderCallback = this.mRenderCallback;
        if (iRenderCallback != null) {
            iRenderCallback.onSurfaceDestroyed();
        }
    }

    public SurfaceRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public SurfaceRenderView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init(context);
    }
}
