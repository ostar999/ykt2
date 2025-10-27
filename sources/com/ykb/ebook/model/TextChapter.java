package com.ykb.ebook.model;

import android.util.Log;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.psychiatrygarden.utils.Constants;
import com.tencent.connect.common.Constants;
import com.umeng.analytics.pro.d;
import com.ykb.ebook.common_interface.LayoutProgressListener;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.page.TextChapterLayout;
import com.ykb.ebook.page.TextParagraph;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.page.column.TextColumn;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u001b\n\u0002\u0010\u0003\n\u0002\b\u0007\b\u0086\b\u0018\u0000 r2\u00020\u0001:\u0001rB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010A\u001a\u00020BJ\t\u0010C\u001a\u00020\u0003HÆ\u0003J\t\u0010D\u001a\u00020\u0005HÆ\u0003J\t\u0010E\u001a\u00020\u0007HÆ\u0003J\t\u0010F\u001a\u00020\u0005HÆ\u0003J\t\u0010G\u001a\u00020\nHÆ\u0003J;\u0010H\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0016\u0010I\u001a\u00020B2\u0006\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020MJ\u0013\u0010N\u001a\u00020\n2\b\u0010O\u001a\u0004\u0018\u00010PHÖ\u0003J\u0010\u0010Q\u001a\u00020\u00052\u0006\u0010R\u001a\u00020\u0005H\u0002J\u0010\u0010S\u001a\u00020\u00052\u0006\u0010T\u001a\u00020\u0017H\u0002J\u0006\u0010U\u001a\u00020\u0007J\u0006\u0010V\u001a\u00020\u0005J\u001e\u0010W\u001a\u00020\u00072\u0006\u0010X\u001a\u00020\u00052\u0006\u0010Y\u001a\u00020\n2\u0006\u0010Z\u001a\u00020\u0005J\u000e\u0010[\u001a\u00020\u00052\u0006\u0010\\\u001a\u00020\u0005J\u0010\u0010]\u001a\u0004\u0018\u00010\u00172\u0006\u0010^\u001a\u00020\u0005J\u0010\u0010_\u001a\u0004\u0018\u00010\u00172\u0006\u0010`\u001a\u00020\u0005J\u000e\u0010a\u001a\u00020\u00052\u0006\u0010R\u001a\u00020\u0005J\u0016\u0010b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010Y\u001a\u00020\nJ\u0014\u00107\u001a\b\u0012\u0004\u0012\u00020)0(2\u0006\u0010Y\u001a\u00020\nJ\u000e\u0010c\u001a\u00020\u00052\u0006\u0010\\\u001a\u00020\u0005J\u000e\u0010d\u001a\u00020\u00052\u0006\u0010X\u001a\u00020\u0005J\u000e\u0010e\u001a\u00020\u00072\u0006\u0010X\u001a\u00020\u0005J\t\u0010f\u001a\u00020\u0005HÖ\u0001J\u000e\u0010g\u001a\u00020\n2\u0006\u0010^\u001a\u00020\u0005J\u000e\u0010h\u001a\u00020\n2\u0006\u0010^\u001a\u00020\u0005J\b\u0010i\u001a\u00020BH\u0016J\u0010\u0010j\u001a\u00020B2\u0006\u0010k\u001a\u00020lH\u0016J\u0018\u0010m\u001a\u00020B2\u0006\u0010^\u001a\u00020\u00052\u0006\u0010n\u001a\u00020\u0017H\u0016J\u0010\u0010o\u001a\u00020B2\b\u0010p\u001a\u0004\u0018\u00010\u0001J\t\u0010q\u001a\u00020\u0007HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000fR\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u00178F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000fR\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00170\u001f8F¢\u0006\u0006\u001a\u0004\b \u0010!R\u001c\u0010\"\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R!\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b*\u0010+R\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020)0(8F¢\u0006\u0006\u001a\u0004\b/\u0010+R\u0011\u00100\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b1\u0010\u000fR\u0017\u00102\u001a\b\u0012\u0004\u0012\u00020\u00170(8F¢\u0006\u0006\u001a\u0004\b3\u0010+R+\u00104\u001a\u0012\u0012\u0004\u0012\u00020)05j\b\u0012\u0004\u0012\u00020)`68FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b9\u0010-\u001a\u0004\b7\u00108R!\u0010:\u001a\u0012\u0012\u0004\u0012\u00020)05j\b\u0012\u0004\u0012\u00020)`68F¢\u0006\u0006\u001a\u0004\b;\u00108R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b<\u0010\u000fR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b=\u0010\u0011R\u001e\u0010>\u001a\u0012\u0012\u0004\u0012\u00020\u001705j\b\u0012\u0004\u0012\u00020\u0017`6X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@¨\u0006s"}, d2 = {"Lcom/ykb/ebook/model/TextChapter;", "Lcom/ykb/ebook/common_interface/LayoutProgressListener;", "chapter", "Lcom/ykb/ebook/model/ChapterInfo;", "position", "", "title", "", "chaptersSize", "sameTitleRemoved", "", "(Lcom/ykb/ebook/model/ChapterInfo;ILjava/lang/String;IZ)V", "getChapter", "()Lcom/ykb/ebook/model/ChapterInfo;", "getChaptersSize", "()I", "isCompleted", "()Z", "setCompleted", "(Z)V", "lastIndex", "getLastIndex", "lastPage", "Lcom/ykb/ebook/model/TextPage;", "getLastPage", "()Lcom/ykb/ebook/model/TextPage;", "lastReadLength", "getLastReadLength", TtmlNode.TAG_LAYOUT, "Lcom/ykb/ebook/page/TextChapterLayout;", "layoutChannel", "Lkotlinx/coroutines/channels/Channel;", "getLayoutChannel", "()Lkotlinx/coroutines/channels/Channel;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "getListener", "()Lcom/ykb/ebook/common_interface/LayoutProgressListener;", "setListener", "(Lcom/ykb/ebook/common_interface/LayoutProgressListener;)V", "pageParagraphs", "", "Lcom/ykb/ebook/page/TextParagraph;", "getPageParagraphs", "()Ljava/util/List;", "pageParagraphs$delegate", "Lkotlin/Lazy;", "pageParagraphsInternal", "getPageParagraphsInternal", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "getPageSize", d.f22698t, "getPages", "paragraphs", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getParagraphs", "()Ljava/util/ArrayList;", "paragraphs$delegate", "paragraphsInternal", "getParagraphsInternal", "getPosition", "getSameTitleRemoved", "textPages", "getTitle", "()Ljava/lang/String;", "cancelLayout", "", "component1", "component2", "component3", "component4", "component5", "copy", "createLayout", Constants.PARAM_SCOPE, "Lkotlinx/coroutines/CoroutineScope;", "bookContent", "Lcom/ykb/ebook/model/BookContent;", "equals", "other", "", "findPageByCharIndex", "charIndex", "findPageLastIndex", "textPage", "getContent", "getLastParagraphPosition", "getNeedReadAloud", ServiceCommon.RequestKey.FORM_KEY_PAGE_INDEX, "pageSplit", "startPos", "getNextPageLength", SessionDescription.ATTR_LENGTH, "getPage", "index", "getPageByReadPos", "readPos", "getPageIndexByCharIndex", "getParagraphNum", "getPrevPageLength", "getReadLength", "getUnRead", "hashCode", "isLastIndex", "isLastIndexCurrent", "onLayoutCompleted", "onLayoutException", AliyunLogKey.KEY_EVENT, "", "onLayoutPageCompleted", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "setProgressListener", NotifyType.LIGHTS, "toString", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTextChapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TextChapter.kt\ncom/ykb/ebook/model/TextChapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 CollectionExtensions.kt\ncom/ykb/ebook/extensions/CollectionExtensionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,348:1\n1855#2,2:349\n1855#2,2:351\n13#3,29:353\n1#4:382\n*S KotlinDebug\n*F\n+ 1 TextChapter.kt\ncom/ykb/ebook/model/TextChapter\n*L\n148#1:349,2\n191#1:351,2\n227#1:353,29\n*E\n"})
/* loaded from: classes7.dex */
public final /* data */ class TextChapter implements LayoutProgressListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final TextChapter emptyTextChapter;

    @NotNull
    private final ChapterInfo chapter;
    private final int chaptersSize;
    private boolean isCompleted;

    @Nullable
    private TextChapterLayout layout;

    @Nullable
    private LayoutProgressListener listener;

    /* renamed from: pageParagraphs$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy pageParagraphs;

    /* renamed from: paragraphs$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy paragraphs;
    private final int position;
    private final boolean sameTitleRemoved;

    @NotNull
    private final ArrayList<TextPage> textPages;

    @NotNull
    private final String title;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/model/TextChapter$Companion;", "", "()V", "emptyTextChapter", "Lcom/ykb/ebook/model/TextChapter;", "getEmptyTextChapter", "()Lcom/ykb/ebook/model/TextChapter;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final TextChapter getEmptyTextChapter() {
            return TextChapter.emptyTextChapter;
        }
    }

    static {
        TextChapter textChapter = new TextChapter(new ChapterInfo(null, null, null, null, null, 0, null, null, 0, null, null, false, null, null, null, null, null, null, 0, null, 1048575, null), -1, "emptyTextChapter", -1, false);
        textChapter.isCompleted = true;
        emptyTextChapter = textChapter;
    }

    public TextChapter(@NotNull ChapterInfo chapter, int i2, @NotNull String title, int i3, boolean z2) {
        Intrinsics.checkNotNullParameter(chapter, "chapter");
        Intrinsics.checkNotNullParameter(title, "title");
        this.chapter = chapter;
        this.position = i2;
        this.title = title;
        this.chaptersSize = i3;
        this.sameTitleRemoved = z2;
        this.textPages = new ArrayList<>();
        this.paragraphs = LazyKt__LazyJVMKt.lazy(new Function0<ArrayList<TextParagraph>>() { // from class: com.ykb.ebook.model.TextChapter$paragraphs$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final ArrayList<TextParagraph> invoke() {
                return this.this$0.getParagraphsInternal();
            }
        });
        this.pageParagraphs = LazyKt__LazyJVMKt.lazy(new Function0<List<? extends TextParagraph>>() { // from class: com.ykb.ebook.model.TextChapter$pageParagraphs$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends TextParagraph> invoke() {
                return this.this$0.getPageParagraphsInternal();
            }
        });
    }

    public static /* synthetic */ TextChapter copy$default(TextChapter textChapter, ChapterInfo chapterInfo, int i2, String str, int i3, boolean z2, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            chapterInfo = textChapter.chapter;
        }
        if ((i4 & 2) != 0) {
            i2 = textChapter.position;
        }
        int i5 = i2;
        if ((i4 & 4) != 0) {
            str = textChapter.title;
        }
        String str2 = str;
        if ((i4 & 8) != 0) {
            i3 = textChapter.chaptersSize;
        }
        int i6 = i3;
        if ((i4 & 16) != 0) {
            z2 = textChapter.sameTitleRemoved;
        }
        return textChapter.copy(chapterInfo, i5, str2, i6, z2);
    }

    private final int findPageByCharIndex(int charIndex) {
        int size = getPages().size();
        if (size == 0) {
            return -1;
        }
        for (int i2 = 0; i2 < size; i2++) {
            int iFindPageLastIndex = findPageLastIndex(getPages().get(i2));
            Log.d("TEST", "当前第 " + i2 + " 页：最后一个charIndex 为 " + iFindPageLastIndex);
            if (iFindPageLastIndex > charIndex) {
                Log.d("TEST", "查询到的index 为 " + i2);
                return i2;
            }
        }
        return 0;
    }

    private final int findPageLastIndex(TextPage textPage) {
        List<TextLine> lines = textPage.getLines();
        int size = lines.size();
        while (true) {
            size--;
            if (-1 >= size) {
                return 0;
            }
            List<BaseColumn> columns = lines.get(size).getColumns();
            for (int size2 = columns.size() - 1; -1 < size2; size2--) {
                BaseColumn baseColumn = columns.get(size2);
                if (baseColumn instanceof TextColumn) {
                    return ((TextColumn) baseColumn).getCharIndex();
                }
            }
        }
    }

    public final void cancelLayout() {
        TextChapterLayout textChapterLayout = this.layout;
        if (textChapterLayout != null) {
            textChapterLayout.cancel();
        }
        this.listener = null;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final ChapterInfo getChapter() {
        return this.chapter;
    }

    /* renamed from: component2, reason: from getter */
    public final int getPosition() {
        return this.position;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component4, reason: from getter */
    public final int getChaptersSize() {
        return this.chaptersSize;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getSameTitleRemoved() {
        return this.sameTitleRemoved;
    }

    @NotNull
    public final TextChapter copy(@NotNull ChapterInfo chapter, int position, @NotNull String title, int chaptersSize, boolean sameTitleRemoved) {
        Intrinsics.checkNotNullParameter(chapter, "chapter");
        Intrinsics.checkNotNullParameter(title, "title");
        return new TextChapter(chapter, position, title, chaptersSize, sameTitleRemoved);
    }

    public final void createLayout(@NotNull CoroutineScope scope, @NotNull BookContent bookContent) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(bookContent, "bookContent");
        if (this.layout != null) {
            throw new IllegalStateException("已经排版过了");
        }
        this.layout = new TextChapterLayout(scope, this, this.textPages, bookContent);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextChapter)) {
            return false;
        }
        TextChapter textChapter = (TextChapter) other;
        return Intrinsics.areEqual(this.chapter, textChapter.chapter) && this.position == textChapter.position && Intrinsics.areEqual(this.title, textChapter.title) && this.chaptersSize == textChapter.chaptersSize && this.sameTitleRemoved == textChapter.sameTitleRemoved;
    }

    @NotNull
    public final ChapterInfo getChapter() {
        return this.chapter;
    }

    public final int getChaptersSize() {
        return this.chaptersSize;
    }

    @NotNull
    public final String getContent() {
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = getPages().iterator();
        while (it.hasNext()) {
            sb.append(((TextPage) it.next()).getText());
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "stringBuilder.toString()");
        return string;
    }

    public final int getLastIndex() {
        return CollectionsKt__CollectionsKt.getLastIndex(getPages());
    }

    @Nullable
    public final TextPage getLastPage() {
        return (TextPage) CollectionsKt___CollectionsKt.lastOrNull((List) getPages());
    }

    public final int getLastParagraphPosition() {
        return ((TextParagraph) CollectionsKt___CollectionsKt.last((List) getPageParagraphs())).getChapterPosition();
    }

    public final int getLastReadLength() {
        return getReadLength(getLastIndex());
    }

    @NotNull
    public final Channel<TextPage> getLayoutChannel() {
        TextChapterLayout textChapterLayout = this.layout;
        Intrinsics.checkNotNull(textChapterLayout);
        return textChapterLayout.getChannel();
    }

    @Nullable
    public final LayoutProgressListener getListener() {
        return this.listener;
    }

    @NotNull
    public final String getNeedReadAloud(int pageIndex, boolean pageSplit, int startPos) {
        int lastIndex;
        StringBuilder sb = new StringBuilder();
        if ((!getPages().isEmpty()) && pageIndex <= (lastIndex = CollectionsKt__CollectionsKt.getLastIndex(getPages()))) {
            while (true) {
                sb.append(getPages().get(pageIndex).getText());
                if (pageSplit && !StringsKt__StringsKt.endsWith$default((CharSequence) sb, (CharSequence) "\n", false, 2, (Object) null)) {
                    sb.append("\n");
                }
                if (pageIndex == lastIndex) {
                    break;
                }
                pageIndex++;
            }
        }
        return sb.substring(startPos).toString();
    }

    public final int getNextPageLength(int length) {
        int pageIndexByCharIndex = getPageIndexByCharIndex(length) + 1;
        if (pageIndexByCharIndex >= getPageSize()) {
            return -1;
        }
        return getReadLength(pageIndexByCharIndex);
    }

    @Nullable
    public final TextPage getPage(int index) {
        return (TextPage) CollectionsKt___CollectionsKt.getOrNull(getPages(), index);
    }

    @Nullable
    public final TextPage getPageByReadPos(int readPos) {
        return getPage(getPageIndexByCharIndex(readPos));
    }

    public final int getPageIndexByCharIndex(int charIndex) {
        int i2;
        int i3 = 0;
        if (ReadBook.INSTANCE.getOpenChapterPageFromSearch()) {
            if (this.chapter.isPay()) {
                return findPageByCharIndex(charIndex);
            }
            return 0;
        }
        int size = getPages().size();
        if (size == 0) {
            return -1;
        }
        List<TextPage> pages = getPages();
        Integer numValueOf = Integer.valueOf(charIndex);
        if (size < 0) {
            throw new IllegalArgumentException("fromIndex (0) is greater than toIndex (" + size + ").");
        }
        if (size > pages.size()) {
            throw new IndexOutOfBoundsException("toIndex (" + size + ") is greater than size (" + pages.size() + ").");
        }
        int i4 = size - 1;
        int i5 = i4;
        while (true) {
            if (i3 > i5) {
                i2 = -(i3 + 1);
                break;
            }
            i2 = (i3 + i5) >>> 1;
            int iCompareValues = ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((TextLine) CollectionsKt___CollectionsKt.first((List) pages.get(i2).getLines())).getChapterPosition()), numValueOf);
            if (iCompareValues >= 0) {
                if (iCompareValues <= 0) {
                    break;
                }
                i5 = i2 - 1;
            } else {
                i3 = i2 + 1;
            }
        }
        int iAbs = Math.abs(i2 + 1) - 1;
        if (!this.isCompleted && iAbs == i4) {
            TextPage textPage = getPages().get(iAbs);
            if (charIndex > ((TextLine) CollectionsKt___CollectionsKt.first((List) textPage.getLines())).getChapterPosition() + textPage.getCharSize()) {
                return -1;
            }
        }
        return iAbs;
    }

    @NotNull
    public final List<TextParagraph> getPageParagraphs() {
        return (List) this.pageParagraphs.getValue();
    }

    @NotNull
    public final List<TextParagraph> getPageParagraphsInternal() {
        ArrayList arrayList = new ArrayList();
        int size = getPages().size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            arrayList.addAll(getPages().get(i3).getParagraphs());
        }
        int size2 = arrayList.size();
        while (i2 < size2) {
            TextParagraph textParagraph = (TextParagraph) arrayList.get(i2);
            i2++;
            textParagraph.setNum(i2);
        }
        return arrayList;
    }

    public final int getPageSize() {
        return getPages().size();
    }

    @NotNull
    public final List<TextPage> getPages() {
        return this.textPages;
    }

    public final int getParagraphNum(int position, boolean pageSplit) {
        for (TextParagraph textParagraph : getParagraphs(pageSplit)) {
            IntRange chapterIndices = textParagraph.getChapterIndices();
            int first = chapterIndices.getFirst();
            boolean z2 = false;
            if (position <= chapterIndices.getLast() && first <= position) {
                z2 = true;
            }
            if (z2) {
                return textParagraph.getNum();
            }
        }
        return -1;
    }

    @NotNull
    public final ArrayList<TextParagraph> getParagraphs() {
        return (ArrayList) this.paragraphs.getValue();
    }

    @NotNull
    public final ArrayList<TextParagraph> getParagraphsInternal() {
        ArrayList<TextParagraph> arrayList = new ArrayList<>();
        int size = getPages().size();
        for (int i2 = 0; i2 < size; i2++) {
            List<TextLine> lines = getPages().get(i2).getLines();
            int size2 = lines.size();
            for (int i3 = 0; i3 < size2; i3++) {
                TextLine textLine = lines.get(i3);
                if (textLine.getParagraphNum() > 0) {
                    if (CollectionsKt__CollectionsKt.getLastIndex(arrayList) < textLine.getParagraphNum() - 1) {
                        arrayList.add(new TextParagraph(textLine.getParagraphNum(), null, 2, 0 == true ? 1 : 0));
                    }
                    arrayList.get(textLine.getParagraphNum() - 1).getTextLines().add(textLine);
                }
            }
        }
        return arrayList;
    }

    public final int getPosition() {
        return this.position;
    }

    public final int getPrevPageLength(int length) {
        int pageIndexByCharIndex = getPageIndexByCharIndex(length) - 1;
        if (pageIndexByCharIndex < 0) {
            return -1;
        }
        return getReadLength(pageIndexByCharIndex);
    }

    public final int getReadLength(int pageIndex) {
        if (pageIndex >= 0) {
            List<TextPage> pages = getPages();
            boolean z2 = true;
            if (!(pages == null || pages.isEmpty())) {
                List<TextLine> lines = getPages().get(Math.min(pageIndex, getLastIndex())).getLines();
                if (lines != null && !lines.isEmpty()) {
                    z2 = false;
                }
                if (!z2) {
                    return ((TextLine) CollectionsKt___CollectionsKt.first((List) getPages().get(Math.min(pageIndex, getLastIndex())).getLines())).getChapterPosition();
                }
            }
        }
        return 0;
    }

    public final boolean getSameTitleRemoved() {
        return this.sameTitleRemoved;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getUnRead(int pageIndex) {
        int lastIndex;
        StringBuilder sb = new StringBuilder();
        if ((!getPages().isEmpty()) && pageIndex <= (lastIndex = CollectionsKt__CollectionsKt.getLastIndex(getPages()))) {
            while (true) {
                sb.append(getPages().get(pageIndex).getText());
                if (pageIndex == lastIndex) {
                    break;
                }
                pageIndex++;
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "stringBuilder.toString()");
        return string;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((((this.chapter.hashCode() * 31) + this.position) * 31) + this.title.hashCode()) * 31) + this.chaptersSize) * 31;
        boolean z2 = this.sameTitleRemoved;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode + i2;
    }

    /* renamed from: isCompleted, reason: from getter */
    public final boolean getIsCompleted() {
        return this.isCompleted;
    }

    public final boolean isLastIndex(int index) {
        return this.isCompleted && index >= getPages().size() - 1;
    }

    public final boolean isLastIndexCurrent(int index) {
        return index >= getPages().size() - 1;
    }

    @Override // com.ykb.ebook.common_interface.LayoutProgressListener
    public void onLayoutCompleted() {
        this.isCompleted = true;
        LayoutProgressListener layoutProgressListener = this.listener;
        if (layoutProgressListener != null) {
            layoutProgressListener.onLayoutCompleted();
        }
        this.listener = null;
    }

    @Override // com.ykb.ebook.common_interface.LayoutProgressListener
    public void onLayoutException(@NotNull Throwable e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        LayoutProgressListener layoutProgressListener = this.listener;
        if (layoutProgressListener != null) {
            layoutProgressListener.onLayoutException(e2);
        }
        this.listener = null;
    }

    @Override // com.ykb.ebook.common_interface.LayoutProgressListener
    public void onLayoutPageCompleted(int index, @NotNull TextPage page) {
        Intrinsics.checkNotNullParameter(page, "page");
        LayoutProgressListener layoutProgressListener = this.listener;
        if (layoutProgressListener != null) {
            layoutProgressListener.onLayoutPageCompleted(index, page);
        }
    }

    public final void setCompleted(boolean z2) {
        this.isCompleted = z2;
    }

    public final void setListener(@Nullable LayoutProgressListener layoutProgressListener) {
        this.listener = layoutProgressListener;
    }

    public final void setProgressListener(@Nullable LayoutProgressListener l2) {
        if (this.isCompleted) {
            return;
        }
        TextChapterLayout textChapterLayout = this.layout;
        if ((textChapterLayout != null ? textChapterLayout.getException() : null) == null) {
            this.listener = l2;
        } else if (l2 != null) {
            TextChapterLayout textChapterLayout2 = this.layout;
            Throwable exception = textChapterLayout2 != null ? textChapterLayout2.getException() : null;
            Intrinsics.checkNotNull(exception);
            l2.onLayoutException(exception);
        }
    }

    @NotNull
    public String toString() {
        return "TextChapter(chapter=" + this.chapter + ", position=" + this.position + ", title=" + this.title + ", chaptersSize=" + this.chaptersSize + ", sameTitleRemoved=" + this.sameTitleRemoved + ')';
    }

    @NotNull
    public final List<TextParagraph> getParagraphs(boolean pageSplit) {
        return pageSplit ? this.isCompleted ? getPageParagraphs() : getPageParagraphsInternal() : this.isCompleted ? getParagraphs() : getParagraphsInternal();
    }
}
