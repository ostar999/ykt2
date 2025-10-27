package com.psychiatrygarden.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.BookShelfDataBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class BookReadRecordAdapter extends BaseQuickAdapter<BookShelfDataBean, BaseViewHolder> {
    private OnItemActionLisenter onItemActionLisenter;

    public static abstract class OnItemActionLisenter {
        public abstract void setAddBook(int pos, BookShelfDataBean item);

        public abstract void setOpenBook(int pos, BookShelfDataBean item);
    }

    public BookReadRecordAdapter() {
        super(R.layout.item_read_book_record, new ArrayList());
        addChildClickViewIds(R.id.iv_select);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(int i2, BookShelfDataBean bookShelfDataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setAddBook(i2, bookShelfDataBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(int i2, BookShelfDataBean bookShelfDataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setOpenBook(i2, bookShelfDataBean);
        }
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder holder, BookShelfDataBean item, @NonNull List payloads) {
        convert2(holder, item, (List<?>) payloads);
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NonNull BaseViewHolder holder, BookShelfDataBean item, @NonNull List<?> payloads) {
        if (payloads != null && ((Integer) payloads.get(0)).intValue() == 1) {
            TextView textView = (TextView) holder.getView(R.id.tv_open);
            TextView textView2 = (TextView) holder.getView(R.id.tv_add);
            if (item.getIsBookshelf().equals("1")) {
                textView.setVisibility(0);
                textView2.setVisibility(8);
                return;
            } else {
                textView.setVisibility(8);
                textView2.setVisibility(0);
                return;
            }
        }
        if (((Integer) payloads.get(0)).intValue() == 999) {
            holder.getView(R.id.iv_select).setSelected(item.isEditMode() && item.isSelect());
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, final BookShelfDataBean item) {
        TextView textView = (TextView) holder.getView(R.id.book_name);
        RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.iv_book_img);
        TextView textView2 = (TextView) holder.getView(R.id.tv_renew);
        TextView textView3 = (TextView) holder.getView(R.id.tv_chapter_name);
        TextView textView4 = (TextView) holder.getView(R.id.tv_open);
        TextView textView5 = (TextView) holder.getView(R.id.tv_add);
        TextView textView6 = (TextView) holder.getView(R.id.tv_day);
        ImageView imageView = (ImageView) holder.getView(R.id.iv_select);
        imageView.setVisibility(item.isEditMode() ? 0 : 8);
        imageView.setSelected(item.isEditMode() && item.isSelect());
        final int layoutPosition = holder.getLayoutPosition();
        if (layoutPosition != 0 && item.getEndTime().equals(getData().get(layoutPosition - 1).getEndTime())) {
            textView6.setVisibility(8);
        } else {
            textView6.setVisibility(0);
        }
        textView6.setText(item.getEndTime());
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getThumbnail())).into(roundedImageView);
        textView.setText(item.getBookTitle());
        if (item.getRenew().equals("1")) {
            textView2.setVisibility(0);
            textView2.setText(item.getRenew_time());
        } else {
            textView2.setVisibility(8);
        }
        textView3.setText(item.getLastChapterTitle());
        if (item.getIsBookshelf().equals("1")) {
            textView4.setVisibility(0);
            textView5.setVisibility(8);
        } else {
            textView4.setVisibility(8);
            textView5.setVisibility(0);
        }
        if (item.isEditMode()) {
            textView4.setVisibility(8);
            textView5.setVisibility(8);
        }
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14415c.lambda$convert$0(layoutPosition, item, view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14455c.lambda$convert$1(layoutPosition, item, view);
            }
        });
    }
}
