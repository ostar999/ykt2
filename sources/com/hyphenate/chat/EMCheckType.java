package com.hyphenate.chat;

/* loaded from: classes4.dex */
public class EMCheckType {
    public static final int ACCOUNT_VALIDATION = 0;
    public static final int DO_LOGIN = 3;
    public static final int DO_LOGOUT = 5;
    public static final int DO_MSG_SEND = 4;
    public static final int GET_DNS_LIST_FROM_SERVER = 1;
    public static final int GET_TOKEN_FROM_SERVER = 2;

    public @interface CheckType {
    }
}
