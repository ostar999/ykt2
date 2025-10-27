package kotlinx.coroutines.future;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.Job;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0012\u0012\u0006\u0012\u0004\u0018\u0001H\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003B\u001b\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b¢\u0006\u0002\u0010\tJ!\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00018\u00002\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\u000eJ\u0018\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J\u0015\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0014R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lkotlinx/coroutines/future/CompletableFutureCoroutine;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/AbstractCoroutine;", "Ljava/util/function/BiConsumer;", "", com.umeng.analytics.pro.d.R, "Lkotlin/coroutines/CoroutineContext;", "future", "Ljava/util/concurrent/CompletableFuture;", "(Lkotlin/coroutines/CoroutineContext;Ljava/util/concurrent/CompletableFuture;)V", "accept", "", "value", "exception", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "onCancelled", "cause", "handled", "", "onCompleted", "(Ljava/lang/Object;)V", "kotlinx-coroutines-jdk8"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
final class CompletableFutureCoroutine<T> extends AbstractCoroutine<T> implements BiConsumer<T, Throwable> {

    @NotNull
    private final CompletableFuture<T> future;

    public CompletableFutureCoroutine(@NotNull CoroutineContext coroutineContext, @NotNull CompletableFuture<T> completableFuture) {
        super(coroutineContext, true, true);
        this.future = completableFuture;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.BiConsumer
    public /* bridge */ /* synthetic */ void accept(Object obj, Throwable th) {
        accept2((CompletableFutureCoroutine<T>) obj, th);
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    public void onCancelled(@NotNull Throwable cause, boolean handled) {
        if (this.future.completeExceptionally(cause) || handled) {
            return;
        }
        CoroutineExceptionHandlerKt.handleCoroutineException(get$context(), cause);
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    public void onCompleted(T value) {
        this.future.complete(value);
    }

    /* renamed from: accept, reason: avoid collision after fix types in other method */
    public void accept2(@Nullable T value, @Nullable Throwable exception) {
        Job.DefaultImpls.cancel$default((Job) this, (CancellationException) null, 1, (Object) null);
    }
}
