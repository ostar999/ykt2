package com.tencent.bugly.proguard;

import java.io.Serializable;

/* loaded from: classes6.dex */
public abstract class m implements Serializable {
    public abstract void a(k kVar);

    public abstract void a(l lVar);

    public abstract void a(StringBuilder sb, int i2);

    public String toString() {
        StringBuilder sb = new StringBuilder();
        a(sb, 0);
        return sb.toString();
    }
}
