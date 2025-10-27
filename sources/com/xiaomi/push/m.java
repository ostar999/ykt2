package com.xiaomi.push;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import com.hjq.permissions.Permission;

/* loaded from: classes6.dex */
public class m {
    public static Account a(Context context) {
        Account[] accountsByType;
        try {
            if (m675a(context) && (accountsByType = AccountManager.get(context).getAccountsByType("com.xiaomi")) != null && accountsByType.length > 0) {
                return accountsByType[0];
            }
            return null;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m675a(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            if (packageManager.checkPermission(Permission.GET_ACCOUNTS, packageName) != 0) {
                if (packageManager.checkPermission("android.permission.GET_ACCOUNTS_PRIVILEGED", packageName) != 0) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}
