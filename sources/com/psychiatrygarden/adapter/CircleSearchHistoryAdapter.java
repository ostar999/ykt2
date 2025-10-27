package com.psychiatrygarden.adapter;

import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class CircleSearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CircleSearchHistoryAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, String item) {
        TextView textView = (TextView) helper.getView(R.id.tv_hot_num);
        ((TextView) helper.getView(R.id.tv_hot_content)).setText(item);
        textView.setText(helper.getAdapterPosition() + "");
        int adapterPosition = helper.getAdapterPosition();
        if (adapterPosition == 1) {
            textView.setBackgroundResource(R.drawable.ffec4e45_2);
            return;
        }
        if (adapterPosition == 2) {
            textView.setBackgroundResource(R.drawable.ffea7642_2);
        } else if (adapterPosition != 3) {
            textView.setBackgroundResource(R.drawable.ff919191_2);
        } else {
            textView.setBackgroundResource(R.drawable.ffe69b3d_2);
        }
    }
}
