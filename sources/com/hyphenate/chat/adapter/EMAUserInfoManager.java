package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public class EMAUserInfoManager extends EMABase {
    public String fetchUserInfoByAttribute(List<String> list, List<String> list2, EMAError eMAError) {
        return nativeFetchUserInfoByAttribute(list, list2, eMAError);
    }

    public native String nativeFetchUserInfoByAttribute(List<String> list, List<String> list2, EMAError eMAError);

    public native String nativeUpdateOwnInfo(String str, EMAError eMAError);

    public String updateOwnInfo(String str, EMAError eMAError) {
        return nativeUpdateOwnInfo(str, eMAError);
    }
}
