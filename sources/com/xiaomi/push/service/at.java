package com.xiaomi.push.service;

import android.content.Context;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import com.xiaomi.push.service.XMPushService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public class at {

    /* renamed from: a, reason: collision with root package name */
    private static at f25590a;

    /* renamed from: a, reason: collision with other field name */
    private ConcurrentHashMap<String, HashMap<String, b>> f1003a = new ConcurrentHashMap<>();

    /* renamed from: a, reason: collision with other field name */
    private List<a> f1002a = new ArrayList();

    public interface a {
        void a();
    }

    public static class b {

        /* renamed from: a, reason: collision with other field name */
        public Context f1004a;

        /* renamed from: a, reason: collision with other field name */
        Messenger f1006a;

        /* renamed from: a, reason: collision with other field name */
        private XMPushService f1008a;

        /* renamed from: a, reason: collision with other field name */
        public d f1011a;

        /* renamed from: a, reason: collision with other field name */
        public String f1012a;

        /* renamed from: a, reason: collision with other field name */
        public boolean f1014a;

        /* renamed from: b, reason: collision with other field name */
        public String f1015b;

        /* renamed from: c, reason: collision with root package name */
        public String f25593c;

        /* renamed from: d, reason: collision with root package name */
        public String f25594d;

        /* renamed from: e, reason: collision with root package name */
        public String f25595e;

        /* renamed from: f, reason: collision with root package name */
        public String f25596f;

        /* renamed from: g, reason: collision with root package name */
        public String f25597g;

        /* renamed from: h, reason: collision with root package name */
        public String f25598h;

        /* renamed from: i, reason: collision with root package name */
        public String f25599i;

        /* renamed from: a, reason: collision with other field name */
        c f1010a = c.unbind;

        /* renamed from: a, reason: collision with root package name */
        private int f25591a = 0;

        /* renamed from: a, reason: collision with other field name */
        private List<a> f1013a = new ArrayList();

        /* renamed from: b, reason: collision with root package name */
        c f25592b = null;

        /* renamed from: b, reason: collision with other field name */
        private boolean f1016b = false;

        /* renamed from: a, reason: collision with other field name */
        private XMPushService.b f1007a = new XMPushService.b(this);

        /* renamed from: a, reason: collision with other field name */
        IBinder.DeathRecipient f1005a = null;

        /* renamed from: a, reason: collision with other field name */
        final C0408b f1009a = new C0408b();

        public interface a {
            void a(c cVar, c cVar2, int i2);
        }

        /* renamed from: com.xiaomi.push.service.at$b$b, reason: collision with other inner class name */
        public class C0408b extends XMPushService.i {

            /* renamed from: a, reason: collision with other field name */
            String f1017a;

            /* renamed from: b, reason: collision with root package name */
            int f25601b;

            /* renamed from: b, reason: collision with other field name */
            String f1018b;

            /* renamed from: c, reason: collision with root package name */
            int f25602c;

            public C0408b() {
                super(0);
            }

            public XMPushService.i a(int i2, int i3, String str, String str2) {
                this.f25601b = i2;
                this.f25602c = i3;
                this.f1018b = str2;
                this.f1017a = str;
                return this;
            }

            @Override // com.xiaomi.push.service.XMPushService.i
            public String a() {
                return "notify job";
            }

            @Override // com.xiaomi.push.service.XMPushService.i
            /* renamed from: a */
            public void mo463a() throws RemoteException {
                if (b.this.a(this.f25601b, this.f25602c, this.f1018b)) {
                    b.this.a(this.f25601b, this.f25602c, this.f1017a, this.f1018b);
                    return;
                }
                com.xiaomi.channel.commonutils.logger.b.b(" ignore notify client :" + b.this.f25597g);
            }
        }

        public class c implements IBinder.DeathRecipient {

            /* renamed from: a, reason: collision with root package name */
            final Messenger f25603a;

            /* renamed from: a, reason: collision with other field name */
            final b f1019a;

            public c(b bVar, Messenger messenger) {
                this.f1019a = bVar;
                this.f25603a = messenger;
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                com.xiaomi.channel.commonutils.logger.b.b("peer died, chid = " + this.f1019a.f25597g);
                b.this.f1008a.a(new av(this, 0), 0L);
                if (Constants.VIA_SHARE_TYPE_MINI_PROGRAM.equals(this.f1019a.f25597g) && "com.xiaomi.xmsf".equals(b.this.f1008a.getPackageName())) {
                    b.this.f1008a.a(new aw(this, 0), 60000L);
                }
            }
        }

        public b() {
        }

        public b(XMPushService xMPushService) {
            this.f1008a = xMPushService;
            a(new au(this));
        }

        public static String a(String str) {
            int iLastIndexOf;
            return (TextUtils.isEmpty(str) || (iLastIndexOf = str.lastIndexOf("/")) == -1) ? "" : str.substring(iLastIndexOf + 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i2, int i3, String str, String str2) throws RemoteException {
            c cVar = this.f1010a;
            this.f25592b = cVar;
            if (i2 == 2) {
                this.f1011a.a(this.f1004a, this, i3);
                return;
            }
            if (i2 == 3) {
                this.f1011a.a(this.f1004a, this, str2, str);
                return;
            }
            if (i2 == 1) {
                boolean z2 = cVar == c.binded;
                if (!z2 && "wait".equals(str2)) {
                    this.f25591a++;
                } else if (z2) {
                    this.f25591a = 0;
                    if (this.f1006a != null) {
                        try {
                            this.f1006a.send(Message.obtain(null, 16, this.f1008a.f952a));
                        } catch (RemoteException unused) {
                        }
                    }
                }
                this.f1011a.a(this.f1008a, this, z2, i3, str);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean a(int i2, int i3, String str) {
            boolean z2;
            StringBuilder sb;
            String str2;
            c cVar = this.f25592b;
            if (cVar == null || !(z2 = this.f1016b)) {
                return true;
            }
            if (cVar == this.f1010a) {
                sb = new StringBuilder();
                str2 = " status recovered, don't notify client:";
            } else {
                if (this.f1006a != null && z2) {
                    com.xiaomi.channel.commonutils.logger.b.b("Peer alive notify status to client:" + this.f25597g);
                    return true;
                }
                sb = new StringBuilder();
                str2 = "peer died, ignore notify ";
            }
            sb.append(str2);
            sb.append(this.f25597g);
            com.xiaomi.channel.commonutils.logger.b.b(sb.toString());
            return false;
        }

        private boolean b(int i2, int i3, String str) {
            if (i2 == 1) {
                return (this.f1010a == c.binded || !this.f1008a.m702c() || i3 == 21 || (i3 == 7 && "wait".equals(str))) ? false : true;
            }
            if (i2 == 2) {
                return this.f1008a.m702c();
            }
            if (i2 != 3) {
                return false;
            }
            return !"wait".equals(str);
        }

        public long a() {
            return (((long) ((Math.random() * 20.0d) - 10.0d)) + ((this.f25591a + 1) * 15)) * 1000;
        }

        public String a(int i2) {
            return i2 != 1 ? i2 != 2 ? i2 != 3 ? "unknown" : "KICK" : "CLOSE" : "OPEN";
        }

        /* renamed from: a, reason: collision with other method in class */
        public void m723a() {
            try {
                Messenger messenger = this.f1006a;
                if (messenger != null && this.f1005a != null) {
                    messenger.getBinder().unlinkToDeath(this.f1005a, 0);
                }
            } catch (Exception unused) {
            }
            this.f25592b = null;
        }

        public void a(Messenger messenger) {
            m723a();
            try {
                if (messenger != null) {
                    this.f1006a = messenger;
                    this.f1016b = true;
                    this.f1005a = new c(this, messenger);
                    messenger.getBinder().linkToDeath(this.f1005a, 0);
                } else {
                    com.xiaomi.channel.commonutils.logger.b.b("peer linked with old sdk chid = " + this.f25597g);
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.b("peer linkToDeath err: " + e2.getMessage());
                this.f1006a = null;
                this.f1016b = false;
            }
        }

        public void a(a aVar) {
            synchronized (this.f1013a) {
                this.f1013a.add(aVar);
            }
        }

        public void a(c cVar, int i2, int i3, String str, String str2) {
            boolean z2;
            synchronized (this.f1013a) {
                Iterator<a> it = this.f1013a.iterator();
                while (it.hasNext()) {
                    it.next().a(this.f1010a, cVar, i3);
                }
            }
            c cVar2 = this.f1010a;
            int i4 = 0;
            if (cVar2 != cVar) {
                com.xiaomi.channel.commonutils.logger.b.m117a(String.format("update the client %7$s status. %1$s->%2$s %3$s %4$s %5$s %6$s", cVar2, cVar, a(i2), ax.a(i3), str, str2, this.f25597g));
                this.f1010a = cVar;
            }
            if (this.f1011a == null) {
                com.xiaomi.channel.commonutils.logger.b.d("status changed while the client dispatcher is missing");
                return;
            }
            if (cVar == c.binding) {
                return;
            }
            if (this.f25592b != null && (z2 = this.f1016b)) {
                i4 = (this.f1006a == null || !z2) ? 10100 : 1000;
            }
            this.f1008a.b(this.f1009a);
            if (b(i2, i3, str2)) {
                a(i2, i3, str, str2);
            } else {
                this.f1008a.a(this.f1009a.a(i2, i3, str, str2), i4);
            }
        }

        public void b(a aVar) {
            synchronized (this.f1013a) {
                this.f1013a.remove(aVar);
            }
        }
    }

    public enum c {
        unbind,
        binding,
        binded
    }

    private at() {
    }

    public static synchronized at a() {
        if (f25590a == null) {
            f25590a = new at();
        }
        return f25590a;
    }

    private String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int iIndexOf = str.indexOf("@");
        return iIndexOf > 0 ? str.substring(0, iIndexOf) : str;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized int m715a() {
        return this.f1003a.size();
    }

    public synchronized b a(String str, String str2) {
        HashMap<String, b> map = this.f1003a.get(str);
        if (map == null) {
            return null;
        }
        return map.get(a(str2));
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized ArrayList<b> m716a() {
        ArrayList<b> arrayList;
        arrayList = new ArrayList<>();
        Iterator<HashMap<String, b>> it = this.f1003a.values().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().values());
        }
        return arrayList;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized Collection<b> m717a(String str) {
        if (this.f1003a.containsKey(str)) {
            return ((HashMap) this.f1003a.get(str).clone()).values();
        }
        return new ArrayList();
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized List<String> m718a(String str) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<HashMap<String, b>> it = this.f1003a.values().iterator();
        while (it.hasNext()) {
            for (b bVar : it.next().values()) {
                if (str.equals(bVar.f1012a)) {
                    arrayList.add(bVar.f25597g);
                }
            }
        }
        return arrayList;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m719a() {
        Iterator<b> it = m716a().iterator();
        while (it.hasNext()) {
            it.next().m723a();
        }
        this.f1003a.clear();
    }

    public synchronized void a(Context context) {
        Iterator<HashMap<String, b>> it = this.f1003a.values().iterator();
        while (it.hasNext()) {
            Iterator<b> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                it2.next().a(c.unbind, 1, 3, (String) null, (String) null);
            }
        }
    }

    public synchronized void a(Context context, int i2) {
        Iterator<HashMap<String, b>> it = this.f1003a.values().iterator();
        while (it.hasNext()) {
            Iterator<b> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                it2.next().a(c.unbind, 2, i2, (String) null, (String) null);
            }
        }
    }

    public synchronized void a(a aVar) {
        this.f1002a.add(aVar);
    }

    public synchronized void a(b bVar) {
        HashMap<String, b> map = this.f1003a.get(bVar.f25597g);
        if (map == null) {
            map = new HashMap<>();
            this.f1003a.put(bVar.f25597g, map);
        }
        map.put(a(bVar.f1015b), bVar);
        Iterator<a> it = this.f1002a.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m720a(String str) {
        HashMap<String, b> map = this.f1003a.get(str);
        if (map != null) {
            Iterator<b> it = map.values().iterator();
            while (it.hasNext()) {
                it.next().m723a();
            }
            map.clear();
            this.f1003a.remove(str);
        }
        Iterator<a> it2 = this.f1002a.iterator();
        while (it2.hasNext()) {
            it2.next().a();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m721a(String str, String str2) {
        HashMap<String, b> map = this.f1003a.get(str);
        if (map != null) {
            b bVar = map.get(a(str2));
            if (bVar != null) {
                bVar.m723a();
            }
            map.remove(a(str2));
            if (map.isEmpty()) {
                this.f1003a.remove(str);
            }
        }
        Iterator<a> it = this.f1002a.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    public synchronized void b() {
        this.f1002a.clear();
    }
}
