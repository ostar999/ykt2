package com.google.android.gms.internal.icing;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzpk' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes3.dex */
public class zzha {
    public static final zzha zzpi;
    public static final zzha zzpj;
    public static final zzha zzpk;
    public static final zzha zzpl;
    public static final zzha zzpm;
    public static final zzha zzpn;
    public static final zzha zzpo;
    public static final zzha zzpp;
    public static final zzha zzpq;
    public static final zzha zzpr;
    public static final zzha zzps;
    public static final zzha zzpt;
    public static final zzha zzpu;
    public static final zzha zzpv;
    public static final zzha zzpw;
    public static final zzha zzpx;
    public static final zzha zzpy;
    public static final zzha zzpz;
    private static final /* synthetic */ zzha[] zzqc;
    private final zzhh zzqa;
    private final int zzqb;

    static {
        zzha zzhaVar = new zzha("DOUBLE", 0, zzhh.DOUBLE, 1);
        zzpi = zzhaVar;
        zzha zzhaVar2 = new zzha("FLOAT", 1, zzhh.FLOAT, 5);
        zzpj = zzhaVar2;
        zzhh zzhhVar = zzhh.LONG;
        final int i2 = 2;
        zzha zzhaVar3 = new zzha("INT64", 2, zzhhVar, 0);
        zzpk = zzhaVar3;
        final int i3 = 3;
        zzha zzhaVar4 = new zzha("UINT64", 3, zzhhVar, 0);
        zzpl = zzhaVar4;
        zzhh zzhhVar2 = zzhh.INT;
        zzha zzhaVar5 = new zzha("INT32", 4, zzhhVar2, 0);
        zzpm = zzhaVar5;
        zzha zzhaVar6 = new zzha("FIXED64", 5, zzhhVar, 1);
        zzpn = zzhaVar6;
        zzha zzhaVar7 = new zzha("FIXED32", 6, zzhhVar2, 5);
        zzpo = zzhaVar7;
        zzha zzhaVar8 = new zzha("BOOL", 7, zzhh.BOOLEAN, 0);
        zzpp = zzhaVar8;
        final zzhh zzhhVar3 = zzhh.STRING;
        final String str = "STRING";
        final int i4 = 8;
        zzha zzhaVar9 = new zzha(str, i4, zzhhVar3, i2) { // from class: com.google.android.gms.internal.icing.zzhd
            {
                int i5 = 8;
                int i6 = 2;
                zzhb zzhbVar = null;
            }
        };
        zzpq = zzhaVar9;
        final zzhh zzhhVar4 = zzhh.MESSAGE;
        final String str2 = "GROUP";
        final int i5 = 9;
        zzha zzhaVar10 = new zzha(str2, i5, zzhhVar4, i3) { // from class: com.google.android.gms.internal.icing.zzhc
            {
                int i6 = 9;
                int i7 = 3;
                zzhb zzhbVar = null;
            }
        };
        zzpr = zzhaVar10;
        final String str3 = "MESSAGE";
        final int i6 = 10;
        final int i7 = 2;
        zzha zzhaVar11 = new zzha(str3, i6, zzhhVar4, i7) { // from class: com.google.android.gms.internal.icing.zzhf
            {
                int i8 = 10;
                int i9 = 2;
                zzhb zzhbVar = null;
            }
        };
        zzps = zzhaVar11;
        final zzhh zzhhVar5 = zzhh.BYTE_STRING;
        final String str4 = "BYTES";
        final int i8 = 11;
        zzha zzhaVar12 = new zzha(str4, i8, zzhhVar5, i7) { // from class: com.google.android.gms.internal.icing.zzhe
            {
                int i9 = 11;
                int i10 = 2;
                zzhb zzhbVar = null;
            }
        };
        zzpt = zzhaVar12;
        zzha zzhaVar13 = new zzha("UINT32", 12, zzhhVar2, 0);
        zzpu = zzhaVar13;
        zzha zzhaVar14 = new zzha("ENUM", 13, zzhh.ENUM, 0);
        zzpv = zzhaVar14;
        zzha zzhaVar15 = new zzha("SFIXED32", 14, zzhhVar2, 5);
        zzpw = zzhaVar15;
        zzha zzhaVar16 = new zzha("SFIXED64", 15, zzhhVar, 1);
        zzpx = zzhaVar16;
        zzha zzhaVar17 = new zzha("SINT32", 16, zzhhVar2, 0);
        zzpy = zzhaVar17;
        zzha zzhaVar18 = new zzha("SINT64", 17, zzhhVar, 0);
        zzpz = zzhaVar18;
        zzqc = new zzha[]{zzhaVar, zzhaVar2, zzhaVar3, zzhaVar4, zzhaVar5, zzhaVar6, zzhaVar7, zzhaVar8, zzhaVar9, zzhaVar10, zzhaVar11, zzhaVar12, zzhaVar13, zzhaVar14, zzhaVar15, zzhaVar16, zzhaVar17, zzhaVar18};
    }

    private zzha(String str, int i2, zzhh zzhhVar, int i3) {
        this.zzqa = zzhhVar;
        this.zzqb = i3;
    }

    public static zzha[] values() {
        return (zzha[]) zzqc.clone();
    }

    public final zzhh zzdt() {
        return this.zzqa;
    }

    public final int zzdu() {
        return this.zzqb;
    }

    public /* synthetic */ zzha(String str, int i2, zzhh zzhhVar, int i3, zzhb zzhbVar) {
        this(str, i2, zzhhVar, i3);
    }
}
