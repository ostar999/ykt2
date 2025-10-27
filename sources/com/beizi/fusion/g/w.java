package com.beizi.fusion.g;

import android.content.Context;
import com.beizi.fusion.model.EventItem;
import com.beizi.fusion.model.FreqItem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public class w {

    /* renamed from: a, reason: collision with root package name */
    private static HashMap<String, Object> f5266a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private static HashMap<String, Object> f5267b = new HashMap<>();

    /* renamed from: c, reason: collision with root package name */
    private static Comparator<EventItem> f5268c = new Comparator<EventItem>() { // from class: com.beizi.fusion.g.w.1
        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(EventItem eventItem, EventItem eventItem2) {
            return eventItem.getTimeStamp().compareTo(eventItem2.getTimeStamp());
        }
    };

    /* renamed from: d, reason: collision with root package name */
    private static HashMap<String, Long> f5269d = new HashMap<>();

    /* renamed from: e, reason: collision with root package name */
    private static HashMap<String, FreqItem> f5270e = new HashMap<>();

    /* renamed from: f, reason: collision with root package name */
    private static HashSet<String> f5271f = new HashSet<>();

    public static void a(Context context, String str) {
        try {
            ArrayList arrayList = (ArrayList) com.beizi.fusion.a.b.a(context).a(str);
            f5266a.put(str, a(arrayList));
            f5267b.put(str, b(arrayList));
            d("adUnitId = " + str + ",platFormMap get = " + f5266a.get(str) + ",channelMap get = " + f5267b.get(str));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void b(Context context, String str) throws InterruptedException {
        if (a(str)) {
            return;
        }
        c(context, str);
        int i2 = 0;
        while (!a(str) && i2 < 100) {
            d("currentWaitInitTime = " + i2);
            try {
                Thread.sleep(5L);
                i2 += 5;
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    private static void c(final Context context, final String str) {
        h.b().c().execute(new Runnable() { // from class: com.beizi.fusion.g.w.2
            @Override // java.lang.Runnable
            public void run() {
                w.a(context, str);
            }
        });
    }

    public static void d(String str) {
        ac.b("FreqUtil", str);
    }

    public static FreqItem c(String str) {
        return f5270e.get(str);
    }

    public static int a(String str, String str2) throws InterruptedException {
        ArrayList arrayList;
        int i2 = 0;
        if (str2 == null) {
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("enter getTodayEventTimes channelMap != null ?  ");
        sb.append(f5267b != null);
        sb.append(",eventCode = ");
        sb.append(str2);
        d(sb.toString());
        b(com.beizi.fusion.d.b.a().e(), str);
        String strValueOf = String.valueOf(a());
        if ("200.000".equalsIgnoreCase(str2)) {
            HashMap<String, Object> map = f5266a;
            if (map != null && map.get(str) != null) {
                d("getSpaceTodayEventTimes platFormMap.containsKey(adUnitId) = " + f5266a.containsKey(str));
                HashMap map2 = (HashMap) f5266a.get(str);
                if (map2 != null && (arrayList = (ArrayList) map2.get(str2)) != null) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        if (((EventItem) it.next()).getTimeStamp().compareTo(strValueOf) > 0) {
                            i2++;
                        }
                    }
                }
            }
        } else {
            HashMap<String, Object> map3 = f5267b;
            if (map3 != null && map3.get(str) != null) {
                d("getSpaceTodayEventTimes channelMap.containsKey(adUnitId) = " + f5267b.containsKey(str));
                HashMap map4 = (HashMap) f5267b.get(str);
                Iterator<String> it2 = f5271f.iterator();
                while (it2.hasNext()) {
                    String next = it2.next();
                    if (map4 != null && map4.get(next) != null) {
                        HashMap map5 = (HashMap) map4.get(next);
                        d("getSpaceTodayEventTimes eventMap = " + map5);
                        if (map5 != null && map5.get(str2) != null) {
                            ArrayList arrayList2 = (ArrayList) map5.get(str2);
                            d("getSpaceTodayEventTimes itemList = " + arrayList2 + ",eventCode = " + str2);
                            if (arrayList2 != null && arrayList2.size() > 0) {
                                ac.c("BeiZis", arrayList2.toString());
                                Iterator it3 = arrayList2.iterator();
                                while (it3.hasNext()) {
                                    if (((EventItem) it3.next()).getTimeStamp().compareTo(strValueOf) > 0) {
                                        i2++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ac.c("BeiZis", "todayEventTimes = " + i2);
        return i2;
    }

    public static long b(String str) {
        Long l2 = f5269d.get(str);
        if (l2 == null) {
            return 0L;
        }
        return l2.longValue();
    }

    private static Map<String, Map<String, List<EventItem>>> b(List<EventItem> list) {
        ArrayList arrayList;
        ArrayList arrayList2;
        if (list != null && list.size() != 0) {
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            for (EventItem eventItem : list) {
                String spaceId = eventItem.getSpaceId();
                if (spaceId != null) {
                    if (!map.containsKey(spaceId)) {
                        arrayList2 = new ArrayList();
                        arrayList2.add(eventItem);
                    } else {
                        arrayList2 = (ArrayList) map.get(spaceId);
                        if (arrayList2 != null) {
                            arrayList2.add(eventItem);
                        }
                    }
                    map.put(spaceId, arrayList2);
                }
                String channel = eventItem.getChannel();
                f5271f.add(channel);
                if (channel != null) {
                    if (!map2.containsKey(channel)) {
                        arrayList = new ArrayList();
                        arrayList.add(eventItem);
                    } else {
                        arrayList = (ArrayList) map2.get(channel);
                        if (arrayList != null) {
                            arrayList.add(eventItem);
                        }
                    }
                    map2.put(channel, arrayList);
                }
            }
            HashMap map3 = new HashMap();
            for (String str : map.keySet()) {
                ArrayList arrayList3 = (ArrayList) map.get(str);
                if (arrayList3 != null) {
                    map3.put(str, (HashMap) a(arrayList3));
                }
            }
            for (String str2 : map2.keySet()) {
                ArrayList arrayList4 = (ArrayList) map2.get(str2);
                if (arrayList4 != null) {
                    map3.put(str2, (HashMap) a(arrayList4));
                }
            }
            return map3;
        }
        return new HashMap();
    }

    public static long a() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTimeInMillis();
    }

    public static boolean a(String str) {
        return f5266a.containsKey(str) && f5267b.containsKey(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0148, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean a(java.util.List<com.beizi.fusion.model.FreqItem> r18, java.lang.String r19, java.lang.String r20, java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 511
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.g.w.a(java.util.List, java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    private static void a(String str, List<FreqItem> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        long jB = b(str);
        Iterator<FreqItem> it = list.iterator();
        while (it.hasNext()) {
            jB = Math.max(it.next().getInterval(), jB);
        }
        d("maxInterval = " + jB);
        f5269d.put(str, Long.valueOf(jB));
    }

    private static Map<String, List<EventItem>> a(List<EventItem> list) {
        ArrayList arrayList;
        if (list != null && list.size() != 0) {
            HashMap map = new HashMap();
            String str = null;
            ArrayList arrayList2 = null;
            for (EventItem eventItem : list) {
                String code = eventItem.getCode();
                if (!code.equals(str)) {
                    if (str != null && arrayList2 != null) {
                        map.put(str, arrayList2);
                    }
                    if (!map.containsKey(code)) {
                        arrayList = new ArrayList();
                        arrayList.add(eventItem);
                    } else {
                        arrayList = (ArrayList) map.get(code);
                        if (arrayList != null) {
                            arrayList.add(eventItem);
                        }
                    }
                    map.put(code, arrayList);
                    arrayList2 = arrayList;
                } else if (arrayList2 != null) {
                    arrayList2.add(eventItem);
                }
                str = code;
            }
            for (String str2 : map.keySet()) {
                ArrayList arrayList3 = (ArrayList) map.get(str2);
                Collections.sort(arrayList3, f5268c);
                map.put(str2, arrayList3);
            }
            return map;
        }
        return new HashMap();
    }

    public static void a(String str, EventItem eventItem) {
        try {
            HashMap map = (HashMap) f5266a.get(str);
            if (map == null) {
                map = new HashMap();
            }
            String code = eventItem.getCode();
            a(eventItem, map, code);
            String spaceId = eventItem.getSpaceId();
            if (spaceId != null) {
                HashMap map2 = (HashMap) f5267b.get(str);
                if (map2 == null) {
                    map2 = new HashMap();
                }
                HashMap map3 = (HashMap) map2.get(spaceId);
                if (map3 == null) {
                    map3 = new HashMap();
                }
                a(eventItem, map3, code);
                map2.put(spaceId, map3);
            }
            String channel = eventItem.getChannel();
            f5271f.add(channel);
            if (channel != null) {
                HashMap map4 = (HashMap) f5267b.get(str);
                if (map4 == null) {
                    map4 = new HashMap();
                }
                HashMap map5 = (HashMap) map4.get(channel);
                if (map5 == null) {
                    map5 = new HashMap();
                }
                a(eventItem, map5, code);
                map4.put(channel, map5);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void a(EventItem eventItem, HashMap<String, List<EventItem>> map, String str) {
        if (map != null) {
            if (!map.containsKey(str)) {
                if (str != null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(eventItem);
                    map.put(str, arrayList);
                    return;
                }
                return;
            }
            ArrayList arrayList2 = (ArrayList) map.get(str);
            if (arrayList2 != null) {
                arrayList2.add(eventItem);
                Collections.sort(arrayList2, f5268c);
                map.put(str, arrayList2);
            }
        }
    }
}
