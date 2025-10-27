package com.ykb.ebook.page.canvans_recorder;

import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import androidx.annotation.RequiresApi;
import com.ykb.ebook.extensions.ObjectPoolExtensionsKt;
import com.ykb.ebook.page.pool.ObjectPool;
import com.ykb.ebook.page.pool.PicturePool;
import com.ykb.ebook.page.pool.RenderNodePool;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiresApi(29)
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000eH\u0016J\b\u0010\u0012\u001a\u00020\u0010H\u0016J\b\u0010\u0013\u001a\u00020\u0010H\u0002J\b\u0010\u0014\u001a\u00020\u0010H\u0002J\b\u0010\u0015\u001a\u00020\u0010H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006¨\u0006\u0017"}, d2 = {"Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorderApi29Impl;", "Lcom/ykb/ebook/page/canvans_recorder/BaseCanvasRecorder;", "()V", "height", "", "getHeight", "()I", "picture", "Landroid/graphics/Picture;", "renderNode", "Landroid/graphics/RenderNode;", "width", "getWidth", "beginRecording", "Landroid/graphics/Canvas;", "draw", "", "canvas", "endRecording", "flushRenderNode", "init", "recycle", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class CanvasRecorderApi29Impl extends BaseCanvasRecorder {

    @NotNull
    private static final ObjectPool<Picture> picturePool = ObjectPoolExtensionsKt.m775synchronized(new PicturePool());

    @NotNull
    private static final ObjectPool<RenderNode> renderNodePool = ObjectPoolExtensionsKt.m775synchronized(new RenderNodePool());

    @Nullable
    private Picture picture;

    @Nullable
    private RenderNode renderNode;

    private final void flushRenderNode() {
        RenderNode renderNode = this.renderNode;
        Intrinsics.checkNotNull(renderNode);
        RecordingCanvas recordingCanvasBeginRecording = renderNode.beginRecording();
        Intrinsics.checkNotNullExpressionValue(recordingCanvasBeginRecording, "renderNode!!.beginRecording()");
        Picture picture = this.picture;
        Intrinsics.checkNotNull(picture);
        recordingCanvasBeginRecording.drawPicture(picture);
        RenderNode renderNode2 = this.renderNode;
        Intrinsics.checkNotNull(renderNode2);
        renderNode2.endRecording();
    }

    private final void init() {
        if (this.renderNode == null) {
            this.renderNode = renderNodePool.obtain();
        }
        if (this.picture == null) {
            this.picture = picturePool.obtain();
        }
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    @NotNull
    public Canvas beginRecording(int width, int height) {
        init();
        RenderNode renderNode = this.renderNode;
        Intrinsics.checkNotNull(renderNode);
        renderNode.setPosition(0, 0, width, height);
        Picture picture = this.picture;
        Intrinsics.checkNotNull(picture);
        Canvas canvasBeginRecording = picture.beginRecording(width, height);
        Intrinsics.checkNotNullExpressionValue(canvasBeginRecording, "picture!!.beginRecording(width, height)");
        return canvasBeginRecording;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void draw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.renderNode == null || this.picture == null) {
            return;
        }
        if (!canvas.isHardwareAccelerated()) {
            Picture picture = this.picture;
            Intrinsics.checkNotNull(picture);
            canvas.drawPicture(picture);
            return;
        }
        RenderNode renderNode = this.renderNode;
        Intrinsics.checkNotNull(renderNode);
        if (!renderNode.hasDisplayList()) {
            flushRenderNode();
        }
        RenderNode renderNode2 = this.renderNode;
        Intrinsics.checkNotNull(renderNode2);
        canvas.drawRenderNode(renderNode2);
    }

    @Override // com.ykb.ebook.page.canvans_recorder.BaseCanvasRecorder, com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void endRecording() {
        Picture picture = this.picture;
        Intrinsics.checkNotNull(picture);
        picture.endRecording();
        flushRenderNode();
        super.endRecording();
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public int getHeight() {
        RenderNode renderNode = this.renderNode;
        if (renderNode != null) {
            return renderNode.getHeight();
        }
        return -1;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public int getWidth() {
        RenderNode renderNode = this.renderNode;
        if (renderNode != null) {
            return renderNode.getWidth();
        }
        return -1;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.BaseCanvasRecorder, com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void recycle() {
        super.recycle();
        RenderNode renderNode = this.renderNode;
        if (renderNode == null || this.picture == null) {
            return;
        }
        ObjectPool<RenderNode> objectPool = renderNodePool;
        Intrinsics.checkNotNull(renderNode);
        objectPool.recycle(renderNode);
        this.renderNode = null;
        ObjectPool<Picture> objectPool2 = picturePool;
        Picture picture = this.picture;
        Intrinsics.checkNotNull(picture);
        objectPool2.recycle(picture);
        this.picture = null;
    }
}
