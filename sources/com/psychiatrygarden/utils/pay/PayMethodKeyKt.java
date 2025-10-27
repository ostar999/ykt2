package com.psychiatrygarden.utils.pay;

import android.content.Context;
import com.psychiatrygarden.utils.SdkConstant;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u001aF\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"ALi_PayMethod", "", "PAY_SUCCESS_CODE", "WX_PayMethod", "wxPay", "", d.R, "Landroid/content/Context;", "appId", "partnerId", "prepayId", "packageValue", "nonceStr", "timeStamp", "sign", "xizongapp_ykbRelease"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PayMethodKeyKt {

    @NotNull
    public static final String ALi_PayMethod = "alipay";

    @NotNull
    public static final String PAY_SUCCESS_CODE = "9000";

    @NotNull
    public static final String WX_PayMethod = "wechat";

    public static final void wxPay(@NotNull Context context, @NotNull String appId, @NotNull String partnerId, @NotNull String prepayId, @NotNull String packageValue, @NotNull String nonceStr, @NotNull String timeStamp, @NotNull String sign) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appId, "appId");
        Intrinsics.checkNotNullParameter(partnerId, "partnerId");
        Intrinsics.checkNotNullParameter(prepayId, "prepayId");
        Intrinsics.checkNotNullParameter(packageValue, "packageValue");
        Intrinsics.checkNotNullParameter(nonceStr, "nonceStr");
        Intrinsics.checkNotNullParameter(timeStamp, "timeStamp");
        Intrinsics.checkNotNullParameter(sign, "sign");
        IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(context, SdkConstant.getWxAppId());
        PayReq payReq = new PayReq();
        payReq.appId = appId;
        payReq.partnerId = partnerId;
        payReq.prepayId = prepayId;
        payReq.packageValue = packageValue;
        payReq.nonceStr = nonceStr;
        payReq.timeStamp = timeStamp;
        payReq.sign = sign;
        iwxapiCreateWXAPI.sendReq(payReq);
    }
}
