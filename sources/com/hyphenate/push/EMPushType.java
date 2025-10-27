package com.hyphenate.push;

import android.text.TextUtils;
import com.hyphenate.easeui.utils.RomUtils;

/* loaded from: classes4.dex */
public enum EMPushType {
    FCM("FCM"),
    MIPUSH("MI"),
    HMSPUSH("HUAWEI"),
    MEIZUPUSH("MEIZU"),
    OPPOPUSH(RomUtils.ROM_OPPO),
    VIVOPUSH(RomUtils.ROM_VIVO),
    NORMAL("NORMAL");

    private String name;

    EMPushType(String str) {
        this.name = str;
    }

    public static EMPushType getType(String str) {
        if (TextUtils.isEmpty(str)) {
            return NORMAL;
        }
        for (EMPushType eMPushType : values()) {
            if (eMPushType.getName().equals(str)) {
                return eMPushType;
            }
        }
        return NORMAL;
    }

    public String getName() {
        return this.name;
    }
}
