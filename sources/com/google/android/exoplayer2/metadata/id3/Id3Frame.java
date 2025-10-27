package com.google.android.exoplayer2.metadata.id3;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.metadata.Metadata;

/* loaded from: classes3.dex */
public abstract class Id3Frame implements Metadata.Entry {
    public final String id;

    public Id3Frame(String str) {
        this.id = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.google.android.exoplayer2.metadata.Metadata.Entry
    public /* synthetic */ byte[] getWrappedMetadataBytes() {
        return com.google.android.exoplayer2.metadata.a.a(this);
    }

    @Override // com.google.android.exoplayer2.metadata.Metadata.Entry
    public /* synthetic */ Format getWrappedMetadataFormat() {
        return com.google.android.exoplayer2.metadata.a.b(this);
    }

    @Override // com.google.android.exoplayer2.metadata.Metadata.Entry
    public /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        com.google.android.exoplayer2.metadata.a.c(this, builder);
    }

    public String toString() {
        return this.id;
    }
}
