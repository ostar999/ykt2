package com.google.android.exoplayer2.source.rtsp;

/* loaded from: classes3.dex */
final class RtspDescribeResponse {
    public final SessionDescription sessionDescription;
    public final int status;

    public RtspDescribeResponse(int i2, SessionDescription sessionDescription) {
        this.status = i2;
        this.sessionDescription = sessionDescription;
    }
}
