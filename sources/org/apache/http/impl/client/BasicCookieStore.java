package org.apache.http.impl.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.apache.http.annotation.GuardedBy;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieIdentityComparator;

@ThreadSafe
/* loaded from: classes9.dex */
public class BasicCookieStore implements CookieStore, Serializable {
    private static final long serialVersionUID = -7581093305228232025L;

    @GuardedBy("this")
    private final TreeSet<Cookie> cookies = new TreeSet<>(new CookieIdentityComparator());

    @Override // org.apache.http.client.CookieStore
    public synchronized void addCookie(Cookie cookie) {
        if (cookie != null) {
            this.cookies.remove(cookie);
            if (!cookie.isExpired(new Date())) {
                this.cookies.add(cookie);
            }
        }
    }

    public synchronized void addCookies(Cookie[] cookieArr) {
        if (cookieArr != null) {
            for (Cookie cookie : cookieArr) {
                addCookie(cookie);
            }
        }
    }

    @Override // org.apache.http.client.CookieStore
    public synchronized void clear() {
        this.cookies.clear();
    }

    @Override // org.apache.http.client.CookieStore
    public synchronized boolean clearExpired(Date date) {
        boolean z2 = false;
        if (date == null) {
            return false;
        }
        Iterator<Cookie> it = this.cookies.iterator();
        while (it.hasNext()) {
            if (it.next().isExpired(date)) {
                it.remove();
                z2 = true;
            }
        }
        return z2;
    }

    @Override // org.apache.http.client.CookieStore
    public synchronized List<Cookie> getCookies() {
        return new ArrayList(this.cookies);
    }

    public synchronized String toString() {
        return this.cookies.toString();
    }
}
