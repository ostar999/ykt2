package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes3.dex */
final class zzx implements Continuation<Void, Task<List<Task<?>>>> {
    private final /* synthetic */ Collection zzae;

    public zzx(Collection collection) {
        this.zzae = collection;
    }

    @Override // com.google.android.gms.tasks.Continuation
    public final /* synthetic */ Task<List<Task<?>>> then(@NonNull Task<Void> task) throws Exception {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.zzae);
        return Tasks.forResult(arrayList);
    }
}
