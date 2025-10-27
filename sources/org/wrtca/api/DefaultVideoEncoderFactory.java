package org.wrtca.api;

import java.util.Arrays;
import java.util.LinkedHashSet;
import org.wrtca.api.EglBase;
import org.wrtca.video.SoftwareVideoEncoderFactory;

/* loaded from: classes9.dex */
public class DefaultVideoEncoderFactory implements VideoEncoderFactory {
    private final VideoEncoderFactory hardwareVideoEncoderFactory;
    private final VideoEncoderFactory softwareVideoEncoderFactory = new SoftwareVideoEncoderFactory();

    public DefaultVideoEncoderFactory(EglBase.Context context, boolean z2, boolean z3) {
        this.hardwareVideoEncoderFactory = new HardwareVideoEncoderFactory(context, z2, z3, false);
    }

    @Override // org.wrtca.api.VideoEncoderFactory
    public VideoEncoder createEncoder(VideoCodecInfo videoCodecInfo) {
        VideoEncoder videoEncoderCreateEncoder = this.hardwareVideoEncoderFactory.createEncoder(videoCodecInfo);
        return videoEncoderCreateEncoder != null ? videoEncoderCreateEncoder : this.softwareVideoEncoderFactory.createEncoder(videoCodecInfo);
    }

    @Override // org.wrtca.api.VideoEncoderFactory
    public VideoCodecInfo[] getSupportedCodecs() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(Arrays.asList(this.softwareVideoEncoderFactory.getSupportedCodecs()));
        linkedHashSet.addAll(Arrays.asList(this.hardwareVideoEncoderFactory.getSupportedCodecs()));
        return (VideoCodecInfo[]) linkedHashSet.toArray(new VideoCodecInfo[linkedHashSet.size()]);
    }

    public DefaultVideoEncoderFactory(VideoEncoderFactory videoEncoderFactory) {
        this.hardwareVideoEncoderFactory = videoEncoderFactory;
    }
}
