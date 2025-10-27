package com.hyphenate.easeui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hyphenate.easeui.R;

/* loaded from: classes4.dex */
public class EaseChatExtendMenuIndicatorAdapter extends RecyclerView.Adapter<ViewHolder> {
    private int page_count;
    private int selectedPosition;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox indicator;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.indicator = (CheckBox) view;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int i2 = this.page_count;
        if (i2 == 1) {
            return 0;
        }
        return i2;
    }

    public void setPageCount(int i2) {
        this.page_count = i2;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int i2) {
        this.selectedPosition = i2;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i2) {
        viewHolder.indicator.setChecked(this.selectedPosition == i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ease_chat_extend_indicator_item, viewGroup, false));
    }
}
