package com.ykb.ebook.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
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
import com.ykb.ebook.dialog.ReportReasonChooseDialog;
import com.ykb.ebook.model.ReportReason;
import com.ykb.ebook.util.Coroutine;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/ReportReasonChooseDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ReportReasonChooseDialog {

    @Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ \u0010=\u001a\u00020\u001e2\u0006\u0010>\u001a\u00020\u00052\u0006\u0010?\u001a\u00020\u00052\u0006\u0010@\u001a\u00020\u0005H\u0002J\b\u0010A\u001a\u00020\u001eH\u0002J \u0010B\u001a\u00020\u001e2\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u001d2\u0006\u0010F\u001a\u00020\u0005H\u0002J\b\u0010G\u001a\u00020\u001eH\u0002J\b\u0010H\u001a\u00020\u001eH\u0002J \u0010I\u001a\u00020\u00002\u0018\u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u001e0\u001cR\u001d\u0010\n\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u001d\u0010\u0010\u001a\u0004\u0018\u00010\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0015\u001a\u0004\u0018\u00010\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u000f\u001a\u0004\b\u0016\u0010\u0013R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u001b\u001a\u0016\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u001f\u001a\u0004\u0018\u00010 8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u000f\u001a\u0004\b!\u0010\"R\u001d\u0010$\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\u000f\u001a\u0004\b&\u0010'R\u001d\u0010)\u001a\u0004\u0018\u00010*8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b-\u0010\u000f\u001a\u0004\b+\u0010,R\u001d\u0010.\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\u000f\u001a\u0004\b/\u0010'R\u001e\u00101\u001a\u0012\u0012\u0004\u0012\u00020302j\b\u0012\u0004\u0012\u000203`4X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u00105\u001a\u0004\u0018\u0001068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b9\u0010\u000f\u001a\u0004\b7\u00108R\u001d\u0010:\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b<\u0010\u000f\u001a\u0004\b;\u0010'¨\u0006J"}, d2 = {"Lcom/ykb/ebook/dialog/ReportReasonChooseDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "content", "", "review_id", "isReport", "", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Z)V", "btnCancel", "Lcom/ruffian/library/widget/RTextView;", "getBtnCancel", "()Lcom/ruffian/library/widget/RTextView;", "btnCancel$delegate", "Lkotlin/Lazy;", "ivTopIndicator", "Lcom/ruffian/library/widget/RLinearLayout;", "getIvTopIndicator", "()Lcom/ruffian/library/widget/RLinearLayout;", "ivTopIndicator$delegate", "llRoot", "getLlRoot", "llRoot$delegate", "mAdapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "mIsReport", "onItemClick", "Lkotlin/Function2;", "", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "recyclerView$delegate", "reportBot", "Landroid/widget/TextView;", "getReportBot", "()Landroid/widget/TextView;", "reportBot$delegate", "reportLl", "Landroid/widget/LinearLayout;", "getReportLl", "()Landroid/widget/LinearLayout;", "reportLl$delegate", "reportTop", "getReportTop", "reportTop$delegate", "resonList", "Ljava/util/ArrayList;", "Lcom/ykb/ebook/model/ReportReason$DataBean;", "Lkotlin/collections/ArrayList;", "topLine", "Landroid/view/View;", "getTopLine", "()Landroid/view/View;", "topLine$delegate", "tvTitlte", "getTvTitlte", "tvTitlte$delegate", "commentReport", "commentId", "reasonId", "reason", "commentReportReson", "convert", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "item", "initTheme", "loadData", "setOnItemClick", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: btnCancel$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy btnCancel;

        /* renamed from: ivTopIndicator$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy ivTopIndicator;

        /* renamed from: llRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llRoot;

        @NotNull
        private CommonAdapter<String> mAdapter;
        private boolean mIsReport;

        @Nullable
        private Function2<? super Integer, ? super String, Unit> onItemClick;

        /* renamed from: recyclerView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy recyclerView;

        /* renamed from: reportBot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy reportBot;

        /* renamed from: reportLl$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy reportLl;

        /* renamed from: reportTop$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy reportTop;

        @NotNull
        private ArrayList<ReportReason.DataBean> resonList;

        /* renamed from: topLine$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy topLine;

        /* renamed from: tvTitlte$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitlte;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context, @NotNull String content, @NotNull final String review_id, boolean z2) throws SecurityException {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(content, "content");
            Intrinsics.checkNotNullParameter(review_id, "review_id");
            this.mIsReport = true;
            this.resonList = new ArrayList<>();
            this.tvTitlte = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$tvTitlte$2
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
            this.topLine = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$topLine$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.line);
                }
            });
            this.reportTop = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$reportTop$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.reportTop);
                }
            });
            this.reportBot = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$reportBot$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.reportBot);
                }
            });
            this.reportLl = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$reportLl$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.reportLl);
                }
            });
            this.recyclerView = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$recyclerView$2
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
            this.btnCancel = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$btnCancel$2
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
            this.llRoot = LazyKt__LazyJVMKt.lazy(new Function0<RLinearLayout>() { // from class: com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$llRoot$2
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
            this.ivTopIndicator = LazyKt__LazyJVMKt.lazy(new Function0<RLinearLayout>() { // from class: com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$ivTopIndicator$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RLinearLayout invoke() {
                    return (RLinearLayout) this.this$0.findViewById(R.id.iv_top_indicator);
                }
            });
            setContentView(R.layout.pop_question_comment_menu_bottom_book);
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            TextView tvTitlte = getTvTitlte();
            if (tvTitlte != null) {
                tvTitlte.setText(content);
            }
            this.mIsReport = z2;
            CommonAdapter<String> commonAdapter = new CommonAdapter<>(R.layout.item_comment_type_choose, null, 2, null);
            this.mAdapter = commonAdapter;
            commonAdapter.setConvert(new Function3<QuickViewHolder, Integer, String, Unit>() { // from class: com.ykb.ebook.dialog.ReportReasonChooseDialog.Builder.1
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
            this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ykb.ebook.dialog.t0
                @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    ReportReasonChooseDialog.Builder._init_$lambda$0(this.f26389c, review_id, baseQuickAdapter, view, i2);
                }
            });
            RTextView btnCancel = getBtnCancel();
            if (btnCancel != null) {
                btnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.u0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ReportReasonChooseDialog.Builder._init_$lambda$1(this.f26392c, view);
                    }
                });
            }
            loadData();
            initTheme();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(Builder this$0, String review_id, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(review_id, "$review_id");
            Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
            String item = this$0.mAdapter.getItem(i2);
            Intrinsics.checkNotNull(item);
            String str = item;
            this$0.dismiss();
            if (!this$0.mIsReport) {
                Function2<? super Integer, ? super String, Unit> function2 = this$0.onItemClick;
                if (function2 != null) {
                    function2.invoke(Integer.valueOf(i2), str);
                    return;
                }
                return;
            }
            String id = this$0.resonList.get(i2).getId();
            Intrinsics.checkNotNullExpressionValue(id, "resonList[position].id");
            String title = this$0.resonList.get(i2).getTitle();
            Intrinsics.checkNotNullExpressionValue(title, "resonList[position].title");
            this$0.commentReport(review_id, id, title);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$1(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dismiss();
        }

        private final void commentReport(String commentId, String reasonId, String reason) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new ReportReasonChooseDialog$Builder$commentReport$1(MapsKt__MapsKt.hashMapOf(new Pair("review_id", commentId), new Pair("reason_id", reasonId), new Pair("content", reason)), null), 15, null), null, new ReportReasonChooseDialog$Builder$commentReport$2(this, null), 1, null), null, new ReportReasonChooseDialog$Builder$commentReport$3(null), 1, null);
        }

        private final void commentReportReson() {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new ReportReasonChooseDialog$Builder$commentReportReson$1(null), 15, null), null, new ReportReasonChooseDialog$Builder$commentReportReson$2(this, null), 1, null), null, new ReportReasonChooseDialog$Builder$commentReportReson$3(null), 1, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void convert(QuickViewHolder holder, int position, String item) {
            int itemCount = this.mAdapter.getItemCount() - 1;
            int i2 = R.id.tv_content;
            BaseViewHolder text = holder.setText(i2, item);
            int i3 = R.id.v_line;
            text.setVisible(i3, position != itemCount);
            View view = holder.getView(i3);
            TextView textView = (TextView) holder.getView(i2);
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            if (colorMode == 0) {
                textView.setTextColor(getColor(R.color.color_303030));
                int i4 = R.color.color_theme_white_line_color;
                view.setBackgroundColor(getColor(i4));
                View topLine = getTopLine();
                if (topLine != null) {
                    topLine.setBackgroundColor(getColor(i4));
                    return;
                }
                return;
            }
            if (colorMode == 1) {
                textView.setTextColor(getColor(R.color.color_303030));
                int i5 = R.color.color_theme_yellow_line_color;
                view.setBackgroundColor(getColor(i5));
                View topLine2 = getTopLine();
                if (topLine2 != null) {
                    topLine2.setBackgroundColor(getColor(i5));
                    return;
                }
                return;
            }
            if (colorMode != 2) {
                return;
            }
            textView.setTextColor(getColor(R.color.color_7380a9));
            int i6 = R.color.color_theme_blue_line_color;
            view.setBackgroundColor(getColor(i6));
            View topLine3 = getTopLine();
            if (topLine3 != null) {
                topLine3.setBackgroundColor(getColor(i6));
            }
        }

        private final RTextView getBtnCancel() {
            return (RTextView) this.btnCancel.getValue();
        }

        private final RLinearLayout getIvTopIndicator() {
            return (RLinearLayout) this.ivTopIndicator.getValue();
        }

        private final RLinearLayout getLlRoot() {
            return (RLinearLayout) this.llRoot.getValue();
        }

        private final RecyclerView getRecyclerView() {
            return (RecyclerView) this.recyclerView.getValue();
        }

        private final TextView getReportBot() {
            return (TextView) this.reportBot.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final LinearLayout getReportLl() {
            return (LinearLayout) this.reportLl.getValue();
        }

        private final TextView getReportTop() {
            return (TextView) this.reportTop.getValue();
        }

        private final View getTopLine() {
            return (View) this.topLine.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TextView getTvTitlte() {
            return (TextView) this.tvTitlte.getValue();
        }

        private final void initTheme() throws SecurityException {
            RTextViewHelper helper;
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            if (colorMode == 0) {
                RLinearLayout llRoot = getLlRoot();
                RBaseHelper helper2 = llRoot != null ? llRoot.getHelper() : null;
                if (helper2 != null) {
                    helper2.setBackgroundColorNormal(getColor(R.color.color_white_theme_bg));
                }
                RLinearLayout ivTopIndicator = getIvTopIndicator();
                RBaseHelper helper3 = ivTopIndicator != null ? ivTopIndicator.getHelper() : null;
                if (helper3 != null) {
                    helper3.setBackgroundColorNormal(getColor(R.color.color_c2c6cb));
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
                    btnCancel2.setTextColor(getColor(R.color.color_909090));
                }
                TextView reportTop = getReportTop();
                if (reportTop != null) {
                    reportTop.setTextColor(getColor(R.color.color_909090));
                }
                TextView reportBot = getReportBot();
                if (reportBot != null) {
                    reportBot.setTextColor(getColor(R.color.color_c2c6cb));
                    return;
                }
                return;
            }
            if (colorMode == 1) {
                RLinearLayout llRoot2 = getLlRoot();
                RBaseHelper helper4 = llRoot2 != null ? llRoot2.getHelper() : null;
                if (helper4 != null) {
                    helper4.setBackgroundColorNormal(getColor(R.color.color_yellow_theme_bg));
                }
                RLinearLayout ivTopIndicator2 = getIvTopIndicator();
                RBaseHelper helper5 = ivTopIndicator2 != null ? ivTopIndicator2.getHelper() : null;
                if (helper5 != null) {
                    helper5.setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
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
                    btnCancel4.setTextColor(getColor(R.color.color_909090));
                }
                TextView reportTop2 = getReportTop();
                if (reportTop2 != null) {
                    reportTop2.setTextColor(getColor(R.color.color_606060));
                }
                TextView reportBot2 = getReportBot();
                if (reportBot2 != null) {
                    reportBot2.setTextColor(getColor(R.color.color_909090));
                    return;
                }
                return;
            }
            if (colorMode != 2) {
                return;
            }
            RLinearLayout llRoot3 = getLlRoot();
            RBaseHelper helper6 = llRoot3 != null ? llRoot3.getHelper() : null;
            if (helper6 != null) {
                helper6.setBackgroundColorNormal(getColor(R.color.color_blue_theme_bg));
            }
            RLinearLayout ivTopIndicator3 = getIvTopIndicator();
            RBaseHelper helper7 = ivTopIndicator3 != null ? ivTopIndicator3.getHelper() : null;
            if (helper7 != null) {
                helper7.setBackgroundColorNormal(getColor(R.color.color_7380a9));
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
            TextView reportTop3 = getReportTop();
            if (reportTop3 != null) {
                reportTop3.setTextColor(getColor(R.color.color_7380a9));
            }
            TextView reportBot3 = getReportBot();
            if (reportBot3 != null) {
                reportBot3.setTextColor(getColor(R.color.color_575F79));
            }
        }

        private final void loadData() {
            if (this.mIsReport) {
                commentReportReson();
                return;
            }
            this.mAdapter.submitList(CollectionsKt__CollectionsKt.arrayListOf("永久禁言", "禁言1天", "禁言3天", "禁言7天", "禁言30天"));
            LinearLayout reportLl = getReportLl();
            if (reportLl != null) {
                reportLl.setVisibility(8);
            }
            TextView tvTitlte = getTvTitlte();
            if (tvTitlte == null) {
                return;
            }
            tvTitlte.setVisibility(0);
        }

        @NotNull
        public final Builder setOnItemClick(@NotNull Function2<? super Integer, ? super String, Unit> onItemClick) {
            Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
            this.onItemClick = onItemClick;
            return this;
        }
    }
}
