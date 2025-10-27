package com.easefun.polyv.livecommon.module.utils;

import android.content.Context;
import android.provider.Settings;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

/* loaded from: classes3.dex */
public class PLVSystemUtils {
    public static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
    }
}
