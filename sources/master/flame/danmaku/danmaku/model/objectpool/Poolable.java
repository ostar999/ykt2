package master.flame.danmaku.danmaku.model.objectpool;

/* loaded from: classes8.dex */
public interface Poolable<T> {
    T getNextPoolable();

    boolean isPooled();

    void setNextPoolable(T t2);

    void setPooled(boolean z2);
}
