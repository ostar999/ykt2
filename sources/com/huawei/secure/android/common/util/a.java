package com.huawei.secure.android.common.util;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private String f8465a;

    /* renamed from: b, reason: collision with root package name */
    private Character f8466b;

    /* renamed from: c, reason: collision with root package name */
    private Character f8467c;

    /* renamed from: d, reason: collision with root package name */
    private int f8468d = 0;

    /* renamed from: e, reason: collision with root package name */
    private int f8469e = 0;

    public a(String str) {
        this.f8465a = str;
    }

    public static boolean c(Character ch) {
        char cCharValue;
        return ch != null && (cCharValue = ch.charValue()) >= '0' && cCharValue <= '7';
    }

    public void a(Character ch) {
        this.f8466b = ch;
    }

    public int b() {
        return this.f8468d;
    }

    public Character d() {
        Character ch = this.f8466b;
        if (ch != null) {
            this.f8466b = null;
            return ch;
        }
        String str = this.f8465a;
        if (str == null || str.length() == 0 || this.f8468d >= this.f8465a.length()) {
            return null;
        }
        String str2 = this.f8465a;
        int i2 = this.f8468d;
        this.f8468d = i2 + 1;
        return Character.valueOf(str2.charAt(i2));
    }

    public Character e() {
        Character chD = d();
        if (chD != null && b(chD)) {
            return chD;
        }
        return null;
    }

    public Character f() {
        Character chD = d();
        if (chD != null && c(chD)) {
            return chD;
        }
        return null;
    }

    public Character g() {
        Character ch = this.f8466b;
        if (ch != null) {
            return ch;
        }
        String str = this.f8465a;
        if (str == null || str.length() == 0 || this.f8468d >= this.f8465a.length()) {
            return null;
        }
        return Character.valueOf(this.f8465a.charAt(this.f8468d));
    }

    public String h() {
        String strSubstring = this.f8465a.substring(this.f8468d);
        if (this.f8466b == null) {
            return strSubstring;
        }
        return this.f8466b + strSubstring;
    }

    public void i() {
        this.f8466b = this.f8467c;
        this.f8468d = this.f8469e;
    }

    public static boolean b(Character ch) {
        if (ch == null) {
            return false;
        }
        char cCharValue = ch.charValue();
        return (cCharValue >= '0' && cCharValue <= '9') || (cCharValue >= 'a' && cCharValue <= 'f') || (cCharValue >= 'A' && cCharValue <= 'F');
    }

    public boolean a() {
        if (this.f8466b != null) {
            return true;
        }
        String str = this.f8465a;
        return (str == null || str.length() == 0 || this.f8468d >= this.f8465a.length()) ? false : true;
    }

    public void c() {
        this.f8467c = this.f8466b;
        this.f8469e = this.f8468d;
    }

    public boolean a(char c3) {
        Character ch = this.f8466b;
        if (ch != null && ch.charValue() == c3) {
            return true;
        }
        String str = this.f8465a;
        return str != null && str.length() != 0 && this.f8468d < this.f8465a.length() && this.f8465a.charAt(this.f8468d) == c3;
    }
}
