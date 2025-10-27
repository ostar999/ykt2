package com.ykb.ebook.page.pool;

import android.graphics.Canvas;
import androidx.core.util.Pools;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/page/pool/CanvasPool;", "", DatabaseManager.SIZE, "", "(I)V", "pool", "Landroidx/core/util/Pools$SynchronizedPool;", "Landroid/graphics/Canvas;", "obtain", "recycle", "", "canvas", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class CanvasPool {

    @NotNull
    private final Pools.SynchronizedPool<Canvas> pool;

    public CanvasPool(int i2) {
        this.pool = new Pools.SynchronizedPool<>(i2);
    }

    @NotNull
    public final Canvas obtain() {
        Canvas canvasAcquire = this.pool.acquire();
        return canvasAcquire == null ? new Canvas() : canvasAcquire;
    }

    public final void recycle(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        canvas.setBitmap(null);
        canvas.restoreToCount(1);
        this.pool.release(canvas);
    }
}
