package com.ykb.ebook.page;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0011H\u0002J@\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\r0\u0013j\b\u0012\u0004\u0012\u00020\r`\u00142\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\rH\u0002J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\u001a\u001a\u00020\u0004H\u0002J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0016\u0010 \u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u0004J\u0018\u0010\"\u001a\u00060#j\u0002`$2\n\u0010\u000f\u001a\u00060#j\u0002`$H\u0002J0\u0010%\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u00042\u0006\u0010'\u001a\u00020\r2\u0006\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020\u001cH\u0002J@\u0010*\u001a\u0012\u0012\u0004\u0012\u00020\r0\u0013j\b\u0012\u0004\u0012\u00020\r`\u00142\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u00042\u0006\u0010'\u001a\u00020\r2\u0006\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020\u001cH\u0002J(\u0010+\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u00042\u0006\u0010'\u001a\u00020\r2\u0006\u0010(\u001a\u00020\rH\u0002J\u0010\u0010,\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082T¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/ykb/ebook/page/ContentHelp;", "", "()V", "MARK_QUOTATION", "", "MARK_QUOTATION_BEFORE", "MARK_SENTENCES_END", "MARK_SENTENCES_END_P", "MARK_SENTENCES_MID", "MARK_SENTENCES_SAY", "PARAGRAPH_DIAGLOG", "Lkotlin/text/Regex;", "WORD_MAX_LENGTH", "", "findNewLines", "str", "dict", "", "forceSplit", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "offset", "min", "gain", "tigger", "makeDict", "content", "match", "", com.heytap.mcssdk.constant.b.f7191p, "chr", "", "reSegment", "chapterName", "reduceLength", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "seekIndex", "key", "from", "to", "inOrder", "seekIndexS", "seekLast", "splitQuote", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nContentHelp.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContentHelp.kt\ncom/ykb/ebook/page/ContentHelp\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 3 Strings.kt\nkotlin/text/StringsKt__StringsKt\n*L\n1#1,608:1\n37#2,2:609\n37#2,2:641\n107#3:611\n79#3,29:612\n*S KotlinDebug\n*F\n+ 1 ContentHelp.kt\ncom/ykb/ebook/page/ContentHelp\n*L\n61#1:609,2\n267#1:641,2\n66#1:611\n66#1:612,29\n*E\n"})
/* loaded from: classes7.dex */
public final class ContentHelp {

    @NotNull
    private static final String MARK_QUOTATION = "\"“”";

    @NotNull
    private static final String MARK_QUOTATION_BEFORE = "，：,:";

    @NotNull
    private static final String MARK_SENTENCES_END = "？。！?!~";

    @NotNull
    private static final String MARK_SENTENCES_END_P = ".？。！?!~";

    @NotNull
    private static final String MARK_SENTENCES_MID = ".，、,—…";

    @NotNull
    private static final String MARK_SENTENCES_SAY = "问说喊唱叫骂道着答";
    private static final int WORD_MAX_LENGTH = 16;

    @NotNull
    public static final ContentHelp INSTANCE = new ContentHelp();

    @NotNull
    private static final Regex PARAGRAPH_DIAGLOG = new Regex("^[\"”“][^\"”“]+[\"”“]$");

    private ContentHelp() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0069, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final java.lang.String findNewLines(java.lang.String r25, java.util.List<java.lang.String> r26) {
        /*
            Method dump skipped, instructions count: 1144
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.page.ContentHelp.findNewLines(java.lang.String, java.util.List):java.lang.String");
    }

    private final ArrayList<Integer> forceSplit(String str, int offset, int min, int gain, int tigger) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<Integer> arrayListSeekIndexS = seekIndexS(str, MARK_SENTENCES_END_P, 0, str.length() - 2, true);
        ArrayList<Integer> arrayListSeekIndexS2 = seekIndexS(str, MARK_SENTENCES_MID, 0, str.length() - 2, true);
        if (arrayListSeekIndexS.size() < tigger && arrayListSeekIndexS2.size() < tigger * 3) {
            return arrayList;
        }
        int iMax = min;
        int i2 = 0;
        while (iMax < arrayListSeekIndexS.size()) {
            int i3 = 0;
            while (i2 < arrayListSeekIndexS2.size()) {
                Integer num = arrayListSeekIndexS2.get(i2);
                Intrinsics.checkNotNullExpressionValue(num, "arrayMid[j]");
                int iIntValue = num.intValue();
                Integer num2 = arrayListSeekIndexS.get(iMax);
                Intrinsics.checkNotNullExpressionValue(num2, "arrayEnd[i]");
                if (iIntValue < num2.intValue()) {
                    i3++;
                }
                i2++;
            }
            if (Math.random() * gain < (i3 / 2.5d) + 0.8d) {
                arrayList.add(Integer.valueOf(arrayListSeekIndexS.get(iMax).intValue() + offset));
                iMax = Math.max(iMax + min, iMax);
            }
            iMax++;
        }
        return arrayList;
    }

    private final List<String> makeDict(String content) {
        Matcher matcher = Pattern.compile("(?<=[\"'”“])([^\n\\p{P}]{1,16})(?=[\"'”“])").matcher(content);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        while (matcher.find()) {
            String word = matcher.group();
            if (!arrayList.contains(word)) {
                Intrinsics.checkNotNullExpressionValue(word, "word");
                arrayList.add(word);
            } else if (!arrayList2.contains(word)) {
                Intrinsics.checkNotNullExpressionValue(word, "word");
                arrayList2.add(word);
            }
        }
        return arrayList2;
    }

    private final boolean match(String rule, char chr) {
        return StringsKt__StringsKt.indexOf$default((CharSequence) rule, chr, 0, false, 6, (Object) null) != -1;
    }

    private final StringBuilder reduceLength(StringBuilder str) {
        String string = str.toString();
        Intrinsics.checkNotNullExpressionValue(string, "str.toString()");
        String[] strArr = (String[]) new Regex("\n").split(string, 0).toArray(new String[0]);
        int length = strArr.length;
        boolean[] zArr = new boolean[length];
        for (int i2 = 0; i2 < length; i2++) {
            zArr[i2] = PARAGRAPH_DIAGLOG.matches(strArr[i2]);
        }
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (zArr[i4]) {
                if (i3 < 0) {
                    i3 = 1;
                } else if (i3 < 2) {
                    i3++;
                }
            } else if (i3 > 1) {
                strArr[i4] = splitQuote(strArr[i4]);
                i3--;
            } else if (i3 > 0 && i4 < length - 2 && zArr[i4 + 1]) {
                strArr[i4] = splitQuote(strArr[i4]);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : strArr) {
            sb.append('\n');
            sb.append(str2);
        }
        return sb;
    }

    private final int seekIndex(String str, String key, int from, int to, boolean inOrder) {
        if (str.length() - from < 1) {
            return -1;
        }
        if (from <= 0) {
            from = 0;
        }
        int length = str.length();
        if (to > 0) {
            length = Math.min(length, to);
        }
        while (from < length) {
            if (StringsKt__StringsKt.indexOf$default((CharSequence) key, inOrder ? str.charAt(from) : str.charAt((str.length() - from) - 1), 0, false, 6, (Object) null) != -1) {
                return from;
            }
            from++;
        }
        return -1;
    }

    private final ArrayList<Integer> seekIndexS(String str, String key, int from, int to, boolean inOrder) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (str.length() - from < 1) {
            return arrayList;
        }
        if (from <= 0) {
            from = 0;
        }
        int length = str.length();
        if (to > 0) {
            length = Math.min(length, to);
        }
        while (from < length) {
            if (StringsKt__StringsKt.indexOf$default((CharSequence) key, inOrder ? str.charAt(from) : str.charAt((str.length() - from) - 1), 0, false, 6, (Object) null) != -1) {
                arrayList.add(Integer.valueOf(from));
            }
            from++;
        }
        return arrayList;
    }

    private final int seekLast(String str, String key, int from, int to) {
        if (str.length() - from < 1) {
            return -1;
        }
        int lastIndex = StringsKt__StringsKt.getLastIndex(str);
        if (from >= lastIndex || lastIndex <= 0) {
            from = lastIndex;
        }
        if (to <= 0) {
            to = 0;
        }
        while (from > to) {
            if (StringsKt__StringsKt.indexOf$default((CharSequence) key, str.charAt(from), 0, false, 6, (Object) null) != -1) {
                return from;
            }
            from--;
        }
        return -1;
    }

    private final String splitQuote(String str) {
        int iSeekIndex;
        int length = str.length();
        if (length < 3) {
            return str;
        }
        if (match(MARK_QUOTATION, str.charAt(0))) {
            int iSeekIndex2 = seekIndex(str, MARK_QUOTATION, 1, length - 2, true) + 1;
            if (iSeekIndex2 > 1 && !match(MARK_QUOTATION_BEFORE, str.charAt(iSeekIndex2 - 1))) {
                StringBuilder sb = new StringBuilder();
                String strSubstring = str.substring(0, iSeekIndex2);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                sb.append(strSubstring);
                sb.append('\n');
                String strSubstring2 = str.substring(iSeekIndex2);
                Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String).substring(startIndex)");
                sb.append(strSubstring2);
                return sb.toString();
            }
        } else {
            int i2 = length - 1;
            if (match(MARK_QUOTATION, str.charAt(i2)) && (iSeekIndex = i2 - seekIndex(str, MARK_QUOTATION, 1, length - 2, false)) > 1 && !match(MARK_QUOTATION_BEFORE, str.charAt(iSeekIndex - 1))) {
                StringBuilder sb2 = new StringBuilder();
                String strSubstring3 = str.substring(0, iSeekIndex);
                Intrinsics.checkNotNullExpressionValue(strSubstring3, "this as java.lang.String…ing(startIndex, endIndex)");
                sb2.append(strSubstring3);
                sb2.append('\n');
                String strSubstring4 = str.substring(iSeekIndex);
                Intrinsics.checkNotNullExpressionValue(strSubstring4, "this as java.lang.String).substring(startIndex)");
                sb2.append(strSubstring4);
                return sb2.toString();
            }
        }
        return str;
    }

    @NotNull
    public final String reSegment(@NotNull String content, @NotNull String chapterName) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(chapterName, "chapterName");
        List<String> listMakeDict = makeDict(content);
        String[] strArr = (String[]) StringsKt__StringsKt.split$default((CharSequence) content, new String[]{"*"}, false, 0, 6, (Object) null).toArray(new String[0]);
        new StringBuilder((int) (content.length() * 1.15d));
        int length = chapterName.length() - 1;
        int i2 = 0;
        boolean z2 = false;
        while (i2 <= length) {
            boolean z3 = Intrinsics.compare((int) chapterName.charAt(!z2 ? i2 : length), 32) <= 0;
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
        String string = chapterName.subSequence(i2, length + 1).toString();
        String str = strArr[0];
        int length2 = str.length() - 1;
        int i3 = 0;
        boolean z4 = false;
        while (i3 <= length2) {
            boolean z5 = Intrinsics.compare((int) str.charAt(!z4 ? i3 : length2), 32) <= 0;
            if (z4) {
                if (!z5) {
                    break;
                }
                length2--;
            } else if (z5) {
                i3++;
            } else {
                z4 = true;
            }
        }
        if (!Intrinsics.areEqual(string, str.subSequence(i3, length2 + 1).toString())) {
            new Regex("[\u3000\\s]+").replace(strArr[0], "");
        }
        int length3 = strArr.length;
        for (int i4 = 1; i4 < length3; i4++) {
            new Regex("[\u3000\\s]").replace(strArr[i4], "");
        }
        new StringBuilder((int) (content.length() * 1.15d));
        for (String str2 : strArr) {
            findNewLines(str2, listMakeDict);
        }
        return new Regex("\n(\\s*)").replace(new Regex("\n[\"“”]([^\n\"“”]+)([,:，：][\"”“])([^\n\"“”]+)").replace(new Regex("[:：][”“\"\\s]+").replace(new Regex("\\s*[\"”“]+[\\s]*[\"”“][\\s\"”“]*").replace(new Regex("^\\s+").replaceFirst(content, ""), "”\n“"), "：“"), "\n$1：“$3"), "\n");
    }
}
