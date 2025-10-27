package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.nio.ByteBuffer;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class PsshAtomUtil {
    private static final String TAG = "PsshAtomUtil";

    public static class PsshAtom {
        private final byte[] schemeData;
        private final UUID uuid;
        private final int version;

        public PsshAtom(UUID uuid, int i2, byte[] bArr) {
            this.uuid = uuid;
            this.version = i2;
            this.schemeData = bArr;
        }
    }

    private PsshAtomUtil() {
    }

    public static byte[] buildPsshAtom(UUID uuid, @Nullable byte[] bArr) {
        return buildPsshAtom(uuid, null, bArr);
    }

    public static boolean isPsshAtom(byte[] bArr) {
        return parsePsshAtom(bArr) != null;
    }

    @Nullable
    private static PsshAtom parsePsshAtom(byte[] bArr) {
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr);
        if (parsableByteArray.limit() < 32) {
            return null;
        }
        parsableByteArray.setPosition(0);
        if (parsableByteArray.readInt() != parsableByteArray.bytesLeft() + 4 || parsableByteArray.readInt() != 1886614376) {
            return null;
        }
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        if (fullAtomVersion > 1) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Unsupported pssh version: ");
            sb.append(fullAtomVersion);
            Log.w(TAG, sb.toString());
            return null;
        }
        UUID uuid = new UUID(parsableByteArray.readLong(), parsableByteArray.readLong());
        if (fullAtomVersion == 1) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedIntToInt() * 16);
        }
        int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (unsignedIntToInt != parsableByteArray.bytesLeft()) {
            return null;
        }
        byte[] bArr2 = new byte[unsignedIntToInt];
        parsableByteArray.readBytes(bArr2, 0, unsignedIntToInt);
        return new PsshAtom(uuid, fullAtomVersion, bArr2);
    }

    @Nullable
    public static byte[] parseSchemeSpecificData(byte[] bArr, UUID uuid) {
        PsshAtom psshAtom = parsePsshAtom(bArr);
        if (psshAtom == null) {
            return null;
        }
        if (uuid.equals(psshAtom.uuid)) {
            return psshAtom.schemeData;
        }
        String strValueOf = String.valueOf(uuid);
        String strValueOf2 = String.valueOf(psshAtom.uuid);
        StringBuilder sb = new StringBuilder(strValueOf.length() + 33 + strValueOf2.length());
        sb.append("UUID mismatch. Expected: ");
        sb.append(strValueOf);
        sb.append(", got: ");
        sb.append(strValueOf2);
        sb.append(StrPool.DOT);
        Log.w(TAG, sb.toString());
        return null;
    }

    @Nullable
    public static UUID parseUuid(byte[] bArr) {
        PsshAtom psshAtom = parsePsshAtom(bArr);
        if (psshAtom == null) {
            return null;
        }
        return psshAtom.uuid;
    }

    public static int parseVersion(byte[] bArr) {
        PsshAtom psshAtom = parsePsshAtom(bArr);
        if (psshAtom == null) {
            return -1;
        }
        return psshAtom.version;
    }

    public static byte[] buildPsshAtom(UUID uuid, @Nullable UUID[] uuidArr, @Nullable byte[] bArr) {
        int length = (bArr != null ? bArr.length : 0) + 32;
        if (uuidArr != null) {
            length += (uuidArr.length * 16) + 4;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        byteBufferAllocate.putInt(length);
        byteBufferAllocate.putInt(Atom.TYPE_pssh);
        byteBufferAllocate.putInt(uuidArr != null ? 16777216 : 0);
        byteBufferAllocate.putLong(uuid.getMostSignificantBits());
        byteBufferAllocate.putLong(uuid.getLeastSignificantBits());
        if (uuidArr != null) {
            byteBufferAllocate.putInt(uuidArr.length);
            for (UUID uuid2 : uuidArr) {
                byteBufferAllocate.putLong(uuid2.getMostSignificantBits());
                byteBufferAllocate.putLong(uuid2.getLeastSignificantBits());
            }
        }
        if (bArr != null && bArr.length != 0) {
            byteBufferAllocate.putInt(bArr.length);
            byteBufferAllocate.put(bArr);
        }
        return byteBufferAllocate.array();
    }
}
