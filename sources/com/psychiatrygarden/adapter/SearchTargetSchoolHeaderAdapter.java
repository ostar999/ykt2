package com.psychiatrygarden.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.widget.ClearEditText;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class SearchTargetSchoolHeaderAdapter extends RecyclerView.Adapter<HeaderViewHolder> {
    private ClearEditText etHeader;

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        ClearEditText headerSearch;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.headerSearch = (ClearEditText) itemView.findViewById(R.id.head_search);
        }
    }

    public ClearEditText getEditText() {
        return this.etHeader;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull HeaderViewHolder holder, int position) {
        this.etHeader = holder.headerSearch;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_target_school_header, parent, false));
    }
}
