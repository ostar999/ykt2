package org.eclipse.jetty.util.preventers;

/* loaded from: classes9.dex */
public class LoginConfigurationLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) throws ClassNotFoundException {
        try {
            Class.forName("javax.security.auth.login.Configuration", true, classLoader);
        } catch (ClassNotFoundException e2) {
            AbstractLeakPreventer.LOG.warn(e2);
        }
    }
}
