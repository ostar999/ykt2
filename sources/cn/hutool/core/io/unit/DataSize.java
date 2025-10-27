package cn.hutool.core.io.unit;

import cn.hutool.core.date.format.f;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import z.a;

/* loaded from: classes.dex */
public final class DataSize implements Comparable<DataSize> {
    private static final long BYTES_PER_GB = 1073741824;
    private static final long BYTES_PER_KB = 1024;
    private static final long BYTES_PER_MB = 1048576;
    private static final long BYTES_PER_TB = 1099511627776L;
    private static final Pattern PATTERN = Pattern.compile("^([+-]?\\d+(\\.\\d+)?)([a-zA-Z]{0,2})$");
    private final long bytes;

    private DataSize(long j2) {
        this.bytes = j2;
    }

    private static DataUnit determineDataUnit(String str, DataUnit dataUnit) {
        if (dataUnit == null) {
            dataUnit = DataUnit.BYTES;
        }
        return CharSequenceUtil.isNotEmpty(str) ? DataUnit.fromSuffix(str) : dataUnit;
    }

    public static DataSize of(long j2, DataUnit dataUnit) {
        if (dataUnit == null) {
            dataUnit = DataUnit.BYTES;
        }
        return new DataSize(f.a(j2, dataUnit.size().toBytes()));
    }

    public static DataSize ofBytes(long j2) {
        return new DataSize(j2);
    }

    public static DataSize ofGigabytes(long j2) {
        return new DataSize(f.a(j2, 1073741824L));
    }

    public static DataSize ofKilobytes(long j2) {
        return new DataSize(f.a(j2, 1024L));
    }

    public static DataSize ofMegabytes(long j2) {
        return new DataSize(f.a(j2, 1048576L));
    }

    public static DataSize ofTerabytes(long j2) {
        return new DataSize(f.a(j2, 1099511627776L));
    }

    public static DataSize parse(CharSequence charSequence) {
        return parse(charSequence, null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && DataSize.class == obj.getClass() && this.bytes == ((DataSize) obj).bytes;
    }

    public int hashCode() {
        return a.a(this.bytes);
    }

    public boolean isNegative() {
        return this.bytes < 0;
    }

    public long toBytes() {
        return this.bytes;
    }

    public long toGigabytes() {
        return this.bytes / 1073741824;
    }

    public long toKilobytes() {
        return this.bytes / 1024;
    }

    public long toMegabytes() {
        return this.bytes / 1048576;
    }

    public String toString() {
        return String.format("%dB", Long.valueOf(this.bytes));
    }

    public long toTerabytes() {
        return this.bytes / 1099511627776L;
    }

    public static DataSize parse(CharSequence charSequence, DataUnit dataUnit) throws IllegalArgumentException {
        Assert.notNull(charSequence, "Text must not be null", new Object[0]);
        try {
            Matcher matcher = PATTERN.matcher(charSequence);
            Assert.state(matcher.matches(), "Does not match data size pattern", new Object[0]);
            return of(new BigDecimal(matcher.group(1)), determineDataUnit(matcher.group(3), dataUnit));
        } catch (Exception e2) {
            throw new IllegalArgumentException("'" + ((Object) charSequence) + "' is not a valid data size", e2);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(DataSize dataSize) {
        return Long.compare(this.bytes, dataSize.bytes);
    }

    public static DataSize of(BigDecimal bigDecimal, DataUnit dataUnit) {
        if (dataUnit == null) {
            dataUnit = DataUnit.BYTES;
        }
        return new DataSize(bigDecimal.multiply(new BigDecimal(dataUnit.size().toBytes())).longValue());
    }
}
