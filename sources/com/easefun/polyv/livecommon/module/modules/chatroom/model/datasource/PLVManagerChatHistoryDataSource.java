package com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.alipay.sdk.authjs.a;
import com.easefun.polyv.livecommon.module.modules.chatroom.model.vo.PLVManagerChatHistoryLoadStatus;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.chatroom.PLVChatroomManager;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.history.PLVChatImgHistoryEvent;
import com.plv.socket.event.history.PLVSpeakHistoryEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class PLVManagerChatHistoryDataSource {
    private ObservableEmitter<PLVBaseViewData<PLVBaseEvent>> chatEventEmitter;
    private ObservableEmitter<PLVManagerChatHistoryLoadStatus> loadStatusEmitter;
    public final Observable<PLVBaseViewData<PLVBaseEvent>> chatEventObservable = Observable.create(new ObservableOnSubscribe<PLVBaseViewData<PLVBaseEvent>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatHistoryDataSource.1
        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(@NonNull ObservableEmitter<PLVBaseViewData<PLVBaseEvent>> observableEmitter) {
            PLVManagerChatHistoryDataSource.this.chatEventEmitter = observableEmitter;
        }
    });
    public final Observable<PLVManagerChatHistoryLoadStatus> loadStatusObservable = Observable.create(new ObservableOnSubscribe<PLVManagerChatHistoryLoadStatus>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatHistoryDataSource.2
        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(@NonNull ObservableEmitter<PLVManagerChatHistoryLoadStatus> observableEmitter) {
            PLVManagerChatHistoryDataSource.this.loadStatusEmitter = observableEmitter;
        }
    });
    private final CompositeDisposable disposables = new CompositeDisposable();
    private PLVManagerChatHistoryLoadStatus lastLoadStatus = new PLVManagerChatHistoryLoadStatus();

    /* JADX INFO: Access modifiers changed from: private */
    public List<PLVBaseViewData<PLVBaseEvent>> acceptChatHistory(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jsonArray.length(); i2++) {
            JSONObject jSONObjectOptJSONObject = jsonArray.optJSONObject(i2);
            if (jSONObjectOptJSONObject != null && TextUtils.isEmpty(jSONObjectOptJSONObject.optString(a.f3175h))) {
                String strOptString = jSONObjectOptJSONObject.optString("msgSource");
                JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(PLVLinkMicManager.USER);
                JSONObject jSONObjectOptJSONObject3 = jSONObjectOptJSONObject.optJSONObject("content");
                if (TextUtils.isEmpty(strOptString)) {
                    if (jSONObjectOptJSONObject2 != null && !"2".equals(jSONObjectOptJSONObject2.optString("uid")) && jSONObjectOptJSONObject3 == null) {
                        PLVSpeakHistoryEvent pLVSpeakHistoryEvent = (PLVSpeakHistoryEvent) PLVGsonUtil.fromJson(PLVSpeakHistoryEvent.class, jSONObjectOptJSONObject.toString());
                        if (pLVSpeakHistoryEvent != null && PLVSocketWrapper.getInstance().getLoginVO().getUserId().equals(pLVSpeakHistoryEvent.getUser().getUserId())) {
                            pLVSpeakHistoryEvent.getUser().setNick(PLVSocketWrapper.getInstance().getLoginVO().getNickName());
                        }
                        arrayList.add(0, new PLVBaseViewData(pLVSpeakHistoryEvent, 0));
                    }
                } else if ("chatImg".equals(strOptString)) {
                    PLVChatImgHistoryEvent pLVChatImgHistoryEvent = (PLVChatImgHistoryEvent) PLVGsonUtil.fromJson(PLVChatImgHistoryEvent.class, jSONObjectOptJSONObject.toString());
                    if (pLVChatImgHistoryEvent != null && PLVSocketWrapper.getInstance().getLoginVO().getUserId().equals(pLVChatImgHistoryEvent.getUser().getUserId())) {
                        pLVChatImgHistoryEvent.getUser().setNick(PLVSocketWrapper.getInstance().getLoginVO().getNickName());
                    }
                    arrayList.add(0, new PLVBaseViewData(pLVChatImgHistoryEvent, 0));
                }
            }
        }
        return arrayList;
    }

    private static String convertSpecialString(String input) {
        return input.replace("&lt;", "<").replace("&lt", "<").replace("&gt;", ">").replace("&gt", ">").replace("&yen;", "¥").replace("&yen", "¥");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLoadStatus(PLVManagerChatHistoryLoadStatus loadStatus) {
        this.lastLoadStatus = loadStatus;
        ObservableEmitter<PLVManagerChatHistoryLoadStatus> observableEmitter = this.loadStatusEmitter;
        if (observableEmitter != null) {
            observableEmitter.onNext(loadStatus);
        }
    }

    public void finalize() throws Throwable {
        this.disposables.dispose();
        super.finalize();
    }

    public void requestChatHistory(final String roomId, final int start, final int end) {
        updateLoadStatus(this.lastLoadStatus.copy().setLoading(true));
        this.disposables.add(PLVChatroomManager.getExtendChatHistory(roomId, start, end).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).map(new Function<ResponseBody, JSONArray>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatHistoryDataSource.7
            @Override // io.reactivex.functions.Function
            public JSONArray apply(@NonNull ResponseBody responseBody) throws Exception {
                return new JSONObject(responseBody.string()).optJSONArray("data");
            }
        }).doOnNext(new Consumer<JSONArray>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatHistoryDataSource.6
            @Override // io.reactivex.functions.Consumer
            public void accept(JSONArray jsonArray) {
                boolean z2 = jsonArray != null && jsonArray.length() > 0;
                PLVManagerChatHistoryDataSource pLVManagerChatHistoryDataSource = PLVManagerChatHistoryDataSource.this;
                pLVManagerChatHistoryDataSource.updateLoadStatus(pLVManagerChatHistoryDataSource.lastLoadStatus.copy().setLoading(false).setCanLoadMore(z2));
            }
        }).map(new Function<JSONArray, List<PLVBaseViewData<PLVBaseEvent>>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatHistoryDataSource.5
            @Override // io.reactivex.functions.Function
            public List<PLVBaseViewData<PLVBaseEvent>> apply(@NonNull JSONArray jsonArray) {
                return PLVManagerChatHistoryDataSource.this.acceptChatHistory(jsonArray);
            }
        }).subscribe(new Consumer<List<PLVBaseViewData<PLVBaseEvent>>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatHistoryDataSource.3
            @Override // io.reactivex.functions.Consumer
            public void accept(List<PLVBaseViewData<PLVBaseEvent>> plvBaseViewDatas) {
                if (PLVManagerChatHistoryDataSource.this.chatEventEmitter == null) {
                    return;
                }
                Iterator<PLVBaseViewData<PLVBaseEvent>> it = plvBaseViewDatas.iterator();
                while (it.hasNext()) {
                    PLVManagerChatHistoryDataSource.this.chatEventEmitter.onNext(it.next());
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatHistoryDataSource.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) {
                PLVCommonLog.exception(throwable);
            }
        }));
    }
}
