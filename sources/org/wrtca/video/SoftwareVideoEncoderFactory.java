package org.wrtca.video;

import c.h;
import java.util.ArrayList;
import java.util.HashMap;
import org.wrtca.api.VideoCodecInfo;
import org.wrtca.api.VideoEncoder;
import org.wrtca.api.VideoEncoderFactory;

/* loaded from: classes9.dex */
public class SoftwareVideoEncoderFactory implements VideoEncoderFactory {
    private static final String TAG = "SoftwareVideoEncoderFac";

    public static VideoCodecInfo[] supportedCodecs() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new VideoCodecInfo(j.a.f27381j, new HashMap()));
        if (VP9Encoder.nativeIsSupported()) {
            arrayList.add(new VideoCodecInfo(j.a.f27382k, new HashMap()));
        }
        return (VideoCodecInfo[]) arrayList.toArray(new VideoCodecInfo[arrayList.size()]);
    }

    @Override // org.wrtca.api.VideoEncoderFactory
    public VideoEncoder createEncoder(VideoCodecInfo videoCodecInfo) {
        h.a(TAG, "createEncoder in SoftwareVideoEncoderFac");
        if (videoCodecInfo.name.equalsIgnoreCase(j.a.f27381j)) {
            h.a(TAG, "create soft ware encoder vp8: ");
            return new VP8Encoder();
        }
        if (!videoCodecInfo.name.equalsIgnoreCase(j.a.f27382k) || !VP9Encoder.nativeIsSupported()) {
            return null;
        }
        h.a(TAG, "create soft ware encoder vp9: ");
        return new VP9Encoder();
    }

    @Override // org.wrtca.api.VideoEncoderFactory
    public VideoCodecInfo[] getSupportedCodecs() {
        return supportedCodecs();
    }
}
