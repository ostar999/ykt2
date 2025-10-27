package com.ta.a.b;

import android.content.Context;

/* loaded from: classes6.dex */
final class a {

    /* renamed from: a, reason: collision with root package name */
    private static final b f17206a = new C0314a();

    /* renamed from: com.ta.a.b.a$a, reason: collision with other inner class name */
    public static class C0314a extends b {
        @Override // com.ta.a.b.a.b
        public int a(Context context, String str, String str2) {
            return com.ta.a.b.b.a(context, str, str2);
        }

        @Override // com.ta.a.b.a.b
        public String permissionToOp(String str) {
            return com.ta.a.b.b.permissionToOp(str);
        }

        private C0314a() {
            super();
        }
    }

    public static class b {
        public int a(Context context, String str, String str2) {
            return 1;
        }

        public String permissionToOp(String str) {
            return null;
        }

        private b() {
        }
    }

    public static int a(Context context, String str, String str2) {
        return f17206a.a(context, str, str2);
    }

    public static String permissionToOp(String str) {
        return f17206a.permissionToOp(str);
    }
}
