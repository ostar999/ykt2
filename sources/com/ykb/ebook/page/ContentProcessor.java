package com.ykb.ebook.page;

import android.os.Build;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.ChapterInfo;
import com.ykb.ebook.util.ChineseUtils;
import com.ykb.ebook.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J4\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/ykb/ebook/page/ContentProcessor;", "", "bookName", "", "bookOrigin", "(Ljava/lang/String;Ljava/lang/String;)V", "getContent", "Lcom/ykb/ebook/model/BookContent;", "chapter", "Lcom/ykb/ebook/model/ChapterInfo;", "content", "includeTitle", "", "chineseConvert", "reSegment", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nContentProcessor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContentProcessor.kt\ncom/ykb/ebook/page/ContentProcessor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Strings.kt\nkotlin/text/StringsKt__StringsKt\n*L\n1#1,85:1\n1855#2:86\n1856#2:110\n107#3:87\n79#3,22:88\n*S KotlinDebug\n*F\n+ 1 ContentProcessor.kt\ncom/ykb/ebook/page/ContentProcessor\n*L\n70#1:86\n70#1:110\n71#1:87\n71#1:88,22\n*E\n"})
/* loaded from: classes7.dex */
public final class ContentProcessor {
    private static final boolean isAndroid8;

    @NotNull
    private final String bookName;

    @NotNull
    private final String bookOrigin;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final HashMap<String, WeakReference<ContentProcessor>> processors = new HashMap<>();

    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010\u000b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u0005\u001a*\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b`\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/ykb/ebook/page/ContentProcessor$Companion;", "", "()V", "isAndroid8", "", "processors", "Ljava/util/HashMap;", "", "Ljava/lang/ref/WeakReference;", "Lcom/ykb/ebook/page/ContentProcessor;", "Lkotlin/collections/HashMap;", "get", "book", "Lcom/ykb/ebook/model/BookInfo;", "bookName", "bookOrigin", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final ContentProcessor get(@NotNull BookInfo book) {
            Intrinsics.checkNotNullParameter(book, "book");
            return get(book.getTitle(), "");
        }

        @NotNull
        public final ContentProcessor get(@NotNull String bookName, @NotNull String bookOrigin) {
            Intrinsics.checkNotNullParameter(bookName, "bookName");
            Intrinsics.checkNotNullParameter(bookOrigin, "bookOrigin");
            WeakReference weakReference = (WeakReference) ContentProcessor.processors.get(bookName + bookOrigin);
            DefaultConstructorMarker defaultConstructorMarker = null;
            ContentProcessor contentProcessor = weakReference != null ? (ContentProcessor) weakReference.get() : null;
            if (contentProcessor != null) {
                return contentProcessor;
            }
            ContentProcessor contentProcessor2 = new ContentProcessor(bookName, bookOrigin, defaultConstructorMarker);
            ContentProcessor.processors.put(bookName + bookOrigin, new WeakReference(contentProcessor2));
            return contentProcessor2;
        }
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        boolean z2 = false;
        if (26 <= i2 && i2 < 28) {
            z2 = true;
        }
        isAndroid8 = z2;
    }

    private ContentProcessor(String str, String str2) {
        this.bookName = str;
        this.bookOrigin = str2;
    }

    public /* synthetic */ ContentProcessor(String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2);
    }

    public static /* synthetic */ com.ykb.ebook.model.BookContent getContent$default(ContentProcessor contentProcessor, ChapterInfo chapterInfo, String str, boolean z2, boolean z3, boolean z4, int i2, Object obj) {
        boolean z5 = (i2 & 4) != 0 ? false : z2;
        boolean z6 = (i2 & 8) != 0 ? false : z3;
        if ((i2 & 16) != 0) {
            z4 = true;
        }
        return contentProcessor.getContent(chapterInfo, str, z5, z6, z4);
    }

    @NotNull
    public final com.ykb.ebook.model.BookContent getContent(@NotNull ChapterInfo chapter, @NotNull String content, boolean includeTitle, boolean chineseConvert, boolean reSegment) {
        Intrinsics.checkNotNullParameter(chapter, "chapter");
        Intrinsics.checkNotNullParameter(content, "content");
        if (chineseConvert) {
            try {
                content = ChineseUtils.INSTANCE.t2s(content);
            } catch (Exception unused) {
                Log.INSTANCE.logE("ContentProcessor", "简繁转换出错》》》》");
            }
        }
        if (includeTitle) {
            content = ChapterInfo.getDisplayTitle$default(chapter, false, 1, null) + '\n' + content;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : StringsKt__StringsKt.split$default((CharSequence) content, new String[]{"\n"}, false, 0, 6, (Object) null)) {
            int length = str.length() - 1;
            int i2 = 0;
            boolean z2 = false;
            while (i2 <= length) {
                char cCharAt = str.charAt(!z2 ? i2 : length);
                boolean z3 = cCharAt <= ' ' && cCharAt != ' ';
                if (z2) {
                    if (!z3) {
                        break;
                    }
                    length--;
                } else if (z3) {
                    i2++;
                } else {
                    z2 = true;
                }
            }
            String string = str.subSequence(i2, length + 1).toString();
            if (string.length() > 0) {
                if (arrayList.isEmpty() && includeTitle) {
                    arrayList.add(string);
                } else {
                    arrayList.add(string);
                }
            }
        }
        return new com.ykb.ebook.model.BookContent(true, arrayList);
    }
}
