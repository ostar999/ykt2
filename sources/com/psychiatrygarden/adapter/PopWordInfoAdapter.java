package com.psychiatrygarden.adapter;

import android.graphics.Color;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.plv.socket.user.PLVAuthorizationBean;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class PopWordInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    boolean isCollect;

    public PopWordInfoAdapter(List<String> data) {
        super(R.layout.item_pop_word_info, data);
        this.isCollect = false;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder baseViewHolder, String s2) {
        if (this.isCollect) {
            baseViewHolder.setTextColor(R.id.tv_word_attribute, Color.parseColor("#898989"));
            baseViewHolder.setTextColor(R.id.tv_word_attribute_content, Color.parseColor("#000000"));
        } else {
            baseViewHolder.setTextColor(R.id.tv_word_attribute, Color.parseColor("#99ffffff"));
            baseViewHolder.setTextColor(R.id.tv_word_attribute_content, Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
        }
        String[] strArrSplit = s2.split("\\.");
        if (strArrSplit.length != 2) {
            baseViewHolder.setText(R.id.tv_word_attribute_content, s2);
            baseViewHolder.setGone(R.id.tv_word_attribute, true);
            return;
        }
        baseViewHolder.setText(R.id.tv_word_attribute_content, strArrSplit[1]);
        baseViewHolder.setGone(R.id.tv_word_attribute, false);
        baseViewHolder.setText(R.id.tv_word_attribute, strArrSplit[0] + StrPool.DOT);
    }
}
