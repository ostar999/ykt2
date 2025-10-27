package com.google.android.exoplayer2.transformer;

import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.MediaClock;

@RequiresApi(18)
/* loaded from: classes3.dex */
abstract class TransformerBaseRenderer extends BaseRenderer {
    protected boolean isRendererStarted;
    protected final TransformerMediaClock mediaClock;
    protected final MuxerWrapper muxerWrapper;
    protected long streamOffsetUs;
    protected final Transformation transformation;

    public TransformerBaseRenderer(int i2, MuxerWrapper muxerWrapper, TransformerMediaClock transformerMediaClock, Transformation transformation) {
        super(i2);
        this.muxerWrapper = muxerWrapper;
        this.mediaClock = transformerMediaClock;
        this.transformation = transformation;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.Renderer
    public final MediaClock getMediaClock() {
        return this.mediaClock;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final boolean isReady() {
        return isSourceReady();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onEnabled(boolean z2, boolean z3) {
        this.muxerWrapper.registerTrack();
        this.mediaClock.updateTimeForTrackType(getTrackType(), 0L);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStarted() throws ExoPlaybackException {
        this.isRendererStarted = true;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public final void onStopped() {
        this.isRendererStarted = false;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStreamChanged(Format[] formatArr, long j2, long j3) {
        this.streamOffsetUs = j3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x003b, code lost:
    
        if (r0.supportsSampleMimeType(r3) != false) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0027  */
    @Override // com.google.android.exoplayer2.RendererCapabilities
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int supportsFormat(com.google.android.exoplayer2.Format r3) {
        /*
            r2 = this;
            java.lang.String r3 = r3.sampleMimeType
            int r0 = com.google.android.exoplayer2.util.MimeTypes.getTrackType(r3)
            int r1 = r2.getTrackType()
            if (r0 == r1) goto L12
            r3 = 0
            int r3 = com.google.android.exoplayer2.b2.a(r3)
            return r3
        L12:
            boolean r0 = com.google.android.exoplayer2.util.MimeTypes.isAudio(r3)
            if (r0 == 0) goto L27
            com.google.android.exoplayer2.transformer.MuxerWrapper r0 = r2.muxerWrapper
            com.google.android.exoplayer2.transformer.Transformation r1 = r2.transformation
            java.lang.String r1 = r1.audioMimeType
            if (r1 != 0) goto L21
            r1 = r3
        L21:
            boolean r0 = r0.supportsSampleMimeType(r1)
            if (r0 != 0) goto L3d
        L27:
            boolean r0 = com.google.android.exoplayer2.util.MimeTypes.isVideo(r3)
            if (r0 == 0) goto L43
            com.google.android.exoplayer2.transformer.MuxerWrapper r0 = r2.muxerWrapper
            com.google.android.exoplayer2.transformer.Transformation r1 = r2.transformation
            java.lang.String r1 = r1.videoMimeType
            if (r1 != 0) goto L36
            goto L37
        L36:
            r3 = r1
        L37:
            boolean r3 = r0.supportsSampleMimeType(r3)
            if (r3 == 0) goto L43
        L3d:
            r3 = 4
            int r3 = com.google.android.exoplayer2.b2.a(r3)
            return r3
        L43:
            r3 = 1
            int r3 = com.google.android.exoplayer2.b2.a(r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.transformer.TransformerBaseRenderer.supportsFormat(com.google.android.exoplayer2.Format):int");
    }
}
