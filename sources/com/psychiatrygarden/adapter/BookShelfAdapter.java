package com.psychiatrygarden.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.BookShelfDataBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class BookShelfAdapter extends BaseQuickAdapter<BookShelfDataBean, BaseViewHolder> {
    private boolean gridMode;
    private setOnItemClickLisenter onItemActionLisenter;

    public static abstract class setOnItemClickLisenter {
        public abstract void setItemClickAction(int pos, BookShelfDataBean item);
    }

    public BookShelfAdapter(boolean isList) {
        super(R.layout.item_book_shelf_vertical);
        this.gridMode = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, BookShelfDataBean bookShelfDataBean, View view) {
        setOnItemClickLisenter setonitemclicklisenter = this.onItemActionLisenter;
        if (setonitemclicklisenter != null) {
            setonitemclicklisenter.setItemClickAction(baseViewHolder.getLayoutPosition(), bookShelfDataBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(BaseViewHolder baseViewHolder, BookShelfDataBean bookShelfDataBean, View view) {
        setOnItemClickLisenter setonitemclicklisenter = this.onItemActionLisenter;
        if (setonitemclicklisenter != null) {
            setonitemclicklisenter.setItemClickAction(baseViewHolder.getLayoutPosition(), bookShelfDataBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$2(BaseViewHolder baseViewHolder, BookShelfDataBean bookShelfDataBean, View view) {
        setOnItemClickLisenter setonitemclicklisenter = this.onItemActionLisenter;
        if (setonitemclicklisenter != null) {
            setonitemclicklisenter.setItemClickAction(baseViewHolder.getLayoutPosition(), bookShelfDataBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$3(BaseViewHolder baseViewHolder, BookShelfDataBean bookShelfDataBean, View view) {
        setOnItemClickLisenter setonitemclicklisenter = this.onItemActionLisenter;
        if (setonitemclicklisenter != null) {
            setonitemclicklisenter.setItemClickAction(baseViewHolder.getLayoutPosition(), bookShelfDataBean);
        }
    }

    public void setOnItemClickLisenter(setOnItemClickLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    public void setShowMode(boolean gridMode) {
        this.gridMode = gridMode;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull final BaseViewHolder holder, final BookShelfDataBean item) {
        int i2;
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ly_item);
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.ly_item_list);
        RelativeLayout relativeLayout2 = (RelativeLayout) holder.getView(R.id.ly_item_grid);
        TextView textView = (TextView) holder.getView(R.id.tv_desc);
        RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.iv_book_img);
        ImageView imageView = (ImageView) holder.getView(R.id.iv_select);
        TextView textView2 = (TextView) holder.getView(R.id.tv_new_flag);
        TextView textView3 = (TextView) holder.getView(R.id.tv_book_name);
        TextView textView4 = (TextView) holder.getView(R.id.tv_desc_grid);
        RoundedImageView roundedImageView2 = (RoundedImageView) holder.getView(R.id.iv_book_img_grid);
        ImageView imageView2 = (ImageView) holder.getView(R.id.iv_select_grid);
        TextView textView5 = (TextView) holder.getView(R.id.tv_new_flag_grid);
        TextView textView6 = (TextView) holder.getView(R.id.tv_book_name_grid);
        if (this.gridMode) {
            relativeLayout.setVisibility(8);
            relativeLayout2.setVisibility(0);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout2.getLayoutParams();
            layoutParams.leftMargin = CommonUtil.dip2px(getContext(), 14.0f);
            layoutParams.rightMargin = CommonUtil.dip2px(getContext(), 14.0f);
            relativeLayout2.setLayoutParams(layoutParams);
            if (TextUtils.isEmpty(item.getBookId())) {
                if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                    roundedImageView2.setImageResource(R.drawable.ic_add_book_vertical);
                } else {
                    roundedImageView2.setImageResource(R.drawable.ic_add_book_vertical_night);
                }
                roundedImageView2.setVisibility(item.isEditMode() ? 8 : 0);
                imageView2.setVisibility(8);
                textView5.setVisibility(8);
                textView4.setVisibility(8);
                roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.g
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14494c.lambda$convert$0(holder, item, view);
                    }
                });
            } else {
                textView4.setVisibility(0);
                roundedImageView2.setVisibility(0);
                if (!TextUtils.isEmpty(item.getThumbnail())) {
                    GlideUtils.loadImage(getContext(), item.getThumbnail(), roundedImageView2);
                }
                imageView2.setSelected(item.isSelect());
                imageView2.setVisibility(item.isEditMode() ? 0 : 8);
                if (TextUtils.isEmpty(item.getLastChapterId()) || item.getLastChapterId().equals("0")) {
                    textView4.setText("未读");
                    textView5.setVisibility(8);
                } else if (item.getRenew().equals("1")) {
                    textView5.setVisibility(0);
                    textView4.setText(item.getRenew_time() + "·第" + item.getSort() + "章");
                } else {
                    textView5.setVisibility(8);
                    textView4.setText("第" + item.getSort() + "章 " + item.getLastChapterTitle());
                }
            }
            textView6.setText(item.getBookTitle());
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14538c.lambda$convert$1(holder, item, view);
                }
            });
            return;
        }
        relativeLayout.setVisibility(0);
        relativeLayout2.setVisibility(8);
        imageView.setSelected(item.isSelect());
        imageView.setVisibility(item.isEditMode() ? 0 : 8);
        textView3.setText(item.getBookTitle());
        if (TextUtils.isEmpty(item.getBookId())) {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                roundedImageView.setImageResource(R.drawable.ic_add_book_vertical);
            } else {
                roundedImageView.setImageResource(R.drawable.ic_add_book_vertical_night);
            }
            roundedImageView.setVisibility(item.isEditMode() ? 8 : 0);
            imageView.setVisibility(8);
            textView2.setVisibility(8);
            textView.setVisibility(8);
            roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14576c.lambda$convert$2(holder, item, view);
                }
            });
            return;
        }
        roundedImageView.setVisibility(0);
        if (TextUtils.isEmpty(item.getThumbnail())) {
            i2 = 0;
        } else {
            GlideUtils.loadImage(getContext(), item.getThumbnail(), roundedImageView);
            i2 = 0;
        }
        textView.setVisibility(i2);
        imageView.setSelected(item.isSelect());
        imageView.setVisibility(item.isEditMode() ? 0 : 8);
        if (TextUtils.isEmpty(item.getLastChapterId()) || item.getLastChapterId().equals("0")) {
            textView.setText("未读");
            textView2.setVisibility(8);
        } else if (item.getRenew().equals("1")) {
            textView2.setVisibility(0);
            textView.setText(item.getRenew_time() + "·第" + item.getSort() + "章找");
        } else {
            textView2.setVisibility(8);
            textView.setText("第" + item.getSort() + "章 " + item.getLastChapterTitle());
        }
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14622c.lambda$convert$3(holder, item, view);
            }
        });
    }
}
