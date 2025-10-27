package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public interface EMAContactListenerInterface {
    void onContactAdded(String str);

    void onContactAgreed(String str);

    void onContactDeleted(String str);

    void onContactInvited(String str, String str2);

    void onContactRefused(String str);
}
