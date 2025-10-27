package com.easefun.polyv.livecommon.module.modules.interact.app;

import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractJSBridgeEventConst;
import com.easefun.polyv.livescenes.chatroom.PolyvChatroomManager;
import com.easefun.polyv.livescenes.feature.interact.IPLVInteractJSBridge;
import com.easefun.polyv.livescenes.feature.interact.PLVInteractAppAbs;
import com.easefun.polyv.livescenes.model.answer.PolyvQuestionnaireSocketVO;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.livescenes.model.PLVInteractiveCallbackVO;
import com.plv.livescenes.model.answer.PLVQuestionnaireSocketVO;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class PLVInteractQuestionnaire extends PLVInteractAppAbs {
    private static final String TAG = "PLVInteractQuestionnaire";

    /* JADX INFO: Access modifiers changed from: private */
    public void sendResultToServer(PolyvQuestionnaireSocketVO socketVO) {
        PLVCommonLog.d(TAG, "发送调查问卷答案");
        socketVO.setNick(this.viewerName);
        socketVO.setRoomId(this.channelId);
        socketVO.setUserId(this.viewerId);
        PolyvChatroomManager.getInstance().sendInteractiveSocketMessage("message", socketVO, 3, PLVInteractiveCallbackVO.EVENT_QUESTIONNAIRE);
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void processSocketMsg(String msg, String event) {
        event.hashCode();
        switch (event) {
            case "QUESTIONNAIRE_ACHIEVEMENT":
                notifyShow();
                sendMsgToJs(PLVInteractJSBridgeEventConst.QUESTIONNAIRE_ACHIEVEMENT, msg, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractQuestionnaire.4
                    @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                    public void onCallBack(String data) {
                        PLVCommonLog.d(PLVInteractQuestionnaire.TAG, "QUESTIONNAIRE_ACHIEVEMENT " + data);
                    }
                });
                break;
            case "START_QUESTIONNAIRE":
                notifyShow();
                sendMsgToJs(PLVInteractJSBridgeEventConst.QUESTIONNAIRE_START, msg, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractQuestionnaire.1
                    @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                    public void onCallBack(String data) {
                        PLVCommonLog.d(PLVInteractQuestionnaire.TAG, "QUESTIONNAIRE_START " + data);
                    }
                });
                break;
            case "SEND_QUESTIONNAIRE_RESULT":
                sendMsgToJs(PLVInteractJSBridgeEventConst.QUESTIONNAIRE_RESULT, msg, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractQuestionnaire.3
                    @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                    public void onCallBack(String data) {
                        PLVCommonLog.d(PLVInteractQuestionnaire.TAG, "QUESTIONNAIRE_RESULT " + data);
                    }
                });
                break;
            case "STOP_QUESTIONNAIRE":
                sendMsgToJs(PLVInteractJSBridgeEventConst.QUESTIONNAIRE_STOP, msg, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractQuestionnaire.2
                    @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                    public void onCallBack(String data) {
                        PLVCommonLog.d(PLVInteractQuestionnaire.TAG, "QUESTIONNAIRE_STOP " + data);
                    }
                });
                break;
        }
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void registerMsgReceiverFromJs(IPLVInteractJSBridge interactJSBridge) {
        interactJSBridge.registerMsgReceiverFromJs(PLVInteractJSBridgeEventConst.QUESTIONNAIRE_CHOOSE, new BridgeHandler() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractQuestionnaire.5
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String data, CallBackFunction function) throws JSONException {
                String strOptString;
                PLVCommonLog.d(PLVInteractQuestionnaire.TAG, "QUESTIONNAIRE_CHOOSE " + data);
                ArrayList arrayList = new ArrayList();
                try {
                    JSONObject jSONObject = new JSONObject(data);
                    JSONArray jSONArray = jSONObject.getJSONArray("answers");
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                        arrayList.add(new PLVQuestionnaireSocketVO.AnswerBean(jSONObject2.optString("questionId"), jSONObject2.optString("answer")));
                    }
                    strOptString = jSONObject.optString("id");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    strOptString = "";
                }
                PLVInteractQuestionnaire.this.sendResultToServer(new PolyvQuestionnaireSocketVO(strOptString, arrayList));
            }
        });
    }
}
