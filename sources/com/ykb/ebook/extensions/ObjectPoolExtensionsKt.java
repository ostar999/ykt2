package com.ykb.ebook.extensions;

import androidx.exifinterface.media.ExifInterface;
import com.ykb.ebook.page.pool.ObjectPool;
import com.ykb.ebook.page.pool.ObjectPoolLocked;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001¨\u0006\u0003"}, d2 = {"synchronized", "Lcom/ykb/ebook/page/pool/ObjectPool;", ExifInterface.GPS_DIRECTION_TRUE, "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ObjectPoolExtensionsKt {
    @NotNull
    /* renamed from: synchronized, reason: not valid java name */
    public static final <T> ObjectPool<T> m775synchronized(@NotNull ObjectPool<T> objectPool) {
        Intrinsics.checkNotNullParameter(objectPool, "<this>");
        return new ObjectPoolLocked(objectPool);
    }
}
