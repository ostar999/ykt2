package com.psychiatrygarden.widget;

import android.app.Dialog;
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
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0006H\u0016R(\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/widget/InviteTwoDialog;", "Landroidx/fragment/app/DialogFragment;", "()V", "clickKnowListener", "Lkotlin/Function1;", "", "", "getClickKnowListener", "()Lkotlin/jvm/functions/Function1;", "setClickKnowListener", "(Lkotlin/jvm/functions/Function1;)V", "tvContent", "Landroid/widget/TextView;", "tvKnow", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class InviteTwoDialog extends DialogFragment {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private Function1<? super Integer, Unit> clickKnowListener;
    private TextView tvContent;
    private TextView tvKnow;

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"Lcom/psychiatrygarden/widget/InviteTwoDialog$Companion;", "", "()V", "newInstance", "Lcom/psychiatrygarden/widget/InviteTwoDialog;", "day", "", "appId", "fromWho", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final InviteTwoDialog newInstance(@NotNull String day, @NotNull String appId, int fromWho) {
            Intrinsics.checkNotNullParameter(day, "day");
            Intrinsics.checkNotNullParameter(appId, "appId");
            Bundle bundle = new Bundle();
            bundle.putString("day", day);
            bundle.putInt("fromWho", fromWho);
            bundle.putString("app_id", appId);
            InviteTwoDialog inviteTwoDialog = new InviteTwoDialog();
            inviteTwoDialog.setArguments(bundle);
            return inviteTwoDialog;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$0(Integer num, InviteTwoDialog this$0, View view) {
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
        Function1<? super Integer, Unit> function1 = this$0.clickKnowListener;
        if (function1 != null) {
            function1.invoke(0);
        }
    }

    @Nullable
    public final Function1<Integer, Unit> getClickKnowListener() {
        return this.clickKnowListener;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window;
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Bundle arguments = getArguments();
        TextView textView = null;
        String string = arguments != null ? arguments.getString("day") : null;
        Bundle arguments2 = getArguments();
        final Integer numValueOf = arguments2 != null ? Integer.valueOf(arguments2.getInt("fromWho")) : null;
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
        View viewInflate = inflater.inflate(R.layout.layout_invite_pop_two, container, false);
        View viewFindViewById = viewInflate.findViewById(R.id.tv_content);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "view.findViewById(R.id.tv_content)");
        this.tvContent = (TextView) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.tv_know);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "view.findViewById(R.id.tv_know)");
        TextView textView2 = (TextView) viewFindViewById2;
        this.tvKnow = textView2;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvKnow");
            textView2 = null;
        }
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ha
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InviteTwoDialog.onCreateView$lambda$0(numValueOf, this, view);
            }
        });
        String str = "恭喜您获得" + string + "会员";
        TextView textView3 = this.tvContent;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvContent");
        } else {
            textView = textView3;
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

    public final void setClickKnowListener(@Nullable Function1<? super Integer, Unit> function1) {
        this.clickKnowListener = function1;
    }
}
