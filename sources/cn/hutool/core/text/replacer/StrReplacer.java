package cn.hutool.core.text.replacer;

import cn.hutool.core.lang.Replacer;
import cn.hutool.core.text.StrBuilder;
import java.io.Serializable;

/* loaded from: classes.dex */
public abstract class StrReplacer implements Replacer<CharSequence>, Serializable {
    private static final long serialVersionUID = 1;

    public abstract int replace(CharSequence charSequence, int i2, StrBuilder strBuilder);

    @Override // cn.hutool.core.lang.Replacer
    public CharSequence replace(CharSequence charSequence) {
        int length = charSequence.length();
        StrBuilder strBuilderCreate = StrBuilder.create(length);
        int i2 = 0;
        while (i2 < length) {
            int iReplace = replace(charSequence, i2, strBuilderCreate);
            if (iReplace == 0) {
                strBuilderCreate.append(charSequence.charAt(i2));
                i2++;
            }
            i2 += iReplace;
        }
        return strBuilderCreate;
    }
}
