package org.eclipse.jetty.util.preventers;

import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes9.dex */
public class GCThreadLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Class.forName("sun.misc.GC").getMethod("requestLatency", Long.TYPE).invoke(null, Long.valueOf(TimestampAdjuster.MODE_SHARED));
        } catch (ClassNotFoundException e2) {
            AbstractLeakPreventer.LOG.ignore(e2);
        } catch (Exception e3) {
            AbstractLeakPreventer.LOG.warn(e3);
        }
    }
}
