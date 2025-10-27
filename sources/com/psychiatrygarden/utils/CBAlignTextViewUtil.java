package com.psychiatrygarden.utils;

import kotlin.text.Typography;

/* loaded from: classes6.dex */
public class CBAlignTextViewUtil {
    public static String replacePunctuation(String text) {
        return text.replace((char) 65292, ',').replace((char) 12290, '.').replace((char) 12304, '[').replace((char) 12305, ']').replace((char) 65311, '?').replace((char) 65281, '!').replace((char) 65288, '(').replace((char) 65289, ')').replace(Typography.leftDoubleQuote, '\"').replace(Typography.rightDoubleQuote, '\"');
    }
}
