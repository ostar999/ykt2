package com.beizi.ad.internal.utilities;

import com.beizi.ad.R;
import com.beizi.ad.c.e;

/* loaded from: classes2.dex */
public class UserEnvInfo {
    private static UserEnvInfo sUserEnvInfoInstance;
    public String ip;
    public e.c isp = e.c.ISP_OTHER;
    public boolean locationEnabled = true;
    private int mLocationDecimalDigits = -1;

    /* renamed from: net, reason: collision with root package name */
    public e.d f4406net;

    private UserEnvInfo() {
    }

    public static UserEnvInfo getInstance() {
        UserEnvInfo userEnvInfo;
        synchronized (UserEnvInfo.class) {
            if (sUserEnvInfoInstance == null) {
                sUserEnvInfoInstance = new UserEnvInfo();
                HaoboLog.v(HaoboLog.baseLogTag, HaoboLog.getString(R.string.init));
            }
            userEnvInfo = sUserEnvInfoInstance;
        }
        return userEnvInfo;
    }

    public int getLocationDecimalDigits() {
        return this.mLocationDecimalDigits;
    }

    public void setLocationDecimalDigits(int i2) {
        if (i2 > 6) {
            this.mLocationDecimalDigits = 6;
            HaoboLog.w(HaoboLog.baseLogTag, "Out of range input " + i2 + ", set location digits after decimal to maximum 6");
            return;
        }
        if (i2 >= -1) {
            this.mLocationDecimalDigits = i2;
            return;
        }
        this.mLocationDecimalDigits = -1;
        HaoboLog.w(HaoboLog.baseLogTag, "Negative input " + i2 + ", set location digits after decimal to default");
    }
}
