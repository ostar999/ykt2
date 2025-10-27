package com.ykb.ebook.weight;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.app.NotificationCompat;
import androidx.core.view.KeyEventDispatcher;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.umeng.analytics.pro.am;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.ChapterInfo;
import com.ykb.ebook.model.Note;
import com.ykb.ebook.model.TextChapter;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.model.TextPage;
import com.ykb.ebook.model.TextPosition;
import com.ykb.ebook.page.ChapterProvider;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.page.TextPageFactory;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.page.column.ButtonColumn;
import com.ykb.ebook.page.column.ImageColumn;
import com.ykb.ebook.page.column.QuestionColumn;
import com.ykb.ebook.page.column.ReviewColumn;
import com.ykb.ebook.page.column.TextColumn;
import com.ykb.ebook.page.delegate.PageDelegate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.StringsKt___StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\"\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u0094\u00012\u00020\u0001:\u0004\u0093\u0001\u0094\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010@\u001a\u00020\n2\u0006\u0010A\u001a\u00020\u0018H\u0016J\u0010\u0010B\u001a\u00020C2\b\b\u0002\u0010D\u001a\u00020\nJ\u0016\u0010E\u001a\u00020\n2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020GJ\b\u0010I\u001a\u00020CH\u0016J\u0016\u0010J\u001a\u00020\u00182\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020LJ\u000e\u0010N\u001a\u00020\u00182\u0006\u0010O\u001a\u00020LJ\u0010\u0010P\u001a\u00020\n2\u0006\u0010Q\u001a\u00020RH\u0016J\u0006\u0010S\u001a\u00020CJ\u0010\u0010T\u001a\u00020C2\u0006\u0010U\u001a\u00020VH\u0002J(\u0010W\u001a\u00020C2\u0006\u0010X\u001a\u00020\n2\u0006\u0010Y\u001a\u00020\u00182\u0006\u0010Z\u001a\u00020\u00182\b\b\u0002\u0010[\u001a\u00020LJ\u0010\u0010\\\u001a\u00020]2\u0006\u0010^\u001a\u00020_H\u0002J\b\u0010`\u001a\u0004\u0018\u00010aJ\u0006\u0010b\u001a\u000209J\u0006\u0010c\u001a\u00020LJ\u0010\u0010d\u001a\u00020C2\u0006\u0010:\u001a\u000209H\u0002J9\u0010e\u001a\u00020C2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2!\u0010f\u001a\u001d\u0012\u0013\u0012\u001103¢\u0006\f\bh\u0012\b\bi\u0012\u0004\b\b(j\u0012\u0004\u0012\u00020C0gJ\u0010\u0010k\u001a\u00020C2\u0006\u0010U\u001a\u00020VH\u0014J(\u0010l\u001a\u00020C2\u0006\u0010m\u001a\u00020\u00182\u0006\u0010n\u001a\u00020\u00182\u0006\u0010o\u001a\u00020\u00182\u0006\u0010p\u001a\u00020\u0018H\u0014J\b\u0010q\u001a\u00020CH\u0002J\u0010\u0010r\u001a\u00020G2\u0006\u0010s\u001a\u00020\u0018H\u0002J\u000e\u0010t\u001a\u0002092\u0006\u0010s\u001a\u00020\u0018J\u0006\u0010u\u001a\u00020CJ\u0006\u0010v\u001a\u00020CJ\u000e\u0010w\u001a\u00020C2\u0006\u0010x\u001a\u00020\u0018J\u0016\u0010y\u001a\u00020C2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020GJ\u000e\u0010z\u001a\u00020C2\u0006\u0010j\u001a\u000203J0\u0010z\u001a\u00020C2\u0006\u0010t\u001a\u00020\u00182\u0006\u0010{\u001a\u00020\u00182\u0006\u0010|\u001a\u00020\u00182\u0006\u0010}\u001a\u00020\n2\b\b\u0002\u0010~\u001a\u00020\nJ\u0016\u0010\u007f\u001a\u00020C2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020GJ\u000f\u0010\u0080\u0001\u001a\u00020C2\u0006\u0010j\u001a\u000203J2\u0010\u0080\u0001\u001a\u00020C2\u0007\u0010\u0081\u0001\u001a\u00020\u00182\u0006\u0010{\u001a\u00020\u00182\u0006\u0010|\u001a\u00020\u00182\u0006\u0010}\u001a\u00020\n2\b\b\u0002\u0010~\u001a\u00020\nJ:\u0010\u0082\u0001\u001a\u00020C2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2!\u0010f\u001a\u001d\u0012\u0013\u0012\u001103¢\u0006\f\bh\u0012\b\bi\u0012\u0004\b\b(j\u0012\u0004\u0012\u00020C0gJ\u0019\u0010\u0083\u0001\u001a\u00020C2\u0006\u0010:\u001a\u0002092\b\b\u0002\u0010=\u001a\u00020\nJ\u0010\u0010\u0084\u0001\u001a\u00020C2\u0007\u0010\u0085\u0001\u001a\u00020\nJ \u0010\u0086\u0001\u001a\u00020C2\u0017\u0010\u0087\u0001\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u0017j\b\u0012\u0004\u0012\u00020\u0018`\u0019J\u0007\u0010\u0088\u0001\u001a\u00020CJ\u0094\u0001\u0010\u0089\u0001\u001a\u00020C2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2y\u0010\u008a\u0001\u001at\u0012\u0013\u0012\u00110G¢\u0006\f\bh\u0012\b\bi\u0012\u0004\b\b(r\u0012\u0013\u0012\u001103¢\u0006\f\bh\u0012\b\bi\u0012\u0004\b\b(j\u0012\u0013\u0012\u001109¢\u0006\f\bh\u0012\b\bi\u0012\u0004\b\b(:\u0012\u0014\u0012\u00120a¢\u0006\r\bh\u0012\t\bi\u0012\u0005\b\b(\u008c\u0001\u0012\u0014\u0012\u00120]¢\u0006\r\bh\u0012\t\bi\u0012\u0005\b\b(\u008d\u0001\u0012\u0004\u0012\u00020C0\u008b\u0001H\u0002J\u0094\u0001\u0010\u008e\u0001\u001a\u00020C2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2y\u0010\u008a\u0001\u001at\u0012\u0013\u0012\u00110G¢\u0006\f\bh\u0012\b\bi\u0012\u0004\b\b(r\u0012\u0013\u0012\u001103¢\u0006\f\bh\u0012\b\bi\u0012\u0004\b\b(j\u0012\u0013\u0012\u001109¢\u0006\f\bh\u0012\b\bi\u0012\u0004\b\b(:\u0012\u0014\u0012\u00120a¢\u0006\r\bh\u0012\t\bi\u0012\u0005\b\b(\u008c\u0001\u0012\u0014\u0012\u00120]¢\u0006\r\bh\u0012\t\bi\u0012\u0005\b\b(\u008d\u0001\u0012\u0004\u0012\u00020C0\u008b\u0001H\u0002J\t\u0010\u008f\u0001\u001a\u00020CH\u0002J\u0019\u0010\u0090\u0001\u001a\u00020C2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020GH\u0002J\"\u0010\u0091\u0001\u001a\u00020C2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2\u0007\u0010\u0092\u0001\u001a\u00020GH\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0011\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u0017j\b\u0012\u0004\u0012\u00020\u0018`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0012\"\u0004\b\u001c\u0010\u0014R\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u001e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010&\u001a\u00020'8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\u0010\u001a\u0004\b(\u0010)R\u001a\u0010+\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0012\"\u0004\b-\u0010\u0014R\u001a\u0010.\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0012\"\u0004\b0\u0010\u0014R\u000e\u00101\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u00105\u001a\u00020\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b7\u0010\u0010\u001a\u0004\b6\u0010\u000eR\u001e\u0010:\u001a\u0002092\u0006\u00108\u001a\u000209@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u000e\u0010=\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020?X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0095\u0001"}, d2 = {"Lcom/ykb/ebook/weight/ContentTextView;", "Landroid/view/View;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "callBack", "Lcom/ykb/ebook/weight/ContentTextView$CallBack;", "flag", "", "imagePaint", "Landroid/graphics/Paint;", "getImagePaint", "()Landroid/graphics/Paint;", "imagePaint$delegate", "Lkotlin/Lazy;", "isMainView", "()Z", "setMainView", "(Z)V", "isScroll", "longPressParagraphChars", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "longScreenshot", "getLongScreenshot", "setLongScreenshot", "pageDelegate", "Lcom/ykb/ebook/page/delegate/PageDelegate;", "getPageDelegate", "()Lcom/ykb/ebook/page/delegate/PageDelegate;", "pageFactory", "Lcom/ykb/ebook/page/TextPageFactory;", "getPageFactory", "()Lcom/ykb/ebook/page/TextPageFactory;", "pageOffset", "renderRunnable", "Ljava/lang/Runnable;", "getRenderRunnable", "()Ljava/lang/Runnable;", "renderRunnable$delegate", "reverseEndCursor", "getReverseEndCursor", "setReverseEndCursor", "reverseStartCursor", "getReverseStartCursor", "setReverseStartCursor", "selectAble", "selectEnd", "Lcom/ykb/ebook/model/TextPosition;", "selectStart", "selectedPaint", "getSelectedPaint", "selectedPaint$delegate", "<set-?>", "Lcom/ykb/ebook/model/TextPage;", "textPage", "getTextPage", "()Lcom/ykb/ebook/model/TextPage;", "updateTheme", "visibleRect", "Landroid/graphics/RectF;", "canScrollVertically", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "cancelSelect", "", "clearSearchResult", "click", "x", "", "y", "computeScroll", "countRemovedNewlines", "text1", "", "text2", "countSpecialCharacters", "input", "dispatchTouchEvent", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "drawDashLine", "drawPage", "canvas", "Landroid/graphics/Canvas;", "drawUnderLine", "drawOrCancel", "color", TtmlNode.TAG_STYLE, "partLineText", "getClickDrawUnderLineFirstIndex", "Lcom/ykb/ebook/page/column/BaseColumn;", "clickColumn", "Lcom/ykb/ebook/page/column/TextColumn;", "getCurVisibleFirstLine", "Lcom/ykb/ebook/model/TextLine;", "getCurVisiblePage", "getSelectedText", "initDrawLineAndDash", "longPress", "select", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "textPos", "onDraw", "onSizeChanged", "w", "h", "oldw", "oldh", "preRenderPage", "relativeOffset", "relativePos", "relativePage", "resetPageOffset", "resetReverseCursor", "scroll", "mOffset", "selectEndMove", "selectEndMoveIndex", "lineIndex", "charIndex", "isTouch", "isLast", "selectStartMove", "selectStartMoveIndex", "relativePagePos", "selectText", "setContent", "setIsScroll", "value", "setLongPressParagraphChars", am.aF, "submitRenderTask", "touch", "touched", "Lkotlin/Function5;", "textLine", "column", "touchRough", "upSelectChars", "upSelectedEnd", "upSelectedStart", PLVDanmakuInfo.FONTMODE_TOP, "CallBack", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nContentTextView.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContentTextView.kt\ncom/ykb/ebook/weight/ContentTextView\n+ 2 SparseBooleanArray.kt\nandroidx/core/util/SparseBooleanArrayKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1203:1\n30#2:1204\n1#3:1205\n1855#4:1206\n1855#4,2:1207\n1856#4:1209\n1855#4:1210\n1855#4,2:1211\n1856#4:1213\n1855#4:1214\n1855#4:1215\n1855#4,2:1216\n1856#4:1218\n1856#4:1219\n1855#4:1220\n1855#4,2:1221\n1856#4:1223\n1864#4,2:1224\n1864#4,3:1226\n1866#4:1229\n*S KotlinDebug\n*F\n+ 1 ContentTextView.kt\ncom/ykb/ebook/weight/ContentTextView\n*L\n152#1:1204\n281#1:1206\n282#1:1207,2\n281#1:1209\n305#1:1210\n306#1:1211,2\n305#1:1213\n595#1:1214\n596#1:1215\n597#1:1216,2\n596#1:1218\n595#1:1219\n1002#1:1220\n1003#1:1221,2\n1002#1:1223\n1026#1:1224,2\n1029#1:1226,3\n1026#1:1229\n*E\n"})
/* loaded from: classes8.dex */
public final class ContentTextView extends View {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Lazy<ExecutorService> renderThread$delegate = LazyKt__LazyJVMKt.lazy(ContentTextView$Companion$renderThread$2.INSTANCE);

    @NotNull
    private CallBack callBack;
    private boolean flag;

    /* renamed from: imagePaint$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy imagePaint;
    private boolean isMainView;
    private boolean isScroll;

    @NotNull
    private final ArrayList<Integer> longPressParagraphChars;
    private boolean longScreenshot;
    private int pageOffset;

    /* renamed from: renderRunnable$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy renderRunnable;
    private boolean reverseEndCursor;
    private boolean reverseStartCursor;
    private final boolean selectAble;

    @NotNull
    private final TextPosition selectEnd;

    @NotNull
    private final TextPosition selectStart;

    /* renamed from: selectedPaint$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy selectedPaint;

    @NotNull
    private TextPage textPage;
    private boolean updateTheme;

    @NotNull
    private final RectF visibleRect;

    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\b\u0010\u0014\u001a\u00020\u0015H&J\b\u0010\u0016\u001a\u00020\u0015H&J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019H&J \u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u0019H&J\u0010\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020 H&JP\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u00192\b\u0010&\u001a\u0004\u0018\u00010\u00192\b\b\u0002\u0010'\u001a\u00020\u00072\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001cH&J\u0010\u0010(\u001a\u00020\u00152\u0006\u0010)\u001a\u00020\u0019H&J\u0018\u0010*\u001a\u00020\u00152\u0006\u0010)\u001a\u00020\u00192\u0006\u0010\"\u001a\u00020\u0019H&J(\u0010+\u001a\u00020\u00152\u0006\u0010,\u001a\u00020\u001c2\u0006\u0010-\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\u001cH&J(\u00100\u001a\u00020\u00152\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u00072\u0006\u00104\u001a\u00020\u00032\u0006\u00105\u001a\u00020\u0003H&J\u0018\u00106\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH&J \u00107\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u00108\u001a\u00020\u001cH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0018\u0010\t\u001a\u00020\u0007X¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\b\"\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u0004\u0018\u00010\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u00069"}, d2 = {"Lcom/ykb/ebook/weight/ContentTextView$CallBack;", "", "headerHeight", "", "getHeaderHeight", "()I", "isScroll", "", "()Z", "isSelectingSearchResult", "setSelectingSearchResult", "(Z)V", "pageDelegate", "Lcom/ykb/ebook/page/delegate/PageDelegate;", "getPageDelegate", "()Lcom/ykb/ebook/page/delegate/PageDelegate;", "pageFactory", "Lcom/ykb/ebook/page/TextPageFactory;", "getPageFactory", "()Lcom/ykb/ebook/page/TextPageFactory;", "onCancelSelect", "", "onDismissActionMenu", "onImageClick", "src", "", "onImageLongPress", "x", "", "y", "onLongScreenshotTouchEvent", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "onNoteClick", "chapterId", "startPosition", SessionDescription.ATTR_LENGTH, "text", "lineId", "hasUnderLine", "onQuestionClick", "id", "onReviewClick", "onShowActionMenu", "startX", "startY", "endX", "endY", "showDrawLineTextActionMenu", "column", "Lcom/ykb/ebook/page/column/TextColumn;", "drawOrCancel", "color", TtmlNode.TAG_STYLE, "upSelectedEnd", "upSelectedStart", PLVDanmakuInfo.FONTMODE_TOP, "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface CallBack {

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public static final class DefaultImpls {
            public static /* synthetic */ void onNoteClick$default(CallBack callBack, String str, int i2, int i3, String str2, String str3, boolean z2, float f2, float f3, int i4, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onNoteClick");
                }
                callBack.onNoteClick(str, i2, i3, str2, str3, (i4 & 32) != 0 ? false : z2, (i4 & 64) != 0 ? 0.0f : f2, (i4 & 128) != 0 ? 0.0f : f3);
            }
        }

        int getHeaderHeight();

        @Nullable
        PageDelegate getPageDelegate();

        @NotNull
        TextPageFactory getPageFactory();

        boolean isScroll();

        boolean isSelectingSearchResult();

        void onCancelSelect();

        void onDismissActionMenu();

        void onImageClick(@NotNull String src);

        void onImageLongPress(float x2, float y2, @NotNull String src);

        boolean onLongScreenshotTouchEvent(@NotNull MotionEvent event);

        void onNoteClick(@NotNull String chapterId, int startPosition, int length, @NotNull String text, @Nullable String lineId, boolean hasUnderLine, float x2, float y2);

        void onQuestionClick(@NotNull String id);

        void onReviewClick(@NotNull String id, @NotNull String chapterId);

        void onShowActionMenu(float startX, float startY, float endX, float endY);

        void setSelectingSearchResult(boolean z2);

        void showDrawLineTextActionMenu(@NotNull TextColumn column, boolean drawOrCancel, int color, int style);

        void upSelectedEnd(float x2, float y2);

        void upSelectedStart(float x2, float y2, float top2);
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R#\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/weight/ContentTextView$Companion;", "", "()V", "renderThread", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "getRenderThread", "()Ljava/util/concurrent/ExecutorService;", "renderThread$delegate", "Lkotlin/Lazy;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ExecutorService getRenderThread() {
            return (ExecutorService) ContentTextView.renderThread$delegate.getValue();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContentTextView(@NotNull final Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.selectAble = ReadConfig.INSTANCE.getTextSelectAble();
        this.selectedPaint = LazyKt__LazyJVMKt.lazy(new Function0<Paint>() { // from class: com.ykb.ebook.weight.ContentTextView$selectedPaint$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Paint invoke() {
                Paint paint = new Paint();
                paint.setColor(ContextExtensionsKt.getCompatColor(context, R.color.color_1578aeff));
                paint.setStyle(Paint.Style.FILL);
                return paint;
            }
        });
        this.visibleRect = ChapterProvider.getVisibleRect();
        this.selectStart = new TextPosition(0, -1, -1, false, false, 24, null);
        this.selectEnd = new TextPosition(0, -1, -1, false, false, 24, null);
        this.textPage = new TextPage(0, null, null, null, 0, 0, 0.0f, 0, 255, null);
        this.renderRunnable = LazyKt__LazyJVMKt.lazy(new ContentTextView$renderRunnable$2(this));
        this.imagePaint = LazyKt__LazyJVMKt.lazy(new Function0<Paint>() { // from class: com.ykb.ebook.weight.ContentTextView$imagePaint$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Paint invoke() {
                Paint paint = new Paint();
                paint.setAntiAlias(ReadConfig.INSTANCE.getUseAntiAlias());
                return paint;
            }
        });
        KeyEventDispatcher.Component activity = ViewExtensionsKt.getActivity(this);
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.ykb.ebook.weight.ContentTextView.CallBack");
        this.callBack = (CallBack) activity;
        this.longPressParagraphChars = new ArrayList<>();
    }

    public static /* synthetic */ void cancelSelect$default(ContentTextView contentTextView, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        contentTextView.cancelSelect(z2);
    }

    private final void drawPage(Canvas canvas) {
        float fRelativeOffset = relativeOffset(0);
        this.textPage.draw(this, canvas, fRelativeOffset, this.updateTheme);
        if (this.callBack.isScroll() && getPageFactory().hasNext()) {
            TextPage textPageRelativePage = relativePage(1);
            float height = fRelativeOffset + this.textPage.getHeight();
            TextPage.draw$default(textPageRelativePage, this, canvas, height, false, 8, null);
            if (getPageFactory().hasNextPlus()) {
                float height2 = height + textPageRelativePage.getHeight();
                if (height2 < ChapterProvider.getVisibleHeight()) {
                    TextPage.draw$default(relativePage(2), this, canvas, height2, false, 8, null);
                }
            }
        }
    }

    public static /* synthetic */ void drawUnderLine$default(ContentTextView contentTextView, boolean z2, int i2, int i3, String str, int i4, Object obj) {
        if ((i4 & 8) != 0) {
            str = "";
        }
        contentTextView.drawUnderLine(z2, i2, i3, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BaseColumn getClickDrawUnderLineFirstIndex(TextColumn clickColumn) {
        Log.d(ContentTextView.class.getSimpleName(), "clickColume.CharIndex:" + clickColumn.getCharIndex() + ",text:  " + clickColumn.getCharData() + ",lineId:" + clickColumn.getLineId());
        BaseColumn baseColumn = this.textPage.textChapter.getPages().get(0).getLines().get(0).getColumns().get(0);
        BaseColumn baseColumn2 = this.textPage.textChapter.getPages().get(this.textPage.getIndex()).getLines().get(0).getColumns().get(0);
        boolean z2 = false;
        for (TextPage textPage : this.textPage.textChapter.getPages()) {
            Iterator<T> it = textPage.getLines().iterator();
            while (it.hasNext()) {
                for (BaseColumn baseColumn3 : ((TextLine) it.next()).getColumns()) {
                    if (baseColumn3 instanceof TextColumn) {
                        TextColumn textColumn = (TextColumn) baseColumn3;
                        if (TextUtils.isEmpty(StringsKt__StringsKt.trim((CharSequence) textColumn.getCharData()).toString())) {
                            continue;
                        } else {
                            Log.d(ContentTextView.class.getSimpleName(), "this is forEach LineIndex:" + textColumn.getLineId() + ",clickLineIndex:  " + clickColumn.getLineId());
                            if (textColumn.getIsDrawUnderLine() && Intrinsics.areEqual(textColumn.getLineId(), clickColumn.getLineId())) {
                                if (!z2) {
                                    if (textPage.getIndex() == this.textPage.getIndex()) {
                                        baseColumn = baseColumn3;
                                        baseColumn2 = baseColumn;
                                    } else {
                                        baseColumn = baseColumn3;
                                    }
                                }
                                if (textColumn.getCharIndex() == clickColumn.getCharIndex()) {
                                    String simpleName = ContentTextView.class.getSimpleName();
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("this is pageIndex:");
                                    Intrinsics.checkNotNull(baseColumn2, "null cannot be cast to non-null type com.ykb.ebook.page.column.TextColumn");
                                    sb.append(((TextColumn) baseColumn2).getCharData());
                                    Log.d(simpleName, sb.toString());
                                    String simpleName2 = ContentTextView.class.getSimpleName();
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("this is index:");
                                    Intrinsics.checkNotNull(baseColumn, "null cannot be cast to non-null type com.ykb.ebook.page.column.TextColumn");
                                    sb2.append(((TextColumn) baseColumn).getCharData());
                                    Log.d(simpleName2, sb2.toString());
                                    return baseColumn2;
                                }
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                        }
                    }
                }
            }
        }
        return baseColumn2;
    }

    private final PageDelegate getPageDelegate() {
        return this.callBack.getPageDelegate();
    }

    private final TextPageFactory getPageFactory() {
        return this.callBack.getPageFactory();
    }

    private final Runnable getRenderRunnable() {
        return (Runnable) this.renderRunnable.getValue();
    }

    private final void initDrawLineAndDash(TextPage textPage) {
        Iterator<Note> it;
        TextChapter textChapter = textPage.textChapter;
        ChapterInfo chapter = textChapter.getChapter();
        String content = StringExtensionsKt.formatContent(chapter.getHandleContent());
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        Iterator<Note> it2 = chapter.getDrawNotesList().iterator();
        while (it2.hasNext()) {
            Note next = it2.next();
            String content2 = StringExtensionsKt.formatContent(next.getDrawContent());
            int type = next.getType();
            boolean z2 = false;
            if (type == 1) {
                it = it2;
                if (StringsKt__StringsKt.contains$default((CharSequence) content, (CharSequence) StringExtensionsKt.formatContent(content2), false, 2, (Object) null)) {
                    if (next.getStartPosition() > textChapter.getPages().get(0).getTitle().length()) {
                        String strSubstring = chapter.getHandleContent().substring(0, next.getStartPosition() - textChapter.getPages().get(0).getTitle().length());
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                        countSpecialCharacters(strSubstring);
                    }
                    int startPosition = next.getStartPosition() - textChapter.getPages().get(0).getTitle().length();
                    int length = (content2.length() + startPosition) - 1;
                    Iterator<TextLine> it3 = textPage.getLines().iterator();
                    while (it3.hasNext()) {
                        for (BaseColumn baseColumn : it3.next().getColumns()) {
                            if (baseColumn instanceof TextColumn) {
                                TextColumn textColumn = (TextColumn) baseColumn;
                                if (!TextUtils.isEmpty(textColumn.getCharData())) {
                                    if ((StringsKt__StringsKt.trim((CharSequence) textColumn.getCharData()).toString().length() > 0) && StringsKt__StringsKt.contains$default((CharSequence) content2, (CharSequence) textColumn.getCharData(), false, 2, (Object) null)) {
                                        int charIndex = textColumn.getCharIndex();
                                        if (startPosition <= charIndex && charIndex <= length) {
                                            textColumn.setDrawUnderLine(true);
                                            textColumn.setLineColor(next.getColor());
                                            textColumn.setLineStyle(next.getStyle());
                                            textColumn.setLineId(next.getId());
                                            sparseBooleanArray.put(textColumn.getCharIndex(), true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (type == 2) {
                if (!ReadConfig.INSTANCE.isShowNote()) {
                    it = it2;
                    if (Intrinsics.areEqual(next.getUserId(), ReadBook.INSTANCE.getUserId()) && StringsKt__StringsKt.contains$default((CharSequence) content, (CharSequence) content2, false, 2, (Object) null)) {
                        if (next.getStartPosition() > textChapter.getPages().get(0).getTitle().length()) {
                            String strSubstring2 = chapter.getHandleContent().substring(0, next.getStartPosition() - textChapter.getPages().get(0).getTitle().length());
                            Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String…ing(startIndex, endIndex)");
                            countSpecialCharacters(strSubstring2);
                        }
                        int startPosition2 = next.getStartPosition() - textChapter.getPages().get(0).getTitle().length();
                        int length2 = (content2.length() + startPosition2) - 1;
                        Iterator<TextLine> it4 = textPage.getLines().iterator();
                        while (it4.hasNext()) {
                            for (BaseColumn baseColumn2 : it4.next().getColumns()) {
                                if (baseColumn2 instanceof TextColumn) {
                                    TextColumn textColumn2 = (TextColumn) baseColumn2;
                                    int charIndex2 = textColumn2.getCharIndex();
                                    if (startPosition2 <= charIndex2 && charIndex2 <= length2) {
                                        textColumn2.setDashId(next.getId());
                                        textColumn2.setDashStartPosition(next.getStartPosition());
                                        textColumn2.setDashLength(next.getLength());
                                        textColumn2.setDashContent(next.getDrawContent());
                                        textColumn2.setDrawDashLine(true);
                                        textColumn2.setDashColor(AppCtxKt.getAppCtx().getColor(R.color.color_ffbe5d));
                                    }
                                }
                            }
                        }
                    }
                } else if (StringsKt__StringsKt.contains$default((CharSequence) content, (CharSequence) content2, false, 2, (Object) null)) {
                    if (next.getStartPosition() > textChapter.getPages().get(0).getTitle().length()) {
                        String strSubstring3 = chapter.getHandleContent().substring(0, next.getStartPosition() - textChapter.getPages().get(0).getTitle().length());
                        Intrinsics.checkNotNullExpressionValue(strSubstring3, "this as java.lang.String…ing(startIndex, endIndex)");
                        countSpecialCharacters(strSubstring3);
                    }
                    int startPosition3 = next.getStartPosition() - textChapter.getPages().get(0).getTitle().length();
                    int length3 = (content2.length() + startPosition3) - 1;
                    Iterator<TextLine> it5 = textPage.getLines().iterator();
                    while (it5.hasNext()) {
                        for (BaseColumn baseColumn3 : it5.next().getColumns()) {
                            if (baseColumn3 instanceof TextColumn) {
                                TextColumn textColumn3 = (TextColumn) baseColumn3;
                                if (!TextUtils.isEmpty(StringsKt__StringsKt.trim((CharSequence) textColumn3.getCharData()).toString())) {
                                    Iterator<Note> it6 = it2;
                                    if (StringsKt__StringsKt.contains$default(content2, textColumn3.getCharData(), z2, 2, (Object) null)) {
                                        int charIndex3 = textColumn3.getCharIndex();
                                        if ((startPosition3 > charIndex3 || charIndex3 > length3) ? z2 : true) {
                                            textColumn3.setDashId(next.getId());
                                            textColumn3.setDashStartPosition(next.getStartPosition());
                                            textColumn3.setDashLength(next.getLength());
                                            textColumn3.setDashContent(next.getDrawContent());
                                            textColumn3.setDrawDashLine(!sparseBooleanArray.get(textColumn3.getCharIndex()));
                                            int i2 = startPosition3;
                                            textColumn3.setDashColor(Intrinsics.areEqual(next.getUserId(), ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), "user_id", null, 2, null)) ? AppCtxKt.getAppCtx().getColor(R.color.color_ffbe5d) : AppCtxKt.getAppCtx().getColor(R.color.color_bfbfbf));
                                            it2 = it6;
                                            startPosition3 = i2;
                                            z2 = false;
                                        }
                                    }
                                    it2 = it6;
                                }
                            }
                        }
                    }
                }
            }
            it2 = it;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void preRenderPage() {
        TextPageFactory pageFactory = getPageFactory();
        boolean z2 = pageFactory.hasPrev() && TextPage.render$default(pageFactory.getPrevPage(), this, false, 2, null);
        if (TextPage.render$default(pageFactory.getCurPage(), this, false, 2, null)) {
            z2 = true;
        }
        if (pageFactory.hasNext() && TextPage.render$default(pageFactory.getNextPage(), this, false, 2, null) && this.callBack.isScroll()) {
            z2 = true;
        }
        if ((pageFactory.hasNextPlus() && TextPage.render$default(pageFactory.getNextPlusPage(), this, false, 2, null) && this.callBack.isScroll() && relativeOffset(2) < ((float) ChapterProvider.getVisibleHeight())) ? true : z2) {
            postInvalidate();
            PageDelegate pageDelegate = getPageDelegate();
            if (pageDelegate != null) {
                pageDelegate.postInvalidate();
            }
        }
    }

    private final float relativeOffset(int relativePos) {
        float height;
        float height2;
        if (relativePos == 0) {
            return this.pageOffset;
        }
        if (relativePos != 1) {
            height = this.pageOffset + this.textPage.getHeight();
            height2 = getPageFactory().getNextPage().getHeight();
        } else {
            height = this.pageOffset;
            height2 = this.textPage.getHeight();
        }
        return height + height2;
    }

    public static /* synthetic */ void selectEndMoveIndex$default(ContentTextView contentTextView, int i2, int i3, int i4, boolean z2, boolean z3, int i5, Object obj) {
        if ((i5 & 16) != 0) {
            z3 = false;
        }
        contentTextView.selectEndMoveIndex(i2, i3, i4, z2, z3);
    }

    public static /* synthetic */ void selectStartMoveIndex$default(ContentTextView contentTextView, int i2, int i3, int i4, boolean z2, boolean z3, int i5, Object obj) {
        if ((i5 & 16) != 0) {
            z3 = false;
        }
        contentTextView.selectStartMoveIndex(i2, i3, i4, z2, z3);
    }

    public static /* synthetic */ void setContent$default(ContentTextView contentTextView, TextPage textPage, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        contentTextView.setContent(textPage, z2);
    }

    private final void touch(float x2, float y2, Function5<? super Float, ? super TextPosition, ? super TextPage, ? super TextLine, ? super BaseColumn, Unit> touched) {
        if (this.visibleRect.contains(x2, y2)) {
            int i2 = 0;
            for (int i3 = 0; i3 < 3; i3++) {
                float fRelativeOffset = relativeOffset(i3);
                if (i3 > 0 && (!this.callBack.isScroll() || fRelativeOffset >= ChapterProvider.getVisibleHeight())) {
                    return;
                }
                TextPage textPageRelativePage = relativePage(i3);
                int i4 = 0;
                for (TextLine textLine : textPageRelativePage.getLines()) {
                    int i5 = i4 + 1;
                    if (textLine.isTouch(x2, y2, fRelativeOffset)) {
                        for (BaseColumn baseColumn : textLine.getColumns()) {
                            int i6 = i2 + 1;
                            if (baseColumn.isTouch(x2) && !ReadConfig.INSTANCE.getFirstPage()) {
                                touched.invoke(Float.valueOf(fRelativeOffset), new TextPosition(i3, i4, i2, false, false, 24, null), textPageRelativePage, textLine, baseColumn);
                                return;
                            }
                            i2 = i6;
                        }
                        return;
                    }
                    i4 = i5;
                }
            }
        }
    }

    private final void touchRough(float x2, float y2, Function5<? super Float, ? super TextPosition, ? super TextPage, ? super TextLine, ? super BaseColumn, Unit> touched) {
        for (int i2 = 0; i2 < 3; i2++) {
            float fRelativeOffset = relativeOffset(i2);
            if (i2 > 0 && (!this.callBack.isScroll() || fRelativeOffset >= ChapterProvider.getVisibleHeight())) {
                return;
            }
            TextPage textPageRelativePage = relativePage(i2);
            int size = textPageRelativePage.getLines().size();
            for (int i3 = 0; i3 < size; i3++) {
                TextLine line = textPageRelativePage.getLine(i3);
                if (line.isTouchY(y2, fRelativeOffset)) {
                    List<BaseColumn> columns = line.getColumns();
                    int size2 = columns.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        BaseColumn baseColumn = columns.get(i4);
                        if (baseColumn.isTouch(x2)) {
                            if ((baseColumn instanceof TextColumn) && ((TextColumn) baseColumn).getCharIndex() == -1) {
                                return;
                            }
                            touched.invoke(Float.valueOf(fRelativeOffset), new TextPosition(i2, i3, i4, false, false, 24, null), textPageRelativePage, line, baseColumn);
                            return;
                        }
                    }
                    boolean z2 = ((BaseColumn) CollectionsKt___CollectionsKt.first((List) columns)).getStart() < x2;
                    touched.invoke(Float.valueOf(fRelativeOffset), new TextPosition(i2, i3, z2 ? CollectionsKt__CollectionsKt.getLastIndex(columns) : 0, false, z2), textPageRelativePage, line, (BaseColumn) (z2 ? CollectionsKt___CollectionsKt.last((List) columns) : CollectionsKt___CollectionsKt.first((List) columns)));
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void upSelectChars() {
        if (this.selectStart.isSelected() && this.selectEnd.isSelected()) {
            int i2 = this.callBack.isScroll() ? 2 : 0;
            TextPosition textPosition = new TextPosition(0, 0, 0, false, false, 24, null);
            com.ykb.ebook.util.Log.INSTANCE.logD("upSelectChars", String.valueOf(i2));
            if (i2 >= 0) {
                int i3 = 0;
                while (true) {
                    textPosition.setRelativePagePosition(i3);
                    TextPage textPageRelativePage = relativePage(i3);
                    int i4 = 0;
                    for (TextLine textLine : textPageRelativePage.getLines()) {
                        int i5 = i4 + 1;
                        textPosition.setLineIndex(i4);
                        int i6 = 0;
                        for (BaseColumn baseColumn : textLine.getColumns()) {
                            int i7 = i6 + 1;
                            textPosition.setColumnIndex(i6);
                            if (baseColumn instanceof TextColumn) {
                                int iCompare = textPosition.compare(this.selectStart);
                                int iCompare2 = textPosition.compare(this.selectEnd);
                                TextColumn textColumn = (TextColumn) baseColumn;
                                textColumn.setSelected(iCompare == 0 ? this.selectStart.isTouch() : iCompare2 != 0 ? !(iCompare <= 0 || iCompare2 >= 0) : this.selectEnd.isTouch() || this.selectEnd.isLast());
                                if (textColumn.getCharIndex() == -1) {
                                    textColumn.setSelected(false);
                                }
                                if (textColumn.getSelected()) {
                                    ReadConfig readConfig = ReadConfig.INSTANCE;
                                    if (readConfig.getSelectTextPosition() == -1) {
                                        String handleContent = baseColumn.getTextLine().getTextPage().getTextChapter().getChapter().getHandleContent();
                                        TextColumn textColumn2 = (TextColumn) baseColumn;
                                        String strSubstring = StringExtensionsKt.formatContent(baseColumn.getTextLine().getTextPage().getTextChapter().getChapter().getHandleContent()).substring(0, textColumn2.getCharIndex());
                                        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                                        countRemovedNewlines(handleContent, strSubstring);
                                        readConfig.setSelectTextPosition(textColumn2.getCharIndex() + baseColumn.getTextLine().getTextPage().getTextChapter().getPages().get(0).getTitle().length());
                                    }
                                }
                                textColumn.setSearchResult(textColumn.getSelected() && this.callBack.isSelectingSearchResult());
                                if (textColumn.getIsSearchResult()) {
                                    textPageRelativePage.getSearchResult().add(baseColumn);
                                }
                            }
                            i6 = i7;
                        }
                        i4 = i5;
                    }
                    if (i3 == i2) {
                        break;
                    } else {
                        i3++;
                    }
                }
            }
            postInvalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void upSelectedEnd(float x2, float y2) {
        this.callBack.upSelectedEnd(x2, y2 + r0.getHeaderHeight());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void upSelectedStart(float x2, float y2, float top2) {
        ReadConfig.INSTANCE.setSelectTextPosition(-1);
        this.callBack.upSelectedStart(x2, y2 + r0.getHeaderHeight(), top2 + r0.getHeaderHeight());
    }

    @Override // android.view.View
    public boolean canScrollVertically(int direction) {
        return this.callBack.isScroll() && getPageFactory().hasNext();
    }

    public final void cancelSelect(boolean clearSearchResult) {
        int i2 = this.callBack.isScroll() ? 2 : 0;
        if (i2 >= 0) {
            int i3 = 0;
            while (true) {
                TextPage textPageRelativePage = relativePage(i3);
                Iterator<T> it = textPageRelativePage.getLines().iterator();
                while (it.hasNext()) {
                    for (BaseColumn baseColumn : ((TextLine) it.next()).getColumns()) {
                        if (baseColumn instanceof TextColumn) {
                            TextColumn textColumn = (TextColumn) baseColumn;
                            textColumn.setSelected(false);
                            if (clearSearchResult) {
                                textColumn.setSearchResult(false);
                                textPageRelativePage.getSearchResult().remove(baseColumn);
                            }
                        }
                    }
                }
                if (i3 == i2) {
                    break;
                } else {
                    i3++;
                }
            }
        }
        this.selectStart.reset();
        this.selectEnd.reset();
        postInvalidate();
        this.callBack.onCancelSelect();
    }

    public final boolean click(final float x2, final float y2) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        touch(x2, y2, new Function5<Float, TextPosition, TextPage, TextLine, BaseColumn, Unit>() { // from class: com.ykb.ebook.weight.ContentTextView.click.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(5);
            }

            @Override // kotlin.jvm.functions.Function5
            public /* bridge */ /* synthetic */ Unit invoke(Float f2, TextPosition textPosition, TextPage textPage, TextLine textLine, BaseColumn baseColumn) {
                invoke(f2.floatValue(), textPosition, textPage, textLine, baseColumn);
                return Unit.INSTANCE;
            }

            public final void invoke(float f2, @NotNull TextPosition textPos, @NotNull TextPage textPage, @NotNull TextLine textLine, @NotNull BaseColumn column) {
                Intrinsics.checkNotNullParameter(textPos, "textPos");
                Intrinsics.checkNotNullParameter(textPage, "textPage");
                Intrinsics.checkNotNullParameter(textLine, "textLine");
                Intrinsics.checkNotNullParameter(column, "column");
                boolean z2 = true;
                if (column instanceof ButtonColumn) {
                    booleanRef.element = true;
                    return;
                }
                if (column instanceof ReviewColumn) {
                    if (ViewExtensionsKt.doubleClick()) {
                        ReviewColumn reviewColumn = (ReviewColumn) column;
                        String str = (String) StringsKt__StringsKt.split$default((CharSequence) reviewColumn.getMList().get(reviewColumn.getIndex() - 1), new String[]{","}, false, 0, 6, (Object) null).get(0);
                        String id = textPage.textChapter.getChapter().getId();
                        boolean zIsPay = textPage.textChapter.getChapter().isPay();
                        Ref.BooleanRef booleanRef2 = booleanRef;
                        if (zIsPay) {
                            this.callBack.onReviewClick(str, id);
                        } else {
                            z2 = false;
                        }
                        booleanRef2.element = z2;
                        return;
                    }
                    return;
                }
                if (column instanceof ImageColumn) {
                    if (ReadConfig.INSTANCE.getPreviewImageByClick() && ViewExtensionsKt.doubleClick()) {
                        Ref.BooleanRef booleanRef3 = booleanRef;
                        if (textPage.textChapter.getChapter().isPay()) {
                            ImageColumn imageColumn = (ImageColumn) column;
                            String strSubstring = imageColumn.getSrcList().get(imageColumn.getIndex() - 1);
                            if (StringsKt__StringsKt.contains$default((CharSequence) strSubstring, (CharSequence) "（", false, 2, (Object) null)) {
                                strSubstring = strSubstring.substring(0, StringsKt__StringsKt.indexOf$default((CharSequence) strSubstring, "（", 0, false, 6, (Object) null));
                                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                            }
                            this.callBack.onImageClick(strSubstring);
                        } else {
                            z2 = false;
                        }
                        booleanRef3.element = z2;
                        return;
                    }
                    return;
                }
                if (column instanceof QuestionColumn) {
                    QuestionColumn questionColumn = (QuestionColumn) column;
                    if (!questionColumn.getIds().isEmpty() && ViewExtensionsKt.doubleClick()) {
                        boolean zIsPay2 = textPage.textChapter.getChapter().isPay();
                        Ref.BooleanRef booleanRef4 = booleanRef;
                        if (!zIsPay2) {
                            z2 = false;
                        } else if (!questionColumn.getIds().isEmpty()) {
                            this.callBack.onQuestionClick(questionColumn.getIds().size() > 1 ? CollectionsKt___CollectionsKt.joinToString$default(questionColumn.getIds(), ",", null, null, 0, null, null, 62, null) : questionColumn.getIds().get(0));
                        }
                        booleanRef4.element = z2;
                        return;
                    }
                    return;
                }
                if (column instanceof TextColumn) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("点击的TextColumn：charData: ");
                    TextColumn textColumn = (TextColumn) column;
                    sb.append(textColumn.getCharData());
                    sb.append("  -- charIndex: ");
                    sb.append(textColumn.getCharIndex());
                    Log.d("TEST", sb.toString());
                    if (!textPage.textChapter.getChapter().isPay()) {
                        booleanRef.element = false;
                        return;
                    }
                    if (!textColumn.getIsDrawUnderLine()) {
                        if (!textColumn.getIsDrawDashLine()) {
                            booleanRef.element = false;
                            return;
                        }
                        Iterator<T> it = textPage.textChapter.getPages().iterator();
                        while (it.hasNext()) {
                            Iterator<T> it2 = ((TextPage) it.next()).getLines().iterator();
                            while (it2.hasNext()) {
                                for (BaseColumn baseColumn : ((TextLine) it2.next()).getColumns()) {
                                    if (baseColumn instanceof TextColumn) {
                                        TextColumn textColumn2 = (TextColumn) baseColumn;
                                        textColumn2.setSelected(Intrinsics.areEqual(textColumn2.getDashId(), textColumn.getDashId()));
                                    }
                                }
                            }
                        }
                        this.postInvalidate();
                        this.callBack.onNoteClick(textPage.textChapter.getChapter().getId(), textColumn.getDashStartPosition(), textColumn.getDashLength(), textColumn.getDashContent(), textColumn.getDashId(), false, x2, y2);
                        booleanRef.element = true;
                        return;
                    }
                    Iterator<T> it3 = textPage.textChapter.getPages().iterator();
                    int lineColor = 0;
                    while (it3.hasNext()) {
                        Iterator<T> it4 = ((TextPage) it3.next()).getLines().iterator();
                        while (it4.hasNext()) {
                            for (BaseColumn baseColumn2 : ((TextLine) it4.next()).getColumns()) {
                                if (baseColumn2 instanceof TextColumn) {
                                    TextColumn textColumn3 = (TextColumn) baseColumn2;
                                    if (Intrinsics.areEqual(textColumn3.getLineId(), textColumn.getLineId())) {
                                        textColumn3.setSelected(!textColumn3.getSelected());
                                        lineColor = textColumn3.getLineColor();
                                    } else {
                                        textColumn3.setSelected(false);
                                    }
                                }
                            }
                        }
                    }
                    BaseColumn clickDrawUnderLineFirstIndex = this.getClickDrawUnderLineFirstIndex(textColumn);
                    if (clickDrawUnderLineFirstIndex instanceof TextColumn) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("点击开始的第一个index:：");
                        TextColumn textColumn4 = (TextColumn) clickDrawUnderLineFirstIndex;
                        sb2.append(textColumn4.getCharData());
                        System.out.println((Object) sb2.toString());
                        this.callBack.showDrawLineTextActionMenu(textColumn4, false, lineColor, 0);
                    }
                    this.postInvalidate();
                    booleanRef.element = true;
                }
            }
        });
        return booleanRef.element;
    }

    @Override // android.view.View
    public void computeScroll() {
        PageDelegate pageDelegate = getPageDelegate();
        if (pageDelegate != null) {
            pageDelegate.computeScroll();
        }
    }

    public final int countRemovedNewlines(@NotNull String text1, @NotNull String text2) {
        Intrinsics.checkNotNullParameter(text1, "text1");
        Intrinsics.checkNotNullParameter(text2, "text2");
        List mutableList = StringsKt___StringsKt.toMutableList(text1);
        int size = mutableList.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            char cCharValue = ((Character) mutableList.get(i3)).charValue();
            if (Intrinsics.areEqual(String.valueOf(cCharValue), "@") || Intrinsics.areEqual(String.valueOf(cCharValue), ReadConfig.REVIEW_CHAR) || Intrinsics.areEqual(String.valueOf(cCharValue), ReadConfig.QUESTION_SINGLE_CHAR) || Intrinsics.areEqual(String.valueOf(cCharValue), ReadConfig.QUESTION_MULTI_CHAR) || cCharValue == '\t' || cCharValue == '\n' || cCharValue == '\r' || Intrinsics.areEqual(String.valueOf(cCharValue), "\\f") || Intrinsics.areEqual(String.valueOf(cCharValue), "\\v")) {
                i2++;
                String strSubstring = text1.substring(0, i3);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                if (StringsKt__StringsKt.contains$default((CharSequence) StringExtensionsKt.formatContent(strSubstring), (CharSequence) text2, false, 2, (Object) null)) {
                    break;
                }
            }
        }
        return i2 - 1;
    }

    public final int countSpecialCharacters(@NotNull String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return SequencesKt___SequencesKt.count(Regex.findAll$default(new Regex("[@▨▧▦\\t\\n\\r\\f\\v]"), input, 0, 2, null));
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int action = event.getAction();
        if (action == 0) {
            this.longScreenshot = true;
            setScrollY(0);
        } else if (action == 1) {
            this.longScreenshot = false;
            setScrollY(0);
        }
        return this.callBack.onLongScreenshotTouchEvent(event);
    }

    public final void drawDashLine() {
        Iterator<T> it = this.textPage.getLines().iterator();
        while (it.hasNext()) {
            for (BaseColumn baseColumn : ((TextLine) it.next()).getColumns()) {
                if (baseColumn instanceof TextColumn) {
                    TextColumn textColumn = (TextColumn) baseColumn;
                    if (textColumn.getSelected()) {
                        textColumn.setDrawDashLine(true);
                        textColumn.setDashColor(AppCtxKt.getAppCtx().getColor(R.color.color_ffbe5d));
                    }
                }
            }
        }
        postInvalidate();
    }

    public final void drawUnderLine(boolean drawOrCancel, int color, int style, @NotNull String partLineText) {
        Intrinsics.checkNotNullParameter(partLineText, "partLineText");
        Iterator<T> it = this.textPage.getLines().iterator();
        while (it.hasNext()) {
            for (BaseColumn baseColumn : ((TextLine) it.next()).getColumns()) {
                if (baseColumn instanceof TextColumn) {
                    TextColumn textColumn = (TextColumn) baseColumn;
                    if (textColumn.getSelected()) {
                        String strReplace$default = StringsKt__StringsJVMKt.replace$default(textColumn.getCharData(), ReadConfig.INDENT_CHAR, "", false, 4, (Object) null);
                        if (drawOrCancel) {
                            if (strReplace$default.length() > 0) {
                                textColumn.setDrawUnderLine(true);
                                textColumn.setLineColor(color);
                                textColumn.setLineStyle(style);
                            }
                        }
                        textColumn.setDrawUnderLine(false);
                        textColumn.setLineColor(-1);
                        textColumn.setLineStyle(-1);
                    }
                }
            }
        }
        postInvalidate();
    }

    @Nullable
    public final TextLine getCurVisibleFirstLine() {
        for (int i2 = 0; i2 < 3; i2++) {
            float fRelativeOffset = relativeOffset(i2);
            if (i2 > 0 && (!this.callBack.isScroll() || fRelativeOffset >= ChapterProvider.getVisibleHeight())) {
                return null;
            }
            List<TextLine> lines = relativePage(i2).getLines();
            int size = lines.size();
            for (int i3 = 0; i3 < size; i3++) {
                TextLine textLine = lines.get(i3);
                if (textLine.isVisible(fRelativeOffset)) {
                    TextLine textLineCopy = textLine.copy((524287 & 1) != 0 ? textLine.text : null, (524287 & 2) != 0 ? textLine.textColumns : null, (524287 & 4) != 0 ? textLine.lineTop : 0.0f, (524287 & 8) != 0 ? textLine.lineBase : 0.0f, (524287 & 16) != 0 ? textLine.lineBottom : 0.0f, (524287 & 32) != 0 ? textLine.indentWidth : 0.0f, (524287 & 64) != 0 ? textLine.paragraphNum : 0, (524287 & 128) != 0 ? textLine.chapterPosition : 0, (524287 & 256) != 0 ? textLine.pagePosition : 0, (524287 & 512) != 0 ? textLine.isTitle : false, (524287 & 1024) != 0 ? textLine.isParagraphEnd : false, (524287 & 2048) != 0 ? textLine.isImage : false, (524287 & 4096) != 0 ? textLine.startX : 0.0f, (524287 & 8192) != 0 ? textLine.indentSize : 0, (524287 & 16384) != 0 ? textLine.extraLetterSpacing : 0.0f, (524287 & 32768) != 0 ? textLine.extraLetterSpacingOffsetX : 0.0f, (524287 & 65536) != 0 ? textLine.wordSpacing : 0.0f, (524287 & 131072) != 0 ? textLine.exceed : false, (524287 & 262144) != 0 ? textLine.onlyTextColumn : false);
                    textLineCopy.setLineTop(textLineCopy.getLineTop() + fRelativeOffset);
                    textLineCopy.setLineBottom(textLineCopy.getLineBottom() + fRelativeOffset);
                    return textLineCopy;
                }
            }
        }
        return null;
    }

    @NotNull
    public final TextPage getCurVisiblePage() {
        TextPage textPage = new TextPage(0, null, null, null, 0, 0, 0.0f, 0, 255, null);
        for (int i2 = 0; i2 < 3; i2++) {
            float fRelativeOffset = relativeOffset(i2);
            if (i2 > 0 && (!this.callBack.isScroll() || fRelativeOffset >= ChapterProvider.getVisibleHeight())) {
                break;
            }
            List<TextLine> lines = relativePage(i2).getLines();
            int size = lines.size();
            for (int i3 = 0; i3 < size; i3++) {
                TextLine textLine = lines.get(i3);
                if (textLine.isVisible(fRelativeOffset)) {
                    TextLine textLineCopy = textLine.copy((524287 & 1) != 0 ? textLine.text : null, (524287 & 2) != 0 ? textLine.textColumns : null, (524287 & 4) != 0 ? textLine.lineTop : 0.0f, (524287 & 8) != 0 ? textLine.lineBase : 0.0f, (524287 & 16) != 0 ? textLine.lineBottom : 0.0f, (524287 & 32) != 0 ? textLine.indentWidth : 0.0f, (524287 & 64) != 0 ? textLine.paragraphNum : 0, (524287 & 128) != 0 ? textLine.chapterPosition : 0, (524287 & 256) != 0 ? textLine.pagePosition : 0, (524287 & 512) != 0 ? textLine.isTitle : false, (524287 & 1024) != 0 ? textLine.isParagraphEnd : false, (524287 & 2048) != 0 ? textLine.isImage : false, (524287 & 4096) != 0 ? textLine.startX : 0.0f, (524287 & 8192) != 0 ? textLine.indentSize : 0, (524287 & 16384) != 0 ? textLine.extraLetterSpacing : 0.0f, (524287 & 32768) != 0 ? textLine.extraLetterSpacingOffsetX : 0.0f, (524287 & 65536) != 0 ? textLine.wordSpacing : 0.0f, (524287 & 131072) != 0 ? textLine.exceed : false, (524287 & 262144) != 0 ? textLine.onlyTextColumn : false);
                    textLineCopy.setLineTop(textLineCopy.getLineTop() + fRelativeOffset);
                    textLineCopy.setLineBottom(textLineCopy.getLineBottom() + fRelativeOffset);
                    textPage.addLine(textLineCopy);
                }
            }
        }
        return textPage;
    }

    @NotNull
    public final Paint getImagePaint() {
        return (Paint) this.imagePaint.getValue();
    }

    public final boolean getLongScreenshot() {
        return this.longScreenshot;
    }

    public final boolean getReverseEndCursor() {
        return this.reverseEndCursor;
    }

    public final boolean getReverseStartCursor() {
        return this.reverseStartCursor;
    }

    @NotNull
    public final Paint getSelectedPaint() {
        return (Paint) this.selectedPaint.getValue();
    }

    @NotNull
    public final String getSelectedText() {
        TextPosition textPosition = new TextPosition(0, 0, 0, false, false, 24, null);
        StringBuilder sb = new StringBuilder();
        int relativePagePosition = this.selectStart.getRelativePagePosition();
        int relativePagePosition2 = this.selectEnd.getRelativePagePosition();
        if (relativePagePosition <= relativePagePosition2) {
            while (true) {
                TextPage textPageRelativePage = relativePage(relativePagePosition);
                textPosition.setRelativePagePosition(relativePagePosition);
                Iterator it = textPageRelativePage.getLines().iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    Object next = it.next();
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    TextLine textLine = (TextLine) next;
                    textLine.getText();
                    textPosition.setLineIndex(i2);
                    int i4 = 0;
                    for (Object obj : textLine.getColumns()) {
                        int i5 = i4 + 1;
                        if (i4 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        BaseColumn baseColumn = (BaseColumn) obj;
                        textPosition.setColumnIndex(i4);
                        int iCompare = textPosition.compare(this.selectStart);
                        int iCompare2 = textPosition.compare(this.selectEnd);
                        Iterator it2 = it;
                        if (!(baseColumn instanceof TextColumn)) {
                            boolean z2 = baseColumn instanceof QuestionColumn;
                            if (z2 || (baseColumn instanceof ImageColumn) || (baseColumn instanceof ReviewColumn)) {
                                if (iCompare > 0 && iCompare2 < 0) {
                                    sb.append(z2 ? ReadConfig.QUESTION_MULTI_CHAR : baseColumn instanceof ImageColumn ? "@" : ReadConfig.REVIEW_CHAR);
                                    if (textLine.isParagraphEnd() && i4 == textLine.getCharSize()) {
                                        sb.append("\n");
                                    } else if (textLine.getColumns().size() == 1) {
                                        if (textLine.getColumns().get(0) instanceof ImageColumn) {
                                            sb.append("\n");
                                        }
                                    }
                                } else if (iCompare == 0) {
                                    if (this.selectStart.isTouch()) {
                                        sb.append("");
                                    }
                                    if (textLine.isParagraphEnd() && i4 == textLine.getCharSize() - 1 && iCompare2 != 0) {
                                        sb.append("\n");
                                    }
                                } else if (iCompare2 == 0 && (this.selectEnd.isTouch() || this.selectEnd.isLast())) {
                                    sb.append("");
                                }
                            }
                            i4 = i5;
                            it = it2;
                        } else if (iCompare == 0) {
                            if (this.selectStart.isTouch()) {
                                TextColumn textColumn = (TextColumn) baseColumn;
                                if (textColumn.getCharIndex() != -1) {
                                    sb.append(textColumn.getCharData());
                                }
                            }
                            if (textLine.isParagraphEnd() && i4 == textLine.getCharSize() - 1 && iCompare2 != 0) {
                                sb.append("\n");
                            }
                        } else if (iCompare2 == 0) {
                            if (this.selectEnd.isTouch() || this.selectEnd.isLast()) {
                                TextColumn textColumn2 = (TextColumn) baseColumn;
                                if (textColumn2.getCharIndex() != -1) {
                                    sb.append(textColumn2.getCharData());
                                }
                            }
                        } else if (iCompare > 0 && iCompare2 < 0) {
                            TextColumn textColumn3 = (TextColumn) baseColumn;
                            if (textColumn3.getCharIndex() != -1) {
                                sb.append(textColumn3.getCharData());
                            }
                            if (textLine.isParagraphEnd() && i4 == textLine.getCharSize() - 1 && (textLine.getColumn(textLine.getCharSize()) instanceof TextColumn)) {
                                sb.append("\n");
                            }
                        }
                        i4 = i5;
                        it = it2;
                    }
                    i2 = i3;
                }
                if (relativePagePosition == relativePagePosition2) {
                    break;
                }
                relativePagePosition++;
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "builder.toString()");
        return string;
    }

    @NotNull
    public final TextPage getTextPage() {
        return this.textPage;
    }

    /* renamed from: isMainView, reason: from getter */
    public final boolean getIsMainView() {
        return this.isMainView;
    }

    public final void longPress(final float x2, final float y2, @NotNull final Function1<? super TextPosition, Unit> select) {
        Intrinsics.checkNotNullParameter(select, "select");
        touch(x2, y2, new Function5<Float, TextPosition, TextPage, TextLine, BaseColumn, Unit>() { // from class: com.ykb.ebook.weight.ContentTextView.longPress.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(5);
            }

            @Override // kotlin.jvm.functions.Function5
            public /* bridge */ /* synthetic */ Unit invoke(Float f2, TextPosition textPosition, TextPage textPage, TextLine textLine, BaseColumn baseColumn) {
                invoke(f2.floatValue(), textPosition, textPage, textLine, baseColumn);
                return Unit.INSTANCE;
            }

            public final void invoke(float f2, @NotNull TextPosition textPos, @NotNull TextPage textPage, @NotNull TextLine textLine, @NotNull BaseColumn column) {
                Intrinsics.checkNotNullParameter(textPos, "textPos");
                Intrinsics.checkNotNullParameter(textPage, "<anonymous parameter 2>");
                Intrinsics.checkNotNullParameter(textLine, "<anonymous parameter 3>");
                Intrinsics.checkNotNullParameter(column, "column");
                if (column instanceof ImageColumn) {
                    ContentTextView.this.callBack.onImageLongPress(x2, y2, String.valueOf(((ImageColumn) column).getIndex()));
                } else if ((column instanceof TextColumn) && ContentTextView.this.selectAble) {
                    ((TextColumn) column).setSelected(true);
                    select.invoke(textPos);
                }
            }
        });
    }

    @Override // android.view.View
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        if (this.longScreenshot) {
            canvas.translate(0.0f, getScrollY());
        }
        if (!(!this.visibleRect.isEmpty())) {
            throw new IllegalStateException("visibleRect 为空".toString());
        }
        canvas.clipRect(this.visibleRect);
        drawPage(canvas);
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        if (this.isMainView) {
            ChapterProvider.INSTANCE.upViewSize(w2, h2);
            this.textPage.format();
        }
    }

    @NotNull
    public final TextPage relativePage(int relativePos) {
        return relativePos != 0 ? relativePos != 1 ? getPageFactory().getNextPlusPage() : getPageFactory().getNextPage() : this.textPage;
    }

    public final void resetPageOffset() {
        this.pageOffset = 0;
    }

    public final void resetReverseCursor() {
        this.reverseStartCursor = false;
        this.reverseEndCursor = false;
    }

    public final void scroll(int mOffset) {
        int i2;
        this.pageOffset += mOffset;
        if (this.longScreenshot) {
            setScrollY(getScrollY() + (-mOffset));
        }
        if (!getPageFactory().hasPrev() && this.pageOffset > 0) {
            this.pageOffset = 0;
            PageDelegate pageDelegate = getPageDelegate();
            if (pageDelegate != null) {
                pageDelegate.abortAnim();
            }
        } else if (getPageFactory().hasNext() || (i2 = this.pageOffset) >= 0 || i2 + this.textPage.getHeight() >= ChapterProvider.getVisibleHeight()) {
            int i3 = this.pageOffset;
            if (i3 > 0) {
                if (!this.textPage.textChapter.getChapter().isPay()) {
                    ReadBook.moveToPrevChapter$default(ReadBook.INSTANCE, true, true, false, 4, null);
                } else if (getPageFactory().moveToPrev(true)) {
                    this.pageOffset -= (int) this.textPage.getHeight();
                } else {
                    this.pageOffset = 0;
                    PageDelegate pageDelegate2 = getPageDelegate();
                    if (pageDelegate2 != null) {
                        pageDelegate2.abortAnim();
                    }
                }
            } else if (i3 < (-this.textPage.getHeight())) {
                if (this.textPage.textChapter.getChapter().isPay()) {
                    float height = this.textPage.getHeight();
                    if (getPageFactory().moveToNext(true)) {
                        this.pageOffset += (int) height;
                    } else {
                        this.pageOffset = -((int) height);
                        PageDelegate pageDelegate3 = getPageDelegate();
                        if (pageDelegate3 != null) {
                            pageDelegate3.abortAnim();
                        }
                    }
                } else {
                    ReadBook.moveToNextChapter$default(ReadBook.INSTANCE, true, false, 2, null);
                }
            }
        } else {
            this.pageOffset = Math.min(0, (int) (ChapterProvider.getVisibleHeight() - this.textPage.getHeight()));
            PageDelegate pageDelegate4 = getPageDelegate();
            if (pageDelegate4 != null) {
                pageDelegate4.abortAnim();
            }
        }
        postInvalidate();
    }

    public final void selectEndMove(float x2, float y2) {
        touchRough(x2, y2, new Function5<Float, TextPosition, TextPage, TextLine, BaseColumn, Unit>() { // from class: com.ykb.ebook.weight.ContentTextView.selectEndMove.1
            {
                super(5);
            }

            @Override // kotlin.jvm.functions.Function5
            public /* bridge */ /* synthetic */ Unit invoke(Float f2, TextPosition textPosition, TextPage textPage, TextLine textLine, BaseColumn baseColumn) {
                invoke(f2.floatValue(), textPosition, textPage, textLine, baseColumn);
                return Unit.INSTANCE;
            }

            public final void invoke(float f2, @NotNull TextPosition textPos, @NotNull TextPage textPage, @NotNull TextLine textLine, @NotNull BaseColumn textColumn) {
                Intrinsics.checkNotNullParameter(textPos, "textPos");
                Intrinsics.checkNotNullParameter(textPage, "<anonymous parameter 2>");
                Intrinsics.checkNotNullParameter(textLine, "textLine");
                Intrinsics.checkNotNullParameter(textColumn, "textColumn");
                if (textPos.compare(ContentTextView.this.selectEnd) == 0) {
                    return;
                }
                com.ykb.ebook.util.Log log = com.ykb.ebook.util.Log.INSTANCE;
                log.logD("textPos", " columnIndex =" + textPos.getColumnIndex() + " relativePagePosition = " + textPos.getRelativePagePosition() + " line = " + textPos.getLineIndex() + "  ");
                if (textPos.compare(ContentTextView.this.selectStart) >= 0) {
                    ContentTextView.this.selectEnd.upData(textPos);
                    ContentTextView contentTextView = ContentTextView.this;
                    contentTextView.upSelectedEnd((contentTextView.selectEnd.isTouch() || ContentTextView.this.selectEnd.isLast()) ? textColumn.getEnd() : textColumn.getStart(), textLine.getLineBottom() + f2);
                } else {
                    log.logD("selectEndMove", "< 0");
                    ContentTextView.this.setReverseEndCursor(true);
                    ContentTextView.this.setReverseStartCursor(false);
                    ContentTextView contentTextView2 = ContentTextView.this;
                    contentTextView2.selectEndMoveIndex(contentTextView2.selectStart);
                    ContentTextView.this.selectStart.upData(textPos);
                    ContentTextView.this.upSelectedStart(textPos.isTouch() ? textColumn.getStart() : textColumn.getEnd(), textLine.getLineBottom() + f2, textLine.getLineTop() + f2);
                }
                ContentTextView.this.upSelectChars();
            }
        });
    }

    public final void selectEndMoveIndex(int relativePage, int lineIndex, int charIndex, boolean isTouch, boolean isLast) {
        this.selectEnd.setRelativePagePosition(relativePage);
        this.selectEnd.setLineIndex(lineIndex);
        this.selectEnd.setColumnIndex(charIndex);
        this.selectEnd.setTouch(isTouch);
        this.selectEnd.setLast(isLast);
        TextLine line = relativePage(relativePage).getLine(lineIndex);
        upSelectedEnd(line.getColumn(charIndex).getEnd(), line.getLineBottom() + relativeOffset(relativePage));
        upSelectChars();
    }

    public final void selectStartMove(float x2, float y2) {
        touchRough(x2, y2, new Function5<Float, TextPosition, TextPage, TextLine, BaseColumn, Unit>() { // from class: com.ykb.ebook.weight.ContentTextView.selectStartMove.1
            {
                super(5);
            }

            @Override // kotlin.jvm.functions.Function5
            public /* bridge */ /* synthetic */ Unit invoke(Float f2, TextPosition textPosition, TextPage textPage, TextLine textLine, BaseColumn baseColumn) {
                invoke(f2.floatValue(), textPosition, textPage, textLine, baseColumn);
                return Unit.INSTANCE;
            }

            public final void invoke(float f2, @NotNull TextPosition textPos, @NotNull TextPage textPage, @NotNull TextLine textLine, @NotNull BaseColumn textColumn) {
                Intrinsics.checkNotNullParameter(textPos, "textPos");
                Intrinsics.checkNotNullParameter(textPage, "<anonymous parameter 2>");
                Intrinsics.checkNotNullParameter(textLine, "textLine");
                Intrinsics.checkNotNullParameter(textColumn, "textColumn");
                if (ContentTextView.this.selectStart.compare(textPos) == 0) {
                    return;
                }
                if (textPos.compare(ContentTextView.this.selectEnd) <= 0) {
                    if (textColumn instanceof TextColumn) {
                        ((TextColumn) textColumn).getCharIndex();
                    }
                    ContentTextView.this.selectStart.upData(textPos);
                    ContentTextView.this.upSelectedStart(textPos.isTouch() ? textColumn.getStart() : textColumn.getEnd(), textLine.getLineBottom() + f2, textLine.getLineTop() + f2);
                } else {
                    ContentTextView.this.setReverseStartCursor(true);
                    ContentTextView.this.setReverseEndCursor(false);
                    ContentTextView contentTextView = ContentTextView.this;
                    contentTextView.selectStartMoveIndex(contentTextView.selectEnd);
                    ContentTextView.this.selectEnd.upData(textPos);
                    ContentTextView contentTextView2 = ContentTextView.this;
                    contentTextView2.upSelectedEnd((contentTextView2.selectEnd.isTouch() || ContentTextView.this.selectEnd.isLast()) ? textColumn.getEnd() : textColumn.getStart(), textLine.getLineBottom() + f2);
                }
                ContentTextView.this.upSelectChars();
            }
        });
    }

    public final void selectStartMoveIndex(int relativePagePos, int lineIndex, int charIndex, boolean isTouch, boolean isLast) {
        this.selectStart.setRelativePagePosition(relativePagePos);
        this.selectStart.setLineIndex(lineIndex);
        this.selectStart.setColumnIndex(charIndex);
        this.selectStart.setTouch(isTouch);
        this.selectStart.setLast(isLast);
        TextLine line = relativePage(relativePagePos).getLine(lineIndex);
        upSelectedStart(line.getColumn(charIndex).getStart(), line.getLineBottom() + relativeOffset(relativePagePos), line.getLineTop() + relativeOffset(relativePagePos));
        upSelectChars();
    }

    public final void selectText(float x2, float y2, @NotNull final Function1<? super TextPosition, Unit> select) {
        Intrinsics.checkNotNullParameter(select, "select");
        touchRough(x2, y2, new Function5<Float, TextPosition, TextPage, TextLine, BaseColumn, Unit>() { // from class: com.ykb.ebook.weight.ContentTextView.selectText.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(5);
            }

            @Override // kotlin.jvm.functions.Function5
            public /* bridge */ /* synthetic */ Unit invoke(Float f2, TextPosition textPosition, TextPage textPage, TextLine textLine, BaseColumn baseColumn) {
                invoke(f2.floatValue(), textPosition, textPage, textLine, baseColumn);
                return Unit.INSTANCE;
            }

            public final void invoke(float f2, @NotNull TextPosition textPos, @NotNull TextPage textPage, @NotNull TextLine textLine, @NotNull BaseColumn column) {
                Intrinsics.checkNotNullParameter(textPos, "textPos");
                Intrinsics.checkNotNullParameter(textPage, "<anonymous parameter 2>");
                Intrinsics.checkNotNullParameter(textLine, "<anonymous parameter 3>");
                Intrinsics.checkNotNullParameter(column, "column");
                if (column instanceof TextColumn) {
                    ((TextColumn) column).setSelected(true);
                    select.invoke(textPos);
                }
            }
        });
    }

    public final void setContent(@NotNull TextPage textPage, boolean updateTheme) {
        Intrinsics.checkNotNullParameter(textPage, "textPage");
        this.updateTheme = updateTheme;
        initDrawLineAndDash(textPage);
        this.textPage = textPage;
        if (this.isScroll) {
            postInvalidate();
        } else {
            invalidate();
        }
    }

    public final void setIsScroll(boolean value) {
        this.isScroll = value;
    }

    public final void setLongPressParagraphChars(@NotNull ArrayList<Integer> c3) {
        Intrinsics.checkNotNullParameter(c3, "c");
        this.flag = true;
        this.longPressParagraphChars.clear();
        this.longPressParagraphChars.addAll(c3);
    }

    public final void setLongScreenshot(boolean z2) {
        this.longScreenshot = z2;
    }

    public final void setMainView(boolean z2) {
        this.isMainView = z2;
    }

    public final void setReverseEndCursor(boolean z2) {
        this.reverseEndCursor = z2;
    }

    public final void setReverseStartCursor(boolean z2) {
        this.reverseStartCursor = z2;
    }

    public final void submitRenderTask() {
        INSTANCE.getRenderThread().submit(getRenderRunnable());
    }

    public final void selectEndMoveIndex(@NotNull TextPosition textPos) {
        Intrinsics.checkNotNullParameter(textPos, "textPos");
        selectEndMoveIndex(textPos.getRelativePagePosition(), textPos.getLineIndex(), textPos.getColumnIndex(), textPos.isTouch(), textPos.isLast());
    }

    public final void selectStartMoveIndex(@NotNull TextPosition textPos) {
        Intrinsics.checkNotNullParameter(textPos, "textPos");
        selectStartMoveIndex(textPos.getRelativePagePosition(), textPos.getLineIndex(), textPos.getColumnIndex(), textPos.isTouch(), textPos.isLast());
    }
}
