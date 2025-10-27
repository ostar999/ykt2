package j;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.wrtca.api.PeerConnection;

/* loaded from: classes8.dex */
public class b {

    /* renamed from: c, reason: collision with root package name */
    public static final String f27398c = "EventLog";

    /* renamed from: d, reason: collision with root package name */
    public static final int f27399d = 10000000;

    /* renamed from: a, reason: collision with root package name */
    public final PeerConnection f27400a;

    /* renamed from: b, reason: collision with root package name */
    public a f27401b = a.INACTIVE;

    public enum a {
        INACTIVE,
        STARTED,
        STOPPED
    }

    public b(PeerConnection peerConnection) {
        if (peerConnection == null) {
            throw new NullPointerException("The peer connection is null.");
        }
        this.f27400a = peerConnection;
    }

    public void a(File file) throws FileNotFoundException {
        a aVar = this.f27401b;
        a aVar2 = a.STARTED;
        if (aVar == aVar2) {
            Log.e(f27398c, "EventLog has already started.");
            return;
        }
        try {
            if (!this.f27400a.startRtcEventLog(ParcelFileDescriptor.open(file, 1006632960).detachFd(), 10000000)) {
                Log.e(f27398c, "Failed to start RTC event log.");
            } else {
                this.f27401b = aVar2;
                c.h.a(f27398c, "EventLog started.");
            }
        } catch (IOException e2) {
            Log.e(f27398c, "Failed to create a new file", e2);
        }
    }

    public void a() {
        if (this.f27401b != a.STARTED) {
            Log.e(f27398c, "EventLog was not started.");
            return;
        }
        this.f27400a.stopRtcEventLog();
        this.f27401b = a.STOPPED;
        c.h.a(f27398c, "EventLog stopped.");
    }
}
