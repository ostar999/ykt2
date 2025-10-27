package com.google.android.exoplayer2.upstream;

import android.text.TextUtils;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.common.base.Ascii;
import com.google.common.base.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class l {
    static {
        Predicate<String> predicate = HttpDataSource.REJECT_PAYWALL_TYPES;
    }

    public static /* synthetic */ boolean a(String str) {
        if (str == null) {
            return false;
        }
        String lowerCase = Ascii.toLowerCase(str);
        if (TextUtils.isEmpty(lowerCase)) {
            return false;
        }
        return ((lowerCase.contains("text") && !lowerCase.contains(MimeTypes.TEXT_VTT)) || lowerCase.contains("html") || lowerCase.contains(AliyunVodHttpCommon.Format.FORMAT_XML)) ? false : true;
    }
}
