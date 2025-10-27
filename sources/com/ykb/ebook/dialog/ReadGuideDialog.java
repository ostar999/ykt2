package com.ykb.ebook.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import com.ykb.ebook.R;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/ReadGuideDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ReadGuideDialog {

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lcom/ykb/ebook/dialog/ReadGuideDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "llRoot", "Landroid/widget/LinearLayout;", "getLlRoot", "()Landroid/widget/LinearLayout;", "llRoot$delegate", "Lkotlin/Lazy;", "onClick", "", "view", "Landroid/view/View;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: llRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llRoot;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.llRoot = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.ReadGuideDialog$Builder$llRoot$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ll_root);
                }
            });
            setContentView(R.layout.dialog_read_guide);
            setAnimStyle(AnimAction.INSTANCE.getANIM_IOS());
            setBackgroundDimAmount(0.8f);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setOnClickListener(getLlRoot());
            addOnDismissListener(new BasicDialog.OnDismissListener() { // from class: com.ykb.ebook.dialog.ReadGuideDialog.Builder.1
                @Override // com.ykb.ebook.dialog.BasicDialog.OnDismissListener
                public void onDismiss(@Nullable BasicDialog dialog) {
                }
            });
        }

        private final LinearLayout getLlRoot() {
            return (LinearLayout) this.llRoot.getValue();
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            if (Intrinsics.areEqual(view, getLlRoot())) {
                dismiss();
            }
        }
    }
}
