package com.ykb.ebook.weight;

import android.annotation.SuppressLint;
import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.ColorResourcesKt;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.KeyEventDispatcher;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.psychiatrygarden.utils.Constants;
import com.tencent.connect.common.Constants;
import com.umeng.analytics.pro.am;
import com.ykb.ebook.R;
import com.ykb.ebook.activity.ReadBookActivity;
import com.ykb.ebook.common.PreferKeyKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.DataSource;
import com.ykb.ebook.common_interface.LayoutProgressListener;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.model.TextChapter;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.model.TextPage;
import com.ykb.ebook.model.TextPosition;
import com.ykb.ebook.page.ChapterProvider;
import com.ykb.ebook.page.PageDirection;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.page.TextPageFactory;
import com.ykb.ebook.page.TextParagraph;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.page.column.ImageColumn;
import com.ykb.ebook.page.column.QuestionColumn;
import com.ykb.ebook.page.column.ReviewColumn;
import com.ykb.ebook.page.column.TextColumn;
import com.ykb.ebook.page.delegate.CoverPageDelegate;
import com.ykb.ebook.page.delegate.NoAnimPageDelegate;
import com.ykb.ebook.page.delegate.PageDelegate;
import com.ykb.ebook.page.delegate.ScrollPageDelegate;
import com.ykb.ebook.page.delegate.SimulationPageDelegate;
import com.ykb.ebook.page.delegate.VerticalSlidePageDelegate;
import com.ykb.ebook.page.pool.BitmapPool;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.util.StatusBarUtil;
import com.ykb.ebook.util.Throttle;
import com.ykb.ebook.util.ThrottleKt;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.StringsKt___StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000ð\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u0011\n\u0002\b\u0017\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\u0002Ü\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u008b\u0001\u001a\u00030\u008c\u0001J\u0013\u0010\u008d\u0001\u001a\u00030\u008c\u00012\u0007\u0010\u008e\u0001\u001a\u00020\"H\u0002J\n\u0010\u008f\u0001\u001a\u00030\u008c\u0001H\u0016J\u001b\u0010\u0090\u0001\u001a\u00020\"2\b\u0010\u0091\u0001\u001a\u00030\u0092\u00012\b\u0010\u0093\u0001\u001a\u00030\u0092\u0001J\u001e\u0010\u0094\u0001\u001a\u00030\u0095\u00012\u0007\u0010\u0096\u0001\u001a\u00020'2\t\b\u0002\u0010\u0097\u0001\u001a\u00020-H\u0002J\u0014\u0010\u0098\u0001\u001a\u00030\u008c\u00012\b\u0010\u0099\u0001\u001a\u00030\u009a\u0001H\u0014J\u0011\u0010\u009b\u0001\u001a\u00020-2\b\u0010\u009c\u0001\u001a\u00030\u009d\u0001J\u0007\u0010\u009e\u0001\u001a\u00020\"J\b\u0010\u009f\u0001\u001a\u00030 \u0001J\b\u0010¡\u0001\u001a\u00030\u0092\u0001J\u0014\u0010¢\u0001\u001a\u00030\u008c\u00012\b\u0010£\u0001\u001a\u00030\u0092\u0001H\u0002J\t\u0010¤\u0001\u001a\u00020-H\u0016J\t\u0010¥\u0001\u001a\u00020-H\u0016J\n\u0010¦\u0001\u001a\u00030\u008c\u0001H\u0002J\t\u0010§\u0001\u001a\u00020-H\u0002J\u0007\u0010¨\u0001\u001a\u00020-J$\u0010©\u0001\u001a\u00020-2\u0007\u0010\u0096\u0001\u001a\u00020'2\u0007\u0010ª\u0001\u001a\u00020\"2\u0007\u0010«\u0001\u001a\u00020\"H\u0002J\b\u0010¬\u0001\u001a\u00030\u008c\u0001J\u0013\u0010\u00ad\u0001\u001a\u00020-2\b\u0010®\u0001\u001a\u00030¯\u0001H\u0016J\u001d\u0010°\u0001\u001a\u00030\u008c\u00012\u0007\u0010±\u0001\u001a\u00020\"2\b\u0010²\u0001\u001a\u00030 \u0001H\u0016J\n\u0010³\u0001\u001a\u00030\u008c\u0001H\u0002J\b\u0010´\u0001\u001a\u00030\u008c\u0001J\b\u0010µ\u0001\u001a\u00030\u008c\u0001J\b\u0010¶\u0001\u001a\u00030\u008c\u0001J\n\u0010·\u0001\u001a\u00030\u008c\u0001H\u0002J.\u0010¸\u0001\u001a\u00030\u008c\u00012\u0007\u0010¹\u0001\u001a\u00020\"2\u0007\u0010º\u0001\u001a\u00020\"2\u0007\u0010»\u0001\u001a\u00020\"2\u0007\u0010¼\u0001\u001a\u00020\"H\u0014J\u0013\u0010½\u0001\u001a\u00020-2\b\u0010®\u0001\u001a\u00030¯\u0001H\u0017J\b\u0010¾\u0001\u001a\u00030\u008c\u0001J\b\u0010¿\u0001\u001a\u00030\u008c\u0001J'\u0010À\u0001\u001a\u00030\u008c\u00012\u0007\u0010Á\u0001\u001a\u0002062\u0007\u0010Â\u0001\u001a\u0002062\t\b\u0002\u0010Ã\u0001\u001a\u00020-H\u0002J$\u0010Ä\u0001\u001a\u00030\u008c\u00012\u0014\u0010Å\u0001\u001a\u000b\u0012\u0006\b\u0001\u0012\u00020'0Æ\u0001\"\u00020'¢\u0006\u0003\u0010Ç\u0001J\n\u0010È\u0001\u001a\u00030\u008c\u0001H\u0002J%\u0010É\u0001\u001a\u00030\u008c\u00012\u0007\u0010Á\u0001\u001a\u0002062\u0007\u0010Â\u0001\u001a\u0002062\t\b\u0002\u0010Ê\u0001\u001a\u00020-J%\u0010Ë\u0001\u001a\u00030\u008c\u00012\u0007\u0010Á\u0001\u001a\u0002062\u0007\u0010Â\u0001\u001a\u0002062\t\b\u0002\u0010Ê\u0001\u001a\u00020-J\n\u0010Ì\u0001\u001a\u00030\u008c\u0001H\u0002J\u0011\u0010Í\u0001\u001a\u00030\u008c\u00012\u0007\u0010Î\u0001\u001a\u00020\"J\u001c\u0010Ï\u0001\u001a\u00030\u008c\u00012\u0007\u0010Ð\u0001\u001a\u00020\"2\u0007\u0010Ñ\u0001\u001a\u00020-H\u0016J\b\u0010Ò\u0001\u001a\u00030\u008c\u0001J\n\u0010Ó\u0001\u001a\u00030\u008c\u0001H\u0002J\n\u0010Ô\u0001\u001a\u00030\u008c\u0001H\u0002J\b\u0010Õ\u0001\u001a\u00030\u008c\u0001J\b\u0010Ö\u0001\u001a\u00030\u008c\u0001J\b\u0010×\u0001\u001a\u00030\u008c\u0001J\u001e\u0010Ø\u0001\u001a\u00030\u008c\u00012\u0007\u0010Ð\u0001\u001a\u00020\"2\t\b\u0002\u0010Ñ\u0001\u001a\u00020-H\u0002J\n\u0010Ù\u0001\u001a\u00030\u008c\u0001H\u0002J\u0013\u0010Ú\u0001\u001a\u00030\u008c\u00012\t\b\u0002\u0010Û\u0001\u001a\u00020-R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0018\u001a\u00020\u00198FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u0012\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001e\u0010%\u001a\u0012\u0012\u0004\u0012\u00020'0&j\b\u0012\u0004\u0012\u00020'`(X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010,\u001a\u00020-X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010.\"\u0004\b/\u00100R\u000e\u00101\u001a\u00020-X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00102\u001a\u00020-X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010.\"\u0004\b3\u00100R\u000e\u00104\u001a\u00020-X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00105\u001a\u000206X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u001a\u0010;\u001a\u000206X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u00108\"\u0004\b=\u0010:R\u000e\u0010>\u001a\u000206X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020@X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020BX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020-X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010G\u001a\u0004\u0018\u00010\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u0010 R\u001b\u0010I\u001a\u00020\u00198FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bK\u0010\u0012\u001a\u0004\bJ\u0010\u001bR(\u0010N\u001a\u0004\u0018\u00010M2\b\u0010L\u001a\u0004\u0018\u00010M@BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR\u001a\u0010S\u001a\u00020TX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010V\"\u0004\bW\u0010XR\u000e\u0010Y\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010Z\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010$\"\u0004\b\\\u0010]R\u000e\u0010^\u001a\u00020-X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010_\u001a\u00020-X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010`\u001a\u0004\u0018\u00010\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\ba\u0010 R\u001b\u0010b\u001a\u00020\u00198FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bd\u0010\u0012\u001a\u0004\bc\u0010\u001bR\u0013\u0010e\u001a\u0004\u0018\u00010f8F¢\u0006\u0006\u001a\u0004\bg\u0010hR\u000e\u0010i\u001a\u00020jX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010k\u001a\b\u0012\u0004\u0012\u0002060lX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010m\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010$\"\u0004\bo\u0010]R\u001a\u0010p\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010$\"\u0004\br\u0010]R\u001b\u0010s\u001a\u00020\"8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bu\u0010\u0012\u001a\u0004\bt\u0010$R\u001a\u0010v\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bw\u0010$\"\u0004\bx\u0010]R\u001a\u0010y\u001a\u000206X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bz\u00108\"\u0004\b{\u0010:R\u001a\u0010|\u001a\u000206X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b}\u00108\"\u0004\b~\u0010:R\u000e\u0010\u007f\u001a\u000206X\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010\u0080\u0001\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u0081\u0001\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0082\u0001\u001a\u000206X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0083\u0001\u00108\"\u0005\b\u0084\u0001\u0010:R\u001d\u0010\u0085\u0001\u001a\u000206X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0086\u0001\u00108\"\u0005\b\u0087\u0001\u0010:R\u000f\u0010\u0088\u0001\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0089\u0001\u001a\t\u0012\u0004\u0012\u00020-0\u008a\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Ý\u0001"}, d2 = {"Lcom/ykb/ebook/weight/ReadView;", "Landroid/widget/FrameLayout;", "Lcom/ykb/ebook/common_interface/DataSource;", "Lcom/ykb/ebook/common_interface/LayoutProgressListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "bcRect", "Landroid/graphics/RectF;", "blRect", "boundary", "Ljava/text/BreakIterator;", "kotlin.jvm.PlatformType", "getBoundary", "()Ljava/text/BreakIterator;", "boundary$delegate", "Lkotlin/Lazy;", "brRect", com.alipay.sdk.authjs.a.f3170c, "Lcom/ykb/ebook/weight/ReadView$Callback;", "getCallback", "()Lcom/ykb/ebook/weight/ReadView$Callback;", "curPage", "Lcom/ykb/ebook/weight/PageView;", "getCurPage", "()Lcom/ykb/ebook/weight/PageView;", "curPage$delegate", "currentChapter", "Lcom/ykb/ebook/model/TextChapter;", "getCurrentChapter", "()Lcom/ykb/ebook/model/TextChapter;", "defaultAnimationSpeed", "", "getDefaultAnimationSpeed", "()I", "dragCursorViews", "Ljava/util/ArrayList;", "Landroid/view/View;", "Lkotlin/collections/ArrayList;", "functionMenu", "initialTextPos", "Lcom/ykb/ebook/model/TextPosition;", "isAbortAnim", "", "()Z", "setAbortAnim", "(Z)V", "isMove", "isScroll", "setScroll", "isTextSelected", "lastX", "", "getLastX", "()F", "setLastX", "(F)V", "lastY", "getLastY", "setLastY", "lastYPay", "longPressRunnable", "Ljava/lang/Runnable;", "longPressTimeout", "", "longPressed", "mcRect", "mlRect", "mrRect", "nextChapter", "getNextChapter", "nextPage", "getNextPage", "nextPage$delegate", "value", "Lcom/ykb/ebook/page/delegate/PageDelegate;", "pageDelegate", "getPageDelegate", "()Lcom/ykb/ebook/page/delegate/PageDelegate;", "setPageDelegate", "(Lcom/ykb/ebook/page/delegate/PageDelegate;)V", "pageFactory", "Lcom/ykb/ebook/page/TextPageFactory;", "getPageFactory", "()Lcom/ykb/ebook/page/TextPageFactory;", "setPageFactory", "(Lcom/ykb/ebook/page/TextPageFactory;)V", "pageSlopSquare", "pageSlopSquare2", "getPageSlopSquare2", "setPageSlopSquare2", "(I)V", "pressDown", "pressOnTextSelected", "prevChapter", "getPrevChapter", "prevPage", "getPrevPage", "prevPage$delegate", "readBookActivity", "Lcom/ykb/ebook/activity/ReadBookActivity;", "getReadBookActivity", "()Lcom/ykb/ebook/activity/ReadBookActivity;", Constants.PARAM_SCOPE, "Lkotlinx/coroutines/CoroutineScope;", "scrollFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "selectTextPosition", "getSelectTextPosition", "setSelectTextPosition", "selectTextPosition_original", "getSelectTextPosition_original", "setSelectTextPosition_original", "slopSquare", "getSlopSquare", "slopSquare$delegate", "startPosition", "getStartPosition", "setStartPosition", "startX", "getStartX", "setStartX", "startY", "getStartY", "setStartY", "startYPay", "tcRect", "tlRect", "touchX", "getTouchX", "setTouchX", "touchY", "getTouchY", "setTouchY", "trRect", "upProgressThrottle", "Lcom/ykb/ebook/util/Throttle;", "cancelSelect", "", "click", "action", "computeScroll", "countRemovedNewlines", "text1", "", "text2", "createBitMap", "Landroid/graphics/Bitmap;", "view", "firstPage", "dispatchDraw", "canvas", "Landroid/graphics/Canvas;", "fillPage", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lcom/ykb/ebook/page/PageDirection;", "getCurPagePosition", "getCurVisiblePage", "Lcom/ykb/ebook/model/TextPage;", "getSelectText", "handleJumpUnderLineNoteParagraphComment", "jumpDrawLinText", "hasNextChapter", "hasPrevChapter", "invalidateTextPage", "isLastPage", "isLongScreenShot", "isPointInsideView", "pointX", "pointY", "onDestroy", "onInterceptTouchEvent", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "onLayoutPageCompleted", "index", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "onLongPress", "onPageChange", "onScrollAnimStart", "onScrollAnimStop", "onSingleTapUp", "onSizeChanged", "w", "h", "oldw", "oldh", "onTouchEvent", "readFirstPageContent", "resetSelect", "selectText", "x", "y", "includeHeaderHeight", "setCursorView", "v", "", "([Landroid/view/View;)V", "setRect9x", "setStartPoint", "invalidate", "setTouchPoint", "submitRenderTask", "upBattery", am.Z, "upContent", "relativePosition", "resetPageOffset", "upPageAnim", "upPageSlopSquare", "upProgress", "upStatusBar", "upTime", "upTimeShow", "updateContent", "updateFirstLastPageFlipPageShadow", "updateThemeStyle", "refresh", "Callback", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nReadView.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReadView.kt\ncom/ykb/ebook/weight/ReadView\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,1434:1\n1855#2,2:1435\n1855#2,2:1437\n1864#2,2:1439\n1864#2,3:1441\n1866#2:1444\n2634#2:1445\n1855#2,2:1447\n1855#2,2:1449\n1855#2:1451\n1855#2,2:1452\n1856#2:1454\n1864#2,2:1456\n350#2,7:1458\n1866#2:1465\n350#2,7:1466\n350#2,7:1476\n1#3:1446\n1#3:1455\n42#4:1473\n42#4:1474\n42#4:1475\n*S KotlinDebug\n*F\n+ 1 ReadView.kt\ncom/ykb/ebook/weight/ReadView\n*L\n214#1:1435,2\n220#1:1437,2\n439#1:1439,2\n441#1:1441,3\n439#1:1444\n462#1:1445\n470#1:1447,2\n479#1:1449,2\n486#1:1451\n487#1:1452,2\n486#1:1454\n1079#1:1456,2\n1085#1:1458,7\n1079#1:1465\n1105#1:1466,7\n1069#1:1476,7\n462#1:1446\n1256#1:1473\n1257#1:1474\n1258#1:1475\n*E\n"})
/* loaded from: classes8.dex */
public final class ReadView extends FrameLayout implements DataSource, LayoutProgressListener {

    @NotNull
    private final RectF bcRect;

    @NotNull
    private final RectF blRect;

    /* renamed from: boundary$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy boundary;

    @NotNull
    private final RectF brRect;

    /* renamed from: curPage$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy curPage;
    private final int defaultAnimationSpeed;

    @NotNull
    private final ArrayList<View> dragCursorViews;

    @Nullable
    private View functionMenu;

    @NotNull
    private final TextPosition initialTextPos;
    private boolean isAbortAnim;
    private boolean isMove;
    private boolean isScroll;
    private boolean isTextSelected;
    private float lastX;
    private float lastY;
    private float lastYPay;

    @NotNull
    private final Runnable longPressRunnable;
    private final long longPressTimeout;
    private boolean longPressed;

    @NotNull
    private final RectF mcRect;

    @NotNull
    private final RectF mlRect;

    @NotNull
    private final RectF mrRect;

    /* renamed from: nextPage$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy nextPage;

    @Nullable
    private PageDelegate pageDelegate;

    @NotNull
    private TextPageFactory pageFactory;
    private int pageSlopSquare;
    private int pageSlopSquare2;
    private boolean pressDown;
    private boolean pressOnTextSelected;

    /* renamed from: prevPage$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy prevPage;

    @NotNull
    private final CoroutineScope scope;

    @NotNull
    private final MutableStateFlow<Float> scrollFlow;
    private int selectTextPosition;
    private int selectTextPosition_original;

    /* renamed from: slopSquare$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy slopSquare;
    private int startPosition;
    private float startX;
    private float startY;
    private float startYPay;

    @NotNull
    private final RectF tcRect;

    @NotNull
    private final RectF tlRect;
    private float touchX;
    private float touchY;

    @NotNull
    private final RectF trRect;

    @NotNull
    private final Throttle<Boolean> upProgressThrottle;

    @Metadata(d1 = {"\u0000\f\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.weight.ReadView$1", f = "ReadView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.weight.ReadView$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<Float, Continuation<? super Unit>, Object> {
        /* synthetic */ float F$0;
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.F$0 = ((Number) obj).floatValue();
            return anonymousClass1;
        }

        @Nullable
        public final Object invoke(float f2, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(Float.valueOf(f2), continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Float f2, Continuation<? super Unit> continuation) {
            return invoke(f2.floatValue(), continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            float f2 = this.F$0;
            if (f2 > 0.0f) {
                ReadBook.moveToPrevChapter$default(ReadBook.INSTANCE, true, false, false, 4, null);
            } else if (f2 < 0.0f) {
                ReadBook.moveToNextChapter$default(ReadBook.INSTANCE, true, false, 2, null);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0003H&J\b\u0010\b\u001a\u00020\u0006H&J\b\u0010\t\u001a\u00020\u0006H&J\b\u0010\n\u001a\u00020\u0006H&J\u0012\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\u0003H&J6\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u00102\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014H&J\b\u0010\u0015\u001a\u00020\u0006H&J0\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0014H&J\u0018\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006!"}, d2 = {"Lcom/ykb/ebook/weight/ReadView$Callback;", "", "isInitFinish", "", "()Z", "dismissFunctionMenu", "", "isShowActionMenu", "onBookReadOver", "screenOffTimerStart", "showActionMenu", "showHideContinueRead", "show", "showTextActionMenu", "drawOrCancel", "color", "", TtmlNode.TAG_STYLE, "startPosition", "lineId", "", "upSystemUiVisibility", "updateSelectCursor", "startX", "", "startY", "endX", "endY", "selectText", "updateSelectTextPosition", "startTextPosition", "Lcom/ykb/ebook/model/TextPosition;", "endTextPosition", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface Callback {

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public static final class DefaultImpls {
            public static /* synthetic */ void showHideContinueRead$default(Callback callback, boolean z2, int i2, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: showHideContinueRead");
                }
                if ((i2 & 1) != 0) {
                    z2 = false;
                }
                callback.showHideContinueRead(z2);
            }

            public static /* synthetic */ void showTextActionMenu$default(Callback callback, boolean z2, int i2, int i3, int i4, String str, int i5, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: showTextActionMenu");
                }
                if ((i5 & 8) != 0) {
                    i4 = 0;
                }
                int i6 = i4;
                if ((i5 & 16) != 0) {
                    str = null;
                }
                callback.showTextActionMenu(z2, i2, i3, i6, str);
            }
        }

        void dismissFunctionMenu();

        boolean isInitFinish();

        boolean isShowActionMenu();

        void onBookReadOver();

        void screenOffTimerStart();

        void showActionMenu();

        void showHideContinueRead(boolean show);

        void showTextActionMenu(boolean drawOrCancel, int color, int style, int startPosition, @Nullable String lineId);

        void upSystemUiVisibility();

        void updateSelectCursor(float startX, float startY, float endX, float endY, @NotNull String selectText);

        void updateSelectTextPosition(@NotNull TextPosition startTextPosition, @NotNull TextPosition endTextPosition);
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PageDirection.values().length];
            try {
                iArr[PageDirection.PREV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PageDirection.NEXT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadView(@NotNull final Context context, @NotNull AttributeSet attrs) throws SecurityException, NumberFormatException {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        this.pageFactory = new TextPageFactory(this);
        this.prevPage = LazyKt__LazyJVMKt.lazy(new Function0<PageView>() { // from class: com.ykb.ebook.weight.ReadView$prevPage$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final PageView invoke() {
                return new PageView(context);
            }
        });
        this.curPage = LazyKt__LazyJVMKt.lazy(new Function0<PageView>() { // from class: com.ykb.ebook.weight.ReadView$curPage$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final PageView invoke() {
                return new PageView(context);
            }
        });
        this.nextPage = LazyKt__LazyJVMKt.lazy(new Function0<PageView>() { // from class: com.ykb.ebook.weight.ReadView$nextPage$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final PageView invoke() {
                return new PageView(context);
            }
        });
        this.defaultAnimationSpeed = 300;
        this.startPosition = -1;
        this.selectTextPosition = -1;
        this.selectTextPosition_original = -1;
        this.initialTextPos = new TextPosition(0, 0, 0, false, false, 24, null);
        this.slopSquare = LazyKt__LazyJVMKt.lazy(new Function0<Integer>() { // from class: com.ykb.ebook.weight.ReadView$slopSquare$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Integer invoke() {
                return Integer.valueOf(ViewConfiguration.get(context).getScaledTouchSlop());
            }
        });
        int slopSquare = getSlopSquare();
        this.pageSlopSquare = slopSquare;
        this.pageSlopSquare2 = slopSquare * slopSquare;
        this.tlRect = new RectF();
        this.tcRect = new RectF();
        this.trRect = new RectF();
        this.mlRect = new RectF();
        this.mcRect = new RectF();
        this.mrRect = new RectF();
        this.blRect = new RectF();
        this.bcRect = new RectF();
        this.brRect = new RectF();
        this.boundary = LazyKt__LazyJVMKt.lazy(new Function0<BreakIterator>() { // from class: com.ykb.ebook.weight.ReadView$boundary$2
            @Override // kotlin.jvm.functions.Function0
            public final BreakIterator invoke() {
                return BreakIterator.getWordInstance(Locale.getDefault());
            }
        });
        this.upProgressThrottle = ThrottleKt.throttle$default(200L, false, false, new ReadView$upProgressThrottle$1(this), 6, null);
        MutableStateFlow<Float> MutableStateFlow = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this.scrollFlow = MutableStateFlow;
        CoroutineScope coroutineScopeMainScope = CoroutineScopeKt.MainScope();
        this.scope = coroutineScopeMainScope;
        FlowKt.launchIn(FlowKt.onEach(FlowKt.debounce(MutableStateFlow, 500L), new AnonymousClass1(null)), coroutineScopeMainScope);
        this.longPressTimeout = 600L;
        this.longPressRunnable = new Runnable() { // from class: com.ykb.ebook.weight.s0
            @Override // java.lang.Runnable
            public final void run() {
                ReadView.longPressRunnable$lambda$0(this.f26522c);
            }
        };
        addView(getNextPage());
        addView(getCurPage());
        addView(getPrevPage());
        ViewExtensionsKt.invisible(getPrevPage());
        ViewExtensionsKt.invisible(getNextPage());
        getCurPage().markAsMainView();
        if (!isInEditMode()) {
            updateThemeStyle$default(this, false, 1, null);
            setWillNotDraw(false);
            upPageAnim();
            upPageSlopSquare();
        }
        this.dragCursorViews = new ArrayList<>();
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void click(int r8) {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            r2 = 1
            if (r8 == 0) goto Laa
            if (r8 == r2) goto L1f
            r0 = 2
            if (r8 == r0) goto Lc
            goto Lbf
        Lc:
            com.ykb.ebook.common.ReadConfig r8 = com.ykb.ebook.common.ReadConfig.INSTANCE
            boolean r8 = r8.getFirstPage()
            if (r8 != 0) goto Lbf
            com.ykb.ebook.page.delegate.PageDelegate r8 = r7.pageDelegate
            if (r8 == 0) goto Lbf
            int r0 = r7.defaultAnimationSpeed
            r8.prevPageByAnim(r0)
            goto Lbf
        L1f:
            com.ykb.ebook.page.ReadBook r8 = com.ykb.ebook.page.ReadBook.INSTANCE
            com.ykb.ebook.model.BookInfo r3 = r8.getBook()
            if (r3 == 0) goto L32
            java.util.List r3 = r3.getChapterList()
            if (r3 == 0) goto L32
            int r3 = r3.size()
            goto L33
        L32:
            r3 = r1
        L33:
            int r4 = r8.getDurChapterIndex()
            com.ykb.ebook.model.BookInfo r8 = r8.getBook()
            if (r8 == 0) goto L66
            java.util.List r8 = r8.getChapterList()
            if (r8 == 0) goto L66
            java.lang.Iterable r8 = (java.lang.Iterable) r8
            java.util.Iterator r8 = r8.iterator()
        L49:
            boolean r5 = r8.hasNext()
            if (r5 == 0) goto L62
            java.lang.Object r5 = r8.next()
            r6 = r5
            com.ykb.ebook.model.Chapter r6 = (com.ykb.ebook.model.Chapter) r6
            int r6 = r6.getIndex()
            if (r6 != r4) goto L5e
            r6 = r2
            goto L5f
        L5e:
            r6 = r1
        L5f:
            if (r6 == 0) goto L49
            goto L63
        L62:
            r5 = r0
        L63:
            com.ykb.ebook.model.Chapter r5 = (com.ykb.ebook.model.Chapter) r5
            goto L67
        L66:
            r5 = r0
        L67:
            com.ykb.ebook.page.ReadBook r8 = com.ykb.ebook.page.ReadBook.INSTANCE
            com.ykb.ebook.model.BookInfo r4 = r8.getBook()
            if (r4 == 0) goto L73
            java.lang.String r0 = r4.getPass()
        L73:
            java.lang.String r4 = "1"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r0)
            if (r0 != 0) goto L85
            if (r5 == 0) goto L82
            boolean r0 = r5.isPay()
            goto L83
        L82:
            r0 = r1
        L83:
            if (r0 == 0) goto L86
        L85:
            r1 = r2
        L86:
            int r0 = r8.getDurPageIndex()
            if (r0 != 0) goto L99
            int r8 = r8.getDurChapterIndex()
            int r0 = r3 + (-1)
            if (r8 != r0) goto L99
            if (r1 != 0) goto L99
            if (r3 <= r2) goto L99
            return
        L99:
            com.ykb.ebook.weight.ReadView$Callback r8 = r7.getCallback()
            r8.dismissFunctionMenu()
            com.ykb.ebook.page.delegate.PageDelegate r8 = r7.pageDelegate
            if (r8 == 0) goto Lbf
            int r0 = r7.defaultAnimationSpeed
            r8.nextPageByAnim(r0)
            goto Lbf
        Laa:
            com.ykb.ebook.page.delegate.PageDelegate r8 = r7.pageDelegate
            if (r8 == 0) goto Lb1
            r8.dismissSnackBar()
        Lb1:
            com.ykb.ebook.weight.PageView r8 = r7.getCurPage()
            com.ykb.ebook.weight.PageView.cancelSelect$default(r8, r1, r2, r0)
            com.ykb.ebook.weight.ReadView$Callback r8 = r7.getCallback()
            r8.showActionMenu()
        Lbf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.weight.ReadView.click(int):void");
    }

    private final Bitmap createBitMap(View view, boolean firstPage) {
        int statusBarHeight;
        int statusBarHeight2 = 0;
        try {
            view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(view.getHeight(), 1073741824));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            if (firstPage) {
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "context");
                statusBarHeight = ContextExtensionsKt.getStatusBarHeight(context);
            } else {
                statusBarHeight = 0;
            }
            Bitmap bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight + statusBarHeight, Bitmap.Config.RGB_565);
            view.draw(new Canvas(bitmap));
            Intrinsics.checkNotNullExpressionValue(bitmap, "bitmap");
            return bitmap;
        } catch (Exception unused) {
            int i2 = getContext().getResources().getDisplayMetrics().widthPixels;
            int i3 = getContext().getResources().getDisplayMetrics().widthPixels;
            if (firstPage) {
                Context context2 = getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "context");
                statusBarHeight2 = ContextExtensionsKt.getStatusBarHeight(context2);
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3 + statusBarHeight2, Bitmap.Config.RGB_565);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(\n          …fig.RGB_565\n            )");
            return bitmapCreateBitmap;
        }
    }

    public static /* synthetic */ Bitmap createBitMap$default(ReadView readView, View view, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return readView.createBitMap(view, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BreakIterator getBoundary() {
        return (BreakIterator) this.boundary.getValue();
    }

    private final int getSlopSquare() {
        return ((Number) this.slopSquare.getValue()).intValue();
    }

    private final void handleJumpUnderLineNoteParagraphComment(String jumpDrawLinText) {
        Integer numValueOf;
        List<Chapter> chapterList;
        List<TextPage> pages;
        Integer numValueOf2;
        List<Chapter> chapterList2;
        Log.INSTANCE.logD("draw_page_text", jumpDrawLinText);
        int prefInt$default = ContextExtensionsKt.getPrefInt$default(AppCtxKt.getAppCtx(), PreferKeyKt.START_POSITION, 0, 2, null);
        final String prefString$default = ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_PARAGRAPH_ID, null, 2, null);
        final String prefString$default2 = ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_CHAPTER_ID, null, 2, null);
        if (prefInt$default == 0) {
            if (!TextUtils.isEmpty(prefString$default)) {
                if (ReadBook.INSTANCE.getCurTextChapter() == null) {
                    return;
                }
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.ykb.ebook.weight.r0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ReadView.handleJumpUnderLineNoteParagraphComment$lambda$16(prefString$default, prefString$default2);
                    }
                }, 200L);
                return;
            }
            TextChapter curTextChapter = ReadBook.INSTANCE.getCurTextChapter();
            if (curTextChapter == null || (pages = curTextChapter.getPages()) == null) {
                return;
            }
            int i2 = 0;
            for (Object obj : pages) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                if (StringsKt__StringsKt.contains$default((CharSequence) StringExtensionsKt.formatContent(((TextPage) obj).getText()), (CharSequence) StringExtensionsKt.formatContent(jumpDrawLinText), false, 2, (Object) null)) {
                    ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, "");
                    ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_CHAPTER_ID, "");
                    BookInfo book = ReadBook.INSTANCE.getBook();
                    if (book == null || (chapterList2 = book.getChapterList()) == null) {
                        numValueOf2 = null;
                    } else {
                        Iterator<Chapter> it = chapterList2.iterator();
                        int i4 = 0;
                        while (true) {
                            if (!it.hasNext()) {
                                i4 = -1;
                                break;
                            } else if (Intrinsics.areEqual(prefString$default2, it.next().getId())) {
                                break;
                            } else {
                                i4++;
                            }
                        }
                        numValueOf2 = Integer.valueOf(i4);
                    }
                    if (numValueOf2 == null || numValueOf2.intValue() != -1) {
                        ReadBook readBook = ReadBook.INSTANCE;
                        readBook.setDurChapterIndex(numValueOf2 != null ? numValueOf2.intValue() : 0);
                        ReadBook.skipToPage$default(readBook, i2, null, 2, null);
                        getCurPage().updateTitle();
                        getPrevPage().updateTitle();
                        getNextPage().updateTitle();
                    }
                }
                i2 = i3;
            }
            return;
        }
        ReadBook readBook2 = ReadBook.INSTANCE;
        if (readBook2.getCurTextChapter() == null) {
            return;
        }
        TextChapter curTextChapter2 = readBook2.getCurTextChapter();
        List<TextPage> pages2 = curTextChapter2 != null ? curTextChapter2.getPages() : null;
        Intrinsics.checkNotNull(pages2);
        Iterator<TextPage> it2 = pages2.iterator();
        int i5 = 0;
        int length = 0;
        while (it2.hasNext()) {
            int i6 = i5 + 1;
            length += StringExtensionsKt.formatContent(it2.next().getText()).length();
            if (prefInt$default <= length) {
                ReadBook readBook3 = ReadBook.INSTANCE;
                TextChapter curTextChapter3 = readBook3.getCurTextChapter();
                readBook3.setDurChapterPos(curTextChapter3 != null ? curTextChapter3.getReadLength(i5) : 0);
                String prefString$default3 = ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_CHAPTER_ID, null, 2, null);
                BookInfo book2 = readBook3.getBook();
                if (book2 == null || (chapterList = book2.getChapterList()) == null) {
                    numValueOf = null;
                } else {
                    Iterator<Chapter> it3 = chapterList.iterator();
                    int i7 = 0;
                    while (true) {
                        if (!it3.hasNext()) {
                            i7 = -1;
                            break;
                        } else if (Intrinsics.areEqual(prefString$default3, it3.next().getId())) {
                            break;
                        } else {
                            i7++;
                        }
                    }
                    numValueOf = Integer.valueOf(i7);
                }
                if (numValueOf != null && numValueOf.intValue() == -1) {
                    return;
                }
                ReadBook readBook4 = ReadBook.INSTANCE;
                readBook4.setDurChapterIndex(numValueOf != null ? numValueOf.intValue() : 0);
                ReadBook.skipToPage$default(readBook4, i5, null, 2, null);
                getCurPage().updateTitle();
                getPrevPage().updateTitle();
                getNextPage().updateTitle();
                ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, "");
                ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_CHAPTER_ID, "");
                ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.START_POSITION, 0);
                return;
            }
            i5 = i6;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleJumpUnderLineNoteParagraphComment$lambda$16(String str, String str2) {
        Integer numValueOf;
        List<Chapter> chapterList;
        ReadBook readBook = ReadBook.INSTANCE;
        TextChapter curTextChapter = readBook.getCurTextChapter();
        if (curTextChapter != null) {
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, "");
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_PARAGRAPH_ID, "");
            BookInfo book = readBook.getBook();
            if (book == null || (chapterList = book.getChapterList()) == null) {
                numValueOf = null;
            } else {
                Iterator<Chapter> it = chapterList.iterator();
                int i2 = 0;
                while (true) {
                    if (!it.hasNext()) {
                        i2 = -1;
                        break;
                    } else if (Intrinsics.areEqual(str2, it.next().getId())) {
                        break;
                    } else {
                        i2++;
                    }
                }
                numValueOf = Integer.valueOf(i2);
            }
            ReadBook readBook2 = ReadBook.INSTANCE;
            Intrinsics.checkNotNull(numValueOf);
            readBook2.setDurChapterIndex(numValueOf.intValue());
            if (curTextChapter.getParagraphs().size() != 0) {
                Intrinsics.checkNotNull(str);
                if (Integer.parseInt(str) >= curTextChapter.getParagraphs().size()) {
                    return;
                }
                ReadBook.skipToPage$default(readBook2, curTextChapter.getParagraphs().get(Integer.parseInt(str)).getTextLines().get(r5.size() - 1).getTextPage().getIndex(), null, 2, null);
            }
        }
    }

    private final void invalidateTextPage() {
        TextPageFactory textPageFactory = this.pageFactory;
        textPageFactory.getPrevPage().invalidateAll();
        textPageFactory.getCurPage().invalidateAll();
        textPageFactory.getNextPage().invalidateAll();
        textPageFactory.getNextPlusPage().invalidateAll();
    }

    private final boolean isLastPage() {
        List<Chapter> chapterList;
        ReadBook readBook = ReadBook.INSTANCE;
        int durChapterIndex = readBook.getDurChapterIndex();
        BookInfo book = readBook.getBook();
        return durChapterIndex == ((book == null || (chapterList = book.getChapterList()) == null) ? 0 : chapterList.size()) - 1 && readBook.getDurPageIndex() == readBook.getCurrentChapterTotalPages() - 1 && !ReadConfig.INSTANCE.getLastPage();
    }

    private final boolean isPointInsideView(View view, int pointX, int pointY) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        return (i2 <= pointX && pointX <= view.getWidth() + i2) && pointY >= i3 && pointY <= view.getHeight() + i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void longPressRunnable$lambda$0(ReadView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.longPressed = true;
        this$0.onLongPress();
    }

    private final void onLongPress() {
        if (getCurPage().getTextChapter().getChapter().isPay()) {
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            getCurPage().longPress(this.startX, this.startY, new Function1<TextPosition, Unit>() { // from class: com.ykb.ebook.weight.ReadView.onLongPress.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(TextPosition textPosition) {
                    invoke2(textPosition);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull TextPosition textPos) {
                    Intrinsics.checkNotNullParameter(textPos, "textPos");
                    BaseColumn baseColumn = ReadView.this.getCurPage().relativePage(textPos.getRelativePagePosition()).getLine(textPos.getLineIndex()).getColumns().get(0);
                    Intrinsics.checkNotNull(baseColumn, "null cannot be cast to non-null type com.ykb.ebook.page.column.TextColumn");
                    if (((TextColumn) baseColumn).getCharIndex() == -1) {
                        booleanRef.element = true;
                    }
                }
            });
            if (booleanRef.element) {
                return;
            }
            try {
                Result.Companion companion = Result.INSTANCE;
                getCurPage().longPress(this.startX, this.startY, new Function1<TextPosition, Unit>() { // from class: com.ykb.ebook.weight.ReadView$onLongPress$2$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(TextPosition textPosition) {
                        invoke2(textPosition);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:37:0x010f, code lost:
                    
                        if (r6 == r7) goto L60;
                     */
                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void invoke2(@org.jetbrains.annotations.NotNull com.ykb.ebook.model.TextPosition r15) {
                        /*
                            Method dump skipped, instructions count: 373
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.weight.ReadView$onLongPress$2$1.invoke2(com.ykb.ebook.model.TextPosition):void");
                    }
                });
                Result.m783constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                Result.m783constructorimpl(ResultKt.createFailure(th));
            }
        }
    }

    private final void onSingleTapUp() {
        if (this.isTextSelected || getCallback().isShowActionMenu()) {
            return;
        }
        if (this.mcRect.contains(this.startX, this.startY)) {
            if (this.isAbortAnim) {
                return;
            }
            click(0);
            return;
        }
        if (this.bcRect.contains(this.startX, this.startY)) {
            click(1);
            return;
        }
        if (this.blRect.contains(this.startX, this.startY)) {
            click(2);
            return;
        }
        if (this.brRect.contains(this.startX, this.startY)) {
            click(1);
            return;
        }
        if (this.mlRect.contains(this.startX, this.startY)) {
            click(2);
            return;
        }
        if (this.mrRect.contains(this.startX, this.startY)) {
            click(1);
            return;
        }
        if (this.tlRect.contains(this.startX, this.startY)) {
            click(2);
        } else if (this.tcRect.contains(this.startX, this.startY)) {
            click(2);
        } else if (this.trRect.contains(this.startX, this.startY)) {
            click(1);
        }
    }

    private final void selectText(float x2, float y2, boolean includeHeaderHeight) {
        getCurPage().selectText(x2, y2, new Function1<TextPosition, Unit>() { // from class: com.ykb.ebook.weight.ReadView.selectText.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(TextPosition textPosition) {
                invoke2(textPosition);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull TextPosition textPos) {
                Intrinsics.checkNotNullParameter(textPos, "textPos");
                int iCompare = ReadView.this.initialTextPos.compare(textPos);
                Log.INSTANCE.logD("compare", String.valueOf(iCompare));
                ReadView.this.getCallback().updateSelectTextPosition(textPos, ReadView.this.initialTextPos);
                if (iCompare >= 0) {
                    ReadView.this.getCurPage().selectStartMoveIndex(textPos);
                    ReadView.this.getCurPage().selectEndMoveIndex(ReadView.this.initialTextPos);
                } else {
                    ReadView.this.getCurPage().selectStartMoveIndex(ReadView.this.initialTextPos);
                    ReadView.this.getCurPage().selectEndMoveIndex(textPos);
                }
            }
        });
    }

    public static /* synthetic */ void selectText$default(ReadView readView, float f2, float f3, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        readView.selectText(f2, f3, z2);
    }

    private final void setPageDelegate(PageDelegate pageDelegate) {
        PageDelegate pageDelegate2 = this.pageDelegate;
        if (pageDelegate2 != null) {
            pageDelegate2.onDestroy();
        }
        this.pageDelegate = pageDelegate;
        DataSource.DefaultImpls.upContent$default(this, 0, false, 3, null);
    }

    private final void setRect9x() {
        this.tlRect.set(0.0f, 0.0f, getWidth() * 0.33f, getHeight() * 0.33f);
        this.tcRect.set(getWidth() * 0.33f, 0.0f, getWidth() * 0.66f, getHeight() * 0.33f);
        this.trRect.set(getWidth() * 0.36f, 0.0f, getWidth(), getHeight() * 0.33f);
        this.mlRect.set(0.0f, getHeight() * 0.33f, getWidth() * 0.33f, getHeight() * 0.66f);
        this.mcRect.set(getWidth() * 0.33f, getHeight() * 0.33f, getWidth() * 0.66f, getHeight() * 0.66f);
        this.mrRect.set(getWidth() * 0.66f, getHeight() * 0.33f, getWidth(), getHeight() * 0.66f);
        this.blRect.set(0.0f, getHeight() * 0.66f, getWidth() * 0.33f, getHeight());
        this.bcRect.set(getWidth() * 0.33f, getHeight() * 0.66f, getWidth() * 0.66f, getHeight());
        this.brRect.set(getWidth() * 0.66f, getHeight() * 0.66f, getWidth(), getHeight());
    }

    public static /* synthetic */ void setStartPoint$default(ReadView readView, float f2, float f3, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        readView.setStartPoint(f2, f3, z2);
    }

    public static /* synthetic */ void setTouchPoint$default(ReadView readView, float f2, float f3, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        readView.setTouchPoint(f2, f3, z2);
    }

    private final void submitRenderTask() {
        getCurPage().submitRenderTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void upContent$lambda$13(ReadView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCurPage().setContentDescription(this$0.pageFactory.getCurPage().getText());
    }

    private final void upPageSlopSquare() {
        int slopSquare = getSlopSquare();
        this.pageSlopSquare = slopSquare;
        this.pageSlopSquare2 = slopSquare * slopSquare;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void upProgress() {
    }

    private final void updateContent(int relativePosition, boolean resetPageOffset) throws SecurityException, NumberFormatException {
        if (relativePosition == -1) {
            PageView.setContent$default(getPrevPage(), this.pageFactory.getPrevPage(), false, false, 6, null);
        } else {
            if (relativePosition == 1) {
                PageView.setContent$default(getNextPage(), this.pageFactory.getNextPage(), false, false, 6, null);
                return;
            }
            PageView.setContent$default(getCurPage(), this.pageFactory.getCurPage(), resetPageOffset, false, 4, null);
            PageView.setContent$default(getNextPage(), this.pageFactory.getNextPage(), false, false, 6, null);
            PageView.setContent$default(getPrevPage(), this.pageFactory.getPrevPage(), false, false, 6, null);
        }
    }

    public static /* synthetic */ void updateContent$default(ReadView readView, int i2, boolean z2, int i3, Object obj) throws SecurityException, NumberFormatException {
        if ((i3 & 2) != 0) {
            z2 = false;
        }
        readView.updateContent(i2, z2);
    }

    private final void updateFirstLastPageFlipPageShadow() {
        postDelayed(new Runnable() { // from class: com.ykb.ebook.weight.w0
            @Override // java.lang.Runnable
            public final void run() throws SecurityException, NumberFormatException {
                ReadView.updateFirstLastPageFlipPageShadow$lambda$24(this.f26543c);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r2v2, types: [T, android.graphics.Bitmap] */
    /* JADX WARN: Type inference failed for: r3v1, types: [T, android.graphics.Bitmap] */
    public static final void updateFirstLastPageFlipPageShadow$lambda$24(final ReadView this$0) throws SecurityException, NumberFormatException {
        int color;
        Context context;
        int i2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.pageDelegate instanceof SimulationPageDelegate) {
            ReadBook readBook = ReadBook.INSTANCE;
            if (readBook.getBook() == null || this$0.getCurPage() == null) {
                return;
            }
            PageView curPage = this$0.getCurPage();
            BookInfo book = readBook.getBook();
            Intrinsics.checkNotNull(book);
            curPage.setBookInfo(book);
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = readBook.getPageBitMap("first");
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = readBook.getPageBitMap("last");
            if (this$0.isLastPage()) {
                Bitmap pageBitMap = readBook.getPageBitMap("last_pre");
                if (pageBitMap == null) {
                    pageBitMap = createBitMap$default(this$0, this$0.getCurPage(), false, 2, null);
                }
                readBook.savePageBitMap(pageBitMap, "last_pre");
                PageDelegate pageDelegate = this$0.pageDelegate;
                Intrinsics.checkNotNull(pageDelegate, "null cannot be cast to non-null type com.ykb.ebook.page.delegate.SimulationPageDelegate");
                ((SimulationPageDelegate) pageDelegate).setLastPreBitMap(pageBitMap);
            }
            if (objectRef.element == 0) {
                this$0.getCurPage().setFirstPageContent();
                this$0.getCurPage().getPageBookInfoView().postDelayed(new Runnable() { // from class: com.ykb.ebook.weight.t0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ReadView.updateFirstLastPageFlipPageShadow$lambda$24$lambda$21(this.f26533c, objectRef);
                    }
                }, 300L);
            }
            if (objectRef2.element == 0) {
                this$0.getCurPage().setLastPageContent();
                final View endPageView = this$0.getCurPage().getEndPageView();
                ReadConfig readConfig = ReadConfig.INSTANCE;
                if (readConfig.getColorMode() == 2) {
                    context = endPageView.getContext();
                    i2 = R.color.color_171C2D;
                } else {
                    if (readConfig.getColorMode() != 1) {
                        color = -1;
                        endPageView.setBackground(new ColorDrawable(color));
                        this$0.getCurPage().getEndPageView().postDelayed(new Runnable() { // from class: com.ykb.ebook.weight.u0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ReadView.updateFirstLastPageFlipPageShadow$lambda$24$lambda$23(this.f26536c, endPageView, objectRef2);
                            }
                        }, 300L);
                    }
                    context = endPageView.getContext();
                    i2 = R.color.color_F5EBCE;
                }
                color = context.getColor(i2);
                endPageView.setBackground(new ColorDrawable(color));
                this$0.getCurPage().getEndPageView().postDelayed(new Runnable() { // from class: com.ykb.ebook.weight.u0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ReadView.updateFirstLastPageFlipPageShadow$lambda$24$lambda$23(this.f26536c, endPageView, objectRef2);
                    }
                }, 300L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [T, android.graphics.Bitmap] */
    public static final void updateFirstLastPageFlipPageShadow$lambda$24$lambda$21(ReadView this$0, Ref.ObjectRef first) {
        Drawable colorDrawable;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(first, "$first");
        View pageBookInfoView = this$0.getCurPage().getPageBookInfoView();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 1) {
            colorDrawable = ContextCompat.getDrawable(pageBookInfoView.getContext(), R.drawable.bg_content_view);
        } else {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(pageBookInfoView.getContext(), readConfig.getColorMode() == 2 ? R.color.color_0d111d : R.color.white));
        }
        pageBookInfoView.setBackground(colorDrawable);
        ?? CreateBitMap = this$0.createBitMap(pageBookInfoView, true);
        first.element = CreateBitMap;
        ReadBook.INSTANCE.savePageBitMap(CreateBitMap, "first");
        PageDelegate pageDelegate = this$0.pageDelegate;
        if (pageDelegate instanceof SimulationPageDelegate) {
            Intrinsics.checkNotNull(pageDelegate, "null cannot be cast to non-null type com.ykb.ebook.page.delegate.SimulationPageDelegate");
            ((SimulationPageDelegate) pageDelegate).setFirstLastPageBitMap(CreateBitMap, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [T, android.graphics.Bitmap] */
    public static final void updateFirstLastPageFlipPageShadow$lambda$24$lambda$23(ReadView this$0, View endPage, Ref.ObjectRef last) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(endPage, "$endPage");
        Intrinsics.checkNotNullParameter(last, "$last");
        ?? CreateBitMap$default = createBitMap$default(this$0, endPage, false, 2, null);
        last.element = CreateBitMap$default;
        ReadBook.INSTANCE.savePageBitMap(CreateBitMap$default, "last");
        PageDelegate pageDelegate = this$0.pageDelegate;
        if (pageDelegate instanceof SimulationPageDelegate) {
            Intrinsics.checkNotNull(pageDelegate, "null cannot be cast to non-null type com.ykb.ebook.page.delegate.SimulationPageDelegate");
            ((SimulationPageDelegate) pageDelegate).setFirstLastPageBitMap(null, CreateBitMap$default);
        }
    }

    public static /* synthetic */ void updateThemeStyle$default(ReadView readView, boolean z2, int i2, Object obj) throws SecurityException, NumberFormatException {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        readView.updateThemeStyle(z2);
    }

    public final void cancelSelect() {
        PageView.cancelSelect$default(getCurPage(), false, 1, null);
        PageView.cancelSelect$default(getPrevPage(), false, 1, null);
        PageView.cancelSelect$default(getNextPage(), false, 1, null);
    }

    @Override // android.view.View
    public void computeScroll() {
        PageDelegate pageDelegate = this.pageDelegate;
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

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.dispatchDraw(canvas);
        PageDelegate pageDelegate = this.pageDelegate;
        if (pageDelegate != null) {
            pageDelegate.onDraw(canvas);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean fillPage(@org.jetbrains.annotations.NotNull com.ykb.ebook.page.PageDirection r13) throws java.lang.SecurityException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 460
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.weight.ReadView.fillPage(com.ykb.ebook.page.PageDirection):boolean");
    }

    @NotNull
    public final Callback getCallback() {
        KeyEventDispatcher.Component activity = ViewExtensionsKt.getActivity(this);
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.ykb.ebook.weight.ReadView.Callback");
        return (Callback) activity;
    }

    @NotNull
    public final PageView getCurPage() {
        return (PageView) this.curPage.getValue();
    }

    public final int getCurPagePosition() {
        TextLine curVisibleFirstLine = getCurPage().getCurVisibleFirstLine();
        if (curVisibleFirstLine != null) {
            return curVisibleFirstLine.getPagePosition();
        }
        return 0;
    }

    @NotNull
    public final TextPage getCurVisiblePage() {
        return getCurPage().getCurVisiblePage();
    }

    @Override // com.ykb.ebook.common_interface.DataSource
    @Nullable
    public TextChapter getCurrentChapter() {
        if (getCallback().isInitFinish()) {
            return ReadBook.INSTANCE.textChapter(0);
        }
        return null;
    }

    public final int getDefaultAnimationSpeed() {
        return this.defaultAnimationSpeed;
    }

    public final float getLastX() {
        return this.lastX;
    }

    public final float getLastY() {
        return this.lastY;
    }

    @Override // com.ykb.ebook.common_interface.DataSource
    @Nullable
    public TextChapter getNextChapter() {
        if (getCallback().isInitFinish()) {
            return ReadBook.INSTANCE.textChapter(1);
        }
        return null;
    }

    @NotNull
    public final PageView getNextPage() {
        return (PageView) this.nextPage.getValue();
    }

    @Nullable
    public final PageDelegate getPageDelegate() {
        return this.pageDelegate;
    }

    @NotNull
    public final TextPageFactory getPageFactory() {
        return this.pageFactory;
    }

    @Override // com.ykb.ebook.common_interface.DataSource
    public int getPageIndex() {
        return DataSource.DefaultImpls.getPageIndex(this);
    }

    public final int getPageSlopSquare2() {
        return this.pageSlopSquare2;
    }

    @Override // com.ykb.ebook.common_interface.DataSource
    @Nullable
    public TextChapter getPrevChapter() {
        if (getCallback().isInitFinish()) {
            return ReadBook.INSTANCE.textChapter(-1);
        }
        return null;
    }

    @NotNull
    public final PageView getPrevPage() {
        return (PageView) this.prevPage.getValue();
    }

    @Nullable
    public final ReadBookActivity getReadBookActivity() {
        AppCompatActivity activity = ViewExtensionsKt.getActivity(this);
        if (activity instanceof ReadBookActivity) {
            return (ReadBookActivity) activity;
        }
        return null;
    }

    @NotNull
    public final String getSelectText() {
        return getCurPage().getSelectedText();
    }

    public final int getSelectTextPosition() {
        return this.selectTextPosition;
    }

    public final int getSelectTextPosition_original() {
        return this.selectTextPosition_original;
    }

    public final int getStartPosition() {
        return this.startPosition;
    }

    public final float getStartX() {
        return this.startX;
    }

    public final float getStartY() {
        return this.startY;
    }

    public final float getTouchX() {
        return this.touchX;
    }

    public final float getTouchY() {
        return this.touchY;
    }

    @Override // com.ykb.ebook.common_interface.DataSource
    public boolean hasNextChapter() {
        ReadBook readBook = ReadBook.INSTANCE;
        return readBook.getDurChapterIndex() < readBook.getChapterSize() - 1;
    }

    @Override // com.ykb.ebook.common_interface.DataSource
    public boolean hasPrevChapter() {
        return ReadBook.INSTANCE.getDurChapterIndex() > 0;
    }

    /* renamed from: isAbortAnim, reason: from getter */
    public final boolean getIsAbortAnim() {
        return this.isAbortAnim;
    }

    public final boolean isLongScreenShot() {
        return getCurPage().isLongScreenShot();
    }

    @Override // com.ykb.ebook.common_interface.DataSource
    /* renamed from: isScroll, reason: from getter */
    public boolean getIsScroll() {
        return this.isScroll;
    }

    public final void onDestroy() {
        PageDelegate pageDelegate = this.pageDelegate;
        if (pageDelegate != null) {
            pageDelegate.onDestroy();
        }
        PageView.cancelSelect$default(getCurPage(), false, 1, null);
        invalidateTextPage();
        BitmapPool.INSTANCE.clear();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getFirstPage()) {
            Iterator<T> it = getCurPage().getFirstPageInterceptViews().iterator();
            while (it.hasNext()) {
                if (isPointInsideView((View) it.next(), (int) event.getRawX(), (int) event.getRawY())) {
                    return false;
                }
            }
            return true;
        }
        if (!readConfig.getLastPage()) {
            return true;
        }
        Iterator<T> it2 = getCurPage().getLastPageInterceptViews().iterator();
        while (it2.hasNext()) {
            if (isPointInsideView((View) it2.next(), (int) event.getRawX(), (int) event.getRawY())) {
                return false;
            }
        }
        return true;
    }

    @Override // com.ykb.ebook.common_interface.LayoutProgressListener
    public void onLayoutCompleted() {
        LayoutProgressListener.DefaultImpls.onLayoutCompleted(this);
    }

    @Override // com.ykb.ebook.common_interface.LayoutProgressListener
    public void onLayoutException(@NotNull Throwable th) {
        LayoutProgressListener.DefaultImpls.onLayoutException(this, th);
    }

    @Override // com.ykb.ebook.common_interface.LayoutProgressListener
    public void onLayoutPageCompleted(int index, @NotNull TextPage page) {
        Intrinsics.checkNotNullParameter(page, "page");
    }

    public final void onPageChange() {
        submitRenderTask();
        this.upProgressThrottle.invoke();
    }

    public final void onScrollAnimStart() {
    }

    public final void onScrollAnimStop() {
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) throws SecurityException, NumberFormatException {
        super.onSizeChanged(w2, h2, oldw, oldh);
        setRect9x();
        getPrevPage().setX(-w2);
        PageDelegate pageDelegate = this.pageDelegate;
        if (pageDelegate != null) {
            pageDelegate.setViewSize(w2, h2);
        }
        if (w2 <= 0 || h2 <= 0) {
            return;
        }
        updateThemeStyle$default(this, false, 1, null);
        getCallback().upSystemUiVisibility();
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@NotNull MotionEvent event) {
        PageDelegate pageDelegate;
        boolean z2;
        PageDelegate pageDelegate2;
        boolean z3;
        float f2;
        Iterator it;
        int rvRecycleItemPos;
        PageDelegate pageDelegate3;
        int i2;
        int lineStyle;
        boolean z4;
        WindowManager windowManager;
        WindowMetrics currentWindowMetrics;
        Rect bounds;
        Intrinsics.checkNotNullParameter(event, "event");
        boolean z5 = true;
        if (Build.VERSION.SDK_INT >= 30) {
            Intrinsics.checkNotNullExpressionValue(getRootWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.mandatorySystemGestures()), "this.rootWindowInsets.ge…andatorySystemGestures())");
            AppCompatActivity activity = ViewExtensionsKt.getActivity(this);
            if (((activity == null || (windowManager = activity.getWindowManager()) == null || (currentWindowMetrics = windowManager.getCurrentWindowMetrics()) == null || (bounds = currentWindowMetrics.getBounds()) == null) ? null : Integer.valueOf(bounds.height())) != null && event.getY() > r4.intValue() - r1.bottom) {
                return true;
            }
        }
        if ((event.getActionMasked() == 5 || event.getActionMasked() == 6) && (pageDelegate = this.pageDelegate) != null) {
            pageDelegate.onTouch(event);
            Unit unit = Unit.INSTANCE;
        }
        int action = event.getAction();
        int i3 = 0;
        if (action != 0) {
            int i4 = -1;
            if (action != 1) {
                if (action == 2) {
                    float fAbs = Math.abs(this.startX - event.getX());
                    float fAbs2 = Math.abs(this.startY - event.getY());
                    if (!this.isMove) {
                        this.isMove = fAbs > ((float) getSlopSquare()) || fAbs2 > ((float) getSlopSquare());
                    }
                    float y2 = event.getY() - this.lastYPay;
                    if (getIsScroll() && !getCurPage().getTextChapter().getChapter().isPay()) {
                        this.scrollFlow.setValue(Float.valueOf(y2));
                    }
                    if (this.isMove) {
                        this.longPressed = false;
                        removeCallbacks(this.longPressRunnable);
                        if (this.isTextSelected) {
                            selectText$default(this, event.getX(), event.getY(), false, 4, null);
                        } else {
                            PageDelegate pageDelegate4 = this.pageDelegate;
                            if (pageDelegate4 != null) {
                                pageDelegate4.onTouch(event);
                                Unit unit2 = Unit.INSTANCE;
                            }
                        }
                    }
                } else if (action == 3) {
                    removeCallbacks(this.longPressRunnable);
                    if (!this.pressDown) {
                        return true;
                    }
                    this.pressDown = false;
                    if (this.isTextSelected) {
                        Iterator<TextLine> it2 = getCurPage().getTextPage().getLines().iterator();
                        loop0: while (true) {
                            if (!it2.hasNext()) {
                                i2 = -1;
                                lineStyle = -1;
                                z4 = false;
                                break;
                            }
                            for (BaseColumn baseColumn : it2.next().getColumns()) {
                                if (baseColumn instanceof TextColumn) {
                                    TextColumn textColumn = (TextColumn) baseColumn;
                                    if (textColumn.getSelected()) {
                                        z4 = !textColumn.getIsDrawUnderLine();
                                        int lineColor = textColumn.getLineColor();
                                        lineStyle = textColumn.getLineStyle();
                                        i2 = lineColor;
                                        break loop0;
                                    }
                                }
                            }
                        }
                        Log.INSTANCE.logD("ReadView", "判断出来的类型为：" + z4);
                        ReadConfig readConfig = ReadConfig.INSTANCE;
                        if (!readConfig.getFirstPage() && !readConfig.getLastPage()) {
                            Callback.DefaultImpls.showTextActionMenu$default(getCallback(), readConfig.getPageAnim() == 1 ? true : z4, i2, lineStyle, -1, null, 16, null);
                        }
                    } else {
                        PageDelegate pageDelegate5 = this.pageDelegate;
                        Intrinsics.checkNotNull(pageDelegate5);
                        if (pageDelegate5.getIsMoved() && (pageDelegate3 = this.pageDelegate) != null) {
                            pageDelegate3.onTouch(event);
                            Unit unit3 = Unit.INSTANCE;
                        }
                    }
                    this.pressOnTextSelected = false;
                }
                return true;
            }
            boolean zPosInRvBottom = getCurPage().posInRvBottom((int) event.getRawX(), (int) event.getRawY());
            if (getCurPage().rvBottomVisibleStatus() && zPosInRvBottom && !this.isMove && (rvRecycleItemPos = getCurPage().getRvRecycleItemPos((int) event.getRawY())) != -1) {
                getCurPage().setBottomActionClick(rvRecycleItemPos);
                return true;
            }
            getCallback().screenOffTimerStart();
            removeCallbacks(this.longPressRunnable);
            if (!this.pressDown) {
                return true;
            }
            this.pressDown = false;
            PageDelegate pageDelegate6 = this.pageDelegate;
            Intrinsics.checkNotNull(pageDelegate6);
            if (!pageDelegate6.getIsMoved() && !this.isMove && !this.longPressed && !this.pressOnTextSelected) {
                if (!getCurPage().onClick(this.startX, this.startY)) {
                    onSingleTapUp();
                }
                return true;
            }
            if (this.isTextSelected) {
                TextPage textPage = getCurPage().getTextPage();
                this.startPosition = -1;
                this.selectTextPosition = -1;
                this.selectTextPosition_original = -1;
                Iterator<TextLine> it3 = textPage.getLines().iterator();
                int lineColor2 = -1;
                int lineStyle2 = -1;
                boolean z6 = false;
                String lineId = "";
                while (it3.hasNext()) {
                    for (BaseColumn baseColumn2 : it3.next().getColumns()) {
                        if (baseColumn2 instanceof TextColumn) {
                            TextColumn textColumn2 = (TextColumn) baseColumn2;
                            if (textColumn2.getCharIndex() != i4 && textColumn2.getSelected()) {
                                if (TextUtils.isEmpty(lineId) && !TextUtils.isEmpty(textColumn2.getLineId())) {
                                    lineId = textColumn2.getLineId();
                                }
                                z6 = !textColumn2.getIsDrawUnderLine();
                                if (lineColor2 == i4) {
                                    lineColor2 = textColumn2.getLineColor();
                                }
                                if (lineStyle2 == i4) {
                                    lineStyle2 = textColumn2.getLineStyle();
                                }
                                if (this.startPosition == i4 && (StringsKt__StringsJVMKt.isBlank(textColumn2.getCharData()) ^ z5)) {
                                    if (textColumn2.getCharData().length() > 0 ? z5 : false) {
                                        String handleContent = getCurPage().getTextChapter().getChapter().getHandleContent();
                                        String strSubstring = StringExtensionsKt.formatContent(getCurPage().getTextChapter().getChapter().getHandleContent()).substring(0, textColumn2.getCharIndex());
                                        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                                        int iCountRemovedNewlines = countRemovedNewlines(handleContent, strSubstring);
                                        this.startPosition = textColumn2.getCharIndex();
                                        this.selectTextPosition_original = textColumn2.getCharIndex() + iCountRemovedNewlines + getCurPage().getTextChapter().getPages().get(0).getTitle().length();
                                        this.selectTextPosition = textColumn2.getCharIndex() + getCurPage().getTextChapter().getPages().get(0).getTitle().length();
                                    }
                                }
                            }
                        }
                        i4 = -1;
                        z5 = true;
                    }
                }
                ReadConfig readConfig2 = ReadConfig.INSTANCE;
                if (readConfig2.getFirstPage() || readConfig2.getLastPage()) {
                    z3 = false;
                    this.pressOnTextSelected = z3;
                } else {
                    if (!this.isMove) {
                        float y3 = event.getY();
                        int statusBarHeight = StatusBarUtil.getStatusBarHeight(getContext());
                        int pxByDp = ScreenUtil.getPxByDp(getContext(), 30);
                        if (readConfig2.getHideStatusBar()) {
                            statusBarHeight = 0;
                        }
                        int i5 = pxByDp + statusBarHeight;
                        ArrayList arrayList = new ArrayList();
                        Iterator it4 = getCurPage().getTextPage().getParagraphs().iterator();
                        int i6 = 0;
                        int i7 = 0;
                        int i8 = 0;
                        String string = "";
                        int size = 0;
                        while (it4.hasNext()) {
                            Object next = it4.next();
                            int i9 = i6 + 1;
                            if (i6 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                            }
                            TextParagraph textParagraph = (TextParagraph) next;
                            size += textParagraph.getTextLines().size();
                            int i10 = i3;
                            for (Object obj : textParagraph.getTextLines()) {
                                int i11 = i10 + 1;
                                if (i10 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                }
                                TextLine textLine = (TextLine) obj;
                                float f3 = i5;
                                if (y3 < textLine.getLineTop() + f3 || y3 > textLine.getLineBottom() + f3) {
                                    f2 = y3;
                                    it = it4;
                                } else {
                                    i7 += size;
                                    int size2 = (i7 - textParagraph.getTextLines().size()) + 1;
                                    arrayList.addAll(textParagraph.getTextLines());
                                    String text = textParagraph.getText();
                                    f2 = y3;
                                    it = it4;
                                    BaseColumn baseColumn3 = textParagraph.getTextLines().get(textParagraph.getTextLines().size() - 1).getColumns().get(textParagraph.getTextLines().get(textParagraph.getTextLines().size() - 1).getColumns().size() - 1);
                                    if (baseColumn3 instanceof TextColumn) {
                                        string = text + '\n';
                                    } else {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(text);
                                        sb.append(baseColumn3 instanceof QuestionColumn ? ReadConfig.QUESTION_MULTI_CHAR : baseColumn3 instanceof ImageColumn ? "@" : baseColumn3 instanceof ReviewColumn ? ReadConfig.REVIEW_CHAR : Unit.INSTANCE);
                                        sb.append('\n');
                                        string = sb.toString();
                                    }
                                    i8 = size2;
                                }
                                y3 = f2;
                                it4 = it;
                                i10 = i11;
                                i3 = 0;
                            }
                            i6 = i9;
                        }
                        if (!arrayList.isEmpty()) {
                            Iterator it5 = arrayList.iterator();
                            while (it5.hasNext()) {
                                for (BaseColumn baseColumn4 : ((TextLine) it5.next()).getColumns()) {
                                    if (baseColumn4 instanceof TextColumn) {
                                        TextColumn textColumn3 = (TextColumn) baseColumn4;
                                        if (!StringsKt__StringsJVMKt.isBlank(textColumn3.getCharData())) {
                                            textColumn3.setSelected(true);
                                        }
                                    }
                                }
                            }
                            getCurPage().getTextPage().invalidateAll();
                            getCurPage().invalidateContentView();
                            int size3 = arrayList.size() - 1;
                            List<BaseColumn> columns = ((TextLine) arrayList.get(size3)).getColumns();
                            float fMeasureText = 0.0f;
                            if (!columns.isEmpty()) {
                                for (BaseColumn baseColumn5 : columns) {
                                    if ((baseColumn5 instanceof TextColumn) && !TextUtils.isEmpty(((TextColumn) baseColumn5).getCharData())) {
                                        fMeasureText = baseColumn5.getEnd();
                                    }
                                }
                            } else {
                                for (BaseColumn baseColumn6 : ((TextLine) arrayList.get(size3)).getColumns()) {
                                    if (baseColumn6 instanceof TextColumn) {
                                        fMeasureText += ChapterProvider.getContentPaint().measureText(((TextColumn) baseColumn6).getCharData());
                                    }
                                }
                            }
                            float f4 = fMeasureText;
                            ArrayList arrayList2 = new ArrayList();
                            Iterator it6 = arrayList.iterator();
                            while (it6.hasNext()) {
                                for (BaseColumn baseColumn7 : ((TextLine) it6.next()).getColumns()) {
                                    Iterator it7 = it6;
                                    if (baseColumn7 instanceof TextColumn) {
                                        TextColumn textColumn4 = (TextColumn) baseColumn7;
                                        if (textColumn4.getSelected()) {
                                            arrayList2.add(Integer.valueOf(textColumn4.getCharIndex()));
                                        }
                                    }
                                    it6 = it7;
                                }
                            }
                            TextPosition textPosition = new TextPosition(0, i8 - 1, 0, false, false, 24, null);
                            TextPosition textPosition2 = new TextPosition(0, i7 - 1, columns.size() - 1, false, false, 24, null);
                            getCurPage().selectStartMoveIndex(textPosition);
                            getCurPage().selectEndMoveIndex(textPosition2);
                            float f5 = i5;
                            getCallback().updateSelectCursor(((TextLine) arrayList.get(0)).getStartX(), ((TextLine) arrayList.get(0)).getLineBottom() + f5, f4, ((TextLine) arrayList.get(arrayList.size() - 1)).getLineBottom() + f5, string);
                            this.selectTextPosition = -1;
                            this.selectTextPosition_original = -1;
                            Iterator<TextLine> it8 = textPage.getLines().iterator();
                            while (it8.hasNext()) {
                                for (BaseColumn baseColumn8 : it8.next().getColumns()) {
                                    if (baseColumn8 instanceof TextColumn) {
                                        TextColumn textColumn5 = (TextColumn) baseColumn8;
                                        if (textColumn5.getCharIndex() != -1 && textColumn5.getSelected() && this.selectTextPosition == -1 && (!StringsKt__StringsJVMKt.isBlank(textColumn5.getCharData()))) {
                                            if (textColumn5.getCharData().length() > 0) {
                                                String handleContent2 = getCurPage().getTextChapter().getChapter().getHandleContent();
                                                String strSubstring2 = StringExtensionsKt.formatContent(getCurPage().getTextChapter().getChapter().getHandleContent()).substring(0, textColumn5.getCharIndex() + 1);
                                                Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String…ing(startIndex, endIndex)");
                                                int iCountRemovedNewlines2 = countRemovedNewlines(handleContent2, strSubstring2);
                                                this.startPosition = textColumn5.getCharIndex();
                                                this.selectTextPosition_original = textColumn5.getCharIndex() + iCountRemovedNewlines2 + getCurPage().getTextChapter().getPages().get(0).getTitle().length();
                                                this.selectTextPosition = textColumn5.getCharIndex() + getCurPage().getTextChapter().getPages().get(0).getTitle().length();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    getCallback().showTextActionMenu(ReadConfig.INSTANCE.getPageAnim() == 1 ? true : z6, lineColor2, lineStyle2, this.startPosition, lineId);
                }
            } else {
                PageDelegate pageDelegate7 = this.pageDelegate;
                Intrinsics.checkNotNull(pageDelegate7);
                if (pageDelegate7.getIsMoved() && (pageDelegate2 = this.pageDelegate) != null) {
                    pageDelegate2.onTouch(event);
                    Unit unit4 = Unit.INSTANCE;
                }
            }
            z3 = false;
            this.pressOnTextSelected = z3;
        } else {
            float y4 = event.getY();
            this.startYPay = y4;
            this.lastYPay = y4;
            getCallback().screenOffTimerStart();
            if (this.isTextSelected) {
                z2 = true;
                PageView.cancelSelect$default(getCurPage(), false, 1, null);
                this.isTextSelected = false;
                this.pressOnTextSelected = true;
            } else {
                z2 = true;
                this.pressOnTextSelected = false;
            }
            this.longPressed = false;
            postDelayed(this.longPressRunnable, this.longPressTimeout);
            this.pressDown = z2;
            this.isMove = false;
            PageDelegate pageDelegate8 = this.pageDelegate;
            if (pageDelegate8 != null) {
                pageDelegate8.onTouch(event);
                Unit unit5 = Unit.INSTANCE;
            }
            PageDelegate pageDelegate9 = this.pageDelegate;
            if (pageDelegate9 != null) {
                pageDelegate9.onDown();
                Unit unit6 = Unit.INSTANCE;
            }
            setStartPoint(event.getX(), event.getY(), false);
            getCallback().dismissFunctionMenu();
        }
        return true;
    }

    public final void readFirstPageContent() {
        click(1);
    }

    public final void resetSelect() {
        this.isTextSelected = false;
    }

    public final void setAbortAnim(boolean z2) {
        this.isAbortAnim = z2;
    }

    public final void setCursorView(@NotNull View... v2) {
        Intrinsics.checkNotNullParameter(v2, "v");
        this.dragCursorViews.clear();
        CollectionsKt__MutableCollectionsKt.addAll(this.dragCursorViews, v2);
    }

    public final void setLastX(float f2) {
        this.lastX = f2;
    }

    public final void setLastY(float f2) {
        this.lastY = f2;
    }

    public final void setPageFactory(@NotNull TextPageFactory textPageFactory) {
        Intrinsics.checkNotNullParameter(textPageFactory, "<set-?>");
        this.pageFactory = textPageFactory;
    }

    public final void setPageSlopSquare2(int i2) {
        this.pageSlopSquare2 = i2;
    }

    public void setScroll(boolean z2) {
        this.isScroll = z2;
    }

    public final void setSelectTextPosition(int i2) {
        this.selectTextPosition = i2;
    }

    public final void setSelectTextPosition_original(int i2) {
        this.selectTextPosition_original = i2;
    }

    public final void setStartPoint(float x2, float y2, boolean invalidate) {
        this.startX = x2;
        this.startY = y2;
        this.lastX = x2;
        this.lastY = y2;
        this.touchX = x2;
        this.touchY = y2;
        if (invalidate) {
            invalidate();
        }
    }

    public final void setStartPosition(int i2) {
        this.startPosition = i2;
    }

    public final void setStartX(float f2) {
        this.startX = f2;
    }

    public final void setStartY(float f2) {
        this.startY = f2;
    }

    public final void setTouchPoint(float x2, float y2, boolean invalidate) {
        this.lastX = this.touchX;
        this.lastY = this.touchY;
        this.touchX = x2;
        this.touchY = y2;
        if (invalidate) {
            invalidate();
        }
        PageDelegate pageDelegate = this.pageDelegate;
        if (pageDelegate != null) {
            pageDelegate.onScroll();
        }
    }

    public final void setTouchX(float f2) {
        this.touchX = f2;
    }

    public final void setTouchY(float f2) {
        this.touchY = f2;
    }

    public final void upBattery(int battery) {
        float f2 = battery * 1.0f;
        getCurPage().upBattery(f2);
        getPrevPage().upBattery(f2);
        getNextPage().upBattery(f2);
    }

    @Override // com.ykb.ebook.common_interface.DataSource
    public void upContent(int relativePosition, boolean resetPageOffset) throws SecurityException, NumberFormatException {
        post(new Runnable() { // from class: com.ykb.ebook.weight.v0
            @Override // java.lang.Runnable
            public final void run() {
                ReadView.upContent$lambda$13(this.f26541c);
            }
        });
        String prefString = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, "");
        String str = prefString != null ? prefString : "";
        if (getIsScroll()) {
            if (relativePosition == 0) {
                PageView.setContent$default(getCurPage(), this.pageFactory.getCurPage(), resetPageOffset, false, 4, null);
            } else {
                getCurPage().invalidateContentView();
            }
            if (!TextUtils.isEmpty(str)) {
                handleJumpUnderLineNoteParagraphComment(str);
            }
        } else {
            if (!TextUtils.isEmpty(str)) {
                handleJumpUnderLineNoteParagraphComment(str);
            } else if (relativePosition == -1) {
                PageView.setContent$default(getPrevPage(), this.pageFactory.getPrevPage(), false, false, 6, null);
            } else if (relativePosition != 1) {
                PageView.setContent$default(getCurPage(), this.pageFactory.getCurPage(), resetPageOffset, false, 4, null);
                PageView.setContent$default(getNextPage(), this.pageFactory.getNextPage(), false, false, 6, null);
                PageView.setContent$default(getPrevPage(), this.pageFactory.getPrevPage(), false, false, 6, null);
            } else {
                PageView.setContent$default(getNextPage(), this.pageFactory.getNextPage(), false, false, 6, null);
            }
            try {
                updateFirstLastPageFlipPageShadow();
            } catch (Exception unused) {
            }
        }
        getCallback().screenOffTimerStart();
    }

    public final void upPageAnim() {
        ReadBook readBook = ReadBook.INSTANCE;
        setScroll(readBook.pageAnim() == 3);
        ReadBookActivity readBookActivity = getReadBookActivity();
        if (readBookActivity != null) {
            readBookActivity.uiConfigChange();
        }
        int iPageAnim = readBook.pageAnim();
        if (iPageAnim != 0) {
            if (iPageAnim != 1) {
                if (iPageAnim != 2) {
                    if (iPageAnim != 3) {
                        if (!(this.pageDelegate instanceof NoAnimPageDelegate)) {
                            setPageDelegate(new NoAnimPageDelegate(this));
                        }
                    } else if (!(this.pageDelegate instanceof ScrollPageDelegate)) {
                        setPageDelegate(new ScrollPageDelegate(this));
                    }
                } else if (!(this.pageDelegate instanceof SimulationPageDelegate)) {
                    setPageDelegate(new SimulationPageDelegate(this));
                }
            } else if (!(this.pageDelegate instanceof VerticalSlidePageDelegate)) {
                setPageDelegate(new VerticalSlidePageDelegate(this));
            }
        } else if (!(this.pageDelegate instanceof CoverPageDelegate)) {
            setPageDelegate(new CoverPageDelegate(this));
        }
        PageDelegate pageDelegate = this.pageDelegate;
        ScrollPageDelegate scrollPageDelegate = pageDelegate instanceof ScrollPageDelegate ? (ScrollPageDelegate) pageDelegate : null;
        if (scrollPageDelegate != null) {
            scrollPageDelegate.setNoAnim(ReadConfig.INSTANCE.getNoAnimScrollPage());
        }
        PageDelegate pageDelegate2 = this.pageDelegate;
        if (pageDelegate2 != null) {
            pageDelegate2.setViewSize(getWidth(), getHeight());
        }
        getCurPage().setIsScroll(getIsScroll());
    }

    public final void upStatusBar() {
        getCurPage().upStatusBar();
        getPrevPage().upStatusBar();
        getNextPage().upStatusBar();
    }

    public final void upTime() {
        getCurPage().upTime();
        getPrevPage().upTime();
        getNextPage().upTime();
    }

    public final void upTimeShow() {
        getCurPage().upTimeShow();
        getPrevPage().upTimeShow();
        getNextPage().upTimeShow();
    }

    public final void updateThemeStyle(boolean refresh) throws SecurityException, NumberFormatException {
        Drawable colorDrawable;
        TextPaint contentPaint = ChapterProvider.getContentPaint();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        contentPaint.setColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        ChapterProvider.getTitlePaint().setColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        ChapterProvider.getTitleNumPaint().setColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        getCurPage().upBg(refresh);
        getPrevPage().upBg(refresh);
        getNextPage().upBg(refresh);
        if (readConfig.getFirstPage()) {
            getCurPage().setFirstPageContent();
        } else if (readConfig.getLastPage()) {
            getCurPage().setLastPageContent();
        }
        if (readConfig.getColorMode() == 1) {
            colorDrawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_content_view);
        } else {
            colorDrawable = new ColorDrawable(readConfig.getColorMode() == 2 ? ContextCompat.getColor(getContext(), R.color.color_0d111d) : ContextCompat.getColor(getContext(), R.color.white));
        }
        setBackground(colorDrawable);
        ReadBook.INSTANCE.resetPageBitMap();
        try {
            updateFirstLastPageFlipPageShadow();
        } catch (Exception unused) {
        }
    }
}
