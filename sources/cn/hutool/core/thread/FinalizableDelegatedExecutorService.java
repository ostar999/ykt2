package cn.hutool.core.thread;

import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
public class FinalizableDelegatedExecutorService extends DelegatedExecutorService {
    public FinalizableDelegatedExecutorService(ExecutorService executorService) {
        super(executorService);
    }

    public void finalize() {
        super.shutdown();
    }
}
