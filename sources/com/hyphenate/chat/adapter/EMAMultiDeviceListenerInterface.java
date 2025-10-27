package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public interface EMAMultiDeviceListenerInterface {
    void onContactEvent(int i2, String str, String str2);

    void onGroupEvent(int i2, String str, List<String> list);

    void onThreadEvent(int i2, String str, List<String> list);
}
