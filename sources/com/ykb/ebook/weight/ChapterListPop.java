package com.ykb.ebook.weight;

import android.annotation.SuppressLint;
import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lb.fast_scroller_and_recycler_view_fixes.GridLayoutManagerEx;
import com.lb.fast_scroller_and_recycler_view_fixes_library.BottomOffsetDecoration;
import com.lxj.xpopup.core.DrawerPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.ruffian.library.widget.RCheckBox;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RCheckHelper;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.ChapterListAdapter;
import com.ykb.ebook.common.ReadConfig;
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

@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u00102\u001a\u00020\u0019H\u0002J\u0012\u00103\u001a\u00020\u00182\b\b\u0001\u00104\u001a\u00020\u0018H\u0003J\b\u00105\u001a\u00020\u0018H\u0014J\u000e\u00106\u001a\u00020\u00002\u0006\u00107\u001a\u00020$J\u0012\u00108\u001a\u00020\u001a2\b\u00109\u001a\u0004\u0018\u00010:H\u0016J\b\u0010;\u001a\u00020\u001aH\u0014J\b\u0010<\u001a\u00020\u001aH\u0002J\u001c\u0010=\u001a\u00020\u001a2\b\b\u0001\u00104\u001a\u00020\u00182\b\u0010>\u001a\u0004\u0018\u00010?H\u0002J\u0010\u0010@\u001a\u00020\u00002\u0006\u0010A\u001a\u00020BH\u0007J \u0010C\u001a\u00020\u00002\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a0\u0017J\u0014\u0010D\u001a\u00020\u00002\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\u001cJ\u001a\u0010E\u001a\u00020\u00002\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u001a0#R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001d\u0010\f\u001a\u0004\u0018\u00010\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0011\u001a\u0004\u0018\u00010\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u000b\u001a\u0004\b\u0013\u0010\u0014R\"\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u001d\u001a\u0004\u0018\u00010\u001e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u000b\u001a\u0004\b\u001f\u0010 R\u001c\u0010\"\u001a\u0010\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u001a\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010%\u001a\u0004\u0018\u00010&8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b)\u0010\u000b\u001a\u0004\b'\u0010(R\u001d\u0010*\u001a\u0004\u0018\u00010&8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\u000b\u001a\u0004\b+\u0010(R\u001d\u0010-\u001a\u0004\u0018\u00010.8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b1\u0010\u000b\u001a\u0004\b/\u00100¨\u0006F"}, d2 = {"Lcom/ykb/ebook/weight/ChapterListPop;", "Lcom/lxj/xpopup/core/DrawerPopupView;", "Landroid/view/View$OnClickListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "adapter", "Lcom/ykb/ebook/adapter/ChapterListAdapter;", "getAdapter", "()Lcom/ykb/ebook/adapter/ChapterListAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "cbSort", "Lcom/ruffian/library/widget/RCheckBox;", "getCbSort", "()Lcom/ruffian/library/widget/RCheckBox;", "cbSort$delegate", "imgCover", "Landroid/widget/ImageView;", "getImgCover", "()Landroid/widget/ImageView;", "imgCover$delegate", "onItemClick", "Lkotlin/Function2;", "", "Lcom/ykb/ebook/model/Chapter;", "", "onSearchClick", "Lkotlin/Function0;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "recyclerView$delegate", "sortCallback", "Lkotlin/Function1;", "", "tvAuthor", "Landroid/widget/TextView;", "getTvAuthor", "()Landroid/widget/TextView;", "tvAuthor$delegate", "tvBookName", "getTvBookName", "tvBookName$delegate", "tvSearch", "Lcom/ruffian/library/widget/RTextView;", "getTvSearch", "()Lcom/ruffian/library/widget/RTextView;", "tvSearch$delegate", "getChapterFY", "getColor", "id", "getImplLayoutId", "initSort", "sort", "onClick", "v", "Landroid/view/View;", "onCreate", "refreshUiMode", "setBackground", "drawable", "Landroid/graphics/drawable/Drawable;", PLVRxEncryptDataFunction.SET_DATA_METHOD, "bookInfo", "Lcom/ykb/ebook/model/BookInfo;", "setOnItemClick", "setSearchClick", "setSortCallback", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ChapterListPop extends DrawerPopupView implements View.OnClickListener {

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
    public ChapterListPop(@NotNull final Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.imgCover = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.weight.ChapterListPop$imgCover$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ImageView invoke() {
                return (ImageView) this.this$0.findViewById(R.id.img_cover);
            }
        });
        this.tvBookName = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.weight.ChapterListPop$tvBookName$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final TextView invoke() {
                return (TextView) this.this$0.findViewById(R.id.tv_book_name);
            }
        });
        this.tvAuthor = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.weight.ChapterListPop$tvAuthor$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final TextView invoke() {
                return (TextView) this.this$0.findViewById(R.id.tv_author);
            }
        });
        this.tvSearch = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.weight.ChapterListPop$tvSearch$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final RTextView invoke() {
                return (RTextView) this.this$0.findViewById(R.id.tv_search);
            }
        });
        this.cbSort = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.weight.ChapterListPop$cbSort$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final RCheckBox invoke() {
                return (RCheckBox) this.this$0.findViewById(R.id.cb_sort);
            }
        });
        this.recyclerView = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.ykb.ebook.weight.ChapterListPop$recyclerView$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final RecyclerView invoke() {
                return (RecyclerView) this.this$0.findViewById(R.id.rv_chapter_list);
            }
        });
        this.adapter = LazyKt__LazyJVMKt.lazy(new Function0<ChapterListAdapter>() { // from class: com.ykb.ebook.weight.ChapterListPop$adapter$2
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

    @ColorInt
    private final int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getContext(), id);
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

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(ChapterListPop this$0, CompoundButton compoundButton, boolean z2) {
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

    private final void refreshUiMode() {
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int colorMode = readConfig.getColorMode();
        ColorDrawable colorDrawable = colorMode != 0 ? colorMode != 1 ? colorMode != 2 ? new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.white)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_121622)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_F5EBCE)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.white));
        int colorMode2 = readConfig.getColorMode();
        ColorDrawable colorDrawable2 = colorMode2 != 0 ? colorMode2 != 1 ? new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_171C2D)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_EDE2C3)) : new ColorDrawable(AppCtxKt.getAppCtx().getColor(R.color.color_f7f8fa));
        int colorMode3 = readConfig.getColorMode();
        ImageView imageView = (ImageView) findViewById(R.id.iv_cover_slider);
        if (imageView != null) {
            imageView.setImageResource(colorMode3 != 1 ? colorMode3 != 2 ? R.mipmap.ic_menu_book_cover_desc : R.mipmap.ic_menu_book_cover_desc_blue : R.mipmap.ic_menu_book_cover_desc_yellow);
        }
        RTextView tvSearch = getTvSearch();
        RTextViewHelper helper = tvSearch != null ? tvSearch.getHelper() : null;
        if (helper != null) {
            helper.setIconNormalLeft(ContextCompat.getDrawable(getContext(), readConfig.getColorMode() == 2 ? R.mipmap.ic_chapter_search_blue : R.drawable.icon_chapter_search));
        }
        setBackground(R.id.ll_content, colorDrawable);
        setBackground(R.id.ll_chapter_list, colorDrawable);
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
            RCheckBox cbSort = getCbSort();
            if (cbSort != null) {
                cbSort.setTextColor(getColor(R.color.color_575F79));
            }
            TextView tvBookName = getTvBookName();
            if (tvBookName != null) {
                tvBookName.setTextColor(getColor(R.color.color_7380a9));
            }
            TextView tvAuthor = getTvAuthor();
            if (tvAuthor != null) {
                tvAuthor.setTextColor(getColor(R.color.color_575F79));
            }
        }
    }

    private final void setBackground(@IdRes int id, Drawable drawable) {
        View viewFindViewById = findViewById(id);
        if (viewFindViewById == null) {
            return;
        }
        viewFindViewById.setBackground(drawable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setData$lambda$1(ChapterListPop this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerView recyclerView = this$0.getRecyclerView();
        if (recyclerView != null) {
            recyclerView.scrollToPosition(ReadBook.INSTANCE.getDurChapterIndex() + 1);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_chapter_list;
    }

    @NotNull
    public final ChapterListPop initSort(boolean sort) {
        RCheckBox cbSort = getCbSort();
        if (cbSort != null) {
            cbSort.setChecked(sort);
        }
        return this;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@Nullable View v2) {
        Function0<Unit> function0 = this.onSearchClick;
        if (function0 != null) {
            function0.invoke();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        RTextView tvSearch = getTvSearch();
        if (tvSearch != null) {
            tvSearch.setOnClickListener(this);
        }
        getAdapter().setOnItemClick(new Function2<Integer, Chapter, Unit>() { // from class: com.ykb.ebook.weight.ChapterListPop.onCreate.1
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
                    ChapterListPop.this.dismiss();
                    ReadConfig readConfig = ReadConfig.INSTANCE;
                    readConfig.setShowFeiYe(true);
                    readConfig.setLastPage(false);
                    readConfig.setFirstPage(true);
                    ReadBook.openChapter$default(ReadBook.INSTANCE, 0, 0, null, 6, null);
                    return;
                }
                ChapterListPop.this.dismiss();
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
                Function2 function2 = ChapterListPop.this.onItemClick;
                if (function2 != null) {
                    function2.invoke(Integer.valueOf(chapter.getIndex()), chapter);
                }
            }
        });
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        GridLayoutManagerEx gridLayoutManagerEx = new GridLayoutManagerEx(context, 1, 1, false);
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.setLayoutManager(gridLayoutManagerEx);
        }
        RecyclerView recyclerView2 = getRecyclerView();
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(getAdapter());
        }
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int colorMode = readConfig.getColorMode();
        Drawable drawable = getContext().getDrawable(R.drawable.thumb_drawable);
        Intrinsics.checkNotNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.StateListDrawable");
        StateListDrawable stateListDrawable = (StateListDrawable) drawable;
        if (colorMode == 2) {
            Drawable drawable2 = getContext().getDrawable(R.drawable.thumb_drawable_night);
            Intrinsics.checkNotNull(drawable2, "null cannot be cast to non-null type android.graphics.drawable.StateListDrawable");
            stateListDrawable = (StateListDrawable) drawable2;
        }
        StateListDrawable stateListDrawable2 = stateListDrawable;
        Drawable drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.line_drawable);
        new FastScrollerEx(getRecyclerView(), stateListDrawable2, drawable3, stateListDrawable2, drawable3, getResources().getDimensionPixelSize(R.dimen.fastScrollThickness), getResources().getDimensionPixelSize(R.dimen.fastScrollMinimumRange), getResources().getDimensionPixelSize(R.dimen.fastScrollMargin), true, getResources().getDimensionPixelSize(R.dimen.fastScrollMinThumbSize));
        RecyclerView recyclerView3 = getRecyclerView();
        if (recyclerView3 != null) {
            recyclerView3.addItemDecoration(new BottomOffsetDecoration(getResources().getDimensionPixelSize(R.dimen.bottom_list_padding), BottomOffsetDecoration.LayoutManagerType.GRID_LAYOUT_MANAGER));
        }
        RCheckBox cbSort = getCbSort();
        if (cbSort != null) {
            cbSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.weight.b
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    ChapterListPop.onCreate$lambda$0(this.f26458c, compoundButton, z2);
                }
            });
        }
        refreshUiMode();
        RCheckBox cbSort2 = getCbSort();
        RCheckHelper helper = cbSort2 != null ? cbSort2.getHelper() : null;
        if (helper != null) {
            helper.setIconCheckedLeft(ContextCompat.getDrawable(getContext(), readConfig.getColorMode() == 2 ? R.mipmap.icon_positive_sequence_blue : R.drawable.icon_positive_sequence));
        }
        RCheckBox cbSort3 = getCbSort();
        RCheckHelper helper2 = cbSort3 != null ? cbSort3.getHelper() : null;
        if (helper2 == null) {
            return;
        }
        helper2.setIconNormalLeft(ContextCompat.getDrawable(getContext(), readConfig.getColorMode() == 2 ? R.mipmap.icon_reverse_order_blue : R.drawable.icon_reverse_order));
    }

    @SuppressLint({"SetTextI18n"})
    @NotNull
    public final ChapterListPop setData(@NotNull BookInfo bookInfo) {
        Intrinsics.checkNotNullParameter(bookInfo, "bookInfo");
        ArrayList chapterList = (ArrayList) new Gson().fromJson(new Gson().toJson(bookInfo.getChapterList()), new TypeToken<ArrayList<Chapter>>() { // from class: com.ykb.ebook.weight.ChapterListPop$setData$type$1
        }.getType());
        Chapter chapterFY = getChapterFY();
        ArrayList arrayList = new ArrayList();
        arrayList.add(chapterFY);
        Intrinsics.checkNotNullExpressionValue(chapterList, "chapterList");
        arrayList.addAll(chapterList);
        getAdapter().setData(arrayList);
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.postDelayed(new Runnable() { // from class: com.ykb.ebook.weight.c
                @Override // java.lang.Runnable
                public final void run() {
                    ChapterListPop.setData$lambda$1(this.f26465c);
                }
            }, 300L);
        }
        ImageView imgCover = getImgCover();
        if (imgCover != null) {
            ImageLoader imageLoader = ImageLoader.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            imageLoader.load(context, bookInfo.getThumbnail()).optionalCenterCrop().into(imgCover);
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
    public final ChapterListPop setOnItemClick(@NotNull Function2<? super Integer, ? super Chapter, Unit> onItemClick) {
        Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
        this.onItemClick = onItemClick;
        return this;
    }

    @NotNull
    public final ChapterListPop setSearchClick(@NotNull Function0<Unit> onSearchClick) {
        Intrinsics.checkNotNullParameter(onSearchClick, "onSearchClick");
        this.onSearchClick = onSearchClick;
        return this;
    }

    @NotNull
    public final ChapterListPop setSortCallback(@NotNull Function1<? super Boolean, Unit> sortCallback) {
        Intrinsics.checkNotNullParameter(sortCallback, "sortCallback");
        this.sortCallback = sortCallback;
        return this;
    }
}
