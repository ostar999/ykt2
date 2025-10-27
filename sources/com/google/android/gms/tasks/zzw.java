package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
final class zzw implements Continuation<Void, List<Object>> {
    private final /* synthetic */ Collection zzae;

    public zzw(Collection collection) {
        this.zzae = collection;
    }

    @Override // com.google.android.gms.tasks.Continuation
    public final /* synthetic */ List<Object> then(@NonNull Task<Void> task) throws Exception {
        if (this.zzae.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = this.zzae.iterator();
        while (it.hasNext()) {
            arrayList.add(((Task) it.next()).getResult());
        }
        return arrayList;
    }
}
