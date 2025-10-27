package com.ykb.ebook.page.pool;

import android.graphics.Paint;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\b\u0010\u0005\u001a\u00020\u0002H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/ykb/ebook/page/pool/PaintPool;", "Lcom/ykb/ebook/page/pool/BaseSafeObjectPool;", "Landroid/graphics/Paint;", "()V", "emptyPaint", "create", "recycle", "", TypedValues.AttributesType.S_TARGET, "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class PaintPool extends BaseSafeObjectPool<Paint> {

    @NotNull
    public static final PaintPool INSTANCE = new PaintPool();

    @NotNull
    private static final Paint emptyPaint = new Paint();

    private PaintPool() {
        super(8);
    }

    @Override // com.ykb.ebook.page.pool.ObjectPool
    @NotNull
    public Paint create() {
        return new Paint();
    }

    @Override // com.ykb.ebook.page.pool.BaseObjectPool, com.ykb.ebook.page.pool.ObjectPool
    public void recycle(@NotNull Paint target) {
        Intrinsics.checkNotNullParameter(target, "target");
        target.set(emptyPaint);
        super.recycle((PaintPool) target);
    }
}
