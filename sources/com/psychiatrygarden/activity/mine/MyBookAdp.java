package com.psychiatrygarden.activity.mine;

import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.BookShelfDataBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class MyBookAdp extends BaseQuickAdapter<BookShelfDataBean, BaseViewHolder> {
    public MyBookAdp() {
        super(R.layout.item_my_book);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, BookShelfDataBean item) {
        RoundedImageView roundedImageView = (RoundedImageView) helper.getView(R.id.iv_book_img);
        TextView textView = (TextView) helper.getView(R.id.book_name);
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getThumbnail())).into(roundedImageView);
        textView.setText(item.getBookTitle());
    }
}
