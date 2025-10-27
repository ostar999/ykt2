package com.beizi.fusion.f;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.g.w;
import com.beizi.fusion.model.AdSpacesBean;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class b {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static List<AdSpacesBean.ForwardBean> a(AdSpacesBean.ComponentBean componentBean, List<AdSpacesBean.BuyerBean> list, String str) {
        String content;
        ArrayList arrayList = new ArrayList();
        if (componentBean != null && list != null && list.size() != 0 && (content = componentBean.getContent()) != null) {
            switch (content) {
                case "random":
                    a(componentBean, arrayList);
                    break;
                case "fail":
                    a(componentBean, list, str, arrayList, "280.500");
                    break;
                case "show":
                    a(componentBean, list, str, arrayList, "280.300");
                    break;
                case "click":
                    a(componentBean, list, str, arrayList, "290.300");
                    break;
                case "request":
                    a(componentBean, list, str, arrayList, "200.000");
                    break;
            }
        } else {
            return arrayList;
        }
    }

    private static boolean b(List<AdSpacesBean.RulesBean> list, int i2) {
        boolean z2;
        if (list == null) {
            return false;
        }
        if (list.size() == 0) {
            ac.c("BeiZis", "enter rulesBeanList.size() == 0");
            z2 = true;
        } else {
            z2 = false;
        }
        for (int i3 = 0; i3 < list.size(); i3++) {
            if (a(list.get(i3), i2)) {
                return true;
            }
        }
        return z2;
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        if (ar.b().equalsIgnoreCase(str) || "BEIZI".equalsIgnoreCase(str)) {
            return "6666";
        }
        switch (str) {
        }
        return null;
    }

    private static void a(AdSpacesBean.ComponentBean componentBean, List<AdSpacesBean.BuyerBean> list, String str, List<AdSpacesBean.ForwardBean> list2, String str2) throws InterruptedException {
        ac.c("BeiZis", "enter handleSpaceStrategyByEvent eventCode = " + str2);
        List<AdSpacesBean.ForwardBean> forward = componentBean.getForward();
        if (forward == null || forward.size() <= 0) {
            return;
        }
        int iA = w.a(str, str2);
        if (!str2.equalsIgnoreCase("200.000")) {
            iA++;
        }
        for (int i2 = 0; i2 < forward.size(); i2++) {
            AdSpacesBean.ForwardBean forwardBean = forward.get(i2);
            for (int i3 = 0; i3 < list.size(); i3++) {
                AdSpacesBean.BuyerBean buyerBean = list.get(i3);
                if (buyerBean.getId() != null && buyerBean.getId().equalsIgnoreCase(forwardBean.getBuyerId()) && buyerBean.getBuyerSpaceUuId() != null && buyerBean.getBuyerSpaceUuId().equalsIgnoreCase(forwardBean.getBuyerSpaceUuId())) {
                    ac.c("BeiZis", forwardBean.getBuyerId() + " handleSpaceRequestStrategy buyerBean match");
                    if (b(forwardBean.getRules(), iA)) {
                        ac.c("BeiZis", forwardBean.getBuyerId() + " enter rulesMatch");
                        list2.add(forwardBean);
                    }
                }
            }
        }
    }

    public static void a(AdSpacesBean.ComponentBean componentBean, List<AdSpacesBean.ForwardBean> list) {
        List<AdSpacesBean.ForwardBean> forward = componentBean.getForward();
        int iRandom = (int) ((Math.random() * 100.0d) + 1.0d);
        ac.a("BeiZis", "AdForward random:" + iRandom);
        if (forward == null || forward.size() <= 0) {
            return;
        }
        int size = forward.size();
        for (int i2 = 0; i2 < size; i2++) {
            AdSpacesBean.ForwardBean forwardBean = forward.get(i2);
            List<AdSpacesBean.RulesBean> rules = forwardBean.getRules();
            if (rules != null && rules.size() > 0) {
                int size2 = rules.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    Integer[] results = rules.get(i3).getResults();
                    if (results != null && results.length >= 2) {
                        int iIntValue = results[0].intValue();
                        int iIntValue2 = results[1].intValue();
                        if (iIntValue <= iRandom && iRandom <= iIntValue2) {
                            list.add(forwardBean);
                        }
                    }
                }
            }
        }
    }

    private static boolean a(AdSpacesBean.RulesBean rulesBean, int i2) {
        boolean z2 = false;
        if (rulesBean == null) {
            return false;
        }
        try {
            String strReplace = rulesBean.getFormula().replace("x", i2 + "");
            int iA = d.a(strReplace);
            Integer[] results = rulesBean.getResults();
            ac.c("BeiZis", "formulaOrig = " + strReplace + ",isOneRuleMatch holderNum = " + i2);
            if (results != null && results.length >= 2) {
                ac.c("BeiZis", "formulaResult = " + iA + ",results[0] = " + results[0] + ",results[1] = " + results[1]);
                if (iA >= results[0].intValue() && iA <= results[1].intValue()) {
                    z2 = true;
                }
            }
            return rulesBean.getRules() != null ? z2 & b(rulesBean.getRules(), i2) : z2;
        } catch (Exception unused) {
            ac.c("BeiZis", "execute formula error!");
            return z2;
        }
    }

    public static void a(Context context, long j2, AdSpacesBean.FilterBean filterBean, com.beizi.fusion.b.d dVar, String str, String str2, String str3, com.beizi.fusion.d.a aVar) {
        boolean z2;
        boolean z3;
        boolean z4;
        int iA = a(str2);
        if (dVar != null) {
            ac.a("BeiZis", "channel = " + str2 + ",observer.mPlatformFilterStatus.getStatus() = " + dVar.f4805c.a() + ",observer.mChannelFilterStatus.getStatus(channelId) = " + dVar.f4807e.b(iA));
        }
        boolean zA = true;
        boolean z5 = !TextUtils.isEmpty(str2);
        if (dVar == null || !(dVar.f4805c.a() == 1 || dVar.f4807e.b(iA) == 2)) {
            if (dVar != null) {
                if (z5) {
                    dVar.f4807e.a(iA, -2);
                    return;
                } else {
                    dVar.f4805c.a(-2);
                    return;
                }
            }
            if (aVar == null || z5) {
                return;
            }
            aVar.a("status not PlatformFilterStatus.kPlatformFilterStatusBegin");
            return;
        }
        if (filterBean != null) {
            List<String> privilege = filterBean.getPrivilege();
            boolean zA2 = (privilege == null || privilege.size() <= 0) ? true : a(context, privilege);
            z4 = false;
            z3 = j2 > ((long) filterBean.getMinAdLoadTime());
            try {
                zA = w.a(filterBean.getFrequency(), str, str2, str3);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            z2 = zA;
            zA = zA2;
        } else {
            if (z5) {
                dVar.f4807e.a(iA, 3);
            } else {
                dVar.f4805c.a(2);
            }
            z2 = true;
            z3 = true;
            z4 = true;
        }
        if (!zA) {
            if (z5) {
                dVar.f4807e.a(iA, 5);
            } else {
                dVar.f4805c.a(4);
            }
        }
        if (!z3) {
            if (z5) {
                dVar.f4807e.a(iA, 6);
            } else {
                dVar.f4805c.a(5);
            }
        }
        if (!z2) {
            if (z5) {
                dVar.f4807e.a(iA, 7);
            } else {
                dVar.f4805c.a(6);
            }
        }
        if (!z4 && zA && z3 && z2) {
            if (z5) {
                dVar.f4807e.a(iA, 3);
            } else {
                dVar.f4805c.a(2);
            }
        }
    }

    public static int a(String str) {
        String strB;
        if (str == null || (strB = b(str)) == null) {
            return -1;
        }
        return Integer.parseInt(strB);
    }

    private static boolean a(Context context, List<String> list) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        for (String str : list) {
            if (-1 == packageManager.checkPermission(str, packageName)) {
                Log.d("lance", "required permission not granted . permission = " + str);
                return false;
            }
        }
        return true;
    }

    public static AdSpacesBean.BuyerBean a(String str, List<AdSpacesBean.BuyerBean> list, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("buyerBeans != null ? ");
        sb.append(list != null);
        ac.c("BeiZis", sb.toString());
        if (list != null) {
            ac.c("BeiZis", "buyerBeans.size() = " + list.size());
        }
        if (list == null || list.size() <= 0) {
            return null;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            AdSpacesBean.BuyerBean buyerBean = list.get(i2);
            ac.c("BeiZis", "AdBuyer buyerBean.getUuid() = " + buyerBean.getBuyerSpaceUuId());
            if (buyerBean.getId() != null && buyerBean.getId().equals(str) && str2 != null && str2.equalsIgnoreCase(buyerBean.getBuyerSpaceUuId())) {
                return buyerBean;
            }
        }
        return null;
    }

    public static String a(List<AdSpacesBean.BuyerBean.RenderRulesBean> list, int i2) {
        Integer[] results;
        if (list == null) {
            return null;
        }
        try {
            if (list.size() == 0) {
                return null;
            }
            for (int i3 = 0; i3 < list.size(); i3++) {
                AdSpacesBean.BuyerBean.RenderRulesBean renderRulesBean = list.get(i3);
                if (renderRulesBean != null && (results = renderRulesBean.getResults()) != null && results.length >= 2 && i2 >= results[0].intValue() && i2 <= results[1].intValue()) {
                    String type = renderRulesBean.getType();
                    ac.c("BeiZis", "type = " + type + ";holderNum:" + i2);
                    return type;
                }
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
