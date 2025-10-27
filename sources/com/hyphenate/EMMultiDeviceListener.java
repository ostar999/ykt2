package com.hyphenate;

import java.util.List;

/* loaded from: classes4.dex */
public interface EMMultiDeviceListener {
    public static final int CONTACT_ACCEPT = 3;
    public static final int CONTACT_ALLOW = 6;
    public static final int CONTACT_BAN = 5;
    public static final int CONTACT_DECLINE = 4;
    public static final int CONTACT_REMOVE = 2;
    public static final int GROUP_ADD_ADMIN = 26;
    public static final int GROUP_ADD_MUTE = 28;
    public static final int GROUP_ADD_USER_WHITE_LIST = 30;
    public static final int GROUP_ALLOW = 22;
    public static final int GROUP_ALL_BAN = 32;
    public static final int GROUP_APPLY = 14;
    public static final int GROUP_APPLY_ACCEPT = 15;
    public static final int GROUP_APPLY_DECLINE = 16;
    public static final int GROUP_ASSIGN_OWNER = 25;
    public static final int GROUP_BAN = 21;
    public static final int GROUP_BLOCK = 23;
    public static final int GROUP_CREATE = 10;
    public static final int GROUP_DESTROY = 11;
    public static final int GROUP_INVITE = 17;
    public static final int GROUP_INVITE_ACCEPT = 18;
    public static final int GROUP_INVITE_DECLINE = 19;
    public static final int GROUP_JOIN = 12;
    public static final int GROUP_KICK = 20;
    public static final int GROUP_LEAVE = 13;
    public static final int GROUP_REMOVE_ADMIN = 27;
    public static final int GROUP_REMOVE_ALL_BAN = 33;
    public static final int GROUP_REMOVE_MUTE = 29;
    public static final int GROUP_REMOVE_USER_WHITE_LIST = 31;
    public static final int GROUP_UNBLOCK = 24;
    public static final int THREAD_CREATE = 40;
    public static final int THREAD_DESTROY = 41;
    public static final int THREAD_JOIN = 42;
    public static final int THREAD_KICK = 45;
    public static final int THREAD_LEAVE = 43;
    public static final int THREAD_UPDATE = 44;

    void onChatThreadEvent(int i2, String str, List<String> list);

    void onContactEvent(int i2, String str, String str2);

    void onGroupEvent(int i2, String str, List<String> list);
}
