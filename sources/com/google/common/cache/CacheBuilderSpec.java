package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.cache.LocalCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
/* loaded from: classes4.dex */
public final class CacheBuilderSpec {
    private static final Splitter KEYS_SPLITTER = Splitter.on(',').trimResults();
    private static final Splitter KEY_VALUE_SPLITTER = Splitter.on('=').trimResults();
    private static final ImmutableMap<String, ValueParser> VALUE_PARSERS;

    @VisibleForTesting
    long accessExpirationDuration;

    @VisibleForTesting
    @MonotonicNonNullDecl
    TimeUnit accessExpirationTimeUnit;

    @VisibleForTesting
    @MonotonicNonNullDecl
    Integer concurrencyLevel;

    @VisibleForTesting
    @MonotonicNonNullDecl
    Integer initialCapacity;

    @VisibleForTesting
    @MonotonicNonNullDecl
    LocalCache.Strength keyStrength;

    @VisibleForTesting
    @MonotonicNonNullDecl
    Long maximumSize;

    @VisibleForTesting
    @MonotonicNonNullDecl
    Long maximumWeight;

    @VisibleForTesting
    @MonotonicNonNullDecl
    Boolean recordStats;

    @VisibleForTesting
    long refreshDuration;

    @VisibleForTesting
    @MonotonicNonNullDecl
    TimeUnit refreshTimeUnit;
    private final String specification;

    @VisibleForTesting
    @MonotonicNonNullDecl
    LocalCache.Strength valueStrength;

    @VisibleForTesting
    long writeExpirationDuration;

    @VisibleForTesting
    @MonotonicNonNullDecl
    TimeUnit writeExpirationTimeUnit;

    /* renamed from: com.google.common.cache.CacheBuilderSpec$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$cache$LocalCache$Strength;

        static {
            int[] iArr = new int[LocalCache.Strength.values().length];
            $SwitchMap$com$google$common$cache$LocalCache$Strength = iArr;
            try {
                iArr[LocalCache.Strength.WEAK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$common$cache$LocalCache$Strength[LocalCache.Strength.SOFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class AccessDurationParser extends DurationParser {
        @Override // com.google.common.cache.CacheBuilderSpec.DurationParser
        public void parseDuration(CacheBuilderSpec cacheBuilderSpec, long j2, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.accessExpirationTimeUnit == null, "expireAfterAccess already set");
            cacheBuilderSpec.accessExpirationDuration = j2;
            cacheBuilderSpec.accessExpirationTimeUnit = timeUnit;
        }
    }

    public static class ConcurrencyLevelParser extends IntegerParser {
        @Override // com.google.common.cache.CacheBuilderSpec.IntegerParser
        public void parseInteger(CacheBuilderSpec cacheBuilderSpec, int i2) {
            Integer num = cacheBuilderSpec.concurrencyLevel;
            Preconditions.checkArgument(num == null, "concurrency level was already set to ", num);
            cacheBuilderSpec.concurrencyLevel = Integer.valueOf(i2);
        }
    }

    public static abstract class DurationParser implements ValueParser {
        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            TimeUnit timeUnit;
            Preconditions.checkArgument((str2 == null || str2.isEmpty()) ? false : true, "value of key %s omitted", str);
            try {
                char cCharAt = str2.charAt(str2.length() - 1);
                if (cCharAt == 'd') {
                    timeUnit = TimeUnit.DAYS;
                } else if (cCharAt == 'h') {
                    timeUnit = TimeUnit.HOURS;
                } else if (cCharAt == 'm') {
                    timeUnit = TimeUnit.MINUTES;
                } else {
                    if (cCharAt != 's') {
                        throw new IllegalArgumentException(CacheBuilderSpec.format("key %s invalid format.  was %s, must end with one of [dDhHmMsS]", str, str2));
                    }
                    timeUnit = TimeUnit.SECONDS;
                }
                parseDuration(cacheBuilderSpec, Long.parseLong(str2.substring(0, str2.length() - 1)), timeUnit);
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", str, str2));
            }
        }

        public abstract void parseDuration(CacheBuilderSpec cacheBuilderSpec, long j2, TimeUnit timeUnit);
    }

    public static class InitialCapacityParser extends IntegerParser {
        @Override // com.google.common.cache.CacheBuilderSpec.IntegerParser
        public void parseInteger(CacheBuilderSpec cacheBuilderSpec, int i2) {
            Integer num = cacheBuilderSpec.initialCapacity;
            Preconditions.checkArgument(num == null, "initial capacity was already set to ", num);
            cacheBuilderSpec.initialCapacity = Integer.valueOf(i2);
        }
    }

    public static abstract class IntegerParser implements ValueParser {
        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            Preconditions.checkArgument((str2 == null || str2.isEmpty()) ? false : true, "value of key %s omitted", str);
            try {
                parseInteger(cacheBuilderSpec, Integer.parseInt(str2));
            } catch (NumberFormatException e2) {
                throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", str, str2), e2);
            }
        }

        public abstract void parseInteger(CacheBuilderSpec cacheBuilderSpec, int i2);
    }

    public static class KeyStrengthParser implements ValueParser {
        private final LocalCache.Strength strength;

        public KeyStrengthParser(LocalCache.Strength strength) {
            this.strength = strength;
        }

        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @NullableDecl String str2) {
            Preconditions.checkArgument(str2 == null, "key %s does not take values", str);
            LocalCache.Strength strength = cacheBuilderSpec.keyStrength;
            Preconditions.checkArgument(strength == null, "%s was already set to %s", str, strength);
            cacheBuilderSpec.keyStrength = this.strength;
        }
    }

    public static abstract class LongParser implements ValueParser {
        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            Preconditions.checkArgument((str2 == null || str2.isEmpty()) ? false : true, "value of key %s omitted", str);
            try {
                parseLong(cacheBuilderSpec, Long.parseLong(str2));
            } catch (NumberFormatException e2) {
                throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", str, str2), e2);
            }
        }

        public abstract void parseLong(CacheBuilderSpec cacheBuilderSpec, long j2);
    }

    public static class MaximumSizeParser extends LongParser {
        @Override // com.google.common.cache.CacheBuilderSpec.LongParser
        public void parseLong(CacheBuilderSpec cacheBuilderSpec, long j2) {
            Long l2 = cacheBuilderSpec.maximumSize;
            Preconditions.checkArgument(l2 == null, "maximum size was already set to ", l2);
            Long l3 = cacheBuilderSpec.maximumWeight;
            Preconditions.checkArgument(l3 == null, "maximum weight was already set to ", l3);
            cacheBuilderSpec.maximumSize = Long.valueOf(j2);
        }
    }

    public static class MaximumWeightParser extends LongParser {
        @Override // com.google.common.cache.CacheBuilderSpec.LongParser
        public void parseLong(CacheBuilderSpec cacheBuilderSpec, long j2) {
            Long l2 = cacheBuilderSpec.maximumWeight;
            Preconditions.checkArgument(l2 == null, "maximum weight was already set to ", l2);
            Long l3 = cacheBuilderSpec.maximumSize;
            Preconditions.checkArgument(l3 == null, "maximum size was already set to ", l3);
            cacheBuilderSpec.maximumWeight = Long.valueOf(j2);
        }
    }

    public static class RecordStatsParser implements ValueParser {
        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @NullableDecl String str2) {
            Preconditions.checkArgument(str2 == null, "recordStats does not take values");
            Preconditions.checkArgument(cacheBuilderSpec.recordStats == null, "recordStats already set");
            cacheBuilderSpec.recordStats = Boolean.TRUE;
        }
    }

    public static class RefreshDurationParser extends DurationParser {
        @Override // com.google.common.cache.CacheBuilderSpec.DurationParser
        public void parseDuration(CacheBuilderSpec cacheBuilderSpec, long j2, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.refreshTimeUnit == null, "refreshAfterWrite already set");
            cacheBuilderSpec.refreshDuration = j2;
            cacheBuilderSpec.refreshTimeUnit = timeUnit;
        }
    }

    public interface ValueParser {
        void parse(CacheBuilderSpec cacheBuilderSpec, String str, @NullableDecl String str2);
    }

    public static class ValueStrengthParser implements ValueParser {
        private final LocalCache.Strength strength;

        public ValueStrengthParser(LocalCache.Strength strength) {
            this.strength = strength;
        }

        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @NullableDecl String str2) {
            Preconditions.checkArgument(str2 == null, "key %s does not take values", str);
            LocalCache.Strength strength = cacheBuilderSpec.valueStrength;
            Preconditions.checkArgument(strength == null, "%s was already set to %s", str, strength);
            cacheBuilderSpec.valueStrength = this.strength;
        }
    }

    public static class WriteDurationParser extends DurationParser {
        @Override // com.google.common.cache.CacheBuilderSpec.DurationParser
        public void parseDuration(CacheBuilderSpec cacheBuilderSpec, long j2, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.writeExpirationTimeUnit == null, "expireAfterWrite already set");
            cacheBuilderSpec.writeExpirationDuration = j2;
            cacheBuilderSpec.writeExpirationTimeUnit = timeUnit;
        }
    }

    static {
        ImmutableMap.Builder builderPut = ImmutableMap.builder().put("initialCapacity", new InitialCapacityParser()).put("maximumSize", new MaximumSizeParser()).put("maximumWeight", new MaximumWeightParser()).put("concurrencyLevel", new ConcurrencyLevelParser());
        LocalCache.Strength strength = LocalCache.Strength.WEAK;
        VALUE_PARSERS = builderPut.put("weakKeys", new KeyStrengthParser(strength)).put("softValues", new ValueStrengthParser(LocalCache.Strength.SOFT)).put("weakValues", new ValueStrengthParser(strength)).put("recordStats", new RecordStatsParser()).put("expireAfterAccess", new AccessDurationParser()).put("expireAfterWrite", new WriteDurationParser()).put("refreshAfterWrite", new RefreshDurationParser()).put("refreshInterval", new RefreshDurationParser()).build();
    }

    private CacheBuilderSpec(String str) {
        this.specification = str;
    }

    public static CacheBuilderSpec disableCaching() {
        return parse("maximumSize=0");
    }

    @NullableDecl
    private static Long durationInNanos(long j2, @NullableDecl TimeUnit timeUnit) {
        if (timeUnit == null) {
            return null;
        }
        return Long.valueOf(timeUnit.toNanos(j2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String format(String str, Object... objArr) {
        return String.format(Locale.ROOT, str, objArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static CacheBuilderSpec parse(String str) {
        CacheBuilderSpec cacheBuilderSpec = new CacheBuilderSpec(str);
        if (!str.isEmpty()) {
            for (String str2 : KEYS_SPLITTER.split(str)) {
                ImmutableList immutableListCopyOf = ImmutableList.copyOf(KEY_VALUE_SPLITTER.split(str2));
                Preconditions.checkArgument(!immutableListCopyOf.isEmpty(), "blank key-value pair");
                Preconditions.checkArgument(immutableListCopyOf.size() <= 2, "key-value pair %s with more than one equals sign", str2);
                String str3 = (String) immutableListCopyOf.get(0);
                ValueParser valueParser = VALUE_PARSERS.get(str3);
                Preconditions.checkArgument(valueParser != null, "unknown key %s", str3);
                valueParser.parse(cacheBuilderSpec, str3, immutableListCopyOf.size() == 1 ? null : (String) immutableListCopyOf.get(1));
            }
        }
        return cacheBuilderSpec;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CacheBuilderSpec)) {
            return false;
        }
        CacheBuilderSpec cacheBuilderSpec = (CacheBuilderSpec) obj;
        return Objects.equal(this.initialCapacity, cacheBuilderSpec.initialCapacity) && Objects.equal(this.maximumSize, cacheBuilderSpec.maximumSize) && Objects.equal(this.maximumWeight, cacheBuilderSpec.maximumWeight) && Objects.equal(this.concurrencyLevel, cacheBuilderSpec.concurrencyLevel) && Objects.equal(this.keyStrength, cacheBuilderSpec.keyStrength) && Objects.equal(this.valueStrength, cacheBuilderSpec.valueStrength) && Objects.equal(this.recordStats, cacheBuilderSpec.recordStats) && Objects.equal(durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), durationInNanos(cacheBuilderSpec.writeExpirationDuration, cacheBuilderSpec.writeExpirationTimeUnit)) && Objects.equal(durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), durationInNanos(cacheBuilderSpec.accessExpirationDuration, cacheBuilderSpec.accessExpirationTimeUnit)) && Objects.equal(durationInNanos(this.refreshDuration, this.refreshTimeUnit), durationInNanos(cacheBuilderSpec.refreshDuration, cacheBuilderSpec.refreshTimeUnit));
    }

    public int hashCode() {
        return Objects.hashCode(this.initialCapacity, this.maximumSize, this.maximumWeight, this.concurrencyLevel, this.keyStrength, this.valueStrength, this.recordStats, durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), durationInNanos(this.refreshDuration, this.refreshTimeUnit));
    }

    public CacheBuilder<Object, Object> toCacheBuilder() {
        CacheBuilder<Object, Object> cacheBuilderNewBuilder = CacheBuilder.newBuilder();
        Integer num = this.initialCapacity;
        if (num != null) {
            cacheBuilderNewBuilder.initialCapacity(num.intValue());
        }
        Long l2 = this.maximumSize;
        if (l2 != null) {
            cacheBuilderNewBuilder.maximumSize(l2.longValue());
        }
        Long l3 = this.maximumWeight;
        if (l3 != null) {
            cacheBuilderNewBuilder.maximumWeight(l3.longValue());
        }
        Integer num2 = this.concurrencyLevel;
        if (num2 != null) {
            cacheBuilderNewBuilder.concurrencyLevel(num2.intValue());
        }
        LocalCache.Strength strength = this.keyStrength;
        if (strength != null) {
            if (AnonymousClass1.$SwitchMap$com$google$common$cache$LocalCache$Strength[strength.ordinal()] != 1) {
                throw new AssertionError();
            }
            cacheBuilderNewBuilder.weakKeys();
        }
        LocalCache.Strength strength2 = this.valueStrength;
        if (strength2 != null) {
            int i2 = AnonymousClass1.$SwitchMap$com$google$common$cache$LocalCache$Strength[strength2.ordinal()];
            if (i2 == 1) {
                cacheBuilderNewBuilder.weakValues();
            } else {
                if (i2 != 2) {
                    throw new AssertionError();
                }
                cacheBuilderNewBuilder.softValues();
            }
        }
        Boolean bool = this.recordStats;
        if (bool != null && bool.booleanValue()) {
            cacheBuilderNewBuilder.recordStats();
        }
        TimeUnit timeUnit = this.writeExpirationTimeUnit;
        if (timeUnit != null) {
            cacheBuilderNewBuilder.expireAfterWrite(this.writeExpirationDuration, timeUnit);
        }
        TimeUnit timeUnit2 = this.accessExpirationTimeUnit;
        if (timeUnit2 != null) {
            cacheBuilderNewBuilder.expireAfterAccess(this.accessExpirationDuration, timeUnit2);
        }
        TimeUnit timeUnit3 = this.refreshTimeUnit;
        if (timeUnit3 != null) {
            cacheBuilderNewBuilder.refreshAfterWrite(this.refreshDuration, timeUnit3);
        }
        return cacheBuilderNewBuilder;
    }

    public String toParsableString() {
        return this.specification;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(toParsableString()).toString();
    }
}
