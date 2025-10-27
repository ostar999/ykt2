package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import k.a;

/* loaded from: classes8.dex */
public final class SchedulerPoolFactory {
    static final String PURGE_ENABLED_KEY = "rx3.purge-enabled";
    public static final boolean PURGE_ENABLED = getBooleanProperty(true, PURGE_ENABLED_KEY, true, true, new SystemPropertyAccessor());

    public static final class SystemPropertyAccessor implements Function<String, String> {
        @Override // io.reactivex.rxjava3.functions.Function
        public String apply(String t2) {
            return System.getProperty(t2);
        }
    }

    private SchedulerPoolFactory() {
        throw new IllegalStateException("No instances!");
    }

    public static ScheduledExecutorService create(ThreadFactory factory) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, factory);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(PURGE_ENABLED);
        return scheduledThreadPoolExecutor;
    }

    public static boolean getBooleanProperty(boolean enabled, String key, boolean defaultNotFound, boolean defaultNotEnabled, Function<String, String> propertyAccessor) {
        if (!enabled) {
            return defaultNotEnabled;
        }
        try {
            String strApply = propertyAccessor.apply(key);
            return strApply == null ? defaultNotFound : a.f27523u.equals(strApply);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            return defaultNotFound;
        }
    }
}
