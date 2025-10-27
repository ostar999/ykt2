package cn.hutool.core.text;

import cn.hutool.core.map.SafeConcurrentHashMap;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class AntPathMatcher {
    private static final int CACHE_TURNOFF_THRESHOLD = 65536;
    public static final String DEFAULT_PATH_SEPARATOR = "/";
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{[^/]+?}");
    private static final char[] WILDCARD_CHARS = {'*', '?', '{'};
    private volatile Boolean cachePatterns;
    private boolean caseSensitive;
    private String pathSeparator;
    private PathSeparatorPatternCache pathSeparatorPatternCache;
    private final Map<String, AntPathStringMatcher> stringMatcherCache;
    private final Map<String, String[]> tokenizedPatternCache;
    private boolean trimTokens;

    public static class AntPathStringMatcher {
        private static final String DEFAULT_VARIABLE_PATTERN = "((?s).*)";
        private static final Pattern GLOB_PATTERN = Pattern.compile("\\?|\\*|\\{((?:\\{[^/]+?}|[^/{}]|\\\\[{}])+?)}");
        private final boolean caseSensitive;
        private final boolean exactMatch;
        private final Pattern pattern;
        private final String rawPattern;
        private final List<String> variableNames = new ArrayList();

        public AntPathStringMatcher(String str, boolean z2) {
            this.rawPattern = str;
            this.caseSensitive = z2;
            StringBuilder sb = new StringBuilder();
            Matcher matcher = GLOB_PATTERN.matcher(str);
            int iEnd = 0;
            while (matcher.find()) {
                sb.append(quote(str, iEnd, matcher.start()));
                String strGroup = matcher.group();
                if ("?".equals(strGroup)) {
                    sb.append('.');
                } else if ("*".equals(strGroup)) {
                    sb.append(".*");
                } else if (strGroup.startsWith(StrPool.DELIM_START) && strGroup.endsWith("}")) {
                    int iIndexOf = strGroup.indexOf(58);
                    if (iIndexOf == -1) {
                        sb.append(DEFAULT_VARIABLE_PATTERN);
                        this.variableNames.add(matcher.group(1));
                    } else {
                        String strSubstring = strGroup.substring(iIndexOf + 1, strGroup.length() - 1);
                        sb.append('(');
                        sb.append(strSubstring);
                        sb.append(')');
                        this.variableNames.add(strGroup.substring(1, iIndexOf));
                    }
                }
                iEnd = matcher.end();
            }
            if (iEnd == 0) {
                this.exactMatch = true;
                this.pattern = null;
            } else {
                this.exactMatch = false;
                sb.append(quote(str, iEnd, str.length()));
                this.pattern = this.caseSensitive ? Pattern.compile(sb.toString()) : Pattern.compile(sb.toString(), 2);
            }
        }

        private String quote(String str, int i2, int i3) {
            return i2 == i3 ? "" : Pattern.quote(str.substring(i2, i3));
        }

        public boolean matchStrings(String str, Map<String, String> map) {
            if (this.exactMatch) {
                return this.caseSensitive ? this.rawPattern.equals(str) : this.rawPattern.equalsIgnoreCase(str);
            }
            Pattern pattern = this.pattern;
            if (pattern == null) {
                return false;
            }
            Matcher matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                return false;
            }
            if (map != null) {
                if (this.variableNames.size() != matcher.groupCount()) {
                    throw new IllegalArgumentException("The number of capturing groups in the pattern segment " + this.pattern + " does not match the number of URI template variables it defines, which can occur if capturing groups are used in a URI template regex. Use non-capturing groups instead.");
                }
                for (int i2 = 1; i2 <= matcher.groupCount(); i2++) {
                    String str2 = this.variableNames.get(i2 - 1);
                    if (str2.startsWith("*")) {
                        throw new IllegalArgumentException("Capturing patterns (" + str2 + ") are not supported by the AntPathMatcher. Use the PathPatternParser instead.");
                    }
                    map.put(str2, matcher.group(i2));
                }
            }
            return true;
        }
    }

    public static class AntPatternComparator implements Comparator<String> {
        private final String path;

        public static class PatternInfo {
            private boolean catchAllPattern;
            private int doubleWildcards;
            private Integer length;
            private final String pattern;
            private boolean prefixPattern;
            private int singleWildcards;
            private int uriVars;

            public PatternInfo(String str) {
                this.pattern = str;
                if (str != null) {
                    initCounters();
                    boolean zEquals = str.equals("/**");
                    this.catchAllPattern = zEquals;
                    this.prefixPattern = !zEquals && str.endsWith("/**");
                }
                if (this.uriVars == 0) {
                    this.length = Integer.valueOf(str != null ? str.length() : 0);
                }
            }

            public int getDoubleWildcards() {
                return this.doubleWildcards;
            }

            public int getLength() {
                if (this.length == null) {
                    this.length = Integer.valueOf(this.pattern != null ? AntPathMatcher.VARIABLE_PATTERN.matcher(this.pattern).replaceAll(DictionaryFactory.SHARP).length() : 0);
                }
                return this.length.intValue();
            }

            public int getSingleWildcards() {
                return this.singleWildcards;
            }

            public int getTotalCount() {
                return this.uriVars + this.singleWildcards + (this.doubleWildcards * 2);
            }

            public int getUriVars() {
                return this.uriVars;
            }

            public void initCounters() {
                if (this.pattern != null) {
                    int i2 = 0;
                    while (i2 < this.pattern.length()) {
                        if (this.pattern.charAt(i2) == '{') {
                            this.uriVars++;
                        } else if (this.pattern.charAt(i2) == '*') {
                            int i3 = i2 + 1;
                            if (i3 >= this.pattern.length() || this.pattern.charAt(i3) != '*') {
                                if (i2 > 0 && !this.pattern.substring(i2 - 1).equals(".*")) {
                                    this.singleWildcards++;
                                }
                                i2 = i3;
                            } else {
                                this.doubleWildcards++;
                                i2 += 2;
                            }
                        }
                        i2++;
                    }
                }
            }

            public boolean isLeastSpecific() {
                return this.pattern == null || this.catchAllPattern;
            }

            public boolean isPrefixPattern() {
                return this.prefixPattern;
            }
        }

        public AntPatternComparator(String str) {
            this.path = str;
        }

        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            int length;
            int length2;
            PatternInfo patternInfo = new PatternInfo(str);
            PatternInfo patternInfo2 = new PatternInfo(str2);
            if (patternInfo.isLeastSpecific() && patternInfo2.isLeastSpecific()) {
                return 0;
            }
            if (patternInfo.isLeastSpecific()) {
                return 1;
            }
            if (patternInfo2.isLeastSpecific()) {
                return -1;
            }
            boolean zEquals = str.equals(this.path);
            boolean zEquals2 = str2.equals(this.path);
            if (zEquals && zEquals2) {
                return 0;
            }
            if (zEquals) {
                return -1;
            }
            if (zEquals2) {
                return 1;
            }
            if (patternInfo.isPrefixPattern() && patternInfo2.isPrefixPattern()) {
                length = patternInfo2.getLength();
                length2 = patternInfo.getLength();
            } else {
                if (patternInfo.isPrefixPattern() && patternInfo2.getDoubleWildcards() == 0) {
                    return 1;
                }
                if (patternInfo2.isPrefixPattern() && patternInfo.getDoubleWildcards() == 0) {
                    return -1;
                }
                if (patternInfo.getTotalCount() != patternInfo2.getTotalCount()) {
                    length = patternInfo.getTotalCount();
                    length2 = patternInfo2.getTotalCount();
                } else {
                    if (patternInfo.getLength() == patternInfo2.getLength()) {
                        if (patternInfo.getSingleWildcards() < patternInfo2.getSingleWildcards()) {
                            return -1;
                        }
                        if (patternInfo2.getSingleWildcards() < patternInfo.getSingleWildcards()) {
                            return 1;
                        }
                        if (patternInfo.getUriVars() < patternInfo2.getUriVars()) {
                            return -1;
                        }
                        return patternInfo2.getUriVars() < patternInfo.getUriVars() ? 1 : 0;
                    }
                    length = patternInfo2.getLength();
                    length2 = patternInfo.getLength();
                }
            }
            return length - length2;
        }
    }

    public static class PathSeparatorPatternCache {
        private final String endsOnDoubleWildCard;
        private final String endsOnWildCard;

        public PathSeparatorPatternCache(String str) {
            this.endsOnWildCard = str + "*";
            this.endsOnDoubleWildCard = str + "**";
        }

        public String getEndsOnDoubleWildCard() {
            return this.endsOnDoubleWildCard;
        }

        public String getEndsOnWildCard() {
            return this.endsOnWildCard;
        }
    }

    public AntPathMatcher() {
        this("/");
    }

    private String concat(String str, String str2) {
        boolean zEndsWith = str.endsWith(this.pathSeparator);
        boolean zStartsWith = str2.startsWith(this.pathSeparator);
        if (zEndsWith && zStartsWith) {
            return str + str2.substring(1);
        }
        if (zEndsWith || zStartsWith) {
            return str + str2;
        }
        return str + this.pathSeparator + str2;
    }

    private void deactivatePatternCache() {
        this.cachePatterns = Boolean.FALSE;
        this.tokenizedPatternCache.clear();
        this.stringMatcherCache.clear();
    }

    private boolean isPotentialMatch(String str, String[] strArr) {
        if (!this.trimTokens) {
            int i2 = 0;
            for (String str2 : strArr) {
                int iSkipSeparator = i2 + skipSeparator(str, i2, this.pathSeparator);
                int iSkipSegment = skipSegment(str, iSkipSeparator, str2);
                if (iSkipSegment < str2.length()) {
                    if (iSkipSegment <= 0) {
                        return str2.length() > 0 && isWildcardChar(str2.charAt(0));
                    }
                    return true;
                }
                i2 = iSkipSeparator + iSkipSegment;
            }
        }
        return true;
    }

    private boolean isWildcardChar(char c3) {
        for (char c4 : WILDCARD_CHARS) {
            if (c3 == c4) {
                return true;
            }
        }
        return false;
    }

    private boolean notMatchStrings(String str, String str2, Map<String, String> map) {
        return !getStringMatcher(str).matchStrings(str2, map);
    }

    private int skipSegment(String str, int i2, String str2) {
        int i3 = 0;
        for (int i4 = 0; i4 < str2.length(); i4++) {
            char cCharAt = str2.charAt(i4);
            if (isWildcardChar(cCharAt)) {
                return i3;
            }
            int i5 = i2 + i3;
            if (i5 >= str.length()) {
                return 0;
            }
            if (cCharAt == str.charAt(i5)) {
                i3++;
            }
        }
        return i3;
    }

    private int skipSeparator(String str, int i2, String str2) {
        int length = 0;
        while (str.startsWith(str2, i2 + length)) {
            length += str2.length();
        }
        return length;
    }

    public String combine(String str, String str2) {
        if (CharSequenceUtil.isEmpty(str) && CharSequenceUtil.isEmpty(str2)) {
            return "";
        }
        if (CharSequenceUtil.isEmpty(str)) {
            return str2;
        }
        if (CharSequenceUtil.isEmpty(str2)) {
            return str;
        }
        boolean z2 = true;
        boolean z3 = str.indexOf(123) != -1;
        if (!str.equals(str2) && !z3 && match(str, str2)) {
            return str2;
        }
        if (str.endsWith(this.pathSeparatorPatternCache.getEndsOnWildCard())) {
            return concat(str.substring(0, str.length() - 2), str2);
        }
        if (str.endsWith(this.pathSeparatorPatternCache.getEndsOnDoubleWildCard())) {
            return concat(str, str2);
        }
        int iIndexOf = str.indexOf("*.");
        if (z3 || iIndexOf == -1 || this.pathSeparator.equals(StrPool.DOT)) {
            return concat(str, str2);
        }
        String strSubstring = str.substring(iIndexOf + 1);
        int iIndexOf2 = str2.indexOf(46);
        String strSubstring2 = iIndexOf2 == -1 ? str2 : str2.substring(0, iIndexOf2);
        String strSubstring3 = iIndexOf2 != -1 ? str2.substring(iIndexOf2) : "";
        boolean z4 = strSubstring.equals(".*") || strSubstring.isEmpty();
        if (!strSubstring3.equals(".*") && !strSubstring3.isEmpty()) {
            z2 = false;
        }
        if (z4 || z2) {
            if (z4) {
                strSubstring = strSubstring3;
            }
            return strSubstring2 + strSubstring;
        }
        throw new IllegalArgumentException("Cannot combine patterns: " + str + " vs " + str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1, types: [int] */
    /* JADX WARN: Type inference failed for: r15v3 */
    public boolean doMatch(String str, String str2, boolean z2, Map<String, String> map) {
        int i2;
        int i3;
        boolean z3 = false;
        if (str2 == null || str2.startsWith(this.pathSeparator) != str.startsWith(this.pathSeparator)) {
            return false;
        }
        String[] strArr = tokenizePattern(str);
        if (z2 && this.caseSensitive && !isPotentialMatch(str2, strArr)) {
            return false;
        }
        String[] strArr2 = tokenizePath(str2);
        int i4 = 1;
        int length = strArr.length - 1;
        int length2 = strArr2.length - 1;
        int i5 = 0;
        int i6 = 0;
        while (i5 <= length && i6 <= length2) {
            String str3 = strArr[i5];
            if ("**".equals(str3)) {
                break;
            }
            if (notMatchStrings(str3, strArr2[i6], map)) {
                return false;
            }
            i5++;
            i6++;
        }
        if (i6 > length2) {
            if (i5 > length) {
                return str.endsWith(this.pathSeparator) == str2.endsWith(this.pathSeparator);
            }
            if (!z2) {
                return true;
            }
            if (i5 == length && strArr[i5].equals("*") && str2.endsWith(this.pathSeparator)) {
                return true;
            }
            while (i5 <= length) {
                if (!strArr[i5].equals("**")) {
                    return false;
                }
                i5++;
            }
            return true;
        }
        if (i5 > length) {
            return false;
        }
        if (!z2 && "**".equals(strArr[i5])) {
            return true;
        }
        while (i5 <= length && i6 <= length2) {
            String str4 = strArr[length];
            if (str4.equals("**")) {
                break;
            }
            if (notMatchStrings(str4, strArr2[length2], map)) {
                return false;
            }
            length--;
            length2--;
        }
        if (i6 > length2) {
            while (i5 <= length) {
                if (!strArr[i5].equals("**")) {
                    return false;
                }
                i5++;
            }
            return true;
        }
        while (i5 != length && i6 <= length2) {
            int i7 = i5 + 1;
            int i8 = i7;
            while (true) {
                if (i8 > length) {
                    i8 = -1;
                    break;
                }
                if (strArr[i8].equals("**")) {
                    break;
                }
                i8++;
            }
            if (i8 == i7) {
                i5 = i7;
            } else {
                int i9 = (i8 - i5) - i4;
                int i10 = (length2 - i6) + i4;
                ?? r15 = z3;
                while (true) {
                    if (r15 > i10 - i9) {
                        i2 = -1;
                        i3 = -1;
                        break;
                    }
                    for (int i11 = 0; i11 < i9; i11++) {
                        if (notMatchStrings(strArr[i5 + i11 + 1], strArr2[i6 + r15 + i11], map)) {
                            break;
                        }
                    }
                    i3 = i6 + r15;
                    i2 = -1;
                    break;
                    r15++;
                }
                if (i3 == i2) {
                    return false;
                }
                z3 = false;
                i6 = i3 + i9;
                i5 = i8;
                i4 = 1;
            }
        }
        while (i5 <= length) {
            if (!strArr[i5].equals("**")) {
                return z3;
            }
            i5++;
        }
        return true;
    }

    public String extractPathWithinPattern(String str, String str2) {
        String[] strArr = tokenizePath(str);
        String[] strArr2 = tokenizePath(str2);
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        boolean z2 = false;
        while (i2 < strArr.length) {
            String str3 = strArr[i2];
            if (str3.indexOf(42) > -1 || str3.indexOf(63) > -1) {
                while (i2 < strArr2.length) {
                    if (z2 || (i2 == 0 && !str.startsWith(this.pathSeparator))) {
                        sb.append(this.pathSeparator);
                    }
                    sb.append(strArr2[i2]);
                    i2++;
                    z2 = true;
                }
            }
            i2++;
        }
        return sb.toString();
    }

    public Map<String, String> extractUriTemplateVariables(String str, String str2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (doMatch(str, str2, true, linkedHashMap)) {
            return linkedHashMap;
        }
        throw new IllegalStateException("Pattern \"" + str + "\" is not a match for \"" + str2 + "\"");
    }

    public Comparator<String> getPatternComparator(String str) {
        return new AntPatternComparator(str);
    }

    public AntPathStringMatcher getStringMatcher(String str) {
        Boolean bool = this.cachePatterns;
        AntPathStringMatcher antPathStringMatcher = (bool == null || bool.booleanValue()) ? this.stringMatcherCache.get(str) : null;
        if (antPathStringMatcher == null) {
            antPathStringMatcher = new AntPathStringMatcher(str, this.caseSensitive);
            if (bool == null && this.stringMatcherCache.size() >= 65536) {
                deactivatePatternCache();
                return antPathStringMatcher;
            }
            if (bool == null || bool.booleanValue()) {
                this.stringMatcherCache.put(str, antPathStringMatcher);
            }
        }
        return antPathStringMatcher;
    }

    public boolean isPattern(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '*' || cCharAt == '?') {
                return true;
            }
            if (cCharAt == '{') {
                z2 = true;
            } else if (cCharAt == '}' && z2) {
                return true;
            }
        }
        return false;
    }

    public boolean match(String str, String str2) {
        return doMatch(str, str2, true, null);
    }

    public boolean matchStart(String str, String str2) {
        return doMatch(str, str2, false, null);
    }

    public AntPathMatcher setCachePatterns(boolean z2) {
        this.cachePatterns = Boolean.valueOf(z2);
        return this;
    }

    public AntPathMatcher setCaseSensitive(boolean z2) {
        this.caseSensitive = z2;
        return this;
    }

    public AntPathMatcher setPathSeparator(String str) {
        if (str == null) {
            str = "/";
        }
        this.pathSeparator = str;
        this.pathSeparatorPatternCache = new PathSeparatorPatternCache(str);
        return this;
    }

    public AntPathMatcher setTrimTokens(boolean z2) {
        this.trimTokens = z2;
        return this;
    }

    public String[] tokenizePath(String str) {
        return StrSplitter.splitToArray((CharSequence) str, this.pathSeparator, 0, this.trimTokens, true);
    }

    public String[] tokenizePattern(String str) {
        Boolean bool = this.cachePatterns;
        String[] strArr = (bool == null || bool.booleanValue()) ? this.tokenizedPatternCache.get(str) : null;
        if (strArr == null) {
            strArr = tokenizePath(str);
            if (bool == null && this.tokenizedPatternCache.size() >= 65536) {
                deactivatePatternCache();
                return strArr;
            }
            if (bool == null || bool.booleanValue()) {
                this.tokenizedPatternCache.put(str, strArr);
            }
        }
        return strArr;
    }

    public AntPathMatcher(String str) {
        this.caseSensitive = true;
        this.trimTokens = false;
        this.tokenizedPatternCache = new SafeConcurrentHashMap(256);
        this.stringMatcherCache = new SafeConcurrentHashMap(256);
        setPathSeparator(str == null ? "/" : str);
    }
}
