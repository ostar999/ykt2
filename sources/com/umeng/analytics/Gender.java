package com.umeng.analytics;

import java.util.Locale;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'Male' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes6.dex */
public class Gender {
    public static final Gender Female;
    public static final Gender Male;
    public static final Gender Unknown;

    /* renamed from: a, reason: collision with root package name */
    private static final /* synthetic */ Gender[] f22350a;
    public int value;

    /* renamed from: com.umeng.analytics.Gender$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f22351a;

        static {
            int[] iArr = new int[Gender.values().length];
            f22351a = iArr;
            try {
                iArr[Gender.Male.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f22351a[Gender.Female.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f22351a[Gender.Unknown.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static {
        int i2 = 0;
        int i3 = 1;
        Gender gender = new Gender("Male", i2, i3) { // from class: com.umeng.analytics.Gender.1
            @Override // java.lang.Enum
            public String toString() {
                return String.format(Locale.US, "Male:%d", Integer.valueOf(this.value));
            }
        };
        Male = gender;
        int i4 = 2;
        Gender gender2 = new Gender("Female", i3, i4) { // from class: com.umeng.analytics.Gender.2
            @Override // java.lang.Enum
            public String toString() {
                return String.format(Locale.US, "Female:%d", Integer.valueOf(this.value));
            }
        };
        Female = gender2;
        Gender gender3 = new Gender("Unknown", i4, i2) { // from class: com.umeng.analytics.Gender.3
            @Override // java.lang.Enum
            public String toString() {
                return String.format(Locale.US, "Unknown:%d", Integer.valueOf(this.value));
            }
        };
        Unknown = gender3;
        f22350a = new Gender[]{gender, gender2, gender3};
    }

    public static Gender getGender(int i2) {
        return i2 != 1 ? i2 != 2 ? Unknown : Female : Male;
    }

    public static com.umeng.commonsdk.statistics.proto.Gender transGender(Gender gender) {
        int i2 = AnonymousClass4.f22351a[gender.ordinal()];
        return i2 != 1 ? i2 != 2 ? com.umeng.commonsdk.statistics.proto.Gender.UNKNOWN : com.umeng.commonsdk.statistics.proto.Gender.FEMALE : com.umeng.commonsdk.statistics.proto.Gender.MALE;
    }

    public static Gender valueOf(String str) {
        return (Gender) Enum.valueOf(Gender.class, str);
    }

    public static Gender[] values() {
        return (Gender[]) f22350a.clone();
    }

    public int value() {
        return this.value;
    }

    private Gender(String str, int i2, int i3) {
        this.value = i3;
    }
}
