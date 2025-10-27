package com.yddmi.doctor.pages.login;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.psychiatrygarden.utils.CommonParameter;
import com.yddmi.doctor.repository.YddClinicRepository;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00132\u0006\u0010\u0014\u001a\u00020\u0004J\u0016\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00132\u0006\u0010\u0014\u001a\u00020\u0004R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lcom/yddmi/doctor/pages/login/PswSetViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "code", "", "getCode", "()Ljava/lang/String;", "setCode", "(Ljava/lang/String;)V", AliyunLogCommon.TERMINAL_TYPE, "getPhone", "setPhone", "type", "", "getType", "()I", "setType", "(I)V", "forgetPwd", "Lkotlinx/coroutines/flow/Flow;", "psw", "updatePwd", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PswSetViewModel extends BaseViewModel {
    private int type = 100;

    @Nullable
    private String code = "";

    @Nullable
    private String phone = "";

    @NotNull
    public final Flow<String> forgetPwd(@NotNull String psw) {
        Intrinsics.checkNotNullParameter(psw, "psw");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String str = this.phone;
        Intrinsics.checkNotNull(str);
        linkedHashMap.put(AliyunLogCommon.TERMINAL_TYPE, str);
        String str2 = this.code;
        Intrinsics.checkNotNull(str2);
        linkedHashMap.put("code", str2);
        linkedHashMap.put(CommonParameter.password, psw);
        return YddClinicRepository.INSTANCE.forgetPwd(linkedHashMap);
    }

    @Nullable
    public final String getCode() {
        return this.code;
    }

    @Nullable
    public final String getPhone() {
        return this.phone;
    }

    public final int getType() {
        return this.type;
    }

    public final void setCode(@Nullable String str) {
        this.code = str;
    }

    public final void setPhone(@Nullable String str) {
        this.phone = str;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    @NotNull
    public final Flow<String> updatePwd(@NotNull String psw) {
        Intrinsics.checkNotNullParameter(psw, "psw");
        return YddClinicRepository.INSTANCE.updatePwd(MapsKt__MapsJVMKt.mapOf(TuplesKt.to(CommonParameter.password, psw)));
    }
}
