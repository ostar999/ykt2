package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.gson.Gson;
import com.lxj.xpopup.core.BottomPopupView;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.coupon.adapter.CouponAdapter;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.bean.BottomBtn;
import com.psychiatrygarden.bean.CourseDetailBean;
import com.psychiatrygarden.bean.CourseGiftItem;
import com.psychiatrygarden.bean.CourseLabel;
import com.psychiatrygarden.bean.GetCouponsBean;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.bean.SkuItem;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReadBookActivity;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0002\u0018\u001b\u0018\u00002\u00020\u00012\u00020\u0002:\u0001.B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001a\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\b\b\u0002\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020#H\u0014J\u001a\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001e2\b\b\u0002\u0010'\u001a\u00020!H\u0002J\u0010\u0010(\u001a\u00020%2\u0006\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020%H\u0014J\b\u0010,\u001a\u00020%H\u0002J\u001a\u0010-\u001a\u00020%2\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010'\u001a\u00020!H\u0002R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0019R\u0010\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001c¨\u0006/"}, d2 = {"Lcom/psychiatrygarden/widget/CourseSkuPop;", "Lcom/lxj/xpopup/core/BottomPopupView;", "Landroid/view/View$OnClickListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "data", "Lcom/psychiatrygarden/bean/CourseDetailBean;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/psychiatrygarden/widget/CourseSkuPop$SkuUpdateListener;", "(Landroid/content/Context;Lcom/psychiatrygarden/bean/CourseDetailBean;Lcom/psychiatrygarden/widget/CourseSkuPop$SkuUpdateListener;)V", "courseDetailBean", "getListener", "()Lcom/psychiatrygarden/widget/CourseSkuPop$SkuUpdateListener;", "llBuyNow", "Landroid/widget/LinearLayout;", "llBuyVipStudy", "llJoinActivitiesStudy", "llNotSaleAlone", "llSoldOut", "llStartStudy", "llStopSale", "llTakeNow", "llUnlock", "skuAdapter", "com/psychiatrygarden/widget/CourseSkuPop$skuAdapter$1", "Lcom/psychiatrygarden/widget/CourseSkuPop$skuAdapter$1;", "tagAdapter", "com/psychiatrygarden/widget/CourseSkuPop$tagAdapter$1", "Lcom/psychiatrygarden/widget/CourseSkuPop$tagAdapter$1;", "checkColor", "", "color", "fontColor", "", "getImplLayoutId", "", "loadCourseDetail", "", "courseId", PLVUpdateMicSiteEvent.EVENT_NAME, "onClick", "v", "Landroid/view/View;", "onCreate", "showBottomBtn", "showUi", "SkuUpdateListener", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCourseSkuPop.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CourseSkuPop.kt\ncom/psychiatrygarden/widget/CourseSkuPop\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,616:1\n2634#2:617\n766#2:620\n857#2,2:621\n2634#2:623\n766#2:626\n857#2,2:627\n2645#2:629\n1864#2,3:631\n1#3:618\n1#3:619\n1#3:624\n1#3:625\n1#3:630\n*S KotlinDebug\n*F\n+ 1 CourseSkuPop.kt\ncom/psychiatrygarden/widget/CourseSkuPop\n*L\n204#1:617\n210#1:620\n210#1:621,2\n270#1:623\n573#1:626\n573#1:627,2\n274#1:629\n274#1:631,3\n204#1:618\n270#1:624\n274#1:630\n*E\n"})
/* loaded from: classes6.dex */
public final class CourseSkuPop extends BottomPopupView implements View.OnClickListener {

    @Nullable
    private CourseDetailBean courseDetailBean;

    @NotNull
    private final CourseDetailBean data;

    @NotNull
    private final SkuUpdateListener listener;
    private LinearLayout llBuyNow;
    private LinearLayout llBuyVipStudy;
    private LinearLayout llJoinActivitiesStudy;
    private LinearLayout llNotSaleAlone;
    private LinearLayout llSoldOut;
    private LinearLayout llStartStudy;
    private LinearLayout llStopSale;
    private LinearLayout llTakeNow;
    private LinearLayout llUnlock;

    @NotNull
    private final CourseSkuPop$skuAdapter$1 skuAdapter;

    @NotNull
    private final CourseSkuPop$tagAdapter$1 tagAdapter;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/psychiatrygarden/widget/CourseSkuPop$SkuUpdateListener;", "", "updateCourseShow", "", "courseId", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface SkuUpdateListener {
        void updateCourseShow(@NotNull String courseId);
    }

    @Metadata(d1 = {"\u0000+\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014J(\u0010\t\u001a\u00020\u00062\u000e\u0010\n\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"com/psychiatrygarden/widget/CourseSkuPop$showUi$6", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/CourseGiftItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "Lcom/chad/library/adapter/base/listener/OnItemClickListener;", "convert", "", "holder", "item", "onItemClick", "adapter", "view", "Landroid/view/View;", "position", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.widget.CourseSkuPop$showUi$6, reason: invalid class name */
    public static final class AnonymousClass6 extends BaseQuickAdapter<CourseGiftItem, BaseViewHolder> implements OnItemClickListener {
        public AnonymousClass6(ArrayList<CourseGiftItem> arrayList) {
            super(R.layout.item_sku_gift, arrayList);
            setOnItemClickListener(this);
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
        java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
         */
        @Override // com.chad.library.adapter.base.listener.OnItemClickListener
        public void onItemClick(@NotNull BaseQuickAdapter<?, ?> adapter, @NotNull View view, int position) {
            Intrinsics.checkNotNullParameter(adapter, "adapter");
            Intrinsics.checkNotNullParameter(view, "view");
            Object item = adapter.getItem(position);
            Intrinsics.checkNotNull(item, "null cannot be cast to non-null type com.psychiatrygarden.bean.CourseGiftItem");
            CourseGiftItem courseGiftItem = (CourseGiftItem) item;
            String id = courseGiftItem.getId();
            String type = courseGiftItem.getType();
            if (type != null) {
                switch (type.hashCode()) {
                    case 49:
                        if (type.equals("1")) {
                            Context context = getContext();
                            Intrinsics.checkNotNullExpressionValue(id, "id");
                            NavigationUtilKt.gotoGoodsDetail(context, id);
                            break;
                        }
                        break;
                    case 50:
                        if (type.equals("2")) {
                            ActCourseOrGoodsDetail.Companion companion = ActCourseOrGoodsDetail.INSTANCE;
                            Context context2 = getContext();
                            Intrinsics.checkNotNullExpressionValue(id, "id");
                            companion.navigationToCourseOrGoodsDetail(context2, id, "");
                            break;
                        }
                        break;
                    case 51:
                        if (type.equals("3")) {
                            NavigationUtilKt.gotoVipCenter(getContext());
                            break;
                        }
                        break;
                    case 52:
                        if (type.equals("4")) {
                            String appId = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                            String admin = UserConfig.getInstance().getUser().getAdmin();
                            String avatar = UserConfig.getInstance().getUser().getAvatar();
                            Context context3 = getContext();
                            ReadBookActivity.Companion companion2 = ReadBookActivity.INSTANCE;
                            Context context4 = getContext();
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
                            context3.startActivity(companion2.newIntent(context4, id, userId, appId, admin, avatar, token, secret));
                            break;
                        }
                        break;
                    case 53:
                        if (type.equals("5")) {
                            InformationPreviewAct.newIntent(getContext(), id, "", false);
                            break;
                        }
                        break;
                }
            }
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull CourseGiftItem item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            GlideUtils.loadImageDef(getContext(), item.getCover(), (ImageView) holder.getView(R.id.iv_icon));
            holder.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_count, 'x' + item.getCount());
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.psychiatrygarden.widget.CourseSkuPop$tagAdapter$1] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.psychiatrygarden.widget.CourseSkuPop$skuAdapter$1] */
    public CourseSkuPop(@NotNull final Context context, @NotNull CourseDetailBean data, @NotNull SkuUpdateListener listener) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.data = data;
        this.listener = listener;
        this.tagAdapter = new BaseQuickAdapter<CourseLabel, BaseViewHolder>() { // from class: com.psychiatrygarden.widget.CourseSkuPop$tagAdapter$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(R.layout.item_sku_detail_tag, null, 2, null);
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder holder, @NotNull CourseLabel item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
                RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
                ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = holder.getLayoutPosition() == 0 ? 0 : SizeUtil.dp2px(context, 8);
                holder.itemView.setLayoutParams(layoutParams2);
                View view = holder.itemView;
                Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.widget.TextView");
                ((TextView) view).setText(item.getValue());
                View view2 = holder.itemView;
                Intrinsics.checkNotNull(view2, "null cannot be cast to non-null type android.widget.TextView");
                ((TextView) view2).getPaint().setFakeBoldText(true);
                GradientDrawable gradientDrawable = new GradientDrawable();
                CourseSkuPop courseSkuPop = this;
                Context context2 = context;
                gradientDrawable.setColor(Color.parseColor(courseSkuPop.checkColor(item.getBgColor(), false)));
                gradientDrawable.setCornerRadius(SizeUtil.dp2px(context2, 4) * 1.0f);
                View view3 = holder.itemView;
                Intrinsics.checkNotNull(view3, "null cannot be cast to non-null type android.widget.TextView");
                ((TextView) view3).setTextColor(Color.parseColor(this.checkColor(item.getFontColor(), true)));
                if (!item.getPromotion() && !item.getCoupon() && !item.getGift()) {
                    gradientDrawable.setStroke((int) TypedValue.applyDimension(1, 0.5f, context.getResources().getDisplayMetrics()), Color.parseColor(this.checkColor(item.getFontColor(), true)));
                }
                holder.itemView.setBackground(gradientDrawable);
            }
        };
        this.skuAdapter = new BaseQuickAdapter<SkuItem, BaseViewHolder>(context) { // from class: com.psychiatrygarden.widget.CourseSkuPop$skuAdapter$1
            final /* synthetic */ Context $context;
            private int availableWidth;
            private int bgColor;

            @NotNull
            private final ArrayMap<Integer, Integer> skuNameLineMap;
            private int textColor;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(R.layout.item_pop_sku, null, 2, null);
                this.$context = context;
                this.skuNameLineMap = new ArrayMap<>();
                if (SkinManager.getCurrentSkinType(context) == 0) {
                    this.bgColor = context.getColor(R.color.main_theme_color);
                    this.textColor = -1;
                } else {
                    this.bgColor = context.getColor(R.color.main_theme_color_night);
                    this.textColor = context.getColor(R.color.color_121622);
                }
                this.availableWidth = context.getResources().getDisplayMetrics().widthPixels - context.getResources().getDimensionPixelSize(R.dimen.dp_80);
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, SkuItem skuItem, List list) {
                convert2(baseViewHolder, skuItem, (List<? extends Object>) list);
            }

            /* renamed from: convert, reason: avoid collision after fix types in other method */
            public void convert2(@NotNull BaseViewHolder holder, @NotNull SkuItem item, @NotNull List<? extends Object> payloads) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                Intrinsics.checkNotNullParameter(payloads, "payloads");
                TextView textView = (TextView) holder.getView(R.id.tv_sku_name);
                holder.setGone(R.id.iv_exp, true);
                holder.getView(R.id.ll_sku).setSelected(item.getSelect());
                textView.setSelected(item.getSelect());
                if (item.getClickExpand()) {
                    String title = item.getTitle();
                    if (title == null) {
                        title = item.getTitle_sku();
                    }
                    textView.setText(title);
                }
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder holder, @NotNull SkuItem item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                String title = item.getTitle();
                if (title == null) {
                    title = item.getTitle_sku();
                }
                holder.setText(R.id.tv_sku_name, title).setGone(R.id.tv_label, TextUtils.isEmpty(item.getLabel()));
                TextView textView = (TextView) holder.getView(R.id.tv_sku_name);
                TextPaint paint = textView.getPaint();
                String title2 = item.getTitle();
                if (title2 == null) {
                    title2 = item.getTitle_sku();
                }
                String title3 = item.getTitle();
                if (title3 == null) {
                    title3 = item.getTitle_sku();
                }
                float fMeasureText = paint.measureText(title3);
                float fDp2px = this.$context.getResources().getDisplayMetrics().widthPixels - (SizeUtil.dp2px(this.$context, 84) * 1.0f);
                ViewGroup.LayoutParams layoutParams = holder.getView(R.id.tv_label).getLayoutParams();
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                if (fMeasureText > fDp2px && !item.getClickExpand()) {
                    int length = title2 != null ? title2.length() : 0;
                    int i2 = 0;
                    while (i2 < length) {
                        int i3 = (i2 + length) / 2;
                        if (paint.measureText(title2, 0, i3) <= fDp2px) {
                            i2 = i3 + 1;
                        } else {
                            length = i3;
                        }
                    }
                    if (i2 > 3) {
                        holder.setGone(R.id.iv_exp, false);
                        layoutParams2.rightMargin = this.$context.getResources().getDimensionPixelSize(R.dimen.dp_24);
                        StringBuilder sb = new StringBuilder();
                        Intrinsics.checkNotNull(title2);
                        sb.append((Object) title2.subSequence(0, i2 - 3));
                        sb.append("...");
                        textView.setText(sb.toString());
                    }
                } else {
                    textView.setText(title2);
                    holder.setGone(R.id.iv_exp, true);
                    layoutParams2.rightMargin = 0;
                }
                holder.getView(R.id.tv_label).setLayoutParams(layoutParams2);
                textView.setSelected(item.getSelect());
                holder.getView(R.id.ll_sku).setSelected(item.getSelect());
                if (TextUtils.isEmpty(item.getLabel())) {
                    return;
                }
                TextView textView2 = (TextView) holder.getView(R.id.tv_label);
                GradientDrawable gradientDrawable = new GradientDrawable();
                Context context2 = this.$context;
                gradientDrawable.setColor(this.bgColor);
                gradientDrawable.setCornerRadius(context2.getResources().getDimensionPixelSize(R.dimen.dp_100) * 1.0f);
                textView2.setBackground(gradientDrawable);
                holder.setText(R.id.tv_label, item.getLabel()).setTextColor(R.id.tv_label, this.textColor);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String checkColor(String color, boolean fontColor) {
        Regex regex = new Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$");
        if (TextUtils.isEmpty(color) || !regex.matches(color)) {
            return !fontColor ? "#F95843" : "#141516";
        }
        if (regex.matches(color) || color.charAt(0) != '#' || color.length() != 4) {
            return color;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('#');
        sb.append(color.charAt(1));
        sb.append(color.charAt(1));
        sb.append(color.charAt(2));
        sb.append(color.charAt(2));
        sb.append(color.charAt(3));
        sb.append(color.charAt(3));
        return sb.toString();
    }

    public static /* synthetic */ String checkColor$default(CourseSkuPop courseSkuPop, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return courseSkuPop.checkColor(str, z2);
    }

    private final void loadCourseDetail(String courseId, final boolean update) throws PackageManager.NameNotFoundException {
        String str = this.data.getDataType() == 2 ? "goods_id" : "id";
        String str2 = this.data.getDataType() == 2 ? "GET" : "POST";
        Context context = getContext();
        String str3 = this.data.getDataType() == 2 ? NetworkRequestsURL.goodsDetailNewURL : NetworkRequestsURL.courseDetail;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(str, courseId);
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.request(str2, context, str3, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.CourseSkuPop.loadCourseDetail.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String result) {
                Intrinsics.checkNotNullParameter(result, "result");
                try {
                    JSONObject jSONObject = new JSONObject(result);
                    CourseSkuPop.this.courseDetailBean = (CourseDetailBean) new Gson().fromJson(jSONObject.optString("data"), CourseDetailBean.class);
                    CourseDetailBean courseDetailBean = CourseSkuPop.this.courseDetailBean;
                    Intrinsics.checkNotNull(courseDetailBean);
                    courseDetailBean.setDataType(CourseSkuPop.this.data.getDataType());
                    CourseSkuPop courseSkuPop = CourseSkuPop.this;
                    CourseDetailBean courseDetailBean2 = courseSkuPop.courseDetailBean;
                    Intrinsics.checkNotNull(courseDetailBean2);
                    courseSkuPop.showUi(courseDetailBean2, update);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static /* synthetic */ void loadCourseDetail$default(CourseSkuPop courseSkuPop, String str, boolean z2, int i2, Object obj) throws PackageManager.NameNotFoundException {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        courseSkuPop.loadCourseDetail(str, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(CourseSkuPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(CourseSkuPop this$0, View view) {
        CourseDetailBean courseDetailBean;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!UserConfig.isLogin() || (courseDetailBean = this$0.courseDetailBean) == null) {
            return;
        }
        if (courseDetailBean.hasPermission()) {
            if (courseDetailBean.getAfterCs() != null) {
                Context context = this$0.getContext();
                Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
                CommonUtil.onlineService((Activity) context, courseDetailBean.getAfterCs());
                return;
            }
            return;
        }
        if (courseDetailBean.getBeforeCs() != null) {
            Context context2 = this$0.getContext();
            Intrinsics.checkNotNull(context2, "null cannot be cast to non-null type android.app.Activity");
            CommonUtil.onlineService((Activity) context2, courseDetailBean.getBeforeCs());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(CourseSkuPop this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LogUtils.d("pop_height", String.valueOf(this$0.bottomPopupContainer.getMeasuredHeight()));
    }

    private final void showBottomBtn() {
        Object next;
        Object next2;
        Object next3;
        Object next4;
        Object next5;
        Object next6;
        Object next7;
        Object next8;
        Object next9;
        Object next10;
        Object next11;
        Object next12;
        Object next13;
        Object next14;
        Object next15;
        Object next16;
        Object next17;
        Object next18;
        CourseDetailBean courseDetailBean = this.courseDetailBean;
        Intrinsics.checkNotNull(courseDetailBean);
        ArrayList<BottomBtn> btnList = courseDetailBean.getBtnList();
        Intrinsics.checkNotNull(btnList);
        LinearLayout linearLayout = this.llStartStudy;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llStartStudy");
            linearLayout = null;
        }
        Iterator<T> it = btnList.iterator();
        while (true) {
            if (it.hasNext()) {
                next = it.next();
                if (Intrinsics.areEqual("1", ((BottomBtn) next).getType())) {
                    break;
                }
            } else {
                next = null;
                break;
            }
        }
        linearLayout.setVisibility(next != null ? 0 : 8);
        LinearLayout linearLayout3 = this.llStartStudy;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llStartStudy");
            linearLayout3 = null;
        }
        Iterator<T> it2 = btnList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                next2 = null;
                break;
            }
            next2 = it2.next();
            BottomBtn bottomBtn = (BottomBtn) next2;
            if (Intrinsics.areEqual("1", bottomBtn.getType()) && Intrinsics.areEqual("0", bottomBtn.getDisable())) {
                break;
            }
        }
        linearLayout3.setEnabled(next2 != null);
        LinearLayout linearLayout4 = this.llStartStudy;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llStartStudy");
            linearLayout4 = null;
        }
        if (linearLayout4.isEnabled()) {
            LinearLayout linearLayout5 = this.llStartStudy;
            if (linearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llStartStudy");
                linearLayout5 = null;
            }
            linearLayout5.setOnClickListener(this);
        }
        LinearLayout linearLayout6 = this.llTakeNow;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llTakeNow");
            linearLayout6 = null;
        }
        Iterator<T> it3 = btnList.iterator();
        while (true) {
            if (!it3.hasNext()) {
                next3 = null;
                break;
            }
            next3 = it3.next();
            BottomBtn bottomBtn2 = (BottomBtn) next3;
            if (Intrinsics.areEqual("2", bottomBtn2.getType()) || Intrinsics.areEqual(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, bottomBtn2.getType()) || Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_MINI_PROGRAM, bottomBtn2.getType())) {
                break;
            }
        }
        linearLayout6.setVisibility(next3 != null ? 0 : 8);
        LinearLayout linearLayout7 = this.llTakeNow;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llTakeNow");
            linearLayout7 = null;
        }
        Iterator<T> it4 = btnList.iterator();
        while (true) {
            if (!it4.hasNext()) {
                next4 = null;
                break;
            }
            next4 = it4.next();
            BottomBtn bottomBtn3 = (BottomBtn) next4;
            if ((Intrinsics.areEqual("2", bottomBtn3.getType()) || Intrinsics.areEqual(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, bottomBtn3.getType()) || Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_MINI_PROGRAM, bottomBtn3.getType())) && Intrinsics.areEqual("0", bottomBtn3.getDisable())) {
                break;
            }
        }
        linearLayout7.setEnabled(next4 != null);
        LinearLayout linearLayout8 = this.llTakeNow;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llTakeNow");
            linearLayout8 = null;
        }
        if (linearLayout8.isEnabled()) {
            Iterator<T> it5 = btnList.iterator();
            while (true) {
                if (!it5.hasNext()) {
                    next18 = null;
                    break;
                }
                next18 = it5.next();
                BottomBtn bottomBtn4 = (BottomBtn) next18;
                if (Intrinsics.areEqual("2", bottomBtn4.getType()) || Intrinsics.areEqual(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, bottomBtn4.getType()) || Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_MINI_PROGRAM, bottomBtn4.getType())) {
                    break;
                }
            }
            BottomBtn bottomBtn5 = (BottomBtn) next18;
            String type = bottomBtn5 != null ? bottomBtn5.getType() : null;
            LinearLayout linearLayout9 = this.llTakeNow;
            if (linearLayout9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llTakeNow");
                linearLayout9 = null;
            }
            linearLayout9.setTag(type != null ? Integer.valueOf(Integer.parseInt(type)) : 0);
            LinearLayout linearLayout10 = this.llTakeNow;
            if (linearLayout10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llTakeNow");
                linearLayout10 = null;
            }
            linearLayout10.setOnClickListener(this);
        }
        LinearLayout linearLayout11 = this.llSoldOut;
        if (linearLayout11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llSoldOut");
            linearLayout11 = null;
        }
        Iterator<T> it6 = btnList.iterator();
        while (true) {
            if (it6.hasNext()) {
                next5 = it6.next();
                if (Intrinsics.areEqual("3", ((BottomBtn) next5).getType())) {
                    break;
                }
            } else {
                next5 = null;
                break;
            }
        }
        linearLayout11.setVisibility(next5 != null ? 0 : 8);
        LinearLayout linearLayout12 = this.llSoldOut;
        if (linearLayout12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llSoldOut");
            linearLayout12 = null;
        }
        Iterator<T> it7 = btnList.iterator();
        while (true) {
            if (!it7.hasNext()) {
                next6 = null;
                break;
            }
            next6 = it7.next();
            BottomBtn bottomBtn6 = (BottomBtn) next6;
            if (Intrinsics.areEqual("3", bottomBtn6.getType()) && Intrinsics.areEqual("0", bottomBtn6.getDisable())) {
                break;
            }
        }
        linearLayout12.setEnabled(next6 != null);
        View viewFindViewById = findViewById(R.id.tv_sold_out);
        LinearLayout linearLayout13 = this.llSoldOut;
        if (linearLayout13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llSoldOut");
            linearLayout13 = null;
        }
        viewFindViewById.setEnabled(linearLayout13.isEnabled());
        LinearLayout linearLayout14 = this.llStopSale;
        if (linearLayout14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llStopSale");
            linearLayout14 = null;
        }
        Iterator<T> it8 = btnList.iterator();
        while (true) {
            if (it8.hasNext()) {
                next7 = it8.next();
                if (Intrinsics.areEqual("4", ((BottomBtn) next7).getType())) {
                    break;
                }
            } else {
                next7 = null;
                break;
            }
        }
        linearLayout14.setVisibility(next7 != null ? 0 : 8);
        LinearLayout linearLayout15 = this.llStopSale;
        if (linearLayout15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llStopSale");
            linearLayout15 = null;
        }
        Iterator<T> it9 = btnList.iterator();
        while (true) {
            if (!it9.hasNext()) {
                next8 = null;
                break;
            }
            next8 = it9.next();
            BottomBtn bottomBtn7 = (BottomBtn) next8;
            if (Intrinsics.areEqual("4", bottomBtn7.getType()) && Intrinsics.areEqual("0", bottomBtn7.getDisable())) {
                break;
            }
        }
        linearLayout15.setEnabled(next8 != null);
        View viewFindViewById2 = findViewById(R.id.tv_stop_sale);
        LinearLayout linearLayout16 = this.llStopSale;
        if (linearLayout16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llStopSale");
            linearLayout16 = null;
        }
        viewFindViewById2.setEnabled(linearLayout16.isEnabled());
        LinearLayout linearLayout17 = this.llNotSaleAlone;
        if (linearLayout17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llNotSaleAlone");
            linearLayout17 = null;
        }
        Iterator<T> it10 = btnList.iterator();
        while (true) {
            if (it10.hasNext()) {
                next9 = it10.next();
                if (Intrinsics.areEqual("5", ((BottomBtn) next9).getType())) {
                    break;
                }
            } else {
                next9 = null;
                break;
            }
        }
        linearLayout17.setVisibility(next9 != null ? 0 : 8);
        LinearLayout linearLayout18 = this.llUnlock;
        if (linearLayout18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llUnlock");
            linearLayout18 = null;
        }
        Iterator<T> it11 = btnList.iterator();
        while (true) {
            if (it11.hasNext()) {
                next10 = it11.next();
                if (Intrinsics.areEqual("5", ((BottomBtn) next10).getType())) {
                    break;
                }
            } else {
                next10 = null;
                break;
            }
        }
        linearLayout18.setVisibility(next10 != null ? 0 : 8);
        LinearLayout linearLayout19 = this.llUnlock;
        if (linearLayout19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llUnlock");
            linearLayout19 = null;
        }
        linearLayout19.setOnClickListener(this);
        LinearLayout linearLayout20 = this.llNotSaleAlone;
        if (linearLayout20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llNotSaleAlone");
            linearLayout20 = null;
        }
        Iterator<T> it12 = btnList.iterator();
        while (true) {
            if (!it12.hasNext()) {
                next11 = null;
                break;
            }
            next11 = it12.next();
            BottomBtn bottomBtn8 = (BottomBtn) next11;
            if (Intrinsics.areEqual("5", bottomBtn8.getType()) && Intrinsics.areEqual("0", bottomBtn8.getDisable())) {
                break;
            }
        }
        linearLayout20.setEnabled(next11 != null);
        View viewFindViewById3 = findViewById(R.id.tv_not_sale_alone);
        LinearLayout linearLayout21 = this.llNotSaleAlone;
        if (linearLayout21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llNotSaleAlone");
            linearLayout21 = null;
        }
        viewFindViewById3.setEnabled(linearLayout21.isEnabled());
        LinearLayout linearLayout22 = this.llNotSaleAlone;
        if (linearLayout22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llNotSaleAlone");
            linearLayout22 = null;
        }
        if (linearLayout22.isEnabled()) {
            LinearLayout linearLayout23 = this.llNotSaleAlone;
            if (linearLayout23 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llNotSaleAlone");
                linearLayout23 = null;
            }
            linearLayout23.setTag(5);
            LinearLayout linearLayout24 = this.llNotSaleAlone;
            if (linearLayout24 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llNotSaleAlone");
                linearLayout24 = null;
            }
            linearLayout24.setOnClickListener(this);
        }
        LinearLayout linearLayout25 = this.llBuyNow;
        if (linearLayout25 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBuyNow");
            linearLayout25 = null;
        }
        Iterator<T> it13 = btnList.iterator();
        while (true) {
            if (it13.hasNext()) {
                next12 = it13.next();
                if (Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_INFO, ((BottomBtn) next12).getType())) {
                    break;
                }
            } else {
                next12 = null;
                break;
            }
        }
        linearLayout25.setVisibility(next12 != null ? 0 : 8);
        LinearLayout linearLayout26 = this.llBuyNow;
        if (linearLayout26 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBuyNow");
            linearLayout26 = null;
        }
        Iterator<T> it14 = btnList.iterator();
        while (true) {
            if (!it14.hasNext()) {
                next13 = null;
                break;
            }
            next13 = it14.next();
            BottomBtn bottomBtn9 = (BottomBtn) next13;
            if (Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_INFO, bottomBtn9.getType()) && Intrinsics.areEqual("0", bottomBtn9.getDisable())) {
                break;
            }
        }
        linearLayout26.setEnabled(next13 != null);
        LinearLayout linearLayout27 = this.llBuyNow;
        if (linearLayout27 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBuyNow");
            linearLayout27 = null;
        }
        linearLayout27.setTag(6);
        LinearLayout linearLayout28 = this.llBuyNow;
        if (linearLayout28 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBuyNow");
            linearLayout28 = null;
        }
        if (linearLayout28.isEnabled()) {
            TextView textView = (TextView) findViewById(R.id.tv_buy);
            CourseDetailBean courseDetailBean2 = this.courseDetailBean;
            if (courseDetailBean2 != null) {
                ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> coupon = courseDetailBean2.getCoupon();
                if (!(coupon == null || coupon.isEmpty())) {
                    ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> coupon2 = courseDetailBean2.getCoupon();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : coupon2) {
                        if (Intrinsics.areEqual(((RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj).getIs_receive(), "0")) {
                            arrayList.add(obj);
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        textView.setText("领券购买");
                    }
                }
                Unit unit = Unit.INSTANCE;
            }
            LinearLayout linearLayout29 = this.llBuyNow;
            if (linearLayout29 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llBuyNow");
                linearLayout29 = null;
            }
            linearLayout29.setOnClickListener(this);
        }
        LinearLayout linearLayout30 = this.llBuyVipStudy;
        if (linearLayout30 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBuyVipStudy");
            linearLayout30 = null;
        }
        Iterator<T> it15 = btnList.iterator();
        while (true) {
            if (it15.hasNext()) {
                next14 = it15.next();
                if (Intrinsics.areEqual("7", ((BottomBtn) next14).getType())) {
                    break;
                }
            } else {
                next14 = null;
                break;
            }
        }
        linearLayout30.setVisibility(next14 != null ? 0 : 8);
        LinearLayout linearLayout31 = this.llBuyVipStudy;
        if (linearLayout31 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBuyVipStudy");
            linearLayout31 = null;
        }
        Iterator<T> it16 = btnList.iterator();
        while (true) {
            if (!it16.hasNext()) {
                next15 = null;
                break;
            }
            next15 = it16.next();
            BottomBtn bottomBtn10 = (BottomBtn) next15;
            if (Intrinsics.areEqual("7", bottomBtn10.getType()) && Intrinsics.areEqual("0", bottomBtn10.getDisable())) {
                break;
            }
        }
        linearLayout31.setEnabled(next15 != null);
        LinearLayout linearLayout32 = this.llBuyVipStudy;
        if (linearLayout32 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBuyVipStudy");
            linearLayout32 = null;
        }
        linearLayout32.setTag(7);
        LinearLayout linearLayout33 = this.llBuyVipStudy;
        if (linearLayout33 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBuyVipStudy");
            linearLayout33 = null;
        }
        if (linearLayout33.isEnabled()) {
            LinearLayout linearLayout34 = this.llBuyVipStudy;
            if (linearLayout34 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llBuyVipStudy");
                linearLayout34 = null;
            }
            linearLayout34.setOnClickListener(this);
        }
        LinearLayout linearLayout35 = this.llJoinActivitiesStudy;
        if (linearLayout35 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llJoinActivitiesStudy");
            linearLayout35 = null;
        }
        Iterator<T> it17 = btnList.iterator();
        while (true) {
            if (it17.hasNext()) {
                next16 = it17.next();
                if (Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, ((BottomBtn) next16).getType())) {
                    break;
                }
            } else {
                next16 = null;
                break;
            }
        }
        linearLayout35.setVisibility(next16 != null ? 0 : 8);
        LinearLayout linearLayout36 = this.llJoinActivitiesStudy;
        if (linearLayout36 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llJoinActivitiesStudy");
            linearLayout36 = null;
        }
        Iterator<T> it18 = btnList.iterator();
        while (true) {
            if (!it18.hasNext()) {
                next17 = null;
                break;
            }
            next17 = it18.next();
            BottomBtn bottomBtn11 = (BottomBtn) next17;
            if (Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, bottomBtn11.getType()) && Intrinsics.areEqual("0", bottomBtn11.getDisable())) {
                break;
            }
        }
        linearLayout36.setEnabled(next17 != null);
        LinearLayout linearLayout37 = this.llJoinActivitiesStudy;
        if (linearLayout37 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llJoinActivitiesStudy");
            linearLayout37 = null;
        }
        linearLayout37.setTag(8);
        LinearLayout linearLayout38 = this.llJoinActivitiesStudy;
        if (linearLayout38 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llJoinActivitiesStudy");
            linearLayout38 = null;
        }
        if (linearLayout38.isEnabled()) {
            LinearLayout linearLayout39 = this.llJoinActivitiesStudy;
            if (linearLayout39 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llJoinActivitiesStudy");
            } else {
                linearLayout2 = linearLayout39;
            }
            linearLayout2.setOnClickListener(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showUi(CourseDetailBean data, boolean update) {
        ArrayList arrayList;
        Context context = getContext();
        String cover = data.getCover();
        if (cover == null) {
            cover = "";
        }
        GlideUtils.loadImage(context, cover, (ImageView) findViewById(R.id.ivCover), R.mipmap.ic_order_default, R.mipmap.ic_order_default);
        if (data.getDataType() == 2) {
            Context context2 = getContext();
            String goods_thumbnail = data.getGoods_thumbnail();
            GlideUtils.loadImage(context2, goods_thumbnail != null ? goods_thumbnail : "", (ImageView) findViewById(R.id.ivCover), R.mipmap.ic_order_default, R.mipmap.ic_order_default);
        }
        View viewFindViewById = findViewById(R.id.tv_sku_name);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tv_sku_name)");
        ((TextView) viewFindViewById).setText(data.getTitle());
        RecyclerView rvTags = (RecyclerView) findViewById(R.id.rvTags);
        ArrayList<CourseLabel> label = data.getLabel();
        if (label == null || label.isEmpty()) {
            Intrinsics.checkNotNullExpressionValue(rvTags, "rvTags");
            ViewExtensionsKt.gone(rvTags);
        } else {
            ArrayList<CourseLabel> label2 = data.getLabel();
            Intrinsics.checkNotNull(label2);
            for (CourseLabel courseLabel : label2) {
                courseLabel.setPromotion(Intrinsics.areEqual("促", courseLabel.getValue()));
                courseLabel.setGift(Intrinsics.areEqual("赠", courseLabel.getValue()));
                courseLabel.setCoupon(Intrinsics.areEqual("券", courseLabel.getValue()));
            }
            Intrinsics.checkNotNullExpressionValue(rvTags, "rvTags");
            ViewExtensionsKt.visible(rvTags);
            CourseSkuPop$tagAdapter$1 courseSkuPop$tagAdapter$1 = this.tagAdapter;
            ArrayList<CourseLabel> label3 = data.getLabel();
            if (label3 != null) {
                arrayList = new ArrayList();
                for (Object obj : label3) {
                    if (!TextUtils.isEmpty(((CourseLabel) obj).getValue())) {
                        arrayList.add(obj);
                    }
                }
            } else {
                arrayList = null;
            }
            courseSkuPop$tagAdapter$1.setList(arrayList);
            rvTags.setAdapter(courseSkuPop$tagAdapter$1);
        }
        View viewFindViewById2 = findViewById(R.id.tv_sold);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tv_sold)");
        ((TextView) viewFindViewById2).setText(data.getSalesVolume() + "人购买");
        String originalPrice = data.getOriginalPrice();
        Intrinsics.checkNotNull(originalPrice);
        String strReplace$default = StringsKt__StringsJVMKt.replace$default(originalPrice, "¥", "", false, 4, (Object) null);
        String promotionPrice = data.getPromotionPrice();
        String strReplace$default2 = promotionPrice != null ? StringsKt__StringsJVMKt.replace$default(promotionPrice, "¥", "", false, 4, (Object) null) : null;
        View viewFindViewById3 = findViewById(R.id.tv_original_price);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tv_original_price)");
        TextView textView = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.tv_price);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.tv_price)");
        TextView textView2 = (TextView) viewFindViewById4;
        if (!Intrinsics.areEqual("1", data.isPromotion())) {
            strReplace$default2 = strReplace$default;
        }
        if (Intrinsics.areEqual("1", data.isPromotion())) {
            ViewExtensionsKt.visible(textView);
            SpannableStringBuilder spannableStringBuilderAppend = new SpannableStringBuilder("¥").append((CharSequence) StringsKt__StringsJVMKt.replace$default(strReplace$default, "¥", "", false, 4, (Object) null));
            spannableStringBuilderAppend.setSpan(new AbsoluteSizeSpan((int) TypedValue.applyDimension(2, 12.0f, getContext().getResources().getDisplayMetrics())), 0, spannableStringBuilderAppend.length(), 18);
            spannableStringBuilderAppend.setSpan(new StrikethroughSpan(), 0, spannableStringBuilderAppend.length(), 18);
            textView.setText(spannableStringBuilderAppend);
        } else {
            ViewExtensionsKt.gone(textView);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("¥");
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan((int) TypedValue.applyDimension(2, 11.0f, getContext().getResources().getDisplayMetrics())), 0, spannableStringBuilder.length(), 18);
        int length = spannableStringBuilder.length();
        if (data.getDataType() == 1) {
            spannableStringBuilder.append((CharSequence) strReplace$default2);
        } else {
            if (TextUtils.isEmpty(data.getPrice())) {
                spannableStringBuilder.append((CharSequence) strReplace$default2);
            } else {
                String price = data.getPrice();
                Intrinsics.checkNotNull(price);
                spannableStringBuilder.append((CharSequence) StringsKt__StringsJVMKt.replace$default(price, "¥", "", false, 4, (Object) null));
            }
            if (TextUtils.equals(data.getPrice(), data.getOriginalPrice())) {
                ViewExtensionsKt.gone(textView);
            }
        }
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan((int) TypedValue.applyDimension(2, 16.0f, getContext().getResources().getDisplayMetrics())), length, spannableStringBuilder.length(), 18);
        spannableStringBuilder.setSpan(new StyleSpan(1), length, spannableStringBuilder.length(), 18);
        textView2.setText(spannableStringBuilder);
        if (!update) {
            ArrayList<SkuItem> sku = this.data.getSku();
            if (!(sku == null || sku.isEmpty())) {
                ((RecyclerView) findViewById(R.id.rvSku)).setAdapter(this.skuAdapter);
                CourseSkuPop$skuAdapter$1 courseSkuPop$skuAdapter$1 = this.skuAdapter;
                ArrayList<SkuItem> sku2 = this.data.getSku();
                Intrinsics.checkNotNull(sku2);
                Iterator<T> it = sku2.iterator();
                while (it.hasNext()) {
                    ((SkuItem) it.next()).setClickExpand(false);
                }
                courseSkuPop$skuAdapter$1.setList(sku2);
                addChildClickViewIds(R.id.iv_exp);
                setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.o4
                    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                    public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) throws PackageManager.NameNotFoundException {
                        CourseSkuPop.showUi$lambda$9(this.f16752c, baseQuickAdapter, view, i2);
                    }
                });
                setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.widget.p4
                    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
                    public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                        CourseSkuPop.showUi$lambda$10(this.f16781c, baseQuickAdapter, view, i2);
                    }
                });
            }
        }
        ArrayList<SkuItem> sku3 = this.data.getSku();
        if (sku3 == null || sku3.isEmpty()) {
            View viewFindViewById5 = findViewById(R.id.tv_cat);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById<View>(R.id.tv_cat)");
            ViewExtensionsKt.gone(viewFindViewById5);
            View viewFindViewById6 = findViewById(R.id.rvSku);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById<View>(R.id.rvSku)");
            ViewExtensionsKt.gone(viewFindViewById6);
        } else {
            View viewFindViewById7 = findViewById(R.id.tv_cat);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById<View>(R.id.tv_cat)");
            ViewExtensionsKt.visible(viewFindViewById7);
            View viewFindViewById8 = findViewById(R.id.rvSku);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById<View>(R.id.rvSku)");
            ViewExtensionsKt.visible(viewFindViewById8);
        }
        TextView tvGift = (TextView) findViewById(R.id.tv_gift);
        RecyclerView rvGift = (RecyclerView) findViewById(R.id.rvGift);
        ArrayList<CourseGiftItem> gift = data.getGift();
        if (gift == null || gift.isEmpty()) {
            Intrinsics.checkNotNullExpressionValue(tvGift, "tvGift");
            ViewExtensionsKt.gone(tvGift);
            Intrinsics.checkNotNullExpressionValue(rvGift, "rvGift");
            ViewExtensionsKt.gone(rvGift);
        } else {
            Intrinsics.checkNotNullExpressionValue(tvGift, "tvGift");
            ViewExtensionsKt.visible(tvGift);
            Intrinsics.checkNotNullExpressionValue(rvGift, "rvGift");
            ViewExtensionsKt.visible(rvGift);
            tvGift.setText("赠品（共" + data.getGift().size() + "件）");
            rvGift.setAdapter(new AnonymousClass6(data.getGift()));
        }
        ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> coupon = data.getCoupon();
        if (coupon == null || coupon.isEmpty()) {
            View viewFindViewById9 = findViewById(R.id.ll_coupon);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById<View>(R.id.ll_coupon)");
            ViewExtensionsKt.gone(viewFindViewById9);
        } else {
            ViewGroup.LayoutParams layoutParams = findViewById(R.id.ll_coupon).getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
            ArrayList<SkuItem> sku4 = this.data.getSku();
            if (sku4 == null || sku4.isEmpty()) {
                layoutParams2.topMargin = 0;
            } else {
                layoutParams2.topMargin = SizeUtil.dp2px(getContext(), 16);
            }
            findViewById(R.id.ll_coupon).setLayoutParams(layoutParams2);
            View viewFindViewById10 = findViewById(R.id.ll_coupon);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById<View>(R.id.ll_coupon)");
            ViewExtensionsKt.visible(viewFindViewById10);
            ((TextView) findViewById(R.id.tv_coupon_count)).setText("优惠券（共" + data.getCoupon().size() + "张）");
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvCoupon);
            final CouponAdapter couponAdapter = new CouponAdapter(true, true, true, false, false, 24, null);
            couponAdapter.setList(data.getCoupon());
            couponAdapter.setFromDetail(true);
            recyclerView.setAdapter(couponAdapter);
            couponAdapter.setClickListener(new Function1<CouponAdapter.ClickListenerBuild, Unit>() { // from class: com.psychiatrygarden.widget.CourseSkuPop.showUi.7
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(CouponAdapter.ClickListenerBuild clickListenerBuild) {
                    invoke2(clickListenerBuild);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull CouponAdapter.ClickListenerBuild setClickListener) {
                    Intrinsics.checkNotNullParameter(setClickListener, "$this$setClickListener");
                    final CourseSkuPop courseSkuPop = CourseSkuPop.this;
                    final CouponAdapter couponAdapter2 = couponAdapter;
                    setClickListener.buttonClickGet(new Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit>() { // from class: com.psychiatrygarden.widget.CourseSkuPop.showUi.7.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
                            invoke(redEnvelopeCouponsDataItem, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(@NotNull final RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, final int i2) {
                            Intrinsics.checkNotNullParameter(item, "item");
                            AjaxParams ajaxParams = new AjaxParams();
                            ajaxParams.put("coupon", item.getId());
                            Context context3 = courseSkuPop.getContext();
                            String str = NetworkRequestsURL.getCoupon;
                            final CourseSkuPop courseSkuPop2 = courseSkuPop;
                            final CouponAdapter couponAdapter3 = couponAdapter2;
                            YJYHttpUtils.post(context3, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.CourseSkuPop.showUi.7.1.1
                                @Override // net.tsz.afinal.http.AjaxCallBack
                                public void onSuccess(@NotNull String t2) {
                                    Intrinsics.checkNotNullParameter(t2, "t");
                                    super.onSuccess((C03061) t2);
                                    try {
                                        JSONObject jSONObject = new JSONObject(t2);
                                        GetCouponsBean getCouponsBean = (GetCouponsBean) new Gson().fromJson(t2, GetCouponsBean.class);
                                        if (!Intrinsics.areEqual(getCouponsBean.getCode(), "200")) {
                                            String strOptString = jSONObject.optString("message");
                                            if (!TextUtils.isEmpty(strOptString)) {
                                                NewToast.showShort(courseSkuPop2.getContext(), strOptString);
                                            }
                                        } else if (getCouponsBean.getData() != null && getCouponsBean.getData().size() > 0) {
                                            NewToast.showShort(courseSkuPop2.getContext(), "领取成功，现在下单更优惠");
                                            item.setCoupon_start(getCouponsBean.getData().get(0).getCoupon_start());
                                            item.setCoupon_end(getCouponsBean.getData().get(0).getCoupon_end());
                                            item.setIs_receive("1");
                                            item.setStatus("0");
                                            couponAdapter3.notifyItemChanged(i2, 0);
                                        }
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                    setClickListener.itemClick(new Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit>() { // from class: com.psychiatrygarden.widget.CourseSkuPop.showUi.7.2
                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num) {
                            invoke(redEnvelopeCouponsDataItem, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(@NotNull RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, int i2) {
                            Intrinsics.checkNotNullParameter(item, "item");
                        }
                    });
                }
            });
        }
        if (data.getDataType() == 1) {
            showBottomBtn();
            View viewFindViewById11 = findViewById(R.id.ll_goods);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById<View>(R.id.ll_goods)");
            ViewExtensionsKt.gone(viewFindViewById11);
            return;
        }
        View viewFindViewById12 = findViewById(R.id.ll_goods);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById<View>(R.id.ll_goods)");
        ViewExtensionsKt.visible(viewFindViewById12);
        findViewById(R.id.tv_goods).setEnabled(Intrinsics.areEqual("0", data.getBtnGray()));
        ((TextView) findViewById(R.id.tv_goods)).setText(data.getBtnText());
        if (findViewById(R.id.tv_goods).isEnabled()) {
            findViewById(R.id.ll_goods).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.q4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws NumberFormatException {
                    CourseSkuPop.showUi$lambda$12(this.f16814c, view);
                }
            });
        }
    }

    public static /* synthetic */ void showUi$default(CourseSkuPop courseSkuPop, CourseDetailBean courseDetailBean, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        courseSkuPop.showUi(courseDetailBean, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showUi$lambda$10(CourseSkuPop this$0, BaseQuickAdapter adapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        this$0.skuAdapter.getItem(i2).setClickExpand(true);
        adapter.notifyItemChanged(i2, 99);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showUi$lambda$12(CourseSkuPop this$0, View it) throws NumberFormatException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        this$0.onClick(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showUi$lambda$9(CourseSkuPop this$0, BaseQuickAdapter adapter, View view, int i2) throws PackageManager.NameNotFoundException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        this$0.skuAdapter.getItem(i2).setClickExpand(true);
        int i3 = 0;
        for (Object obj : this$0.skuAdapter.getData()) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            ((SkuItem) obj).setSelect(i3 == i2);
            i3 = i4;
        }
        adapter.notifyDataSetChanged();
        this$0.loadCourseDetail(this$0.skuAdapter.getItem(i2).getSkuId(), true);
        this$0.listener.updateCourseShow(this$0.skuAdapter.getItem(i2).getSkuId());
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_course_sku;
    }

    @NotNull
    public final SkuUpdateListener getListener() {
        return this.listener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@NotNull View v2) throws NumberFormatException {
        Intrinsics.checkNotNullParameter(v2, "v");
        Context context = getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.psychiatrygarden.activity.ActCourseOrGoodsDetail");
        ((ActCourseOrGoodsDetail) context).btnCLick(v2);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.l4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseSkuPop.onCreate$lambda$0(this.f16668c, view);
            }
        });
        View viewFindViewById = findViewById(R.id.rootLayout);
        int dimensionPixelSize = (getContext().getResources().getDisplayMetrics().heightPixels - getContext().getResources().getDimensionPixelSize(R.dimen.dp_96)) + StatusBarUtil.getStatusBarHeight(getContext());
        ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
        layoutParams.height = dimensionPixelSize;
        viewFindViewById.setLayoutParams(layoutParams);
        View viewFindViewById2 = findViewById(R.id.ll_buy_now);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.ll_buy_now)");
        this.llBuyNow = (LinearLayout) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.ll_sold_out);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.ll_sold_out)");
        this.llSoldOut = (LinearLayout) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.ll_buy_vip_study);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.ll_buy_vip_study)");
        this.llBuyVipStudy = (LinearLayout) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.ll_join_activity_study);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.ll_join_activity_study)");
        this.llJoinActivitiesStudy = (LinearLayout) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.ll_stop_sale);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.ll_stop_sale)");
        this.llStopSale = (LinearLayout) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.ll_not_sale_alone);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.ll_not_sale_alone)");
        this.llNotSaleAlone = (LinearLayout) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.ll_start_study);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.ll_start_study)");
        this.llStartStudy = (LinearLayout) viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.ll_take_now);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.ll_take_now)");
        this.llTakeNow = (LinearLayout) viewFindViewById9;
        View viewFindViewById10 = findViewById(R.id.ll_unlock_course);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.ll_unlock_course)");
        this.llUnlock = (LinearLayout) viewFindViewById10;
        if (this.data.getDataType() == 2) {
            View viewFindViewById11 = findViewById(R.id.tv_cat);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.tv_cat)");
            ((TextView) viewFindViewById11).setText("商品分类");
        }
        findViewById(R.id.ll_customer).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.m4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseSkuPop.onCreate$lambda$2(this.f16699c, view);
            }
        });
        this.bottomPopupContainer.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.n4
            @Override // java.lang.Runnable
            public final void run() {
                CourseSkuPop.onCreate$lambda$3(this.f16727c);
            }
        }, 500L);
        if (this.courseDetailBean == null) {
            this.courseDetailBean = this.data;
        }
        showUi(this.data, false);
    }
}
