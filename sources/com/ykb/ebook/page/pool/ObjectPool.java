package com.ykb.ebook.page.pool;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\r\u0010\u0003\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0004J\r\u0010\u0005\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/page/pool/ObjectPool;", ExifInterface.GPS_DIRECTION_TRUE, "", "create", "()Ljava/lang/Object;", "obtain", "recycle", "", TypedValues.AttributesType.S_TARGET, "(Ljava/lang/Object;)V", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public interface ObjectPool<T> {
    T create();

    T obtain();

    void recycle(T target);
}
