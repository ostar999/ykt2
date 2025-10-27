package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes.dex */
public class CalendarConverter extends AbstractConverter<Calendar> {
    private static final long serialVersionUID = 1;
    private String format;

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String str) {
        this.format = str;
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Calendar convertInternal(Object obj) {
        if (obj instanceof Date) {
            return CalendarUtil.calendar((Date) obj);
        }
        if (obj instanceof Long) {
            return CalendarUtil.calendar(((Long) obj).longValue());
        }
        String strConvertToStr = convertToStr(obj);
        return CalendarUtil.calendar(CharSequenceUtil.isBlank(this.format) ? DateUtil.parse(strConvertToStr) : DateUtil.parse(strConvertToStr, this.format));
    }
}
