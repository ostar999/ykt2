package com.psychiatrygarden.interfaceclass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import com.aliyun.vod.common.utils.UriUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* loaded from: classes4.dex */
public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "JIGUANG-Example";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final Context mAppContext;
    private final Handler mHandler;
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();

    public static class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        public BroadcastRecord(Intent _intent, ArrayList<ReceiverRecord> _receivers) {
            this.intent = _intent;
            this.receivers = _receivers;
        }
    }

    public static class ReceiverRecord {
        boolean broadcasting;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        public ReceiverRecord(IntentFilter _filter, BroadcastReceiver _receiver) {
            this.filter = _filter;
            this.receiver = _receiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.receiver);
            sb.append(" filter=");
            sb.append(this.filter);
            sb.append("}");
            return sb.toString();
        }
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) { // from class: com.psychiatrygarden.interfaceclass.LocalBroadcastManager.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what != 1) {
                    super.handleMessage(msg);
                } else {
                    LocalBroadcastManager.this.executePendingBroadcasts();
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executePendingBroadcasts() {
        int size;
        BroadcastRecord[] broadcastRecordArr;
        while (true) {
            synchronized (this.mReceivers) {
                size = this.mPendingBroadcasts.size();
                if (size <= 0) {
                    return;
                }
                broadcastRecordArr = new BroadcastRecord[size];
                this.mPendingBroadcasts.toArray(broadcastRecordArr);
                this.mPendingBroadcasts.clear();
            }
            for (int i2 = 0; i2 < size; i2++) {
                BroadcastRecord broadcastRecord = broadcastRecordArr[i2];
                for (int i3 = 0; i3 < broadcastRecord.receivers.size(); i3++) {
                    broadcastRecord.receivers.get(i3).receiver.onReceive(this.mAppContext, broadcastRecord.intent);
                }
            }
        }
    }

    public static LocalBroadcastManager getInstance(Context context) {
        LocalBroadcastManager localBroadcastManager;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocalBroadcastManager(context.getApplicationContext());
            }
            localBroadcastManager = mInstance;
        }
        return localBroadcastManager;
    }

    public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        synchronized (this.mReceivers) {
            ReceiverRecord receiverRecord = new ReceiverRecord(filter, receiver);
            ArrayList<IntentFilter> arrayList = this.mReceivers.get(receiver);
            if (arrayList == null) {
                arrayList = new ArrayList<>(1);
                this.mReceivers.put(receiver, arrayList);
            }
            arrayList.add(filter);
            for (int i2 = 0; i2 < filter.countActions(); i2++) {
                String action = filter.getAction(i2);
                ArrayList<ReceiverRecord> arrayList2 = this.mActions.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList<>(1);
                    this.mActions.put(action, arrayList2);
                }
                arrayList2.add(receiverRecord);
            }
        }
    }

    public boolean sendBroadcast(Intent intent) {
        int i2;
        String str;
        ArrayList arrayList;
        ArrayList<ReceiverRecord> arrayList2;
        String str2;
        synchronized (this.mReceivers) {
            String action = intent.getAction();
            String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
            Uri data = intent.getData();
            String scheme = intent.getScheme();
            Set<String> categories = intent.getCategories();
            boolean z2 = (intent.getFlags() & 8) != 0;
            if (z2) {
                Logger.v("LocalBroadcastManager", "Resolving type " + strResolveTypeIfNeeded + " scheme " + scheme + " of intent " + intent);
            }
            ArrayList<ReceiverRecord> arrayList3 = this.mActions.get(intent.getAction());
            if (arrayList3 != null) {
                if (z2) {
                    Logger.v("LocalBroadcastManager", "Action list: " + arrayList3);
                }
                ArrayList arrayList4 = null;
                int i3 = 0;
                while (i3 < arrayList3.size()) {
                    ReceiverRecord receiverRecord = arrayList3.get(i3);
                    if (z2) {
                        Logger.v("LocalBroadcastManager", "Matching against filter " + receiverRecord.filter);
                    }
                    if (receiverRecord.broadcasting) {
                        if (z2) {
                            Logger.v("LocalBroadcastManager", "  Filter's target already added");
                        }
                        i2 = i3;
                        arrayList2 = arrayList3;
                        str = action;
                        str2 = strResolveTypeIfNeeded;
                        arrayList = arrayList4;
                    } else {
                        i2 = i3;
                        str = action;
                        arrayList = arrayList4;
                        arrayList2 = arrayList3;
                        str2 = strResolveTypeIfNeeded;
                        int iMatch = receiverRecord.filter.match(action, strResolveTypeIfNeeded, scheme, data, categories, "LocalBroadcastManager");
                        if (iMatch >= 0) {
                            if (z2) {
                                Logger.v("LocalBroadcastManager", "  Filter matched!  match=0x" + Integer.toHexString(iMatch));
                            }
                            arrayList4 = arrayList == null ? new ArrayList() : arrayList;
                            arrayList4.add(receiverRecord);
                            receiverRecord.broadcasting = true;
                            i3 = i2 + 1;
                            action = str;
                            arrayList3 = arrayList2;
                            strResolveTypeIfNeeded = str2;
                        } else if (z2) {
                            Logger.v("LocalBroadcastManager", "  Filter did not match: " + (iMatch != -4 ? iMatch != -3 ? iMatch != -2 ? iMatch != -1 ? "unknown reason" : "type" : "data" : "action" : UriUtil.QUERY_CATEGORY));
                        }
                    }
                    arrayList4 = arrayList;
                    i3 = i2 + 1;
                    action = str;
                    arrayList3 = arrayList2;
                    strResolveTypeIfNeeded = str2;
                }
                ArrayList arrayList5 = arrayList4;
                if (arrayList5 != null) {
                    for (int i4 = 0; i4 < arrayList5.size(); i4++) {
                        ((ReceiverRecord) arrayList5.get(i4)).broadcasting = false;
                    }
                    this.mPendingBroadcasts.add(new BroadcastRecord(intent, arrayList5));
                    if (!this.mHandler.hasMessages(1)) {
                        this.mHandler.sendEmptyMessage(1);
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public void sendBroadcastSync(Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts();
        }
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        synchronized (this.mReceivers) {
            ArrayList<IntentFilter> arrayListRemove = this.mReceivers.remove(receiver);
            if (arrayListRemove != null) {
                for (int i2 = 0; i2 < arrayListRemove.size(); i2++) {
                    IntentFilter intentFilter = arrayListRemove.get(i2);
                    for (int i3 = 0; i3 < intentFilter.countActions(); i3++) {
                        String action = intentFilter.getAction(i3);
                        ArrayList<ReceiverRecord> arrayList = this.mActions.get(action);
                        if (arrayList != null) {
                            int i4 = 0;
                            while (i4 < arrayList.size()) {
                                if (arrayList.get(i4).receiver == receiver) {
                                    arrayList.remove(i4);
                                    i4--;
                                }
                                i4++;
                            }
                            if (arrayList.size() <= 0) {
                                this.mActions.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }
}
