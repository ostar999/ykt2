package com.google.android.gms.common;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

/* loaded from: classes3.dex */
final /* synthetic */ class zab implements SuccessContinuation {
    static final SuccessContinuation zaar = new zab();

    private zab() {
    }

    @Override // com.google.android.gms.tasks.SuccessContinuation
    public final Task then(Object obj) {
        return Tasks.forResult(null);
    }
}
