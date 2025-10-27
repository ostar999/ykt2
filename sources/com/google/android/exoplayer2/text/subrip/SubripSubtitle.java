package com.google.android.exoplayer2.text.subrip;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
final class SubripSubtitle implements Subtitle {
    private final long[] cueTimesUs;
    private final Cue[] cues;

    public SubripSubtitle(Cue[] cueArr, long[] jArr) {
        this.cues = cueArr;
        this.cueTimesUs = jArr;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long j2) {
        Cue cue;
        int iBinarySearchFloor = Util.binarySearchFloor(this.cueTimesUs, j2, true, false);
        return (iBinarySearchFloor == -1 || (cue = this.cues[iBinarySearchFloor]) == Cue.EMPTY) ? Collections.emptyList() : Collections.singletonList(cue);
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public long getEventTime(int i2) {
        Assertions.checkArgument(i2 >= 0);
        Assertions.checkArgument(i2 < this.cueTimesUs.length);
        return this.cueTimesUs[i2];
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getEventTimeCount() {
        return this.cueTimesUs.length;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getNextEventTimeIndex(long j2) {
        int iBinarySearchCeil = Util.binarySearchCeil(this.cueTimesUs, j2, false, false);
        if (iBinarySearchCeil < this.cueTimesUs.length) {
            return iBinarySearchCeil;
        }
        return -1;
    }
}
