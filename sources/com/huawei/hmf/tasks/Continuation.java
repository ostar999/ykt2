package com.huawei.hmf.tasks;

/* loaded from: classes4.dex */
public interface Continuation<TResult, TContinuationResult> {
    TContinuationResult then(Task<TResult> task) throws Exception;
}
