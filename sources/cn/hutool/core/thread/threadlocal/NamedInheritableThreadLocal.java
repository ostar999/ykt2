package cn.hutool.core.thread.threadlocal;

/* loaded from: classes.dex */
public class NamedInheritableThreadLocal<T> extends InheritableThreadLocal<T> {
    private final String name;

    public NamedInheritableThreadLocal(String str) {
        this.name = str;
    }

    public String toString() {
        return this.name;
    }
}
