package com.psychiatrygarden.activity.ebook;

import android.text.TextUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.BookStackBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class BookStoreAdp extends BaseQuickAdapter<BookStackBean.BookStackData, BaseViewHolder> {
    public BookStoreAdp() {
        super(R.layout.item_book_stack);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, BookStackBean.BookStackData item) {
        RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.img_pic);
        TextView textView = (TextView) holder.getView(R.id.tv_name);
        TextView textView2 = (TextView) holder.getView(R.id.tv_count);
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getThumbnail())).placeholder(R.mipmap.ic_order_default).into(roundedImageView);
        if (!TextUtils.isEmpty(item.getApp_name())) {
            textView.setText(item.getApp_name());
        }
        if (!TextUtils.isEmpty(item.getBookTitle())) {
            textView.setText(item.getBookTitle());
        }
        textView2.setText(item.getBook_count() + "本书籍");
    }
}
