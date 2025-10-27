package com.ykb.ebook.page.canvans_recorder;

import android.graphics.Canvas;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0011H\u0016J\b\u0010\u0015\u001a\u00020\u0013H\u0016J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\t\u0010\u0017\u001a\u00020\u0013H\u0096\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u0096\u0001J\b\u0010\u001a\u001a\u00020\u0019H\u0016J\t\u0010\u001b\u001a\u00020\u0019H\u0096\u0001J\b\u0010\u001c\u001a\u00020\u0013H\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0007¨\u0006\u001d"}, d2 = {"Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorderLocked;", "Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "delegate", "(Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;)V", "height", "", "getHeight", "()I", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", "setLock", "(Ljava/util/concurrent/locks/ReentrantLock;)V", "width", "getWidth", "beginRecording", "Landroid/graphics/Canvas;", "draw", "", "canvas", "endRecording", "initLock", "invalidate", "isDirty", "", "isLocked", "needRecord", "recycle", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class CanvasRecorderLocked implements CanvasRecorder {

    @NotNull
    private final CanvasRecorder delegate;

    @Nullable
    private ReentrantLock lock;

    public CanvasRecorderLocked(@NotNull CanvasRecorder delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
        this.lock = new ReentrantLock();
    }

    private final void initLock() {
        if (this.lock == null) {
            synchronized (this) {
                if (this.lock == null) {
                    this.lock = new ReentrantLock();
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    @NotNull
    public Canvas beginRecording(int width, int height) {
        initLock();
        ReentrantLock reentrantLock = this.lock;
        Intrinsics.checkNotNull(reentrantLock);
        reentrantLock.lock();
        return this.delegate.beginRecording(width, height);
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void draw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        ReentrantLock reentrantLock = this.lock;
        if (reentrantLock == null) {
            return;
        }
        Intrinsics.checkNotNull(reentrantLock);
        reentrantLock.lock();
        try {
            this.delegate.draw(canvas);
        } finally {
            ReentrantLock reentrantLock2 = this.lock;
            Intrinsics.checkNotNull(reentrantLock2);
            reentrantLock2.unlock();
        }
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void endRecording() {
        this.delegate.endRecording();
        ReentrantLock reentrantLock = this.lock;
        Intrinsics.checkNotNull(reentrantLock);
        reentrantLock.unlock();
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public int getHeight() {
        return this.delegate.getHeight();
    }

    @Nullable
    public final ReentrantLock getLock() {
        return this.lock;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public int getWidth() {
        return this.delegate.getWidth();
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void invalidate() {
        this.delegate.invalidate();
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public boolean isDirty() {
        return this.delegate.isDirty();
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public boolean isLocked() {
        ReentrantLock reentrantLock = this.lock;
        if (reentrantLock == null) {
            return false;
        }
        Intrinsics.checkNotNull(reentrantLock);
        return reentrantLock.isLocked();
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public boolean needRecord() {
        return this.delegate.needRecord();
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void recycle() {
        ReentrantLock reentrantLock = this.lock;
        if (reentrantLock == null) {
            return;
        }
        Intrinsics.checkNotNull(reentrantLock);
        reentrantLock.lock();
        try {
            this.delegate.recycle();
            ReentrantLock reentrantLock2 = this.lock;
            Intrinsics.checkNotNull(reentrantLock2);
            reentrantLock2.unlock();
            this.lock = null;
        } catch (Throwable th) {
            ReentrantLock reentrantLock3 = this.lock;
            Intrinsics.checkNotNull(reentrantLock3);
            reentrantLock3.unlock();
            throw th;
        }
    }

    public final void setLock(@Nullable ReentrantLock reentrantLock) {
        this.lock = reentrantLock;
    }
}
