package com.meizu.cloud.pushsdk.c.c;

import com.meizu.cloud.pushsdk.c.a.c;
import com.meizu.cloud.pushsdk.c.f.d;
import com.meizu.cloud.pushsdk.c.f.e;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    protected final List<com.meizu.cloud.pushsdk.c.a.b> f9342a;

    /* renamed from: b, reason: collision with root package name */
    protected final long f9343b;

    /* renamed from: c, reason: collision with root package name */
    protected final String f9344c;

    /* renamed from: com.meizu.cloud.pushsdk.c.c.a$a, reason: collision with other inner class name */
    public static abstract class AbstractC0195a<T extends AbstractC0195a<T>> {

        /* renamed from: a, reason: collision with root package name */
        private List<com.meizu.cloud.pushsdk.c.a.b> f9345a = new LinkedList();

        /* renamed from: b, reason: collision with root package name */
        private long f9346b = System.currentTimeMillis();

        /* renamed from: c, reason: collision with root package name */
        private String f9347c = e.b();

        public abstract T a();

        public T a(long j2) {
            this.f9346b = j2;
            return (T) a();
        }
    }

    public a(AbstractC0195a<?> abstractC0195a) {
        d.a(((AbstractC0195a) abstractC0195a).f9345a);
        d.a(((AbstractC0195a) abstractC0195a).f9347c);
        d.a(!((AbstractC0195a) abstractC0195a).f9347c.isEmpty(), "eventId cannot be empty");
        this.f9342a = ((AbstractC0195a) abstractC0195a).f9345a;
        this.f9343b = ((AbstractC0195a) abstractC0195a).f9346b;
        this.f9344c = ((AbstractC0195a) abstractC0195a).f9347c;
    }

    public c a(c cVar) {
        cVar.a(NotificationStyle.EXPANDABLE_IMAGE_URL, c());
        cVar.a("ts", Long.toString(b()));
        return cVar;
    }

    public List<com.meizu.cloud.pushsdk.c.a.b> a() {
        return new ArrayList(this.f9342a);
    }

    public long b() {
        return this.f9343b;
    }

    public String c() {
        return this.f9344c;
    }
}
