package cn.hutool.crypto.digest.otp;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;

/* loaded from: classes.dex */
public class TOTP extends HOTP {
    public static final Duration DEFAULT_TIME_STEP = Duration.ofSeconds(30);
    private final Duration timeStep;

    public TOTP(byte[] bArr) {
        this(DEFAULT_TIME_STEP, bArr);
    }

    public static String generateGoogleSecretKey(String str, int i2) {
        return CharSequenceUtil.format("otpauth://totp/{}?secret={}", str, HOTP.generateSecretKey(i2));
    }

    public int generate(Instant instant) {
        return generate(instant.toEpochMilli() / this.timeStep.toMillis());
    }

    public Duration getTimeStep() {
        return this.timeStep;
    }

    public boolean validate(Instant instant, int i2, int i3) {
        if (i2 == 0) {
            return generate(instant) == i3;
        }
        for (int i4 = -i2; i4 <= i2; i4++) {
            if (generate(instant.plus((TemporalAmount) getTimeStep().multipliedBy(i4))) == i3) {
                return true;
            }
        }
        return false;
    }

    public TOTP(Duration duration, byte[] bArr) {
        this(duration, 6, bArr);
    }

    public TOTP(Duration duration, int i2, byte[] bArr) {
        this(duration, i2, HOTP.HOTP_HMAC_ALGORITHM, bArr);
    }

    public TOTP(Duration duration, int i2, HmacAlgorithm hmacAlgorithm, byte[] bArr) {
        super(i2, hmacAlgorithm, bArr);
        this.timeStep = duration;
    }
}
