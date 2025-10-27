package net.tsz.afinal.bitmap.core;

import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.Adler32;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes9.dex */
public class DiskCache implements Closeable {
    private static final int BH_CHECKSUM = 8;
    private static final int BH_KEY = 0;
    private static final int BH_LENGTH = 16;
    private static final int BH_OFFSET = 12;
    private static final int BLOB_HEADER_SIZE = 20;
    private static final int DATA_HEADER_SIZE = 4;
    private static final int IH_ACTIVE_BYTES = 20;
    private static final int IH_ACTIVE_ENTRIES = 16;
    private static final int IH_ACTIVE_REGION = 12;
    private static final int IH_CHECKSUM = 28;
    private static final int IH_MAGIC = 0;
    private static final int IH_MAX_BYTES = 8;
    private static final int IH_MAX_ENTRIES = 4;
    private static final int IH_VERSION = 24;
    private static final int INDEX_HEADER_SIZE = 32;
    private static final int MAGIC_DATA_FILE = -1121680112;
    private static final int MAGIC_INDEX_FILE = -1289277392;
    private static final String TAG = "DiskCache";
    private int mActiveBytes;
    private RandomAccessFile mActiveDataFile;
    private int mActiveEntries;
    private int mActiveHashStart;
    private int mActiveRegion;
    private Adler32 mAdler32;
    private byte[] mBlobHeader;
    private RandomAccessFile mDataFile0;
    private RandomAccessFile mDataFile1;
    private int mFileOffset;
    private RandomAccessFile mInactiveDataFile;
    private int mInactiveHashStart;
    private MappedByteBuffer mIndexBuffer;
    private FileChannel mIndexChannel;
    private RandomAccessFile mIndexFile;
    private byte[] mIndexHeader;
    private LookupRequest mLookupRequest;
    private int mMaxBytes;
    private int mMaxEntries;
    private String mPath;
    private int mSlotOffset;
    private int mVersion;

    public static class LookupRequest {
        public byte[] buffer;
        public long key;
        public int length;
    }

    public DiskCache(String str, int i2, int i3, boolean z2) throws IOException {
        this(str, i2, i3, z2, 0);
    }

    private void clearHash(int i2) {
        byte[] bArr = new byte[1024];
        this.mIndexBuffer.position(i2);
        int i3 = this.mMaxEntries * 12;
        while (i3 > 0) {
            int iMin = Math.min(i3, 1024);
            this.mIndexBuffer.put(bArr, 0, iMin);
            i3 -= iMin;
        }
    }

    private void closeAll() {
        closeSilently(this.mIndexChannel);
        closeSilently(this.mIndexFile);
        closeSilently(this.mDataFile0);
        closeSilently(this.mDataFile1);
    }

    public static void closeSilently(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Throwable unused) {
        }
    }

    private static void deleteFileSilently(String str) {
        try {
            new File(str).delete();
        } catch (Throwable unused) {
        }
    }

    private void flipRegion() throws IOException {
        int i2 = 1 - this.mActiveRegion;
        this.mActiveRegion = i2;
        this.mActiveEntries = 0;
        this.mActiveBytes = 4;
        writeInt(this.mIndexHeader, 12, i2);
        writeInt(this.mIndexHeader, 16, this.mActiveEntries);
        writeInt(this.mIndexHeader, 20, this.mActiveBytes);
        updateIndexHeader();
        setActiveVariables();
        clearHash(this.mActiveHashStart);
        syncIndex();
    }

    private boolean getBlob(RandomAccessFile randomAccessFile, int i2, LookupRequest lookupRequest) throws IOException {
        byte[] bArr = this.mBlobHeader;
        long filePointer = randomAccessFile.getFilePointer();
        try {
            randomAccessFile.seek(i2);
            if (randomAccessFile.read(bArr) != 20) {
                Log.w(TAG, "cannot read blob header");
                return false;
            }
            long j2 = readLong(bArr, 0);
            if (j2 != lookupRequest.key) {
                Log.w(TAG, "blob key does not match: " + j2);
                return false;
            }
            int i3 = readInt(bArr, 8);
            int i4 = readInt(bArr, 12);
            if (i4 != i2) {
                Log.w(TAG, "blob offset does not match: " + i4);
                return false;
            }
            int i5 = readInt(bArr, 16);
            if (i5 >= 0 && i5 <= (this.mMaxBytes - i2) - 20) {
                byte[] bArr2 = lookupRequest.buffer;
                if (bArr2 == null || bArr2.length < i5) {
                    lookupRequest.buffer = new byte[i5];
                }
                byte[] bArr3 = lookupRequest.buffer;
                lookupRequest.length = i5;
                if (randomAccessFile.read(bArr3, 0, i5) != i5) {
                    Log.w(TAG, "cannot read blob data");
                    return false;
                }
                if (checkSum(bArr3, 0, i5) == i3) {
                    randomAccessFile.seek(filePointer);
                    return true;
                }
                Log.w(TAG, "blob checksum does not match: " + i3);
                return false;
            }
            Log.w(TAG, "invalid blob length: " + i5);
            return false;
        } catch (Throwable th) {
            try {
                Log.e(TAG, "getBlob failed.", th);
                return false;
            } finally {
                randomAccessFile.seek(filePointer);
            }
        }
    }

    private void insertInternal(long j2, byte[] bArr, int i2) throws IOException {
        byte[] bArr2 = this.mBlobHeader;
        int iCheckSum = checkSum(bArr);
        writeLong(bArr2, 0, j2);
        writeInt(bArr2, 8, iCheckSum);
        writeInt(bArr2, 12, this.mActiveBytes);
        writeInt(bArr2, 16, i2);
        this.mActiveDataFile.write(bArr2);
        this.mActiveDataFile.write(bArr, 0, i2);
        this.mIndexBuffer.putLong(this.mSlotOffset, j2);
        this.mIndexBuffer.putInt(this.mSlotOffset + 8, this.mActiveBytes);
        int i3 = this.mActiveBytes + i2 + 20;
        this.mActiveBytes = i3;
        writeInt(this.mIndexHeader, 20, i3);
    }

    private boolean loadIndex() throws IOException {
        try {
            this.mIndexFile.seek(0L);
            this.mDataFile0.seek(0L);
            this.mDataFile1.seek(0L);
            byte[] bArr = this.mIndexHeader;
            if (this.mIndexFile.read(bArr) != 32) {
                Log.w(TAG, "cannot read header");
                return false;
            }
            if (readInt(bArr, 0) != MAGIC_INDEX_FILE) {
                Log.w(TAG, "cannot read header magic");
                return false;
            }
            if (readInt(bArr, 24) != this.mVersion) {
                Log.w(TAG, "version mismatch");
                return false;
            }
            this.mMaxEntries = readInt(bArr, 4);
            this.mMaxBytes = readInt(bArr, 8);
            this.mActiveRegion = readInt(bArr, 12);
            this.mActiveEntries = readInt(bArr, 16);
            this.mActiveBytes = readInt(bArr, 20);
            if (checkSum(bArr, 0, 28) != readInt(bArr, 28)) {
                Log.w(TAG, "header checksum does not match");
                return false;
            }
            int i2 = this.mMaxEntries;
            if (i2 <= 0) {
                Log.w(TAG, "invalid max entries");
                return false;
            }
            int i3 = this.mMaxBytes;
            if (i3 <= 0) {
                Log.w(TAG, "invalid max bytes");
                return false;
            }
            int i4 = this.mActiveRegion;
            if (i4 != 0 && i4 != 1) {
                Log.w(TAG, "invalid active region");
                return false;
            }
            int i5 = this.mActiveEntries;
            if (i5 >= 0 && i5 <= i2) {
                int i6 = this.mActiveBytes;
                if (i6 >= 4 && i6 <= i3) {
                    if (this.mIndexFile.length() != (this.mMaxEntries * 12 * 2) + 32) {
                        Log.w(TAG, "invalid index file length");
                        return false;
                    }
                    byte[] bArr2 = new byte[4];
                    if (this.mDataFile0.read(bArr2) != 4) {
                        Log.w(TAG, "cannot read data file magic");
                        return false;
                    }
                    if (readInt(bArr2, 0) != MAGIC_DATA_FILE) {
                        Log.w(TAG, "invalid data file magic");
                        return false;
                    }
                    if (this.mDataFile1.read(bArr2) != 4) {
                        Log.w(TAG, "cannot read data file magic");
                        return false;
                    }
                    if (readInt(bArr2, 0) != MAGIC_DATA_FILE) {
                        Log.w(TAG, "invalid data file magic");
                        return false;
                    }
                    FileChannel channel = this.mIndexFile.getChannel();
                    this.mIndexChannel = channel;
                    MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0L, this.mIndexFile.length());
                    this.mIndexBuffer = map;
                    map.order(ByteOrder.LITTLE_ENDIAN);
                    setActiveVariables();
                    return true;
                }
                Log.w(TAG, "invalid active bytes");
                return false;
            }
            Log.w(TAG, "invalid active entries");
            return false;
        } catch (IOException e2) {
            Log.e(TAG, "loadIndex failed.", e2);
            return false;
        }
    }

    private boolean lookupInternal(long j2, int i2) {
        int i3 = this.mMaxEntries;
        int i4 = (int) (j2 % i3);
        if (i4 < 0) {
            i4 += i3;
        }
        int i5 = i4;
        while (true) {
            int i6 = (i5 * 12) + i2;
            long j3 = this.mIndexBuffer.getLong(i6);
            int i7 = this.mIndexBuffer.getInt(i6 + 8);
            if (i7 == 0) {
                this.mSlotOffset = i6;
                return false;
            }
            if (j3 == j2) {
                this.mSlotOffset = i6;
                this.mFileOffset = i7;
                return true;
            }
            i5++;
            if (i5 >= this.mMaxEntries) {
                i5 = 0;
            }
            if (i5 == i4) {
                Log.w(TAG, "corrupted index: clear the slot.");
                this.mIndexBuffer.putInt((i5 * 12) + i2 + 8, 0);
            }
        }
    }

    public static int readInt(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    public static long readLong(byte[] bArr, int i2) {
        long j2 = bArr[i2 + 7] & 255;
        for (int i3 = 6; i3 >= 0; i3--) {
            j2 = (j2 << 8) | (bArr[i2 + i3] & 255);
        }
        return j2;
    }

    private void resetCache(int i2, int i3) throws IOException {
        this.mIndexFile.setLength(0L);
        this.mIndexFile.setLength((i2 * 12 * 2) + 32);
        this.mIndexFile.seek(0L);
        byte[] bArr = this.mIndexHeader;
        writeInt(bArr, 0, MAGIC_INDEX_FILE);
        writeInt(bArr, 4, i2);
        writeInt(bArr, 8, i3);
        writeInt(bArr, 12, 0);
        writeInt(bArr, 16, 0);
        writeInt(bArr, 20, 4);
        writeInt(bArr, 24, this.mVersion);
        writeInt(bArr, 28, checkSum(bArr, 0, 28));
        this.mIndexFile.write(bArr);
        this.mDataFile0.setLength(0L);
        this.mDataFile1.setLength(0L);
        this.mDataFile0.seek(0L);
        this.mDataFile1.seek(0L);
        writeInt(bArr, 0, MAGIC_DATA_FILE);
        this.mDataFile0.write(bArr, 0, 4);
        this.mDataFile1.write(bArr, 0, 4);
    }

    private void setActiveVariables() throws IOException {
        int i2 = this.mActiveRegion;
        RandomAccessFile randomAccessFile = i2 == 0 ? this.mDataFile0 : this.mDataFile1;
        this.mActiveDataFile = randomAccessFile;
        this.mInactiveDataFile = i2 == 1 ? this.mDataFile0 : this.mDataFile1;
        randomAccessFile.setLength(this.mActiveBytes);
        this.mActiveDataFile.seek(this.mActiveBytes);
        this.mActiveHashStart = 32;
        this.mInactiveHashStart = 32;
        if (this.mActiveRegion == 0) {
            this.mInactiveHashStart = 32 + (this.mMaxEntries * 12);
        } else {
            this.mActiveHashStart = 32 + (this.mMaxEntries * 12);
        }
    }

    private void updateIndexHeader() {
        byte[] bArr = this.mIndexHeader;
        writeInt(bArr, 28, checkSum(bArr, 0, 28));
        this.mIndexBuffer.position(0);
        this.mIndexBuffer.put(this.mIndexHeader);
    }

    public static void writeInt(byte[] bArr, int i2, int i3) {
        for (int i4 = 0; i4 < 4; i4++) {
            bArr[i2 + i4] = (byte) (i3 & 255);
            i3 >>= 8;
        }
    }

    public static void writeLong(byte[] bArr, int i2, long j2) {
        for (int i3 = 0; i3 < 8; i3++) {
            bArr[i2 + i3] = (byte) (255 & j2);
            j2 >>= 8;
        }
    }

    public int checkSum(byte[] bArr) {
        this.mAdler32.reset();
        this.mAdler32.update(bArr);
        return (int) this.mAdler32.getValue();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        syncAll();
        closeAll();
    }

    public void delete() {
        deleteFileSilently(this.mPath + ".idx");
        deleteFileSilently(this.mPath + ".0");
        deleteFileSilently(this.mPath + ".1");
    }

    public int getActiveCount() {
        int i2 = 0;
        for (int i3 = 0; i3 < this.mMaxEntries; i3++) {
            if (this.mIndexBuffer.getInt(this.mActiveHashStart + (i3 * 12) + 8) != 0) {
                i2++;
            }
        }
        if (i2 == this.mActiveEntries) {
            return i2;
        }
        Log.e(TAG, "wrong active count: " + this.mActiveEntries + " vs " + i2);
        return -1;
    }

    public void insert(long j2, byte[] bArr) throws IOException {
        int length = bArr.length + 24;
        int i2 = this.mMaxBytes;
        if (length > i2) {
            throw new RuntimeException("blob is too large!");
        }
        if (this.mActiveBytes + 20 + bArr.length > i2 || this.mActiveEntries * 2 >= this.mMaxEntries) {
            flipRegion();
        }
        if (!lookupInternal(j2, this.mActiveHashStart)) {
            int i3 = this.mActiveEntries + 1;
            this.mActiveEntries = i3;
            writeInt(this.mIndexHeader, 16, i3);
        }
        insertInternal(j2, bArr, bArr.length);
        updateIndexHeader();
    }

    public byte[] lookup(long j2) throws IOException {
        LookupRequest lookupRequest = this.mLookupRequest;
        lookupRequest.key = j2;
        lookupRequest.buffer = null;
        if (lookup(lookupRequest)) {
            return this.mLookupRequest.buffer;
        }
        return null;
    }

    public void syncAll() {
        syncIndex();
        try {
            this.mDataFile0.getFD().sync();
        } catch (Throwable th) {
            Log.w(TAG, "sync data file 0 failed", th);
        }
        try {
            this.mDataFile1.getFD().sync();
        } catch (Throwable th2) {
            Log.w(TAG, "sync data file 1 failed", th2);
        }
    }

    public void syncIndex() {
        try {
            this.mIndexBuffer.force();
        } catch (Throwable th) {
            Log.w(TAG, "sync index failed", th);
        }
    }

    public DiskCache(String str, int i2, int i3, boolean z2, int i4) throws IOException {
        this.mIndexHeader = new byte[32];
        this.mBlobHeader = new byte[20];
        this.mAdler32 = new Adler32();
        this.mLookupRequest = new LookupRequest();
        File file = new File(str);
        if (!file.exists() && !file.mkdirs()) {
            throw new IOException("unable to make dirs");
        }
        this.mPath = str;
        this.mIndexFile = new RandomAccessFile(str + ".idx", InternalZipConstants.WRITE_MODE);
        this.mDataFile0 = new RandomAccessFile(str + ".0", InternalZipConstants.WRITE_MODE);
        this.mDataFile1 = new RandomAccessFile(str + ".1", InternalZipConstants.WRITE_MODE);
        this.mVersion = i4;
        if (z2 || !loadIndex()) {
            resetCache(i2, i3);
            if (loadIndex()) {
                return;
            }
            closeAll();
            throw new IOException("unable to load index");
        }
    }

    public int checkSum(byte[] bArr, int i2, int i3) {
        this.mAdler32.reset();
        this.mAdler32.update(bArr, i2, i3);
        return (int) this.mAdler32.getValue();
    }

    public boolean lookup(LookupRequest lookupRequest) throws IOException {
        if (lookupInternal(lookupRequest.key, this.mActiveHashStart) && getBlob(this.mActiveDataFile, this.mFileOffset, lookupRequest)) {
            return true;
        }
        int i2 = this.mSlotOffset;
        if (!lookupInternal(lookupRequest.key, this.mInactiveHashStart) || !getBlob(this.mInactiveDataFile, this.mFileOffset, lookupRequest)) {
            return false;
        }
        int i3 = this.mActiveBytes + 20;
        int i4 = lookupRequest.length;
        if (i3 + i4 <= this.mMaxBytes && this.mActiveEntries * 2 < this.mMaxEntries) {
            this.mSlotOffset = i2;
            try {
                insertInternal(lookupRequest.key, lookupRequest.buffer, i4);
                int i5 = this.mActiveEntries + 1;
                this.mActiveEntries = i5;
                writeInt(this.mIndexHeader, 16, i5);
                updateIndexHeader();
            } catch (Throwable unused) {
                Log.e(TAG, "cannot copy over");
            }
        }
        return true;
    }
}
