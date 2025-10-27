package org.eclipse.jetty.security;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* loaded from: classes9.dex */
public class HashCrossContextPsuedoSession<T> implements CrossContextPsuedoSession<T> {
    private final String _cookieName;
    private final String _cookiePath;
    private final Random _random = new SecureRandom();
    private final Map<String, T> _data = new HashMap();

    public HashCrossContextPsuedoSession(String str, String str2) {
        this._cookieName = str;
        this._cookiePath = str2 == null ? "/" : str2;
    }

    @Override // org.eclipse.jetty.security.CrossContextPsuedoSession
    public void clear(HttpServletRequest httpServletRequest) {
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (this._cookieName.equals(cookie.getName())) {
                this._data.remove(cookie.getValue());
                return;
            }
        }
    }

    @Override // org.eclipse.jetty.security.CrossContextPsuedoSession
    public T fetch(HttpServletRequest httpServletRequest) {
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (this._cookieName.equals(cookie.getName())) {
                return this._data.get(cookie.getValue());
            }
        }
        return null;
    }

    @Override // org.eclipse.jetty.security.CrossContextPsuedoSession
    public void store(T t2, HttpServletResponse httpServletResponse) {
        String string;
        synchronized (this._data) {
            do {
                string = Long.toString(Math.abs(this._random.nextLong()), ((int) (System.currentTimeMillis() % 7)) + 30);
            } while (this._data.containsKey(string));
            this._data.put(string, t2);
        }
        Cookie cookie = new Cookie(this._cookieName, string);
        cookie.setPath(this._cookiePath);
        httpServletResponse.addCookie(cookie);
    }
}
