package com.alibaba.fastjson.serializer;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.catchpig.mvvm.utils.DateUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/* loaded from: classes2.dex */
public class JodaCodec implements ObjectSerializer, ContextObjectSerializer, ObjectDeserializer {
    private static final String defaultPatttern = "yyyy-MM-dd HH:mm:ss";
    private static final String formatter_iso8601_pattern = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String formatter_iso8601_pattern_23 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String formatter_iso8601_pattern_29 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
    public static final JodaCodec instance = new JodaCodec();
    private static final DateTimeFormatter defaultFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter defaultFormatter_23 = DateTimeFormat.forPattern(DatePattern.NORM_DATETIME_MS_PATTERN);
    private static final DateTimeFormatter formatter_dt19_tw = DateTimeFormat.forPattern(DateUtil.TIME_FORMAT_YMDHMS);
    private static final DateTimeFormatter formatter_dt19_cn = DateTimeFormat.forPattern("yyyy年M月d日 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn_1 = DateTimeFormat.forPattern("yyyy年M月d日 H时m分s秒");
    private static final DateTimeFormatter formatter_dt19_kr = DateTimeFormat.forPattern("yyyy년M월d일 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_us = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_eur = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_de = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_in = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_d8 = DateTimeFormat.forPattern(DatePattern.PURE_DATE_PATTERN);
    private static final DateTimeFormatter formatter_d10_tw = DateTimeFormat.forPattern(DateUtil.TIME_FROMAT_DAY_WITH_SLASH);
    private static final DateTimeFormatter formatter_d10_cn = DateTimeFormat.forPattern("yyyy年M月d日");
    private static final DateTimeFormatter formatter_d10_kr = DateTimeFormat.forPattern("yyyy년M월d일");
    private static final DateTimeFormatter formatter_d10_us = DateTimeFormat.forPattern("MM/dd/yyyy");
    private static final DateTimeFormatter formatter_d10_eur = DateTimeFormat.forPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatter_d10_de = DateTimeFormat.forPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatter_d10_in = DateTimeFormat.forPattern("dd-MM-yyyy");
    private static final DateTimeFormatter ISO_FIXED_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.getDefault());
    private static final DateTimeFormatter formatter_iso8601 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null, 0);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0129 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.joda.time.LocalDateTime parseDateTime(java.lang.String r17, org.joda.time.format.DateTimeFormatter r18) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JodaCodec.parseDateTime(java.lang.String, org.joda.time.format.DateTimeFormatter):org.joda.time.LocalDateTime");
    }

    public LocalDate parseLocalDate(String str, String str2, DateTimeFormatter dateTimeFormatter) {
        DateTimeFormatter dateTimeFormatter2;
        DateTimeFormatter dateTimeFormatter3;
        if (dateTimeFormatter == null) {
            if (str.length() == 8) {
                dateTimeFormatter = formatter_d8;
            }
            boolean z2 = false;
            if (str.length() == 10) {
                char cCharAt = str.charAt(4);
                char cCharAt2 = str.charAt(7);
                if (cCharAt == '/' && cCharAt2 == '/') {
                    dateTimeFormatter = formatter_d10_tw;
                }
                char cCharAt3 = str.charAt(0);
                char cCharAt4 = str.charAt(1);
                char cCharAt5 = str.charAt(2);
                char cCharAt6 = str.charAt(3);
                char cCharAt7 = str.charAt(5);
                if (cCharAt5 == '/' && cCharAt7 == '/') {
                    int i2 = ((cCharAt6 - '0') * 10) + (cCharAt - '0');
                    if (((cCharAt3 - '0') * 10) + (cCharAt4 - '0') > 12) {
                        dateTimeFormatter3 = formatter_d10_eur;
                    } else if (i2 > 12) {
                        dateTimeFormatter3 = formatter_d10_us;
                    } else {
                        String country = Locale.getDefault().getCountry();
                        if (country.equals("US")) {
                            dateTimeFormatter3 = formatter_d10_us;
                        } else if (country.equals("BR") || country.equals("AU")) {
                            dateTimeFormatter3 = formatter_d10_eur;
                        }
                    }
                    dateTimeFormatter = dateTimeFormatter3;
                } else if (cCharAt5 == '.' && cCharAt7 == '.') {
                    dateTimeFormatter = formatter_d10_de;
                } else if (cCharAt5 == '-' && cCharAt7 == '-') {
                    dateTimeFormatter = formatter_d10_in;
                }
            }
            if (str.length() >= 9) {
                char cCharAt8 = str.charAt(4);
                if (cCharAt8 == 24180) {
                    dateTimeFormatter2 = formatter_d10_cn;
                } else if (cCharAt8 == 45380) {
                    dateTimeFormatter2 = formatter_d10_kr;
                }
                dateTimeFormatter = dateTimeFormatter2;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= str.length()) {
                    z2 = true;
                    break;
                }
                char cCharAt9 = str.charAt(i3);
                if (cCharAt9 < '0' || cCharAt9 > '9') {
                    break;
                }
                i3++;
            }
            if (z2 && str.length() > 8 && str.length() < 19) {
                return new LocalDateTime(Long.parseLong(str), DateTimeZone.forTimeZone(JSON.defaultTimeZone)).toLocalDate();
            }
        }
        return dateTimeFormatter == null ? LocalDate.parse(str) : LocalDate.parse(str, dateTimeFormatter);
    }

    public DateTime parseZonedDateTime(String str, DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter == null) {
            if (str.length() == 19) {
                char cCharAt = str.charAt(4);
                char cCharAt2 = str.charAt(7);
                char cCharAt3 = str.charAt(10);
                char cCharAt4 = str.charAt(13);
                char cCharAt5 = str.charAt(16);
                if (cCharAt4 == ':' && cCharAt5 == ':') {
                    if (cCharAt == '-' && cCharAt2 == '-') {
                        if (cCharAt3 == 'T') {
                            dateTimeFormatter = formatter_iso8601;
                        } else if (cCharAt3 == ' ') {
                            dateTimeFormatter = defaultFormatter;
                        }
                    } else if (cCharAt == '/' && cCharAt2 == '/') {
                        dateTimeFormatter = formatter_dt19_tw;
                    } else {
                        char cCharAt6 = str.charAt(0);
                        char cCharAt7 = str.charAt(1);
                        char cCharAt8 = str.charAt(2);
                        char cCharAt9 = str.charAt(3);
                        char cCharAt10 = str.charAt(5);
                        if (cCharAt8 == '/' && cCharAt10 == '/') {
                            int i2 = ((cCharAt9 - '0') * 10) + (cCharAt - '0');
                            if (((cCharAt6 - '0') * 10) + (cCharAt7 - '0') > 12) {
                                dateTimeFormatter = formatter_dt19_eur;
                            } else if (i2 > 12) {
                                dateTimeFormatter = formatter_dt19_us;
                            } else {
                                String country = Locale.getDefault().getCountry();
                                if (country.equals("US")) {
                                    dateTimeFormatter = formatter_dt19_us;
                                } else if (country.equals("BR") || country.equals("AU")) {
                                    dateTimeFormatter = formatter_dt19_eur;
                                }
                            }
                        } else if (cCharAt8 == '.' && cCharAt10 == '.') {
                            dateTimeFormatter = formatter_dt19_de;
                        } else if (cCharAt8 == '-' && cCharAt10 == '-') {
                            dateTimeFormatter = formatter_dt19_in;
                        }
                    }
                }
            }
            if (str.length() >= 17) {
                char cCharAt11 = str.charAt(4);
                if (cCharAt11 == 24180) {
                    dateTimeFormatter = str.charAt(str.length() - 1) == 31186 ? formatter_dt19_cn_1 : formatter_dt19_cn;
                } else if (cCharAt11 == 45380) {
                    dateTimeFormatter = formatter_dt19_kr;
                }
            }
        }
        return dateTimeFormatter == null ? DateTime.parse(str) : DateTime.parse(str, dateTimeFormatter);
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        if (type == null) {
            type = obj.getClass();
        }
        if (type != LocalDateTime.class) {
            serializeWriter.writeString(obj.toString());
            return;
        }
        SerializerFeature serializerFeature = SerializerFeature.UseISO8601DateFormat;
        int mask = serializerFeature.getMask();
        LocalDateTime localDateTime = (LocalDateTime) obj;
        String dateFormatPattern = jSONSerializer.getDateFormatPattern();
        if (dateFormatPattern == null) {
            dateFormatPattern = ((i2 & mask) != 0 || jSONSerializer.isEnabled(serializerFeature)) ? "yyyy-MM-dd'T'HH:mm:ss" : localDateTime.getMillisOfSecond() == 0 ? "yyyy-MM-dd'T'HH:mm:ss.SSS" : formatter_iso8601_pattern_29;
        }
        write(serializeWriter, (ReadablePartial) localDateTime, dateFormatPattern);
    }

    /* JADX WARN: Type inference failed for: r8v3, types: [T, org.joda.time.LocalDateTime] */
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i2) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        if (jSONLexer.token() != 4) {
            if (jSONLexer.token() != 2) {
                throw new UnsupportedOperationException();
            }
            long jLongValue = jSONLexer.longValue();
            jSONLexer.nextToken();
            TimeZone timeZone = JSON.defaultTimeZone;
            if (timeZone == null) {
                timeZone = TimeZone.getDefault();
            }
            if (type == DateTime.class) {
                return (T) new DateTime(jLongValue, DateTimeZone.forTimeZone(timeZone));
            }
            ?? r8 = (T) new LocalDateTime(jLongValue, DateTimeZone.forTimeZone(timeZone));
            if (type == LocalDateTime.class) {
                return r8;
            }
            if (type == LocalDate.class) {
                return (T) r8.toLocalDate();
            }
            if (type == LocalTime.class) {
                return (T) r8.toLocalTime();
            }
            if (type == Instant.class) {
                return (T) new Instant(jLongValue);
            }
            throw new UnsupportedOperationException();
        }
        String strStringVal = jSONLexer.stringVal();
        jSONLexer.nextToken();
        DateTimeFormatter dateTimeFormatterForPattern = str != null ? "yyyy-MM-dd HH:mm:ss".equals(str) ? defaultFormatter : DateTimeFormat.forPattern(str) : null;
        if ("".equals(strStringVal)) {
            return null;
        }
        if (type == LocalDateTime.class) {
            return (strStringVal.length() == 10 || strStringVal.length() == 8) ? (T) parseLocalDate(strStringVal, str, dateTimeFormatterForPattern).toLocalDateTime(LocalTime.MIDNIGHT) : (T) parseDateTime(strStringVal, dateTimeFormatterForPattern);
        }
        if (type == LocalDate.class) {
            return strStringVal.length() == 23 ? (T) LocalDateTime.parse(strStringVal).toLocalDate() : (T) parseLocalDate(strStringVal, str, dateTimeFormatterForPattern);
        }
        if (type == LocalTime.class) {
            return strStringVal.length() == 23 ? (T) LocalDateTime.parse(strStringVal).toLocalTime() : (T) LocalTime.parse(strStringVal);
        }
        if (type == DateTime.class) {
            if (dateTimeFormatterForPattern == defaultFormatter) {
                dateTimeFormatterForPattern = ISO_FIXED_FORMAT;
            }
            return (T) parseZonedDateTime(strStringVal, dateTimeFormatterForPattern);
        }
        if (type == DateTimeZone.class) {
            return (T) DateTimeZone.forID(strStringVal);
        }
        if (type == Period.class) {
            return (T) Period.parse(strStringVal);
        }
        if (type == Duration.class) {
            return (T) Duration.parse(strStringVal);
        }
        if (type != Instant.class) {
            if (type == DateTimeFormatter.class) {
                return (T) DateTimeFormat.forPattern(strStringVal);
            }
            return null;
        }
        boolean z2 = false;
        int i3 = 0;
        while (true) {
            if (i3 >= strStringVal.length()) {
                z2 = true;
                break;
            }
            char cCharAt = strStringVal.charAt(i3);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            i3++;
        }
        return (!z2 || strStringVal.length() <= 8 || strStringVal.length() >= 19) ? (T) Instant.parse(strStringVal) : (T) new Instant(Long.parseLong(strStringVal));
    }

    @Override // com.alibaba.fastjson.serializer.ContextObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, BeanContext beanContext) throws IOException {
        write(jSONSerializer.out, (ReadablePartial) obj, beanContext.getFormat());
    }

    private void write(SerializeWriter serializeWriter, ReadablePartial readablePartial, String str) {
        DateTimeFormatter dateTimeFormatterForPattern;
        if (str.equals("yyyy-MM-dd'T'HH:mm:ss")) {
            dateTimeFormatterForPattern = formatter_iso8601;
        } else {
            dateTimeFormatterForPattern = DateTimeFormat.forPattern(str);
        }
        serializeWriter.writeString(dateTimeFormatterForPattern.print(readablePartial));
    }
}
