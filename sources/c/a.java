package c;

import android.content.Context;
import android.media.AudioRecord;
import com.hjq.permissions.Permission;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f2210a = "AudioPermissionCheckUtils";

    /* renamed from: b, reason: collision with root package name */
    public static int f2211b = 1;

    /* renamed from: c, reason: collision with root package name */
    public static int f2212c = 44100;

    /* renamed from: d, reason: collision with root package name */
    public static int f2213d = 12;

    /* renamed from: e, reason: collision with root package name */
    public static int f2214e = 2;

    /* renamed from: f, reason: collision with root package name */
    public static int f2215f;

    public static boolean a() throws IllegalStateException {
        f2215f = 0;
        f2215f = AudioRecord.getMinBufferSize(f2212c, f2213d, f2214e);
        try {
            AudioRecord audioRecord = new AudioRecord(f2211b, f2212c, f2213d, f2214e, f2215f);
            try {
                audioRecord.startRecording();
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
            }
            if (audioRecord.getRecordingState() != 3 && audioRecord.getRecordingState() != 1) {
                h.c(f2210a, "audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING : " + audioRecord.getRecordingState());
                return false;
            }
            int i2 = audioRecord.read(new byte[1024], 0, 1024);
            if (i2 == -3 || i2 <= 0) {
                h.c(f2210a, "readSize illegal : " + i2);
                return false;
            }
            if (audioRecord.getRecordingState() == 1) {
                return true;
            }
            audioRecord.stop();
            audioRecord.release();
            return true;
        } catch (Exception e3) {
            e3.printStackTrace();
            return false;
        }
    }

    public static boolean a(Context context) {
        return context.checkSelfPermission(Permission.RECORD_AUDIO) == 0;
    }
}
