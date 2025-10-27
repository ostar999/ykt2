package com.psychiatrygarden.forum;

import android.graphics.Color;
import android.text.TextPaint;
import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.bean.ForumTopBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class SearchTypeAdp extends BaseQuickAdapter<ForumTopBean.TopModule, BaseViewHolder> {
    private int selectPos;

    public SearchTypeAdp() {
        super(R.layout.item_search_type);
        this.selectPos = 0;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder helper, ForumTopBean.TopModule item) {
        TextView textView = (TextView) helper.getView(R.id.tv_title);
        TextPaint paint = textView.getPaint();
        textView.setText(item.getName());
        paint.setFakeBoldText(false);
        if (this.selectPos == helper.getLayoutPosition()) {
            textView.setBackgroundResource(R.drawable.shape_main_color_round8);
            textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(this.mContext) == 0 ? "#F95843" : "#B2575C"));
            paint.setFakeBoldText(true);
        } else {
            textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(this.mContext) == 0 ? "#141516" : "#7380A9"));
            textView.setBackgroundResource(R.drawable.shape_bg_one_round8);
        }
        textView.invalidate();
    }
}
