package cn.hutool.core.net;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.HexUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.BitSet;
import kotlin.text.Typography;

@Deprecated
/* loaded from: classes.dex */
public class URLEncoder implements Serializable {
    private static final long serialVersionUID = 1;
    private boolean encodeSpaceAsPlus;
    private final BitSet safeCharacters;
    public static final URLEncoder DEFAULT = createDefault();
    public static final URLEncoder PATH_SEGMENT = createPathSegment();
    public static final URLEncoder FRAGMENT = createFragment();
    public static final URLEncoder QUERY = createQuery();
    public static final URLEncoder ALL = createAll();

    public URLEncoder() {
        this(new BitSet(256));
        addAlpha();
        addDigit();
    }

    private void addAlpha() {
        for (char c3 = 'a'; c3 <= 'z'; c3 = (char) (c3 + 1)) {
            addSafeCharacter(c3);
        }
        for (char c4 = 'A'; c4 <= 'Z'; c4 = (char) (c4 + 1)) {
            addSafeCharacter(c4);
        }
    }

    private void addDigit() {
        for (char c3 = '0'; c3 <= '9'; c3 = (char) (c3 + 1)) {
            addSafeCharacter(c3);
        }
    }

    private static void addSubDelims(URLEncoder uRLEncoder) {
        uRLEncoder.addSafeCharacter('!');
        uRLEncoder.addSafeCharacter(Typography.dollar);
        uRLEncoder.addSafeCharacter('&');
        uRLEncoder.addSafeCharacter(CharPool.SINGLE_QUOTE);
        uRLEncoder.addSafeCharacter('(');
        uRLEncoder.addSafeCharacter(')');
        uRLEncoder.addSafeCharacter('*');
        uRLEncoder.addSafeCharacter('+');
        uRLEncoder.addSafeCharacter(',');
        uRLEncoder.addSafeCharacter(';');
        uRLEncoder.addSafeCharacter('=');
    }

    public static URLEncoder createAll() {
        URLEncoder uRLEncoder = new URLEncoder();
        uRLEncoder.addSafeCharacter('*');
        uRLEncoder.addSafeCharacter(CharPool.DASHED);
        uRLEncoder.addSafeCharacter('.');
        uRLEncoder.addSafeCharacter('_');
        return uRLEncoder;
    }

    public static URLEncoder createDefault() {
        URLEncoder uRLEncoder = new URLEncoder();
        uRLEncoder.addSafeCharacter(CharPool.DASHED);
        uRLEncoder.addSafeCharacter('.');
        uRLEncoder.addSafeCharacter('_');
        uRLEncoder.addSafeCharacter('~');
        addSubDelims(uRLEncoder);
        uRLEncoder.addSafeCharacter(':');
        uRLEncoder.addSafeCharacter('@');
        uRLEncoder.addSafeCharacter('/');
        return uRLEncoder;
    }

    public static URLEncoder createFragment() {
        URLEncoder uRLEncoder = new URLEncoder();
        uRLEncoder.addSafeCharacter(CharPool.DASHED);
        uRLEncoder.addSafeCharacter('.');
        uRLEncoder.addSafeCharacter('_');
        uRLEncoder.addSafeCharacter('~');
        addSubDelims(uRLEncoder);
        uRLEncoder.addSafeCharacter(':');
        uRLEncoder.addSafeCharacter('@');
        uRLEncoder.addSafeCharacter('/');
        uRLEncoder.addSafeCharacter('?');
        return uRLEncoder;
    }

    public static URLEncoder createPathSegment() {
        URLEncoder uRLEncoder = new URLEncoder();
        uRLEncoder.addSafeCharacter(CharPool.DASHED);
        uRLEncoder.addSafeCharacter('.');
        uRLEncoder.addSafeCharacter('_');
        uRLEncoder.addSafeCharacter('~');
        addSubDelims(uRLEncoder);
        uRLEncoder.addSafeCharacter('@');
        return uRLEncoder;
    }

    public static URLEncoder createQuery() {
        URLEncoder uRLEncoder = new URLEncoder();
        uRLEncoder.setEncodeSpaceAsPlus(true);
        uRLEncoder.addSafeCharacter('*');
        uRLEncoder.addSafeCharacter(CharPool.DASHED);
        uRLEncoder.addSafeCharacter('.');
        uRLEncoder.addSafeCharacter('_');
        uRLEncoder.addSafeCharacter('=');
        uRLEncoder.addSafeCharacter('&');
        return uRLEncoder;
    }

    public void addSafeCharacter(char c3) {
        this.safeCharacters.set(c3);
    }

    public String encode(String str, Charset charset) throws IOException {
        if (charset == null || CharSequenceUtil.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, charset);
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (this.safeCharacters.get(cCharAt)) {
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

    public void removeSafeCharacter(char c3) {
        this.safeCharacters.clear(c3);
    }

    public void setEncodeSpaceAsPlus(boolean z2) {
        this.encodeSpaceAsPlus = z2;
    }

    private URLEncoder(BitSet bitSet) {
        this.encodeSpaceAsPlus = false;
        this.safeCharacters = bitSet;
    }
}
