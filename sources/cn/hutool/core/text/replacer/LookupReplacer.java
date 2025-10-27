package cn.hutool.core.text.replacer;

import cn.hutool.core.text.StrBuilder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class LookupReplacer extends StrReplacer {
    private static final long serialVersionUID = 1;
    private final int maxLength;
    private final int minLength;
    private final Map<String, String> lookupMap = new HashMap();
    private final Set<Character> prefixSet = new HashSet();

    public LookupReplacer(String[]... strArr) {
        int i2 = Integer.MAX_VALUE;
        int i3 = 0;
        for (String[] strArr2 : strArr) {
            String str = strArr2[0];
            this.lookupMap.put(str, strArr2[1]);
            this.prefixSet.add(Character.valueOf(str.charAt(0)));
            int length = str.length();
            i3 = length > i3 ? length : i3;
            if (length < i2) {
                i2 = length;
            }
        }
        this.maxLength = i3;
        this.minLength = i2;
    }

    @Override // cn.hutool.core.text.replacer.StrReplacer
    public int replace(CharSequence charSequence, int i2, StrBuilder strBuilder) {
        if (!this.prefixSet.contains(Character.valueOf(charSequence.charAt(i2)))) {
            return 0;
        }
        int length = this.maxLength;
        if (i2 + length > charSequence.length()) {
            length = charSequence.length() - i2;
        }
        while (length >= this.minLength) {
            String str = this.lookupMap.get(charSequence.subSequence(i2, i2 + length).toString());
            if (str != null) {
                strBuilder.append((CharSequence) str);
                return length;
            }
            length--;
        }
        return 0;
    }
}
