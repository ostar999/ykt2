package com.psychiatrygarden.activity.coupon.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.socket.user.PLVSocketUserConstant;
import com.psychiatrygarden.activity.coupon.fragment.CouponSelectFragment;
import com.psychiatrygarden.activity.coupon.fragment.RedPacketSelectFragment;
import com.psychiatrygarden.event.CouponSelectEvent;
import com.psychiatrygarden.utils.AnimUtil;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.imagezoom.HackyViewPager;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 F2\u00020\u0001:\u0002FGB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J$\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*2\b\u0010+\u001a\u0004\u0018\u00010,H\u0016J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020.2\u0006\u00102\u001a\u00020\nH\u0002J\u001e\u00103\u001a\u00020.2\u0006\u00104\u001a\u00020\n2\u0006\u00105\u001a\u00020\n2\u0006\u00106\u001a\u00020\"J8\u00107\u001a\u00020.2\u0006\u00108\u001a\u00020\n2\u0006\u00109\u001a\u00020\u001a2\u0006\u0010:\u001a\u00020\u001a2\u0006\u0010;\u001a\u00020&2\u0006\u0010<\u001a\u00020&2\u0006\u0010=\u001a\u00020>H\u0002J\u001a\u0010?\u001a\u00020.2\u0006\u0010@\u001a\u00020A2\b\u0010B\u001a\u0004\u0018\u00010\u0006H\u0016J\u001e\u0010C\u001a\u00020.2\u0006\u00104\u001a\u00020\n2\u0006\u0010D\u001a\u00020\n2\u0006\u0010E\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/fragment/CouponAndRedPacketSelectDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "btnCancel", "Landroid/widget/ImageView;", "defSelectId", "", "goodId", "goodType", "isPromotion", "", "isUseCoupon", "isUseRedPacket", "layoutBottom", "Landroid/widget/LinearLayout;", "orderAmount", "selectCouponId", "getSelectCouponId", "()Ljava/lang/String;", "setSelectCouponId", "(Ljava/lang/String;)V", "selectRedPacketId", "getSelectRedPacketId", "setSelectRedPacketId", "selectType", "tvFlag", "Landroid/widget/TextView;", "tvSelectDecreaseCount", "tvSelectNum", "tvSubmit", "tvTab1", "tvTab2", "tvTitle", "getContextRect", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDismiss", "", "dialog", "Landroid/content/DialogInterface;", "setButtonEnable", "enable", "setListNum", "isCoupon", "available", "num", "setTabUi", "isSelectTab1", "tabTextView1", "tabTextView2", "tabLine1", "tabLine2", "viewPager", "Landroidx/viewpager/widget/ViewPager;", "show", PLVSocketUserConstant.USERTYPE_MANAGER, "Landroidx/fragment/app/FragmentManager;", "tag", "updateSelectCouponOrRedPacket", "mIsUse", "amount", "Companion", "MyPagerAdapter", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CouponAndRedPacketSelectDialogFragment extends DialogFragment {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private ImageView btnCancel;
    private String goodId;
    private String goodType;
    private boolean isPromotion;
    private boolean isUseCoupon;
    private boolean isUseRedPacket;
    private LinearLayout layoutBottom;
    private TextView tvFlag;
    private TextView tvSelectDecreaseCount;
    private TextView tvSelectNum;
    private TextView tvSubmit;
    private TextView tvTab1;
    private TextView tvTab2;
    private TextView tvTitle;

    @Nullable
    private String selectType = "1";

    @NotNull
    private String orderAmount = "0";

    @NotNull
    private String selectCouponId = "0";

    @NotNull
    private String selectRedPacketId = "0";

    @NotNull
    private String defSelectId = "0";

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J8\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/fragment/CouponAndRedPacketSelectDialogFragment$Companion;", "", "()V", "newInstance", "Lcom/psychiatrygarden/activity/coupon/fragment/CouponAndRedPacketSelectDialogFragment;", "selectType", "", "goodId", "goodType", "orderAmount", "defSelectId", "isPromotion", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ CouponAndRedPacketSelectDialogFragment newInstance$default(Companion companion, String str, String str2, String str3, String str4, String str5, boolean z2, int i2, Object obj) {
            if ((i2 & 32) != 0) {
                z2 = false;
            }
            return companion.newInstance(str, str2, str3, str4, str5, z2);
        }

        @NotNull
        public final CouponAndRedPacketSelectDialogFragment newInstance(@NotNull String selectType, @NotNull String goodId, @NotNull String goodType, @NotNull String orderAmount, @NotNull String defSelectId, boolean isPromotion) {
            Intrinsics.checkNotNullParameter(selectType, "selectType");
            Intrinsics.checkNotNullParameter(goodId, "goodId");
            Intrinsics.checkNotNullParameter(goodType, "goodType");
            Intrinsics.checkNotNullParameter(orderAmount, "orderAmount");
            Intrinsics.checkNotNullParameter(defSelectId, "defSelectId");
            Bundle bundle = new Bundle();
            bundle.putString("selectType", selectType);
            bundle.putString("goodId", goodId);
            bundle.putString("goodType", goodType);
            bundle.putString("orderAmount", orderAmount);
            bundle.putString("defSelectId", defSelectId);
            bundle.putBoolean("is_promotion", isPromotion);
            CouponAndRedPacketSelectDialogFragment couponAndRedPacketSelectDialogFragment = new CouponAndRedPacketSelectDialogFragment();
            couponAndRedPacketSelectDialogFragment.setArguments(bundle);
            return couponAndRedPacketSelectDialogFragment;
        }
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\tH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/fragment/CouponAndRedPacketSelectDialogFragment$MyPagerAdapter;", "Landroidx/fragment/app/FragmentPagerAdapter;", "fm", "Landroidx/fragment/app/FragmentManager;", "fragments", "", "Landroidx/fragment/app/Fragment;", "(Landroidx/fragment/app/FragmentManager;Ljava/util/List;)V", "getCount", "", "getItem", "position", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class MyPagerAdapter extends FragmentPagerAdapter {

        @NotNull
        private List<? extends Fragment> fragments;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MyPagerAdapter(@NotNull FragmentManager fm, @NotNull List<? extends Fragment> fragments) {
            super(fm);
            Intrinsics.checkNotNullParameter(fm, "fm");
            Intrinsics.checkNotNullParameter(fragments, "fragments");
            new ArrayList();
            this.fragments = fragments;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        /* renamed from: getCount */
        public int getSize() {
            return this.fragments.size();
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        @NotNull
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }
    }

    private final int getContextRect(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$3(CouponAndRedPacketSelectDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Dialog dialog = this$0.getDialog();
        Intrinsics.checkNotNull(dialog);
        dialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$4(CouponAndRedPacketSelectDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Dialog dialog = this$0.getDialog();
        Intrinsics.checkNotNull(dialog);
        dialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$5(CouponAndRedPacketSelectDialogFragment this$0, View tabLine1, View tabLine2, HackyViewPager viewPager, View view) throws Resources.NotFoundException {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TextView textView3 = this$0.tvTab1;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTab1");
            textView = null;
        } else {
            textView = textView3;
        }
        TextView textView4 = this$0.tvTab2;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTab2");
            textView2 = null;
        } else {
            textView2 = textView4;
        }
        Intrinsics.checkNotNullExpressionValue(tabLine1, "tabLine1");
        Intrinsics.checkNotNullExpressionValue(tabLine2, "tabLine2");
        Intrinsics.checkNotNullExpressionValue(viewPager, "viewPager");
        this$0.setTabUi(true, textView, textView2, tabLine1, tabLine2, viewPager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$6(CouponAndRedPacketSelectDialogFragment this$0, View tabLine1, View tabLine2, HackyViewPager viewPager, View view) throws Resources.NotFoundException {
        TextView textView;
        TextView textView2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TextView textView3 = this$0.tvTab1;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTab1");
            textView = null;
        } else {
            textView = textView3;
        }
        TextView textView4 = this$0.tvTab2;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTab2");
            textView2 = null;
        } else {
            textView2 = textView4;
        }
        Intrinsics.checkNotNullExpressionValue(tabLine1, "tabLine1");
        Intrinsics.checkNotNullExpressionValue(tabLine2, "tabLine2");
        Intrinsics.checkNotNullExpressionValue(viewPager, "viewPager");
        this$0.setTabUi(false, textView, textView2, tabLine1, tabLine2, viewPager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$7(CouponAndRedPacketSelectDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (Intrinsics.areEqual(this$0.selectType, "1")) {
            EventBus.getDefault().post(new CouponSelectEvent(true, this$0.selectCouponId));
        } else {
            EventBus.getDefault().post(new CouponSelectEvent(false, this$0.selectRedPacketId));
        }
        Dialog dialog = this$0.getDialog();
        Intrinsics.checkNotNull(dialog);
        dialog.dismiss();
    }

    private final void setButtonEnable(boolean enable) {
        TextView textView = this.tvSubmit;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSubmit");
            textView = null;
        }
        textView.setEnabled(enable);
        TextView textView3 = this.tvSubmit;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSubmit");
        } else {
            textView2 = textView3;
        }
        textView2.setTextColor(enable ? SkinManager.getThemeColor(getActivity(), R.attr.app_bg) : SkinManager.getThemeColor(getActivity(), R.attr.forth_txt_color));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setTabUi(boolean isSelectTab1, TextView tabTextView1, TextView tabTextView2, View tabLine1, View tabLine2, ViewPager viewPager) throws Resources.NotFoundException {
        int themeColor = SkinManager.getThemeColor(getContext(), R.attr.first_txt_color);
        int themeColor2 = SkinManager.getThemeColor(getContext(), R.attr.third_txt_color);
        tabTextView1.setTextColor(isSelectTab1 ? themeColor : themeColor2);
        if (isSelectTab1) {
            themeColor = themeColor2;
        }
        tabTextView2.setTextColor(themeColor);
        tabLine1.setVisibility(isSelectTab1 ? 0 : 4);
        tabLine2.setVisibility(isSelectTab1 ? 4 : 0);
        viewPager.setCurrentItem(!isSelectTab1 ? 1 : 0);
        LinearLayout linearLayout = this.layoutBottom;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutBottom");
            linearLayout = null;
        }
        linearLayout.setVisibility(viewPager.getCurrentItem() == 1 ? 8 : 0);
        tabTextView1.setTypeface(isSelectTab1 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        tabTextView2.setTypeface(isSelectTab1 ? Typeface.defaultFromStyle(0) : Typeface.defaultFromStyle(1));
    }

    @NotNull
    public final String getSelectCouponId() {
        return this.selectCouponId;
    }

    @NotNull
    public final String getSelectRedPacketId() {
        return this.selectRedPacketId;
    }

    @Override // androidx.fragment.app.Fragment
    @NotNull
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) throws Resources.NotFoundException {
        String str;
        String str2;
        String str3;
        String str4;
        TextView textView;
        String str5;
        String str6;
        String str7;
        String str8;
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.selectType = arguments.getString("selectType");
            String string = arguments.getString("goodId", "");
            Intrinsics.checkNotNullExpressionValue(string, "it.getString(\"goodId\", \"\")");
            this.goodId = string;
            String string2 = arguments.getString("goodType", "");
            Intrinsics.checkNotNullExpressionValue(string2, "it.getString(\"goodType\", \"\")");
            this.goodType = string2;
            String string3 = arguments.getString("orderAmount", "");
            Intrinsics.checkNotNullExpressionValue(string3, "it.getString(\"orderAmount\", \"\")");
            this.orderAmount = string3;
            String string4 = arguments.getString("defSelectId", "");
            Intrinsics.checkNotNullExpressionValue(string4, "it.getString(\"defSelectId\", \"\")");
            this.defSelectId = string4;
            this.isPromotion = arguments.getBoolean("is_promotion", false);
            if (Intrinsics.areEqual(this.selectType, "1")) {
                this.selectCouponId = this.defSelectId;
            } else {
                this.selectRedPacketId = this.defSelectId;
            }
        }
        Dialog dialog = getDialog();
        Intrinsics.checkNotNull(dialog);
        Window window = dialog.getWindow();
        Intrinsics.checkNotNull(window);
        window.requestFeature(1);
        window.getDecorView().setPadding(0, 0, 0, 0);
        Context context = getContext();
        if (context != null) {
            window.setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.transparent));
        }
        window.setLayout(-1, -1);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.dimAmount = 0.5f;
        window.setAttributes(attributes);
        Dialog dialog2 = getDialog();
        Intrinsics.checkNotNull(dialog2);
        dialog2.setCancelable(true);
        Dialog dialog3 = getDialog();
        Intrinsics.checkNotNull(dialog3);
        dialog3.setCanceledOnTouchOutside(true);
        View viewOnCreateView = super.onCreateView(inflater, container, savedInstanceState);
        if (viewOnCreateView == null) {
            viewOnCreateView = inflater.inflate(R.layout.fragment_coupon_and_red_packet_select, container, false);
        }
        Intrinsics.checkNotNull(viewOnCreateView);
        final HackyViewPager hackyViewPager = (HackyViewPager) viewOnCreateView.findViewById(R.id.viewPager);
        View viewFindViewById = viewOnCreateView.findViewById(R.id.tvSubmit);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "mLoadRootView.findViewById(R.id.tvSubmit)");
        this.tvSubmit = (TextView) viewFindViewById;
        setButtonEnable(false);
        View viewFindViewById2 = viewOnCreateView.findViewById(R.id.tvSelectDecreaseCount);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "mLoadRootView.findViewBy…id.tvSelectDecreaseCount)");
        this.tvSelectDecreaseCount = (TextView) viewFindViewById2;
        View viewFindViewById3 = viewOnCreateView.findViewById(R.id.tvSelectNum);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "mLoadRootView.findViewById(R.id.tvSelectNum)");
        this.tvSelectNum = (TextView) viewFindViewById3;
        View viewFindViewById4 = viewOnCreateView.findViewById(R.id.tvTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "mLoadRootView.findViewById(R.id.tvTitle)");
        this.tvTitle = (TextView) viewFindViewById4;
        View viewFindViewById5 = viewOnCreateView.findViewById(R.id.tvFlag);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "mLoadRootView.findViewById(R.id.tvFlag)");
        this.tvFlag = (TextView) viewFindViewById5;
        TextView textView2 = this.tvTitle;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView2 = null;
        }
        textView2.setText(Intrinsics.areEqual(this.selectType, "1") ? "优惠券" : "红包");
        TextView textView3 = this.tvFlag;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvFlag");
            textView3 = null;
        }
        textView3.setText(Intrinsics.areEqual(this.selectType, "1") ? "张， 可减 " : "个， 可减 ");
        View viewFindViewById6 = viewOnCreateView.findViewById(R.id.layoutBottom);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "mLoadRootView.findViewById(R.id.layoutBottom)");
        this.layoutBottom = (LinearLayout) viewFindViewById6;
        View viewFindViewById7 = viewOnCreateView.findViewById(R.id.btn_cancel);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "mLoadRootView.findViewById(R.id.btn_cancel)");
        this.btnCancel = (ImageView) viewFindViewById7;
        RelativeLayout relativeLayout = (RelativeLayout) viewOnCreateView.findViewById(R.id.layout_tab1);
        View viewFindViewById8 = viewOnCreateView.findViewById(R.id.tvTab1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "mLoadRootView.findViewById(R.id.tvTab1)");
        this.tvTab1 = (TextView) viewFindViewById8;
        final View viewFindViewById9 = viewOnCreateView.findViewById(R.id.tabLine1);
        RelativeLayout relativeLayout2 = (RelativeLayout) viewOnCreateView.findViewById(R.id.layout_tab2);
        View viewFindViewById10 = viewOnCreateView.findViewById(R.id.tvTab2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "mLoadRootView.findViewById(R.id.tvTab2)");
        this.tvTab2 = (TextView) viewFindViewById10;
        final View viewFindViewById11 = viewOnCreateView.findViewById(R.id.tabLine2);
        if (Intrinsics.areEqual(this.selectType, "1")) {
            TextView textView4 = this.tvTab1;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTab1");
                textView4 = null;
            }
            textView4.setText("可用优惠券（0）");
            TextView textView5 = this.tvTab2;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTab2");
                textView5 = null;
            }
            textView5.setText("不可用优惠券（0）");
        } else {
            TextView textView6 = this.tvTab1;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTab1");
                textView6 = null;
            }
            textView6.setText("可用红包（0）");
            TextView textView7 = this.tvTab2;
            if (textView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTab2");
                textView7 = null;
            }
            textView7.setText("不可用红包（0）");
        }
        LinearLayout linearLayout = (LinearLayout) viewOnCreateView.findViewById(R.id.ly_content);
        Context context2 = getContext();
        if (context2 != null) {
            linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, context2.getResources().getDisplayMetrics().heightPixels - ScreenUtil.getPxByDp(getContext(), 96)));
            AnimUtil.fromBottomToTopAnim(linearLayout);
        }
        ArrayList arrayList = new ArrayList();
        if (Intrinsics.areEqual(this.selectType, "2")) {
            RedPacketSelectFragment.Companion companion = RedPacketSelectFragment.INSTANCE;
            String str9 = this.goodId;
            if (str9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodId");
                str5 = null;
            } else {
                str5 = str9;
            }
            String str10 = this.goodType;
            if (str10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodType");
                str6 = null;
            } else {
                str6 = str10;
            }
            arrayList.add(companion.newInstance(true, str5, str6, this.orderAmount, this.defSelectId, this.isPromotion));
            String str11 = this.goodId;
            if (str11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodId");
                str7 = null;
            } else {
                str7 = str11;
            }
            String str12 = this.goodType;
            if (str12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodType");
                str8 = null;
            } else {
                str8 = str12;
            }
            arrayList.add(companion.newInstance(false, str7, str8, this.orderAmount, this.defSelectId, this.isPromotion));
        } else {
            CouponSelectFragment.Companion companion2 = CouponSelectFragment.INSTANCE;
            String str13 = this.goodId;
            if (str13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodId");
                str = null;
            } else {
                str = str13;
            }
            String str14 = this.goodType;
            if (str14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodType");
                str2 = null;
            } else {
                str2 = str14;
            }
            arrayList.add(companion2.newInstance(true, str, str2, this.orderAmount, this.defSelectId, this.isPromotion));
            String str15 = this.goodId;
            if (str15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodId");
                str3 = null;
            } else {
                str3 = str15;
            }
            String str16 = this.goodType;
            if (str16 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodType");
                str4 = null;
            } else {
                str4 = str16;
            }
            arrayList.add(companion2.newInstance(false, str3, str4, this.orderAmount, this.defSelectId, this.isPromotion));
        }
        FragmentManager childFragmentManager = getChildFragmentManager();
        Intrinsics.checkNotNullExpressionValue(childFragmentManager, "childFragmentManager");
        hackyViewPager.setAdapter(new MyPagerAdapter(childFragmentManager, arrayList));
        hackyViewPager.setOffscreenPageLimit(1);
        hackyViewPager.setCurrentItem(0);
        viewOnCreateView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.fragment.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CouponAndRedPacketSelectDialogFragment.onCreateView$lambda$3(this.f11814c, view);
            }
        });
        ImageView imageView = this.btnCancel;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnCancel");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.fragment.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CouponAndRedPacketSelectDialogFragment.onCreateView$lambda$4(this.f11815c, view);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.fragment.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                CouponAndRedPacketSelectDialogFragment.onCreateView$lambda$5(this.f11816c, viewFindViewById9, viewFindViewById11, hackyViewPager, view);
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.fragment.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                CouponAndRedPacketSelectDialogFragment.onCreateView$lambda$6(this.f11820c, viewFindViewById9, viewFindViewById11, hackyViewPager, view);
            }
        });
        TextView textView8 = this.tvSubmit;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSubmit");
            textView = null;
        } else {
            textView = textView8;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.fragment.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CouponAndRedPacketSelectDialogFragment.onCreateView$lambda$7(this.f11824c, view);
            }
        });
        hackyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.coupon.fragment.CouponAndRedPacketSelectDialogFragment.onCreateView.9
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float v2, int i12) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) throws Resources.NotFoundException {
                CouponAndRedPacketSelectDialogFragment couponAndRedPacketSelectDialogFragment = CouponAndRedPacketSelectDialogFragment.this;
                boolean z2 = i2 == 0;
                TextView textView9 = couponAndRedPacketSelectDialogFragment.tvTab1;
                if (textView9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvTab1");
                    textView9 = null;
                }
                TextView textView10 = CouponAndRedPacketSelectDialogFragment.this.tvTab2;
                if (textView10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvTab2");
                    textView10 = null;
                }
                View tabLine1 = viewFindViewById9;
                Intrinsics.checkNotNullExpressionValue(tabLine1, "tabLine1");
                View tabLine2 = viewFindViewById11;
                Intrinsics.checkNotNullExpressionValue(tabLine2, "tabLine2");
                HackyViewPager viewPager = hackyViewPager;
                Intrinsics.checkNotNullExpressionValue(viewPager, "viewPager");
                couponAndRedPacketSelectDialogFragment.setTabUi(z2, textView9, textView10, tabLine1, tabLine2, viewPager);
            }
        });
        return viewOnCreateView;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(@NotNull DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        super.onDismiss(dialog);
        Dialog dialog2 = getDialog();
        Intrinsics.checkNotNull(dialog2);
        dialog2.dismiss();
    }

    public final void setListNum(boolean isCoupon, boolean available, int num) {
        String str;
        String str2;
        if (num > 0 && available) {
            setButtonEnable(true);
        }
        TextView textView = null;
        if (isCoupon) {
            if (available) {
                TextView textView2 = this.tvTab1;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvTab1");
                } else {
                    textView = textView2;
                }
                str2 = "可用优惠券（" + num + (char) 65289;
            } else {
                TextView textView3 = this.tvTab2;
                if (textView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvTab2");
                } else {
                    textView = textView3;
                }
                str2 = "不可用优惠券（" + num + (char) 65289;
            }
            textView.setText(str2);
            return;
        }
        if (available) {
            TextView textView4 = this.tvTab1;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTab1");
            } else {
                textView = textView4;
            }
            str = "可用红包（" + num + (char) 65289;
        } else {
            TextView textView5 = this.tvTab2;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTab2");
            } else {
                textView = textView5;
            }
            str = "不可用红包（" + num + (char) 65289;
        }
        textView.setText(str);
    }

    public final void setSelectCouponId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.selectCouponId = str;
    }

    public final void setSelectRedPacketId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.selectRedPacketId = str;
    }

    @Override // androidx.fragment.app.DialogFragment
    public void show(@NotNull FragmentManager manager, @Nullable String tag) {
        Intrinsics.checkNotNullParameter(manager, "manager");
        try {
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void updateSelectCouponOrRedPacket(boolean isCoupon, boolean mIsUse, @NotNull String amount) {
        String str;
        Intrinsics.checkNotNullParameter(amount, "amount");
        if (isCoupon) {
            this.isUseCoupon = mIsUse;
        } else {
            this.isUseRedPacket = mIsUse;
        }
        TextView textView = this.tvSelectNum;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSelectNum");
            textView = null;
        }
        textView.setText(mIsUse ? "1" : "0");
        TextView textView3 = this.tvSelectDecreaseCount;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSelectDecreaseCount");
        } else {
            textView2 = textView3;
        }
        if (mIsUse) {
            str = (char) 165 + amount + ' ';
        } else {
            str = "¥0 ";
        }
        textView2.setText(str);
    }
}
