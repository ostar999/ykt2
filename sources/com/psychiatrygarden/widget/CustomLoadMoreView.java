package com.psychiatrygarden.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yikaobang.yixue.R;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class CustomLoadMoreView extends BaseLoadMoreView {
    @Override // com.chad.library.adapter.base.loadmore.BaseLoadMoreView
    @NotNull
    public View getLoadComplete(@NotNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.findView(R.id.load_more_load_complete_view);
    }

    @Override // com.chad.library.adapter.base.loadmore.BaseLoadMoreView
    @NotNull
    public View getLoadEndView(@NotNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.findView(R.id.load_more_load_end_view);
    }

    @Override // com.chad.library.adapter.base.loadmore.BaseLoadMoreView
    @NotNull
    public View getLoadFailView(@NotNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.findView(R.id.load_more_load_fail_view);
    }

    @Override // com.chad.library.adapter.base.loadmore.BaseLoadMoreView
    @NotNull
    public View getLoadingView(@NotNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.findView(R.id.load_more_loading_view);
    }

    @Override // com.chad.library.adapter.base.loadmore.BaseLoadMoreView
    @NotNull
    public View getRootView(@NotNull ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brvah_quick_view_load_more, viewGroup, false);
    }
}
