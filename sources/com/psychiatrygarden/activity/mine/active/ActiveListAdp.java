package com.psychiatrygarden.activity.mine.active;

import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.bean.MyMessageCommentBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ActiveListAdp extends BaseQuickAdapter<MyMessageCommentBean.DataBean, BaseViewHolder> {
    private OnItemActionLisenter onItemActionLisenter;

    public static abstract class OnItemActionLisenter {
        public abstract void setSupportAction(int pos, MyMessageCommentBean.DataBean item, TextView textView);
    }

    public ActiveListAdp() {
        super(R.layout.item_active);
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder holder, MyMessageCommentBean.DataBean item) {
    }
}
