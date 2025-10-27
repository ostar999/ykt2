package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zacm extends com.google.android.gms.internal.base.zar {
    private final /* synthetic */ zack zaky;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zacm(zack zackVar, Looper looper) {
        super(looper);
        this.zaky = zackVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 0) {
            PendingResult<?> pendingResult = (PendingResult) message.obj;
            synchronized (this.zaky.zadp) {
                if (pendingResult == null) {
                    this.zaky.zaks.zad(new Status(13, "Transform returned null"));
                } else if (pendingResult instanceof zacc) {
                    this.zaky.zaks.zad(((zacc) pendingResult).getStatus());
                } else {
                    this.zaky.zaks.zaa(pendingResult);
                }
            }
            return;
        }
        if (i2 == 1) {
            RuntimeException runtimeException = (RuntimeException) message.obj;
            String strValueOf = String.valueOf(runtimeException.getMessage());
            Log.e("TransformedResultImpl", strValueOf.length() != 0 ? "Runtime exception on the transformation worker thread: ".concat(strValueOf) : new String("Runtime exception on the transformation worker thread: "));
            throw runtimeException;
        }
        StringBuilder sb = new StringBuilder(70);
        sb.append("TransformationResultHandler received unknown message type: ");
        sb.append(i2);
        Log.e("TransformedResultImpl", sb.toString());
    }
}
