package cn.hutool.core.util;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class PhoneUtil {
    public static CharSequence hideAfter(CharSequence charSequence) {
        return CharSequenceUtil.hide(charSequence, 7, 11);
    }

    public static CharSequence hideBefore(CharSequence charSequence) {
        return CharSequenceUtil.hide(charSequence, 0, 7);
    }

    public static CharSequence hideBetween(CharSequence charSequence) {
        return CharSequenceUtil.hide(charSequence, 3, 7);
    }

    public static boolean isMobile(CharSequence charSequence) {
        return Validator.isMatchRegex(PatternPool.MOBILE, charSequence);
    }

    public static boolean isMobileHk(CharSequence charSequence) {
        return Validator.isMatchRegex(PatternPool.MOBILE_HK, charSequence);
    }

    public static boolean isMobileMo(CharSequence charSequence) {
        return Validator.isMatchRegex(PatternPool.MOBILE_MO, charSequence);
    }

    public static boolean isMobileTw(CharSequence charSequence) {
        return Validator.isMatchRegex(PatternPool.MOBILE_TW, charSequence);
    }

    public static boolean isPhone(CharSequence charSequence) {
        return isMobile(charSequence) || isTel400800(charSequence) || isMobileHk(charSequence) || isMobileTw(charSequence) || isMobileMo(charSequence);
    }

    public static boolean isTel(CharSequence charSequence) {
        return Validator.isMatchRegex(PatternPool.TEL, charSequence);
    }

    public static boolean isTel400800(CharSequence charSequence) {
        return Validator.isMatchRegex(PatternPool.TEL_400_800, charSequence);
    }

    public static CharSequence subAfter(CharSequence charSequence) {
        return CharSequenceUtil.sub(charSequence, 7, 11);
    }

    public static CharSequence subBefore(CharSequence charSequence) {
        return CharSequenceUtil.sub(charSequence, 0, 3);
    }

    public static CharSequence subBetween(CharSequence charSequence) {
        return CharSequenceUtil.sub(charSequence, 3, 7);
    }

    public static CharSequence subTelAfter(CharSequence charSequence) {
        return ReUtil.get(PatternPool.TEL, charSequence, 2);
    }

    public static CharSequence subTelBefore(CharSequence charSequence) {
        return ReUtil.getGroup1(PatternPool.TEL, charSequence);
    }
}
