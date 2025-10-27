package com.ykb.ebook.weight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.ColorResourcesKt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.umeng.analytics.pro.am;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.R;
import com.ykb.ebook.activity.BookReviewActivity;
import com.ykb.ebook.activity.ReadBookActivity;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.BaseQuickAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.common.ConstantKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.databinding.ViewBookPageBinding;
import com.ykb.ebook.dialog.BookCommentDialog;
import com.ykb.ebook.dialog.BookInfoDialog;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.model.PerusalDuration;
import com.ykb.ebook.model.TextChapter;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.model.TextPage;
import com.ykb.ebook.model.TextPosition;
import com.ykb.ebook.model.Ways;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.NumberUtilKt;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.vm.BookInfoViewModel;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b:\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010%\u001a\u00020&2\b\b\u0002\u0010'\u001a\u00020\u0011J\u0006\u0010(\u001a\u00020&J\u0018\u0010)\u001a\u00020&2\u0006\u0010*\u001a\u00020 2\b\b\u0002\u0010+\u001a\u00020\u0013J\b\u0010,\u001a\u0004\u0018\u00010-J\u0006\u0010.\u001a\u00020\u000fJ\u0006\u0010/\u001a\u000200J\u0016\u00101\u001a\u0012\u0012\u0004\u0012\u00020002j\b\u0012\u0004\u0012\u000200`3J\u0016\u00104\u001a\u0012\u0012\u0004\u0012\u00020002j\b\u0012\u0004\u0012\u000200`3J\u0006\u00105\u001a\u000200J\u0006\u00106\u001a\u00020\u0011J\u0006\u00107\u001a\u00020\u0011J\b\u00108\u001a\u000209H\u0002J\u000e\u0010:\u001a\u00020\u00132\u0006\u0010;\u001a\u00020\u0013J\u0006\u0010<\u001a\u00020=J\u0006\u0010>\u001a\u00020\u000fJ\u0006\u0010?\u001a\u00020&J\b\u0010@\u001a\u00020&H\u0002J\u0006\u0010A\u001a\u00020&J\u0006\u0010B\u001a\u00020\u0011J9\u0010C\u001a\u00020&2\u0006\u0010D\u001a\u00020\t2\u0006\u0010;\u001a\u00020\t2!\u0010E\u001a\u001d\u0012\u0013\u0012\u00110G¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(J\u0012\u0004\u0012\u00020&0FJ\u0006\u0010K\u001a\u00020&J\u000e\u0010L\u001a\u00020&2\u0006\u0010D\u001a\u00020\u0013J\u0016\u0010M\u001a\u00020\u00112\u0006\u0010D\u001a\u00020\t2\u0006\u0010;\u001a\u00020\tJ\u0016\u0010N\u001a\u00020\u00112\u0006\u0010D\u001a\u00020\u00132\u0006\u0010;\u001a\u00020\u0013J\u000e\u0010O\u001a\u00020\u000f2\u0006\u0010P\u001a\u00020\u0013J\u0006\u0010Q\u001a\u00020&J\u0006\u0010R\u001a\u00020&J\u0006\u0010S\u001a\u00020\u0011J\u000e\u0010T\u001a\u00020&2\u0006\u0010U\u001a\u00020\u0013J \u0010V\u001a\u00020&2\u0006\u0010D\u001a\u00020\t2\u0006\u0010;\u001a\u00020\t2\b\b\u0002\u0010W\u001a\u00020\u0011J\u000e\u0010X\u001a\u00020&2\u0006\u0010J\u001a\u00020GJ2\u0010X\u001a\u00020&2\u0006\u0010P\u001a\u00020\u00132\u0006\u0010Y\u001a\u00020\u00132\u0006\u0010Z\u001a\u00020\u00132\b\b\u0002\u0010[\u001a\u00020\u00112\b\b\u0002\u0010\\\u001a\u00020\u0011J \u0010]\u001a\u00020&2\u0006\u0010D\u001a\u00020\t2\u0006\u0010;\u001a\u00020\t2\b\b\u0002\u0010W\u001a\u00020\u0011J\u000e\u0010^\u001a\u00020&2\u0006\u0010J\u001a\u00020GJ2\u0010^\u001a\u00020&2\u0006\u0010P\u001a\u00020\u00132\u0006\u0010Y\u001a\u00020\u00132\u0006\u0010Z\u001a\u00020\u00132\b\b\u0002\u0010[\u001a\u00020\u00112\b\b\u0002\u0010\\\u001a\u00020\u0011J9\u0010_\u001a\u00020&2\u0006\u0010D\u001a\u00020\t2\u0006\u0010;\u001a\u00020\t2!\u0010E\u001a\u001d\u0012\u0013\u0012\u00110G¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(J\u0012\u0004\u0012\u00020&0FJ\u000e\u0010`\u001a\u00020&2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010a\u001a\u00020&2\u0006\u0010b\u001a\u00020\u0013J\"\u0010c\u001a\u00020&2\u0006\u0010d\u001a\u00020\u000f2\b\b\u0002\u0010Q\u001a\u00020\u00112\b\b\u0002\u0010e\u001a\u00020\u0011J\u000e\u0010f\u001a\u00020&2\u0006\u0010g\u001a\u00020 J\u0006\u0010h\u001a\u00020&J\u000e\u0010i\u001a\u00020&2\u0006\u0010j\u001a\u00020\u0011J\u0006\u0010k\u001a\u00020&J\u0006\u0010l\u001a\u00020&J\u0010\u0010m\u001a\u00020\u000f2\u0006\u0010d\u001a\u00020\u000fH\u0007J\u0012\u0010n\u001a\u00020&2\b\b\u0002\u0010o\u001a\u00020\u0013H\u0002J\u0006\u0010p\u001a\u00020&J\u0006\u0010q\u001a\u00020&J\u0010\u0010r\u001a\u00020&2\b\b\u0002\u0010s\u001a\u00020\u0011J\u0006\u0010t\u001a\u00020&J\u0010\u0010u\u001a\u00020&2\u0006\u0010\b\u001a\u00020\tH\u0007J\u0012\u0010v\u001a\u00020&2\b\b\u0002\u0010w\u001a\u00020\u0011H\u0007J\b\u0010x\u001a\u00020&H\u0002J\u0006\u0010y\u001a\u00020&J\b\u0010z\u001a\u00020&H\u0002J\u0006\u0010{\u001a\u00020&J\u0006\u0010|\u001a\u00020&J\u001e\u0010}\u001a\u00020&2\u0016\u0010Z\u001a\u0012\u0012\u0004\u0012\u00020\u001302j\b\u0012\u0004\u0012\u00020\u0013`3J\u0017\u0010~\u001a\u00020&2\u0006\u0010\u007f\u001a\u00020G2\u0007\u0010\u0080\u0001\u001a\u00020GJ\u0007\u0010\u0081\u0001\u001a\u00020&R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001c8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020 8F¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0082\u0001"}, d2 = {"Lcom/ykb/ebook/weight/PageView;", "Landroid/widget/FrameLayout;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "adapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "Lcom/ykb/ebook/model/Ways;", am.Z, "", "binding", "Lcom/ykb/ebook/databinding/ViewBookPageBinding;", "bookInfo", "Lcom/ykb/ebook/model/BookInfo;", "curTextPage", "Lcom/ykb/ebook/model/TextPage;", "firstChapterFirstPage", "", "headerHeight", "", "getHeaderHeight", "()I", "isMainView", "isScroll", "lastChapterLastPage", "mRateClickListener", "Landroid/view/View$OnClickListener;", "readBookActivity", "Lcom/ykb/ebook/activity/ReadBookActivity;", "getReadBookActivity", "()Lcom/ykb/ebook/activity/ReadBookActivity;", "selectedText", "", "getSelectedText", "()Ljava/lang/String;", "viewModel", "Lcom/ykb/ebook/vm/BookInfoViewModel;", "cancelSelect", "", "clearSearchResult", "drawDashLine", "drawLine", "charData", TtmlNode.TAG_STYLE, "getCurVisibleFirstLine", "Lcom/ykb/ebook/model/TextLine;", "getCurVisiblePage", "getEndPageView", "Landroid/view/View;", "getFirstPageInterceptViews", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getLastPageInterceptViews", "getPageBookInfoView", "getReverseEndCursor", "getReverseStartCursor", "getRvBottomPosition", "", "getRvRecycleItemPos", "y", "getTextChapter", "Lcom/ykb/ebook/model/TextChapter;", "getTextPage", "hideFirstPage", "initView", "invalidateContentView", "isLongScreenShot", "longPress", "x", "select", "Lkotlin/Function1;", "Lcom/ykb/ebook/model/TextPosition;", "Lkotlin/ParameterName;", "name", "textPos", "markAsMainView", "moveRvBottom", "onClick", "posInRvBottom", "relativePage", "relativePagePos", "resetPageOffset", "resetReverseCursor", "rvBottomVisibleStatus", "scroll", "offset", "selectEndMove", "includeHeaderHeight", "selectEndMoveIndex", "lineIndex", "charIndex", "isTouch", "isLast", "selectStartMove", "selectStartMoveIndex", "selectText", "setBookInfo", "setBottomActionClick", "position", "setContent", "textPage", "updateTheme", "setContentDescription", "content", "setFirstPageContent", "setIsScroll", "value", "setLastPageContent", "setLibraryState", "setProgress", "showBookCommentDialog", "rate", "showFirstPage", "showLastPage", "showNormalPage", "fromLastPage", "submitRenderTask", "upBattery", "upBg", "refresh", "upBgAlpha", "upStatusBar", "upStyle", "upTime", "upTimeShow", "updateSelectColumns", "updateStartEndTextPosition", "start", "end", "updateTitle", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPageView.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PageView.kt\ncom/ykb/ebook/weight/PageView\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 3 View.kt\nandroidx/core/view/ViewKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1607:1\n42#2:1608\n42#2:1609\n42#2:1610\n42#2:1614\n42#2:1615\n296#3,2:1611\n252#3:1616\n1#4:1613\n*S KotlinDebug\n*F\n+ 1 PageView.kt\ncom/ykb/ebook/weight/PageView\n*L\n314#1:1608\n319#1:1609\n320#1:1610\n745#1:1614\n1301#1:1615\n365#1:1611,2\n1547#1:1616\n*E\n"})
/* loaded from: classes8.dex */
public final class PageView extends FrameLayout {

    @NotNull
    private final CommonAdapter<Ways> adapter;
    private float battery;

    @NotNull
    private final ViewBookPageBinding binding;

    @Nullable
    private BookInfo bookInfo;

    @Nullable
    private TextPage curTextPage;
    private boolean firstChapterFirstPage;
    private boolean isMainView;
    private boolean isScroll;
    private boolean lastChapterLastPage;

    @NotNull
    private final View.OnClickListener mRateClickListener;

    @NotNull
    private BookInfoViewModel viewModel;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PageView(@NotNull final Context context) {
        Context appCtx;
        int i2;
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        ViewBookPageBinding viewBookPageBindingInflate = ViewBookPageBinding.inflate(LayoutInflater.from(context), this, true);
        Intrinsics.checkNotNullExpressionValue(viewBookPageBindingInflate, "inflate(LayoutInflater.from(context), this, true)");
        this.binding = viewBookPageBindingInflate;
        this.battery = 1.0f;
        this.adapter = new CommonAdapter<>(R.layout.item_book_unlock, null, 2, 0 == true ? 1 : 0);
        ReadBookActivity readBookActivity = getReadBookActivity();
        Intrinsics.checkNotNull(readBookActivity);
        Application application = readBookActivity.getApplication();
        Intrinsics.checkNotNullExpressionValue(application, "readBookActivity!!.application");
        this.viewModel = new BookInfoViewModel(application);
        if (!isInEditMode()) {
            upStyle();
            initView();
        }
        this.bookInfo = ReadBook.INSTANCE.getBook();
        MutableLiveData<BookInfo> bookInfo = this.viewModel.getBookInfo();
        AppCompatActivity activity = ViewExtensionsKt.getActivity(this);
        Intrinsics.checkNotNull(activity);
        final Function1<BookInfo, Unit> function1 = new Function1<BookInfo, Unit>() { // from class: com.ykb.ebook.weight.PageView.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookInfo bookInfo2) throws SecurityException, NumberFormatException {
                invoke2(bookInfo2);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BookInfo bookInfo2) throws SecurityException, NumberFormatException {
                PageView.this.bookInfo = bookInfo2;
                if (PageView.this.firstChapterFirstPage) {
                    PageView.this.showFirstPage();
                } else if (PageView.this.lastChapterLastPage) {
                    PageView.this.setLastPageContent();
                }
            }
        };
        bookInfo.observe(activity, new Observer() { // from class: com.ykb.ebook.weight.n
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PageView._init_$lambda$0(function1, obj);
            }
        });
        MutableLiveData<String> bookError = this.viewModel.getBookError();
        AppCompatActivity activity2 = ViewExtensionsKt.getActivity(this);
        Intrinsics.checkNotNull(activity2);
        final Function1<String, Unit> function12 = new Function1<String, Unit>() { // from class: com.ykb.ebook.weight.PageView.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String str) {
                Toast.makeText(context, str, 0).show();
            }
        };
        bookError.observe(activity2, new Observer() { // from class: com.ykb.ebook.weight.w
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PageView._init_$lambda$1(function12, obj);
            }
        });
        MutableLiveData<Integer> publishReview = this.viewModel.getPublishReview();
        AppCompatActivity activity3 = ViewExtensionsKt.getActivity(this);
        Intrinsics.checkNotNull(activity3);
        final Function1<Integer, Unit> function13 = new Function1<Integer, Unit>() { // from class: com.ykb.ebook.weight.PageView.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke2(num);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Integer it) {
                String id;
                ReadBook readBook = ReadBook.INSTANCE;
                BookInfo book = readBook.getBook();
                if (book != null) {
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    book.setRate(it.intValue());
                }
                RTextView rTextView = PageView.this.binding.endPage.rbHigh;
                BookInfo book2 = readBook.getBook();
                rTextView.setSelected(book2 != null && book2.getRate() == 1);
                RTextView rTextView2 = PageView.this.binding.endPage.rbMiddle;
                BookInfo book3 = readBook.getBook();
                rTextView2.setSelected(book3 != null && book3.getRate() == 2);
                RTextView rTextView3 = PageView.this.binding.endPage.rbLow;
                BookInfo book4 = readBook.getBook();
                rTextView3.setSelected(book4 != null && book4.getRate() == 3);
                Toast.makeText(context, "点评成功", 0).show();
                BookInfo book5 = readBook.getBook();
                if (book5 == null || (id = book5.getId()) == null) {
                    return;
                }
                PageView.this.viewModel.bookInfo(id);
            }
        };
        publishReview.observe(activity3, new Observer() { // from class: com.ykb.ebook.weight.x
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PageView._init_$lambda$2(function13, obj);
            }
        });
        MutableLiveData<Object> addBookData = this.viewModel.getAddBookData();
        AppCompatActivity activity4 = ViewExtensionsKt.getActivity(this);
        Intrinsics.checkNotNull(activity4);
        addBookData.observe(activity4, new Observer() { // from class: com.ykb.ebook.weight.y
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PageView._init_$lambda$3(context, this, obj);
            }
        });
        MutableLiveData<Object> delBookData = this.viewModel.getDelBookData();
        AppCompatActivity activity5 = ViewExtensionsKt.getActivity(this);
        Intrinsics.checkNotNull(activity5);
        delBookData.observe(activity5, new Observer() { // from class: com.ykb.ebook.weight.z
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PageView._init_$lambda$4(context, this, obj);
            }
        });
        TextView textView = viewBookPageBindingInflate.tvTitle;
        if (ReadConfig.INSTANCE.getColorMode() != 2) {
            appCtx = AppCtxKt.getAppCtx();
            i2 = R.color.color_909090;
        } else {
            appCtx = AppCtxKt.getAppCtx();
            i2 = R.color.color_575F79;
        }
        textView.setTextColor(appCtx.getColor(i2));
        this.mRateClickListener = new View.OnClickListener() { // from class: com.ykb.ebook.weight.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PageView.mRateClickListener$lambda$31(context, this, view);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$3(Context context, PageView this$0, Object obj) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Toast.makeText(context, "已加入书架", 0).show();
        this$0.binding.firstPage.tvAddBook.setText("移出书架");
        BookInfo bookInfo = this$0.bookInfo;
        Intrinsics.checkNotNull(bookInfo);
        bookInfo.setBookshelf("1");
        ReadBookActivity readBookActivity = this$0.getReadBookActivity();
        if (readBookActivity != null) {
            readBookActivity.setLibraryState();
        }
        EventBus.getDefault().post("updateBookList");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$4(Context context, PageView this$0, Object obj) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Toast.makeText(context, "已移出书架", 0).show();
        this$0.binding.firstPage.tvAddBook.setText("加入书架");
        BookInfo bookInfo = this$0.bookInfo;
        Intrinsics.checkNotNull(bookInfo);
        bookInfo.setBookshelf("0");
        ReadBookActivity readBookActivity = this$0.getReadBookActivity();
        if (readBookActivity != null) {
            readBookActivity.setLibraryState();
        }
        EventBus.getDefault().post("updateBookList");
    }

    public static /* synthetic */ void cancelSelect$default(PageView pageView, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        pageView.cancelSelect(z2);
    }

    public static /* synthetic */ void drawLine$default(PageView pageView, String str, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        pageView.drawLine(str, i2);
    }

    private final ReadBookActivity getReadBookActivity() {
        AppCompatActivity activity = ViewExtensionsKt.getActivity(this);
        if (activity instanceof ReadBookActivity) {
            return (ReadBookActivity) activity;
        }
        return null;
    }

    private final int[] getRvBottomPosition() {
        int[] iArr = new int[2];
        this.binding.llPermission.getLocationOnScreen(iArr);
        return new int[]{iArr[0], iArr[1], iArr[0] + this.binding.llPermission.getWidth(), iArr[1] + this.binding.llPermission.getHeight()};
    }

    private final void initView() {
        ViewBookPageBinding viewBookPageBinding = this.binding;
        this.adapter.setConvert(new Function3<QuickViewHolder, Integer, Ways, Unit>() { // from class: com.ykb.ebook.weight.PageView$initView$1$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, Ways ways) throws SecurityException {
                invoke(quickViewHolder, num.intValue(), ways);
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable Ways ways) throws SecurityException {
                Context appCtx;
                int i3;
                Intrinsics.checkNotNullParameter(holder, "holder");
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
                RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
                ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin = i2 == this.this$0.adapter.getItems().size() - 1 ? 0 : ScreenUtil.getPxByDp(this.this$0.getContext(), 16);
                holder.itemView.setLayoutParams(layoutParams2);
                ImageView imageView = (ImageView) holder.getView(R.id.leftimg);
                RTextView rTextView = (RTextView) holder.getView(R.id.tv_to_lock);
                ReadConfig readConfig = ReadConfig.INSTANCE;
                if (readConfig.getColorMode() == 2) {
                    rTextView.setTextColor(AppCtxKt.getAppCtx().getColor(R.color.color_121622));
                } else {
                    rTextView.setTextColor(AppCtxKt.getAppCtx().getColor(R.color.white));
                }
                RTextViewHelper helper = rTextView.getHelper();
                if (readConfig.getColorMode() != 2) {
                    appCtx = AppCtxKt.getAppCtx();
                    i3 = R.color.color_F95843;
                } else {
                    appCtx = AppCtxKt.getAppCtx();
                    i3 = R.color.color_B2575C;
                }
                helper.setBackgroundColorNormal(appCtx.getColor(i3));
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius(ConvertExtensionsKt.dpToPx(8.0f));
                int colorMode = readConfig.getColorMode();
                if (colorMode == 0 || colorMode == 1) {
                    gradientDrawable.setStroke(ConvertExtensionsKt.dpToPx(1), Color.argb(25, 255, 116, 0));
                } else if (colorMode == 2) {
                    gradientDrawable.setStroke(ConvertExtensionsKt.dpToPx(1), Color.argb(25, R2.anim.window_bottom_out, 90, 20));
                }
                int colorMode2 = readConfig.getColorMode();
                if (colorMode2 == 0 || colorMode2 == 1) {
                    gradientDrawable.setColor(ColorStateList.valueOf(Color.argb(10, 255, 116, 0)));
                } else if (colorMode2 == 2) {
                    gradientDrawable.setColor(ColorStateList.valueOf(Color.argb(10, R2.anim.window_bottom_out, 90, 20)));
                }
                holder.itemView.setBackground(gradientDrawable);
                if (!StringsKt__StringsJVMKt.equals$default(ways != null ? ways.getWay() : null, "join_us", false, 2, null)) {
                    if (StringsKt__StringsJVMKt.equals$default(ways != null ? ways.getWay() : null, "vip_enable", false, 2, null)) {
                        if (readConfig.getColorMode() == 0 || readConfig.getColorMode() == 1) {
                            imageView.setImageResource(R.mipmap.ic_ebook_way_vip_day);
                        } else {
                            imageView.setImageResource(R.mipmap.ic_ebook_way_vip_night);
                        }
                    } else if (readConfig.getColorMode() == 0 || readConfig.getColorMode() == 1) {
                        imageView.setImageResource(R.mipmap.ic_ebook_way_buy_day);
                    } else {
                        imageView.setImageResource(R.mipmap.ic_ebook_way_buy_night);
                    }
                } else if (readConfig.getColorMode() == 0 || readConfig.getColorMode() == 1) {
                    imageView.setImageResource(R.mipmap.ic_ebook_way_join_us_day);
                } else {
                    imageView.setImageResource(R.mipmap.ic_ebook_way_join_us_night);
                }
                int i4 = R.id.mfristTxt;
                BaseViewHolder textColor = holder.setText(i4, ways != null ? ways.getTitle() : null).setTextColor(i4, readConfig.getColorMode() == 2 ? StringExtensionsKt.hexValue2IntColor("#7380a9") : StringExtensionsKt.hexValue2IntColor("#141516"));
                int i5 = R.id.mDofristTxt;
                textColor.setText(i5, ways != null ? ways.getDescription() : null).setTextColor(i5, readConfig.getColorMode() == 2 ? StringExtensionsKt.hexValue2IntColor("#606A8A") : StringExtensionsKt.hexValue2IntColor("#7B7E83"));
            }
        });
        this.adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ykb.ebook.weight.b0
            @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                PageView.initView$lambda$7$lambda$6(this.f26459c, baseQuickAdapter, view, i2);
            }
        });
        viewBookPageBinding.rvBottom.setLayoutManager(new LinearLayoutManager(getContext()));
        viewBookPageBinding.rvBottom.setAdapter(this.adapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$7$lambda$6(PageView this$0, BaseQuickAdapter adapter, View view, int i2) {
        ReadBookActivity readBookActivity;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        Ways ways = (Ways) adapter.getItem(i2);
        if (ways == null || (readBookActivity = this$0.getReadBookActivity()) == null) {
            return;
        }
        readBookActivity.onUnlockClick(ways);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mRateClickListener$lambda$31(Context context, PageView this$0, View view) {
        String duration;
        PerusalDuration perusalDuration;
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (perusalDuration = book.getPerusalDuration()) == null || (duration = perusalDuration.getDuration()) == null) {
            duration = "0";
        }
        if ((new Regex(RegexPool.NUMBERS).matches(duration) ? Integer.parseInt(duration) : 0) < 300) {
            Toast.makeText(context, "阅读本书5分钟后，方可点评", 0).show();
            return;
        }
        int id = view.getId();
        if (id == R.id.rb_high) {
            this$0.showBookCommentDialog(1);
        } else if (id == R.id.rb_middle) {
            this$0.showBookCommentDialog(2);
        } else if (id == R.id.rb_low) {
            this$0.showBookCommentDialog(3);
        }
    }

    public static /* synthetic */ void selectEndMove$default(PageView pageView, float f2, float f3, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        pageView.selectEndMove(f2, f3, z2);
    }

    public static /* synthetic */ void selectEndMoveIndex$default(PageView pageView, int i2, int i3, int i4, boolean z2, boolean z3, int i5, Object obj) {
        if ((i5 & 8) != 0) {
            z2 = true;
        }
        boolean z4 = z2;
        if ((i5 & 16) != 0) {
            z3 = false;
        }
        pageView.selectEndMoveIndex(i2, i3, i4, z4, z3);
    }

    public static /* synthetic */ void selectStartMove$default(PageView pageView, float f2, float f3, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        pageView.selectStartMove(f2, f3, z2);
    }

    public static /* synthetic */ void selectStartMoveIndex$default(PageView pageView, int i2, int i3, int i4, boolean z2, boolean z3, int i5, Object obj) {
        if ((i5 & 8) != 0) {
            z2 = true;
        }
        boolean z4 = z2;
        if ((i5 & 16) != 0) {
            z3 = false;
        }
        pageView.selectStartMoveIndex(i2, i3, i4, z4, z3);
    }

    public static /* synthetic */ void setContent$default(PageView pageView, TextPage textPage, boolean z2, boolean z3, int i2, Object obj) throws SecurityException, NumberFormatException {
        if ((i2 & 2) != 0) {
            z2 = true;
        }
        if ((i2 & 4) != 0) {
            z3 = false;
        }
        pageView.setContent(textPage, z2, z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setContent$lambda$9(PageView this$0, TextPage textPage) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(textPage, "$textPage");
        this$0.setProgress(textPage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setFirstPageContent$lambda$23$lambda$15(PageView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AppCompatActivity activity = ViewExtensionsKt.getActivity(this$0);
        Intrinsics.checkNotNull(activity);
        activity.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setFirstPageContent$lambda$23$lambda$16(PageView this$0, BookInfo book, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(book, "$book");
        Intent intent = new Intent(this$0.getContext(), (Class<?>) BookReviewActivity.class);
        intent.putExtra("bookId", book.getId());
        intent.putExtra("type", 2);
        this$0.getContext().startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setFirstPageContent$lambda$23$lambda$17(PageView this$0, BookInfo book, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(book, "$book");
        Intent intent = new Intent(this$0.getContext(), (Class<?>) BookReviewActivity.class);
        intent.putExtra("bookId", book.getId());
        intent.putExtra("type", 1);
        this$0.getContext().startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setFirstPageContent$lambda$23$lambda$18(PageView this$0, BookInfo book, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(book, "$book");
        new XPopup.Builder(this$0.getContext()).asImageViewer(null, book.getThumbnail(), new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setFirstPageContent$lambda$23$lambda$19(PageView this$0, BookInfo book, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(book, "$book");
        BookInfo bookInfo = this$0.bookInfo;
        Intrinsics.checkNotNull(bookInfo);
        if (Intrinsics.areEqual(bookInfo.isBookshelf(), "0")) {
            this$0.viewModel.addBook(book.getId());
        } else {
            this$0.viewModel.delBook(book.getId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setFirstPageContent$lambda$23$lambda$20(PageView this$0, BookInfo book, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(book, "$book");
        Log.INSTANCE.logE("sub_str", "sub===>点击更多");
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        new BookInfoDialog.Builder(context, book.getDescribe(), new Function0<Unit>() { // from class: com.ykb.ebook.weight.PageView$setFirstPageContent$1$6$1
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                ReadConfig readConfig = ReadConfig.INSTANCE;
                readConfig.setFirstPage(false);
                readConfig.setLastPage(false);
                ReadBook.openChapter$default(ReadBook.INSTANCE, 0, 0, null, 6, null);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setFirstPageContent$lambda$23$lambda$21(PageView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReadBookActivity readBookActivity = this$0.getReadBookActivity();
        if (readBookActivity != null) {
            readBookActivity.readFirstPageContent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setFirstPageContent$lambda$23$lambda$22(PageView this$0, BookInfo book) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(book, "$book");
        TextView textView = this$0.binding.firstPage.tvBookInfo;
        Intrinsics.checkNotNullExpressionValue(textView, "binding.firstPage.tvBookInfo");
        ViewExtensionsKt.invisible(textView);
        this$0.binding.firstPage.tvBookInfo.setText(book.getDescribe());
        Layout layout = this$0.binding.firstPage.tvBookInfo.getLayout();
        if (layout != null) {
            if (layout.getLineCount() >= 5) {
                this$0.binding.firstPage.tvBookInfo.setText(book.getDescribe());
                LinearLayout linearLayout = this$0.binding.firstPage.lyMore;
                Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.firstPage.lyMore");
                ViewExtensionsKt.visible(linearLayout);
            } else {
                LinearLayout linearLayout2 = this$0.binding.firstPage.lyMore;
                Intrinsics.checkNotNullExpressionValue(linearLayout2, "binding.firstPage.lyMore");
                ViewExtensionsKt.gone(linearLayout2);
            }
        }
        TextView textView2 = this$0.binding.firstPage.tvBookInfo;
        Intrinsics.checkNotNullExpressionValue(textView2, "binding.firstPage.tvBookInfo");
        ViewExtensionsKt.visible(textView2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setLastPageContent$lambda$29$lambda$25(PageView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Context context = this$0.getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
        ((Activity) context).onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setLastPageContent$lambda$30(PageView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AppCompatActivity activity = ViewExtensionsKt.getActivity(this$0);
        Intrinsics.checkNotNull(activity);
        activity.finish();
    }

    private final void showBookCommentDialog(int rate) {
        String rateComment;
        ReadBook readBook = ReadBook.INSTANCE;
        String ratePicture = "";
        if (readBook.getBook() != null) {
            BookInfo book = readBook.getBook();
            Intrinsics.checkNotNull(book);
            rateComment = book.getRateComment();
        } else {
            rateComment = "";
        }
        if (readBook.getBook() != null) {
            BookInfo book2 = readBook.getBook();
            Intrinsics.checkNotNull(book2);
            ratePicture = book2.getRatePicture();
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        BookCommentDialog.Builder builder = new BookCommentDialog.Builder(context, String.valueOf(rate), rateComment, ratePicture, "评价本书");
        builder.setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.weight.PageView.showBookCommentDialog.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(String str, String str2, String str3) {
                invoke2(str, str2, str3);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String picPath, @NotNull String content, @NotNull String rate2) {
                String id;
                Intrinsics.checkNotNullParameter(picPath, "picPath");
                Intrinsics.checkNotNullParameter(content, "content");
                Intrinsics.checkNotNullParameter(rate2, "rate");
                BookInfo book3 = ReadBook.INSTANCE.getBook();
                if (book3 == null || (id = book3.getId()) == null) {
                    return;
                }
                PageView.this.viewModel.publishReview(Integer.parseInt(rate2), content, picPath, id);
            }
        });
        builder.show();
    }

    public static /* synthetic */ void showBookCommentDialog$default(PageView pageView, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 0;
        }
        pageView.showBookCommentDialog(i2);
    }

    public static /* synthetic */ void showNormalPage$default(PageView pageView, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        pageView.showNormalPage(z2);
    }

    public static /* synthetic */ void upBg$default(PageView pageView, boolean z2, int i2, Object obj) throws SecurityException, NumberFormatException {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        pageView.upBg(z2);
    }

    private final void upBgAlpha() {
        this.binding.vwBg.setAlpha(ReadConfig.INSTANCE.getBgAlpha() / 100.0f);
    }

    private final void upStyle() {
        upStatusBar();
        upTime();
        upTimeShow();
        upBattery(this.battery);
    }

    public final void cancelSelect(boolean clearSearchResult) {
        this.binding.contentTextView.cancelSelect(clearSearchResult);
    }

    public final void drawDashLine() {
        this.binding.contentTextView.drawDashLine();
    }

    public final void drawLine(@NotNull String charData, int style) {
        Intrinsics.checkNotNullParameter(charData, "charData");
        this.binding.contentTextView.drawUnderLine(true, ReadConfig.INSTANCE.getDrawLineColor(), style, charData);
    }

    @Nullable
    public final TextLine getCurVisibleFirstLine() {
        return this.binding.contentTextView.getCurVisibleFirstLine();
    }

    @NotNull
    public final TextPage getCurVisiblePage() {
        return this.binding.contentTextView.getCurVisiblePage();
    }

    @NotNull
    public final View getEndPageView() {
        FrameLayout root = this.binding.endPage.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.endPage.root");
        return root;
    }

    @NotNull
    public final ArrayList<View> getFirstPageInterceptViews() {
        ArrayList<View> arrayList = new ArrayList<>();
        arrayList.add(this.binding.firstPage.imgBack);
        arrayList.add(this.binding.firstPage.imgCover);
        arrayList.add(this.binding.firstPage.llComment);
        arrayList.add(this.binding.firstPage.llBookComment);
        arrayList.add(this.binding.firstPage.tvBeginRead);
        arrayList.add(this.binding.firstPage.tvBookInfo);
        arrayList.add(this.binding.firstPage.tvAddBook);
        return arrayList;
    }

    public final int getHeaderHeight() {
        int statusBarHeight;
        if (ReadConfig.INSTANCE.getHideStatusBar()) {
            statusBarHeight = 0;
        } else {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            statusBarHeight = ContextExtensionsKt.getStatusBarHeight(context);
        }
        return statusBarHeight + this.binding.llTop.getHeight();
    }

    @NotNull
    public final ArrayList<View> getLastPageInterceptViews() {
        ArrayList<View> arrayList = new ArrayList<>();
        arrayList.add(this.binding.endPage.rbHigh);
        arrayList.add(this.binding.endPage.rbMiddle);
        arrayList.add(this.binding.endPage.rbLow);
        arrayList.add(this.binding.endPage.imgBack);
        arrayList.add(this.binding.endPage.tvExitRead);
        return arrayList;
    }

    @NotNull
    public final View getPageBookInfoView() {
        LinearLayout root = this.binding.firstPage.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.firstPage.root");
        return root;
    }

    public final boolean getReverseEndCursor() {
        return this.binding.contentTextView.getReverseEndCursor();
    }

    public final boolean getReverseStartCursor() {
        return this.binding.contentTextView.getReverseStartCursor();
    }

    public final int getRvRecycleItemPos(int y2) {
        View view;
        int itemCount = this.adapter.getItemCount();
        for (int i2 = 0; i2 < itemCount; i2++) {
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.binding.rvBottom.findViewHolderForAdapterPosition(i2);
            if (viewHolderFindViewHolderForAdapterPosition != null && (view = viewHolderFindViewHolderForAdapterPosition.itemView) != null) {
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                int i3 = iArr[1];
                if (i3 <= y2 && y2 < viewHolderFindViewHolderForAdapterPosition.itemView.getHeight() + i3) {
                    return i2;
                }
            }
        }
        return -1;
    }

    @NotNull
    public final String getSelectedText() {
        return this.binding.contentTextView.getSelectedText();
    }

    @NotNull
    public final TextChapter getTextChapter() {
        return this.binding.contentTextView.getTextPage().getTextChapter();
    }

    @NotNull
    public final TextPage getTextPage() {
        return this.binding.contentTextView.getTextPage();
    }

    public final void hideFirstPage() {
        ConstraintLayout constraintLayout = this.binding.vwRoot;
        Intrinsics.checkNotNullExpressionValue(constraintLayout, "binding.vwRoot");
        ViewExtensionsKt.visible(constraintLayout);
        FrameLayout frameLayout = this.binding.endPage.llEndPage;
        Intrinsics.checkNotNullExpressionValue(frameLayout, "binding.endPage.llEndPage");
        ViewExtensionsKt.gone(frameLayout);
        LinearLayout linearLayout = this.binding.firstPage.llStartPage;
        Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.firstPage.llStartPage");
        ViewExtensionsKt.gone(linearLayout);
    }

    public final void invalidateContentView() {
        this.binding.contentTextView.invalidate();
    }

    public final boolean isLongScreenShot() {
        return this.binding.contentTextView.getLongScreenshot();
    }

    public final void longPress(float x2, float y2, @NotNull Function1<? super TextPosition, Unit> select) {
        Intrinsics.checkNotNullParameter(select, "select");
        this.binding.contentTextView.longPress(x2, y2 - getHeaderHeight(), select);
    }

    public final void markAsMainView() {
        this.isMainView = true;
        this.binding.contentTextView.setMainView(true);
    }

    public final void moveRvBottom(int x2) {
        this.binding.rvBottom.smoothScrollBy(0, x2);
    }

    public final boolean onClick(float x2, float y2) {
        return this.binding.contentTextView.click(x2, y2 - getHeaderHeight());
    }

    public final boolean posInRvBottom(int x2, int y2) {
        int[] rvBottomPosition = getRvBottomPosition();
        return x2 > rvBottomPosition[0] && x2 < rvBottomPosition[2] && y2 > rvBottomPosition[1] && y2 < rvBottomPosition[3];
    }

    @NotNull
    public final TextPage relativePage(int relativePagePos) {
        return this.binding.contentTextView.relativePage(relativePagePos);
    }

    public final void resetPageOffset() {
        this.binding.contentTextView.resetPageOffset();
    }

    public final void resetReverseCursor() {
        this.binding.contentTextView.resetReverseCursor();
    }

    public final boolean rvBottomVisibleStatus() {
        LinearLayout linearLayout = this.binding.llPermission;
        Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.llPermission");
        return linearLayout.getVisibility() == 0;
    }

    public final void scroll(int offset) {
        this.binding.contentTextView.scroll(offset);
    }

    public final void selectEndMove(float x2, float y2, boolean includeHeaderHeight) {
        Log log = Log.INSTANCE;
        log.logD("end_move", "x " + x2 + "  y = " + y2);
        log.logD("end_move_new", "x " + x2 + "  y = " + (y2 - ((float) getHeaderHeight())));
        this.binding.contentTextView.selectEndMove(x2, y2 - (includeHeaderHeight ? getHeaderHeight() : 0));
    }

    public final void selectEndMoveIndex(int relativePagePos, int lineIndex, int charIndex, boolean isTouch, boolean isLast) {
        this.binding.contentTextView.selectEndMoveIndex(relativePagePos, lineIndex, charIndex, isTouch, isLast);
    }

    public final void selectStartMove(float x2, float y2, boolean includeHeaderHeight) {
        this.binding.contentTextView.selectStartMove(x2, y2 - (includeHeaderHeight ? getHeaderHeight() : 0));
    }

    public final void selectStartMoveIndex(int relativePagePos, int lineIndex, int charIndex, boolean isTouch, boolean isLast) {
        this.binding.contentTextView.selectStartMoveIndex(relativePagePos, lineIndex, charIndex, isTouch, isLast);
    }

    public final void selectText(float x2, float y2, @NotNull Function1<? super TextPosition, Unit> select) {
        Intrinsics.checkNotNullParameter(select, "select");
        this.binding.contentTextView.selectText(x2, y2 - getHeaderHeight(), select);
    }

    public final void setBookInfo(@NotNull BookInfo bookInfo) {
        Intrinsics.checkNotNullParameter(bookInfo, "bookInfo");
        this.bookInfo = bookInfo;
    }

    public final void setBottomActionClick(int position) {
        ReadBookActivity readBookActivity;
        Ways item = this.adapter.getItem(position);
        if (item == null || (readBookActivity = getReadBookActivity()) == null) {
            return;
        }
        readBookActivity.onUnlockClick(item);
    }

    public final void setContent(@NotNull final TextPage textPage, boolean resetPageOffset, boolean updateTheme) throws SecurityException, NumberFormatException {
        Intrinsics.checkNotNullParameter(textPage, "textPage");
        if (Intrinsics.areEqual(textPage.getText(), AppCtxKt.getAppCtx().getString(R.string.data_loading))) {
            return;
        }
        this.curTextPage = textPage;
        if (this.bookInfo == null) {
            this.bookInfo = ReadBook.INSTANCE.getBook();
        }
        if (resetPageOffset) {
            resetPageOffset();
        }
        if (textPage.textChapter.getChapter().isPay()) {
            this.adapter.submitList(CollectionsKt__CollectionsKt.emptyList());
            LinearLayout linearLayout = this.binding.llPermission;
            Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.llPermission");
            ViewExtensionsKt.gone(linearLayout);
            View view = this.binding.topEmpty;
            Intrinsics.checkNotNullExpressionValue(view, "binding.topEmpty");
            ViewExtensionsKt.gone(view);
        } else {
            ReadBook readBook = ReadBook.INSTANCE;
            if (!readBook.getPayWays().isEmpty()) {
                if (this.isScroll) {
                    View view2 = this.binding.topEmpty;
                    Intrinsics.checkNotNullExpressionValue(view2, "binding.topEmpty");
                    ViewExtensionsKt.visible(view2);
                } else {
                    View view3 = this.binding.topEmpty;
                    Intrinsics.checkNotNullExpressionValue(view3, "binding.topEmpty");
                    ViewExtensionsKt.gone(view3);
                }
                this.adapter.submitList(readBook.getPayWays());
                ReadConfig readConfig = ReadConfig.INSTANCE;
                int colorMode = readConfig.getColorMode();
                this.binding.vBg.setBackground(ContextCompat.getDrawable(getContext(), colorMode != 0 ? colorMode != 1 ? R.drawable.shape_translate_deep_bg : R.drawable.shape_translate_yellow_bg : R.drawable.shape_translate_bg));
                RecyclerView recyclerView = this.binding.rvBottom;
                int colorMode2 = readConfig.getColorMode();
                recyclerView.setBackground(new ColorDrawable(colorMode2 != 0 ? colorMode2 != 1 ? StringExtensionsKt.hexValue2IntColor("#0D111D") : StringExtensionsKt.hexValue2IntColor("#EDDDB7") : StringExtensionsKt.hexValue2IntColor("#F7F8FA")));
                LinearLayout linearLayout2 = this.binding.llPermission;
                Intrinsics.checkNotNullExpressionValue(linearLayout2, "binding.llPermission");
                ViewExtensionsKt.visible(linearLayout2);
            }
        }
        Context appCtx = AppCtxKt.getAppCtx();
        StringBuilder sb = new StringBuilder();
        ReadBook readBook2 = ReadBook.INSTANCE;
        BookInfo book = readBook2.getBook();
        sb.append(book != null ? book.getId() : null);
        sb.append("_CURRENT_PAGE");
        int prefInt$default = ContextExtensionsKt.getPrefInt$default(appCtx, sb.toString(), 0, 2, null);
        ReadConfig readConfig2 = ReadConfig.INSTANCE;
        if (readConfig2.getFirstPage() && readBook2.getDurChapterIndex() == 0 && (prefInt$default == 0 || readConfig2.getShowFeiYe())) {
            showFirstPage();
        } else if (readConfig2.getLastPage()) {
            showLastPage();
        } else {
            showNormalPage$default(this, false, 1, null);
        }
        this.binding.contentTextView.setContent(textPage, updateTheme);
        if (readBook2.getDurChapterIndex() == 0 && readBook2.getDurPageIndex() == 0) {
            ViewGroup.LayoutParams layoutParams = this.binding.firstPage.toolbar.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
            AppCompatActivity activity = ViewExtensionsKt.getActivity(this);
            layoutParams2.topMargin = activity != null ? ContextExtensionsKt.getStatusBarHeight(activity) : 0;
            this.binding.firstPage.toolbar.setLayoutParams(layoutParams2);
            if (readConfig2.getShowFirstPage() && readBook2.getBook() != null) {
                readConfig2.setShowFirstPage(false);
                this.firstChapterFirstPage = true;
                this.lastChapterLastPage = false;
                readConfig2.setFirstPage(true);
                setFirstPageContent();
                ConstraintLayout constraintLayout = this.binding.vwRoot;
                Intrinsics.checkNotNullExpressionValue(constraintLayout, "binding.vwRoot");
                ViewExtensionsKt.gone(constraintLayout);
                LinearLayout linearLayout3 = this.binding.firstPage.llStartPage;
                Intrinsics.checkNotNullExpressionValue(linearLayout3, "binding.firstPage.llStartPage");
                ViewExtensionsKt.visible(linearLayout3);
            }
        }
        postDelayed(new Runnable() { // from class: com.ykb.ebook.weight.v
            @Override // java.lang.Runnable
            public final void run() {
                PageView.setContent$lambda$9(this.f26539c, textPage);
            }
        }, 200L);
    }

    public final void setContentDescription(@NotNull String content) {
        Intrinsics.checkNotNullParameter(content, "content");
        this.binding.contentTextView.setContentDescription(content);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setFirstPageContent() throws SecurityException, NumberFormatException {
        Drawable colorDrawable;
        String str;
        String str2;
        final BookInfo bookInfo = this.bookInfo;
        if (bookInfo != null) {
            LinearLayout root = this.binding.firstPage.getRoot();
            ReadConfig readConfig = ReadConfig.INSTANCE;
            if (readConfig.getColorMode() == 1) {
                colorDrawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_content_view);
            } else {
                colorDrawable = new ColorDrawable(readConfig.getColorMode() == 2 ? ContextCompat.getColor(getContext(), R.color.color_0d111d) : ContextCompat.getColor(getContext(), R.color.white));
            }
            root.setBackground(colorDrawable);
            this.binding.firstPage.imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.c0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PageView.setFirstPageContent$lambda$23$lambda$15(this.f26466c, view);
                }
            });
            this.binding.firstPage.llComment.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.d0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PageView.setFirstPageContent$lambda$23$lambda$16(this.f26472c, bookInfo, view);
                }
            });
            this.binding.firstPage.llBookComment.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.e0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PageView.setFirstPageContent$lambda$23$lambda$17(this.f26477c, bookInfo, view);
                }
            });
            this.binding.firstPage.imgCover.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PageView.setFirstPageContent$lambda$23$lambda$18(this.f26508c, bookInfo, view);
                }
            });
            this.binding.firstPage.tvAddBook.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.p
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PageView.setFirstPageContent$lambda$23$lambda$19(this.f26511c, bookInfo, view);
                }
            });
            setLibraryState();
            this.binding.firstPage.lyMore.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PageView.setFirstPageContent$lambda$23$lambda$20(this.f26514c, bookInfo, view);
                }
            });
            this.binding.firstPage.tvBeginRead.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PageView.setFirstPageContent$lambda$23$lambda$21(this.f26517c, view);
                }
            });
            if (readConfig.getColorMode() == 2) {
                Drawable drawable = getContext().getDrawable(R.drawable.icon_more_grey);
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79), PorterDuff.Mode.SRC_IN));
                }
                this.binding.firstPage.ivBookReviewCount.setImageDrawable(drawable);
                this.binding.firstPage.ivBookCommentCount.setImageDrawable(drawable);
                Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.mipmap.ic_start_read);
                Drawable drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.icon_black_back);
                Context context = getContext();
                int i2 = R.color.color_7380a9;
                int color = context.getColor(i2);
                if (drawable2 != null) {
                    drawable2.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                }
                if (drawable3 != null) {
                    drawable3.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                }
                this.binding.firstPage.imgBack.setImageDrawable(drawable3);
                this.binding.firstPage.tvTitle.setTextColor(color);
                this.binding.firstPage.tvReadNum.setTextColor(color);
                this.binding.firstPage.tvCommentNum.setTextColor(color);
                this.binding.firstPage.tvSimpleIntroduce.setTextColor(color);
                this.binding.firstPage.tvReviewNum.setTextColor(color);
                this.binding.firstPage.tvBookInfo.setTextColor(color);
                this.binding.firstPage.tvWordNum.setTextColor(getContext().getColor(R.color.color_606060));
                TextView textView = this.binding.firstPage.tvGotoComment;
                Context context2 = getContext();
                int i3 = R.color.color_575F79;
                textView.setTextColor(context2.getColor(i3));
                this.binding.firstPage.tvBookComment.setTextColor(getContext().getColor(i3));
                this.binding.firstPage.tvRead.setTextColor(getContext().getColor(i3));
                this.binding.firstPage.tvBeginRead.getHelper().setBackgroundColorNormal(getContext().getColor(R.color.color_B2575C));
                this.binding.firstPage.tvBeginRead.getHelper().setTextColorNormal(getContext().getColor(R.color.color_121622));
                this.binding.firstPage.tvAddBook.getHelper().setBackgroundColorNormal(getContext().getColor(R.color.transparent));
                this.binding.firstPage.tvAddBook.getHelper().setTextColorNormal(getContext().getColor(i2));
                RTextViewHelper helper = this.binding.firstPage.tvAddBook.getHelper();
                Context context3 = getContext();
                int i4 = R.color.color_1C2134;
                helper.setBorderColorNormal(context3.getColor(i4));
                this.binding.firstPage.line1.setBackground(new ColorDrawable(getContext().getColor(i4)));
                this.binding.firstPage.line2.setBackground(new ColorDrawable(getContext().getColor(i4)));
            } else {
                Drawable drawable4 = getContext().getDrawable(R.drawable.icon_more_grey);
                if (drawable4 != null) {
                    drawable4.setColorFilter(null);
                }
                this.binding.firstPage.ivBookReviewCount.setImageDrawable(drawable4);
                this.binding.firstPage.ivBookCommentCount.setImageDrawable(drawable4);
                this.binding.firstPage.tvAddBook.getHelper().setTextColorNormal(getContext().getColor(R.color.color_141516));
                if (readConfig.getColorMode() == 1) {
                    View view = this.binding.firstPage.line1;
                    Context context4 = getContext();
                    int i5 = R.color.color_DFCDA2;
                    view.setBackground(new ColorDrawable(context4.getColor(i5)));
                    this.binding.firstPage.line2.setBackground(new ColorDrawable(getContext().getColor(i5)));
                    this.binding.firstPage.tvAddBook.getHelper().setBorderColorNormal(getContext().getColor(i5));
                    this.binding.firstPage.tvAddBook.getHelper().setBackgroundColorNormal(getContext().getColor(R.color.transparent));
                } else {
                    View view2 = this.binding.firstPage.line1;
                    Context context5 = getContext();
                    int i6 = R.color.color_eeeeee;
                    view2.setBackground(new ColorDrawable(context5.getColor(i6)));
                    this.binding.firstPage.line2.setBackground(new ColorDrawable(getContext().getColor(i6)));
                    this.binding.firstPage.tvAddBook.getHelper().setBackgroundColorNormal(getContext().getColor(R.color.white));
                    this.binding.firstPage.tvAddBook.getHelper().setBorderColorNormal(getContext().getColor(i6));
                }
                Drawable drawable5 = ContextCompat.getDrawable(getContext(), R.mipmap.ic_start_read);
                Drawable drawable6 = ContextCompat.getDrawable(getContext(), R.drawable.icon_black_back);
                Context context6 = getContext();
                int i7 = R.color.color_303030;
                int color2 = context6.getColor(i7);
                if (drawable6 != null) {
                    drawable6.setColorFilter(new PorterDuffColorFilter(getContext().getColor(i7), PorterDuff.Mode.SRC_IN));
                }
                if (drawable5 != null) {
                    drawable5.setColorFilter(new PorterDuffColorFilter(getContext().getColor(i7), PorterDuff.Mode.SRC_IN));
                }
                this.binding.firstPage.tvBeginRead.getHelper().setBackgroundColorNormal(getContext().getColor(R.color.color_F95843));
                this.binding.firstPage.tvBeginRead.getHelper().setTextColorNormal(getContext().getColor(R.color.white));
                this.binding.firstPage.imgBack.setImageDrawable(drawable6);
                this.binding.firstPage.tvTitle.setTextColor(color2);
                this.binding.firstPage.tvReadNum.setTextColor(color2);
                this.binding.firstPage.tvCommentNum.setTextColor(color2);
                this.binding.firstPage.tvSimpleIntroduce.setTextColor(color2);
                this.binding.firstPage.tvReviewNum.setTextColor(color2);
                this.binding.firstPage.tvBookInfo.setTextColor(color2);
                this.binding.firstPage.tvWordNum.setTextColor(getContext().getColor(i7));
                TextView textView2 = this.binding.firstPage.tvGotoComment;
                Context context7 = getContext();
                int i8 = R.color.color_909090;
                textView2.setTextColor(context7.getColor(i8));
                this.binding.firstPage.tvBookComment.setTextColor(getContext().getColor(i8));
                this.binding.firstPage.tvRead.setTextColor(getContext().getColor(i8));
            }
            ImageLoader imageLoader = ImageLoader.INSTANCE;
            Context context8 = getContext();
            Intrinsics.checkNotNullExpressionValue(context8, "context");
            imageLoader.load(context8, bookInfo.getThumbnail()).optionalCenterCrop().into(this.binding.firstPage.imgCover);
            this.binding.firstPage.tvTitle.setText(bookInfo.getTitle());
            this.binding.firstPage.tvDesc.setText(bookInfo.getAuthor());
            if (bookInfo.getCommentCount() > 10000) {
                this.binding.firstPage.tvGotoComment.setText((bookInfo.getCommentCount() / 10000) + "人点评");
            } else {
                TextView textView3 = this.binding.firstPage.tvGotoComment;
                if (bookInfo.getCommentCount() > 0) {
                    str = bookInfo.getCommentCount() + "人点评";
                } else {
                    str = "快去评价";
                }
                textView3.setText(str);
            }
            this.binding.firstPage.tvWordNum.setText(" · " + bookInfo.getWordCount() + (char) 23383);
            String tenThousand = NumberUtilKt.toTenThousand(bookInfo.getBookReviewCount());
            if (bookInfo.getBookReviewCount() == 0) {
                str2 = "暂无";
            } else {
                str2 = tenThousand + (char) 26465;
            }
            this.binding.firstPage.tvReviewNum.setText(str2);
            if (bookInfo.getBookReviewCount() > 0) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tenThousand);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030)), 0, spannableStringBuilder.length(), 18);
                spannableStringBuilder.setSpan(new StyleSpan(1), 0, spannableStringBuilder.length(), 18);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(18, true), 0, spannableStringBuilder.length(), 18);
                spannableStringBuilder.append((CharSequence) "条");
                spannableStringBuilder.setSpan(new ForegroundColorSpan(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030)), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 18);
                spannableStringBuilder.setSpan(new StyleSpan(0), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 18);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(12, true), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 18);
                this.binding.firstPage.tvReviewNum.setText(spannableStringBuilder);
            }
            if (new Regex(RegexPool.NUMBERS).matches(bookInfo.getReadCount())) {
                int i9 = Integer.parseInt(bookInfo.getReadCount());
                int i10 = i9 > 10000 ? 2 : 1;
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(bookInfo.getReadCount());
                spannableStringBuilder2.setSpan(new ForegroundColorSpan(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030)), 0, spannableStringBuilder2.length(), 18);
                spannableStringBuilder2.setSpan(new StyleSpan(1), 0, spannableStringBuilder2.length(), 18);
                spannableStringBuilder2.setSpan(new AbsoluteSizeSpan(18, true), 0, spannableStringBuilder2.length(), 18);
                spannableStringBuilder2.append((CharSequence) (i9 > 10000 ? "万人" : "人"));
                spannableStringBuilder2.setSpan(new ForegroundColorSpan(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030)), spannableStringBuilder2.length() - 1, spannableStringBuilder2.length(), 18);
                spannableStringBuilder2.setSpan(new StyleSpan(0), spannableStringBuilder2.length() - i10, spannableStringBuilder2.length(), 18);
                spannableStringBuilder2.setSpan(new AbsoluteSizeSpan(12, true), spannableStringBuilder2.length() - i10, spannableStringBuilder2.length(), 18);
                this.binding.firstPage.tvReadNum.setText(spannableStringBuilder2);
            } else {
                this.binding.firstPage.tvReadNum.setText("暂无");
            }
            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(String.valueOf(bookInfo.getGrade()));
            spannableStringBuilder3.setSpan(new ForegroundColorSpan(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030)), 0, spannableStringBuilder3.length(), 18);
            spannableStringBuilder3.setSpan(new StyleSpan(1), 0, spannableStringBuilder3.length(), 18);
            spannableStringBuilder3.setSpan(new AbsoluteSizeSpan(18, true), 0, spannableStringBuilder3.length(), 18);
            spannableStringBuilder3.append((CharSequence) "分");
            spannableStringBuilder3.setSpan(new ForegroundColorSpan(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030)), spannableStringBuilder3.length() - 1, spannableStringBuilder3.length(), 18);
            spannableStringBuilder3.setSpan(new StyleSpan(0), spannableStringBuilder3.length() - 1, spannableStringBuilder3.length(), 18);
            spannableStringBuilder3.setSpan(new AbsoluteSizeSpan(12, true), spannableStringBuilder3.length() - 1, spannableStringBuilder3.length(), 18);
            this.binding.firstPage.tvCommentNum.setText(Intrinsics.areEqual(bookInfo.getGrade(), "0") ? "暂无" : spannableStringBuilder3);
            this.binding.firstPage.tvBookInfo.post(new Runnable() { // from class: com.ykb.ebook.weight.s
                @Override // java.lang.Runnable
                public final void run() {
                    PageView.setFirstPageContent$lambda$23$lambda$22(this.f26520c, bookInfo);
                }
            });
            RTextView rTextView = this.binding.firstPage.tvBeginRead;
            Intrinsics.checkNotNullExpressionValue(rTextView, "binding.firstPage.tvBeginRead");
            ViewExtensionsKt.visible(rTextView);
            ReadBook.INSTANCE.setBookInfoViewData();
        }
    }

    public final void setIsScroll(boolean value) {
        this.isScroll = value;
        this.binding.contentTextView.setIsScroll(value);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0363  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x03cb  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x03d2  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x03eb  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x03f6  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x040f  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x041a  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x043e  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x045b  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0462  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0482  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x049b  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x04a2  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x04bb  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x04c2  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x04db  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x04e2  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x04fb  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0502  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x051b  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0526  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0543  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0567  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x056e  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x058b  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0592  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x05af  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x05b6  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x05d3  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x05da  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x05f7  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x05fe  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x061b  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0622  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x063f  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0646  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0663  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x066a  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0687  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x068e  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x06ab  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x06b2  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x06cf  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x06d6  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x06e9  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0701  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x071e  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0765  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x078e  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0791  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x07af  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x07b2  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x07d0  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x07d3  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0823  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x083a  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x085c  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x085f  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x08a8  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x08aa  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x08ad  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x08c5  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x08c7  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x08ca  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x08e2  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x08e4  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x08e8  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0902  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0904  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0914  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x0916  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0927  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02b3  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x031b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setLastPageContent() throws java.lang.NumberFormatException, java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 2364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.weight.PageView.setLastPageContent():void");
    }

    public final void setLibraryState() {
        BookInfo bookInfo = this.bookInfo;
        if (bookInfo != null) {
            if (Intrinsics.areEqual(bookInfo.isBookshelf(), "1")) {
                this.binding.firstPage.tvAddBook.setText("移出书架");
            } else {
                this.binding.firstPage.tvAddBook.setText("加入书架");
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint({"SetTextI18n"})
    @NotNull
    public final TextPage setProgress(@NotNull TextPage textPage) {
        String strReplace$default;
        Context context;
        int i2;
        String title;
        List<Chapter> chapterList;
        Intrinsics.checkNotNullParameter(textPage, "textPage");
        ReadBook readBook = ReadBook.INSTANCE;
        int durChapterIndex = readBook.getDurChapterIndex();
        int durPageIndex = readBook.getDurPageIndex();
        BookInfo book = readBook.getBook();
        Chapter chapter = null;
        if (book != null && (chapterList = book.getChapterList()) != null) {
            Iterator<T> it = chapterList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (((Chapter) next).getIndex() == durChapterIndex) {
                    chapter = next;
                    break;
                }
            }
            chapter = chapter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append((char) 31532);
        ReadBook readBook2 = ReadBook.INSTANCE;
        sb.append(readBook2.getDurChapterIndex() + 1);
        sb.append((char) 31456);
        String string = sb.toString();
        TextView textView = this.binding.tvTitle;
        Intrinsics.checkNotNullExpressionValue(textView, "binding.tvTitle");
        if (chapter == null || (title = chapter.getTitle()) == null || (strReplace$default = StringsKt__StringsJVMKt.replace$default(title, string, "", false, 4, (Object) null)) == null) {
            strReplace$default = StringsKt__StringsJVMKt.replace$default(textPage.getTitle(), string, "", false, 4, (Object) null);
        }
        ViewExtensionsKt.setTextIfNotEqual(textView, String.valueOf(strReplace$default));
        if (textPage.textChapter.getChapter().isPay()) {
            this.binding.tvProgress.setVisibility(0);
            BatteryView batteryView = this.binding.tvProgress;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(durPageIndex + 1);
            sb2.append('/');
            sb2.append(readBook2.getCurrentChapterTotalPages());
            batteryView.setText(sb2.toString());
        } else {
            this.binding.tvProgress.setVisibility(8);
        }
        BatteryView batteryView2 = this.binding.tvProgress;
        if (ReadConfig.INSTANCE.getColorMode() == 2) {
            context = getContext();
            i2 = R.color.color_575F79;
        } else {
            context = getContext();
            i2 = R.color.color_909090;
        }
        batteryView2.setTextColor(context.getColor(i2));
        return textPage;
    }

    public final void showFirstPage() throws SecurityException, NumberFormatException {
        this.firstChapterFirstPage = true;
        this.lastChapterLastPage = false;
        if (this.bookInfo != null) {
            ConstraintLayout constraintLayout = this.binding.vwRoot;
            Intrinsics.checkNotNullExpressionValue(constraintLayout, "binding.vwRoot");
            ViewExtensionsKt.gone(constraintLayout);
            FrameLayout frameLayout = this.binding.endPage.llEndPage;
            Intrinsics.checkNotNullExpressionValue(frameLayout, "binding.endPage.llEndPage");
            ViewExtensionsKt.gone(frameLayout);
            LinearLayout linearLayout = this.binding.firstPage.llStartPage;
            Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.firstPage.llStartPage");
            ViewExtensionsKt.visible(linearLayout);
            ReadConfig readConfig = ReadConfig.INSTANCE;
            readConfig.setLastPage(false);
            readConfig.setFirstPage(true);
            if (ReadBook.INSTANCE.hasSetBookInfoViewData()) {
                return;
            }
            Log.INSTANCE.logD("showFirstPage", "UI==设置扉页数据");
            setFirstPageContent();
        }
    }

    public final void showLastPage() {
        String id;
        ReadConfig readConfig = ReadConfig.INSTANCE;
        readConfig.setLastPage(true);
        readConfig.setFirstPage(false);
        this.firstChapterFirstPage = false;
        this.lastChapterLastPage = true;
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book != null && (id = book.getId()) != null) {
            this.viewModel.bookInfo(id);
        }
        ConstraintLayout constraintLayout = this.binding.vwRoot;
        Intrinsics.checkNotNullExpressionValue(constraintLayout, "binding.vwRoot");
        ViewExtensionsKt.gone(constraintLayout);
        FrameLayout frameLayout = this.binding.endPage.llEndPage;
        Intrinsics.checkNotNullExpressionValue(frameLayout, "binding.endPage.llEndPage");
        ViewExtensionsKt.visible(frameLayout);
        LinearLayout linearLayout = this.binding.firstPage.llStartPage;
        Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.firstPage.llStartPage");
        ViewExtensionsKt.gone(linearLayout);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showNormalPage(boolean r19) {
        /*
            Method dump skipped, instructions count: 280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.weight.PageView.showNormalPage(boolean):void");
    }

    public final void submitRenderTask() {
        this.binding.contentTextView.submitRenderTask();
    }

    @SuppressLint({"SetTextI18n"})
    public final void upBattery(float battery) {
        this.battery = battery;
        this.binding.batteryInfo.setCurrentBatteryLevel(battery / 100.0f);
    }

    @SuppressLint({"UseCompatLoadingForDrawables"})
    public final void upBg(boolean refresh) throws SecurityException, NumberFormatException {
        if (refresh) {
            submitRenderTask();
            getTextPage().invalidateAll();
            getTextPage().getCanvasRecorder().invalidate();
            setFirstPageContent();
            setLastPageContent();
        }
        View view = this.binding.vBg;
        Context context = getContext();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int colorMode = readConfig.getColorMode();
        view.setBackground(ContextCompat.getDrawable(context, colorMode != 0 ? colorMode != 1 ? R.drawable.shape_translate_deep_bg : R.drawable.shape_translate_yellow_bg : R.drawable.shape_translate_bg));
        RecyclerView recyclerView = this.binding.rvBottom;
        int colorMode2 = readConfig.getColorMode();
        recyclerView.setBackground(new ColorDrawable(colorMode2 != 0 ? colorMode2 != 1 ? StringExtensionsKt.hexValue2IntColor("#0D111D") : StringExtensionsKt.hexValue2IntColor("#EDDDB7") : StringExtensionsKt.hexValue2IntColor("#F7F8FA")));
        this.adapter.notifyDataSetChanged();
        int colorMode3 = readConfig.getColorMode();
        Drawable drawable = colorMode3 != 0 ? colorMode3 != 1 ? AppCtxKt.getAppCtx().getDrawable(R.color.color_0d111d) : AppCtxKt.getAppCtx().getDrawable(R.drawable.bg_content_view) : AppCtxKt.getAppCtx().getDrawable(R.color.color_f7f8fa);
        this.binding.vwRoot.setBackground(drawable);
        this.binding.vwBg.setBackground(drawable);
        this.binding.tvTitle.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        this.binding.batteryInfo.invalidate();
        this.binding.tvTime.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        this.binding.tvProgress.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        upBgAlpha();
    }

    public final void upStatusBar() {
        FrameLayout upStatusBar$lambda$8 = this.binding.vwStatusBar;
        int paddingLeft = upStatusBar$lambda$8.getPaddingLeft();
        Context context = upStatusBar$lambda$8.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        upStatusBar$lambda$8.setPadding(paddingLeft, ContextExtensionsKt.getStatusBarHeight(context), upStatusBar$lambda$8.getPaddingRight(), upStatusBar$lambda$8.getPaddingBottom());
        Intrinsics.checkNotNullExpressionValue(upStatusBar$lambda$8, "upStatusBar$lambda$8");
        boolean z2 = true;
        if (!ReadConfig.INSTANCE.getHideStatusBar()) {
            ReadBookActivity readBookActivity = getReadBookActivity();
            if (!(readBookActivity != null && readBookActivity.isInMultiWindow())) {
                z2 = false;
            }
        }
        upStatusBar$lambda$8.setVisibility(z2 ? 8 : 0);
    }

    public final void upTime() {
        this.binding.tvTime.setText(ConstantKt.getTimeFormat().format(new Date(System.currentTimeMillis())));
        this.binding.tvTime.setTextColor(getContext().getColor(ReadConfig.INSTANCE.getColorMode() != 2 ? R.color.color_909090 : R.color.color_575F79));
    }

    public final void upTimeShow() {
        if (ReadConfig.INSTANCE.getShowTime()) {
            BatteryView batteryView = this.binding.tvTime;
            Intrinsics.checkNotNullExpressionValue(batteryView, "binding.tvTime");
            ViewExtensionsKt.visible(batteryView);
            BatteryWidget batteryWidget = this.binding.batteryInfo;
            Intrinsics.checkNotNullExpressionValue(batteryWidget, "binding.batteryInfo");
            ViewExtensionsKt.visible(batteryWidget);
            return;
        }
        BatteryView batteryView2 = this.binding.tvTime;
        Intrinsics.checkNotNullExpressionValue(batteryView2, "binding.tvTime");
        ViewExtensionsKt.invisible(batteryView2);
        BatteryWidget batteryWidget2 = this.binding.batteryInfo;
        Intrinsics.checkNotNullExpressionValue(batteryWidget2, "binding.batteryInfo");
        ViewExtensionsKt.invisible(batteryWidget2);
    }

    public final void updateSelectColumns(@NotNull ArrayList<Integer> charIndex) {
        Intrinsics.checkNotNullParameter(charIndex, "charIndex");
        this.binding.contentTextView.setLongPressParagraphChars(charIndex);
    }

    public final void updateStartEndTextPosition(@NotNull TextPosition start, @NotNull TextPosition end) {
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(end, "end");
    }

    public final void updateTitle() {
        TextChapter textChapter;
        TextView textView = this.binding.tvTitle;
        TextPage textPage = this.curTextPage;
        textView.setText((textPage == null || (textChapter = textPage.textChapter) == null) ? null : textChapter.getTitle());
    }

    public final void selectEndMoveIndex(@NotNull TextPosition textPos) {
        Intrinsics.checkNotNullParameter(textPos, "textPos");
        this.binding.contentTextView.selectEndMoveIndex(textPos);
    }

    public final void selectStartMoveIndex(@NotNull TextPosition textPos) {
        Intrinsics.checkNotNullParameter(textPos, "textPos");
        this.binding.contentTextView.selectStartMoveIndex(textPos);
    }
}
