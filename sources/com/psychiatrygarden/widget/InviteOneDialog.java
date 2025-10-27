package com.psychiatrygarden.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.psychiatrygarden.activity.HomePageNewActivity;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0002J&\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0006H\u0016R(\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR(\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/psychiatrygarden/widget/InviteOneDialog;", "Landroidx/fragment/app/DialogFragment;", "()V", "clickAtOnceLookListener", "Lkotlin/Function1;", "", "", "getClickAtOnceLookListener", "()Lkotlin/jvm/functions/Function1;", "setClickAtOnceLookListener", "(Lkotlin/jvm/functions/Function1;)V", "clickCancelListener", "getClickCancelListener", "setClickCancelListener", "tvCancel", "Landroid/widget/TextView;", "tvContent", "tvDay", "tvSeeNow", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class InviteOneDialog extends DialogFragment {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private Function1<? super Integer, Unit> clickAtOnceLookListener;

    @Nullable
    private Function1<? super Integer, Unit> clickCancelListener;
    private TextView tvCancel;
    private TextView tvContent;
    private TextView tvDay;
    private TextView tvSeeNow;

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JD\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u0006¨\u0006\u000e"}, d2 = {"Lcom/psychiatrygarden/widget/InviteOneDialog$Companion;", "", "()V", "newInstance", "Lcom/psychiatrygarden/widget/InviteOneDialog;", "name", "", "day", "appId", "fromWho", "", "firstIdentityId", "secondIdentityId", "identityId", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final InviteOneDialog newInstance(@NotNull String name, @NotNull String day, @NotNull String appId, int fromWho, @NotNull String firstIdentityId, @NotNull String secondIdentityId, @NotNull String identityId) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(day, "day");
            Intrinsics.checkNotNullParameter(appId, "appId");
            Intrinsics.checkNotNullParameter(firstIdentityId, "firstIdentityId");
            Intrinsics.checkNotNullParameter(secondIdentityId, "secondIdentityId");
            Intrinsics.checkNotNullParameter(identityId, "identityId");
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("day", day);
            bundle.putString("app_id", appId);
            bundle.putString("firstIdentityId", firstIdentityId);
            bundle.putString("secondIdentityId", secondIdentityId);
            bundle.putString("identityId", identityId);
            bundle.putInt("fromWho", fromWho);
            InviteOneDialog inviteOneDialog = new InviteOneDialog();
            inviteOneDialog.setArguments(bundle);
            return inviteOneDialog;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$0(Integer num, InviteOneDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (num != null && num.intValue() == 0) {
            Intent intent = new Intent(this$0.requireContext(), (Class<?>) HomePageNewActivity.class);
            intent.addFlags(268468224);
            this$0.startActivity(intent);
            this$0.requireActivity().finish();
            return;
        }
        if (this$0.isAdded() && this$0.isVisible()) {
            this$0.dismissAllowingStateLoss();
        }
        Function1<? super Integer, Unit> function1 = this$0.clickCancelListener;
        if (function1 != null) {
            function1.invoke(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$5(Integer num, InviteOneDialog this$0, String str, String str2, String str3, String str4, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (num == null || num.intValue() != 1) {
            Intent intent = new Intent(this$0.requireContext(), (Class<?>) HomePageNewActivity.class);
            intent.addFlags(268468224);
            this$0.startActivity(intent);
            this$0.requireActivity().finish();
            return;
        }
        if (this$0.isAdded() && this$0.isVisible()) {
            this$0.dismissAllowingStateLoss();
        }
        Context contextRequireContext = this$0.requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        if (str != null) {
            SharePreferencesUtils.writeStrConfig(CommonParameter.App_Id, str, contextRequireContext);
        }
        if (str2 != null) {
            SharePreferencesUtils.writeStrConfig(CommonParameter.identity_id, str2, this$0.requireContext());
            SharePreferencesUtils.writeStrConfig("2", str2, contextRequireContext);
        }
        if (str3 != null) {
            SharePreferencesUtils.writeStrConfig("0", str3, contextRequireContext);
        }
        if (str4 != null) {
            SharePreferencesUtils.writeStrConfig("1", str4, contextRequireContext);
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.last_update_time, "", this$0.requireContext());
        Function1<? super Integer, Unit> function1 = this$0.clickAtOnceLookListener;
        if (function1 != null) {
            function1.invoke(0);
        }
    }

    @Nullable
    public final Function1<Integer, Unit> getClickAtOnceLookListener() {
        return this.clickAtOnceLookListener;
    }

    @Nullable
    public final Function1<Integer, Unit> getClickCancelListener() {
        return this.clickCancelListener;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window;
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Bundle arguments = getArguments();
        TextView textView = null;
        String string = arguments != null ? arguments.getString("name") : null;
        Bundle arguments2 = getArguments();
        String string2 = arguments2 != null ? arguments2.getString("day") : null;
        Bundle arguments3 = getArguments();
        final String string3 = arguments3 != null ? arguments3.getString("app_id") : null;
        Bundle arguments4 = getArguments();
        final String string4 = arguments4 != null ? arguments4.getString("identityId") : null;
        Bundle arguments5 = getArguments();
        final String string5 = arguments5 != null ? arguments5.getString("firstIdentityId") : null;
        Bundle arguments6 = getArguments();
        final String string6 = arguments6 != null ? arguments6.getString("secondIdentityId") : null;
        Bundle arguments7 = getArguments();
        final Integer numValueOf = arguments7 != null ? Integer.valueOf(arguments7.getInt("fromWho")) : null;
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.requestWindowFeature(1);
        }
        Dialog dialog2 = getDialog();
        if (dialog2 != null) {
            dialog2.setCanceledOnTouchOutside(false);
        }
        Dialog dialog3 = getDialog();
        if (dialog3 != null) {
            dialog3.setCancelable(false);
        }
        Dialog dialog4 = getDialog();
        if (dialog4 != null && (window = dialog4.getWindow()) != null) {
            window.setWindowAnimations(R.style.popupAnimation);
        }
        View viewInflate = inflater.inflate(R.layout.layout_invite_pop_one, container, false);
        View viewFindViewById = viewInflate.findViewById(R.id.tv_cancel);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "view.findViewById(R.id.tv_cancel)");
        this.tvCancel = (TextView) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.tv_see_now);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "view.findViewById(R.id.tv_see_now)");
        this.tvSeeNow = (TextView) viewFindViewById2;
        View viewFindViewById3 = viewInflate.findViewById(R.id.tv_content);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "view.findViewById(R.id.tv_content)");
        this.tvContent = (TextView) viewFindViewById3;
        View viewFindViewById4 = viewInflate.findViewById(R.id.tv_invite_day);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "view.findViewById(R.id.tv_invite_day)");
        this.tvDay = (TextView) viewFindViewById4;
        TextView textView2 = this.tvCancel;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCancel");
            textView2 = null;
        }
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.fa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InviteOneDialog.onCreateView$lambda$0(numValueOf, this, view);
            }
        });
        TextView textView3 = this.tvSeeNow;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSeeNow");
            textView3 = null;
        }
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ga
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InviteOneDialog.onCreateView$lambda$5(numValueOf, this, string3, string4, string5, string6, view);
            }
        });
        String str = "】的" + string2 + "会员";
        TextView textView4 = this.tvContent;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvContent");
            textView4 = null;
        }
        textView4.setText(string);
        TextView textView5 = this.tvDay;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDay");
        } else {
            textView = textView5;
        }
        textView.setText(str);
        return viewInflate;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        Window window = dialog != null ? dialog.getWindow() : null;
        ColorDrawable colorDrawable = new ColorDrawable(0);
        if (window != null) {
            window.setBackgroundDrawable(colorDrawable);
        }
        WindowManager.LayoutParams attributes = window != null ? window.getAttributes() : null;
        if (attributes != null) {
            attributes.width = -1;
            attributes.height = -2;
            attributes.dimAmount = 0.2f;
            attributes.gravity = 17;
        }
        if (window == null) {
            return;
        }
        window.setAttributes(attributes);
    }

    public final void setClickAtOnceLookListener(@Nullable Function1<? super Integer, Unit> function1) {
        this.clickAtOnceLookListener = function1;
    }

    public final void setClickCancelListener(@Nullable Function1<? super Integer, Unit> function1) {
        this.clickCancelListener = function1;
    }
}
