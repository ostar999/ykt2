package com.beizi.fusion.g;

import com.beizi.fusion.model.AdSpacesBean;
import java.util.List;

/* loaded from: classes2.dex */
public class ah {
    public static int a(List<AdSpacesBean.BuyerBean.PriceDictBean> list, String str) {
        if (str == null || list == null || list.size() == 0) {
            return -1;
        }
        for (AdSpacesBean.BuyerBean.PriceDictBean priceDictBean : list) {
            ac.a("BeiZis", "priceLevel = " + str + ",priceDictBean.getName() = " + priceDictBean.getName());
            if (str.equalsIgnoreCase(priceDictBean.getName())) {
                return priceDictBean.getPrice();
            }
        }
        return -2;
    }
}
