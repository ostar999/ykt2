package com.psychiatrygarden.adapter;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.psychiatrygarden.widget.DetailCourseCommentWidget;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t¨\u0006\u0011"}, d2 = {"Lcom/psychiatrygarden/adapter/CourseCommentProvider;", "Lcom/chad/library/adapter/base/provider/BaseItemProvider;", "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "()V", "init", "", "itemViewType", "", "getItemViewType", "()I", "layoutId", "getLayoutId", "convert", "", "helper", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseCommentProvider extends BaseItemProvider<GoodsDetailItem> {
    private boolean init;

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return 6;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_course_comment_list;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull BaseViewHolder helper, @NotNull GoodsDetailItem item) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        DetailCourseCommentWidget detailCourseCommentWidget = (DetailCourseCommentWidget) helper.getView(R.id.view);
        if (this.init) {
            return;
        }
        this.init = true;
        detailCourseCommentWidget.initParams(item);
    }
}
