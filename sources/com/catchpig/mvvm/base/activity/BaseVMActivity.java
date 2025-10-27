package com.catchpig.mvvm.base.activity;

import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;
import com.catchpig.mvvm.base.view.BaseVMView;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.catchpig.mvvm.widget.refresh.RefreshRecyclerView;
import com.catchpig.utils.ext.LogExtKt;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 (*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\u00020\u0006:\u0001(B\u0005¢\u0006\u0002\u0010\u0007JZ\u0010\u0012\u001a\u00020\u0013\"\u0004\b\u0002\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00162#\u0010\u0017\u001a\u001f\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00182\u0017\u0010\u001d\u001a\u0013\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00130\u0018¢\u0006\u0002\b\u001eH\u0017JZ\u0010\u001f\u001a\u00020\u0013\"\u0004\b\u0002\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00162#\u0010\u0017\u001a\u001f\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00182\u0017\u0010\u001d\u001a\u0013\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00130\u0018¢\u0006\u0002\b\u001eH\u0017JZ\u0010 \u001a\u00020\u0013\"\u0004\b\u0002\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00162#\u0010\u0017\u001a\u001f\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00182\u0017\u0010\u001d\u001a\u0013\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00130\u0018¢\u0006\u0002\b\u001eH\u0017J*\u0010!\u001a\u00020\u0013\"\u0004\b\u0002\u0010\u00142\u0012\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\"0\u00162\u0006\u0010#\u001a\u00020$H\u0017J\u0012\u0010%\u001a\u00020\u00132\b\u0010&\u001a\u0004\u0018\u00010'H\u0015R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\u000e\u001a\u00028\u00018FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\r\u001a\u0004\b\u000f\u0010\u0010¨\u0006)"}, d2 = {"Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "VB", "Landroidx/viewbinding/ViewBinding;", "VM", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "Lcom/catchpig/mvvm/base/activity/BaseActivity;", "Lcom/catchpig/mvvm/base/view/BaseVMView;", "()V", "fullTag", "", "getFullTag", "()Ljava/lang/String;", "fullTag$delegate", "Lkotlin/Lazy;", "viewModel", "getViewModel", "()Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "viewModel$delegate", "lifecycleFlow", "", ExifInterface.GPS_DIRECTION_TRUE, "flow", "Lkotlinx/coroutines/flow/Flow;", "errorCallback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "t", com.alipay.sdk.authjs.a.f3170c, "Lkotlin/ExtensionFunctionType;", "lifecycleFlowLoadingDialog", "lifecycleFlowLoadingView", "lifecycleFlowRefresh", "", "refresh", "Lcom/catchpig/mvvm/widget/refresh/RefreshRecyclerView;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public abstract class BaseVMActivity<VB extends ViewBinding, VM extends BaseViewModel> extends BaseActivity<VB> implements BaseVMView {

    @NotNull
    private static final String TAG = "BaseVMActivity";

    /* renamed from: fullTag$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy fullTag = LazyKt__LazyJVMKt.lazy(new Function0<String>(this) { // from class: com.catchpig.mvvm.base.activity.BaseVMActivity$fullTag$2
        final /* synthetic */ BaseVMActivity<VB, VM> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        {
            super(0);
            this.this$0 = this;
        }

        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final String invoke() {
            return this.this$0.getClass().getSimpleName() + "_BaseVMActivity";
        }
    });

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy viewModel = LazyKt__LazyJVMKt.lazy(new Function0<VM>(this) { // from class: com.catchpig.mvvm.base.activity.BaseVMActivity$viewModel$2
        final /* synthetic */ BaseVMActivity<VB, VM> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        {
            super(0);
            this.this$0 = this;
        }

        /* JADX WARN: Incorrect return type in method signature: ()TVM; */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final BaseViewModel invoke() {
            Type genericSuperclass = this.this$0.getClass().getGenericSuperclass();
            Intrinsics.checkNotNull(genericSuperclass, "null cannot be cast to non-null type java.lang.reflect.ParameterizedType");
            Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1];
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type java.lang.Class<VM of com.catchpig.mvvm.base.activity.BaseVMActivity>");
            return (BaseViewModel) new ViewModelProvider(this.this$0, new ViewModelProvider.NewInstanceFactory()).get((Class) type);
        }
    });

    /* JADX WARN: Unknown type variable: T in type: kotlin.jvm.functions.Function1<T, kotlin.Unit> */
    /* JADX WARN: Unknown type variable: T in type: kotlinx.coroutines.flow.Flow<T> */
    @Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0007\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\u00020\u0005H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlow$1", f = "BaseVMActivity.kt", i = {}, l = {88}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlow$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1<T, Unit> $callback;
        final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
        final /* synthetic */ Flow<T> $flow;
        int label;
        final /* synthetic */ BaseVMActivity<VB, VM> this$0;

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\t\u001a\u00020\b\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u0007\u001a\u00020\u0006H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/flow/FlowCollector;", "", "t", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlow$1$1", f = "BaseVMActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlow$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00851<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ BaseVMActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public C00851(BaseVMActivity<VB, VM> baseVMActivity, Function1<? super Throwable, Unit> function1, Continuation<? super C00851> continuation) {
                super(3, continuation);
                this.this$0 = baseVMActivity;
                this.$errorCallback = function1;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                C00851 c00851 = new C00851(this.this$0, this.$errorCallback, continuation);
                c00851.L$0 = th;
                return c00851.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Throwable th = (Throwable) this.L$0;
                KotlinMvvmCompiler.INSTANCE.onError(this.this$0, th);
                Function1<Throwable, Unit> function1 = this.$errorCallback;
                if (function1 != null) {
                    function1.invoke(th);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\t\u001a\u00020\b\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/flow/FlowCollector;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlow$1$2", f = "BaseVMActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlow$1$2, reason: invalid class name */
        public static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ BaseVMActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(BaseVMActivity<VB, VM> baseVMActivity, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.this$0 = baseVMActivity;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @Nullable Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new AnonymousClass2(this.this$0, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                LogExtKt.logd("lifecycleFlow:onCompletion", this.this$0.getFullTag());
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Unknown type variable: T in type: kotlin.jvm.functions.Function1<? super T, kotlin.Unit> */
        /* JADX WARN: Unknown type variable: T in type: kotlinx.coroutines.flow.Flow<? extends T> */
        public AnonymousClass1(Flow<? extends T> flow, BaseVMActivity<VB, VM> baseVMActivity, Function1<? super Throwable, Unit> function1, Function1<? super T, Unit> function12, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$flow = flow;
            this.this$0 = baseVMActivity;
            this.$errorCallback = function1;
            this.$callback = function12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass1(this.$flow, this.this$0, this.$errorCallback, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowOnCompletion = FlowKt.onCompletion(FlowKt.m2338catch(FlowKt.flowOn(this.$flow, Dispatchers.getIO()), new C00851(this.this$0, this.$errorCallback, null)), new AnonymousClass2(this.this$0, null));
                final Function1<T, Unit> function1 = this.$callback;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.catchpig.mvvm.base.activity.BaseVMActivity.lifecycleFlow.1.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    @Nullable
                    public final Object emit(T t2, @NotNull Continuation<? super Unit> continuation) {
                        function1.invoke(t2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowOnCompletion.collect(flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Unknown type variable: T in type: kotlin.jvm.functions.Function1<T, kotlin.Unit> */
    /* JADX WARN: Unknown type variable: T in type: kotlinx.coroutines.flow.Flow<T> */
    @Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0007\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\u00020\u0005H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingDialog$1", f = "BaseVMActivity.kt", i = {}, l = {144}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingDialog$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05171 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1<T, Unit> $callback;
        final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
        final /* synthetic */ Flow<T> $flow;
        int label;
        final /* synthetic */ BaseVMActivity<VB, VM> this$0;

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0007\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\b\u0012\u0004\u0012\u00028\u00000\u0005H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/flow/FlowCollector;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingDialog$1$1", f = "BaseVMActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingDialog$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00861<T> extends SuspendLambda implements Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ BaseVMActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00861(BaseVMActivity<VB, VM> baseVMActivity, Continuation<? super C00861> continuation) {
                super(2, continuation);
                this.this$0 = baseVMActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                return new C00861(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @Nullable Continuation<? super Unit> continuation) {
                return ((C00861) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.loadingDialog();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\t\u001a\u00020\b\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/flow/FlowCollector;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingDialog$1$2", f = "BaseVMActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingDialog$1$2, reason: invalid class name */
        public static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ BaseVMActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(BaseVMActivity<VB, VM> baseVMActivity, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.this$0 = baseVMActivity;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @Nullable Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new AnonymousClass2(this.this$0, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                LogExtKt.logd("lifecycleFlowLoadingDialog:onCompletion", this.this$0.getFullTag());
                this.this$0.hideLoading();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\t\u001a\u00020\b\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u0007\u001a\u00020\u0006H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/flow/FlowCollector;", "", "t", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingDialog$1$3", f = "BaseVMActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingDialog$1$3, reason: invalid class name */
        public static final class AnonymousClass3<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ BaseVMActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass3(BaseVMActivity<VB, VM> baseVMActivity, Function1<? super Throwable, Unit> function1, Continuation<? super AnonymousClass3> continuation) {
                super(3, continuation);
                this.this$0 = baseVMActivity;
                this.$errorCallback = function1;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0, this.$errorCallback, continuation);
                anonymousClass3.L$0 = th;
                return anonymousClass3.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Throwable th = (Throwable) this.L$0;
                KotlinMvvmCompiler.INSTANCE.onError(this.this$0, th);
                Function1<Throwable, Unit> function1 = this.$errorCallback;
                if (function1 != null) {
                    function1.invoke(th);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Unknown type variable: T in type: kotlin.jvm.functions.Function1<? super T, kotlin.Unit> */
        /* JADX WARN: Unknown type variable: T in type: kotlinx.coroutines.flow.Flow<? extends T> */
        public C05171(Flow<? extends T> flow, BaseVMActivity<VB, VM> baseVMActivity, Function1<? super Throwable, Unit> function1, Function1<? super T, Unit> function12, Continuation<? super C05171> continuation) {
            super(2, continuation);
            this.$flow = flow;
            this.this$0 = baseVMActivity;
            this.$errorCallback = function1;
            this.$callback = function12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05171(this.$flow, this.this$0, this.$errorCallback, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05171) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowM2338catch = FlowKt.m2338catch(FlowKt.onCompletion(FlowKt.onStart(FlowKt.flowOn(this.$flow, Dispatchers.getIO()), new C00861(this.this$0, null)), new AnonymousClass2(this.this$0, null)), new AnonymousClass3(this.this$0, this.$errorCallback, null));
                final Function1<T, Unit> function1 = this.$callback;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.catchpig.mvvm.base.activity.BaseVMActivity.lifecycleFlowLoadingDialog.1.4
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    @Nullable
                    public final Object emit(T t2, @NotNull Continuation<? super Unit> continuation) {
                        function1.invoke(t2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowM2338catch.collect(flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Unknown type variable: T in type: kotlin.jvm.functions.Function1<T, kotlin.Unit> */
    /* JADX WARN: Unknown type variable: T in type: kotlinx.coroutines.flow.Flow<T> */
    @Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0007\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\u00020\u0005H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingView$1", f = "BaseVMActivity.kt", i = {}, l = {116}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05181 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1<T, Unit> $callback;
        final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
        final /* synthetic */ Flow<T> $flow;
        int label;
        final /* synthetic */ BaseVMActivity<VB, VM> this$0;

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0007\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\b\u0012\u0004\u0012\u00028\u00000\u0005H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/flow/FlowCollector;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingView$1$1", f = "BaseVMActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingView$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00871<T> extends SuspendLambda implements Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ BaseVMActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00871(BaseVMActivity<VB, VM> baseVMActivity, Continuation<? super C00871> continuation) {
                super(2, continuation);
                this.this$0 = baseVMActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                return new C00871(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @Nullable Continuation<? super Unit> continuation) {
                return ((C00871) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.loadingView();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\t\u001a\u00020\b\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/flow/FlowCollector;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingView$1$2", f = "BaseVMActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingView$1$2, reason: invalid class name */
        public static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ BaseVMActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(BaseVMActivity<VB, VM> baseVMActivity, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.this$0 = baseVMActivity;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @Nullable Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new AnonymousClass2(this.this$0, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                LogExtKt.logd("lifecycleFlowLoadingView:onCompletion", this.this$0.getFullTag());
                this.this$0.hideLoading();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\t\u001a\u00020\b\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u0007\u001a\u00020\u0006H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/flow/FlowCollector;", "", "t", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingView$1$3", f = "BaseVMActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowLoadingView$1$3, reason: invalid class name */
        public static final class AnonymousClass3<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ BaseVMActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass3(BaseVMActivity<VB, VM> baseVMActivity, Function1<? super Throwable, Unit> function1, Continuation<? super AnonymousClass3> continuation) {
                super(3, continuation);
                this.this$0 = baseVMActivity;
                this.$errorCallback = function1;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0, this.$errorCallback, continuation);
                anonymousClass3.L$0 = th;
                return anonymousClass3.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Throwable th = (Throwable) this.L$0;
                KotlinMvvmCompiler.INSTANCE.onError(this.this$0, th);
                Function1<Throwable, Unit> function1 = this.$errorCallback;
                if (function1 != null) {
                    function1.invoke(th);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Unknown type variable: T in type: kotlin.jvm.functions.Function1<? super T, kotlin.Unit> */
        /* JADX WARN: Unknown type variable: T in type: kotlinx.coroutines.flow.Flow<? extends T> */
        public C05181(Flow<? extends T> flow, BaseVMActivity<VB, VM> baseVMActivity, Function1<? super Throwable, Unit> function1, Function1<? super T, Unit> function12, Continuation<? super C05181> continuation) {
            super(2, continuation);
            this.$flow = flow;
            this.this$0 = baseVMActivity;
            this.$errorCallback = function1;
            this.$callback = function12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05181(this.$flow, this.this$0, this.$errorCallback, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05181) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowM2338catch = FlowKt.m2338catch(FlowKt.onCompletion(FlowKt.onStart(FlowKt.flowOn(this.$flow, Dispatchers.getIO()), new C00871(this.this$0, null)), new AnonymousClass2(this.this$0, null)), new AnonymousClass3(this.this$0, this.$errorCallback, null));
                final Function1<T, Unit> function1 = this.$callback;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.catchpig.mvvm.base.activity.BaseVMActivity.lifecycleFlowLoadingView.1.4
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    @Nullable
                    public final Object emit(T t2, @NotNull Continuation<? super Unit> continuation) {
                        function1.invoke(t2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowM2338catch.collect(flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Unknown type variable: T in type: kotlinx.coroutines.flow.Flow<java.util.List<T>> */
    @Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0007\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\u00020\u0005H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowRefresh$1", f = "BaseVMActivity.kt", i = {}, l = {63}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowRefresh$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05191 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Flow<List<T>> $flow;
        final /* synthetic */ RefreshRecyclerView $refresh;
        int label;
        final /* synthetic */ BaseVMActivity<VB, VM> this$0;

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\n\u001a\u00020\t\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u00052\u0006\u0010\b\u001a\u00020\u0007H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/flow/FlowCollector;", "", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowRefresh$1$1", f = "BaseVMActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowRefresh$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00881<T> extends SuspendLambda implements Function3<FlowCollector<? super List<T>>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ RefreshRecyclerView $refresh;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00881(RefreshRecyclerView refreshRecyclerView, Continuation<? super C00881> continuation) {
                super(3, continuation);
                this.$refresh = refreshRecyclerView;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super List<T>> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new C00881(this.$refresh, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$refresh.updateError();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\n\u001a\u00020\t\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001\"\b\b\u0002\u0010\u0004*\u00020\u0003*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "VB", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "VM", "Lkotlinx/coroutines/flow/FlowCollector;", "", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowRefresh$1$2", f = "BaseVMActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.base.activity.BaseVMActivity$lifecycleFlowRefresh$1$2, reason: invalid class name */
        public static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super List<T>>, Throwable, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ BaseVMActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(BaseVMActivity<VB, VM> baseVMActivity, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.this$0 = baseVMActivity;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super List<T>> flowCollector, @Nullable Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new AnonymousClass2(this.this$0, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                LogExtKt.logd("lifecycleFlowRefresh:onCompletion", this.this$0.getFullTag());
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Unknown type variable: T in type: kotlinx.coroutines.flow.Flow<? extends java.util.List<T>> */
        public C05191(Flow<? extends List<T>> flow, RefreshRecyclerView refreshRecyclerView, BaseVMActivity<VB, VM> baseVMActivity, Continuation<? super C05191> continuation) {
            super(2, continuation);
            this.$flow = flow;
            this.$refresh = refreshRecyclerView;
            this.this$0 = baseVMActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05191(this.$flow, this.$refresh, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05191) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowOnCompletion = FlowKt.onCompletion(FlowKt.m2338catch(FlowKt.flowOn(this.$flow, Dispatchers.getIO()), new C00881(this.$refresh, null)), new AnonymousClass2(this.this$0, null));
                final RefreshRecyclerView refreshRecyclerView = this.$refresh;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.catchpig.mvvm.base.activity.BaseVMActivity.lifecycleFlowRefresh.1.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((List) obj2, (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(@NotNull List<T> list, @NotNull Continuation<? super Unit> continuation) {
                        refreshRecyclerView.updateData(list);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowOnCompletion.collect(flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getFullTag() {
        return (String) this.fullTag.getValue();
    }

    @NotNull
    public final VM getViewModel() {
        return (VM) this.viewModel.getValue();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    @Deprecated(message = "当前方法已废弃,请使用FlowExt.lifecycle()", replaceWith = @ReplaceWith(expression = "flow.lifecycle(this,{\n\n}){\n\n}", imports = {}))
    public <T> void lifecycleFlow(@NotNull Flow<? extends T> flow, @Nullable Function1<? super Throwable, Unit> errorCallback, @NotNull Function1<? super T, Unit> callback) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getMain(), null, new AnonymousClass1(flow, this, errorCallback, callback, null), 2, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    @Deprecated(message = "当前方法已废弃,请使用FlowExt.lifecycleLoadingDialog()", replaceWith = @ReplaceWith(expression = "flow.lifecycleLoadingDialog(this,{\n\n}){\n\n}", imports = {}))
    public <T> void lifecycleFlowLoadingDialog(@NotNull Flow<? extends T> flow, @Nullable Function1<? super Throwable, Unit> errorCallback, @NotNull Function1<? super T, Unit> callback) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getMain(), null, new C05171(flow, this, errorCallback, callback, null), 2, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    @Deprecated(message = "当前方法已废弃,请使用FlowExt.lifecycleLoadingView()", replaceWith = @ReplaceWith(expression = "flow.lifecycleLoadingView(this,{\n\n}){\n\n}", imports = {}))
    public <T> void lifecycleFlowLoadingView(@NotNull Flow<? extends T> flow, @Nullable Function1<? super Throwable, Unit> errorCallback, @NotNull Function1<? super T, Unit> callback) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getMain(), null, new C05181(flow, this, errorCallback, callback, null), 2, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    @Deprecated(message = "当前方法已废弃,请使用FlowExt.lifecycleRefresh()", replaceWith = @ReplaceWith(expression = "flow.lifecycleRefresh(this,refresh)}", imports = {}))
    public <T> void lifecycleFlowRefresh(@NotNull Flow<? extends List<T>> flow, @NotNull RefreshRecyclerView refresh) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(refresh, "refresh");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getMain(), null, new C05191(flow, refresh, this, null), 2, null);
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) throws IllegalAccessException, InstantiationException {
        super.onCreate(savedInstanceState);
        initParam();
        getLifecycle().addObserver(getViewModel());
        initView();
        initFlow();
    }
}
