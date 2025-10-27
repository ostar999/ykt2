package com.hyphenate.easeui.utils;

import android.content.Context;

/* loaded from: classes4.dex */
public class EaseVoiceLengthUtils {
    public static int getVoiceLength(Context context, int i2) {
        float fDip2px = (EaseCommonUtils.getScreenInfo(context)[0] / 4.0f) - EaseCommonUtils.dip2px(context, 10.0f);
        return (int) (i2 <= 20 ? ((i2 / 20.0f) * fDip2px) + EaseCommonUtils.dip2px(context, 10.0f) : fDip2px + EaseCommonUtils.dip2px(context, 10.0f));
    }
}
