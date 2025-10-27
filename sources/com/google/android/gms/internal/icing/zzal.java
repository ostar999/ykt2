package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;

/* loaded from: classes3.dex */
public final class zzal {

    public static final class zza extends zzdx<zza, C0145zza> implements zzfj {
        private static final zza zzbb;
        private static volatile zzfr<zza> zzbc;
        private zzee<zzb> zzba = zzdx.zzbp();

        /* renamed from: com.google.android.gms.internal.icing.zzal$zza$zza, reason: collision with other inner class name */
        public static final class C0145zza extends zzdx.zzb<zza, C0145zza> implements zzfj {
            private C0145zza() {
                super(zza.zzbb);
            }

            public final C0145zza zza(Iterable<? extends zzb> iterable) {
                if (this.zzki) {
                    zzbt();
                    this.zzki = false;
                }
                ((zza) this.zzkh).zzb(iterable);
                return this;
            }

            public /* synthetic */ C0145zza(zzak zzakVar) {
                this();
            }
        }

        static {
            zza zzaVar = new zza();
            zzbb = zzaVar;
            zzdx.zza((Class<zza>) zza.class, zzaVar);
        }

        private zza() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(Iterable<? extends zzb> iterable) {
            if (!this.zzba.zzah()) {
                this.zzba = zzdx.zza(this.zzba);
            }
            zzcm.zza(iterable, this.zzba);
        }

        public static C0145zza zzf() {
            return zzbb.zzbk();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.icing.zzdx$zza, com.google.android.gms.internal.icing.zzfr<com.google.android.gms.internal.icing.zzal$zza>] */
        @Override // com.google.android.gms.internal.icing.zzdx
        public final Object zza(int i2, Object obj, Object obj2) {
            zzfr<zza> zzfrVar;
            zzak zzakVar = null;
            switch (zzak.zzaz[i2 - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0145zza(zzakVar);
                case 3:
                    return zzdx.zza(zzbb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzba", zzb.class});
                case 4:
                    return zzbb;
                case 5:
                    zzfr<zza> zzfrVar2 = zzbc;
                    zzfr<zza> zzfrVar3 = zzfrVar2;
                    if (zzfrVar2 == null) {
                        synchronized (zza.class) {
                            zzfr<zza> zzfrVar4 = zzbc;
                            zzfrVar = zzfrVar4;
                            if (zzfrVar4 == null) {
                                ?? zzaVar = new zzdx.zza(zzbb);
                                zzbc = zzaVar;
                                zzfrVar = zzaVar;
                            }
                        }
                        zzfrVar3 = zzfrVar;
                    }
                    return zzfrVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static final class zzb extends zzdx<zzb, C0146zza> implements zzfj {
            private static volatile zzfr<zzb> zzbc;
            private static final zzb zzbh;
            private int zzbd;
            private String zzbe = "";
            private String zzbf = "";
            private int zzbg;

            /* renamed from: com.google.android.gms.internal.icing.zzal$zza$zzb$zza, reason: collision with other inner class name */
            public static final class C0146zza extends zzdx.zzb<zzb, C0146zza> implements zzfj {
                private C0146zza() {
                    super(zzb.zzbh);
                }

                public final C0146zza zzd(int i2) {
                    if (this.zzki) {
                        zzbt();
                        this.zzki = false;
                    }
                    ((zzb) this.zzkh).zze(i2);
                    return this;
                }

                public final C0146zza zze(String str) {
                    if (this.zzki) {
                        zzbt();
                        this.zzki = false;
                    }
                    ((zzb) this.zzkh).zzg(str);
                    return this;
                }

                public final C0146zza zzf(String str) {
                    if (this.zzki) {
                        zzbt();
                        this.zzki = false;
                    }
                    ((zzb) this.zzkh).zzh(str);
                    return this;
                }

                public /* synthetic */ C0146zza(zzak zzakVar) {
                    this();
                }
            }

            static {
                zzb zzbVar = new zzb();
                zzbh = zzbVar;
                zzdx.zza((Class<zzb>) zzb.class, zzbVar);
            }

            private zzb() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zze(int i2) {
                this.zzbd |= 4;
                this.zzbg = i2;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zzg(String str) {
                str.getClass();
                this.zzbd |= 1;
                this.zzbe = str;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zzh(String str) {
                str.getClass();
                this.zzbd |= 2;
                this.zzbf = str;
            }

            /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.icing.zzdx$zza, com.google.android.gms.internal.icing.zzfr<com.google.android.gms.internal.icing.zzal$zza$zzb>] */
            @Override // com.google.android.gms.internal.icing.zzdx
            public final Object zza(int i2, Object obj, Object obj2) {
                zzfr<zzb> zzfrVar;
                zzak zzakVar = null;
                switch (zzak.zzaz[i2 - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new C0146zza(zzakVar);
                    case 3:
                        return zzdx.zza(zzbh, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\u0004\u0002", new Object[]{"zzbd", "zzbe", "zzbf", "zzbg"});
                    case 4:
                        return zzbh;
                    case 5:
                        zzfr<zzb> zzfrVar2 = zzbc;
                        zzfr<zzb> zzfrVar3 = zzfrVar2;
                        if (zzfrVar2 == null) {
                            synchronized (zzb.class) {
                                zzfr<zzb> zzfrVar4 = zzbc;
                                zzfrVar = zzfrVar4;
                                if (zzfrVar4 == null) {
                                    ?? zzaVar = new zzdx.zza(zzbh);
                                    zzbc = zzaVar;
                                    zzfrVar = zzaVar;
                                }
                            }
                            zzfrVar3 = zzfrVar;
                        }
                        return zzfrVar3;
                    case 6:
                        return (byte) 1;
                    case 7:
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            public static C0146zza zzh() {
                return zzbh.zzbk();
            }
        }
    }
}
