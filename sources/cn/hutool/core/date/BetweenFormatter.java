package cn.hutool.core.date;

import java.io.Serializable;

/* loaded from: classes.dex */
public class BetweenFormatter implements Serializable {
    private static final long serialVersionUID = 1;
    private long betweenMs;
    private Level level;
    private final int levelMaxCount;

    public enum Level {
        DAY("天"),
        HOUR("小时"),
        MINUTE("分"),
        SECOND("秒"),
        MILLISECOND("毫秒");

        private final String name;

        Level(String str) {
            this.name = str;
        }

        public String getName() {
            return this.name;
        }
    }

    public BetweenFormatter(long j2, Level level) {
        this(j2, level, 0);
    }

    private boolean isLevelCountValid(int i2) {
        int i3 = this.levelMaxCount;
        return i3 <= 0 || i2 < i3;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String format() {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.date.BetweenFormatter.format():java.lang.String");
    }

    public long getBetweenMs() {
        return this.betweenMs;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setBetweenMs(long j2) {
        this.betweenMs = j2;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String toString() {
        return format();
    }

    public BetweenFormatter(long j2, Level level, int i2) {
        this.betweenMs = j2;
        this.level = level;
        this.levelMaxCount = i2;
    }
}
