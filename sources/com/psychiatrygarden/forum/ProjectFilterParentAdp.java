package com.psychiatrygarden.forum;

import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ProjectFilterParentAdp extends BaseQuickAdapter<ForumFilterBean.FilterDataBean, BaseViewHolder> {
    public ProjectFilterParentAdp() {
        super(R.layout.item_project_parent_view);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder helper, ForumFilterBean.FilterDataBean item) {
        View view = helper.getView(R.id.line);
        TextView textView = (TextView) helper.getView(R.id.tv_name);
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.ly_item);
        textView.setText(item.getTitle());
        TextPaint paint = textView.getPaint();
        if (item.isSelected()) {
            view.setVisibility(0);
            textView.setSelected(true);
            linearLayout.setBackgroundResource(R.drawable.shape_project_choosed_bg);
            paint.setFakeBoldText(true);
        } else {
            view.setVisibility(4);
            textView.setSelected(false);
            paint.setFakeBoldText(false);
            if (helper.getLayoutPosition() == 0) {
                linearLayout.setBackgroundResource(R.drawable.shape_project_top_normal_bg);
            } else {
                linearLayout.setBackgroundResource(R.drawable.shape_project_normal_bg);
            }
        }
        textView.invalidate();
    }
}
