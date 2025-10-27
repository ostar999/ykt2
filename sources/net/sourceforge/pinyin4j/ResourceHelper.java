package net.sourceforge.pinyin4j;

import java.io.BufferedInputStream;

/* loaded from: classes9.dex */
class ResourceHelper {
    static /* synthetic */ Class class$net$sourceforge$pinyin4j$ResourceHelper;

    public static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public static BufferedInputStream getResourceInputStream(String str) {
        Class clsClass$ = class$net$sourceforge$pinyin4j$ResourceHelper;
        if (clsClass$ == null) {
            clsClass$ = class$("net.sourceforge.pinyin4j.ResourceHelper");
            class$net$sourceforge$pinyin4j$ResourceHelper = clsClass$;
        }
        return new BufferedInputStream(clsClass$.getResourceAsStream(str));
    }
}
