package com.hyphenate.easeui.modules.conversation.adapter;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseBaseDelegateAdapter;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;

/* loaded from: classes4.dex */
public class EaseConversationListAdapter extends EaseBaseDelegateAdapter<EaseConversationInfo> {
    private int emptyLayoutId;

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter
    public int getEmptyLayoutId() {
        int i2 = this.emptyLayoutId;
        return i2 != 0 ? i2 : R.layout.ease_layout_default_no_conversation_data;
    }

    public void setEmptyLayoutId(int i2) {
        this.emptyLayoutId = i2;
        notifyDataSetChanged();
    }
}
