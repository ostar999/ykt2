package com.plv.livescenes.playback.chat;

import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import com.alipay.sdk.authjs.a;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.livescenes.playback.chat.model.PLVHistoryPartVO;
import com.plv.socket.event.chat.PLVChatImgContent;
import com.plv.socket.event.chat.PLVChatQuoteVO;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import com.plv.thirdpart.litepal.LitePal;
import com.plv.thirdpart.litepal.LitePalDB;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.HttpException;

/* loaded from: classes5.dex */
public class PLVChatPlaybackManager implements IPLVChatPlaybackManager {
    private static final int ALLOW_BOUNCE_TIME = 1500;
    private static final String DB_NAME = "plv_chat_playback_db";
    public static final int DISPLAY_ITEM_COUNT = 500;
    public static final int LOAD_PREVIOUS_ITEM_COUNT = 50;
    public static final int START_TASK_INTERVAL = 800;
    private static final String TAG = "PLVChatPlaybackManager";
    public static final int TAIL_TO_DISPLAY_ITEM_COUNT = 500;
    private List<IPLVChatPlaybackCallDataListener> callDataListenerList;
    private boolean disableLoadPreviousByClearData;
    private IPLVChatPlaybackGetDataListener getDataListener;
    private Disposable loadPreviousTask;
    private Disposable requestNetDataTask;
    private Disposable requestNextNetDataTask;
    private String roomId;
    private int saveTime;
    private String sessionId;
    private Disposable startTask;
    private List<PLVChatPlaybackData> displayDataList = new ArrayList();
    private List<PLVChatPlaybackData> tailToDisplayDataList = new ArrayList();
    private Map<String, List<PLVHistoryPartVO>> historyPartMap = Collections.synchronizedMap(new HashMap());

    public interface CallListener {
        void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener);
    }

    public abstract class ExCallListener implements CallListener {
        public ExCallListener() {
        }

        @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
        public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
            if (iPLVChatPlaybackCallDataListener instanceof PLVChatPlaybackCallDataExListener) {
                exCall((PLVChatPlaybackCallDataExListener) iPLVChatPlaybackCallDataListener);
            }
        }

        public abstract void exCall(@NonNull PLVChatPlaybackCallDataExListener pLVChatPlaybackCallDataExListener);
    }

    public interface ValueRunnable<T> {
        T run();
    }

    static {
        safeRun(new ValueRunnable<Object>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.1
            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ValueRunnable
            public Object run() {
                LitePal.initialize(Utils.getApp());
                LitePal.deleteDatabase(PLVChatPlaybackManager.DB_NAME);
                LitePalDB litePalDB = new LitePalDB(PLVChatPlaybackManager.DB_NAME, 1);
                litePalDB.addClassName(PLVChatPlaybackData.class.getName());
                litePalDB.addClassName(PLVChatPlaybackDataCacheTime.class.getName());
                LitePal.use(litePalDB);
                return null;
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Pair<List<PLVChatPlaybackData>, List<PLVChatPlaybackData>> acceptRequestData(int i2, int i3, int i4, int i5, List<PLVChatPlaybackData> list) throws InterruptedException {
        int size;
        saveData(this.sessionId, i3, i4, i5, list);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i6 = -1;
        for (int i7 = 0; i7 < list.size(); i7++) {
            PLVChatPlaybackData pLVChatPlaybackData = list.get(i7);
            if (pLVChatPlaybackData.getRelativeTime() < i2) {
                i6 = i7;
            } else if (pLVChatPlaybackData.getRelativeTime() == i2) {
                arrayList.add(pLVChatPlaybackData);
                i6 = i7;
            } else if (pLVChatPlaybackData.getRelativeTime() > i2) {
                arrayList2.add(pLVChatPlaybackData);
            }
        }
        if (i6 >= 0) {
            if (arrayList.size() == 0) {
                for (int iMax = Math.max(0, (i6 - 500) + 1); iMax <= i6; iMax++) {
                    arrayList.add(list.get(iMax));
                }
            } else if (arrayList.size() < 500 && (i6 - arrayList.size()) - 1 > 0) {
                for (int iMax2 = Math.max(0, (size - (500 - arrayList.size())) + 1); iMax2 <= size; iMax2++) {
                    arrayList.add(list.get(iMax2));
                }
            }
        }
        return new Pair<>(arrayList, arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callData(CallListener callListener) {
        List<IPLVChatPlaybackCallDataListener> list = this.callDataListenerList;
        if (list != null) {
            Iterator<IPLVChatPlaybackCallDataListener> it = list.iterator();
            while (it.hasNext()) {
                callListener.call(it.next());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearListDataAndCall() {
        if (!this.displayDataList.isEmpty()) {
            this.displayDataList.clear();
            callData(new CallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.59
                @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
                public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                    iPLVChatPlaybackCallDataListener.onDataCleared();
                }
            });
        }
        this.tailToDisplayDataList.clear();
        this.disableLoadPreviousByClearData = true;
        callData(new ExCallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.60
            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ExCallListener
            public void exCall(@NonNull PLVChatPlaybackCallDataExListener pLVChatPlaybackCallDataExListener) {
                pLVChatPlaybackCallDataExListener.onLoadPreviousEnabled(false, PLVChatPlaybackManager.this.disableLoadPreviousByClearData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void disposeAllTask() {
        dispose(this.startTask);
        dispose(this.requestNetDataTask);
        dispose(this.requestNextNetDataTask);
        disposeLoadPreviousTask();
    }

    private void disposeLoadPreviousTask() {
        Disposable disposable = this.loadPreviousTask;
        if (disposable != null && !disposable.isDisposed()) {
            callData(new CallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.64
                @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
                public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                    iPLVChatPlaybackCallDataListener.onLoadPreviousFinish();
                }
            });
        }
        dispose(this.loadPreviousTask);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillDisPlayDataWithPrevious(final List<PLVChatPlaybackData> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.displayDataList.addAll(0, list);
        PLVCommonLog.d(TAG, "fillDisPlayDataWithPrevious：" + this.displayDataList.size());
        callData(new CallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.39
            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
            public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                iPLVChatPlaybackCallDataListener.onDataInserted(0, list.size(), list, true, PLVChatPlaybackManager.this.saveTime);
            }
        });
        if (this.displayDataList.size() > 500) {
            final ArrayList arrayList = new ArrayList();
            for (int i2 = 500; i2 < this.displayDataList.size(); i2++) {
                arrayList.add(this.displayDataList.get(i2));
            }
            if (arrayList.size() > 0) {
                removeDataFromEnd(this.displayDataList, arrayList.size());
                callData(new CallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.40
                    @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
                    public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                        iPLVChatPlaybackCallDataListener.onDataRemoved(500, arrayList.size(), arrayList, false);
                    }
                });
                this.tailToDisplayDataList.addAll(0, arrayList);
                if (this.tailToDisplayDataList.size() > 500) {
                    List<PLVChatPlaybackData> list2 = this.tailToDisplayDataList;
                    removeDataFromEnd(list2, list2.size() - 500);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<Integer> fillDisplayData(final String str, final int i2, final int i3, final List<PLVChatPlaybackData> list, final List<PLVChatPlaybackData> list2) {
        return Observable.just(1).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<Integer, ObservableSource<Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.43
            @Override // io.reactivex.functions.Function
            public ObservableSource<Integer> apply(Integer num) throws Exception {
                int[] nextTimeBucket = PLVChatPlaybackManager.this.getNextTimeBucket(i3);
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillDisplayData nextTimeBucket：" + Arrays.toString(nextTimeBucket));
                List list3 = list;
                if (list3 != null && !list3.isEmpty()) {
                    return Observable.just(1);
                }
                List list4 = list2;
                if (list4 != null && list4.size() > 0) {
                    PLVChatPlaybackManager.this.tailToDisplayDataList.clear();
                    List list5 = PLVChatPlaybackManager.this.tailToDisplayDataList;
                    List list6 = list2;
                    list5.addAll(list6.subList(0, Math.min(500, list6.size())));
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillDisplayData only addTailData：" + PLVChatPlaybackManager.this.tailToDisplayDataList.size());
                }
                return PLVChatPlaybackManager.this.tailToDisplayDataList.size() < 500 ? PLVChatPlaybackManager.this.requestNextTimeDataIfNoCache(str, nextTimeBucket[0]) : Observable.just(1).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.43.1
                    @Override // io.reactivex.functions.Predicate
                    public boolean test(Integer num2) throws Exception {
                        PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillDisplayData addDisplayDataList isEmpty");
                        return false;
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<Integer, ObservableSource<Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.42
            @Override // io.reactivex.functions.Function
            public ObservableSource<Integer> apply(Integer num) throws Exception {
                int[] nextTimeBucket = PLVChatPlaybackManager.this.getNextTimeBucket(i3);
                PLVChatPlaybackManager.this.clearListDataAndCall();
                List list3 = list;
                final List listSubList = list3.subList(0, Math.min(500, list3.size()));
                PLVChatPlaybackManager.this.displayDataList.addAll(listSubList);
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillDisplayData：" + PLVChatPlaybackManager.this.displayDataList.size());
                PLVChatPlaybackManager.this.callData(new ExCallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.42.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super();
                    }

                    @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ExCallListener, com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
                    public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                        super.call(iPLVChatPlaybackCallDataListener);
                        iPLVChatPlaybackCallDataListener.onDataInserted(0, listSubList.size(), listSubList, false, i2);
                    }

                    @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ExCallListener
                    public void exCall(@NonNull PLVChatPlaybackCallDataExListener pLVChatPlaybackCallDataExListener) {
                        if (PLVChatPlaybackManager.this.disableLoadPreviousByClearData) {
                            pLVChatPlaybackCallDataExListener.onLoadPreviousEnabled(true, PLVChatPlaybackManager.this.disableLoadPreviousByClearData);
                        }
                    }
                });
                for (int size = listSubList.size(); size > 0; size--) {
                    list.remove(0);
                }
                if (list.size() > 0) {
                    list2.addAll(0, list);
                }
                if (list2.size() > 0) {
                    PLVChatPlaybackManager.this.tailToDisplayDataList.clear();
                    List list4 = PLVChatPlaybackManager.this.tailToDisplayDataList;
                    List list5 = list2;
                    list4.addAll(list5.subList(0, Math.min(500, list5.size())));
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillDisplayData addTailData：" + PLVChatPlaybackManager.this.tailToDisplayDataList.size());
                }
                return PLVChatPlaybackManager.this.tailToDisplayDataList.size() < 500 ? PLVChatPlaybackManager.this.requestNextTimeDataIfNoCache(str, nextTimeBucket[0]) : Observable.just(1);
            }
        }).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.41
            @Override // io.reactivex.functions.Predicate
            public boolean test(Integer num) throws Exception {
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<Integer> fillTailData(final String str, final Pair<Integer, Integer> pair) {
        return Observable.just(Integer.valueOf(500 - this.tailToDisplayDataList.size())).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.58
            @Override // io.reactivex.functions.Predicate
            public boolean test(Integer num) throws Exception {
                return num.intValue() > 0;
            }
        }).observeOn(Schedulers.io()).map(new Function<Integer, Pair<List<PLVChatPlaybackData>, Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.57
            @Override // io.reactivex.functions.Function
            public Pair<List<PLVChatPlaybackData>, Integer> apply(Integer num) throws Exception {
                PLVChatPlaybackDataCacheTime pLVChatPlaybackDataCacheTimeFindCurrentTimeCache = PLVChatPlaybackManager.this.findCurrentTimeCache(str, ((Integer) pair.second).intValue());
                if (pLVChatPlaybackDataCacheTimeFindCurrentTimeCache == null) {
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillTailData break1");
                    return new Pair<>(null, -1);
                }
                int endTime = pLVChatPlaybackDataCacheTimeFindCurrentTimeCache.getEndTime();
                List listSafeFindPlaybackData = PLVChatPlaybackManager.this.safeFindPlaybackData(num.intValue(), "sessionId = ? and id > ? and relativeTime >= ? and relativeTime <= ?", str, pair.first + "", pair.second + "", endTime + "");
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillTailData currentTimeBucket：" + PLVChatPlaybackManager.this.tailToDisplayDataList.size() + "*" + listSafeFindPlaybackData.size() + "*" + pair.second + "*" + pLVChatPlaybackDataCacheTimeFindCurrentTimeCache.getStartTime() + "*" + endTime);
                return new Pair<>(listSafeFindPlaybackData, Integer.valueOf(endTime));
            }
        }).filter(new Predicate<Pair<List<PLVChatPlaybackData>, Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.56
            @Override // io.reactivex.functions.Predicate
            public boolean test(Pair<List<PLVChatPlaybackData>, Integer> pair2) throws Exception {
                return pair2.first != null;
            }
        }).observeOn(AndroidSchedulers.mainThread()).map(new Function<Pair<List<PLVChatPlaybackData>, Integer>, Pair<Integer, Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.55
            @Override // io.reactivex.functions.Function
            public Pair<Integer, Integer> apply(Pair<List<PLVChatPlaybackData>, Integer> pair2) throws Exception {
                PLVChatPlaybackManager.this.tailToDisplayDataList.addAll((Collection) pair2.first);
                return new Pair<>(Integer.valueOf(500 - PLVChatPlaybackManager.this.tailToDisplayDataList.size()), pair2.second);
            }
        }).filter(new Predicate<Pair<Integer, Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.54
            @Override // io.reactivex.functions.Predicate
            public boolean test(Pair<Integer, Integer> pair2) throws Exception {
                return ((Integer) pair2.first).intValue() > 0 && ((Integer) pair2.second).intValue() >= 0;
            }
        }).observeOn(Schedulers.io()).map(new Function<Pair<Integer, Integer>, Pair<List<PLVChatPlaybackData>, Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.53
            @Override // io.reactivex.functions.Function
            public Pair<List<PLVChatPlaybackData>, Integer> apply(Pair<Integer, Integer> pair2) throws Exception {
                int i2 = PLVChatPlaybackManager.this.getNextTimeBucket(((Integer) pair2.second).intValue())[0];
                if (i2 == -1) {
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillTailData noNextTime");
                    return new Pair<>(null, Integer.valueOf(i2));
                }
                PLVChatPlaybackDataCacheTime pLVChatPlaybackDataCacheTimeFindTimeCacheWithStartTime = PLVChatPlaybackManager.this.findTimeCacheWithStartTime(str, i2);
                if (pLVChatPlaybackDataCacheTimeFindTimeCacheWithStartTime == null) {
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillTailData noNextTimeCache");
                    return new Pair<>(null, Integer.valueOf(i2));
                }
                int endTime = pLVChatPlaybackDataCacheTimeFindTimeCacheWithStartTime.getEndTime();
                List listSafeFindPlaybackData = PLVChatPlaybackManager.this.safeFindPlaybackData(((Integer) pair2.first).intValue(), "sessionId = ? and relativeTime >= ? and relativeTime <= ?", str, i2 + "", endTime + "");
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillTailData nextTimeBucket：" + PLVChatPlaybackManager.this.tailToDisplayDataList.size() + "*" + listSafeFindPlaybackData.size() + "*" + pair.second + "*" + i2 + "*" + endTime);
                return new Pair<>(listSafeFindPlaybackData, Integer.valueOf(i2));
            }
        }).observeOn(AndroidSchedulers.mainThread()).map(new Function<Pair<List<PLVChatPlaybackData>, Integer>, Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.52
            @Override // io.reactivex.functions.Function
            public Integer apply(Pair<List<PLVChatPlaybackData>, Integer> pair2) throws Exception {
                if (pair2.first == null) {
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, "fillTailData requestNextTimeDataByNoCache break：" + pair2.second);
                    if (((Integer) pair2.second).intValue() != -1) {
                        PLVChatPlaybackManager.this.requestNextTimeDataByNoCache(str, ((Integer) pair2.second).intValue());
                    }
                } else {
                    PLVChatPlaybackManager.this.tailToDisplayDataList.addAll((Collection) pair2.first);
                }
                return 0;
            }
        }).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.51
            @Override // io.reactivex.functions.Predicate
            public boolean test(Integer num) throws Exception {
                return false;
            }
        });
    }

    private void fillTailDisplayData(final List<PLVChatPlaybackData> list, final int i2) {
        final int size = this.displayDataList.size();
        this.displayDataList.addAll(list);
        PLVCommonLog.d(TAG, "fillTailDisplayData：" + this.displayDataList.size());
        callData(new ExCallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.49
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ExCallListener, com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
            public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                super.call(iPLVChatPlaybackCallDataListener);
                iPLVChatPlaybackCallDataListener.onDataInserted(size, list.size(), list, false, i2);
            }

            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ExCallListener
            public void exCall(@NonNull PLVChatPlaybackCallDataExListener pLVChatPlaybackCallDataExListener) {
                if (PLVChatPlaybackManager.this.disableLoadPreviousByClearData) {
                    pLVChatPlaybackCallDataExListener.onLoadPreviousEnabled(true, PLVChatPlaybackManager.this.disableLoadPreviousByClearData);
                }
            }
        });
        if (this.displayDataList.size() > 500) {
            final ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < this.displayDataList.size() - 500; i3++) {
                arrayList.add(this.displayDataList.get(i3));
            }
            if (arrayList.size() > 0) {
                removeDataFromStart(this.displayDataList, arrayList.size());
                callData(new ExCallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.50
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super();
                    }

                    @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ExCallListener, com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
                    public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                        super.call(iPLVChatPlaybackCallDataListener);
                        iPLVChatPlaybackCallDataListener.onDataRemoved(0, arrayList.size(), arrayList, true);
                    }

                    @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ExCallListener
                    public void exCall(@NonNull PLVChatPlaybackCallDataExListener pLVChatPlaybackCallDataExListener) {
                        pLVChatPlaybackCallDataExListener.onLoadPreviousEnabled(true, PLVChatPlaybackManager.this.disableLoadPreviousByClearData);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PLVChatPlaybackDataCacheTime findCurrentTimeCache(String str, int i2) {
        List<PLVChatPlaybackDataCacheTime> listFindCurrentTimeCacheList = findCurrentTimeCacheList(str, i2);
        if (listFindCurrentTimeCacheList == null || listFindCurrentTimeCacheList.size() <= 0) {
            return null;
        }
        return listFindCurrentTimeCacheList.get(0);
    }

    private List<PLVChatPlaybackDataCacheTime> findCurrentTimeCacheList(String str, int i2) {
        int i3 = getCurrentTimeBucket(i2)[0];
        if (i3 != -1) {
            return findTimeCacheListWithStartTime(str, i3);
        }
        return safeFindCacheTime("sessionId = ? and startTime <= ? and endTime >= ?", str, i2 + "", i2 + "");
    }

    private List<PLVChatPlaybackDataCacheTime> findTimeCacheListWithStartTime(String str, int i2) {
        return safeFindCacheTime("sessionId = ? and startTime = ?", str, i2 + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PLVChatPlaybackDataCacheTime findTimeCacheWithStartTime(String str, int i2) {
        List<PLVChatPlaybackDataCacheTime> listFindTimeCacheListWithStartTime = findTimeCacheListWithStartTime(str, i2);
        if (listFindTimeCacheListWithStartTime == null || listFindTimeCacheListWithStartTime.size() <= 0) {
            return null;
        }
        return listFindTimeCacheListWithStartTime.get(0);
    }

    private int[] getCurrentTimeBucket(int i2) {
        int startTime;
        int endTime;
        int id;
        List<PLVHistoryPartVO> list = this.historyPartMap.get(this.sessionId);
        if (list == null || list.isEmpty()) {
            startTime = -1;
            endTime = -1;
            id = -1;
        } else {
            PLVHistoryPartVO pLVHistoryPartVO = list.get(list.size() - 1);
            if (i2 > pLVHistoryPartVO.getEndTime()) {
                startTime = pLVHistoryPartVO.getStartTime();
                endTime = pLVHistoryPartVO.getEndTime();
                id = pLVHistoryPartVO.getId();
            } else {
                for (PLVHistoryPartVO pLVHistoryPartVO2 : list) {
                    int startTime2 = pLVHistoryPartVO2.getStartTime();
                    int endTime2 = pLVHistoryPartVO2.getEndTime();
                    if (i2 >= startTime2 && i2 <= endTime2) {
                        id = pLVHistoryPartVO2.getId();
                        startTime = startTime2;
                        endTime = endTime2;
                        break;
                    }
                }
                startTime = -1;
                endTime = -1;
                id = -1;
            }
        }
        return new int[]{startTime, endTime, id};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getNextTimeBucket(int i2) {
        int startTime;
        int id;
        int endTime;
        List<PLVHistoryPartVO> list = this.historyPartMap.get(this.sessionId);
        if (list != null) {
            for (PLVHistoryPartVO pLVHistoryPartVO : list) {
                startTime = pLVHistoryPartVO.getStartTime();
                endTime = pLVHistoryPartVO.getEndTime();
                if (i2 < startTime) {
                    id = pLVHistoryPartVO.getId();
                    break;
                }
            }
            startTime = -1;
            id = -1;
            endTime = -1;
        } else {
            startTime = -1;
            id = -1;
            endTime = -1;
        }
        return new int[]{startTime, endTime, id};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<Integer> getNextTimeDataFromCache(final int i2) {
        return Observable.just(1).observeOn(AndroidSchedulers.mainThread()).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.33
            @Override // io.reactivex.functions.Predicate
            public boolean test(Integer num) throws Exception {
                return PLVChatPlaybackManager.this.tailToDisplayDataList.size() < 500;
            }
        }).observeOn(Schedulers.io()).map(new Function<Integer, List<PLVChatPlaybackData>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.32
            @Override // io.reactivex.functions.Function
            public List<PLVChatPlaybackData> apply(Integer num) throws Exception {
                int[] timeBucketWithStartTime = PLVChatPlaybackManager.this.getTimeBucketWithStartTime(i2);
                int i3 = timeBucketWithStartTime[0];
                int i4 = timeBucketWithStartTime[1];
                if (i3 < 0 || i4 < 0) {
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, "getNextTimeDataFromCache break：" + i3 + "*" + i4);
                    return null;
                }
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "getNextTimeDataFromCache time：" + i3 + "*" + i4);
                PLVChatPlaybackManager pLVChatPlaybackManager = PLVChatPlaybackManager.this;
                return pLVChatPlaybackManager.safeFindPlaybackData(500 - pLVChatPlaybackManager.tailToDisplayDataList.size(), "sessionId = ? and relativeTime >= ? and relativeTime <= ?", PLVChatPlaybackManager.this.sessionId, i3 + "", i4 + "");
            }
        }).observeOn(AndroidSchedulers.mainThread()).map(new Function<List<PLVChatPlaybackData>, Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.31
            @Override // io.reactivex.functions.Function
            public Integer apply(List<PLVChatPlaybackData> list) throws Exception {
                if (list != null) {
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, "getNextTimeDataFromCache data：" + PLVChatPlaybackManager.this.tailToDisplayDataList.size() + "*" + list.size());
                    PLVChatPlaybackManager.this.tailToDisplayDataList.addAll(list);
                }
                return 0;
            }
        }).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.30
            @Override // io.reactivex.functions.Predicate
            public boolean test(Integer num) throws Exception {
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getPreviousTimeBucket(int i2) {
        int i3;
        int id;
        List<PLVHistoryPartVO> list = this.historyPartMap.get(this.sessionId);
        int i4 = -1;
        if (list != null) {
            i3 = -1;
            id = -1;
            for (PLVHistoryPartVO pLVHistoryPartVO : list) {
                int startTime = pLVHistoryPartVO.getStartTime();
                int endTime = pLVHistoryPartVO.getEndTime();
                if (i2 <= endTime) {
                    break;
                }
                id = pLVHistoryPartVO.getId();
                i4 = startTime;
                i3 = endTime;
            }
        } else {
            i3 = -1;
            id = -1;
        }
        return new int[]{i4, i3, id};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getTimeBucketWithStartTime(int i2) {
        int endTime;
        int id;
        List<PLVHistoryPartVO> list = this.historyPartMap.get(this.sessionId);
        if (list != null) {
            for (PLVHistoryPartVO pLVHistoryPartVO : list) {
                if (pLVHistoryPartVO.getStartTime() == i2) {
                    endTime = pLVHistoryPartVO.getEndTime();
                    id = pLVHistoryPartVO.getId();
                    break;
                }
            }
            endTime = -1;
            id = -1;
        } else {
            endTime = -1;
            id = -1;
        }
        return new int[]{i2, endTime, id};
    }

    private boolean hasCurrentTimeCache(String str, int i2) {
        return findCurrentTimeCache(str, i2) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasTimeCacheWithStartTime(String str, int i2) {
        return findTimeCacheWithStartTime(str, i2) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<PLVChatPlaybackData> improveField(List<PLVChatPlaybackData> list) {
        if (list == null) {
            return list;
        }
        Iterator<PLVChatPlaybackData> it = list.iterator();
        while (it.hasNext()) {
            improveField(it.next());
        }
        return list;
    }

    public static boolean isEmptyAndNullStr(String str) {
        return TextUtils.isEmpty(str) || "null".equals(str);
    }

    private void loadPreviousTask() {
        PLVChatPlaybackData pLVChatPlaybackData = this.displayDataList.get(0);
        final int relativeTime = pLVChatPlaybackData.getRelativeTime();
        final int id = pLVChatPlaybackData.getId();
        final boolean[] zArr = {false};
        this.loadPreviousTask = Observable.just(1).observeOn(Schedulers.io()).flatMap(new Function<Integer, ObservableSource<List<PLVChatPlaybackData>>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.15
            @Override // io.reactivex.functions.Function
            public ObservableSource<List<PLVChatPlaybackData>> apply(Integer num) throws Exception {
                ArrayList arrayList = new ArrayList();
                PLVChatPlaybackManager pLVChatPlaybackManager = PLVChatPlaybackManager.this;
                PLVChatPlaybackDataCacheTime pLVChatPlaybackDataCacheTimeFindCurrentTimeCache = pLVChatPlaybackManager.findCurrentTimeCache(pLVChatPlaybackManager.sessionId, relativeTime);
                if (pLVChatPlaybackDataCacheTimeFindCurrentTimeCache == null) {
                    return Observable.just(arrayList);
                }
                int startTime = pLVChatPlaybackDataCacheTimeFindCurrentTimeCache.getStartTime();
                int startId = pLVChatPlaybackDataCacheTimeFindCurrentTimeCache.getStartId();
                int iMax = Math.max(startId, ((id - startId) - 50) + startId);
                PLVChatPlaybackManager pLVChatPlaybackManager2 = PLVChatPlaybackManager.this;
                List listSafeFindPlaybackData = pLVChatPlaybackManager2.safeFindPlaybackData(50, "sessionId = ? and relativeTime >= ? and relativeTime <= ? and id >= ? and id < ?", pLVChatPlaybackManager2.sessionId, startTime + "", relativeTime + "", iMax + "", id + "");
                StringBuilder sb = new StringBuilder();
                sb.append("loadPreviousTask id：");
                sb.append(pLVChatPlaybackDataCacheTimeFindCurrentTimeCache.getPartId());
                sb.append("*size：");
                sb.append(listSafeFindPlaybackData.size());
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, sb.toString());
                if (listSafeFindPlaybackData.size() > 0) {
                    return Observable.just(listSafeFindPlaybackData);
                }
                int[] previousTimeBucket = PLVChatPlaybackManager.this.getPreviousTimeBucket(relativeTime);
                int i2 = previousTimeBucket[0];
                int i3 = previousTimeBucket[1];
                int i4 = previousTimeBucket[2];
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "loadPreviousTask previous id：" + i4);
                if (i2 < 0 || i3 < 0) {
                    zArr[0] = true;
                    return Observable.just(listSafeFindPlaybackData);
                }
                PLVChatPlaybackManager pLVChatPlaybackManager3 = PLVChatPlaybackManager.this;
                PLVChatPlaybackDataCacheTime pLVChatPlaybackDataCacheTimeFindTimeCacheWithStartTime = pLVChatPlaybackManager3.findTimeCacheWithStartTime(pLVChatPlaybackManager3.sessionId, i2);
                if (pLVChatPlaybackDataCacheTimeFindTimeCacheWithStartTime == null) {
                    PLVChatPlaybackManager pLVChatPlaybackManager4 = PLVChatPlaybackManager.this;
                    return pLVChatPlaybackManager4.requestNetDataForPrevious(pLVChatPlaybackManager4.sessionId, i2, i3, i4);
                }
                int startId2 = pLVChatPlaybackDataCacheTimeFindTimeCacheWithStartTime.getStartId();
                int iMax2 = Math.max(startId2, ((pLVChatPlaybackDataCacheTimeFindTimeCacheWithStartTime.getEndId() - startId2) - 50) + 1 + startId2);
                PLVChatPlaybackManager pLVChatPlaybackManager5 = PLVChatPlaybackManager.this;
                List listSafeFindPlaybackData2 = pLVChatPlaybackManager5.safeFindPlaybackData(50, "sessionId = ? and relativeTime >= ? and relativeTime <= ? and id >= ?", pLVChatPlaybackManager5.sessionId, i2 + "", i3 + "", iMax2 + "");
                StringBuilder sb2 = new StringBuilder();
                sb2.append("loadPreviousTask previous load cache size：");
                sb2.append(listSafeFindPlaybackData2.size());
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, sb2.toString());
                return Observable.just(listSafeFindPlaybackData2);
            }
        }).observeOn(AndroidSchedulers.mainThread()).map(new Function<List<PLVChatPlaybackData>, List<PLVChatPlaybackData>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.14
            @Override // io.reactivex.functions.Function
            public List<PLVChatPlaybackData> apply(List<PLVChatPlaybackData> list) throws Exception {
                PLVChatPlaybackManager.this.fillDisPlayDataWithPrevious(list);
                return list;
            }
        }).doAfterTerminate(new Action() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.13
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "loadPreviousTask doAfterTerminate dispose：" + Thread.currentThread().getName());
                PLVChatPlaybackManager pLVChatPlaybackManager = PLVChatPlaybackManager.this;
                pLVChatPlaybackManager.dispose(pLVChatPlaybackManager.loadPreviousTask);
                if (zArr[0]) {
                    PLVChatPlaybackManager.this.disableLoadPreviousByClearData = false;
                }
                PLVChatPlaybackManager.this.callData(new ExCallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.13.1
                    {
                        PLVChatPlaybackManager pLVChatPlaybackManager2 = PLVChatPlaybackManager.this;
                    }

                    @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ExCallListener, com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
                    public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                        super.call(iPLVChatPlaybackCallDataListener);
                        iPLVChatPlaybackCallDataListener.onLoadPreviousFinish();
                    }

                    @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ExCallListener
                    public void exCall(@NonNull PLVChatPlaybackCallDataExListener pLVChatPlaybackCallDataExListener) {
                        AnonymousClass13 anonymousClass13 = AnonymousClass13.this;
                        if (zArr[0]) {
                            pLVChatPlaybackCallDataExListener.onLoadPreviousEnabled(false, PLVChatPlaybackManager.this.disableLoadPreviousByClearData);
                        }
                    }
                });
            }
        }).subscribe(new Consumer<List<PLVChatPlaybackData>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.11
            @Override // io.reactivex.functions.Consumer
            public void accept(List<PLVChatPlaybackData> list) throws Exception {
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.12
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(th);
            }
        });
    }

    private void removeDataFromEnd(List<PLVChatPlaybackData> list, int i2) {
        while (i2 > 0) {
            if (!list.isEmpty()) {
                list.remove(list.size() - 1);
            }
            i2--;
        }
    }

    private void removeDataFromStart(List<PLVChatPlaybackData> list, int i2) {
        while (i2 > 0) {
            if (!list.isEmpty()) {
                list.remove(0);
            }
            i2--;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<Integer> requestHistoryPartData(String str, final String str2, final int i2, final int i3) {
        return PLVApiManager.getPlvApichatApi().getPlaybackHistoryPart(str, str2, i2).map(new Function<ResponseBody, Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.19
            @Override // io.reactivex.functions.Function
            public Integer apply(ResponseBody responseBody) throws Exception {
                List arrayList = (List) PLVChatPlaybackManager.this.historyPartMap.get(str2);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    PLVChatPlaybackManager.this.historyPartMap.put(str2, arrayList);
                }
                JSONObject jSONObjectOptJSONObject = new JSONObject(responseBody.string()).optJSONObject("data");
                if (jSONObjectOptJSONObject != null) {
                    int iOptInt = jSONObjectOptJSONObject.optInt("totalPage");
                    JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("partDetail");
                    if (jSONArrayOptJSONArray != null) {
                        for (int i4 = 0; i4 < jSONArrayOptJSONArray.length(); i4++) {
                            JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray.optJSONObject(i4);
                            int iOptInt2 = jSONObjectOptJSONObject2.optInt("id");
                            int iOptInt3 = jSONObjectOptJSONObject2.optInt("startTime");
                            if (iOptInt3 != 0) {
                                iOptInt3++;
                            }
                            int iOptInt4 = jSONObjectOptJSONObject2.optInt("endTime");
                            PLVHistoryPartVO pLVHistoryPartVO = new PLVHistoryPartVO();
                            pLVHistoryPartVO.setId(iOptInt2);
                            pLVHistoryPartVO.setStartTime(iOptInt3);
                            pLVHistoryPartVO.setEndTime(iOptInt4);
                            pLVHistoryPartVO.setInPage(i2);
                            pLVHistoryPartVO.setTotalPage(iOptInt);
                            arrayList.add(pLVHistoryPartVO);
                        }
                    }
                }
                return Integer.valueOf(i3);
            }
        }).doOnError(new Consumer<Throwable>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.18
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                ResponseBody responseBodyErrorBody;
                if (!(th instanceof HttpException) || (responseBodyErrorBody = ((HttpException) th).response().errorBody()) == null) {
                    PLVCommonLog.exception(th);
                    return;
                }
                PLVCommonLog.e(PLVChatPlaybackManager.TAG, "history part请求失败：" + new JSONObject(responseBodyErrorBody.string()).optString("message"));
            }
        }).onErrorResumeNext(new ObservableSource<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.17
            @Override // io.reactivex.ObservableSource
            public void subscribe(Observer<? super Integer> observer) {
                observer.onNext(Integer.valueOf(i3));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestNetDataByNoCache(final String str, final int i2) {
        Disposable disposable = this.requestNetDataTask;
        if (disposable != null && !disposable.isDisposed()) {
            PLVCommonLog.d(TAG, "requestNetDataTask isRunning");
            return;
        }
        Disposable disposable2 = this.requestNextNetDataTask;
        if (disposable2 != null && !disposable2.isDisposed()) {
            PLVCommonLog.d(TAG, "requestNetDataTask->requestNextNetDataTask isRunning");
            return;
        }
        int[] currentTimeBucket = getCurrentTimeBucket(i2);
        final int i3 = currentTimeBucket[0];
        final int i4 = currentTimeBucket[1];
        final int i5 = currentTimeBucket[2];
        if (i3 < 0 || i4 < 0) {
            PLVCommonLog.d(TAG, "requestNetDataByNoCache break：" + i3 + "*" + i4);
            return;
        }
        PLVCommonLog.d(TAG, "requestNetDataByNoCache：" + i2 + "*" + i3 + "*" + i4 + "*id：" + i5);
        dispose(this.requestNetDataTask);
        this.requestNetDataTask = PLVApiManager.getPlvApichatApi().getPlaybackHistoryList(this.roomId, str, i5).subscribeOn(Schedulers.io()).map(new Function<ResponseBody, Pair<List<PLVChatPlaybackData>, List<PLVChatPlaybackData>>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.25
            @Override // io.reactivex.functions.Function
            public Pair<List<PLVChatPlaybackData>, List<PLVChatPlaybackData>> apply(ResponseBody responseBody) throws Exception {
                ArrayList arrayList = new ArrayList();
                PLVChatPlaybackManager.this.transformToChatListData(responseBody.string(), arrayList);
                return PLVChatPlaybackManager.this.acceptRequestData(i2, i3, i4, i5, arrayList);
            }
        }).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<Pair<List<PLVChatPlaybackData>, List<PLVChatPlaybackData>>, ObservableSource<Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.24
            @Override // io.reactivex.functions.Function
            public ObservableSource<Integer> apply(Pair<List<PLVChatPlaybackData>, List<PLVChatPlaybackData>> pair) throws Exception {
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "requestNetDataByNoCache fillDisplayData：" + ((List) pair.first).size() + "*" + ((List) pair.second).size());
                return PLVChatPlaybackManager.this.fillDisplayData(str, i2, i4, (List) pair.first, (List) pair.second);
            }
        }).doAfterTerminate(new Action() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.23
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "requestNetDataByNoCache doAfterTerminate dispose：" + Thread.currentThread().getName());
                PLVChatPlaybackManager pLVChatPlaybackManager = PLVChatPlaybackManager.this;
                pLVChatPlaybackManager.dispose(pLVChatPlaybackManager.requestNetDataTask);
            }
        }).doOnDispose(new Action() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.22
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "requestNetDataByNoCache dispose touch");
            }
        }).subscribe(new Consumer<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.20
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) throws Exception {
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.21
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ObservableSource<List<PLVChatPlaybackData>> requestNetDataForPrevious(final String str, final int i2, final int i3, final int i4) {
        return PLVApiManager.getPlvApichatApi().getPlaybackHistoryList(this.roomId, str, i4).map(new Function<ResponseBody, List<PLVChatPlaybackData>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.16
            @Override // io.reactivex.functions.Function
            public List<PLVChatPlaybackData> apply(ResponseBody responseBody) throws Exception {
                ArrayList arrayList = new ArrayList();
                PLVChatPlaybackManager.this.transformToChatListData(responseBody.string(), arrayList);
                PLVChatPlaybackManager.this.saveData(str, i2, i3, i4, arrayList);
                List<PLVChatPlaybackData> arrayList2 = new ArrayList<>();
                if (arrayList.size() > 0) {
                    arrayList2 = arrayList.subList(Math.max(0, arrayList.size() - 50), arrayList.size());
                }
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "requestNetDataForPrevious size：" + arrayList.size() + "*" + arrayList2.size());
                return arrayList2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestNextTimeDataByNoCache(final String str, int i2) {
        Disposable disposable = this.requestNextNetDataTask;
        if (disposable != null && !disposable.isDisposed()) {
            PLVCommonLog.d(TAG, "requestNextNetDataTask isRunning");
            return;
        }
        int[] timeBucketWithStartTime = getTimeBucketWithStartTime(i2);
        final int i3 = timeBucketWithStartTime[0];
        final int i4 = timeBucketWithStartTime[1];
        final int i5 = timeBucketWithStartTime[2];
        if (i3 < 0 || i4 < 0) {
            PLVCommonLog.d(TAG, "requestNextTimeDataByNoCache break：" + i3 + "*" + i4);
            return;
        }
        PLVCommonLog.d(TAG, "requestNextTimeDataByNoCache：" + i3 + "*" + i4 + "*id：" + i5);
        dispose(this.requestNextNetDataTask);
        this.requestNextNetDataTask = PLVApiManager.getPlvApichatApi().getPlaybackHistoryList(this.roomId, str, i5).subscribeOn(Schedulers.io()).map(new Function<ResponseBody, List<PLVChatPlaybackData>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.38
            @Override // io.reactivex.functions.Function
            public List<PLVChatPlaybackData> apply(ResponseBody responseBody) throws Exception {
                ArrayList arrayList = new ArrayList();
                PLVChatPlaybackManager.this.transformToChatListData(responseBody.string(), arrayList);
                PLVChatPlaybackManager.this.saveData(str, i3, i4, i5, arrayList);
                return arrayList;
            }
        }).observeOn(AndroidSchedulers.mainThread()).map(new Function<List<PLVChatPlaybackData>, List<PLVChatPlaybackData>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.37
            @Override // io.reactivex.functions.Function
            public List<PLVChatPlaybackData> apply(List<PLVChatPlaybackData> list) throws Exception {
                int size = 500 - PLVChatPlaybackManager.this.tailToDisplayDataList.size();
                if (size > 0) {
                    List<PLVChatPlaybackData> listSubList = list.subList(0, Math.min(size, list.size()));
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, "requestNextTimeDataByNoCache addDataToTail：" + PLVChatPlaybackManager.this.tailToDisplayDataList.size() + "*" + listSubList.size());
                    PLVChatPlaybackManager.this.tailToDisplayDataList.addAll(listSubList);
                }
                return list;
            }
        }).doAfterTerminate(new Action() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.36
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "requestNextTimeDataByNoCache doAfterTerminate dispose：" + Thread.currentThread().getName());
                PLVChatPlaybackManager pLVChatPlaybackManager = PLVChatPlaybackManager.this;
                pLVChatPlaybackManager.dispose(pLVChatPlaybackManager.requestNextNetDataTask);
            }
        }).subscribe(new Consumer<List<PLVChatPlaybackData>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.34
            @Override // io.reactivex.functions.Consumer
            public void accept(List<PLVChatPlaybackData> list) throws Exception {
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.35
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<Integer> requestNextTimeDataIfNoCache(final String str, final int i2) {
        return Observable.just(1).observeOn(Schedulers.io()).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.29
            @Override // io.reactivex.functions.Predicate
            public boolean test(Integer num) throws Exception {
                if (i2 == -1) {
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, "requestNextTimeDataIfNoCache break");
                }
                return i2 != -1;
            }
        }).flatMap(new Function<Integer, ObservableSource<Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.28
            @Override // io.reactivex.functions.Function
            public ObservableSource<Integer> apply(Integer num) throws Exception {
                if (!PLVChatPlaybackManager.this.hasTimeCacheWithStartTime(str, i2)) {
                    return Observable.just(1);
                }
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "hasCurrentTimeCache：" + i2);
                return PLVChatPlaybackManager.this.getNextTimeDataFromCache(i2);
            }
        }).observeOn(AndroidSchedulers.mainThread()).map(new Function<Integer, Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.27
            @Override // io.reactivex.functions.Function
            public Integer apply(Integer num) throws Exception {
                PLVChatPlaybackManager.this.requestNextTimeDataByNoCache(str, i2);
                return 0;
            }
        }).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.26
            @Override // io.reactivex.functions.Predicate
            public boolean test(Integer num) throws Exception {
                return false;
            }
        });
    }

    private List<PLVChatPlaybackDataCacheTime> safeFindCacheTime(final String... strArr) {
        return (List) safeRun(new ValueRunnable<List<PLVChatPlaybackDataCacheTime>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.61
            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ValueRunnable
            public List<PLVChatPlaybackDataCacheTime> run() {
                return LitePal.where(strArr).find(PLVChatPlaybackDataCacheTime.class);
            }
        }, new ArrayList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<PLVChatPlaybackData> safeFindPlaybackData(String... strArr) {
        return safeFindPlaybackData(-1, strArr);
    }

    private static <T> T safeRun(@NonNull ValueRunnable<T> valueRunnable, T t2) {
        try {
            return valueRunnable.run();
        } catch (Throwable th) {
            PLVCommonLog.exception(th);
            return t2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveData(final String str, final int i2, final int i3, final int i4, final List<PLVChatPlaybackData> list) throws InterruptedException {
        if (safeRun(new ValueRunnable<Object>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.63
            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ValueRunnable
            public Object run() {
                LitePal.saveAll(list);
                PLVChatPlaybackDataCacheTime pLVChatPlaybackDataCacheTime = new PLVChatPlaybackDataCacheTime();
                pLVChatPlaybackDataCacheTime.setPartId(i4);
                pLVChatPlaybackDataCacheTime.setStartTime(i2);
                pLVChatPlaybackDataCacheTime.setEndTime(i3);
                pLVChatPlaybackDataCacheTime.setSessionId(str);
                if (list.size() > 0) {
                    pLVChatPlaybackDataCacheTime.setStartId(((PLVChatPlaybackData) list.get(0)).getId());
                    pLVChatPlaybackDataCacheTime.setEndId(((PLVChatPlaybackData) list.get(r1.size() - 1)).getId());
                }
                pLVChatPlaybackDataCacheTime.save();
                return new Object();
            }
        }, null) == null) {
            try {
                List<PLVHistoryPartVO> list2 = this.historyPartMap.get(str);
                if (list2 == null || list2.isEmpty() || list2.get(list2.size() - 1).getId() != i4) {
                    Thread.sleep(2400L);
                } else {
                    Thread.sleep(7200L);
                }
            } catch (InterruptedException unused) {
                PLVCommonLog.d(TAG, "saveData sleep break");
            }
        }
    }

    private void startTask() {
        startTask(false, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void transformToChatListData(String str, List<PLVChatPlaybackData> list) throws Exception {
        JSONArray jSONArrayOptJSONArray;
        JSONObject jSONObjectOptJSONObject = new JSONObject(str).optJSONObject("data");
        if (jSONObjectOptJSONObject == null || (jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("list")) == null) {
            return;
        }
        for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
            JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray.optJSONObject(i2);
            JSONObject jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject(PLVLinkMicManager.USER);
            String strOptString = jSONObjectOptJSONObject2.optString(a.f3175h);
            if (isEmptyAndNullStr(strOptString) || "chatImg".equals(strOptString)) {
                PLVChatPlaybackData pLVChatPlaybackData = new PLVChatPlaybackData();
                pLVChatPlaybackData.setContent(jSONObjectOptJSONObject2.optString("content"));
                pLVChatPlaybackData.setMsgType(strOptString);
                pLVChatPlaybackData.setSessionId(jSONObjectOptJSONObject2.optString(PLVLinkMicManager.SESSION_ID));
                pLVChatPlaybackData.setQuote(jSONObjectOptJSONObject2.optString("quote"));
                pLVChatPlaybackData.setRelativeTime(jSONObjectOptJSONObject2.optInt("relativeTime"));
                if (jSONObjectOptJSONObject3 != null) {
                    pLVChatPlaybackData.setActor(jSONObjectOptJSONObject3.optString("actor"));
                    pLVChatPlaybackData.setUserId(jSONObjectOptJSONObject3.optString("userId"));
                    pLVChatPlaybackData.setPic(jSONObjectOptJSONObject3.optString("pic"));
                    pLVChatPlaybackData.setNick(jSONObjectOptJSONObject3.optString(PLVLinkMicManager.NICK));
                    pLVChatPlaybackData.setUserType(jSONObjectOptJSONObject3.optString(PLVLinkMicManager.USER_TYPE));
                }
                improveField(pLVChatPlaybackData);
                list.add(pLVChatPlaybackData);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<Integer> useCacheData(final String str, final int i2, final PLVChatPlaybackDataCacheTime pLVChatPlaybackDataCacheTime) {
        return Observable.just(1).observeOn(AndroidSchedulers.mainThread()).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.47
            @Override // io.reactivex.functions.Predicate
            public boolean test(Integer num) throws Exception {
                return PLVChatPlaybackManager.this.displayDataList.size() <= 0;
            }
        }).observeOn(Schedulers.io()).map(new Function<Integer, Object[]>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.46
            @Override // io.reactivex.functions.Function
            public Object[] apply(Integer num) throws Exception {
                int startTime = pLVChatPlaybackDataCacheTime.getStartTime();
                int endTime = pLVChatPlaybackDataCacheTime.getEndTime();
                int startId = pLVChatPlaybackDataCacheTime.getStartId();
                List listSafeFindPlaybackData = PLVChatPlaybackManager.this.safeFindPlaybackData(500, "sessionId = ? and relativeTime = ?", str, i2 + "");
                int size = listSafeFindPlaybackData.size();
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "useCacheData currentTimeDataSize：" + size + "*time：" + i2 + "*startTime：" + startTime + "*entTime：" + endTime + "*id：" + pLVChatPlaybackDataCacheTime.getPartId());
                if (i2 > startTime) {
                    if (listSafeFindPlaybackData.size() == 0) {
                        listSafeFindPlaybackData = PLVChatPlaybackManager.this.safeFindPlaybackData("sessionId = ? and relativeTime < ? and relativeTime >= ?", str, i2 + "", startTime + "");
                        StringBuilder sb = new StringBuilder();
                        sb.append("useCacheData findData1：");
                        sb.append(listSafeFindPlaybackData.size());
                        PLVCommonLog.d(PLVChatPlaybackManager.TAG, sb.toString());
                        if (listSafeFindPlaybackData.size() > 500) {
                            int size2 = listSafeFindPlaybackData.size() - 500;
                            listSafeFindPlaybackData = listSafeFindPlaybackData.subList(size2, listSafeFindPlaybackData.size());
                            PLVCommonLog.d(PLVChatPlaybackManager.TAG, "useCacheData findData1：" + size2 + "*" + listSafeFindPlaybackData.size());
                        }
                    } else if (listSafeFindPlaybackData.size() < 500) {
                        int iMax = Math.max(((PLVChatPlaybackData) listSafeFindPlaybackData.get(0)).getId() - (500 - listSafeFindPlaybackData.size()), startId);
                        int size3 = 500 - listSafeFindPlaybackData.size();
                        List listSafeFindPlaybackData2 = PLVChatPlaybackManager.this.safeFindPlaybackData(size3, "sessionId = ? and relativeTime < ? and relativeTime >= ? and id >= ?", str, i2 + "", startTime + "", iMax + "");
                        listSafeFindPlaybackData.addAll(0, listSafeFindPlaybackData2);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("useCacheData findData2：");
                        sb2.append(listSafeFindPlaybackData2.size());
                        PLVCommonLog.d(PLVChatPlaybackManager.TAG, sb2.toString());
                    }
                }
                List arrayList = new ArrayList();
                if (size < 500) {
                    if (i2 < endTime) {
                        arrayList = PLVChatPlaybackManager.this.safeFindPlaybackData(500, "sessionId = ? and relativeTime > ? and relativeTime <= ?", str, i2 + "", endTime + "");
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("useCacheData findData3：");
                        sb3.append(arrayList.size());
                        PLVCommonLog.d(PLVChatPlaybackManager.TAG, sb3.toString());
                    }
                } else if (i2 <= endTime) {
                    int id = ((PLVChatPlaybackData) listSafeFindPlaybackData.get(listSafeFindPlaybackData.size() - 1)).getId();
                    List listSafeFindPlaybackData3 = PLVChatPlaybackManager.this.safeFindPlaybackData(500, "sessionId = ? and relativeTime >= ? and relativeTime <= ? and id > ?", str, i2 + "", endTime + "", id + "");
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("useCacheData findData4：");
                    sb4.append(listSafeFindPlaybackData3.size());
                    sb4.append("*");
                    sb4.append(id);
                    PLVCommonLog.d(PLVChatPlaybackManager.TAG, sb4.toString());
                    arrayList = listSafeFindPlaybackData3;
                }
                PLVCommonLog.d(PLVChatPlaybackManager.TAG, "useCacheData：" + listSafeFindPlaybackData.size() + "*" + arrayList.size());
                return new Object[]{Integer.valueOf(endTime), listSafeFindPlaybackData, arrayList};
            }
        }).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<Object[], ObservableSource<Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.45
            @Override // io.reactivex.functions.Function
            public ObservableSource<Integer> apply(Object[] objArr) throws Exception {
                return PLVChatPlaybackManager.this.fillDisplayData(str, i2, ((Integer) objArr[0]).intValue(), (List) objArr[1], (List) objArr[2]);
            }
        }).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.44
            @Override // io.reactivex.functions.Predicate
            public boolean test(Integer num) throws Exception {
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Pair<Integer, Integer> useTailData(int i2) {
        PLVChatPlaybackData pLVChatPlaybackData = this.tailToDisplayDataList.get(r0.size() - 1);
        int id = pLVChatPlaybackData.getId();
        int relativeTime = pLVChatPlaybackData.getRelativeTime();
        List<PLVChatPlaybackData> arrayList = new ArrayList<>();
        for (int i3 = 0; i3 < this.tailToDisplayDataList.size(); i3++) {
            PLVChatPlaybackData pLVChatPlaybackData2 = this.tailToDisplayDataList.get(i3);
            if (pLVChatPlaybackData2.getRelativeTime() > i2) {
                break;
            }
            arrayList.add(pLVChatPlaybackData2);
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        int size = 500 - this.displayDataList.size();
        if (size > 0) {
            arrayList = arrayList.subList(0, Math.min(size, arrayList.size()));
        } else {
            IPLVChatPlaybackGetDataListener iPLVChatPlaybackGetDataListener = this.getDataListener;
            if (iPLVChatPlaybackGetDataListener != null && iPLVChatPlaybackGetDataListener.canScrollBottom()) {
                callData(new CallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.48
                    @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
                    public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                        iPLVChatPlaybackCallDataListener.onHasNotAddedData();
                    }
                });
                PLVCommonLog.d(TAG, "useTailData break");
                return null;
            }
        }
        removeDataFromStart(this.tailToDisplayDataList, arrayList.size());
        PLVCommonLog.d(TAG, "useTailData：" + this.tailToDisplayDataList.size() + "*" + arrayList.size());
        fillTailDisplayData(arrayList, i2);
        return new Pair<>(Integer.valueOf(id), Integer.valueOf(relativeTime));
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackManager
    public void addOnCallDataListener(IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
        if (this.callDataListenerList == null) {
            this.callDataListenerList = new ArrayList();
        }
        if (iPLVChatPlaybackCallDataListener == null || this.callDataListenerList.contains(iPLVChatPlaybackCallDataListener)) {
            return;
        }
        iPLVChatPlaybackCallDataListener.onManager(this);
        this.callDataListenerList.add(iPLVChatPlaybackCallDataListener);
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackManager
    public void destroy() {
        List<IPLVChatPlaybackCallDataListener> list = this.callDataListenerList;
        if (list != null) {
            list.clear();
        }
        this.callDataListenerList = null;
        this.getDataListener = null;
        this.historyPartMap.clear();
        this.displayDataList.clear();
        this.tailToDisplayDataList.clear();
        disposeAllTask();
        safeRun(new ValueRunnable<Object>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.4
            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ValueRunnable
            public Object run() {
                LitePal.deleteDatabase(PLVChatPlaybackManager.DB_NAME);
                return null;
            }
        }, null);
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackManager
    public void loadPrevious() {
        if (this.displayDataList.isEmpty() || TextUtils.isEmpty(this.sessionId)) {
            callData(new CallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.3
                @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
                public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                    iPLVChatPlaybackCallDataListener.onLoadPreviousFinish();
                }
            });
        } else {
            disposeLoadPreviousTask();
            loadPreviousTask();
        }
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackManager
    public void removeOnCallDataListener(IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
        List<IPLVChatPlaybackCallDataListener> list = this.callDataListenerList;
        if (list != null) {
            list.remove(iPLVChatPlaybackCallDataListener);
        }
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackManager
    public void seek(int i2) {
        if (i2 >= 0 && !TextUtils.isEmpty(this.sessionId)) {
            disposeAllTask();
            clearListDataAndCall();
            startTask(true, i2);
        } else {
            PLVCommonLog.e(TAG, "chat playback seek error：" + i2 + "*" + this.sessionId);
        }
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackManager
    public void setOnGetDataListener(IPLVChatPlaybackGetDataListener iPLVChatPlaybackGetDataListener) {
        this.getDataListener = iPLVChatPlaybackGetDataListener;
    }

    @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackManager
    public void start(String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str) || str.equals(this.sessionId)) {
            PLVCommonLog.e(TAG, "chat playback start error：" + str + "*" + str2);
            return;
        }
        this.sessionId = str;
        this.roomId = str2;
        this.saveTime = 0;
        clearListDataAndCall();
        callData(new CallListener() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.2
            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.CallListener
            public void call(@NonNull IPLVChatPlaybackCallDataListener iPLVChatPlaybackCallDataListener) {
                iPLVChatPlaybackCallDataListener.onData(PLVChatPlaybackManager.this.displayDataList);
            }
        });
        disposeAllTask();
        startTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<PLVChatPlaybackData> safeFindPlaybackData(final int i2, final String... strArr) {
        return (List) safeRun(new ValueRunnable<List<PLVChatPlaybackData>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.62
            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackManager.ValueRunnable
            public List<PLVChatPlaybackData> run() {
                return i2 != -1 ? PLVChatPlaybackManager.this.improveField((List<PLVChatPlaybackData>) LitePal.where(strArr).limit(i2).find(PLVChatPlaybackData.class)) : PLVChatPlaybackManager.this.improveField((List<PLVChatPlaybackData>) LitePal.where(strArr).find(PLVChatPlaybackData.class));
            }
        }, new ArrayList());
    }

    private void startTask(boolean z2, final int i2) {
        final boolean[] zArr = {z2};
        this.startTask = Observable.interval(0L, 800L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).map(new Function<Long, Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.10
            @Override // io.reactivex.functions.Function
            public Integer apply(Long l2) throws Exception {
                int iCurrentTime = zArr[0] ? i2 : PLVChatPlaybackManager.this.getDataListener == null ? 0 : PLVChatPlaybackManager.this.getDataListener.currentTime();
                zArr[0] = false;
                return Integer.valueOf(iCurrentTime);
            }
        }).observeOn(Schedulers.io()).flatMap(new Function<Integer, ObservableSource<Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.9
            @Override // io.reactivex.functions.Function
            public ObservableSource<Integer> apply(Integer num) throws Exception {
                List list = (List) PLVChatPlaybackManager.this.historyPartMap.get(PLVChatPlaybackManager.this.sessionId);
                if (list == null) {
                    PLVChatPlaybackManager pLVChatPlaybackManager = PLVChatPlaybackManager.this;
                    return pLVChatPlaybackManager.requestHistoryPartData(pLVChatPlaybackManager.roomId, PLVChatPlaybackManager.this.sessionId, 1, num.intValue());
                }
                if (list.size() > 0) {
                    PLVHistoryPartVO pLVHistoryPartVO = (PLVHistoryPartVO) list.get(list.size() - 1);
                    int endTime = pLVHistoryPartVO.getEndTime();
                    boolean z3 = pLVHistoryPartVO.getInPage() < pLVHistoryPartVO.getTotalPage();
                    if (num.intValue() > endTime && z3) {
                        PLVChatPlaybackManager pLVChatPlaybackManager2 = PLVChatPlaybackManager.this;
                        return pLVChatPlaybackManager2.requestHistoryPartData(pLVChatPlaybackManager2.roomId, PLVChatPlaybackManager.this.sessionId, pLVHistoryPartVO.getInPage() + 1, num.intValue());
                    }
                }
                return Observable.just(num);
            }
        }).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<Integer, ObservableSource<Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.8
            @Override // io.reactivex.functions.Function
            public ObservableSource<Integer> apply(Integer num) throws Exception {
                if (num.intValue() + 1500 < PLVChatPlaybackManager.this.saveTime) {
                    PLVChatPlaybackManager.this.clearListDataAndCall();
                }
                PLVChatPlaybackManager.this.saveTime = num.intValue();
                if (PLVChatPlaybackManager.this.tailToDisplayDataList.size() <= 0) {
                    return Observable.just(num);
                }
                Pair pairUseTailData = PLVChatPlaybackManager.this.useTailData(num.intValue());
                if (pairUseTailData == null) {
                    return Observable.just(num).filter(new Predicate<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.8.1
                        @Override // io.reactivex.functions.Predicate
                        public boolean test(Integer num2) throws Exception {
                            return false;
                        }
                    });
                }
                PLVChatPlaybackManager pLVChatPlaybackManager = PLVChatPlaybackManager.this;
                return pLVChatPlaybackManager.fillTailData(pLVChatPlaybackManager.sessionId, pairUseTailData);
            }
        }).observeOn(Schedulers.io()).flatMap(new Function<Integer, ObservableSource<Integer>>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.7
            @Override // io.reactivex.functions.Function
            public ObservableSource<Integer> apply(Integer num) throws Exception {
                PLVChatPlaybackManager pLVChatPlaybackManager = PLVChatPlaybackManager.this;
                PLVChatPlaybackDataCacheTime pLVChatPlaybackDataCacheTimeFindCurrentTimeCache = pLVChatPlaybackManager.findCurrentTimeCache(pLVChatPlaybackManager.sessionId, num.intValue());
                if (pLVChatPlaybackDataCacheTimeFindCurrentTimeCache == null) {
                    return Observable.just(num);
                }
                PLVChatPlaybackManager pLVChatPlaybackManager2 = PLVChatPlaybackManager.this;
                return pLVChatPlaybackManager2.useCacheData(pLVChatPlaybackManager2.sessionId, num.intValue(), pLVChatPlaybackDataCacheTimeFindCurrentTimeCache);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) throws Exception {
                PLVChatPlaybackManager pLVChatPlaybackManager = PLVChatPlaybackManager.this;
                pLVChatPlaybackManager.requestNetDataByNoCache(pLVChatPlaybackManager.sessionId, num.intValue());
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.playback.chat.PLVChatPlaybackManager.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(th);
            }
        });
    }

    private PLVChatPlaybackData improveField(PLVChatPlaybackData pLVChatPlaybackData) {
        if (pLVChatPlaybackData == null) {
            return pLVChatPlaybackData;
        }
        if (pLVChatPlaybackData.getChatQuoteVO() == null && !isEmptyAndNullStr(pLVChatPlaybackData.getQuote())) {
            PLVChatQuoteVO pLVChatQuoteVO = (PLVChatQuoteVO) PLVGsonUtil.fromJson(PLVChatQuoteVO.class, pLVChatPlaybackData.getQuote());
            pLVChatPlaybackData.setChatQuoteVO(pLVChatQuoteVO);
            if (pLVChatQuoteVO != null && pLVChatQuoteVO.isSpeakMessage() && this.getDataListener != null && pLVChatQuoteVO.getObjects() == null) {
                pLVChatQuoteVO.setObjects(this.getDataListener.getParsedEmoObjects(pLVChatQuoteVO.getContent()));
            }
        }
        if (pLVChatPlaybackData.isImgMsg()) {
            if (pLVChatPlaybackData.getChatImgContent() == null) {
                pLVChatPlaybackData.setChatImgContent((PLVChatImgContent) PLVGsonUtil.fromJson(PLVChatImgContent.class, pLVChatPlaybackData.getContent()));
            }
        } else if (this.getDataListener != null && pLVChatPlaybackData.getObjects() == null) {
            pLVChatPlaybackData.setObjects(this.getDataListener.getParsedEmoObjects(pLVChatPlaybackData.getContent()));
        }
        if (isEmptyAndNullStr(pLVChatPlaybackData.getActor())) {
            pLVChatPlaybackData.setActor(null);
        }
        return pLVChatPlaybackData;
    }
}
