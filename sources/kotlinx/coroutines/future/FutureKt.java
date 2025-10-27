package kotlinx.coroutines.future;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001*\u00020\u0005\u001a\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a!\u0010\b\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\t\u001a[\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2'\u0010\u0010\u001a#\b\u0001\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0011¢\u0006\u0002\b\u0014ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u0018\u0010\u0016\u001a\u00020\u0004*\u00020\u00052\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0002\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0017"}, d2 = {"asCompletableFuture", "Ljava/util/concurrent/CompletableFuture;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/Deferred;", "", "Lkotlinx/coroutines/Job;", "asDeferred", "Ljava/util/concurrent/CompletionStage;", "await", "(Ljava/util/concurrent/CompletionStage;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "future", "Lkotlinx/coroutines/CoroutineScope;", com.umeng.analytics.pro.d.R, "Lkotlin/coroutines/CoroutineContext;", "start", "Lkotlinx/coroutines/CoroutineStart;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;)Ljava/util/concurrent/CompletableFuture;", "setupCancellation", "kotlinx-coroutines-jdk8"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class FutureKt {
    @NotNull
    public static final <T> CompletableFuture<T> asCompletableFuture(@NotNull final Deferred<? extends T> deferred) {
        final CompletableFuture<T> completableFuture = new CompletableFuture<>();
        setupCancellation(deferred, completableFuture);
        deferred.invokeOnCompletion(new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.future.FutureKt.asCompletableFuture.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable Throwable th) {
                try {
                    completableFuture.complete(deferred.getCompleted());
                } catch (Throwable th2) {
                    completableFuture.completeExceptionally(th2);
                }
            }
        });
        return completableFuture;
    }

    @NotNull
    public static final <T> Deferred<T> asDeferred(@NotNull CompletionStage<T> completionStage) {
        Throwable cause;
        CompletableFuture completableFuture = completionStage.toCompletableFuture();
        if (!completableFuture.isDone()) {
            final CompletableDeferred completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
            completionStage.whenComplete(new BiConsumer() { // from class: kotlinx.coroutines.future.g
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    FutureKt.m2349asDeferred$lambda4(completableDeferredCompletableDeferred$default, obj, (Throwable) obj2);
                }
            });
            JobKt.cancelFutureOnCompletion(completableDeferredCompletableDeferred$default, completableFuture);
            return completableDeferredCompletableDeferred$default;
        }
        try {
            return CompletableDeferredKt.CompletableDeferred(completableFuture.get());
        } catch (Throwable th) {
            th = th;
            ExecutionException executionException = th instanceof ExecutionException ? (ExecutionException) th : null;
            if (executionException != null && (cause = executionException.getCause()) != null) {
                th = cause;
            }
            CompletableDeferred completableDeferredCompletableDeferred$default2 = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
            completableDeferredCompletableDeferred$default2.completeExceptionally(th);
            return completableDeferredCompletableDeferred$default2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: asDeferred$lambda-4, reason: not valid java name */
    public static final void m2349asDeferred$lambda4(CompletableDeferred completableDeferred, Object obj, Throwable th) {
        Throwable cause;
        try {
            if (th == null) {
                completableDeferred.complete(obj);
                return;
            }
            CompletionException completionException = th instanceof CompletionException ? (CompletionException) th : null;
            if (completionException != null && (cause = completionException.getCause()) != null) {
                th = cause;
            }
            completableDeferred.completeExceptionally(th);
        } catch (Throwable th2) {
            CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, th2);
        }
    }

    @Nullable
    public static final <T> Object await(@NotNull CompletionStage<T> completionStage, @NotNull Continuation<? super T> continuation) throws Throwable {
        final CompletableFuture completableFuture = completionStage.toCompletableFuture();
        if (completableFuture.isDone()) {
            try {
                return completableFuture.get();
            } catch (ExecutionException e2) {
                Throwable cause = e2.getCause();
                if (cause == null) {
                    throw e2;
                }
                throw cause;
            }
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final ContinuationConsumer continuationConsumer = new ContinuationConsumer(cancellableContinuationImpl);
        completionStage.whenComplete(continuationConsumer);
        cancellableContinuationImpl.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.future.FutureKt$await$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable Throwable th) {
                completableFuture.cancel(false);
                continuationConsumer.cont = null;
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    @NotNull
    public static final <T> CompletableFuture<T> future(@NotNull CoroutineScope coroutineScope, @NotNull CoroutineContext coroutineContext, @NotNull CoroutineStart coroutineStart, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2) {
        if (!(!coroutineStart.isLazy())) {
            throw new IllegalArgumentException((coroutineStart + " start is not supported").toString());
        }
        CoroutineContext coroutineContextNewCoroutineContext = CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext);
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        CompletableFutureCoroutine completableFutureCoroutine = new CompletableFutureCoroutine(coroutineContextNewCoroutineContext, completableFuture);
        completableFuture.whenComplete((BiConsumer) completableFutureCoroutine);
        completableFutureCoroutine.start(coroutineStart, completableFutureCoroutine, function2);
        return completableFuture;
    }

    public static /* synthetic */ CompletableFuture future$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i2 & 2) != 0) {
            coroutineStart = CoroutineStart.DEFAULT;
        }
        return future(coroutineScope, coroutineContext, coroutineStart, function2);
    }

    private static final void setupCancellation(final Job job, CompletableFuture<?> completableFuture) {
        completableFuture.whenComplete((BiConsumer<? super Object, ? super Throwable>) new BiConsumer() { // from class: kotlinx.coroutines.future.h
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                FutureKt.m2350setupCancellation$lambda2(job, obj, (Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setupCancellation$lambda-2, reason: not valid java name */
    public static final void m2350setupCancellation$lambda2(Job job, Object obj, Throwable th) {
        if (th != null) {
            CancellationException = th instanceof CancellationException ? (CancellationException) th : null;
            if (CancellationException == null) {
                CancellationException = ExceptionsKt.CancellationException("CompletableFuture was completed exceptionally", th);
            }
        }
        job.cancel(CancellationException);
    }

    @NotNull
    public static final CompletableFuture<Unit> asCompletableFuture(@NotNull Job job) {
        final CompletableFuture<Unit> completableFuture = new CompletableFuture<>();
        setupCancellation(job, completableFuture);
        job.invokeOnCompletion(new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.future.FutureKt.asCompletableFuture.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable Throwable th) {
                if (th == null) {
                    completableFuture.complete(Unit.INSTANCE);
                } else {
                    completableFuture.completeExceptionally(th);
                }
            }
        });
        return completableFuture;
    }
}
