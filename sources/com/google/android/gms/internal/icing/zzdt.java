package com.google.android.gms.internal.icing;

import java.lang.reflect.Type;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzho' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes3.dex */
public final class zzdt {
    private static final zzdt zzho;
    private static final zzdt zzhp;
    private static final zzdt zzhq;
    private static final zzdt zzhr;
    private static final zzdt zzhs;
    private static final zzdt zzht;
    private static final zzdt zzhu;
    private static final zzdt zzhv;
    private static final zzdt zzhw;
    private static final zzdt zzhx;
    private static final zzdt zzhy;
    private static final zzdt zzhz;
    private static final zzdt zzia;
    private static final zzdt zzib;
    private static final zzdt zzic;
    private static final zzdt zzid;
    private static final zzdt zzie;
    private static final zzdt zzif;
    private static final zzdt zzig;
    private static final zzdt zzih;
    private static final zzdt zzii;
    private static final zzdt zzij;
    private static final zzdt zzik;
    private static final zzdt zzil;
    private static final zzdt zzim;
    private static final zzdt zzin;
    private static final zzdt zzio;
    private static final zzdt zzip;
    private static final zzdt zziq;
    private static final zzdt zzir;
    private static final zzdt zzis;
    private static final zzdt zzit;
    private static final zzdt zziu;
    private static final zzdt zziv;
    private static final zzdt zziw;
    public static final zzdt zzix;
    private static final zzdt zziy;
    private static final zzdt zziz;
    private static final zzdt zzja;
    private static final zzdt zzjb;
    private static final zzdt zzjc;
    private static final zzdt zzjd;
    private static final zzdt zzje;
    private static final zzdt zzjf;
    private static final zzdt zzjg;
    private static final zzdt zzjh;
    private static final zzdt zzji;
    private static final zzdt zzjj;
    public static final zzdt zzjk;
    private static final zzdt zzjl;
    private static final zzdt zzjm;
    private static final zzdt[] zzjr;
    private static final Type[] zzjs;
    private static final /* synthetic */ zzdt[] zzjt;
    private final int id;
    private final zzej zzjn;
    private final zzdv zzjo;
    private final Class<?> zzjp;
    private final boolean zzjq;

    static {
        zzdv zzdvVar = zzdv.SCALAR;
        zzej zzejVar = zzej.zzli;
        zzdt zzdtVar = new zzdt("DOUBLE", 0, 0, zzdvVar, zzejVar);
        zzho = zzdtVar;
        zzej zzejVar2 = zzej.zzlh;
        zzdt zzdtVar2 = new zzdt("FLOAT", 1, 1, zzdvVar, zzejVar2);
        zzhp = zzdtVar2;
        zzej zzejVar3 = zzej.zzlg;
        zzdt zzdtVar3 = new zzdt("INT64", 2, 2, zzdvVar, zzejVar3);
        zzhq = zzdtVar3;
        zzdt zzdtVar4 = new zzdt("UINT64", 3, 3, zzdvVar, zzejVar3);
        zzhr = zzdtVar4;
        zzej zzejVar4 = zzej.zzlf;
        zzdt zzdtVar5 = new zzdt("INT32", 4, 4, zzdvVar, zzejVar4);
        zzhs = zzdtVar5;
        zzdt zzdtVar6 = new zzdt("FIXED64", 5, 5, zzdvVar, zzejVar3);
        zzht = zzdtVar6;
        zzdt zzdtVar7 = new zzdt("FIXED32", 6, 6, zzdvVar, zzejVar4);
        zzhu = zzdtVar7;
        zzej zzejVar5 = zzej.zzlj;
        zzdt zzdtVar8 = new zzdt("BOOL", 7, 7, zzdvVar, zzejVar5);
        zzhv = zzdtVar8;
        zzej zzejVar6 = zzej.zzlk;
        zzdt zzdtVar9 = new zzdt("STRING", 8, 8, zzdvVar, zzejVar6);
        zzhw = zzdtVar9;
        zzej zzejVar7 = zzej.zzln;
        zzdt zzdtVar10 = new zzdt("MESSAGE", 9, 9, zzdvVar, zzejVar7);
        zzhx = zzdtVar10;
        zzej zzejVar8 = zzej.zzll;
        zzdt zzdtVar11 = new zzdt("BYTES", 10, 10, zzdvVar, zzejVar8);
        zzhy = zzdtVar11;
        zzdt zzdtVar12 = new zzdt("UINT32", 11, 11, zzdvVar, zzejVar4);
        zzhz = zzdtVar12;
        zzej zzejVar9 = zzej.zzlm;
        zzdt zzdtVar13 = new zzdt("ENUM", 12, 12, zzdvVar, zzejVar9);
        zzia = zzdtVar13;
        zzdt zzdtVar14 = new zzdt("SFIXED32", 13, 13, zzdvVar, zzejVar4);
        zzib = zzdtVar14;
        zzdt zzdtVar15 = new zzdt("SFIXED64", 14, 14, zzdvVar, zzejVar3);
        zzic = zzdtVar15;
        zzdt zzdtVar16 = new zzdt("SINT32", 15, 15, zzdvVar, zzejVar4);
        zzid = zzdtVar16;
        zzdt zzdtVar17 = new zzdt("SINT64", 16, 16, zzdvVar, zzejVar3);
        zzie = zzdtVar17;
        zzdt zzdtVar18 = new zzdt("GROUP", 17, 17, zzdvVar, zzejVar7);
        zzif = zzdtVar18;
        zzdv zzdvVar2 = zzdv.VECTOR;
        zzdt zzdtVar19 = new zzdt("DOUBLE_LIST", 18, 18, zzdvVar2, zzejVar);
        zzig = zzdtVar19;
        zzdt zzdtVar20 = new zzdt("FLOAT_LIST", 19, 19, zzdvVar2, zzejVar2);
        zzih = zzdtVar20;
        zzdt zzdtVar21 = new zzdt("INT64_LIST", 20, 20, zzdvVar2, zzejVar3);
        zzii = zzdtVar21;
        zzdt zzdtVar22 = new zzdt("UINT64_LIST", 21, 21, zzdvVar2, zzejVar3);
        zzij = zzdtVar22;
        zzdt zzdtVar23 = new zzdt("INT32_LIST", 22, 22, zzdvVar2, zzejVar4);
        zzik = zzdtVar23;
        zzdt zzdtVar24 = new zzdt("FIXED64_LIST", 23, 23, zzdvVar2, zzejVar3);
        zzil = zzdtVar24;
        zzdt zzdtVar25 = new zzdt("FIXED32_LIST", 24, 24, zzdvVar2, zzejVar4);
        zzim = zzdtVar25;
        zzdt zzdtVar26 = new zzdt("BOOL_LIST", 25, 25, zzdvVar2, zzejVar5);
        zzin = zzdtVar26;
        zzdt zzdtVar27 = new zzdt("STRING_LIST", 26, 26, zzdvVar2, zzejVar6);
        zzio = zzdtVar27;
        zzdt zzdtVar28 = new zzdt("MESSAGE_LIST", 27, 27, zzdvVar2, zzejVar7);
        zzip = zzdtVar28;
        zzdt zzdtVar29 = new zzdt("BYTES_LIST", 28, 28, zzdvVar2, zzejVar8);
        zziq = zzdtVar29;
        zzdt zzdtVar30 = new zzdt("UINT32_LIST", 29, 29, zzdvVar2, zzejVar4);
        zzir = zzdtVar30;
        zzdt zzdtVar31 = new zzdt("ENUM_LIST", 30, 30, zzdvVar2, zzejVar9);
        zzis = zzdtVar31;
        zzdt zzdtVar32 = new zzdt("SFIXED32_LIST", 31, 31, zzdvVar2, zzejVar4);
        zzit = zzdtVar32;
        zzdt zzdtVar33 = new zzdt("SFIXED64_LIST", 32, 32, zzdvVar2, zzejVar3);
        zziu = zzdtVar33;
        zzdt zzdtVar34 = new zzdt("SINT32_LIST", 33, 33, zzdvVar2, zzejVar4);
        zziv = zzdtVar34;
        zzdt zzdtVar35 = new zzdt("SINT64_LIST", 34, 34, zzdvVar2, zzejVar3);
        zziw = zzdtVar35;
        zzdv zzdvVar3 = zzdv.PACKED_VECTOR;
        zzdt zzdtVar36 = new zzdt("DOUBLE_LIST_PACKED", 35, 35, zzdvVar3, zzejVar);
        zzix = zzdtVar36;
        zzdt zzdtVar37 = new zzdt("FLOAT_LIST_PACKED", 36, 36, zzdvVar3, zzejVar2);
        zziy = zzdtVar37;
        zzdt zzdtVar38 = new zzdt("INT64_LIST_PACKED", 37, 37, zzdvVar3, zzejVar3);
        zziz = zzdtVar38;
        zzdt zzdtVar39 = new zzdt("UINT64_LIST_PACKED", 38, 38, zzdvVar3, zzejVar3);
        zzja = zzdtVar39;
        zzdt zzdtVar40 = new zzdt("INT32_LIST_PACKED", 39, 39, zzdvVar3, zzejVar4);
        zzjb = zzdtVar40;
        zzdt zzdtVar41 = new zzdt("FIXED64_LIST_PACKED", 40, 40, zzdvVar3, zzejVar3);
        zzjc = zzdtVar41;
        zzdt zzdtVar42 = new zzdt("FIXED32_LIST_PACKED", 41, 41, zzdvVar3, zzejVar4);
        zzjd = zzdtVar42;
        zzdt zzdtVar43 = new zzdt("BOOL_LIST_PACKED", 42, 42, zzdvVar3, zzejVar5);
        zzje = zzdtVar43;
        zzdt zzdtVar44 = new zzdt("UINT32_LIST_PACKED", 43, 43, zzdvVar3, zzejVar4);
        zzjf = zzdtVar44;
        zzdt zzdtVar45 = new zzdt("ENUM_LIST_PACKED", 44, 44, zzdvVar3, zzejVar9);
        zzjg = zzdtVar45;
        zzdt zzdtVar46 = new zzdt("SFIXED32_LIST_PACKED", 45, 45, zzdvVar3, zzejVar4);
        zzjh = zzdtVar46;
        zzdt zzdtVar47 = new zzdt("SFIXED64_LIST_PACKED", 46, 46, zzdvVar3, zzejVar3);
        zzji = zzdtVar47;
        zzdt zzdtVar48 = new zzdt("SINT32_LIST_PACKED", 47, 47, zzdvVar3, zzejVar4);
        zzjj = zzdtVar48;
        zzdt zzdtVar49 = new zzdt("SINT64_LIST_PACKED", 48, 48, zzdvVar3, zzejVar3);
        zzjk = zzdtVar49;
        zzdt zzdtVar50 = new zzdt("GROUP_LIST", 49, 49, zzdvVar2, zzejVar7);
        zzjl = zzdtVar50;
        zzdt zzdtVar51 = new zzdt("MAP", 50, 50, zzdv.MAP, zzej.zzle);
        zzjm = zzdtVar51;
        zzjt = new zzdt[]{zzdtVar, zzdtVar2, zzdtVar3, zzdtVar4, zzdtVar5, zzdtVar6, zzdtVar7, zzdtVar8, zzdtVar9, zzdtVar10, zzdtVar11, zzdtVar12, zzdtVar13, zzdtVar14, zzdtVar15, zzdtVar16, zzdtVar17, zzdtVar18, zzdtVar19, zzdtVar20, zzdtVar21, zzdtVar22, zzdtVar23, zzdtVar24, zzdtVar25, zzdtVar26, zzdtVar27, zzdtVar28, zzdtVar29, zzdtVar30, zzdtVar31, zzdtVar32, zzdtVar33, zzdtVar34, zzdtVar35, zzdtVar36, zzdtVar37, zzdtVar38, zzdtVar39, zzdtVar40, zzdtVar41, zzdtVar42, zzdtVar43, zzdtVar44, zzdtVar45, zzdtVar46, zzdtVar47, zzdtVar48, zzdtVar49, zzdtVar50, zzdtVar51};
        zzjs = new Type[0];
        zzdt[] zzdtVarArrValues = values();
        zzjr = new zzdt[zzdtVarArrValues.length];
        for (zzdt zzdtVar52 : zzdtVarArrValues) {
            zzjr[zzdtVar52.id] = zzdtVar52;
        }
    }

    private zzdt(String str, int i2, int i3, zzdv zzdvVar, zzej zzejVar) {
        int i4;
        this.id = i3;
        this.zzjo = zzdvVar;
        this.zzjn = zzejVar;
        int i5 = zzdw.zzka[zzdvVar.ordinal()];
        if (i5 == 1 || i5 == 2) {
            this.zzjp = zzejVar.zzcb();
        } else {
            this.zzjp = null;
        }
        this.zzjq = (zzdvVar != zzdv.SCALAR || (i4 = zzdw.zzkb[zzejVar.ordinal()]) == 1 || i4 == 2 || i4 == 3) ? false : true;
    }

    public static zzdt[] values() {
        return (zzdt[]) zzjt.clone();
    }

    public final int id() {
        return this.id;
    }
}
