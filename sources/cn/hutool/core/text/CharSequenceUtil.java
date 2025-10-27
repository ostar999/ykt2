package cn.hutool.core.text;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.comparator.VersionComparator;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.Matcher;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.text.finder.CharFinder;
import cn.hutool.core.text.finder.StrFinder;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import cn.hutool.core.util.ReUtil;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class CharSequenceUtil {
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    public static final String NULL = "null";
    public static final String SPACE = " ";

    public static String addPrefixIfNot(CharSequence charSequence, CharSequence charSequence2) {
        return prependIfMissing(charSequence, charSequence2, charSequence2);
    }

    public static String addSuffixIfNot(CharSequence charSequence, CharSequence charSequence2) {
        return appendIfMissing(charSequence, charSequence2, charSequence2);
    }

    public static String appendIfMissing(CharSequence charSequence, CharSequence charSequence2, CharSequence... charSequenceArr) {
        return appendIfMissing(charSequence, charSequence2, false, charSequenceArr);
    }

    public static String appendIfMissingIgnoreCase(CharSequence charSequence, CharSequence charSequence2, CharSequence... charSequenceArr) {
        return appendIfMissing(charSequence, charSequence2, true, charSequenceArr);
    }

    public static String blankToDefault(CharSequence charSequence, String str) {
        return isBlank(charSequence) ? str : charSequence.toString();
    }

    public static String brief(CharSequence charSequence, int i2) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        if (i2 <= 0 || length <= i2) {
            return charSequence.toString();
        }
        if (i2 == 1) {
            return String.valueOf(charSequence.charAt(0));
        }
        if (i2 == 2) {
            return charSequence.charAt(0) + StrPool.DOT;
        }
        if (i2 == 3) {
            return charSequence.charAt(0) + StrPool.DOT + charSequence.charAt(length - 1);
        }
        if (i2 != 4) {
            int i3 = i2 - 3;
            int i4 = i3 / 2;
            String string = charSequence.toString();
            return format("{}...{}", string.substring(0, (i3 % 2) + i4), string.substring(length - i4));
        }
        return charSequence.charAt(0) + StrPool.DOUBLE_DOT + charSequence.charAt(length - 1);
    }

    public static StringBuilder builder(CharSequence... charSequenceArr) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence charSequence : charSequenceArr) {
            sb.append(charSequence);
        }
        return sb;
    }

    public static ByteBuffer byteBuffer(CharSequence charSequence, String str) {
        return ByteBuffer.wrap(bytes(charSequence, str));
    }

    public static int byteLength(CharSequence charSequence, Charset charset) {
        if (charSequence == null) {
            return 0;
        }
        return charSequence.toString().getBytes(charset).length;
    }

    public static byte[] bytes(CharSequence charSequence) {
        return bytes(charSequence, Charset.defaultCharset());
    }

    public static String center(CharSequence charSequence, int i2) {
        return center(charSequence, i2, ' ');
    }

    public static String cleanBlank(CharSequence charSequence) {
        return filter(charSequence, new Filter() { // from class: cn.hutool.core.text.f
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return CharSequenceUtil.lambda$cleanBlank$0((Character) obj);
            }
        });
    }

    public static CharSequence commonPrefix(CharSequence charSequence, CharSequence charSequence2) {
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return "";
        }
        int iMin = Math.min(charSequence.length(), charSequence2.length());
        int i2 = 0;
        while (i2 < iMin && charSequence.charAt(i2) == charSequence2.charAt(i2)) {
            i2++;
        }
        return charSequence.subSequence(0, i2);
    }

    public static CharSequence commonSuffix(CharSequence charSequence, CharSequence charSequence2) {
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return "";
        }
        int length = charSequence.length() - 1;
        for (int length2 = charSequence2.length() - 1; length >= 0 && length2 >= 0 && charSequence.charAt(length) == charSequence2.charAt(length2); length2--) {
            length--;
        }
        return charSequence.subSequence(length + 1, charSequence.length());
    }

    public static int compare(CharSequence charSequence, CharSequence charSequence2, boolean z2) {
        if (charSequence == charSequence2) {
            return 0;
        }
        return charSequence == null ? z2 ? -1 : 1 : charSequence2 == null ? z2 ? 1 : -1 : charSequence.toString().compareTo(charSequence2.toString());
    }

    public static int compareIgnoreCase(CharSequence charSequence, CharSequence charSequence2, boolean z2) {
        if (charSequence == charSequence2) {
            return 0;
        }
        return charSequence == null ? z2 ? -1 : 1 : charSequence2 == null ? z2 ? 1 : -1 : charSequence.toString().compareToIgnoreCase(charSequence2.toString());
    }

    public static int compareVersion(CharSequence charSequence, CharSequence charSequence2) {
        return VersionComparator.INSTANCE.compare(str(charSequence), str(charSequence2));
    }

    public static String concat(boolean z2, CharSequence... charSequenceArr) {
        StrBuilder strBuilder = new StrBuilder();
        for (CharSequence charSequenceNullToEmpty : charSequenceArr) {
            if (z2) {
                charSequenceNullToEmpty = nullToEmpty(charSequenceNullToEmpty);
            }
            strBuilder.append(charSequenceNullToEmpty);
        }
        return strBuilder.toString();
    }

    public static boolean contains(CharSequence charSequence, char c3) {
        return indexOf(charSequence, c3) > -1;
    }

    public static boolean containsAll(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (isBlank(charSequence) || ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            return false;
        }
        for (CharSequence charSequence2 : charSequenceArr) {
            if (!contains(charSequence, charSequence2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        return getContainsStr(charSequence, charSequenceArr) != null;
    }

    public static boolean containsAnyIgnoreCase(CharSequence charSequence, CharSequence... charSequenceArr) {
        return getContainsStrIgnoreCase(charSequence, charSequenceArr) != null;
    }

    public static boolean containsBlank(CharSequence charSequence) {
        int length;
        if (charSequence == null || (length = charSequence.length()) == 0) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (CharUtil.isBlankChar(charSequence.charAt(i2))) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return charSequence == null ? charSequence2 == null : indexOfIgnoreCase(charSequence, charSequence2) > -1;
    }

    public static boolean containsOnly(CharSequence charSequence, char... cArr) {
        if (isEmpty(charSequence)) {
            return true;
        }
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!PrimitiveArrayUtil.contains(cArr, charSequence.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static int count(CharSequence charSequence, CharSequence charSequence2) {
        int length = 0;
        if (hasEmpty(charSequence, charSequence2) || charSequence2.length() > charSequence.length()) {
            return 0;
        }
        String string = charSequence.toString();
        String string2 = charSequence2.toString();
        int i2 = 0;
        while (true) {
            int iIndexOf = string.indexOf(string2, length);
            if (iIndexOf <= -1) {
                return i2;
            }
            i2++;
            length = iIndexOf + charSequence2.length();
        }
    }

    public static String[] cut(CharSequence charSequence, int i2) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        int i3 = 0;
        if (length < i2) {
            return new String[]{charSequence.toString()};
        }
        int iCount = NumberUtil.count(length, i2);
        String[] strArr = new String[iCount];
        String string = charSequence.toString();
        while (i3 < iCount) {
            int i4 = i3 * i2;
            strArr[i3] = string.substring(i4, i3 == iCount + (-1) ? length : i2 + i4);
            i3++;
        }
        return strArr;
    }

    public static String desensitized(CharSequence charSequence, DesensitizedUtil.DesensitizedType desensitizedType) {
        return DesensitizedUtil.desensitized(charSequence, desensitizedType);
    }

    public static String emptyIfNull(CharSequence charSequence) {
        return nullToEmpty(charSequence);
    }

    public static String emptyToDefault(CharSequence charSequence, String str) {
        return isEmpty(charSequence) ? str : charSequence.toString();
    }

    public static String emptyToNull(CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return null;
        }
        return charSequence.toString();
    }

    public static boolean endWith(CharSequence charSequence, char c3) {
        return !isEmpty(charSequence) && c3 == charSequence.charAt(charSequence.length() - 1);
    }

    public static boolean endWithAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (!isEmpty(charSequence) && !ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (endWith(charSequence, charSequence2, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean endWithAnyIgnoreCase(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (!isEmpty(charSequence) && !ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (endWith(charSequence, charSequence2, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean endWithIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return endWith(charSequence, charSequence2, true);
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        return equals(charSequence, charSequence2, false);
    }

    public static boolean equalsAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        return equalsAny(charSequence, false, charSequenceArr);
    }

    public static boolean equalsAnyIgnoreCase(CharSequence charSequence, CharSequence... charSequenceArr) {
        return equalsAny(charSequence, true, charSequenceArr);
    }

    public static boolean equalsCharAt(CharSequence charSequence, int i2, char c3) {
        return charSequence != null && i2 >= 0 && charSequence.length() > i2 && c3 == charSequence.charAt(i2);
    }

    public static boolean equalsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return equals(charSequence, charSequence2, true);
    }

    public static String filter(CharSequence charSequence, Filter<Character> filter) {
        if (charSequence == null || filter == null) {
            return str(charSequence);
        }
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = charSequence.charAt(i2);
            if (filter.accept(Character.valueOf(cCharAt))) {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    public static <T extends CharSequence> T firstNonBlank(T... tArr) {
        return (T) ArrayUtil.firstMatch(new Matcher() { // from class: cn.hutool.core.text.e
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return CharSequenceUtil.isNotBlank((CharSequence) obj);
            }
        }, tArr);
    }

    public static <T extends CharSequence> T firstNonEmpty(T... tArr) {
        return (T) ArrayUtil.firstMatch(new Matcher() { // from class: cn.hutool.core.text.h
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return CharSequenceUtil.isNotEmpty((CharSequence) obj);
            }
        }, tArr);
    }

    public static <T extends CharSequence> T firstNonNull(T... tArr) {
        return (T) ArrayUtil.firstNonNull(tArr);
    }

    public static String fixLength(CharSequence charSequence, char c3, int i2) {
        int length = i2 - charSequence.length();
        if (length <= 0) {
            return charSequence.toString();
        }
        return ((Object) charSequence) + repeat(c3, length);
    }

    public static String format(CharSequence charSequence, Object... objArr) {
        return charSequence == null ? "null" : (ArrayUtil.isEmpty(objArr) || isBlank(charSequence)) ? charSequence.toString() : StrFormatter.format(charSequence.toString(), objArr);
    }

    public static String genGetter(CharSequence charSequence) {
        return upperFirstAndAddPre(charSequence, "get");
    }

    public static String genSetter(CharSequence charSequence) {
        return upperFirstAndAddPre(charSequence, "set");
    }

    public static String getContainsStr(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (!isEmpty(charSequence) && !ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (charSequence2 != null && charSequence.toString().contains(charSequence2)) {
                    return charSequence2.toString();
                }
            }
        }
        return null;
    }

    public static String getContainsStrIgnoreCase(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (!isEmpty(charSequence) && !ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (containsIgnoreCase(charSequence, charSequence2)) {
                    return charSequence2.toString();
                }
            }
        }
        return null;
    }

    public static String getGeneralField(CharSequence charSequence) {
        String string = charSequence.toString();
        if (string.startsWith("get") || string.startsWith("set")) {
            return removePreAndLowerFirst(charSequence, 3);
        }
        if (string.startsWith("is")) {
            return removePreAndLowerFirst(charSequence, 2);
        }
        return null;
    }

    public static boolean hasBlank(CharSequence... charSequenceArr) {
        if (ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            return true;
        }
        for (CharSequence charSequence : charSequenceArr) {
            if (isBlank(charSequence)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasEmpty(CharSequence... charSequenceArr) {
        if (ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            return true;
        }
        for (CharSequence charSequence : charSequenceArr) {
            if (isEmpty(charSequence)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasLetter(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            if (CharUtil.isLetter(charSequence.charAt(i2))) {
                return true;
            }
        }
        return false;
    }

    public static String hide(CharSequence charSequence, int i2, int i3) {
        return replace(charSequence, i2, i3, '*');
    }

    public static int indexOf(CharSequence charSequence, char c3) {
        return indexOf(charSequence, c3, 0);
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return indexOfIgnoreCase(charSequence, charSequence2, 0);
    }

    public static String indexedFormat(CharSequence charSequence, Object... objArr) {
        return MessageFormat.format(charSequence.toString(), objArr);
    }

    public static boolean isAllBlank(CharSequence... charSequenceArr) {
        if (ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            return true;
        }
        for (CharSequence charSequence : charSequenceArr) {
            if (isNotBlank(charSequence)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllCharMatch(CharSequence charSequence, Matcher<Character> matcher) {
        if (isBlank(charSequence)) {
            return false;
        }
        int length = charSequence.length();
        do {
            length--;
            if (length < 0) {
                return true;
            }
        } while (matcher.match(Character.valueOf(charSequence.charAt(length))));
        return false;
    }

    public static boolean isAllEmpty(CharSequence... charSequenceArr) {
        if (ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            return true;
        }
        for (CharSequence charSequence : charSequenceArr) {
            if (isNotEmpty(charSequence)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNotBlank(CharSequence... charSequenceArr) {
        return !hasBlank(charSequenceArr);
    }

    public static boolean isAllNotEmpty(CharSequence... charSequenceArr) {
        return !hasEmpty(charSequenceArr);
    }

    public static boolean isBlank(CharSequence charSequence) {
        int length;
        if (charSequence != null && (length = charSequence.length()) != 0) {
            for (int i2 = 0; i2 < length; i2++) {
                if (!CharUtil.isBlankChar(charSequence.charAt(i2))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isBlankOrUndefined(CharSequence charSequence) {
        if (isBlank(charSequence)) {
            return true;
        }
        return isNullOrUndefinedStr(charSequence);
    }

    public static boolean isCharEquals(CharSequence charSequence) throws IllegalArgumentException {
        Assert.notEmpty(charSequence, "Str to check must be not empty!", new Object[0]);
        return count(charSequence, charSequence.charAt(0)) == charSequence.length();
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isEmptyOrUndefined(CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return true;
        }
        return isNullOrUndefinedStr(charSequence);
    }

    public static boolean isLowerCase(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (Character.isUpperCase(charSequence.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    public static boolean isNullOrUndefined(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        return isNullOrUndefinedStr(charSequence);
    }

    private static boolean isNullOrUndefinedStr(CharSequence charSequence) {
        String strTrim = charSequence.toString().trim();
        return "null".equals(strTrim) || "undefined".equals(strTrim);
    }

    public static boolean isNumeric(CharSequence charSequence) {
        return isAllCharMatch(charSequence, new Matcher() { // from class: cn.hutool.core.text.d
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return Character.isDigit(((Character) obj).charValue());
            }
        });
    }

    public static boolean isSubEquals(CharSequence charSequence, int i2, CharSequence charSequence2, boolean z2) {
        return isSubEquals(charSequence, i2, charSequence2, 0, charSequence2.length(), z2);
    }

    public static boolean isSurround(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        if (isBlank(charSequence) || charSequence.length() < charSequence2.length() + charSequence3.length()) {
            return false;
        }
        String string = charSequence.toString();
        return string.startsWith(charSequence2.toString()) && string.endsWith(charSequence3.toString());
    }

    public static boolean isUpperCase(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (Character.isLowerCase(charSequence.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWrap(CharSequence charSequence, String str, String str2) {
        if (ArrayUtil.hasNull(charSequence, str, str2)) {
            return false;
        }
        String string = charSequence.toString();
        return string.startsWith(str) && string.endsWith(str2);
    }

    public static String join(CharSequence charSequence, Object... objArr) {
        return ArrayUtil.join(objArr, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$cleanBlank$0(Character ch) {
        return !CharUtil.isBlankChar(ch.charValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$subByCodePoint$1(StringBuilder sb, int i2) {
        sb.append(Character.toChars(i2));
    }

    public static int lastIndexOf(CharSequence charSequence, CharSequence charSequence2, int i2, boolean z2) {
        return (isEmpty(charSequence) || isEmpty(charSequence2)) ? equals(charSequence, charSequence2) ? 0 : -1 : new StrFinder(charSequence2, z2).setText(charSequence).setNegative(true).start(i2);
    }

    public static int lastIndexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return lastIndexOfIgnoreCase(charSequence, charSequence2, charSequence.length());
    }

    public static int length(CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        return charSequence.length();
    }

    public static String lowerFirst(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        if (charSequence.length() > 0) {
            char cCharAt = charSequence.charAt(0);
            if (Character.isUpperCase(cCharAt)) {
                return Character.toLowerCase(cCharAt) + subSuf(charSequence, 1);
            }
        }
        return charSequence.toString();
    }

    public static String maxLength(CharSequence charSequence, int i2) throws Throwable {
        Assert.isTrue(i2 > 0);
        if (charSequence == null) {
            return null;
        }
        if (charSequence.length() <= i2) {
            return charSequence.toString();
        }
        return sub(charSequence, 0, i2) + "...";
    }

    public static String move(CharSequence charSequence, int i2, int i3, int i4) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        int length = charSequence.length();
        if (Math.abs(i4) > length) {
            i4 %= length;
        }
        StringBuilder sb = new StringBuilder(length);
        if (i4 > 0) {
            int iMin = Math.min(i4 + i3, charSequence.length());
            sb.append(charSequence.subSequence(0, i2));
            sb.append(charSequence.subSequence(i3, iMin));
            sb.append(charSequence.subSequence(i2, i3));
            sb.append(charSequence.subSequence(iMin, charSequence.length()));
        } else {
            if (i4 >= 0) {
                return str(charSequence);
            }
            int iMax = Math.max(i4 + i2, 0);
            sb.append(charSequence.subSequence(0, iMax));
            sb.append(charSequence.subSequence(i2, i3));
            sb.append(charSequence.subSequence(iMax, i2));
            sb.append(charSequence.subSequence(i3, charSequence.length()));
        }
        return sb.toString();
    }

    public static String normalize(CharSequence charSequence) {
        return Normalizer.normalize(charSequence, Normalizer.Form.NFC);
    }

    public static String nullToDefault(CharSequence charSequence, String str) {
        return charSequence == null ? str : charSequence.toString();
    }

    public static String nullToEmpty(CharSequence charSequence) {
        return nullToDefault(charSequence, "");
    }

    public static int ordinalIndexOf(CharSequence charSequence, CharSequence charSequence2, int i2) {
        int iIndexOf = -1;
        if (charSequence != null && charSequence2 != null && i2 > 0) {
            if (charSequence2.length() == 0) {
                return 0;
            }
            int i3 = 0;
            do {
                iIndexOf = indexOf(charSequence, charSequence2, iIndexOf + 1, false);
                if (iIndexOf < 0) {
                    return iIndexOf;
                }
                i3++;
            } while (i3 < i2);
        }
        return iIndexOf;
    }

    public static String padAfter(CharSequence charSequence, int i2, char c3) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        return length == i2 ? charSequence.toString() : length > i2 ? sub(charSequence, length - i2, length) : charSequence.toString().concat(repeat(c3, i2 - length));
    }

    public static String padPre(CharSequence charSequence, int i2, CharSequence charSequence2) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        return length == i2 ? charSequence.toString() : length > i2 ? subPre(charSequence, i2) : repeatByLength(charSequence2, i2 - length).concat(charSequence.toString());
    }

    public static String prependIfMissing(CharSequence charSequence, CharSequence charSequence2, CharSequence... charSequenceArr) {
        return prependIfMissing(charSequence, charSequence2, false, charSequenceArr);
    }

    public static String prependIfMissingIgnoreCase(CharSequence charSequence, CharSequence charSequence2, CharSequence... charSequenceArr) {
        return prependIfMissing(charSequence, charSequence2, true, charSequenceArr);
    }

    public static String removeAll(CharSequence charSequence, CharSequence charSequence2) {
        return (isEmpty(charSequence) || isEmpty(charSequence2)) ? str(charSequence) : charSequence.toString().replace(charSequence2, "");
    }

    public static String removeAllLineBreaks(CharSequence charSequence) {
        return removeAll(charSequence, '\r', '\n');
    }

    public static String removeAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        String str = str(charSequence);
        if (isNotEmpty(charSequence)) {
            for (CharSequence charSequence2 : charSequenceArr) {
                str = removeAll(str, charSequence2);
            }
        }
        return str;
    }

    public static String removePreAndLowerFirst(CharSequence charSequence, int i2) {
        if (charSequence == null) {
            return null;
        }
        if (charSequence.length() <= i2) {
            return charSequence.toString();
        }
        char lowerCase = Character.toLowerCase(charSequence.charAt(i2));
        int i3 = i2 + 1;
        if (charSequence.length() <= i3) {
            return String.valueOf(lowerCase);
        }
        return lowerCase + charSequence.toString().substring(i3);
    }

    public static String removePrefix(CharSequence charSequence, CharSequence charSequence2) {
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return str(charSequence);
        }
        String string = charSequence.toString();
        return string.startsWith(charSequence2.toString()) ? subSuf(string, charSequence2.length()) : string;
    }

    public static String removePrefixIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return str(charSequence);
        }
        String string = charSequence.toString();
        return startWithIgnoreCase(charSequence, charSequence2) ? subSuf(string, charSequence2.length()) : string;
    }

    public static String removeSufAndLowerFirst(CharSequence charSequence, CharSequence charSequence2) {
        return lowerFirst(removeSuffix(charSequence, charSequence2));
    }

    public static String removeSuffix(CharSequence charSequence, CharSequence charSequence2) {
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return str(charSequence);
        }
        String string = charSequence.toString();
        return string.endsWith(charSequence2.toString()) ? subPre(string, string.length() - charSequence2.length()) : string;
    }

    public static String removeSuffixIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return str(charSequence);
        }
        String string = charSequence.toString();
        return endWithIgnoreCase(charSequence, charSequence2) ? subPre(string, string.length() - charSequence2.length()) : string;
    }

    public static String repeat(char c3, int i2) {
        if (i2 <= 0) {
            return "";
        }
        char[] cArr = new char[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            cArr[i3] = c3;
        }
        return new String(cArr);
    }

    public static String repeatAndJoin(CharSequence charSequence, int i2, CharSequence charSequence2) {
        if (i2 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(charSequence.length() * i2);
        sb.append(charSequence);
        int i3 = i2 - 1;
        boolean zIsNotEmpty = isNotEmpty(charSequence2);
        while (true) {
            int i4 = i3 - 1;
            if (i3 <= 0) {
                return sb.toString();
            }
            if (zIsNotEmpty) {
                sb.append(charSequence2);
            }
            sb.append(charSequence);
            i3 = i4;
        }
    }

    public static String repeatByLength(CharSequence charSequence, int i2) {
        if (charSequence == null) {
            return null;
        }
        if (i2 <= 0) {
            return "";
        }
        int length = charSequence.length();
        if (length == i2) {
            return charSequence.toString();
        }
        if (length > i2) {
            return subPre(charSequence, i2);
        }
        char[] cArr = new char[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            cArr[i3] = charSequence.charAt(i3 % length);
        }
        return new String(cArr);
    }

    public static String replace(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return replace(charSequence, 0, charSequence2, charSequence3, false);
    }

    public static String replaceChars(CharSequence charSequence, String str, CharSequence charSequence2) {
        return (isEmpty(charSequence) || isEmpty(str)) ? str(charSequence) : replaceChars(charSequence, str.toCharArray(), charSequence2);
    }

    public static String replaceFirst(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return replaceFirst(charSequence, charSequence2, charSequence3, false);
    }

    public static String replaceIgnoreCase(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return replace(charSequence, 0, charSequence2, charSequence3, true);
    }

    public static String replaceLast(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return replaceLast(charSequence, charSequence2, charSequence3, false);
    }

    public static List<String> split(CharSequence charSequence, char c3) {
        return split(charSequence, c3, 0);
    }

    public static String[] splitToArray(CharSequence charSequence, CharSequence charSequence2) {
        return charSequence == null ? new String[0] : StrSplitter.splitToArray((CharSequence) charSequence.toString(), str(charSequence2), 0, false, false);
    }

    public static int[] splitToInt(CharSequence charSequence, char c3) {
        return (int[]) Convert.convert(int[].class, (Object) splitTrim(charSequence, c3));
    }

    public static long[] splitToLong(CharSequence charSequence, char c3) {
        return (long[]) Convert.convert(long[].class, (Object) splitTrim(charSequence, c3));
    }

    public static List<String> splitTrim(CharSequence charSequence, char c3) {
        return splitTrim(charSequence, c3, -1);
    }

    public static boolean startWith(CharSequence charSequence, char c3) {
        return !isEmpty(charSequence) && c3 == charSequence.charAt(0);
    }

    public static boolean startWithAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (!isEmpty(charSequence) && !ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (startWith(charSequence, charSequence2, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean startWithAnyIgnoreCase(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (!isEmpty(charSequence) && !ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (startWith(charSequence, charSequence2, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean startWithIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return startWith(charSequence, charSequence2, true);
    }

    public static boolean startWithIgnoreEquals(CharSequence charSequence, CharSequence charSequence2) {
        return startWith(charSequence, charSequence2, false, true);
    }

    public static String str(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return charSequence.toString();
    }

    public static StrBuilder strBuilder(CharSequence... charSequenceArr) {
        return StrBuilder.create(charSequenceArr);
    }

    public static String strip(CharSequence charSequence, CharSequence charSequence2) {
        return equals(charSequence, charSequence2) ? "" : strip(charSequence, charSequence2, charSequence2);
    }

    public static String stripIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return stripIgnoreCase(charSequence, charSequence2, charSequence2);
    }

    public static String sub(CharSequence charSequence, int i2, int i3) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        int length = charSequence.length();
        if (i2 < 0) {
            i2 += length;
            if (i2 < 0) {
                i2 = 0;
            }
        } else if (i2 > length) {
            i2 = length;
        }
        if (i3 >= 0 ? i3 > length : (i3 = i3 + length) < 0) {
            i3 = length;
        }
        if (i3 < i2) {
            int i4 = i3;
            i3 = i2;
            i2 = i4;
        }
        return i2 == i3 ? "" : charSequence.toString().substring(i2, i3);
    }

    public static String subAfter(CharSequence charSequence, CharSequence charSequence2, boolean z2) {
        if (isEmpty(charSequence)) {
            if (charSequence == null) {
                return null;
            }
            return "";
        }
        if (charSequence2 == null) {
            return "";
        }
        String string = charSequence.toString();
        String string2 = charSequence2.toString();
        int iLastIndexOf = z2 ? string.lastIndexOf(string2) : string.indexOf(string2);
        return (-1 == iLastIndexOf || charSequence.length() + (-1) == iLastIndexOf) ? "" : string.substring(iLastIndexOf + charSequence2.length());
    }

    public static String subBefore(CharSequence charSequence, CharSequence charSequence2, boolean z2) {
        if (isEmpty(charSequence) || charSequence2 == null) {
            if (charSequence == null) {
                return null;
            }
            return charSequence.toString();
        }
        String string = charSequence.toString();
        String string2 = charSequence2.toString();
        if (string2.isEmpty()) {
            return "";
        }
        int iLastIndexOf = z2 ? string.lastIndexOf(string2) : string.indexOf(string2);
        return -1 == iLastIndexOf ? string : iLastIndexOf == 0 ? "" : string.substring(0, iLastIndexOf);
    }

    public static String subBetween(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        int iIndexOf;
        if (charSequence != null && charSequence2 != null && charSequence3 != null) {
            String string = charSequence.toString();
            String string2 = charSequence2.toString();
            String string3 = charSequence3.toString();
            int iIndexOf2 = string.indexOf(string2);
            if (iIndexOf2 != -1 && (iIndexOf = string.indexOf(string3, string2.length() + iIndexOf2)) != -1) {
                return string.substring(iIndexOf2 + string2.length(), iIndexOf);
            }
        }
        return null;
    }

    public static String[] subBetweenAll(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        int i2 = 1;
        if (hasEmpty(charSequence, charSequence2, charSequence3) || !contains(charSequence, charSequence2)) {
            return new String[0];
        }
        LinkedList linkedList = new LinkedList();
        String[] strArrSplitToArray = splitToArray(charSequence, charSequence2);
        if (charSequence2.equals(charSequence3)) {
            int length = strArrSplitToArray.length - 1;
            while (i2 < length) {
                linkedList.add(strArrSplitToArray[i2]);
                i2 += 2;
            }
        } else {
            while (i2 < strArrSplitToArray.length) {
                String str = strArrSplitToArray[i2];
                int iIndexOf = str.indexOf(charSequence3.toString());
                if (iIndexOf > 0) {
                    linkedList.add(str.substring(0, iIndexOf));
                }
                i2++;
            }
        }
        return (String[]) linkedList.toArray(new String[0]);
    }

    public static String subByCodePoint(CharSequence charSequence, int i2, int i3) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        if (i2 < 0 || i2 > i3) {
            throw new IllegalArgumentException();
        }
        if (i2 == i3) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        charSequence.toString().codePoints().skip(i2).limit(i3 - i2).forEach(new IntConsumer() { // from class: cn.hutool.core.text.i
            @Override // java.util.function.IntConsumer
            public final void accept(int i4) {
                CharSequenceUtil.lambda$subByCodePoint$1(sb, i4);
            }
        });
        return sb.toString();
    }

    public static String subPre(CharSequence charSequence, int i2) {
        return sub(charSequence, 0, i2);
    }

    public static String subPreGbk(CharSequence charSequence, int i2, CharSequence charSequence2) {
        return subPreGbk(charSequence, i2, true) + ((Object) charSequence2);
    }

    public static String subSuf(CharSequence charSequence, int i2) {
        if (isEmpty(charSequence)) {
            return null;
        }
        return sub(charSequence, i2, charSequence.length());
    }

    public static String subSufByLength(CharSequence charSequence, int i2) {
        if (isEmpty(charSequence)) {
            return null;
        }
        return i2 <= 0 ? "" : sub(charSequence, -i2, charSequence.length());
    }

    public static String subWithLength(String str, int i2, int i3) {
        return sub(str, i2, i2 < 0 ? i2 - i3 : i3 + i2);
    }

    public static String swapCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            char c3 = charArray[i2];
            if (Character.isUpperCase(c3)) {
                charArray[i2] = Character.toLowerCase(c3);
            } else if (Character.isTitleCase(c3)) {
                charArray[i2] = Character.toLowerCase(c3);
            } else if (Character.isLowerCase(c3)) {
                charArray[i2] = Character.toUpperCase(c3);
            }
        }
        return new String(charArray);
    }

    public static String toCamelCase(CharSequence charSequence) {
        return NamingCase.toCamelCase(charSequence);
    }

    public static String toSymbolCase(CharSequence charSequence, char c3) {
        return NamingCase.toSymbolCase(charSequence, c3);
    }

    public static String toUnderlineCase(CharSequence charSequence) {
        return NamingCase.toUnderlineCase(charSequence);
    }

    public static int totalLength(CharSequence... charSequenceArr) {
        int length = charSequenceArr.length;
        int length2 = 0;
        for (int i2 = 0; i2 < length; i2++) {
            CharSequence charSequence = charSequenceArr[i2];
            length2 += charSequence == null ? 0 : charSequence.length();
        }
        return length2;
    }

    public static String trim(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return trim(charSequence, 0);
    }

    public static String trimEnd(CharSequence charSequence) {
        return trim(charSequence, 1);
    }

    public static String trimStart(CharSequence charSequence) {
        return trim(charSequence, -1);
    }

    public static String trimToEmpty(CharSequence charSequence) {
        return charSequence == null ? "" : trim(charSequence);
    }

    public static String trimToNull(CharSequence charSequence) {
        String strTrim = trim(charSequence);
        if ("".equals(strTrim)) {
            return null;
        }
        return strTrim;
    }

    public static String unWrap(CharSequence charSequence, String str, String str2) {
        return isWrap(charSequence, str, str2) ? sub(charSequence, str.length(), charSequence.length() - str2.length()) : charSequence.toString();
    }

    public static String upperFirst(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        if (charSequence.length() > 0) {
            char cCharAt = charSequence.charAt(0);
            if (Character.isLowerCase(cCharAt)) {
                return Character.toUpperCase(cCharAt) + subSuf(charSequence, 1);
            }
        }
        return charSequence.toString();
    }

    public static String upperFirstAndAddPre(CharSequence charSequence, String str) {
        if (charSequence == null || str == null) {
            return null;
        }
        return str + upperFirst(charSequence);
    }

    public static byte[] utf8Bytes(CharSequence charSequence) {
        return bytes(charSequence, CharsetUtil.CHARSET_UTF_8);
    }

    public static String wrap(CharSequence charSequence, CharSequence charSequence2) {
        return wrap(charSequence, charSequence2, charSequence2);
    }

    public static String[] wrapAll(CharSequence charSequence, CharSequence charSequence2, CharSequence... charSequenceArr) {
        String[] strArr = new String[charSequenceArr.length];
        for (int i2 = 0; i2 < charSequenceArr.length; i2++) {
            strArr[i2] = wrap(charSequenceArr[i2], charSequence, charSequence2);
        }
        return strArr;
    }

    public static String[] wrapAllIfMissing(CharSequence charSequence, CharSequence charSequence2, CharSequence... charSequenceArr) {
        String[] strArr = new String[charSequenceArr.length];
        for (int i2 = 0; i2 < charSequenceArr.length; i2++) {
            strArr[i2] = wrapIfMissing(charSequenceArr[i2], charSequence, charSequence2);
        }
        return strArr;
    }

    public static String[] wrapAllWithPair(CharSequence charSequence, CharSequence... charSequenceArr) {
        return wrapAll(charSequence, charSequence, charSequenceArr);
    }

    public static String[] wrapAllWithPairIfMissing(CharSequence charSequence, CharSequence... charSequenceArr) {
        return wrapAllIfMissing(charSequence, charSequence, charSequenceArr);
    }

    public static String wrapIfMissing(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        int length = isNotEmpty(charSequence) ? 0 + charSequence.length() : 0;
        if (isNotEmpty(charSequence2)) {
            length += charSequence2.length();
        }
        if (isNotEmpty(charSequence3)) {
            length += charSequence3.length();
        }
        StringBuilder sb = new StringBuilder(length);
        if (isNotEmpty(charSequence2) && !startWith(charSequence, charSequence2)) {
            sb.append(charSequence2);
        }
        if (isNotEmpty(charSequence)) {
            sb.append(charSequence);
        }
        if (isNotEmpty(charSequence3) && !endWith(charSequence, charSequence3)) {
            sb.append(charSequence3);
        }
        return sb.toString();
    }

    public static String appendIfMissing(CharSequence charSequence, CharSequence charSequence2, boolean z2, CharSequence... charSequenceArr) {
        if (charSequence == null || isEmpty(charSequence2) || endWith(charSequence, charSequence2, z2)) {
            return str(charSequence);
        }
        if (ArrayUtil.isNotEmpty((Object[]) charSequenceArr)) {
            for (CharSequence charSequence3 : charSequenceArr) {
                if (endWith(charSequence, charSequence3, z2)) {
                    return charSequence.toString();
                }
            }
        }
        return charSequence.toString().concat(charSequence2.toString());
    }

    public static byte[] bytes(CharSequence charSequence, String str) {
        return bytes(charSequence, isBlank(str) ? Charset.defaultCharset() : Charset.forName(str));
    }

    public static String center(CharSequence charSequence, int i2, char c3) {
        if (charSequence == null || i2 <= 0) {
            return str(charSequence);
        }
        int length = charSequence.length();
        int i3 = i2 - length;
        return i3 <= 0 ? charSequence.toString() : padAfter(padPre(charSequence, length + (i3 / 2), c3), i2, c3).toString();
    }

    public static boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        return charSequence.toString().contains(charSequence2);
    }

    public static boolean containsAny(CharSequence charSequence, char... cArr) {
        if (!isEmpty(charSequence)) {
            int length = charSequence.length();
            for (int i2 = 0; i2 < length; i2++) {
                if (PrimitiveArrayUtil.contains(cArr, charSequence.charAt(i2))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2, boolean z2) {
        if (charSequence == null) {
            return charSequence2 == null;
        }
        if (charSequence2 == null) {
            return false;
        }
        return z2 ? charSequence.toString().equalsIgnoreCase(charSequence2.toString()) : charSequence.toString().contentEquals(charSequence2);
    }

    public static boolean equalsAny(CharSequence charSequence, boolean z2, CharSequence... charSequenceArr) {
        if (ArrayUtil.isEmpty((Object[]) charSequenceArr)) {
            return false;
        }
        for (CharSequence charSequence2 : charSequenceArr) {
            if (equals(charSequence, charSequence2, z2)) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(CharSequence charSequence, char c3, int i2) {
        return charSequence instanceof String ? ((String) charSequence).indexOf(c3, i2) : indexOf(charSequence, c3, i2, -1);
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int i2) {
        return indexOf(charSequence, charSequence2, i2, true);
    }

    public static boolean isSubEquals(CharSequence charSequence, int i2, CharSequence charSequence2, int i3, int i4, boolean z2) {
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        return charSequence.toString().regionMatches(z2, i2, charSequence2.toString(), i3, i4);
    }

    public static <T> String join(CharSequence charSequence, Iterable<T> iterable) {
        return CollUtil.join(iterable, charSequence);
    }

    public static int lastIndexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int i2) {
        return lastIndexOf(charSequence, charSequence2, i2, true);
    }

    public static String prependIfMissing(CharSequence charSequence, CharSequence charSequence2, boolean z2, CharSequence... charSequenceArr) {
        if (charSequence == null || isEmpty(charSequence2) || startWith(charSequence, charSequence2, z2)) {
            return str(charSequence);
        }
        if (charSequenceArr != null && charSequenceArr.length > 0) {
            for (CharSequence charSequence3 : charSequenceArr) {
                if (startWith(charSequence, charSequence3, z2)) {
                    return charSequence.toString();
                }
            }
        }
        return charSequence2.toString().concat(charSequence.toString());
    }

    public static String replace(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, boolean z2) {
        return replace(charSequence, 0, charSequence2, charSequence3, z2);
    }

    public static String replaceFirst(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, boolean z2) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        int iIndexOf = indexOf(charSequence, charSequence2, 0, z2);
        return -1 == iIndexOf ? str(charSequence) : replace(charSequence, iIndexOf, charSequence2.length() + iIndexOf, charSequence3);
    }

    public static String replaceLast(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, boolean z2) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        int iLastIndexOf = lastIndexOf(charSequence, charSequence2, charSequence.length(), z2);
        return -1 == iLastIndexOf ? str(charSequence) : replace(charSequence, iLastIndexOf, charSequence2, charSequence3, z2);
    }

    public static List<String> split(CharSequence charSequence, char c3, int i2) {
        return split(charSequence, c3, i2, false, false);
    }

    public static String[] splitToArray(CharSequence charSequence, char c3) {
        return splitToArray(charSequence, c3, 0);
    }

    public static int[] splitToInt(CharSequence charSequence, CharSequence charSequence2) {
        return (int[]) Convert.convert(int[].class, (Object) splitTrim(charSequence, charSequence2));
    }

    public static long[] splitToLong(CharSequence charSequence, CharSequence charSequence2) {
        return (long[]) Convert.convert(long[].class, (Object) splitTrim(charSequence, charSequence2));
    }

    public static List<String> splitTrim(CharSequence charSequence, CharSequence charSequence2) {
        return splitTrim(charSequence, charSequence2, -1);
    }

    public static String stripIgnoreCase(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        int length = charSequence.length();
        String string = charSequence.toString();
        int length2 = startWithIgnoreCase(string, charSequence2) ? charSequence2.length() : 0;
        if (endWithIgnoreCase(string, charSequence3)) {
            length -= charSequence3.length();
        }
        return string.substring(length2, length);
    }

    public static String subPreGbk(CharSequence charSequence, int i2, boolean z2) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        byte[] bArrBytes = bytes(charSequence, CharsetUtil.CHARSET_GBK);
        if (bArrBytes.length <= i2) {
            return charSequence.toString();
        }
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            if (bArrBytes[i4] < 0) {
                i3++;
            }
        }
        if (i3 % 2 != 0) {
            i2 = z2 ? i2 + 1 : i2 - 1;
        }
        return new String(bArrBytes, 0, i2, CharsetUtil.CHARSET_GBK);
    }

    public static String toCamelCase(CharSequence charSequence, char c3) {
        return NamingCase.toCamelCase(charSequence, c3);
    }

    public static String trim(CharSequence charSequence, int i2) {
        return trim(charSequence, i2, new Predicate() { // from class: cn.hutool.core.text.g
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CharUtil.isBlankChar(((Character) obj).charValue());
            }
        });
    }

    public static String wrap(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return nullToEmpty(charSequence2).concat(nullToEmpty(charSequence)).concat(nullToEmpty(charSequence3));
    }

    public static byte[] bytes(CharSequence charSequence, Charset charset) {
        if (charSequence == null) {
            return null;
        }
        if (charset == null) {
            return charSequence.toString().getBytes();
        }
        return charSequence.toString().getBytes(charset);
    }

    public static boolean endWith(CharSequence charSequence, CharSequence charSequence2, boolean z2) {
        return endWith(charSequence, charSequence2, z2, false);
    }

    public static String replace(CharSequence charSequence, int i2, CharSequence charSequence2, CharSequence charSequence3, boolean z2) {
        if (!isEmpty(charSequence) && !isEmpty(charSequence2)) {
            if (charSequence3 == null) {
                charSequence3 = "";
            }
            int length = charSequence.length();
            int length2 = charSequence2.length();
            if (length < length2) {
                return str(charSequence);
            }
            if (i2 > length) {
                return str(charSequence);
            }
            if (i2 < 0) {
                i2 = 0;
            }
            StringBuilder sb = new StringBuilder((length - length2) + charSequence3.length());
            if (i2 != 0) {
                sb.append(charSequence.subSequence(0, i2));
            }
            while (true) {
                int iIndexOf = indexOf(charSequence, charSequence2, i2, z2);
                if (iIndexOf <= -1) {
                    break;
                }
                sb.append(charSequence.subSequence(i2, iIndexOf));
                sb.append(charSequence3);
                i2 = iIndexOf + length2;
            }
            if (i2 < length) {
                sb.append(charSequence.subSequence(i2, length));
            }
            return sb.toString();
        }
        return str(charSequence);
    }

    public static List<String> split(CharSequence charSequence, char c3, boolean z2, boolean z3) {
        return split(charSequence, c3, 0, z2, z3);
    }

    public static String[] splitToArray(CharSequence charSequence, char c3, int i2) throws IllegalArgumentException {
        Assert.notNull(charSequence, "Text must be not null!", new Object[0]);
        return StrSplitter.splitToArray((CharSequence) charSequence.toString(), c3, i2, false, false);
    }

    public static List<String> splitTrim(CharSequence charSequence, char c3, int i2) {
        return split(charSequence, c3, i2, true, true);
    }

    public static boolean startWith(CharSequence charSequence, CharSequence charSequence2, boolean z2) {
        return startWith(charSequence, charSequence2, z2, false);
    }

    public static String strip(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        int length = charSequence.length();
        String string = charSequence.toString();
        int length2 = startWith(string, charSequence2) ? charSequence2.length() : 0;
        if (endWith(string, charSequence3)) {
            length -= charSequence3.length();
        }
        return string.substring(Math.min(length2, length), Math.max(length2, length));
    }

    public static String trim(CharSequence charSequence, int i2, Predicate<Character> predicate) {
        int i3;
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        int i4 = 0;
        if (i2 <= 0) {
            while (i4 < length && predicate.test(Character.valueOf(charSequence.charAt(i4)))) {
                i4++;
            }
        }
        if (i2 >= 0) {
            i3 = length;
            while (i4 < i3 && predicate.test(Character.valueOf(charSequence.charAt(i3 - 1)))) {
                i3--;
            }
        } else {
            i3 = length;
        }
        if (i4 <= 0 && i3 >= length) {
            return charSequence.toString();
        }
        return charSequence.toString().substring(i4, i3);
    }

    public static boolean endWith(CharSequence charSequence, CharSequence charSequence2, boolean z2, boolean z3) {
        if (charSequence == null || charSequence2 == null) {
            if (z3) {
                return false;
            }
            return charSequence == null && charSequence2 == null;
        }
        if (charSequence.toString().regionMatches(z2, charSequence.length() - charSequence2.length(), charSequence2.toString(), 0, charSequence2.length())) {
            return (z3 && equals(charSequence, charSequence2, z2)) ? false : true;
        }
        return false;
    }

    public static boolean isWrap(CharSequence charSequence, String str) {
        return isWrap(charSequence, str, str);
    }

    public static String removeAll(CharSequence charSequence, char... cArr) {
        if (charSequence != null && !PrimitiveArrayUtil.isEmpty(cArr)) {
            int length = charSequence.length();
            if (length == 0) {
                return str(charSequence);
            }
            StringBuilder sb = new StringBuilder(length);
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = charSequence.charAt(i2);
                if (!PrimitiveArrayUtil.contains(cArr, cCharAt)) {
                    sb.append(cCharAt);
                }
            }
            return sb.toString();
        }
        return str(charSequence);
    }

    public static String repeat(CharSequence charSequence, int i2) {
        if (charSequence == null) {
            return null;
        }
        if (i2 <= 0 || charSequence.length() == 0) {
            return "";
        }
        if (i2 == 1) {
            return charSequence.toString();
        }
        int length = charSequence.length();
        long j2 = length * i2;
        int i3 = (int) j2;
        if (i3 == j2) {
            char[] cArr = new char[i3];
            charSequence.toString().getChars(0, length, cArr, 0);
            while (true) {
                int i4 = i3 - length;
                if (length < i4) {
                    System.arraycopy(cArr, 0, cArr, length, length);
                    length <<= 1;
                } else {
                    System.arraycopy(cArr, 0, cArr, length, i4);
                    return new String(cArr);
                }
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Required String length is too large: " + j2);
        }
    }

    public static String replaceChars(CharSequence charSequence, char[] cArr, CharSequence charSequence2) {
        if (!isEmpty(charSequence) && !PrimitiveArrayUtil.isEmpty(cArr)) {
            HashSet hashSet = new HashSet(cArr.length);
            for (char c3 : cArr) {
                hashSet.add(Character.valueOf(c3));
            }
            int length = charSequence.length();
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = charSequence.charAt(i2);
                sb.append(hashSet.contains(Character.valueOf(cCharAt)) ? charSequence2 : Character.valueOf(cCharAt));
            }
            return sb.toString();
        }
        return str(charSequence);
    }

    public static List<String> split(CharSequence charSequence, char c3, int i2, boolean z2, boolean z3) {
        return StrSplitter.split(charSequence, c3, i2, z2, z3);
    }

    public static List<String> splitTrim(CharSequence charSequence, CharSequence charSequence2, int i2) {
        return split(charSequence, charSequence2, i2, true, true);
    }

    public static boolean startWith(CharSequence charSequence, CharSequence charSequence2, boolean z2, boolean z3) {
        if (charSequence == null || charSequence2 == null) {
            if (z3) {
                return false;
            }
            return charSequence == null && charSequence2 == null;
        }
        if (charSequence.toString().regionMatches(z2, 0, charSequence2.toString(), 0, charSequence2.length())) {
            return (z3 && equals(charSequence, charSequence2, z2)) ? false : true;
        }
        return false;
    }

    public static String unWrap(CharSequence charSequence, char c3, char c4) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        if (charSequence.charAt(0) == c3 && charSequence.charAt(charSequence.length() - 1) == c4) {
            return sub(charSequence, 1, charSequence.length() - 1);
        }
        return charSequence.toString();
    }

    public static int indexOf(CharSequence charSequence, char c3, int i2, int i3) {
        if (isEmpty(charSequence)) {
            return -1;
        }
        return new CharFinder(c3).setText(charSequence).setEndIndex(i3).start(i2);
    }

    public static boolean isSurround(CharSequence charSequence, char c3, char c4) {
        return !isBlank(charSequence) && charSequence.length() >= 2 && charSequence.charAt(0) == c3 && charSequence.charAt(charSequence.length() - 1) == c4;
    }

    public static boolean isWrap(CharSequence charSequence, char c3) {
        return isWrap(charSequence, c3, c3);
    }

    public static String padAfter(CharSequence charSequence, int i2, CharSequence charSequence2) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        if (length == i2) {
            return charSequence.toString();
        }
        if (length > i2) {
            return subSufByLength(charSequence, i2);
        }
        return charSequence.toString().concat(repeatByLength(charSequence2, i2 - length));
    }

    public static String padPre(CharSequence charSequence, int i2, char c3) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        if (length == i2) {
            return charSequence.toString();
        }
        if (length > i2) {
            return subPre(charSequence, i2);
        }
        return repeat(c3, i2 - length).concat(charSequence.toString());
    }

    public static <R> List<R> split(CharSequence charSequence, char c3, int i2, boolean z2, Function<String, R> function) {
        return StrSplitter.split(charSequence, c3, i2, z2, function);
    }

    public static int count(CharSequence charSequence, char c3) {
        if (isEmpty(charSequence)) {
            return 0;
        }
        int length = charSequence.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (c3 == charSequence.charAt(i3)) {
                i2++;
            }
        }
        return i2;
    }

    public static boolean isWrap(CharSequence charSequence, char c3, char c4) {
        return charSequence != null && charSequence.charAt(0) == c3 && charSequence.charAt(charSequence.length() - 1) == c4;
    }

    public static List<String> split(CharSequence charSequence, CharSequence charSequence2) {
        return split(charSequence, charSequence2, false, false);
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int i2, boolean z2) {
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return equals(charSequence, charSequence2) ? 0 : -1;
        }
        return new StrFinder(charSequence2, z2).setText(charSequence).start(i2);
    }

    public static String removePreAndLowerFirst(CharSequence charSequence, CharSequence charSequence2) {
        return lowerFirst(removePrefix(charSequence, charSequence2));
    }

    public static List<String> split(CharSequence charSequence, CharSequence charSequence2, boolean z2, boolean z3) {
        return split(charSequence, charSequence2, 0, z2, z3);
    }

    public static boolean startWith(CharSequence charSequence, CharSequence charSequence2) {
        return startWith(charSequence, charSequence2, false);
    }

    public static String subAfter(CharSequence charSequence, char c3, boolean z2) {
        if (isEmpty(charSequence)) {
            if (charSequence == null) {
                return null;
            }
            return "";
        }
        String string = charSequence.toString();
        int iLastIndexOf = z2 ? string.lastIndexOf(c3) : string.indexOf(c3);
        return -1 == iLastIndexOf ? "" : string.substring(iLastIndexOf + 1);
    }

    public static String subBetween(CharSequence charSequence, CharSequence charSequence2) {
        return subBetween(charSequence, charSequence2, charSequence2);
    }

    public static String center(CharSequence charSequence, int i2, CharSequence charSequence2) {
        if (charSequence != null && i2 > 0) {
            if (isEmpty(charSequence2)) {
                charSequence2 = " ";
            }
            int length = charSequence.length();
            int i3 = i2 - length;
            if (i3 <= 0) {
                return charSequence.toString();
            }
            return padAfter(padPre(charSequence, length + (i3 / 2), charSequence2), i2, charSequence2).toString();
        }
        return str(charSequence);
    }

    public static boolean endWith(CharSequence charSequence, CharSequence charSequence2) {
        return endWith(charSequence, charSequence2, false);
    }

    public static List<String> split(CharSequence charSequence, CharSequence charSequence2, int i2, boolean z2, boolean z3) {
        return StrSplitter.split(charSequence, charSequence2 == null ? null : charSequence2.toString(), i2, z2, z3);
    }

    public static String subBefore(CharSequence charSequence, char c3, boolean z2) {
        if (isEmpty(charSequence)) {
            if (charSequence == null) {
                return null;
            }
            return "";
        }
        String string = charSequence.toString();
        int iLastIndexOf = z2 ? string.lastIndexOf(c3) : string.indexOf(c3);
        return -1 == iLastIndexOf ? string : iLastIndexOf == 0 ? "" : string.substring(0, iLastIndexOf);
    }

    public static String unWrap(CharSequence charSequence, char c3) {
        return unWrap(charSequence, c3, c3);
    }

    public static String[] split(CharSequence charSequence, int i2) {
        return StrSplitter.splitByLength(charSequence, i2);
    }

    public static String[] subBetweenAll(CharSequence charSequence, CharSequence charSequence2) {
        return subBetweenAll(charSequence, charSequence2, charSequence2);
    }

    public static String replace(CharSequence charSequence, int i2, int i3, char c3) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        String str = str(charSequence);
        int[] array = str.codePoints().toArray();
        int length = array.length;
        if (i2 > length) {
            return str;
        }
        if (i3 > length) {
            i3 = length;
        }
        if (i2 > i3) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i4 = 0; i4 < length; i4++) {
            if (i4 >= i2 && i4 < i3) {
                sb.append(c3);
            } else {
                sb.append(new String(array, i4, 1));
            }
        }
        return sb.toString();
    }

    public static String replace(CharSequence charSequence, int i2, int i3, CharSequence charSequence2) {
        if (isEmpty(charSequence)) {
            return str(charSequence);
        }
        String str = str(charSequence);
        int[] array = str.codePoints().toArray();
        int length = array.length;
        if (i2 > length) {
            return str;
        }
        if (i3 > length) {
            i3 = length;
        }
        if (i2 > i3) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i4 = 0; i4 < i2; i4++) {
            sb.append(new String(array, i4, 1));
        }
        sb.append(charSequence2);
        while (i3 < length) {
            sb.append(new String(array, i3, 1));
            i3++;
        }
        return sb.toString();
    }

    public static String replace(CharSequence charSequence, Pattern pattern, Func1<java.util.regex.Matcher, String> func1) {
        return ReUtil.replaceAll(charSequence, pattern, func1);
    }

    public static String replace(CharSequence charSequence, String str, Func1<java.util.regex.Matcher, String> func1) {
        return ReUtil.replaceAll(charSequence, str, func1);
    }
}
