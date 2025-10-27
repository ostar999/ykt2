package com.vivo.push.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import com.vivo.push.model.InsideNotificationItem;

/* loaded from: classes6.dex */
public final class h implements BaseNotifyDataAdapter {

    /* renamed from: e, reason: collision with root package name */
    private static int f24442e;

    /* renamed from: f, reason: collision with root package name */
    private static int f24443f;

    /* renamed from: a, reason: collision with root package name */
    private Resources f24444a;

    /* renamed from: b, reason: collision with root package name */
    private String f24445b;

    /* renamed from: c, reason: collision with root package name */
    private String f24446c;

    /* renamed from: d, reason: collision with root package name */
    private String f24447d;

    private static boolean a(int i2) {
        return (i2 == -1 || i2 == 0) ? false : true;
    }

    private static boolean a(String str) {
        if (Build.VERSION.SDK_INT < 26) {
            return false;
        }
        if (!TextUtils.isEmpty(str)) {
            return true;
        }
        p.d("DefaultNotifyDataAdapter", "systemVersion is not suit ");
        return false;
    }

    @Override // com.vivo.push.util.BaseNotifyDataAdapter
    public final int getDefaultNotifyIcon() {
        if (a(f24442e)) {
            return f24442e;
        }
        String str = this.f24447d;
        int iA = !a(str) ? -1 : a(str, "_notifyicon");
        f24442e = iA;
        if (a(iA)) {
            return f24442e;
        }
        for (String strSubstring = this.f24446c; !TextUtils.isEmpty(strSubstring); strSubstring = strSubstring.substring(0, strSubstring.length() - 1)) {
            int identifier = this.f24444a.getIdentifier("vivo_push_rom" + strSubstring + "_notifyicon", "drawable", this.f24445b);
            if (identifier > 0) {
                return identifier;
            }
        }
        return this.f24444a.getIdentifier("vivo_push_notifyicon", "drawable", this.f24445b);
    }

    @Override // com.vivo.push.util.BaseNotifyDataAdapter
    public final int getDefaultSmallIconId() {
        if (a(f24443f)) {
            return f24443f;
        }
        String str = this.f24447d;
        int iA = !a(str) ? -1 : a(str, "_icon");
        f24443f = iA;
        if (a(iA)) {
            return f24443f;
        }
        for (String strSubstring = this.f24446c; !TextUtils.isEmpty(strSubstring); strSubstring = strSubstring.substring(0, strSubstring.length() - 1)) {
            int identifier = this.f24444a.getIdentifier("vivo_push_rom" + strSubstring + "_icon", "drawable", this.f24445b);
            if (identifier > 0) {
                return identifier;
            }
        }
        return this.f24444a.getIdentifier("vivo_push_icon", "drawable", this.f24445b);
    }

    @Override // com.vivo.push.util.BaseNotifyDataAdapter
    public final int getNotifyMode(InsideNotificationItem insideNotificationItem) {
        return 2;
    }

    @Override // com.vivo.push.util.BaseNotifyDataAdapter
    public final void init(Context context) {
        this.f24445b = context.getPackageName();
        this.f24444a = context.getResources();
        this.f24446c = j.a();
        this.f24447d = Build.VERSION.RELEASE;
    }

    private int a(String str, String str2) throws NumberFormatException {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String[] strArrSplit = str.split("\\.");
            if (strArrSplit != null && strArrSplit.length > 0) {
                str = strArrSplit[0];
            }
            try {
                for (int i2 = Integer.parseInt(str); i2 > 0; i2--) {
                    String str3 = "vivo_push_ard" + i2 + str2;
                    p.c("DefaultNotifyDataAdapter", "get notify icon : ".concat(String.valueOf(str3)));
                    int identifier = this.f24444a.getIdentifier(str3, "drawable", this.f24445b);
                    if (identifier > 0) {
                        p.c("DefaultNotifyDataAdapter", "find notify icon : ".concat(String.valueOf(str3)));
                        return identifier;
                    }
                }
            } catch (Exception e2) {
                p.a("DefaultNotifyDataAdapter", e2);
            }
        }
        return -1;
    }
}
