package com.easefun.polyv.livecommon.module.modules.interact.app;

import android.text.TextUtils;
import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractJSBridgeEventConst;
import com.easefun.polyv.livescenes.chatroom.PolyvChatroomManager;
import com.easefun.polyv.livescenes.feature.interact.IPLVInteractJSBridge;
import com.easefun.polyv.livescenes.feature.interact.PLVInteractAppAbs;
import com.easefun.polyv.livescenes.model.answer.PolyvJSQuestionVO;
import com.easefun.polyv.livescenes.model.answer.PolyvQuestionResultJsVO;
import com.easefun.polyv.livescenes.model.answer.PolyvQuestionResultVO;
import com.easefun.polyv.livescenes.model.answer.PolyvQuestionSResult;
import com.easefun.polyv.livescenes.model.answer.PolyvQuestionSocketVO;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.android.exoplayer2.ExoPlayer;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.model.web.PLVJSResponseVO;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.PLVSocketEvent;
import io.reactivex.functions.Consumer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class PLVInteractAnswer extends PLVInteractAppAbs {
    private static final int DELAY_SOCKET_MSG = 2000;
    private static final String TAG = "PLVInteractAnswer";
    private String curQuestionId;
    private boolean isQuestionAnswer = false;
    private Map<String, PolyvJSQuestionVO> questions = new ConcurrentHashMap();
    private String socketMsgStopQuestion = "";

    private void delay(final Runnable runnable) {
        PLVRxTimer.delay(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, new Consumer<Object>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractAnswer.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Object o2) {
                if (((com.plv.livescenes.feature.interact.PLVInteractAppAbs) PLVInteractAnswer.this).isDestroyed) {
                    return;
                }
                runnable.run();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveNewQuestionForStopQuestion(String socketMessage) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(socketMessage);
            jSONObject.put("EVENT", PLVSocketEvent.STOP_TEST_QUESTION);
            this.socketMsgStopQuestion = jSONObject.toString();
        } catch (JSONException e2) {
            PLVCommonLog.e(TAG, "保存问题失败\n" + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveSelectedOption(String data) {
        PolyvJSQuestionVO polyvJSQuestionVO = (PolyvJSQuestionVO) PLVGsonUtil.fromJson(PolyvJSQuestionVO.class, data);
        String str = TAG;
        PLVCommonLog.d(str, "receive result answer " + data);
        if (polyvJSQuestionVO == null || TextUtils.isEmpty(polyvJSQuestionVO.getQuestionId())) {
            return;
        }
        sendResultToServer(polyvJSQuestionVO);
        PLVCommonLog.d(str, "save answer :" + polyvJSQuestionVO.getQuestionId());
        this.questions.put(polyvJSQuestionVO.getQuestionId(), polyvJSQuestionVO);
    }

    private void sendResultToServer(PolyvJSQuestionVO polyvJSQuestionVO) {
        PolyvChatroomManager.getInstance().sendInteractiveSocketMessage("message", new PolyvQuestionSocketVO(polyvJSQuestionVO.getAnswerId(), this.viewerName, polyvJSQuestionVO.getQuestionId(), this.channelId, this.viewerId), 3, "ANSWER_TEST_QUESTION");
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void processSocketMsg(final String msg, String event) {
        event.hashCode();
        switch (event) {
            case "GET_TEST_QUESTION_RESULT":
                delay(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractAnswer.3
                    @Override // java.lang.Runnable
                    public void run() {
                        PolyvQuestionResultVO polyvQuestionResultVO = (PolyvQuestionResultVO) PLVGsonUtil.fromJson(PolyvQuestionResultVO.class, msg);
                        if (polyvQuestionResultVO == null) {
                            return;
                        }
                        PLVInteractAnswer.this.notifyShow();
                        PolyvJSQuestionVO polyvJSQuestionVO = (PolyvJSQuestionVO) PLVInteractAnswer.this.questions.remove(polyvQuestionResultVO.getQuestionId());
                        PLVInteractAnswer.this.sendMsgToJs(PLVInteractJSBridgeEventConst.ANSWER_SHEET_RESULT, (polyvJSQuestionVO == null ? new PolyvQuestionResultJsVO("", msg) : new PolyvQuestionResultJsVO(polyvJSQuestionVO.getAnswerId(), msg)).toString(), new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractAnswer.3.1
                            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                            public void onCallBack(String data) {
                                PLVCommonLog.d(PLVInteractAnswer.TAG, "GET_TEST_QUESTION_RESULT " + data);
                            }
                        });
                    }
                });
                break;
            case "STOP_TEST_QUESTION":
                delay(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractAnswer.2
                    @Override // java.lang.Runnable
                    public void run() throws JSONException {
                        try {
                            String string = new JSONObject(msg).getString("questionId");
                            if (PLVInteractAnswer.this.isQuestionAnswer || !string.equals(PLVInteractAnswer.this.curQuestionId)) {
                                return;
                            }
                            PLVInteractAnswer.this.notifyShow();
                            PLVInteractAnswer pLVInteractAnswer = PLVInteractAnswer.this;
                            pLVInteractAnswer.sendMsgToJs(PLVInteractJSBridgeEventConst.TEST_QUESTION_METHOD, pLVInteractAnswer.socketMsgStopQuestion, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractAnswer.2.1
                                @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                                public void onCallBack(String data) {
                                    PLVCommonLog.d(PLVInteractAnswer.TAG, "TEST_QUESTION_METHOD " + data);
                                }
                            });
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                break;
            case "GET_TEST_QUESTION_CONTENT":
                delay(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractAnswer.1
                    @Override // java.lang.Runnable
                    public void run() throws JSONException {
                        PolyvQuestionSResult polyvQuestionSResult = (PolyvQuestionSResult) PLVGsonUtil.fromJson(PolyvQuestionSResult.class, msg);
                        if (polyvQuestionSResult == null) {
                            return;
                        }
                        PLVInteractAnswer.this.curQuestionId = polyvQuestionSResult.getQuestionId();
                        PLVInteractAnswer.this.isQuestionAnswer = false;
                        PLVInteractAnswer.this.notifyShow();
                        PLVInteractAnswer.this.saveNewQuestionForStopQuestion(msg);
                        PLVInteractAnswer.this.sendMsgToJs(PLVInteractJSBridgeEventConst.ANSWER_SHEET_START, msg, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractAnswer.1.1
                            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                            public void onCallBack(String data) {
                                PLVCommonLog.d(PLVInteractAnswer.TAG, "GET_TEST_QUESTION_CONTENT " + data);
                            }
                        });
                    }
                });
                break;
            default:
                if (event.contains(PLVSocketEvent.TEST_QUESTION)) {
                    sendMsgToJs(PLVInteractJSBridgeEventConst.TEST_QUESTION_METHOD, msg, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractAnswer.4
                        @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                        public void onCallBack(String data) {
                            PLVCommonLog.d(PLVInteractAnswer.TAG, "TEST_QUESTION " + data);
                        }
                    });
                    break;
                }
                break;
        }
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void registerMsgReceiverFromJs(IPLVInteractJSBridge interactJSBridge) {
        interactJSBridge.registerMsgReceiverFromJs(PLVInteractJSBridgeEventConst.ANSWER_SHEET_CHOOSE, new BridgeHandler() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractAnswer.5
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String data, CallBackFunction function) {
                PLVInteractAnswer.this.saveSelectedOption(data);
                PLVJSResponseVO pLVJSResponseVO = new PLVJSResponseVO();
                pLVJSResponseVO.setStatus(1);
                pLVJSResponseVO.setMessage("成功调用方法：knowAnswer");
                function.onCallBack(PLVGsonUtil.toJson(pLVJSResponseVO));
                PLVInteractAnswer.this.isQuestionAnswer = true;
            }
        });
    }
}
