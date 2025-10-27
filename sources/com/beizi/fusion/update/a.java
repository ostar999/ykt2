package com.beizi.fusion.update;

import android.content.Context;
import android.text.TextUtils;
import com.beizi.fusion.g.aa;
import com.beizi.fusion.g.aq;
import com.beizi.fusion.g.d;
import com.beizi.fusion.g.x;
import com.beizi.fusion.model.GlobalConfig;
import com.beizi.fusion.model.ResponseInfo;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private Context f5325a;

    public a(Context context, String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (str == null) {
            return;
        }
        this.f5325a = context;
        a(str);
    }

    private void a(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        String strB = d.b(aa.a(), str);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        try {
            String strB2 = x.b(strB);
            String str2 = (String) aq.b(this.f5325a, "globalConfig", "");
            if ("".equals(str2)) {
                aq.a(this.f5325a, "globalConfig", (Object) d.a(aa.a(), strB2));
            }
            String strB3 = d.b(aa.a(), str2);
            GlobalConfig globalConfigObjectFromData = GlobalConfig.objectFromData(strB2);
            GlobalConfig globalConfigObjectFromData2 = GlobalConfig.objectFromData(strB3);
            if (globalConfigObjectFromData != null) {
                ResponseInfo.getInstance(this.f5325a).setGlobalConfig(globalConfigObjectFromData);
                if (!TextUtils.isEmpty(globalConfigObjectFromData.getConfigVersion())) {
                    if (globalConfigObjectFromData2 != null) {
                        globalConfigObjectFromData2.setConfigVersion(globalConfigObjectFromData.getConfigVersion());
                    }
                    if (ResponseInfo.getInstance(this.f5325a).getManager() == null) {
                        ResponseInfo.getInstance(this.f5325a).setConfigVersion(globalConfigObjectFromData.getConfigVersion());
                    }
                }
                if (globalConfigObjectFromData.getExpireTime() > 0) {
                    if (globalConfigObjectFromData2 != null) {
                        globalConfigObjectFromData2.setExpireTime(globalConfigObjectFromData.getExpireTime());
                    }
                    ResponseInfo.getInstance(this.f5325a).setExpireTime(globalConfigObjectFromData.getExpireTime());
                }
                if (globalConfigObjectFromData.getMaxValidTime() > 0) {
                    if (globalConfigObjectFromData2 != null) {
                        globalConfigObjectFromData2.setMaxValidTime(globalConfigObjectFromData.getMaxValidTime());
                    }
                    ResponseInfo.getInstance(this.f5325a).setMaxValidTime(globalConfigObjectFromData.getMaxValidTime());
                }
                if (globalConfigObjectFromData.getConfigurator() != null) {
                    if (globalConfigObjectFromData2 != null) {
                        globalConfigObjectFromData2.setConfigurator(globalConfigObjectFromData.getConfigurator());
                    }
                    if (ResponseInfo.getInstance(this.f5325a).getManager() == null) {
                        ResponseInfo.getInstance(this.f5325a).setConfigurator(globalConfigObjectFromData.getConfigurator());
                    }
                }
                if (globalConfigObjectFromData.getMessenger() != null) {
                    if (globalConfigObjectFromData2 != null) {
                        globalConfigObjectFromData2.setMessenger(globalConfigObjectFromData.getMessenger());
                    }
                    if (ResponseInfo.getInstance(this.f5325a).getManager() == null) {
                        ResponseInfo.getInstance(this.f5325a).setMessenger(globalConfigObjectFromData.getMessenger());
                    }
                }
                if (globalConfigObjectFromData.getManager() != null) {
                    if (globalConfigObjectFromData2 != null) {
                        globalConfigObjectFromData2.setManager(globalConfigObjectFromData.getManager());
                    }
                    if (ResponseInfo.getInstance(this.f5325a).getManager() == null) {
                        ResponseInfo.getInstance(this.f5325a).setManager(globalConfigObjectFromData.getManager());
                    }
                }
                if (globalConfigObjectFromData.getTaskConfig() != null) {
                    if (globalConfigObjectFromData2 != null) {
                        globalConfigObjectFromData2.setTaskConfig(globalConfigObjectFromData.getTaskConfig());
                    }
                    ResponseInfo.getInstance(this.f5325a).setTaskConfig(globalConfigObjectFromData.getTaskConfig());
                }
                if (globalConfigObjectFromData.getAdPlusConfig() != null) {
                    if (globalConfigObjectFromData2 != null) {
                        globalConfigObjectFromData2.setAdPlusConfig(globalConfigObjectFromData.getAdPlusConfig());
                    }
                    ResponseInfo.getInstance(this.f5325a).setAdPlusConfig(globalConfigObjectFromData.getAdPlusConfig());
                    com.beizi.fusion.d.x.a(this.f5325a);
                }
                if (!TextUtils.isEmpty(globalConfigObjectFromData.getCrashUrl())) {
                    if (globalConfigObjectFromData2 != null) {
                        globalConfigObjectFromData2.setCrashUrl(globalConfigObjectFromData.getCrashUrl());
                    }
                    ResponseInfo.getInstance(this.f5325a).setCrashUrl(globalConfigObjectFromData.getCrashUrl());
                }
                String strObjectToJson = GlobalConfig.objectToJson(globalConfigObjectFromData2);
                if (TextUtils.isEmpty(strObjectToJson)) {
                    return;
                }
                aq.a(this.f5325a, "globalConfig", (Object) d.a(aa.a(), strObjectToJson));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
