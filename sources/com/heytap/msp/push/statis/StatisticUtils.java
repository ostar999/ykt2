package com.heytap.msp.push.statis;

import android.content.Context;
import com.heytap.mcssdk.utils.StatUtil;
import com.heytap.msp.push.mode.DataMessage;
import com.heytap.msp.push.mode.MessageStat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class StatisticUtils {
    public static boolean statisticEvent(Context context, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MessageStat(context.getPackageName(), str));
        return StatUtil.statisticMessage(context, arrayList);
    }

    public static boolean statisticEvent(Context context, String str, DataMessage dataMessage) {
        ArrayList arrayList = new ArrayList();
        String packageName = context.getPackageName();
        arrayList.add(dataMessage == null ? new MessageStat(packageName, str) : new MessageStat(dataMessage.getMessageType(), packageName, dataMessage.getGlobalId(), dataMessage.getTaskID(), str, null, dataMessage.getStatisticsExtra(), dataMessage.getDataExtra()));
        return StatUtil.statisticMessage(context, arrayList);
    }

    public static boolean statisticEvent(Context context, List<String> list) {
        ArrayList arrayList = new ArrayList();
        String packageName = context.getPackageName();
        if (list != null && list.size() != 0) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new MessageStat(packageName, it.next()));
            }
        }
        return StatUtil.statisticMessage(context, arrayList);
    }

    public static boolean statisticEvent(Context context, Map<String, List<DataMessage>> map) {
        if (map == null) {
            return false;
        }
        String packageName = context.getPackageName();
        ArrayList arrayList = new ArrayList();
        for (String str : map.keySet()) {
            List<DataMessage> list = map.get(str);
            if (list != null) {
                for (DataMessage dataMessage : list) {
                    arrayList.add(new MessageStat(dataMessage.getMessageType(), packageName, dataMessage.getGlobalId(), dataMessage.getTaskID(), str, null, dataMessage.getStatisticsExtra(), dataMessage.getDataExtra()));
                }
            } else {
                arrayList.add(new MessageStat(packageName, str));
            }
        }
        return StatUtil.statisticMessage(context, arrayList);
    }
}
