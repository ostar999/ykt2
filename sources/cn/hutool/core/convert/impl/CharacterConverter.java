package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.BooleanUtil;

/* loaded from: classes.dex */
public class CharacterConverter extends AbstractConverter<Character> {
    private static final long serialVersionUID = 1;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.convert.AbstractConverter
    public Character convertInternal(Object obj) {
        if (obj instanceof Boolean) {
            return BooleanUtil.toCharacter(((Boolean) obj).booleanValue());
        }
        String strConvertToStr = convertToStr(obj);
        if (CharSequenceUtil.isNotBlank(strConvertToStr)) {
            return Character.valueOf(strConvertToStr.charAt(0));
        }
        return null;
    }
}
