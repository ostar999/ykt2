package com.yddmi.doctor.repository;

import com.catchpig.mvvm.network.manager.NetManager;
import com.yddmi.doctor.entity.result.WxToken;
import com.yddmi.doctor.entity.result.WxUserInfo;
import com.yddmi.doctor.network.api.WxService;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u00062\u0006\u0010\b\u001a\u00020\tJ\u001b\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\b\u001a\u00020\tH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, d2 = {"Lcom/yddmi/doctor/repository/WxRepository;", "", "()V", "wxService", "Lcom/yddmi/doctor/network/api/WxService;", "wxCodeGetUserInfo", "Lkotlinx/coroutines/flow/Flow;", "Lcom/yddmi/doctor/entity/result/WxUserInfo;", "code", "", "wxTokenGet", "Lcom/yddmi/doctor/entity/result/WxToken;", "wxTokenGet0", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class WxRepository {

    @NotNull
    public static final WxRepository INSTANCE = new WxRepository();

    @NotNull
    private static final WxService wxService = (WxService) NetManager.INSTANCE.getInstance().getService(WxService.class);

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/WxUserInfo;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.WxRepository$wxCodeGetUserInfo$1", f = "WxRepository.kt", i = {0}, l = {31, 33, 35, 35}, m = "invokeSuspend", n = {"$this$flow"}, s = {"L$0"})
    /* renamed from: com.yddmi.doctor.repository.WxRepository$wxCodeGetUserInfo$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<FlowCollector<? super WxUserInfo>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $code;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$code = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$code, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super WxUserInfo> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:28:0x007d A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
            /*
                r9 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r9.label
                r2 = 4
                r3 = 3
                r4 = 2
                r5 = 1
                r6 = 0
                if (r1 == 0) goto L32
                if (r1 == r5) goto L2a
                if (r1 == r4) goto L26
                if (r1 == r3) goto L1e
                if (r1 != r2) goto L16
                goto L26
            L16:
                java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r10.<init>(r0)
                throw r10
            L1e:
                java.lang.Object r1 = r9.L$0
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r10)
                goto L73
            L26:
                kotlin.ResultKt.throwOnFailure(r10)
                goto L7e
            L2a:
                java.lang.Object r1 = r9.L$0
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r10)
                goto L4d
            L32:
                kotlin.ResultKt.throwOnFailure(r10)
                java.lang.Object r10 = r9.L$0
                kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
                com.yddmi.doctor.network.api.WxService r1 = com.yddmi.doctor.repository.WxRepository.access$getWxService$p()
                java.lang.String r7 = r9.$code
                r9.L$0 = r10
                r9.label = r5
                java.lang.Object r1 = r1.wxGetAccessToken(r7, r9)
                if (r1 != r0) goto L4a
                return r0
            L4a:
                r8 = r1
                r1 = r10
                r10 = r8
            L4d:
                com.yddmi.doctor.entity.result.WxToken r10 = (com.yddmi.doctor.entity.result.WxToken) r10
                if (r10 != 0) goto L5c
                r9.L$0 = r6
                r9.label = r4
                java.lang.Object r10 = r1.emit(r6, r9)
                if (r10 != r0) goto L7e
                return r0
            L5c:
                com.yddmi.doctor.network.api.WxService r4 = com.yddmi.doctor.repository.WxRepository.access$getWxService$p()
                java.lang.String r5 = r10.getAccess_token()
                java.lang.String r10 = r10.getOpenid()
                r9.L$0 = r1
                r9.label = r3
                java.lang.Object r10 = r4.wxGetUserInfo(r5, r10, r9)
                if (r10 != r0) goto L73
                return r0
            L73:
                r9.L$0 = r6
                r9.label = r2
                java.lang.Object r10 = r1.emit(r10, r9)
                if (r10 != r0) goto L7e
                return r0
            L7e:
                kotlin.Unit r10 = kotlin.Unit.INSTANCE
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.repository.WxRepository.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/WxToken;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.WxRepository$wxTokenGet$1", f = "WxRepository.kt", i = {}, l = {19, 19}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.WxRepository$wxTokenGet$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09101 extends SuspendLambda implements Function2<FlowCollector<? super WxToken>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $code;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09101(String str, Continuation<? super C09101> continuation) {
            super(2, continuation);
            this.$code = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09101 c09101 = new C09101(this.$code, continuation);
            c09101.L$0 = obj;
            return c09101;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super WxToken> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09101) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                WxService wxService = WxRepository.wxService;
                String str = this.$code;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = wxService.wxGetAccessToken(str, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.yddmi.doctor.repository.WxRepository", f = "WxRepository.kt", i = {0}, l = {24}, m = "wxTokenGet0", n = {"code"}, s = {"L$0"})
    /* renamed from: com.yddmi.doctor.repository.WxRepository$wxTokenGet0$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09111 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C09111(Continuation<? super C09111> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return WxRepository.this.wxTokenGet0(null, this);
        }
    }

    private WxRepository() {
    }

    @NotNull
    public final Flow<WxUserInfo> wxCodeGetUserInfo(@NotNull String code) {
        Intrinsics.checkNotNullParameter(code, "code");
        return FlowKt.flow(new AnonymousClass1(code, null));
    }

    @NotNull
    public final Flow<WxToken> wxTokenGet(@NotNull String code) {
        Intrinsics.checkNotNullParameter(code, "code");
        return FlowKt.flow(new C09101(code, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object wxTokenGet0(@org.jetbrains.annotations.NotNull java.lang.String r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super com.yddmi.doctor.entity.result.WxToken> r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.yddmi.doctor.repository.WxRepository.C09111
            if (r0 == 0) goto L13
            r0 = r6
            com.yddmi.doctor.repository.WxRepository$wxTokenGet0$1 r0 = (com.yddmi.doctor.repository.WxRepository.C09111) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.yddmi.doctor.repository.WxRepository$wxTokenGet0$1 r0 = new com.yddmi.doctor.repository.WxRepository$wxTokenGet0$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r5 = r0.L$0
            java.lang.String r5 = (java.lang.String) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L45
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            com.yddmi.doctor.network.api.WxService r6 = com.yddmi.doctor.repository.WxRepository.wxService
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = r6.wxGetAccessToken(r5, r0)
            if (r6 != r1) goto L45
            return r1
        L45:
            com.yddmi.doctor.entity.result.WxToken r6 = (com.yddmi.doctor.entity.result.WxToken) r6
            if (r6 != 0) goto L4a
            goto L4d
        L4a:
            r6.setMCode(r5)
        L4d:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.repository.WxRepository.wxTokenGet0(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
