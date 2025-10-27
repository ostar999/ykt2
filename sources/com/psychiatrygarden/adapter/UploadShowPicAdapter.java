package com.psychiatrygarden.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.PushCommentNoteUploadPicItem;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/adapter/UploadShowPicAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/PushCommentNoteUploadPicItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class UploadShowPicAdapter extends BaseQuickAdapter<PushCommentNoteUploadPicItem, BaseViewHolder> {
    public UploadShowPicAdapter() {
        super(R.layout.item_upload_pic, null, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$0(PushCommentNoteUploadPicItem item, UploadShowPicAdapter this$0, BaseViewHolder holder, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        if (item.getType() == 1) {
            this$0.removeAt(holder.getLayoutPosition());
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull final BaseViewHolder holder, @NotNull final PushCommentNoteUploadPicItem item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        ImageView imageView = (ImageView) holder.getView(R.id.iv_delete);
        RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.iv_image);
        int i2 = 8;
        if (getData().size() > 1) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
            RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
            ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = holder.getLayoutPosition() == 0 ? 0 : SizeUtil.dp2px(getContext(), 8);
            holder.itemView.setLayoutParams(layoutParams2);
        }
        View view = holder.getView(R.id.progress_bar);
        holder.setGone(R.id.iv_error, item.getStatus() != 3).setImageResource(R.id.iv_error, SkinManager.getCurrentSkinType(getContext()) == 0 ? R.drawable.ic_upload_error_day : R.drawable.ic_upload_error_night).setGone(R.id.line, getData().size() <= 1 || (getData().size() > 1 && holder.getLayoutPosition() == getData().size() - 1));
        view.setVisibility(item.getStatus() == 1 ? 0 : 8);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.vf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UploadShowPicAdapter.convert$lambda$0(item, this, holder, view2);
            }
        });
        if (item.getType() == 1 && (item.getStatus() == 2 || item.getStatus() == 3)) {
            i2 = 0;
        }
        imageView.setVisibility(i2);
        if (item.getStatus() == 2) {
            GlideUtils.loadImage(view.getContext(), item.getbImg(), roundedImageView, R.mipmap.ic_order_default, R.mipmap.ic_order_default);
        }
        if (item.getStatus() != 2) {
            roundedImageView.setImageDrawable(new ColorDrawable(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? "#EEEEEE" : "#252C46")));
        } else {
            GlideUtils.loadImage(view.getContext(), item.getbImg(), roundedImageView, R.mipmap.ic_order_default, R.mipmap.ic_order_default);
        }
    }
}
