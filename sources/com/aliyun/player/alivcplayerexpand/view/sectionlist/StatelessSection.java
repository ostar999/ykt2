package com.aliyun.player.alivcplayerexpand.view.sectionlist;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public abstract class StatelessSection extends Section {
    public StatelessSection(SectionParameters sectionParameters) {
        super(sectionParameters);
        if (sectionParameters.loadingResourceId != null) {
            throw new IllegalArgumentException("Stateless section shouldn't have a loading state resource");
        }
        if (sectionParameters.loadingViewWillBeProvided) {
            throw new IllegalArgumentException("Stateless section shouldn't have loadingViewWillBeProvided set");
        }
        if (sectionParameters.failedResourceId != null) {
            throw new IllegalArgumentException("Stateless section shouldn't have a failed state resource");
        }
        if (sectionParameters.failedViewWillBeProvided) {
            throw new IllegalArgumentException("Stateless section shouldn't have failedViewWillBeProvided set");
        }
        if (sectionParameters.emptyResourceId != null) {
            throw new IllegalArgumentException("Stateless section shouldn't have an empty state resource");
        }
        if (sectionParameters.emptyViewWillBeProvided) {
            throw new IllegalArgumentException("Stateless section shouldn't have emptyViewWillBeProvided set");
        }
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public final RecyclerView.ViewHolder getEmptyViewHolder(View view) {
        return super.getEmptyViewHolder(view);
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public final RecyclerView.ViewHolder getFailedViewHolder(View view) {
        return super.getFailedViewHolder(view);
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public final RecyclerView.ViewHolder getLoadingViewHolder(View view) {
        return super.getLoadingViewHolder(view);
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public final void onBindEmptyViewHolder(RecyclerView.ViewHolder viewHolder) {
        super.onBindEmptyViewHolder(viewHolder);
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public final void onBindFailedViewHolder(RecyclerView.ViewHolder viewHolder) {
        super.onBindFailedViewHolder(viewHolder);
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.sectionlist.Section
    public final void onBindLoadingViewHolder(RecyclerView.ViewHolder viewHolder) {
        super.onBindLoadingViewHolder(viewHolder);
    }
}
