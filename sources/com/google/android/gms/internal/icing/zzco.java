package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzcm;
import com.google.android.gms.internal.icing.zzco;

/* loaded from: classes3.dex */
public abstract class zzco<MessageType extends zzcm<MessageType, BuilderType>, BuilderType extends zzco<MessageType, BuilderType>> implements zzfg {
    public abstract BuilderType zza(MessageType messagetype);

    @Override // com.google.android.gms.internal.icing.zzfg
    public final /* synthetic */ zzfg zza(zzfh zzfhVar) {
        if (zzbr().getClass().isInstance(zzfhVar)) {
            return zza((zzco<MessageType, BuilderType>) zzfhVar);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }

    @Override // 
    /* renamed from: zzag, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType clone();
}
