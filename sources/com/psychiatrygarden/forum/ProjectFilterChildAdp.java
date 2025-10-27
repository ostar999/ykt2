package com.psychiatrygarden.forum;

import android.text.TextPaint;
import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ProjectFilterChildAdp extends BaseQuickAdapter<ForumFilterBean.FilterDataBean, BaseViewHolder> {
    public ProjectFilterChildAdp() {
        super(R.layout.item_project_child_view);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder helper, ForumFilterBean.FilterDataBean item) {
        TextView textView = (TextView) helper.getView(R.id.tv_name);
        textView.setText(item.getTitle());
        TextPaint paint = textView.getPaint();
        if (item.isSelected()) {
            textView.setSelected(true);
            textView.setBackgroundResource(R.drawable.shape_main_color_round8);
            paint.setFakeBoldText(true);
        } else {
            textView.setSelected(false);
            paint.setFakeBoldText(false);
            textView.setBackgroundResource(R.drawable.shape_bg_one_round8);
        }
        textView.invalidate();
    }
}
