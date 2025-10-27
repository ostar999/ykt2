package cn.hutool.crypto.symmetric;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.KeyUtil;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes.dex */
public class ZUC extends SymmetricCrypto {
    private static final long serialVersionUID = 1;

    /* renamed from: cn.hutool.crypto.symmetric.ZUC$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$crypto$symmetric$ZUC$ZUCAlgorithm;

        static {
            int[] iArr = new int[ZUCAlgorithm.values().length];
            $SwitchMap$cn$hutool$crypto$symmetric$ZUC$ZUCAlgorithm = iArr;
            try {
                iArr[ZUCAlgorithm.ZUC_128.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$crypto$symmetric$ZUC$ZUCAlgorithm[ZUCAlgorithm.ZUC_256.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum ZUCAlgorithm {
        ZUC_128("ZUC-128"),
        ZUC_256("ZUC-256");

        private final String value;

        ZUCAlgorithm(String str) {
            this.value = str;
        }

        public String getValue() {
            return this.value;
        }
    }

    public ZUC(ZUCAlgorithm zUCAlgorithm, byte[] bArr, byte[] bArr2) {
        super(zUCAlgorithm.value, KeyUtil.generateKey(zUCAlgorithm.value, bArr), generateIvParam(zUCAlgorithm, bArr2));
    }

    private static IvParameterSpec generateIvParam(ZUCAlgorithm zUCAlgorithm, byte[] bArr) {
        if (bArr == null) {
            int i2 = AnonymousClass1.$SwitchMap$cn$hutool$crypto$symmetric$ZUC$ZUCAlgorithm[zUCAlgorithm.ordinal()];
            if (i2 == 1) {
                bArr = RandomUtil.randomBytes(16);
            } else if (i2 == 2) {
                bArr = RandomUtil.randomBytes(25);
            }
        }
        return new IvParameterSpec(bArr);
    }

    public static byte[] generateKey(ZUCAlgorithm zUCAlgorithm) {
        return KeyUtil.generateKey(zUCAlgorithm.value).getEncoded();
    }
}
