package cn.hutool.core.lang;

import cn.hutool.core.date.SystemClock;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import java.io.Serializable;
import java.util.Date;

/* loaded from: classes.dex */
public class Snowflake implements Serializable {
    private static final long DATA_CENTER_ID_BITS = 5;
    private static final long DATA_CENTER_ID_SHIFT = 17;
    public static long DEFAULT_TIME_OFFSET = 2000;
    public static long DEFAULT_TWEPOCH = 1288834974657L;
    private static final long MAX_DATA_CENTER_ID = 31;
    private static final long MAX_WORKER_ID = 31;
    private static final long SEQUENCE_BITS = 12;
    private static final long SEQUENCE_MASK = 4095;
    private static final long TIMESTAMP_LEFT_SHIFT = 22;
    private static final long WORKER_ID_BITS = 5;
    private static final long WORKER_ID_SHIFT = 12;
    private static final long serialVersionUID = 1;
    private final long dataCenterId;
    private long lastTimestamp;
    private final long randomSequenceLimit;
    private long sequence;
    private final long timeOffset;
    private final long twepoch;
    private final boolean useSystemClock;
    private final long workerId;

    public Snowflake() {
        this(IdUtil.getWorkerId(IdUtil.getDataCenterId(31L), 31L));
    }

    private long genTime() {
        return this.useSystemClock ? SystemClock.now() : System.currentTimeMillis();
    }

    private long tilNextMillis(long j2) {
        long jGenTime = genTime();
        while (jGenTime == j2) {
            jGenTime = genTime();
        }
        if (jGenTime >= j2) {
            return jGenTime;
        }
        throw new IllegalStateException(CharSequenceUtil.format("Clock moved backwards. Refusing to generate id for {}ms", Long.valueOf(j2 - jGenTime)));
    }

    public long getDataCenterId(long j2) {
        return (j2 >> DATA_CENTER_ID_SHIFT) & 31;
    }

    public long getGenerateDateTime(long j2) {
        return ((j2 >> TIMESTAMP_LEFT_SHIFT) & 2199023255551L) + this.twepoch;
    }

    public long getWorkerId(long j2) {
        return (j2 >> 12) & 31;
    }

    public synchronized long nextId() {
        long jGenTime;
        jGenTime = genTime();
        long j2 = this.lastTimestamp;
        if (jGenTime < j2) {
            if (j2 - jGenTime >= this.timeOffset) {
                throw new IllegalStateException(CharSequenceUtil.format("Clock moved backwards. Refusing to generate id for {}ms", Long.valueOf(this.lastTimestamp - jGenTime)));
            }
            jGenTime = j2;
        }
        if (jGenTime == j2) {
            long j3 = SEQUENCE_MASK & (this.sequence + 1);
            if (j3 == 0) {
                jGenTime = tilNextMillis(j2);
            }
            this.sequence = j3;
        } else {
            long j4 = this.randomSequenceLimit;
            if (j4 > 1) {
                this.sequence = RandomUtil.randomLong(j4);
            } else {
                this.sequence = 0L;
            }
        }
        this.lastTimestamp = jGenTime;
        return ((jGenTime - this.twepoch) << TIMESTAMP_LEFT_SHIFT) | (this.dataCenterId << DATA_CENTER_ID_SHIFT) | (this.workerId << 12) | this.sequence;
    }

    public String nextIdStr() {
        return Long.toString(nextId());
    }

    public Snowflake(long j2) {
        this(j2, IdUtil.getDataCenterId(31L));
    }

    public Snowflake(long j2, long j3) {
        this(j2, j3, false);
    }

    public Snowflake(long j2, long j3, boolean z2) {
        this(null, j2, j3, z2);
    }

    public Snowflake(Date date, long j2, long j3, boolean z2) {
        this(date, j2, j3, z2, DEFAULT_TIME_OFFSET);
    }

    public Snowflake(Date date, long j2, long j3, boolean z2, long j4) {
        this(date, j2, j3, z2, j4, 0L);
    }

    public Snowflake(Date date, long j2, long j3, boolean z2, long j4, long j5) {
        this.sequence = 0L;
        this.lastTimestamp = -1L;
        this.twepoch = date != null ? date.getTime() : DEFAULT_TWEPOCH;
        this.workerId = Assert.checkBetween(j2, 0L, 31L);
        this.dataCenterId = Assert.checkBetween(j3, 0L, 31L);
        this.useSystemClock = z2;
        this.timeOffset = j4;
        this.randomSequenceLimit = Assert.checkBetween(j5, 0L, SEQUENCE_MASK);
    }
}
