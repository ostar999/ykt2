package com.psychiatrygarden.activity.forum;

import android.graphics.Color;
import android.text.Html;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.forum.bean.GiveLogBean;
import com.psychiatrygarden.utils.DividerItemDecoration;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class GiveLogAdapter extends BaseQuickAdapter<GiveLogBean.DataBean.DataChildlistBean, BaseViewHolder> {
    public GiveLogAdapter() {
        super(R.layout.layout_forum_detail_log);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder baseViewHolder, GiveLogBean.DataBean.DataChildlistBean item) {
        ((TextView) baseViewHolder.getView(R.id.title)).setText(item.getMonth());
        RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0, 1, Color.parseColor("#1C2134")));
        } else {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0, 1, Color.parseColor("#EEEEEE")));
        }
        BaseQuickAdapter<GiveLogBean.DataBean.DataChildlistBean.LogBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GiveLogBean.DataBean.DataChildlistBean.LogBean, BaseViewHolder>(R.layout.layout_forum_detail_child_log, item.getLogs()) { // from class: com.psychiatrygarden.activity.forum.GiveLogAdapter.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder2, GiveLogBean.DataBean.DataChildlistBean.LogBean logBean) {
                TextView textView = (TextView) baseViewHolder2.getView(R.id.title);
                TextView textView2 = (TextView) baseViewHolder2.getView(R.id.timer);
                TextView textView3 = (TextView) baseViewHolder2.getView(R.id.daytext);
                textView.setText(logBean.getTip());
                textView2.setText(logBean.getTime());
                if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                    textView3.setText(Html.fromHtml("<font color='#B2575C'>" + logBean.getGive_day() + "</font>"));
                    return;
                }
                textView3.setText(Html.fromHtml("<font color='#DD594A'>" + logBean.getGive_day() + "</font>"));
            }
        };
        recyclerView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setEmptyView(R.layout.adapter_empty_log_view);
    }
}
