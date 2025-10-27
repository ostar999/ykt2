package org.wrtca.api;

import java.nio.ByteBuffer;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;
import org.wrtca.jni.JniCommon;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class DataChannel {
    private final long nativeDataChannel;
    private long nativeObserver;

    public static class Buffer {
        public final boolean binary;
        public final ByteBuffer data;

        @CalledByNative("Buffer")
        public Buffer(ByteBuffer byteBuffer, boolean z2) {
            this.data = byteBuffer;
            this.binary = z2;
        }
    }

    public static class Init {
        public boolean ordered = true;
        public int maxRetransmitTimeMs = -1;
        public int maxRetransmits = -1;
        public String protocol = "";
        public boolean negotiated = false;
        public int id = -1;

        @CalledByNative("Init")
        public int getId() {
            return this.id;
        }

        @CalledByNative("Init")
        public int getMaxRetransmitTimeMs() {
            return this.maxRetransmitTimeMs;
        }

        @CalledByNative("Init")
        public int getMaxRetransmits() {
            return this.maxRetransmits;
        }

        @CalledByNative("Init")
        public boolean getNegotiated() {
            return this.negotiated;
        }

        @CalledByNative("Init")
        public boolean getOrdered() {
            return this.ordered;
        }

        @CalledByNative("Init")
        public String getProtocol() {
            return this.protocol;
        }
    }

    public interface Observer {
        @CalledByNative("Observer")
        void onBufferedAmountChange(long j2);

        @CalledByNative("Observer")
        void onMessage(Buffer buffer);

        @CalledByNative("Observer")
        void onStateChange();
    }

    public enum State {
        CONNECTING,
        OPEN,
        CLOSING,
        CLOSED;

        @CalledByNative("State")
        public static State fromNativeIndex(int i2) {
            return values()[i2];
        }
    }

    @CalledByNative
    public DataChannel(long j2) {
        this.nativeDataChannel = j2;
    }

    private native long nativeBufferedAmount();

    private native void nativeClose();

    private native int nativeId();

    private native String nativeLabel();

    private native long nativeRegisterObserver(Observer observer);

    private native boolean nativeSend(byte[] bArr, boolean z2);

    private native State nativeState();

    private native void nativeUnregisterObserver(long j2);

    public long bufferedAmount() {
        return nativeBufferedAmount();
    }

    public void close() {
        nativeClose();
    }

    public void dispose() {
        JniCommon.nativeReleaseRef(this.nativeDataChannel);
    }

    @CalledByNative
    public long getNativeDataChannel() {
        return this.nativeDataChannel;
    }

    public int id() {
        return nativeId();
    }

    public String label() {
        return nativeLabel();
    }

    public void registerObserver(Observer observer) {
        long j2 = this.nativeObserver;
        if (j2 != 0) {
            nativeUnregisterObserver(j2);
        }
        this.nativeObserver = nativeRegisterObserver(observer);
    }

    public boolean send(Buffer buffer) {
        byte[] bArr = new byte[buffer.data.remaining()];
        buffer.data.get(bArr);
        return nativeSend(bArr, buffer.binary);
    }

    public State state() {
        return nativeState();
    }

    public void unregisterObserver() {
        nativeUnregisterObserver(this.nativeObserver);
    }
}
