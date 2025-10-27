package com.squareup.wire;

import com.google.common.base.Ascii;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import okio.BufferedSource;
import okio.ByteString;

/* loaded from: classes6.dex */
public final class ProtoReader {
    private static final int FIELD_ENCODING_MASK = 7;
    private static final int RECURSION_LIMIT = 65;
    private static final int STATE_END_GROUP = 4;
    private static final int STATE_FIXED32 = 5;
    private static final int STATE_FIXED64 = 1;
    private static final int STATE_LENGTH_DELIMITED = 2;
    private static final int STATE_PACKED_TAG = 7;
    private static final int STATE_START_GROUP = 3;
    private static final int STATE_TAG = 6;
    private static final int STATE_VARINT = 0;
    static final int TAG_FIELD_ENCODING_BITS = 3;
    private FieldEncoding nextFieldEncoding;
    private int recursionDepth;
    private final BufferedSource source;
    private long pos = 0;
    private long limit = Long.MAX_VALUE;
    private int state = 2;
    private int tag = -1;
    private long pushedLimit = -1;

    public ProtoReader(BufferedSource bufferedSource) {
        this.source = bufferedSource;
    }

    private void afterPackableScalar(int i2) throws IOException {
        if (this.state == i2) {
            this.state = 6;
            return;
        }
        long j2 = this.pos;
        long j3 = this.limit;
        if (j2 > j3) {
            throw new IOException("Expected to end at " + this.limit + " but was " + this.pos);
        }
        if (j2 != j3) {
            this.state = 7;
            return;
        }
        this.limit = this.pushedLimit;
        this.pushedLimit = -1L;
        this.state = 6;
    }

    private long beforeLengthDelimitedScalar() throws IOException {
        if (this.state != 2) {
            throw new ProtocolException("Expected LENGTH_DELIMITED but was " + this.state);
        }
        long j2 = this.limit - this.pos;
        this.source.require(j2);
        this.state = 6;
        this.pos = this.limit;
        this.limit = this.pushedLimit;
        this.pushedLimit = -1L;
        return j2;
    }

    private int internalReadVarint32() throws IOException {
        int i2;
        this.source.require(1L);
        this.pos++;
        byte b3 = this.source.readByte();
        if (b3 >= 0) {
            return b3;
        }
        int i3 = b3 & 127;
        this.source.require(1L);
        this.pos++;
        byte b4 = this.source.readByte();
        if (b4 >= 0) {
            i2 = b4 << 7;
        } else {
            i3 |= (b4 & 127) << 7;
            this.source.require(1L);
            this.pos++;
            byte b5 = this.source.readByte();
            if (b5 >= 0) {
                i2 = b5 << 14;
            } else {
                i3 |= (b5 & 127) << 14;
                this.source.require(1L);
                this.pos++;
                byte b6 = this.source.readByte();
                if (b6 < 0) {
                    int i4 = i3 | ((b6 & 127) << 21);
                    this.source.require(1L);
                    this.pos++;
                    byte b7 = this.source.readByte();
                    int i5 = i4 | (b7 << Ascii.FS);
                    if (b7 >= 0) {
                        return i5;
                    }
                    for (int i6 = 0; i6 < 5; i6++) {
                        this.source.require(1L);
                        this.pos++;
                        if (this.source.readByte() >= 0) {
                            return i5;
                        }
                    }
                    throw new ProtocolException("Malformed VARINT");
                }
                i2 = b6 << 21;
            }
        }
        return i3 | i2;
    }

    private void skipGroup(int i2) throws IOException {
        while (this.pos < this.limit && !this.source.exhausted()) {
            int iInternalReadVarint32 = internalReadVarint32();
            if (iInternalReadVarint32 == 0) {
                throw new ProtocolException("Unexpected tag 0");
            }
            int i3 = iInternalReadVarint32 >> 3;
            int i4 = iInternalReadVarint32 & 7;
            if (i4 == 0) {
                this.state = 0;
                readVarint64();
            } else if (i4 == 1) {
                this.state = 1;
                readFixed64();
            } else if (i4 == 2) {
                long jInternalReadVarint32 = internalReadVarint32();
                this.pos += jInternalReadVarint32;
                this.source.skip(jInternalReadVarint32);
            } else if (i4 == 3) {
                skipGroup(i3);
            } else if (i4 == 4) {
                if (i3 != i2) {
                    throw new ProtocolException("Unexpected end group");
                }
                return;
            } else {
                if (i4 != 5) {
                    throw new ProtocolException("Unexpected field encoding: " + i4);
                }
                this.state = 5;
                readFixed32();
            }
        }
        throw new EOFException();
    }

    public long beginMessage() throws IOException {
        if (this.state != 2) {
            throw new IllegalStateException("Unexpected call to beginMessage()");
        }
        int i2 = this.recursionDepth + 1;
        this.recursionDepth = i2;
        if (i2 > 65) {
            throw new IOException("Wire recursion limit exceeded");
        }
        long j2 = this.pushedLimit;
        this.pushedLimit = -1L;
        this.state = 6;
        return j2;
    }

    public void endMessage(long j2) throws IOException {
        if (this.state != 6) {
            throw new IllegalStateException("Unexpected call to endMessage()");
        }
        int i2 = this.recursionDepth - 1;
        this.recursionDepth = i2;
        if (i2 < 0 || this.pushedLimit != -1) {
            throw new IllegalStateException("No corresponding call to beginMessage()");
        }
        if (this.pos == this.limit || i2 == 0) {
            this.limit = j2;
            return;
        }
        throw new IOException("Expected to end at " + this.limit + " but was " + this.pos);
    }

    public int nextTag() throws IOException {
        int i2 = this.state;
        if (i2 == 7) {
            this.state = 2;
            return this.tag;
        }
        if (i2 != 6) {
            throw new IllegalStateException("Unexpected call to nextTag()");
        }
        while (this.pos < this.limit && !this.source.exhausted()) {
            int iInternalReadVarint32 = internalReadVarint32();
            if (iInternalReadVarint32 == 0) {
                throw new ProtocolException("Unexpected tag 0");
            }
            int i3 = iInternalReadVarint32 >> 3;
            this.tag = i3;
            int i4 = iInternalReadVarint32 & 7;
            if (i4 == 0) {
                this.nextFieldEncoding = FieldEncoding.VARINT;
                this.state = 0;
                return i3;
            }
            if (i4 == 1) {
                this.nextFieldEncoding = FieldEncoding.FIXED64;
                this.state = 1;
                return i3;
            }
            if (i4 == 2) {
                this.nextFieldEncoding = FieldEncoding.LENGTH_DELIMITED;
                this.state = 2;
                int iInternalReadVarint322 = internalReadVarint32();
                if (iInternalReadVarint322 < 0) {
                    throw new ProtocolException("Negative length: " + iInternalReadVarint322);
                }
                if (this.pushedLimit != -1) {
                    throw new IllegalStateException();
                }
                long j2 = this.limit;
                this.pushedLimit = j2;
                long j3 = this.pos + iInternalReadVarint322;
                this.limit = j3;
                if (j3 <= j2) {
                    return this.tag;
                }
                throw new EOFException();
            }
            if (i4 != 3) {
                if (i4 == 4) {
                    throw new ProtocolException("Unexpected end group");
                }
                if (i4 == 5) {
                    this.nextFieldEncoding = FieldEncoding.FIXED32;
                    this.state = 5;
                    return i3;
                }
                throw new ProtocolException("Unexpected field encoding: " + i4);
            }
            skipGroup(i3);
        }
        return -1;
    }

    public FieldEncoding peekFieldEncoding() {
        return this.nextFieldEncoding;
    }

    public ByteString readBytes() throws IOException {
        long jBeforeLengthDelimitedScalar = beforeLengthDelimitedScalar();
        this.source.require(jBeforeLengthDelimitedScalar);
        return this.source.readByteString(jBeforeLengthDelimitedScalar);
    }

    public int readFixed32() throws IOException {
        int i2 = this.state;
        if (i2 != 5 && i2 != 2) {
            throw new ProtocolException("Expected FIXED32 or LENGTH_DELIMITED but was " + this.state);
        }
        this.source.require(4L);
        this.pos += 4;
        int intLe = this.source.readIntLe();
        afterPackableScalar(5);
        return intLe;
    }

    public long readFixed64() throws IOException {
        int i2 = this.state;
        if (i2 != 1 && i2 != 2) {
            throw new ProtocolException("Expected FIXED64 or LENGTH_DELIMITED but was " + this.state);
        }
        this.source.require(8L);
        this.pos += 8;
        long longLe = this.source.readLongLe();
        afterPackableScalar(1);
        return longLe;
    }

    public String readString() throws IOException {
        long jBeforeLengthDelimitedScalar = beforeLengthDelimitedScalar();
        this.source.require(jBeforeLengthDelimitedScalar);
        return this.source.readUtf8(jBeforeLengthDelimitedScalar);
    }

    public int readVarint32() throws IOException {
        int i2 = this.state;
        if (i2 == 0 || i2 == 2) {
            int iInternalReadVarint32 = internalReadVarint32();
            afterPackableScalar(0);
            return iInternalReadVarint32;
        }
        throw new ProtocolException("Expected VARINT or LENGTH_DELIMITED but was " + this.state);
    }

    public long readVarint64() throws IOException {
        int i2 = this.state;
        if (i2 != 0 && i2 != 2) {
            throw new ProtocolException("Expected VARINT or LENGTH_DELIMITED but was " + this.state);
        }
        long j2 = 0;
        for (int i3 = 0; i3 < 64; i3 += 7) {
            this.source.require(1L);
            this.pos++;
            j2 |= (r4 & 127) << i3;
            if ((this.source.readByte() & 128) == 0) {
                afterPackableScalar(0);
                return j2;
            }
        }
        throw new ProtocolException("WireInput encountered a malformed varint");
    }

    public void skip() throws IOException {
        int i2 = this.state;
        if (i2 == 0) {
            readVarint64();
            return;
        }
        if (i2 == 1) {
            readFixed64();
            return;
        }
        if (i2 == 2) {
            this.source.skip(beforeLengthDelimitedScalar());
        } else {
            if (i2 != 5) {
                throw new IllegalStateException("Unexpected call to skip()");
            }
            readFixed32();
        }
    }
}
