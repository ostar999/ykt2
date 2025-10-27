package com.hyphenate;

/* loaded from: classes4.dex */
public interface EMContactListener {
    void onContactAdded(String str);

    void onContactDeleted(String str);

    void onContactInvited(String str, String str2);

    void onFriendRequestAccepted(String str);

    void onFriendRequestDeclined(String str);
}
