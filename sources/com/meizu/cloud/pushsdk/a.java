package com.meizu.cloud.pushsdk;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.handler.a.b;
import com.meizu.cloud.pushsdk.handler.a.d;
import com.meizu.cloud.pushsdk.handler.a.e;
import com.meizu.cloud.pushsdk.handler.a.f;
import com.meizu.cloud.pushsdk.handler.c;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    static volatile a f8910a;

    /* renamed from: b, reason: collision with root package name */
    private Context f8911b;

    /* renamed from: c, reason: collision with root package name */
    private Map<Integer, c> f8912c;

    /* renamed from: d, reason: collision with root package name */
    private Map<String, com.meizu.cloud.pushsdk.handler.a> f8913d;

    /* renamed from: com.meizu.cloud.pushsdk.a$a, reason: collision with other inner class name */
    public class C0192a extends com.meizu.cloud.pushsdk.handler.a {
        public C0192a() {
        }

        @Override // com.meizu.cloud.pushsdk.handler.a
        public void a(Context context, Intent intent) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, intent);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, MzPushMessage mzPushMessage) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, mzPushMessage);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, PushSwitchStatus pushSwitchStatus) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, pushSwitchStatus);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, RegisterStatus registerStatus) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, registerStatus);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, SubAliasStatus subAliasStatus) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, subAliasStatus);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, SubTagsStatus subTagsStatus) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, subTagsStatus);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, UnRegisterStatus unRegisterStatus) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, unRegisterStatus);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, String str) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, str);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, String str, String str2) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, str, str2);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(Context context, boolean z2) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(context, z2);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void a(PushNotificationBuilder pushNotificationBuilder) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.a(pushNotificationBuilder);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void b(Context context, MzPushMessage mzPushMessage) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.b(context, mzPushMessage);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void b(Context context, String str) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.b(context, str);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void c(Context context, MzPushMessage mzPushMessage) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.c(context, mzPushMessage);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.handler.b
        public void c(Context context, String str) {
            Iterator it = a.this.f8913d.entrySet().iterator();
            while (it.hasNext()) {
                com.meizu.cloud.pushsdk.handler.a aVar = (com.meizu.cloud.pushsdk.handler.a) ((Map.Entry) it.next()).getValue();
                if (aVar != null) {
                    aVar.c(context, str);
                }
            }
        }
    }

    public a(Context context) {
        this(context, null);
    }

    public a(Context context, List<c> list) {
        this(context, list, null);
    }

    public a(Context context, List<c> list, com.meizu.cloud.pushsdk.handler.a aVar) {
        this.f8912c = new HashMap();
        this.f8913d = null;
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.f8911b = context.getApplicationContext();
        this.f8913d = new HashMap();
        C0192a c0192a = new C0192a();
        if (list != null) {
            a(list);
            return;
        }
        a(new com.meizu.cloud.pushsdk.handler.a.c(context, c0192a));
        a(new b(context, c0192a));
        a(new e(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.c.b(context, c0192a));
        a(new d(context, c0192a));
        a(new f(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.c.c(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.d.a(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.d.c(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.d.f(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.d.d(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.d.e(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.e.a(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.d.b(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.c.d(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.a.a(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.c.a(context, c0192a));
        a(new com.meizu.cloud.pushsdk.handler.a.c.e(context, c0192a));
    }

    public static a a(Context context) {
        if (f8910a == null) {
            synchronized (a.class) {
                if (f8910a == null) {
                    DebugLogger.i("PushMessageProxy", "PushMessageProxy init");
                    f8910a = new a(context);
                }
            }
        }
        return f8910a;
    }

    public a a(c cVar) {
        this.f8912c.put(Integer.valueOf(cVar.a()), cVar);
        return this;
    }

    public a a(String str, com.meizu.cloud.pushsdk.handler.a aVar) {
        this.f8913d.put(str, aVar);
        return this;
    }

    public a a(List<c> list) {
        if (list == null) {
            throw new IllegalArgumentException("messageManagerList must not be null.");
        }
        Iterator<c> it = list.iterator();
        while (it.hasNext()) {
            a(it.next());
        }
        return this;
    }

    public void a(Intent intent) {
        DebugLogger.e("PushMessageProxy", "is onMainThread " + a());
        try {
            DebugLogger.i("PushMessageProxy", "receive action " + intent.getAction() + " method " + intent.getStringExtra("method"));
            Iterator<Map.Entry<Integer, c>> it = this.f8912c.entrySet().iterator();
            while (it.hasNext() && !it.next().getValue().b(intent)) {
            }
        } catch (Exception e2) {
            DebugLogger.e("PushMessageProxy", "processMessage error " + e2.getMessage());
        }
    }

    public boolean a() {
        return Thread.currentThread() == this.f8911b.getMainLooper().getThread();
    }
}
