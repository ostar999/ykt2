package com.alipay.android.phone.mrpc.core;

import android.text.format.Time;
import com.yikaobang.yixue.R2;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class k {

    /* renamed from: a, reason: collision with root package name */
    private static final Pattern f2956a = Pattern.compile("([0-9]{1,2})[- ]([A-Za-z]{3,9})[- ]([0-9]{2,4})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])");

    /* renamed from: b, reason: collision with root package name */
    private static final Pattern f2957b = Pattern.compile("[ ]([A-Za-z]{3,9})[ ]+([0-9]{1,2})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])[ ]([0-9]{2,4})");

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        int f2958a;

        /* renamed from: b, reason: collision with root package name */
        int f2959b;

        /* renamed from: c, reason: collision with root package name */
        int f2960c;

        public a(int i2, int i3, int i4) {
            this.f2958a = i2;
            this.f2959b = i3;
            this.f2960c = i4;
        }
    }

    public static long a(String str) {
        int iC;
        int iD;
        int iB;
        a aVarE;
        int i2;
        int i3;
        int i4;
        Matcher matcher = f2956a.matcher(str);
        if (matcher.find()) {
            iB = b(matcher.group(1));
            iC = c(matcher.group(2));
            iD = d(matcher.group(3));
            aVarE = e(matcher.group(4));
        } else {
            Matcher matcher2 = f2957b.matcher(str);
            if (!matcher2.find()) {
                throw new IllegalArgumentException();
            }
            iC = c(matcher2.group(1));
            int iB2 = b(matcher2.group(2));
            a aVarE2 = e(matcher2.group(3));
            iD = d(matcher2.group(4));
            iB = iB2;
            aVarE = aVarE2;
        }
        if (iD >= 2038) {
            i3 = 0;
            i4 = 2038;
            i2 = 1;
        } else {
            i2 = iB;
            i3 = iC;
            i4 = iD;
        }
        Time time = new Time("UTC");
        time.set(aVarE.f2960c, aVarE.f2959b, aVarE.f2958a, i2, i3, i4);
        return time.toMillis(false);
    }

    private static int b(String str) {
        return str.length() == 2 ? ((str.charAt(0) - '0') * 10) + (str.charAt(1) - '0') : str.charAt(0) - '0';
    }

    private static int c(String str) {
        int lowerCase = ((Character.toLowerCase(str.charAt(0)) + Character.toLowerCase(str.charAt(1))) + Character.toLowerCase(str.charAt(2))) - 291;
        if (lowerCase == 9) {
            return 11;
        }
        if (lowerCase == 10) {
            return 1;
        }
        if (lowerCase == 22) {
            return 0;
        }
        if (lowerCase == 26) {
            return 7;
        }
        if (lowerCase == 29) {
            return 2;
        }
        if (lowerCase == 32) {
            return 3;
        }
        if (lowerCase == 40) {
            return 6;
        }
        if (lowerCase == 42) {
            return 5;
        }
        if (lowerCase == 48) {
            return 10;
        }
        switch (lowerCase) {
            case 35:
                return 9;
            case 36:
                return 4;
            case 37:
                return 8;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static int d(String str) {
        if (str.length() != 2) {
            return str.length() == 3 ? ((str.charAt(0) - '0') * 100) + ((str.charAt(1) - '0') * 10) + (str.charAt(2) - '0') + 1900 : str.length() == 4 ? ((str.charAt(0) - '0') * 1000) + ((str.charAt(1) - '0') * 100) + ((str.charAt(2) - '0') * 10) + (str.charAt(3) - '0') : R2.attr.icon_normal_left;
        }
        int iCharAt = ((str.charAt(0) - '0') * 10) + (str.charAt(1) - '0');
        return iCharAt >= 70 ? iCharAt + 1900 : iCharAt + 2000;
    }

    private static a e(String str) {
        int i2;
        int iCharAt = str.charAt(0) - '0';
        if (str.charAt(1) != ':') {
            iCharAt = (iCharAt * 10) + (str.charAt(1) - '0');
            i2 = 2;
        } else {
            i2 = 1;
        }
        int i3 = i2 + 1 + 1 + 1 + 1;
        return new a(iCharAt, ((str.charAt(r2) - '0') * 10) + (str.charAt(r3) - '0'), ((str.charAt(i3) - '0') * 10) + (str.charAt(i3 + 1) - '0'));
    }
}
