package com.lxj.easyadapter;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J%\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u00002\u0006\u0010\u0011\u001a\u00020\u0006H$¢\u0006\u0002\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Lcom/lxj/easyadapter/EasyAdapter;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/lxj/easyadapter/MultiItemTypeAdapter;", "data", "", "mLayoutId", "", "(Ljava/util/List;I)V", "getMLayoutId", "()I", "setMLayoutId", "(I)V", "bind", "", "holder", "Lcom/lxj/easyadapter/ViewHolder;", "t", "position", "(Lcom/lxj/easyadapter/ViewHolder;Ljava/lang/Object;I)V", "easy-adapter_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public abstract class EasyAdapter<T> extends MultiItemTypeAdapter<T> {
    private int mLayoutId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EasyAdapter(@NotNull List<? extends T> data, int i2) {
        super(data);
        Intrinsics.checkParameterIsNotNull(data, "data");
        this.mLayoutId = i2;
        addItemDelegate(new ItemDelegate<T>() { // from class: com.lxj.easyadapter.EasyAdapter.1
            @Override // com.lxj.easyadapter.ItemDelegate
            public void bind(@NotNull ViewHolder holder, T t2, int position) {
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                EasyAdapter.this.bind(holder, t2, position);
            }

            @Override // com.lxj.easyadapter.ItemDelegate
            public int getLayoutId() {
                return EasyAdapter.this.getMLayoutId();
            }

            @Override // com.lxj.easyadapter.ItemDelegate
            public boolean isThisType(T item, int position) {
                return true;
            }
        });
    }

    public abstract void bind(@NotNull ViewHolder holder, T t2, int position);

    public final int getMLayoutId() {
        return this.mLayoutId;
    }

    public final void setMLayoutId(int i2) {
        this.mLayoutId = i2;
    }
}
