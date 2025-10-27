package g;

import android.os.Handler;
import core.data.AuthInfo;
import core.data.MediaOp;
import core.data.MixProfile;
import core.data.RecordProfile;
import core.data.StreamInfo;
import j.h;
import java.util.List;

/* loaded from: classes8.dex */
public interface b extends Handler.Callback {
    void a();

    void a(int i2);

    void a(int i2, Object obj);

    void a(int i2, String str);

    void a(int i2, boolean z2);

    void a(int i2, String[] strArr);

    void a(AuthInfo authInfo);

    void a(MediaOp mediaOp);

    void a(MixProfile mixProfile);

    void a(RecordProfile recordProfile);

    void a(StreamInfo streamInfo);

    void a(StreamInfo streamInfo, Object obj);

    void a(h hVar);

    void a(boolean z2);

    void a(StreamInfo[] streamInfoArr);

    void adjustPlaybackSignalVolume(double d3);

    void adjustUserPlaybackSignalVolume(String str, double d3);

    void b();

    void b(int i2);

    void b(int i2, Object obj);

    void b(int i2, boolean z2);

    void b(StreamInfo streamInfo);

    void b(StreamInfo streamInfo, Object obj);

    void b(boolean z2);

    void b(StreamInfo[] streamInfoArr);

    void c();

    void c(int i2, Object obj);

    void c(StreamInfo streamInfo, Object obj);

    void c(boolean z2);

    void cropPushResolution(int i2, int i3);

    void d(boolean z2);

    void e(boolean z2);

    void f(boolean z2);

    void kickOffOthers(int i2, List<String> list);

    void messageNotify(String str);

    void muteRemoteAudio(String str, boolean z2);

    void muteRemoteScreen(String str, boolean z2);

    void muteRemoteScreenSound(String str, boolean z2);

    void muteRemoteVideo(String str, boolean z2);

    void pauseAudioFile();

    void queryMix();

    void resumeAudioFile();

    void setCameraId(int i2);

    void startPlayAudioFile(String str, boolean z2, boolean z3);

    void stopPlayAudioFile();

    void stopRecord();

    void switchCamera();

    void switchCameraSkipSameSide();
}
