package org.apache.commons.compress.archivers.ar;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.utils.ArchiveUtils;

/* loaded from: classes9.dex */
public class ArArchiveOutputStream extends ArchiveOutputStream {
    public static final int LONGFILE_BSD = 1;
    public static final int LONGFILE_ERROR = 0;
    private final OutputStream out;
    private ArArchiveEntry prevEntry;
    private long entryOffset = 0;
    private boolean haveUnclosedEntry = false;
    private int longFileMode = 0;
    private boolean finished = false;

    public ArArchiveOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    private long fill(long j2, long j3, char c3) throws IOException {
        long j4 = j3 - j2;
        if (j4 > 0) {
            for (int i2 = 0; i2 < j4; i2++) {
                write(c3);
            }
        }
        return j3;
    }

    private long write(String str) throws IOException {
        write(str.getBytes("ascii"));
        return r3.length;
    }

    private long writeArchiveHeader() throws IOException {
        this.out.write(ArchiveUtils.toAsciiBytes(ArArchiveEntry.HEADER));
        return r0.length;
    }

    private long writeEntryHeader(ArArchiveEntry arArchiveEntry) throws IOException {
        long jWrite;
        boolean z2;
        String name = arArchiveEntry.getName();
        if (this.longFileMode == 0 && name.length() > 16) {
            throw new IOException("filename too long, > 16 chars: " + name);
        }
        if (1 != this.longFileMode || (name.length() <= 16 && !name.contains(" "))) {
            jWrite = 0 + write(name);
            z2 = false;
        } else {
            z2 = true;
            jWrite = 0 + write("#1/" + String.valueOf(name.length()));
        }
        long jFill = fill(jWrite, 16L, ' ');
        String str = "" + arArchiveEntry.getLastModified();
        if (str.length() > 12) {
            throw new IOException("modified too long");
        }
        long jFill2 = fill(jFill + write(str), 28L, ' ');
        String str2 = "" + arArchiveEntry.getUserId();
        if (str2.length() > 6) {
            throw new IOException("userid too long");
        }
        long jFill3 = fill(jFill2 + write(str2), 34L, ' ');
        String str3 = "" + arArchiveEntry.getGroupId();
        if (str3.length() > 6) {
            throw new IOException("groupid too long");
        }
        long jFill4 = fill(jFill3 + write(str3), 40L, ' ');
        String str4 = "" + Integer.toString(arArchiveEntry.getMode(), 8);
        if (str4.length() > 8) {
            throw new IOException("filemode too long");
        }
        long jFill5 = fill(jFill4 + write(str4), 48L, ' ');
        String strValueOf = String.valueOf(arArchiveEntry.getLength() + (z2 ? name.length() : 0));
        if (strValueOf.length() > 10) {
            throw new IOException("size too long");
        }
        long jFill6 = fill(jFill5 + write(strValueOf), 58L, ' ') + write(ArArchiveEntry.TRAILER);
        return z2 ? jFill6 + write(name) : jFill6;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        this.out.close();
        this.prevEntry = null;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.prevEntry == null || !this.haveUnclosedEntry) {
            throw new IOException("No current entry to close");
        }
        if (this.entryOffset % 2 != 0) {
            this.out.write(10);
        }
        this.haveUnclosedEntry = false;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public ArchiveEntry createArchiveEntry(File file, String str) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new ArArchiveEntry(file, str);
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void finish() throws IOException {
        if (this.haveUnclosedEntry) {
            throw new IOException("This archive contains unclosed entries.");
        }
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        this.finished = true;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        ArArchiveEntry arArchiveEntry = (ArArchiveEntry) archiveEntry;
        ArArchiveEntry arArchiveEntry2 = this.prevEntry;
        if (arArchiveEntry2 == null) {
            writeArchiveHeader();
        } else {
            if (arArchiveEntry2.getLength() != this.entryOffset) {
                throw new IOException("length does not match entry (" + this.prevEntry.getLength() + " != " + this.entryOffset);
            }
            if (this.haveUnclosedEntry) {
                closeArchiveEntry();
            }
        }
        this.prevEntry = arArchiveEntry;
        writeEntryHeader(arArchiveEntry);
        this.entryOffset = 0L;
        this.haveUnclosedEntry = true;
    }

    public void setLongFileMode(int i2) {
        this.longFileMode = i2;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        this.out.write(bArr, i2, i3);
        count(i3);
        this.entryOffset += i3;
    }
}
