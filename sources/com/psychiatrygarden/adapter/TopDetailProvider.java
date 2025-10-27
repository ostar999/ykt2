package com.psychiatrygarden.adapter;

import androidx.lifecycle.LifecycleOwner;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.psychiatrygarden.widget.DetailCourseTopInfoWidget;
import com.yikaobang.yixue.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016J&\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00022\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/psychiatrygarden/adapter/TopDetailProvider;", "Lcom/chad/library/adapter/base/provider/BaseItemProvider;", "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "owner", "Landroidx/lifecycle/LifecycleOwner;", "(Landroidx/lifecycle/LifecycleOwner;)V", "init", "", "itemViewType", "", "getItemViewType", "()I", "layoutId", "getLayoutId", "convert", "", "helper", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "item", "payloads", "", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class TopDetailProvider extends BaseItemProvider<GoodsDetailItem> {
    private boolean init;

    @NotNull
    private final LifecycleOwner owner;

    public TopDetailProvider(@NotNull LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.owner = owner;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return 1;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_top_detail;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, GoodsDetailItem goodsDetailItem, List list) throws NumberFormatException {
        convert2(baseViewHolder, goodsDetailItem, (List<? extends Object>) list);
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NotNull BaseViewHolder helper, @NotNull GoodsDetailItem item, @NotNull List<? extends Object> payloads) throws NumberFormatException {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        super.convert(helper, (BaseViewHolder) item, payloads);
        if (this.init) {
            ((DetailCourseTopInfoWidget) helper.getView(R.id.course)).fixActivityCountDown();
        }
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull BaseViewHolder helper, @NotNull GoodsDetailItem item) throws NumberFormatException {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        DetailCourseTopInfoWidget detailCourseTopInfoWidget = (DetailCourseTopInfoWidget) helper.getView(R.id.course);
        if (this.init) {
            return;
        }
        this.init = true;
        detailCourseTopInfoWidget.initParams(item, this.owner);
    }
}
