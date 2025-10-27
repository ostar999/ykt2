package org.eclipse.jetty.util.preventers;

/* loaded from: classes9.dex */
public class Java2DLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) throws ClassNotFoundException {
        try {
            Class.forName("sun.java2d.Disposer", true, classLoader);
        } catch (ClassNotFoundException e2) {
            AbstractLeakPreventer.LOG.ignore(e2);
        }
    }
}
