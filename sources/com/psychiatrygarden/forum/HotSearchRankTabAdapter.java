package com.psychiatrygarden.forum;

import android.graphics.Typeface;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.HotSearchRankTabItem;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/forum/HotSearchRankTabAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/HotSearchRankTabItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class HotSearchRankTabAdapter extends BaseQuickAdapter<HotSearchRankTabItem, BaseViewHolder> {
    public HotSearchRankTabAdapter() {
        super(R.layout.item_hot_search_rank_tab, null, 2, null);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull HotSearchRankTabItem item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        TextView textView = (TextView) holder.getView(R.id.tv_name);
        holder.setVisible(R.id.iv_tag, item.getSelect());
        textView.setText(item.getTitle());
        boolean z2 = SkinManager.getCurrentSkinType(getContext()) == 1;
        if (item.getSelect()) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setTextColor(getContext().getColor(z2 ? R.color.first_txt_color_night : R.color.first_txt_color));
        } else {
            textView.setTypeface(Typeface.DEFAULT);
            textView.setTextColor(getContext().getColor(z2 ? R.color.third_txt_color_night : R.color.third_txt_color));
        }
    }
}
