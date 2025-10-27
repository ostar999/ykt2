package com.beizi.fusion.f;

import com.beizi.fusion.g.ac;
import com.beizi.fusion.model.AdSpacesBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static c f5060a;

    private c() {
    }

    public static c a() {
        if (f5060a == null) {
            f5060a = new c();
        }
        return f5060a;
    }

    public List<AdSpacesBean.ForwardBean> b(List<AdSpacesBean.ForwardBean> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                int iRandom = (int) ((Math.random() * 100.0d) + 1.0d);
                ac.a("BeiZis", "bidForward handleRandomForwardBean random:" + iRandom);
                AdSpacesBean.ForwardBean forwardBean = list.get(i2);
                List<AdSpacesBean.RulesBean> rules = forwardBean.getRules();
                if (rules == null || rules.size() <= 0) {
                    arrayList.add(forwardBean);
                } else {
                    int size2 = rules.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        Integer[] results = rules.get(i3).getResults();
                        if (results != null && results.length >= 2) {
                            int iIntValue = results[0].intValue();
                            int iIntValue2 = results[1].intValue();
                            if (iIntValue <= iRandom && iRandom <= iIntValue2) {
                                arrayList.add(forwardBean);
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public List<AdSpacesBean.ForwardBean> a(AdSpacesBean.BidComponent bidComponent, List<AdSpacesBean.BuyerBean> list) {
        ArrayList arrayList = new ArrayList();
        if (bidComponent == null || list == null || list.size() == 0) {
            return arrayList;
        }
        List<AdSpacesBean.RulesBean> rules = bidComponent.getRules();
        if (rules != null && rules.size() != 0) {
            return a(b(a(a(rules), bidComponent.getBidList())), list);
        }
        return a(b(bidComponent.getBidList()), list);
    }

    public HashMap<String, Boolean> a(List<AdSpacesBean.RulesBean> list) {
        Integer[] results;
        HashMap<String, Boolean> map = new HashMap<>();
        int iRandom = (int) ((Math.random() * 100.0d) + 1.0d);
        ac.a("BeiZis", "bidForward handleRandomRulesBean random:" + iRandom);
        if (list != null && list.size() > 0) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                AdSpacesBean.RulesBean rulesBean = list.get(i2);
                if (rulesBean != null && (results = rulesBean.getResults()) != null && results.length >= 2) {
                    map.put(rulesBean.getBaseId(), Boolean.valueOf(results[0].intValue() <= iRandom && iRandom <= results[1].intValue()));
                }
            }
        }
        return map;
    }

    private List<AdSpacesBean.ForwardBean> a(HashMap<String, Boolean> map, List<AdSpacesBean.ForwardBean> list) {
        ArrayList arrayList = new ArrayList();
        if (map != null && map.size() != 0 && list != null && list.size() != 0) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                AdSpacesBean.ForwardBean forwardBean = list.get(i2);
                if (map.containsKey(forwardBean.getBaseId())) {
                    if (map.get(forwardBean.getBaseId()).booleanValue()) {
                        arrayList.add(forwardBean);
                    }
                } else {
                    arrayList.add(forwardBean);
                }
            }
        }
        return arrayList;
    }

    private List<AdSpacesBean.ForwardBean> a(List<AdSpacesBean.ForwardBean> list, List<AdSpacesBean.BuyerBean> list2) {
        ArrayList arrayList = new ArrayList();
        if (list2 != null && list2.size() != 0 && list != null && list.size() != 0) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                AdSpacesBean.ForwardBean forwardBean = list.get(i2);
                for (int i3 = 0; i3 < list2.size(); i3++) {
                    AdSpacesBean.BuyerBean buyerBean = list2.get(i3);
                    if (buyerBean.getId() != null && buyerBean.getId().equals(forwardBean.getBuyerId()) && buyerBean.getBuyerSpaceUuId() != null && buyerBean.getBuyerSpaceUuId().equalsIgnoreCase(forwardBean.getBuyerSpaceUuId()) && buyerBean.getBidType() != null && a(buyerBean.getBidType())) {
                        arrayList.add(forwardBean);
                    }
                }
            }
            ac.a("BeiZis", "filteredForwardBean.size() = " + arrayList.size());
        }
        return arrayList;
    }

    private boolean a(String str) {
        return "C2S".equalsIgnoreCase(str) || "BPDI".equalsIgnoreCase(str) || "S2S".equalsIgnoreCase(str);
    }
}
