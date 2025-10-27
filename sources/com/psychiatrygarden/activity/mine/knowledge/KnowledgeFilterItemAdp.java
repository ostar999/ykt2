package com.psychiatrygarden.activity.mine.knowledge;

import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class KnowledgeFilterItemAdp extends BaseQuickAdapter<String, BaseViewHolder> {
    public KnowledgeFilterItemAdp() {
        super(R.layout.item_knowledge_filter_view);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder helper, String item) {
        ((TextView) helper.getView(R.id.tv_value)).setText(item);
    }
}
