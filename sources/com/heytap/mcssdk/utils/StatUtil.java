package com.heytap.mcssdk.utils;

import android.content.Context;
import android.content.Intent;
import com.heytap.mcssdk.PushService;
import com.heytap.msp.push.mode.MessageStat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public class StatUtil {
    private static final String COUNT = "count";
    private static final int MCS_SUPPORT_VERSION = 1017;
    private static final String STAT_LIST = "list";
    private static final String TYPE = "type";

    private static boolean isSupportStatisticByMcs(Context context) {
        String mcsPackageName = PushService.getInstance().getMcsPackageName(context);
        return Utils.isExistPackage(context, mcsPackageName) && Utils.getVersionCode(context, mcsPackageName) >= 1017;
    }

    public static void statisticMessage(Context context, MessageStat messageStat) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(messageStat);
        statisticMessage(context, linkedList);
    }

    public static boolean statisticMessage(Context context, List<MessageStat> list) {
        LinkedList linkedList = new LinkedList();
        linkedList.addAll(list);
        d.b("isSupportStatisticByMcs:" + isSupportStatisticByMcs(context) + ",list size:" + linkedList.size());
        if (linkedList.size() <= 0 || !isSupportStatisticByMcs(context)) {
            return false;
        }
        return statisticMessageByMcs(context, linkedList);
    }

    private static boolean statisticMessageByMcs(Context context, List<MessageStat> list) {
        try {
            Intent intent = new Intent();
            intent.setAction(PushService.getInstance().getReceiveSdkAction(context));
            intent.setPackage(PushService.getInstance().getMcsPackageName(context));
            intent.putExtra(com.heytap.mcssdk.constant.b.f7180e, context.getPackageName());
            intent.putExtra("type", 12291);
            intent.putExtra(COUNT, list.size());
            ArrayList<String> arrayList = new ArrayList<>();
            Iterator<MessageStat> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toJsonObject());
            }
            intent.putStringArrayListExtra(STAT_LIST, arrayList);
            context.startService(intent);
            return true;
        } catch (Exception e2) {
            d.e("statisticMessage--Exception" + e2.getMessage());
            return false;
        }
    }
}
