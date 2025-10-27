package com.psychiatrygarden.activity.chooseSchool.adapter;

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
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.bean.HomeInfoItemBean;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/WxInfoAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/HomeInfoItemBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class WxInfoAdapter extends BaseQuickAdapter<HomeInfoItemBean, BaseViewHolder> {
    public WxInfoAdapter() {
        super(R.layout.item_latest_news, null, 2, null);
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.m
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                WxInfoAdapter._init_$lambda$0(this.f11212c, baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(WxInfoAdapter this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        HomeInfoItemBean item = this$0.getItem(i2);
        try {
            AliYunLogUtil.getInstance().addLog(AliyunEvent.Means, item.getArticle_id(), item.getTitle());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this$0.getContext().startActivity(new Intent().setClass(this$0.getContext(), WebLongSaveActivity.class).putExtra("web_url", item.getUrl()).putExtra("title", item.getTitle()));
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
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = holder.getLayoutPosition() == 0 ? 0 : SizeUtil.dp2px(getContext(), 20);
        holder.itemView.setLayoutParams(layoutParams2);
        holder.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_time, item.getUpdate_time());
        String thumb_url = item.getThumb_url();
        if (thumb_url == null || thumb_url.length() == 0) {
            return;
        }
        ImageView imageView = (ImageView) holder.getView(R.id.iv_img);
        ViewExtensionsKt.setCornerRadius(imageView, 8);
        Glide.with(getContext()).load((Object) new GlideUrl(item.getThumb_url(), new Headers() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.n
            @Override // com.bumptech.glide.load.model.Headers
            public final Map getHeaders() {
                return WxInfoAdapter.convert$lambda$1();
            }
        })).placeholder(R.mipmap.ic_order_default).into(imageView);
    }
}
