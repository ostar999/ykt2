package com.ykb.ebook.page;

import android.graphics.Paint;
import android.text.TextPaint;
import android.util.Size;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.LayoutProgressListener;
import com.ykb.ebook.model.ChapterInfo;
import com.ykb.ebook.model.TextChapter;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.model.TextPage;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.page.column.ImageColumn;
import com.ykb.ebook.page.column.QuestionColumn;
import com.ykb.ebook.page.column.ReviewColumn;
import com.ykb.ebook.page.column.TextColumn;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.text.StringsKt__StringBuilderJVMKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.SendChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000¶\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\u0013\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0016\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJB\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020\u00182\u0006\u0010M\u001a\u00020N2\u0006\u0010O\u001a\u00020#2\u0006\u0010P\u001a\u00020!2\u0006\u0010Q\u001a\u00020!2\u0006\u0010R\u001a\u00020/2\b\b\u0002\u0010S\u001a\u00020/H\u0002JL\u0010T\u001a\u00020K2\u0006\u0010L\u001a\u00020\u00182\u0006\u0010M\u001a\u00020N2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020#0V2\u0006\u0010W\u001a\u00020\u001d2\u0006\u0010X\u001a\u00020!2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020!0V2\u0006\u0010S\u001a\u00020/H\u0002JT\u0010Z\u001a\u00020K2\u0006\u0010L\u001a\u00020\u00182\u0006\u0010M\u001a\u00020N2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020#0V2\u0006\u0010W\u001a\u00020\u001d2\u0006\u0010X\u001a\u00020!2\u0006\u0010[\u001a\u00020!2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020!0V2\u0006\u0010S\u001a\u00020/H\u0002JL\u0010\\\u001a\u00020K2\u0006\u0010L\u001a\u00020\u00182\u0006\u0010M\u001a\u00020N2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020#0V2\u0006\u0010[\u001a\u00020!2\u0006\u0010]\u001a\u00020/2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020!0V2\u0006\u0010S\u001a\u00020/H\u0002J0\u0010^\u001a\u00020K2\u0016\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\t2\u0006\u0010M\u001a\u00020N2\u0006\u0010_\u001a\u00020\u0018H\u0002J\u0006\u0010`\u001a\u00020KJ&\u0010a\u001a\u00020K2\u0006\u0010L\u001a\u00020\u00182\u0006\u0010M\u001a\u00020N2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020#0VH\u0002J!\u0010b\u001a\u00020K2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\n\u001a\u00020\u000bH\u0083@ø\u0001\u0000¢\u0006\u0002\u0010cJ\u0010\u0010d\u001a\u00020/2\u0006\u0010O\u001a\u00020eH\u0002JN\u0010f\u001a.\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020#0\u0007j\b\u0012\u0004\u0012\u00020#`\t\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020!0\u0007j\b\u0012\u0004\u0012\u00020!`\t0g2\u0006\u0010h\u001a\u00020#2\u0006\u0010i\u001a\u00020j2\b\b\u0002\u0010k\u001a\u00020\u0018H\u0002J\b\u0010l\u001a\u00020KH\u0002J\u0010\u0010m\u001a\u00020K2\u0006\u0010n\u001a\u00020'H\u0002J\b\u0010o\u001a\u00020KH\u0002J\u0010\u0010p\u001a\u00020K2\b\u0010q\u001a\u0004\u0018\u000104JI\u0010r\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020!0g2\u0006\u0010s\u001a\u00020#2\u0006\u0010t\u001a\u00020\u00182\u0006\u0010u\u001a\u00020!2\u0006\u0010v\u001a\u00020!2\n\u0010;\u001a\u00060<j\u0002`=H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010wJk\u0010x\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020!0g2\u0006\u0010t\u001a\u00020\u00182\u0006\u0010u\u001a\u00020!2\u0006\u0010h\u001a\u00020#2\u0006\u0010W\u001a\u00020\u001d2\u0006\u0010v\u001a\u00020!2\u0006\u0010y\u001a\u00020\u001f2\b\b\u0002\u0010S\u001a\u00020/2\b\b\u0002\u0010z\u001a\u00020/2\b\b\u0002\u0010{\u001a\u00020/H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010|R\u0015\u0010\r\u001a\u00020\u000e8Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0015\u0010\u0017\u001a\u00020\u00188Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\"\u001a\u00020#8Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u001c\u0010&\u001a\u0004\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u000e\u0010,\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u00100\u001a\u0006\u0012\u0002\b\u000301X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u00103\u001a\u0004\u0018\u000104X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010;\u001a\u00060<j\u0002`=X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010>\u001a\n ?*\u0004\u0018\u00010#0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006}"}, d2 = {"Lcom/ykb/ebook/page/TextChapterLayout;", "", Constants.PARAM_SCOPE, "Lkotlinx/coroutines/CoroutineScope;", "textChapter", "Lcom/ykb/ebook/model/TextChapter;", "textPages", "Ljava/util/ArrayList;", "Lcom/ykb/ebook/model/TextPage;", "Lkotlin/collections/ArrayList;", "bookContent", "Lcom/ykb/ebook/model/BookContent;", "(Lkotlinx/coroutines/CoroutineScope;Lcom/ykb/ebook/model/TextChapter;Ljava/util/ArrayList;Lcom/ykb/ebook/model/BookContent;)V", "bookChapter", "Lcom/ykb/ebook/model/ChapterInfo;", "getBookChapter", "()Lcom/ykb/ebook/model/ChapterInfo;", "channel", "Lkotlinx/coroutines/channels/Channel;", "getChannel", "()Lkotlinx/coroutines/channels/Channel;", "setChannel", "(Lkotlinx/coroutines/channels/Channel;)V", "chaptersSize", "", "getChaptersSize", "()I", "charIndex", "contentPaint", "Landroid/text/TextPaint;", "contentPaintFontMetrics", "Landroid/graphics/Paint$FontMetrics;", "contentPaintTextHeight", "", "displayTitle", "", "getDisplayTitle", "()Ljava/lang/String;", "exception", "", "getException", "()Ljava/lang/Throwable;", "setException", "(Ljava/lang/Throwable;)V", "imgIndex", "indentCharWidth", "isCompleted", "", "job", "Lcom/ykb/ebook/util/Coroutine;", "lineSpacingExtra", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/ykb/ebook/common_interface/LayoutProgressListener;", "paddingLeft", "paddingTop", "paragraphSpacing", "pendingTextPage", "questionIndex", "reviewIndex", "stringBuilder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "tag", "kotlin.jvm.PlatformType", "titleBottomSpacing", "titleNumPaint", "titleNumPaintFontMetrics", "titleNumPaintTextHeight", "titlePaint", "titlePaintFontMetrics", "titlePaintTextHeight", "titleTopSpacing", "visibleHeight", "visibleWidth", "addCharToLine", "", "absStartX", "textLine", "Lcom/ykb/ebook/model/TextLine;", "char", "xStart", "xEnd", "isLineEnd", "isTitle", "addCharsToLineFirst", "words", "", "textPaint", "desiredWidth", "textWidths", "addCharsToLineMiddle", "startX", "addCharsToLineNatural", "hasIndent", "calcTextLinePosition", "sbLength", "cancel", "exceed", "getTextChapter", "(Ljava/lang/String;Lcom/ykb/ebook/model/BookContent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isZeroWidthChar", "", "measureTextSplit", "Lkotlin/Pair;", "text", "widthsArray", "", "start", "onCompleted", "onException", AliyunLogKey.KEY_EVENT, "onPageCompleted", "setProgressListener", NotifyType.LIGHTS, "setTypeImage", "src", "x", "y", "textHeight", "(Ljava/lang/String;IFFLjava/lang/StringBuilder;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setTypeText", "fontMetrics", "emptyContent", "isTieleNum", "(IFLjava/lang/String;Landroid/text/TextPaint;FLandroid/graphics/Paint$FontMetrics;ZZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTextChapterLayout.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TextChapterLayout.kt\ncom/ykb/ebook/page/TextChapterLayout\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,894:1\n81#1:895\n83#1:896\n82#1:897\n1864#2,3:898\n1855#2,2:901\n533#2,6:904\n533#2,6:910\n1774#2,4:916\n1#3:903\n*S KotlinDebug\n*F\n+ 1 TextChapterLayout.kt\ncom/ykb/ebook/page/TextChapterLayout\n*L\n130#1:895\n131#1:896\n132#1:897\n250#1:898,3\n393#1:901,2\n599#1:904,6\n600#1:910,6\n684#1:916,4\n*E\n"})
/* loaded from: classes7.dex */
public final class TextChapterLayout {

    @NotNull
    private final com.ykb.ebook.model.BookContent bookContent;

    @NotNull
    private Channel<TextPage> channel;
    private int charIndex;

    @NotNull
    private final TextPaint contentPaint;

    @NotNull
    private final Paint.FontMetrics contentPaintFontMetrics;
    private final float contentPaintTextHeight;

    @Nullable
    private Throwable exception;
    private int imgIndex;
    private final float indentCharWidth;
    private boolean isCompleted;

    @NotNull
    private final Coroutine<?> job;
    private final float lineSpacingExtra;

    @Nullable
    private volatile LayoutProgressListener listener;
    private final int paddingLeft;
    private final int paddingTop;
    private final int paragraphSpacing;

    @NotNull
    private TextPage pendingTextPage;
    private int questionIndex;
    private int reviewIndex;

    @NotNull
    private final StringBuilder stringBuilder;
    private final String tag;

    @NotNull
    private final TextChapter textChapter;

    @NotNull
    private final ArrayList<TextPage> textPages;
    private final int titleBottomSpacing;

    @NotNull
    private final TextPaint titleNumPaint;

    @NotNull
    private final Paint.FontMetrics titleNumPaintFontMetrics;
    private final float titleNumPaintTextHeight;

    @NotNull
    private final TextPaint titlePaint;

    @NotNull
    private final Paint.FontMetrics titlePaintFontMetrics;
    private final float titlePaintTextHeight;
    private final int titleTopSpacing;
    private final int visibleHeight;
    private final int visibleWidth;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.TextChapterLayout$1", f = "TextChapterLayout.kt", i = {}, l = {98}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nTextChapterLayout.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TextChapterLayout.kt\ncom/ykb/ebook/page/TextChapterLayout$1\n+ 2 TextChapterLayout.kt\ncom/ykb/ebook/page/TextChapterLayout\n*L\n1#1,894:1\n82#2:895\n*S KotlinDebug\n*F\n+ 1 TextChapterLayout.kt\ncom/ykb/ebook/page/TextChapterLayout$1\n*L\n98#1:895\n*E\n"})
    /* renamed from: com.ykb.ebook.page.TextChapterLayout$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return TextChapterLayout.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                TextChapterLayout textChapterLayout = TextChapterLayout.this;
                String title = textChapterLayout.textChapter.getTitle();
                com.ykb.ebook.model.BookContent bookContent = TextChapterLayout.this.bookContent;
                this.label = 1;
                if (textChapterLayout.getTextChapter(title, bookContent, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.TextChapterLayout$2", f = "TextChapterLayout.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.page.TextChapterLayout$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            AnonymousClass2 anonymousClass2 = TextChapterLayout.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = th;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Throwable th = (Throwable) this.L$0;
            TextChapterLayout.this.setException(th);
            TextChapterLayout.this.onException(th);
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.page.TextChapterLayout$3", f = "TextChapterLayout.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.page.TextChapterLayout$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return TextChapterLayout.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            TextChapterLayout.this.isCompleted = true;
            return Unit.INSTANCE;
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.ykb.ebook.page.TextChapterLayout", f = "TextChapterLayout.kt", i = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4}, l = {215, R2.attr.adSizes, R2.attr.all_course_subject_bg1_end_color, 312, 326}, m = "getTextChapter", n = {"this", "contents", "absStartX", "durY", "isPay", "this", "absStartX", "durY", "content", "isPay", "index$iv", "index", "this", "absStartX", "durY", "content", "isPay", "index$iv", "index", "this", "absStartX", "durY", "isPay", "index$iv", "this", "absStartX", "durY", "isPay", "index$iv"}, s = {"L$0", "L$1", "L$2", "L$3", "Z$0", "L$0", "L$1", "L$2", "L$4", "Z$0", "I$0", "I$1", "L$0", "L$1", "L$2", "L$4", "Z$0", "I$0", "I$1", "L$0", "L$1", "L$2", "Z$0", "I$0", "L$0", "L$1", "L$2", "Z$0", "I$0"})
    /* renamed from: com.ykb.ebook.page.TextChapterLayout$getTextChapter$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10531 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C10531(Continuation<? super C10531> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TextChapterLayout.this.getTextChapter(null, null, this);
        }
    }

    public TextChapterLayout(@NotNull CoroutineScope scope, @NotNull TextChapter textChapter, @NotNull ArrayList<TextPage> textPages, @NotNull com.ykb.ebook.model.BookContent bookContent) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(textChapter, "textChapter");
        Intrinsics.checkNotNullParameter(textPages, "textPages");
        Intrinsics.checkNotNullParameter(bookContent, "bookContent");
        this.textChapter = textChapter;
        this.textPages = textPages;
        this.bookContent = bookContent;
        this.tag = TextChapterLayout.class.getSimpleName();
        this.listener = textChapter;
        this.paddingLeft = ChapterProvider.getPaddingLeft();
        this.paddingTop = ChapterProvider.getPaddingTop();
        this.titlePaint = ChapterProvider.getTitlePaint();
        this.titleNumPaint = ChapterProvider.getTitleNumPaint();
        this.titlePaintTextHeight = ChapterProvider.getTitlePaintTextHeight();
        this.titlePaintFontMetrics = ChapterProvider.getTitlePaintFontMetrics();
        this.titleNumPaintTextHeight = ChapterProvider.getTitleNumPaintTextHeight();
        this.titleNumPaintFontMetrics = ChapterProvider.getTitleNumPaintFontMetrics();
        this.contentPaint = ChapterProvider.getContentPaint();
        this.contentPaintTextHeight = ChapterProvider.INSTANCE.getContentPaintTextHeight();
        this.contentPaintFontMetrics = ChapterProvider.getContentPaintFontMetrics();
        this.titleTopSpacing = ChapterProvider.getTitleTopSpacing();
        this.titleBottomSpacing = ChapterProvider.getTitleBottomSpacing();
        this.lineSpacingExtra = ChapterProvider.getLineSpacingExtra();
        this.paragraphSpacing = ChapterProvider.getParagraphSpacing();
        this.visibleHeight = ChapterProvider.getVisibleHeight();
        this.visibleWidth = ChapterProvider.getVisibleWidth();
        this.indentCharWidth = ChapterProvider.getIndentCharWidth();
        this.stringBuilder = new StringBuilder();
        this.pendingTextPage = new TextPage(0, null, null, null, 0, 0, 0.0f, 0, 255, null);
        this.channel = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6, null);
        this.job = Coroutine.onFinally$default(Coroutine.onError$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, scope, null, null, null, new AnonymousClass1(null), 14, null), null, new AnonymousClass2(null), 1, null), null, new AnonymousClass3(null), 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addCharToLine(int absStartX, TextLine textLine, String str, float xStart, float xEnd, boolean isLineEnd, boolean isTitle) {
        int i2;
        TextLine textLine2;
        BaseColumn imageColumn;
        if (Intrinsics.areEqual(str, ReadConfig.QUESTION_SINGLE_CHAR)) {
            float f2 = absStartX;
            float f3 = f2 + xStart;
            float f4 = f2 + xEnd;
            List<String> arrayList = this.textChapter.getChapter().getQuestionIds().get(Integer.valueOf(this.questionIndex));
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            int i3 = this.questionIndex;
            this.questionIndex = i3 + 1;
            imageColumn = new QuestionColumn(f3, f4, true, arrayList, i3);
        } else {
            if (!Intrinsics.areEqual(str, ReadConfig.QUESTION_MULTI_CHAR)) {
                if (isLineEnd && Intrinsics.areEqual(str, ReadConfig.REVIEW_CHAR)) {
                    int i4 = this.reviewIndex + 1;
                    this.reviewIndex = i4;
                    float f5 = absStartX;
                    ReviewColumn reviewColumn = new ReviewColumn(f5 + xStart, f5 + xEnd, i4, this.textChapter.getChapter().getReviewList());
                    textLine2 = textLine;
                    imageColumn = reviewColumn;
                } else if (Intrinsics.areEqual(str, "@")) {
                    float f6 = absStartX;
                    imageColumn = new ImageColumn(f6 + xStart, f6 + xEnd, this.imgIndex, this.textChapter.getChapter().getImgList());
                } else {
                    float f7 = absStartX;
                    float f8 = f7 + xStart;
                    float f9 = f7 + xEnd;
                    if (isTitle || Intrinsics.areEqual(str, ReadConfig.INDENT_CHAR)) {
                        i2 = -1;
                    } else {
                        i2 = this.charIndex;
                        this.charIndex = i2 + 1;
                    }
                    TextColumn textColumn = new TextColumn(f8, f9, i2, str, null, 0, 0, null, 0, 0, null, 0, R2.color.FF333333, null);
                    textLine2 = textLine;
                    imageColumn = textColumn;
                }
                textLine2.addColumn(imageColumn);
            }
            float f10 = absStartX;
            float f11 = f10 + xStart;
            float f12 = f10 + xEnd;
            List<String> arrayList2 = this.textChapter.getChapter().getQuestionIds().get(Integer.valueOf(this.questionIndex));
            if (arrayList2 == null) {
                arrayList2 = new ArrayList<>();
            }
            int i5 = this.questionIndex;
            this.questionIndex = i5 + 1;
            imageColumn = new QuestionColumn(f11, f12, false, arrayList2, i5);
        }
        textLine2 = textLine;
        textLine2.addColumn(imageColumn);
    }

    private final void addCharsToLineFirst(int absStartX, TextLine textLine, List<String> words, TextPaint textPaint, float desiredWidth, List<Float> textWidths, boolean isTitle) {
        float f2;
        float f3 = 0.0f;
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (!readConfig.getTextFullJustify()) {
            addCharsToLineNatural(absStartX, textLine, words, 0.0f, true, textWidths, isTitle);
            return;
        }
        String paragraphIndent = readConfig.getParagraphIndent();
        int length = paragraphIndent.length();
        int i2 = 0;
        while (true) {
            f2 = f3;
            if (i2 >= length) {
                break;
            }
            f3 = this.indentCharWidth + f2;
            float f4 = absStartX;
            textLine.addColumn(new TextColumn(f4 + f2, f4 + f3, -1, ReadConfig.INDENT_CHAR, null, 0, 0, null, 0, 0, null, 0, R2.color.FF333333, null));
            textLine.setIndentWidth(f3);
            i2++;
        }
        textLine.setIndentSize(paragraphIndent.length());
        if (words.size() > paragraphIndent.length()) {
            addCharsToLineMiddle(absStartX, textLine, words.subList(paragraphIndent.length(), words.size()), textPaint, desiredWidth, f2, textWidths.subList(paragraphIndent.length(), textWidths.size()), isTitle);
        }
    }

    private final void addCharsToLineMiddle(int absStartX, TextLine textLine, List<String> words, TextPaint textPaint, float desiredWidth, float startX, List<Float> textWidths, boolean isTitle) {
        int i2;
        int i3;
        if (!ReadConfig.INSTANCE.getTextFullJustify()) {
            addCharsToLineNatural(absStartX, textLine, words, startX, false, textWidths, isTitle);
            return;
        }
        float f2 = this.visibleWidth - desiredWidth;
        List<String> list = words;
        if ((list instanceof Collection) && list.isEmpty()) {
            i3 = absStartX;
            i2 = 0;
        } else {
            Iterator<T> it = list.iterator();
            i2 = 0;
            while (it.hasNext()) {
                if (Intrinsics.areEqual((String) it.next(), " ") && (i2 = i2 + 1) < 0) {
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                }
            }
            i3 = absStartX;
        }
        textLine.setStartX(i3 + startX);
        if (i2 > 1) {
            float f3 = f2 / i2;
            textLine.setWordSpacing(f3);
            float f4 = startX;
            int i4 = 0;
            for (int size = words.size(); i4 < size; size = size) {
                String str = words.get(i4);
                float fFloatValue = textWidths.get(i4).floatValue();
                float f5 = (!Intrinsics.areEqual(str, " ") || i4 == CollectionsKt__CollectionsKt.getLastIndex(words)) ? fFloatValue + f4 : fFloatValue + f4 + f3;
                int i5 = i4 + 1;
                addCharToLine(absStartX, textLine, str, f4, f5, i5 == words.size(), isTitle);
                f4 = f5;
                f3 = f3;
                i4 = i5;
            }
        } else {
            float lastIndex = f2 / CollectionsKt__CollectionsKt.getLastIndex(words);
            textLine.setExtraLetterSpacingOffsetX((-lastIndex) / 2);
            textLine.setExtraLetterSpacing(lastIndex / textPaint.getTextSize());
            int size2 = words.size();
            float f6 = startX;
            int i6 = 0;
            while (i6 < size2) {
                String str2 = words.get(i6);
                float fFloatValue2 = textWidths.get(i6).floatValue() + f6;
                if (i6 != CollectionsKt__CollectionsKt.getLastIndex(words)) {
                    fFloatValue2 += lastIndex;
                }
                float f7 = fFloatValue2;
                int i7 = i6 + 1;
                addCharToLine(absStartX, textLine, str2, f6, f7, i7 == words.size(), isTitle);
                f6 = f7;
                size2 = size2;
                i6 = i7;
            }
        }
        exceed(absStartX, textLine, words);
    }

    private final void addCharsToLineNatural(int absStartX, TextLine textLine, List<String> words, float startX, boolean hasIndent, List<Float> textWidths, boolean isTitle) {
        int length = ReadConfig.INSTANCE.getParagraphIndent().length();
        textLine.setStartX(absStartX + startX);
        int size = words.size();
        float f2 = startX;
        int i2 = 0;
        while (i2 < size) {
            String str = words.get(i2);
            float fFloatValue = f2 + textWidths.get(i2).floatValue();
            int i3 = i2 + 1;
            addCharToLine(absStartX, textLine, str, f2, fFloatValue, i3 == words.size(), isTitle);
            if (hasIndent && i2 == length - 1) {
                textLine.setIndentWidth(fFloatValue);
            }
            f2 = fFloatValue;
            i2 = i3;
        }
        exceed(absStartX, textLine, words);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void calcTextLinePosition(ArrayList<TextPage> textPages, TextLine textLine, int sbLength) {
        int chapterPosition;
        TextLine textLine2;
        TextLine textLinePrevious;
        List<TextLine> lines;
        TextLine textLine3;
        List<TextLine> lines2;
        List<TextLine> lines3 = this.pendingTextPage.getLines();
        ListIterator<TextLine> listIterator = lines3.listIterator(lines3.size());
        while (true) {
            chapterPosition = 0;
            textLine2 = null;
            if (!listIterator.hasPrevious()) {
                textLinePrevious = null;
                break;
            } else {
                textLinePrevious = listIterator.previous();
                if ((textLinePrevious.getParagraphNum() > 0) != false) {
                    break;
                }
            }
        }
        TextLine textLine4 = textLinePrevious;
        if (textLine4 == null) {
            TextPage textPage = (TextPage) CollectionsKt___CollectionsKt.lastOrNull((List) textPages);
            if (textPage != null && (lines2 = textPage.getLines()) != null) {
                ListIterator<TextLine> listIterator2 = lines2.listIterator(lines2.size());
                while (true) {
                    if (!listIterator2.hasPrevious()) {
                        break;
                    }
                    TextLine textLinePrevious2 = listIterator2.previous();
                    if ((textLinePrevious2.getParagraphNum() > 0) != false) {
                        textLine2 = textLinePrevious2;
                        break;
                    }
                }
                textLine2 = textLine2;
            }
        } else {
            textLine2 = textLine4;
        }
        textLine.setParagraphNum(textLine2 != null ? textLine2.isParagraphEnd() ? 1 + textLine2.getParagraphNum() : textLine2.getParagraphNum() : 1);
        TextPage textPage2 = (TextPage) CollectionsKt___CollectionsKt.lastOrNull((List) textPages);
        if (textPage2 != null && (lines = textPage2.getLines()) != null && (textLine3 = (TextLine) CollectionsKt___CollectionsKt.lastOrNull((List) lines)) != null) {
            chapterPosition = textLine3.getChapterPosition() + textLine3.getCharSize() + (textLine3.isParagraphEnd() ? 1 : 0);
        }
        textLine.setChapterPosition(chapterPosition + sbLength);
        textLine.setPagePosition(sbLength);
    }

    private final void exceed(int absStartX, TextLine textLine, List<String> words) {
        BaseColumn baseColumn;
        int i2;
        int size = words.size();
        if (size < 2) {
            return;
        }
        int i3 = absStartX + this.visibleWidth;
        List<BaseColumn> columns = textLine.getColumns();
        if (Intrinsics.areEqual(CollectionsKt___CollectionsKt.last((List) words), " ")) {
            size--;
            baseColumn = columns.get(CollectionsKt__CollectionsKt.getLastIndex(columns) - 1);
            i2 = 1;
        } else {
            baseColumn = (BaseColumn) CollectionsKt___CollectionsKt.last((List) columns);
            i2 = 0;
        }
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(baseColumn.getEnd());
        if (iRoundToInt > i3) {
            textLine.setExceed(true);
            int i4 = (iRoundToInt - i3) / size;
            for (int i5 = 0; i5 < size; i5++) {
                BaseColumn columnReverseAt = textLine.getColumnReverseAt(i5, i2);
                float f2 = (size - i5) * i4;
                columnReverseAt.setStart(columnReverseAt.getStart() - f2);
                columnReverseAt.setEnd(columnReverseAt.getEnd() - f2);
            }
        }
    }

    private final ChapterInfo getBookChapter() {
        return this.textChapter.getChapter();
    }

    private final int getChaptersSize() {
        return this.textChapter.getChaptersSize();
    }

    private final String getDisplayTitle() {
        return this.textChapter.getTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01d5  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x030e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x033a  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x03b8  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x043e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x0179 -> B:30:0x017b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x0197 -> B:31:0x0195). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:82:0x0404 -> B:84:0x0407). Please report as a decompilation issue!!! */
    @android.annotation.SuppressLint({"SuspiciousIndentation"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getTextChapter(java.lang.String r43, com.ykb.ebook.model.BookContent r44, kotlin.coroutines.Continuation<? super kotlin.Unit> r45) {
        /*
            Method dump skipped, instructions count: 1155
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.page.TextChapterLayout.getTextChapter(java.lang.String, com.ykb.ebook.model.BookContent, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final boolean isZeroWidthChar(char c3) {
        return c3 == 8203 || c3 == 8204 || c3 == 8288;
    }

    private final Pair<ArrayList<String>, ArrayList<Float>> measureTextSplit(String text, float[] widthsArray, int start) {
        int length = text.length();
        int i2 = start + length;
        int i3 = 0;
        for (int i4 = start; i4 < i2; i4++) {
            if (widthsArray[i4] > 0.0f) {
                i3++;
            }
        }
        ArrayList arrayList = new ArrayList(i3);
        ArrayList arrayList2 = new ArrayList(i3);
        int i5 = 0;
        while (i5 < length) {
            int i6 = i5 + 1;
            arrayList.add(Float.valueOf(widthsArray[start + i5]));
            while (i6 < length) {
                if (!(widthsArray[start + i6] == 0.0f) || isZeroWidthChar(text.charAt(i6))) {
                    break;
                }
                i6++;
            }
            String strSubstring = text.substring(i5, i6);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
            arrayList2.add(strSubstring);
            i5 = i6;
        }
        return TuplesKt.to(arrayList2, arrayList);
    }

    public static /* synthetic */ Pair measureTextSplit$default(TextChapterLayout textChapterLayout, String str, float[] fArr, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        return textChapterLayout.measureTextSplit(str, fArr, i2);
    }

    private final void onCompleted() {
        if (!this.textChapter.getChapter().isPay() && this.textPages.size() > 1) {
            ArrayList<TextPage> arrayList = this.textPages;
            arrayList.subList(1, arrayList.size()).clear();
        }
        SendChannel.DefaultImpls.close$default(this.channel, null, 1, null);
        try {
            try {
                LayoutProgressListener layoutProgressListener = this.listener;
                if (layoutProgressListener != null) {
                    layoutProgressListener.onLayoutCompleted();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                Log log = Log.INSTANCE;
                String tag = this.tag;
                Intrinsics.checkNotNullExpressionValue(tag, "tag");
                log.logE(tag, "调用布局进度监听回调出错\n" + e2.getLocalizedMessage());
            }
            Log.INSTANCE.logD("chapter_pages", String.valueOf(this.textPages.size()));
            ReadBook.INSTANCE.setChapterPage(Integer.parseInt(this.textChapter.getChapter().getId()), this.textPages.size());
        } finally {
            this.listener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onException(Throwable e2) {
        this.channel.cancel(e2);
        if (e2 instanceof CancellationException) {
            return;
        }
        try {
            try {
                LayoutProgressListener layoutProgressListener = this.listener;
                if (layoutProgressListener != null) {
                    layoutProgressListener.onLayoutException(e2);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                Log log = Log.INSTANCE;
                String tag = this.tag;
                Intrinsics.checkNotNullExpressionValue(tag, "tag");
                log.logE(tag, "调用布局进度监听回调出错\n" + e3.getLocalizedMessage());
            }
        } finally {
            this.listener = null;
        }
    }

    private final void onPageCompleted() {
        TextPage textPage = (TextPage) CollectionsKt___CollectionsKt.last((List) this.textPages);
        textPage.setIndex(CollectionsKt__CollectionsKt.getLastIndex(this.textPages));
        textPage.setChapterIndex(this.textChapter.getChapter().getSort() - 1);
        textPage.setChapterSize(this.textChapter.getChaptersSize());
        textPage.setTitle(this.textChapter.getTitle());
        textPage.setPaddingTop(this.paddingTop);
        textPage.setCompleted(true);
        textPage.textChapter = this.textChapter;
        textPage.upLinesPosition();
        this.channel.mo2315trySendJP2dKIU(textPage);
        try {
            LayoutProgressListener layoutProgressListener = this.listener;
            if (layoutProgressListener != null) {
                layoutProgressListener.onLayoutPageCompleted(CollectionsKt__CollectionsKt.getLastIndex(this.textPages), textPage);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            Log log = Log.INSTANCE;
            String tag = this.tag;
            Intrinsics.checkNotNullExpressionValue(tag, "tag");
            log.logE(tag, "调用布局进度监听回调出错\n" + e2.getLocalizedMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object setTypeImage(String str, int i2, float f2, float f3, StringBuilder sb, Continuation<? super Pair<Integer, Float>> continuation) {
        Pair pair;
        float f4 = f2;
        Size size = com.ykb.ebook.model.ImageProvider.INSTANCE.getSize(str);
        Size size2 = new Size(this.visibleWidth, (int) ((this.visibleWidth / size.getWidth()) * size.getHeight()));
        if (size2.getWidth() > 0 && size2.getHeight() > 0) {
            if (f4 > this.visibleHeight) {
                TextPage textPage = this.pendingTextPage;
                if (textPage.getHeight() < f4) {
                    textPage.setHeight(f4);
                }
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "stringBuilder.toString()");
                if (string.length() == 0) {
                    string = "本页无文字内容";
                }
                textPage.setText(string);
                StringsKt__StringBuilderJVMKt.clear(sb);
                this.textPages.add(textPage);
                JobKt.ensureActive(continuation.getContext());
                onPageCompleted();
                this.pendingTextPage = new TextPage(0, null, null, null, 0, 0, 0.0f, 0, 255, null);
                f4 = 0.0f;
            }
            int height = size2.getHeight();
            int width = size2.getWidth();
            if (size2.getWidth() > this.visibleWidth) {
                height = (size2.getHeight() * this.visibleWidth) / size2.getWidth();
                width = this.visibleWidth;
            }
            int i3 = this.visibleHeight;
            if (height > i3) {
                width = (width * i3) / height;
                height = i3;
            }
            float f5 = height;
            if (f4 + f5 > i3) {
                TextPage textPage2 = this.pendingTextPage;
                if (textPage2.getHeight() < f4) {
                    textPage2.setHeight(f4);
                }
                if (textPage2.getLeftLineSize() == 0) {
                    textPage2.setLeftLineSize(textPage2.getLineSize());
                }
                String string2 = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string2, "stringBuilder.toString()");
                textPage2.setText(string2.length() == 0 ? "本页无文字内容" : string2);
                StringsKt__StringBuilderJVMKt.clear(sb);
                this.textPages.add(textPage2);
                JobKt.ensureActive(continuation.getContext());
                onPageCompleted();
                this.pendingTextPage = new TextPage(0, null, null, null, 0, 0, 0.0f, 0, 255, null);
                f4 = 0.0f;
            }
            TextLine textLine = new TextLine(null, null, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0, false, false, true, 0.0f, 0, 0.0f, 0.0f, 0.0f, false, false, 522239, null);
            textLine.setText(" ");
            textLine.setLineTop(this.paddingTop + f4);
            f4 += f5;
            textLine.setLineBottom(this.paddingTop + f4);
            if (this.visibleWidth > width) {
                float f6 = (r3 - width) / 2.0f;
                pair = new Pair(Boxing.boxFloat(f6), Boxing.boxFloat(f6 + width));
            } else {
                pair = new Pair(Boxing.boxFloat(0.0f), Boxing.boxFloat(width));
            }
            float f7 = i2;
            textLine.addColumn(new ImageColumn(((Number) pair.component1()).floatValue() + f7, f7 + ((Number) pair.component2()).floatValue(), this.imgIndex, this.textChapter.getChapter().getImgList()));
            calcTextLinePosition(this.textPages, textLine, sb.length());
            sb.append(" ");
            this.pendingTextPage.addLine(textLine);
        }
        return TuplesKt.to(Boxing.boxInt(i2), Boxing.boxFloat(f4 + ((this.paragraphSpacing * f3) / 10.0f)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:70:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x029f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object setTypeText(int r37, float r38, java.lang.String r39, android.text.TextPaint r40, float r41, android.graphics.Paint.FontMetrics r42, boolean r43, boolean r44, boolean r45, kotlin.coroutines.Continuation<? super kotlin.Pair<java.lang.Integer, java.lang.Float>> r46) {
        /*
            Method dump skipped, instructions count: 713
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.page.TextChapterLayout.setTypeText(int, float, java.lang.String, android.text.TextPaint, float, android.graphics.Paint$FontMetrics, boolean, boolean, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ Object setTypeText$default(TextChapterLayout textChapterLayout, int i2, float f2, String str, TextPaint textPaint, float f3, Paint.FontMetrics fontMetrics, boolean z2, boolean z3, boolean z4, Continuation continuation, int i3, Object obj) {
        return textChapterLayout.setTypeText(i2, f2, str, textPaint, f3, fontMetrics, (i3 & 64) != 0 ? false : z2, (i3 & 128) != 0 ? false : z3, (i3 & 256) != 0 ? false : z4, continuation);
    }

    public final void cancel() {
        Coroutine.cancel$default(this.job, null, 1, null);
        this.listener = null;
    }

    @NotNull
    public final Channel<TextPage> getChannel() {
        return this.channel;
    }

    @Nullable
    public final Throwable getException() {
        return this.exception;
    }

    public final void setChannel(@NotNull Channel<TextPage> channel) {
        Intrinsics.checkNotNullParameter(channel, "<set-?>");
        this.channel = channel;
    }

    public final void setException(@Nullable Throwable th) {
        this.exception = th;
    }

    public final void setProgressListener(@Nullable LayoutProgressListener l2) {
        try {
            if (!this.isCompleted) {
                Throwable th = this.exception;
                if (th == null) {
                    this.listener = l2;
                } else if (l2 != null) {
                    Intrinsics.checkNotNull(th);
                    l2.onLayoutException(th);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            Log log = Log.INSTANCE;
            String tag = this.tag;
            Intrinsics.checkNotNullExpressionValue(tag, "tag");
            log.logE(tag, "调用布局进度监听回调出错\n" + e2.getLocalizedMessage());
        }
    }
}
