package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.bean.CourseGiftItem;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReadBookActivity;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000/\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u000b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u0010H\u0014R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\f¨\u0006\u0011"}, d2 = {"Lcom/psychiatrygarden/widget/CourseGiftPop;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "list", "", "Lcom/psychiatrygarden/bean/CourseGiftItem;", "(Landroid/content/Context;Ljava/util/List;)V", "getList", "()Ljava/util/List;", "mAdapter", "com/psychiatrygarden/widget/CourseGiftPop$mAdapter$1", "Lcom/psychiatrygarden/widget/CourseGiftPop$mAdapter$1;", "getImplLayoutId", "", "onCreate", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CourseGiftPop extends BottomPopupView {

    @NotNull
    private final List<CourseGiftItem> list;

    @NotNull
    private final CourseGiftPop$mAdapter$1 mAdapter;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.psychiatrygarden.widget.CourseGiftPop$mAdapter$1] */
    public CourseGiftPop(@NotNull final Context context, @NotNull List<? extends CourseGiftItem> list) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(list, "list");
        this.list = list;
        this.mAdapter = new BaseQuickAdapter<CourseGiftItem, BaseViewHolder>() { // from class: com.psychiatrygarden.widget.CourseGiftPop$mAdapter$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(R.layout.item_sku_gift, null, 2, null);
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder holder, @NotNull CourseGiftItem item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                GlideUtils.loadImage(context, item.getCover(), (ImageView) holder.getView(R.id.iv_icon), R.mipmap.ic_order_default, R.mipmap.ic_order_default);
                holder.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_count, 'x' + item.getCount());
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(CourseGiftPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public static final void onCreate$lambda$1(CourseGiftPop this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        CourseGiftItem item = this$0.mAdapter.getItem(i2);
        String id = item.getId();
        String type = item.getType();
        if (type != null) {
            switch (type.hashCode()) {
                case 49:
                    if (type.equals("1")) {
                        Context context = this$0.getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "context");
                        Intrinsics.checkNotNullExpressionValue(id, "id");
                        NavigationUtilKt.gotoGoodsDetail(context, id);
                        break;
                    }
                    break;
                case 50:
                    if (type.equals("2")) {
                        ActCourseOrGoodsDetail.Companion companion = ActCourseOrGoodsDetail.INSTANCE;
                        Context context2 = this$0.getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "context");
                        Intrinsics.checkNotNullExpressionValue(id, "id");
                        companion.navigationToCourseOrGoodsDetail(context2, id, "");
                        break;
                    }
                    break;
                case 51:
                    if (type.equals("3")) {
                        Context context3 = this$0.getContext();
                        Intrinsics.checkNotNullExpressionValue(context3, "context");
                        NavigationUtilKt.gotoVipCenter(context3);
                        break;
                    }
                    break;
                case 52:
                    if (type.equals("4")) {
                        String appId = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                        String admin = UserConfig.getInstance().getUser().getAdmin();
                        String avatar = UserConfig.getInstance().getUser().getAvatar();
                        Context context4 = this$0.getContext();
                        ReadBookActivity.Companion companion2 = ReadBookActivity.INSTANCE;
                        Context context5 = this$0.getContext();
                        Intrinsics.checkNotNullExpressionValue(context5, "context");
                        Intrinsics.checkNotNullExpressionValue(id, "id");
                        String userId = UserConfig.getUserId();
                        Intrinsics.checkNotNullExpressionValue(userId, "getUserId()");
                        Intrinsics.checkNotNullExpressionValue(appId, "appId");
                        Intrinsics.checkNotNullExpressionValue(admin, "admin");
                        Intrinsics.checkNotNullExpressionValue(avatar, "avatar");
                        String token = UserConfig.getInstance().getUser().getToken();
                        Intrinsics.checkNotNullExpressionValue(token, "getInstance().user.token");
                        String secret = UserConfig.getInstance().getUser().getSecret();
                        Intrinsics.checkNotNullExpressionValue(secret, "getInstance().user.secret");
                        context4.startActivity(companion2.newIntent(context5, id, userId, appId, admin, avatar, token, secret));
                        break;
                    }
                    break;
                case 53:
                    if (type.equals("5")) {
                        InformationPreviewAct.newIntent(this$0.getContext(), id, "", false);
                        break;
                    }
                    break;
            }
        }
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_course_gift;
    }

    @NotNull
    public final List<CourseGiftItem> getList() {
        return this.list;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.w3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseGiftPop.onCreate$lambda$0(this.f17029c, view);
            }
        });
        ((RecyclerView) findViewById(R.id.rvGift)).setAdapter(this.mAdapter);
        setList(this.list);
        View viewFindViewById = findViewById(R.id.tv_gift);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tv_gift)");
        ((TextView) viewFindViewById).setText("赠品（共" + getData().size() + "件）");
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.x3
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                CourseGiftPop.onCreate$lambda$1(this.f17074c, baseQuickAdapter, view, i2);
            }
        });
    }
}
