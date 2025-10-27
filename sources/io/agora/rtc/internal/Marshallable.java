package io.agora.rtc.internal;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/* loaded from: classes8.dex */
class Marshallable {
    public static final int PROTO_PACKET_SIZE = 8192;
    private ByteBuffer mBuffer;

    public Marshallable() {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8192);
        this.mBuffer = byteBufferAllocate;
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        this.mBuffer.position(2);
    }

    public void clear() {
        this.mBuffer.position(10);
    }

    public ByteBuffer getBuffer() {
        return this.mBuffer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public byte[] marshall() {
        int iPosition = (short) this.mBuffer.position();
        this.mBuffer.putShort(0, iPosition);
        byte[] bArr = new byte[iPosition];
        this.mBuffer.position(0);
        this.mBuffer.get(bArr);
        return bArr;
    }

    public byte[] popAll() {
        byte[] bArr = new byte[this.mBuffer.remaining()];
        this.mBuffer.get(bArr);
        return bArr;
    }

    public Boolean popBool() {
        return Boolean.valueOf(this.mBuffer.get() == 1);
    }

    public byte popByte() {
        return this.mBuffer.get();
    }

    public byte[] popBytes() {
        int i2 = this.mBuffer.getShort();
        byte[] bArr = new byte[0];
        if (i2 <= 0) {
            return bArr;
        }
        byte[] bArr2 = new byte[i2];
        this.mBuffer.get(bArr2);
        return bArr2;
    }

    public byte[] popBytes32() {
        int i2 = this.mBuffer.getInt();
        if (i2 <= 0) {
            return null;
        }
        byte[] bArr = new byte[i2];
        this.mBuffer.get(bArr);
        return bArr;
    }

    public int popInt() {
        return this.mBuffer.getInt();
    }

    public long popInt64() {
        return this.mBuffer.getLong();
    }

    public int[] popIntArray() {
        int iPopInt = popInt();
        int[] iArr = new int[iPopInt];
        for (int i2 = 0; i2 < iPopInt; i2++) {
            iArr[i2] = popInt();
        }
        return iArr;
    }

    public short popShort() {
        return this.mBuffer.getShort();
    }

    public short[] popShortArray() {
        int iPopInt = popInt();
        short[] sArr = new short[iPopInt];
        for (int i2 = 0; i2 < iPopInt; i2++) {
            sArr[i2] = popShort();
        }
        return sArr;
    }

    public String popString16() {
        int i2 = this.mBuffer.getShort();
        if (i2 <= 0) {
            return "";
        }
        byte[] bArr = new byte[i2];
        this.mBuffer.get(bArr);
        try {
            return new String(bArr, "ISO-8859-1");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public String popString16UTF8() {
        int i2 = this.mBuffer.getShort();
        if (i2 <= 0) {
            return "";
        }
        byte[] bArr = new byte[i2];
        this.mBuffer.get(bArr);
        try {
            return new String(bArr, "utf-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public void pushBool(Boolean bool) {
        this.mBuffer.put(bool.booleanValue() ? (byte) 1 : (byte) 0);
    }

    public void pushByte(byte val) {
        this.mBuffer.put(val);
    }

    public void pushBytes(byte[] buf) {
        this.mBuffer.putShort((short) buf.length);
        this.mBuffer.put(buf);
    }

    public void pushBytes32(byte[] buf) {
        this.mBuffer.putInt(buf.length);
        this.mBuffer.put(buf);
    }

    public void pushDouble(double val) {
        this.mBuffer.putDouble(val);
    }

    public void pushInt(int val) {
        this.mBuffer.putInt(val);
    }

    public void pushInt64(long val) {
        this.mBuffer.putLong(val);
    }

    public void pushIntArray(int[] vals) {
        if (vals == null) {
            pushInt(0);
            return;
        }
        pushInt(vals.length);
        for (int i2 : vals) {
            pushInt(i2);
        }
    }

    public void pushShort(short val) {
        this.mBuffer.putShort(val);
    }

    public void pushShortArray(short[] vals) {
        if (vals == null) {
            pushInt(0);
            return;
        }
        pushInt(vals.length);
        for (short s2 : vals) {
            pushShort(s2);
        }
    }

    public void pushString16(String val) {
        if (val == null) {
            this.mBuffer.putShort((short) 0);
            return;
        }
        this.mBuffer.putShort((short) val.getBytes().length);
        if (val.getBytes().length > 0) {
            this.mBuffer.put(val.getBytes());
        }
    }

    public void pushStringArray(ArrayList<String> vals) {
        if (vals == null) {
            pushInt(0);
            return;
        }
        int size = vals.size();
        pushShort((short) size);
        for (int i2 = 0; i2 < size; i2++) {
            pushBytes(vals.get(i2).getBytes());
        }
    }

    public void unmarshall(byte[] buf) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(buf);
        this.mBuffer = byteBufferWrap;
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
        popShort();
    }

    public void unmarshall(ByteBuffer buf) {
        this.mBuffer = buf;
    }

    public void pushIntArray(Integer[] vals) {
        if (vals == null) {
            pushInt(0);
            return;
        }
        pushInt(vals.length);
        for (Integer num : vals) {
            pushInt(num.intValue());
        }
    }

    public void marshall(ByteBuffer buf) {
        this.mBuffer = buf;
    }
}
