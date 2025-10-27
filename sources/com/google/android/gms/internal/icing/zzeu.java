package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;

/* loaded from: classes3.dex */
final class zzeu implements zzfx {
    private static final zzfe zzme = new zzex();
    private final zzfe zzmd;

    public zzeu() {
        this(new zzew(zzdy.zzbs(), zzch()));
    }

    private static boolean zza(zzff zzffVar) {
        return zzffVar.zzco() == zzdx.zze.zzku;
    }

    private static zzfe zzch() {
        try {
            return (zzfe) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzme;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzfx
    public final <T> zzfu<T> zzd(Class<T> cls) {
        zzfw.zzf((Class<?>) cls);
        zzff zzffVarZzc = this.zzmd.zzc(cls);
        return zzffVarZzc.zzcp() ? zzdx.class.isAssignableFrom(cls) ? zzfk.zza(zzfw.zzda(), zzdp.zzbb(), zzffVarZzc.zzcq()) : zzfk.zza(zzfw.zzcy(), zzdp.zzbc(), zzffVarZzc.zzcq()) : zzdx.class.isAssignableFrom(cls) ? zza(zzffVarZzc) ? zzfl.zza(cls, zzffVarZzc, zzfo.zzcs(), zzer.zzcg(), zzfw.zzda(), zzdp.zzbb(), zzfc.zzcm()) : zzfl.zza(cls, zzffVarZzc, zzfo.zzcs(), zzer.zzcg(), zzfw.zzda(), null, zzfc.zzcm()) : zza(zzffVarZzc) ? zzfl.zza(cls, zzffVarZzc, zzfo.zzcr(), zzer.zzcf(), zzfw.zzcy(), zzdp.zzbc(), zzfc.zzcl()) : zzfl.zza(cls, zzffVarZzc, zzfo.zzcr(), zzer.zzcf(), zzfw.zzcz(), null, zzfc.zzcl());
    }

    private zzeu(zzfe zzfeVar) {
        this.zzmd = (zzfe) zzeb.zza(zzfeVar, "messageInfoFactory");
    }
}
