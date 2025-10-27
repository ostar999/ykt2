package org.bouncycastle.crypto.engines;

import android.R;
import androidx.core.view.InputDeviceCompat;
import com.google.common.base.Ascii;
import com.umeng.commonsdk.internal.a;
import com.yikaobang.yixue.R2;
import kotlin.io.encoding.Base64;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes9.dex */
public class DESEngine implements BlockCipher {
    protected static final int BLOCK_SIZE = 8;
    private int[] workingKey = null;
    private static final short[] bytebit = {128, 64, 32, 16, 8, 4, 2, 1};
    private static final int[] bigbyte = {8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1};
    private static final byte[] pc1 = {56, TarConstants.LF_NORMAL, 40, 32, Ascii.CAN, 16, 8, 0, 57, TarConstants.LF_LINK, 41, 33, Ascii.EM, 17, 9, 1, HttpTokens.COLON, TarConstants.LF_SYMLINK, 42, 34, Ascii.SUB, Ascii.DC2, 10, 2, HttpTokens.SEMI_COLON, TarConstants.LF_CHR, 43, 35, 62, TarConstants.LF_FIFO, 46, 38, Ascii.RS, 22, 14, 6, Base64.padSymbol, TarConstants.LF_DIR, 45, 37, Ascii.GS, 21, 13, 5, 60, TarConstants.LF_BLK, 44, 36, Ascii.FS, Ascii.DC4, 12, 4, Ascii.ESC, 19, 11, 3};
    private static final byte[] totrot = {1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, Ascii.EM, Ascii.ESC, Ascii.FS};
    private static final byte[] pc2 = {13, 16, 10, 23, 0, 4, 2, Ascii.ESC, 14, 5, Ascii.DC4, 9, 22, Ascii.DC2, 11, 3, Ascii.EM, 7, 15, 6, Ascii.SUB, 19, 12, 1, 40, TarConstants.LF_CHR, Ascii.RS, 36, 46, TarConstants.LF_FIFO, Ascii.GS, 39, TarConstants.LF_SYMLINK, 44, 32, 47, 43, TarConstants.LF_NORMAL, 38, TarConstants.LF_CONTIG, 33, TarConstants.LF_BLK, 45, 41, TarConstants.LF_LINK, 35, Ascii.FS, Ascii.US};
    private static final int[] SP1 = {R.attr.transitionName, 0, 65536, R.attr.fillColor, R.attr.manageSpaceActivity, 66564, 4, 65536, 1024, R.attr.transitionName, R.attr.fillColor, 1024, 16778244, R.attr.manageSpaceActivity, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, R.attr.theme, R.attr.theme, 16778244, InputDeviceCompat.SOURCE_TRACKBALL, 16777220, 16777220, InputDeviceCompat.SOURCE_TRACKBALL, 0, 1028, 66564, 16777216, 65536, R.attr.fillColor, 4, R.attr.theme, R.attr.transitionName, 16777216, 16777216, 1024, R.attr.manageSpaceActivity, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, R.attr.fillColor, InputDeviceCompat.SOURCE_TRACKBALL, R.attr.theme, 16778244, 16777220, 1028, 66564, R.attr.transitionName, 1028, 16778240, 16778240, 0, InputDeviceCompat.SOURCE_TRACKBALL, 66560, 0, R.attr.manageSpaceActivity};
    private static final int[] SP2 = {-2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, Integer.MIN_VALUE, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, Integer.MIN_VALUE, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, a.B, -2146402304, -2146435072, a.B, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, Integer.MIN_VALUE, a.B, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, a.B, Integer.MIN_VALUE, -2146435040, -2146402272, 1081344};
    private static final int[] SP3 = {R2.attr.bl_active_textColor, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, R2.attr.bl_active_textColor, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, R2.attr.bl_active_textColor, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, R2.attr.bl_active_textColor, 134348800, 131592, 8, 134348808, 131584};
    private static final int[] SP4 = {8396801, R2.drawable.abc_btn_check_to_on_mtrl_015, R2.drawable.abc_btn_check_to_on_mtrl_015, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, R2.drawable.abc_btn_check_to_on_mtrl_000, 8388737, 1, R2.drawable.abc_btn_check_to_on_mtrl_000, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, R2.drawable.abc_btn_check_to_on_mtrl_000, 8388736, 8388737, 1, 8396801, R2.drawable.abc_btn_check_to_on_mtrl_015, R2.drawable.abc_btn_check_to_on_mtrl_015, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, R2.drawable.abc_btn_check_to_on_mtrl_000, 8388608, 8396801, 128, 8388608, 8192, 8396928};
    private static final int[] SP5 = {256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080};
    private static final int[] SP6 = {536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, R2.id.ll_result, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, R2.id.ll_result, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, R2.id.ll_result, 536870928, 4194304, 536887296, 536870912, R2.id.ll_result, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312};
    private static final int[] SP7 = {2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154};
    private static final int[] SP8 = {268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, R2.color.alivc_common_bg_white_alpha_40, 266240, 262208, 268697664, 268701696, R2.color.alivc_common_bg_white_alpha_40, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, R2.color.alivc_common_bg_white_alpha_40, R2.color.alivc_common_bg_white_alpha_40, 262208, 268435456, 268701696};

    public void desFunc(int[] iArr, byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = ((bArr[i2 + 0] & 255) << 24) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8) | (bArr[i2 + 3] & 255);
        int i5 = ((bArr[i2 + 4] & 255) << 24) | ((bArr[i2 + 5] & 255) << 16) | ((bArr[i2 + 6] & 255) << 8) | (bArr[i2 + 7] & 255);
        int i6 = ((i4 >>> 4) ^ i5) & 252645135;
        int i7 = i5 ^ i6;
        int i8 = i4 ^ (i6 << 4);
        int i9 = ((i8 >>> 16) ^ i7) & 65535;
        int i10 = i7 ^ i9;
        int i11 = i8 ^ (i9 << 16);
        int i12 = ((i10 >>> 2) ^ i11) & 858993459;
        int i13 = i11 ^ i12;
        int i14 = i10 ^ (i12 << 2);
        int i15 = ((i14 >>> 8) ^ i13) & 16711935;
        int i16 = i13 ^ i15;
        int i17 = i14 ^ (i15 << 8);
        int i18 = (((i17 >>> 31) & 1) | (i17 << 1)) & (-1);
        int i19 = (i16 ^ i18) & (-1431655766);
        int i20 = i16 ^ i19;
        int i21 = i18 ^ i19;
        int i22 = (((i20 >>> 31) & 1) | (i20 << 1)) & (-1);
        for (int i23 = 0; i23 < 8; i23++) {
            int i24 = i23 * 4;
            int i25 = ((i21 << 28) | (i21 >>> 4)) ^ iArr[i24 + 0];
            int[] iArr2 = SP7;
            int i26 = iArr2[i25 & 63];
            int[] iArr3 = SP5;
            int i27 = i26 | iArr3[(i25 >>> 8) & 63];
            int[] iArr4 = SP3;
            int i28 = i27 | iArr4[(i25 >>> 16) & 63];
            int[] iArr5 = SP1;
            int i29 = iArr5[(i25 >>> 24) & 63] | i28;
            int i30 = iArr[i24 + 1] ^ i21;
            int[] iArr6 = SP8;
            int i31 = i29 | iArr6[i30 & 63];
            int[] iArr7 = SP6;
            int i32 = i31 | iArr7[(i30 >>> 8) & 63];
            int[] iArr8 = SP4;
            int i33 = i32 | iArr8[(i30 >>> 16) & 63];
            int[] iArr9 = SP2;
            i22 ^= i33 | iArr9[(i30 >>> 24) & 63];
            int i34 = ((i22 << 28) | (i22 >>> 4)) ^ iArr[i24 + 2];
            int i35 = iArr5[(i34 >>> 24) & 63] | iArr2[i34 & 63] | iArr3[(i34 >>> 8) & 63] | iArr4[(i34 >>> 16) & 63];
            int i36 = iArr[i24 + 3] ^ i22;
            i21 ^= (((i35 | iArr6[i36 & 63]) | iArr7[(i36 >>> 8) & 63]) | iArr8[(i36 >>> 16) & 63]) | iArr9[(i36 >>> 24) & 63];
        }
        int i37 = (i21 >>> 1) | (i21 << 31);
        int i38 = (i22 ^ i37) & (-1431655766);
        int i39 = i22 ^ i38;
        int i40 = i37 ^ i38;
        int i41 = (i39 >>> 1) | (i39 << 31);
        int i42 = ((i41 >>> 8) ^ i40) & 16711935;
        int i43 = i40 ^ i42;
        int i44 = i41 ^ (i42 << 8);
        int i45 = ((i44 >>> 2) ^ i43) & 858993459;
        int i46 = i43 ^ i45;
        int i47 = i44 ^ (i45 << 2);
        int i48 = ((i46 >>> 16) ^ i47) & 65535;
        int i49 = i47 ^ i48;
        int i50 = i46 ^ (i48 << 16);
        int i51 = ((i50 >>> 4) ^ i49) & 252645135;
        int i52 = i49 ^ i51;
        int i53 = i50 ^ (i51 << 4);
        bArr2[i3 + 0] = (byte) ((i53 >>> 24) & 255);
        bArr2[i3 + 1] = (byte) ((i53 >>> 16) & 255);
        bArr2[i3 + 2] = (byte) ((i53 >>> 8) & 255);
        bArr2[i3 + 3] = (byte) (i53 & 255);
        bArr2[i3 + 4] = (byte) ((i52 >>> 24) & 255);
        bArr2[i3 + 5] = (byte) ((i52 >>> 16) & 255);
        bArr2[i3 + 6] = (byte) ((i52 >>> 8) & 255);
        bArr2[i3 + 7] = (byte) (i52 & 255);
    }

    public int[] generateWorkingKey(boolean z2, byte[] bArr) {
        int i2;
        int[] iArr = new int[32];
        boolean[] zArr = new boolean[56];
        boolean[] zArr2 = new boolean[56];
        int i3 = 0;
        while (true) {
            boolean z3 = true;
            if (i3 >= 56) {
                break;
            }
            byte b3 = pc1[i3];
            if ((bytebit[b3 & 7] & bArr[b3 >>> 3]) == 0) {
                z3 = false;
            }
            zArr[i3] = z3;
            i3++;
        }
        for (int i4 = 0; i4 < 16; i4++) {
            int i5 = z2 ? i4 << 1 : (15 - i4) << 1;
            int i6 = i5 + 1;
            iArr[i6] = 0;
            iArr[i5] = 0;
            int i7 = 0;
            while (true) {
                if (i7 >= 28) {
                    break;
                }
                int i8 = totrot[i4] + i7;
                if (i8 < 28) {
                    zArr2[i7] = zArr[i8];
                } else {
                    zArr2[i7] = zArr[i8 - 28];
                }
                i7++;
            }
            for (i2 = 28; i2 < 56; i2++) {
                int i9 = totrot[i4] + i2;
                if (i9 < 56) {
                    zArr2[i2] = zArr[i9];
                } else {
                    zArr2[i2] = zArr[i9 - 28];
                }
            }
            for (int i10 = 0; i10 < 24; i10++) {
                byte[] bArr2 = pc2;
                if (zArr2[bArr2[i10]]) {
                    iArr[i5] = iArr[i5] | bigbyte[i10];
                }
                if (zArr2[bArr2[i10 + 24]]) {
                    iArr[i6] = iArr[i6] | bigbyte[i10];
                }
            }
        }
        for (int i11 = 0; i11 != 32; i11 += 2) {
            int i12 = iArr[i11];
            int i13 = i11 + 1;
            int i14 = iArr[i13];
            iArr[i11] = ((16515072 & i14) >>> 10) | ((i12 & 16515072) << 6) | ((i12 & R2.attr.zx_color_edit_score_bg) << 10) | ((i14 & R2.attr.zx_color_edit_score_bg) >>> 6);
            iArr[i13] = ((i12 & 63) << 16) | ((i12 & 258048) << 12) | ((258048 & i14) >>> 4) | (i14 & 63);
        }
        return iArr;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "DES";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to DES init - " + cipherParameters.getClass().getName());
        }
        KeyParameter keyParameter = (KeyParameter) cipherParameters;
        if (keyParameter.getKey().length > 8) {
            throw new IllegalArgumentException("DES key too long - should be 8 bytes");
        }
        this.workingKey = generateWorkingKey(z2, keyParameter.getKey());
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int[] iArr = this.workingKey;
        if (iArr == null) {
            throw new IllegalStateException("DES engine not initialised");
        }
        if (i2 + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 8 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        desFunc(iArr, bArr, i2, bArr2, i3);
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
