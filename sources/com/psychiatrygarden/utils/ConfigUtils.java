package com.psychiatrygarden.utils;

import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.db.SharePreferencesUtils;

/* loaded from: classes6.dex */
public class ConfigUtils {
    public static final int TYPE_HIDE_ANALYSIS = 6;
    public static final int TYPE_HIDE_DIFFICULTY = 1;
    public static final int TYPE_HIDE_LABEL = 3;
    public static final int TYPE_HIDE_OPTIONS_ANALYSIS = 7;
    public static final int TYPE_HIDE_OUTLINES = 4;
    public static final int TYPE_HIDE_RESTORE = 5;
    public static final int TYPE_HIDE_STAT = 2;

    public static boolean isHidden(int type) {
        String str;
        switch (type) {
            case 1:
                str = CommonParameter.is_hidden_difficulty;
                break;
            case 2:
                str = CommonParameter.is_hidden_stat;
                break;
            case 3:
                str = CommonParameter.is_hidden_label;
                break;
            case 4:
                str = CommonParameter.is_hidden_outlines;
                break;
            case 5:
                str = CommonParameter.is_hidden_restore;
                break;
            case 6:
                str = CommonParameter.is_hidden_analysis;
                break;
            case 7:
                str = CommonParameter.is_hidden_options;
                break;
            default:
                str = null;
                break;
        }
        if (str == null) {
            return false;
        }
        return "1".equals(SharePreferencesUtils.readStrConfig(str, ProjectApp.instance(), "0"));
    }
}
