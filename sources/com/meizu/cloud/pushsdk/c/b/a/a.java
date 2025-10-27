package com.meizu.cloud.pushsdk.c.b.a;

import com.meizu.cloud.pushsdk.b.c.i;
import com.meizu.cloud.pushsdk.c.b.c;
import com.meizu.cloud.pushsdk.c.b.f;
import com.meizu.cloud.pushsdk.c.b.g;
import com.meizu.cloud.pushsdk.c.d.d;
import com.meizu.cloud.pushsdk.c.f.e;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public class a extends c {

    /* renamed from: u, reason: collision with root package name */
    private final String f9282u;

    /* renamed from: v, reason: collision with root package name */
    private d f9283v;

    /* renamed from: w, reason: collision with root package name */
    private int f9284w;

    public a(c.a aVar) {
        super(aVar);
        String simpleName = a.class.getSimpleName();
        this.f9282u = simpleName;
        com.meizu.cloud.pushsdk.c.d.a aVar2 = new com.meizu.cloud.pushsdk.c.d.a(this.f9297d, this.f9308o);
        this.f9283v = aVar2;
        if (aVar2.a()) {
            return;
        }
        this.f9283v = new com.meizu.cloud.pushsdk.c.d.c(this.f9308o);
        com.meizu.cloud.pushsdk.c.f.c.a(simpleName, "init memory store", new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.LinkedList<com.meizu.cloud.pushsdk.c.b.g> a(java.util.LinkedList<com.meizu.cloud.pushsdk.c.b.e> r10) {
        /*
            r9 = this;
            java.util.LinkedList r0 = new java.util.LinkedList
            r0.<init>()
            java.util.LinkedList r1 = new java.util.LinkedList
            r1.<init>()
            java.util.Iterator r2 = r10.iterator()
        Le:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L2a
            java.lang.Object r3 = r2.next()
            com.meizu.cloud.pushsdk.c.b.e r3 = (com.meizu.cloud.pushsdk.c.b.e) r3
            com.meizu.cloud.pushsdk.b.c.i r3 = r3.a()
            java.util.concurrent.Callable r3 = r9.b(r3)
            java.util.concurrent.Future r3 = com.meizu.cloud.pushsdk.c.b.a.b.a(r3)
            r1.add(r3)
            goto Le
        L2a:
            java.lang.String r2 = r9.f9282u
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]
            int r5 = r1.size()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r6 = 0
            r4[r6] = r5
            java.lang.String r5 = "Request Futures: %s"
            com.meizu.cloud.pushsdk.c.f.c.b(r2, r5, r4)
            r2 = r6
        L40:
            int r4 = r1.size()
            if (r2 >= r4) goto Lc7
            java.lang.Object r4 = r1.get(r2)     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L6c java.lang.InterruptedException -> L7d
            java.util.concurrent.Future r4 = (java.util.concurrent.Future) r4     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L6c java.lang.InterruptedException -> L7d
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L6c java.lang.InterruptedException -> L7d
            r7 = 5
            java.lang.Object r4 = r4.get(r7, r5)     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L6c java.lang.InterruptedException -> L7d
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L6c java.lang.InterruptedException -> L7d
            int r4 = r4.intValue()     // Catch: java.util.concurrent.TimeoutException -> L5b java.util.concurrent.ExecutionException -> L6c java.lang.InterruptedException -> L7d
            goto L8e
        L5b:
            r4 = move-exception
            java.lang.String r5 = r9.f9282u
            java.lang.Object[] r7 = new java.lang.Object[r3]
            java.lang.String r4 = r4.getMessage()
            r7[r6] = r4
            java.lang.String r4 = "Request Future had a timeout: %s"
            com.meizu.cloud.pushsdk.c.f.c.a(r5, r4, r7)
            goto L8d
        L6c:
            r4 = move-exception
            java.lang.String r5 = r9.f9282u
            java.lang.Object[] r7 = new java.lang.Object[r3]
            java.lang.String r4 = r4.getMessage()
            r7[r6] = r4
            java.lang.String r4 = "Request Future failed: %s"
            com.meizu.cloud.pushsdk.c.f.c.a(r5, r4, r7)
            goto L8d
        L7d:
            r4 = move-exception
            java.lang.String r5 = r9.f9282u
            java.lang.Object[] r7 = new java.lang.Object[r3]
            java.lang.String r4 = r4.getMessage()
            r7[r6] = r4
            java.lang.String r4 = "Request Future was interrupted: %s"
            com.meizu.cloud.pushsdk.c.f.c.a(r5, r4, r7)
        L8d:
            r4 = -1
        L8e:
            java.lang.Object r5 = r10.get(r2)
            com.meizu.cloud.pushsdk.c.b.e r5 = (com.meizu.cloud.pushsdk.c.b.e) r5
            boolean r5 = r5.c()
            if (r5 == 0) goto Lad
            com.meizu.cloud.pushsdk.c.b.g r4 = new com.meizu.cloud.pushsdk.c.b.g
            java.lang.Object r5 = r10.get(r2)
            com.meizu.cloud.pushsdk.c.b.e r5 = (com.meizu.cloud.pushsdk.c.b.e) r5
            java.util.LinkedList r5 = r5.b()
            r4.<init>(r3, r5)
            r0.add(r4)
            goto Lc3
        Lad:
            com.meizu.cloud.pushsdk.c.b.g r5 = new com.meizu.cloud.pushsdk.c.b.g
            boolean r4 = r9.a(r4)
            java.lang.Object r7 = r10.get(r2)
            com.meizu.cloud.pushsdk.c.b.e r7 = (com.meizu.cloud.pushsdk.c.b.e) r7
            java.util.LinkedList r7 = r7.b()
            r5.<init>(r4, r7)
            r0.add(r5)
        Lc3:
            int r2 = r2 + 1
            goto L40
        Lc7:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.c.b.a.a.a(java.util.LinkedList):java.util.LinkedList");
    }

    private Callable<Boolean> a(final Long l2) {
        return new Callable<Boolean>() { // from class: com.meizu.cloud.pushsdk.c.b.a.a.3
            @Override // java.util.concurrent.Callable
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean call() throws Exception {
                return Boolean.valueOf(a.this.f9283v.a(l2.longValue()));
            }
        };
    }

    private LinkedList<Boolean> b(LinkedList<Long> linkedList) {
        boolean zBooleanValue;
        LinkedList<Boolean> linkedList2 = new LinkedList<>();
        LinkedList linkedList3 = new LinkedList();
        Iterator<Long> it = linkedList.iterator();
        while (it.hasNext()) {
            linkedList3.add(b.a(a(it.next())));
        }
        com.meizu.cloud.pushsdk.c.f.c.b(this.f9282u, "Removal Futures: %s", Integer.valueOf(linkedList3.size()));
        for (int i2 = 0; i2 < linkedList3.size(); i2++) {
            try {
                zBooleanValue = ((Boolean) ((Future) linkedList3.get(i2)).get(5L, TimeUnit.SECONDS)).booleanValue();
            } catch (InterruptedException e2) {
                com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Removal Future was interrupted: %s", e2.getMessage());
                zBooleanValue = false;
                linkedList2.add(Boolean.valueOf(zBooleanValue));
            } catch (ExecutionException e3) {
                com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Removal Future failed: %s", e3.getMessage());
                zBooleanValue = false;
                linkedList2.add(Boolean.valueOf(zBooleanValue));
            } catch (TimeoutException e4) {
                com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Removal Future had a timeout: %s", e4.getMessage());
                zBooleanValue = false;
                linkedList2.add(Boolean.valueOf(zBooleanValue));
            }
            linkedList2.add(Boolean.valueOf(zBooleanValue));
        }
        return linkedList2;
    }

    private Callable<Integer> b(final i iVar) {
        return new Callable<Integer>() { // from class: com.meizu.cloud.pushsdk.c.b.a.a.2
            @Override // java.util.concurrent.Callable
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Integer call() throws Exception {
                return Integer.valueOf(a.this.a(iVar));
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() throws InterruptedException {
        if (e.a(this.f9297d)) {
            if (this.f9283v.c() > 0) {
                this.f9284w = 0;
                LinkedList<g> linkedListA = a(a(this.f9283v.d()));
                com.meizu.cloud.pushsdk.c.f.c.c(this.f9282u, "Processing emitter results.", new Object[0]);
                LinkedList<Long> linkedList = new LinkedList<>();
                Iterator<g> it = linkedListA.iterator();
                int size = 0;
                int size2 = 0;
                while (it.hasNext()) {
                    g next = it.next();
                    if (next.a()) {
                        Iterator<Long> it2 = next.b().iterator();
                        while (it2.hasNext()) {
                            linkedList.add(it2.next());
                        }
                        size += next.b().size();
                    } else {
                        size2 += next.b().size();
                        com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Request sending failed but we will retry later.", new Object[0]);
                    }
                }
                b(linkedList);
                com.meizu.cloud.pushsdk.c.f.c.b(this.f9282u, "Success Count: %s", Integer.valueOf(size));
                com.meizu.cloud.pushsdk.c.f.c.b(this.f9282u, "Failure Count: %s", Integer.valueOf(size2));
                f fVar = this.f9299f;
                if (fVar != null) {
                    if (size2 != 0) {
                        fVar.a(size, size2);
                    } else {
                        fVar.a(size);
                    }
                }
                if (size2 > 0 && size == 0) {
                    if (e.a(this.f9297d)) {
                        com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Ensure collector path is valid: %s", b());
                    }
                    com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Emitter loop stopping: failures.", new Object[0]);
                }
            } else {
                int i2 = this.f9284w;
                if (i2 >= this.f9307n) {
                    com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Emitter loop stopping: empty limit reached.", new Object[0]);
                    this.f9313t.compareAndSet(true, false);
                    f fVar2 = this.f9299f;
                    if (fVar2 != null) {
                        fVar2.a(true);
                        return;
                    }
                    return;
                }
                this.f9284w = i2 + 1;
                com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Emitter database empty: " + this.f9284w, new Object[0]);
                try {
                    this.f9311r.sleep(this.f9306m);
                } catch (InterruptedException e2) {
                    com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Emitter thread sleep interrupted: " + e2.toString(), new Object[0]);
                }
            }
            c();
            return;
        }
        com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Emitter loop stopping: emitter offline.", new Object[0]);
        this.f9313t.compareAndSet(true, false);
    }

    @Override // com.meizu.cloud.pushsdk.c.b.c
    public void a() {
        b.a(new Runnable() { // from class: com.meizu.cloud.pushsdk.c.b.a.a.1
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                if (((c) a.this).f9313t.compareAndSet(false, true)) {
                    a.this.c();
                }
            }
        });
    }

    @Override // com.meizu.cloud.pushsdk.c.b.c
    public void a(com.meizu.cloud.pushsdk.c.a.a aVar, boolean z2) throws InterruptedException {
        this.f9283v.a(aVar);
        com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "isRunning " + this.f9313t + " attemptEmit " + z2, new Object[0]);
        if (!z2) {
            try {
                this.f9311r.sleep(1L);
            } catch (InterruptedException e2) {
                com.meizu.cloud.pushsdk.c.f.c.a(this.f9282u, "Emitter add thread sleep interrupted: " + e2.toString(), new Object[0]);
            }
        }
        if (this.f9313t.compareAndSet(false, true)) {
            c();
        }
    }
}
