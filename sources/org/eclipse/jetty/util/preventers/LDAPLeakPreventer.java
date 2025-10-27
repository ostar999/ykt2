package org.eclipse.jetty.util.preventers;

/* loaded from: classes9.dex */
public class LDAPLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) throws ClassNotFoundException {
        try {
            Class.forName("com.sun.jndi.LdapPoolManager", true, classLoader);
        } catch (ClassNotFoundException e2) {
            AbstractLeakPreventer.LOG.ignore(e2);
        }
    }
}
