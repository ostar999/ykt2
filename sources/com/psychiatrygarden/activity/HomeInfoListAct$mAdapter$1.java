package com.psychiatrygarden.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
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

@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/activity/HomeInfoListAct$mAdapter$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/HomeInfoItemBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class HomeInfoListAct$mAdapter$1 extends BaseQuickAdapter<HomeInfoItemBean, BaseViewHolder> {
    final /* synthetic */ HomeInfoListAct this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeInfoListAct$mAdapter$1(final HomeInfoListAct homeInfoListAct) {
        super(R.layout.item_latest_news, null, 2, null);
        this.this$0 = homeInfoListAct;
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.lc
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                HomeInfoListAct$mAdapter$1._init_$lambda$0(this.f12661c, homeInfoListAct, baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(HomeInfoListAct$mAdapter$1 this$0, HomeInfoListAct this$1, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this$1, "this$1");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        HomeInfoItemBean item = this$0.getItem(i2);
        this$1.startActivity(new Intent().setClass(this$1.mContext, WebLongSaveActivity.class).putExtra("web_url", item.getUrl()).putExtra("title", item.getTitle()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map convert$lambda$1() {
        return MapsKt__MapsJVMKt.mapOf(TuplesKt.to("Refer", "https://mp.weixin.qq.com"));
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull HomeInfoItemBean item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
        RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = holder.getLayoutPosition() == 0 ? 0 : SizeUtil.dp2px(this.this$0.mContext, 20);
        holder.itemView.setLayoutParams(layoutParams2);
        holder.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_time, item.getUpdate_time());
        String thumb_url = item.getThumb_url();
        if (thumb_url == null || thumb_url.length() == 0) {
            return;
        }
        ImageView imageView = (ImageView) holder.getView(R.id.iv_img);
        ViewExtensionsKt.setCornerRadius(imageView, 8);
        Glide.with(this.this$0.mContext).load((Object) new GlideUrl(item.getThumb_url(), new Headers() { // from class: com.psychiatrygarden.activity.kc
            @Override // com.bumptech.glide.load.model.Headers
            public final Map getHeaders() {
                return HomeInfoListAct$mAdapter$1.convert$lambda$1();
            }
        })).placeholder(R.mipmap.ic_order_default).into(imageView);
    }
}
