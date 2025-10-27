package com.psychiatrygarden.activity.coupon.adapter;

import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001)B7\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005¢\u0006\u0002\u0010\nJ\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0002H\u0014J&\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00022\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0014J\b\u0010\u001f\u001a\u00020\u0019H\u0002J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\"H\u0016J#\u0010#\u001a\u00020\u00192\u001b\u0010$\u001a\u0017\u0012\b\u0012\u00060\u0012R\u00020\u0000\u0012\u0004\u0012\u00020\u00190%¢\u0006\u0002\b&J\u0010\u0010'\u001a\u00020\u00192\b\b\u0002\u0010(\u001a\u00020\u0005R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\fR\u000e\u0010\r\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\fR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00060\u0012R\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\fR\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/adapter/CouponAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/RedEnvelopeCouponsBean$RedEnvelopeCouponsDataItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "available", "", "isCenter", "isCoupons", "isSelect", "needMarginTop", "(ZZZZZ)V", "getAvailable", "()Z", "isDetail", "itemRootBg", "", "leftTextColor", "mClickListenerBuild", "Lcom/psychiatrygarden/activity/coupon/adapter/CouponAdapter$ClickListenerBuild;", "getNeedMarginTop", "otherColor", "shapeBg", "shapeBgExpand", "titleColor", "convert", "", "holder", "item", "payloads", "", "", "initColor", "onAttachedToRecyclerView", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "setClickListener", "listenerBuild", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "setFromDetail", "detail", "ClickListenerBuild", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CouponAdapter extends BaseQuickAdapter<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, BaseViewHolder> {
    private final boolean available;
    private final boolean isCenter;
    private final boolean isCoupons;
    private boolean isDetail;
    private final boolean isSelect;
    private int itemRootBg;
    private int leftTextColor;
    private ClickListenerBuild mClickListenerBuild;
    private final boolean needMarginTop;
    private int otherColor;
    private int shapeBg;
    private int shapeBgExpand;
    private int titleColor;

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00072\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0004J \u0010\f\u001a\u00020\u00072\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0004J \u0010\u000f\u001a\u00020\u00072\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0004J&\u0010\u0012\u001a\u00020\u00072\u001e\u0010\u0019\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00070\u0013R.\u0010\u0003\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR.\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\t\"\u0004\b\u000e\u0010\u000bR.\u0010\u000f\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000bR2\u0010\u0012\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00070\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006\u001a"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/adapter/CouponAdapter$ClickListenerBuild;", "", "(Lcom/psychiatrygarden/activity/coupon/adapter/CouponAdapter;)V", "buttonClickGet", "Lkotlin/Function2;", "Lcom/psychiatrygarden/bean/RedEnvelopeCouponsBean$RedEnvelopeCouponsDataItem;", "", "", "getButtonClickGet$xizongapp_ykbRelease", "()Lkotlin/jvm/functions/Function2;", "setButtonClickGet$xizongapp_ykbRelease", "(Lkotlin/jvm/functions/Function2;)V", "buttonClickGoToUse", "getButtonClickGoToUse$xizongapp_ykbRelease", "setButtonClickGoToUse$xizongapp_ykbRelease", "itemClick", "getItemClick$xizongapp_ykbRelease", "setItemClick$xizongapp_ykbRelease", "itemSelect", "Lkotlin/Function3;", "", "getItemSelect$xizongapp_ykbRelease", "()Lkotlin/jvm/functions/Function3;", "setItemSelect$xizongapp_ykbRelease", "(Lkotlin/jvm/functions/Function3;)V", "action", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class ClickListenerBuild {

        @Nullable
        private Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> buttonClickGet;

        @Nullable
        private Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> buttonClickGoToUse;

        @Nullable
        private Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> itemClick;

        @NotNull
        private Function3<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, ? super Boolean, Unit> itemSelect = new Function3<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Boolean, Unit>() { // from class: com.psychiatrygarden.activity.coupon.adapter.CouponAdapter$ClickListenerBuild$itemSelect$1
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, Integer num, Boolean bool) {
                invoke(redEnvelopeCouponsDataItem, num.intValue(), bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, int i2, boolean z2) {
                Intrinsics.checkNotNullParameter(redEnvelopeCouponsDataItem, "<anonymous parameter 0>");
                System.out.println();
            }
        };

        public ClickListenerBuild() {
        }

        public final void buttonClickGet(@NotNull Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> action) {
            Intrinsics.checkNotNullParameter(action, "action");
            this.buttonClickGet = action;
        }

        public final void buttonClickGoToUse(@NotNull Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> action) {
            Intrinsics.checkNotNullParameter(action, "action");
            this.buttonClickGoToUse = action;
        }

        @Nullable
        public final Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit> getButtonClickGet$xizongapp_ykbRelease() {
            return this.buttonClickGet;
        }

        @Nullable
        public final Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit> getButtonClickGoToUse$xizongapp_ykbRelease() {
            return this.buttonClickGoToUse;
        }

        @Nullable
        public final Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit> getItemClick$xizongapp_ykbRelease() {
            return this.itemClick;
        }

        @NotNull
        public final Function3<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Boolean, Unit> getItemSelect$xizongapp_ykbRelease() {
            return this.itemSelect;
        }

        public final void itemClick(@NotNull Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> action) {
            Intrinsics.checkNotNullParameter(action, "action");
            this.itemClick = action;
        }

        public final void itemSelect(@NotNull Function3<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, ? super Boolean, Unit> action) {
            Intrinsics.checkNotNullParameter(action, "action");
            this.itemSelect = action;
        }

        public final void setButtonClickGet$xizongapp_ykbRelease(@Nullable Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> function2) {
            this.buttonClickGet = function2;
        }

        public final void setButtonClickGoToUse$xizongapp_ykbRelease(@Nullable Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> function2) {
            this.buttonClickGoToUse = function2;
        }

        public final void setItemClick$xizongapp_ykbRelease(@Nullable Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> function2) {
            this.itemClick = function2;
        }

        public final void setItemSelect$xizongapp_ykbRelease(@NotNull Function3<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, ? super Boolean, Unit> function3) {
            Intrinsics.checkNotNullParameter(function3, "<set-?>");
            this.itemSelect = function3;
        }
    }

    public CouponAdapter() {
        this(false, false, false, false, false, 31, null);
    }

    public /* synthetic */ CouponAdapter(boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z2, (i2 & 2) != 0 ? false : z3, (i2 & 4) != 0 ? true : z4, (i2 & 8) != 0 ? false : z5, (i2 & 16) != 0 ? false : z6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$0(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, LinearLayout layoutDetail, ImageView imgDetail, CouponAdapter this$0, RelativeLayout layout1, LinearLayout layoutReasonZD, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(layoutDetail, "$layoutDetail");
        Intrinsics.checkNotNullParameter(imgDetail, "$imgDetail");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(layout1, "$layout1");
        Intrinsics.checkNotNullParameter(layoutReasonZD, "$layoutReasonZD");
        item.setExpand(!item.isExpand());
        layoutDetail.setVisibility(item.isExpand() ? 0 : 8);
        imgDetail.setImageDrawable(!item.isExpand() ? SkinManager.getThemeDrawable(this$0.getContext(), R.attr.icon_bottom_arrow_new) : SkinManager.getThemeDrawable(this$0.getContext(), R.attr.icon_top_arrow_new));
        layout1.setBackgroundResource(!item.isExpand() ? this$0.shapeBg : this$0.shapeBgExpand);
        if (this$0.available || !this$0.isSelect) {
            return;
        }
        layoutReasonZD.setVisibility(item.isExpand() ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$1(CouponAdapter this$0, CheckedTextView tvUse, RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tvUse, "$tvUse");
        Intrinsics.checkNotNullParameter(item, "$item");
        if (this$0.mClickListenerBuild != null) {
            ClickListenerBuild clickListenerBuild = null;
            if (Intrinsics.areEqual("立即使用", tvUse.getText())) {
                ClickListenerBuild clickListenerBuild2 = this$0.mClickListenerBuild;
                if (clickListenerBuild2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mClickListenerBuild");
                } else {
                    clickListenerBuild = clickListenerBuild2;
                }
                Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit> buttonClickGoToUse$xizongapp_ykbRelease = clickListenerBuild.getButtonClickGoToUse$xizongapp_ykbRelease();
                if (buttonClickGoToUse$xizongapp_ykbRelease != null) {
                    buttonClickGoToUse$xizongapp_ykbRelease.invoke(item, Integer.valueOf(this$0.getItemPosition(item)));
                    return;
                }
                return;
            }
            if (Intrinsics.areEqual("立即领取", tvUse.getText())) {
                ClickListenerBuild clickListenerBuild3 = this$0.mClickListenerBuild;
                if (clickListenerBuild3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mClickListenerBuild");
                } else {
                    clickListenerBuild = clickListenerBuild3;
                }
                Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit> buttonClickGet$xizongapp_ykbRelease = clickListenerBuild.getButtonClickGet$xizongapp_ykbRelease();
                if (buttonClickGet$xizongapp_ykbRelease != null) {
                    buttonClickGet$xizongapp_ykbRelease.invoke(item, Integer.valueOf(this$0.getItemPosition(item)));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$2(CouponAdapter this$0, RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        ClickListenerBuild clickListenerBuild = this$0.mClickListenerBuild;
        if (clickListenerBuild != null) {
            if (this$0.isSelect) {
                if (clickListenerBuild == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mClickListenerBuild");
                    clickListenerBuild = null;
                }
                clickListenerBuild.getItemSelect$xizongapp_ykbRelease().invoke(item, Integer.valueOf(this$0.getItemPosition(item)), Boolean.valueOf(!item.isCheck()));
                return;
            }
            if (clickListenerBuild == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mClickListenerBuild");
                clickListenerBuild = null;
            }
            Function2<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Unit> itemClick$xizongapp_ykbRelease = clickListenerBuild.getItemClick$xizongapp_ykbRelease();
            if (itemClick$xizongapp_ykbRelease != null) {
                itemClick$xizongapp_ykbRelease.invoke(item, Integer.valueOf(this$0.getItemPosition(item)));
            }
        }
    }

    private final void initColor() {
        this.itemRootBg = this.available ? R.drawable.bg_item_detail_coupon : R.drawable.shape_coupon_record_bg;
        int i2 = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.icon_red_packet_left_bg_night : R.drawable.icon_red_packet_left_bg_day;
        int i3 = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.icon_red_packet_left_unable_expand_bg_night : R.drawable.icon_red_packet_left_unable_expand_bg_day;
        if (this.isCoupons) {
            i2 = this.available ? R.drawable.bg_detail_coupon_left_hide_detail : R.drawable.shape_coupon_record_item_bg;
        } else if (!this.available) {
            i2 = i3;
        }
        this.shapeBg = i2;
        boolean z2 = this.available;
        this.shapeBgExpand = z2 ? R.drawable.bg_detail_coupon_left_hide_detail_expand : R.drawable.shape_coupon_record_item_bg_expand;
        this.leftTextColor = z2 ? SkinManager.getThemeColor(getContext(), R.attr.main_theme_color) : SkinManager.getThemeColor(getContext(), R.attr.forth_txt_color);
        this.titleColor = this.available ? SkinManager.getThemeColor(getContext(), R.attr.first_txt_color) : SkinManager.getThemeColor(getContext(), R.attr.forth_txt_color);
        this.otherColor = this.available ? SkinManager.getThemeColor(getContext(), R.attr.second_txt_color) : SkinManager.getThemeColor(getContext(), R.attr.forth_txt_color);
    }

    public static /* synthetic */ void setFromDetail$default(CouponAdapter couponAdapter, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        couponAdapter.setFromDetail(z2);
    }

    public final boolean getAvailable() {
        return this.available;
    }

    public final boolean getNeedMarginTop() {
        return this.needMarginTop;
    }

    /* renamed from: isCenter, reason: from getter */
    public final boolean getIsCenter() {
        return this.isCenter;
    }

    /* renamed from: isCoupons, reason: from getter */
    public final boolean getIsCoupons() {
        return this.isCoupons;
    }

    /* renamed from: isSelect, reason: from getter */
    public final boolean getIsSelect() {
        return this.isSelect;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onAttachedToRecyclerView(recyclerView);
        initColor();
    }

    public final void setClickListener(@NotNull Function1<? super ClickListenerBuild, Unit> listenerBuild) {
        Intrinsics.checkNotNullParameter(listenerBuild, "listenerBuild");
        ClickListenerBuild clickListenerBuild = new ClickListenerBuild();
        listenerBuild.invoke(clickListenerBuild);
        this.mClickListenerBuild = clickListenerBuild;
    }

    public final void setFromDetail(boolean detail) {
        this.isDetail = detail;
    }

    public CouponAdapter(boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        super(R.layout.item_detail_coupon_new, null, 2, 0 == true ? 1 : 0);
        this.available = z2;
        this.isCenter = z3;
        this.isCoupons = z4;
        this.isSelect = z5;
        this.needMarginTop = z6;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, List list) {
        convert2(baseViewHolder, redEnvelopeCouponsDataItem, (List<? extends Object>) list);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0485  */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void convert(@org.jetbrains.annotations.NotNull com.chad.library.adapter.base.viewholder.BaseViewHolder r26, @org.jetbrains.annotations.NotNull final com.psychiatrygarden.bean.RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem r27) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 1412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.coupon.adapter.CouponAdapter.convert(com.chad.library.adapter.base.viewholder.BaseViewHolder, com.psychiatrygarden.bean.RedEnvelopeCouponsBean$RedEnvelopeCouponsDataItem):void");
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NotNull BaseViewHolder holder, @NotNull RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        Object obj = payloads.get(0);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
        if (((Integer) obj).intValue() == 0) {
            CheckedTextView checkedTextView = (CheckedTextView) holder.getView(R.id.tvUse);
            TextView textView = (TextView) holder.getView(R.id.tvTime);
            if (Intrinsics.areEqual("1", item.getIs_receive())) {
                checkedTextView.setChecked(true);
                checkedTextView.setText("已领取");
            } else {
                checkedTextView.setChecked(true);
                checkedTextView.setText("立即使用");
            }
            textView.setText(CommonUtil.handleCouponsTime(item.getCoupon_start(), item.getCoupon_end()));
        }
    }
}
