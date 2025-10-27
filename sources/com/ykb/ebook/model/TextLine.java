package com.ykb.ebook.model;

import android.annotation.SuppressLint;
import android.content.AppCtxKt;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.ColorResourcesKt;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.page.ChapterProvider;
import com.ykb.ebook.page.canvans_recorder.CanvasRecorder;
import com.ykb.ebook.page.canvans_recorder.CanvasRecorderFactory;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.page.column.TextColumn;
import com.ykb.ebook.page.pool.PaintPool;
import com.ykb.ebook.weight.ContentTextView;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b1\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u0094\u00012\u00020\u0001:\u0002\u0094\u0001BÓ\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\t\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0015\u001a\u00020\t\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0017\u001a\u00020\t\u0012\b\b\u0002\u0010\u0018\u001a\u00020\t\u0012\b\b\u0002\u0010\u0019\u001a\u00020\t\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0012¢\u0006\u0002\u0010\u001cJ\u000e\u0010_\u001a\u00020`2\u0006\u0010a\u001a\u00020\u0006J\b\u0010b\u001a\u00020\u0012H\u0002J\t\u0010c\u001a\u00020\u0003HÆ\u0003J\t\u0010d\u001a\u00020\u0012HÆ\u0003J\t\u0010e\u001a\u00020\u0012HÆ\u0003J\t\u0010f\u001a\u00020\u0012HÆ\u0003J\t\u0010g\u001a\u00020\tHÆ\u0003J\t\u0010h\u001a\u00020\u000eHÆ\u0003J\t\u0010i\u001a\u00020\tHÆ\u0003J\t\u0010j\u001a\u00020\tHÆ\u0003J\t\u0010k\u001a\u00020\tHÆ\u0003J\t\u0010l\u001a\u00020\u0012HÆ\u0003J\t\u0010m\u001a\u00020\u0012HÆ\u0003J\u0019\u0010n\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007HÂ\u0003J\t\u0010o\u001a\u00020\tHÆ\u0003J\t\u0010p\u001a\u00020\tHÆ\u0003J\t\u0010q\u001a\u00020\tHÆ\u0003J\t\u0010r\u001a\u00020\tHÆ\u0003J\t\u0010s\u001a\u00020\u000eHÆ\u0003J\t\u0010t\u001a\u00020\u000eHÆ\u0003J\t\u0010u\u001a\u00020\u000eHÆ\u0003J×\u0001\u0010v\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\t2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000e2\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00122\b\b\u0002\u0010\u0014\u001a\u00020\u00122\b\b\u0002\u0010\u0015\u001a\u00020\t2\b\b\u0002\u0010\u0016\u001a\u00020\u000e2\b\b\u0002\u0010\u0017\u001a\u00020\t2\b\b\u0002\u0010\u0018\u001a\u00020\t2\b\b\u0002\u0010\u0019\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\u00122\b\b\u0002\u0010\u001b\u001a\u00020\u0012HÆ\u0001J\u0016\u0010w\u001a\u00020`2\u0006\u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020{J\u0018\u0010|\u001a\u00020`2\u0006\u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020{H\u0002J\u0013\u0010}\u001a\u00020\u00122\b\u0010~\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0018\u0010\u007f\u001a\u00020`2\u0006\u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020{H\u0003J\u0010\u0010\u0080\u0001\u001a\u00020\u00062\u0007\u0010\u0081\u0001\u001a\u00020\u000eJ\u001b\u0010\u0082\u0001\u001a\u00020\u00062\u0007\u0010\u0081\u0001\u001a\u00020\u000e2\t\b\u0002\u0010\u0083\u0001\u001a\u00020\u000eJ\n\u0010\u0084\u0001\u001a\u00020\u000eHÖ\u0001J\u0007\u0010\u0085\u0001\u001a\u00020`J\u0007\u0010\u0086\u0001\u001a\u00020`J\"\u0010\u0087\u0001\u001a\u00020\u00122\u0007\u0010\u0088\u0001\u001a\u00020\t2\u0007\u0010\u0089\u0001\u001a\u00020\t2\u0007\u0010\u008a\u0001\u001a\u00020\tJ\u0019\u0010\u008b\u0001\u001a\u00020\u00122\u0007\u0010\u0089\u0001\u001a\u00020\t2\u0007\u0010\u008a\u0001\u001a\u00020\tJ\u0010\u0010\u008c\u0001\u001a\u00020\u00122\u0007\u0010\u008a\u0001\u001a\u00020\tJ\u0007\u0010\u008d\u0001\u001a\u00020`J\n\u0010\u008e\u0001\u001a\u00020\u0003HÖ\u0001J#\u0010\u008f\u0001\u001a\u00020`2\u0007\u0010\u0090\u0001\u001a\u00020\t2\u0007\u0010\u0091\u0001\u001a\u00020\t2\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b$\u0010 R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00060&8F¢\u0006\u0006\u001a\u0004\b'\u0010(R\u001a\u0010\u001a\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001a\u0010\u0017\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u0010\u0018\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010.\"\u0004\b2\u00100R\u0012\u00103\u001a\u00020\t8Æ\u0002¢\u0006\u0006\u001a\u0004\b4\u0010.R\u001a\u0010\u0016\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010 \"\u0004\b6\u0010\"R\u001a\u0010\f\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010.\"\u0004\b8\u00100R\u001a\u0010\u0014\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010*\"\u0004\b9\u0010,R\u001a\u0010\u0013\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010*\"\u0004\b:\u0010,R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010*R\u001a\u0010\n\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010.\"\u0004\b<\u00100R\u001a\u0010\u000b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010.\"\u0004\b>\u00100R\u0014\u0010?\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b@\u0010.R\u0014\u0010A\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bB\u0010.R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010.\"\u0004\bD\u00100R\u001a\u0010\u001b\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010*\"\u0004\bF\u0010,R\u001a\u0010\u0010\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010 \"\u0004\bH\u0010\"R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010 \"\u0004\bJ\u0010\"R\u001a\u0010K\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010 \"\u0004\bM\u0010\"R\u001a\u0010\u0015\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010.\"\u0004\bO\u00100R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u001e\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010T\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010*\"\u0004\bV\u0010,R\u001a\u0010W\u001a\u00020XX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u001a\u0010\u0019\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010.\"\u0004\b^\u00100¨\u0006\u0095\u0001"}, d2 = {"Lcom/ykb/ebook/model/TextLine;", "", "text", "", "textColumns", "Ljava/util/ArrayList;", "Lcom/ykb/ebook/page/column/BaseColumn;", "Lkotlin/collections/ArrayList;", "lineTop", "", "lineBase", "lineBottom", "indentWidth", "paragraphNum", "", "chapterPosition", "pagePosition", "isTitle", "", "isParagraphEnd", "isImage", "startX", "indentSize", "extraLetterSpacing", "extraLetterSpacingOffsetX", "wordSpacing", "exceed", "onlyTextColumn", "(Ljava/lang/String;Ljava/util/ArrayList;FFFFIIIZZZFIFFFZZ)V", "canvasRecorder", "Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "getChapterPosition", "()I", "setChapterPosition", "(I)V", "charSize", "getCharSize", "columns", "", "getColumns", "()Ljava/util/List;", "getExceed", "()Z", "setExceed", "(Z)V", "getExtraLetterSpacing", "()F", "setExtraLetterSpacing", "(F)V", "getExtraLetterSpacingOffsetX", "setExtraLetterSpacingOffsetX", "height", "getHeight", "getIndentSize", "setIndentSize", "getIndentWidth", "setIndentWidth", "setImage", "setParagraphEnd", "getLineBase", "setLineBase", "getLineBottom", "setLineBottom", "lineEnd", "getLineEnd", "lineStart", "getLineStart", "getLineTop", "setLineTop", "getOnlyTextColumn", "setOnlyTextColumn", "getPagePosition", "setPagePosition", "getParagraphNum", "setParagraphNum", "searchResultColumnCount", "getSearchResultColumnCount", "setSearchResultColumnCount", "getStartX", "setStartX", "getText", "()Ljava/lang/String;", "setText", "(Ljava/lang/String;)V", "textLineIsNum", "getTextLineIsNum", "setTextLineIsNum", "textPage", "Lcom/ykb/ebook/model/TextPage;", "getTextPage", "()Lcom/ykb/ebook/model/TextPage;", "setTextPage", "(Lcom/ykb/ebook/model/TextPage;)V", "getWordSpacing", "setWordSpacing", "addColumn", "", "column", "checkFastDraw", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "draw", "view", "Lcom/ykb/ebook/weight/ContentTextView;", "canvas", "Landroid/graphics/Canvas;", "drawTextLine", "equals", "other", "fastDrawTextLine", "getColumn", "index", "getColumnReverseAt", "offset", "hashCode", "invalidate", "invalidateSelf", "isTouch", "x", "y", "relativeOffset", "isTouchY", "isVisible", "recycleRecorder", "toString", "upTopBottom", "durY", "textHeight", "fontMetrics", "Landroid/graphics/Paint$FontMetrics;", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTextLine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TextLine.kt\ncom/ykb/ebook/model/TextLine\n+ 2 CanvasRecorderExtensions.kt\ncom/ykb/ebook/extensions/CanvasRecorderExtensionsKt\n+ 3 Canvas.kt\nandroidx/core/graphics/CanvasKt\n+ 4 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,270:1\n53#1:271\n53#1:310\n53#1:311\n53#1:312\n53#1:313\n34#2:272\n12#2,9:273\n21#2,2:285\n24#2,3:290\n14#2:293\n35#2,2:294\n30#3,3:282\n34#3,3:287\n42#3,13:297\n42#4:296\n*S KotlinDebug\n*F\n+ 1 TextLine.kt\ncom/ykb/ebook/model/TextLine\n*L\n68#1:271\n127#1:310\n146#1:311\n159#1:312\n172#1:313\n68#1:272\n68#1:273,9\n68#1:285,2\n68#1:290,3\n68#1:293\n68#1:294,2\n68#1:282,3\n68#1:287,3\n117#1:297,13\n108#1:296\n*E\n"})
/* loaded from: classes7.dex */
public final /* data */ class TextLine {
    private static final boolean atLeastApi26;

    @NotNull
    private static final Lazy<Boolean> wordSpacingWorking$delegate;

    @NotNull
    private final CanvasRecorder canvasRecorder;
    private int chapterPosition;
    private boolean exceed;
    private float extraLetterSpacing;
    private float extraLetterSpacingOffsetX;
    private int indentSize;
    private float indentWidth;
    private boolean isImage;
    private boolean isParagraphEnd;
    private final boolean isTitle;
    private float lineBase;
    private float lineBottom;
    private float lineTop;
    private boolean onlyTextColumn;
    private int pagePosition;
    private int paragraphNum;
    private int searchResultColumnCount;
    private float startX;

    @NotNull
    private String text;

    @NotNull
    private final ArrayList<BaseColumn> textColumns;
    private boolean textLineIsNum;

    @NotNull
    private TextPage textPage;
    private float wordSpacing;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final TextLine emptyTextLine = new TextLine(null, null, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0, false, false, false, 0.0f, 0, 0.0f, 0.0f, 0.0f, false, false, 524287, null);

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0087\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lcom/ykb/ebook/model/TextLine$Companion;", "", "()V", "atLeastApi26", "", "emptyTextLine", "Lcom/ykb/ebook/model/TextLine;", "getEmptyTextLine", "()Lcom/ykb/ebook/model/TextLine;", "wordSpacingWorking", "getWordSpacingWorking", "()Z", "wordSpacingWorking$delegate", "Lkotlin/Lazy;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SuppressLint({"NewApi"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean getWordSpacingWorking() {
            return ((Boolean) TextLine.wordSpacingWorking$delegate.getValue()).booleanValue();
        }

        @NotNull
        public final TextLine getEmptyTextLine() {
            return TextLine.emptyTextLine;
        }
    }

    static {
        atLeastApi26 = Build.VERSION.SDK_INT >= 26;
        wordSpacingWorking$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Boolean>() { // from class: com.ykb.ebook.model.TextLine$Companion$wordSpacingWorking$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Boolean invoke() {
                PaintPool paintPool = PaintPool.INSTANCE;
                Paint paintObtain = paintPool.obtain();
                float fMeasureText = paintObtain.measureText("一二 三");
                paintObtain.setWordSpacing(10.0f);
                float fMeasureText2 = paintObtain.measureText("一二 三");
                paintPool.recycle(paintObtain);
                return Boolean.valueOf(fMeasureText2 - fMeasureText == 10.0f);
            }
        });
    }

    public TextLine() {
        this(null, null, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0, false, false, false, 0.0f, 0, 0.0f, 0.0f, 0.0f, false, false, 524287, null);
    }

    public TextLine(@NotNull String text, @NotNull ArrayList<BaseColumn> textColumns, float f2, float f3, float f4, float f5, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, float f6, int i5, float f7, float f8, float f9, boolean z5, boolean z6) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(textColumns, "textColumns");
        this.text = text;
        this.textColumns = textColumns;
        this.lineTop = f2;
        this.lineBase = f3;
        this.lineBottom = f4;
        this.indentWidth = f5;
        this.paragraphNum = i2;
        this.chapterPosition = i3;
        this.pagePosition = i4;
        this.isTitle = z2;
        this.isParagraphEnd = z3;
        this.isImage = z4;
        this.startX = f6;
        this.indentSize = i5;
        this.extraLetterSpacing = f7;
        this.extraLetterSpacingOffsetX = f8;
        this.wordSpacing = f9;
        this.exceed = z5;
        this.onlyTextColumn = z6;
        this.textPage = TextPage.INSTANCE.getEmptyTextPage();
        this.canvasRecorder = CanvasRecorderFactory.create$default(CanvasRecorderFactory.INSTANCE, false, 1, null);
    }

    private final boolean checkFastDraw() {
        if (this.exceed || !this.onlyTextColumn || this.textPage.getIsMsgPage()) {
            return false;
        }
        return (((this.wordSpacing > 0.0f ? 1 : (this.wordSpacing == 0.0f ? 0 : -1)) == 0) || (atLeastApi26 && INSTANCE.getWordSpacingWorking())) && this.searchResultColumnCount == 0;
    }

    private final ArrayList<BaseColumn> component2() {
        return this.textColumns;
    }

    private final void drawTextLine(ContentTextView view, Canvas canvas) throws Throwable {
        if (checkFastDraw()) {
            fastDrawTextLine(view, canvas);
            return;
        }
        int size = getColumns().size();
        for (int i2 = 0; i2 < size; i2++) {
            getColumns().get(i2).draw(view, canvas);
        }
    }

    @SuppressLint({"NewApi"})
    private final void fastDrawTextLine(ContentTextView view, Canvas canvas) throws Throwable {
        int i2;
        String str;
        TextPaint titleNumPaint = this.textLineIsNum ? ChapterProvider.getTitleNumPaint() : this.isTitle ? ChapterProvider.getTitlePaint() : ChapterProvider.getContentPaint();
        PaintPool paintPool = PaintPool.INSTANCE;
        Paint paintObtain = paintPool.obtain();
        paintObtain.set(titleNumPaint);
        titleNumPaint.setColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        if (!(this.extraLetterSpacing == 0.0f)) {
            paintObtain.setLetterSpacing(paintObtain.getLetterSpacing() + this.extraLetterSpacing);
        }
        float f2 = this.wordSpacing;
        if (!(f2 == 0.0f)) {
            paintObtain.setWordSpacing(f2);
        }
        float f3 = this.extraLetterSpacingOffsetX;
        if (f3 == 0.0f) {
            String str2 = this.text;
            canvas.drawText(str2, this.indentSize, str2.length(), this.startX, this.lineBase - this.lineTop, paintObtain);
        } else {
            int iSave = canvas.save();
            canvas.translate(f3, 0.0f);
            try {
                str = this.text;
                i2 = iSave;
            } catch (Throwable th) {
                th = th;
                i2 = iSave;
            }
            try {
                canvas.drawText(str, this.indentSize, str.length(), this.startX, this.lineBase - this.lineTop, paintObtain);
                canvas.restoreToCount(i2);
            } catch (Throwable th2) {
                th = th2;
                canvas.restoreToCount(i2);
                throw th;
            }
        }
        paintPool.recycle(paintObtain);
        int size = getColumns().size();
        for (int i3 = 0; i3 < size; i3++) {
            BaseColumn baseColumn = getColumns().get(i3);
            Intrinsics.checkNotNull(baseColumn, "null cannot be cast to non-null type com.ykb.ebook.page.column.TextColumn");
            TextColumn textColumn = (TextColumn) baseColumn;
            if (textColumn.getSelected()) {
                canvas.drawRect(textColumn.getStart(), 0.0f, textColumn.getEnd(), getLineBottom() - getLineTop(), view.getSelectedPaint());
            }
            if (textColumn.getIsDrawUnderLine()) {
                if (textColumn.getLineStyle() == 0) {
                    Paint paint = new Paint();
                    int lineColor = textColumn.getLineColor();
                    paint.setColor(lineColor != 0 ? lineColor != 1 ? lineColor != 2 ? lineColor != 3 ? lineColor != 4 ? AppCtxKt.getAppCtx().getColor(R.color.color_ff9da6) : AppCtxKt.getAppCtx().getColor(R.color.color_ffc86e) : AppCtxKt.getAppCtx().getColor(R.color.color_90d691) : AppCtxKt.getAppCtx().getColor(R.color.color_78aeff) : AppCtxKt.getAppCtx().getColor(R.color.color_b6a0ff) : AppCtxKt.getAppCtx().getColor(R.color.color_ff9da6));
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(4.0f);
                    canvas.drawLine(textColumn.getStart(), getLineBottom() - getLineTop(), textColumn.getEnd(), getLineBottom() - getLineTop(), paint);
                } else {
                    Paint paint2 = new Paint();
                    int lineColor2 = textColumn.getLineColor();
                    paint2.setColor(lineColor2 != 0 ? lineColor2 != 1 ? lineColor2 != 2 ? lineColor2 != 3 ? lineColor2 != 4 ? AppCtxKt.getAppCtx().getColor(R.color.color_ff9da6) : AppCtxKt.getAppCtx().getColor(R.color.color_26ffc86e) : AppCtxKt.getAppCtx().getColor(R.color.color_2690d691) : AppCtxKt.getAppCtx().getColor(R.color.color_2678aeff) : AppCtxKt.getAppCtx().getColor(R.color.color_26b6a0ff) : AppCtxKt.getAppCtx().getColor(R.color.color_26ff9da6));
                    paint2.setStyle(Paint.Style.FILL);
                    canvas.drawRect(textColumn.getStart(), 0.0f, textColumn.getEnd(), getLineBottom() - getLineTop(), paint2);
                }
            }
            if (textColumn.getIsDrawDashLine()) {
                Paint paint3 = new Paint();
                paint3.setColor(textColumn.getDashColor());
                paint3.setStrokeWidth(4.0f);
                paint3.setStyle(Paint.Style.STROKE);
                paint3.setPathEffect(new DashPathEffect(new float[]{10.0f, 5.0f, 10.0f, 5.0f}, 0.0f));
                if (!TextUtils.isEmpty(textColumn.getCharData()) && !TextUtils.equals(" ", textColumn.getCharData()) && (!StringsKt__StringsJVMKt.isBlank(textColumn.getCharData()))) {
                    canvas.drawLine(textColumn.getStart(), getLineBottom() - getLineTop(), textColumn.getEnd(), getLineBottom() - getLineTop(), paint3);
                }
            }
        }
    }

    public static /* synthetic */ BaseColumn getColumnReverseAt$default(TextLine textLine, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i3 = 0;
        }
        return textLine.getColumnReverseAt(i2, i3);
    }

    private final float getLineEnd() {
        BaseColumn baseColumn = (BaseColumn) CollectionsKt___CollectionsKt.lastOrNull((List) this.textColumns);
        if (baseColumn != null) {
            return baseColumn.getEnd();
        }
        return 0.0f;
    }

    private final float getLineStart() {
        BaseColumn baseColumn = (BaseColumn) CollectionsKt___CollectionsKt.firstOrNull((List) this.textColumns);
        if (baseColumn != null) {
            return baseColumn.getStart();
        }
        return 0.0f;
    }

    public final void addColumn(@NotNull BaseColumn column) {
        Intrinsics.checkNotNullParameter(column, "column");
        if (!(column instanceof TextColumn)) {
            this.onlyTextColumn = false;
        }
        column.setTextLine(this);
        this.textColumns.add(column);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getText() {
        return this.text;
    }

    /* renamed from: component10, reason: from getter */
    public final boolean getIsTitle() {
        return this.isTitle;
    }

    /* renamed from: component11, reason: from getter */
    public final boolean getIsParagraphEnd() {
        return this.isParagraphEnd;
    }

    /* renamed from: component12, reason: from getter */
    public final boolean getIsImage() {
        return this.isImage;
    }

    /* renamed from: component13, reason: from getter */
    public final float getStartX() {
        return this.startX;
    }

    /* renamed from: component14, reason: from getter */
    public final int getIndentSize() {
        return this.indentSize;
    }

    /* renamed from: component15, reason: from getter */
    public final float getExtraLetterSpacing() {
        return this.extraLetterSpacing;
    }

    /* renamed from: component16, reason: from getter */
    public final float getExtraLetterSpacingOffsetX() {
        return this.extraLetterSpacingOffsetX;
    }

    /* renamed from: component17, reason: from getter */
    public final float getWordSpacing() {
        return this.wordSpacing;
    }

    /* renamed from: component18, reason: from getter */
    public final boolean getExceed() {
        return this.exceed;
    }

    /* renamed from: component19, reason: from getter */
    public final boolean getOnlyTextColumn() {
        return this.onlyTextColumn;
    }

    /* renamed from: component3, reason: from getter */
    public final float getLineTop() {
        return this.lineTop;
    }

    /* renamed from: component4, reason: from getter */
    public final float getLineBase() {
        return this.lineBase;
    }

    /* renamed from: component5, reason: from getter */
    public final float getLineBottom() {
        return this.lineBottom;
    }

    /* renamed from: component6, reason: from getter */
    public final float getIndentWidth() {
        return this.indentWidth;
    }

    /* renamed from: component7, reason: from getter */
    public final int getParagraphNum() {
        return this.paragraphNum;
    }

    /* renamed from: component8, reason: from getter */
    public final int getChapterPosition() {
        return this.chapterPosition;
    }

    /* renamed from: component9, reason: from getter */
    public final int getPagePosition() {
        return this.pagePosition;
    }

    @NotNull
    public final TextLine copy(@NotNull String text, @NotNull ArrayList<BaseColumn> textColumns, float lineTop, float lineBase, float lineBottom, float indentWidth, int paragraphNum, int chapterPosition, int pagePosition, boolean isTitle, boolean isParagraphEnd, boolean isImage, float startX, int indentSize, float extraLetterSpacing, float extraLetterSpacingOffsetX, float wordSpacing, boolean exceed, boolean onlyTextColumn) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(textColumns, "textColumns");
        return new TextLine(text, textColumns, lineTop, lineBase, lineBottom, indentWidth, paragraphNum, chapterPosition, pagePosition, isTitle, isParagraphEnd, isImage, startX, indentSize, extraLetterSpacing, extraLetterSpacingOffsetX, wordSpacing, exceed, onlyTextColumn);
    }

    public final void draw(@NotNull ContentTextView view, @NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        CanvasRecorder canvasRecorder = this.canvasRecorder;
        int width = view.getWidth();
        int lineBottom = (int) (getLineBottom() - getLineTop());
        if (canvasRecorder.needRecord()) {
            try {
                Canvas canvasBeginRecording = canvasRecorder.beginRecording(width, lineBottom);
                int iSave = canvasBeginRecording.save();
                try {
                    drawTextLine(view, canvasBeginRecording);
                } finally {
                    canvasBeginRecording.restoreToCount(iSave);
                }
            } finally {
                canvasRecorder.endRecording();
            }
        }
        canvasRecorder.draw(canvas);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextLine)) {
            return false;
        }
        TextLine textLine = (TextLine) other;
        return Intrinsics.areEqual(this.text, textLine.text) && Intrinsics.areEqual(this.textColumns, textLine.textColumns) && Float.compare(this.lineTop, textLine.lineTop) == 0 && Float.compare(this.lineBase, textLine.lineBase) == 0 && Float.compare(this.lineBottom, textLine.lineBottom) == 0 && Float.compare(this.indentWidth, textLine.indentWidth) == 0 && this.paragraphNum == textLine.paragraphNum && this.chapterPosition == textLine.chapterPosition && this.pagePosition == textLine.pagePosition && this.isTitle == textLine.isTitle && this.isParagraphEnd == textLine.isParagraphEnd && this.isImage == textLine.isImage && Float.compare(this.startX, textLine.startX) == 0 && this.indentSize == textLine.indentSize && Float.compare(this.extraLetterSpacing, textLine.extraLetterSpacing) == 0 && Float.compare(this.extraLetterSpacingOffsetX, textLine.extraLetterSpacingOffsetX) == 0 && Float.compare(this.wordSpacing, textLine.wordSpacing) == 0 && this.exceed == textLine.exceed && this.onlyTextColumn == textLine.onlyTextColumn;
    }

    public final int getChapterPosition() {
        return this.chapterPosition;
    }

    public final int getCharSize() {
        return this.text.length();
    }

    @NotNull
    public final BaseColumn getColumn(int index) {
        ArrayList<BaseColumn> arrayList = this.textColumns;
        return (index < 0 || index > CollectionsKt__CollectionsKt.getLastIndex(arrayList)) ? (BaseColumn) CollectionsKt___CollectionsKt.last((List) this.textColumns) : arrayList.get(index);
    }

    @NotNull
    public final BaseColumn getColumnReverseAt(int index, int offset) {
        ArrayList<BaseColumn> arrayList = this.textColumns;
        BaseColumn baseColumn = arrayList.get((CollectionsKt__CollectionsKt.getLastIndex(arrayList) - offset) - index);
        Intrinsics.checkNotNullExpressionValue(baseColumn, "textColumns[textColumns.…stIndex - offset - index]");
        return baseColumn;
    }

    @NotNull
    public final List<BaseColumn> getColumns() {
        return this.textColumns;
    }

    public final boolean getExceed() {
        return this.exceed;
    }

    public final float getExtraLetterSpacing() {
        return this.extraLetterSpacing;
    }

    public final float getExtraLetterSpacingOffsetX() {
        return this.extraLetterSpacingOffsetX;
    }

    public final float getHeight() {
        return getLineBottom() - getLineTop();
    }

    public final int getIndentSize() {
        return this.indentSize;
    }

    public final float getIndentWidth() {
        return this.indentWidth;
    }

    public final float getLineBase() {
        return this.lineBase;
    }

    public final float getLineBottom() {
        return this.lineBottom;
    }

    public final float getLineTop() {
        return this.lineTop;
    }

    public final boolean getOnlyTextColumn() {
        return this.onlyTextColumn;
    }

    public final int getPagePosition() {
        return this.pagePosition;
    }

    public final int getParagraphNum() {
        return this.paragraphNum;
    }

    public final int getSearchResultColumnCount() {
        return this.searchResultColumnCount;
    }

    public final float getStartX() {
        return this.startX;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }

    public final boolean getTextLineIsNum() {
        return this.textLineIsNum;
    }

    @NotNull
    public final TextPage getTextPage() {
        return this.textPage;
    }

    public final float getWordSpacing() {
        return this.wordSpacing;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((((((((((((((this.text.hashCode() * 31) + this.textColumns.hashCode()) * 31) + Float.floatToIntBits(this.lineTop)) * 31) + Float.floatToIntBits(this.lineBase)) * 31) + Float.floatToIntBits(this.lineBottom)) * 31) + Float.floatToIntBits(this.indentWidth)) * 31) + this.paragraphNum) * 31) + this.chapterPosition) * 31) + this.pagePosition) * 31;
        boolean z2 = this.isTitle;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        int i3 = (iHashCode + i2) * 31;
        boolean z3 = this.isParagraphEnd;
        int i4 = z3;
        if (z3 != 0) {
            i4 = 1;
        }
        int i5 = (i3 + i4) * 31;
        boolean z4 = this.isImage;
        int i6 = z4;
        if (z4 != 0) {
            i6 = 1;
        }
        int iFloatToIntBits = (((((((((((i5 + i6) * 31) + Float.floatToIntBits(this.startX)) * 31) + this.indentSize) * 31) + Float.floatToIntBits(this.extraLetterSpacing)) * 31) + Float.floatToIntBits(this.extraLetterSpacingOffsetX)) * 31) + Float.floatToIntBits(this.wordSpacing)) * 31;
        boolean z5 = this.exceed;
        int i7 = z5;
        if (z5 != 0) {
            i7 = 1;
        }
        int i8 = (iFloatToIntBits + i7) * 31;
        boolean z6 = this.onlyTextColumn;
        return i8 + (z6 ? 1 : z6 ? 1 : 0);
    }

    public final void invalidate() {
        invalidateSelf();
        this.textPage.invalidate();
    }

    public final void invalidateSelf() {
        this.canvasRecorder.invalidate();
    }

    public final boolean isImage() {
        return this.isImage;
    }

    public final boolean isParagraphEnd() {
        return this.isParagraphEnd;
    }

    public final boolean isTitle() {
        return this.isTitle;
    }

    public final boolean isTouch(float x2, float y2, float relativeOffset) {
        return y2 > this.lineTop + relativeOffset && y2 < this.lineBottom + relativeOffset && x2 >= getLineStart() && x2 <= getLineEnd();
    }

    public final boolean isTouchY(float y2, float relativeOffset) {
        return y2 > this.lineTop + relativeOffset && y2 < this.lineBottom + relativeOffset;
    }

    public final boolean isVisible(float relativeOffset) {
        float f2 = this.lineTop + relativeOffset;
        float f3 = this.lineBottom + relativeOffset;
        float f4 = f3 - f2;
        int paddingTop = ChapterProvider.getPaddingTop();
        int visibleBottom = ChapterProvider.getVisibleBottom();
        float f5 = paddingTop;
        if (f2 >= f5 && f3 <= visibleBottom) {
            return true;
        }
        if (f2 <= f5 && f3 >= visibleBottom) {
            return true;
        }
        if (f2 >= f5 || f3 <= f5 || f3 >= visibleBottom) {
            if (f2 > f5) {
                float f6 = visibleBottom;
                if (f2 < f6 && f3 > f6 && (this.isImage || (f6 - f2) / f4 > 0.6d)) {
                    return true;
                }
            }
        } else if (this.isImage || (f3 - f5) / f4 > 0.6d) {
            return true;
        }
        return false;
    }

    public final void recycleRecorder() {
        this.canvasRecorder.recycle();
    }

    public final void setChapterPosition(int i2) {
        this.chapterPosition = i2;
    }

    public final void setExceed(boolean z2) {
        this.exceed = z2;
    }

    public final void setExtraLetterSpacing(float f2) {
        this.extraLetterSpacing = f2;
    }

    public final void setExtraLetterSpacingOffsetX(float f2) {
        this.extraLetterSpacingOffsetX = f2;
    }

    public final void setImage(boolean z2) {
        this.isImage = z2;
    }

    public final void setIndentSize(int i2) {
        this.indentSize = i2;
    }

    public final void setIndentWidth(float f2) {
        this.indentWidth = f2;
    }

    public final void setLineBase(float f2) {
        this.lineBase = f2;
    }

    public final void setLineBottom(float f2) {
        this.lineBottom = f2;
    }

    public final void setLineTop(float f2) {
        this.lineTop = f2;
    }

    public final void setOnlyTextColumn(boolean z2) {
        this.onlyTextColumn = z2;
    }

    public final void setPagePosition(int i2) {
        this.pagePosition = i2;
    }

    public final void setParagraphEnd(boolean z2) {
        this.isParagraphEnd = z2;
    }

    public final void setParagraphNum(int i2) {
        this.paragraphNum = i2;
    }

    public final void setSearchResultColumnCount(int i2) {
        this.searchResultColumnCount = i2;
    }

    public final void setStartX(float f2) {
        this.startX = f2;
    }

    public final void setText(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.text = str;
    }

    public final void setTextLineIsNum(boolean z2) {
        this.textLineIsNum = z2;
    }

    public final void setTextPage(@NotNull TextPage textPage) {
        Intrinsics.checkNotNullParameter(textPage, "<set-?>");
        this.textPage = textPage;
    }

    public final void setWordSpacing(float f2) {
        this.wordSpacing = f2;
    }

    @NotNull
    public String toString() {
        return "TextLine(text=" + this.text + ", textColumns=" + this.textColumns + ", lineTop=" + this.lineTop + ", lineBase=" + this.lineBase + ", lineBottom=" + this.lineBottom + ", indentWidth=" + this.indentWidth + ", paragraphNum=" + this.paragraphNum + ", chapterPosition=" + this.chapterPosition + ", pagePosition=" + this.pagePosition + ", isTitle=" + this.isTitle + ", isParagraphEnd=" + this.isParagraphEnd + ", isImage=" + this.isImage + ", startX=" + this.startX + ", indentSize=" + this.indentSize + ", extraLetterSpacing=" + this.extraLetterSpacing + ", extraLetterSpacingOffsetX=" + this.extraLetterSpacingOffsetX + ", wordSpacing=" + this.wordSpacing + ", exceed=" + this.exceed + ", onlyTextColumn=" + this.onlyTextColumn + ')';
    }

    public final void upTopBottom(float durY, float textHeight, @NotNull Paint.FontMetrics fontMetrics) {
        Intrinsics.checkNotNullParameter(fontMetrics, "fontMetrics");
        float paddingTop = ChapterProvider.getPaddingTop() + durY;
        this.lineTop = paddingTop;
        float f2 = paddingTop + textHeight;
        this.lineBottom = f2;
        this.lineBase = f2 - fontMetrics.descent;
    }

    public /* synthetic */ TextLine(String str, ArrayList arrayList, float f2, float f3, float f4, float f5, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, float f6, int i5, float f7, float f8, float f9, boolean z5, boolean z6, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? "" : str, (i6 & 2) != 0 ? new ArrayList() : arrayList, (i6 & 4) != 0 ? 0.0f : f2, (i6 & 8) != 0 ? 0.0f : f3, (i6 & 16) != 0 ? 0.0f : f4, (i6 & 32) != 0 ? 0.0f : f5, (i6 & 64) != 0 ? 0 : i2, (i6 & 128) != 0 ? 0 : i3, (i6 & 256) != 0 ? 0 : i4, (i6 & 512) != 0 ? false : z2, (i6 & 1024) != 0 ? false : z3, (i6 & 2048) != 0 ? false : z4, (i6 & 4096) != 0 ? 0.0f : f6, (i6 & 8192) != 0 ? 0 : i5, (i6 & 16384) != 0 ? 0.0f : f7, (i6 & 32768) != 0 ? 0.0f : f8, (i6 & 65536) != 0 ? 0.0f : f9, (i6 & 131072) != 0 ? false : z5, (i6 & 262144) != 0 ? true : z6);
    }
}
