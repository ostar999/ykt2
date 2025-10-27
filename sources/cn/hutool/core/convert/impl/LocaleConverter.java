package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import java.util.Locale;

/* loaded from: classes.dex */
public class LocaleConverter extends AbstractConverter<Locale> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public Locale convertInternal(Object obj) {
        try {
            String strConvertToStr = convertToStr(obj);
            if (CharSequenceUtil.isEmpty(strConvertToStr)) {
                return null;
            }
            String[] strArrSplit = strConvertToStr.split(StrPool.UNDERLINE);
            return strArrSplit.length == 1 ? new Locale(strArrSplit[0]) : strArrSplit.length == 2 ? new Locale(strArrSplit[0], strArrSplit[1]) : new Locale(strArrSplit[0], strArrSplit[1], strArrSplit[2]);
        } catch (Exception unused) {
            return null;
        }
    }
}
