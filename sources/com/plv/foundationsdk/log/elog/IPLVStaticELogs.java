package com.plv.foundationsdk.log.elog;

import com.plv.foundationsdk.model.log.PLVELogSendType;
import com.plv.foundationsdk.net.PLVBaseResponseBean;
import retrofit2.Call;

/* loaded from: classes4.dex */
public interface IPLVStaticELogs {
    Call<PLVBaseResponseBean> sendLogs(PLVELogSendType pLVELogSendType, String str);
}
