package com.aliyun.player.videoview.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import com.aliyun.player.IPlayer;
import com.aliyun.player.videoview.a.a;
import com.cicada.player.utils.Logger;

/* loaded from: classes2.dex */
public class b extends com.aliyun.player.videoview.a.a {

    /* renamed from: l, reason: collision with root package name */
    private static final String f3564l = "AliDisplayView_" + b.class.getSimpleName();

    /* renamed from: m, reason: collision with root package name */
    private SurfaceView f3565m;

    public class a implements SurfaceHolder.Callback2 {
        public a() {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
            Logger.i(b.f3564l, "surfaceChanged ");
            a.h hVar = b.this.f3547c;
            if (hVar != null) {
                hVar.onSurfaceSizeChanged();
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            Surface surface = surfaceHolder.getSurface();
            Logger.i(b.f3564l, "onSurfaceCreated  " + surface);
            a.h hVar = b.this.f3547c;
            if (hVar != null) {
                hVar.onSurfaceCreated(surface);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            Logger.i(b.f3564l, "surfaceDestroyed ");
            a.h hVar = b.this.f3547c;
            if (hVar != null) {
                hVar.onSurfaceDestroy();
            }
        }

        @Override // android.view.SurfaceHolder.Callback2
        public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            Logger.i(b.f3564l, "surfaceRedrawNeeded ");
        }
    }

    public b(ViewGroup viewGroup) {
        super(viewGroup);
        this.f3565m = null;
    }

    @Override // com.aliyun.player.videoview.a.a
    public View a(Context context) {
        SurfaceView surfaceView = new SurfaceView(context);
        this.f3565m = surfaceView;
        surfaceView.getHolder().addCallback(new a());
        return this.f3565m;
    }

    @Override // com.aliyun.player.videoview.a.a
    public boolean a(IPlayer.MirrorMode mirrorMode) {
        return false;
    }

    @Override // com.aliyun.player.videoview.a.a
    public boolean a(IPlayer.RotateMode rotateMode) {
        return false;
    }

    @Override // com.aliyun.player.videoview.a.a
    public void b(boolean z2) {
    }

    @Override // com.aliyun.player.videoview.a.a
    public Bitmap e() {
        return null;
    }
}
