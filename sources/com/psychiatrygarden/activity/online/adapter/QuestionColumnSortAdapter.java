package com.psychiatrygarden.activity.online.adapter;

import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionColumnSortAdapter extends BaseQuickAdapter<SelectIdentityBean.DataBean, BaseViewHolder> implements DraggableModule {
    public QuestionColumnSortAdapter(@Nullable List<SelectIdentityBean.DataBean> data) {
        super(R.layout.item_question_column_sort, data);
    }

    @Override // com.chad.library.adapter.base.module.DraggableModule
    public /* synthetic */ BaseDraggableModule addDraggableModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.g.a(this, baseQuickAdapter);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, SelectIdentityBean.DataBean item) {
        ((TextView) helper.getView(R.id.tv_question_column)).setText(item.getTitle());
    }
}
