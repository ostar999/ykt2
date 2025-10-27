package com.catchpig.mvvm.ext;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.catchpig.mvvm.base.view.BaseView;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.catchpig.mvvm.widget.refresh.RefreshRecyclerView;
import com.catchpig.utils.ext.LogExtKt;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\u001ah\u0010\u0002\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2%\b\u0002\u0010\n\u001a\u001f\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00030\u000b¢\u0006\u0002\b\u0011\u001ah\u0010\u0012\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2%\b\u0002\u0010\n\u001a\u001f\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00030\u000b¢\u0006\u0002\b\u0011\u001ah\u0010\u0013\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2%\b\u0002\u0010\n\u001a\u001f\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00030\u000b¢\u0006\u0002\b\u0011\u001a,\u0010\u0014\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0004*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00150\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"TAG", "", RequestParameters.SUBRESOURCE_LIFECYCLE, "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/Flow;", TtmlNode.RUBY_BASE, "Lcom/catchpig/mvvm/base/view/BaseView;", "showFailedView", "", "errorCallback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "t", com.alipay.sdk.authjs.a.f3170c, "Lkotlin/ExtensionFunctionType;", "lifecycleLoadingDialog", "lifecycleLoadingView", "lifecycleRefresh", "", "refreshLayoutWrapper", "Lcom/catchpig/mvvm/widget/refresh/RefreshRecyclerView;", "mvvm_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class FlowExtKt {

    @NotNull
    private static final String TAG = "FlowExt";

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycle$1", f = "FlowExt.kt", i = {}, l = {45}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycle$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseView $base;
        final /* synthetic */ Function1<T, Unit> $callback;
        final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
        final /* synthetic */ boolean $showFailedView;
        final /* synthetic */ Flow<T> $this_lifecycle;
        int label;

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycle$1$1", f = "FlowExt.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycle$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00931<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ BaseView $base;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00931(BaseView baseView, Continuation<? super C00931> continuation) {
                super(3, continuation);
                this.$base = baseView;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @Nullable Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new C00931(this.$base, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                String simpleName = Reflection.getOrCreateKotlinClass(this.$base.getClass()).getSimpleName();
                Intrinsics.checkNotNull(simpleName);
                LogExtKt.logd("FlowExt:lifecycle:onCompletion", simpleName);
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "", "t", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycle$1$2", f = "FlowExt.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycle$1$2, reason: invalid class name */
        public static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ BaseView $base;
            final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
            final /* synthetic */ boolean $showFailedView;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass2(boolean z2, BaseView baseView, Function1<? super Throwable, Unit> function1, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.$showFailedView = z2;
                this.$base = baseView;
                this.$errorCallback = function1;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$showFailedView, this.$base, this.$errorCallback, continuation);
                anonymousClass2.L$0 = th;
                return anonymousClass2.invokeSuspend(Unit.INSTANCE);
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
                if (this.$showFailedView) {
                    this.$base.showFailedView();
                }
                KotlinMvvmCompiler.INSTANCE.onError(this.$base, th);
                Function1<Throwable, Unit> function1 = this.$errorCallback;
                if (function1 != null) {
                    function1.invoke(th);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(Flow<? extends T> flow, BaseView baseView, boolean z2, Function1<? super Throwable, Unit> function1, Function1<? super T, Unit> function12, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$this_lifecycle = flow;
            this.$base = baseView;
            this.$showFailedView = z2;
            this.$errorCallback = function1;
            this.$callback = function12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass1(this.$this_lifecycle, this.$base, this.$showFailedView, this.$errorCallback, this.$callback, continuation);
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
                Flow flowM2338catch = FlowKt.m2338catch(FlowKt.onCompletion(FlowKt.flowOn(this.$this_lifecycle, Dispatchers.getIO()), new C00931(this.$base, null)), new AnonymousClass2(this.$showFailedView, this.$base, this.$errorCallback, null));
                final Function1<T, Unit> function1 = this.$callback;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.catchpig.mvvm.ext.FlowExtKt.lifecycle.1.3
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingDialog$1", f = "FlowExt.kt", i = {}, l = {74}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingDialog$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05241 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseView $base;
        final /* synthetic */ Function1<T, Unit> $callback;
        final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
        final /* synthetic */ boolean $showFailedView;
        final /* synthetic */ Flow<T> $this_lifecycleLoadingDialog;
        int label;

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingDialog$1$1", f = "FlowExt.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingDialog$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00941<T> extends SuspendLambda implements Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> {
            final /* synthetic */ BaseView $base;
            final /* synthetic */ boolean $showFailedView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00941(BaseView baseView, boolean z2, Continuation<? super C00941> continuation) {
                super(2, continuation);
                this.$base = baseView;
                this.$showFailedView = z2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                return new C00941(this.$base, this.$showFailedView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @Nullable Continuation<? super Unit> continuation) {
                return ((C00941) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$base.loadingDialog();
                if (this.$showFailedView) {
                    this.$base.removeFailedView();
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingDialog$1$2", f = "FlowExt.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingDialog$1$2, reason: invalid class name */
        public static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ BaseView $base;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(BaseView baseView, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.$base = baseView;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @Nullable Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new AnonymousClass2(this.$base, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                String simpleName = Reflection.getOrCreateKotlinClass(this.$base.getClass()).getSimpleName();
                Intrinsics.checkNotNull(simpleName);
                LogExtKt.logd("FlowExt:lifecycleLoadingDialog:onCompletion", simpleName);
                this.$base.hideLoading();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "", "t", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingDialog$1$3", f = "FlowExt.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingDialog$1$3, reason: invalid class name */
        public static final class AnonymousClass3<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ BaseView $base;
            final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
            final /* synthetic */ boolean $showFailedView;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass3(boolean z2, BaseView baseView, Function1<? super Throwable, Unit> function1, Continuation<? super AnonymousClass3> continuation) {
                super(3, continuation);
                this.$showFailedView = z2;
                this.$base = baseView;
                this.$errorCallback = function1;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$showFailedView, this.$base, this.$errorCallback, continuation);
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
                if (this.$showFailedView) {
                    this.$base.showFailedView();
                }
                KotlinMvvmCompiler.INSTANCE.onError(this.$base, th);
                Function1<Throwable, Unit> function1 = this.$errorCallback;
                if (function1 != null) {
                    function1.invoke(th);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C05241(Flow<? extends T> flow, BaseView baseView, boolean z2, Function1<? super Throwable, Unit> function1, Function1<? super T, Unit> function12, Continuation<? super C05241> continuation) {
            super(2, continuation);
            this.$this_lifecycleLoadingDialog = flow;
            this.$base = baseView;
            this.$showFailedView = z2;
            this.$errorCallback = function1;
            this.$callback = function12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05241(this.$this_lifecycleLoadingDialog, this.$base, this.$showFailedView, this.$errorCallback, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05241) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowM2338catch = FlowKt.m2338catch(FlowKt.onCompletion(FlowKt.onStart(FlowKt.flowOn(this.$this_lifecycleLoadingDialog, Dispatchers.getIO()), new C00941(this.$base, this.$showFailedView, null)), new AnonymousClass2(this.$base, null)), new AnonymousClass3(this.$showFailedView, this.$base, this.$errorCallback, null));
                final Function1<T, Unit> function1 = this.$callback;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.catchpig.mvvm.ext.FlowExtKt.lifecycleLoadingDialog.1.4
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingView$1", f = "FlowExt.kt", i = {}, l = {103}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05251 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseView $base;
        final /* synthetic */ Function1<T, Unit> $callback;
        final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
        final /* synthetic */ boolean $showFailedView;
        final /* synthetic */ Flow<T> $this_lifecycleLoadingView;
        int label;

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingView$1$1", f = "FlowExt.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingView$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00951<T> extends SuspendLambda implements Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> {
            final /* synthetic */ BaseView $base;
            final /* synthetic */ boolean $showFailedView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00951(BaseView baseView, boolean z2, Continuation<? super C00951> continuation) {
                super(2, continuation);
                this.$base = baseView;
                this.$showFailedView = z2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                return new C00951(this.$base, this.$showFailedView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @Nullable Continuation<? super Unit> continuation) {
                return ((C00951) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$base.loadingView();
                if (this.$showFailedView) {
                    this.$base.removeFailedView();
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingView$1$2", f = "FlowExt.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingView$1$2, reason: invalid class name */
        public static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ BaseView $base;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(BaseView baseView, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.$base = baseView;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @Nullable Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new AnonymousClass2(this.$base, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                String simpleName = Reflection.getOrCreateKotlinClass(this.$base.getClass()).getSimpleName();
                Intrinsics.checkNotNull(simpleName);
                LogExtKt.logd("FlowExt:lifecycleLoadingView:onCompletion", simpleName);
                this.$base.hideLoading();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "", "t", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingView$1$3", f = "FlowExt.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleLoadingView$1$3, reason: invalid class name */
        public static final class AnonymousClass3<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ BaseView $base;
            final /* synthetic */ Function1<Throwable, Unit> $errorCallback;
            final /* synthetic */ boolean $showFailedView;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass3(boolean z2, BaseView baseView, Function1<? super Throwable, Unit> function1, Continuation<? super AnonymousClass3> continuation) {
                super(3, continuation);
                this.$showFailedView = z2;
                this.$base = baseView;
                this.$errorCallback = function1;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super T> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$showFailedView, this.$base, this.$errorCallback, continuation);
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
                if (this.$showFailedView) {
                    this.$base.showFailedView();
                }
                KotlinMvvmCompiler.INSTANCE.onError(this.$base, th);
                Function1<Throwable, Unit> function1 = this.$errorCallback;
                if (function1 != null) {
                    function1.invoke(th);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C05251(Flow<? extends T> flow, BaseView baseView, boolean z2, Function1<? super Throwable, Unit> function1, Function1<? super T, Unit> function12, Continuation<? super C05251> continuation) {
            super(2, continuation);
            this.$this_lifecycleLoadingView = flow;
            this.$base = baseView;
            this.$showFailedView = z2;
            this.$errorCallback = function1;
            this.$callback = function12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05251(this.$this_lifecycleLoadingView, this.$base, this.$showFailedView, this.$errorCallback, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05251) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowM2338catch = FlowKt.m2338catch(FlowKt.onCompletion(FlowKt.onStart(FlowKt.flowOn(this.$this_lifecycleLoadingView, Dispatchers.getIO()), new C00951(this.$base, this.$showFailedView, null)), new AnonymousClass2(this.$base, null)), new AnonymousClass3(this.$showFailedView, this.$base, this.$errorCallback, null));
                final Function1<T, Unit> function1 = this.$callback;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.catchpig.mvvm.ext.FlowExtKt.lifecycleLoadingView.1.4
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleRefresh$1", f = "FlowExt.kt", i = {}, l = {22}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleRefresh$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05261 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseView $base;
        final /* synthetic */ RefreshRecyclerView $refreshLayoutWrapper;
        final /* synthetic */ Flow<List<T>> $this_lifecycleRefresh;
        int label;

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0006\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0000*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0003H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleRefresh$1$1", f = "FlowExt.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleRefresh$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00961<T> extends SuspendLambda implements Function3<FlowCollector<? super List<T>>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ RefreshRecyclerView $refreshLayoutWrapper;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00961(RefreshRecyclerView refreshRecyclerView, Continuation<? super C00961> continuation) {
                super(3, continuation);
                this.$refreshLayoutWrapper = refreshRecyclerView;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super List<T>> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new C00961(this.$refreshLayoutWrapper, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$refreshLayoutWrapper.updateError();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        @Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0006\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0000*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u00012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.ext.FlowExtKt$lifecycleRefresh$1$2", f = "FlowExt.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.ext.FlowExtKt$lifecycleRefresh$1$2, reason: invalid class name */
        public static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super List<T>>, Throwable, Continuation<? super Unit>, Object> {
            final /* synthetic */ BaseView $base;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(BaseView baseView, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.$base = baseView;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super List<T>> flowCollector, @Nullable Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new AnonymousClass2(this.$base, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                String simpleName = Reflection.getOrCreateKotlinClass(this.$base.getClass()).getSimpleName();
                Intrinsics.checkNotNull(simpleName);
                LogExtKt.logd("FlowExt:lifecycleRefresh:onCompletion", simpleName);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C05261(Flow<? extends List<T>> flow, RefreshRecyclerView refreshRecyclerView, BaseView baseView, Continuation<? super C05261> continuation) {
            super(2, continuation);
            this.$this_lifecycleRefresh = flow;
            this.$refreshLayoutWrapper = refreshRecyclerView;
            this.$base = baseView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05261(this.$this_lifecycleRefresh, this.$refreshLayoutWrapper, this.$base, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05261) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowOnCompletion = FlowKt.onCompletion(FlowKt.m2338catch(FlowKt.flowOn(this.$this_lifecycleRefresh, Dispatchers.getIO()), new C00961(this.$refreshLayoutWrapper, null)), new AnonymousClass2(this.$base, null));
                final RefreshRecyclerView refreshRecyclerView = this.$refreshLayoutWrapper;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.catchpig.mvvm.ext.FlowExtKt.lifecycleRefresh.1.3
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

    public static final <T> void lifecycle(@NotNull Flow<? extends T> flow, @NotNull BaseView base, boolean z2, @Nullable Function1<? super Throwable, Unit> function1, @NotNull Function1<? super T, Unit> callback) {
        Intrinsics.checkNotNullParameter(flow, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(base.scope(), Dispatchers.getMain(), null, new AnonymousClass1(flow, base, z2, function1, callback, null), 2, null);
    }

    public static /* synthetic */ void lifecycle$default(Flow flow, BaseView baseView, boolean z2, Function1 function1, Function1 function12, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        if ((i2 & 4) != 0) {
            function1 = null;
        }
        lifecycle(flow, baseView, z2, function1, function12);
    }

    public static final <T> void lifecycleLoadingDialog(@NotNull Flow<? extends T> flow, @NotNull BaseView base, boolean z2, @Nullable Function1<? super Throwable, Unit> function1, @NotNull Function1<? super T, Unit> callback) {
        Intrinsics.checkNotNullParameter(flow, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(base.scope(), Dispatchers.getMain(), null, new C05241(flow, base, z2, function1, callback, null), 2, null);
    }

    public static /* synthetic */ void lifecycleLoadingDialog$default(Flow flow, BaseView baseView, boolean z2, Function1 function1, Function1 function12, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        if ((i2 & 4) != 0) {
            function1 = null;
        }
        lifecycleLoadingDialog(flow, baseView, z2, function1, function12);
    }

    public static final <T> void lifecycleLoadingView(@NotNull Flow<? extends T> flow, @NotNull BaseView base, boolean z2, @Nullable Function1<? super Throwable, Unit> function1, @NotNull Function1<? super T, Unit> callback) {
        Intrinsics.checkNotNullParameter(flow, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(base.scope(), Dispatchers.getMain(), null, new C05251(flow, base, z2, function1, callback, null), 2, null);
    }

    public static /* synthetic */ void lifecycleLoadingView$default(Flow flow, BaseView baseView, boolean z2, Function1 function1, Function1 function12, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        if ((i2 & 4) != 0) {
            function1 = null;
        }
        lifecycleLoadingView(flow, baseView, z2, function1, function12);
    }

    public static final <T> void lifecycleRefresh(@NotNull Flow<? extends List<T>> flow, @NotNull BaseView base, @NotNull RefreshRecyclerView refreshLayoutWrapper) {
        Intrinsics.checkNotNullParameter(flow, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(refreshLayoutWrapper, "refreshLayoutWrapper");
        BuildersKt__Builders_commonKt.launch$default(base.scope(), Dispatchers.getMain(), null, new C05261(flow, refreshLayoutWrapper, base, null), 2, null);
    }
}
