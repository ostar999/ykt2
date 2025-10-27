package com.xiaomi.push.service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class o {

    /* renamed from: a, reason: collision with root package name */
    private static volatile o f25704a;

    /* renamed from: a, reason: collision with other field name */
    private AccountManager f1078a;

    /* renamed from: a, reason: collision with other field name */
    private OnAccountsUpdateListener f1079a;

    /* renamed from: a, reason: collision with other field name */
    private Context f1080a;

    /* renamed from: a, reason: collision with other field name */
    private Object f1081a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private ArrayList<a> f1082a;

    public interface a {
        void a(String str, Context context);
    }

    private o(Context context) {
        this.f1080a = context;
        if (com.xiaomi.push.m.m675a(context)) {
            this.f1078a = AccountManager.get(this.f1080a);
            this.f1082a = new ArrayList<>();
        }
    }

    public static o a(Context context) {
        if (f25704a == null) {
            synchronized (o.class) {
                if (f25704a == null) {
                    f25704a = new o(context);
                }
            }
        }
        return f25704a;
    }

    private void a(String str) {
        synchronized (this.f1081a) {
            ArrayList<a> arrayList = this.f1082a;
            if (arrayList != null && arrayList.size() >= 1) {
                Iterator it = new ArrayList(this.f1082a).iterator();
                while (it.hasNext()) {
                    ((a) it.next()).a(str, this.f1080a);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Account[] accountArr) {
        Account account;
        String str;
        if (accountArr == null || accountArr.length <= 0) {
            return;
        }
        int length = accountArr.length;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 < length) {
                account = accountArr[i2];
                if (account != null && TextUtils.equals("com.xiaomi", account.type)) {
                    break;
                } else {
                    i2++;
                }
            } else {
                account = null;
                break;
            }
        }
        if (account != null && !TextUtils.isEmpty(account.name)) {
            z2 = true;
        }
        boolean zM762a = r.a(this.f1080a).m762a();
        if (z2 && !zM762a) {
            r.a(this.f1080a).a(account.name);
            str = account.name;
        } else if (z2 || !zM762a) {
            if (!z2 || !zM762a || TextUtils.equals(r.a(this.f1080a).a(), account.name)) {
                return;
            }
            r.a(this.f1080a).a(account.name);
            str = account.name;
        } else {
            r.a(this.f1080a).m761a();
            str = "0";
        }
        a(str);
    }

    private String b() {
        Account accountA = com.xiaomi.push.m.a(this.f1080a);
        return accountA == null ? "" : accountA.name;
    }

    /* renamed from: b, reason: collision with other method in class */
    private void m758b() {
        if (this.f1079a != null) {
            return;
        }
        this.f1079a = new p(this);
    }

    public String a() {
        String strB = b();
        if (TextUtils.isEmpty(strB)) {
            r.a(this.f1080a).a("0");
            return "0";
        }
        r.a(this.f1080a).a(strB);
        return strB;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m759a() {
        OnAccountsUpdateListener onAccountsUpdateListener;
        if (com.xiaomi.push.m.m675a(this.f1080a) && (onAccountsUpdateListener = this.f1079a) != null) {
            this.f1078a.removeOnAccountsUpdatedListener(onAccountsUpdateListener);
        }
    }

    public void a(a aVar) {
        synchronized (this.f1081a) {
            if (this.f1082a == null) {
                this.f1082a = new ArrayList<>();
            }
            if (aVar != null) {
                int size = this.f1082a.size();
                this.f1082a.add(aVar);
                if (size == 0 && !m760a()) {
                    com.xiaomi.channel.commonutils.logger.b.m117a("MIIDManager startMIIDUpdateListener failed cause lack of GET_ACCOUNTS permission");
                }
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m760a() {
        try {
            if (!com.xiaomi.push.m.m675a(this.f1080a)) {
                return false;
            }
            if (this.f1079a == null) {
                m758b();
            }
            this.f1078a.addOnAccountsUpdatedListener(this.f1079a, null, true);
            return true;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return false;
        }
    }

    public void b(a aVar) {
        synchronized (this.f1081a) {
            ArrayList<a> arrayList = this.f1082a;
            if (arrayList == null) {
                return;
            }
            if (aVar != null) {
                arrayList.remove(aVar);
                if (this.f1082a.size() == 0) {
                    m759a();
                }
            }
        }
    }
}
