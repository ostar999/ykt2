package cn.hutool.crypto.digest;

import cn.hutool.crypto.SecureUtil;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class DigesterFactory {
    private final boolean cloneSupport;
    private final MessageDigest prototype;

    private DigesterFactory(MessageDigest messageDigest) {
        this.prototype = messageDigest;
        this.cloneSupport = checkCloneSupport(messageDigest);
    }

    private static boolean checkCloneSupport(MessageDigest messageDigest) throws CloneNotSupportedException {
        try {
            messageDigest.clone();
            return true;
        } catch (CloneNotSupportedException unused) {
            return false;
        }
    }

    public static DigesterFactory of(String str) {
        return of(SecureUtil.createMessageDigest(str));
    }

    public static DigesterFactory ofJdk(String str) {
        return of(SecureUtil.createJdkMessageDigest(str));
    }

    public Digester createDigester() {
        return new Digester(createMessageDigester());
    }

    public MessageDigest createMessageDigester() {
        if (this.cloneSupport) {
            try {
                return (MessageDigest) this.prototype.clone();
            } catch (CloneNotSupportedException unused) {
            }
        }
        return SecureUtil.createJdkMessageDigest(this.prototype.getAlgorithm());
    }

    public static DigesterFactory of(MessageDigest messageDigest) {
        return new DigesterFactory(messageDigest);
    }
}
