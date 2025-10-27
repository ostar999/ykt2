package org.apache.http.impl.auth;

import cn.hutool.core.text.StrPool;
import com.heytap.mcssdk.constant.a;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.http.util.EncodingUtils;

/* loaded from: classes9.dex */
final class NTLMEngineImpl implements NTLMEngine {
    static final String DEFAULT_CHARSET = "ASCII";
    protected static final int FLAG_NEGOTIATE_128 = 536870912;
    protected static final int FLAG_NEGOTIATE_ALWAYS_SIGN = 32768;
    protected static final int FLAG_NEGOTIATE_KEY_EXCH = 1073741824;
    protected static final int FLAG_NEGOTIATE_NTLM = 512;
    protected static final int FLAG_NEGOTIATE_NTLM2 = 524288;
    protected static final int FLAG_NEGOTIATE_SEAL = 32;
    protected static final int FLAG_NEGOTIATE_SIGN = 16;
    protected static final int FLAG_TARGET_DESIRED = 4;
    protected static final int FLAG_UNICODE_ENCODING = 1;
    private static final SecureRandom RND_GEN;
    private static byte[] SIGNATURE;
    private String credentialCharset = "ASCII";

    public static class HMACMD5 {
        protected byte[] ipad;
        protected MessageDigest md5;
        protected byte[] opad;

        public HMACMD5(byte[] bArr) throws NoSuchAlgorithmException, NTLMEngineException {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                this.md5 = messageDigest;
                this.ipad = new byte[64];
                this.opad = new byte[64];
                int length = bArr.length;
                if (length > 64) {
                    messageDigest.update(bArr);
                    bArr = this.md5.digest();
                    length = bArr.length;
                }
                int i2 = 0;
                while (i2 < length) {
                    this.ipad[i2] = (byte) (54 ^ bArr[i2]);
                    this.opad[i2] = (byte) (92 ^ bArr[i2]);
                    i2++;
                }
                while (i2 < 64) {
                    this.ipad[i2] = TarConstants.LF_FIFO;
                    this.opad[i2] = 92;
                    i2++;
                }
                this.md5.reset();
                this.md5.update(this.ipad);
            } catch (Exception e2) {
                throw new NTLMEngineException("Error getting md5 message digest implementation: " + e2.getMessage(), e2);
            }
        }

        public byte[] getOutput() {
            byte[] bArrDigest = this.md5.digest();
            this.md5.update(this.opad);
            return this.md5.digest(bArrDigest);
        }

        public void update(byte[] bArr) {
            this.md5.update(bArr);
        }

        public void update(byte[] bArr, int i2, int i3) {
            this.md5.update(bArr, i2, i3);
        }
    }

    public static class MD4 {
        protected int A = 1732584193;
        protected int B = -271733879;
        protected int C = -1732584194;
        protected int D = 271733878;
        protected long count = 0;
        protected byte[] dataBuffer = new byte[64];

        public byte[] getOutput() {
            int i2 = (int) (this.count & 63);
            int i3 = i2 < 56 ? 56 - i2 : 120 - i2;
            byte[] bArr = new byte[i3 + 8];
            bArr[0] = -128;
            for (int i4 = 0; i4 < 8; i4++) {
                bArr[i3 + i4] = (byte) ((this.count * 8) >>> (i4 * 8));
            }
            update(bArr);
            byte[] bArr2 = new byte[16];
            NTLMEngineImpl.writeULong(bArr2, this.A, 0);
            NTLMEngineImpl.writeULong(bArr2, this.B, 4);
            NTLMEngineImpl.writeULong(bArr2, this.C, 8);
            NTLMEngineImpl.writeULong(bArr2, this.D, 12);
            return bArr2;
        }

        public void processBuffer() {
            int[] iArr = new int[16];
            for (int i2 = 0; i2 < 16; i2++) {
                byte[] bArr = this.dataBuffer;
                int i3 = i2 * 4;
                iArr[i2] = (bArr[i3] & 255) + ((bArr[i3 + 1] & 255) << 8) + ((bArr[i3 + 2] & 255) << 16) + ((bArr[i3 + 3] & 255) << 24);
            }
            int i4 = this.A;
            int i5 = this.B;
            int i6 = this.C;
            int i7 = this.D;
            round1(iArr);
            round2(iArr);
            round3(iArr);
            this.A += i4;
            this.B += i5;
            this.C += i6;
            this.D += i7;
        }

        public void round1(int[] iArr) {
            int iRotintlft = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + iArr[0], 3);
            this.A = iRotintlft;
            int iRotintlft2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(iRotintlft, this.B, this.C) + iArr[1], 7);
            this.D = iRotintlft2;
            int iRotintlft3 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(iRotintlft2, this.A, this.B) + iArr[2], 11);
            this.C = iRotintlft3;
            int iRotintlft4 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(iRotintlft3, this.D, this.A) + iArr[3], 19);
            this.B = iRotintlft4;
            int iRotintlft5 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(iRotintlft4, this.C, this.D) + iArr[4], 3);
            this.A = iRotintlft5;
            int iRotintlft6 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(iRotintlft5, this.B, this.C) + iArr[5], 7);
            this.D = iRotintlft6;
            int iRotintlft7 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(iRotintlft6, this.A, this.B) + iArr[6], 11);
            this.C = iRotintlft7;
            int iRotintlft8 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(iRotintlft7, this.D, this.A) + iArr[7], 19);
            this.B = iRotintlft8;
            int iRotintlft9 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(iRotintlft8, this.C, this.D) + iArr[8], 3);
            this.A = iRotintlft9;
            int iRotintlft10 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(iRotintlft9, this.B, this.C) + iArr[9], 7);
            this.D = iRotintlft10;
            int iRotintlft11 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(iRotintlft10, this.A, this.B) + iArr[10], 11);
            this.C = iRotintlft11;
            int iRotintlft12 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(iRotintlft11, this.D, this.A) + iArr[11], 19);
            this.B = iRotintlft12;
            int iRotintlft13 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(iRotintlft12, this.C, this.D) + iArr[12], 3);
            this.A = iRotintlft13;
            int iRotintlft14 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(iRotintlft13, this.B, this.C) + iArr[13], 7);
            this.D = iRotintlft14;
            int iRotintlft15 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(iRotintlft14, this.A, this.B) + iArr[14], 11);
            this.C = iRotintlft15;
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(iRotintlft15, this.D, this.A) + iArr[15], 19);
        }

        public void round2(int[] iArr) {
            int iRotintlft = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + iArr[0] + 1518500249, 3);
            this.A = iRotintlft;
            int iRotintlft2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(iRotintlft, this.B, this.C) + iArr[4] + 1518500249, 5);
            this.D = iRotintlft2;
            int iRotintlft3 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(iRotintlft2, this.A, this.B) + iArr[8] + 1518500249, 9);
            this.C = iRotintlft3;
            int iRotintlft4 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(iRotintlft3, this.D, this.A) + iArr[12] + 1518500249, 13);
            this.B = iRotintlft4;
            int iRotintlft5 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(iRotintlft4, this.C, this.D) + iArr[1] + 1518500249, 3);
            this.A = iRotintlft5;
            int iRotintlft6 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(iRotintlft5, this.B, this.C) + iArr[5] + 1518500249, 5);
            this.D = iRotintlft6;
            int iRotintlft7 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(iRotintlft6, this.A, this.B) + iArr[9] + 1518500249, 9);
            this.C = iRotintlft7;
            int iRotintlft8 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(iRotintlft7, this.D, this.A) + iArr[13] + 1518500249, 13);
            this.B = iRotintlft8;
            int iRotintlft9 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(iRotintlft8, this.C, this.D) + iArr[2] + 1518500249, 3);
            this.A = iRotintlft9;
            int iRotintlft10 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(iRotintlft9, this.B, this.C) + iArr[6] + 1518500249, 5);
            this.D = iRotintlft10;
            int iRotintlft11 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(iRotintlft10, this.A, this.B) + iArr[10] + 1518500249, 9);
            this.C = iRotintlft11;
            int iRotintlft12 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(iRotintlft11, this.D, this.A) + iArr[14] + 1518500249, 13);
            this.B = iRotintlft12;
            int iRotintlft13 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(iRotintlft12, this.C, this.D) + iArr[3] + 1518500249, 3);
            this.A = iRotintlft13;
            int iRotintlft14 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(iRotintlft13, this.B, this.C) + iArr[7] + 1518500249, 5);
            this.D = iRotintlft14;
            int iRotintlft15 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(iRotintlft14, this.A, this.B) + iArr[11] + 1518500249, 9);
            this.C = iRotintlft15;
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(iRotintlft15, this.D, this.A) + iArr[15] + 1518500249, 13);
        }

        public void round3(int[] iArr) {
            int iRotintlft = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + iArr[0] + 1859775393, 3);
            this.A = iRotintlft;
            int iRotintlft2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(iRotintlft, this.B, this.C) + iArr[8] + 1859775393, 9);
            this.D = iRotintlft2;
            int iRotintlft3 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(iRotintlft2, this.A, this.B) + iArr[4] + 1859775393, 11);
            this.C = iRotintlft3;
            int iRotintlft4 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(iRotintlft3, this.D, this.A) + iArr[12] + 1859775393, 15);
            this.B = iRotintlft4;
            int iRotintlft5 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(iRotintlft4, this.C, this.D) + iArr[2] + 1859775393, 3);
            this.A = iRotintlft5;
            int iRotintlft6 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(iRotintlft5, this.B, this.C) + iArr[10] + 1859775393, 9);
            this.D = iRotintlft6;
            int iRotintlft7 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(iRotintlft6, this.A, this.B) + iArr[6] + 1859775393, 11);
            this.C = iRotintlft7;
            int iRotintlft8 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(iRotintlft7, this.D, this.A) + iArr[14] + 1859775393, 15);
            this.B = iRotintlft8;
            int iRotintlft9 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(iRotintlft8, this.C, this.D) + iArr[1] + 1859775393, 3);
            this.A = iRotintlft9;
            int iRotintlft10 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(iRotintlft9, this.B, this.C) + iArr[9] + 1859775393, 9);
            this.D = iRotintlft10;
            int iRotintlft11 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(iRotintlft10, this.A, this.B) + iArr[5] + 1859775393, 11);
            this.C = iRotintlft11;
            int iRotintlft12 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(iRotintlft11, this.D, this.A) + iArr[13] + 1859775393, 15);
            this.B = iRotintlft12;
            int iRotintlft13 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(iRotintlft12, this.C, this.D) + iArr[3] + 1859775393, 3);
            this.A = iRotintlft13;
            int iRotintlft14 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(iRotintlft13, this.B, this.C) + iArr[11] + 1859775393, 9);
            this.D = iRotintlft14;
            int iRotintlft15 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(iRotintlft14, this.A, this.B) + iArr[7] + 1859775393, 11);
            this.C = iRotintlft15;
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(iRotintlft15, this.D, this.A) + iArr[15] + 1859775393, 15);
        }

        public void update(byte[] bArr) {
            byte[] bArr2;
            int i2 = (int) (this.count & 63);
            int i3 = 0;
            while (true) {
                int length = (bArr.length - i3) + i2;
                bArr2 = this.dataBuffer;
                if (length < bArr2.length) {
                    break;
                }
                int length2 = bArr2.length - i2;
                System.arraycopy(bArr, i3, bArr2, i2, length2);
                this.count += length2;
                i3 += length2;
                processBuffer();
                i2 = 0;
            }
            if (i3 < bArr.length) {
                int length3 = bArr.length - i3;
                System.arraycopy(bArr, i3, bArr2, i2, length3);
                this.count += length3;
            }
        }
    }

    public static class Type1Message extends NTLMMessage {
        protected byte[] domainBytes;
        protected byte[] hostBytes;

        public Type1Message(String str, String str2) throws NTLMEngineException {
            try {
                String strConvertHost = NTLMEngineImpl.convertHost(str2);
                String strConvertDomain = NTLMEngineImpl.convertDomain(str);
                this.hostBytes = strConvertHost.getBytes("UnicodeLittleUnmarked");
                this.domainBytes = strConvertDomain.toUpperCase().getBytes("UnicodeLittleUnmarked");
            } catch (UnsupportedEncodingException e2) {
                throw new NTLMEngineException("Unicode unsupported: " + e2.getMessage(), e2);
            }
        }

        @Override // org.apache.http.impl.auth.NTLMEngineImpl.NTLMMessage
        public String getResponse() {
            prepareResponse(this.hostBytes.length + 32 + this.domainBytes.length, 1);
            addULong(537395765);
            addUShort(this.domainBytes.length);
            addUShort(this.domainBytes.length);
            addULong(this.hostBytes.length + 32);
            addUShort(this.hostBytes.length);
            addUShort(this.hostBytes.length);
            addULong(32);
            addBytes(this.hostBytes);
            addBytes(this.domainBytes);
            return super.getResponse();
        }
    }

    public static class Type2Message extends NTLMMessage {
        protected byte[] challenge;
        protected int flags;
        protected String target;
        protected byte[] targetInfo;

        public Type2Message(String str) throws NTLMEngineException {
            super(str, 2);
            byte[] bArr = new byte[8];
            this.challenge = bArr;
            readBytes(bArr, 24);
            int uLong = readULong(20);
            this.flags = uLong;
            if ((uLong & 1) == 0) {
                throw new NTLMEngineException("NTLM type 2 message has flags that make no sense: " + Integer.toString(this.flags));
            }
            this.target = null;
            if (getMessageLength() >= 20) {
                byte[] securityBuffer = readSecurityBuffer(12);
                if (securityBuffer.length != 0) {
                    try {
                        this.target = new String(securityBuffer, "UnicodeLittleUnmarked");
                    } catch (UnsupportedEncodingException e2) {
                        throw new NTLMEngineException(e2.getMessage(), e2);
                    }
                }
            }
            this.targetInfo = null;
            if (getMessageLength() >= 48) {
                byte[] securityBuffer2 = readSecurityBuffer(40);
                if (securityBuffer2.length != 0) {
                    this.targetInfo = securityBuffer2;
                }
            }
        }

        public byte[] getChallenge() {
            return this.challenge;
        }

        public int getFlags() {
            return this.flags;
        }

        public String getTarget() {
            return this.target;
        }

        public byte[] getTargetInfo() {
            return this.targetInfo;
        }
    }

    public static class Type3Message extends NTLMMessage {
        protected byte[] domainBytes;
        protected byte[] hostBytes;
        protected byte[] lmResp;
        protected byte[] ntResp;
        protected int type2Flags;
        protected byte[] userBytes;

        public Type3Message(String str, String str2, String str3, String str4, byte[] bArr, int i2, String str5, byte[] bArr2) throws NTLMEngineException {
            this.type2Flags = i2;
            String strConvertHost = NTLMEngineImpl.convertHost(str2);
            String strConvertDomain = NTLMEngineImpl.convertDomain(str);
            try {
                if (bArr2 != null && str5 != null) {
                    byte[] bArrMakeRandomChallenge = NTLMEngineImpl.makeRandomChallenge();
                    this.ntResp = NTLMEngineImpl.getNTLMv2Response(str5, str3, str4, bArr, bArrMakeRandomChallenge, bArr2);
                    this.lmResp = NTLMEngineImpl.getLMv2Response(str5, str3, str4, bArr, bArrMakeRandomChallenge);
                } else if ((i2 & 524288) != 0) {
                    byte[] bArrMakeNTLM2RandomChallenge = NTLMEngineImpl.makeNTLM2RandomChallenge();
                    this.ntResp = NTLMEngineImpl.getNTLM2SessionResponse(str4, bArr, bArrMakeNTLM2RandomChallenge);
                    this.lmResp = bArrMakeNTLM2RandomChallenge;
                } else {
                    this.ntResp = NTLMEngineImpl.getNTLMResponse(str4, bArr);
                    this.lmResp = NTLMEngineImpl.getLMResponse(str4, bArr);
                }
            } catch (NTLMEngineException unused) {
                this.ntResp = new byte[0];
                this.lmResp = NTLMEngineImpl.getLMResponse(str4, bArr);
            }
            try {
                this.domainBytes = strConvertDomain.toUpperCase().getBytes("UnicodeLittleUnmarked");
                this.hostBytes = strConvertHost.getBytes("UnicodeLittleUnmarked");
                this.userBytes = str3.getBytes("UnicodeLittleUnmarked");
            } catch (UnsupportedEncodingException e2) {
                throw new NTLMEngineException("Unicode not supported: " + e2.getMessage(), e2);
            }
        }

        @Override // org.apache.http.impl.auth.NTLMEngineImpl.NTLMMessage
        public String getResponse() {
            int length = this.ntResp.length;
            int length2 = this.lmResp.length;
            int length3 = this.domainBytes.length;
            int length4 = this.hostBytes.length;
            int length5 = this.userBytes.length;
            int i2 = length2 + 64;
            int i3 = i2 + length;
            int i4 = i3 + length3;
            int i5 = i4 + length5;
            int i6 = i5 + length4 + 0;
            prepareResponse(i6, 3);
            addUShort(length2);
            addUShort(length2);
            addULong(64);
            addUShort(length);
            addUShort(length);
            addULong(i2);
            addUShort(length3);
            addUShort(length3);
            addULong(i3);
            addUShort(length5);
            addUShort(length5);
            addULong(i4);
            addUShort(length4);
            addUShort(length4);
            addULong(i5);
            addULong(0);
            addULong(i6);
            int i7 = this.type2Flags;
            addULong((i7 & 32768) | (524288 & i7) | 536871429 | (i7 & 16) | (i7 & 32) | (1073741824 & i7));
            addBytes(this.lmResp);
            addBytes(this.ntResp);
            addBytes(this.domainBytes);
            addBytes(this.userBytes);
            addBytes(this.hostBytes);
            return super.getResponse();
        }
    }

    static {
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception unused) {
            secureRandom = null;
        }
        RND_GEN = secureRandom;
        byte[] bytes = EncodingUtils.getBytes("NTLMSSP", "ASCII");
        byte[] bArr = new byte[bytes.length + 1];
        SIGNATURE = bArr;
        System.arraycopy(bytes, 0, bArr, 0, bytes.length);
        SIGNATURE[bytes.length] = 0;
    }

    public static int F(int i2, int i3, int i4) {
        return ((~i2) & i4) | (i3 & i2);
    }

    public static int G(int i2, int i3, int i4) {
        return (i2 & i4) | (i2 & i3) | (i3 & i4);
    }

    public static int H(int i2, int i3, int i4) {
        return (i2 ^ i3) ^ i4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String convertDomain(String str) {
        return stripDotSuffix(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String convertHost(String str) {
        return stripDotSuffix(str);
    }

    private static byte[] createBlob(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = {1, 1, 0, 0};
        byte[] bArr4 = {0, 0, 0, 0};
        byte[] bArr5 = {0, 0, 0, 0};
        long jCurrentTimeMillis = (System.currentTimeMillis() + 11644473600000L) * a.f7153q;
        byte[] bArr6 = new byte[8];
        for (int i2 = 0; i2 < 8; i2++) {
            bArr6[i2] = (byte) jCurrentTimeMillis;
            jCurrentTimeMillis >>>= 8;
        }
        byte[] bArr7 = new byte[bArr2.length + 28];
        System.arraycopy(bArr3, 0, bArr7, 0, 4);
        System.arraycopy(bArr4, 0, bArr7, 4, 4);
        System.arraycopy(bArr6, 0, bArr7, 8, 8);
        System.arraycopy(bArr, 0, bArr7, 16, 8);
        System.arraycopy(bArr5, 0, bArr7, 24, 4);
        System.arraycopy(bArr2, 0, bArr7, 28, bArr2.length);
        return bArr7;
    }

    private static Key createDESKey(byte[] bArr, int i2) {
        byte[] bArr2 = new byte[7];
        System.arraycopy(bArr, i2, bArr2, 0, 7);
        byte[] bArr3 = {bArr2[0], (byte) ((bArr2[0] << 7) | ((bArr2[1] & 255) >>> 1)), (byte) ((bArr2[1] << 6) | ((bArr2[2] & 255) >>> 2)), (byte) ((bArr2[2] << 5) | ((bArr2[3] & 255) >>> 3)), (byte) ((bArr2[3] << 4) | ((bArr2[4] & 255) >>> 4)), (byte) ((bArr2[4] << 3) | ((bArr2[5] & 255) >>> 5)), (byte) ((bArr2[5] << 2) | ((bArr2[6] & 255) >>> 6)), (byte) (bArr2[6] << 1)};
        oddParity(bArr3);
        return new SecretKeySpec(bArr3, "DES");
    }

    public static byte[] getLMResponse(String str, byte[] bArr) throws NTLMEngineException {
        return lmResponse(lmHash(str), bArr);
    }

    public static byte[] getLMv2Response(String str, String str2, String str3, byte[] bArr, byte[] bArr2) throws NTLMEngineException {
        return lmv2Response(ntlmv2Hash(str, str2, str3), bArr, bArr2);
    }

    public static byte[] getNTLM2SessionResponse(String str, byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, NTLMEngineException {
        try {
            byte[] bArrNtlmHash = ntlmHash(str);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            messageDigest.update(bArr2);
            byte[] bArrDigest = messageDigest.digest();
            byte[] bArr3 = new byte[8];
            System.arraycopy(bArrDigest, 0, bArr3, 0, 8);
            return lmResponse(bArrNtlmHash, bArr3);
        } catch (Exception e2) {
            if (e2 instanceof NTLMEngineException) {
                throw ((NTLMEngineException) e2);
            }
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    public static byte[] getNTLMResponse(String str, byte[] bArr) throws NTLMEngineException {
        return lmResponse(ntlmHash(str), bArr);
    }

    public static byte[] getNTLMv2Response(String str, String str2, String str3, byte[] bArr, byte[] bArr2, byte[] bArr3) throws NTLMEngineException {
        return lmv2Response(ntlmv2Hash(str, str2, str3), bArr, createBlob(bArr2, bArr3));
    }

    private static byte[] lmHash(String str) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, NTLMEngineException, UnsupportedEncodingException {
        try {
            byte[] bytes = str.toUpperCase().getBytes("US-ASCII");
            byte[] bArr = new byte[14];
            System.arraycopy(bytes, 0, bArr, 0, Math.min(bytes.length, 14));
            Key keyCreateDESKey = createDESKey(bArr, 0);
            Key keyCreateDESKey2 = createDESKey(bArr, 7);
            byte[] bytes2 = "KGS!@#$%".getBytes("US-ASCII");
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, keyCreateDESKey);
            byte[] bArrDoFinal = cipher.doFinal(bytes2);
            cipher.init(1, keyCreateDESKey2);
            byte[] bArrDoFinal2 = cipher.doFinal(bytes2);
            byte[] bArr2 = new byte[16];
            System.arraycopy(bArrDoFinal, 0, bArr2, 0, 8);
            System.arraycopy(bArrDoFinal2, 0, bArr2, 8, 8);
            return bArr2;
        } catch (Exception e2) {
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    private static byte[] lmResponse(byte[] bArr, byte[] bArr2) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, NTLMEngineException {
        try {
            byte[] bArr3 = new byte[21];
            System.arraycopy(bArr, 0, bArr3, 0, 16);
            Key keyCreateDESKey = createDESKey(bArr3, 0);
            Key keyCreateDESKey2 = createDESKey(bArr3, 7);
            Key keyCreateDESKey3 = createDESKey(bArr3, 14);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, keyCreateDESKey);
            byte[] bArrDoFinal = cipher.doFinal(bArr2);
            cipher.init(1, keyCreateDESKey2);
            byte[] bArrDoFinal2 = cipher.doFinal(bArr2);
            cipher.init(1, keyCreateDESKey3);
            byte[] bArrDoFinal3 = cipher.doFinal(bArr2);
            byte[] bArr4 = new byte[24];
            System.arraycopy(bArrDoFinal, 0, bArr4, 0, 8);
            System.arraycopy(bArrDoFinal2, 0, bArr4, 8, 8);
            System.arraycopy(bArrDoFinal3, 0, bArr4, 16, 8);
            return bArr4;
        } catch (Exception e2) {
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    private static byte[] lmv2Response(byte[] bArr, byte[] bArr2, byte[] bArr3) throws NTLMEngineException {
        HMACMD5 hmacmd5 = new HMACMD5(bArr);
        hmacmd5.update(bArr2);
        hmacmd5.update(bArr3);
        byte[] output = hmacmd5.getOutput();
        byte[] bArr4 = new byte[output.length + bArr3.length];
        System.arraycopy(output, 0, bArr4, 0, output.length);
        System.arraycopy(bArr3, 0, bArr4, output.length, bArr3.length);
        return bArr4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] makeNTLM2RandomChallenge() throws NTLMEngineException {
        SecureRandom secureRandom = RND_GEN;
        if (secureRandom == null) {
            throw new NTLMEngineException("Random generator not available");
        }
        byte[] bArr = new byte[24];
        synchronized (secureRandom) {
            secureRandom.nextBytes(bArr);
        }
        Arrays.fill(bArr, 8, 24, (byte) 0);
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] makeRandomChallenge() throws NTLMEngineException {
        SecureRandom secureRandom = RND_GEN;
        if (secureRandom == null) {
            throw new NTLMEngineException("Random generator not available");
        }
        byte[] bArr = new byte[8];
        synchronized (secureRandom) {
            secureRandom.nextBytes(bArr);
        }
        return bArr;
    }

    private static byte[] ntlmHash(String str) throws NTLMEngineException, UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("UnicodeLittleUnmarked");
            MD4 md4 = new MD4();
            md4.update(bytes);
            return md4.getOutput();
        } catch (UnsupportedEncodingException e2) {
            throw new NTLMEngineException("Unicode not supported: " + e2.getMessage(), e2);
        }
    }

    private static byte[] ntlmv2Hash(String str, String str2, String str3) throws NTLMEngineException {
        try {
            HMACMD5 hmacmd5 = new HMACMD5(ntlmHash(str3));
            hmacmd5.update(str2.toUpperCase().getBytes("UnicodeLittleUnmarked"));
            hmacmd5.update(str.getBytes("UnicodeLittleUnmarked"));
            return hmacmd5.getOutput();
        } catch (UnsupportedEncodingException e2) {
            throw new NTLMEngineException("Unicode not supported! " + e2.getMessage(), e2);
        }
    }

    private static void oddParity(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b3 = bArr[i2];
            if (((((((((b3 >>> 7) ^ (b3 >>> 6)) ^ (b3 >>> 5)) ^ (b3 >>> 4)) ^ (b3 >>> 3)) ^ (b3 >>> 2)) ^ (b3 >>> 1)) & 1) == 0) {
                bArr[i2] = (byte) (b3 | 1);
            } else {
                bArr[i2] = (byte) (b3 & (-2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] readSecurityBuffer(byte[] bArr, int i2) throws NTLMEngineException {
        int uShort = readUShort(bArr, i2);
        int uLong = readULong(bArr, i2 + 4);
        if (bArr.length < uLong + uShort) {
            throw new NTLMEngineException("NTLM authentication - buffer too small for data item");
        }
        byte[] bArr2 = new byte[uShort];
        System.arraycopy(bArr, uLong, bArr2, 0, uShort);
        return bArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int readULong(byte[] bArr, int i2) throws NTLMEngineException {
        if (bArr.length < i2 + 4) {
            throw new NTLMEngineException("NTLM authentication - buffer too small for DWORD");
        }
        return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int readUShort(byte[] bArr, int i2) throws NTLMEngineException {
        if (bArr.length < i2 + 2) {
            throw new NTLMEngineException("NTLM authentication - buffer too small for WORD");
        }
        return ((bArr[i2 + 1] & 255) << 8) | (bArr[i2] & 255);
    }

    public static int rotintlft(int i2, int i3) {
        return (i2 >>> (32 - i3)) | (i2 << i3);
    }

    private static String stripDotSuffix(String str) {
        int iIndexOf = str.indexOf(StrPool.DOT);
        return iIndexOf != -1 ? str.substring(0, iIndexOf) : str;
    }

    public static void writeULong(byte[] bArr, int i2, int i3) {
        bArr[i3] = (byte) (i2 & 255);
        bArr[i3 + 1] = (byte) ((i2 >> 8) & 255);
        bArr[i3 + 2] = (byte) ((i2 >> 16) & 255);
        bArr[i3 + 3] = (byte) ((i2 >> 24) & 255);
    }

    @Override // org.apache.http.impl.auth.NTLMEngine
    public String generateType1Msg(String str, String str2) throws NTLMEngineException {
        return getType1Message(str2, str);
    }

    @Override // org.apache.http.impl.auth.NTLMEngine
    public String generateType3Msg(String str, String str2, String str3, String str4, String str5) throws NTLMEngineException {
        Type2Message type2Message = new Type2Message(str5);
        return getType3Message(str, str2, str4, str3, type2Message.getChallenge(), type2Message.getFlags(), type2Message.getTarget(), type2Message.getTargetInfo());
    }

    public String getCredentialCharset() {
        return this.credentialCharset;
    }

    public final String getResponseFor(String str, String str2, String str3, String str4, String str5) throws NTLMEngineException {
        if (str == null || str.trim().equals("")) {
            return getType1Message(str4, str5);
        }
        Type2Message type2Message = new Type2Message(str);
        return getType3Message(str2, str3, str4, str5, type2Message.getChallenge(), type2Message.getFlags(), type2Message.getTarget(), type2Message.getTargetInfo());
    }

    public String getType1Message(String str, String str2) throws NTLMEngineException {
        return new Type1Message(str2, str).getResponse();
    }

    public String getType3Message(String str, String str2, String str3, String str4, byte[] bArr, int i2, String str5, byte[] bArr2) throws NTLMEngineException {
        return new Type3Message(str4, str3, str, str2, bArr, i2, str5, bArr2).getResponse();
    }

    public void setCredentialCharset(String str) {
        this.credentialCharset = str;
    }

    public static class NTLMMessage {
        private int currentOutputPosition;
        private byte[] messageContents;

        public NTLMMessage() {
            this.messageContents = null;
            this.currentOutputPosition = 0;
        }

        public void addByte(byte b3) {
            byte[] bArr = this.messageContents;
            int i2 = this.currentOutputPosition;
            bArr[i2] = b3;
            this.currentOutputPosition = i2 + 1;
        }

        public void addBytes(byte[] bArr) {
            for (byte b3 : bArr) {
                byte[] bArr2 = this.messageContents;
                int i2 = this.currentOutputPosition;
                bArr2[i2] = b3;
                this.currentOutputPosition = i2 + 1;
            }
        }

        public void addULong(int i2) {
            addByte((byte) (i2 & 255));
            addByte((byte) ((i2 >> 8) & 255));
            addByte((byte) ((i2 >> 16) & 255));
            addByte((byte) ((i2 >> 24) & 255));
        }

        public void addUShort(int i2) {
            addByte((byte) (i2 & 255));
            addByte((byte) ((i2 >> 8) & 255));
        }

        public int getMessageLength() {
            return this.currentOutputPosition;
        }

        public int getPreambleLength() {
            return NTLMEngineImpl.SIGNATURE.length + 4;
        }

        public String getResponse() {
            byte[] bArr = this.messageContents;
            int length = bArr.length;
            int i2 = this.currentOutputPosition;
            if (length > i2) {
                bArr = new byte[i2];
                for (int i3 = 0; i3 < this.currentOutputPosition; i3++) {
                    bArr[i3] = this.messageContents[i3];
                }
            }
            return EncodingUtils.getAsciiString(Base64.encodeBase64(bArr));
        }

        public void prepareResponse(int i2, int i3) {
            this.messageContents = new byte[i2];
            this.currentOutputPosition = 0;
            addBytes(NTLMEngineImpl.SIGNATURE);
            addULong(i3);
        }

        public byte readByte(int i2) throws NTLMEngineException {
            byte[] bArr = this.messageContents;
            if (bArr.length >= i2 + 1) {
                return bArr[i2];
            }
            throw new NTLMEngineException("NTLM: Message too short");
        }

        public void readBytes(byte[] bArr, int i2) throws NTLMEngineException {
            byte[] bArr2 = this.messageContents;
            if (bArr2.length < bArr.length + i2) {
                throw new NTLMEngineException("NTLM: Message too short");
            }
            System.arraycopy(bArr2, i2, bArr, 0, bArr.length);
        }

        public byte[] readSecurityBuffer(int i2) throws NTLMEngineException {
            return NTLMEngineImpl.readSecurityBuffer(this.messageContents, i2);
        }

        public int readULong(int i2) throws NTLMEngineException {
            return NTLMEngineImpl.readULong(this.messageContents, i2);
        }

        public int readUShort(int i2) throws NTLMEngineException {
            return NTLMEngineImpl.readUShort(this.messageContents, i2);
        }

        public NTLMMessage(String str, int i2) throws NTLMEngineException {
            this.messageContents = null;
            this.currentOutputPosition = 0;
            byte[] bArrDecodeBase64 = Base64.decodeBase64(EncodingUtils.getBytes(str, "ASCII"));
            this.messageContents = bArrDecodeBase64;
            if (bArrDecodeBase64.length >= NTLMEngineImpl.SIGNATURE.length) {
                for (int i3 = 0; i3 < NTLMEngineImpl.SIGNATURE.length; i3++) {
                    if (this.messageContents[i3] != NTLMEngineImpl.SIGNATURE[i3]) {
                        throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");
                    }
                }
                int uLong = readULong(NTLMEngineImpl.SIGNATURE.length);
                if (uLong == i2) {
                    this.currentOutputPosition = this.messageContents.length;
                    return;
                }
                throw new NTLMEngineException("NTLM type " + Integer.toString(i2) + " message expected - instead got type " + Integer.toString(uLong));
            }
            throw new NTLMEngineException("NTLM message decoding error - packet too short");
        }
    }
}
