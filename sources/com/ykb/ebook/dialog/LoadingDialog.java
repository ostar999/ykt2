package com.ykb.ebook.dialog;

import android.content.Context;
import android.widget.TextView;
import com.ykb.ebook.R;
import com.ykb.ebook.dialog.BasicDialog;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/LoadingDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class LoadingDialog {

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\rR\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lcom/ykb/ebook/dialog/LoadingDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "loadingView", "Landroid/widget/TextView;", "getLoadingView", "()Landroid/widget/TextView;", "loadingView$delegate", "Lkotlin/Lazy;", "setLoadingText", "loadingText", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: loadingView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy loadingView;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.loadingView = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.LoadingDialog$Builder$loadingView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_loading);
                }
            });
            setContentView(R.layout.dialog_loading_view_ebook);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            setGravity(17);
        }

        private final TextView getLoadingView() {
            return (TextView) this.loadingView.getValue();
        }

        @NotNull
        public final Builder setLoadingText(@NotNull String loadingText) {
            Intrinsics.checkNotNullParameter(loadingText, "loadingText");
            TextView loadingView = getLoadingView();
            if (loadingView != null) {
                loadingView.setText(loadingText);
            }
            return this;
        }
    }
}
