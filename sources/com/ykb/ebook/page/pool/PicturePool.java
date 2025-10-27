package com.ykb.ebook.page.pool;

import android.graphics.Picture;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0002H\u0016¨\u0006\u0005"}, d2 = {"Lcom/ykb/ebook/page/pool/PicturePool;", "Lcom/ykb/ebook/page/pool/BaseObjectPool;", "Landroid/graphics/Picture;", "()V", "create", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class PicturePool extends BaseObjectPool<Picture> {
    public PicturePool() {
        super(64);
    }

    @Override // com.ykb.ebook.page.pool.ObjectPool
    @NotNull
    public Picture create() {
        return new Picture();
    }
}
