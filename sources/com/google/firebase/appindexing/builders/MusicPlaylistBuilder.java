package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public class MusicPlaylistBuilder extends IndexableBuilder<MusicPlaylistBuilder> {
    public MusicPlaylistBuilder() {
        super("MusicPlaylist");
    }

    public MusicPlaylistBuilder setNumTracks(int i2) {
        return put("numTracks", i2);
    }

    public MusicPlaylistBuilder setTrack(@NonNull MusicRecordingBuilder... musicRecordingBuilderArr) {
        return put("track", musicRecordingBuilderArr);
    }
}
