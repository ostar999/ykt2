package com.psychiatrygarden.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.GroupChatClassifyBean;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatClassifyAdapter extends BaseQuickAdapter<GroupChatClassifyBean.DataDTO, BaseViewHolder> {
    public GroupChatClassifyAdapter(@Nullable List<GroupChatClassifyBean.DataDTO> data) {
        super(R.layout.item_group_chat_classify, data);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, GroupChatClassifyBean.DataDTO item) {
        RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.rl_classify);
        View view = helper.getView(R.id.view_line);
        TextView textView = (TextView) helper.getView(R.id.tv_name);
        textView.setText(item.getName());
        if (item.getIs_select().equals("1")) {
            relativeLayout.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            textView.setTextColor(getContext().getResources().getColor(R.color.black));
            view.setVisibility(0);
        } else {
            relativeLayout.setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
            textView.setTextColor(getContext().getResources().getColor(R.color.FF969696));
            view.setVisibility(4);
        }
    }
}
