package com.ykb.ebook.page.pool;

import android.graphics.RenderNode;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@RequiresApi(29)
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0002H\u0016J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0002H\u0016¨\u0006\b"}, d2 = {"Lcom/ykb/ebook/page/pool/RenderNodePool;", "Lcom/ykb/ebook/page/pool/BaseObjectPool;", "Landroid/graphics/RenderNode;", "()V", "create", "recycle", "", TypedValues.AttributesType.S_TARGET, "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class RenderNodePool extends BaseObjectPool<RenderNode> {
    public RenderNodePool() {
        super(64);
    }

    @Override // com.ykb.ebook.page.pool.ObjectPool
    @NotNull
    public RenderNode create() {
        return new RenderNode("CanvasRecorder");
    }

    @Override // com.ykb.ebook.page.pool.BaseObjectPool, com.ykb.ebook.page.pool.ObjectPool
    public void recycle(@NotNull RenderNode target) {
        Intrinsics.checkNotNullParameter(target, "target");
        target.discardDisplayList();
        super.recycle((RenderNodePool) target);
    }
}
