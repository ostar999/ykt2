package io.agora.rtc.mediaio;

/* loaded from: classes8.dex */
public class MediaIO {

    public enum BufferType {
        BYTE_BUFFER(1),
        BYTE_ARRAY(2),
        TEXTURE(3);

        final int value;

        BufferType(int value) {
            this.value = value;
        }

        public int intValue() {
            return this.value;
        }
    }

    public enum PixelFormat {
        I420(1),
        NV21(3),
        RGBA(4),
        TEXTURE_2D(10),
        TEXTURE_OES(11);

        final int value;

        PixelFormat(int value) {
            this.value = value;
        }

        public int intValue() {
            return this.value;
        }
    }
}
