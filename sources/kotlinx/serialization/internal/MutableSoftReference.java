package kotlinx.serialization.internal;

import androidx.exifinterface.media.ExifInterface;
import java.lang.ref.SoftReference;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0019\u0010\u0006\u001a\u00028\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b¢\u0006\u0002\u0010\tR\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lkotlinx/serialization/internal/MutableSoftReference;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "reference", "Ljava/lang/ref/SoftReference;", "getOrSetWithLock", "factory", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlinx-serialization-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCaching.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Caching.kt\nkotlinx/serialization/internal/MutableSoftReference\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,203:1\n1#2:204\n*E\n"})
/* loaded from: classes8.dex */
final class MutableSoftReference<T> {

    @JvmField
    @NotNull
    public volatile SoftReference<T> reference = new SoftReference<>(null);

    public final synchronized T getOrSetWithLock(@NotNull Function0<? extends T> factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        T t2 = this.reference.get();
        if (t2 != null) {
            return t2;
        }
        T tInvoke = factory.invoke();
        this.reference = new SoftReference<>(tInvoke);
        return tInvoke;
    }
}
