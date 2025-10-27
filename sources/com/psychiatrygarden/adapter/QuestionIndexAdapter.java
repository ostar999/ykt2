package com.psychiatrygarden.adapter;

import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.QuestionIndexBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class QuestionIndexAdapter extends BaseQuickAdapter<QuestionIndexBean, BaseViewHolder> {
    public QuestionIndexAdapter() {
        super(R.layout.item_question_index);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, QuestionIndexBean item) {
        int i2;
        int color;
        ((TextView) holder.itemView).setText(item.getTitle());
        if (item.isSelect()) {
            color = SkinManager.getCurrentSkinType(getContext()) == 0 ? ContextCompat.getColor(getContext(), R.color.dominant_color) : ContextCompat.getColor(getContext(), R.color.dominant_color_night);
            i2 = 16;
        } else {
            i2 = 14;
            color = SkinManager.getCurrentSkinType(getContext()) == 0 ? ContextCompat.getColor(getContext(), R.color.tertiary_text_color) : ContextCompat.getColor(getContext(), R.color.tertiary_text_color_night);
        }
        ((TextView) holder.itemView).setTextColor(color);
        ((TextView) holder.itemView).setTextSize(i2);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = holder.getBindingAdapterPosition() == 0 ? 0 : CommonUtil.dip2px(getContext(), 40.0f);
        holder.itemView.setLayoutParams(layoutParams);
    }
}
