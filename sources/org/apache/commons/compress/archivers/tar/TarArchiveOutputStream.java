package org.apache.commons.compress.archivers.tar;

import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.common.base.Ascii;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.CountingOutputStream;
import org.apache.http.protocol.HTTP;

/* loaded from: classes9.dex */
public class TarArchiveOutputStream extends ArchiveOutputStream {
    private static final ZipEncoding ASCII = ZipEncodingHelper.getZipEncoding(HTTP.ASCII);
    public static final int BIGNUMBER_ERROR = 0;
    public static final int BIGNUMBER_POSIX = 2;
    public static final int BIGNUMBER_STAR = 1;
    public static final int LONGFILE_ERROR = 0;
    public static final int LONGFILE_GNU = 2;
    public static final int LONGFILE_POSIX = 3;
    public static final int LONGFILE_TRUNCATE = 1;
    private boolean addPaxHeadersForNonAsciiNames;
    private final byte[] assemBuf;
    private int assemLen;
    private int bigNumberMode;
    private boolean closed;
    private long currBytes;
    private String currName;
    private long currSize;
    final String encoding;
    private boolean finished;
    private boolean haveUnclosedEntry;
    private int longFileMode;
    private final OutputStream out;
    private final byte[] recordBuf;
    private final int recordSize;
    private final int recordsPerBlock;
    private int recordsWritten;
    private final ZipEncoding zipEncoding;

    public TarArchiveOutputStream(OutputStream outputStream) {
        this(outputStream, 10240, 512);
    }

    private void addPaxHeaderForBigNumber(Map<String, String> map, String str, long j2, long j3) {
        if (j2 < 0 || j2 > j3) {
            map.put(str, String.valueOf(j2));
        }
    }

    private void addPaxHeadersForBigNumbers(Map<String, String> map, TarArchiveEntry tarArchiveEntry) {
        addPaxHeaderForBigNumber(map, DatabaseManager.SIZE, tarArchiveEntry.getSize(), TarConstants.MAXSIZE);
        addPaxHeaderForBigNumber(map, "gid", tarArchiveEntry.getLongGroupId(), TarConstants.MAXID);
        addPaxHeaderForBigNumber(map, "mtime", tarArchiveEntry.getModTime().getTime() / 1000, TarConstants.MAXSIZE);
        addPaxHeaderForBigNumber(map, "uid", tarArchiveEntry.getLongUserId(), TarConstants.MAXID);
        addPaxHeaderForBigNumber(map, "SCHILY.devmajor", tarArchiveEntry.getDevMajor(), TarConstants.MAXID);
        addPaxHeaderForBigNumber(map, "SCHILY.devminor", tarArchiveEntry.getDevMinor(), TarConstants.MAXID);
        failForBigNumber("mode", tarArchiveEntry.getMode(), TarConstants.MAXID);
    }

    private void failForBigNumber(String str, long j2, long j3) {
        failForBigNumber(str, j2, j3, "");
    }

    private void failForBigNumberWithPosixMessage(String str, long j2, long j3) {
        failForBigNumber(str, j2, j3, " Use STAR or POSIX extensions to overcome this limit");
    }

    private void failForBigNumbers(TarArchiveEntry tarArchiveEntry) {
        failForBigNumber("entry size", tarArchiveEntry.getSize(), TarConstants.MAXSIZE);
        failForBigNumberWithPosixMessage("group id", tarArchiveEntry.getLongGroupId(), TarConstants.MAXID);
        failForBigNumber("last modification time", tarArchiveEntry.getModTime().getTime() / 1000, TarConstants.MAXSIZE);
        failForBigNumber("user id", tarArchiveEntry.getLongUserId(), TarConstants.MAXID);
        failForBigNumber("mode", tarArchiveEntry.getMode(), TarConstants.MAXID);
        failForBigNumber("major device number", tarArchiveEntry.getDevMajor(), TarConstants.MAXID);
        failForBigNumber("minor device number", tarArchiveEntry.getDevMinor(), TarConstants.MAXID);
    }

    private boolean handleLongName(TarArchiveEntry tarArchiveEntry, String str, Map<String, String> map, String str2, byte b3, String str3) throws IOException {
        ByteBuffer byteBufferEncode = this.zipEncoding.encode(str);
        int iLimit = byteBufferEncode.limit() - byteBufferEncode.position();
        if (iLimit >= 100) {
            int i2 = this.longFileMode;
            if (i2 == 3) {
                map.put(str2, str);
                return true;
            }
            if (i2 == 2) {
                TarArchiveEntry tarArchiveEntry2 = new TarArchiveEntry(TarConstants.GNU_LONGLINK, b3);
                tarArchiveEntry2.setSize(iLimit + 1);
                transferModTime(tarArchiveEntry, tarArchiveEntry2);
                putArchiveEntry(tarArchiveEntry2);
                write(byteBufferEncode.array(), byteBufferEncode.arrayOffset(), iLimit);
                write(0);
                closeArchiveEntry();
            } else if (i2 != 1) {
                throw new RuntimeException(str3 + " '" + str + "' is too long ( > 100 bytes)");
            }
        }
        return false;
    }

    private void padAsNeeded() throws IOException {
        int i2 = this.recordsWritten % this.recordsPerBlock;
        if (i2 != 0) {
            while (i2 < this.recordsPerBlock) {
                writeEOFRecord();
                i2++;
            }
        }
    }

    private boolean shouldBeReplaced(char c3) {
        return c3 == 0 || c3 == '/' || c3 == '\\';
    }

    private String stripTo7Bits(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = (char) (str.charAt(i2) & Ascii.MAX);
            if (shouldBeReplaced(cCharAt)) {
                sb.append(StrPool.UNDERLINE);
            } else {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    private void transferModTime(TarArchiveEntry tarArchiveEntry, TarArchiveEntry tarArchiveEntry2) {
        Date modTime = tarArchiveEntry.getModTime();
        long time = modTime.getTime() / 1000;
        if (time < 0 || time > TarConstants.MAXSIZE) {
            modTime = new Date(0L);
        }
        tarArchiveEntry2.setModTime(modTime);
    }

    private void writeEOFRecord() throws IOException {
        Arrays.fill(this.recordBuf, (byte) 0);
        writeRecord(this.recordBuf);
    }

    private void writeRecord(byte[] bArr) throws IOException {
        if (bArr.length == this.recordSize) {
            this.out.write(bArr);
            this.recordsWritten++;
            return;
        }
        throw new IOException("record to write has length '" + bArr.length + "' which is not the record size of '" + this.recordSize + "'");
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        if (this.closed) {
            return;
        }
        this.out.close();
        this.closed = true;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void closeArchiveEntry() throws IOException {
        byte[] bArr;
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (!this.haveUnclosedEntry) {
            throw new IOException("No current entry to close");
        }
        int i2 = this.assemLen;
        if (i2 > 0) {
            while (true) {
                bArr = this.assemBuf;
                if (i2 >= bArr.length) {
                    break;
                }
                bArr[i2] = 0;
                i2++;
            }
            writeRecord(bArr);
            this.currBytes += this.assemLen;
            this.assemLen = 0;
        }
        if (this.currBytes >= this.currSize) {
            this.haveUnclosedEntry = false;
            return;
        }
        throw new IOException("entry '" + this.currName + "' closed at '" + this.currBytes + "' before the '" + this.currSize + "' bytes specified in the header were written");
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public ArchiveEntry createArchiveEntry(File file, String str) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new TarArchiveEntry(file, str);
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.haveUnclosedEntry) {
            throw new IOException("This archives contains unclosed entries.");
        }
        writeEOFRecord();
        writeEOFRecord();
        padAsNeeded();
        this.out.flush();
        this.finished = true;
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public long getBytesWritten() {
        return ((CountingOutputStream) this.out).getBytesWritten();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    @Deprecated
    public int getCount() {
        return (int) getBytesWritten();
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        TarArchiveEntry tarArchiveEntry = (TarArchiveEntry) archiveEntry;
        HashMap map = new HashMap();
        String name = tarArchiveEntry.getName();
        boolean zHandleLongName = handleLongName(tarArchiveEntry, name, map, "path", TarConstants.LF_GNUTYPE_LONGNAME, "file name");
        String linkName = tarArchiveEntry.getLinkName();
        boolean z2 = linkName != null && linkName.length() > 0 && handleLongName(tarArchiveEntry, linkName, map, "linkpath", TarConstants.LF_GNUTYPE_LONGLINK, "link name");
        int i2 = this.bigNumberMode;
        if (i2 == 2) {
            addPaxHeadersForBigNumbers(map, tarArchiveEntry);
        } else if (i2 != 1) {
            failForBigNumbers(tarArchiveEntry);
        }
        if (this.addPaxHeadersForNonAsciiNames && !zHandleLongName && !ASCII.canEncode(name)) {
            map.put("path", name);
        }
        if (this.addPaxHeadersForNonAsciiNames && !z2 && ((tarArchiveEntry.isLink() || tarArchiveEntry.isSymbolicLink()) && !ASCII.canEncode(linkName))) {
            map.put("linkpath", linkName);
        }
        if (map.size() > 0) {
            writePaxHeaders(tarArchiveEntry, name, map);
        }
        tarArchiveEntry.writeEntryHeader(this.recordBuf, this.zipEncoding, this.bigNumberMode == 1);
        writeRecord(this.recordBuf);
        this.currBytes = 0L;
        if (tarArchiveEntry.isDirectory()) {
            this.currSize = 0L;
        } else {
            this.currSize = tarArchiveEntry.getSize();
        }
        this.currName = name;
        this.haveUnclosedEntry = true;
    }

    public void setAddPaxHeadersForNonAsciiNames(boolean z2) {
        this.addPaxHeadersForNonAsciiNames = z2;
    }

    public void setBigNumberMode(int i2) {
        this.bigNumberMode = i2;
    }

    public void setLongFileMode(int i2) {
        this.longFileMode = i2;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        if (!this.haveUnclosedEntry) {
            throw new IllegalStateException("No current tar entry");
        }
        if (this.currBytes + i3 > this.currSize) {
            throw new IOException("request to write '" + i3 + "' bytes exceeds size in header of '" + this.currSize + "' bytes for entry '" + this.currName + "'");
        }
        int i4 = this.assemLen;
        if (i4 > 0) {
            int i5 = i4 + i3;
            byte[] bArr2 = this.recordBuf;
            if (i5 >= bArr2.length) {
                int length = bArr2.length - i4;
                System.arraycopy(this.assemBuf, 0, bArr2, 0, i4);
                System.arraycopy(bArr, i2, this.recordBuf, this.assemLen, length);
                writeRecord(this.recordBuf);
                this.currBytes += this.recordBuf.length;
                i2 += length;
                i3 -= length;
                this.assemLen = 0;
            } else {
                System.arraycopy(bArr, i2, this.assemBuf, i4, i3);
                i2 += i3;
                this.assemLen += i3;
                i3 = 0;
            }
        }
        while (i3 > 0) {
            if (i3 < this.recordBuf.length) {
                System.arraycopy(bArr, i2, this.assemBuf, this.assemLen, i3);
                this.assemLen += i3;
                return;
            } else {
                writeRecord(bArr, i2);
                int length2 = this.recordBuf.length;
                this.currBytes += length2;
                i3 -= length2;
                i2 += length2;
            }
        }
    }

    public void writePaxHeaders(TarArchiveEntry tarArchiveEntry, String str, Map<String, String> map) throws IOException {
        String strSubstring = "./PaxHeaders.X/" + stripTo7Bits(str);
        if (strSubstring.length() >= 100) {
            strSubstring = strSubstring.substring(0, 99);
        }
        TarArchiveEntry tarArchiveEntry2 = new TarArchiveEntry(strSubstring, TarConstants.LF_PAX_EXTENDED_HEADER_LC);
        transferModTime(tarArchiveEntry, tarArchiveEntry2);
        StringWriter stringWriter = new StringWriter();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            int length = key.length() + value.length() + 3 + 2;
            String str2 = length + " " + key + "=" + value + "\n";
            int length2 = str2.getBytes("UTF-8").length;
            while (length != length2) {
                str2 = length2 + " " + key + "=" + value + "\n";
                int i2 = length2;
                length2 = str2.getBytes("UTF-8").length;
                length = i2;
            }
            stringWriter.write(str2);
        }
        byte[] bytes = stringWriter.toString().getBytes("UTF-8");
        tarArchiveEntry2.setSize(bytes.length);
        putArchiveEntry(tarArchiveEntry2);
        write(bytes);
        closeArchiveEntry();
    }

    public TarArchiveOutputStream(OutputStream outputStream, String str) {
        this(outputStream, 10240, 512, str);
    }

    private void failForBigNumber(String str, long j2, long j3, String str2) {
        if (j2 < 0 || j2 > j3) {
            throw new RuntimeException(str + " '" + j2 + "' is too big ( > " + j3 + " )." + str2);
        }
    }

    public TarArchiveOutputStream(OutputStream outputStream, int i2) {
        this(outputStream, i2, 512);
    }

    public TarArchiveOutputStream(OutputStream outputStream, int i2, String str) {
        this(outputStream, i2, 512, str);
    }

    public TarArchiveOutputStream(OutputStream outputStream, int i2, int i3) {
        this(outputStream, i2, i3, null);
    }

    private void writeRecord(byte[] bArr, int i2) throws IOException {
        int i3 = this.recordSize;
        if (i2 + i3 <= bArr.length) {
            this.out.write(bArr, i2, i3);
            this.recordsWritten++;
            return;
        }
        throw new IOException("record has length '" + bArr.length + "' with offset '" + i2 + "' which is less than the record size of '" + this.recordSize + "'");
    }

    public TarArchiveOutputStream(OutputStream outputStream, int i2, int i3, String str) {
        this.longFileMode = 0;
        this.bigNumberMode = 0;
        this.closed = false;
        this.haveUnclosedEntry = false;
        this.finished = false;
        this.addPaxHeadersForNonAsciiNames = false;
        this.out = new CountingOutputStream(outputStream);
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        this.assemLen = 0;
        this.assemBuf = new byte[i3];
        this.recordBuf = new byte[i3];
        this.recordSize = i3;
        this.recordsPerBlock = i2 / i3;
    }
}
