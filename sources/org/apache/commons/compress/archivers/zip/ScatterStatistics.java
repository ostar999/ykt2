package org.apache.commons.compress.archivers.zip;

/* loaded from: classes9.dex */
public class ScatterStatistics {
    private final long compressionElapsed;
    private final long mergingElapsed;

    public ScatterStatistics(long j2, long j3) {
        this.compressionElapsed = j2;
        this.mergingElapsed = j3;
    }

    public long getCompressionElapsed() {
        return this.compressionElapsed;
    }

    public long getMergingElapsed() {
        return this.mergingElapsed;
    }

    public String toString() {
        return "compressionElapsed=" + this.compressionElapsed + "ms, mergingElapsed=" + this.mergingElapsed + "ms";
    }
}
