package com.google.android.exoplayer2.transformer;

import android.os.ParcelFileDescriptor;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
interface Muxer {

    public interface Factory {
        Muxer create(ParcelFileDescriptor parcelFileDescriptor, String str) throws IOException;

        Muxer create(String str, String str2) throws IOException;

        boolean supportsOutputMimeType(String str);

        boolean supportsSampleMimeType(@Nullable String str, String str2);
    }

    int addTrack(Format format);

    void release(boolean z2);

    void writeSampleData(int i2, ByteBuffer byteBuffer, boolean z2, long j2);
}
