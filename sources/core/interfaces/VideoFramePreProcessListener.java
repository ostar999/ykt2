package core.interfaces;

import core.data.WrappedVideoFrame;

/* loaded from: classes8.dex */
public interface VideoFramePreProcessListener {
    void onGLContextCreated();

    void onGLContextDestroy();

    int onPreProcessVideoFrame(WrappedVideoFrame wrappedVideoFrame, WrappedVideoFrame wrappedVideoFrame2);
}
