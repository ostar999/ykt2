package com.aliyun.player.alivcplayerexpand.view.quality;

import android.content.Context;

/* loaded from: classes2.dex */
public class QualityItem {
    private String mName;
    private String mQuality;

    private QualityItem(String str, String str2) {
        this.mQuality = str;
        this.mName = str2;
    }

    public static QualityItem getItem(Context context, String str, boolean z2) {
        return z2 ? new QualityItem(str, QualityLanguage.getMtsLanguage(context, str)) : new QualityItem(str, QualityLanguage.getSaasLanguage(context, str));
    }

    public String getName() {
        return this.mName;
    }
}
