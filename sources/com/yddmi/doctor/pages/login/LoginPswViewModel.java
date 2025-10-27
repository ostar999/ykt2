package com.yddmi.doctor.pages.login;

import android.os.Build;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.AppInformationUtil;
import com.psychiatrygarden.utils.CommonParameter;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.entity.result.AuthLoginResult;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.utils.SecretUtil;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\rJ\u000e\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u0013R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0019"}, d2 = {"Lcom/yddmi/doctor/pages/login/LoginPswViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "mSelected", "", "getMSelected", "()Z", "setMSelected", "(Z)V", "mShowPsw", "getMShowPsw", "setMShowPsw", "mUserName", "", "getMUserName", "()Ljava/lang/String;", "setMUserName", "(Ljava/lang/String;)V", "authLogin", "Lkotlinx/coroutines/flow/Flow;", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "userName", "userPsw", "getPersonInfo", "Lcom/yddmi/doctor/entity/result/MeProfile;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class LoginPswViewModel extends BaseViewModel {
    private boolean mSelected;
    private boolean mShowPsw;

    @NotNull
    private String mUserName = "";

    @NotNull
    public final Flow<AuthLoginResult> authLogin(@NotNull String userName, @NotNull String userPsw) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        Intrinsics.checkNotNullParameter(userName, "userName");
        Intrinsics.checkNotNullParameter(userPsw, "userPsw");
        this.mUserName = userName;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("username", userName);
        String strEncrypt = SecretUtil.encrypt(userPsw, "lskj2017lskj2017", "lskj2017lskj2017");
        Intrinsics.checkNotNullExpressionValue(strEncrypt, "encrypt(\n            use…inSecret}j2017\"\n        )");
        linkedHashMap.put(CommonParameter.password, strEncrypt);
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
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("brandName", BRAND);
        linkedHashMap.put("operatingSystem", AppInformationUtil.isHarmonyOS() ? YddConfig.harmonyOs : "android");
        linkedHashMap.put("deviceType", "2");
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("modelName", MODEL);
        String DEVICE = Build.DEVICE;
        Intrinsics.checkNotNullExpressionValue(DEVICE, "DEVICE");
        linkedHashMap.put("modelCode", DEVICE);
        return YddClinicRepository.INSTANCE.authLogin(linkedHashMap);
    }

    public final boolean getMSelected() {
        return this.mSelected;
    }

    public final boolean getMShowPsw() {
        return this.mShowPsw;
    }

    @NotNull
    public final String getMUserName() {
        return this.mUserName;
    }

    @NotNull
    public final Flow<MeProfile> getPersonInfo() {
        return YddClinicRepository.INSTANCE.getPersonInfoApp();
    }

    public final void setMSelected(boolean z2) {
        this.mSelected = z2;
    }

    public final void setMShowPsw(boolean z2) {
        this.mShowPsw = z2;
    }

    public final void setMUserName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.mUserName = str;
    }
}
