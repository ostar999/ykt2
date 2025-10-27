package com.ykb.ebook.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.helper.RBaseHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.CommonSureDialog;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/CommonSureDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class CommonSureDialog {

    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\b\u0010&\u001a\u00020\bH\u0002J\u0010\u0010'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\u001fH\u0016R\u001d\u0010\n\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u001d\u0010\u0010\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0011\u0010\rR\u001d\u0010\u0013\u001a\u0004\u0018\u00010\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u000f\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\u0018\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u000f\u001a\u0004\b\u0019\u0010\rR\u001d\u0010\u001b\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u000f\u001a\u0004\b\u001c\u0010\rR\u001d\u0010\u001e\u001a\u0004\u0018\u00010\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u000f\u001a\u0004\b \u0010!R\u001d\u0010#\u001a\u0004\u0018\u00010\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u000f\u001a\u0004\b$\u0010!¨\u0006)"}, d2 = {"Lcom/ykb/ebook/dialog/CommonSureDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", AliyunLogCommon.LogLevel.INFO, "", "sureClick", "Lkotlin/Function0;", "", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "btnClose", "Landroid/widget/TextView;", "getBtnClose", "()Landroid/widget/TextView;", "btnClose$delegate", "Lkotlin/Lazy;", "btnSure", "getBtnSure", "btnSure$delegate", "layoutRoot", "Lcom/ruffian/library/widget/RLinearLayout;", "getLayoutRoot", "()Lcom/ruffian/library/widget/RLinearLayout;", "layoutRoot$delegate", "tvContent", "getTvContent", "tvContent$delegate", "tvTitle", "getTvTitle", "tvTitle$delegate", "viewLine1", "Landroid/view/View;", "getViewLine1", "()Landroid/view/View;", "viewLine1$delegate", "viewLine2", "getViewLine2", "viewLine2$delegate", "initTheme", "onClick", "view", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nCommonSureDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CommonSureDialog.kt\ncom/ykb/ebook/dialog/CommonSureDialog$Builder\n+ 2 Background.kt\nsplitties/views/BackgroundKt\n*L\n1#1,84:1\n32#2:85\n32#2:86\n32#2:87\n32#2:88\n32#2:89\n32#2:90\n*S KotlinDebug\n*F\n+ 1 CommonSureDialog.kt\ncom/ykb/ebook/dialog/CommonSureDialog$Builder\n*L\n57#1:85\n58#1:86\n66#1:87\n67#1:88\n76#1:89\n77#1:90\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: btnClose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy btnClose;

        /* renamed from: btnSure$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy btnSure;

        /* renamed from: layoutRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutRoot;

        /* renamed from: tvContent$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvContent;

        /* renamed from: tvTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitle;

        /* renamed from: viewLine1$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine1;

        /* renamed from: viewLine2$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine2;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context, @NotNull String info, @NotNull final Function0<Unit> sureClick) throws SecurityException {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(info, "info");
            Intrinsics.checkNotNullParameter(sureClick, "sureClick");
            this.tvContent = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonSureDialog$Builder$tvContent$2
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
            this.btnClose = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonSureDialog$Builder$btnClose$2
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
            this.btnSure = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonSureDialog$Builder$btnSure$2
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
            this.tvTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonSureDialog$Builder$tvTitle$2
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
            this.viewLine1 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.CommonSureDialog$Builder$viewLine1$2
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
            this.viewLine2 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.CommonSureDialog$Builder$viewLine2$2
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
            this.layoutRoot = LazyKt__LazyJVMKt.lazy(new Function0<RLinearLayout>() { // from class: com.ykb.ebook.dialog.CommonSureDialog$Builder$layoutRoot$2
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
            setContentView(R.layout.dialog_common_one);
            setGravity(17);
            setAnimStyle(AnimAction.INSTANCE.getANIM_IOS());
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            setOnClickListener(getBtnClose());
            TextView tvContent = getTvContent();
            if (tvContent != null) {
                tvContent.setText(info);
            }
            TextView btnSure = getBtnSure();
            if (btnSure != null) {
                btnSure.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.d0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CommonSureDialog.Builder._init_$lambda$0(this.f26312c, sureClick, view);
                    }
                });
            }
            initTheme();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(Builder this$0, Function0 sureClick, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(sureClick, "$sureClick");
            this$0.dismiss();
            sureClick.invoke();
        }

        private final TextView getBtnClose() {
            return (TextView) this.btnClose.getValue();
        }

        private final TextView getBtnSure() {
            return (TextView) this.btnSure.getValue();
        }

        private final RLinearLayout getLayoutRoot() {
            return (RLinearLayout) this.layoutRoot.getValue();
        }

        private final TextView getTvContent() {
            return (TextView) this.tvContent.getValue();
        }

        private final TextView getTvTitle() {
            return (TextView) this.tvTitle.getValue();
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
                TextView tvTitle = getTvTitle();
                if (tvTitle != null) {
                    tvTitle.setTextColor(getColor(R.color.color_303030));
                }
                TextView tvContent = getTvContent();
                if (tvContent != null) {
                    tvContent.setTextColor(getColor(R.color.color_606060));
                }
                TextView btnClose = getBtnClose();
                if (btnClose != null) {
                    btnClose.setTextColor(getColor(R.color.color_303030));
                }
                TextView btnSure = getBtnSure();
                if (btnSure != null) {
                    btnSure.setTextColor(getColor(R.color.color_dd594a));
                }
                View viewLine1 = getViewLine1();
                if (viewLine1 != null) {
                    viewLine1.setBackgroundColor(getColor(R.color.color_theme_white_line_color));
                }
                View viewLine2 = getViewLine2();
                if (viewLine2 != null) {
                    viewLine2.setBackgroundColor(getColor(R.color.color_theme_white_line_color));
                }
                RLinearLayout layoutRoot = getLayoutRoot();
                helper = layoutRoot != null ? layoutRoot.getHelper() : null;
                if (helper == null) {
                    return;
                }
                helper.setBackgroundColorNormal(getColor(R.color.white));
                return;
            }
            if (colorMode == 1) {
                TextView tvTitle2 = getTvTitle();
                if (tvTitle2 != null) {
                    tvTitle2.setTextColor(getColor(R.color.color_303030));
                }
                TextView tvContent2 = getTvContent();
                if (tvContent2 != null) {
                    tvContent2.setTextColor(getColor(R.color.color_606060));
                }
                TextView btnClose2 = getBtnClose();
                if (btnClose2 != null) {
                    btnClose2.setTextColor(getColor(R.color.color_303030));
                }
                TextView btnSure2 = getBtnSure();
                if (btnSure2 != null) {
                    btnSure2.setTextColor(getColor(R.color.color_dd594a));
                }
                View viewLine12 = getViewLine1();
                if (viewLine12 != null) {
                    viewLine12.setBackgroundColor(getColor(R.color.color_theme_yellow_line_color));
                }
                View viewLine22 = getViewLine2();
                if (viewLine22 != null) {
                    viewLine22.setBackgroundColor(getColor(R.color.color_theme_yellow_line_color));
                }
                RLinearLayout layoutRoot2 = getLayoutRoot();
                helper = layoutRoot2 != null ? layoutRoot2.getHelper() : null;
                if (helper == null) {
                    return;
                }
                helper.setBackgroundColorNormal(getColor(R.color.color_yellow_theme_bg));
                return;
            }
            if (colorMode != 2) {
                return;
            }
            TextView tvTitle3 = getTvTitle();
            if (tvTitle3 != null) {
                tvTitle3.setTextColor(getColor(R.color.color_7380a9));
            }
            TextView tvContent3 = getTvContent();
            if (tvContent3 != null) {
                tvContent3.setTextColor(getColor(R.color.color_575F79));
            }
            TextView btnClose3 = getBtnClose();
            if (btnClose3 != null) {
                btnClose3.setTextColor(getColor(R.color.color_7380a9));
            }
            TextView btnSure3 = getBtnSure();
            if (btnSure3 != null) {
                btnSure3.setTextColor(getColor(R.color.color_B2575C));
            }
            View viewLine13 = getViewLine1();
            if (viewLine13 != null) {
                viewLine13.setBackgroundColor(getColor(R.color.color_theme_blue_line_color));
            }
            View viewLine23 = getViewLine2();
            if (viewLine23 != null) {
                viewLine23.setBackgroundColor(getColor(R.color.color_theme_blue_line_color));
            }
            RLinearLayout layoutRoot3 = getLayoutRoot();
            helper = layoutRoot3 != null ? layoutRoot3.getHelper() : null;
            if (helper == null) {
                return;
            }
            helper.setBackgroundColorNormal(getColor(R.color.color_blue_theme_bg));
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            dismiss();
        }
    }
}
