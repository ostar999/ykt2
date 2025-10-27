package com.mobile.auth.gatewayauth;

import com.mobile.auth.gatewayauth.annotations.AuthNumber;

@AuthNumber
/* loaded from: classes4.dex */
public interface PnsLoggerHandler {
    void debug(String str);

    void error(String str);

    void info(String str);

    void monitor(String str);

    void verbose(String str);

    void warning(String str);
}
