package com.yddmi.doctor.utils.pay.wx;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.catchpig.utils.LogUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.utils.pay.JPay;
import java.util.List;

/* loaded from: classes6.dex */
public class WeiXinPay {
    public static final int PAY_ERROR = 3;
    public static final int PAY_PARAMETERS_ERROE = 2;
    public static final int WEIXIN_VERSION_LOW = 1;
    private static WeiXinPay mWeiXinPay;
    private final String TAG = "WeiXinPay";
    private Context mContext;
    private IWXAPI mIWXAPI;
    private JPay.PayListener mJPayListener;

    private WeiXinPay(Context context) {
        this.mContext = context;
    }

    private boolean appIsAvailable(String str) {
        List<PackageInfo> installedPackages = this.mContext.getPackageManager().getInstalledPackages(0);
        if (installedPackages != null) {
            for (int i2 = 0; i2 < installedPackages.size(); i2++) {
                if (installedPackages.get(i2).packageName.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkWx() {
        return isWeiXinAvailable() && this.mIWXAPI.isWXAppInstalled() && this.mIWXAPI.getWXAppSupportAPI() >= 570425345;
    }

    public static WeiXinPay getInstance(Context context) {
        if (mWeiXinPay == null) {
            synchronized (WeiXinPay.class) {
                if (mWeiXinPay == null) {
                    mWeiXinPay = new WeiXinPay(context);
                }
            }
        }
        return mWeiXinPay;
    }

    private void init(String str) {
        IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(this.mContext, null);
        this.mIWXAPI = iwxapiCreateWXAPI;
        iwxapiCreateWXAPI.registerApp(str);
    }

    public IWXAPI getWXApi() {
        return this.mIWXAPI;
    }

    public boolean isWeiXinAvailable() {
        return appIsAvailable("com.tencent.mm");
    }

    public void onResp(int i2, String str) {
        LogUtils.INSTANCE.getInstance().d("WeiXinPay", "微信 onResp 回调");
        JPay.PayListener payListener = this.mJPayListener;
        if (payListener == null) {
            return;
        }
        if (i2 == 0) {
            payListener.onPaySuccess();
        } else if (i2 == -1) {
            payListener.onPayError(3, str);
        } else if (i2 == -2) {
            payListener.onPayCancel();
        }
        this.mJPayListener = null;
    }

    public void startWXPay(String str, String str2, String str3, String str4, String str5, String str6, JPay.PayListener payListener) {
        this.mJPayListener = payListener;
        init(YddUserManager.INSTANCE.getInstance().getmWxApi());
        if (!checkWx()) {
            if (payListener != null) {
                payListener.onPayError(1, "未安装微信或者微信版本过低");
                return;
            }
            return;
        }
        PayReq payReq = new PayReq();
        payReq.appId = str;
        payReq.partnerId = str2;
        payReq.prepayId = str3;
        payReq.packageValue = "Sign=WXPay";
        payReq.nonceStr = str4;
        payReq.timeStamp = str5;
        payReq.sign = str6;
        this.mIWXAPI.sendReq(payReq);
    }

    public void init(IWXAPI iwxapi) {
        this.mIWXAPI = iwxapi;
    }
}
