package com.psychiatrygarden.adapter;

import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class CircleSchoolHomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CircleSchoolHomeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, String dataBean) {
        RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.rl_circle_school);
        if (helper.getBindingAdapterPosition() < 3) {
            relativeLayout.setBackgroundColor(getContext().getResources().getColor(R.color.gray_line_126));
        } else {
            relativeLayout.setBackgroundColor(getContext().getResources().getColor(R.color.bg_backgroud));
        }
    }
}
