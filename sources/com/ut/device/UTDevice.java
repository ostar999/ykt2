package com.ut.device;

import android.content.Context;
import com.ta.utdid2.device.c;

/* loaded from: classes6.dex */
public class UTDevice {
    public static String getUtdid(Context context) {
        return com.ta.utdid2.device.UTDevice.getUtdid(context);
    }

    public static void setExtendFactor(String str) {
        c.setExtendFactor(str);
    }
}
