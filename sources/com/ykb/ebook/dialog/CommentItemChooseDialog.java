package com.ykb.ebook.dialog;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.ColorResourcesKt;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.plv.socket.user.PLVSocketUserConstant;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RBaseHelper;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.BaseQuickAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.CommentItemChooseDialog;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/CommentItemChooseDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class CommentItemChooseDialog {

    @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u0007H\u0002J\b\u0010)\u001a\u00020\u0018H\u0002J\u0010\u0010*\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u001a\u0010+\u001a\u00020\u00002\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00180\u0017R\u001d\u0010\t\u001a\u0004\u0018\u00010\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u000f\u001a\u0004\u0018\u00010\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0019\u001a\u0004\u0018\u00010\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u000e\u001a\u0004\b\u001b\u0010\u001cR\u001d\u0010\u001e\u001a\u0004\u0018\u00010\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u000e\u001a\u0004\b \u0010!¨\u0006,"}, d2 = {"Lcom/ykb/ebook/dialog/CommentItemChooseDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "isOwner", "", "content", "", "(Landroid/content/Context;ZLjava/lang/String;)V", "btnCancel", "Lcom/ruffian/library/widget/RTextView;", "getBtnCancel", "()Lcom/ruffian/library/widget/RTextView;", "btnCancel$delegate", "Lkotlin/Lazy;", "llRoot", "Lcom/ruffian/library/widget/RLinearLayout;", "getLlRoot", "()Lcom/ruffian/library/widget/RLinearLayout;", "llRoot$delegate", "mAdapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "onItemClick", "Lkotlin/Function1;", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "recyclerView$delegate", "tvTitlte", "Landroid/widget/TextView;", "getTvTitlte", "()Landroid/widget/TextView;", "tvTitlte$delegate", "convert", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "", "item", "initTheme", "loadData", "setOnItemClick", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nCommentItemChooseDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CommentItemChooseDialog.kt\ncom/ykb/ebook/dialog/CommentItemChooseDialog$Builder\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,153:1\n42#2:154\n42#2:155\n42#2:156\n42#2:157\n42#2:158\n*S KotlinDebug\n*F\n+ 1 CommentItemChooseDialog.kt\ncom/ykb/ebook/dialog/CommentItemChooseDialog$Builder\n*L\n52#1:154\n53#1:155\n55#1:156\n129#1:157\n130#1:158\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: btnCancel$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy btnCancel;

        /* renamed from: llRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llRoot;

        @NotNull
        private CommonAdapter<String> mAdapter;

        @Nullable
        private Function1<? super String, Unit> onItemClick;

        /* renamed from: recyclerView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy recyclerView;

        /* renamed from: tvTitlte$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitlte;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context, boolean z2, @NotNull String content) throws SecurityException {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(content, "content");
            this.tvTitlte = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommentItemChooseDialog$Builder$tvTitlte$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_comment_content);
                }
            });
            this.recyclerView = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.ykb.ebook.dialog.CommentItemChooseDialog$Builder$recyclerView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RecyclerView invoke() {
                    return (RecyclerView) this.this$0.findViewById(R.id.recyclerView);
                }
            });
            this.btnCancel = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.CommentItemChooseDialog$Builder$btnCancel$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_comment_cancel);
                }
            });
            this.llRoot = LazyKt__LazyJVMKt.lazy(new Function0<RLinearLayout>() { // from class: com.ykb.ebook.dialog.CommentItemChooseDialog$Builder$llRoot$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RLinearLayout invoke() {
                    return (RLinearLayout) this.this$0.findViewById(R.id.ll_root);
                }
            });
            setContentView(R.layout.pop_question_comment_menu_bottom_book);
            View viewFindViewById = findViewById(R.id.line);
            if (viewFindViewById != null) {
                viewFindViewById.setBackground(new ColorDrawable(ReadConfig.INSTANCE.getColorMode() != 2 ? StringExtensionsKt.hexValue2IntColor("#EEEEEE") : StringExtensionsKt.hexValue2IntColor("#2E3241")));
            }
            RLinearLayout rLinearLayout = (RLinearLayout) findViewById(R.id.iv_top_indicator);
            RBaseHelper helper = rLinearLayout != null ? rLinearLayout.getHelper() : null;
            if (helper != null) {
                helper.setBackgroundColorNormal(ReadConfig.INSTANCE.getColorMode() == 2 ? StringExtensionsKt.hexValue2IntColor("#2E3241") : StringExtensionsKt.hexValue2IntColor("#eeeeee"));
            }
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            TextView tvTitlte = getTvTitlte();
            if (tvTitlte != null) {
                tvTitlte.setText(content);
            }
            TextView tvTitlte2 = getTvTitlte();
            if (tvTitlte2 != null) {
                tvTitlte2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
            }
            RTextView btnCancel = getBtnCancel();
            if (btnCancel != null) {
                btnCancel.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
            }
            RTextView btnCancel2 = getBtnCancel();
            RTextViewHelper helper2 = btnCancel2 != null ? btnCancel2.getHelper() : null;
            if (helper2 != null) {
                helper2.setBackgroundColorNormal(ReadConfig.INSTANCE.getColorMode() == 2 ? StringExtensionsKt.hexValue2IntColor("#292D3E") : ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_f7f8fa));
            }
            CommonAdapter<String> commonAdapter = new CommonAdapter<>(R.layout.item_comment_type_choose, null, 2, null);
            this.mAdapter = commonAdapter;
            commonAdapter.setConvert(new Function3<QuickViewHolder, Integer, String, Unit>() { // from class: com.ykb.ebook.dialog.CommentItemChooseDialog.Builder.1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, String str) {
                    invoke(quickViewHolder, num.intValue(), str);
                    return Unit.INSTANCE;
                }

                public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable String str) {
                    Intrinsics.checkNotNullParameter(holder, "holder");
                    Builder builder = Builder.this;
                    Intrinsics.checkNotNull(str);
                    builder.convert(holder, i2, str);
                }
            });
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView != null) {
                recyclerView.setAdapter(this.mAdapter);
            }
            this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ykb.ebook.dialog.b0
                @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    CommentItemChooseDialog.Builder._init_$lambda$0(this.f26304c, baseQuickAdapter, view, i2);
                }
            });
            RTextView btnCancel3 = getBtnCancel();
            if (btnCancel3 != null) {
                btnCancel3.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.c0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CommentItemChooseDialog.Builder._init_$lambda$1(this.f26308c, view);
                    }
                });
            }
            loadData(z2);
            initTheme();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(Builder this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
            String item = this$0.mAdapter.getItem(i2);
            Intrinsics.checkNotNull(item);
            String str = item;
            this$0.dismiss();
            Function1<? super String, Unit> function1 = this$0.onItemClick;
            if (function1 != null) {
                function1.invoke(str);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$1(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dismiss();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void convert(QuickViewHolder holder, int position, String item) {
            int itemCount = this.mAdapter.getItemCount() - 1;
            int i2 = R.id.tv_content;
            BaseViewHolder text = holder.setText(i2, item);
            int i3 = R.id.v_line;
            BaseViewHolder visible = text.setVisible(i3, position != itemCount);
            ReadConfig readConfig = ReadConfig.INSTANCE;
            visible.setTextColor(i2, ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030)).setBackgroundColor(i3, readConfig.getColorMode() == 2 ? StringExtensionsKt.hexValue2IntColor("#2E3241") : ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_eeeeee));
            View view = holder.getView(i3);
            int colorMode = readConfig.getColorMode();
            if (colorMode == 0) {
                view.setBackgroundColor(getColor(R.color.color_theme_white_line_color));
            } else if (colorMode == 1) {
                view.setBackgroundColor(getColor(R.color.color_theme_yellow_line_color));
            } else {
                if (colorMode != 2) {
                    return;
                }
                view.setBackgroundColor(getColor(R.color.color_theme_blue_line_color));
            }
        }

        private final RTextView getBtnCancel() {
            return (RTextView) this.btnCancel.getValue();
        }

        private final RLinearLayout getLlRoot() {
            return (RLinearLayout) this.llRoot.getValue();
        }

        private final RecyclerView getRecyclerView() {
            return (RecyclerView) this.recyclerView.getValue();
        }

        private final TextView getTvTitlte() {
            return (TextView) this.tvTitlte.getValue();
        }

        private final void initTheme() throws SecurityException {
            RTextViewHelper helper;
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            if (colorMode == 0) {
                RLinearLayout llRoot = getLlRoot();
                RBaseHelper helper2 = llRoot != null ? llRoot.getHelper() : null;
                if (helper2 != null) {
                    helper2.setBackgroundColorNormal(getColor(R.color.white));
                }
                TextView tvTitlte = getTvTitlte();
                if (tvTitlte != null) {
                    tvTitlte.setTextColor(getColor(R.color.color_909090));
                }
                RTextView btnCancel = getBtnCancel();
                helper = btnCancel != null ? btnCancel.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(getColor(R.color.color_f7f8fa));
                }
                RTextView btnCancel2 = getBtnCancel();
                if (btnCancel2 != null) {
                    btnCancel2.setTextColor(getColor(R.color.color_303030));
                    return;
                }
                return;
            }
            if (colorMode == 1) {
                RLinearLayout llRoot2 = getLlRoot();
                RBaseHelper helper3 = llRoot2 != null ? llRoot2.getHelper() : null;
                if (helper3 != null) {
                    helper3.setBackgroundColorNormal(getColor(R.color.color_F5EBCE));
                }
                TextView tvTitlte2 = getTvTitlte();
                if (tvTitlte2 != null) {
                    tvTitlte2.setTextColor(getColor(R.color.color_909090));
                }
                RTextView btnCancel3 = getBtnCancel();
                helper = btnCancel3 != null ? btnCancel3.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
                }
                RTextView btnCancel4 = getBtnCancel();
                if (btnCancel4 != null) {
                    btnCancel4.setTextColor(getColor(R.color.color_303030));
                    return;
                }
                return;
            }
            if (colorMode != 2) {
                return;
            }
            RLinearLayout llRoot3 = getLlRoot();
            RBaseHelper helper4 = llRoot3 != null ? llRoot3.getHelper() : null;
            if (helper4 != null) {
                helper4.setBackgroundColorNormal(getColor(R.color.color_121622));
            }
            TextView tvTitlte3 = getTvTitlte();
            if (tvTitlte3 != null) {
                tvTitlte3.setTextColor(getColor(R.color.color_7380a9));
            }
            RTextView btnCancel5 = getBtnCancel();
            helper = btnCancel5 != null ? btnCancel5.getHelper() : null;
            if (helper != null) {
                helper.setBackgroundColorNormal(getColor(R.color.color_171C2D));
            }
            RTextView btnCancel6 = getBtnCancel();
            if (btnCancel6 != null) {
                btnCancel6.setTextColor(getColor(R.color.color_7380a9));
            }
        }

        private final void loadData(boolean isOwner) {
            ArrayList arrayList = new ArrayList();
            if (Intrinsics.areEqual(ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), PLVSocketUserConstant.ROLE_ADMIN, null, 2, null), "1")) {
                arrayList.add("回复");
                arrayList.add("复制");
                arrayList.add("编辑");
                arrayList.add("删除");
                arrayList.add("封禁");
            } else {
                arrayList.add("回复");
                arrayList.add("复制");
                arrayList.add("举报");
                if (isOwner) {
                    arrayList.add("编辑");
                    arrayList.add("删除");
                }
            }
            this.mAdapter.submitList(arrayList);
        }

        @NotNull
        public final Builder setOnItemClick(@NotNull Function1<? super String, Unit> onItemClick) {
            Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
            this.onItemClick = onItemClick;
            return this;
        }
    }
}
