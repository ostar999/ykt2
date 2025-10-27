package cn.hutool.crypto.symmetric;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes.dex */
public class AES extends SymmetricCrypto {
    private static final long serialVersionUID = 1;

    public AES() {
        super(SymmetricAlgorithm.AES);
    }

    public AES(byte[] bArr) {
        super(SymmetricAlgorithm.AES, bArr);
    }

    public AES(SecretKey secretKey) {
        super(SymmetricAlgorithm.AES, secretKey);
    }

    public AES(Mode mode, Padding padding) {
        this(mode.name(), padding.name());
    }

    public AES(Mode mode, Padding padding, byte[] bArr) {
        this(mode, padding, bArr, (byte[]) null);
    }

    public AES(Mode mode, Padding padding, byte[] bArr, byte[] bArr2) {
        this(mode.name(), padding.name(), bArr, bArr2);
    }

    public AES(Mode mode, Padding padding, SecretKey secretKey) {
        this(mode, padding, secretKey, (AlgorithmParameterSpec) null);
    }

    public AES(Mode mode, Padding padding, SecretKey secretKey, byte[] bArr) {
        this(mode, padding, secretKey, PrimitiveArrayUtil.isEmpty(bArr) ? null : new IvParameterSpec(bArr));
    }

    public AES(Mode mode, Padding padding, SecretKey secretKey, AlgorithmParameterSpec algorithmParameterSpec) {
        this(mode.name(), padding.name(), secretKey, algorithmParameterSpec);
    }

    public AES(String str, String str2) {
        this(str, str2, (byte[]) null);
    }

    public AES(String str, String str2, byte[] bArr) {
        this(str, str2, bArr, (byte[]) null);
    }

    public AES(String str, String str2, byte[] bArr, byte[] bArr2) {
        this(str, str2, KeyUtil.generateKey(SymmetricAlgorithm.AES.getValue(), bArr), PrimitiveArrayUtil.isEmpty(bArr2) ? null : new IvParameterSpec(bArr2));
    }

    public AES(String str, String str2, SecretKey secretKey) {
        this(str, str2, secretKey, (AlgorithmParameterSpec) null);
    }

    public AES(String str, String str2, SecretKey secretKey, AlgorithmParameterSpec algorithmParameterSpec) {
        super(CharSequenceUtil.format("AES/{}/{}", str, str2), secretKey, algorithmParameterSpec);
    }
}
