package cn.hutool.core.codec;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.BitSet;

/* loaded from: classes.dex */
public class PercentCodec implements Serializable {
    private static final long serialVersionUID = 1;
    private boolean encodeSpaceAsPlus;
    private final BitSet safeCharacters;

    public PercentCodec() {
        this(new BitSet(256));
    }

    public static PercentCodec of(PercentCodec percentCodec) {
        return new PercentCodec((BitSet) percentCodec.safeCharacters.clone());
    }

    public PercentCodec addSafe(char c3) {
        this.safeCharacters.set(c3);
        return this;
    }

    public String encode(CharSequence charSequence, Charset charset, char... cArr) throws IOException {
        if (charset == null || CharSequenceUtil.isEmpty(charSequence)) {
            return CharSequenceUtil.str(charSequence);
        }
        StringBuilder sb = new StringBuilder(charSequence.length());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, charset);
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            char cCharAt = charSequence.charAt(i2);
            if (this.safeCharacters.get(cCharAt) || PrimitiveArrayUtil.contains(cArr, cCharAt)) {
                sb.append(cCharAt);
            } else if (this.encodeSpaceAsPlus && cCharAt == ' ') {
                sb.append('+');
            } else {
                try {
                    outputStreamWriter.write(cCharAt);
                    outputStreamWriter.flush();
                    for (byte b3 : byteArrayOutputStream.toByteArray()) {
                        sb.append('%');
                        HexUtil.appendHex(sb, b3, false);
                    }
                    byteArrayOutputStream.reset();
                } catch (IOException unused) {
                    byteArrayOutputStream.reset();
                }
            }
        }
        return sb.toString();
    }

    public PercentCodec or(PercentCodec percentCodec) {
        this.safeCharacters.or(percentCodec.safeCharacters);
        return this;
    }

    public PercentCodec orNew(PercentCodec percentCodec) {
        return of(this).or(percentCodec);
    }

    public PercentCodec removeSafe(char c3) {
        this.safeCharacters.clear(c3);
        return this;
    }

    public PercentCodec setEncodeSpaceAsPlus(boolean z2) {
        this.encodeSpaceAsPlus = z2;
        return this;
    }

    public PercentCodec(BitSet bitSet) {
        this.encodeSpaceAsPlus = false;
        this.safeCharacters = bitSet;
    }

    public static PercentCodec of(CharSequence charSequence) throws IllegalArgumentException {
        Assert.notNull(charSequence, "chars must not be null", new Object[0]);
        PercentCodec percentCodec = new PercentCodec();
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            percentCodec.addSafe(charSequence.charAt(i2));
        }
        return percentCodec;
    }
}
