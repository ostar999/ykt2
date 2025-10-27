package cn.hutool.crypto.symmetric;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes.dex */
public class DESede extends SymmetricCrypto {
    private static final long serialVersionUID = 1;

    public DESede() {
        super(SymmetricAlgorithm.DESede);
    }

    public DESede(byte[] bArr) {
        super(SymmetricAlgorithm.DESede, bArr);
    }

    public DESede(Mode mode, Padding padding) {
        this(mode.name(), padding.name());
    }

    public DESede(Mode mode, Padding padding, byte[] bArr) {
        this(mode, padding, bArr, (byte[]) null);
    }

    public DESede(Mode mode, Padding padding, byte[] bArr, byte[] bArr2) {
        this(mode.name(), padding.name(), bArr, bArr2);
    }

    public DESede(Mode mode, Padding padding, SecretKey secretKey) {
        this(mode, padding, secretKey, (IvParameterSpec) null);
    }

    public DESede(Mode mode, Padding padding, SecretKey secretKey, IvParameterSpec ivParameterSpec) {
        this(mode.name(), padding.name(), secretKey, ivParameterSpec);
    }

    public DESede(String str, String str2) {
        this(str, str2, (byte[]) null);
    }

    public DESede(String str, String str2, byte[] bArr) {
        this(str, str2, bArr, (byte[]) null);
    }

    public DESede(String str, String str2, byte[] bArr, byte[] bArr2) {
        this(str, str2, SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue(), bArr), bArr2 == null ? null : new IvParameterSpec(bArr2));
    }

    public DESede(String str, String str2, SecretKey secretKey) {
        this(str, str2, secretKey, (IvParameterSpec) null);
    }

    public DESede(String str, String str2, SecretKey secretKey, IvParameterSpec ivParameterSpec) {
        super(CharSequenceUtil.format("{}/{}/{}", SymmetricAlgorithm.DESede.getValue(), str, str2), secretKey, ivParameterSpec);
    }
}
