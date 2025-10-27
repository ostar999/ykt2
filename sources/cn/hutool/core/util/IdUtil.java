package cn.hutool.core.util;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.id.NanoId;
import cn.hutool.core.net.NetUtil;

/* loaded from: classes.dex */
public class IdUtil {
    @Deprecated
    public static Snowflake createSnowflake(long j2, long j3) {
        return new Snowflake(j2, j3);
    }

    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }

    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    public static long getDataCenterId(long j2) throws Throwable {
        byte[] localHardwareAddress;
        Assert.isTrue(j2 > 0, "maxDatacenterId must be > 0", new Object[0]);
        if (j2 == Long.MAX_VALUE) {
            j2--;
        }
        try {
            localHardwareAddress = NetUtil.getLocalHardwareAddress();
        } catch (UtilException unused) {
            localHardwareAddress = null;
        }
        if (localHardwareAddress != null) {
            return ((((localHardwareAddress[localHardwareAddress.length - 1] << 8) & 65280) | (localHardwareAddress[localHardwareAddress.length - 2] & 255)) >> 6) % (j2 + 1);
        }
        return 1L;
    }

    public static Snowflake getSnowflake(long j2, long j3) {
        return (Snowflake) Singleton.get(Snowflake.class, Long.valueOf(j2), Long.valueOf(j3));
    }

    public static long getSnowflakeNextId() {
        return getSnowflake().nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return getSnowflake().nextIdStr();
    }

    public static long getWorkerId(long j2, long j3) {
        StringBuilder sb = new StringBuilder();
        sb.append(j2);
        try {
            sb.append(RuntimeUtil.getPid());
        } catch (UtilException unused) {
        }
        return (sb.toString().hashCode() & 65535) % (j3 + 1);
    }

    public static String nanoId() {
        return NanoId.randomNanoId();
    }

    public static String objectId() {
        return ObjectId.next();
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    public static Snowflake getSnowflake(long j2) {
        return (Snowflake) Singleton.get(Snowflake.class, Long.valueOf(j2));
    }

    public static String nanoId(int i2) {
        return NanoId.randomNanoId(i2);
    }

    public static Snowflake getSnowflake() {
        return (Snowflake) Singleton.get(Snowflake.class, new Object[0]);
    }
}
