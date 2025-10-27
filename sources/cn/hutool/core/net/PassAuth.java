package cn.hutool.core.net;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/* loaded from: classes.dex */
public class PassAuth extends Authenticator {
    private final PasswordAuthentication auth;

    public PassAuth(String str, char[] cArr) {
        this.auth = new PasswordAuthentication(str, cArr);
    }

    public static PassAuth of(String str, char[] cArr) {
        return new PassAuth(str, cArr);
    }

    @Override // java.net.Authenticator
    public PasswordAuthentication getPasswordAuthentication() {
        return this.auth;
    }
}
