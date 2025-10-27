package com.xiaomi.push;

/* loaded from: classes6.dex */
final class hm {

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        fr f25064a;

        /* renamed from: a, reason: collision with other field name */
        String f534a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.lang.Throwable] */
    public static a a(Exception exc) {
        m486a(exc);
        boolean z2 = exc instanceof gn;
        Exception excA = exc;
        if (z2) {
            gn gnVar = (gn) exc;
            excA = exc;
            if (gnVar.a() != null) {
                excA = gnVar.a();
            }
        }
        a aVar = new a();
        String message = excA.getMessage();
        if (excA.getCause() != null) {
            message = excA.getCause().getMessage();
        }
        String str = excA.getClass().getSimpleName() + ":" + message;
        int iA = ge.a(excA);
        if (iA != 0) {
            aVar.f25064a = fr.a(fr.GSLB_REQUEST_SUCCESS.a() + iA);
        }
        if (aVar.f25064a == null) {
            aVar.f25064a = fr.GSLB_TCP_ERR_OTHER;
        }
        if (aVar.f25064a == fr.GSLB_TCP_ERR_OTHER) {
            aVar.f534a = str;
        }
        return aVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static void m486a(Exception exc) {
        exc.getClass();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007d  */
    /* JADX WARN: Type inference failed for: r5v8, types: [java.lang.Throwable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.push.hm.a b(java.lang.Exception r5) {
        /*
            m486a(r5)
            boolean r0 = r5 instanceof com.xiaomi.push.gn
            if (r0 == 0) goto L14
            r0 = r5
            com.xiaomi.push.gn r0 = (com.xiaomi.push.gn) r0
            java.lang.Throwable r1 = r0.a()
            if (r1 == 0) goto L14
            java.lang.Throwable r5 = r0.a()
        L14:
            com.xiaomi.push.hm$a r0 = new com.xiaomi.push.hm$a
            r0.<init>()
            java.lang.String r1 = r5.getMessage()
            java.lang.Throwable r2 = r5.getCause()
            if (r2 == 0) goto L2b
            java.lang.Throwable r1 = r5.getCause()
            java.lang.String r1 = r1.getMessage()
        L2b:
            int r2 = com.xiaomi.push.ge.a(r5)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r5.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            java.lang.String r4 = ":"
            r3.append(r4)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            if (r2 == 0) goto L6b
            com.xiaomi.push.fr r3 = com.xiaomi.push.fr.CONN_SUCCESS
            int r3 = r3.a()
            int r3 = r3 + r2
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.a(r3)
            r0.f25064a = r2
            com.xiaomi.push.fr r3 = com.xiaomi.push.fr.CONN_BOSH_ERR
            if (r2 != r3) goto L6f
            java.lang.Throwable r5 = r5.getCause()
            if (r5 == 0) goto L6f
            boolean r5 = r5 instanceof java.net.UnknownHostException
            if (r5 == 0) goto L6f
            com.xiaomi.push.fr r5 = com.xiaomi.push.fr.CONN_BOSH_UNKNOWNHOST
            goto L6d
        L6b:
            com.xiaomi.push.fr r5 = com.xiaomi.push.fr.CONN_XMPP_ERR
        L6d:
            r0.f25064a = r5
        L6f:
            com.xiaomi.push.fr r5 = r0.f25064a
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.CONN_TCP_ERR_OTHER
            if (r5 == r2) goto L7d
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.CONN_XMPP_ERR
            if (r5 == r2) goto L7d
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.CONN_BOSH_ERR
            if (r5 != r2) goto L7f
        L7d:
            r0.f534a = r1
        L7f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.hm.b(java.lang.Exception):com.xiaomi.push.hm$a");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x008c  */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.lang.Throwable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.push.hm.a c(java.lang.Exception r4) {
        /*
            m486a(r4)
            boolean r0 = r4 instanceof com.xiaomi.push.gn
            if (r0 == 0) goto L14
            r0 = r4
            com.xiaomi.push.gn r0 = (com.xiaomi.push.gn) r0
            java.lang.Throwable r1 = r0.a()
            if (r1 == 0) goto L14
            java.lang.Throwable r4 = r0.a()
        L14:
            com.xiaomi.push.hm$a r0 = new com.xiaomi.push.hm$a
            r0.<init>()
            java.lang.String r1 = r4.getMessage()
            java.lang.Throwable r2 = r4.getCause()
            if (r2 == 0) goto L2b
            java.lang.Throwable r1 = r4.getCause()
            java.lang.String r1 = r1.getMessage()
        L2b:
            int r2 = com.xiaomi.push.ge.a(r4)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r4.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            java.lang.String r4 = ":"
            r3.append(r4)
            r3.append(r1)
            java.lang.String r4 = r3.toString()
            r3 = 105(0x69, float:1.47E-43)
            if (r2 == r3) goto L7a
            r3 = 199(0xc7, float:2.79E-43)
            if (r2 == r3) goto L77
            r3 = 499(0x1f3, float:6.99E-43)
            if (r2 == r3) goto L68
            r1 = 109(0x6d, float:1.53E-43)
            if (r2 == r1) goto L65
            r1 = 110(0x6e, float:1.54E-43)
            if (r2 == r1) goto L62
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.BIND_XMPP_ERR
            goto L7c
        L62:
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.BIND_TCP_BROKEN_PIPE
            goto L7c
        L65:
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.BIND_TCP_CONNRESET
            goto L7c
        L68:
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.BIND_BOSH_ERR
            r0.f25064a = r2
            java.lang.String r2 = "Terminal binding condition encountered: item-not-found"
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L7e
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.BIND_BOSH_ITEM_NOT_FOUND
            goto L7c
        L77:
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.BIND_TCP_ERR
            goto L7c
        L7a:
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.BIND_TCP_READ_TIMEOUT
        L7c:
            r0.f25064a = r1
        L7e:
            com.xiaomi.push.fr r1 = r0.f25064a
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.BIND_TCP_ERR
            if (r1 == r2) goto L8c
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.BIND_XMPP_ERR
            if (r1 == r2) goto L8c
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.BIND_BOSH_ERR
            if (r1 != r2) goto L8e
        L8c:
            r0.f534a = r4
        L8e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.hm.c(java.lang.Exception):com.xiaomi.push.hm$a");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007e  */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.lang.Throwable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.push.hm.a d(java.lang.Exception r4) {
        /*
            m486a(r4)
            boolean r0 = r4 instanceof com.xiaomi.push.gn
            if (r0 == 0) goto L14
            r0 = r4
            com.xiaomi.push.gn r0 = (com.xiaomi.push.gn) r0
            java.lang.Throwable r1 = r0.a()
            if (r1 == 0) goto L14
            java.lang.Throwable r4 = r0.a()
        L14:
            com.xiaomi.push.hm$a r0 = new com.xiaomi.push.hm$a
            r0.<init>()
            java.lang.String r1 = r4.getMessage()
            int r2 = com.xiaomi.push.ge.a(r4)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r4.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            java.lang.String r4 = ":"
            r3.append(r4)
            r3.append(r1)
            java.lang.String r4 = r3.toString()
            r3 = 105(0x69, float:1.47E-43)
            if (r2 == r3) goto L6c
            r3 = 199(0xc7, float:2.79E-43)
            if (r2 == r3) goto L69
            r3 = 499(0x1f3, float:6.99E-43)
            if (r2 == r3) goto L5a
            r1 = 109(0x6d, float:1.53E-43)
            if (r2 == r1) goto L57
            r1 = 110(0x6e, float:1.54E-43)
            if (r2 == r1) goto L54
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.CHANNEL_XMPPEXCEPTION
            goto L6e
        L54:
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.CHANNEL_TCP_BROKEN_PIPE
            goto L6e
        L57:
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.CHANNEL_TCP_CONNRESET
            goto L6e
        L5a:
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.CHANNEL_BOSH_EXCEPTION
            r0.f25064a = r2
            java.lang.String r2 = "Terminal binding condition encountered: item-not-found"
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L70
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.CHANNEL_BOSH_ITEMNOTFIND
            goto L6e
        L69:
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.CHANNEL_TCP_ERR
            goto L6e
        L6c:
            com.xiaomi.push.fr r1 = com.xiaomi.push.fr.CHANNEL_TCP_READTIMEOUT
        L6e:
            r0.f25064a = r1
        L70:
            com.xiaomi.push.fr r1 = r0.f25064a
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.CHANNEL_TCP_ERR
            if (r1 == r2) goto L7e
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.CHANNEL_XMPPEXCEPTION
            if (r1 == r2) goto L7e
            com.xiaomi.push.fr r2 = com.xiaomi.push.fr.CHANNEL_BOSH_EXCEPTION
            if (r1 != r2) goto L80
        L7e:
            r0.f534a = r4
        L80:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.hm.d(java.lang.Exception):com.xiaomi.push.hm$a");
    }
}
