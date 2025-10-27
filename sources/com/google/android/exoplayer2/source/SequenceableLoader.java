package com.google.android.exoplayer2.source;

/* loaded from: classes3.dex */
public interface SequenceableLoader {

    public interface Callback<T extends SequenceableLoader> {
        void onContinueLoadingRequested(T t2);
    }

    boolean continueLoading(long j2);

    long getBufferedPositionUs();

    long getNextLoadPositionUs();

    boolean isLoading();

    void reevaluateBuffer(long j2);
}
