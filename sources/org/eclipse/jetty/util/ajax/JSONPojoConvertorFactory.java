package org.eclipse.jetty.util.ajax;

import org.eclipse.jetty.util.ajax.JSON;

/* loaded from: classes9.dex */
public class JSONPojoConvertorFactory implements JSON.Convertor {
    private final boolean _fromJson;
    private final JSON _json;

    public JSONPojoConvertorFactory(JSON json) {
        if (json == null) {
            throw new IllegalArgumentException();
        }
        this._json = json;
        this._fromJson = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    @Override // org.eclipse.jetty.util.ajax.JSON.Convertor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object fromJSON(java.util.Map r6) {
        /*
            r5 = this;
            java.lang.String r0 = "class"
            java.lang.Object r0 = r6.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L35
            org.eclipse.jetty.util.ajax.JSON r1 = r5._json
            org.eclipse.jetty.util.ajax.JSON$Convertor r1 = r1.getConvertorFor(r0)
            if (r1 != 0) goto L2f
            java.lang.Class<org.eclipse.jetty.util.ajax.JSON> r2 = org.eclipse.jetty.util.ajax.JSON.class
            java.lang.Class r2 = org.eclipse.jetty.util.Loader.loadClass(r2, r0)     // Catch: java.lang.ClassNotFoundException -> L29
            org.eclipse.jetty.util.ajax.JSONPojoConvertor r3 = new org.eclipse.jetty.util.ajax.JSONPojoConvertor     // Catch: java.lang.ClassNotFoundException -> L29
            boolean r4 = r5._fromJson     // Catch: java.lang.ClassNotFoundException -> L29
            r3.<init>(r2, r4)     // Catch: java.lang.ClassNotFoundException -> L29
            org.eclipse.jetty.util.ajax.JSON r1 = r5._json     // Catch: java.lang.ClassNotFoundException -> L26
            r1.addConvertorFor(r0, r3)     // Catch: java.lang.ClassNotFoundException -> L26
            r1 = r3
            goto L2f
        L26:
            r0 = move-exception
            r1 = r3
            goto L2a
        L29:
            r0 = move-exception
        L2a:
            org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.util.ajax.JSON.LOG
            r2.warn(r0)
        L2f:
            if (r1 == 0) goto L35
            java.lang.Object r6 = r1.fromJSON(r6)
        L35:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSONPojoConvertorFactory.fromJSON(java.util.Map):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    @Override // org.eclipse.jetty.util.ajax.JSON.Convertor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void toJSON(java.lang.Object r6, org.eclipse.jetty.util.ajax.JSON.Output r7) {
        /*
            r5 = this;
            java.lang.Class r0 = r6.getClass()
            java.lang.String r0 = r0.getName()
            org.eclipse.jetty.util.ajax.JSON r1 = r5._json
            org.eclipse.jetty.util.ajax.JSON$Convertor r1 = r1.getConvertorFor(r0)
            if (r1 != 0) goto L2d
            java.lang.Class<org.eclipse.jetty.util.ajax.JSON> r2 = org.eclipse.jetty.util.ajax.JSON.class
            java.lang.Class r2 = org.eclipse.jetty.util.Loader.loadClass(r2, r0)     // Catch: java.lang.ClassNotFoundException -> L27
            org.eclipse.jetty.util.ajax.JSONPojoConvertor r3 = new org.eclipse.jetty.util.ajax.JSONPojoConvertor     // Catch: java.lang.ClassNotFoundException -> L27
            boolean r4 = r5._fromJson     // Catch: java.lang.ClassNotFoundException -> L27
            r3.<init>(r2, r4)     // Catch: java.lang.ClassNotFoundException -> L27
            org.eclipse.jetty.util.ajax.JSON r1 = r5._json     // Catch: java.lang.ClassNotFoundException -> L24
            r1.addConvertorFor(r0, r3)     // Catch: java.lang.ClassNotFoundException -> L24
            r1 = r3
            goto L2d
        L24:
            r0 = move-exception
            r1 = r3
            goto L28
        L27:
            r0 = move-exception
        L28:
            org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.util.ajax.JSON.LOG
            r2.warn(r0)
        L2d:
            if (r1 == 0) goto L32
            r1.toJSON(r6, r7)
        L32:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSONPojoConvertorFactory.toJSON(java.lang.Object, org.eclipse.jetty.util.ajax.JSON$Output):void");
    }

    public JSONPojoConvertorFactory(JSON json, boolean z2) {
        if (json != null) {
            this._json = json;
            this._fromJson = z2;
            return;
        }
        throw new IllegalArgumentException();
    }
}
