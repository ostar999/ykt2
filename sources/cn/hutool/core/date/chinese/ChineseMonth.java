package cn.hutool.core.date.chinese;

/* loaded from: classes.dex */
public class ChineseMonth {
    private static final String[] MONTH_NAME = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    private static final String[] MONTH_NAME_TRADITIONAL = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "寒", "冬", "腊"};

    public static String getChineseMonthName(boolean z2, int i2, boolean z3) {
        StringBuilder sb = new StringBuilder();
        sb.append(z2 ? "闰" : "");
        sb.append((z3 ? MONTH_NAME_TRADITIONAL : MONTH_NAME)[i2 - 1]);
        sb.append("月");
        return sb.toString();
    }

    public static boolean isLeapMonth(int i2, int i3) {
        return i3 == LunarInfo.leapMonth(i2);
    }
}
