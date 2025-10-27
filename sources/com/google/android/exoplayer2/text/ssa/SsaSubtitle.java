package com.google.android.exoplayer2.text.ssa;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
final class SsaSubtitle implements Subtitle {
    private final List<Long> cueTimesUs;
    private final List<List<Cue>> cues;

    public SsaSubtitle(List<List<Cue>> list, List<Long> list2) {
        this.cues = list;
        this.cueTimesUs = list2;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long j2) {
        int iBinarySearchFloor = Util.binarySearchFloor((List<? extends Comparable<? super Long>>) this.cueTimesUs, Long.valueOf(j2), true, false);
        return iBinarySearchFloor == -1 ? Collections.emptyList() : this.cues.get(iBinarySearchFloor);
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public long getEventTime(int i2) {
        Assertions.checkArgument(i2 >= 0);
        Assertions.checkArgument(i2 < this.cueTimesUs.size());
        return this.cueTimesUs.get(i2).longValue();
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getEventTimeCount() {
        return this.cueTimesUs.size();
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getNextEventTimeIndex(long j2) {
        int iBinarySearchCeil = Util.binarySearchCeil((List<? extends Comparable<? super Long>>) this.cueTimesUs, Long.valueOf(j2), false, false);
        if (iBinarySearchCeil < this.cueTimesUs.size()) {
            return iBinarySearchCeil;
        }
        return -1;
    }
}
