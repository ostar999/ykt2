package com.ykb.ebook.network;

import androidx.exifinterface.media.ExifInterface;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.tencent.open.SocialConstants;
import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.api.ApiServiceKt;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.base.BaseViewModel;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.NetUtilKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u001ag\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\u0001\"\u0004\b\u0000\u0010\u0003*\u00020\u00042-\u0010\u0005\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006¢\u0006\u0002\b\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\r0\fø\u0001\u0000¢\u0006\u0002\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"executeRequest", "Lcom/ykb/ebook/util/Coroutine;", "Lcom/ykb/ebook/base/BaseResponse;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/ykb/ebook/base/BaseViewModel;", SocialConstants.TYPE_REQUEST, "Lkotlin/Function2;", "Lcom/ykb/ebook/api/ApiService;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "success", "Lkotlin/Function1;", "", "(Lcom/ykb/ebook/base/BaseViewModel;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)Lcom/ykb/ebook/util/Coroutine;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class RequestKt {

    /* JADX INFO: Add missing generic type declarations: [T] */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.network.RequestKt$executeRequest$1", f = "Request.kt", i = {}, l = {21}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.network.RequestKt$executeRequest$1, reason: invalid class name */
    public static final class AnonymousClass1<T> extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super BaseResponse<T>>, Object> {
        final /* synthetic */ Function2<ApiService, Continuation<? super BaseResponse<T>>, Object> $request;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(Function2<? super ApiService, ? super Continuation<? super BaseResponse<T>>, ? extends Object> function2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$request = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass1(this.$request, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super BaseResponse<T>> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Function2<ApiService, Continuation<? super BaseResponse<T>>, Object> function2 = this.$request;
                ApiService api = ApiServiceKt.getAPI();
                this.label = 1;
                obj = function2.invoke(api, this);
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.network.RequestKt$executeRequest$2", f = "Request.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.network.RequestKt$executeRequest$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseViewModel $this_executeRequest;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(BaseViewModel baseViewModel, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$this_executeRequest = baseViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass2(this.$this_executeRequest, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
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
            this.$this_executeRequest.getLoadEvent().setValue(Boxing.boxInt(0));
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.network.RequestKt$executeRequest$3", f = "Request.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.network.RequestKt$executeRequest$3, reason: invalid class name */
    public static final class AnonymousClass3<T> extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1<T, Unit> $success;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass3(Function1<? super T, Unit> function1, Continuation<? super AnonymousClass3> continuation) {
            super(3, continuation);
            this.$success = function1;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<T> baseResponse, @Nullable Continuation<? super Unit> continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$success, continuation);
            anonymousClass3.L$0 = baseResponse;
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
            BaseResponse baseResponse = (BaseResponse) this.L$0;
            baseResponse.throwApiException();
            Object data = baseResponse.getData();
            if (data != null) {
                this.$success.invoke(data);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.network.RequestKt$executeRequest$4", f = "Request.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.network.RequestKt$executeRequest$4, reason: invalid class name */
    public static final class AnonymousClass4 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseViewModel $this_executeRequest;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(BaseViewModel baseViewModel, Continuation<? super AnonymousClass4> continuation) {
            super(3, continuation);
            this.$this_executeRequest = baseViewModel;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$this_executeRequest, continuation);
            anonymousClass4.L$0 = th;
            return anonymousClass4.invokeSuspend(Unit.INSTANCE);
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
            Log.INSTANCE.logE("request_err", "error:" + NetUtilKt.getErrorMsg(th));
            this.$this_executeRequest.getErrorEvent().setValue(NetUtilKt.getErrorMsg(th));
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.network.RequestKt$executeRequest$5", f = "Request.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.network.RequestKt$executeRequest$5, reason: invalid class name */
    public static final class AnonymousClass5 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseViewModel $this_executeRequest;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(BaseViewModel baseViewModel, Continuation<? super AnonymousClass5> continuation) {
            super(2, continuation);
            this.$this_executeRequest = baseViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass5(this.$this_executeRequest, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$this_executeRequest.getLoadEvent().setValue(Boxing.boxInt(1));
            return Unit.INSTANCE;
        }
    }

    @NotNull
    public static final <T> Coroutine<BaseResponse<T>> executeRequest(@NotNull BaseViewModel baseViewModel, @NotNull Function2<? super ApiService, ? super Continuation<? super BaseResponse<T>>, ? extends Object> request, @NotNull Function1<? super T, Unit> success) {
        Intrinsics.checkNotNullParameter(baseViewModel, "<this>");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(success, "success");
        return Coroutine.onFinally$default(Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.onStart$default(BaseViewModel.execute$default(baseViewModel, null, null, null, null, new AnonymousClass1(request, null), 15, null), null, new AnonymousClass2(baseViewModel, null), 1, null), null, new AnonymousClass3(success, null), 1, null), null, new AnonymousClass4(baseViewModel, null), 1, null), null, new AnonymousClass5(baseViewModel, null), 1, null);
    }
}
