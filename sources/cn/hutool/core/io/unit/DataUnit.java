package cn.hutool.core.io.unit;

import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public enum DataUnit {
    BYTES("B", DataSize.ofBytes(1)),
    KILOBYTES("KB", DataSize.ofKilobytes(1)),
    MEGABYTES("MB", DataSize.ofMegabytes(1)),
    GIGABYTES("GB", DataSize.ofGigabytes(1)),
    TERABYTES("TB", DataSize.ofTerabytes(1));

    public static final String[] UNIT_NAMES = {"B", "KB", "MB", "GB", "TB", "PB", "EB"};
    private final DataSize size;
    private final String suffix;

    DataUnit(String str, DataSize dataSize) {
        this.suffix = str;
        this.size = dataSize;
    }

    public static DataUnit fromSuffix(String str) {
        for (DataUnit dataUnit : values()) {
            if (CharSequenceUtil.startWithIgnoreCase(dataUnit.suffix, str)) {
                return dataUnit;
            }
        }
        throw new IllegalArgumentException("Unknown data unit suffix '" + str + "'");
    }

    public DataSize size() {
        return this.size;
    }
}
