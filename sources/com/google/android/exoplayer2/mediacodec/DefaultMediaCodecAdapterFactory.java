package com.google.android.exoplayer2.mediacodec;

import com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class DefaultMediaCodecAdapterFactory implements MediaCodecAdapter.Factory {
    private static final int MODE_DEFAULT = 0;
    private static final int MODE_DISABLED = 2;
    private static final int MODE_ENABLED = 1;
    private static final String TAG = "DefaultMediaCodecAdapterFactory";
    private int asynchronousMode = 0;
    private boolean enableImmediateCodecStartAfterFlush = true;
    private boolean enableSynchronizeCodecInteractionsWithQueueing;

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Factory
    public MediaCodecAdapter createAdapter(MediaCodecAdapter.Configuration configuration) throws IOException {
        int i2 = this.asynchronousMode;
        if ((i2 != 1 || Util.SDK_INT < 23) && (i2 != 0 || Util.SDK_INT < 31)) {
            return new SynchronousMediaCodecAdapter.Factory().createAdapter(configuration);
        }
        int trackType = MimeTypes.getTrackType(configuration.format.sampleMimeType);
        String strValueOf = String.valueOf(Util.getTrackTypeString(trackType));
        Log.i(TAG, strValueOf.length() != 0 ? "Creating an asynchronous MediaCodec adapter for track type ".concat(strValueOf) : new String("Creating an asynchronous MediaCodec adapter for track type "));
        return new AsynchronousMediaCodecAdapter.Factory(trackType, this.enableSynchronizeCodecInteractionsWithQueueing, this.enableImmediateCodecStartAfterFlush).createAdapter(configuration);
    }

    public void experimentalSetImmediateCodecStartAfterFlushEnabled(boolean z2) {
        this.enableImmediateCodecStartAfterFlush = z2;
    }

    public void experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(boolean z2) {
        this.enableSynchronizeCodecInteractionsWithQueueing = z2;
    }

    public DefaultMediaCodecAdapterFactory forceDisableAsynchronous() {
        this.asynchronousMode = 2;
        return this;
    }

    public DefaultMediaCodecAdapterFactory forceEnableAsynchronous() {
        this.asynchronousMode = 1;
        return this;
    }
}
