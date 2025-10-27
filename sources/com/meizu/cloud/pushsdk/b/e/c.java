package com.meizu.cloud.pushsdk.b.e;

import com.meizu.cloud.pushsdk.b.a.e;
import com.meizu.cloud.pushsdk.b.c.k;

/* loaded from: classes4.dex */
public final class c {
    public static <T> com.meizu.cloud.pushsdk.b.a.c<T> a(com.meizu.cloud.pushsdk.b.a.b bVar) {
        int iG = bVar.g();
        return iG != 0 ? iG != 1 ? iG != 2 ? new com.meizu.cloud.pushsdk.b.a.c<>(new com.meizu.cloud.pushsdk.b.b.a()) : d(bVar) : c(bVar) : b(bVar);
    }

    private static <T> com.meizu.cloud.pushsdk.b.a.c<T> b(com.meizu.cloud.pushsdk.b.a.b bVar) {
        try {
            try {
                k kVarA = a.a(bVar);
                if (kVarA == null) {
                    com.meizu.cloud.pushsdk.b.a.c<T> cVar = new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(new com.meizu.cloud.pushsdk.b.b.a()));
                    com.meizu.cloud.pushsdk.b.i.a.a(kVarA, bVar);
                    return cVar;
                }
                if (bVar.f() == e.OK_HTTP_RESPONSE) {
                    com.meizu.cloud.pushsdk.b.a.c<T> cVar2 = new com.meizu.cloud.pushsdk.b.a.c<>(kVarA);
                    cVar2.a(kVarA);
                    com.meizu.cloud.pushsdk.b.i.a.a(kVarA, bVar);
                    return cVar2;
                }
                if (kVarA.a() >= 400) {
                    com.meizu.cloud.pushsdk.b.a.c<T> cVar3 = new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(new com.meizu.cloud.pushsdk.b.b.a(kVarA), bVar, kVarA.a()));
                    cVar3.a(kVarA);
                    com.meizu.cloud.pushsdk.b.i.a.a(kVarA, bVar);
                    return cVar3;
                }
                com.meizu.cloud.pushsdk.b.a.c<T> cVarA = bVar.a(kVarA);
                cVarA.a(kVarA);
                com.meizu.cloud.pushsdk.b.i.a.a(kVarA, bVar);
                return cVarA;
            } catch (com.meizu.cloud.pushsdk.b.b.a e2) {
                com.meizu.cloud.pushsdk.b.a.c<T> cVar4 = new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(new com.meizu.cloud.pushsdk.b.b.a(e2)));
                com.meizu.cloud.pushsdk.b.i.a.a(null, bVar);
                return cVar4;
            } catch (Exception e3) {
                com.meizu.cloud.pushsdk.b.a.c<T> cVar5 = new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(e3));
                com.meizu.cloud.pushsdk.b.i.a.a(null, bVar);
                return cVar5;
            }
        } catch (Throwable th) {
            com.meizu.cloud.pushsdk.b.i.a.a(null, bVar);
            throw th;
        }
    }

    private static <T> com.meizu.cloud.pushsdk.b.a.c<T> c(com.meizu.cloud.pushsdk.b.a.b bVar) {
        try {
            k kVarB = a.b(bVar);
            if (kVarB == null) {
                return new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(new com.meizu.cloud.pushsdk.b.b.a()));
            }
            if (kVarB.a() >= 400) {
                com.meizu.cloud.pushsdk.b.a.c<T> cVar = new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(new com.meizu.cloud.pushsdk.b.b.a(kVarB), bVar, kVarB.a()));
                cVar.a(kVarB);
                return cVar;
            }
            com.meizu.cloud.pushsdk.b.a.c<T> cVar2 = new com.meizu.cloud.pushsdk.b.a.c<>("success");
            cVar2.a(kVarB);
            return cVar2;
        } catch (com.meizu.cloud.pushsdk.b.b.a e2) {
            return new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(new com.meizu.cloud.pushsdk.b.b.a(e2)));
        } catch (Exception e3) {
            return new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(e3));
        }
    }

    private static <T> com.meizu.cloud.pushsdk.b.a.c<T> d(com.meizu.cloud.pushsdk.b.a.b bVar) {
        try {
            try {
                k kVarC = a.c(bVar);
                if (kVarC == null) {
                    com.meizu.cloud.pushsdk.b.a.c<T> cVar = new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(new com.meizu.cloud.pushsdk.b.b.a()));
                    com.meizu.cloud.pushsdk.b.i.a.a(kVarC, bVar);
                    return cVar;
                }
                if (bVar.f() == e.OK_HTTP_RESPONSE) {
                    com.meizu.cloud.pushsdk.b.a.c<T> cVar2 = new com.meizu.cloud.pushsdk.b.a.c<>(kVarC);
                    cVar2.a(kVarC);
                    com.meizu.cloud.pushsdk.b.i.a.a(kVarC, bVar);
                    return cVar2;
                }
                if (kVarC.a() >= 400) {
                    com.meizu.cloud.pushsdk.b.a.c<T> cVar3 = new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(new com.meizu.cloud.pushsdk.b.b.a(kVarC), bVar, kVarC.a()));
                    cVar3.a(kVarC);
                    com.meizu.cloud.pushsdk.b.i.a.a(kVarC, bVar);
                    return cVar3;
                }
                com.meizu.cloud.pushsdk.b.a.c<T> cVarA = bVar.a(kVarC);
                cVarA.a(kVarC);
                com.meizu.cloud.pushsdk.b.i.a.a(kVarC, bVar);
                return cVarA;
            } catch (com.meizu.cloud.pushsdk.b.b.a e2) {
                com.meizu.cloud.pushsdk.b.a.c<T> cVar4 = new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(e2));
                com.meizu.cloud.pushsdk.b.i.a.a(null, bVar);
                return cVar4;
            } catch (Exception e3) {
                com.meizu.cloud.pushsdk.b.a.c<T> cVar5 = new com.meizu.cloud.pushsdk.b.a.c<>(com.meizu.cloud.pushsdk.b.i.b.a(e3));
                com.meizu.cloud.pushsdk.b.i.a.a(null, bVar);
                return cVar5;
            }
        } catch (Throwable th) {
            com.meizu.cloud.pushsdk.b.i.a.a(null, bVar);
            throw th;
        }
    }
}
