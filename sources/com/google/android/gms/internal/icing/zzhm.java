package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;

/* loaded from: classes3.dex */
public final class zzhm {

    public static final class zza extends zzdx<zza, C0148zza> implements zzfj {
        private static volatile zzfr<zza> zzbc;
        private static final zza zzrs;
        private int zzbd;
        private String zzqs = "";
        private zzc zzrr;

        /* renamed from: com.google.android.gms.internal.icing.zzhm$zza$zza, reason: collision with other inner class name */
        public static final class C0148zza extends zzdx.zzb<zza, C0148zza> implements zzfj {
            private C0148zza() {
                super(zza.zzrs);
            }

            public final C0148zza zzb(zzc zzcVar) {
                if (this.zzki) {
                    zzbt();
                    this.zzki = false;
                }
                ((zza) this.zzkh).zza(zzcVar);
                return this;
            }

            public final C0148zza zzu(String str) {
                if (this.zzki) {
                    zzbt();
                    this.zzki = false;
                }
                ((zza) this.zzkh).setName(str);
                return this;
            }

            public /* synthetic */ C0148zza(zzho zzhoVar) {
                this();
            }
        }

        static {
            zza zzaVar = new zza();
            zzrs = zzaVar;
            zzdx.zza((Class<zza>) zza.class, zzaVar);
        }

        private zza() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setName(String str) {
            str.getClass();
            this.zzbd |= 1;
            this.zzqs = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzc zzcVar) {
            zzcVar.getClass();
            this.zzrr = zzcVar;
            this.zzbd |= 2;
        }

        public static C0148zza zzec() {
            return zzrs.zzbk();
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.icing.zzdx$zza, com.google.android.gms.internal.icing.zzfr<com.google.android.gms.internal.icing.zzhm$zza>] */
        @Override // com.google.android.gms.internal.icing.zzdx
        public final Object zza(int i2, Object obj, Object obj2) {
            zzfr<zza> zzfrVar;
            zzho zzhoVar = null;
            switch (zzho.zzaz[i2 - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0148zza(zzhoVar);
                case 3:
                    return zzdx.zza(zzrs, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\b\u0000\u0002\t\u0001", new Object[]{"zzbd", "zzqs", "zzrr"});
                case 4:
                    return zzrs;
                case 5:
                    zzfr<zza> zzfrVar2 = zzbc;
                    zzfr<zza> zzfrVar3 = zzfrVar2;
                    if (zzfrVar2 == null) {
                        synchronized (zza.class) {
                            zzfr<zza> zzfrVar4 = zzbc;
                            zzfrVar = zzfrVar4;
                            if (zzfrVar4 == null) {
                                ?? zzaVar = new zzdx.zza(zzrs);
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
    }

    public static final class zzc extends zzdx<zzc, zza> implements zzfj {
        private static volatile zzfr<zzc> zzbc;
        private static final zzc zzsa;
        private int zzbd;
        private boolean zzrv;
        private String zzrw = "";
        private long zzrx;
        private double zzry;
        private zzb zzrz;

        public static final class zza extends zzdx.zzb<zzc, zza> implements zzfj {
            private zza() {
                super(zzc.zzsa);
            }

            public final zza zzb(zzb zzbVar) {
                if (this.zzki) {
                    zzbt();
                    this.zzki = false;
                }
                ((zzc) this.zzkh).zza(zzbVar);
                return this;
            }

            public final zza zzj(boolean z2) {
                if (this.zzki) {
                    zzbt();
                    this.zzki = false;
                }
                ((zzc) this.zzkh).zzi(z2);
                return this;
            }

            public final zza zzx(String str) {
                if (this.zzki) {
                    zzbt();
                    this.zzki = false;
                }
                ((zzc) this.zzkh).zzv(str);
                return this;
            }

            public /* synthetic */ zza(zzho zzhoVar) {
                this();
            }
        }

        static {
            zzc zzcVar = new zzc();
            zzsa = zzcVar;
            zzdx.zza((Class<zzc>) zzc.class, zzcVar);
        }

        private zzc() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzb zzbVar) {
            zzbVar.getClass();
            this.zzrz = zzbVar;
            this.zzbd |= 16;
        }

        public static zza zzeg() {
            return zzsa.zzbk();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzi(boolean z2) {
            this.zzbd |= 1;
            this.zzrv = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzv(String str) {
            str.getClass();
            this.zzbd |= 2;
            this.zzrw = str;
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.icing.zzdx$zza, com.google.android.gms.internal.icing.zzfr<com.google.android.gms.internal.icing.zzhm$zzc>] */
        @Override // com.google.android.gms.internal.icing.zzdx
        public final Object zza(int i2, Object obj, Object obj2) {
            zzfr<zzc> zzfrVar;
            zzho zzhoVar = null;
            switch (zzho.zzaz[i2 - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza(zzhoVar);
                case 3:
                    return zzdx.zza(zzsa, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\u0007\u0000\u0002\b\u0001\u0003\u0002\u0002\u0004\u0000\u0003\u0005\t\u0004", new Object[]{"zzbd", "zzrv", "zzrw", "zzrx", "zzry", "zzrz"});
                case 4:
                    return zzsa;
                case 5:
                    zzfr<zzc> zzfrVar2 = zzbc;
                    zzfr<zzc> zzfrVar3 = zzfrVar2;
                    if (zzfrVar2 == null) {
                        synchronized (zzc.class) {
                            zzfr<zzc> zzfrVar4 = zzbc;
                            zzfrVar = zzfrVar4;
                            if (zzfrVar4 == null) {
                                ?? zzaVar = new zzdx.zza(zzsa);
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
    }

    public static final class zzb extends zzdx<zzb, zza> implements zzfj {
        private static volatile zzfr<zzb> zzbc;
        private static final zzb zzru;
        private int zzbd;
        private String zzra = "";
        private zzee<zza> zzrt = zzdx.zzbp();

        public static final class zza extends zzdx.zzb<zzb, zza> implements zzfj {
            private zza() {
                super(zzb.zzru);
            }

            public final zza zzb(zza zzaVar) {
                if (this.zzki) {
                    zzbt();
                    this.zzki = false;
                }
                ((zzb) this.zzkh).zza(zzaVar);
                return this;
            }

            public final zza zzw(String str) {
                if (this.zzki) {
                    zzbt();
                    this.zzki = false;
                }
                ((zzb) this.zzkh).zzt(str);
                return this;
            }

            public /* synthetic */ zza(zzho zzhoVar) {
                this();
            }
        }

        static {
            zzb zzbVar = new zzb();
            zzru = zzbVar;
            zzdx.zza((Class<zzb>) zzb.class, zzbVar);
        }

        private zzb() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zza zzaVar) {
            zzaVar.getClass();
            if (!this.zzrt.zzah()) {
                this.zzrt = zzdx.zza(this.zzrt);
            }
            this.zzrt.add(zzaVar);
        }

        public static zza zzee() {
            return zzru.zzbk();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzt(String str) {
            str.getClass();
            this.zzbd |= 1;
            this.zzra = str;
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.icing.zzdx$zza, com.google.android.gms.internal.icing.zzfr<com.google.android.gms.internal.icing.zzhm$zzb>] */
        @Override // com.google.android.gms.internal.icing.zzdx
        public final Object zza(int i2, Object obj, Object obj2) {
            zzfr<zzb> zzfrVar;
            zzho zzhoVar = null;
            switch (zzho.zzaz[i2 - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzhoVar);
                case 3:
                    return zzdx.zza(zzru, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\b\u0000\u0002\u001b", new Object[]{"zzbd", "zzra", "zzrt", zza.class});
                case 4:
                    return zzru;
                case 5:
                    zzfr<zzb> zzfrVar2 = zzbc;
                    zzfr<zzb> zzfrVar3 = zzfrVar2;
                    if (zzfrVar2 == null) {
                        synchronized (zzb.class) {
                            zzfr<zzb> zzfrVar4 = zzbc;
                            zzfrVar = zzfrVar4;
                            if (zzfrVar4 == null) {
                                ?? zzaVar = new zzdx.zza(zzru);
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
    }
}
