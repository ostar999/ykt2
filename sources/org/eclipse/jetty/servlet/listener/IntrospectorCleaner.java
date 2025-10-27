package org.eclipse.jetty.servlet.listener;

import java.beans.Introspector;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/* loaded from: classes9.dex */
public class IntrospectorCleaner implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Introspector.flushCaches();
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }
}
