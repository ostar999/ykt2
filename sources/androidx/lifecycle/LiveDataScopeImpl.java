package androidx.lifecycle;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.tencent.open.SocialConstants;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0019\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0013J\u001f\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0018R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\u0004\u0018\u00018\u00008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR \u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, d2 = {"Landroidx/lifecycle/LiveDataScopeImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/LiveDataScope;", TypedValues.AttributesType.S_TARGET, "Landroidx/lifecycle/CoroutineLiveData;", com.umeng.analytics.pro.d.R, "Lkotlin/coroutines/CoroutineContext;", "(Landroidx/lifecycle/CoroutineLiveData;Lkotlin/coroutines/CoroutineContext;)V", "coroutineContext", "latestValue", "getLatestValue", "()Ljava/lang/Object;", "getTarget$lifecycle_livedata_ktx_release", "()Landroidx/lifecycle/CoroutineLiveData;", "setTarget$lifecycle_livedata_ktx_release", "(Landroidx/lifecycle/CoroutineLiveData;)V", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitSource", "Lkotlinx/coroutines/DisposableHandle;", SocialConstants.PARAM_SOURCE, "Landroidx/lifecycle/LiveData;", "(Landroidx/lifecycle/LiveData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lifecycle-livedata-ktx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class LiveDataScopeImpl<T> implements LiveDataScope<T> {

    @NotNull
    private final CoroutineContext coroutineContext;

    @NotNull
    private CoroutineLiveData<T> target;

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "androidx.lifecycle.LiveDataScopeImpl$emit$2", f = "CoroutineLiveData.kt", i = {}, l = {98}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.lifecycle.LiveDataScopeImpl$emit$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ T $value;
        int label;
        final /* synthetic */ LiveDataScopeImpl<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(LiveDataScopeImpl<T> liveDataScopeImpl, T t2, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.this$0 = liveDataScopeImpl;
            this.$value = t2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass2(this.this$0, this.$value, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineLiveData<T> target$lifecycle_livedata_ktx_release = this.this$0.getTarget$lifecycle_livedata_ktx_release();
                this.label = 1;
                if (target$lifecycle_livedata_ktx_release.clearSource$lifecycle_livedata_ktx_release(this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.this$0.getTarget$lifecycle_livedata_ktx_release().setValue(this.$value);
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "Lkotlinx/coroutines/DisposableHandle;", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "androidx.lifecycle.LiveDataScopeImpl$emitSource$2", f = "CoroutineLiveData.kt", i = {}, l = {94}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.lifecycle.LiveDataScopeImpl$emitSource$2, reason: invalid class name and case insensitive filesystem */
    public static final class C04982 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super DisposableHandle>, Object> {
        final /* synthetic */ LiveData<T> $source;
        int label;
        final /* synthetic */ LiveDataScopeImpl<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C04982(LiveDataScopeImpl<T> liveDataScopeImpl, LiveData<T> liveData, Continuation<? super C04982> continuation) {
            super(2, continuation);
            this.this$0 = liveDataScopeImpl;
            this.$source = liveData;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04982(this.this$0, this.$source, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super DisposableHandle> continuation) {
            return ((C04982) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineLiveData<T> target$lifecycle_livedata_ktx_release = this.this$0.getTarget$lifecycle_livedata_ktx_release();
                LiveData<T> liveData = this.$source;
                this.label = 1;
                obj = target$lifecycle_livedata_ktx_release.emitSource$lifecycle_livedata_ktx_release(liveData, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    public LiveDataScopeImpl(@NotNull CoroutineLiveData<T> target, @NotNull CoroutineContext context) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(context, "context");
        this.target = target;
        this.coroutineContext = context.plus(Dispatchers.getMain().getImmediate());
    }

    @Override // androidx.lifecycle.LiveDataScope
    @Nullable
    public Object emit(T t2, @NotNull Continuation<? super Unit> continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.coroutineContext, new AnonymousClass2(this, t2, null), continuation);
        return objWithContext == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    @Override // androidx.lifecycle.LiveDataScope
    @Nullable
    public Object emitSource(@NotNull LiveData<T> liveData, @NotNull Continuation<? super DisposableHandle> continuation) {
        return BuildersKt.withContext(this.coroutineContext, new C04982(this, liveData, null), continuation);
    }

    @Override // androidx.lifecycle.LiveDataScope
    @Nullable
    public T getLatestValue() {
        return this.target.getValue();
    }

    @NotNull
    public final CoroutineLiveData<T> getTarget$lifecycle_livedata_ktx_release() {
        return this.target;
    }

    public final void setTarget$lifecycle_livedata_ktx_release(@NotNull CoroutineLiveData<T> coroutineLiveData) {
        Intrinsics.checkNotNullParameter(coroutineLiveData, "<set-?>");
        this.target = coroutineLiveData;
    }
}
