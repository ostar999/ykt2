package com.ykb.ebook.page;

import com.ykb.ebook.common.AppPatternKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.model.ChapterInfo;
import com.ykb.ebook.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/page/BookContent;", "", "()V", "analyzeContent", "", "bookChapter", "Lcom/ykb/ebook/model/ChapterInfo;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class BookContent {

    @NotNull
    public static final BookContent INSTANCE = new BookContent();

    private BookContent() {
    }

    @NotNull
    public final String analyzeContent(@NotNull ChapterInfo bookChapter) {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(bookChapter, "bookChapter");
        String content = bookChapter.getContent();
        ArrayList arrayList = new ArrayList();
        Iterator it = Regex.findAll$default(new Regex("<p(\\d+)>"), content, 0, 2, null).iterator();
        while (it.hasNext()) {
            arrayList.add(((MatchResult) it.next()).getGroupValues().get(1));
        }
        Matcher matcher = AppPatternKt.getQuestionPattern().matcher(content);
        Map<Integer, ? extends List<String>> map = new HashMap<>();
        int i2 = 0;
        while (matcher.find()) {
            String strGroup = matcher.group(1);
            String str = strGroup == null ? "" : strGroup;
            if (StringsKt__StringsKt.contains$default((CharSequence) str, (CharSequence) ",", false, 2, (Object) null)) {
                map.put(Integer.valueOf(i2), StringsKt__StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null));
            } else {
                map.put(Integer.valueOf(i2), CollectionsKt__CollectionsJVMKt.listOf(str));
            }
            i2++;
        }
        bookChapter.setQuestionIds(map);
        bookChapter.setParagraphIdList(arrayList);
        String strReplaceAll = AppPatternKt.getLfPattern().matcher(content).replaceAll("\n");
        Intrinsics.checkNotNullExpressionValue(strReplaceAll, "matcher.replaceAll(\"\\n\")");
        String strReplaceAll2 = AppPatternKt.getRgPattern().matcher(strReplaceAll).replaceAll("");
        Intrinsics.checkNotNullExpressionValue(strReplaceAll2, "matcher.replaceAll(\"\")");
        Matcher matcher2 = AppPatternKt.getQuestionPattern().matcher(strReplaceAll2);
        while (matcher2.find()) {
            String strGroup2 = matcher2.group(1);
            if (strGroup2 == null || (listEmptyList = StringsKt__StringsKt.split$default((CharSequence) strGroup2, new String[]{","}, false, 0, 6, (Object) null)) == null) {
                listEmptyList = CollectionsKt__CollectionsKt.emptyList();
            }
            if (listEmptyList.isEmpty()) {
                strReplaceAll2 = matcher2.replaceAll("");
                Intrinsics.checkNotNullExpressionValue(strReplaceAll2, "{\n                matche…laceAll(\"\")\n            }");
            } else if (listEmptyList.size() == 1) {
                strReplaceAll2 = matcher2.replaceAll(ReadConfig.QUESTION_SINGLE_CHAR);
                Intrinsics.checkNotNullExpressionValue(strReplaceAll2, "{\n                matche…INGLE_CHAR)\n            }");
            } else {
                strReplaceAll2 = matcher2.replaceAll(ReadConfig.QUESTION_MULTI_CHAR);
                Intrinsics.checkNotNullExpressionValue(strReplaceAll2, "{\n                matche…MULTI_CHAR)\n            }");
            }
        }
        Log.INSTANCE.logD("question_id", CollectionsKt___CollectionsKt.joinToString$default(bookChapter.getQuestionIdList(), ",", null, null, 0, null, null, 62, null));
        Matcher matcher3 = AppPatternKt.getPrPattern().matcher(strReplaceAll2);
        ArrayList arrayList2 = new ArrayList();
        while (matcher3.find()) {
            String strGroup3 = matcher3.group(1);
            if (strGroup3 != null) {
                arrayList2.add(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(strGroup3, "id:", "", false, 4, (Object) null), "count:", "", false, 4, (Object) null), "is_hot:", "", false, 4, (Object) null));
            }
        }
        String strReplaceAll3 = matcher3.replaceAll(ReadConfig.REVIEW_CHAR);
        Intrinsics.checkNotNullExpressionValue(strReplaceAll3, "matcher.replaceAll(ReadConfig.REVIEW_CHAR)");
        bookChapter.setReviewList(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        Matcher matcher4 = AppPatternKt.getImgPattern().matcher(strReplaceAll3);
        while (matcher4.find()) {
            String strGroup4 = matcher4.group(1);
            if (strGroup4 != null) {
                arrayList3.add(strGroup4);
            }
        }
        String strReplaceAll4 = matcher4.replaceAll("@");
        Intrinsics.checkNotNullExpressionValue(strReplaceAll4, "matcher.replaceAll(ReadConfig.SRC_REPLACE_CHAR)");
        bookChapter.setImgList(arrayList3);
        if (StringsKt__StringsJVMKt.isBlank(strReplaceAll4)) {
            throw new NullPointerException("内容为空");
        }
        bookChapter.setHandleContent(strReplaceAll4);
        return strReplaceAll4;
    }
}
