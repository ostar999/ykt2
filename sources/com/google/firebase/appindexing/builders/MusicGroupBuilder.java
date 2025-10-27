package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class MusicGroupBuilder extends IndexableBuilder<MusicGroupBuilder> {
    public MusicGroupBuilder() {
        super("MusicGroup");
    }

    public final MusicGroupBuilder setAlbum(@NonNull MusicAlbumBuilder... musicAlbumBuilderArr) {
        return put("album", musicAlbumBuilderArr);
    }

    public final MusicGroupBuilder setGenre(@NonNull String str) {
        return put("genre", str);
    }

    public final MusicGroupBuilder setTrack(@NonNull MusicRecordingBuilder... musicRecordingBuilderArr) {
        return put("track", musicRecordingBuilderArr);
    }
}
