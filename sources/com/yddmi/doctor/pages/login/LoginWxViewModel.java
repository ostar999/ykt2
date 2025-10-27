package com.yddmi.doctor.pages.login;

import androidx.lifecycle.ViewModelKt;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.umeng.analytics.pro.am;
import com.yddmi.doctor.entity.result.AuthLoginResult;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.repository.YddClinicRepository;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0!J\u0016\u0010#\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010$0!2\u0006\u0010%\u001a\u00020$J\u0016\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020$2\u0006\u0010(\u001a\u00020$R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013¨\u0006)"}, d2 = {"Lcom/yddmi/doctor/pages/login/LoginWxViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "loginResult", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "getLoginResult", "()Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "setLoginResult", "(Lcom/yddmi/doctor/entity/result/AuthLoginResult;)V", "mSelected", "", "getMSelected", "()Z", "setMSelected", "(Z)V", "timeCost", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getTimeCost", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "setTimeCost", "(Lkotlinx/coroutines/flow/MutableStateFlow;)V", "type", "getType", "()I", "setType", "(I)V", "wxLoginMsf", "", "getWxLoginMsf", "dealTimeGo", "", "getPersonInfo", "Lkotlinx/coroutines/flow/Flow;", "Lcom/yddmi/doctor/entity/result/MeProfile;", "getPushCodeRegister", "", AliyunLogCommon.TERMINAL_TYPE, "httpWxLogin", "phoneStr", "codeStr", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class LoginWxViewModel extends BaseViewModel {

    @Nullable
    private AuthLoginResult loginResult;
    private boolean mSelected;
    private int type = 100;

    @NotNull
    private MutableStateFlow<Integer> timeCost = StateFlowKt.MutableStateFlow(0);

    @NotNull
    private final MutableStateFlow<Long> wxLoginMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.login.LoginWxViewModel$dealTimeGo$1", f = "LoginWxViewModel.kt", i = {0, 1}, l = {36, 37}, m = "invokeSuspend", n = {am.aC, am.aC}, s = {"I$0", "I$0"})
    /* renamed from: com.yddmi.doctor.pages.login.LoginWxViewModel$dealTimeGo$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return LoginWxViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x004f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0050  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0058  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0050 -> B:19:0x0053). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = -1
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L26
                if (r1 == r4) goto L1e
                if (r1 != r3) goto L16
                int r1 = r8.I$0
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r8
                goto L53
            L16:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L1e:
                int r1 = r8.I$0
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r1
                r1 = r8
                goto L43
            L26:
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = 60
                r1 = r8
            L2c:
                if (r2 >= r9) goto L58
                com.yddmi.doctor.pages.login.LoginWxViewModel r5 = com.yddmi.doctor.pages.login.LoginWxViewModel.this
                kotlinx.coroutines.flow.MutableStateFlow r5 = r5.getTimeCost()
                java.lang.Integer r6 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r9)
                r1.I$0 = r9
                r1.label = r4
                java.lang.Object r5 = r5.emit(r6, r1)
                if (r5 != r0) goto L43
                return r0
            L43:
                r1.I$0 = r9
                r1.label = r3
                r5 = 1000(0x3e8, double:4.94E-321)
                java.lang.Object r5 = kotlinx.coroutines.DelayKt.delay(r5, r1)
                if (r5 != r0) goto L50
                return r0
            L50:
                r7 = r1
                r1 = r9
                r9 = r7
            L53:
                int r1 = r1 + r2
                r7 = r1
                r1 = r9
                r9 = r7
                goto L2c
            L58:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.login.LoginWxViewModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.login.LoginWxViewModel$httpWxLogin$1", f = "LoginWxViewModel.kt", i = {}, l = {64, 65, 67, 71}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.login.LoginWxViewModel$httpWxLogin$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07691 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $codeStr;
        final /* synthetic */ String $phoneStr;
        Object L$0;
        int label;
        final /* synthetic */ LoginWxViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07691(String str, String str2, LoginWxViewModel loginWxViewModel, Continuation<? super C07691> continuation) {
            super(2, continuation);
            this.$phoneStr = str;
            this.$codeStr = str2;
            this.this$0 = loginWxViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C07691(this.$phoneStr, this.$codeStr, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07691) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x0092 A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r18) {
            /*
                r17 = this;
                r1 = r17
                java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r0 = r1.label
                r3 = 100
                r5 = 4
                r6 = 3
                r7 = 2
                r8 = 1
                r9 = 0
                if (r0 == 0) goto L38
                if (r0 == r8) goto L2e
                if (r0 == r7) goto L26
                if (r0 == r6) goto L26
                if (r0 != r5) goto L1e
                kotlin.ResultKt.throwOnFailure(r18)
                goto Lc4
            L1e:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r2)
                throw r0
            L26:
                kotlin.ResultKt.throwOnFailure(r18)     // Catch: java.lang.Throwable -> L2b
                goto Lc4
            L2b:
                r0 = move-exception
                goto La6
            L2e:
                java.lang.Object r0 = r1.L$0
                com.yddmi.doctor.pages.login.LoginWxViewModel r0 = (com.yddmi.doctor.pages.login.LoginWxViewModel) r0
                kotlin.ResultKt.throwOnFailure(r18)     // Catch: java.lang.Throwable -> L2b
                r6 = r18
                goto L75
            L38:
                kotlin.ResultKt.throwOnFailure(r18)
                com.yddmi.doctor.config.YddConfig r0 = com.yddmi.doctor.config.YddConfig.INSTANCE     // Catch: java.lang.Throwable -> L2b
                java.util.concurrent.ConcurrentHashMap r0 = r0.getKvData()     // Catch: java.lang.Throwable -> L2b
                java.lang.String r10 = "kvWxToken"
                java.lang.Object r0 = r0.get(r10)     // Catch: java.lang.Throwable -> L2b
                boolean r10 = r0 instanceof com.yddmi.doctor.entity.result.WxToken     // Catch: java.lang.Throwable -> L2b
                if (r10 == 0) goto L4e
                com.yddmi.doctor.entity.result.WxToken r0 = (com.yddmi.doctor.entity.result.WxToken) r0     // Catch: java.lang.Throwable -> L2b
                goto L4f
            L4e:
                r0 = r9
            L4f:
                if (r0 == 0) goto L93
                com.yddmi.doctor.entity.request.LoginWx r6 = new com.yddmi.doctor.entity.request.LoginWx     // Catch: java.lang.Throwable -> L2b
                java.lang.String r11 = r0.getUnionid()     // Catch: java.lang.Throwable -> L2b
                java.lang.String r12 = "android"
                r13 = 1
                java.lang.String r14 = r1.$phoneStr     // Catch: java.lang.Throwable -> L2b
                java.lang.String r15 = r1.$codeStr     // Catch: java.lang.Throwable -> L2b
                java.lang.String r16 = r0.getAccess_token()     // Catch: java.lang.Throwable -> L2b
                r10 = r6
                r10.<init>(r11, r12, r13, r14, r15, r16)     // Catch: java.lang.Throwable -> L2b
                com.yddmi.doctor.pages.login.LoginWxViewModel r0 = r1.this$0     // Catch: java.lang.Throwable -> L2b
                com.yddmi.doctor.repository.YddClinicRepository r10 = com.yddmi.doctor.repository.YddClinicRepository.INSTANCE     // Catch: java.lang.Throwable -> L2b
                r1.L$0 = r0     // Catch: java.lang.Throwable -> L2b
                r1.label = r8     // Catch: java.lang.Throwable -> L2b
                java.lang.Object r6 = r10.postWxLogin(r6, r1)     // Catch: java.lang.Throwable -> L2b
                if (r6 != r2) goto L75
                return r2
            L75:
                com.yddmi.doctor.entity.result.AuthLoginResult r6 = (com.yddmi.doctor.entity.result.AuthLoginResult) r6     // Catch: java.lang.Throwable -> L2b
                r0.setLoginResult(r6)     // Catch: java.lang.Throwable -> L2b
                com.yddmi.doctor.pages.login.LoginWxViewModel r0 = r1.this$0     // Catch: java.lang.Throwable -> L2b
                kotlinx.coroutines.flow.MutableStateFlow r0 = r0.getWxLoginMsf()     // Catch: java.lang.Throwable -> L2b
                long r10 = com.catchpig.mvvm.utils.DateUtil.getTimeInMillisLong()     // Catch: java.lang.Throwable -> L2b
                java.lang.Long r6 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r10)     // Catch: java.lang.Throwable -> L2b
                r1.L$0 = r9     // Catch: java.lang.Throwable -> L2b
                r1.label = r7     // Catch: java.lang.Throwable -> L2b
                java.lang.Object r0 = r0.emit(r6, r1)     // Catch: java.lang.Throwable -> L2b
                if (r0 != r2) goto Lc4
                return r2
            L93:
                com.yddmi.doctor.pages.login.LoginWxViewModel r0 = r1.this$0     // Catch: java.lang.Throwable -> L2b
                kotlinx.coroutines.flow.MutableStateFlow r0 = r0.getWxLoginMsf()     // Catch: java.lang.Throwable -> L2b
                java.lang.Long r7 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r3)     // Catch: java.lang.Throwable -> L2b
                r1.label = r6     // Catch: java.lang.Throwable -> L2b
                java.lang.Object r0 = r0.emit(r7, r1)     // Catch: java.lang.Throwable -> L2b
                if (r0 != r2) goto Lc4
                return r2
            La6:
                java.lang.String r0 = java.lang.String.valueOf(r0)
                java.lang.String r6 = "h0xta"
                com.catchpig.utils.ext.LogExtKt.loge(r0, r6)
                com.yddmi.doctor.pages.login.LoginWxViewModel r0 = r1.this$0
                kotlinx.coroutines.flow.MutableStateFlow r0 = r0.getWxLoginMsf()
                java.lang.Long r3 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r3)
                r1.L$0 = r9
                r1.label = r5
                java.lang.Object r0 = r0.emit(r3, r1)
                if (r0 != r2) goto Lc4
                return r2
            Lc4:
                kotlin.Unit r0 = kotlin.Unit.INSTANCE
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.login.LoginWxViewModel.C07691.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void dealTimeGo() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    @Nullable
    public final AuthLoginResult getLoginResult() {
        return this.loginResult;
    }

    public final boolean getMSelected() {
        return this.mSelected;
    }

    @NotNull
    public final Flow<MeProfile> getPersonInfo() {
        return YddClinicRepository.INSTANCE.getPersonInfoApp();
    }

    @NotNull
    public final Flow<String> getPushCodeRegister(@NotNull String phone) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        return YddClinicRepository.INSTANCE.getPushCodeRegister(phone);
    }

    @NotNull
    public final MutableStateFlow<Integer> getTimeCost() {
        return this.timeCost;
    }

    public final int getType() {
        return this.type;
    }

    @NotNull
    public final MutableStateFlow<Long> getWxLoginMsf() {
        return this.wxLoginMsf;
    }

    public final void httpWxLogin(@NotNull String phoneStr, @NotNull String codeStr) {
        Intrinsics.checkNotNullParameter(phoneStr, "phoneStr");
        Intrinsics.checkNotNullParameter(codeStr, "codeStr");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07691(phoneStr, codeStr, this, null), 3, null);
    }

    public final void setLoginResult(@Nullable AuthLoginResult authLoginResult) {
        this.loginResult = authLoginResult;
    }

    public final void setMSelected(boolean z2) {
        this.mSelected = z2;
    }

    public final void setTimeCost(@NotNull MutableStateFlow<Integer> mutableStateFlow) {
        Intrinsics.checkNotNullParameter(mutableStateFlow, "<set-?>");
        this.timeCost = mutableStateFlow;
    }

    public final void setType(int i2) {
        this.type = i2;
    }
}
