package com.psychiatrygarden.fragmenthome;

import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.HomeInfoItemBean;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/fragmenthome/NewHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/HomeInfoItemBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class NewHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1 extends BaseQuickAdapter<HomeInfoItemBean, BaseViewHolder> {
    final /* synthetic */ NewHomeBottomListFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1(NewHomeBottomListFragment newHomeBottomListFragment) {
        super(R.layout.item_latest_news, null, 2, null);
        this.this$0 = newHomeBottomListFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map convert$lambda$0() {
        return MapsKt__MapsJVMKt.mapOf(TuplesKt.to("Refer", "https://mp.weixin.qq.com"));
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull HomeInfoItemBean item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
        RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
        ImageView imageView = (ImageView) holder.getView(R.id.iv_img);
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = holder.getLayoutPosition() == 0 ? 0 : SizeUtil.dp2px(((BaseFragment) this.this$0).mContext, 20);
        holder.itemView.setLayoutParams(layoutParams2);
        holder.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_time, item.getUpdate_time());
        String thumb_url = item.getThumb_url();
        if (thumb_url == null || thumb_url.length() == 0) {
            return;
        }
        GlideUrl glideUrl = new GlideUrl(item.getThumb_url(), new Headers() { // from class: com.psychiatrygarden.fragmenthome.w8
            @Override // com.bumptech.glide.load.model.Headers
            public final Map getHeaders() {
                return NewHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1.convert$lambda$0();
            }
        });
        ViewExtensionsKt.setCornerRadius(imageView, 8);
        Glide.with(((BaseFragment) this.this$0).mContext).load((Object) glideUrl).placeholder(R.mipmap.ic_order_default).into(imageView);
    }
}
