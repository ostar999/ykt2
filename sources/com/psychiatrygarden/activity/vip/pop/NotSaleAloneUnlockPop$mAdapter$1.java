package com.psychiatrygarden.activity.vip.pop;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.bean.ParentCourseBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/activity/vip/pop/NotSaleAloneUnlockPop$mAdapter$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/ParentCourseBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class NotSaleAloneUnlockPop$mAdapter$1 extends BaseQuickAdapter<ParentCourseBean, BaseViewHolder> {
    final /* synthetic */ Context $context;
    final /* synthetic */ NotSaleAloneUnlockPop this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotSaleAloneUnlockPop$mAdapter$1(final Context context, final NotSaleAloneUnlockPop notSaleAloneUnlockPop) {
        super(R.layout.layout_mem_item, null, 2, null);
        this.$context = context;
        this.this$0 = notSaleAloneUnlockPop;
        setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.h
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                NotSaleAloneUnlockPop$mAdapter$1._init_$lambda$0(context, this, notSaleAloneUnlockPop, baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(Context context, NotSaleAloneUnlockPop$mAdapter$1 this$0, NotSaleAloneUnlockPop this$1, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this$1, "this$1");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        context.startActivity(new Intent(context, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("course_id", this$0.getItem(i2).getId()).putExtra("buyNotSaleAlone", true));
        this$1.dismiss();
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull ParentCourseBean item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        holder.setText(R.id.mfristTxt, item.getTitle()).setText(R.id.mDofristTxt, item.getDescription()).setVisible(R.id.mgdsimg, false);
        holder.getView(R.id.ly_item).setSelected(SkinManager.getCurrentSkinType(this.$context) != 1);
        Glide.with(this.this$0.getContext()).load((Object) GlideUtils.generateUrl(item.getIcon())).error(R.mipmap.lock_way).placeholder(new ColorDrawable(ContextCompat.getColor(this.$context, SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into((ImageView) holder.getView(R.id.leftimg));
    }
}
