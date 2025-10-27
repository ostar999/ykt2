package com.easefun.polyv.livecommon.module.modules.interact.app;

import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractJSBridgeEventConst;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livescenes.feature.interact.IPLVInteractJSBridge;
import com.easefun.polyv.livescenes.feature.interact.PLVInteractAppAbs;
import com.easefun.polyv.livescenes.model.PolyvInteractiveCallbackVO;
import com.easefun.polyv.livescenes.model.lottery.PolyvLotteryEndVO;
import com.easefun.polyv.livescenes.net.PolyvApiManager;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.model.PLVInteractiveCallbackVO;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import java.io.IOException;
import javax.xml.transform.TransformerException;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.HttpException;

/* loaded from: classes3.dex */
public class PLVInteractLottery extends PLVInteractAppAbs {
    private static final String TAG = "PLVInteractLottery";
    private IPLVInteractSendServerResultToJs commonControl;
    private boolean isWinLotteryShow = false;
    private String lotteryId;
    private String lotterySessionId;
    private String winnerCode;

    public PLVInteractLottery(IPLVInteractSendServerResultToJs commonControl) {
        this.commonControl = commonControl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAbandonLotteryToServer() {
        PLVResponseExcutor.excuteDataBean(PolyvApiManager.getPolyvApichatApi().postLotteryAbandon(this.channelId, this.viewerId), String.class, new PLVrResponseCallback<String>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractLottery.7
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable e2) {
                super.onError(e2);
                PLVCommonLog.e(PLVInteractLottery.TAG, "放弃领奖信息上传失败");
                if (e2 instanceof HttpException) {
                    try {
                        ResponseBody responseBodyErrorBody = ((HttpException) e2).response().errorBody();
                        if (responseBodyErrorBody != null) {
                            LogUtils.e(responseBodyErrorBody.string());
                        }
                    } catch (IOException e3) {
                        PLVCommonLog.d(PLVInteractLottery.TAG, "postLotteryAbandon:" + e3.getMessage());
                    }
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<String> responseBean) {
                super.onFailure(responseBean);
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVInteractLottery.TAG, "postLotteryAbandon onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(String s2) {
                PLVCommonLog.d(PLVInteractLottery.TAG, "放弃领奖信息上传成功 " + s2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendWinLotteryToServer(String data) throws JSONException {
        String string;
        try {
            string = new JSONObject(data).getString("receiveInfo");
        } catch (JSONException e2) {
            PLVCommonLog.d(TAG, "sendWinLotteryToServer：" + e2.getMessage());
            string = "";
        }
        PLVResponseExcutor.excuteDataBean(PolyvApiManager.getPolyvApichatApi().postLotteryWinnerInfoNew(this.channelId, this.lotteryId, this.winnerCode, this.viewerId, string, this.lotterySessionId), String.class, new PLVrResponseCallback<String>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractLottery.6
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable e3) {
                super.onError(e3);
                PLVInteractLottery.this.commonControl.sendServerResultToJs(PLVGsonUtil.toJsonSimple(new PolyvInteractiveCallbackVO(PLVInteractiveCallbackVO.EVENT_LOTTERY, 400)));
                PLVCommonLog.exception(e3);
                if (e3 instanceof HttpException) {
                    try {
                        ResponseBody responseBodyErrorBody = ((HttpException) e3).response().errorBody();
                        if (responseBodyErrorBody != null) {
                            PLVCommonLog.e(PLVInteractLottery.TAG, responseBodyErrorBody.string());
                        }
                    } catch (IOException unused) {
                        PLVCommonLog.d(PLVInteractLottery.TAG, "postLotteryWinnerInfoNew: " + e3.getMessage());
                    }
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<String> responseBean) {
                super.onFailure(responseBean);
                PLVCommonLog.e(PLVInteractLottery.TAG, "抽奖信息上传失败" + responseBean);
                PLVInteractLottery.this.commonControl.sendServerResultToJs(PLVGsonUtil.toJsonSimple(new PolyvInteractiveCallbackVO(PLVInteractiveCallbackVO.EVENT_LOTTERY, 400)));
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVInteractLottery.TAG, "postLotteryWinnerInfoNew onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(String s2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.d("抽奖信息上传成功" + s2);
                PLVInteractLottery.this.commonControl.sendServerResultToJs(PLVGsonUtil.toJsonSimple(new PolyvInteractiveCallbackVO(PLVInteractiveCallbackVO.EVENT_LOTTERY, 200)));
            }
        });
    }

    public boolean onBackPress() {
        if (!this.isWinLotteryShow) {
            return false;
        }
        sendMsgToJs(PLVInteractJSBridgeEventConst.LOTTERY_CLOSE_WINNER, null, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractLottery.1
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String data) {
                PLVCommonLog.d(PLVInteractLottery.TAG, "LOTTERY_CLOSE_WINNER " + data);
            }
        });
        return true;
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void processSocketMsg(String msg, String event) {
        event.hashCode();
        switch (event) {
            case "LotteryEnd":
                final PolyvLotteryEndVO polyvLotteryEndVO = (PolyvLotteryEndVO) PLVGsonUtil.fromJson(PolyvLotteryEndVO.class, msg);
                if (polyvLotteryEndVO != null) {
                    notifyShow();
                    if (!polyvLotteryEndVO.getData().isEmpty()) {
                        this.winnerCode = polyvLotteryEndVO.getData().get(0).getWinnerCode();
                    }
                    this.lotterySessionId = polyvLotteryEndVO.getSessionId();
                    this.lotteryId = polyvLotteryEndVO.getLotteryId();
                    sendMsgToJs(PLVInteractJSBridgeEventConst.LOTTERY_STOP, msg, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractLottery.3
                        @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                        public void onCallBack(String data) {
                            if (polyvLotteryEndVO.getData().isEmpty()) {
                                return;
                            }
                            PLVInteractLottery.this.isWinLotteryShow = true;
                        }
                    });
                    break;
                }
                break;
            case "LotteryStart":
            case "ON_LOTTERY":
                notifyShow();
                sendMsgToJs(PLVInteractJSBridgeEventConst.LOTTERY_START, null, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractLottery.2
                    @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                    public void onCallBack(String data) {
                        PLVCommonLog.d(PLVInteractLottery.TAG, "LOTTERY_START " + data);
                    }
                });
                break;
        }
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void registerMsgReceiverFromJs(IPLVInteractJSBridge interactJSBridge) {
        interactJSBridge.registerMsgReceiverFromJs(PLVInteractJSBridgeEventConst.ON_SEND_WIN_DATA, new BridgeHandler() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractLottery.4
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String data, CallBackFunction function) throws JSONException {
                PLVCommonLog.d(PLVInteractLottery.TAG, "ON_SEND_WIN_DATA " + data);
                PLVInteractLottery.this.isWinLotteryShow = false;
                PLVOrientationManager.getInstance().unlockOrientation();
                PLVInteractLottery.this.sendWinLotteryToServer(data);
            }
        });
        interactJSBridge.registerMsgReceiverFromJs(PLVInteractJSBridgeEventConst.ON_ABANDON_LOTTERY, new BridgeHandler() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractLottery.5
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String data, CallBackFunction function) {
                PLVCommonLog.d(PLVInteractLottery.TAG, "ON_ABANDON_LOTTERY " + data);
                PLVInteractLottery.this.isWinLotteryShow = false;
                PLVOrientationManager.getInstance().unlockOrientation();
                PLVInteractLottery.this.sendAbandonLotteryToServer();
            }
        });
    }
}
