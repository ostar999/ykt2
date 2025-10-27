package com.ykb.ebook.model;

import android.content.AppCtxKt;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.R;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import com.ykb.ebook.page.ChapterProvider;
import com.ykb.ebook.page.TextParagraph;
import com.ykb.ebook.page.canvans_recorder.CanvasRecorder;
import com.ykb.ebook.page.canvans_recorder.CanvasRecorderFactory;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.page.column.TextColumn;
import com.ykb.ebook.page.pool.PaintPool;
import com.ykb.ebook.weight.ContentTextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0016\b\u0086\b\u0018\u0000 z2\u00020\u0001:\u0001zBe\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\u0018\b\u0002\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003¢\u0006\u0002\u0010\u0010J\u000e\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020\tJ\t\u0010X\u001a\u00020\u0003HÆ\u0003J\t\u0010Y\u001a\u00020\u0005HÆ\u0003J\t\u0010Z\u001a\u00020\u0005HÆ\u0003J\u0019\u0010[\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\nHÂ\u0003J\t\u0010\\\u001a\u00020\u0003HÆ\u0003J\t\u0010]\u001a\u00020\u0003HÆ\u0003J\t\u0010^\u001a\u00020\u000eHÆ\u0003J\t\u0010_\u001a\u00020\u0003HÆ\u0003J\u000e\u0010`\u001a\u00020*2\u0006\u0010a\u001a\u00020\u0003Ji\u0010b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\u0018\b\u0002\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0003HÆ\u0001J(\u0010c\u001a\u00020V2\u0006\u0010d\u001a\u00020e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010f\u001a\u00020\u000e2\b\b\u0002\u0010g\u001a\u00020*J\u0010\u0010h\u001a\u00020V2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010i\u001a\u00020V2\u0006\u0010d\u001a\u00020e2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0013\u0010j\u001a\u00020*2\b\u0010k\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0006\u0010l\u001a\u00020\u0000J\u000e\u0010m\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003J\u0016\u0010n\u001a\u00020\u00032\u0006\u0010o\u001a\u00020\u00032\u0006\u0010p\u001a\u00020\u0003J\u0006\u0010q\u001a\u00020RJ\u0006\u0010r\u001a\u00020*J\t\u0010s\u001a\u00020\u0003HÖ\u0001J\u0006\u0010t\u001a\u00020VJ\u0006\u0010u\u001a\u00020VJ\u0006\u0010v\u001a\u00020VJ\u0018\u0010w\u001a\u00020*2\u0006\u0010d\u001a\u00020e2\b\b\u0002\u0010g\u001a\u00020*J\t\u0010x\u001a\u00020\u0005HÖ\u0001J\u0006\u0010y\u001a\u00020VR\u0014\u0010\u0011\u001a\u00020\u0003X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0013\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u000b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0013\"\u0004\b \u0010\u001eR\u0011\u0010!\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\"\u0010\u0013R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0013\"\u0004\b(\u0010\u001eR\u001a\u0010)\u001a\u00020*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010+\"\u0004\b,\u0010-R\u001a\u0010.\u001a\u00020*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010+\"\u0004\b/\u0010-R\u001a\u0010\u000f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u0013\"\u0004\b1\u0010\u001eR\u0011\u00102\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b3\u0010\u0013R\u0017\u00104\u001a\b\u0012\u0004\u0012\u00020\t058F¢\u0006\u0006\u001a\u0004\b6\u00107R\u001a\u00108\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u0013\"\u0004\b:\u0010\u001eR\u0011\u0010;\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b<\u0010\u0013R+\u0010=\u001a\u0012\u0012\u0004\u0012\u00020>0\bj\b\u0012\u0004\u0012\u00020>`\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bA\u0010B\u001a\u0004\b?\u0010@R!\u0010C\u001a\u0012\u0012\u0004\u0012\u00020>0\bj\b\u0012\u0004\u0012\u00020>`\n8F¢\u0006\u0006\u001a\u0004\bD\u0010@R\u0011\u0010E\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\bF\u0010GR!\u0010H\u001a\u0012\u0012\u0004\u0012\u00020J0Ij\b\u0012\u0004\u0012\u00020J`K¢\u0006\b\n\u0000\u001a\u0004\bL\u0010MR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010G\"\u0004\bO\u0010PR\u0012\u0010Q\u001a\u00020R8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010G\"\u0004\bT\u0010P¨\u0006{"}, d2 = {"Lcom/ykb/ebook/model/TextPage;", "", "index", "", "text", "", "title", "textLines", "Ljava/util/ArrayList;", "Lcom/ykb/ebook/model/TextLine;", "Lkotlin/collections/ArrayList;", "chapterSize", "chapterIndex", "height", "", "leftLineSize", "(ILjava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;IIFI)V", "allIndex", "getAllIndex", "()I", "canvas", "Landroid/graphics/Canvas;", "canvasRecorder", "Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "getCanvasRecorder", "()Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "setCanvasRecorder", "(Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;)V", "getChapterIndex", "setChapterIndex", "(I)V", "getChapterSize", "setChapterSize", "charSize", "getCharSize", "getHeight", "()F", "setHeight", "(F)V", "getIndex", "setIndex", "isCompleted", "", "()Z", "setCompleted", "(Z)V", "isMsgPage", "setMsgPage", "getLeftLineSize", "setLeftLineSize", "lineSize", "getLineSize", "lines", "", "getLines", "()Ljava/util/List;", "paddingTop", "getPaddingTop", "setPaddingTop", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "getPageSize", "paragraphs", "Lcom/ykb/ebook/page/TextParagraph;", "getParagraphs", "()Ljava/util/ArrayList;", "paragraphs$delegate", "Lkotlin/Lazy;", "paragraphsInternal", "getParagraphsInternal", "readProgress", "getReadProgress", "()Ljava/lang/String;", "searchResult", "Ljava/util/HashSet;", "Lcom/ykb/ebook/page/column/TextColumn;", "Lkotlin/collections/HashSet;", "getSearchResult", "()Ljava/util/HashSet;", "getText", "setText", "(Ljava/lang/String;)V", "textChapter", "Lcom/ykb/ebook/model/TextChapter;", "getTitle", "setTitle", "addLine", "", PLVDocumentMarkToolType.BRUSH, "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "containPos", "chapterPos", "copy", "draw", "view", "Lcom/ykb/ebook/weight/ContentTextView;", "relativeOffset", "updateStye", "drawDebugInfo", "drawPage", "equals", "other", "format", "getLine", "getPosByLineColumn", "lineIndex", "columnIndex", "getTextChapter", "hasImageOrEmpty", "hashCode", "invalidate", "invalidateAll", "recycleRecorders", "render", "toString", "upLinesPosition", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTextPage.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TextPage.kt\ncom/ykb/ebook/model/TextPage\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 Canvas.kt\nandroidx/core/graphics/CanvasKt\n+ 5 CanvasRecorderExtensions.kt\ncom/ykb/ebook/extensions/CanvasRecorderExtensionsKt\n*L\n1#1,358:1\n766#2:359\n857#2,2:360\n1855#2,2:362\n1747#2,3:401\n1#3:364\n47#4,8:365\n47#4,8:373\n30#4,3:390\n34#4,3:395\n12#5,9:381\n21#5,2:393\n24#5,3:398\n*S KotlinDebug\n*F\n+ 1 TextPage.kt\ncom/ykb/ebook/model/TextPage\n*L\n64#1:359\n64#1:360,2\n66#1:362,2\n355#1:401,3\n291#1:365,8\n315#1:373,8\n331#1:390,3\n331#1:395,3\n331#1:381,9\n331#1:393,2\n331#1:398,3\n*E\n"})
/* loaded from: classes7.dex */
public final /* data */ class TextPage {
    private final int allIndex;

    @Nullable
    private Canvas canvas;

    @NotNull
    private CanvasRecorder canvasRecorder;
    private int chapterIndex;
    private int chapterSize;
    private float height;
    private int index;
    private boolean isCompleted;
    private boolean isMsgPage;
    private int leftLineSize;
    private int paddingTop;

    /* renamed from: paragraphs$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy paragraphs;

    @NotNull
    private final HashSet<TextColumn> searchResult;

    @NotNull
    private String text;

    @JvmField
    @NotNull
    public TextChapter textChapter;

    @NotNull
    private final ArrayList<TextLine> textLines;

    @NotNull
    private String title;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final DecimalFormat readProgressFormatter = new DecimalFormat("0.0%");

    @NotNull
    private static final TextPage emptyTextPage = new TextPage(0, null, null, null, 0, 0, 0.0f, 0, 255, null);

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/model/TextPage$Companion;", "", "()V", "emptyTextPage", "Lcom/ykb/ebook/model/TextPage;", "getEmptyTextPage", "()Lcom/ykb/ebook/model/TextPage;", "readProgressFormatter", "Ljava/text/DecimalFormat;", "getReadProgressFormatter", "()Ljava/text/DecimalFormat;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final TextPage getEmptyTextPage() {
            return TextPage.emptyTextPage;
        }

        @NotNull
        public final DecimalFormat getReadProgressFormatter() {
            return TextPage.readProgressFormatter;
        }
    }

    public TextPage() {
        this(0, null, null, null, 0, 0, 0.0f, 0, 255, null);
    }

    public TextPage(int i2, @NotNull String text, @NotNull String title, @NotNull ArrayList<TextLine> textLines, int i3, int i4, float f2, int i5) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(textLines, "textLines");
        this.index = i2;
        this.text = text;
        this.title = title;
        this.textLines = textLines;
        this.chapterSize = i3;
        this.chapterIndex = i4;
        this.height = f2;
        this.leftLineSize = i5;
        this.searchResult = new HashSet<>();
        this.canvasRecorder = CanvasRecorderFactory.INSTANCE.create(true);
        this.paddingTop = ChapterProvider.getPaddingTop();
        this.textChapter = TextChapter.INSTANCE.getEmptyTextChapter();
        this.paragraphs = LazyKt__LazyJVMKt.lazy(new Function0<ArrayList<TextParagraph>>() { // from class: com.ykb.ebook.model.TextPage$paragraphs$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final ArrayList<TextParagraph> invoke() {
                return this.this$0.getParagraphsInternal();
            }
        });
        this.allIndex = 1;
    }

    private final ArrayList<TextLine> component4() {
        return this.textLines;
    }

    public static /* synthetic */ void draw$default(TextPage textPage, ContentTextView contentTextView, Canvas canvas, float f2, boolean z2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z2 = false;
        }
        textPage.draw(contentTextView, canvas, f2, z2);
    }

    private final void drawDebugInfo(Canvas canvas) {
        ChapterProvider chapterProvider = ChapterProvider.INSTANCE;
        PaintPool paintPool = PaintPool.INSTANCE;
        Paint paintObtain = paintPool.obtain();
        paintObtain.setStyle(Paint.Style.STROKE);
        canvas.drawRect(ChapterProvider.getPaddingLeft(), 0.0f, ChapterProvider.getPaddingLeft() + ChapterProvider.getVisibleWidth(), this.height - ConvertExtensionsKt.dpToPx(1), paintObtain);
        paintPool.recycle(paintObtain);
    }

    private final void drawPage(ContentTextView view, Canvas canvas) {
        this.canvas = canvas;
        int size = getLines().size();
        for (int i2 = 0; i2 < size; i2++) {
            TextLine textLine = getLines().get(i2);
            float lineTop = textLine.getLineTop() - this.paddingTop;
            int iSave = canvas.save();
            canvas.translate(0.0f, lineTop);
            try {
                textLine.draw(view, canvas);
                canvas.restoreToCount(iSave);
            } catch (Throwable th) {
                canvas.restoreToCount(iSave);
                throw th;
            }
        }
    }

    public static /* synthetic */ boolean render$default(TextPage textPage, ContentTextView contentTextView, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return textPage.render(contentTextView, z2);
    }

    public final void addLine(@NotNull TextLine line) {
        Intrinsics.checkNotNullParameter(line, "line");
        line.setTextPage(this);
        this.textLines.add(line);
    }

    /* renamed from: component1, reason: from getter */
    public final int getIndex() {
        return this.index;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getText() {
        return this.text;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component5, reason: from getter */
    public final int getChapterSize() {
        return this.chapterSize;
    }

    /* renamed from: component6, reason: from getter */
    public final int getChapterIndex() {
        return this.chapterIndex;
    }

    /* renamed from: component7, reason: from getter */
    public final float getHeight() {
        return this.height;
    }

    /* renamed from: component8, reason: from getter */
    public final int getLeftLineSize() {
        return this.leftLineSize;
    }

    public final boolean containPos(int chapterPos) {
        int chapterPosition = ((TextLine) CollectionsKt___CollectionsKt.first((List) getLines())).getChapterPosition();
        return chapterPosition <= chapterPos && chapterPos < getCharSize() + chapterPosition;
    }

    @NotNull
    public final TextPage copy(int index, @NotNull String text, @NotNull String title, @NotNull ArrayList<TextLine> textLines, int chapterSize, int chapterIndex, float height, int leftLineSize) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(textLines, "textLines");
        return new TextPage(index, text, title, textLines, chapterSize, chapterIndex, height, leftLineSize);
    }

    public final void draw(@NotNull ContentTextView view, @NotNull Canvas canvas, float relativeOffset, boolean updateStye) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        render(view, updateStye);
        int iSave = canvas.save();
        canvas.translate(0.0f, relativeOffset + this.paddingTop);
        try {
            this.canvasRecorder.draw(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextPage)) {
            return false;
        }
        TextPage textPage = (TextPage) other;
        return this.index == textPage.index && Intrinsics.areEqual(this.text, textPage.text) && Intrinsics.areEqual(this.title, textPage.title) && Intrinsics.areEqual(this.textLines, textPage.textLines) && this.chapterSize == textPage.chapterSize && this.chapterIndex == textPage.chapterIndex && Float.compare(this.height, textPage.height) == 0 && this.leftLineSize == textPage.leftLineSize;
    }

    @NotNull
    public final TextPage format() {
        if (this.textLines.isEmpty()) {
            this.isMsgPage = true;
        }
        if (this.isMsgPage && ChapterProvider.getViewWidth() > 0) {
            this.textLines.clear();
            int visibleRight = ChapterProvider.getVisibleRight() - ChapterProvider.getPaddingLeft();
            StaticLayout staticLayout = new StaticLayout(this.text, ChapterProvider.getContentPaint(), visibleRight, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            float visibleHeight = (ChapterProvider.getVisibleHeight() - staticLayout.getHeight()) / 2.0f;
            if (visibleHeight < 0.0f) {
                visibleHeight = 0.0f;
            }
            int lineCount = staticLayout.getLineCount();
            for (int i2 = 0; i2 < lineCount; i2++) {
                int i3 = 0;
                TextLine textLine = new TextLine(null, null, 0.0f, 0.0f, 0.0f, 0.0f, i3, i3, 0, false, false, false, 0.0f, 0, 0.0f, 0.0f, 0.0f, false, false, 524287, null);
                textLine.setLineTop(ChapterProvider.getPaddingTop() + visibleHeight + staticLayout.getLineTop(i2));
                textLine.setLineBase(ChapterProvider.getPaddingTop() + visibleHeight + staticLayout.getLineBaseline(i2));
                textLine.setLineBottom(ChapterProvider.getPaddingTop() + visibleHeight + staticLayout.getLineBottom(i2));
                float paddingLeft = ChapterProvider.getPaddingLeft() + ((visibleRight - staticLayout.getLineMax(i2)) / 2);
                String strSubstring = this.text.substring(staticLayout.getLineStart(i2), staticLayout.getLineEnd(i2));
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                textLine.setText(strSubstring);
                int length = textLine.getText().length();
                float f2 = paddingLeft;
                int i4 = 0;
                while (i4 < length) {
                    String strValueOf = String.valueOf(textLine.getText().charAt(i4));
                    float desiredWidth = f2 + Layout.getDesiredWidth(strValueOf, ChapterProvider.getContentPaint());
                    textLine.addColumn(new TextColumn(f2, desiredWidth, 0, strValueOf, null, 0, 0, null, 0, 0, null, 0, R2.color.FF333333, null));
                    i4++;
                    f2 = desiredWidth;
                }
                addLine(textLine);
            }
            this.height = ChapterProvider.getVisibleHeight();
            invalidate();
            this.isCompleted = true;
        }
        return this;
    }

    public final int getAllIndex() {
        return this.allIndex;
    }

    @NotNull
    public final CanvasRecorder getCanvasRecorder() {
        return this.canvasRecorder;
    }

    public final int getChapterIndex() {
        return this.chapterIndex;
    }

    public final int getChapterSize() {
        return this.chapterSize;
    }

    public final int getCharSize() {
        return RangesKt___RangesKt.coerceAtLeast(this.text.length(), 1);
    }

    public final float getHeight() {
        return this.height;
    }

    public final int getIndex() {
        return this.index;
    }

    public final int getLeftLineSize() {
        return this.leftLineSize;
    }

    @NotNull
    public final TextLine getLine(int index) {
        ArrayList<TextLine> arrayList = this.textLines;
        return (index < 0 || index > CollectionsKt__CollectionsKt.getLastIndex(arrayList)) ? (TextLine) CollectionsKt___CollectionsKt.last((List) this.textLines) : arrayList.get(index);
    }

    public final int getLineSize() {
        return this.textLines.size();
    }

    @NotNull
    public final List<TextLine> getLines() {
        return this.textLines;
    }

    public final int getPaddingTop() {
        return this.paddingTop;
    }

    public final int getPageSize() {
        return this.textChapter.getPageSize();
    }

    @NotNull
    public final ArrayList<TextParagraph> getParagraphs() {
        return (ArrayList) this.paragraphs.getValue();
    }

    @NotNull
    public final ArrayList<TextParagraph> getParagraphsInternal() {
        ArrayList<TextParagraph> arrayList = new ArrayList<>();
        ArrayList<TextLine> arrayList2 = this.textLines;
        ArrayList<TextLine> arrayList3 = new ArrayList();
        Iterator<T> it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (((TextLine) next).getParagraphNum() > 0) {
                arrayList3.add(next);
            }
        }
        int paragraphNum = ((TextLine) CollectionsKt___CollectionsKt.first((List) arrayList3)).getParagraphNum() - 1;
        for (TextLine textLine : arrayList3) {
            if (CollectionsKt__CollectionsKt.getLastIndex(arrayList) < (textLine.getParagraphNum() - paragraphNum) - 1) {
                arrayList.add(new TextParagraph(0, null, 2, null));
            }
            arrayList.get((textLine.getParagraphNum() - paragraphNum) - 1).getTextLines().add(textLine);
        }
        return arrayList;
    }

    public final int getPosByLineColumn(int lineIndex, int columnIndex) {
        int iMin = Math.min(lineIndex, getLineSize());
        int length = 0;
        for (int i2 = 0; i2 < iMin; i2++) {
            length += this.textLines.get(i2).getCharSize();
            if (this.textLines.get(i2).isParagraphEnd()) {
                length++;
            }
        }
        List<BaseColumn> columns = this.textLines.get(iMin).getColumns();
        for (int i3 = 0; i3 < columnIndex; i3++) {
            BaseColumn baseColumn = columns.get(i3);
            if (baseColumn instanceof TextColumn) {
                length += ((TextColumn) baseColumn).getCharData().length();
            }
        }
        return length;
    }

    @NotNull
    public final String getReadProgress() {
        DecimalFormat decimalFormat = readProgressFormatter;
        if (this.chapterSize == 0) {
            return "0.0%";
        }
        if (getPageSize() == 0 && this.chapterIndex == 0) {
            return "0.0%";
        }
        if (getPageSize() == 0) {
            String str = decimalFormat.format((this.chapterIndex + 1.0f) / this.chapterSize);
            Intrinsics.checkNotNullExpressionValue(str, "df.format((chapterIndex …/ chapterSize.toDouble())");
            return str;
        }
        float f2 = this.chapterIndex * 1.0f;
        int i2 = this.chapterSize;
        String percent = decimalFormat.format((f2 / i2) + (((1.0f / i2) * (this.index + 1)) / getPageSize()));
        if (Intrinsics.areEqual(percent, "100.0%") && (this.chapterIndex + 1 != this.chapterSize || this.index + 1 != getPageSize())) {
            percent = "99.9%";
        }
        Intrinsics.checkNotNullExpressionValue(percent, "percent");
        if (StringsKt__StringsKt.indexOf$default((CharSequence) percent, StrPool.DOT, 0, false, 6, (Object) null) != -1) {
            Intrinsics.checkNotNullExpressionValue(percent, "percent");
            percent = (String) StringsKt__StringsKt.split$default((CharSequence) percent, new String[]{StrPool.DOT}, false, 0, 6, (Object) null).get(0);
        }
        String percent2 = percent;
        Intrinsics.checkNotNullExpressionValue(percent2, "percent");
        return StringsKt__StringsJVMKt.replace$default(percent2, "%", "", false, 4, (Object) null);
    }

    @NotNull
    public final HashSet<TextColumn> getSearchResult() {
        return this.searchResult;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }

    @NotNull
    public final TextChapter getTextChapter() {
        return this.textChapter;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final boolean hasImageOrEmpty() {
        boolean z2;
        ArrayList<TextLine> arrayList = this.textLines;
        if ((arrayList instanceof Collection) && arrayList.isEmpty()) {
            z2 = false;
        } else {
            Iterator<T> it = arrayList.iterator();
            while (it.hasNext()) {
                if (((TextLine) it.next()).isImage()) {
                    z2 = true;
                    break;
                }
            }
            z2 = false;
        }
        return z2 || this.textLines.isEmpty();
    }

    public int hashCode() {
        return (((((((((((((this.index * 31) + this.text.hashCode()) * 31) + this.title.hashCode()) * 31) + this.textLines.hashCode()) * 31) + this.chapterSize) * 31) + this.chapterIndex) * 31) + Float.floatToIntBits(this.height)) * 31) + this.leftLineSize;
    }

    public final void invalidate() {
        this.canvasRecorder.invalidate();
    }

    public final void invalidateAll() {
        int size = getLines().size();
        for (int i2 = 0; i2 < size; i2++) {
            getLines().get(i2).invalidateSelf();
        }
        invalidate();
    }

    /* renamed from: isCompleted, reason: from getter */
    public final boolean getIsCompleted() {
        return this.isCompleted;
    }

    /* renamed from: isMsgPage, reason: from getter */
    public final boolean getIsMsgPage() {
        return this.isMsgPage;
    }

    public final void recycleRecorders() {
        this.canvasRecorder.recycle();
        int size = getLines().size();
        for (int i2 = 0; i2 < size; i2++) {
            getLines().get(i2).recycleRecorder();
        }
    }

    public final boolean render(@NotNull ContentTextView view, boolean updateStye) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (!this.isCompleted && !updateStye) {
            return false;
        }
        CanvasRecorder canvasRecorder = this.canvasRecorder;
        int width = view.getWidth();
        int i2 = (int) this.height;
        if (!canvasRecorder.needRecord()) {
            return false;
        }
        try {
            Canvas canvasBeginRecording = canvasRecorder.beginRecording(width, i2);
            int iSave = canvasBeginRecording.save();
            try {
                drawPage(view, canvasBeginRecording);
                canvasRecorder.endRecording();
                return true;
            } finally {
                canvasBeginRecording.restoreToCount(iSave);
            }
        } catch (Throwable th) {
            canvasRecorder.endRecording();
            throw th;
        }
    }

    public final void setCanvasRecorder(@NotNull CanvasRecorder canvasRecorder) {
        Intrinsics.checkNotNullParameter(canvasRecorder, "<set-?>");
        this.canvasRecorder = canvasRecorder;
    }

    public final void setChapterIndex(int i2) {
        this.chapterIndex = i2;
    }

    public final void setChapterSize(int i2) {
        this.chapterSize = i2;
    }

    public final void setCompleted(boolean z2) {
        this.isCompleted = z2;
    }

    public final void setHeight(float f2) {
        this.height = f2;
    }

    public final void setIndex(int i2) {
        this.index = i2;
    }

    public final void setLeftLineSize(int i2) {
        this.leftLineSize = i2;
    }

    public final void setMsgPage(boolean z2) {
        this.isMsgPage = z2;
    }

    public final void setPaddingTop(int i2) {
        this.paddingTop = i2;
    }

    public final void setText(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.text = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    @NotNull
    public String toString() {
        return "TextPage(index=" + this.index + ", text=" + this.text + ", title=" + this.title + ", textLines=" + this.textLines + ", chapterSize=" + this.chapterSize + ", chapterIndex=" + this.chapterIndex + ", height=" + this.height + ", leftLineSize=" + this.leftLineSize + ')';
    }

    public final void upLinesPosition() {
        if (this.textLines.size() <= 1) {
            return;
        }
        if (this.leftLineSize == 0) {
            this.leftLineSize = getLineSize();
        }
        ChapterProvider chapterProvider = ChapterProvider.INSTANCE;
        TextLine textLine = this.textLines.get(this.leftLineSize - 1);
        Intrinsics.checkNotNullExpressionValue(textLine, "textLines[leftLineSize - 1]");
        TextLine textLine2 = textLine;
        if (!textLine2.isImage()) {
            if (ChapterProvider.getVisibleHeight() - (textLine2.getLineBottom() + (chapterProvider.getContentPaintTextHeight() * ChapterProvider.getLineSpacingExtra())) < textLine2.getLineBottom() - textLine2.getLineTop()) {
                float visibleBottom = ChapterProvider.getVisibleBottom() - textLine2.getLineBottom();
                if (!(visibleBottom == 0.0f)) {
                    this.height += visibleBottom;
                    int i2 = this.leftLineSize;
                    float f2 = visibleBottom / (i2 - 1);
                    for (int i3 = 1; i3 < i2; i3++) {
                        TextLine textLine3 = this.textLines.get(i3);
                        Intrinsics.checkNotNullExpressionValue(textLine3, "textLines[i]");
                        TextLine textLine4 = textLine3;
                        float f3 = i3 * f2;
                        textLine4.setLineTop(textLine4.getLineTop() + f3);
                        textLine4.setLineBase(textLine4.getLineBase() + f3);
                        textLine4.setLineBottom(textLine4.getLineBottom() + f3);
                    }
                }
            }
        }
        if (this.leftLineSize == getLineSize()) {
            return;
        }
        ChapterProvider chapterProvider2 = ChapterProvider.INSTANCE;
        TextLine textLine5 = (TextLine) CollectionsKt___CollectionsKt.last((List) this.textLines);
        if (textLine5.isImage()) {
            return;
        }
        if (ChapterProvider.getVisibleHeight() - (textLine5.getLineBottom() + (chapterProvider2.getContentPaintTextHeight() * ChapterProvider.getLineSpacingExtra())) < textLine5.getLineBottom() - textLine5.getLineTop()) {
            float visibleBottom2 = ChapterProvider.getVisibleBottom() - textLine5.getLineBottom();
            if (visibleBottom2 == 0.0f) {
                return;
            }
            int size = this.textLines.size();
            float f4 = visibleBottom2 / ((size - r3) - 1);
            int size2 = this.textLines.size();
            for (int i4 = this.leftLineSize + 1; i4 < size2; i4++) {
                TextLine textLine6 = this.textLines.get(i4);
                Intrinsics.checkNotNullExpressionValue(textLine6, "textLines[i]");
                TextLine textLine7 = textLine6;
                float f5 = (i4 - this.leftLineSize) * f4;
                textLine7.setLineTop(textLine7.getLineTop() + f5);
                textLine7.setLineBase(textLine7.getLineBase() + f5);
                textLine7.setLineBottom(textLine7.getLineBottom() + f5);
            }
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ TextPage(int i2, String str, String str2, ArrayList arrayList, int i3, int i4, float f2, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        String string;
        String string2;
        int i7 = (i6 & 1) != 0 ? 0 : i2;
        if ((i6 & 2) != 0) {
            string = AppCtxKt.getAppCtx().getString(R.string.data_loading);
            Intrinsics.checkNotNullExpressionValue(string, "appCtx.getString(R.string.data_loading)");
        } else {
            string = str;
        }
        if ((i6 & 4) != 0) {
            string2 = AppCtxKt.getAppCtx().getString(R.string.data_loading);
            Intrinsics.checkNotNullExpressionValue(string2, "appCtx.getString(R.string.data_loading)");
        } else {
            string2 = str2;
        }
        this(i7, string, string2, (i6 & 8) != 0 ? new ArrayList() : arrayList, (i6 & 16) != 0 ? 0 : i3, (i6 & 32) != 0 ? 0 : i4, (i6 & 64) != 0 ? 0.0f : f2, (i6 & 128) == 0 ? i5 : 0);
    }
}
