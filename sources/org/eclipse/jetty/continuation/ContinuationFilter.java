package org.eclipse.jetty.continuation;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;

/* loaded from: classes9.dex */
public class ContinuationFilter implements Filter {
    static boolean __debug;
    static boolean _initialized;
    ServletContext _context;
    private boolean _debug;
    private boolean _faux;
    private boolean _filtered;
    private boolean _jetty6;

    public interface FilteredContinuation extends Continuation {
        boolean enter(ServletResponse servletResponse);

        boolean exit();
    }

    private void debug(String str) {
        if (this._debug) {
            this._context.log(str);
        }
    }

    public void destroy() {
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doFilter(javax.servlet.ServletRequest r7, javax.servlet.ServletResponse r8, javax.servlet.FilterChain r9) throws javax.servlet.ServletException, java.io.IOException {
        /*
            r6 = this;
            boolean r0 = r6._filtered
            if (r0 == 0) goto L6b
            java.lang.String r0 = "org.eclipse.jetty.continuation"
            java.lang.Object r1 = r7.getAttribute(r0)
            org.eclipse.jetty.continuation.Continuation r1 = (org.eclipse.jetty.continuation.Continuation) r1
            boolean r2 = r6._faux
            if (r2 == 0) goto L1f
            if (r1 == 0) goto L16
            boolean r2 = r1 instanceof org.eclipse.jetty.continuation.FauxContinuation
            if (r2 != 0) goto L1f
        L16:
            org.eclipse.jetty.continuation.FauxContinuation r1 = new org.eclipse.jetty.continuation.FauxContinuation
            r1.<init>(r7)
            r7.setAttribute(r0, r1)
            goto L21
        L1f:
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r1 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r1
        L21:
            r2 = 0
        L22:
            r3 = r2
        L23:
            if (r3 != 0) goto L75
            r3 = 1
            if (r1 == 0) goto L2e
            boolean r4 = r1.enter(r8)     // Catch: java.lang.Throwable -> L42 org.eclipse.jetty.continuation.ContinuationThrowable -> L44
            if (r4 == 0) goto L31
        L2e:
            r9.doFilter(r7, r8)     // Catch: java.lang.Throwable -> L42 org.eclipse.jetty.continuation.ContinuationThrowable -> L44
        L31:
            if (r1 != 0) goto L39
            java.lang.Object r1 = r7.getAttribute(r0)
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r1 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r1
        L39:
            if (r1 == 0) goto L23
            boolean r4 = r1.exit()
            if (r4 == 0) goto L22
            goto L23
        L42:
            r8 = move-exception
            goto L5b
        L44:
            r4 = move-exception
            java.lang.String r5 = "faux"
            r6.debug(r5, r4)     // Catch: java.lang.Throwable -> L42
            if (r1 != 0) goto L52
            java.lang.Object r1 = r7.getAttribute(r0)
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r1 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r1
        L52:
            if (r1 == 0) goto L23
            boolean r4 = r1.exit()
            if (r4 == 0) goto L22
            goto L23
        L5b:
            if (r1 != 0) goto L64
            java.lang.Object r7 = r7.getAttribute(r0)
            r1 = r7
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r1 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r1
        L64:
            if (r1 == 0) goto L6a
            boolean r7 = r1.exit()
        L6a:
            throw r8
        L6b:
            r9.doFilter(r7, r8)     // Catch: org.eclipse.jetty.continuation.ContinuationThrowable -> L6f
            goto L75
        L6f:
            r7 = move-exception
            java.lang.String r8 = "caught"
            r6.debug(r8, r7)
        L75:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.continuation.ContinuationFilter.doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain):void");
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        boolean zEquals = "org.eclipse.jetty.servlet".equals(filterConfig.getClass().getPackage().getName());
        this._context = filterConfig.getServletContext();
        String initParameter = filterConfig.getInitParameter("debug");
        boolean z2 = initParameter != null && Boolean.parseBoolean(initParameter);
        this._debug = z2;
        if (z2) {
            __debug = true;
        }
        String initParameter2 = filterConfig.getInitParameter("jetty6");
        if (initParameter2 == null) {
            initParameter2 = filterConfig.getInitParameter("partial");
        }
        if (initParameter2 != null) {
            this._jetty6 = Boolean.parseBoolean(initParameter2);
        } else {
            this._jetty6 = ContinuationSupport.__jetty6 && !zEquals;
        }
        String initParameter3 = filterConfig.getInitParameter("faux");
        if (initParameter3 != null) {
            this._faux = Boolean.parseBoolean(initParameter3);
        } else {
            this._faux = (zEquals || this._jetty6 || this._context.getMajorVersion() >= 3) ? false : true;
        }
        this._filtered = this._faux || this._jetty6;
        if (this._debug) {
            this._context.log("ContinuationFilter  jetty=" + zEquals + " jetty6=" + this._jetty6 + " faux=" + this._faux + " filtered=" + this._filtered + " servlet3=" + ContinuationSupport.__servlet3);
        }
        _initialized = true;
    }

    private void debug(String str, Throwable th) {
        if (this._debug) {
            if (th instanceof ContinuationThrowable) {
                this._context.log(str + ":" + th);
                return;
            }
            this._context.log(str, th);
        }
    }
}
