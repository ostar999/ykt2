package cn.hutool.core.lang.generator;

import cn.hutool.core.lang.Snowflake;

/* loaded from: classes.dex */
public class SnowflakeGenerator implements Generator<Long> {
    private final Snowflake snowflake;

    public SnowflakeGenerator() {
        this(0L, 0L);
    }

    public SnowflakeGenerator(long j2, long j3) {
        this.snowflake = new Snowflake(j2, j3);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.lang.generator.Generator
    public Long next() {
        return Long.valueOf(this.snowflake.nextId());
    }
}
