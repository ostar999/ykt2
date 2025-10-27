package cn.hutool.core.date.format;

import cn.hutool.core.map.SafeConcurrentHashMap;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class FastDateParser extends AbstractDateBasic implements DateParser {
    private static final long serialVersionUID = -3199383897950947498L;
    private final int century;
    private transient List<StrategyAndWidth> patterns;
    private final int startYear;
    static final Locale JAPANESE_IMPERIAL = new Locale("ja", "JP", "JP");
    private static final Comparator<String> LONGER_FIRST_LOWERCASE = Comparator.reverseOrder();
    private static final ConcurrentMap<Locale, Strategy>[] CACHES = new ConcurrentMap[17];
    private static final Strategy ABBREVIATED_YEAR_STRATEGY = new NumberStrategy(1) { // from class: cn.hutool.core.date.format.FastDateParser.1
        @Override // cn.hutool.core.date.format.FastDateParser.NumberStrategy
        public int modify(FastDateParser fastDateParser, int i2) {
            return i2 < 100 ? fastDateParser.adjustYear(i2) : i2;
        }
    };
    private static final Strategy NUMBER_MONTH_STRATEGY = new NumberStrategy(2) { // from class: cn.hutool.core.date.format.FastDateParser.2
        @Override // cn.hutool.core.date.format.FastDateParser.NumberStrategy
        public int modify(FastDateParser fastDateParser, int i2) {
            return i2 - 1;
        }
    };
    private static final Strategy LITERAL_YEAR_STRATEGY = new NumberStrategy(1);
    private static final Strategy WEEK_OF_YEAR_STRATEGY = new NumberStrategy(3);
    private static final Strategy WEEK_OF_MONTH_STRATEGY = new NumberStrategy(4);
    private static final Strategy DAY_OF_YEAR_STRATEGY = new NumberStrategy(6);
    private static final Strategy DAY_OF_MONTH_STRATEGY = new NumberStrategy(5);
    private static final Strategy DAY_OF_WEEK_STRATEGY = new NumberStrategy(7) { // from class: cn.hutool.core.date.format.FastDateParser.3
        @Override // cn.hutool.core.date.format.FastDateParser.NumberStrategy
        public int modify(FastDateParser fastDateParser, int i2) {
            if (i2 != 7) {
                return 1 + i2;
            }
            return 1;
        }
    };
    private static final Strategy DAY_OF_WEEK_IN_MONTH_STRATEGY = new NumberStrategy(8);
    private static final Strategy HOUR_OF_DAY_STRATEGY = new NumberStrategy(11);
    private static final Strategy HOUR24_OF_DAY_STRATEGY = new NumberStrategy(11) { // from class: cn.hutool.core.date.format.FastDateParser.4
        @Override // cn.hutool.core.date.format.FastDateParser.NumberStrategy
        public int modify(FastDateParser fastDateParser, int i2) {
            if (i2 == 24) {
                return 0;
            }
            return i2;
        }
    };
    private static final Strategy HOUR12_STRATEGY = new NumberStrategy(10) { // from class: cn.hutool.core.date.format.FastDateParser.5
        @Override // cn.hutool.core.date.format.FastDateParser.NumberStrategy
        public int modify(FastDateParser fastDateParser, int i2) {
            if (i2 == 12) {
                return 0;
            }
            return i2;
        }
    };
    private static final Strategy HOUR_STRATEGY = new NumberStrategy(10);
    private static final Strategy MINUTE_STRATEGY = new NumberStrategy(12);
    private static final Strategy SECOND_STRATEGY = new NumberStrategy(13);
    private static final Strategy MILLISECOND_STRATEGY = new NumberStrategy(14);

    public static class CaseInsensitiveTextStrategy extends PatternStrategy {
        private final int field;
        private final Map<String, Integer> lKeyValues;
        final Locale locale;

        public CaseInsensitiveTextStrategy(int i2, Calendar calendar, Locale locale) {
            super();
            this.field = i2;
            this.locale = locale;
            StringBuilder sb = new StringBuilder();
            sb.append("((?iu)");
            this.lKeyValues = FastDateParser.appendDisplayNames(calendar, locale, i2, sb);
            sb.setLength(sb.length() - 1);
            sb.append(")");
            createPattern(sb);
        }

        @Override // cn.hutool.core.date.format.FastDateParser.PatternStrategy
        public void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
            calendar.set(this.field, this.lKeyValues.get(str.toLowerCase(this.locale)).intValue());
        }
    }

    public static class CopyQuotedStrategy extends Strategy {
        private final String formatField;

        public CopyQuotedStrategy(String str) {
            super();
            this.formatField = str;
        }

        @Override // cn.hutool.core.date.format.FastDateParser.Strategy
        public boolean isNumber() {
            return false;
        }

        @Override // cn.hutool.core.date.format.FastDateParser.Strategy
        public boolean parse(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i2) {
            for (int i3 = 0; i3 < this.formatField.length(); i3++) {
                int index = parsePosition.getIndex() + i3;
                if (index == str.length()) {
                    parsePosition.setErrorIndex(index);
                    return false;
                }
                if (this.formatField.charAt(i3) != str.charAt(index)) {
                    parsePosition.setErrorIndex(index);
                    return false;
                }
            }
            parsePosition.setIndex(this.formatField.length() + parsePosition.getIndex());
            return true;
        }
    }

    public static class ISO8601TimeZoneStrategy extends PatternStrategy {
        private static final Strategy ISO_8601_1_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}))");
        private static final Strategy ISO_8601_2_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}\\d{2}))");
        private static final Strategy ISO_8601_3_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}(?::)\\d{2}))");

        public ISO8601TimeZoneStrategy(String str) {
            super();
            createPattern(str);
        }

        public static Strategy getStrategy(int i2) {
            if (i2 == 1) {
                return ISO_8601_1_STRATEGY;
            }
            if (i2 == 2) {
                return ISO_8601_2_STRATEGY;
            }
            if (i2 == 3) {
                return ISO_8601_3_STRATEGY;
            }
            throw new IllegalArgumentException("invalid number of X");
        }

        @Override // cn.hutool.core.date.format.FastDateParser.PatternStrategy
        public void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
            if (Objects.equals(str, "Z")) {
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
                return;
            }
            calendar.setTimeZone(TimeZone.getTimeZone("GMT" + str));
        }
    }

    public static class NumberStrategy extends Strategy {
        private final int field;

        public NumberStrategy(int i2) {
            super();
            this.field = i2;
        }

        @Override // cn.hutool.core.date.format.FastDateParser.Strategy
        public boolean isNumber() {
            return true;
        }

        public int modify(FastDateParser fastDateParser, int i2) {
            return i2;
        }

        @Override // cn.hutool.core.date.format.FastDateParser.Strategy
        public boolean parse(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i2) throws NumberFormatException {
            int index = parsePosition.getIndex();
            int length = str.length();
            if (i2 == 0) {
                while (index < length && Character.isWhitespace(str.charAt(index))) {
                    index++;
                }
                parsePosition.setIndex(index);
            } else {
                int i3 = i2 + index;
                if (length > i3) {
                    length = i3;
                }
            }
            while (index < length && Character.isDigit(str.charAt(index))) {
                index++;
            }
            if (parsePosition.getIndex() == index) {
                parsePosition.setErrorIndex(index);
                return false;
            }
            int i4 = Integer.parseInt(str.substring(parsePosition.getIndex(), index));
            parsePosition.setIndex(index);
            calendar.set(this.field, modify(fastDateParser, i4));
            return true;
        }
    }

    public static abstract class PatternStrategy extends Strategy {
        private Pattern pattern;

        private PatternStrategy() {
            super();
        }

        public void createPattern(StringBuilder sb) {
            createPattern(sb.toString());
        }

        @Override // cn.hutool.core.date.format.FastDateParser.Strategy
        public boolean isNumber() {
            return false;
        }

        @Override // cn.hutool.core.date.format.FastDateParser.Strategy
        public boolean parse(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i2) {
            Matcher matcher = this.pattern.matcher(str.substring(parsePosition.getIndex()));
            if (!matcher.lookingAt()) {
                parsePosition.setErrorIndex(parsePosition.getIndex());
                return false;
            }
            parsePosition.setIndex(parsePosition.getIndex() + matcher.end(1));
            setCalendar(fastDateParser, calendar, matcher.group(1));
            return true;
        }

        public abstract void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str);

        public void createPattern(String str) {
            this.pattern = Pattern.compile(str);
        }
    }

    public static abstract class Strategy {
        private Strategy() {
        }

        public boolean isNumber() {
            return false;
        }

        public abstract boolean parse(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i2);
    }

    public static class StrategyAndWidth {
        final Strategy strategy;
        final int width;

        public StrategyAndWidth(Strategy strategy, int i2) {
            this.strategy = strategy;
            this.width = i2;
        }

        public int getMaxWidth(ListIterator<StrategyAndWidth> listIterator) {
            if (!this.strategy.isNumber() || !listIterator.hasNext()) {
                return 0;
            }
            Strategy strategy = listIterator.next().strategy;
            listIterator.previous();
            if (strategy.isNumber()) {
                return this.width;
            }
            return 0;
        }
    }

    public class StrategyParser {
        private int currentIdx;
        private final Calendar definingCalendar;

        public StrategyParser(Calendar calendar) {
            this.definingCalendar = calendar;
        }

        private StrategyAndWidth letterPattern(char c3) {
            int i2 = this.currentIdx;
            do {
                int i3 = this.currentIdx + 1;
                this.currentIdx = i3;
                if (i3 >= FastDateParser.this.pattern.length()) {
                    break;
                }
            } while (FastDateParser.this.pattern.charAt(this.currentIdx) == c3);
            int i4 = this.currentIdx - i2;
            return new StrategyAndWidth(FastDateParser.this.getStrategy(c3, i4, this.definingCalendar), i4);
        }

        private StrategyAndWidth literal() {
            StringBuilder sb = new StringBuilder();
            boolean z2 = false;
            while (this.currentIdx < FastDateParser.this.pattern.length()) {
                char cCharAt = FastDateParser.this.pattern.charAt(this.currentIdx);
                if (!z2 && FastDateParser.isFormatLetter(cCharAt)) {
                    break;
                }
                if (cCharAt == '\'') {
                    int i2 = this.currentIdx + 1;
                    this.currentIdx = i2;
                    if (i2 == FastDateParser.this.pattern.length() || FastDateParser.this.pattern.charAt(this.currentIdx) != '\'') {
                        z2 = !z2;
                    }
                }
                this.currentIdx++;
                sb.append(cCharAt);
            }
            if (z2) {
                throw new IllegalArgumentException("Unterminated quote");
            }
            String string = sb.toString();
            return new StrategyAndWidth(new CopyQuotedStrategy(string), string.length());
        }

        public StrategyAndWidth getNextStrategy() {
            if (this.currentIdx >= FastDateParser.this.pattern.length()) {
                return null;
            }
            char cCharAt = FastDateParser.this.pattern.charAt(this.currentIdx);
            return FastDateParser.isFormatLetter(cCharAt) ? letterPattern(cCharAt) : literal();
        }
    }

    public static class TimeZoneStrategy extends PatternStrategy {
        private static final String GMT_OPTION = "GMT[+-]\\d{1,2}:\\d{2}";
        private static final int ID = 0;
        private static final String RFC_822_TIME_ZONE = "[+-]\\d{4}";
        private static final String UTC_TIME_ZONE_WITH_OFFSET = "[+-]\\d{2}:\\d{2}";
        private final Locale locale;
        private final Map<String, TzInfo> tzNames;

        public static class TzInfo {
            int dstOffset;
            TimeZone zone;

            public TzInfo(TimeZone timeZone, boolean z2) {
                this.zone = timeZone;
                this.dstOffset = z2 ? timeZone.getDSTSavings() : 0;
            }
        }

        public TimeZoneStrategy(Locale locale) {
            super();
            this.tzNames = new HashMap();
            this.locale = locale;
            StringBuilder sb = new StringBuilder();
            sb.append("((?iu)[+-]\\d{4}|[+-]\\d{2}:\\d{2}|GMT[+-]\\d{1,2}:\\d{2}");
            TreeSet<String> treeSet = new TreeSet(FastDateParser.LONGER_FIRST_LOWERCASE);
            for (String[] strArr : DateFormatSymbols.getInstance(locale).getZoneStrings()) {
                String str = strArr[0];
                if (!"GMT".equalsIgnoreCase(str)) {
                    TimeZone timeZone = TimeZone.getTimeZone(str);
                    TzInfo tzInfo = new TzInfo(timeZone, false);
                    TzInfo tzInfo2 = tzInfo;
                    for (int i2 = 1; i2 < strArr.length; i2++) {
                        if (i2 == 3) {
                            tzInfo2 = new TzInfo(timeZone, true);
                        } else if (i2 == 5) {
                            tzInfo2 = tzInfo;
                        }
                        String str2 = strArr[i2];
                        if (str2 != null) {
                            String lowerCase = str2.toLowerCase(locale);
                            if (treeSet.add(lowerCase)) {
                                this.tzNames.put(lowerCase, tzInfo2);
                            }
                        }
                    }
                }
            }
            for (String str3 : treeSet) {
                sb.append('|');
                FastDateParser.simpleQuote(sb, str3);
            }
            sb.append(")");
            createPattern(sb);
        }

        @Override // cn.hutool.core.date.format.FastDateParser.PatternStrategy
        public void setCalendar(FastDateParser fastDateParser, Calendar calendar, String str) {
            if (str.charAt(0) == '+' || str.charAt(0) == '-') {
                calendar.setTimeZone(TimeZone.getTimeZone("GMT" + str));
                return;
            }
            if (str.regionMatches(true, 0, "GMT", 0, 3)) {
                calendar.setTimeZone(TimeZone.getTimeZone(str.toUpperCase()));
            } else {
                calendar.set(16, this.tzNames.get(str.toLowerCase(this.locale)).dstOffset);
                calendar.set(15, fastDateParser.getTimeZone().getRawOffset());
            }
        }
    }

    public FastDateParser(String str, TimeZone timeZone, Locale locale) {
        this(str, timeZone, locale, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int adjustYear(int i2) {
        int i3 = this.century + i2;
        return i2 >= this.startYear ? i3 : i3 + 100;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, Integer> appendDisplayNames(Calendar calendar, Locale locale, int i2, StringBuilder sb) {
        HashMap map = new HashMap();
        Map<String, Integer> displayNames = calendar.getDisplayNames(i2, 0, locale);
        TreeSet treeSet = new TreeSet(LONGER_FIRST_LOWERCASE);
        for (Map.Entry<String, Integer> entry : displayNames.entrySet()) {
            String lowerCase = entry.getKey().toLowerCase(locale);
            if (treeSet.add(lowerCase)) {
                map.put(lowerCase, entry.getValue());
            }
        }
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            simpleQuote(sb, (String) it.next()).append('|');
        }
        return map;
    }

    private static ConcurrentMap<Locale, Strategy> getCache(int i2) {
        ConcurrentMap<Locale, Strategy> concurrentMap;
        ConcurrentMap<Locale, Strategy>[] concurrentMapArr = CACHES;
        synchronized (concurrentMapArr) {
            if (concurrentMapArr[i2] == null) {
                concurrentMapArr[i2] = new SafeConcurrentHashMap(3);
            }
            concurrentMap = concurrentMapArr[i2];
        }
        return concurrentMap;
    }

    private Strategy getLocaleSpecificStrategy(int i2, Calendar calendar) {
        ConcurrentMap<Locale, Strategy> cache = getCache(i2);
        Strategy timeZoneStrategy = cache.get(this.locale);
        if (timeZoneStrategy == null) {
            timeZoneStrategy = i2 == 15 ? new TimeZoneStrategy(this.locale) : new CaseInsensitiveTextStrategy(i2, calendar, this.locale);
            Strategy strategyPutIfAbsent = cache.putIfAbsent(this.locale, timeZoneStrategy);
            if (strategyPutIfAbsent != null) {
                return strategyPutIfAbsent;
            }
        }
        return timeZoneStrategy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Strategy getStrategy(char c3, int i2, Calendar calendar) {
        if (c3 != 'y') {
            if (c3 != 'z') {
                switch (c3) {
                    case 'D':
                        return DAY_OF_YEAR_STRATEGY;
                    case 'E':
                        return getLocaleSpecificStrategy(7, calendar);
                    case 'F':
                        return DAY_OF_WEEK_IN_MONTH_STRATEGY;
                    case 'G':
                        return getLocaleSpecificStrategy(0, calendar);
                    case 'H':
                        return HOUR_OF_DAY_STRATEGY;
                    default:
                        switch (c3) {
                            case 'K':
                                return HOUR_STRATEGY;
                            case 'M':
                                return i2 >= 3 ? getLocaleSpecificStrategy(2, calendar) : NUMBER_MONTH_STRATEGY;
                            case 'S':
                                return MILLISECOND_STRATEGY;
                            case 'a':
                                return getLocaleSpecificStrategy(9, calendar);
                            case 'd':
                                return DAY_OF_MONTH_STRATEGY;
                            case 'h':
                                return HOUR12_STRATEGY;
                            case 'k':
                                return HOUR24_OF_DAY_STRATEGY;
                            case 'm':
                                return MINUTE_STRATEGY;
                            case 's':
                                return SECOND_STRATEGY;
                            case 'u':
                                return DAY_OF_WEEK_STRATEGY;
                            case 'w':
                                return WEEK_OF_YEAR_STRATEGY;
                            default:
                                switch (c3) {
                                    case 'W':
                                        return WEEK_OF_MONTH_STRATEGY;
                                    case 'X':
                                        return ISO8601TimeZoneStrategy.getStrategy(i2);
                                    case 'Y':
                                        break;
                                    case 'Z':
                                        if (i2 == 2) {
                                            return ISO8601TimeZoneStrategy.ISO_8601_3_STRATEGY;
                                        }
                                        break;
                                    default:
                                        throw new IllegalArgumentException("Format '" + c3 + "' not supported");
                                }
                        }
                }
            }
            return getLocaleSpecificStrategy(15, calendar);
        }
        return i2 > 2 ? LITERAL_YEAR_STRATEGY : ABBREVIATED_YEAR_STRATEGY;
    }

    private void init(Calendar calendar) {
        this.patterns = new ArrayList();
        StrategyParser strategyParser = new StrategyParser(calendar);
        while (true) {
            StrategyAndWidth nextStrategy = strategyParser.getNextStrategy();
            if (nextStrategy == null) {
                return;
            } else {
                this.patterns.add(nextStrategy);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFormatLetter(char c3) {
        return (c3 >= 'A' && c3 <= 'Z') || (c3 >= 'a' && c3 <= 'z');
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        init(Calendar.getInstance(this.timeZone, this.locale));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:22:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.StringBuilder simpleQuote(java.lang.StringBuilder r4, java.lang.String r5) {
        /*
            r0 = 0
        L1:
            int r1 = r5.length()
            if (r0 >= r1) goto L38
            char r1 = r5.charAt(r0)
            r2 = 36
            r3 = 92
            if (r1 == r2) goto L2f
            r2 = 46
            if (r1 == r2) goto L2f
            r2 = 63
            if (r1 == r2) goto L2f
            r2 = 94
            if (r1 == r2) goto L2f
            r2 = 91
            if (r1 == r2) goto L2f
            if (r1 == r3) goto L2f
            r2 = 123(0x7b, float:1.72E-43)
            if (r1 == r2) goto L2f
            r2 = 124(0x7c, float:1.74E-43)
            if (r1 == r2) goto L2f
            switch(r1) {
                case 40: goto L2f;
                case 41: goto L2f;
                case 42: goto L2f;
                case 43: goto L2f;
                default: goto L2e;
            }
        L2e:
            goto L32
        L2f:
            r4.append(r3)
        L32:
            r4.append(r1)
            int r0 = r0 + 1
            goto L1
        L38:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.date.format.FastDateParser.simpleQuote(java.lang.StringBuilder, java.lang.String):java.lang.StringBuilder");
    }

    @Override // cn.hutool.core.date.format.DateParser
    public Date parse(String str) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Date date = parse(str, parsePosition);
        if (date != null) {
            return date;
        }
        if (!this.locale.equals(JAPANESE_IMPERIAL)) {
            throw new ParseException("Unparseable date: " + str, parsePosition.getErrorIndex());
        }
        throw new ParseException("(The " + this.locale + " locale does not support dates before 1868 AD)\nUnparseable date: \"" + str, parsePosition.getErrorIndex());
    }

    @Override // cn.hutool.core.date.format.DateParser
    public /* synthetic */ Object parseObject(String str) {
        return a.a(this, str);
    }

    @Override // cn.hutool.core.date.format.DateParser
    public /* synthetic */ Object parseObject(String str, ParsePosition parsePosition) {
        return a.b(this, str, parsePosition);
    }

    public FastDateParser(String str, TimeZone timeZone, Locale locale, Date date) {
        int i2;
        super(str, timeZone, locale);
        Calendar calendar = Calendar.getInstance(timeZone, locale);
        if (date != null) {
            calendar.setTime(date);
            i2 = calendar.get(1);
        } else if (locale.equals(JAPANESE_IMPERIAL)) {
            i2 = 0;
        } else {
            calendar.setTime(new Date());
            i2 = calendar.get(1) - 80;
        }
        int i3 = (i2 / 100) * 100;
        this.century = i3;
        this.startYear = i2 - i3;
        init(calendar);
    }

    @Override // cn.hutool.core.date.format.DateParser
    public Date parse(String str, ParsePosition parsePosition) {
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        calendar.clear();
        if (parse(str, parsePosition, calendar)) {
            return calendar.getTime();
        }
        return null;
    }

    @Override // cn.hutool.core.date.format.DateParser
    public boolean parse(String str, ParsePosition parsePosition, Calendar calendar) {
        ListIterator<StrategyAndWidth> listIterator = this.patterns.listIterator();
        while (listIterator.hasNext()) {
            StrategyAndWidth next = listIterator.next();
            if (!next.strategy.parse(this, calendar, str, parsePosition, next.getMaxWidth(listIterator))) {
                return false;
            }
        }
        return true;
    }
}
