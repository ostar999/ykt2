package org.wrtca.api;

import org.wrtca.api.EglBase;
import org.wrtca.video.SoftwareVideoDecoderFactory;

/* loaded from: classes9.dex */
public class DefaultVideoDecoderFactory implements VideoDecoderFactory {
    private final HardwareVideoDecoderFactory hardwareVideoDecoderFactory;
    private final SoftwareVideoDecoderFactory softwareVideoDecoderFactory = new SoftwareVideoDecoderFactory();

    public DefaultVideoDecoderFactory(EglBase.Context context) {
        this.hardwareVideoDecoderFactory = new HardwareVideoDecoderFactory(context, false);
    }

    @Override // org.wrtca.api.VideoDecoderFactory
    public VideoDecoder createDecoder(String str) {
        VideoDecoder videoDecoderCreateDecoder = this.hardwareVideoDecoderFactory.createDecoder(str);
        return videoDecoderCreateDecoder != null ? videoDecoderCreateDecoder : this.softwareVideoDecoderFactory.createDecoder(str);
    }
}
