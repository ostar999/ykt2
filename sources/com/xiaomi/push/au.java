package com.xiaomi.push;

import java.util.LinkedList;

/* loaded from: classes6.dex */
public class au {

    /* renamed from: a, reason: collision with root package name */
    private LinkedList<a> f24618a = new LinkedList<>();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final au f24619a = new au();

        /* renamed from: a, reason: collision with other field name */
        public int f196a;

        /* renamed from: a, reason: collision with other field name */
        public Object f197a;

        /* renamed from: a, reason: collision with other field name */
        public String f198a;

        public a(int i2, Object obj) {
            this.f196a = i2;
            this.f197a = obj;
        }
    }

    public static au a() {
        return a.f24619a;
    }

    /* renamed from: a, reason: collision with other method in class */
    private void m206a() {
        if (this.f24618a.size() > 100) {
            this.f24618a.removeFirst();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized int m207a() {
        return this.f24618a.size();
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized LinkedList<a> m208a() {
        LinkedList<a> linkedList;
        linkedList = this.f24618a;
        this.f24618a = new LinkedList<>();
        return linkedList;
    }

    public synchronized void a(Object obj) {
        this.f24618a.add(new a(0, obj));
        m206a();
    }
}
