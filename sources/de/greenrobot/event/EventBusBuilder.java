package de.greenrobot.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes8.dex */
public class EventBusBuilder {
    private static final ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    List<Class<?>> skipMethodVerificationForClasses;
    boolean throwSubscriberException;
    boolean logSubscriberExceptions = true;
    boolean logNoSubscriberMessages = true;
    boolean sendSubscriberExceptionEvent = true;
    boolean sendNoSubscriberEvent = true;
    boolean eventInheritance = true;
    ExecutorService executorService = DEFAULT_EXECUTOR_SERVICE;

    public EventBus build() {
        return new EventBus(this);
    }

    public EventBusBuilder eventInheritance(boolean z2) {
        this.eventInheritance = z2;
        return this;
    }

    public EventBusBuilder executorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    public EventBus installDefaultEventBus() {
        EventBus eventBus;
        synchronized (EventBus.class) {
            if (EventBus.defaultInstance != null) {
                throw new EventBusException("Default instance already exists. It may be only set once before it's used the first time to ensure consistent behavior.");
            }
            EventBus.defaultInstance = build();
            eventBus = EventBus.defaultInstance;
        }
        return eventBus;
    }

    public EventBusBuilder logNoSubscriberMessages(boolean z2) {
        this.logNoSubscriberMessages = z2;
        return this;
    }

    public EventBusBuilder logSubscriberExceptions(boolean z2) {
        this.logSubscriberExceptions = z2;
        return this;
    }

    public EventBusBuilder sendNoSubscriberEvent(boolean z2) {
        this.sendNoSubscriberEvent = z2;
        return this;
    }

    public EventBusBuilder sendSubscriberExceptionEvent(boolean z2) {
        this.sendSubscriberExceptionEvent = z2;
        return this;
    }

    public EventBusBuilder skipMethodVerificationFor(Class<?> cls) {
        if (this.skipMethodVerificationForClasses == null) {
            this.skipMethodVerificationForClasses = new ArrayList();
        }
        this.skipMethodVerificationForClasses.add(cls);
        return this;
    }

    public EventBusBuilder throwSubscriberException(boolean z2) {
        this.throwSubscriberException = z2;
        return this;
    }
}
