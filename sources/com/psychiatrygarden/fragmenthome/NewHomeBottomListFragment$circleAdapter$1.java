package com.psychiatrygarden.fragmenthome;

import android.content.Intent;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.config.UserConfig;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/fragmenthome/NewHomeBottomListFragment$circleAdapter$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/CirclrListBean$DataBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "vHolder", "dataBean", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class NewHomeBottomListFragment$circleAdapter$1 extends BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> {
    final /* synthetic */ NewHomeBottomListFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewHomeBottomListFragment$circleAdapter$1(NewHomeBottomListFragment newHomeBottomListFragment) {
        super(R.layout.item_circle_home_style, null, 2, null);
        this.this$0 = newHomeBottomListFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$0(NewHomeBottomListFragment$circleAdapter$1 this$0, BaseViewHolder vHolder, NewHomeBottomListFragment this$1, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(vHolder, "$vHolder");
        Intrinsics.checkNotNullParameter(this$1, "this$1");
        if (Intrinsics.areEqual("1", this$0.getData().get(vHolder.getLayoutPosition()).getNo_access())) {
            this$1.startActivity(new Intent(this$1.getActivity(), (Class<?>) MemberCenterActivity.class));
            return;
        }
        Intent intent = new Intent(((BaseFragment) this$1).mContext, (Class<?>) CircleInfoActivity.class);
        intent.putExtra("channel_id", "0");
        intent.putExtra("article_id", this$0.getData().get(vHolder.getLayoutPosition()).getId());
        intent.putExtra("module_type", Constants.VIA_REPORT_TYPE_SET_AVATAR);
        intent.putExtra("commentCount", this$0.getData().get(vHolder.getLayoutPosition()).getComment_count());
        this$1.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$1(CirclrListBean.DataBean dataBean, NewHomeBottomListFragment this$0, NewHomeBottomListFragment$circleAdapter$1 this$1, View view) {
        Intrinsics.checkNotNullParameter(dataBean, "$dataBean");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this$1, "this$1");
        if (!UserConfig.isLogin()) {
            this$1.getContext().startActivity(new Intent(this$1.getContext(), (Class<?>) LoginActivity.class));
        } else {
            if (Intrinsics.areEqual("1", dataBean.getNo_access())) {
                this$0.startActivity(new Intent(this$0.getActivity(), (Class<?>) MemberCenterActivity.class));
                return;
            }
            Intent intent = new Intent(((BaseFragment) this$0).mContext, (Class<?>) CircleInfoActivity.class);
            intent.putExtra("channel_id", "0");
            intent.putExtra("article_id", dataBean.getId());
            intent.putExtra("module_type", Constants.VIA_REPORT_TYPE_SET_AVATAR);
            intent.putExtra("commentCount", dataBean.getComment_count());
            this$0.startActivity(intent);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x02c6  */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void convert(@org.jetbrains.annotations.NotNull final com.chad.library.adapter.base.viewholder.BaseViewHolder r21, @org.jetbrains.annotations.NotNull final com.psychiatrygarden.bean.CirclrListBean.DataBean r22) {
        /*
            Method dump skipped, instructions count: 1067
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.NewHomeBottomListFragment$circleAdapter$1.convert(com.chad.library.adapter.base.viewholder.BaseViewHolder, com.psychiatrygarden.bean.CirclrListBean$DataBean):void");
    }
}
