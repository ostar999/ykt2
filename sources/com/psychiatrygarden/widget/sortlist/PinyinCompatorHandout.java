package com.psychiatrygarden.widget.sortlist;

import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.psychiatrygarden.bean.HandoutChannelBean;
import java.util.Comparator;

/* loaded from: classes6.dex */
public class PinyinCompatorHandout implements Comparator<HandoutChannelBean.DataBean.ListBean.SubBean> {
    @Override // java.util.Comparator
    public int compare(HandoutChannelBean.DataBean.ListBean.SubBean o12, HandoutChannelBean.DataBean.ListBean.SubBean o2) {
        if (o12.getSortLetters().equals("↑") || o2.getSortLetters().equals(DictionaryFactory.SHARP)) {
            return -1;
        }
        if (o12.getSortLetters().equals(DictionaryFactory.SHARP) || o2.getSortLetters().equals("↑")) {
            return 1;
        }
        return o12.getSortLetters().compareTo(o2.getSortLetters());
    }
}
