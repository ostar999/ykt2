package cn.hutool.crypto.symmetric.fpe;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import java.io.Serializable;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.crypto.AlphabetMapper;
import org.bouncycastle.jcajce.spec.FPEParameterSpec;

/* loaded from: classes.dex */
public class FPE implements Serializable {
    private static final long serialVersionUID = 1;
    private final AES aes;
    private final AlphabetMapper mapper;

    /* renamed from: cn.hutool.crypto.symmetric.fpe.FPE$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$crypto$symmetric$fpe$FPE$FPEMode;

        static {
            int[] iArr = new int[FPEMode.values().length];
            $SwitchMap$cn$hutool$crypto$symmetric$fpe$FPE$FPEMode = iArr;
            try {
                iArr[FPEMode.FF1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$crypto$symmetric$fpe$FPE$FPEMode[FPEMode.FF3_1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum FPEMode {
        FF1("FF1"),
        FF3_1("FF3-1");

        private final String value;

        FPEMode(String str) {
            this.value = str;
        }

        public String getValue() {
            return this.value;
        }
    }

    public FPE(FPEMode fPEMode, byte[] bArr, AlphabetMapper alphabetMapper) {
        this(fPEMode, bArr, alphabetMapper, null);
    }

    public String decrypt(String str) {
        if (str == null) {
            return null;
        }
        return new String(decrypt(str.toCharArray()));
    }

    public String encrypt(String str) {
        if (str == null) {
            return null;
        }
        return new String(encrypt(str.toCharArray()));
    }

    public FPE(FPEMode fPEMode, byte[] bArr, AlphabetMapper alphabetMapper, byte[] bArr2) {
        fPEMode = fPEMode == null ? FPEMode.FF1 : fPEMode;
        if (bArr2 == null) {
            int i2 = AnonymousClass1.$SwitchMap$cn$hutool$crypto$symmetric$fpe$FPE$FPEMode[fPEMode.ordinal()];
            if (i2 == 1) {
                bArr2 = new byte[0];
            } else if (i2 == 2) {
                bArr2 = new byte[7];
            }
        }
        this.aes = new AES(fPEMode.value, Padding.NoPadding.name(), KeyUtil.generateKey(fPEMode.value, bArr), (AlgorithmParameterSpec) new FPEParameterSpec(alphabetMapper.getRadix(), bArr2));
        this.mapper = alphabetMapper;
    }

    public char[] decrypt(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        AlphabetMapper alphabetMapper = this.mapper;
        return alphabetMapper.convertToChars(this.aes.decrypt(alphabetMapper.convertToIndexes(cArr)));
    }

    public char[] encrypt(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        AlphabetMapper alphabetMapper = this.mapper;
        return alphabetMapper.convertToChars(this.aes.encrypt(alphabetMapper.convertToIndexes(cArr)));
    }
}
