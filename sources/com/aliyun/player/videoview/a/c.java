package com.aliyun.player.videoview.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import com.aliyun.player.IPlayer;
import com.aliyun.player.videoview.a.a;
import com.cicada.player.utils.Logger;

/* loaded from: classes2.dex */
public class c extends com.aliyun.player.videoview.a.a {

    /* renamed from: l, reason: collision with root package name */
    private static final String f3567l = "AliDisplayView_" + c.class.getSimpleName();

    /* renamed from: m, reason: collision with root package name */
    private TextureView f3568m;

    /* renamed from: n, reason: collision with root package name */
    private SurfaceTexture f3569n;

    /* renamed from: o, reason: collision with root package name */
    private Surface f3570o;

    /* renamed from: p, reason: collision with root package name */
    private boolean f3571p;

    public class a implements TextureView.SurfaceTextureListener {
        public a() {
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x006d  */
        /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
        @Override // android.view.TextureView.SurfaceTextureListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onSurfaceTextureAvailable(android.graphics.SurfaceTexture r2, int r3, int r4) {
            /*
                r1 = this;
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                android.graphics.SurfaceTexture r3 = com.aliyun.player.videoview.a.c.a(r3)
                if (r3 != 0) goto L18
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                com.aliyun.player.videoview.a.c.a(r3, r2)
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                android.view.Surface r4 = new android.view.Surface
                r4.<init>(r2)
            L14:
                com.aliyun.player.videoview.a.c.a(r3, r4)
                goto L46
            L18:
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                boolean r3 = com.aliyun.player.videoview.a.c.c(r3)
                if (r3 == 0) goto L30
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                android.view.TextureView r3 = com.aliyun.player.videoview.a.c.d(r3)
                com.aliyun.player.videoview.a.c r4 = com.aliyun.player.videoview.a.c.this
                android.graphics.SurfaceTexture r4 = com.aliyun.player.videoview.a.c.a(r4)
                r3.setSurfaceTexture(r4)
                goto L46
            L30:
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                android.view.Surface r3 = com.aliyun.player.videoview.a.c.b(r3)
                r3.release()
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                com.aliyun.player.videoview.a.c.a(r3, r2)
                com.aliyun.player.videoview.a.c r3 = com.aliyun.player.videoview.a.c.this
                android.view.Surface r4 = new android.view.Surface
                r4.<init>(r2)
                goto L14
            L46:
                java.lang.String r3 = com.aliyun.player.videoview.a.c.h()
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                com.aliyun.player.videoview.a.c r0 = com.aliyun.player.videoview.a.c.this
                android.view.TextureView r0 = com.aliyun.player.videoview.a.c.d(r0)
                r4.append(r0)
                java.lang.String r0 = " onSurfaceTextureAvailable  "
                r4.append(r0)
                r4.append(r2)
                java.lang.String r2 = r4.toString()
                com.cicada.player.utils.Logger.i(r3, r2)
                com.aliyun.player.videoview.a.c r2 = com.aliyun.player.videoview.a.c.this
                com.aliyun.player.videoview.a.a$h r3 = r2.f3547c
                if (r3 == 0) goto L74
                android.view.Surface r2 = com.aliyun.player.videoview.a.c.b(r2)
                r3.onSurfaceCreated(r2)
            L74:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aliyun.player.videoview.a.c.a.onSurfaceTextureAvailable(android.graphics.SurfaceTexture, int, int):void");
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            Logger.i(c.f3567l, c.this.f3568m + " onSurfaceTextureDestroyed  ");
            a.h hVar = c.this.f3547c;
            if (hVar == null) {
                return false;
            }
            hVar.onSurfaceDestroy();
            return false;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
            Logger.i(c.f3567l, c.this.f3568m + " onSurfaceTextureSizeChanged  ");
            a.h hVar = c.this.f3547c;
            if (hVar != null) {
                hVar.onSurfaceSizeChanged();
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    }

    public c(ViewGroup viewGroup) {
        super(viewGroup);
        this.f3568m = null;
        this.f3569n = null;
        this.f3570o = null;
        this.f3571p = true;
    }

    private Bitmap a(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preRotate(this.f3568m.getRotation());
        matrix.preScale(this.f3568m.getScaleX(), this.f3568m.getScaleY());
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override // com.aliyun.player.videoview.a.a
    public View a(Context context) {
        TextureView textureView = new TextureView(context);
        this.f3568m = textureView;
        textureView.setSurfaceTextureListener(new a());
        return this.f3568m;
    }

    @Override // com.aliyun.player.videoview.a.a
    public boolean a(IPlayer.MirrorMode mirrorMode) {
        if (mirrorMode == IPlayer.MirrorMode.MIRROR_MODE_HORIZONTAL) {
            this.f3568m.setScaleX(-1.0f);
            this.f3568m.setScaleY(1.0f);
            return true;
        }
        if (mirrorMode == IPlayer.MirrorMode.MIRROR_MODE_VERTICAL) {
            this.f3568m.setScaleY(-1.0f);
        } else {
            this.f3568m.setScaleY(1.0f);
        }
        this.f3568m.setScaleX(1.0f);
        return true;
    }

    @Override // com.aliyun.player.videoview.a.a
    public boolean a(IPlayer.RotateMode rotateMode) {
        TextureView textureView;
        float f2;
        if (rotateMode == IPlayer.RotateMode.ROTATE_90) {
            textureView = this.f3568m;
            f2 = 90.0f;
        } else if (rotateMode == IPlayer.RotateMode.ROTATE_180) {
            textureView = this.f3568m;
            f2 = 180.0f;
        } else if (rotateMode == IPlayer.RotateMode.ROTATE_270) {
            textureView = this.f3568m;
            f2 = 270.0f;
        } else {
            textureView = this.f3568m;
            f2 = 0.0f;
        }
        textureView.setRotation(f2);
        return true;
    }

    @Override // com.aliyun.player.videoview.a.a
    public void b(boolean z2) {
        this.f3571p = z2;
    }

    @Override // com.aliyun.player.videoview.a.a
    public Bitmap e() {
        Bitmap bitmap = this.f3568m.getBitmap();
        Bitmap bitmapA = a(bitmap);
        bitmap.recycle();
        return bitmapA;
    }
}
