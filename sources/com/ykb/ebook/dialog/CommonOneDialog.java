package com.ykb.ebook.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.helper.RBaseHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/CommonOneDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class CommonOneDialog {

    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010&\u001a\u00020\u0016H\u0002J\u0010\u0010'\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u001fH\u0016J\u000e\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020+J\u001c\u0010,\u001a\u00020\u00002\u0014\u0010\u0013\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\u0004\u0012\u00020\u00160\u0014J\u000e\u0010-\u001a\u00020\u00002\u0006\u0010.\u001a\u00020/J2\u00100\u001a\u00020\u00002\u0014\u0010\u0013\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\u0004\u0012\u00020\u00160\u00142\u0014\u0010\u0017\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\u0004\u0012\u00020\u00160\u0014J\u001c\u00101\u001a\u00020\u00002\u0014\u0010\u0017\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\u0004\u0012\u00020\u00160\u0014J\u000e\u00102\u001a\u00020\u00002\u0006\u00103\u001a\u00020/J\u000e\u00104\u001a\u00020\u00002\u0006\u00105\u001a\u00020/J\u000e\u00106\u001a\u00020\u00002\u0006\u00107\u001a\u00020/R\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\u000b\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\n\u001a\u0004\b\f\u0010\bR\u001d\u0010\u000e\u001a\u0004\u0018\u00010\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\n\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0013\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0017\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0018\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\n\u001a\u0004\b\u0019\u0010\bR\u001d\u0010\u001b\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\n\u001a\u0004\b\u001c\u0010\bR\u001d\u0010\u001e\u001a\u0004\u0018\u00010\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\n\u001a\u0004\b \u0010!R\u001d\u0010#\u001a\u0004\u0018\u00010\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\n\u001a\u0004\b$\u0010!¨\u00068"}, d2 = {"Lcom/ykb/ebook/dialog/CommonOneDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "cancelView", "Landroid/widget/TextView;", "getCancelView", "()Landroid/widget/TextView;", "cancelView$delegate", "Lkotlin/Lazy;", "confirmView", "getConfirmView", "confirmView$delegate", "layoutRoot", "Lcom/ruffian/library/widget/RLinearLayout;", "getLayoutRoot", "()Lcom/ruffian/library/widget/RLinearLayout;", "layoutRoot$delegate", "leftClick", "Lkotlin/Function1;", "Lcom/ykb/ebook/dialog/BasicDialog;", "", "rightClick", "subTitleView", "getSubTitleView", "subTitleView$delegate", "titleView", "getTitleView", "titleView$delegate", "viewLine1", "Landroid/view/View;", "getViewLine1", "()Landroid/view/View;", "viewLine1$delegate", "viewLine2", "getViewLine2", "viewLine2$delegate", "initTheme", "onClick", "view", "setIsNight", "isNight", "", "setLeftClick", "setLeftText", "leftText", "", "setListener", "setRightClick", "setRightText", "rightText", "setSubTitle", "subTitle", "setTitle", "title", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: cancelView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cancelView;

        /* renamed from: confirmView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy confirmView;

        /* renamed from: layoutRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutRoot;

        @Nullable
        private Function1<? super BasicDialog, Unit> leftClick;

        @Nullable
        private Function1<? super BasicDialog, Unit> rightClick;

        /* renamed from: subTitleView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy subTitleView;

        /* renamed from: titleView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy titleView;

        /* renamed from: viewLine1$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine1;

        /* renamed from: viewLine2$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine2;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context) throws SecurityException {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.titleView = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonOneDialog$Builder$titleView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_title);
                }
            });
            this.subTitleView = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonOneDialog$Builder$subTitleView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_sub_title);
                }
            });
            this.cancelView = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonOneDialog$Builder$cancelView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_left);
                }
            });
            this.confirmView = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonOneDialog$Builder$confirmView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_right);
                }
            });
            this.layoutRoot = LazyKt__LazyJVMKt.lazy(new Function0<RLinearLayout>() { // from class: com.ykb.ebook.dialog.CommonOneDialog$Builder$layoutRoot$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RLinearLayout invoke() {
                    return (RLinearLayout) this.this$0.findViewById(R.id.dialog_layout_root);
                }
            });
            this.viewLine1 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.CommonOneDialog$Builder$viewLine1$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.viewLine1);
                }
            });
            this.viewLine2 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.CommonOneDialog$Builder$viewLine2$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.viewLine2);
                }
            });
            setContentView(R.layout.dialog_common_one);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            setAnimStyle(AnimAction.INSTANCE.getANIM_IOS());
            setGravity(17);
            setOnClickListener(getCancelView(), getConfirmView());
            initTheme();
        }

        private final TextView getCancelView() {
            return (TextView) this.cancelView.getValue();
        }

        private final TextView getConfirmView() {
            return (TextView) this.confirmView.getValue();
        }

        private final RLinearLayout getLayoutRoot() {
            return (RLinearLayout) this.layoutRoot.getValue();
        }

        private final TextView getSubTitleView() {
            return (TextView) this.subTitleView.getValue();
        }

        private final TextView getTitleView() {
            return (TextView) this.titleView.getValue();
        }

        private final View getViewLine1() {
            return (View) this.viewLine1.getValue();
        }

        private final View getViewLine2() {
            return (View) this.viewLine2.getValue();
        }

        private final void initTheme() throws SecurityException {
            RBaseHelper helper;
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            if (colorMode == 0) {
                RLinearLayout layoutRoot = getLayoutRoot();
                helper = layoutRoot != null ? layoutRoot.getHelper() : null;
                if (helper == null) {
                    return;
                }
                helper.setBackgroundColorNormal(getColor(R.color.white));
                return;
            }
            if (colorMode == 1) {
                RLinearLayout layoutRoot2 = getLayoutRoot();
                helper = layoutRoot2 != null ? layoutRoot2.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(getColor(R.color.color_yellow_theme_bg));
                }
                View viewLine2 = getViewLine2();
                if (viewLine2 != null) {
                    viewLine2.setBackgroundColor(getColor(R.color.color_theme_yellow_line_color));
                }
                View viewLine1 = getViewLine1();
                if (viewLine1 != null) {
                    viewLine1.setBackgroundColor(getColor(R.color.color_theme_yellow_line_color));
                    return;
                }
                return;
            }
            if (colorMode != 2) {
                return;
            }
            RLinearLayout layoutRoot3 = getLayoutRoot();
            helper = layoutRoot3 != null ? layoutRoot3.getHelper() : null;
            if (helper != null) {
                helper.setBackgroundColorNormal(getColor(R.color.color_blue_theme_bg));
            }
            TextView titleView = getTitleView();
            if (titleView != null) {
                titleView.setTextColor(getColor(R.color.color_7380a9));
            }
            TextView subTitleView = getSubTitleView();
            if (subTitleView != null) {
                subTitleView.setTextColor(getColor(R.color.color_575F79));
            }
            TextView cancelView = getCancelView();
            if (cancelView != null) {
                cancelView.setTextColor(getColor(R.color.color_7380a9));
            }
            TextView confirmView = getConfirmView();
            if (confirmView != null) {
                confirmView.setTextColor(getColor(R.color.color_B2575C));
            }
            View viewLine22 = getViewLine2();
            if (viewLine22 != null) {
                viewLine22.setBackgroundColor(getColor(R.color.color_theme_blue_line_color));
            }
            View viewLine12 = getViewLine1();
            if (viewLine12 != null) {
                viewLine12.setBackgroundColor(getColor(R.color.color_theme_blue_line_color));
            }
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            int id = view.getId();
            if (id == R.id.tv_left) {
                dismiss();
                Function1<? super BasicDialog, Unit> function1 = this.leftClick;
                if (function1 != null) {
                    function1.invoke(getDialog());
                    return;
                }
                return;
            }
            if (id == R.id.tv_right) {
                dismiss();
                Function1<? super BasicDialog, Unit> function12 = this.rightClick;
                if (function12 != null) {
                    function12.invoke(getDialog());
                }
            }
        }

        @NotNull
        public final Builder setIsNight(boolean isNight) throws SecurityException {
            RBaseHelper helper;
            if (isNight) {
                RLinearLayout layoutRoot = getLayoutRoot();
                helper = layoutRoot != null ? layoutRoot.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(getColor(R.color.color_blue_theme_bg));
                }
                TextView subTitleView = getSubTitleView();
                if (subTitleView != null) {
                    subTitleView.setTextColor(getColor(R.color.color_7B7E83));
                }
                TextView titleView = getTitleView();
                if (titleView != null) {
                    titleView.setTextColor(getColor(R.color.color_575F79));
                }
                TextView cancelView = getCancelView();
                if (cancelView != null) {
                    cancelView.setTextColor(getColor(R.color.color_575F79));
                }
                TextView confirmView = getConfirmView();
                if (confirmView != null) {
                    confirmView.setTextColor(getColor(R.color.color_B2575C));
                }
                View viewLine1 = getViewLine1();
                if (viewLine1 != null) {
                    viewLine1.setBackgroundColor(getColor(R.color.color_1C2134));
                }
                View viewLine2 = getViewLine2();
                if (viewLine2 != null) {
                    viewLine2.setBackgroundColor(getColor(R.color.color_1C2134));
                }
            } else {
                RLinearLayout layoutRoot2 = getLayoutRoot();
                helper = layoutRoot2 != null ? layoutRoot2.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(getColor(R.color.white));
                }
                TextView subTitleView2 = getSubTitleView();
                if (subTitleView2 != null) {
                    subTitleView2.setTextColor(getColor(R.color.color_7B7E83));
                }
                TextView titleView2 = getTitleView();
                if (titleView2 != null) {
                    titleView2.setTextColor(getColor(R.color.first_txt_color));
                }
                TextView cancelView2 = getCancelView();
                if (cancelView2 != null) {
                    cancelView2.setTextColor(getColor(R.color.first_txt_color));
                }
                TextView confirmView2 = getConfirmView();
                if (confirmView2 != null) {
                    confirmView2.setTextColor(getColor(R.color.color_F95843));
                }
                View viewLine12 = getViewLine1();
                if (viewLine12 != null) {
                    viewLine12.setBackgroundColor(getColor(R.color.color_dcdfe6));
                }
                View viewLine22 = getViewLine2();
                if (viewLine22 != null) {
                    viewLine22.setBackgroundColor(getColor(R.color.color_dcdfe6));
                }
            }
            return this;
        }

        @NotNull
        public final Builder setLeftClick(@NotNull Function1<? super BasicDialog, Unit> leftClick) {
            Intrinsics.checkNotNullParameter(leftClick, "leftClick");
            this.leftClick = leftClick;
            return this;
        }

        @NotNull
        public final Builder setLeftText(@NotNull String leftText) {
            Intrinsics.checkNotNullParameter(leftText, "leftText");
            TextView cancelView = getCancelView();
            if (cancelView != null) {
                cancelView.setText(leftText);
            }
            return this;
        }

        @NotNull
        public final Builder setListener(@NotNull Function1<? super BasicDialog, Unit> leftClick, @NotNull Function1<? super BasicDialog, Unit> rightClick) {
            Intrinsics.checkNotNullParameter(leftClick, "leftClick");
            Intrinsics.checkNotNullParameter(rightClick, "rightClick");
            this.leftClick = leftClick;
            this.rightClick = rightClick;
            return this;
        }

        @NotNull
        public final Builder setRightClick(@NotNull Function1<? super BasicDialog, Unit> rightClick) {
            Intrinsics.checkNotNullParameter(rightClick, "rightClick");
            this.rightClick = rightClick;
            return this;
        }

        @NotNull
        public final Builder setRightText(@NotNull String rightText) {
            Intrinsics.checkNotNullParameter(rightText, "rightText");
            TextView confirmView = getConfirmView();
            if (confirmView != null) {
                confirmView.setText(rightText);
            }
            return this;
        }

        @NotNull
        public final Builder setSubTitle(@NotNull String subTitle) {
            Intrinsics.checkNotNullParameter(subTitle, "subTitle");
            TextView subTitleView = getSubTitleView();
            if (subTitleView != null) {
                subTitleView.setText(subTitle);
            }
            return this;
        }

        @NotNull
        public final Builder setTitle(@NotNull String title) {
            Intrinsics.checkNotNullParameter(title, "title");
            TextView titleView = getTitleView();
            if (titleView != null) {
                titleView.setText(title);
            }
            return this;
        }
    }
}
