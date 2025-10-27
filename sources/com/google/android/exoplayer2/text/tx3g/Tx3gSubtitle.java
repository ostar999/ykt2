package com.google.android.exoplayer2.text.tx3g;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
final class Tx3gSubtitle implements Subtitle {
    public static final Tx3gSubtitle EMPTY = new Tx3gSubtitle();
    private final List<Cue> cues;

    public Tx3gSubtitle(Cue cue) {
        this.cues = Collections.singletonList(cue);
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long j2) {
        return j2 >= 0 ? this.cues : Collections.emptyList();
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public long getEventTime(int i2) {
        Assertions.checkArgument(i2 == 0);
        return 0L;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getEventTimeCount() {
        return 1;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getNextEventTimeIndex(long j2) {
        return j2 < 0 ? 0 : -1;
    }

    private Tx3gSubtitle() {
        this.cues = Collections.emptyList();
    }
}
