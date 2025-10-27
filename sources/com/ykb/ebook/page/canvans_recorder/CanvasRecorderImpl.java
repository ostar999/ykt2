package com.ykb.ebook.page.canvans_recorder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.ykb.ebook.page.pool.BitmapPool;
import com.ykb.ebook.page.pool.CanvasPool;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u001a\u001a\u00020\u0019H\u0016J\u0018\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u001c\u001a\u00020\u0019H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012¨\u0006\u001e"}, d2 = {"Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorderImpl;", "Lcom/ykb/ebook/page/canvans_recorder/BaseCanvasRecorder;", "()V", "bitmap", "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "canvas", "Landroid/graphics/Canvas;", "getCanvas", "()Landroid/graphics/Canvas;", "setCanvas", "(Landroid/graphics/Canvas;)V", "height", "", "getHeight", "()I", "width", "getWidth", "beginRecording", "canReconfigure", "", "draw", "", "endRecording", "init", "recycle", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCanvasRecorderImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CanvasRecorderImpl.kt\ncom/ykb/ebook/page/canvans_recorder/CanvasRecorderImpl\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,71:1\n1#2:72\n*E\n"})
/* loaded from: classes7.dex */
public final class CanvasRecorderImpl extends BaseCanvasRecorder {

    @NotNull
    private static final CanvasPool canvasPool = new CanvasPool(2);

    @Nullable
    private Bitmap bitmap;

    @Nullable
    private Canvas canvas;

    private final boolean canReconfigure(int width, int height) {
        Bitmap bitmap = this.bitmap;
        Intrinsics.checkNotNull(bitmap);
        return bitmap.getAllocationByteCount() >= (width * height) * 4;
    }

    private final void init(int width, int height) {
        if (width <= 0 || height <= 0) {
            return;
        }
        if (this.bitmap == null) {
            this.bitmap = BitmapPool.INSTANCE.obtain(width, height);
        }
        Bitmap bitmap = this.bitmap;
        Intrinsics.checkNotNull(bitmap);
        if (bitmap.getWidth() == width) {
            Bitmap bitmap2 = this.bitmap;
            Intrinsics.checkNotNull(bitmap2);
            if (bitmap2.getHeight() == height) {
                return;
            }
        }
        if (canReconfigure(width, height)) {
            Bitmap bitmap3 = this.bitmap;
            Intrinsics.checkNotNull(bitmap3);
            bitmap3.reconfigure(width, height, Bitmap.Config.ARGB_8888);
        } else {
            BitmapPool bitmapPool = BitmapPool.INSTANCE;
            Bitmap bitmap4 = this.bitmap;
            Intrinsics.checkNotNull(bitmap4);
            bitmapPool.recycle(bitmap4);
            this.bitmap = bitmapPool.obtain(width, height);
        }
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    @NotNull
    public Canvas beginRecording(int width, int height) {
        init(width, height);
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.eraseColor(0);
        }
        Canvas canvasObtain = canvasPool.obtain();
        canvasObtain.setBitmap(this.bitmap);
        this.canvas = canvasObtain;
        Intrinsics.checkNotNull(canvasObtain);
        return canvasObtain;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void draw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Bitmap bitmap = this.bitmap;
        if (bitmap == null) {
            return;
        }
        Intrinsics.checkNotNull(bitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
    }

    @Override // com.ykb.ebook.page.canvans_recorder.BaseCanvasRecorder, com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void endRecording() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.prepareToDraw();
        }
        super.endRecording();
        CanvasPool canvasPool2 = canvasPool;
        Canvas canvas = this.canvas;
        Intrinsics.checkNotNull(canvas);
        canvasPool2.recycle(canvas);
        this.canvas = null;
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    @Nullable
    public final Canvas getCanvas() {
        return this.canvas;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public int getHeight() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            return bitmap.getHeight();
        }
        return -1;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public int getWidth() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            return bitmap.getWidth();
        }
        return -1;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.BaseCanvasRecorder, com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void recycle() {
        super.recycle();
        Bitmap bitmap = this.bitmap;
        if (bitmap == null) {
            return;
        }
        BitmapPool.INSTANCE.recycle(bitmap);
        this.bitmap = null;
    }

    public final void setBitmap(@Nullable Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public final void setCanvas(@Nullable Canvas canvas) {
        this.canvas = canvas;
    }
}
