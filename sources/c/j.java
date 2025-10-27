package c;

import java.nio.ByteBuffer;
import org.wrtca.customize.RtcNativeOperation;
import org.wrtca.jni.JniCommon;

/* loaded from: classes.dex */
public class j implements RtcNativeOperation {

    /* renamed from: a, reason: collision with root package name */
    public static j f2296a;

    public static j a() {
        if (f2296a == null) {
            synchronized (j.class) {
                if (f2296a == null) {
                    f2296a = new j();
                }
            }
        }
        return f2296a;
    }

    @Override // org.wrtca.customize.RtcNativeOperation
    public ByteBuffer createNativeByteBuffer(int i2) {
        return JniCommon.nativeAllocateByteBuffer(i2);
    }

    @Override // org.wrtca.customize.RtcNativeOperation
    public void releaseNativeByteBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            JniCommon.nativeFreeByteBuffer(byteBuffer);
        }
    }
}
