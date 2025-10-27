package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;

/* loaded from: classes8.dex */
public interface SchedulerMultiWorkerSupport {

    public interface WorkerCallback {
        void onWorker(int index, @NonNull Scheduler.Worker worker);
    }

    void createWorkers(int number, @NonNull WorkerCallback callback);
}
