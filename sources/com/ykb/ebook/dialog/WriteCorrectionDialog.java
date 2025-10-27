package com.ykb.ebook.dialog;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.Editable;
import android.util.ColorResourcesKt;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.xpopup.util.KeyboardUtils;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.BaseQuickAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.WriteCorrectionDialog;
import com.ykb.ebook.model.CorrectType;
import com.ykb.ebook.util.ToastUtilsKt;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/WriteCorrectionDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class WriteCorrectionDialog {

    @Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010B\u001a\u00020\"H\u0002J\b\u0010C\u001a\u00020\"H\u0002J\u0010\u0010D\u001a\u00020\"2\u0006\u0010E\u001a\u000208H\u0016J*\u0010D\u001a\u00020\"2\u0010\u0010\t\u001a\f\u0012\u0004\u0012\u00020\u0003\u0012\u0002\b\u00030F2\u0006\u0010E\u001a\u0002082\u0006\u0010G\u001a\u00020HH\u0016J\u000e\u0010I\u001a\u00020\u00002\u0006\u0010J\u001a\u00020\u0010J\u0014\u0010K\u001a\u00020\u00002\f\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00030MJ \u0010N\u001a\u00020\u00002\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\"0!R!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0011\u001a\u0004\u0018\u00010\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u000e\u001a\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0016\u001a\u0004\u0018\u00010\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u000e\u001a\u0004\b\u0018\u0010\u0019R\u001d\u0010\u001b\u001a\u0004\u0018\u00010\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u000e\u001a\u0004\b\u001d\u0010\u001eR\"\u0010 \u001a\u0016\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\"\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010#\u001a\u0004\u0018\u00010$8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u000e\u001a\u0004\b%\u0010&R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u001d\u0010*\u001a\u0004\u0018\u00010+8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b.\u0010\u000e\u001a\u0004\b,\u0010-R\u001d\u0010/\u001a\u0004\u0018\u0001008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b3\u0010\u000e\u001a\u0004\b1\u00102R\u001d\u00104\u001a\u0004\u0018\u00010+8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b6\u0010\u000e\u001a\u0004\b5\u0010-R\u001d\u00107\u001a\u0004\u0018\u0001088BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b;\u0010\u000e\u001a\u0004\b9\u0010:R\u001d\u0010<\u001a\u0004\u0018\u0001088BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b>\u0010\u000e\u001a\u0004\b=\u0010:R\u001d\u0010?\u001a\u0004\u0018\u0001088BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bA\u0010\u000e\u001a\u0004\b@\u0010:¨\u0006O"}, d2 = {"Lcom/ykb/ebook/dialog/WriteCorrectionDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemClickListener;", "Lcom/ykb/ebook/model/CorrectType;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "showKeyborad", "", "(Landroid/content/Context;Z)V", "adapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "getAdapter", "()Lcom/ykb/ebook/adapter/CommonAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "correctId", "", "etInput", "Lcom/ruffian/library/widget/REditText;", "getEtInput", "()Lcom/ruffian/library/widget/REditText;", "etInput$delegate", "imgClose", "Landroid/widget/ImageView;", "getImgClose", "()Landroid/widget/ImageView;", "imgClose$delegate", "layoutRoot", "Landroid/widget/LinearLayout;", "getLayoutRoot", "()Landroid/widget/LinearLayout;", "layoutRoot$delegate", "onPublishClick", "Lkotlin/Function2;", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "recyclerView$delegate", "getShowKeyborad", "()Z", "tvCitation", "Landroid/widget/TextView;", "getTvCitation", "()Landroid/widget/TextView;", "tvCitation$delegate", "tvPublish", "Lcom/ruffian/library/widget/RTextView;", "getTvPublish", "()Lcom/ruffian/library/widget/RTextView;", "tvPublish$delegate", "tvTitle", "getTvTitle", "tvTitle$delegate", "viewLine1", "Landroid/view/View;", "getViewLine1", "()Landroid/view/View;", "viewLine1$delegate", "viewLine2", "getViewLine2", "viewLine2$delegate", "viewTopLine", "getViewTopLine", "viewTopLine$delegate", "initThemeColor", "initView", "onClick", "view", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "position", "", "setCitation", "citation", "setCorrectData", "data", "", "setPublishClick", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nWriteCorrectionDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WriteCorrectionDialog.kt\ncom/ykb/ebook/dialog/WriteCorrectionDialog$Builder\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,198:1\n1855#2,2:199\n223#2,2:201\n42#3:203\n42#3:204\n42#3:205\n42#3:206\n42#3:207\n42#3:208\n*S KotlinDebug\n*F\n+ 1 WriteCorrectionDialog.kt\ncom/ykb/ebook/dialog/WriteCorrectionDialog$Builder\n*L\n115#1:199,2\n133#1:201,2\n162#1:203\n163#1:204\n177#1:205\n178#1:206\n192#1:207\n193#1:208\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> implements BaseQuickAdapter.OnItemClickListener<CorrectType> {

        /* renamed from: adapter$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy adapter;

        @NotNull
        private String correctId;

        /* renamed from: etInput$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy etInput;

        /* renamed from: imgClose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgClose;

        /* renamed from: layoutRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutRoot;

        @Nullable
        private Function2<? super String, ? super String, Unit> onPublishClick;

        /* renamed from: recyclerView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy recyclerView;
        private final boolean showKeyborad;

        /* renamed from: tvCitation$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvCitation;

        /* renamed from: tvPublish$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvPublish;

        /* renamed from: tvTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitle;

        /* renamed from: viewLine1$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine1;

        /* renamed from: viewLine2$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine2;

        /* renamed from: viewTopLine$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewTopLine;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context, boolean z2) throws SecurityException {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.showKeyborad = z2;
            this.imgClose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$imgClose$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_close);
                }
            });
            this.tvCitation = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$tvCitation$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_citation);
                }
            });
            this.tvPublish = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$tvPublish$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_publish);
                }
            });
            this.recyclerView = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$recyclerView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RecyclerView invoke() {
                    return (RecyclerView) this.this$0.findViewById(R.id.recycler_view);
                }
            });
            this.etInput = LazyKt__LazyJVMKt.lazy(new Function0<REditText>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$etInput$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final REditText invoke() {
                    return (REditText) this.this$0.findViewById(R.id.et_input);
                }
            });
            this.layoutRoot = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$layoutRoot$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.layoutRoot);
                }
            });
            this.tvTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$tvTitle$2
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
            this.viewLine2 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$viewLine2$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.view_line2);
                }
            });
            this.viewLine1 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$viewLine1$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.view_line1);
                }
            });
            this.viewTopLine = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$viewTopLine$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.view_top_line);
                }
            });
            this.correctId = "";
            this.adapter = LazyKt__LazyJVMKt.lazy(new Function0<CommonAdapter<CorrectType>>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$adapter$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final CommonAdapter<CorrectType> invoke() {
                    CommonAdapter<CorrectType> commonAdapter = new CommonAdapter<>(R.layout.item_correct, null, 2, null);
                    final WriteCorrectionDialog.Builder builder = this.this$0;
                    commonAdapter.setConvert(new Function3<QuickViewHolder, Integer, CorrectType, Unit>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$adapter$2$1$1
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, CorrectType correctType) {
                            invoke(quickViewHolder, num.intValue(), correctType);
                            return Unit.INSTANCE;
                        }

                        public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable CorrectType correctType) {
                            Intrinsics.checkNotNullParameter(holder, "holder");
                            ImageView imageView = (ImageView) holder.getView(R.id.iv_icon);
                            int i3 = R.id.tv_correct;
                            Intrinsics.checkNotNull(correctType);
                            holder.setText(i3, StringsKt__StringsKt.trim((CharSequence) correctType.getTitle()).toString());
                            TextView textView = (TextView) holder.getView(i3);
                            int colorMode = ReadConfig.INSTANCE.getColorMode();
                            if (colorMode == 0) {
                                textView.setTextColor(builder.getColor(R.color.color_303030));
                                imageView.setImageResource(R.drawable.correct_bg);
                            } else if (colorMode == 1) {
                                textView.setTextColor(builder.getColor(R.color.color_303030));
                                imageView.setImageResource(R.drawable.correct_yellow);
                            } else if (colorMode == 2) {
                                textView.setTextColor(builder.getColor(R.color.color_7380a9));
                                imageView.setImageResource(R.drawable.correct_bg_night);
                            }
                            imageView.setSelected(correctType.isCheck());
                        }
                    });
                    commonAdapter.setConvertPayload(new Function4<QuickViewHolder, Integer, CorrectType, List<? extends Object>, Unit>() { // from class: com.ykb.ebook.dialog.WriteCorrectionDialog$Builder$adapter$2$1$2
                        @Override // kotlin.jvm.functions.Function4
                        public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, CorrectType correctType, List<? extends Object> list) {
                            invoke(quickViewHolder, num.intValue(), correctType, list);
                            return Unit.INSTANCE;
                        }

                        public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable CorrectType correctType, @NotNull List<? extends Object> list) {
                            Intrinsics.checkNotNullParameter(holder, "holder");
                            Intrinsics.checkNotNullParameter(list, "<anonymous parameter 3>");
                            ImageView imageView = (ImageView) holder.getView(R.id.iv_icon);
                            Intrinsics.checkNotNull(correctType);
                            imageView.setSelected(correctType.isCheck());
                        }
                    });
                    commonAdapter.setOnItemClickListener(builder);
                    return commonAdapter;
                }
            });
            setContentView(R.layout.dialog_write_correction);
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setOnClickListener(getImgClose(), getTvPublish());
            initView();
            REditText etInput = getEtInput();
            if (etInput != null) {
                etInput.requestFocus();
            }
            if (z2) {
                new Handler().postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.i1
                    @Override // java.lang.Runnable
                    public final void run() {
                        WriteCorrectionDialog.Builder._init_$lambda$0(this.f26344c);
                    }
                }, 200L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(Builder this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            KeyboardUtils.showSoftInput(this$0.getEtInput());
        }

        private final CommonAdapter<CorrectType> getAdapter() {
            return (CommonAdapter) this.adapter.getValue();
        }

        private final REditText getEtInput() {
            return (REditText) this.etInput.getValue();
        }

        private final ImageView getImgClose() {
            return (ImageView) this.imgClose.getValue();
        }

        private final LinearLayout getLayoutRoot() {
            return (LinearLayout) this.layoutRoot.getValue();
        }

        private final RecyclerView getRecyclerView() {
            return (RecyclerView) this.recyclerView.getValue();
        }

        private final TextView getTvCitation() {
            return (TextView) this.tvCitation.getValue();
        }

        private final RTextView getTvPublish() {
            return (RTextView) this.tvPublish.getValue();
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

        private final View getViewTopLine() {
            return (View) this.viewTopLine.getValue();
        }

        private final void initThemeColor() throws SecurityException {
            RTextViewHelper helper;
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            if (colorMode == 0) {
                LinearLayout layoutRoot = getLayoutRoot();
                if (layoutRoot != null) {
                    layoutRoot.setBackground(getDrawable(R.drawable.shape_white_top_16_bg));
                }
                TextView tvTitle = getTvTitle();
                if (tvTitle != null) {
                    tvTitle.setTextColor(getColor(R.color.color_303030));
                }
                ImageView imgClose = getImgClose();
                if (imgClose != null) {
                    imgClose.setImageResource(R.drawable.ic_close);
                }
                TextView tvCitation = getTvCitation();
                if (tvCitation != null) {
                    tvCitation.setTextColor(getColor(R.color.color_909090));
                }
                REditText etInput = getEtInput();
                RTextViewHelper helper2 = etInput != null ? etInput.getHelper() : null;
                if (helper2 != null) {
                    helper2.setBackgroundColorNormal(getColor(R.color.color_f9fafb));
                }
                REditText etInput2 = getEtInput();
                if (etInput2 != null) {
                    etInput2.setTextColor(getColor(R.color.color_303030));
                }
                REditText etInput3 = getEtInput();
                if (etInput3 != null) {
                    etInput3.setHintTextColor(getColor(R.color.color_bfbfbf));
                }
                View viewLine1 = getViewLine1();
                if (viewLine1 != null) {
                    viewLine1.setBackground(new ColorDrawable(getColor(R.color.color_eeeeee)));
                }
                View viewLine2 = getViewLine2();
                if (viewLine2 != null) {
                    viewLine2.setBackground(new ColorDrawable(getColor(R.color.color_eeeeee)));
                }
                View viewTopLine = getViewTopLine();
                if (viewTopLine != null) {
                    viewTopLine.setBackground(new ColorDrawable(getColor(R.color.color_909090)));
                }
                RTextView tvPublish = getTvPublish();
                RTextViewHelper helper3 = tvPublish != null ? tvPublish.getHelper() : null;
                if (helper3 != null) {
                    helper3.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_dd594a));
                }
                RTextView tvPublish2 = getTvPublish();
                helper = tvPublish2 != null ? tvPublish2.getHelper() : null;
                if (helper == null) {
                    return;
                }
                helper.setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.white));
                return;
            }
            if (colorMode == 1) {
                LinearLayout layoutRoot2 = getLayoutRoot();
                if (layoutRoot2 != null) {
                    layoutRoot2.setBackground(getDrawable(R.drawable.shape_yellow_top_16_bg));
                }
                TextView tvTitle2 = getTvTitle();
                if (tvTitle2 != null) {
                    tvTitle2.setTextColor(getColor(R.color.color_303030));
                }
                ImageView imgClose2 = getImgClose();
                if (imgClose2 != null) {
                    imgClose2.setImageResource(R.mipmap.ic_close_color_mode_1);
                }
                TextView tvCitation2 = getTvCitation();
                if (tvCitation2 != null) {
                    tvCitation2.setTextColor(getColor(R.color.color_909090));
                }
                REditText etInput4 = getEtInput();
                RTextViewHelper helper4 = etInput4 != null ? etInput4.getHelper() : null;
                if (helper4 != null) {
                    helper4.setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
                }
                REditText etInput5 = getEtInput();
                if (etInput5 != null) {
                    etInput5.setTextColor(getColor(R.color.color_303030));
                }
                REditText etInput6 = getEtInput();
                if (etInput6 != null) {
                    etInput6.setHintTextColor(getColor(R.color.color_bfbfbf));
                }
                View viewLine12 = getViewLine1();
                if (viewLine12 != null) {
                    viewLine12.setBackground(new ColorDrawable(getColor(R.color.color_EDE2C3)));
                }
                View viewLine22 = getViewLine2();
                if (viewLine22 != null) {
                    viewLine22.setBackground(new ColorDrawable(getColor(R.color.color_EDE2C3)));
                }
                View viewTopLine2 = getViewTopLine();
                if (viewTopLine2 != null) {
                    viewTopLine2.setBackground(new ColorDrawable(getColor(R.color.color_909090)));
                }
                RTextView tvPublish3 = getTvPublish();
                RTextViewHelper helper5 = tvPublish3 != null ? tvPublish3.getHelper() : null;
                if (helper5 != null) {
                    helper5.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_dd594a));
                }
                RTextView tvPublish4 = getTvPublish();
                helper = tvPublish4 != null ? tvPublish4.getHelper() : null;
                if (helper == null) {
                    return;
                }
                helper.setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_EDE2C3));
                return;
            }
            if (colorMode != 2) {
                return;
            }
            LinearLayout layoutRoot3 = getLayoutRoot();
            if (layoutRoot3 != null) {
                layoutRoot3.setBackground(getDrawable(R.drawable.shape_blue_top_16_bg));
            }
            TextView tvTitle3 = getTvTitle();
            if (tvTitle3 != null) {
                tvTitle3.setTextColor(getColor(R.color.color_7380a9));
            }
            ImageView imgClose3 = getImgClose();
            if (imgClose3 != null) {
                imgClose3.setImageResource(R.drawable.icon_close_night_svg);
            }
            TextView tvCitation3 = getTvCitation();
            if (tvCitation3 != null) {
                tvCitation3.setTextColor(getColor(R.color.color_575F79));
            }
            REditText etInput7 = getEtInput();
            RTextViewHelper helper6 = etInput7 != null ? etInput7.getHelper() : null;
            if (helper6 != null) {
                helper6.setBackgroundColorNormal(getColor(R.color.color_171c2d));
            }
            REditText etInput8 = getEtInput();
            if (etInput8 != null) {
                etInput8.setTextColor(getColor(R.color.color_7380a9));
            }
            REditText etInput9 = getEtInput();
            if (etInput9 != null) {
                etInput9.setHintTextColor(getColor(R.color.color_575F79));
            }
            View viewLine13 = getViewLine1();
            if (viewLine13 != null) {
                viewLine13.setBackground(new ColorDrawable(getColor(R.color.color_1C2134)));
            }
            View viewLine23 = getViewLine2();
            if (viewLine23 != null) {
                viewLine23.setBackground(new ColorDrawable(getColor(R.color.color_1C2134)));
            }
            View viewTopLine3 = getViewTopLine();
            if (viewTopLine3 != null) {
                viewTopLine3.setBackground(new ColorDrawable(getColor(R.color.color_575F79)));
            }
            RTextView tvPublish5 = getTvPublish();
            RTextViewHelper helper7 = tvPublish5 != null ? tvPublish5.getHelper() : null;
            if (helper7 != null) {
                helper7.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
            }
            RTextView tvPublish6 = getTvPublish();
            helper = tvPublish6 != null ? tvPublish6.getHelper() : null;
            if (helper == null) {
                return;
            }
            helper.setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.white));
        }

        private final void initView() throws SecurityException {
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView != null) {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }
            RecyclerView recyclerView2 = getRecyclerView();
            if (recyclerView2 != null) {
                recyclerView2.setAdapter(getAdapter());
            }
            RecyclerView recyclerView3 = getRecyclerView();
            if (recyclerView3 != null) {
                recyclerView3.setHasFixedSize(true);
            }
            initThemeColor();
        }

        public final boolean getShowKeyborad() {
            return this.showKeyborad;
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            String string;
            Editable text;
            String string2;
            Intrinsics.checkNotNullParameter(view, "view");
            if (Intrinsics.areEqual(view, getImgClose())) {
                dismiss();
                return;
            }
            if (Intrinsics.areEqual(view, getTvPublish())) {
                REditText etInput = getEtInput();
                if (etInput == null || (text = etInput.getText()) == null || (string2 = text.toString()) == null || (string = StringsKt__StringsKt.trim((CharSequence) string2).toString()) == null) {
                    string = "";
                }
                if (string.length() == 0) {
                    ToastUtilsKt.toastOnUi$default(getContext(), "请输入纠错内容！", 0, 2, (Object) null);
                    return;
                }
                dismiss();
                Function2<? super String, ? super String, Unit> function2 = this.onPublishClick;
                if (function2 != null) {
                    function2.invoke(this.correctId, string);
                }
            }
        }

        @NotNull
        public final Builder setCitation(@NotNull String citation) {
            Intrinsics.checkNotNullParameter(citation, "citation");
            TextView tvCitation = getTvCitation();
            if (tvCitation != null) {
                tvCitation.setText(StringsKt__StringsKt.trimStart((CharSequence) citation).toString());
            }
            return this;
        }

        @NotNull
        public final Builder setCorrectData(@NotNull List<CorrectType> data) {
            Intrinsics.checkNotNullParameter(data, "data");
            Iterator<T> it = data.iterator();
            if (!it.hasNext()) {
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
            ((CorrectType) it.next()).setCheck(true);
            this.correctId = ((CorrectType) CollectionsKt___CollectionsKt.first((List) data)).getId();
            getAdapter().submitList(data);
            return this;
        }

        @NotNull
        public final Builder setPublishClick(@NotNull Function2<? super String, ? super String, Unit> onPublishClick) {
            Intrinsics.checkNotNullParameter(onPublishClick, "onPublishClick");
            this.onPublishClick = onPublishClick;
            return this;
        }

        @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter.OnItemClickListener
        public void onClick(@NotNull BaseQuickAdapter<CorrectType, ?> adapter, @NotNull View view, int position) {
            String id;
            Intrinsics.checkNotNullParameter(adapter, "adapter");
            Intrinsics.checkNotNullParameter(view, "view");
            Iterator<T> it = adapter.getItems().iterator();
            while (it.hasNext()) {
                ((CorrectType) it.next()).setCheck(false);
            }
            CorrectType item = adapter.getItem(position);
            if (item != null) {
                item.setCheck(true);
            }
            if (item == null || (id = item.getId()) == null) {
                id = "";
            }
            this.correctId = id;
            adapter.notifyItemRangeChanged(0, adapter.getItemCount(), 0);
        }

        public /* synthetic */ Builder(Context context, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(context, (i2 & 2) != 0 ? true : z2);
        }
    }
}
