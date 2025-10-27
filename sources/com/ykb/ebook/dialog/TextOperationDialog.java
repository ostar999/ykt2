package com.ykb.ebook.dialog;

import android.content.AppCtxKt;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.ykb.ebook.R;
import com.ykb.ebook.common.PreferKeyKt;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/TextOperationDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class TextOperationDialog {

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\t\u001a\u0004\u0018\u00010\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006\u0013"}, d2 = {"Lcom/ykb/ebook/dialog/TextOperationDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "dismissOnTouchOutside", "", "(Landroid/content/Context;Z)V", "getDismissOnTouchOutside", "()Z", "tvIKnow", "Landroid/widget/TextView;", "getTvIKnow", "()Landroid/widget/TextView;", "tvIKnow$delegate", "Lkotlin/Lazy;", "onClick", "", "view", "Landroid/view/View;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> {
        private final boolean dismissOnTouchOutside;

        /* renamed from: tvIKnow$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvIKnow;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context, boolean z2) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.dismissOnTouchOutside = z2;
            this.tvIKnow = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.TextOperationDialog$Builder$tvIKnow$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_i_know);
                }
            });
            setContentView(R.layout.dialog_text_operation);
            setGravity(17);
            setAnimStyle(AnimAction.INSTANCE.getANIM_IOS());
            setCancelable(true);
            setCanceledOnTouchOutside(z2);
            setOnClickListener(getTvIKnow());
        }

        private final TextView getTvIKnow() {
            return (TextView) this.tvIKnow.getValue();
        }

        public final boolean getDismissOnTouchOutside() {
            return this.dismissOnTouchOutside;
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.TEXT_OPERATION, true);
            dismiss();
        }

        public /* synthetic */ Builder(Context context, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(context, (i2 & 2) != 0 ? false : z2);
        }
    }
}
