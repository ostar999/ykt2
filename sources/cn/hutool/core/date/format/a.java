package cn.hutool.core.date.format;

import java.text.ParseException;
import java.text.ParsePosition;

/* loaded from: classes.dex */
public final /* synthetic */ class a {
    public static Object a(DateParser dateParser, String str) throws ParseException {
        return dateParser.parse(str);
    }

    public static Object b(DateParser dateParser, String str, ParsePosition parsePosition) {
        return dateParser.parse(str, parsePosition);
    }
}
