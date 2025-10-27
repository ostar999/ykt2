package com.google.android.gms.internal.icing;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzlf' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes3.dex */
public final class zzej {
    public static final zzej zzle;
    public static final zzej zzlf;
    public static final zzej zzlg;
    public static final zzej zzlh;
    public static final zzej zzli;
    public static final zzej zzlj;
    public static final zzej zzlk;
    public static final zzej zzll;
    public static final zzej zzlm;
    public static final zzej zzln;
    private static final /* synthetic */ zzej[] zzlr;
    private final Class<?> zzlo;
    private final Class<?> zzlp;
    private final Object zzlq;

    static {
        zzej zzejVar = new zzej("VOID", 0, Void.class, Void.class, null);
        zzle = zzejVar;
        Class cls = Integer.TYPE;
        zzej zzejVar2 = new zzej("INT", 1, cls, Integer.class, 0);
        zzlf = zzejVar2;
        zzej zzejVar3 = new zzej("LONG", 2, Long.TYPE, Long.class, 0L);
        zzlg = zzejVar3;
        zzej zzejVar4 = new zzej("FLOAT", 3, Float.TYPE, Float.class, Float.valueOf(0.0f));
        zzlh = zzejVar4;
        zzej zzejVar5 = new zzej("DOUBLE", 4, Double.TYPE, Double.class, Double.valueOf(0.0d));
        zzli = zzejVar5;
        zzej zzejVar6 = new zzej("BOOLEAN", 5, Boolean.TYPE, Boolean.class, Boolean.FALSE);
        zzlj = zzejVar6;
        zzej zzejVar7 = new zzej("STRING", 6, String.class, String.class, "");
        zzlk = zzejVar7;
        zzej zzejVar8 = new zzej("BYTE_STRING", 7, zzct.class, zzct.class, zzct.zzgi);
        zzll = zzejVar8;
        zzej zzejVar9 = new zzej("ENUM", 8, cls, Integer.class, null);
        zzlm = zzejVar9;
        zzej zzejVar10 = new zzej("MESSAGE", 9, Object.class, Object.class, null);
        zzln = zzejVar10;
        zzlr = new zzej[]{zzejVar, zzejVar2, zzejVar3, zzejVar4, zzejVar5, zzejVar6, zzejVar7, zzejVar8, zzejVar9, zzejVar10};
    }

    private zzej(String str, int i2, Class cls, Class cls2, Object obj) {
        this.zzlo = cls;
        this.zzlp = cls2;
        this.zzlq = obj;
    }

    public static zzej[] values() {
        return (zzej[]) zzlr.clone();
    }

    public final Class<?> zzcb() {
        return this.zzlp;
    }
}
