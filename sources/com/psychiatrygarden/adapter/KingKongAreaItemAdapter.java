package com.psychiatrygarden.adapter;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.KingKongItem;

/* loaded from: classes5.dex */
public class KingKongAreaItemAdapter extends BaseQuickAdapter<KingKongItem, BaseViewHolder> {
    public KingKongAreaItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, KingKongItem item) {
        int i2 = getContext().getResources().getDisplayMetrics().widthPixels / 5;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = i2;
        holder.itemView.setLayoutParams(layoutParams);
        ((TextView) holder.itemView).setText(item.getLabel());
        int iconAttr = item.getIconAttr();
        if (iconAttr != 0) {
            TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{iconAttr});
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
            if (drawable != null) {
                ((TextView) holder.itemView).setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, drawable, (Drawable) null, (Drawable) null);
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }
}
