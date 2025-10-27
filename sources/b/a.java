package b;

import core.data.AuthInfo;
import core.data.MixProfile;
import core.data.StreamInfo;
import core.interfaces.CameraEventListener;
import core.interfaces.FirstFrameRendered;
import core.interfaces.ScreenShot;
import core.interfaces.VideoFramePreProcessListener;
import d.a;
import java.util.List;
import org.wrtca.customize.RtcNativeOperation;

/* loaded from: classes.dex */
public interface a {

    /* renamed from: a, reason: collision with root package name */
    public static final int f1902a = 1000;

    int a(int i2);

    int a(int i2, Object obj);

    int a(int i2, boolean z2, boolean z3);

    int a(AuthInfo authInfo);

    int a(StreamInfo streamInfo);

    int a(StreamInfo streamInfo, Object obj);

    int a(StreamInfo streamInfo, Object obj, int i2, FirstFrameRendered firstFrameRendered);

    int a(a.w wVar);

    int a(Object obj, int i2, FirstFrameRendered firstFrameRendered);

    int a(boolean z2, int i2);

    void a(MixProfile mixProfile);

    void a(CameraEventListener cameraEventListener);

    void a(VideoFramePreProcessListener videoFramePreProcessListener);

    void a(boolean z2, StreamInfo streamInfo, int i2);

    void a(boolean z2, StreamInfo streamInfo, ScreenShot screenShot);

    void a(StreamInfo[] streamInfoArr);

    int adjustPlaybackSignalVolume(double d3);

    void adjustRecordVolume(int i2);

    int adjustUserPlaybackSignalVolume(String str, double d3);

    int b(int i2);

    int b(StreamInfo streamInfo);

    int b(StreamInfo streamInfo, Object obj, int i2, FirstFrameRendered firstFrameRendered);

    int b(boolean z2, int i2);

    void b(b bVar);

    void b(MixProfile mixProfile);

    void b(StreamInfo[] streamInfoArr);

    int c(int i2);

    int c(StreamInfo streamInfo);

    void c();

    void c(MixProfile mixProfile);

    int configLocalAudioPublish(boolean z2);

    int configLocalCameraPublish(boolean z2);

    int configLocalScreenPublish(boolean z2);

    void controlAudio(boolean z2);

    void controlAudioPlayOut(boolean z2);

    void controlAudioRecord(boolean z2);

    void controlLocalVideo(boolean z2);

    String copyAssetsFileToSdcard(String str);

    int cropPushResolution(int i2, int i3);

    int d(int i2);

    int e(int i2);

    void f(int i2);

    int g(int i2);

    int getDefaultAudioDevice();

    RtcNativeOperation getNativeOpInterface();

    boolean getSpeakerOn();

    int i(int i2);

    boolean isAudioOnlyMode();

    boolean isAutoPublish();

    boolean isAutoSubscribe();

    boolean isLocalAudioPublishEnabled();

    boolean isLocalCameraPublishEnabled();

    boolean isLocalScreenPublishEnabled();

    void j(int i2);

    void kickOffOthers(int i2, List<String> list);

    int leaveChannel();

    int leaveChannelNonStopLocalPreview();

    void lockExtendDeviceInputBuffer();

    void messageNotify(String str);

    int muteLocalMic(boolean z2);

    int muteRemoteAudio(String str, boolean z2);

    int muteRemoteScreen(String str, boolean z2);

    int muteRemoteScreenSound(String str, boolean z2);

    int muteRemoteVideo(String str, boolean z2);

    void pauseAudioFile();

    void queryMix();

    void releaseExtendDeviceInputBuffer();

    void resumeAudioFile();

    int setAudioOnlyMode(boolean z2);

    int setAutoPublish(boolean z2);

    int setAutoSubscribe(boolean z2);

    int setCameraId(int i2);

    int setFlashOn(boolean z2);

    void setSpeakerOn(boolean z2);

    boolean startPlayAudioFile(String str);

    boolean startPlayAudioFile(String str, boolean z2, boolean z3);

    void stopPlayAudioFile();

    void stopRecord();

    void stopRelay(String[] strArr);

    int switchCamera();

    int switchCameraSkipSameSide();
}
