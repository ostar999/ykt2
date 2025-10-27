package com.google.firebase.appindexing.internal;

import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndexingInvalidArgumentException;
import com.google.firebase.appindexing.FirebaseUserActions;

/* loaded from: classes4.dex */
public final class zzn extends FirebaseUserActions {
    private zzp zzfj;

    public zzn(Context context) {
        this.zzfj = new zzp(context);
    }

    private final Task<Void> zza(int i2, Action action) {
        zza[] zzaVarArr = new zza[1];
        if (action != null) {
            if (!(action instanceof zza)) {
                return Tasks.forException(new FirebaseAppIndexingInvalidArgumentException("Custom Action objects are not allowed. Please use the 'Actions' or 'ActionBuilder' class for creating Action objects."));
            }
            zza zzaVar = (zza) action;
            zzaVarArr[0] = zzaVar;
            zzaVar.zzab().zzf(i2);
        }
        return this.zzfj.doWrite(new zzq(this, zzaVarArr));
    }

    @Override // com.google.firebase.appindexing.FirebaseUserActions
    public final Task<Void> end(Action action) {
        return zza(2, action);
    }

    @Override // com.google.firebase.appindexing.FirebaseUserActions
    public final Task<Void> start(Action action) {
        return zza(1, action);
    }
}
