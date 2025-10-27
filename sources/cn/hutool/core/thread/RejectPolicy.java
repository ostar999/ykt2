package cn.hutool.core.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/* loaded from: classes.dex */
public enum RejectPolicy {
    ABORT(new ThreadPoolExecutor.AbortPolicy()),
    DISCARD(new ThreadPoolExecutor.DiscardPolicy()),
    DISCARD_OLDEST(new ThreadPoolExecutor.DiscardOldestPolicy()),
    CALLER_RUNS(new ThreadPoolExecutor.CallerRunsPolicy()),
    BLOCK(new BlockPolicy());

    private final RejectedExecutionHandler value;

    RejectPolicy(RejectedExecutionHandler rejectedExecutionHandler) {
        this.value = rejectedExecutionHandler;
    }

    public RejectedExecutionHandler getValue() {
        return this.value;
    }
}
