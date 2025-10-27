package com.google.android.exoplayer2.text;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.DecoderOutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class SubtitleOutputBuffer extends DecoderOutputBuffer implements Subtitle {
    private long subsampleOffsetUs;

    @Nullable
    private Subtitle subtitle;

    @Override // com.google.android.exoplayer2.decoder.Buffer
    public void clear() {
        super.clear();
        this.subtitle = null;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long j2) {
        return ((Subtitle) Assertions.checkNotNull(this.subtitle)).getCues(j2 - this.subsampleOffsetUs);
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public long getEventTime(int i2) {
        return ((Subtitle) Assertions.checkNotNull(this.subtitle)).getEventTime(i2) + this.subsampleOffsetUs;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getEventTimeCount() {
        return ((Subtitle) Assertions.checkNotNull(this.subtitle)).getEventTimeCount();
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getNextEventTimeIndex(long j2) {
        return ((Subtitle) Assertions.checkNotNull(this.subtitle)).getNextEventTimeIndex(j2 - this.subsampleOffsetUs);
    }

    public void setContent(long j2, Subtitle subtitle, long j3) {
        this.timeUs = j2;
        this.subtitle = subtitle;
        if (j3 != Long.MAX_VALUE) {
            j2 = j3;
        }
        this.subsampleOffsetUs = j2;
    }
}
