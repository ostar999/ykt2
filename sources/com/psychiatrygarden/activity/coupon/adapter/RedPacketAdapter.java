package com.psychiatrygarden.activity.coupon.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.CharPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.CouponUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001(B-\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u0002H\u0014J&\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0014J\b\u0010 \u001a\u00020\u001aH\u0002J\u0010\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020#H\u0016J#\u0010$\u001a\u00020\u001a2\u001b\u0010%\u001a\u0017\u0012\b\u0012\u00060\u000fR\u00020\u0000\u0012\u0004\u0012\u00020\u001a0&¢\u0006\u0002\b'R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000e\u001a\u00060\u000fR\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000bR\u000e\u0010\u0011\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/adapter/RedPacketAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/RedEnvelopeCouponsBean$RedEnvelopeCouponsDataItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "available", "", "isCenter", "isOrderConfirmSelect", "needMarginTop", "(ZZZZ)V", "getAvailable", "()Z", "leftTextColor", "", "mClickListenerBuild", "Lcom/psychiatrygarden/activity/coupon/adapter/RedPacketAdapter$ClickListenerBuild;", "getNeedMarginTop", "otherColor", "shapeBottomBg", "shapeLeftBg", "shapeLeftExpandBg", "shapeMiddleBg", "shapeRightBg", "shapeRightExpandBg", "titleColor", "convert", "", "holder", "item", "payloads", "", "", "initColor", "onAttachedToRecyclerView", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "setClickListener", "listenerBuild", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "ClickListenerBuild", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class RedPacketAdapter extends BaseQuickAdapter<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, BaseViewHolder> {
    private final boolean available;
    private final boolean isCenter;
    private final boolean isOrderConfirmSelect;
    private int leftTextColor;
    private ClickListenerBuild mClickListenerBuild;
    private final boolean needMarginTop;
    private int otherColor;
    private int shapeBottomBg;
    private int shapeLeftBg;
    private int shapeLeftExpandBg;
    private int shapeMiddleBg;
    private int shapeRightBg;
    private int shapeRightExpandBg;
    private int titleColor;

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00072\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0004J \u0010\f\u001a\u00020\u00072\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0004J \u0010\u000f\u001a\u00020\u00072\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0004J&\u0010\u0012\u001a\u00020\u00072\u001e\u0010\u0019\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00070\u0013R.\u0010\u0003\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR.\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\t\"\u0004\b\u000e\u0010\u000bR.\u0010\u000f\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000bR2\u0010\u0012\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00070\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006\u001a"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/adapter/RedPacketAdapter$ClickListenerBuild;", "", "(Lcom/psychiatrygarden/activity/coupon/adapter/RedPacketAdapter;)V", "buttonClickGet", "Lkotlin/Function2;", "Lcom/psychiatrygarden/bean/RedEnvelopeCouponsBean$RedEnvelopeCouponsDataItem;", "", "", "getButtonClickGet$xizongapp_ykbRelease", "()Lkotlin/jvm/functions/Function2;", "setButtonClickGet$xizongapp_ykbRelease", "(Lkotlin/jvm/functions/Function2;)V", "buttonClickGoToUse", "getButtonClickGoToUse$xizongapp_ykbRelease", "setButtonClickGoToUse$xizongapp_ykbRelease", "itemClick", "getItemClick$xizongapp_ykbRelease", "setItemClick$xizongapp_ykbRelease", "itemSelect", "Lkotlin/Function3;", "", "getItemSelect$xizongapp_ykbRelease", "()Lkotlin/jvm/functions/Function3;", "setItemSelect$xizongapp_ykbRelease", "(Lkotlin/jvm/functions/Function3;)V", "action", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class ClickListenerBuild {

        @Nullable
        private Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> buttonClickGet;

        @Nullable
        private Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> buttonClickGoToUse;

        @Nullable
        private Function2<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, Unit> itemClick;

        @NotNull
        private Function3<? super RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, ? super Integer, ? super Boolean, Unit> itemSelect = new Function3<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, Integer, Boolean, Unit>() { // from class: com.psychiatrygarden.activity.coupon.adapter.RedPacketAdapter$ClickListenerBuild$itemSelect$1
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

    public RedPacketAdapter() {
        this(false, false, false, false, 15, null);
    }

    public /* synthetic */ RedPacketAdapter(boolean z2, boolean z3, boolean z4, boolean z5, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z2, (i2 & 2) != 0 ? false : z3, (i2 & 4) != 0 ? false : z4, (i2 & 8) != 0 ? false : z5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$0(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, LinearLayout layoutDetail, ImageView imgDetail, RedPacketAdapter this$0, RelativeLayout layoutLeft, ImageView imgRightView, LinearLayout layoutReasonZD, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(layoutDetail, "$layoutDetail");
        Intrinsics.checkNotNullParameter(imgDetail, "$imgDetail");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(layoutLeft, "$layoutLeft");
        Intrinsics.checkNotNullParameter(imgRightView, "$imgRightView");
        Intrinsics.checkNotNullParameter(layoutReasonZD, "$layoutReasonZD");
        item.setExpand(!item.isExpand());
        layoutDetail.setVisibility(item.isExpand() ? 0 : 8);
        imgDetail.setImageDrawable(!item.isExpand() ? SkinManager.getThemeDrawable(this$0.getContext(), R.attr.icon_bottom_arrow_new) : SkinManager.getThemeDrawable(this$0.getContext(), R.attr.icon_top_arrow_new));
        layoutLeft.setBackgroundResource(!item.isExpand() ? this$0.shapeLeftBg : this$0.shapeLeftExpandBg);
        imgRightView.setBackgroundResource(!item.isExpand() ? this$0.shapeRightBg : this$0.shapeRightExpandBg);
        if (this$0.available || !this$0.isOrderConfirmSelect) {
            return;
        }
        layoutReasonZD.setVisibility(item.isExpand() ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$1(RedPacketAdapter this$0, CheckedTextView tvUse, RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tvUse, "$tvUse");
        Intrinsics.checkNotNullParameter(item, "$item");
        if (this$0.mClickListenerBuild != null) {
            CharSequence text = tvUse.getText();
            Intrinsics.checkNotNullExpressionValue(text, "tvUse.text");
            ClickListenerBuild clickListenerBuild = null;
            if (Intrinsics.areEqual(StringsKt__StringsKt.trim(text), "立即使用")) {
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
            CharSequence text2 = tvUse.getText();
            Intrinsics.checkNotNullExpressionValue(text2, "tvUse.text");
            if (Intrinsics.areEqual(StringsKt__StringsKt.trim(text2), "立即领取")) {
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
    public static final void convert$lambda$2(RedPacketAdapter this$0, RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        ClickListenerBuild clickListenerBuild = this$0.mClickListenerBuild;
        if (clickListenerBuild != null) {
            if (this$0.isOrderConfirmSelect) {
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
        this.shapeLeftBg = this.available ? SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_red_coupons_left_bg_night : R.mipmap.ic_red_coupons_left_bg_day : SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_red_coupons_left_unable_bg_night : R.mipmap.ic_red_coupons_left_unable_bg_day;
        this.shapeLeftExpandBg = this.available ? SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_red_coupons_left_bg_expand_night : R.mipmap.ic_red_coupons_left_bg_expand_day : SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_red_coupons_left_unable_expand_bg_night : R.mipmap.ic_red_coupons_left_unable_expand_bg_day;
        this.shapeMiddleBg = this.available ? SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_red_coupons_middle_bg_night : R.mipmap.ic_red_coupons_middle_bg_day : SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_red_coupons_middle_unable_bg_night : R.mipmap.ic_red_coupons_middle_unable_bg_day;
        this.shapeRightBg = this.available ? SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_red_coupons_right_bg_night : R.mipmap.ic_red_coupons_right_bg_day : SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_red_coupons_right_unable_bg_night : R.mipmap.ic_red_coupons_right_unable_bg_day;
        this.shapeRightExpandBg = this.available ? SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_red_coupons_right_bg_expand_night : R.mipmap.ic_red_coupons_right_bg_expand_day : SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_red_coupons_right_unable_expand_night : R.mipmap.ic_red_coupons_right_unable_expand_bg_day;
        this.leftTextColor = this.available ? SkinManager.getThemeColor(getContext(), R.attr.main_theme_color) : SkinManager.getThemeColor(getContext(), R.attr.forth_txt_color);
        this.titleColor = this.available ? SkinManager.getThemeColor(getContext(), R.attr.first_txt_color) : SkinManager.getThemeColor(getContext(), R.attr.forth_txt_color);
        this.otherColor = this.available ? SkinManager.getThemeColor(getContext(), R.attr.second_txt_color) : SkinManager.getThemeColor(getContext(), R.attr.forth_txt_color);
        this.shapeBottomBg = this.available ? R.drawable.item_red_packet_bottom_bg : R.drawable.item_red_packet_bottom_unable_bg;
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

    /* renamed from: isOrderConfirmSelect, reason: from getter */
    public final boolean getIsOrderConfirmSelect() {
        return this.isOrderConfirmSelect;
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

    public RedPacketAdapter(boolean z2, boolean z3, boolean z4, boolean z5) {
        super(R.layout.item_detail_red_packet, null, 2, 0 == true ? 1 : 0);
        this.available = z2;
        this.isCenter = z3;
        this.isOrderConfirmSelect = z4;
        this.needMarginTop = z5;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem, List list) {
        convert2(baseViewHolder, redEnvelopeCouponsDataItem, (List<? extends Object>) list);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull final RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem item) {
        LinearLayout linearLayout;
        int i2;
        LinearLayout linearLayout2;
        StringBuilder sb;
        String coupon_code;
        final CheckedTextView checkedTextView;
        ImageView imageView;
        int i3;
        int i4;
        String str;
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.layoutStatus);
        RelativeLayout relativeLayout2 = (RelativeLayout) holder.getView(R.id.layoutExpand);
        final RelativeLayout relativeLayout3 = (RelativeLayout) holder.getView(R.id.layoutLeft);
        TextView textView = (TextView) holder.getView(R.id.tvCountTag);
        TextView textView2 = (TextView) holder.getView(R.id.tvCount);
        TextView textView3 = (TextView) holder.getView(R.id.tvDesc);
        CheckedTextView checkedTextView2 = (CheckedTextView) holder.getView(R.id.tvUse);
        ImageView imageView2 = (ImageView) holder.getView(R.id.imgCheck);
        LinearLayout linearLayout3 = (LinearLayout) holder.getView(R.id.ly_middle);
        final ImageView imageView3 = (ImageView) holder.getView(R.id.img_right_view);
        TextView textView4 = (TextView) holder.getView(R.id.tvTitle);
        TextView textView5 = (TextView) holder.getView(R.id.tvTime);
        TextView textView6 = (TextView) holder.getView(R.id.tvDetail);
        final ImageView imageView4 = (ImageView) holder.getView(R.id.imgDetail);
        LinearLayout linearLayout4 = (LinearLayout) holder.getView(R.id.layoutDetail);
        TextView textView7 = (TextView) holder.getView(R.id.tvDetail1);
        TextView textView8 = (TextView) holder.getView(R.id.tvDetail2);
        TextView textView9 = (TextView) holder.getView(R.id.tvCouponStatus);
        TextView textView10 = (TextView) holder.getView(R.id.tvCouponFlag);
        if (item.isExpand()) {
            linearLayout = linearLayout4;
            i2 = this.shapeLeftExpandBg;
        } else {
            linearLayout = linearLayout4;
            i2 = this.shapeLeftBg;
        }
        relativeLayout3.setBackgroundResource(i2);
        imageView3.setBackgroundResource(item.isExpand() ? this.shapeRightExpandBg : this.shapeRightBg);
        linearLayout3.setBackgroundResource(this.shapeMiddleBg);
        textView.setTextColor(this.leftTextColor);
        textView2.setTextColor(this.leftTextColor);
        textView3.setTextColor(this.leftTextColor);
        if (this.available && this.isOrderConfirmSelect && getItemPosition(item) == 0) {
            textView10.setVisibility(0);
        } else {
            textView10.setVisibility(8);
        }
        TextView textView11 = (TextView) holder.getView(R.id.tvDetailReason);
        LinearLayout linearLayout5 = (LinearLayout) holder.getView(R.id.layoutReasonZD);
        TextView textView12 = (TextView) holder.getView(R.id.tvDetailReasonZD);
        if (!this.available && this.isOrderConfirmSelect) {
            ViewExtensionsKt.visible(textView11);
            String reason = item.getReason();
            if (reason == null || reason.length() == 0) {
                str = "不可用原因：无";
            } else {
                str = "不可用原因：" + item.getReason();
            }
            textView11.setText(str);
            if (item.isExpand()) {
                ViewExtensionsKt.gone(linearLayout5);
            } else {
                ViewExtensionsKt.visible(linearLayout5);
                String reason2 = item.getReason();
                textView12.setText(reason2 == null || reason2.length() == 0 ? "无" : item.getReason());
            }
        } else {
            ViewExtensionsKt.gone(textView11);
            ViewExtensionsKt.gone(linearLayout5);
        }
        textView4.setTextColor(this.titleColor);
        textView5.setTextColor(this.otherColor);
        textView6.setTextColor(this.otherColor);
        textView7.setTextColor(this.otherColor);
        textView8.setTextColor(this.otherColor);
        textView11.setTextColor(this.otherColor);
        final LinearLayout linearLayout6 = linearLayout;
        linearLayout6.setBackgroundResource(this.shapeBottomBg);
        textView4.setText(item.getTitle());
        if (this.isOrderConfirmSelect) {
            textView2.setText(item.getPrice());
            textView3.setText("立减");
            linearLayout2 = linearLayout5;
        } else {
            linearLayout2 = linearLayout5;
            if (!TextUtils.isEmpty(item.getType()) && item.getType().equals("1")) {
                textView.setVisibility(8);
                StringBuilder sb2 = new StringBuilder();
                CouponUtils couponUtils = CouponUtils.INSTANCE;
                String discount = item.getDiscount();
                Intrinsics.checkNotNullExpressionValue(discount, "item.discount");
                sb2.append(couponUtils.convertDiscount(discount));
                sb2.append((char) 25240);
                textView2.setText(sb2.toString());
                if (!Intrinsics.areEqual("0", item.getThreshold_price())) {
                    textView3.setText((char) 28385 + item.getThreshold_price() + "元可用");
                } else {
                    textView3.setText("");
                }
            } else {
                textView.setVisibility(0);
                textView2.setText(item.getPrice());
                if (!TextUtils.isEmpty(item.getThreshold_price()) && !item.getThreshold_price().equals("0")) {
                    textView3.setText((char) 28385 + item.getThreshold_price() + "元可用");
                } else {
                    textView3.setText("立减");
                }
            }
        }
        if (this.isOrderConfirmSelect) {
            textView5.setText(CommonUtil.handleCouponsTime(item.getCtime(), item.getEnd_time()));
        } else if (this.isCenter) {
            if (!TextUtils.isEmpty(item.getExpire_type()) && item.getExpire_type().equals("1")) {
                if (item.getIs_receive().equals("1")) {
                    textView5.setText(CommonUtil.handleCouponsTime(item.getCoupon_start(), item.getCoupon_end()));
                } else {
                    textView5.setText("自领取之日起" + item.getExpire_day() + "天有效");
                }
            } else if (Intrinsics.areEqual("0", item.getIs_receive())) {
                textView5.setText(CommonUtil.getDateByTimesTemp(item.getCoupon_start()) + CharPool.DASHED + CommonUtil.getDateByTimesTemp(item.getCoupon_end()));
            } else {
                textView5.setText(CommonUtil.handleCouponsTime(item.getCoupon_start(), item.getCoupon_end()));
            }
        } else if (Intrinsics.areEqual("0", item.getIs_receive())) {
            textView5.setText(CommonUtil.getDateByTimesTemp(item.getCoupon_start()) + CharPool.DASHED + CommonUtil.getDateByTimesTemp(item.getCoupon_end()));
        } else {
            textView5.setText(CommonUtil.handleCouponsTime(item.getCoupon_start(), item.getCoupon_end()));
        }
        if (this.isOrderConfirmSelect) {
            sb = new StringBuilder();
            sb.append("券编码：");
            coupon_code = item.getRed_packet_code();
        } else {
            sb = new StringBuilder();
            sb.append("券编码：");
            coupon_code = item.getCoupon_code();
        }
        sb.append(coupon_code);
        textView7.setText(sb.toString());
        textView8.setText(this.isOrderConfirmSelect ? item.getDescribe() : item.getDesc());
        if (this.isOrderConfirmSelect) {
            if (this.available) {
                checkedTextView = checkedTextView2;
                checkedTextView.setChecked(true);
                checkedTextView.setText("立即使用");
                textView10.setVisibility(getItemPosition(item) == 0 ? 0 : 8);
            } else {
                checkedTextView = checkedTextView2;
                ViewExtensionsKt.gone(checkedTextView);
                ViewExtensionsKt.gone(textView10);
                ViewExtensionsKt.gone(relativeLayout);
            }
        } else {
            checkedTextView = checkedTextView2;
            String use_type = item.getUse_type();
            if (use_type != null) {
                switch (use_type.hashCode()) {
                    case 49:
                        if (use_type.equals("1")) {
                            if (this.isCenter) {
                                checkedTextView.setChecked(false);
                                checkedTextView.setText("立即领取");
                                break;
                            } else {
                                checkedTextView.setChecked(true);
                                checkedTextView.setText("立即使用");
                                break;
                            }
                        }
                        break;
                    case 50:
                        if (use_type.equals("2")) {
                            ViewExtensionsKt.gone(checkedTextView);
                            ViewExtensionsKt.gone(textView10);
                            ViewExtensionsKt.visible(relativeLayout);
                            textView9.setText("已使用");
                            break;
                        }
                        break;
                    case 51:
                        if (use_type.equals("3")) {
                            ViewExtensionsKt.gone(checkedTextView);
                            ViewExtensionsKt.visible(relativeLayout);
                            ViewExtensionsKt.gone(textView10);
                            textView9.setText("已过期");
                            break;
                        }
                        break;
                    case 52:
                        if (use_type.equals("4")) {
                            ViewExtensionsKt.gone(checkedTextView);
                            ViewExtensionsKt.visible(relativeLayout);
                            ViewExtensionsKt.gone(textView10);
                            textView9.setText("已失效");
                            break;
                        }
                        break;
                }
            }
        }
        if (this.isOrderConfirmSelect && this.available) {
            imageView = imageView2;
            i3 = 0;
        } else {
            imageView = imageView2;
            i3 = 8;
        }
        imageView.setVisibility(i3);
        imageView.setSelected(item.isCheck());
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        if (getItemPosition(item) == 0) {
            i4 = 0;
            marginLayoutParams.setMargins(0, 0, 0, 0);
        } else {
            i4 = 0;
            marginLayoutParams.setMargins(0, 0, (int) getContext().getResources().getDimension(R.dimen.dp_13), 0);
        }
        imageView.setLayoutParams(marginLayoutParams);
        linearLayout6.setVisibility(item.isExpand() ? i4 : 8);
        imageView4.setImageDrawable(!item.isExpand() ? SkinManager.getThemeDrawable(getContext(), R.attr.icon_bottom_arrow_new) : SkinManager.getThemeDrawable(getContext(), R.attr.icon_top_arrow_new));
        final LinearLayout linearLayout7 = linearLayout2;
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.adapter.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RedPacketAdapter.convert$lambda$0(item, linearLayout6, imageView4, this, relativeLayout3, imageView3, linearLayout7, view);
            }
        });
        checkedTextView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.adapter.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RedPacketAdapter.convert$lambda$1(this.f11809c, checkedTextView, item, view);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.adapter.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RedPacketAdapter.convert$lambda$2(this.f11812c, item, view);
            }
        });
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
            checkedTextView.setChecked(true);
            checkedTextView.setText("立即使用");
            textView.setText(CommonUtil.handleCouponsTime(item.getCoupon_start(), item.getCoupon_end()));
        }
    }
}
