package io.socket.backo;

import com.heytap.mcssdk.constant.a;
import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes8.dex */
public class Backoff {
    private int attempts;
    private double jitter;
    private long ms = 100;
    private long max = a.f7153q;
    private int factor = 2;

    public long duration() {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(this.ms);
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(this.factor);
        int i2 = this.attempts;
        this.attempts = i2 + 1;
        BigInteger bigIntegerMultiply = bigIntegerValueOf.multiply(bigIntegerValueOf2.pow(i2));
        if (this.jitter != 0.0d) {
            double dRandom = Math.random();
            BigInteger bigInteger = BigDecimal.valueOf(dRandom).multiply(BigDecimal.valueOf(this.jitter)).multiply(new BigDecimal(bigIntegerMultiply)).toBigInteger();
            bigIntegerMultiply = (((int) Math.floor(dRandom * 10.0d)) & 1) == 0 ? bigIntegerMultiply.subtract(bigInteger) : bigIntegerMultiply.add(bigInteger);
        }
        return bigIntegerMultiply.min(BigInteger.valueOf(this.max)).longValue();
    }

    public int getAttempts() {
        return this.attempts;
    }

    public void reset() {
        this.attempts = 0;
    }

    public Backoff setFactor(int i2) {
        this.factor = i2;
        return this;
    }

    public Backoff setJitter(double d3) {
        this.jitter = d3;
        return this;
    }

    public Backoff setMax(long j2) {
        this.max = j2;
        return this;
    }

    public Backoff setMin(long j2) {
        this.ms = j2;
        return this;
    }
}
