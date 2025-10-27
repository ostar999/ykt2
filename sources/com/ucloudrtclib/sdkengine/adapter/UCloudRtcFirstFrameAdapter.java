package com.ucloudrtclib.sdkengine.adapter;

import android.view.View;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamInfo;
import com.ucloudrtclib.sdkengine.openinterface.UCloudRTCFirstFrameRendered;
import core.data.StreamInfo;
import core.interfaces.FirstFrameRendered;

/* loaded from: classes6.dex */
public class UCloudRtcFirstFrameAdapter implements FirstFrameRendered {
    public UCloudRTCFirstFrameRendered mCallBack;

    public UCloudRtcFirstFrameAdapter(UCloudRTCFirstFrameRendered uCloudRTCFirstFrameRendered) {
        this.mCallBack = uCloudRTCFirstFrameRendered;
    }

    @Override // core.interfaces.FirstFrameRendered
    public void onFirstFrameRender(StreamInfo streamInfo, View view) {
        if (this.mCallBack != null) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
            this.mCallBack.onFirstFrameRender(uCloudRtcSdkStreamInfo.toProxy(streamInfo, uCloudRtcSdkStreamInfo), view);
        }
    }
}
