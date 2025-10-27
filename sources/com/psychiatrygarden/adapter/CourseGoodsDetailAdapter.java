package com.psychiatrygarden.adapter;

import androidx.lifecycle.LifecycleOwner;
import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.psychiatrygarden.bean.GoodsDetailItem;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001e\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/psychiatrygarden/adapter/CourseGoodsDetailAdapter;", "Lcom/chad/library/adapter/base/BaseProviderMultiAdapter;", "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "owner", "Landroidx/lifecycle/LifecycleOwner;", "(Landroidx/lifecycle/LifecycleOwner;)V", "getItemType", "", "data", "", "position", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseGoodsDetailAdapter extends BaseProviderMultiAdapter<GoodsDetailItem> {

    @NotNull
    private final LifecycleOwner owner;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CourseGoodsDetailAdapter(@NotNull LifecycleOwner owner) {
        super(null, 1, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.owner = owner;
        addItemProvider(new TopDetailProvider(owner));
        addItemProvider(new TeacherDetailProvider());
        addItemProvider(new CourseDetailProvider());
        addItemProvider(new CourseDirectoryProvider());
        addItemProvider(new CourseStructureProvider());
        addItemProvider(new CourseCommentProvider());
    }

    @Override // com.chad.library.adapter.base.BaseProviderMultiAdapter
    public int getItemType(@NotNull List<? extends GoodsDetailItem> data, int position) {
        Intrinsics.checkNotNullParameter(data, "data");
        return data.get(position).getItemType();
    }
}
