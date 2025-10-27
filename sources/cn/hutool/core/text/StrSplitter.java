package cn.hutool.core.text;

import cn.hutool.core.lang.Matcher;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.text.finder.CharFinder;
import cn.hutool.core.text.finder.CharMatcherFinder;
import cn.hutool.core.text.finder.LengthFinder;
import cn.hutool.core.text.finder.PatternFinder;
import cn.hutool.core.text.finder.StrFinder;
import cn.hutool.core.text.split.SplitIter;
import cn.hutool.core.util.CharUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class StrSplitter {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$trimFunc$0(boolean z2, String str) {
        return z2 ? CharSequenceUtil.trim(str) : str;
    }

    public static List<String> split(CharSequence charSequence, char c3, boolean z2, boolean z3) {
        return split(charSequence, c3, 0, z2, z3);
    }

    public static String[] splitByLength(CharSequence charSequence, int i2) {
        return charSequence == null ? new String[0] : new SplitIter(charSequence, new LengthFinder(i2), -1, false).toArray(false);
    }

    public static List<String> splitByRegex(String str, String str2, int i2, boolean z2, boolean z3) {
        return split(str, PatternPool.get(str2), i2, z2, z3);
    }

    public static List<String> splitIgnoreCase(CharSequence charSequence, char c3, int i2, boolean z2, boolean z3) {
        return split(charSequence, c3, i2, z2, z3, true);
    }

    public static List<String> splitPath(CharSequence charSequence) {
        return splitPath(charSequence, 0);
    }

    public static String[] splitPathToArray(CharSequence charSequence) {
        return toArray(splitPath(charSequence));
    }

    public static String[] splitToArray(CharSequence charSequence, char c3, int i2, boolean z2, boolean z3) {
        return toArray(split(charSequence, c3, i2, z2, z3));
    }

    public static List<String> splitTrim(CharSequence charSequence, char c3, boolean z2) {
        return split(charSequence, c3, 0, true, z2);
    }

    public static List<String> splitTrimIgnoreCase(CharSequence charSequence, String str, int i2, boolean z2) {
        return split(charSequence, str, i2, true, z2, true);
    }

    private static String[] toArray(List<String> list) {
        return (String[]) list.toArray(new String[0]);
    }

    private static Function<String, String> trimFunc(final boolean z2) {
        return new Function() { // from class: cn.hutool.core.text.o
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StrSplitter.lambda$trimFunc$0(z2, (String) obj);
            }
        };
    }

    public static List<String> split(CharSequence charSequence, char c3, int i2, boolean z2, boolean z3) {
        return split(charSequence, c3, i2, z2, z3, false);
    }

    public static List<String> splitIgnoreCase(CharSequence charSequence, String str, int i2, boolean z2, boolean z3) {
        return split(charSequence, str, i2, z2, z3, true);
    }

    public static List<String> splitPath(CharSequence charSequence, int i2) {
        return split(charSequence, '/', i2, true, true);
    }

    public static String[] splitPathToArray(CharSequence charSequence, int i2) {
        return toArray(splitPath(charSequence, i2));
    }

    public static String[] splitToArray(CharSequence charSequence, String str, int i2, boolean z2, boolean z3) {
        return toArray(split(charSequence, str, i2, z2, z3));
    }

    public static List<String> splitTrim(CharSequence charSequence, char c3, int i2, boolean z2) {
        return split(charSequence, c3, i2, true, z2, false);
    }

    public static <R> List<R> split(CharSequence charSequence, char c3, int i2, boolean z2, Function<String, R> function) {
        return split(charSequence, c3, i2, z2, false, (Function) function);
    }

    public static String[] splitToArray(String str, int i2) {
        return toArray(split(str, i2));
    }

    public static List<String> splitTrim(CharSequence charSequence, String str, boolean z2) {
        return split(charSequence, str, true, z2);
    }

    public static List<String> split(CharSequence charSequence, char c3, int i2, boolean z2, boolean z3, boolean z4) {
        return split(charSequence, c3, i2, z3, z4, trimFunc(z2));
    }

    public static String[] splitToArray(String str, Pattern pattern, int i2, boolean z2, boolean z3) {
        return toArray(split(str, pattern, i2, z2, z3));
    }

    public static List<String> splitTrim(CharSequence charSequence, String str, int i2, boolean z2) {
        return split(charSequence, str, i2, true, z2);
    }

    public static <R> List<R> split(CharSequence charSequence, char c3, int i2, boolean z2, boolean z3, Function<String, R> function) {
        if (charSequence == null) {
            return new ArrayList(0);
        }
        return new SplitIter(charSequence, new CharFinder(c3, z3), i2, z2).toList(function);
    }

    public static List<String> split(CharSequence charSequence, String str, boolean z2, boolean z3) {
        return split(charSequence, str, -1, z2, z3, false);
    }

    public static List<String> split(CharSequence charSequence, String str, int i2, boolean z2, boolean z3) {
        return split(charSequence, str, i2, z2, z3, false);
    }

    public static List<String> split(CharSequence charSequence, String str, int i2, boolean z2, boolean z3, boolean z4) {
        if (charSequence == null) {
            return new ArrayList(0);
        }
        return new SplitIter(charSequence, new StrFinder(str, z4), i2, z3).toList(z2);
    }

    public static List<String> split(CharSequence charSequence, int i2) {
        if (charSequence == null) {
            return new ArrayList(0);
        }
        return new SplitIter(charSequence, new CharMatcherFinder(new Matcher() { // from class: cn.hutool.core.text.n
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return CharUtil.isBlankChar(((Character) obj).charValue());
            }
        }), i2, true).toList(false);
    }

    public static List<String> split(String str, Pattern pattern, int i2, boolean z2, boolean z3) {
        if (str == null) {
            return new ArrayList(0);
        }
        return new SplitIter(str, new PatternFinder(pattern), i2, z3).toList(z2);
    }
}
