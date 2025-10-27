package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.URIUtil;

/* loaded from: classes9.dex */
public class MovedContextHandler extends ContextHandler {
    boolean _discardPathInfo;
    boolean _discardQuery;
    String _expires;
    String _newContextURL;
    boolean _permanent;
    final Redirector _redirector;

    public class Redirector extends AbstractHandler {
        private Redirector() {
        }

        @Override // org.eclipse.jetty.server.Handler
        public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
            MovedContextHandler movedContextHandler = MovedContextHandler.this;
            String strAddPaths = movedContextHandler._newContextURL;
            if (strAddPaths == null) {
                return;
            }
            if (!movedContextHandler._discardPathInfo && httpServletRequest.getPathInfo() != null) {
                strAddPaths = URIUtil.addPaths(strAddPaths, httpServletRequest.getPathInfo());
            }
            StringBuilder sb = URIUtil.hasScheme(strAddPaths) ? new StringBuilder() : request.getRootURL();
            sb.append(strAddPaths);
            if (!MovedContextHandler.this._discardQuery && httpServletRequest.getQueryString() != null) {
                sb.append('?');
                sb.append(httpServletRequest.getQueryString().replaceAll("\r\n?&=", "!"));
            }
            httpServletResponse.setHeader("Location", sb.toString());
            String str2 = MovedContextHandler.this._expires;
            if (str2 != null) {
                httpServletResponse.setHeader("Expires", str2);
            }
            httpServletResponse.setStatus(MovedContextHandler.this._permanent ? 301 : 302);
            httpServletResponse.setContentLength(0);
            request.setHandled(true);
        }
    }

    public MovedContextHandler() {
        Redirector redirector = new Redirector();
        this._redirector = redirector;
        setHandler(redirector);
        setAllowNullPathInfo(true);
    }

    public String getExpires() {
        return this._expires;
    }

    public String getNewContextURL() {
        return this._newContextURL;
    }

    public boolean isDiscardPathInfo() {
        return this._discardPathInfo;
    }

    public boolean isDiscardQuery() {
        return this._discardQuery;
    }

    public boolean isPermanent() {
        return this._permanent;
    }

    public void setDiscardPathInfo(boolean z2) {
        this._discardPathInfo = z2;
    }

    public void setDiscardQuery(boolean z2) {
        this._discardQuery = z2;
    }

    public void setExpires(String str) {
        this._expires = str;
    }

    public void setNewContextURL(String str) {
        this._newContextURL = str;
    }

    public void setPermanent(boolean z2) {
        this._permanent = z2;
    }

    public MovedContextHandler(HandlerContainer handlerContainer, String str, String str2) {
        super(handlerContainer, str);
        this._newContextURL = str2;
        Redirector redirector = new Redirector();
        this._redirector = redirector;
        setHandler(redirector);
    }
}
