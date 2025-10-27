package com.ykb.ebook.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.lxj.xpopup.util.KeyboardUtils;
import com.psychiatrygarden.utils.Constants;
import com.ruffian.library.widget.RCheckBox;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RFrameLayout;
import com.ruffian.library.widget.helper.RBaseHelper;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.ChapterSearchDialog;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.TextSearchResult;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.util.ToastUtilsKt;
import com.ykb.ebook.util.ViewUtilKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/ChapterSearchDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ChapterSearchDialog {

    @Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010n\u001a\u00020:H\u0002J\b\u0010o\u001a\u00020:H\u0002J\b\u0010p\u001a\u00020:H\u0002J\u0010\u0010q\u001a\u00020:2\u0006\u0010r\u001a\u00020aH\u0016J\u000e\u0010s\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\rJ,\u0010t\u001a\u00020\u00002$\u00108\u001a \u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020:09R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u000e\u001a\u0004\u0018\u00010\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u000b\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0013\u001a\u0004\u0018\u00010\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\u0018\u001a\u0004\u0018\u00010\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u000b\u001a\u0004\b\u001a\u0010\u001bR\u001d\u0010\u001d\u001a\u0004\u0018\u00010\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u000b\u001a\u0004\b\u001e\u0010\u001bR\u000e\u0010 \u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010#\u001a\u0004\u0018\u00010$8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u000b\u001a\u0004\b%\u0010&R\u001d\u0010(\u001a\u0004\u0018\u00010$8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\u000b\u001a\u0004\b)\u0010&R\u001d\u0010+\u001a\u0004\u0018\u00010,8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b/\u0010\u000b\u001a\u0004\b-\u0010.R\u001d\u00100\u001a\u0004\u0018\u0001018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\u000b\u001a\u0004\b2\u00103R\u001d\u00105\u001a\u0004\u0018\u0001018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b7\u0010\u000b\u001a\u0004\b6\u00103R.\u00108\u001a\"\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020:\u0018\u000109X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010=\u001a\u0004\u0018\u00010>8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bA\u0010\u000b\u001a\u0004\b?\u0010@R\u001d\u0010B\u001a\u0004\u0018\u00010C8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bF\u0010\u000b\u001a\u0004\bD\u0010ER\u001d\u0010G\u001a\u0004\u0018\u00010H8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bK\u0010\u000b\u001a\u0004\bI\u0010JR\u001d\u0010L\u001a\u0004\u0018\u00010M8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bP\u0010\u000b\u001a\u0004\bN\u0010OR\u001d\u0010Q\u001a\u0004\u0018\u00010M8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bS\u0010\u000b\u001a\u0004\bR\u0010OR\u001d\u0010T\u001a\u0004\u0018\u00010M8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bV\u0010\u000b\u001a\u0004\bU\u0010OR\u001d\u0010W\u001a\u0004\u0018\u00010M8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bY\u0010\u000b\u001a\u0004\bX\u0010OR\u001d\u0010Z\u001a\u0004\u0018\u00010M8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\\\u0010\u000b\u001a\u0004\b[\u0010OR\u001d\u0010]\u001a\u0004\u0018\u00010M8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b_\u0010\u000b\u001a\u0004\b^\u0010OR\u001d\u0010`\u001a\u0004\u0018\u00010a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bd\u0010\u000b\u001a\u0004\bb\u0010cR\u001d\u0010e\u001a\u0004\u0018\u00010a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bg\u0010\u000b\u001a\u0004\bf\u0010cR\u001d\u0010h\u001a\u0004\u0018\u00010a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bj\u0010\u000b\u001a\u0004\bi\u0010cR\u001d\u0010k\u001a\u0004\u0018\u00010a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bm\u0010\u000b\u001a\u0004\bl\u0010c¨\u0006u"}, d2 = {"Lcom/ykb/ebook/dialog/ChapterSearchDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "adapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "Lcom/ykb/ebook/model/TextSearchResult;", "getAdapter", "()Lcom/ykb/ebook/adapter/CommonAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "bookId", "", "cbSort", "Lcom/ruffian/library/widget/RCheckBox;", "getCbSort", "()Lcom/ruffian/library/widget/RCheckBox;", "cbSort$delegate", "etInput", "Lcom/ruffian/library/widget/REditText;", "getEtInput", "()Lcom/ruffian/library/widget/REditText;", "etInput$delegate", "imgClean", "Landroid/widget/ImageView;", "getImgClean", "()Landroid/widget/ImageView;", "imgClean$delegate", "imgClose", "getImgClose", "imgClose$delegate", "input", "isReverse", "", "layoutSearch", "Landroid/widget/FrameLayout;", "getLayoutSearch", "()Landroid/widget/FrameLayout;", "layoutSearch$delegate", "layoutSearchResult", "getLayoutSearchResult", "layoutSearchResult$delegate", "layoutTitle", "Lcom/ruffian/library/widget/RFrameLayout;", "getLayoutTitle", "()Lcom/ruffian/library/widget/RFrameLayout;", "layoutTitle$delegate", "llEmpty", "Landroid/widget/LinearLayout;", "getLlEmpty", "()Landroid/widget/LinearLayout;", "llEmpty$delegate", "llResult", "getLlResult", "llResult$delegate", "onItemClick", "Lkotlin/Function4;", "", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "recyclerView$delegate", "refreshHeader", "Lcom/scwang/smartrefresh/layout/header/ClassicsHeader;", "getRefreshHeader", "()Lcom/scwang/smartrefresh/layout/header/ClassicsHeader;", "refreshHeader$delegate", "refreshLayout", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "getRefreshLayout", "()Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "refreshLayout$delegate", "tv1", "Landroid/widget/TextView;", "getTv1", "()Landroid/widget/TextView;", "tv1$delegate", "tv2", "getTv2", "tv2$delegate", "tv3", "getTv3", "tv3$delegate", "tv4", "getTv4", "tv4$delegate", "tvNumber", "getTvNumber", "tvNumber$delegate", "tvTitle", "getTvTitle", "tvTitle$delegate", "view2", "Landroid/view/View;", "getView2", "()Landroid/view/View;", "view2$delegate", "view3", "getView3", "view3$delegate", "view4", "getView4", "view4$delegate", "viewLine3", "getViewLine3", "viewLine3$delegate", "initTheme", "initView", "loadData", "onClick", "view", "setBookId", "setOnItemClick", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nChapterSearchDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChapterSearchDialog.kt\ncom/ykb/ebook/dialog/ChapterSearchDialog$Builder\n+ 2 TextView.kt\nandroidx/core/widget/TextViewKt\n*L\n1#1,348:1\n65#2,16:349\n93#2,3:365\n*S KotlinDebug\n*F\n+ 1 ChapterSearchDialog.kt\ncom/ykb/ebook/dialog/ChapterSearchDialog$Builder\n*L\n197#1:349,16\n197#1:365,3\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: adapter$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy adapter;

        @NotNull
        private String bookId;

        /* renamed from: cbSort$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbSort;

        /* renamed from: etInput$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy etInput;

        /* renamed from: imgClean$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgClean;

        /* renamed from: imgClose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgClose;

        @NotNull
        private String input;
        private int isReverse;

        /* renamed from: layoutSearch$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutSearch;

        /* renamed from: layoutSearchResult$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutSearchResult;

        /* renamed from: layoutTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutTitle;

        /* renamed from: llEmpty$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llEmpty;

        /* renamed from: llResult$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llResult;

        @Nullable
        private Function4<? super Integer, ? super TextSearchResult, ? super String, ? super String, Unit> onItemClick;
        private int page;
        private int pageSize;

        /* renamed from: recyclerView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy recyclerView;

        /* renamed from: refreshHeader$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy refreshHeader;

        /* renamed from: refreshLayout$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy refreshLayout;

        /* renamed from: tv1$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tv1;

        /* renamed from: tv2$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tv2;

        /* renamed from: tv3$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tv3;

        /* renamed from: tv4$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tv4;

        /* renamed from: tvNumber$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvNumber;

        /* renamed from: tvTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitle;

        /* renamed from: view2$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy view2;

        /* renamed from: view3$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy view3;

        /* renamed from: view4$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy view4;

        /* renamed from: viewLine3$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine3;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context) throws SecurityException {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.imgClose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$imgClose$2
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
            this.tvTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$tvTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tvTitle);
                }
            });
            this.tv4 = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$tv4$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv4);
                }
            });
            this.tv3 = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$tv3$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv3);
                }
            });
            this.tv2 = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$tv2$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv2);
                }
            });
            this.tv1 = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$tv1$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv1);
                }
            });
            this.view2 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$view2$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.view2);
                }
            });
            this.view3 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$view3$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.view3);
                }
            });
            this.view4 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$view4$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.view4);
                }
            });
            this.viewLine3 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$viewLine3$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.viewLine3);
                }
            });
            this.layoutTitle = LazyKt__LazyJVMKt.lazy(new Function0<RFrameLayout>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$layoutTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RFrameLayout invoke() {
                    return (RFrameLayout) this.this$0.findViewById(R.id.layoutTitle);
                }
            });
            this.layoutSearch = LazyKt__LazyJVMKt.lazy(new Function0<FrameLayout>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$layoutSearch$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final FrameLayout invoke() {
                    return (FrameLayout) this.this$0.findViewById(R.id.layoutSearch);
                }
            });
            this.layoutSearchResult = LazyKt__LazyJVMKt.lazy(new Function0<FrameLayout>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$layoutSearchResult$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final FrameLayout invoke() {
                    return (FrameLayout) this.this$0.findViewById(R.id.layoutSearchResult);
                }
            });
            this.imgClean = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$imgClean$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_clean);
                }
            });
            this.etInput = LazyKt__LazyJVMKt.lazy(new Function0<REditText>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$etInput$2
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
            this.llEmpty = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$llEmpty$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ll_empty);
                }
            });
            this.llResult = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$llResult$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ll_result);
                }
            });
            this.tvNumber = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$tvNumber$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_number);
                }
            });
            this.cbSort = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$cbSort$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_sort);
                }
            });
            this.refreshLayout = LazyKt__LazyJVMKt.lazy(new Function0<SmartRefreshLayout>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$refreshLayout$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final SmartRefreshLayout invoke() {
                    return (SmartRefreshLayout) this.this$0.findViewById(R.id.refresh);
                }
            });
            this.refreshHeader = LazyKt__LazyJVMKt.lazy(new Function0<ClassicsHeader>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$refreshHeader$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ClassicsHeader invoke() {
                    return (ClassicsHeader) this.this$0.findViewById(R.id.refresh_header);
                }
            });
            this.recyclerView = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$recyclerView$2
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
            this.page = 1;
            this.pageSize = 20;
            this.bookId = "";
            this.input = "";
            this.adapter = LazyKt__LazyJVMKt.lazy(new ChapterSearchDialog$Builder$adapter$2(this, context));
            setContentView(R.layout.dialog_chapter_search);
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setOnClickListener(getImgClose(), getImgClean());
            initView();
            initTheme();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final CommonAdapter<TextSearchResult> getAdapter() {
            return (CommonAdapter) this.adapter.getValue();
        }

        private final RCheckBox getCbSort() {
            return (RCheckBox) this.cbSort.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final REditText getEtInput() {
            return (REditText) this.etInput.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ImageView getImgClean() {
            return (ImageView) this.imgClean.getValue();
        }

        private final ImageView getImgClose() {
            return (ImageView) this.imgClose.getValue();
        }

        private final FrameLayout getLayoutSearch() {
            return (FrameLayout) this.layoutSearch.getValue();
        }

        private final FrameLayout getLayoutSearchResult() {
            return (FrameLayout) this.layoutSearchResult.getValue();
        }

        private final RFrameLayout getLayoutTitle() {
            return (RFrameLayout) this.layoutTitle.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final LinearLayout getLlEmpty() {
            return (LinearLayout) this.llEmpty.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final LinearLayout getLlResult() {
            return (LinearLayout) this.llResult.getValue();
        }

        private final RecyclerView getRecyclerView() {
            return (RecyclerView) this.recyclerView.getValue();
        }

        private final ClassicsHeader getRefreshHeader() {
            return (ClassicsHeader) this.refreshHeader.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final SmartRefreshLayout getRefreshLayout() {
            return (SmartRefreshLayout) this.refreshLayout.getValue();
        }

        private final TextView getTv1() {
            return (TextView) this.tv1.getValue();
        }

        private final TextView getTv2() {
            return (TextView) this.tv2.getValue();
        }

        private final TextView getTv3() {
            return (TextView) this.tv3.getValue();
        }

        private final TextView getTv4() {
            return (TextView) this.tv4.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TextView getTvNumber() {
            return (TextView) this.tvNumber.getValue();
        }

        private final TextView getTvTitle() {
            return (TextView) this.tvTitle.getValue();
        }

        private final View getView2() {
            return (View) this.view2.getValue();
        }

        private final View getView3() {
            return (View) this.view3.getValue();
        }

        private final View getView4() {
            return (View) this.view4.getValue();
        }

        private final View getViewLine3() {
            return (View) this.viewLine3.getValue();
        }

        private final void initTheme() throws SecurityException {
            RTextViewHelper helper;
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            if (colorMode == 0) {
                TextView tvTitle = getTvTitle();
                if (tvTitle != null) {
                    tvTitle.setTextColor(getColor(R.color.color_303030));
                    return;
                }
                return;
            }
            if (colorMode == 1) {
                TextView tvTitle2 = getTvTitle();
                if (tvTitle2 != null) {
                    tvTitle2.setTextColor(getColor(R.color.color_303030));
                }
                ImageView imgClose = getImgClose();
                if (imgClose != null) {
                    imgClose.setImageResource(R.mipmap.ic_close_color_mode_1);
                }
                RFrameLayout layoutTitle = getLayoutTitle();
                RBaseHelper helper2 = layoutTitle != null ? layoutTitle.getHelper() : null;
                if (helper2 != null) {
                    helper2.setBackgroundColorNormal(getColor(R.color.color_F5EBCE));
                }
                FrameLayout layoutSearchResult = getLayoutSearchResult();
                if (layoutSearchResult != null) {
                    layoutSearchResult.setBackgroundColor(getColor(R.color.color_F5EBCE));
                }
                FrameLayout layoutSearch = getLayoutSearch();
                if (layoutSearch != null) {
                    layoutSearch.setBackgroundColor(getColor(R.color.color_F5EBCE));
                }
                View viewLine3 = getViewLine3();
                if (viewLine3 != null) {
                    viewLine3.setBackgroundColor(getColor(R.color.color_EDE2C3));
                }
                REditText etInput = getEtInput();
                helper = etInput != null ? etInput.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
                }
                ImageView imgClean = getImgClean();
                if (imgClean != null) {
                    imgClean.setImageResource(R.drawable.icon_close_yellow_theme_svg);
                    return;
                }
                return;
            }
            if (colorMode != 2) {
                return;
            }
            TextView tvTitle3 = getTvTitle();
            if (tvTitle3 != null) {
                tvTitle3.setTextColor(getColor(R.color.color_7380a9));
            }
            TextView tvNumber = getTvNumber();
            if (tvNumber != null) {
                tvNumber.setTextColor(getColor(R.color.color_575F79));
            }
            RCheckBox cbSort = getCbSort();
            if (cbSort != null) {
                cbSort.setTextColor(getColor(R.color.color_575F79));
            }
            REditText etInput2 = getEtInput();
            if (etInput2 != null) {
                etInput2.setTextColor(getColor(R.color.color_7380a9));
            }
            REditText etInput3 = getEtInput();
            if (etInput3 != null) {
                etInput3.setHintTextColor(getColor(R.color.color_575F79));
            }
            TextView tv1 = getTv1();
            if (tv1 != null) {
                tv1.setTextColor(getColor(R.color.color_7380a9));
            }
            TextView tv2 = getTv2();
            if (tv2 != null) {
                tv2.setTextColor(getColor(R.color.color_7380a9));
            }
            TextView tv3 = getTv3();
            if (tv3 != null) {
                tv3.setTextColor(getColor(R.color.color_7380a9));
            }
            TextView tv4 = getTv4();
            if (tv4 != null) {
                tv4.setTextColor(getColor(R.color.color_7380a9));
            }
            View view2 = getView2();
            if (view2 != null) {
                view2.setBackground(getDrawable(R.drawable.circle_dot_7380a9_bg));
            }
            View view3 = getView3();
            if (view3 != null) {
                view3.setBackground(getDrawable(R.drawable.circle_dot_7380a9_bg));
            }
            View view4 = getView4();
            if (view4 != null) {
                view4.setBackground(getDrawable(R.drawable.circle_dot_7380a9_bg));
            }
            ImageView imgClose2 = getImgClose();
            if (imgClose2 != null) {
                imgClose2.setImageResource(R.drawable.icon_close_night_svg);
            }
            RFrameLayout layoutTitle2 = getLayoutTitle();
            RBaseHelper helper3 = layoutTitle2 != null ? layoutTitle2.getHelper() : null;
            if (helper3 != null) {
                helper3.setBackgroundColorNormal(getColor(R.color.color_121622));
            }
            FrameLayout layoutSearchResult2 = getLayoutSearchResult();
            if (layoutSearchResult2 != null) {
                layoutSearchResult2.setBackgroundColor(getColor(R.color.color_121622));
            }
            FrameLayout layoutSearch2 = getLayoutSearch();
            if (layoutSearch2 != null) {
                layoutSearch2.setBackgroundColor(getColor(R.color.color_121622));
            }
            View viewLine32 = getViewLine3();
            if (viewLine32 != null) {
                viewLine32.setBackgroundColor(getColor(R.color.color_theme_blue_line_color));
            }
            REditText etInput4 = getEtInput();
            RTextViewHelper helper4 = etInput4 != null ? etInput4.getHelper() : null;
            if (helper4 != null) {
                helper4.setBackgroundColorNormal(getColor(R.color.color_171C2D));
            }
            REditText etInput5 = getEtInput();
            helper = etInput5 != null ? etInput5.getHelper() : null;
            if (helper != null) {
                helper.setIconNormal(getDrawable(R.drawable.icon_search_blue_theme_svg));
            }
            ImageView imgClean2 = getImgClean();
            if (imgClean2 != null) {
                imgClean2.setImageResource(R.drawable.icon_close_night_svg);
            }
        }

        private final void initView() {
            setHeight((int) (ScreenUtil.getScreenHeight(getActivity()) * 0.92d));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView != null) {
                recyclerView.setLayoutManager(linearLayoutManager);
            }
            RecyclerView recyclerView2 = getRecyclerView();
            if (recyclerView2 != null) {
                recyclerView2.setAdapter(getAdapter());
            }
            REditText etInput = getEtInput();
            if (etInput != null) {
                etInput.requestFocus();
            }
            REditText etInput2 = getEtInput();
            if (etInput2 != null) {
                etInput2.postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.u
                    @Override // java.lang.Runnable
                    public final void run() {
                        ChapterSearchDialog.Builder.initView$lambda$2(this.f26391c);
                    }
                }, 100L);
            }
            REditText etInput3 = getEtInput();
            if (etInput3 != null) {
                etInput3.addTextChangedListener(new TextWatcher() { // from class: com.ykb.ebook.dialog.ChapterSearchDialog$Builder$initView$$inlined$addTextChangedListener$default$1
                    @Override // android.text.TextWatcher
                    public void afterTextChanged(@Nullable Editable s2) {
                        String string;
                        if (s2 == null || (string = s2.toString()) == null) {
                            string = "";
                        }
                        if (string.length() == 0) {
                            ImageView imgClean = this.this$0.getImgClean();
                            if (imgClean != null) {
                                ViewExtensionsKt.gone(imgClean);
                                return;
                            }
                            return;
                        }
                        ImageView imgClean2 = this.this$0.getImgClean();
                        if (imgClean2 != null) {
                            ViewExtensionsKt.visible(imgClean2);
                        }
                    }

                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
                    }
                });
            }
            REditText etInput4 = getEtInput();
            if (etInput4 != null) {
                etInput4.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.ykb.ebook.dialog.v
                    @Override // android.widget.TextView.OnEditorActionListener
                    public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                        return ChapterSearchDialog.Builder.initView$lambda$4(this.f26393c, textView, i2, keyEvent);
                    }
                });
            }
            SmartRefreshLayout refreshLayout = getRefreshLayout();
            if (refreshLayout != null) {
                refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ykb.ebook.dialog.w
                    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
                    public final void onRefresh(RefreshLayout refreshLayout2) {
                        ChapterSearchDialog.Builder.initView$lambda$5(this.f26396c, refreshLayout2);
                    }
                });
            }
            SmartRefreshLayout refreshLayout2 = getRefreshLayout();
            if (refreshLayout2 != null) {
                refreshLayout2.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ykb.ebook.dialog.x
                    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
                    public final void onLoadMore(RefreshLayout refreshLayout3) {
                        ChapterSearchDialog.Builder.initView$lambda$6(this.f26401c, refreshLayout3);
                    }
                });
            }
            RCheckBox cbSort = getCbSort();
            if (cbSort != null) {
                cbSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.dialog.y
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                        ChapterSearchDialog.Builder.initView$lambda$7(this.f26408c, compoundButton, z2);
                    }
                });
            }
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            SmartRefreshLayout refreshLayout3 = getRefreshLayout();
            Intrinsics.checkNotNull(refreshLayout3);
            ClassicsHeader refreshHeader = getRefreshHeader();
            Intrinsics.checkNotNull(refreshHeader);
            ViewUtilKt.setRefreshTileView(colorMode, refreshLayout3, refreshHeader, getContext());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void initView$lambda$2(Builder this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            KeyboardUtils.showSoftInput(this$0.getEtInput());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean initView$lambda$4(Builder this$0, TextView textView, int i2, KeyEvent keyEvent) {
            String string;
            Editable text;
            String string2;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (i2 != 3) {
                return false;
            }
            REditText etInput = this$0.getEtInput();
            if (etInput == null || (text = etInput.getText()) == null || (string2 = text.toString()) == null || (string = StringsKt__StringsKt.trim((CharSequence) string2).toString()) == null) {
                string = "";
            }
            this$0.input = string;
            if (string.length() == 0) {
                ToastUtilsKt.toastOnUi$default(this$0.getContext(), "请输入关键字！", 0, 2, (Object) null);
            } else {
                KeyboardUtils.hideSoftInput(this$0.getEtInput());
                this$0.page = 1;
                this$0.loadData();
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void initView$lambda$5(Builder this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.page = 1;
            this$0.loadData();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void initView$lambda$6(Builder this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.page++;
            this$0.loadData();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void initView$lambda$7(Builder this$0, CompoundButton compoundButton, boolean z2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.isReverse = z2 ? 1 : 0;
            compoundButton.setText(z2 ? "正序" : "倒序");
            this$0.page = 1;
            this$0.loadData();
        }

        private final void loadData() {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new ChapterSearchDialog$Builder$loadData$1(MapsKt__MapsKt.hashMapOf(new Pair(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page)), new Pair("page_size", String.valueOf(this.pageSize)), new Pair("is_reverse", String.valueOf(this.isReverse)), new Pair(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, this.input), new Pair("book_id", this.bookId), new Pair("user_id", "583383"), new Pair("app_id", "40")), null), 15, null), null, new ChapterSearchDialog$Builder$loadData$2(this, null), 1, null), null, new ChapterSearchDialog$Builder$loadData$3(this, null), 1, null);
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            REditText etInput;
            Intrinsics.checkNotNullParameter(view, "view");
            if (Intrinsics.areEqual(view, getImgClose())) {
                dismiss();
            } else {
                if (!Intrinsics.areEqual(view, getImgClean()) || (etInput = getEtInput()) == null) {
                    return;
                }
                etInput.setText("");
            }
        }

        @NotNull
        public final Builder setBookId(@NotNull String bookId) {
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            this.bookId = bookId;
            return this;
        }

        @NotNull
        public final Builder setOnItemClick(@NotNull Function4<? super Integer, ? super TextSearchResult, ? super String, ? super String, Unit> onItemClick) {
            Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
            this.onItemClick = onItemClick;
            return this;
        }
    }
}
