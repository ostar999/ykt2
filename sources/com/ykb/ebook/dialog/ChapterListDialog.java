package com.ykb.ebook.dialog;

import android.annotation.SuppressLint;
import android.content.AppCtxKt;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lb.fast_scroller_and_recycler_view_fixes.GridLayoutManagerEx;
import com.lb.fast_scroller_and_recycler_view_fixes_library.BottomOffsetDecoration;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.ruffian.library.widget.RCheckBox;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RCheckHelper;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.ChapterListAdapter;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.ChapterListDialog;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.scrollUtil.FastScrollerEx;
import com.ykb.ebook.util.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/ChapterListDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ChapterListDialog {

    @Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u00101\u001a\u00020\u0018H\u0002J\u000e\u00102\u001a\u00020\u00002\u0006\u00103\u001a\u00020#J\u0010\u00104\u001a\u00020\u00192\u0006\u00105\u001a\u000206H\u0016J\b\u00107\u001a\u00020\u0019H\u0002J\u0010\u00108\u001a\u00020\u00002\u0006\u00109\u001a\u00020:H\u0007J \u0010;\u001a\u00020\u00002\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0016J\u0014\u0010<\u001a\u00020\u00002\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u001bJ\u001a\u0010=\u001a\u00020\u00002\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00190\"R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\u000b\u001a\u0004\u0018\u00010\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0010\u001a\u0004\u0018\u00010\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\n\u001a\u0004\b\u0012\u0010\u0013R\"\u0010\u0015\u001a\u0016\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u001c\u001a\u0004\u0018\u00010\u001d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\n\u001a\u0004\b\u001e\u0010\u001fR\u001c\u0010!\u001a\u0010\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u0019\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010$\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\n\u001a\u0004\b&\u0010'R\u001d\u0010)\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\n\u001a\u0004\b*\u0010'R\u001d\u0010,\u001a\u0004\u0018\u00010-8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\n\u001a\u0004\b.\u0010/¨\u0006>"}, d2 = {"Lcom/ykb/ebook/dialog/ChapterListDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "adapter", "Lcom/ykb/ebook/adapter/ChapterListAdapter;", "getAdapter", "()Lcom/ykb/ebook/adapter/ChapterListAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "cbSort", "Lcom/ruffian/library/widget/RCheckBox;", "getCbSort", "()Lcom/ruffian/library/widget/RCheckBox;", "cbSort$delegate", "imgCover", "Landroid/widget/ImageView;", "getImgCover", "()Landroid/widget/ImageView;", "imgCover$delegate", "onItemClick", "Lkotlin/Function2;", "", "Lcom/ykb/ebook/model/Chapter;", "", "onSearchClick", "Lkotlin/Function0;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "recyclerView$delegate", "sortCallback", "Lkotlin/Function1;", "", "tvAuthor", "Landroid/widget/TextView;", "getTvAuthor", "()Landroid/widget/TextView;", "tvAuthor$delegate", "tvBookName", "getTvBookName", "tvBookName$delegate", "tvSearch", "Lcom/ruffian/library/widget/RTextView;", "getTvSearch", "()Lcom/ruffian/library/widget/RTextView;", "tvSearch$delegate", "getChapterFY", "initSort", "sort", "onClick", "view", "Landroid/view/View;", "refreshUiMode", PLVRxEncryptDataFunction.SET_DATA_METHOD, "bookInfo", "Lcom/ykb/ebook/model/BookInfo;", "setOnItemClick", "setSearchClick", "setSortCallback", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: adapter$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy adapter;

        /* renamed from: cbSort$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbSort;

        /* renamed from: imgCover$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgCover;

        @Nullable
        private Function2<? super Integer, ? super Chapter, Unit> onItemClick;

        @Nullable
        private Function0<Unit> onSearchClick;

        /* renamed from: recyclerView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy recyclerView;

        @Nullable
        private Function1<? super Boolean, Unit> sortCallback;

        /* renamed from: tvAuthor$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvAuthor;

        /* renamed from: tvBookName$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvBookName;

        /* renamed from: tvSearch$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvSearch;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull final Context context) throws Resources.NotFoundException {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.imgCover = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.ChapterListDialog$Builder$imgCover$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_cover);
                }
            });
            this.tvBookName = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ChapterListDialog$Builder$tvBookName$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_book_name);
                }
            });
            this.tvAuthor = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ChapterListDialog$Builder$tvAuthor$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_author);
                }
            });
            this.tvSearch = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.ChapterListDialog$Builder$tvSearch$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_search);
                }
            });
            this.cbSort = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.ChapterListDialog$Builder$cbSort$2
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
            this.recyclerView = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.ykb.ebook.dialog.ChapterListDialog$Builder$recyclerView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RecyclerView invoke() {
                    return (RecyclerView) this.this$0.findViewById(R.id.rv_chapter_list);
                }
            });
            this.adapter = LazyKt__LazyJVMKt.lazy(new Function0<ChapterListAdapter>() { // from class: com.ykb.ebook.dialog.ChapterListDialog$Builder$adapter$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final ChapterListAdapter invoke() {
                    return new ChapterListAdapter(context);
                }
            });
            setContentView(R.layout.dialog_chapter_list);
            setGravity(GravityCompat.START);
            setAnimStyle(AnimAction.INSTANCE.getANIM_LEFT());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setOnClickListener(getTvSearch());
            getAdapter().setOnItemClick(new Function2<Integer, Chapter, Unit>() { // from class: com.ykb.ebook.dialog.ChapterListDialog.Builder.1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Integer num, Chapter chapter) {
                    invoke(num.intValue(), chapter);
                    return Unit.INSTANCE;
                }

                public final void invoke(int i2, @NotNull Chapter chapter) {
                    Intrinsics.checkNotNullParameter(chapter, "chapter");
                    if (Intrinsics.areEqual(chapter.getSort(), "-1")) {
                        Builder.this.dismiss();
                        ReadConfig readConfig = ReadConfig.INSTANCE;
                        readConfig.setShowFeiYe(true);
                        readConfig.setLastPage(false);
                        readConfig.setFirstPage(true);
                        ReadBook.openChapter$default(ReadBook.INSTANCE, 0, 0, null, 6, null);
                        return;
                    }
                    Builder.this.dismiss();
                    if (Intrinsics.areEqual("-1", chapter.getId())) {
                        ReadConfig readConfig2 = ReadConfig.INSTANCE;
                        readConfig2.setShowFeiYe(true);
                        readConfig2.setLastPage(false);
                        readConfig2.setFirstPage(true);
                        ReadBook.openChapter$default(ReadBook.INSTANCE, 0, 0, null, 6, null);
                        return;
                    }
                    ReadConfig readConfig3 = ReadConfig.INSTANCE;
                    readConfig3.setLastPage(false);
                    readConfig3.setFirstPage(false);
                    Function2 function2 = Builder.this.onItemClick;
                    if (function2 != null) {
                        function2.invoke(Integer.valueOf(chapter.getIndex()), chapter);
                    }
                }
            });
            GridLayoutManagerEx gridLayoutManagerEx = new GridLayoutManagerEx(context, 1, 1, false);
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView != null) {
                recyclerView.setLayoutManager(gridLayoutManagerEx);
            }
            RecyclerView recyclerView2 = getRecyclerView();
            if (recyclerView2 != null) {
                recyclerView2.setAdapter(getAdapter());
            }
            Drawable drawable = getResources().getDrawable(R.drawable.thumb_drawable);
            Intrinsics.checkNotNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.StateListDrawable");
            StateListDrawable stateListDrawable = (StateListDrawable) drawable;
            Drawable drawable2 = ContextCompat.getDrawable(context, R.drawable.line_drawable);
            new FastScrollerEx(getRecyclerView(), stateListDrawable, drawable2, stateListDrawable, drawable2, getResources().getDimensionPixelSize(R.dimen.fastScrollThickness), getResources().getDimensionPixelSize(R.dimen.fastScrollMinimumRange), getResources().getDimensionPixelSize(R.dimen.fastScrollMargin), true, getResources().getDimensionPixelSize(R.dimen.fastScrollMinThumbSize));
            RecyclerView recyclerView3 = getRecyclerView();
            if (recyclerView3 != null) {
                recyclerView3.addItemDecoration(new BottomOffsetDecoration(getResources().getDimensionPixelSize(R.dimen.bottom_list_padding), BottomOffsetDecoration.LayoutManagerType.GRID_LAYOUT_MANAGER));
            }
            RCheckBox cbSort = getCbSort();
            if (cbSort != null) {
                cbSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.dialog.t
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                        ChapterListDialog.Builder._init_$lambda$0(this.f26388c, compoundButton, z2);
                    }
                });
            }
            refreshUiMode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(Builder this$0, CompoundButton compoundButton, boolean z2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Function1<? super Boolean, Unit> function1 = this$0.sortCallback;
            if (function1 != null) {
                function1.invoke(Boolean.valueOf(z2));
            }
            if (z2) {
                compoundButton.setText("正序");
                List<Chapter> listSubList = this$0.getAdapter().getData().subList(1, this$0.getAdapter().getData().size());
                Chapter chapterFY = this$0.getChapterFY();
                ArrayList arrayList = new ArrayList();
                arrayList.add(chapterFY);
                arrayList.addAll(CollectionsKt___CollectionsKt.reversed(listSubList));
                this$0.getAdapter().setData(arrayList);
            } else {
                compoundButton.setText("倒序");
                List<Chapter> listSubList2 = this$0.getAdapter().getData().subList(1, this$0.getAdapter().getData().size());
                Chapter chapterFY2 = this$0.getChapterFY();
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(chapterFY2);
                arrayList2.addAll(CollectionsKt___CollectionsKt.reversed(listSubList2));
                this$0.getAdapter().setData(arrayList2);
            }
            this$0.refreshUiMode();
        }

        private final ChapterListAdapter getAdapter() {
            return (ChapterListAdapter) this.adapter.getValue();
        }

        private final RCheckBox getCbSort() {
            return (RCheckBox) this.cbSort.getValue();
        }

        private final Chapter getChapterFY() {
            return new Chapter("书籍封面页", "-1", "-1", "书籍封面页", "", true, 0, null, 192, null);
        }

        private final ImageView getImgCover() {
            return (ImageView) this.imgCover.getValue();
        }

        private final RecyclerView getRecyclerView() {
            return (RecyclerView) this.recyclerView.getValue();
        }

        private final TextView getTvAuthor() {
            return (TextView) this.tvAuthor.getValue();
        }

        private final TextView getTvBookName() {
            return (TextView) this.tvBookName.getValue();
        }

        private final RTextView getTvSearch() {
            return (RTextView) this.tvSearch.getValue();
        }

        private final void refreshUiMode() {
            ReadConfig readConfig = ReadConfig.INSTANCE;
            int colorMode = readConfig.getColorMode();
            ColorDrawable colorDrawable = colorMode != 0 ? colorMode != 1 ? colorMode != 2 ? new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.white)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_121622)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_F5EBCE)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.white));
            int colorMode2 = readConfig.getColorMode();
            ColorDrawable colorDrawable2 = colorMode2 != 0 ? colorMode2 != 1 ? colorMode2 != 2 ? new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_f7f8fa)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_171C2D)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_EDE2C3)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_f7f8fa));
            int colorMode3 = readConfig.getColorMode();
            ImageView imageView = (ImageView) findViewById(R.id.iv_cover_slider);
            if (imageView != null) {
                imageView.setImageResource(colorMode3 != 0 ? colorMode3 != 1 ? colorMode3 != 2 ? R.mipmap.ic_menu_book_cover_desc : R.mipmap.ic_menu_book_cover_desc_blue : R.mipmap.ic_menu_book_cover_desc_yellow : R.mipmap.ic_menu_book_cover_desc);
            }
            RTextView tvSearch = getTvSearch();
            RTextViewHelper helper = tvSearch != null ? tvSearch.getHelper() : null;
            if (helper != null) {
                helper.setIconNormalLeft(ContextCompat.getDrawable(getContext(), readConfig.getColorMode() == 2 ? R.mipmap.ic_chapter_search_blue : R.drawable.icon_chapter_search));
            }
            RCheckBox cbSort = getCbSort();
            RCheckHelper helper2 = cbSort != null ? cbSort.getHelper() : null;
            if (helper2 != null) {
                helper2.setIconCheckedLeft(ContextCompat.getDrawable(getContext(), readConfig.getColorMode() == 2 ? R.mipmap.icon_positive_sequence_blue : R.drawable.icon_positive_sequence));
            }
            RCheckBox cbSort2 = getCbSort();
            RCheckHelper helper3 = cbSort2 != null ? cbSort2.getHelper() : null;
            if (helper3 != null) {
                helper3.setIconNormalLeft(ContextCompat.getDrawable(getContext(), readConfig.getColorMode() == 2 ? R.mipmap.icon_reverse_order_blue : R.drawable.icon_reverse_order));
            }
            setBackground(R.id.ll_content, colorDrawable);
            setBackground(R.id.rv_chapter_list, colorDrawable);
            setBackground(R.id.ll_book_cover, colorDrawable);
            setBackground(R.id.tv_footer, colorDrawable);
            setBackground(R.id.ll_book_info, colorDrawable2);
            if (readConfig.getColorMode() == 2) {
                TextView textView = (TextView) findViewById(R.id.tv_all_chapter);
                if (textView != null) {
                    textView.setTextColor(getColor(R.color.color_575F79));
                }
                TextView textView2 = (TextView) findViewById(R.id.tv_title);
                if (textView2 != null) {
                    textView2.setTextColor(getColor(R.color.color_7380a9));
                }
                RTextView tvSearch2 = getTvSearch();
                if (tvSearch2 != null) {
                    tvSearch2.setTextColor(getColor(R.color.color_575F79));
                }
                RCheckBox cbSort3 = getCbSort();
                if (cbSort3 != null) {
                    cbSort3.setTextColor(getColor(R.color.color_575F79));
                }
                TextView tvBookName = getTvBookName();
                if (tvBookName != null) {
                    tvBookName.setTextColor(getColor(R.color.color_575F79));
                }
                TextView tvAuthor = getTvAuthor();
                if (tvAuthor != null) {
                    tvAuthor.setTextColor(getColor(R.color.color_575F79));
                }
            }
        }

        @NotNull
        public final Builder initSort(boolean sort) {
            RCheckBox cbSort = getCbSort();
            if (cbSort != null) {
                cbSort.setChecked(sort);
            }
            return this;
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            Function0<Unit> function0 = this.onSearchClick;
            if (function0 != null) {
                function0.invoke();
            }
        }

        @SuppressLint({"SetTextI18n"})
        @NotNull
        public final Builder setData(@NotNull BookInfo bookInfo) {
            Intrinsics.checkNotNullParameter(bookInfo, "bookInfo");
            ArrayList chapterList = (ArrayList) new Gson().fromJson(new Gson().toJson(bookInfo.getChapterList()), new TypeToken<ArrayList<Chapter>>() { // from class: com.ykb.ebook.dialog.ChapterListDialog$Builder$setData$1$type$1
            }.getType());
            Chapter chapterFY = getChapterFY();
            ArrayList arrayList = new ArrayList();
            arrayList.add(chapterFY);
            Intrinsics.checkNotNullExpressionValue(chapterList, "chapterList");
            arrayList.addAll(chapterList);
            getAdapter().setData(arrayList);
            int durChapterIndex = ReadBook.INSTANCE.getDurChapterIndex();
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView != null) {
                recyclerView.scrollToPosition(durChapterIndex);
            }
            ImageView imgCover = getImgCover();
            if (imgCover != null) {
                ImageLoader.INSTANCE.load(getContext(), bookInfo.getThumbnail()).optionalCenterCrop().into(imgCover);
            }
            TextView tvBookName = getTvBookName();
            if (tvBookName != null) {
                tvBookName.setText(bookInfo.getTitle());
            }
            TextView tvAuthor = getTvAuthor();
            if (tvAuthor != null) {
                tvAuthor.setText(bookInfo.getAuthor());
            }
            RTextView rTextView = (RTextView) findViewById(R.id.tv_all_chapter);
            if (rTextView != null) {
                rTextView.setText("共 " + bookInfo.getChapterList().size() + " 章");
            }
            return this;
        }

        @NotNull
        public final Builder setOnItemClick(@NotNull Function2<? super Integer, ? super Chapter, Unit> onItemClick) {
            Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
            this.onItemClick = onItemClick;
            return this;
        }

        @NotNull
        public final Builder setSearchClick(@NotNull Function0<Unit> onSearchClick) {
            Intrinsics.checkNotNullParameter(onSearchClick, "onSearchClick");
            this.onSearchClick = onSearchClick;
            return this;
        }

        @NotNull
        public final Builder setSortCallback(@NotNull Function1<? super Boolean, Unit> sortCallback) {
            Intrinsics.checkNotNullParameter(sortCallback, "sortCallback");
            this.sortCallback = sortCallback;
            return this;
        }
    }
}
