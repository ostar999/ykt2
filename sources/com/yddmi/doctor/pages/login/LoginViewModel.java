package com.yddmi.doctor.pages.login;

import android.os.Build;
import androidx.lifecycle.ViewModelKt;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.AppInformationUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.hjq.toast.Toaster;
import com.umeng.analytics.pro.am;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.result.AuthLoginResult;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.repository.YddClinicRepository;
import java.util.LinkedHashMap;
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

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014J\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0011J\u0016\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u00112\u0006\u0010\u0013\u001a\u00020\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lcom/yddmi/doctor/pages/login/LoginViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "mSelected", "", "getMSelected", "()Z", "setMSelected", "(Z)V", "timeCost", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getTimeCost", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "setTimeCost", "(Lkotlinx/coroutines/flow/MutableStateFlow;)V", "codeLogin", "Lkotlinx/coroutines/flow/Flow;", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", AliyunLogCommon.TERMINAL_TYPE, "", "code", "dealTimeGo", "", "getPersonInfo", "Lcom/yddmi/doctor/entity/result/MeProfile;", "getPushCodeRegister", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class LoginViewModel extends BaseViewModel {
    private boolean mSelected;

    @NotNull
    private MutableStateFlow<Integer> timeCost = StateFlowKt.MutableStateFlow(0);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.login.LoginViewModel$dealTimeGo$1", f = "LoginViewModel.kt", i = {0, 1}, l = {31, 32}, m = "invokeSuspend", n = {am.aC, am.aC}, s = {"I$0", "I$0"})
    /* renamed from: com.yddmi.doctor.pages.login.LoginViewModel$dealTimeGo$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return LoginViewModel.this.new AnonymousClass1(continuation);
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
                com.yddmi.doctor.pages.login.LoginViewModel r5 = com.yddmi.doctor.pages.login.LoginViewModel.this
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
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.login.LoginViewModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @NotNull
    public final Flow<AuthLoginResult> codeLogin(@NotNull String phone, @NotNull String code) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(code, "code");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(AliyunLogCommon.TERMINAL_TYPE, phone);
        linkedHashMap.put("code", code);
        linkedHashMap.put("phoneSystem", "android");
        YddConfig yddConfig = YddConfig.INSTANCE;
        linkedHashMap.put("oaId", yddConfig.getAndroidInfo(100));
        linkedHashMap.put("mobileDeviceId", yddConfig.getAndroidInfo(100));
        String BRAND = Build.BRAND;
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("mobileBrand", BRAND);
        String MODEL = Build.MODEL;
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("mobileModel", MODEL);
        String RELEASE = Build.VERSION.RELEASE;
        Intrinsics.checkNotNullExpressionValue(RELEASE, "RELEASE");
        linkedHashMap.put("mobileVersion", RELEASE);
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        linkedHashMap.put("inviteSign", companion.getInstance().loginShareCodeGet());
        if (AppInformationUtil.isApkDebugable()) {
            String strLoginShareCodeGet = companion.getInstance().loginShareCodeGet();
            LogExtKt.logd("邀请码：" + strLoginShareCodeGet + " ", YddConfig.TAG);
            StringBuilder sb = new StringBuilder();
            sb.append("手机号验证码登录邀请码:");
            sb.append(strLoginShareCodeGet);
            Toaster.showLong((CharSequence) sb.toString());
        }
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("brandName", BRAND);
        linkedHashMap.put("operatingSystem", AppInformationUtil.isHarmonyOS() ? YddConfig.harmonyOs : "android");
        linkedHashMap.put("deviceType", "2");
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("modelName", MODEL);
        String DEVICE = Build.DEVICE;
        Intrinsics.checkNotNullExpressionValue(DEVICE, "DEVICE");
        linkedHashMap.put("modelCode", DEVICE);
        return YddClinicRepository.INSTANCE.codeLogin(linkedHashMap);
    }

    public final void dealTimeGo() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
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

    public final void setMSelected(boolean z2) {
        this.mSelected = z2;
    }

    public final void setTimeCost(@NotNull MutableStateFlow<Integer> mutableStateFlow) {
        Intrinsics.checkNotNullParameter(mutableStateFlow, "<set-?>");
        this.timeCost = mutableStateFlow;
    }
}
