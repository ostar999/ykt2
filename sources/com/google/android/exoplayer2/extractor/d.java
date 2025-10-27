package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* loaded from: classes3.dex */
public final /* synthetic */ class d {
    public static int a(TrackOutput trackOutput, DataReader dataReader, int i2, boolean z2) throws IOException {
        return trackOutput.sampleData(dataReader, i2, z2, 0);
    }

    public static void b(TrackOutput trackOutput, ParsableByteArray parsableByteArray, int i2) {
        trackOutput.sampleData(parsableByteArray, i2, 0);
    }
}
