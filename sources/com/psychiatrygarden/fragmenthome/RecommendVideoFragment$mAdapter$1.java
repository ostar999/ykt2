package com.psychiatrygarden.fragmenthome;

import android.text.TextUtils;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.RecommendVideoBean;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/fragmenthome/RecommendVideoFragment$mAdapter$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/RecommendVideoBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class RecommendVideoFragment$mAdapter$1 extends BaseQuickAdapter<RecommendVideoBean, BaseViewHolder> {
    public RecommendVideoFragment$mAdapter$1(final RecommendVideoFragment recommendVideoFragment) {
        super(R.layout.item_recommend_video, null, 2, null);
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.zb
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                RecommendVideoFragment$mAdapter$1._init_$lambda$0(this.f16166c, recommendVideoFragment, baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void _init_$lambda$0(com.psychiatrygarden.fragmenthome.RecommendVideoFragment$mAdapter$1 r9, com.psychiatrygarden.fragmenthome.RecommendVideoFragment r10, com.chad.library.adapter.base.BaseQuickAdapter r11, android.view.View r12, int r13) {
        /*
            java.lang.String r0 = "this$0"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            java.lang.String r0 = "this$1"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
            java.lang.String r0 = "<anonymous parameter 0>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r0)
            java.lang.String r11 = "<anonymous parameter 1>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r12, r11)
            java.lang.Object r9 = r9.getItem(r13)
            com.psychiatrygarden.bean.RecommendVideoBean r9 = (com.psychiatrygarden.bean.RecommendVideoBean) r9
            java.lang.String r11 = r9.getVid()
            java.lang.String r12 = r9.getId()
            java.lang.String r13 = "item.id"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r12, r13)
            com.psychiatrygarden.fragmenthome.RecommendVideoFragment.access$setItemId$p(r10, r12)
            boolean r12 = android.text.TextUtils.isEmpty(r11)
            if (r12 != 0) goto Ld0
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "LAST_RECOMMEND_VIDEO"
            r12.append(r13)
            java.lang.String r13 = com.psychiatrygarden.utils.CommonParameter.App_Id
            r12.append(r13)
            r13 = 95
            r12.append(r13)
            java.lang.String r0 = com.psychiatrygarden.fragmenthome.RecommendVideoFragment.access$getKnowledgeId$p(r10)
            r1 = 0
            java.lang.String r2 = "knowledgeId"
            if (r0 != 0) goto L51
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            r0 = r1
        L51:
            r12.append(r0)
            r12.append(r13)
            java.lang.String r13 = r9.getId()
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            android.content.Context r13 = com.psychiatrygarden.fragmenthome.RecommendVideoFragment.access$getMContext$p$s1827048623(r10)
            java.lang.String r3 = com.psychiatrygarden.db.SharePreferencesUtils.readStrConfig(r12, r13)
            boolean r12 = android.text.TextUtils.isEmpty(r3)
            r13 = 0
            if (r12 != 0) goto L93
            java.lang.String r12 = "lastVid"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r12)
            r12 = 2
            java.lang.String r0 = "_"
            boolean r12 = kotlin.text.StringsKt.contains$default(r3, r0, r13, r12, r1)
            if (r12 == 0) goto L93
            java.lang.String[] r4 = new java.lang.String[]{r0}
            r5 = 0
            r6 = 0
            r7 = 6
            r8 = 0
            java.util.List r12 = kotlin.text.StringsKt.split$default(r3, r4, r5, r6, r7, r8)
            r0 = 1
            java.lang.Object r12 = r12.get(r0)
            java.lang.String r12 = (java.lang.String) r12
            goto L95
        L93:
            java.lang.String r12 = ""
        L95:
            android.content.Intent r0 = new android.content.Intent
            android.content.Context r3 = com.psychiatrygarden.fragmenthome.RecommendVideoFragment.access$getMContext$p$s1827048623(r10)
            java.lang.Class<com.psychiatrygarden.activity.comment.alipler.AliperCommentActivity> r4 = com.psychiatrygarden.activity.comment.alipler.AliperCommentActivity.class
            r0.<init>(r3, r4)
            java.lang.String r3 = "vid"
            r0.putExtra(r3, r11)
            java.lang.String r11 = "watch_permission"
            java.lang.String r3 = "1"
            r0.putExtra(r11, r3)
            java.lang.String r11 = "obj_id"
            java.lang.String r9 = r9.getId()
            r0.putExtra(r11, r9)
            java.lang.String r9 = com.psychiatrygarden.fragmenthome.RecommendVideoFragment.access$getKnowledgeId$p(r10)
            if (r9 != 0) goto Lbf
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            goto Lc0
        Lbf:
            r1 = r9
        Lc0:
            r0.putExtra(r2, r1)
            java.lang.String r9 = "seeDuration"
            r0.putExtra(r9, r12)
            java.lang.String r9 = "showDiscuss"
            r0.putExtra(r9, r13)
            r10.startActivity(r0)
        Ld0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.RecommendVideoFragment$mAdapter$1._init_$lambda$0(com.psychiatrygarden.fragmenthome.RecommendVideoFragment$mAdapter$1, com.psychiatrygarden.fragmenthome.RecommendVideoFragment, com.chad.library.adapter.base.BaseQuickAdapter, android.view.View, int):void");
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull RecommendVideoBean item) throws NumberFormatException {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        BaseViewHolder text = holder.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_teacher_name, item.getTeacher_name());
        String teacher_name = item.getTeacher_name();
        text.setGone(R.id.tv_teacher_name, teacher_name == null || teacher_name.length() == 0).setGone(R.id.tv_last_see, !item.isLastSee());
        String duration = item.getDuration();
        String currentSee = item.getSee();
        if (TextUtils.isEmpty(duration)) {
            holder.setGone(R.id.tv_time, true);
        } else {
            StringBuilder sb = new StringBuilder();
            Intrinsics.checkNotNullExpressionValue(duration, "duration");
            sb.append(Integer.parseInt(duration) / 60);
            sb.append("分钟");
            holder.setText(R.id.tv_time, sb.toString());
        }
        if (!(duration == null || duration.length() == 0)) {
            if (!(currentSee == null || currentSee.length() == 0)) {
                Intrinsics.checkNotNullExpressionValue(duration, "duration");
                int i2 = Integer.parseInt(duration);
                Intrinsics.checkNotNullExpressionValue(currentSee, "currentSee");
                int i3 = Integer.parseInt(currentSee);
                if (i2 == 0 || i3 == 0) {
                    holder.setText(R.id.tv_learn, "未学习");
                    return;
                }
                holder.setText(R.id.tv_learn, "已学习" + ((int) (((i3 * 1.0f) / i2) * 1.0f * 100)) + '%');
                return;
            }
        }
        holder.setText(R.id.tv_learn, "未学习");
    }
}
