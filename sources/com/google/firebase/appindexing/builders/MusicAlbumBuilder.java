package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class MusicAlbumBuilder extends IndexableBuilder<MusicAlbumBuilder> {
    public MusicAlbumBuilder() {
        super("MusicAlbum");
    }

    public final MusicAlbumBuilder setByArtist(@NonNull MusicGroupBuilder musicGroupBuilder) {
        return put("byArtist", musicGroupBuilder);
    }

    public final MusicAlbumBuilder setNumTracks(int i2) {
        return put("numTracks", i2);
    }

    public final MusicAlbumBuilder setTrack(@NonNull MusicRecordingBuilder... musicRecordingBuilderArr) {
        return put("track", musicRecordingBuilderArr);
    }
}
