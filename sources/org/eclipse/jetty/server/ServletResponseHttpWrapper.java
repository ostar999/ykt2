package org.eclipse.jetty.server;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/* loaded from: classes9.dex */
public class ServletResponseHttpWrapper extends ServletResponseWrapper implements HttpServletResponse {
    public ServletResponseHttpWrapper(ServletResponse servletResponse) {
        super(servletResponse);
    }

    public void addCookie(Cookie cookie) {
    }

    public void addDateHeader(String str, long j2) {
    }

    public void addHeader(String str, String str2) {
    }

    public void addIntHeader(String str, int i2) {
    }

    public boolean containsHeader(String str) {
        return false;
    }

    public String encodeRedirectURL(String str) {
        return null;
    }

    public String encodeRedirectUrl(String str) {
        return null;
    }

    public String encodeURL(String str) {
        return null;
    }

    public String encodeUrl(String str) {
        return null;
    }

    public String getHeader(String str) {
        return null;
    }

    public Collection<String> getHeaderNames() {
        return null;
    }

    public Collection<String> getHeaders(String str) {
        return null;
    }

    public int getStatus() {
        return 0;
    }

    public void sendError(int i2) throws IOException {
    }

    public void sendError(int i2, String str) throws IOException {
    }

    public void sendRedirect(String str) throws IOException {
    }

    public void setDateHeader(String str, long j2) {
    }

    public void setHeader(String str, String str2) {
    }

    public void setIntHeader(String str, int i2) {
    }

    public void setStatus(int i2) {
    }

    public void setStatus(int i2, String str) {
    }
}
