package com.psychiatrygarden.forum;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u001f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0014R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/psychiatrygarden/forum/CircleHotSearchRankAdp;", "Lcom/aliyun/svideo/common/baseAdapter/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/CirclrListBean$DataBean;", "Lcom/aliyun/svideo/common/baseAdapter/BaseViewHolder;", "type", "", "layoutId", "showItemBackground", "", "(IIZ)V", "mType", "convert", "", "helper", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CircleHotSearchRankAdp extends BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> {
    private int mType;
    private boolean showItemBackground;

    public CircleHotSearchRankAdp(int i2, int i3, boolean z2) {
        super(i3);
        this.mType = i2;
        this.showItemBackground = z2;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder helper, @NotNull CirclrListBean.DataBean item) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.ly_item);
        TextView textView = (TextView) helper.getView(R.id.tv_sort);
        int layoutPosition = helper.getLayoutPosition() + 1;
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
        RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
        int pxByDp = ScreenUtil.getPxByDp(linearLayout.getContext(), 8);
        if (this.showItemBackground) {
            int pxByDp2 = ScreenUtil.getPxByDp(linearLayout.getContext(), 11);
            linearLayout.setPadding(pxByDp, pxByDp2, pxByDp, pxByDp2);
            ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin = ScreenUtil.getPxByDp(linearLayout.getContext(), 0);
        } else {
            ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin = ScreenUtil.getPxByDp(linearLayout.getContext(), 8);
            linearLayout.setPadding(pxByDp, 0, pxByDp, 0);
        }
        linearLayout.setLayoutParams(layoutParams2);
        int i2 = this.mType;
        if (i2 == 1 || i2 == 2) {
            ((TextView) helper.getView(R.id.tv_title)).setText(item.getTitle());
        } else if (i2 == 3) {
            RoundedImageView roundedImageView = (RoundedImageView) helper.getView(R.id.iv_book_img);
            TextView textView2 = (TextView) helper.getView(R.id.tv_book_name);
            TextView textView3 = (TextView) helper.getView(R.id.tv_desc);
            TextView textView4 = (TextView) helper.getView(R.id.tv_read_people_num);
            TextView textView5 = (TextView) helper.getView(R.id.tv_comment_num);
            Glide.with(roundedImageView.getContext()).load((Object) GlideUtils.generateUrl(item.getThumbnail())).placeholder(R.mipmap.ic_order_default).into(roundedImageView);
            textView3.setText(item.getDescribe());
            textView4.setText(item.getRead_count() + "人阅读");
            textView5.setText(item.getBook_review_count() + " 评论");
            textView2.setText(item.getTitle());
        } else if (i2 == 4) {
            ImageView imageView = (ImageView) helper.getView(R.id.ic_file_type);
            ImageView imageView2 = (ImageView) helper.getView(R.id.ic_lock);
            TextView textView6 = (TextView) helper.getView(R.id.tv_file_name);
            TextView textView7 = (TextView) helper.getView(R.id.tv_file_author);
            TextView textView8 = (TextView) helper.getView(R.id.tv_file_size);
            TextView textView9 = (TextView) helper.getView(R.id.tv_download_times);
            textView8.setText(item.getSize());
            textView9.setText(item.getDownload_count() + "次下载");
            textView7.setText(item.getNickname());
            if (TextUtils.isEmpty(item.getIs_rights()) || !Intrinsics.areEqual(item.getIs_rights(), "1")) {
                imageView2.setVisibility(0);
            } else {
                imageView2.setVisibility(8);
            }
            Glide.with(imageView.getContext()).load((Object) GlideUtils.generateUrl(item.getIcon())).placeholder(R.mipmap.ic_order_default).into(imageView);
            textView6.setText(item.getTitle() + item.getType_name());
        }
        if (layoutPosition == 1) {
            textView.setBackgroundResource(R.mipmap.ic_rank_first);
            textView.setTextColor(textView.getContext().getColor(R.color.white));
            linearLayout.setBackgroundResource(this.showItemBackground ? R.drawable.shape_hot_circle_one_bg : 0);
        } else if (layoutPosition == 2) {
            textView.setBackgroundResource(R.mipmap.ic_rank_second);
            textView.setTextColor(textView.getContext().getColor(R.color.white));
            linearLayout.setBackgroundResource(this.showItemBackground ? R.drawable.shape_hot_circle_two_bg : 0);
        } else if (layoutPosition != 3) {
            textView.setBackground(null);
            textView.setTextColor(textView.getContext().getColor(R.color.forth_txt_color));
            linearLayout.setBackgroundResource(this.showItemBackground ? R.drawable.shape_hot_circle_gray_bg : 0);
        } else {
            textView.setBackgroundResource(R.mipmap.ic_rank_third);
            textView.setTextColor(textView.getContext().getColor(R.color.white));
            linearLayout.setBackgroundResource(this.showItemBackground ? R.drawable.shape_hot_circle_three_bg : 0);
        }
        textView.setText(String.valueOf(layoutPosition));
    }
}
