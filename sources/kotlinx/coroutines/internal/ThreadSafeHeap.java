package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.umeng.analytics.pro.am;
import java.lang.Comparable;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.InternalCoroutinesApi;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@InternalCoroutinesApi
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u0000\n\u0002\u0018\u0002\b\u0017\u0018\u0000*\u0012\b\u0000\u0010\u0003*\u00020\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00022\u000605j\u0002`6B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00028\u0000H\u0001¢\u0006\u0004\b\b\u0010\tJ\u0015\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0004\b\n\u0010\tJ.\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00028\u00002\u0014\u0010\r\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\f0\u000bH\u0086\b¢\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0010\u001a\u00020\u0007¢\u0006\u0004\b\u0010\u0010\u0005J2\u0010\u0015\u001a\u0004\u0018\u00018\u00002!\u0010\u0014\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0004\b\u0015\u0010\u0016J\u0011\u0010\u0017\u001a\u0004\u0018\u00018\u0000H\u0001¢\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0019\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u0019\u0010\u0018J\u0017\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u001aH\u0002¢\u0006\u0004\b\u001b\u0010\u001cJ\u0015\u0010\u001d\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010!\u001a\u00028\u00002\u0006\u0010 \u001a\u00020\u001fH\u0001¢\u0006\u0004\b!\u0010\"J&\u0010#\u001a\u0004\u0018\u00018\u00002\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000bH\u0086\b¢\u0006\u0004\b#\u0010\u0016J\u000f\u0010$\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b$\u0010\u0018J\u0018\u0010&\u001a\u00020\u00072\u0006\u0010%\u001a\u00020\u001fH\u0082\u0010¢\u0006\u0004\b&\u0010'J\u0018\u0010(\u001a\u00020\u00072\u0006\u0010%\u001a\u00020\u001fH\u0082\u0010¢\u0006\u0004\b(\u0010'J\u001f\u0010*\u001a\u00020\u00072\u0006\u0010%\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020\u001fH\u0002¢\u0006\u0004\b*\u0010+R \u0010,\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u001a8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b,\u0010-R\u0011\u0010.\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b.\u0010/R$\u00103\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u001f8F@BX\u0086\u000e¢\u0006\f\u001a\u0004\b0\u00101\"\u0004\b2\u0010'¨\u00064"}, d2 = {"Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "", ExifInterface.GPS_DIRECTION_TRUE, "<init>", "()V", "node", "", "addImpl", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)V", "addLast", "Lkotlin/Function1;", "", "cond", "addLastIf", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;Lkotlin/jvm/functions/Function1;)Z", PLVDocumentMarkToolType.CLEAR, "Lkotlin/ParameterName;", "name", "value", "predicate", "find", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "firstImpl", "()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "peek", "", "realloc", "()[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", PLVRemoveMicSiteEvent.EVENT_NAME, "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)Z", "", "index", "removeAtImpl", "(I)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "removeFirstIf", "removeFirstOrNull", am.aC, "siftDownFrom", "(I)V", "siftUpFrom", "j", "swap", "(II)V", am.av, "[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "isEmpty", "()Z", "getSize", "()I", "setSize", DatabaseManager.SIZE, "kotlinx-coroutines-core", "", "Lkotlinx/coroutines/internal/SynchronizedObject;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public class ThreadSafeHeap<T extends ThreadSafeHeapNode & Comparable<? super T>> {

    @NotNull
    private volatile /* synthetic */ int _size = 0;

    @Nullable
    private T[] a;

    private final T[] realloc() {
        T[] tArr = this.a;
        if (tArr == null) {
            T[] tArr2 = (T[]) new ThreadSafeHeapNode[4];
            this.a = tArr2;
            return tArr2;
        }
        if (get_size() < tArr.length) {
            return tArr;
        }
        Object[] objArrCopyOf = Arrays.copyOf(tArr, get_size() * 2);
        Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(this, newSize)");
        T[] tArr3 = (T[]) ((ThreadSafeHeapNode[]) objArrCopyOf);
        this.a = tArr3;
        return tArr3;
    }

    private final void setSize(int i2) {
        this._size = i2;
    }

    private final void siftDownFrom(int i2) {
        while (true) {
            int i3 = (i2 * 2) + 1;
            if (i3 >= get_size()) {
                return;
            }
            T[] tArr = this.a;
            Intrinsics.checkNotNull(tArr);
            int i4 = i3 + 1;
            if (i4 < get_size()) {
                T t2 = tArr[i4];
                Intrinsics.checkNotNull(t2);
                T t3 = tArr[i3];
                Intrinsics.checkNotNull(t3);
                if (((Comparable) t2).compareTo(t3) < 0) {
                    i3 = i4;
                }
            }
            T t4 = tArr[i2];
            Intrinsics.checkNotNull(t4);
            T t5 = tArr[i3];
            Intrinsics.checkNotNull(t5);
            if (((Comparable) t4).compareTo(t5) <= 0) {
                return;
            }
            swap(i2, i3);
            i2 = i3;
        }
    }

    private final void siftUpFrom(int i2) {
        while (i2 > 0) {
            T[] tArr = this.a;
            Intrinsics.checkNotNull(tArr);
            int i3 = (i2 - 1) / 2;
            T t2 = tArr[i3];
            Intrinsics.checkNotNull(t2);
            T t3 = tArr[i2];
            Intrinsics.checkNotNull(t3);
            if (((Comparable) t2).compareTo(t3) <= 0) {
                return;
            }
            swap(i2, i3);
            i2 = i3;
        }
    }

    private final void swap(int i2, int j2) {
        T[] tArr = this.a;
        Intrinsics.checkNotNull(tArr);
        T t2 = tArr[j2];
        Intrinsics.checkNotNull(t2);
        T t3 = tArr[i2];
        Intrinsics.checkNotNull(t3);
        tArr[i2] = t2;
        tArr[j2] = t3;
        t2.setIndex(i2);
        t3.setIndex(j2);
    }

    @PublishedApi
    public final void addImpl(@NotNull T node) {
        node.setHeap(this);
        ThreadSafeHeapNode[] threadSafeHeapNodeArrRealloc = realloc();
        int i2 = get_size();
        setSize(i2 + 1);
        threadSafeHeapNodeArrRealloc[i2] = node;
        node.setIndex(i2);
        siftUpFrom(i2);
    }

    public final void addLast(@NotNull T node) {
        synchronized (this) {
            addImpl(node);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean addLastIf(@NotNull T node, @NotNull Function1<? super T, Boolean> cond) {
        boolean z2;
        synchronized (this) {
            try {
                if (cond.invoke(firstImpl()).booleanValue()) {
                    addImpl(node);
                    z2 = true;
                } else {
                    z2 = false;
                }
                InlineMarker.finallyStart(1);
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
        InlineMarker.finallyEnd(1);
        return z2;
    }

    public final void clear() {
        synchronized (this) {
            T[] tArr = this.a;
            if (tArr != null) {
                ArraysKt___ArraysJvmKt.fill$default(tArr, (Object) null, 0, 0, 6, (Object) null);
            }
            this._size = 0;
            Unit unit = Unit.INSTANCE;
        }
    }

    @Nullable
    public final T find(@NotNull Function1<? super T, Boolean> predicate) {
        T t2;
        synchronized (this) {
            int i2 = get_size();
            int i3 = 0;
            while (true) {
                t2 = null;
                if (i3 >= i2) {
                    break;
                }
                T[] tArr = this.a;
                if (tArr != null) {
                    t2 = (Object) tArr[i3];
                }
                Intrinsics.checkNotNull(t2);
                if (predicate.invoke(t2).booleanValue()) {
                    break;
                }
                i3++;
            }
        }
        return t2;
    }

    @PublishedApi
    @Nullable
    public final T firstImpl() {
        T[] tArr = this.a;
        if (tArr != null) {
            return tArr[0];
        }
        return null;
    }

    /* renamed from: getSize, reason: from getter */
    public final int get_size() {
        return this._size;
    }

    public final boolean isEmpty() {
        return get_size() == 0;
    }

    @Nullable
    public final T peek() {
        T t2;
        synchronized (this) {
            t2 = (T) firstImpl();
        }
        return t2;
    }

    public final boolean remove(@NotNull T node) {
        boolean z2;
        synchronized (this) {
            if (node.getHeap() == null) {
                z2 = false;
            } else {
                removeAtImpl(node.getIndex());
                z2 = true;
            }
        }
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x003a  */
    @kotlin.PublishedApi
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final T removeAtImpl(int r6) {
        /*
            r5 = this;
            T extends kotlinx.coroutines.internal.ThreadSafeHeapNode & java.lang.Comparable<? super T>[] r0 = r5.a
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            int r1 = r5.get_size()
            r2 = -1
            int r1 = r1 + r2
            r5.setSize(r1)
            int r1 = r5.get_size()
            if (r6 >= r1) goto L3d
            int r1 = r5.get_size()
            r5.swap(r6, r1)
            int r1 = r6 + (-1)
            int r1 = r1 / 2
            if (r6 <= 0) goto L3a
            r3 = r0[r6]
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
            java.lang.Comparable r3 = (java.lang.Comparable) r3
            r4 = r0[r1]
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            int r3 = r3.compareTo(r4)
            if (r3 >= 0) goto L3a
            r5.swap(r6, r1)
            r5.siftUpFrom(r1)
            goto L3d
        L3a:
            r5.siftDownFrom(r6)
        L3d:
            int r6 = r5.get_size()
            r6 = r0[r6]
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            r1 = 0
            r6.setHeap(r1)
            r6.setIndex(r2)
            int r2 = r5.get_size()
            r0[r2] = r1
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.ThreadSafeHeap.removeAtImpl(int):kotlinx.coroutines.internal.ThreadSafeHeapNode");
    }

    @Nullable
    public final T removeFirstIf(@NotNull Function1<? super T, Boolean> predicate) {
        synchronized (this) {
            try {
                ThreadSafeHeapNode threadSafeHeapNodeFirstImpl = firstImpl();
                T t2 = null;
                if (threadSafeHeapNodeFirstImpl == null) {
                    InlineMarker.finallyStart(2);
                    InlineMarker.finallyEnd(2);
                    return null;
                }
                if (predicate.invoke(threadSafeHeapNodeFirstImpl).booleanValue()) {
                    t2 = (T) removeAtImpl(0);
                }
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                return t2;
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
    }

    @Nullable
    public final T removeFirstOrNull() {
        T t2;
        synchronized (this) {
            t2 = get_size() > 0 ? (T) removeAtImpl(0) : null;
        }
        return t2;
    }
}
