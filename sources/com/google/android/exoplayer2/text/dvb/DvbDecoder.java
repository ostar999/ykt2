package com.google.android.exoplayer2.text.dvb;

import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.List;

/* loaded from: classes3.dex */
public final class DvbDecoder extends SimpleSubtitleDecoder {
    private final DvbParser parser;

    public DvbDecoder(List<byte[]> list) {
        super("DvbDecoder");
        ParsableByteArray parsableByteArray = new ParsableByteArray(list.get(0));
        this.parser = new DvbParser(parsableByteArray.readUnsignedShort(), parsableByteArray.readUnsignedShort());
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    public Subtitle decode(byte[] bArr, int i2, boolean z2) {
        if (z2) {
            this.parser.reset();
        }
        return new DvbSubtitle(this.parser.decode(bArr, i2));
    }
}
