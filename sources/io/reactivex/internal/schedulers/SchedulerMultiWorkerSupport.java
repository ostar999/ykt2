package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

/* loaded from: classes8.dex */
public interface SchedulerMultiWorkerSupport {

    public interface WorkerCallback {
        void onWorker(int i2, @NonNull Scheduler.Worker worker);
    }

    void createWorkers(int i2, @NonNull WorkerCallback workerCallback);
}
