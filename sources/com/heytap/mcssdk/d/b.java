package com.heytap.mcssdk.d;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.heytap.mcssdk.constant.c;
import com.heytap.msp.push.mode.BaseMode;
import com.heytap.msp.push.mode.DataMessage;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b extends c {
    @Override // com.heytap.mcssdk.d.d
    public BaseMode a(Context context, int i2, Intent intent) throws NumberFormatException {
        if (4103 != i2 && 4098 != i2 && 4108 != i2) {
            return null;
        }
        BaseMode baseModeA = a(intent, i2);
        com.heytap.mcssdk.f.a.a(context, c.a.f7203b, (DataMessage) baseModeA);
        return baseModeA;
    }

    @Override // com.heytap.mcssdk.d.c
    public BaseMode a(Intent intent, int i2) throws NumberFormatException {
        try {
            DataMessage dataMessage = new DataMessage();
            dataMessage.setMessageID(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7178c)));
            dataMessage.setTaskID(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7179d)));
            dataMessage.setGlobalId(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7183h)));
            dataMessage.setAppPackage(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7180e)));
            dataMessage.setTitle(com.heytap.mcssdk.utils.b.d(intent.getStringExtra("title")));
            dataMessage.setContent(com.heytap.mcssdk.utils.b.d(intent.getStringExtra("content")));
            dataMessage.setDescription(com.heytap.mcssdk.utils.b.d(intent.getStringExtra("description")));
            String strD = com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7185j));
            int i3 = 0;
            dataMessage.setNotifyID(TextUtils.isEmpty(strD) ? 0 : Integer.parseInt(strD));
            dataMessage.setMiniProgramPkg(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7198w)));
            dataMessage.setMessageType(i2);
            dataMessage.setEventId(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7186k)));
            dataMessage.setStatisticsExtra(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7187l)));
            String strD2 = com.heytap.mcssdk.utils.b.d(intent.getStringExtra("data_extra"));
            dataMessage.setDataExtra(strD2);
            String strA = a(strD2);
            if (!TextUtils.isEmpty(strA)) {
                i3 = Integer.parseInt(strA);
            }
            dataMessage.setMsgCommand(i3);
            dataMessage.setBalanceTime(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7189n)));
            dataMessage.setStartDate(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7194s)));
            dataMessage.setEndDate(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7195t)));
            dataMessage.setTimeRanges(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7190o)));
            dataMessage.setRule(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7191p)));
            dataMessage.setForcedDelivery(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7192q)));
            dataMessage.setDistinctContent(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7193r)));
            dataMessage.setAppId(com.heytap.mcssdk.utils.b.d(intent.getStringExtra(com.heytap.mcssdk.constant.b.f7196u)));
            return dataMessage;
        } catch (Exception e2) {
            com.heytap.mcssdk.utils.d.b("OnHandleIntent--" + e2.getMessage());
            return null;
        }
    }

    public String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new JSONObject(str).optString(com.heytap.mcssdk.constant.b.f7197v);
        } catch (JSONException e2) {
            com.heytap.mcssdk.utils.d.b(e2.getMessage());
            return "";
        }
    }
}
