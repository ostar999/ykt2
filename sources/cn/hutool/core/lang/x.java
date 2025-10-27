package cn.hutool.core.lang;

import cn.hutool.core.lang.EnumItem;

/* loaded from: classes.dex */
public final /* synthetic */ class x<E extends EnumItem<E>> {
    public static EnumItem a(EnumItem enumItem, Integer num) {
        if (num == null) {
            return null;
        }
        for (EnumItem enumItem2 : enumItem.items()) {
            if (enumItem2.intVal() == num.intValue()) {
                return enumItem2;
            }
        }
        return null;
    }

    public static EnumItem b(EnumItem enumItem, String str) {
        if (str == null) {
            return null;
        }
        for (EnumItem enumItem2 : enumItem.items()) {
            if (str.equalsIgnoreCase(enumItem2.name())) {
                return enumItem2;
            }
        }
        return null;
    }

    public static EnumItem[] c(EnumItem enumItem) {
        return (EnumItem[]) enumItem.getClass().getEnumConstants();
    }

    public static String d(EnumItem enumItem) {
        return enumItem.name();
    }
}
