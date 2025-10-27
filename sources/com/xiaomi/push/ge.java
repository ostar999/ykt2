package com.xiaomi.push;

import com.yikaobang.yixue.R2;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/* loaded from: classes6.dex */
public class ge {
    public static int a(Throwable th) {
        boolean z2 = th instanceof gn;
        if (z2) {
            gn gnVar = (gn) th;
            if (gnVar.a() != null) {
                th = gnVar.a();
            }
        }
        String message = th.getMessage();
        if (th.getCause() != null) {
            message = th.getCause().getMessage();
        }
        if (th instanceof SocketTimeoutException) {
            return 105;
        }
        if (!(th instanceof SocketException)) {
            if (th instanceof UnknownHostException) {
                return 107;
            }
            if (z2) {
                return R2.attr.auto_show;
            }
            return 0;
        }
        if (message.indexOf("Network is unreachable") != -1) {
            return 102;
        }
        if (message.indexOf("Connection refused") != -1) {
            return 103;
        }
        if (message.indexOf("Connection timed out") != -1) {
            return 105;
        }
        if (message.endsWith("EACCES (Permission denied)")) {
            return 101;
        }
        if (message.indexOf("Connection reset by peer") != -1) {
            return 109;
        }
        if (message.indexOf("Broken pipe") != -1) {
            return 110;
        }
        if (message.indexOf("No route to host") != -1) {
            return 104;
        }
        return message.endsWith("EINVAL (Invalid argument)") ? 106 : 199;
    }
}
