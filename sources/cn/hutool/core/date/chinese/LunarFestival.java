package cn.hutool.core.date.chinese;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.TableMap;
import java.util.List;

/* loaded from: classes.dex */
public class LunarFestival {
    private static final TableMap<Pair<Integer, Integer>, String> L_FTV;

    static {
        TableMap<Pair<Integer, Integer>, String> tableMap = new TableMap<>(16);
        L_FTV = tableMap;
        tableMap.put(new Pair<>(1, 1), "春节");
        tableMap.put(new Pair<>(1, 2), "犬日");
        tableMap.put(new Pair<>(1, 3), "猪日");
        tableMap.put(new Pair<>(1, 4), "羊日");
        tableMap.put(new Pair<>(1, 5), "牛日 破五日");
        tableMap.put(new Pair<>(1, 6), "马日 送穷日");
        tableMap.put(new Pair<>(1, 7), "人日 人胜节");
        tableMap.put(new Pair<>(1, 8), "谷日 八仙日");
        tableMap.put(new Pair<>(1, 9), "天日 九皇会");
        tableMap.put(new Pair<>(1, 10), "地日 石头生日");
        tableMap.put(new Pair<>(1, 12), "火日 老鼠娶媳妇日");
        tableMap.put(new Pair<>(1, 13), "上（试）灯日 关公升天日");
        tableMap.put(new Pair<>(1, 15), "元宵节 上元节");
        tableMap.put(new Pair<>(1, 18), "落灯日");
        tableMap.put(new Pair<>(2, 1), "中和节 太阳生日");
        tableMap.put(new Pair<>(2, 2), "龙抬头");
        tableMap.put(new Pair<>(2, 12), "花朝节");
        tableMap.put(new Pair<>(2, 19), "观世音圣诞");
        tableMap.put(new Pair<>(3, 3), "上巳节");
        tableMap.put(new Pair<>(4, 1), "祭雹神");
        tableMap.put(new Pair<>(4, 4), "文殊菩萨诞辰");
        tableMap.put(new Pair<>(4, 8), "佛诞节");
        tableMap.put(new Pair<>(5, 5), "端午节 端阳节");
        tableMap.put(new Pair<>(6, 6), "晒衣节 姑姑节");
        tableMap.put(new Pair<>(6, 6), "天贶节");
        tableMap.put(new Pair<>(6, 24), "彝族火把节");
        tableMap.put(new Pair<>(7, 7), "七夕");
        tableMap.put(new Pair<>(7, 14), "鬼节(南方)");
        tableMap.put(new Pair<>(7, 15), "中元节");
        tableMap.put(new Pair<>(7, 15), "盂兰盆节 中元节");
        tableMap.put(new Pair<>(7, 30), "地藏节");
        tableMap.put(new Pair<>(8, 15), "中秋节");
        tableMap.put(new Pair<>(9, 9), "重阳节");
        tableMap.put(new Pair<>(10, 1), "祭祖节");
        tableMap.put(new Pair<>(10, 15), "下元节");
        tableMap.put(new Pair<>(11, 17), "阿弥陀佛圣诞");
        tableMap.put(new Pair<>(12, 8), "腊八节");
        tableMap.put(new Pair<>(12, 16), "尾牙");
        tableMap.put(new Pair<>(12, 23), "小年");
        tableMap.put(new Pair<>(12, 30), "除夕");
    }

    public static List<String> getFestivals(int i2, int i3, int i4) {
        if (12 == i3 && 29 == i4 && 29 == LunarInfo.monthDays(i2, i3)) {
            i4++;
        }
        return getFestivals(i3, i4);
    }

    public static List<String> getFestivals(int i2, int i3) {
        return L_FTV.getValues(new Pair<>(Integer.valueOf(i2), Integer.valueOf(i3)));
    }
}
