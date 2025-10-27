package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import com.google.common.eventbus.EventBus;
import java.util.concurrent.Executor;
import org.eclipse.jetty.servlet.ServletHandler;

@Beta
/* loaded from: classes4.dex */
public class AsyncEventBus extends EventBus {
    public AsyncEventBus(String str, Executor executor) {
        super(str, executor, Dispatcher.legacyAsync(), EventBus.LoggingHandler.INSTANCE);
    }

    public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
        super(ServletHandler.__DEFAULT_SERVLET, executor, Dispatcher.legacyAsync(), subscriberExceptionHandler);
    }

    public AsyncEventBus(Executor executor) {
        super(ServletHandler.__DEFAULT_SERVLET, executor, Dispatcher.legacyAsync(), EventBus.LoggingHandler.INSTANCE);
    }
}
