package cn.hutool.core.date.format;

import cn.hutool.core.date.DateException;
import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.text.CharPool;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
public class FastDatePrinter extends AbstractDateBasic implements DatePrinter {
    private static final ConcurrentMap<TimeZoneDisplayKey, String> C_TIME_ZONE_DISPLAY_CACHE = new SafeConcurrentHashMap(7);
    private static final int MAX_DIGITS = 10;
    private static final long serialVersionUID = -6305750172255764887L;
    private transient int mMaxLengthEstimate;
    private transient Rule[] rules;

    public static class CharacterLiteral implements Rule {
        private final char mValue;

        public CharacterLiteral(char c3) {
            this.mValue = c3;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.mValue);
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return 1;
        }
    }

    public static class Iso8601_Rule implements Rule {
        final int length;
        static final Iso8601_Rule ISO8601_HOURS = new Iso8601_Rule(3);
        static final Iso8601_Rule ISO8601_HOURS_MINUTES = new Iso8601_Rule(5);
        static final Iso8601_Rule ISO8601_HOURS_COLON_MINUTES = new Iso8601_Rule(6);

        public Iso8601_Rule(int i2) {
            this.length = i2;
        }

        public static Iso8601_Rule getRule(int i2) {
            if (i2 == 1) {
                return ISO8601_HOURS;
            }
            if (i2 == 2) {
                return ISO8601_HOURS_MINUTES;
            }
            if (i2 == 3) {
                return ISO8601_HOURS_COLON_MINUTES;
            }
            throw new IllegalArgumentException("invalid number of X");
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int i2 = calendar.get(15) + calendar.get(16);
            if (i2 == 0) {
                appendable.append("Z");
                return;
            }
            if (i2 < 0) {
                appendable.append(CharPool.DASHED);
                i2 = -i2;
            } else {
                appendable.append('+');
            }
            int i3 = i2 / 3600000;
            FastDatePrinter.appendDigits(appendable, i3);
            int i4 = this.length;
            if (i4 < 5) {
                return;
            }
            if (i4 == 6) {
                appendable.append(':');
            }
            FastDatePrinter.appendDigits(appendable, (i2 / 60000) - (i3 * 60));
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return this.length;
        }
    }

    public interface NumberRule extends Rule {
        void appendTo(Appendable appendable, int i2) throws IOException;
    }

    public static class PaddedNumberField implements NumberRule {
        private final int mField;
        private final int mSize;

        public PaddedNumberField(int i2, int i3) {
            if (i3 < 3) {
                throw new IllegalArgumentException();
            }
            this.mField = i2;
            this.mSize = i3;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(this.mField));
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mSize;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i2) throws IOException {
            FastDatePrinter.appendFullDigits(appendable, i2, this.mSize);
        }
    }

    public interface Rule {
        void appendTo(Appendable appendable, Calendar calendar) throws IOException;

        int estimateLength();
    }

    public static class StringLiteral implements Rule {
        private final String mValue;

        public StringLiteral(String str) {
            this.mValue = str;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.mValue);
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mValue.length();
        }
    }

    public static class TextField implements Rule {
        private final int mField;
        private final String[] mValues;

        public TextField(int i2, String[] strArr) {
            this.mField = i2;
            this.mValues = strArr;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.mValues[calendar.get(this.mField)]);
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            int length = this.mValues.length;
            int i2 = 0;
            while (true) {
                length--;
                if (length < 0) {
                    return i2;
                }
                int length2 = this.mValues[length].length();
                if (length2 > i2) {
                    i2 = length2;
                }
            }
        }
    }

    public static class TimeZoneDisplayKey {
        private final Locale mLocale;
        private final int mStyle;
        private final TimeZone mTimeZone;

        public TimeZoneDisplayKey(TimeZone timeZone, boolean z2, int i2, Locale locale) {
            this.mTimeZone = timeZone;
            if (z2) {
                this.mStyle = Integer.MIN_VALUE | i2;
            } else {
                this.mStyle = i2;
            }
            this.mLocale = locale;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TimeZoneDisplayKey)) {
                return false;
            }
            TimeZoneDisplayKey timeZoneDisplayKey = (TimeZoneDisplayKey) obj;
            return this.mTimeZone.equals(timeZoneDisplayKey.mTimeZone) && this.mStyle == timeZoneDisplayKey.mStyle && this.mLocale.equals(timeZoneDisplayKey.mLocale);
        }

        public int hashCode() {
            return (((this.mStyle * 31) + this.mLocale.hashCode()) * 31) + this.mTimeZone.hashCode();
        }
    }

    public static class TimeZoneNameRule implements Rule {
        private final String mDaylight;
        private final Locale mLocale;
        private final String mStandard;
        private final int mStyle;

        public TimeZoneNameRule(TimeZone timeZone, Locale locale, int i2) {
            this.mLocale = locale;
            this.mStyle = i2;
            this.mStandard = FastDatePrinter.getTimeZoneDisplay(timeZone, false, i2, locale);
            this.mDaylight = FastDatePrinter.getTimeZoneDisplay(timeZone, true, i2, locale);
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            TimeZone timeZone = calendar.getTimeZone();
            if (calendar.get(16) != 0) {
                appendable.append(FastDatePrinter.getTimeZoneDisplay(timeZone, true, this.mStyle, this.mLocale));
            } else {
                appendable.append(FastDatePrinter.getTimeZoneDisplay(timeZone, false, this.mStyle, this.mLocale));
            }
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return Math.max(this.mStandard.length(), this.mDaylight.length());
        }
    }

    public static class TimeZoneNumberRule implements Rule {
        static final TimeZoneNumberRule INSTANCE_COLON = new TimeZoneNumberRule(true);
        static final TimeZoneNumberRule INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
        final boolean mColon;

        public TimeZoneNumberRule(boolean z2) {
            this.mColon = z2;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int i2 = calendar.get(15) + calendar.get(16);
            if (i2 < 0) {
                appendable.append(CharPool.DASHED);
                i2 = -i2;
            } else {
                appendable.append('+');
            }
            int i3 = i2 / 3600000;
            FastDatePrinter.appendDigits(appendable, i3);
            if (this.mColon) {
                appendable.append(':');
            }
            FastDatePrinter.appendDigits(appendable, (i2 / 60000) - (i3 * 60));
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return 5;
        }
    }

    public static class TwoDigitMonthField implements NumberRule {
        static final TwoDigitMonthField INSTANCE = new TwoDigitMonthField();

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(2) + 1);
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return 2;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i2) throws IOException {
            FastDatePrinter.appendDigits(appendable, i2);
        }
    }

    public static class TwoDigitNumberField implements NumberRule {
        private final int mField;

        public TwoDigitNumberField(int i2) {
            this.mField = i2;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(this.mField));
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return 2;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i2) throws IOException {
            if (i2 < 100) {
                FastDatePrinter.appendDigits(appendable, i2);
            } else {
                FastDatePrinter.appendFullDigits(appendable, i2, 2);
            }
        }
    }

    public static class TwoDigitYearField implements NumberRule {
        static final TwoDigitYearField INSTANCE = new TwoDigitYearField();

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(1) % 100);
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return 2;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i2) throws IOException {
            FastDatePrinter.appendDigits(appendable, i2);
        }
    }

    public static class UnpaddedMonthField implements NumberRule {
        static final UnpaddedMonthField INSTANCE = new UnpaddedMonthField();

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(2) + 1);
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return 2;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i2) throws IOException {
            if (i2 < 10) {
                appendable.append((char) (i2 + 48));
            } else {
                FastDatePrinter.appendDigits(appendable, i2);
            }
        }
    }

    public static class UnpaddedNumberField implements NumberRule {
        private final int mField;

        public UnpaddedNumberField(int i2) {
            this.mField = i2;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(this.mField));
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return 4;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i2) throws IOException {
            if (i2 < 10) {
                appendable.append((char) (i2 + 48));
            } else if (i2 < 100) {
                FastDatePrinter.appendDigits(appendable, i2);
            } else {
                FastDatePrinter.appendFullDigits(appendable, i2, 1);
            }
        }
    }

    public static class WeekYear implements NumberRule {
        private final NumberRule mRule;

        public WeekYear(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            this.mRule.appendTo(appendable, calendar.getWeekYear());
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.NumberRule
        public void appendTo(Appendable appendable, int i2) throws IOException {
            this.mRule.appendTo(appendable, i2);
        }
    }

    public FastDatePrinter(String str, TimeZone timeZone, Locale locale) {
        super(str, timeZone, locale);
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendDigits(Appendable appendable, int i2) throws IOException {
        appendable.append((char) ((i2 / 10) + 48));
        appendable.append((char) ((i2 % 10) + 48));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendFullDigits(Appendable appendable, int i2, int i3) throws IOException {
        if (i2 < 10000) {
            int i4 = i2 < 1000 ? i2 < 100 ? i2 < 10 ? 1 : 2 : 3 : 4;
            for (int i5 = i3 - i4; i5 > 0; i5--) {
                appendable.append('0');
            }
            if (i4 != 1) {
                if (i4 != 2) {
                    if (i4 != 3) {
                        if (i4 != 4) {
                            return;
                        }
                        appendable.append((char) ((i2 / 1000) + 48));
                        i2 %= 1000;
                    }
                    if (i2 >= 100) {
                        appendable.append((char) ((i2 / 100) + 48));
                        i2 %= 100;
                    } else {
                        appendable.append('0');
                    }
                }
                if (i2 >= 10) {
                    appendable.append((char) ((i2 / 10) + 48));
                    i2 %= 10;
                } else {
                    appendable.append('0');
                }
            }
            appendable.append((char) (i2 + 48));
            return;
        }
        char[] cArr = new char[10];
        int i6 = 0;
        while (i2 != 0) {
            cArr[i6] = (char) ((i2 % 10) + 48);
            i2 /= 10;
            i6++;
        }
        while (i6 < i3) {
            appendable.append('0');
            i3--;
        }
        while (true) {
            i6--;
            if (i6 < 0) {
                return;
            } else {
                appendable.append(cArr[i6]);
            }
        }
    }

    private <B extends Appendable> B applyRules(Calendar calendar, B b3) {
        try {
            for (Rule rule : this.rules) {
                rule.appendTo(b3, calendar);
            }
            return b3;
        } catch (IOException e2) {
            throw new DateException(e2);
        }
    }

    private String applyRulesToString(Calendar calendar) {
        return ((StringBuilder) applyRules(calendar, new StringBuilder(this.mMaxLengthEstimate))).toString();
    }

    public static String getTimeZoneDisplay(TimeZone timeZone, boolean z2, int i2, Locale locale) {
        TimeZoneDisplayKey timeZoneDisplayKey = new TimeZoneDisplayKey(timeZone, z2, i2, locale);
        ConcurrentMap<TimeZoneDisplayKey, String> concurrentMap = C_TIME_ZONE_DISPLAY_CACHE;
        String str = concurrentMap.get(timeZoneDisplayKey);
        if (str != null) {
            return str;
        }
        String displayName = timeZone.getDisplayName(z2, i2, locale);
        String strPutIfAbsent = concurrentMap.putIfAbsent(timeZoneDisplayKey, displayName);
        return strPutIfAbsent != null ? strPutIfAbsent : displayName;
    }

    private void init() {
        int iEstimateLength = 0;
        Rule[] ruleArr = (Rule[]) parsePattern().toArray(new Rule[0]);
        this.rules = ruleArr;
        int length = ruleArr.length;
        while (true) {
            length--;
            if (length < 0) {
                this.mMaxLengthEstimate = iEstimateLength;
                return;
            }
            iEstimateLength += this.rules[length].estimateLength();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        init();
    }

    public String format(Object obj) {
        if (obj instanceof Date) {
            return format((Date) obj);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown class: ");
        sb.append(obj == null ? "<null>" : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public int getMaxLengthEstimate() {
        return this.mMaxLengthEstimate;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0082  */
    /* JADX WARN: Type inference failed for: r9v11, types: [cn.hutool.core.date.format.FastDatePrinter$TimeZoneNameRule] */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v4, types: [cn.hutool.core.date.format.FastDatePrinter$NumberRule] */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v7, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v80 */
    /* JADX WARN: Type inference failed for: r9v81 */
    /* JADX WARN: Type inference failed for: r9v82 */
    /* JADX WARN: Type inference failed for: r9v83 */
    /* JADX WARN: Type inference failed for: r9v84 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<cn.hutool.core.date.format.FastDatePrinter.Rule> parsePattern() {
        /*
            Method dump skipped, instructions count: 468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.date.format.FastDatePrinter.parsePattern():java.util.List");
    }

    public String parseToken(String str, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        int i2 = iArr[0];
        int length = str.length();
        char cCharAt = str.charAt(i2);
        if ((cCharAt < 'A' || cCharAt > 'Z') && (cCharAt < 'a' || cCharAt > 'z')) {
            sb.append(CharPool.SINGLE_QUOTE);
            boolean z2 = false;
            while (i2 < length) {
                char cCharAt2 = str.charAt(i2);
                if (cCharAt2 != '\'') {
                    if (!z2 && ((cCharAt2 >= 'A' && cCharAt2 <= 'Z') || (cCharAt2 >= 'a' && cCharAt2 <= 'z'))) {
                        i2--;
                        break;
                    }
                    sb.append(cCharAt2);
                } else {
                    int i3 = i2 + 1;
                    if (i3 >= length || str.charAt(i3) != '\'') {
                        z2 = !z2;
                    } else {
                        sb.append(cCharAt2);
                        i2 = i3;
                    }
                }
                i2++;
            }
        } else {
            sb.append(cCharAt);
            while (true) {
                int i4 = i2 + 1;
                if (i4 >= length || str.charAt(i4) != cCharAt) {
                    break;
                }
                sb.append(cCharAt);
                i2 = i4;
            }
        }
        iArr[0] = i2;
        return sb.toString();
    }

    public NumberRule selectNumberRule(int i2, int i3) {
        return i3 != 1 ? i3 != 2 ? new PaddedNumberField(i2, i3) : new TwoDigitNumberField(i2) : new UnpaddedNumberField(i2);
    }

    public static class DayInWeekField implements NumberRule {
        private final NumberRule mRule;

        public DayInWeekField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int i2 = calendar.get(7);
            this.mRule.appendTo(appendable, i2 != 1 ? i2 - 1 : 7);
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.NumberRule
        public void appendTo(Appendable appendable, int i2) throws IOException {
            this.mRule.appendTo(appendable, i2);
        }
    }

    public static class TwelveHourField implements NumberRule {
        private final NumberRule mRule;

        public TwelveHourField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int leastMaximum = calendar.get(10);
            if (leastMaximum == 0) {
                leastMaximum = calendar.getLeastMaximum(10) + 1;
            }
            this.mRule.appendTo(appendable, leastMaximum);
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.NumberRule
        public void appendTo(Appendable appendable, int i2) throws IOException {
            this.mRule.appendTo(appendable, i2);
        }
    }

    public static class TwentyFourHourField implements NumberRule {
        private final NumberRule mRule;

        public TwentyFourHourField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int maximum = calendar.get(11);
            if (maximum == 0) {
                maximum = calendar.getMaximum(11) + 1;
            }
            this.mRule.appendTo(appendable, maximum);
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override // cn.hutool.core.date.format.FastDatePrinter.NumberRule
        public void appendTo(Appendable appendable, int i2) throws IOException {
            this.mRule.appendTo(appendable, i2);
        }
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public String format(long j2) {
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        calendar.setTimeInMillis(j2);
        return applyRulesToString(calendar);
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public String format(Date date) {
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        calendar.setTime(date);
        return applyRulesToString(calendar);
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public String format(Calendar calendar) {
        return ((StringBuilder) format(calendar, (Calendar) new StringBuilder(this.mMaxLengthEstimate))).toString();
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public <B extends Appendable> B format(long j2, B b3) {
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        calendar.setTimeInMillis(j2);
        return (B) applyRules(calendar, b3);
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public <B extends Appendable> B format(Date date, B b3) {
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        calendar.setTime(date);
        return (B) applyRules(calendar, b3);
    }

    @Override // cn.hutool.core.date.format.DatePrinter
    public <B extends Appendable> B format(Calendar calendar, B b3) {
        if (!calendar.getTimeZone().equals(this.timeZone)) {
            calendar = (Calendar) calendar.clone();
            calendar.setTimeZone(this.timeZone);
        }
        return (B) applyRules(calendar, b3);
    }
}
