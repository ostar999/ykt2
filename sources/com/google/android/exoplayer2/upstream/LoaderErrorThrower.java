package com.google.android.exoplayer2.upstream;

import java.io.IOException;

/* loaded from: classes3.dex */
public interface LoaderErrorThrower {

    public static final class Dummy implements LoaderErrorThrower {
        @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
        public void maybeThrowError() {
        }

        @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
        public void maybeThrowError(int i2) {
        }
    }

    void maybeThrowError() throws IOException;

    void maybeThrowError(int i2) throws IOException;
}
