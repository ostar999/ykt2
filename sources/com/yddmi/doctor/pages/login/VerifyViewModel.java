package com.yddmi.doctor.pages.login;

import androidx.lifecycle.ViewModelKt;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.umeng.analytics.pro.am;
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

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0013J\u001e\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0013R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Lcom/yddmi/doctor/pages/login/VerifyViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "timeCost", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getTimeCost", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "setTimeCost", "(Lkotlinx/coroutines/flow/MutableStateFlow;)V", "type", "getType", "()I", "setType", "(I)V", "dealTimeGo", "", "getPushCodeForgetPwd", "Lkotlinx/coroutines/flow/Flow;", "", AliyunLogCommon.TERMINAL_TYPE, "getVeriFyMsg", "code", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class VerifyViewModel extends BaseViewModel {
    private int type = 100;

    @NotNull
    private MutableStateFlow<Integer> timeCost = StateFlowKt.MutableStateFlow(0);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.login.VerifyViewModel$dealTimeGo$1", f = "VerifyViewModel.kt", i = {0, 1}, l = {21, 22}, m = "invokeSuspend", n = {am.aC, am.aC}, s = {"I$0", "I$0"})
    /* renamed from: com.yddmi.doctor.pages.login.VerifyViewModel$dealTimeGo$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return VerifyViewModel.this.new AnonymousClass1(continuation);
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
                com.yddmi.doctor.pages.login.VerifyViewModel r5 = com.yddmi.doctor.pages.login.VerifyViewModel.this
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
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.login.VerifyViewModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void dealTimeGo() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    @NotNull
    public final Flow<String> getPushCodeForgetPwd(@NotNull String phone) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        return YddClinicRepository.INSTANCE.getPushCodeForgetPwd(phone);
    }

    @NotNull
    public final MutableStateFlow<Integer> getTimeCost() {
        return this.timeCost;
    }

    public final int getType() {
        return this.type;
    }

    @NotNull
    public final Flow<String> getVeriFyMsg(@NotNull String phone, @NotNull String code) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(code, "code");
        return YddClinicRepository.INSTANCE.getVeriFyMsg(phone, code);
    }

    public final void setTimeCost(@NotNull MutableStateFlow<Integer> mutableStateFlow) {
        Intrinsics.checkNotNullParameter(mutableStateFlow, "<set-?>");
        this.timeCost = mutableStateFlow;
    }

    public final void setType(int i2) {
        this.type = i2;
    }
}
