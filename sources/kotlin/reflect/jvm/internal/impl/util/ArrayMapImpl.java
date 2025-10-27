package kotlin.reflect.jvm.internal.impl.util;

import java.util.Arrays;
import java.util.Iterator;
import kotlin.collections.AbstractIterator;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nArrayMap.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ArrayMap.kt\norg/jetbrains/kotlin/util/ArrayMapImpl\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,142:1\n11515#2,11:143\n13644#2,2:154\n13646#2:157\n11526#2:158\n1#3:156\n*S KotlinDebug\n*F\n+ 1 ArrayMap.kt\norg/jetbrains/kotlin/util/ArrayMapImpl\n*L\n137#1:143,11\n137#1:154,2\n137#1:157\n137#1:158\n137#1:156\n*E\n"})
/* loaded from: classes8.dex */
public final class ArrayMapImpl<T> extends ArrayMap<T> {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private Object[] data;
    private int size;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private ArrayMapImpl(Object[] objArr, int i2) {
        super(null);
        this.data = objArr;
        this.size = i2;
    }

    private final void ensureCapacity(int i2) {
        Object[] objArr = this.data;
        if (objArr.length <= i2) {
            Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length * 2);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(this, newSize)");
            this.data = objArrCopyOf;
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.util.ArrayMap
    @Nullable
    public T get(int i2) {
        return (T) ArraysKt___ArraysKt.getOrNull(this.data, i2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.util.ArrayMap
    public int getSize() {
        return this.size;
    }

    @Override // kotlin.reflect.jvm.internal.impl.util.ArrayMap, java.lang.Iterable
    @NotNull
    public Iterator<T> iterator() {
        return new AbstractIterator<T>(this) { // from class: kotlin.reflect.jvm.internal.impl.util.ArrayMapImpl.iterator.1
            private int index = -1;
            final /* synthetic */ ArrayMapImpl<T> this$0;

            {
                this.this$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.collections.AbstractIterator
            public void computeNext() {
                do {
                    int i2 = this.index + 1;
                    this.index = i2;
                    if (i2 >= ((ArrayMapImpl) this.this$0).data.length) {
                        break;
                    }
                } while (((ArrayMapImpl) this.this$0).data[this.index] == null);
                if (this.index >= ((ArrayMapImpl) this.this$0).data.length) {
                    done();
                    return;
                }
                Object obj = ((ArrayMapImpl) this.this$0).data[this.index];
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type T of org.jetbrains.kotlin.util.ArrayMapImpl");
                setNext(obj);
            }
        };
    }

    @Override // kotlin.reflect.jvm.internal.impl.util.ArrayMap
    public void set(int i2, @NotNull T value) {
        Intrinsics.checkNotNullParameter(value, "value");
        ensureCapacity(i2);
        if (this.data[i2] == null) {
            this.size = getSize() + 1;
        }
        this.data[i2] = value;
    }

    public ArrayMapImpl() {
        this(new Object[20], 0);
    }
}
