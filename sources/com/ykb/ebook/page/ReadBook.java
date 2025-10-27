package com.ykb.ebook.page;

import android.content.AppCtxKt;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.ArrayMap;
import cn.hutool.core.lang.RegexPool;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.api.ApiServiceKt;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.LayoutProgressListener;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.model.ChapterInfo;
import com.ykb.ebook.model.TextChapter;
import com.ykb.ebook.model.TextPage;
import com.ykb.ebook.model.Ways;
import com.ykb.ebook.page.ContentProcessor;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.ExecutorServiceKt;
import com.ykb.ebook.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobKt__JobKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b'\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u008f\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010U\u001a\u00020\u00162\u0006\u0010V\u001a\u00020\u0010H\u0002J\b\u0010W\u001a\u00020XH\u0002J<\u0010\u0015\u001a\u00020X2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u0002082\b\b\u0002\u0010\\\u001a\u00020\u00162\u0006\u0010]\u001a\u00020\u00162\u0010\b\u0002\u0010^\u001a\n\u0012\u0004\u0012\u00020X\u0018\u00010_H\u0002J\b\u0010`\u001a\u00020XH\u0002J\u0006\u0010a\u001a\u00020\u0010J\u0006\u0010b\u001a\u00020\u0010J\u0010\u0010c\u001a\u0004\u0018\u00010C2\u0006\u0010d\u001a\u000208J\u000e\u0010e\u001a\u00020\u00162\u0006\u0010f\u001a\u00020\u0010J\u0006\u0010g\u001a\u00020\u0016J&\u0010h\u001a\u00020X2\u0006\u0010V\u001a\u00020\u00102\u0016\b\u0002\u0010^\u001a\u0010\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020X\u0018\u00010iJ*\u0010j\u001a\u00020X2\u0006\u0010]\u001a\u00020\u00162\b\b\u0002\u0010k\u001a\u00020\u00162\u0010\b\u0002\u0010^\u001a\n\u0012\u0004\u0012\u00020X\u0018\u00010_J6\u0010j\u001a\u00020X2\u0006\u0010V\u001a\u00020\u00102\b\b\u0002\u0010\\\u001a\u00020\u00162\b\b\u0002\u0010]\u001a\u00020\u00162\u0010\b\u0002\u0010^\u001a\n\u0012\u0004\u0012\u00020X\u0018\u00010_H\u0002J\u0006\u0010l\u001a\u00020XJ\u0018\u0010m\u001a\u00020\u00162\u0006\u0010\\\u001a\u00020\u00162\b\b\u0002\u0010n\u001a\u00020\u0016J\"\u0010o\u001a\u00020\u00162\u0006\u0010\\\u001a\u00020\u00162\b\b\u0002\u0010p\u001a\u00020\u00162\b\b\u0002\u0010n\u001a\u00020\u0016J*\u0010q\u001a\u00020X2\u0006\u0010V\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020\u00102\u0010\b\u0002\u0010^\u001a\n\u0012\u0004\u0012\u00020X\u0018\u00010_J*\u0010r\u001a\u00020X2\u0006\u0010V\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020\u00102\u0010\b\u0002\u0010^\u001a\n\u0012\u0004\u0012\u00020X\u0018\u00010_J\u0006\u0010s\u001a\u00020\u0010J\u0018\u0010t\u001a\u00020X2\u0006\u0010u\u001a\u00020\u00102\u0006\u0010v\u001a\u00020\u0010H\u0002J\u000e\u0010w\u001a\u00020X2\u0006\u0010x\u001a\u00020\nJ\u0010\u0010y\u001a\u00020X2\u0006\u0010V\u001a\u00020\u0010H\u0002J\u0006\u0010z\u001a\u00020XJ\u0006\u0010{\u001a\u00020XJ\u0006\u0010|\u001a\u00020XJ\u0016\u0010}\u001a\u00020X2\u0006\u0010~\u001a\u00020C2\u0006\u0010d\u001a\u000208J\u0013\u0010\u007f\u001a\u00020X2\t\b\u0002\u0010\u0080\u0001\u001a\u00020\u0016H\u0002J\u0007\u0010\u0081\u0001\u001a\u00020XJ\u001b\u0010\u0082\u0001\u001a\u00020X2\u0007\u0010\u0083\u0001\u001a\u00020\u00102\t\b\u0002\u0010\u0084\u0001\u001a\u00020\u0010J\u000f\u0010\u0085\u0001\u001a\u00020X2\u0006\u0010V\u001a\u00020\u0010J!\u0010\u0086\u0001\u001a\u00020X2\u0006\u0010V\u001a\u00020\u00102\u0010\b\u0002\u0010^\u001a\n\u0012\u0004\u0012\u00020X\u0018\u00010_J\u0014\u0010\u0087\u0001\u001a\u0004\u0018\u00010 2\t\b\u0002\u0010\u0088\u0001\u001a\u00020\u0010J\u000f\u0010\u0089\u0001\u001a\u00020X2\u0006\u0010x\u001a\u00020\nJ\u001c\u0010\u008a\u0001\u001a\u00020X2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\t\b\u0002\u0010\u008b\u0001\u001a\u00020\u0016J\u0011\u0010\u008c\u0001\u001a\u00020X2\b\u00107\u001a\u0004\u0018\u000108J\t\u0010\u008d\u0001\u001a\u00020XH\u0002J\u0007\u0010\u008e\u0001\u001a\u00020XR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0012\u0010\u001b\u001a\u00020\u001cX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0012\"\u0004\b'\u0010\u0014R\u001a\u0010(\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0012\"\u0004\b*\u0010\u0014R\u0011\u0010+\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b,\u0010\u0012R\u0011\u0010-\u001a\u00020.¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u001a\u00101\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0018\"\u0004\b2\u0010\u001aR\u0015\u00103\u001a\u00020\u00168Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b3\u0010\u0018R\u001e\u00104\u001a\u0012\u0012\u0004\u0012\u00020\u001005j\b\u0012\u0004\u0012\u00020\u0010`6X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u00107\u001a\u0004\u0018\u000108X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u0010\u0010=\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010>\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u0018\"\u0004\b@\u0010\u001aR*\u0010A\u001a\u001e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020C0Bj\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020C`DX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010E\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100FX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010G\u001a\b\u0012\u0004\u0012\u00020I0HX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR\u0010\u0010N\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010O\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00160FX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010P\u001a\n Q*\u0004\u0018\u00010808X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010R\u001a\u000208X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010:\"\u0004\bT\u0010<¨\u0006\u0090\u0001"}, d2 = {"Lcom/ykb/ebook/page/ReadBook;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "book", "Lcom/ykb/ebook/model/BookInfo;", "getBook", "()Lcom/ykb/ebook/model/BookInfo;", "setBook", "(Lcom/ykb/ebook/model/BookInfo;)V", "callBack", "Lcom/ykb/ebook/page/ReadBook$CallBack;", "getCallBack", "()Lcom/ykb/ebook/page/ReadBook$CallBack;", "setCallBack", "(Lcom/ykb/ebook/page/ReadBook$CallBack;)V", "chapterSize", "", "getChapterSize", "()I", "setChapterSize", "(I)V", "contentLoadFinish", "", "getContentLoadFinish", "()Z", "setContentLoadFinish", "(Z)V", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "curTextChapter", "Lcom/ykb/ebook/model/TextChapter;", "getCurTextChapter", "()Lcom/ykb/ebook/model/TextChapter;", "setCurTextChapter", "(Lcom/ykb/ebook/model/TextChapter;)V", "durChapterIndex", "getDurChapterIndex", "setDurChapterIndex", "durChapterPos", "getDurChapterPos", "setDurChapterPos", "durPageIndex", "getDurPageIndex", "executor", "Ljava/util/concurrent/ExecutorService;", "getExecutor", "()Ljava/util/concurrent/ExecutorService;", "isLoadContent", "setLoadContent", "isScroll", "loadingChapters", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "msg", "", "getMsg", "()Ljava/lang/String;", "setMsg", "(Ljava/lang/String;)V", "nextTextChapter", "openChapterPageFromSearch", "getOpenChapterPageFromSearch", "setOpenChapterPageFromSearch", "pageBitMapMap", "Ljava/util/HashMap;", "Landroid/graphics/Bitmap;", "Lkotlin/collections/HashMap;", "pageMap", "Landroid/util/ArrayMap;", "payWays", "", "Lcom/ykb/ebook/model/Ways;", "getPayWays", "()Ljava/util/List;", "setPayWays", "(Ljava/util/List;)V", "prevTextChapter", "setBookInfoFlagMap", "tag", "kotlin.jvm.PlatformType", "userId", "getUserId", "setUserId", "addLoading", "index", "clearTextChapter", "", "chapter", "Lcom/ykb/ebook/model/ChapterInfo;", "content", "upContent", "resetPageOffset", "success", "Lkotlin/Function0;", "curPageChanged", "getCurrentChapterTotalPages", "getDurChapterPo", "getPageBitMap", "key", "getPreChapterIsPay", "curChapterSort", "hasSetBookInfoViewData", "loadChapterContentByIndex", "Lkotlin/Function1;", "loadContent", "preLoad", "loadOrUpContent", "moveToNextChapter", "upContentInPlace", "moveToPrevChapter", "toLast", "openChapter", "openChapterFromSearch", "pageAnim", "recycleRecorders", "beforeIndex", "afterIndex", MiPushClient.COMMAND_REGISTER, "cb", "removeLoading", "resetBookInfoDataSet", "resetPageBitMap", "resetPages", "savePageBitMap", "bitmap", "saveRead", "pageChanged", "setBookInfoViewData", "setChapterPage", "chapterId", "pageCount", "setPageIndex", "skipToPage", "textChapter", "chapterOnDur", MiPushClient.COMMAND_UNREGISTER, "upData", "reset", "upMsg", "upReadTime", "uploadProgress", "CallBack", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nReadBook.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReadBook.kt\ncom/ykb/ebook/page/ReadBook\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,716:1\n1#2:717\n215#3,2:718\n1855#4,2:720\n1855#4,2:722\n1855#4,2:724\n*S KotlinDebug\n*F\n+ 1 ReadBook.kt\ncom/ykb/ebook/page/ReadBook\n*L\n102#1:718,2\n365#1:720,2\n461#1:722,2\n687#1:724,2\n*E\n"})
/* loaded from: classes7.dex */
public final class ReadBook implements CoroutineScope {

    @NotNull
    public static final ReadBook INSTANCE;

    @Nullable
    private static BookInfo book;

    @Nullable
    private static CallBack callBack;
    private static int chapterSize;
    private static boolean contentLoadFinish;

    @Nullable
    private static TextChapter curTextChapter;
    private static int durChapterIndex;
    private static int durChapterPos;

    @NotNull
    private static final ExecutorService executor;
    private static boolean isLoadContent;

    @NotNull
    private static final ArrayList<Integer> loadingChapters;

    @Nullable
    private static String msg;

    @Nullable
    private static TextChapter nextTextChapter;
    private static boolean openChapterPageFromSearch;

    @NotNull
    private static final HashMap<String, Bitmap> pageBitMapMap;

    @NotNull
    private static final ArrayMap<Integer, Integer> pageMap;

    @NotNull
    private static List<Ways> payWays;

    @Nullable
    private static TextChapter prevTextChapter;

    @NotNull
    private static final ArrayMap<Integer, Boolean> setBookInfoFlagMap;
    private static final String tag;

    @NotNull
    private static String userId;
    private final /* synthetic */ CoroutineScope $$delegate_0 = CoroutineScopeKt.MainScope();

    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0012\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0003H&J\b\u0010\b\u001a\u00020\u0003H&J.\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00062\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000eH&J\b\u0010\u000f\u001a\u00020\u0003H&J\b\u0010\u0010\u001a\u00020\u0003H&¨\u0006\u0011"}, d2 = {"Lcom/ykb/ebook/page/ReadBook$CallBack;", "Lcom/ykb/ebook/common_interface/LayoutProgressListener;", "contentLoadFinish", "", "loadingImage", "isLoadingImage", "", "notifyBookChanged", "pageChanged", "upContent", "relativePosition", "", "resetPageOffset", "success", "Lkotlin/Function0;", "upMenuView", "upPageAnim", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface CallBack extends LayoutProgressListener {

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public static final class DefaultImpls {
            public static /* synthetic */ void loadingImage$default(CallBack callBack, boolean z2, int i2, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: loadingImage");
                }
                if ((i2 & 1) != 0) {
                    z2 = false;
                }
                callBack.loadingImage(z2);
            }

            public static void onLayoutCompleted(@NotNull CallBack callBack) {
                LayoutProgressListener.DefaultImpls.onLayoutCompleted(callBack);
            }

            public static void onLayoutException(@NotNull CallBack callBack, @NotNull Throwable e2) {
                Intrinsics.checkNotNullParameter(e2, "e");
                LayoutProgressListener.DefaultImpls.onLayoutException(callBack, e2);
            }

            public static void onLayoutPageCompleted(@NotNull CallBack callBack, int i2, @NotNull TextPage page) {
                Intrinsics.checkNotNullParameter(page, "page");
                LayoutProgressListener.DefaultImpls.onLayoutPageCompleted(callBack, i2, page);
            }

            /* JADX WARN: Multi-variable type inference failed */
            public static /* synthetic */ void upContent$default(CallBack callBack, int i2, boolean z2, Function0 function0, int i3, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: upContent");
                }
                if ((i3 & 1) != 0) {
                    i2 = 0;
                }
                if ((i3 & 2) != 0) {
                    z2 = true;
                }
                if ((i3 & 4) != 0) {
                    function0 = null;
                }
                callBack.upContent(i2, z2, function0);
            }
        }

        void contentLoadFinish();

        void loadingImage(boolean isLoadingImage);

        void notifyBookChanged();

        void pageChanged();

        void upContent(int relativePosition, boolean resetPageOffset, @Nullable Function0<Unit> success);

        void upMenuView();

        void upPageAnim();
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.ReadBook$contentLoadFinish$1", f = "ReadBook.kt", i = {0, 0, 1, 2}, l = {R2.attr.bl_checkable_gradient_centerY, R2.attr.bl_corners_bottomLeftRadius, R2.attr.bl_corners_topRadius}, m = "invokeSuspend", n = {"offset", "available", "offset", "offset"}, s = {"I$0", "I$1", "I$0", "I$0"})
    @SourceDebugExtension({"SMAP\nReadBook.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReadBook.kt\ncom/ykb/ebook/page/ReadBook$contentLoadFinish$1\n+ 2 ReadBook.kt\ncom/ykb/ebook/page/ReadBook\n*L\n1#1,716:1\n52#2:717\n*S KotlinDebug\n*F\n+ 1 ReadBook.kt\ncom/ykb/ebook/page/ReadBook$contentLoadFinish$1\n*L\n534#1:717\n*E\n"})
    /* renamed from: com.ykb.ebook.page.ReadBook$contentLoadFinish$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ChapterInfo $chapter;
        final /* synthetic */ com.ykb.ebook.model.BookContent $contents;
        final /* synthetic */ int $index;
        final /* synthetic */ boolean $resetPageOffset;
        final /* synthetic */ boolean $upContent;
        int I$0;
        int I$1;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ChapterInfo chapterInfo, com.ykb.ebook.model.BookContent bookContent, int i2, boolean z2, boolean z3, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$chapter = chapterInfo;
            this.$contents = bookContent;
            this.$index = i2;
            this.$upContent = z2;
            this.$resetPageOffset = z3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ReadBook.this.new AnonymousClass1(this.$chapter, this.$contents, this.$index, this.$upContent, this.$resetPageOffset, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Path cross not found for [B:48:0x00ff, B:57:0x0124], limit reached: 99 */
        /* JADX WARN: Removed duplicated region for block: B:23:0x008f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0098  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00ea A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:46:0x00f3  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0129  */
        /* JADX WARN: Removed duplicated region for block: B:72:0x0159  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x015f  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x008d -> B:24:0x0090). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:42:0x00e8 -> B:44:0x00eb). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r17) {
            /*
                Method dump skipped, instructions count: 455
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.page.ReadBook.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.ReadBook$contentLoadFinish$2", f = "ReadBook.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.page.ReadBook$contentLoadFinish$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            return new AnonymousClass2(continuation).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ReadBook.INSTANCE.setContentLoadFinish(true);
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0001*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.ReadBook$contentLoadFinish$3", f = "ReadBook.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.page.ReadBook$contentLoadFinish$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function3<CoroutineScope, Unit, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function0<Unit> $success;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Function0<Unit> function0, Continuation<? super AnonymousClass3> continuation) {
            super(3, continuation);
            this.$success = function0;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Unit unit, @Nullable Continuation<? super Unit> continuation) {
            return new AnonymousClass3(this.$success, continuation).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ReadBook.INSTANCE.setContentLoadFinish(true);
            Function0<Unit> function0 = this.$success;
            if (function0 != null) {
                function0.invoke();
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/ChapterInfo;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.ReadBook$loadChapterContentByIndex$2", f = "ReadBook.kt", i = {}, l = {R2.attr.barrierMargin}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.page.ReadBook$loadChapterContentByIndex$2, reason: invalid class name and case insensitive filesystem */
    public static final class C10452 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super BaseResponse<ChapterInfo>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10452(HashMap<String, String> map, Continuation<? super C10452> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C10452(this.$params, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super BaseResponse<ChapterInfo>> continuation) {
            return ((C10452) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService api = ApiServiceKt.getAPI();
                HashMap<String, String> map = this.$params;
                this.label = 1;
                obj = api.chapterInfo(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/ChapterInfo;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.ReadBook$loadChapterContentByIndex$3", f = "ReadBook.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.page.ReadBook$loadChapterContentByIndex$3, reason: invalid class name and case insensitive filesystem */
    public static final class C10463 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<ChapterInfo>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.BooleanRef $chapterUnlock;
        final /* synthetic */ int $queryIndex;
        final /* synthetic */ Function1<String, Unit> $success;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C10463(Ref.BooleanRef booleanRef, int i2, Function1<? super String, Unit> function1, Continuation<? super C10463> continuation) {
            super(3, continuation);
            this.$chapterUnlock = booleanRef;
            this.$queryIndex = i2;
            this.$success = function1;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<ChapterInfo> baseResponse, @Nullable Continuation<? super Unit> continuation) {
            C10463 c10463 = new C10463(this.$chapterUnlock, this.$queryIndex, this.$success, continuation);
            c10463.L$0 = baseResponse;
            return c10463.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            List<Chapter> chapterList;
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ChapterInfo chapterInfo = (ChapterInfo) ((BaseResponse) this.L$0).getData();
            if (chapterInfo != null) {
                Ref.BooleanRef booleanRef = this.$chapterUnlock;
                int i2 = this.$queryIndex;
                Function1<String, Unit> function1 = this.$success;
                chapterInfo.setPay(booleanRef.element);
                ReadBook readBook = ReadBook.INSTANCE;
                BookInfo book = readBook.getBook();
                if (book != null) {
                    book.getFreeChapterCount();
                }
                BookInfo book2 = readBook.getBook();
                if (i2 == ((book2 == null || (chapterList = book2.getChapterList()) == null) ? 0 : chapterList.size()) - 1) {
                    chapterInfo.setContent(chapterInfo.getContent() + "\n （全书完）");
                }
                String strAnalyzeContent = BookContent.INSTANCE.analyzeContent(chapterInfo);
                if (function1 != null) {
                    function1.invoke(strAnalyzeContent);
                }
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.ReadBook$loadChapterContentByIndex$4", f = "ReadBook.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.page.ReadBook$loadChapterContentByIndex$4, reason: invalid class name */
    public static final class AnonymousClass4 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $index;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(int i2, Continuation<? super AnonymousClass4> continuation) {
            super(3, continuation);
            this.$index = i2;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            return new AnonymousClass4(this.$index, continuation).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ReadBook.INSTANCE.removeLoading(this.$index);
            Log.INSTANCE.logE("ReadBook", "正文内容加载出错！");
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/ChapterInfo;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.ReadBook$loadContent$3", f = "ReadBook.kt", i = {}, l = {R2.attr.arcThumbColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.page.ReadBook$loadContent$3, reason: invalid class name and case insensitive filesystem */
    public static final class C10483 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super BaseResponse<ChapterInfo>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10483(HashMap<String, String> map, Continuation<? super C10483> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C10483(this.$params, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super BaseResponse<ChapterInfo>> continuation) {
            return ((C10483) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ReadBook.INSTANCE.setLoadContent(true);
                ApiService api = ApiServiceKt.getAPI();
                HashMap<String, String> map = this.$params;
                this.label = 1;
                obj = api.chapterInfo(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/ChapterInfo;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.ReadBook$loadContent$4", f = "ReadBook.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.page.ReadBook$loadContent$4, reason: invalid class name and case insensitive filesystem */
    public static final class C10494 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<ChapterInfo>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.BooleanRef $chapterUnlock;
        final /* synthetic */ int $index;
        final /* synthetic */ int $queryIndex;
        final /* synthetic */ boolean $resetPageOffset;
        final /* synthetic */ Function0<Unit> $success;
        final /* synthetic */ boolean $upContent;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10494(Ref.BooleanRef booleanRef, int i2, int i3, boolean z2, boolean z3, Function0<Unit> function0, Continuation<? super C10494> continuation) {
            super(3, continuation);
            this.$chapterUnlock = booleanRef;
            this.$queryIndex = i2;
            this.$index = i3;
            this.$upContent = z2;
            this.$resetPageOffset = z3;
            this.$success = function0;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<ChapterInfo> baseResponse, @Nullable Continuation<? super Unit> continuation) {
            C10494 c10494 = new C10494(this.$chapterUnlock, this.$queryIndex, this.$index, this.$upContent, this.$resetPageOffset, this.$success, continuation);
            c10494.L$0 = baseResponse;
            return c10494.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v4, types: [T, java.lang.String] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            CallBack callBack;
            List<Chapter> chapterList;
            Chapter chapter;
            List<Chapter> chapterList2;
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BaseResponse baseResponse = (BaseResponse) this.L$0;
            ReadBook readBook = ReadBook.INSTANCE;
            int size = 0;
            readBook.setLoadContent(false);
            ChapterInfo chapterInfo = (ChapterInfo) baseResponse.getData();
            if (chapterInfo != null) {
                Ref.BooleanRef booleanRef = this.$chapterUnlock;
                int i2 = this.$queryIndex;
                int i3 = this.$index;
                boolean z2 = this.$upContent;
                boolean z3 = this.$resetPageOffset;
                Function0<Unit> function0 = this.$success;
                chapterInfo.setPay(booleanRef.element);
                BookInfo book = readBook.getBook();
                if (book != null && (chapterList2 = book.getChapterList()) != null) {
                    size = chapterList2.size();
                }
                if (i2 == size - 1) {
                    chapterInfo.setContent(chapterInfo.getContent() + "\n （全书完）");
                }
                Ref.ObjectRef objectRef = new Ref.ObjectRef();
                objectRef.element = BookContent.INSTANCE.analyzeContent(chapterInfo);
                int size2 = chapterInfo.getImgList().size();
                BookInfo book2 = readBook.getBook();
                Boolean boolBoxBoolean = (book2 == null || (chapterList = book2.getChapterList()) == null || (chapter = chapterList.get(i3)) == null) ? null : Boxing.boxBoolean(chapter.isPay());
                if (size2 <= 0 || !Intrinsics.areEqual(boolBoxBoolean, Boxing.boxBoolean(true))) {
                    readBook.contentLoadFinish(chapterInfo, (String) objectRef.element, z2, z3, function0);
                } else {
                    if (size2 > 25 && (callBack = readBook.getCallBack()) != null) {
                        callBack.loadingImage(true);
                    }
                    Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new ReadBook$loadContent$4$1$1(size2, chapterInfo, null), 15, null), null, new ReadBook$loadContent$4$1$2(chapterInfo, objectRef, z2, z3, function0, null), 1, null), null, new ReadBook$loadContent$4$1$3(i3, null), 1, null);
                }
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.ReadBook$loadContent$5", f = "ReadBook.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.page.ReadBook$loadContent$5, reason: invalid class name */
    public static final class AnonymousClass5 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $index;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(int i2, Continuation<? super AnonymousClass5> continuation) {
            super(3, continuation);
            this.$index = i2;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            return new AnonymousClass5(this.$index, continuation).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ReadBook readBook = ReadBook.INSTANCE;
            readBook.removeLoading(this.$index);
            readBook.setLoadContent(false);
            Log.INSTANCE.logE("ReadBook", "正文内容加载出错！");
            return Unit.INSTANCE;
        }
    }

    static {
        ReadBook readBook = new ReadBook();
        INSTANCE = readBook;
        tag = readBook.getClass().getSimpleName();
        pageMap = new ArrayMap<>();
        setBookInfoFlagMap = new ArrayMap<>();
        pageBitMapMap = new HashMap<>();
        payWays = CollectionsKt__CollectionsKt.emptyList();
        userId = "583383";
        executor = ExecutorServiceKt.getGlobalExecutor();
        loadingChapters = new ArrayList<>();
        contentLoadFinish = true;
    }

    private ReadBook() {
    }

    private final boolean addLoading(int index) {
        synchronized (this) {
            ArrayList<Integer> arrayList = loadingChapters;
            if (arrayList.contains(Integer.valueOf(index))) {
                return false;
            }
            arrayList.add(Integer.valueOf(index));
            return true;
        }
    }

    private final void clearTextChapter() {
        TextChapter textChapter = prevTextChapter;
        if (textChapter != null) {
            textChapter.cancelLayout();
        }
        TextChapter textChapter2 = curTextChapter;
        if (textChapter2 != null) {
            textChapter2.cancelLayout();
        }
        TextChapter textChapter3 = nextTextChapter;
        if (textChapter3 != null) {
            textChapter3.cancelLayout();
        }
        prevTextChapter = null;
        curTextChapter = null;
        nextTextChapter = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void contentLoadFinish(ChapterInfo chapter, String content, boolean upContent, boolean resetPageOffset, Function0<Unit> success) {
        boolean z2 = false;
        contentLoadFinish = false;
        int sort = chapter.getSort() - 1;
        removeLoading(sort);
        int i2 = durChapterIndex;
        int i3 = i2 - 1;
        if (sort <= i2 + 1 && i3 <= sort) {
            z2 = true;
        }
        if (z2) {
            if (book == null) {
                removeLoading(sort);
                return;
            }
            ContentProcessor.Companion companion = ContentProcessor.INSTANCE;
            BookInfo bookInfo = book;
            Intrinsics.checkNotNull(bookInfo);
            Coroutine.onSuccess$default(Coroutine.onError$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new AnonymousClass1(chapter, ContentProcessor.getContent$default(companion.get(bookInfo), chapter, content, false, false, false, 28, null), sort, upContent, resetPageOffset, null), 15, null), null, new AnonymousClass2(null), 1, null), null, new AnonymousClass3(success, null), 1, null);
        }
    }

    public static /* synthetic */ void contentLoadFinish$default(ReadBook readBook, ChapterInfo chapterInfo, String str, boolean z2, boolean z3, Function0 function0, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        boolean z4 = z2;
        if ((i2 & 16) != 0) {
            function0 = null;
        }
        readBook.contentLoadFinish(chapterInfo, str, z4, z3, function0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void curPageChanged() {
        CallBack callBack2 = callBack;
        if (callBack2 != null) {
            callBack2.pageChanged();
        }
        upReadTime();
    }

    private final boolean isScroll() {
        return pageAnim() == 3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void loadChapterContentByIndex$default(ReadBook readBook, int i2, Function1 function1, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            function1 = null;
        }
        readBook.loadChapterContentByIndex(i2, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void loadContent$default(ReadBook readBook, boolean z2, boolean z3, Function0 function0, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z3 = true;
        }
        if ((i2 & 4) != 0) {
            function0 = null;
        }
        readBook.loadContent(z2, z3, function0);
    }

    public static /* synthetic */ boolean moveToNextChapter$default(ReadBook readBook, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z3 = true;
        }
        return readBook.moveToNextChapter(z2, z3);
    }

    public static /* synthetic */ boolean moveToPrevChapter$default(ReadBook readBook, boolean z2, boolean z3, boolean z4, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z3 = true;
        }
        if ((i2 & 4) != 0) {
            z4 = true;
        }
        return readBook.moveToPrevChapter(z2, z3, z4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void openChapter$default(ReadBook readBook, int i2, int i3, Function0 function0, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i3 = 0;
        }
        if ((i4 & 4) != 0) {
            function0 = null;
        }
        readBook.openChapter(i2, i3, function0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void openChapterFromSearch$default(ReadBook readBook, int i2, int i3, Function0 function0, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i3 = 0;
        }
        if ((i4 & 4) != 0) {
            function0 = null;
        }
        readBook.openChapterFromSearch(i2, i3, function0);
    }

    private final void recycleRecorders(final int beforeIndex, final int afterIndex) {
        executor.execute(new Runnable() { // from class: com.ykb.ebook.page.b
            @Override // java.lang.Runnable
            public final void run() {
                ReadBook.recycleRecorders$lambda$9(afterIndex, beforeIndex);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void recycleRecorders$lambda$9(int i2, int i3) {
        TextPage page;
        TextPage page2;
        TextChapter textChapter = curTextChapter;
        if (textChapter == null) {
            return;
        }
        if (i2 > i3 && (page2 = textChapter.getPage(i2 - 2)) != null) {
            page2.recycleRecorders();
        }
        if (i2 >= i3 || (page = textChapter.getPage(i2 + 3)) == null) {
            return;
        }
        page.recycleRecorders();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeLoading(int index) {
        synchronized (this) {
            loadingChapters.remove(Integer.valueOf(index));
        }
    }

    private final void saveRead(boolean pageChanged) {
    }

    public static /* synthetic */ void saveRead$default(ReadBook readBook, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        readBook.saveRead(z2);
    }

    public static /* synthetic */ void setChapterPage$default(ReadBook readBook, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i3 = 0;
        }
        readBook.setChapterPage(i2, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void skipToPage$default(ReadBook readBook, int i2, Function0 function0, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            function0 = null;
        }
        readBook.skipToPage(i2, function0);
    }

    public static /* synthetic */ TextChapter textChapter$default(ReadBook readBook, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 0;
        }
        return readBook.textChapter(i2);
    }

    public static /* synthetic */ void upData$default(ReadBook readBook, BookInfo bookInfo, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = true;
        }
        readBook.upData(bookInfo, z2);
    }

    private final void upReadTime() {
        executor.execute(new Runnable() { // from class: com.ykb.ebook.page.c
            @Override // java.lang.Runnable
            public final void run() {
                ReadBook.upReadTime$lambda$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void upReadTime$lambda$3() {
    }

    @Nullable
    public final BookInfo getBook() {
        return book;
    }

    @Nullable
    public final CallBack getCallBack() {
        return callBack;
    }

    public final int getChapterSize() {
        return chapterSize;
    }

    public final boolean getContentLoadFinish() {
        return contentLoadFinish;
    }

    @Override // kotlinx.coroutines.CoroutineScope
    @NotNull
    public CoroutineContext getCoroutineContext() {
        return this.$$delegate_0.getCoroutineContext();
    }

    @Nullable
    public final TextChapter getCurTextChapter() {
        return curTextChapter;
    }

    public final int getCurrentChapterTotalPages() throws NumberFormatException {
        List<Chapter> chapterList;
        Object next;
        Integer num;
        BookInfo bookInfo = book;
        if (bookInfo == null || (chapterList = bookInfo.getChapterList()) == null) {
            return 0;
        }
        Iterator<T> it = chapterList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((Chapter) next).getIndex() == durChapterIndex) {
                break;
            }
        }
        Chapter chapter = (Chapter) next;
        if (chapter == null) {
            return 0;
        }
        int i2 = Integer.parseInt(chapter.getId());
        Integer numValueOf = Integer.valueOf(i2);
        ArrayMap<Integer, Integer> arrayMap = pageMap;
        if (!arrayMap.containsKey(numValueOf) || (num = arrayMap.get(Integer.valueOf(i2))) == null) {
            return 0;
        }
        return num.intValue();
    }

    public final int getDurChapterIndex() {
        return durChapterIndex;
    }

    public final int getDurChapterPo() {
        return durChapterPos;
    }

    public final int getDurChapterPos() {
        return durChapterPos;
    }

    public final int getDurPageIndex() {
        TextChapter textChapter = curTextChapter;
        return textChapter != null ? textChapter.getPageIndexByCharIndex(durChapterPos) : durChapterPos;
    }

    @NotNull
    public final ExecutorService getExecutor() {
        return executor;
    }

    @Nullable
    public final String getMsg() {
        return msg;
    }

    public final boolean getOpenChapterPageFromSearch() {
        return openChapterPageFromSearch;
    }

    @Nullable
    public final Bitmap getPageBitMap(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return pageBitMapMap.get(key);
    }

    @NotNull
    public final List<Ways> getPayWays() {
        return payWays;
    }

    public final boolean getPreChapterIsPay(int curChapterSort) {
        int i2 = curChapterSort > 0 ? curChapterSort - 1 : 0;
        BookInfo bookInfo = book;
        if (bookInfo != null) {
            for (Chapter chapter : bookInfo.getChapterList()) {
                if (Integer.parseInt(chapter.getSort()) == i2) {
                    return chapter.isPay();
                }
            }
        }
        return false;
    }

    @NotNull
    public final String getUserId() {
        return userId;
    }

    public final boolean hasSetBookInfoViewData() {
        Boolean bool;
        String prefString = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "book_id", "0");
        String str = prefString != null ? prefString : "0";
        if (!new Regex(RegexPool.NUMBERS).matches(str) || (bool = setBookInfoFlagMap.get(Integer.valueOf(Integer.parseInt(str)))) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public final boolean isLoadContent() {
        return isLoadContent;
    }

    public final void loadChapterContentByIndex(int index, @Nullable Function1<? super String, Unit> success) {
        int i2;
        List<Chapter> chapterList;
        List<Chapter> chapterList2;
        Chapter chapter;
        if (index < 0 || index > (i2 = chapterSize)) {
            removeLoading(index);
            return;
        }
        int i3 = index == i2 ? i2 - 1 : index;
        BookInfo bookInfo = book;
        String id = (bookInfo == null || (chapterList2 = bookInfo.getChapterList()) == null || (chapter = chapterList2.get(i3)) == null) ? null : chapter.getId();
        if (id == null || id.length() == 0) {
            removeLoading(index);
            return;
        }
        HashMap mapHashMapOf = MapsKt__MapsKt.hashMapOf(new Pair("chapter_id", id));
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        BookInfo bookInfo2 = book;
        if (bookInfo2 != null && (chapterList = bookInfo2.getChapterList()) != null) {
            for (Chapter chapter2 : chapterList) {
                if (TextUtils.equals(id, chapter2.getId())) {
                    booleanRef.element = chapter2.isPay();
                }
            }
        }
        Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new C10452(mapHashMapOf, null), 15, null), null, new C10463(booleanRef, i3, success, null), 1, null), null, new AnonymousClass4(index, null), 1, null);
    }

    public final void loadContent(boolean resetPageOffset, boolean preLoad, @Nullable final Function0<Unit> success) {
        loadContent$default(this, durChapterIndex, false, resetPageOffset, new Function0<Unit>() { // from class: com.ykb.ebook.page.ReadBook.loadContent.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                Function0<Unit> function0 = success;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }, 2, null);
        if (preLoad) {
            loadContent$default(this, durChapterIndex + 1, false, resetPageOffset, null, 10, null);
            loadContent$default(this, durChapterIndex - 1, false, resetPageOffset, null, 10, null);
        }
    }

    public final void loadOrUpContent() {
        if (curTextChapter == null) {
            loadContent$default(this, durChapterIndex, false, false, null, 14, null);
        } else {
            CallBack callBack2 = callBack;
            if (callBack2 != null) {
                CallBack.DefaultImpls.upContent$default(callBack2, 0, false, null, 7, null);
            }
        }
        if (nextTextChapter == null) {
            loadContent$default(this, durChapterIndex + 1, false, false, null, 14, null);
        }
        if (prevTextChapter == null) {
            loadContent$default(this, durChapterIndex - 1, false, false, null, 14, null);
        }
    }

    public final boolean moveToNextChapter(boolean upContent, boolean upContentInPlace) {
        CallBack callBack2;
        int i2 = durChapterIndex;
        if (i2 >= chapterSize - 1) {
            Log log = Log.INSTANCE;
            String tag2 = tag;
            Intrinsics.checkNotNullExpressionValue(tag2, "tag");
            log.logD(tag2, "跳转下一章失败,没有下一章");
            return false;
        }
        durChapterPos = 0;
        durChapterIndex = i2 + 1;
        TextChapter textChapter = prevTextChapter;
        if (textChapter != null) {
            textChapter.cancelLayout();
        }
        prevTextChapter = curTextChapter;
        TextChapter textChapter2 = nextTextChapter;
        curTextChapter = textChapter2;
        nextTextChapter = null;
        if (textChapter2 == null) {
            Log log2 = Log.INSTANCE;
            String tag3 = tag;
            Intrinsics.checkNotNullExpressionValue(tag3, "tag");
            log2.logD(tag3, "moveToNextChapter-章节未加载,开始加载");
            if (upContentInPlace && (callBack2 = callBack) != null) {
                CallBack.DefaultImpls.upContent$default(callBack2, 0, false, null, 7, null);
            }
            loadContent$default(this, durChapterIndex, upContent, false, null, 8, null);
        } else if (upContent && upContentInPlace) {
            Log log3 = Log.INSTANCE;
            String tag4 = tag;
            Intrinsics.checkNotNullExpressionValue(tag4, "tag");
            log3.logD(tag4, "moveToNextChapter-章节已加载,刷新视图");
            CallBack callBack3 = callBack;
            if (callBack3 != null) {
                CallBack.DefaultImpls.upContent$default(callBack3, 0, false, null, 7, null);
            }
        }
        loadContent$default(this, durChapterIndex + 1, upContent, false, null, 8, null);
        saveRead$default(this, false, 1, null);
        CallBack callBack4 = callBack;
        if (callBack4 != null) {
            callBack4.upMenuView();
        }
        Log log4 = Log.INSTANCE;
        String tag5 = tag;
        Intrinsics.checkNotNullExpressionValue(tag5, "tag");
        log4.logD(tag5, "moveToNextChapter-curPageChanged() durChapterIndex =" + durChapterIndex);
        curPageChanged();
        return true;
    }

    public final boolean moveToPrevChapter(boolean upContent, boolean toLast, boolean upContentInPlace) {
        int lastReadLength;
        CallBack callBack2;
        CallBack callBack3;
        if (durChapterIndex <= 0) {
            return false;
        }
        if (toLast) {
            TextChapter textChapter = prevTextChapter;
            lastReadLength = textChapter != null ? textChapter.getLastReadLength() : Integer.MAX_VALUE;
        } else {
            lastReadLength = 0;
        }
        durChapterPos = lastReadLength;
        durChapterIndex--;
        TextChapter textChapter2 = nextTextChapter;
        if (textChapter2 != null) {
            textChapter2.cancelLayout();
        }
        nextTextChapter = curTextChapter;
        TextChapter textChapter3 = prevTextChapter;
        curTextChapter = textChapter3;
        prevTextChapter = null;
        if (textChapter3 == null) {
            if (upContentInPlace && (callBack3 = callBack) != null) {
                CallBack.DefaultImpls.upContent$default(callBack3, 0, false, null, 7, null);
            }
            loadContent$default(this, durChapterIndex, upContent, false, null, 8, null);
        } else if (upContent && upContentInPlace && (callBack2 = callBack) != null) {
            CallBack.DefaultImpls.upContent$default(callBack2, 0, false, null, 7, null);
        }
        loadContent$default(this, durChapterIndex - 1, upContent, false, null, 8, null);
        saveRead$default(this, false, 1, null);
        CallBack callBack4 = callBack;
        if (callBack4 != null) {
            callBack4.upMenuView();
        }
        curPageChanged();
        return true;
    }

    public final void openChapter(int index, int durChapterPos2, @Nullable final Function0<Unit> success) {
        openChapterPageFromSearch = false;
        if (index < chapterSize) {
            clearTextChapter();
            durChapterIndex = index;
            durChapterPos = durChapterPos2;
            saveRead$default(this, false, 1, null);
            loadContent(true, true, new Function0<Unit>() { // from class: com.ykb.ebook.page.ReadBook.openChapter.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    Function0<Unit> function0 = success;
                    if (function0 != null) {
                        function0.invoke();
                    }
                }
            });
        }
    }

    public final void openChapterFromSearch(int index, int durChapterPos2, @Nullable final Function0<Unit> success) {
        openChapterPageFromSearch = true;
        if (index < chapterSize) {
            clearTextChapter();
            CallBack callBack2 = callBack;
            if (callBack2 != null) {
                CallBack.DefaultImpls.upContent$default(callBack2, 0, false, null, 7, null);
            }
            durChapterIndex = index;
            durChapterPos = durChapterPos2;
            saveRead$default(this, false, 1, null);
            loadContent$default(this, true, false, new Function0<Unit>() { // from class: com.ykb.ebook.page.ReadBook.openChapterFromSearch.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    Function0<Unit> function0 = success;
                    if (function0 != null) {
                        function0.invoke();
                    }
                }
            }, 2, null);
        }
    }

    public final int pageAnim() {
        return ReadConfig.INSTANCE.getPageAnim();
    }

    public final void register(@NotNull CallBack cb) {
        Intrinsics.checkNotNullParameter(cb, "cb");
        CallBack callBack2 = callBack;
        if (callBack2 != null) {
            callBack2.notifyBookChanged();
        }
        callBack = cb;
    }

    public final void resetBookInfoDataSet() {
        Iterator<Map.Entry<Integer, Boolean>> it = setBookInfoFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            setBookInfoFlagMap.put(it.next().getKey(), Boolean.FALSE);
        }
        setBookInfoFlagMap.clear();
    }

    public final void resetPageBitMap() {
        pageBitMapMap.clear();
    }

    public final void resetPages() {
        pageMap.clear();
    }

    public final void savePageBitMap(@NotNull Bitmap bitmap, @NotNull String key) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(key, "key");
        pageBitMapMap.put(key, bitmap);
    }

    public final void setBook(@Nullable BookInfo bookInfo) {
        book = bookInfo;
    }

    public final void setBookInfoViewData() {
        String prefString = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "book_id", "0");
        String str = prefString != null ? prefString : "0";
        if (new Regex(RegexPool.NUMBERS).matches(str)) {
            ArrayMap<Integer, Boolean> arrayMap = setBookInfoFlagMap;
            if (arrayMap.containsKey(Integer.valueOf(Integer.parseInt(str)))) {
                return;
            }
            arrayMap.put(Integer.valueOf(Integer.parseInt(str)), Boolean.TRUE);
        }
    }

    public final void setCallBack(@Nullable CallBack callBack2) {
        callBack = callBack2;
    }

    public final void setChapterPage(int chapterId, int pageCount) {
        pageMap.put(Integer.valueOf(chapterId), Integer.valueOf(pageCount));
    }

    public final void setChapterSize(int i2) {
        chapterSize = i2;
    }

    public final void setContentLoadFinish(boolean z2) {
        contentLoadFinish = z2;
    }

    public final void setCurTextChapter(@Nullable TextChapter textChapter) {
        curTextChapter = textChapter;
    }

    public final void setDurChapterIndex(int i2) {
        durChapterIndex = i2;
    }

    public final void setDurChapterPos(int i2) {
        durChapterPos = i2;
    }

    public final void setLoadContent(boolean z2) {
        isLoadContent = z2;
    }

    public final void setMsg(@Nullable String str) {
        msg = str;
    }

    public final void setOpenChapterPageFromSearch(boolean z2) {
        openChapterPageFromSearch = z2;
    }

    public final void setPageIndex(int index) {
        TextChapter textChapter = curTextChapter;
        if (textChapter != null) {
            index = textChapter.getReadLength(index);
        }
        durChapterPos = index;
        curPageChanged();
    }

    public final void setPayWays(@NotNull List<Ways> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        payWays = list;
    }

    public final void setUserId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        userId = str;
    }

    public final void skipToPage(int index, @Nullable final Function0<Unit> success) {
        TextChapter textChapter = curTextChapter;
        if (textChapter != null) {
            index = textChapter.getReadLength(index);
        }
        durChapterPos = index;
        CallBack callBack2 = callBack;
        if (callBack2 != null) {
            CallBack.DefaultImpls.upContent$default(callBack2, 0, false, new Function0<Unit>() { // from class: com.ykb.ebook.page.ReadBook.skipToPage.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    Function0<Unit> function0 = success;
                    if (function0 != null) {
                        function0.invoke();
                    }
                }
            }, 3, null);
        }
        curPageChanged();
        saveRead(true);
    }

    @Nullable
    public final TextChapter textChapter(int chapterOnDur) {
        if (chapterOnDur == -1) {
            return prevTextChapter;
        }
        if (chapterOnDur == 0) {
            return curTextChapter;
        }
        if (chapterOnDur != 1) {
            return null;
        }
        return nextTextChapter;
    }

    public final void unregister(@NotNull CallBack cb) {
        Intrinsics.checkNotNullParameter(cb, "cb");
        if (callBack == cb) {
            callBack = null;
        }
        msg = null;
        JobKt__JobKt.cancelChildren$default(getCoroutineContext(), (CancellationException) null, 1, (Object) null);
        ImageProvider.INSTANCE.clear();
        TextChapter textChapter = curTextChapter;
        if (textChapter != null) {
            textChapter.cancelLayout();
        }
    }

    public final void upData(@Nullable BookInfo book2, boolean reset) {
        book = book2;
        if (reset) {
            clearTextChapter();
            TextChapter textChapter = curTextChapter;
            if ((textChapter == null || textChapter.getIsCompleted()) ? false : true) {
                curTextChapter = null;
            }
            TextChapter textChapter2 = nextTextChapter;
            if ((textChapter2 == null || textChapter2.getIsCompleted()) ? false : true) {
                nextTextChapter = null;
            }
            TextChapter textChapter3 = prevTextChapter;
            if ((textChapter3 == null || textChapter3.getIsCompleted()) ? false : true) {
                prevTextChapter = null;
            }
            CallBack callBack2 = callBack;
            if (callBack2 != null) {
                callBack2.upMenuView();
            }
            synchronized (this) {
                loadingChapters.clear();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void upMsg(@Nullable String msg2) {
        if (Intrinsics.areEqual(msg, msg2)) {
            return;
        }
        msg = msg2;
        CallBack callBack2 = callBack;
        if (callBack2 != null) {
            CallBack.DefaultImpls.upContent$default(callBack2, 0, false, null, 7, null);
        }
    }

    public final void uploadProgress() {
        if (book != null) {
            BuildersKt__Builders_commonKt.launch$default(INSTANCE, Dispatchers.getIO(), null, new ReadBook$uploadProgress$1$1(null), 2, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void loadContent$default(ReadBook readBook, int i2, boolean z2, boolean z3, Function0 function0, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        if ((i3 & 4) != 0) {
            z3 = false;
        }
        if ((i3 & 8) != 0) {
            function0 = null;
        }
        readBook.loadContent(i2, z2, z3, function0);
    }

    private final void loadContent(int index, boolean upContent, boolean resetPageOffset, Function0<Unit> success) {
        int i2;
        List<Chapter> chapterList;
        List<Chapter> chapterList2;
        Chapter chapter;
        if (index >= 0 && index <= (i2 = chapterSize)) {
            int i3 = index == i2 ? i2 - 1 : index;
            BookInfo bookInfo = book;
            String id = (bookInfo == null || (chapterList2 = bookInfo.getChapterList()) == null || (chapter = chapterList2.get(i3)) == null) ? null : chapter.getId();
            if (!(id == null || id.length() == 0)) {
                HashMap mapHashMapOf = MapsKt__MapsKt.hashMapOf(new Pair("chapter_id", id));
                Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                BookInfo bookInfo2 = book;
                if (bookInfo2 != null && (chapterList = bookInfo2.getChapterList()) != null) {
                    for (Chapter chapter2 : chapterList) {
                        if (TextUtils.equals(id, chapter2.getId())) {
                            booleanRef.element = chapter2.isPay();
                        }
                    }
                }
                Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new C10483(mapHashMapOf, null), 15, null), null, new C10494(booleanRef, i3, index, upContent, resetPageOffset, success, null), 1, null), null, new AnonymousClass5(index, null), 1, null);
                return;
            }
            removeLoading(index);
            return;
        }
        removeLoading(index);
    }
}
