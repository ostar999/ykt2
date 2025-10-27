package io.agora.rtc.video;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Process;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public class ViESurfaceRenderer implements SurfaceHolder.Callback {
    private static final String TAG = "ViESurfaceRenderer";
    private SurfaceHolder surfaceHolder;
    private Bitmap bitmap = null;
    private ByteBuffer byteBuffer = null;
    private Rect source = new Rect();
    private Rect dest = new Rect();
    private float topScale = 0.0f;
    private float bottomScale = 1.0f;
    private float leftScale = 0.0f;
    private float rightScale = 1.0f;

    public ViESurfaceRenderer(SurfaceView view) {
        Log.i(TAG, "surface view " + view);
        SurfaceHolder holder = view.getHolder();
        this.surfaceHolder = holder;
        if (holder == null) {
            return;
        }
        holder.addCallback(this);
        surfaceCreated(this.surfaceHolder);
    }

    private void changeDestRect(int dstWidth, int dstHeight) {
        this.dest.right = (int) (r0.left + (Math.abs(this.leftScale - this.rightScale) * dstWidth));
        this.dest.bottom = (int) (r0.top + (Math.abs(this.topScale - this.bottomScale) * dstHeight));
        Log.i(TAG, "ViESurfaceRender::surfaceChanged in_width:" + dstWidth + " in_height:" + dstHeight + " source.left:" + this.source.left + " source.top:" + this.source.top + " source.dest:" + this.source.right + " source.bottom:" + this.source.bottom + " dest.left:" + this.dest.left + " dest.top:" + this.dest.top + " dest.dest:" + this.dest.right + " dest.bottom:" + this.dest.bottom + " dest scale " + this.rightScale + " bottom scale " + this.bottomScale);
    }

    private void saveBitmapToJPEG(int width, int height) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(String.format("/sdcard/render_%d.jpg", Long.valueOf(System.currentTimeMillis())));
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
            Log.i(TAG, "saved jpg " + fileOutputStream.toString());
        } catch (IOException e2) {
            Log.w(TAG, "save jpg failed", e2);
        }
    }

    public Bitmap CreateBitmap(int width, int height) throws SecurityException, IllegalArgumentException {
        Log.d(TAG, "CreateByteBitmap " + width + ":" + height);
        if (this.bitmap == null) {
            try {
                Process.setThreadPriority(-4);
            } catch (Exception unused) {
            }
        }
        changeDestRect(width, height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        this.bitmap = bitmapCreateBitmap;
        Rect rect = this.source;
        rect.left = 0;
        rect.top = 0;
        rect.bottom = height;
        rect.right = width;
        return bitmapCreateBitmap;
    }

    public ByteBuffer CreateByteBuffer(int width, int height) {
        Log.i(TAG, "CreateByteBuffer " + width + " * " + height);
        this.bitmap = CreateBitmap(width, height);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(width * height * 2);
        this.byteBuffer = byteBufferAllocateDirect;
        return byteBufferAllocateDirect;
    }

    public void DrawBitmap() {
        Canvas canvasLockCanvas;
        if (this.bitmap == null || (canvasLockCanvas = this.surfaceHolder.lockCanvas()) == null) {
            return;
        }
        canvasLockCanvas.drawBitmap(this.bitmap, this.source, this.dest, (Paint) null);
        this.surfaceHolder.unlockCanvasAndPost(canvasLockCanvas);
    }

    public void DrawByteBuffer() {
        ByteBuffer byteBuffer = this.byteBuffer;
        if (byteBuffer == null) {
            Log.w(TAG, "DrawByteBuffer null");
            return;
        }
        byteBuffer.rewind();
        this.bitmap.copyPixelsFromBuffer(this.byteBuffer);
        DrawBitmap();
    }

    public void SetCoordinates(float left, float top2, float right, float bottom) {
        Log.i(TAG, "SetCoordinates " + left + "," + top2 + " : " + right + "," + bottom);
        this.leftScale = left;
        this.topScale = top2;
        this.rightScale = right;
        this.bottomScale = bottom;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int in_width, int in_height) {
        changeDestRect(in_width, in_height);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvasLockCanvas = this.surfaceHolder.lockCanvas();
        if (canvasLockCanvas != null) {
            Rect surfaceFrame = this.surfaceHolder.getSurfaceFrame();
            if (surfaceFrame != null) {
                changeDestRect(surfaceFrame.right - surfaceFrame.left, surfaceFrame.bottom - surfaceFrame.top);
                Log.i(TAG, "ViESurfaceRender::surfaceCreated dst.left:" + surfaceFrame.left + " dst.top:" + surfaceFrame.top + " dst.dest:" + surfaceFrame.right + " dst.bottom:" + surfaceFrame.bottom + " source.left:" + this.source.left + " source.top:" + this.source.top + " source.dest:" + this.source.right + " source.bottom:" + this.source.bottom + " dest.left:" + this.dest.left + " dest.top:" + this.dest.top + " dest.dest:" + this.dest.right + " dest.bottom:" + this.dest.bottom);
            }
            this.surfaceHolder.unlockCanvasAndPost(canvasLockCanvas);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "ViESurfaceRenderer::surfaceDestroyed");
        this.bitmap = null;
        this.byteBuffer = null;
    }
}
