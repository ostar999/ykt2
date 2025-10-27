package pl.droidsonroids.gif;

import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import pl.droidsonroids.gif.GifTextureView;

/* loaded from: classes9.dex */
class PlaceholderDrawingSurfaceTextureListener implements TextureView.SurfaceTextureListener {
    private final GifTextureView.PlaceholderDrawListener mDrawer;

    public PlaceholderDrawingSurfaceTextureListener(GifTextureView.PlaceholderDrawListener placeholderDrawListener) {
        this.mDrawer = placeholderDrawListener;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) throws Surface.OutOfResourcesException, IllegalArgumentException {
        Surface surface = new Surface(surfaceTexture);
        Canvas canvasLockCanvas = surface.lockCanvas(null);
        this.mDrawer.onDrawPlaceholder(canvasLockCanvas);
        surface.unlockCanvasAndPost(canvasLockCanvas);
        surface.release();
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
}
