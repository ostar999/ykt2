package cn.hutool.core.bean;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class BeanPath implements Serializable {
    private static final char[] EXP_CHARS = {'.', '[', ']'};
    private static final long serialVersionUID = 1;
    private boolean isStartWith = false;
    protected List<String> patternParts;

    public BeanPath(String str) {
        init(str);
    }

    public static BeanPath create(String str) {
        return new BeanPath(str);
    }

    private static Object getFieldValue(Object obj, String str) throws NumberFormatException {
        if (CharSequenceUtil.isBlank(str)) {
            return null;
        }
        if (CharSequenceUtil.contains((CharSequence) str, ':')) {
            List<String> listSplitTrim = CharSequenceUtil.splitTrim((CharSequence) str, ':');
            int i2 = Integer.parseInt(listSplitTrim.get(0));
            int i3 = Integer.parseInt(listSplitTrim.get(1));
            int i4 = 3 == listSplitTrim.size() ? Integer.parseInt(listSplitTrim.get(2)) : 1;
            if (obj instanceof Collection) {
                return CollUtil.sub((Collection) obj, i2, i3, i4);
            }
            if (ArrayUtil.isArray(obj)) {
                return ArrayUtil.sub(obj, i2, i3, i4);
            }
            return null;
        }
        if (!CharSequenceUtil.contains((CharSequence) str, ',')) {
            return BeanUtil.getFieldValue(obj, str);
        }
        List<String> listSplitTrim2 = CharSequenceUtil.splitTrim((CharSequence) str, ',');
        if (obj instanceof Collection) {
            return CollUtil.getAny((Collection) obj, (int[]) Convert.convert(int[].class, (Object) listSplitTrim2));
        }
        if (ArrayUtil.isArray(obj)) {
            return ArrayUtil.getAny(obj, (int[]) Convert.convert(int[].class, (Object) listSplitTrim2));
        }
        int size = listSplitTrim2.size();
        String[] strArr = new String[size];
        for (int i5 = 0; i5 < size; i5++) {
            strArr[i5] = CharSequenceUtil.unWrap(listSplitTrim2.get(i5), CharPool.SINGLE_QUOTE);
        }
        return obj instanceof Map ? MapUtil.getAny((Map) obj, strArr) : MapUtil.getAny(BeanUtil.beanToMap(obj, new String[0]), strArr);
    }

    private static List<String> getParentParts(List<String> list) {
        return list.subList(0, list.size() - 1);
    }

    private void init(String str) {
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (i2 == 0 && '$' == cCharAt) {
                this.isStartWith = true;
            } else if ('\'' == cCharAt) {
                z3 = !z3;
            } else if (z3 || !PrimitiveArrayUtil.contains(EXP_CHARS, cCharAt)) {
                sb.append(cCharAt);
            } else {
                if (']' == cCharAt) {
                    if (!z2) {
                        throw new IllegalArgumentException(CharSequenceUtil.format("Bad expression '{}':{}, we find ']' but no '[' !", str, Integer.valueOf(i2)));
                    }
                    z2 = false;
                } else {
                    if (z2) {
                        throw new IllegalArgumentException(CharSequenceUtil.format("Bad expression '{}':{}, we find '[' but no ']' !", str, Integer.valueOf(i2)));
                    }
                    if ('[' == cCharAt) {
                        z2 = true;
                    }
                }
                if (sb.length() > 0) {
                    arrayList.add(sb.toString());
                }
                sb.setLength(0);
            }
        }
        if (z2) {
            throw new IllegalArgumentException(CharSequenceUtil.format("Bad expression '{}':{}, we find '[' but no ']' !", str, Integer.valueOf(length - 1)));
        }
        if (sb.length() > 0) {
            arrayList.add(sb.toString());
        }
        this.patternParts = ListUtil.unmodifiable(arrayList);
    }

    private static boolean lastIsNumber(List<String> list) {
        return NumberUtil.isInteger(list.get(list.size() - 1));
    }

    public Object get(Object obj) {
        return get(this.patternParts, obj, false);
    }

    public List<String> getPatternParts() {
        return this.patternParts;
    }

    public void set(Object obj, Object obj2) {
        List<String> list = this.patternParts;
        set(obj, list, lastIsNumber(list), obj2);
    }

    public String toString() {
        return this.patternParts.toString();
    }

    private Object get(List<String> list, Object obj, boolean z2) throws NumberFormatException {
        int size = list.size();
        if (z2) {
            size--;
        }
        Object fieldValue = obj;
        boolean z3 = true;
        for (int i2 = 0; i2 < size; i2++) {
            String str = list.get(i2);
            fieldValue = getFieldValue(fieldValue, str);
            if (fieldValue == null) {
                if (!z3 || this.isStartWith || !BeanUtil.isMatchName(obj, str, true)) {
                    return null;
                }
                fieldValue = obj;
                z3 = false;
            }
        }
        return fieldValue;
    }

    private void set(Object obj, List<String> list, boolean z2, Object obj2) throws UtilException, NumberFormatException {
        Object obj3 = get(list, obj, true);
        if (obj3 == null) {
            List<String> parentParts = getParentParts(list);
            set(obj, parentParts, lastIsNumber(parentParts), z2 ? new ArrayList() : new HashMap());
            obj3 = get(list, obj, true);
        }
        Object fieldValue = BeanUtil.setFieldValue(obj3, list.get(list.size() - 1), obj2);
        if (fieldValue != obj3) {
            set(obj, getParentParts(list), z2, fieldValue);
        }
    }
}
