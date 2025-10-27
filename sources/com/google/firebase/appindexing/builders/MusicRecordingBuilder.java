package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class MusicRecordingBuilder extends IndexableBuilder<MusicRecordingBuilder> {
    public MusicRecordingBuilder() {
        super("MusicRecording");
    }

    public final MusicRecordingBuilder setByArtist(@NonNull MusicGroupBuilder musicGroupBuilder) {
        return put("byArtist", musicGroupBuilder);
    }

    public final MusicRecordingBuilder setDuration(int i2) {
        return put("duration", i2);
    }

    public final MusicRecordingBuilder setInAlbum(@NonNull MusicAlbumBuilder musicAlbumBuilder) {
        return put("inAlbum", musicAlbumBuilder);
    }

    public final MusicRecordingBuilder setInPlaylist(@NonNull MusicPlaylistBuilder... musicPlaylistBuilderArr) {
        return put("inPlaylist", musicPlaylistBuilderArr);
    }
}
