package com.lxj.easyadapter;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J%\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\tH&¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\tH&J\u001d\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\tH&¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"Lcom/lxj/easyadapter/ItemDelegate;", ExifInterface.GPS_DIRECTION_TRUE, "", "bind", "", "holder", "Lcom/lxj/easyadapter/ViewHolder;", "t", "position", "", "(Lcom/lxj/easyadapter/ViewHolder;Ljava/lang/Object;I)V", "getLayoutId", "isThisType", "", "item", "(Ljava/lang/Object;I)Z", "easy-adapter_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public interface ItemDelegate<T> {
    void bind(@NotNull ViewHolder holder, T t2, int position);

    int getLayoutId();

    boolean isThisType(T item, int position);
}
