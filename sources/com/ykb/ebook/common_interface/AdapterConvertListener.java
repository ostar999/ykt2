package com.ykb.ebook.common_interface;

import com.tencent.rtmp.sharp.jni.QLog;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J%\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u0000H&¢\u0006\u0002\u0010\nJ3\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u00002\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\fH&¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lcom/ykb/ebook/common_interface/AdapterConvertListener;", QLog.TAG_REPORTLEVEL_DEVELOPER, "", "convert", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "", "item", "(Lcom/ykb/ebook/adapter/base/QuickViewHolder;ILjava/lang/Object;)V", "payloads", "", "(Lcom/ykb/ebook/adapter/base/QuickViewHolder;ILjava/lang/Object;Ljava/util/List;)V", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface AdapterConvertListener<D> {
    void convert(@NotNull QuickViewHolder holder, int position, D item);

    void convert(@NotNull QuickViewHolder holder, int position, D item, @NotNull List<? extends Object> payloads);
}
