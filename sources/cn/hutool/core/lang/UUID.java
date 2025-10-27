package cn.hutool.core.lang;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.primitives.SignedBytes;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import okhttp3.internal.ws.WebSocketProtocol;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes.dex */
public class UUID implements Serializable, Comparable<UUID> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -1185015143654744140L;
    private final long leastSigBits;
    private final long mostSigBits;

    public static class Holder {
        static final SecureRandom NUMBER_GENERATOR = RandomUtil.getSecureRandom();

        private Holder() {
        }
    }

    private UUID(byte[] bArr) {
        long j2 = 0;
        long j3 = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            j3 = (j3 << 8) | (bArr[i2] & 255);
        }
        for (int i3 = 8; i3 < 16; i3++) {
            j2 = (j2 << 8) | (bArr[i3] & 255);
        }
        this.mostSigBits = j3;
        this.leastSigBits = j2;
    }

    private void checkTimeBase() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
    }

    private static String digits(long j2, int i2) {
        long j3 = 1 << (i2 * 4);
        return Long.toHexString((j2 & (j3 - 1)) | j3).substring(1);
    }

    public static UUID fastUUID() {
        return randomUUID(false);
    }

    public static UUID fromString(String str) {
        String[] strArrSplit = str.split("-");
        if (strArrSplit.length != 5) {
            throw new IllegalArgumentException("Invalid UUID string: " + str);
        }
        for (int i2 = 0; i2 < 5; i2++) {
            strArrSplit[i2] = "0x" + strArrSplit[i2];
        }
        return new UUID((((Long.decode(strArrSplit[0]).longValue() << 16) | Long.decode(strArrSplit[1]).longValue()) << 16) | Long.decode(strArrSplit[2]).longValue(), (Long.decode(strArrSplit[3]).longValue() << 48) | Long.decode(strArrSplit[4]).longValue());
    }

    public static UUID nameUUIDFromBytes(byte[] bArr) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(bArr);
            byte b3 = (byte) (bArrDigest[6] & 15);
            bArrDigest[6] = b3;
            bArrDigest[6] = (byte) (b3 | TarConstants.LF_NORMAL);
            byte b4 = (byte) (bArrDigest[8] & Utf8.REPLACEMENT_BYTE);
            bArrDigest[8] = b4;
            bArrDigest[8] = (byte) (b4 | 128);
            return new UUID(bArrDigest);
        } catch (NoSuchAlgorithmException unused) {
            throw new InternalError("MD5 not supported");
        }
    }

    public static UUID randomUUID() {
        return randomUUID(true);
    }

    public int clockSequence() throws UnsupportedOperationException {
        checkTimeBase();
        return (int) ((this.leastSigBits & 4611404543450677248L) >>> 48);
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != UUID.class) {
            return false;
        }
        UUID uuid = (UUID) obj;
        return this.mostSigBits == uuid.mostSigBits && this.leastSigBits == uuid.leastSigBits;
    }

    public long getLeastSignificantBits() {
        return this.leastSigBits;
    }

    public long getMostSignificantBits() {
        return this.mostSigBits;
    }

    public int hashCode() {
        long j2 = this.mostSigBits ^ this.leastSigBits;
        return ((int) j2) ^ ((int) (j2 >> 32));
    }

    public long node() throws UnsupportedOperationException {
        checkTimeBase();
        return this.leastSigBits & 281474976710655L;
    }

    public long timestamp() throws UnsupportedOperationException {
        checkTimeBase();
        long j2 = this.mostSigBits;
        return (j2 >>> 32) | ((4095 & j2) << 48) | (((j2 >> 16) & WebSocketProtocol.PAYLOAD_SHORT_MAX) << 32);
    }

    public String toString() {
        return toString(false);
    }

    public int variant() {
        long j2 = this.leastSigBits;
        return (int) ((j2 >> 63) & (j2 >>> ((int) (64 - (j2 >>> 62)))));
    }

    public int version() {
        return (int) ((this.mostSigBits >> 12) & 15);
    }

    public static UUID randomUUID(boolean z2) {
        byte[] bArr = new byte[16];
        (z2 ? Holder.NUMBER_GENERATOR : RandomUtil.getRandom()).nextBytes(bArr);
        byte b3 = (byte) (bArr[6] & 15);
        bArr[6] = b3;
        bArr[6] = (byte) (b3 | SignedBytes.MAX_POWER_OF_TWO);
        byte b4 = (byte) (bArr[8] & Utf8.REPLACEMENT_BYTE);
        bArr[8] = b4;
        bArr[8] = (byte) (b4 | 128);
        return new UUID(bArr);
    }

    @Override // java.lang.Comparable
    public int compareTo(UUID uuid) {
        int iCompare = Long.compare(this.mostSigBits, uuid.mostSigBits);
        return iCompare == 0 ? Long.compare(this.leastSigBits, uuid.leastSigBits) : iCompare;
    }

    public String toString(boolean z2) {
        StringBuilder sbBuilder = StrUtil.builder(z2 ? 32 : 36);
        sbBuilder.append(digits(this.mostSigBits >> 32, 8));
        if (!z2) {
            sbBuilder.append(CharPool.DASHED);
        }
        sbBuilder.append(digits(this.mostSigBits >> 16, 4));
        if (!z2) {
            sbBuilder.append(CharPool.DASHED);
        }
        sbBuilder.append(digits(this.mostSigBits, 4));
        if (!z2) {
            sbBuilder.append(CharPool.DASHED);
        }
        sbBuilder.append(digits(this.leastSigBits >> 48, 4));
        if (!z2) {
            sbBuilder.append(CharPool.DASHED);
        }
        sbBuilder.append(digits(this.leastSigBits, 12));
        return sbBuilder.toString();
    }

    public UUID(long j2, long j3) {
        this.mostSigBits = j2;
        this.leastSigBits = j3;
    }
}
