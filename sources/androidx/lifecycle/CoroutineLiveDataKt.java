package androidx.lifecycle;

import androidx.annotation.RequiresApi;
import androidx.exifinterface.media.ExifInterface;
import com.tencent.open.SocialConstants;
import java.time.Duration;
import kotlin.BuilderInference;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a_\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2/\b\u0001\u0010\t\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\n¢\u0006\u0002\b\u000fH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a_\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0011\u001a\u00020\u00012/\b\u0001\u0010\t\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\n¢\u0006\u0002\b\u000fø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a/\u0010\u0013\u001a\u00020\u0014\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003H\u0080@ø\u0001\u0000¢\u0006\u0002\u0010\u0017\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000*`\b\u0000\u0010\u0018\u001a\u0004\b\u0000\u0010\u0004\")\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\n¢\u0006\u0002\b\u000f2)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\n¢\u0006\u0002\b\u000f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, d2 = {"DEFAULT_TIMEOUT", "", "liveData", "Landroidx/lifecycle/LiveData;", ExifInterface.GPS_DIRECTION_TRUE, com.umeng.analytics.pro.d.R, "Lkotlin/coroutines/CoroutineContext;", "timeout", "Ljava/time/Duration;", "block", "Lkotlin/Function2;", "Landroidx/lifecycle/LiveDataScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Ljava/time/Duration;Lkotlin/jvm/functions/Function2;)Landroidx/lifecycle/LiveData;", "timeoutInMs", "(Lkotlin/coroutines/CoroutineContext;JLkotlin/jvm/functions/Function2;)Landroidx/lifecycle/LiveData;", "addDisposableSource", "Landroidx/lifecycle/EmittedSource;", "Landroidx/lifecycle/MediatorLiveData;", SocialConstants.PARAM_SOURCE, "(Landroidx/lifecycle/MediatorLiveData;Landroidx/lifecycle/LiveData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Block", "lifecycle-livedata-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CoroutineLiveDataKt {
    public static final long DEFAULT_TIMEOUT = 5000;

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "Landroidx/lifecycle/EmittedSource;", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    @DebugMetadata(c = "androidx.lifecycle.CoroutineLiveDataKt$addDisposableSource$2", f = "CoroutineLiveData.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.lifecycle.CoroutineLiveDataKt$addDisposableSource$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super EmittedSource>, Object> {
        final /* synthetic */ LiveData<T> $source;
        final /* synthetic */ MediatorLiveData<T> $this_addDisposableSource;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(MediatorLiveData<T> mediatorLiveData, LiveData<T> liveData, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$this_addDisposableSource = mediatorLiveData;
            this.$source = liveData;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass2(this.$this_addDisposableSource, this.$source, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super EmittedSource> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            final MediatorLiveData<T> mediatorLiveData = this.$this_addDisposableSource;
            mediatorLiveData.addSource(this.$source, new Observer() { // from class: androidx.lifecycle.CoroutineLiveDataKt.addDisposableSource.2.1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(T t2) {
                    mediatorLiveData.setValue(t2);
                }
            });
            return new EmittedSource(this.$source, this.$this_addDisposableSource);
        }
    }

    @Nullable
    public static final <T> Object addDisposableSource(@NotNull MediatorLiveData<T> mediatorLiveData, @NotNull LiveData<T> liveData, @NotNull Continuation<? super EmittedSource> continuation) {
        return BuildersKt.withContext(Dispatchers.getMain().getImmediate(), new AnonymousClass2(mediatorLiveData, liveData, null), continuation);
    }

    @NotNull
    public static final <T> LiveData<T> liveData(@NotNull CoroutineContext context, long j2, @BuilderInference @NotNull Function2<? super LiveDataScope<T>, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(block, "block");
        return new CoroutineLiveData(context, j2, block);
    }

    public static /* synthetic */ LiveData liveData$default(CoroutineContext coroutineContext, long j2, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i2 & 2) != 0) {
            j2 = 5000;
        }
        return liveData(coroutineContext, j2, function2);
    }

    @RequiresApi(26)
    @NotNull
    public static final <T> LiveData<T> liveData(@NotNull CoroutineContext context, @NotNull Duration timeout, @BuilderInference @NotNull Function2<? super LiveDataScope<T>, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(timeout, "timeout");
        Intrinsics.checkNotNullParameter(block, "block");
        return new CoroutineLiveData(context, timeout.toMillis(), block);
    }

    public static /* synthetic */ LiveData liveData$default(CoroutineContext coroutineContext, Duration duration, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return liveData(coroutineContext, duration, function2);
    }
}
