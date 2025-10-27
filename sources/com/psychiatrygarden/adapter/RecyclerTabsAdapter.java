package com.psychiatrygarden.adapter;

import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/adapter/RecyclerTabsAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/SelectIdentityBean$DataBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class RecyclerTabsAdapter extends BaseQuickAdapter<SelectIdentityBean.DataBean, BaseViewHolder> {
    public RecyclerTabsAdapter() {
        super(R.layout.item_tabs, null, 2, null);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull SelectIdentityBean.DataBean item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        ImageView imageView = (ImageView) holder.getView(R.id.img_choose);
        TextView textView = (TextView) holder.getView(R.id.tv_tabs_name);
        int themeColor = SkinManager.getThemeColor(getContext(), R.attr.first_txt_color);
        int themeColor2 = SkinManager.getThemeColor(getContext(), R.attr.third_txt_color);
        textView.setText(item.getTitle());
        if (!item.isSelect()) {
            themeColor = themeColor2;
        }
        textView.setTextColor(themeColor);
        imageView.setVisibility(item.isSelect() ? 0 : 4);
        textView.setTextSize(item.isSelect() ? 16.0f : 14.0f);
        textView.setTypeface(item.isSelect() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
    }
}
