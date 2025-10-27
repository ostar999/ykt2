package cn.hutool.core.lang;

import cn.hutool.core.lang.EnumItem;
import java.io.Serializable;

/* loaded from: classes.dex */
public interface EnumItem<E extends EnumItem<E>> extends Serializable {
    E fromInt(Integer num);

    E fromStr(String str);

    int intVal();

    E[] items();

    String name();

    String text();
}
