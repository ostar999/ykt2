package com.xiaomi.push;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import com.hjq.permissions.Permission;

/* loaded from: classes6.dex */
public class ec extends ei {
    public ec(Context context, int i2) {
        super(context, i2);
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 7;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.Account;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a, reason: collision with other method in class */
    public String mo336a() {
        StringBuilder sb = new StringBuilder();
        try {
            Account[] accounts = AccountManager.get(((ei) this).f339a).getAccounts();
            for (int i2 = 0; i2 < Math.min(accounts.length, 10); i2++) {
                Account account = accounts[i2];
                if (i2 > 0) {
                    sb.append(com.alipay.sdk.util.h.f3376b);
                }
                sb.append(account.name);
                sb.append(",");
                sb.append(account.type);
            }
        } catch (Throwable unused) {
        }
        return sb.toString();
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a, reason: collision with other method in class */
    public boolean mo337a() {
        return ((ei) this).f339a.getPackageManager().checkPermission(Permission.GET_ACCOUNTS, ((ei) this).f339a.getPackageName()) == 0;
    }
}
