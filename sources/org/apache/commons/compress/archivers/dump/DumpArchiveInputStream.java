package org.apache.commons.compress.archivers.dump;

import cn.hutool.core.text.StrPool;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.dump.DumpArchiveConstants;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

/* loaded from: classes9.dex */
public class DumpArchiveInputStream extends ArchiveInputStream {
    private DumpArchiveEntry active;
    private byte[] blockBuffer;
    final String encoding;
    private long entryOffset;
    private long entrySize;
    private long filepos;
    private boolean hasHitEOF;
    private boolean isClosed;
    private final Map<Integer, Dirent> names;
    private final Map<Integer, DumpArchiveEntry> pending;
    private Queue<DumpArchiveEntry> queue;
    protected TapeInputStream raw;
    private final byte[] readBuf;
    private int readIdx;
    private int recordOffset;
    private DumpArchiveSummary summary;
    private final ZipEncoding zipEncoding;

    public DumpArchiveInputStream(InputStream inputStream) throws ArchiveException {
        this(inputStream, null);
    }

    private String getPath(DumpArchiveEntry dumpArchiveEntry) {
        Stack stack = new Stack();
        int ino = dumpArchiveEntry.getIno();
        while (true) {
            if (!this.names.containsKey(Integer.valueOf(ino))) {
                stack.clear();
                break;
            }
            Dirent dirent = this.names.get(Integer.valueOf(ino));
            stack.push(dirent.getName());
            if (dirent.getIno() == dirent.getParentIno()) {
                break;
            }
            ino = dirent.getParentIno();
        }
        if (stack.isEmpty()) {
            this.pending.put(Integer.valueOf(dumpArchiveEntry.getIno()), dumpArchiveEntry);
            return null;
        }
        StringBuilder sb = new StringBuilder((String) stack.pop());
        while (!stack.isEmpty()) {
            sb.append('/');
            sb.append((String) stack.pop());
        }
        return sb.toString();
    }

    public static boolean matches(byte[] bArr, int i2) {
        if (i2 < 32) {
            return false;
        }
        return i2 >= 1024 ? DumpArchiveUtil.verify(bArr) : 60012 == DumpArchiveUtil.convert32(bArr, 24);
    }

    private void readBITS() throws IOException {
        byte[] record = this.raw.readRecord();
        if (!DumpArchiveUtil.verify(record)) {
            throw new InvalidFormatException();
        }
        DumpArchiveEntry dumpArchiveEntry = DumpArchiveEntry.parse(record);
        this.active = dumpArchiveEntry;
        if (DumpArchiveConstants.SEGMENT_TYPE.BITS != dumpArchiveEntry.getHeaderType()) {
            throw new InvalidFormatException();
        }
        if (this.raw.skip(this.active.getHeaderCount() * 1024) == -1) {
            throw new EOFException();
        }
        this.readIdx = this.active.getHeaderCount();
    }

    private void readCLRI() throws IOException {
        byte[] record = this.raw.readRecord();
        if (!DumpArchiveUtil.verify(record)) {
            throw new InvalidFormatException();
        }
        DumpArchiveEntry dumpArchiveEntry = DumpArchiveEntry.parse(record);
        this.active = dumpArchiveEntry;
        if (DumpArchiveConstants.SEGMENT_TYPE.CLRI != dumpArchiveEntry.getHeaderType()) {
            throw new InvalidFormatException();
        }
        if (this.raw.skip(this.active.getHeaderCount() * 1024) == -1) {
            throw new EOFException();
        }
        this.readIdx = this.active.getHeaderCount();
    }

    private void readDirectoryEntry(DumpArchiveEntry dumpArchiveEntry) throws IOException {
        long entrySize = dumpArchiveEntry.getEntrySize();
        boolean z2 = true;
        while (true) {
            if (!z2 && DumpArchiveConstants.SEGMENT_TYPE.ADDR != dumpArchiveEntry.getHeaderType()) {
                return;
            }
            if (!z2) {
                this.raw.readRecord();
            }
            if (!this.names.containsKey(Integer.valueOf(dumpArchiveEntry.getIno())) && DumpArchiveConstants.SEGMENT_TYPE.INODE == dumpArchiveEntry.getHeaderType()) {
                this.pending.put(Integer.valueOf(dumpArchiveEntry.getIno()), dumpArchiveEntry);
            }
            int headerCount = dumpArchiveEntry.getHeaderCount() * 1024;
            if (this.blockBuffer.length < headerCount) {
                this.blockBuffer = new byte[headerCount];
            }
            if (this.raw.read(this.blockBuffer, 0, headerCount) != headerCount) {
                throw new EOFException();
            }
            int i2 = 0;
            while (i2 < headerCount - 8 && i2 < entrySize - 8) {
                int iConvert32 = DumpArchiveUtil.convert32(this.blockBuffer, i2);
                int iConvert16 = DumpArchiveUtil.convert16(this.blockBuffer, i2 + 4);
                byte[] bArr = this.blockBuffer;
                byte b3 = bArr[i2 + 6];
                String strDecode = DumpArchiveUtil.decode(this.zipEncoding, bArr, i2 + 8, bArr[i2 + 7]);
                if (!StrPool.DOT.equals(strDecode) && !StrPool.DOUBLE_DOT.equals(strDecode)) {
                    this.names.put(Integer.valueOf(iConvert32), new Dirent(iConvert32, dumpArchiveEntry.getIno(), b3, strDecode));
                    for (Map.Entry<Integer, DumpArchiveEntry> entry : this.pending.entrySet()) {
                        String path = getPath(entry.getValue());
                        if (path != null) {
                            entry.getValue().setName(path);
                            entry.getValue().setSimpleName(this.names.get(entry.getKey()).getName());
                            this.queue.add(entry.getValue());
                        }
                    }
                    Iterator<DumpArchiveEntry> it = this.queue.iterator();
                    while (it.hasNext()) {
                        this.pending.remove(Integer.valueOf(it.next().getIno()));
                    }
                }
                i2 += iConvert16;
            }
            byte[] bArrPeek = this.raw.peek();
            if (!DumpArchiveUtil.verify(bArrPeek)) {
                throw new InvalidFormatException();
            }
            dumpArchiveEntry = DumpArchiveEntry.parse(bArrPeek);
            entrySize -= 1024;
            z2 = false;
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.isClosed) {
            return;
        }
        this.isClosed = true;
        this.raw.close();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public long getBytesRead() {
        return this.raw.getBytesRead();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    @Deprecated
    public int getCount() {
        return (int) getBytesRead();
    }

    public DumpArchiveEntry getNextDumpEntry() throws IOException {
        return getNextEntry();
    }

    public DumpArchiveSummary getSummary() {
        return this.summary;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (this.hasHitEOF || this.isClosed) {
            return -1;
        }
        long j2 = this.entryOffset;
        long j3 = this.entrySize;
        if (j2 >= j3) {
            return -1;
        }
        if (this.active == null) {
            throw new IllegalStateException("No current dump entry");
        }
        if (i3 + j2 > j3) {
            i3 = (int) (j3 - j2);
        }
        int i4 = 0;
        while (i3 > 0) {
            byte[] bArr2 = this.readBuf;
            int length = bArr2.length;
            int i5 = this.recordOffset;
            int length2 = i3 > length - i5 ? bArr2.length - i5 : i3;
            if (i5 + length2 <= bArr2.length) {
                System.arraycopy(bArr2, i5, bArr, i2, length2);
                i4 += length2;
                this.recordOffset += length2;
                i3 -= length2;
                i2 += length2;
            }
            if (i3 > 0) {
                if (this.readIdx >= 512) {
                    byte[] record = this.raw.readRecord();
                    if (!DumpArchiveUtil.verify(record)) {
                        throw new InvalidFormatException();
                    }
                    this.active = DumpArchiveEntry.parse(record);
                    this.readIdx = 0;
                }
                DumpArchiveEntry dumpArchiveEntry = this.active;
                int i6 = this.readIdx;
                this.readIdx = i6 + 1;
                if (dumpArchiveEntry.isSparseRecord(i6)) {
                    Arrays.fill(this.readBuf, (byte) 0);
                } else {
                    TapeInputStream tapeInputStream = this.raw;
                    byte[] bArr3 = this.readBuf;
                    if (tapeInputStream.read(bArr3, 0, bArr3.length) != this.readBuf.length) {
                        throw new EOFException();
                    }
                }
                this.recordOffset = 0;
            }
        }
        this.entryOffset += i4;
        return i4;
    }

    public DumpArchiveInputStream(InputStream inputStream, String str) throws ArchiveException, UnrecognizedFormatException {
        this.readBuf = new byte[1024];
        HashMap map = new HashMap();
        this.names = map;
        this.pending = new HashMap();
        this.raw = new TapeInputStream(inputStream);
        this.hasHitEOF = false;
        this.encoding = str;
        ZipEncoding zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        this.zipEncoding = zipEncoding;
        try {
            byte[] record = this.raw.readRecord();
            if (!DumpArchiveUtil.verify(record)) {
                throw new UnrecognizedFormatException();
            }
            DumpArchiveSummary dumpArchiveSummary = new DumpArchiveSummary(record, zipEncoding);
            this.summary = dumpArchiveSummary;
            this.raw.resetBlockSize(dumpArchiveSummary.getNTRec(), this.summary.isCompressed());
            this.blockBuffer = new byte[4096];
            readCLRI();
            readBITS();
            map.put(2, new Dirent(2, 2, 4, StrPool.DOT));
            this.queue = new PriorityQueue(10, new Comparator<DumpArchiveEntry>() { // from class: org.apache.commons.compress.archivers.dump.DumpArchiveInputStream.1
                @Override // java.util.Comparator
                public int compare(DumpArchiveEntry dumpArchiveEntry, DumpArchiveEntry dumpArchiveEntry2) {
                    if (dumpArchiveEntry.getOriginalName() == null || dumpArchiveEntry2.getOriginalName() == null) {
                        return Integer.MAX_VALUE;
                    }
                    return dumpArchiveEntry.getOriginalName().compareTo(dumpArchiveEntry2.getOriginalName());
                }
            });
        } catch (IOException e2) {
            throw new ArchiveException(e2.getMessage(), e2);
        }
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public DumpArchiveEntry getNextEntry() throws IOException {
        if (!this.queue.isEmpty()) {
            return this.queue.remove();
        }
        DumpArchiveEntry dumpArchiveEntry = null;
        String str = null;
        while (dumpArchiveEntry == null) {
            if (this.hasHitEOF) {
                return null;
            }
            while (this.readIdx < this.active.getHeaderCount()) {
                DumpArchiveEntry dumpArchiveEntry2 = this.active;
                int i2 = this.readIdx;
                this.readIdx = i2 + 1;
                if (!dumpArchiveEntry2.isSparseRecord(i2) && this.raw.skip(1024L) == -1) {
                    throw new EOFException();
                }
            }
            this.readIdx = 0;
            this.filepos = this.raw.getBytesRead();
            byte[] record = this.raw.readRecord();
            if (!DumpArchiveUtil.verify(record)) {
                throw new InvalidFormatException();
            }
            this.active = DumpArchiveEntry.parse(record);
            while (DumpArchiveConstants.SEGMENT_TYPE.ADDR == this.active.getHeaderType()) {
                if (this.raw.skip((this.active.getHeaderCount() - this.active.getHeaderHoles()) * 1024) == -1) {
                    throw new EOFException();
                }
                this.filepos = this.raw.getBytesRead();
                byte[] record2 = this.raw.readRecord();
                if (!DumpArchiveUtil.verify(record2)) {
                    throw new InvalidFormatException();
                }
                this.active = DumpArchiveEntry.parse(record2);
            }
            if (DumpArchiveConstants.SEGMENT_TYPE.END == this.active.getHeaderType()) {
                this.hasHitEOF = true;
                return null;
            }
            DumpArchiveEntry dumpArchiveEntry3 = this.active;
            if (dumpArchiveEntry3.isDirectory()) {
                readDirectoryEntry(this.active);
                this.entryOffset = 0L;
                this.entrySize = 0L;
                this.readIdx = this.active.getHeaderCount();
            } else {
                this.entryOffset = 0L;
                this.entrySize = this.active.getEntrySize();
                this.readIdx = 0;
            }
            this.recordOffset = this.readBuf.length;
            String path = getPath(dumpArchiveEntry3);
            if (path == null) {
                dumpArchiveEntry3 = null;
            }
            DumpArchiveEntry dumpArchiveEntry4 = dumpArchiveEntry3;
            str = path;
            dumpArchiveEntry = dumpArchiveEntry4;
        }
        dumpArchiveEntry.setName(str);
        dumpArchiveEntry.setSimpleName(this.names.get(Integer.valueOf(dumpArchiveEntry.getIno())).getName());
        dumpArchiveEntry.setOffset(this.filepos);
        return dumpArchiveEntry;
    }
}
