package kotlin.reflect.jvm.internal;

import androidx.exifinterface.media.ExifInterface;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u0016\u0010\u0003\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016J\u0019\u0010\u000b\u001a\u00028\u00002\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0016¢\u0006\u0002\u0010\rR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlin/reflect/jvm/internal/ClassValueCache;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlin/reflect/jvm/internal/CacheByClass;", "compute", "Lkotlin/Function1;", "Ljava/lang/Class;", "(Lkotlin/jvm/functions/Function1;)V", "classValue", "Lkotlin/reflect/jvm/internal/ComputableClassValue;", PLVDocumentMarkToolType.CLEAR, "", "get", "key", "(Ljava/lang/Class;)Ljava/lang/Object;", "kotlin-reflection"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCacheByClass.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CacheByClass.kt\nkotlin/reflect/jvm/internal/ClassValueCache\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,96:1\n1#2:97\n*E\n"})
/* loaded from: classes8.dex */
final class ClassValueCache<V> extends CacheByClass<V> {

    @NotNull
    private volatile ComputableClassValue<V> classValue;

    public ClassValueCache(@NotNull Function1<? super Class<?>, ? extends V> compute) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        this.classValue = new ComputableClassValue<>(compute);
    }

    @Override // kotlin.reflect.jvm.internal.CacheByClass
    public void clear() {
        this.classValue = this.classValue.createNewCopy();
    }

    @Override // kotlin.reflect.jvm.internal.CacheByClass
    public V get(@NotNull Class<?> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        ComputableClassValue<V> computableClassValue = this.classValue;
        V v2 = computableClassValue.get(key).get();
        if (v2 != null) {
            return v2;
        }
        computableClassValue.remove(key);
        V v3 = computableClassValue.get(key).get();
        return v3 != null ? v3 : computableClassValue.compute.invoke(key);
    }
}
