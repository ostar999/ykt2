package com.google.android.exoplayer2.extractor;

import java.io.IOException;

/* loaded from: classes3.dex */
public class ForwardingExtractorInput implements ExtractorInput {
    private final ExtractorInput input;

    public ForwardingExtractorInput(ExtractorInput extractorInput) {
        this.input = extractorInput;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean advancePeekPosition(int i2, boolean z2) throws IOException {
        return this.input.advancePeekPosition(i2, z2);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public long getLength() {
        return this.input.getLength();
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public long getPeekPosition() {
        return this.input.getPeekPosition();
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public long getPosition() {
        return this.input.getPosition();
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public int peek(byte[] bArr, int i2, int i3) throws IOException {
        return this.input.peek(bArr, i2, i3);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean peekFully(byte[] bArr, int i2, int i3, boolean z2) throws IOException {
        return this.input.peekFully(bArr, i2, i3, z2);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        return this.input.read(bArr, i2, i3);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean readFully(byte[] bArr, int i2, int i3, boolean z2) throws IOException {
        return this.input.readFully(bArr, i2, i3, z2);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void resetPeekPosition() {
        this.input.resetPeekPosition();
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public <E extends Throwable> void setRetryPosition(long j2, E e2) throws Throwable {
        this.input.setRetryPosition(j2, e2);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public int skip(int i2) throws IOException {
        return this.input.skip(i2);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean skipFully(int i2, boolean z2) throws IOException {
        return this.input.skipFully(i2, z2);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void advancePeekPosition(int i2) throws IOException {
        this.input.advancePeekPosition(i2);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void peekFully(byte[] bArr, int i2, int i3) throws IOException {
        this.input.peekFully(bArr, i2, i3);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void readFully(byte[] bArr, int i2, int i3) throws IOException {
        this.input.readFully(bArr, i2, i3);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void skipFully(int i2) throws IOException {
        this.input.skipFully(i2);
    }
}
