package cn.hutool.core.bean;

/* loaded from: classes.dex */
public class NullWrapperBean<T> {
    private final Class<T> clazz;

    public NullWrapperBean(Class<T> cls) {
        this.clazz = cls;
    }

    public Class<T> getWrappedClass() {
        return this.clazz;
    }
}
