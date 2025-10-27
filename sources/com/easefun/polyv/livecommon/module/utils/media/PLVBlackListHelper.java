package com.easefun.polyv.livecommon.module.utils.media;

import android.os.Build;
import android.text.TextUtils;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class PLVBlackListHelper {
    private static final String[] BLACKLISTED_AEC_MODELS = {"Nexus 5"};
    private static final String[] BLACKLISTED_FPS_MODELS = {"OPPO R9", "Nexus 6P"};

    public static boolean deviceInAecBlacklisted() {
        for (String str : Arrays.asList(BLACKLISTED_AEC_MODELS)) {
            String str2 = Build.MODEL;
            if (!TextUtils.isEmpty(str2) && str2.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean deviceInFpsBlacklisted() {
        for (String str : Arrays.asList(BLACKLISTED_FPS_MODELS)) {
            String str2 = Build.MODEL;
            if (!TextUtils.isEmpty(str2) && str2.contains(str)) {
                return true;
            }
        }
        return false;
    }
}
