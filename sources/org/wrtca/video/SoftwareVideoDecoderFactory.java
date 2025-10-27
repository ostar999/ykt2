package org.wrtca.video;

import org.wrtca.api.VideoDecoder;
import org.wrtca.api.VideoDecoderFactory;

/* loaded from: classes9.dex */
public class SoftwareVideoDecoderFactory implements VideoDecoderFactory {
    @Override // org.wrtca.api.VideoDecoderFactory
    public VideoDecoder createDecoder(String str) {
        if (str.equalsIgnoreCase(j.a.f27381j)) {
            return new VP8Decoder();
        }
        if (str.equalsIgnoreCase(j.a.f27382k) && VP9Decoder.nativeIsSupported()) {
            return new VP9Decoder();
        }
        return null;
    }
}
