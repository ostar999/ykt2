package org.eclipse.jetty.continuation;

import java.lang.reflect.Constructor;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/* loaded from: classes9.dex */
public class ContinuationSupport {
    static final boolean __jetty6;
    static final Constructor<? extends Continuation> __newJetty6Continuation;
    static final Constructor<? extends Continuation> __newServlet3Continuation;
    static final boolean __servlet3;
    static final Class<?> __waitingContinuation;

    static {
        Constructor<? extends Continuation> constructor;
        boolean z2;
        Constructor<? extends Continuation> constructor2;
        boolean z3 = true;
        try {
            if (ServletRequest.class.getMethod("startAsync", new Class[0]) != null) {
                constructor2 = ContinuationSupport.class.getClassLoader().loadClass("org.eclipse.jetty.continuation.Servlet3Continuation").asSubclass(Continuation.class).getConstructor(ServletRequest.class);
                z2 = true;
            } else {
                z2 = false;
                constructor2 = null;
            }
            __servlet3 = z2;
            __newServlet3Continuation = constructor2;
        } catch (Exception unused) {
            __servlet3 = false;
            __newServlet3Continuation = null;
        } catch (Throwable th) {
            __servlet3 = false;
            __newServlet3Continuation = null;
            throw th;
        }
        try {
            Class<?> clsLoadClass = ContinuationSupport.class.getClassLoader().loadClass("org.mortbay.util.ajax.Continuation");
            if (clsLoadClass != null) {
                constructor = ContinuationSupport.class.getClassLoader().loadClass("org.eclipse.jetty.continuation.Jetty6Continuation").asSubclass(Continuation.class).getConstructor(ServletRequest.class, clsLoadClass);
            } else {
                z3 = false;
                constructor = null;
            }
            __jetty6 = z3;
            __newJetty6Continuation = constructor;
        } catch (Exception unused2) {
            __jetty6 = false;
            __newJetty6Continuation = null;
        } catch (Throwable th2) {
            __jetty6 = false;
            __newJetty6Continuation = null;
            throw th2;
        }
        try {
            __waitingContinuation = ContinuationSupport.class.getClassLoader().loadClass("org.mortbay.util.ajax.WaitingContinuation");
        } catch (Exception unused3) {
            __waitingContinuation = null;
        } catch (Throwable th3) {
            __waitingContinuation = null;
            throw th3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x005a A[Catch: Exception -> 0x0063, TryCatch #1 {Exception -> 0x0063, blocks: (B:19:0x003f, B:21:0x0043, B:24:0x004a, B:26:0x005f, B:25:0x005a), top: B:35:0x003f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.eclipse.jetty.continuation.Continuation getContinuation(javax.servlet.ServletRequest r6) throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            java.lang.String r0 = "org.eclipse.jetty.continuation"
            java.lang.Object r1 = r6.getAttribute(r0)
            org.eclipse.jetty.continuation.Continuation r1 = (org.eclipse.jetty.continuation.Continuation) r1
            if (r1 == 0) goto Lb
            return r1
        Lb:
            boolean r1 = r6 instanceof javax.servlet.ServletRequestWrapper
            if (r1 == 0) goto L16
            javax.servlet.ServletRequestWrapper r6 = (javax.servlet.ServletRequestWrapper) r6
            javax.servlet.ServletRequest r6 = r6.getRequest()
            goto Lb
        L16:
            boolean r1 = org.eclipse.jetty.continuation.ContinuationSupport.__servlet3
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L33
            java.lang.reflect.Constructor<? extends org.eclipse.jetty.continuation.Continuation> r1 = org.eclipse.jetty.continuation.ContinuationSupport.__newServlet3Continuation     // Catch: java.lang.Exception -> L2c
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Exception -> L2c
            r3[r2] = r6     // Catch: java.lang.Exception -> L2c
            java.lang.Object r1 = r1.newInstance(r3)     // Catch: java.lang.Exception -> L2c
            org.eclipse.jetty.continuation.Continuation r1 = (org.eclipse.jetty.continuation.Continuation) r1     // Catch: java.lang.Exception -> L2c
            r6.setAttribute(r0, r1)     // Catch: java.lang.Exception -> L2c
            return r1
        L2c:
            r6 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            r0.<init>(r6)
            throw r0
        L33:
            boolean r1 = org.eclipse.jetty.continuation.ContinuationSupport.__jetty6
            if (r1 == 0) goto L6a
            java.lang.String r1 = "org.mortbay.jetty.ajax.Continuation"
            java.lang.Object r1 = r6.getAttribute(r1)
            if (r1 == 0) goto L5a
            java.lang.Class<?> r4 = org.eclipse.jetty.continuation.ContinuationSupport.__waitingContinuation     // Catch: java.lang.Exception -> L63
            if (r4 == 0) goto L5a
            boolean r4 = r4.isInstance(r1)     // Catch: java.lang.Exception -> L63
            if (r4 == 0) goto L4a
            goto L5a
        L4a:
            java.lang.reflect.Constructor<? extends org.eclipse.jetty.continuation.Continuation> r4 = org.eclipse.jetty.continuation.ContinuationSupport.__newJetty6Continuation     // Catch: java.lang.Exception -> L63
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: java.lang.Exception -> L63
            r5[r2] = r6     // Catch: java.lang.Exception -> L63
            r5[r3] = r1     // Catch: java.lang.Exception -> L63
            java.lang.Object r1 = r4.newInstance(r5)     // Catch: java.lang.Exception -> L63
            org.eclipse.jetty.continuation.Continuation r1 = (org.eclipse.jetty.continuation.Continuation) r1     // Catch: java.lang.Exception -> L63
            goto L5f
        L5a:
            org.eclipse.jetty.continuation.FauxContinuation r1 = new org.eclipse.jetty.continuation.FauxContinuation     // Catch: java.lang.Exception -> L63
            r1.<init>(r6)     // Catch: java.lang.Exception -> L63
        L5f:
            r6.setAttribute(r0, r1)     // Catch: java.lang.Exception -> L63
            return r1
        L63:
            r6 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            r0.<init>(r6)
            throw r0
        L6a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r0 = "!(Jetty || Servlet 3.0 || ContinuationFilter)"
            r6.<init>(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.continuation.ContinuationSupport.getContinuation(javax.servlet.ServletRequest):org.eclipse.jetty.continuation.Continuation");
    }

    @Deprecated
    public static Continuation getContinuation(ServletRequest servletRequest, ServletResponse servletResponse) {
        return getContinuation(servletRequest);
    }
}
