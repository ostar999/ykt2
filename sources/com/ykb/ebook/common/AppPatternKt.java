package com.ykb.ebook.common;

import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007\"\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007\"\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007\"\u0011\u0010\u0010\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0003¨\u0006\u0012"}, d2 = {"dataUriRegex", "Lkotlin/text/Regex;", "getDataUriRegex", "()Lkotlin/text/Regex;", "imgPattern", "Ljava/util/regex/Pattern;", "getImgPattern", "()Ljava/util/regex/Pattern;", "lfPattern", "getLfPattern", "prPattern", "getPrPattern", "questionPattern", "getQuestionPattern", "rgPattern", "getRgPattern", "rnRegex", "getRnRegex", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AppPatternKt {

    @NotNull
    private static final Pattern imgPattern;

    @NotNull
    private static final Pattern lfPattern;

    @NotNull
    private static final Pattern prPattern;

    @NotNull
    private static final Pattern questionPattern;

    @NotNull
    private static final Pattern rgPattern;

    @NotNull
    private static final Regex rnRegex = new Regex("[\\r\\n]");

    @NotNull
    private static final Regex dataUriRegex = new Regex("data:.*?;base64,(.*)");

    static {
        Pattern patternCompile = Pattern.compile("<p\\d+>");
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(\"<p\\\\d+>\")");
        lfPattern = patternCompile;
        Pattern patternCompile2 = Pattern.compile("</p\\d+>");
        Intrinsics.checkNotNullExpressionValue(patternCompile2, "compile(\"</p\\\\d+>\")");
        rgPattern = patternCompile2;
        Pattern patternCompile3 = Pattern.compile("<question_id>(.*?)</question_id>");
        Intrinsics.checkNotNullExpressionValue(patternCompile3, "compile(\"<question_id>(.*?)</question_id>\")");
        questionPattern = patternCompile3;
        Pattern patternCompile4 = Pattern.compile("<paragraph_review_count>(.*?)</paragraph_review_count>");
        Intrinsics.checkNotNullExpressionValue(patternCompile4, "compile(\"<paragraph_revi…paragraph_review_count>\")");
        prPattern = patternCompile4;
        Pattern patternCompile5 = Pattern.compile("<img src=\"(.*?)\"></img>");
        Intrinsics.checkNotNullExpressionValue(patternCompile5, "<clinit>");
        imgPattern = patternCompile5;
    }

    @NotNull
    public static final Regex getDataUriRegex() {
        return dataUriRegex;
    }

    @NotNull
    public static final Pattern getImgPattern() {
        return imgPattern;
    }

    @NotNull
    public static final Pattern getLfPattern() {
        return lfPattern;
    }

    @NotNull
    public static final Pattern getPrPattern() {
        return prPattern;
    }

    @NotNull
    public static final Pattern getQuestionPattern() {
        return questionPattern;
    }

    @NotNull
    public static final Pattern getRgPattern() {
        return rgPattern;
    }

    @NotNull
    public static final Regex getRnRegex() {
        return rnRegex;
    }
}
