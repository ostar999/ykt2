package com.psychiatrygarden.activity.mine.knowledge;

import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.bean.MockStatisticsTreeBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ExamPointStatisticsAdp extends BaseQuickAdapter<MockStatisticsTreeBean.MockStatisticsData, BaseViewHolder> {
    private int currentPos;

    public ExamPointStatisticsAdp() {
        super(R.layout.item_mock_statistics_part);
        this.currentPos = 0;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder helper, MockStatisticsTreeBean.MockStatisticsData item) {
        TextView textView = (TextView) helper.getView(R.id.tv_part_name);
        textView.setText(item.getName());
        if (this.currentPos == helper.getLayoutPosition()) {
            textView.setTextColor(textView.getContext().getColor(R.color.main_theme_color));
            textView.setBackgroundResource(R.drawable.shape_main_theme_five_deep_color_bg);
        } else {
            textView.setTextColor(textView.getContext().getColor(R.color.first_txt_color));
            textView.setBackgroundResource(R.drawable.shape_new_bg_two_color_no_night_corners_8);
        }
    }
}
