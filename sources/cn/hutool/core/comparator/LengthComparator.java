package cn.hutool.core.comparator;

import java.util.Comparator;

/* loaded from: classes.dex */
public class LengthComparator implements Comparator<CharSequence> {
    public static final LengthComparator INSTANCE = new LengthComparator();

    @Override // java.util.Comparator
    public int compare(CharSequence charSequence, CharSequence charSequence2) {
        int iCompare = Integer.compare(charSequence.length(), charSequence2.length());
        return iCompare == 0 ? CompareUtil.compare(charSequence.toString(), charSequence2.toString()) : iCompare;
    }
}
