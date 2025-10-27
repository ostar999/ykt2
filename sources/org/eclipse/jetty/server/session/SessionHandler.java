package org.eclipse.jetty.server.session;

import java.io.IOException;
import java.util.EnumSet;
import java.util.EventListener;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.handler.ScopedHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class SessionHandler extends ScopedHandler {
    private SessionManager _sessionManager;
    static final Logger LOG = Log.getLogger("org.eclipse.jetty.server.session");
    public static final EnumSet<SessionTrackingMode> DEFAULT_TRACKING = EnumSet.of(SessionTrackingMode.COOKIE, SessionTrackingMode.URL);

    public SessionHandler() {
        this(new HashSessionManager());
    }

    public void addEventListener(EventListener eventListener) {
        SessionManager sessionManager = this._sessionManager;
        if (sessionManager != null) {
            sessionManager.addEventListener(eventListener);
        }
    }

    public void checkRequestedSessionId(Request request, HttpServletRequest httpServletRequest) {
        boolean z2;
        int iIndexOf;
        char cCharAt;
        Cookie[] cookies;
        String requestedSessionId = httpServletRequest.getRequestedSessionId();
        SessionManager sessionManager = getSessionManager();
        if (requestedSessionId != null && sessionManager != null) {
            HttpSession httpSession = sessionManager.getHttpSession(requestedSessionId);
            if (httpSession == null || !sessionManager.isValid(httpSession)) {
                return;
            }
            request.setSession(httpSession);
            return;
        }
        if (DispatcherType.REQUEST.equals(request.getDispatcherType())) {
            HttpSession httpSession2 = null;
            if (!this._sessionManager.isUsingCookies() || (cookies = httpServletRequest.getCookies()) == null || cookies.length <= 0) {
                z2 = false;
            } else {
                String name = sessionManager.getSessionCookieConfig().getName();
                int i2 = 0;
                z2 = false;
                while (true) {
                    if (i2 >= cookies.length) {
                        break;
                    }
                    if (name.equalsIgnoreCase(cookies[i2].getName())) {
                        requestedSessionId = cookies[i2].getValue();
                        Logger logger = LOG;
                        logger.debug("Got Session ID {} from cookie", requestedSessionId);
                        if (requestedSessionId != null) {
                            httpSession2 = sessionManager.getHttpSession(requestedSessionId);
                            if (httpSession2 != null && sessionManager.isValid(httpSession2)) {
                                z2 = true;
                                break;
                            }
                        } else {
                            logger.warn("null session id from cookie", new Object[0]);
                        }
                        z2 = true;
                    }
                    i2++;
                }
            }
            if (requestedSessionId == null || httpSession2 == null) {
                String requestURI = httpServletRequest.getRequestURI();
                String sessionIdPathParameterNamePrefix = sessionManager.getSessionIdPathParameterNamePrefix();
                if (sessionIdPathParameterNamePrefix != null && (iIndexOf = requestURI.indexOf(sessionIdPathParameterNamePrefix)) >= 0) {
                    int length = iIndexOf + sessionIdPathParameterNamePrefix.length();
                    int i3 = length;
                    while (i3 < requestURI.length() && (cCharAt = requestURI.charAt(i3)) != ';' && cCharAt != '#' && cCharAt != '?' && cCharAt != '/') {
                        i3++;
                    }
                    requestedSessionId = requestURI.substring(length, i3);
                    httpSession2 = sessionManager.getHttpSession(requestedSessionId);
                    Logger logger2 = LOG;
                    if (logger2.isDebugEnabled()) {
                        logger2.debug("Got Session ID {} from URL", requestedSessionId);
                    }
                    z2 = false;
                }
            }
            request.setRequestedSessionId(requestedSessionId);
            request.setRequestedSessionIdFromCookie(requestedSessionId != null && z2);
            if (httpSession2 == null || !sessionManager.isValid(httpSession2)) {
                return;
            }
            request.setSession(httpSession2);
        }
    }

    public void clearEventListeners() {
        SessionManager sessionManager = this._sessionManager;
        if (sessionManager != null) {
            sessionManager.clearEventListeners();
        }
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    public void doHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        if (never()) {
            nextHandle(str, request, httpServletRequest, httpServletResponse);
            return;
        }
        ScopedHandler scopedHandler = this._nextScope;
        if (scopedHandler != null && scopedHandler == this._handler) {
            scopedHandler.doHandle(str, request, httpServletRequest, httpServletResponse);
            return;
        }
        Handler handler = this._handler;
        if (handler != null) {
            handler.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    public void doScope(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Throwable {
        SessionManager sessionManager;
        HttpSession session;
        HttpSession session2;
        HttpSession httpSession = null;
        try {
            sessionManager = request.getSessionManager();
            try {
                session = request.getSession(false);
                try {
                    SessionManager sessionManager2 = this._sessionManager;
                    if (sessionManager != sessionManager2) {
                        request.setSessionManager(sessionManager2);
                        request.setSession(null);
                        checkRequestedSessionId(request, httpServletRequest);
                    }
                    if (this._sessionManager != null) {
                        session2 = request.getSession(false);
                        if (session2 == null) {
                            session2 = request.recoverNewSession(this._sessionManager);
                            if (session2 != null) {
                                request.setSession(session2);
                            }
                        } else if (session2 != session) {
                            try {
                                HttpCookie httpCookieAccess = this._sessionManager.access(session2, httpServletRequest.isSecure());
                                if (httpCookieAccess != null) {
                                    request.getResponse().addCookie(httpCookieAccess);
                                }
                                httpSession = session2;
                            } catch (Throwable th) {
                                th = th;
                                httpSession = session2;
                                if (httpSession != null) {
                                    this._sessionManager.complete(httpSession);
                                }
                                HttpSession session3 = request.getSession(false);
                                if (session3 != null && session == null && session3 != httpSession) {
                                    this._sessionManager.complete(session3);
                                }
                                if (sessionManager != null && sessionManager != this._sessionManager) {
                                    request.setSessionManager(sessionManager);
                                    request.setSession(session);
                                }
                                throw th;
                            }
                        }
                        HttpSession httpSession2 = session2;
                        session2 = null;
                        httpSession = httpSession2;
                    } else {
                        session2 = null;
                    }
                    Logger logger = LOG;
                    if (logger.isDebugEnabled()) {
                        logger.debug("sessionManager=" + this._sessionManager, new Object[0]);
                        logger.debug("session=" + httpSession, new Object[0]);
                    }
                    ScopedHandler scopedHandler = this._nextScope;
                    if (scopedHandler != null) {
                        scopedHandler.doScope(str, request, httpServletRequest, httpServletResponse);
                    } else {
                        ScopedHandler scopedHandler2 = this._outerScope;
                        if (scopedHandler2 != null) {
                            scopedHandler2.doHandle(str, request, httpServletRequest, httpServletResponse);
                        } else {
                            doHandle(str, request, httpServletRequest, httpServletResponse);
                        }
                    }
                    if (session2 != null) {
                        this._sessionManager.complete(session2);
                    }
                    HttpSession session4 = request.getSession(false);
                    if (session4 != null && session == null && session4 != session2) {
                        this._sessionManager.complete(session4);
                    }
                    if (sessionManager == null || sessionManager == this._sessionManager) {
                        return;
                    }
                    request.setSessionManager(sessionManager);
                    request.setSession(session);
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                session = null;
            }
        } catch (Throwable th4) {
            th = th4;
            sessionManager = null;
            session = null;
        }
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler, org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        this._sessionManager.start();
        super.doStart();
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        this._sessionManager.stop();
        super.doStop();
    }

    public SessionManager getSessionManager() {
        return this._sessionManager;
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        Server server2 = getServer();
        if (server2 != null && server2 != server) {
            server2.getContainer().update((Object) this, (Object) this._sessionManager, (Object) null, "sessionManager", true);
        }
        super.setServer(server);
        if (server == null || server == server2) {
            return;
        }
        server.getContainer().update((Object) this, (Object) null, (Object) this._sessionManager, "sessionManager", true);
    }

    public void setSessionManager(SessionManager sessionManager) {
        if (isStarted()) {
            throw new IllegalStateException();
        }
        SessionManager sessionManager2 = this._sessionManager;
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object) sessionManager2, (Object) sessionManager, "sessionManager", true);
        }
        if (sessionManager != null) {
            sessionManager.setSessionHandler(this);
        }
        this._sessionManager = sessionManager;
        if (sessionManager2 != null) {
            sessionManager2.setSessionHandler(null);
        }
    }

    public SessionHandler(SessionManager sessionManager) {
        setSessionManager(sessionManager);
    }
}
