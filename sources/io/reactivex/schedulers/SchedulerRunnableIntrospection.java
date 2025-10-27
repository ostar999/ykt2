package io.reactivex.schedulers;

import io.reactivex.annotations.NonNull;

/* loaded from: classes8.dex */
public interface SchedulerRunnableIntrospection {
    @NonNull
    Runnable getWrappedRunnable();
}
