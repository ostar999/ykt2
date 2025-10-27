package com.ta.utdid2.device;

/* loaded from: classes6.dex */
class e {
    public String g(String str) {
        return com.ta.utdid2.a.a.a.e(str);
    }

    public String h(String str) {
        String strE = com.ta.utdid2.a.a.a.e(str);
        if (!com.ta.utdid2.a.a.f.b(strE)) {
            try {
                return new String(com.ta.utdid2.a.a.b.decode(strE, 0));
            } catch (IllegalArgumentException unused) {
            }
        }
        return null;
    }
}
