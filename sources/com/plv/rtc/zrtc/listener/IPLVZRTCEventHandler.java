package com.plv.rtc.zrtc.listener;

import com.plv.rtc.zrtc.enummeration.ZRTCRemoteDeviceState;
import com.plv.rtc.zrtc.enummeration.ZRTCRoomState;
import com.plv.rtc.zrtc.enummeration.ZRTCUpdateType;
import com.plv.rtc.zrtc.model.ZRTCStream;
import java.util.ArrayList;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public abstract class IPLVZRTCEventHandler {
    public void onDebugError(int i2, String str, String str2) {
    }

    public void onRemoteCameraStateUpdate(String str, ZRTCRemoteDeviceState zRTCRemoteDeviceState) {
    }

    public void onRemoteMicStateUpdate(String str, ZRTCRemoteDeviceState zRTCRemoteDeviceState) {
    }

    public void onRoomStateUpdate(String str, ZRTCRoomState zRTCRoomState, int i2, JSONObject jSONObject) {
    }

    public void onRoomStreamUpdate(String str, ZRTCUpdateType zRTCUpdateType, ArrayList<ZRTCStream> arrayList) {
    }
}
