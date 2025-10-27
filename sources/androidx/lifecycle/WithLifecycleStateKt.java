package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u001aA\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0081@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a+\u0010\f\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\r\u001a+\u0010\f\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a+\u0010\u0010\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\r\u001a+\u0010\u0010\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a+\u0010\u0011\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\r\u001a+\u0010\u0011\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a3\u0010\u0012\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a3\u0010\u0012\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a3\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0081Hø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0016"}, d2 = {"suspendWithStateAtLeastUnchecked", "R", "Landroidx/lifecycle/Lifecycle;", "state", "Landroidx/lifecycle/Lifecycle$State;", "dispatchNeeded", "", "lifecycleDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "block", "Lkotlin/Function0;", "(Landroidx/lifecycle/Lifecycle;Landroidx/lifecycle/Lifecycle$State;ZLkotlinx/coroutines/CoroutineDispatcher;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withCreated", "(Landroidx/lifecycle/Lifecycle;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/lifecycle/LifecycleOwner;", "(Landroidx/lifecycle/LifecycleOwner;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withResumed", "withStarted", "withStateAtLeast", "(Landroidx/lifecycle/Lifecycle;Landroidx/lifecycle/Lifecycle$State;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Landroidx/lifecycle/LifecycleOwner;Landroidx/lifecycle/Lifecycle$State;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withStateAtLeastUnchecked", "lifecycle-runtime-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WithLifecycleStateKt {

    /* JADX INFO: Add missing generic type declarations: [R] */
    @Metadata(d1 = {"\u0000\u0004\n\u0002\b\u0002\u0010\u0001\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u0000H\n"}, d2 = {"R", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    /* renamed from: androidx.lifecycle.WithLifecycleStateKt$withStateAtLeastUnchecked$2, reason: invalid class name */
    public static final class AnonymousClass2<R> extends Lambda implements Function0<R> {
        final /* synthetic */ Function0<R> $block;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass2(Function0<? extends R> function0) {
            super(0);
            this.$block = function0;
        }

        @Override // kotlin.jvm.functions.Function0
        public final R invoke() {
            return this.$block.invoke();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.lifecycle.LifecycleObserver, androidx.lifecycle.WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1] */
    @PublishedApi
    @Nullable
    public static final <R> Object suspendWithStateAtLeastUnchecked(@NotNull final Lifecycle lifecycle, @NotNull final Lifecycle.State state, boolean z2, @NotNull final CoroutineDispatcher coroutineDispatcher, @NotNull final Function0<? extends R> function0, @NotNull Continuation<? super R> continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final ?? r12 = new LifecycleEventObserver() { // from class: androidx.lifecycle.WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(@NotNull LifecycleOwner source, @NotNull Lifecycle.Event event) {
                Object objM783constructorimpl;
                Intrinsics.checkNotNullParameter(source, "source");
                Intrinsics.checkNotNullParameter(event, "event");
                if (event != Lifecycle.Event.upTo(state)) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        lifecycle.removeObserver(this);
                        Continuation continuation2 = cancellableContinuationImpl;
                        LifecycleDestroyedException lifecycleDestroyedException = new LifecycleDestroyedException();
                        Result.Companion companion = Result.INSTANCE;
                        continuation2.resumeWith(Result.m783constructorimpl(ResultKt.createFailure(lifecycleDestroyedException)));
                        return;
                    }
                    return;
                }
                lifecycle.removeObserver(this);
                Continuation continuation3 = cancellableContinuationImpl;
                Function0<R> function02 = function0;
                try {
                    Result.Companion companion2 = Result.INSTANCE;
                    objM783constructorimpl = Result.m783constructorimpl(function02.invoke());
                } catch (Throwable th) {
                    Result.Companion companion3 = Result.INSTANCE;
                    objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
                }
                continuation3.resumeWith(objM783constructorimpl);
            }
        };
        if (z2) {
            coroutineDispatcher.mo2362dispatch(EmptyCoroutineContext.INSTANCE, new Runnable() { // from class: androidx.lifecycle.WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$1
                @Override // java.lang.Runnable
                public final void run() {
                    lifecycle.addObserver(r12);
                }
            });
        } else {
            lifecycle.addObserver(r12);
        }
        cancellableContinuationImpl.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: androidx.lifecycle.WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$2
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
                CoroutineDispatcher coroutineDispatcher2 = coroutineDispatcher;
                EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
                if (!coroutineDispatcher2.isDispatchNeeded(emptyCoroutineContext)) {
                    lifecycle.removeObserver(r12);
                    return;
                }
                CoroutineDispatcher coroutineDispatcher3 = coroutineDispatcher;
                final Lifecycle lifecycle2 = lifecycle;
                final WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1 withLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1 = r12;
                coroutineDispatcher3.mo2362dispatch(emptyCoroutineContext, new Runnable() { // from class: androidx.lifecycle.WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$2.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        lifecycle2.removeObserver(withLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1);
                    }
                });
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    @Nullable
    public static final <R> Object withCreated(@NotNull Lifecycle lifecycle, @NotNull Function0<? extends R> function0, @NotNull Continuation<? super R> continuation) {
        Lifecycle.State state = Lifecycle.State.CREATED;
        MainCoroutineDispatcher immediate = Dispatchers.getMain().getImmediate();
        boolean zIsDispatchNeeded = immediate.isDispatchNeeded(continuation.getContext());
        if (!zIsDispatchNeeded) {
            if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            }
            if (lifecycle.getCurrentState().compareTo(state) >= 0) {
                return function0.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked(lifecycle, state, zIsDispatchNeeded, immediate, new AnonymousClass2(function0), continuation);
    }

    private static final <R> Object withCreated$$forInline(Lifecycle lifecycle, Function0<? extends R> function0, Continuation<? super R> continuation) {
        Lifecycle.State state = Lifecycle.State.CREATED;
        Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        throw null;
    }

    @Nullable
    public static final <R> Object withResumed(@NotNull Lifecycle lifecycle, @NotNull Function0<? extends R> function0, @NotNull Continuation<? super R> continuation) {
        Lifecycle.State state = Lifecycle.State.RESUMED;
        MainCoroutineDispatcher immediate = Dispatchers.getMain().getImmediate();
        boolean zIsDispatchNeeded = immediate.isDispatchNeeded(continuation.getContext());
        if (!zIsDispatchNeeded) {
            if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            }
            if (lifecycle.getCurrentState().compareTo(state) >= 0) {
                return function0.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked(lifecycle, state, zIsDispatchNeeded, immediate, new AnonymousClass2(function0), continuation);
    }

    private static final <R> Object withResumed$$forInline(Lifecycle lifecycle, Function0<? extends R> function0, Continuation<? super R> continuation) {
        Lifecycle.State state = Lifecycle.State.RESUMED;
        Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        throw null;
    }

    @Nullable
    public static final <R> Object withStarted(@NotNull Lifecycle lifecycle, @NotNull Function0<? extends R> function0, @NotNull Continuation<? super R> continuation) {
        Lifecycle.State state = Lifecycle.State.STARTED;
        MainCoroutineDispatcher immediate = Dispatchers.getMain().getImmediate();
        boolean zIsDispatchNeeded = immediate.isDispatchNeeded(continuation.getContext());
        if (!zIsDispatchNeeded) {
            if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            }
            if (lifecycle.getCurrentState().compareTo(state) >= 0) {
                return function0.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked(lifecycle, state, zIsDispatchNeeded, immediate, new AnonymousClass2(function0), continuation);
    }

    private static final <R> Object withStarted$$forInline(Lifecycle lifecycle, Function0<? extends R> function0, Continuation<? super R> continuation) {
        Lifecycle.State state = Lifecycle.State.STARTED;
        Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        throw null;
    }

    @Nullable
    public static final <R> Object withStateAtLeast(@NotNull Lifecycle lifecycle, @NotNull Lifecycle.State state, @NotNull Function0<? extends R> function0, @NotNull Continuation<? super R> continuation) {
        if (!(state.compareTo(Lifecycle.State.CREATED) >= 0)) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("target state must be CREATED or greater, found ", state).toString());
        }
        MainCoroutineDispatcher immediate = Dispatchers.getMain().getImmediate();
        boolean zIsDispatchNeeded = immediate.isDispatchNeeded(continuation.getContext());
        if (!zIsDispatchNeeded) {
            if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            }
            if (lifecycle.getCurrentState().compareTo(state) >= 0) {
                return function0.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked(lifecycle, state, zIsDispatchNeeded, immediate, new AnonymousClass2(function0), continuation);
    }

    private static final <R> Object withStateAtLeast$$forInline(Lifecycle lifecycle, Lifecycle.State state, Function0<? extends R> function0, Continuation<? super R> continuation) {
        if (!(state.compareTo(Lifecycle.State.CREATED) >= 0)) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("target state must be CREATED or greater, found ", state).toString());
        }
        Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        throw null;
    }

    @PublishedApi
    @Nullable
    public static final <R> Object withStateAtLeastUnchecked(@NotNull Lifecycle lifecycle, @NotNull Lifecycle.State state, @NotNull Function0<? extends R> function0, @NotNull Continuation<? super R> continuation) {
        MainCoroutineDispatcher immediate = Dispatchers.getMain().getImmediate();
        boolean zIsDispatchNeeded = immediate.isDispatchNeeded(continuation.getContext());
        if (!zIsDispatchNeeded) {
            if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            }
            if (lifecycle.getCurrentState().compareTo(state) >= 0) {
                return function0.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked(lifecycle, state, zIsDispatchNeeded, immediate, new AnonymousClass2(function0), continuation);
    }

    @PublishedApi
    private static final <R> Object withStateAtLeastUnchecked$$forInline(Lifecycle lifecycle, Lifecycle.State state, Function0<? extends R> function0, Continuation<? super R> continuation) {
        Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        throw null;
    }

    private static final <R> Object withCreated$$forInline(LifecycleOwner lifecycleOwner, Function0<? extends R> function0, Continuation<? super R> continuation) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
        Lifecycle.State state = Lifecycle.State.CREATED;
        Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        throw null;
    }

    private static final <R> Object withResumed$$forInline(LifecycleOwner lifecycleOwner, Function0<? extends R> function0, Continuation<? super R> continuation) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
        Lifecycle.State state = Lifecycle.State.RESUMED;
        Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        throw null;
    }

    private static final <R> Object withStarted$$forInline(LifecycleOwner lifecycleOwner, Function0<? extends R> function0, Continuation<? super R> continuation) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
        Lifecycle.State state = Lifecycle.State.STARTED;
        Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        throw null;
    }

    private static final <R> Object withStateAtLeast$$forInline(LifecycleOwner lifecycleOwner, Lifecycle.State state, Function0<? extends R> function0, Continuation<? super R> continuation) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
        if (!(state.compareTo(Lifecycle.State.CREATED) >= 0)) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("target state must be CREATED or greater, found ", state).toString());
        }
        Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        throw null;
    }

    @Nullable
    public static final <R> Object withCreated(@NotNull LifecycleOwner lifecycleOwner, @NotNull Function0<? extends R> function0, @NotNull Continuation<? super R> continuation) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
        Lifecycle.State state = Lifecycle.State.CREATED;
        MainCoroutineDispatcher immediate = Dispatchers.getMain().getImmediate();
        boolean zIsDispatchNeeded = immediate.isDispatchNeeded(continuation.getContext());
        if (!zIsDispatchNeeded) {
            if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
                if (lifecycle.getCurrentState().compareTo(state) >= 0) {
                    return function0.invoke();
                }
            } else {
                throw new LifecycleDestroyedException();
            }
        }
        return suspendWithStateAtLeastUnchecked(lifecycle, state, zIsDispatchNeeded, immediate, new AnonymousClass2(function0), continuation);
    }

    @Nullable
    public static final <R> Object withResumed(@NotNull LifecycleOwner lifecycleOwner, @NotNull Function0<? extends R> function0, @NotNull Continuation<? super R> continuation) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
        Lifecycle.State state = Lifecycle.State.RESUMED;
        MainCoroutineDispatcher immediate = Dispatchers.getMain().getImmediate();
        boolean zIsDispatchNeeded = immediate.isDispatchNeeded(continuation.getContext());
        if (!zIsDispatchNeeded) {
            if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
                if (lifecycle.getCurrentState().compareTo(state) >= 0) {
                    return function0.invoke();
                }
            } else {
                throw new LifecycleDestroyedException();
            }
        }
        return suspendWithStateAtLeastUnchecked(lifecycle, state, zIsDispatchNeeded, immediate, new AnonymousClass2(function0), continuation);
    }

    @Nullable
    public static final <R> Object withStarted(@NotNull LifecycleOwner lifecycleOwner, @NotNull Function0<? extends R> function0, @NotNull Continuation<? super R> continuation) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
        Lifecycle.State state = Lifecycle.State.STARTED;
        MainCoroutineDispatcher immediate = Dispatchers.getMain().getImmediate();
        boolean zIsDispatchNeeded = immediate.isDispatchNeeded(continuation.getContext());
        if (!zIsDispatchNeeded) {
            if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
                if (lifecycle.getCurrentState().compareTo(state) >= 0) {
                    return function0.invoke();
                }
            } else {
                throw new LifecycleDestroyedException();
            }
        }
        return suspendWithStateAtLeastUnchecked(lifecycle, state, zIsDispatchNeeded, immediate, new AnonymousClass2(function0), continuation);
    }

    @Nullable
    public static final <R> Object withStateAtLeast(@NotNull LifecycleOwner lifecycleOwner, @NotNull Lifecycle.State state, @NotNull Function0<? extends R> function0, @NotNull Continuation<? super R> continuation) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
        if (state.compareTo(Lifecycle.State.CREATED) >= 0) {
            MainCoroutineDispatcher immediate = Dispatchers.getMain().getImmediate();
            boolean zIsDispatchNeeded = immediate.isDispatchNeeded(continuation.getContext());
            if (!zIsDispatchNeeded) {
                if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
                    if (lifecycle.getCurrentState().compareTo(state) >= 0) {
                        return function0.invoke();
                    }
                } else {
                    throw new LifecycleDestroyedException();
                }
            }
            return suspendWithStateAtLeastUnchecked(lifecycle, state, zIsDispatchNeeded, immediate, new AnonymousClass2(function0), continuation);
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("target state must be CREATED or greater, found ", state).toString());
    }
}
