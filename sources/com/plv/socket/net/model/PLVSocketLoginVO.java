package com.plv.socket.net.model;

import android.text.TextUtils;
import android.util.Patterns;
import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.log.elog.logcode.socket.PLVErrorCodeSocketParam;
import com.plv.foundationsdk.model.net.PLVSocketLoginBase;
import com.plv.foundationsdk.utils.PLVCheckUtils;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.log.PLVELogSender;
import com.plv.socket.socketio.PLVSocketIOClient;
import com.plv.socket.status.PLVSocketStatus;
import com.plv.socket.user.PLVAuthorizationBean;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.socket.user.PLVSocketUserConstant;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PLVSocketLoginVO extends PLVSocketLoginBase {
    private static final String ACTOR1 = "actor";
    private static final String AUTHORIZATION1 = "authorization";
    private static final String AVATAR_URL = "avatarUrl";
    private static final String BG_COLOR = "bgColor";
    private static final String CHANNEL_ID = "channelId";
    private static final String F_COLOR = "fColor";
    private static final String GET_CUP = "getCup";
    private static final String MIC_ID = "micId";
    private static final String NICK_NAME = "nickName";
    private static final String PARAM4 = "param4";
    private static final String PARAM5 = "param5";
    private static final String ROOM_ID = "roomId";
    private static final String SESSION_ID = "sessionId";
    private static final String TYPE = "type";
    private static final String USER_ID = "userId";
    private static final String USER_TYPE = "userType";
    private static final String VALUES = "values";
    private String actor;
    private PLVAuthorizationBean authorization;
    private String avatarUrl;
    private String channelId;
    private String micId;
    private String nickName;
    private String param4;
    private String param5;
    private String userId;
    private String userType;

    private PLVSocketLoginVO() {
    }

    private static PLVSocketLoginVO create(String str, String str2, String str3, String str4, String str5) {
        return create(str, str2, str3, str4, PLVSocketUserConstant.STUDENT_AVATAR_URL, str5);
    }

    public static PLVSocketLoginVO createFromUserClient() {
        return create(PLVSocketIOClient.getInstance().getSocketUserId(), PLVSocketIOClient.getInstance().getChannelId(), PLVSocketIOClient.getInstance().getNickName(), PLVSocketIOClient.getInstance().getUserType(), PLVSocketIOClient.getInstance().getAvatarUrl(), PLVSocketIOClient.getInstance().getActor(), PLVSocketIOClient.getInstance().getAuthorization(), PLVSocketIOClient.getInstance().getMicId(), PLVSocketIOClient.getInstance().getParam4(), PLVSocketIOClient.getInstance().getParam5());
    }

    private void setActor(String str) {
        this.actor = str;
    }

    private void setAvatarUrl(String str) {
        this.avatarUrl = str;
    }

    private void setChannelId(String str) {
        this.channelId = str;
    }

    private void setNickName(String str) {
        this.nickName = str;
    }

    private void setUserId(String str) {
        this.userId = str;
    }

    public boolean checkParamsCallback(PLVSocketMessageObserver pLVSocketMessageObserver) {
        int i2 = 2;
        String strCheckEmptyParams = PLVCheckUtils.checkEmptyParams(this.userId, "userId", this.channelId, "channelId", this.nickName, NICK_NAME, this.userType, "userType", this.avatarUrl, AVATAR_URL);
        if (strCheckEmptyParams == null) {
            if (Patterns.WEB_URL.matcher(this.avatarUrl).matches()) {
                return true;
            }
            if (pLVSocketMessageObserver != null) {
                Exception exc = new Exception("avatarUrl is not webUrl");
                pLVSocketMessageObserver.dispatchOnStatus(PLVSocketStatus.fail(PLVELogSender.send(PLVErrorCodeSocketParam.class, 5, exc), exc));
            }
            return false;
        }
        if (pLVSocketMessageObserver != null) {
            if (strCheckEmptyParams.startsWith("userId")) {
                i2 = 3;
            } else if (!strCheckEmptyParams.startsWith("channelId")) {
                i2 = strCheckEmptyParams.startsWith(NICK_NAME) ? 4 : strCheckEmptyParams.startsWith("userType") ? 6 : strCheckEmptyParams.startsWith(AVATAR_URL) ? 5 : -1;
            }
            Exception exc2 = new Exception(strCheckEmptyParams);
            pLVSocketMessageObserver.dispatchOnStatus(PLVSocketStatus.fail(PLVELogSender.send(PLVErrorCodeSocketParam.class, i2, exc2), exc2));
        }
        return false;
    }

    @Override // com.plv.foundationsdk.model.net.PLVSocketLoginBase
    public String createLoginInfo(String str) throws Exception {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        jSONObject.put("EVENT", "LOGIN");
        jSONArray.put(0, this.nickName);
        jSONArray.put(1, this.avatarUrl);
        jSONArray.put(2, this.userId);
        jSONObject.put(VALUES, jSONArray);
        PLVAuthorizationBean pLVAuthorizationBean = this.authorization;
        if (pLVAuthorizationBean != null && !TextUtils.isEmpty(pLVAuthorizationBean.getActor())) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(ACTOR1, this.authorization.getActor());
            jSONObject2.put(BG_COLOR, this.authorization.getBgColor());
            jSONObject2.put(F_COLOR, this.authorization.getfColor());
            jSONObject.put(AUTHORIZATION1, jSONObject2);
        }
        if (!TextUtils.isEmpty(this.actor)) {
            jSONObject.put(ACTOR1, this.actor);
        }
        jSONObject.put(MIC_ID, this.micId);
        jSONObject.put("roomId", str);
        jSONObject.put("channelId", this.channelId);
        jSONObject.put("type", this.userType);
        jSONObject.put(GET_CUP, 1);
        jSONObject.put(PARAM4, this.param4);
        jSONObject.put(PARAM5, this.param5);
        if (!TextUtils.isEmpty(PLVSocketIOClient.getInstance().getToken())) {
            jSONObject.put("sessionId", PLVSocketIOClient.getInstance().getLessonId() + "");
        }
        return jSONObject.toString();
    }

    public PLVSocketUserBean createSocketUserBean() {
        PLVSocketUserBean pLVSocketUserBean = new PLVSocketUserBean();
        pLVSocketUserBean.setUserId(this.userId);
        pLVSocketUserBean.setRoomId(this.channelId);
        pLVSocketUserBean.setChannelId(this.channelId);
        pLVSocketUserBean.setNick(this.nickName);
        pLVSocketUserBean.setPic(this.avatarUrl);
        pLVSocketUserBean.setUserType(this.userType);
        pLVSocketUserBean.setActor(this.actor);
        pLVSocketUserBean.setAuthorization(this.authorization);
        return pLVSocketUserBean;
    }

    public String getActor() {
        return this.actor;
    }

    public PLVAuthorizationBean getAuthorization() {
        return this.authorization;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getMicId() {
        return this.micId;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getParam4() {
        return this.param4;
    }

    public String getParam5() {
        return this.param5;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setAuthorization(PLVAuthorizationBean pLVAuthorizationBean) {
        this.authorization = pLVAuthorizationBean;
    }

    public void setMicId(String str) {
        this.micId = str;
    }

    public void setParam4(String str) {
        this.param4 = str;
    }

    public void setParam5(String str) {
        this.param5 = str;
    }

    public void setUserType(String str) {
        this.userType = str;
    }

    public String toString() {
        return "PLVSocketLoginVO{userId='" + this.userId + CharPool.SINGLE_QUOTE + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", nickName='" + this.nickName + CharPool.SINGLE_QUOTE + ", avatarUrl='" + this.avatarUrl + CharPool.SINGLE_QUOTE + ", userType='" + this.userType + CharPool.SINGLE_QUOTE + ", actor='" + this.actor + CharPool.SINGLE_QUOTE + ", authorization=" + this.authorization + ", micId='" + this.micId + CharPool.SINGLE_QUOTE + ", param4='" + this.param4 + CharPool.SINGLE_QUOTE + ", param5='" + this.param5 + CharPool.SINGLE_QUOTE + '}';
    }

    private static PLVSocketLoginVO create(String str, String str2, String str3, String str4, String str5, String str6) {
        return create(str, str2, str3, str4, str5, null, str6);
    }

    private static PLVSocketLoginVO create(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        return create(str, str2, str3, str4, str5, str6, null, str7, "", "");
    }

    private static PLVSocketLoginVO create(String str, String str2, String str3, String str4, String str5, String str6, PLVAuthorizationBean pLVAuthorizationBean, String str7) {
        return create(str, str2, str3, str4, str5, str6, pLVAuthorizationBean, str7, "", "");
    }

    private static PLVSocketLoginVO create(String str, String str2, String str3, String str4, String str5, String str6, PLVAuthorizationBean pLVAuthorizationBean, String str7, String str8, String str9) {
        String strFixChatPic = PLVEventHelper.fixChatPic(str5);
        PLVSocketLoginVO pLVSocketLoginVO = new PLVSocketLoginVO();
        pLVSocketLoginVO.setUserId(str);
        pLVSocketLoginVO.setChannelId(str2);
        if (TextUtils.isEmpty(str3)) {
            str3 = PLVSocketUserConstant.ACTOR_STUDENT;
        }
        pLVSocketLoginVO.setNickName(str3);
        if (TextUtils.isEmpty(str4)) {
            str4 = "student";
        }
        pLVSocketLoginVO.setUserType(str4);
        if (TextUtils.isEmpty(strFixChatPic)) {
            strFixChatPic = PLVSocketUserConstant.STUDENT_AVATAR_URL;
        }
        pLVSocketLoginVO.setAvatarUrl(strFixChatPic);
        pLVSocketLoginVO.setActor(str6);
        pLVSocketLoginVO.setAuthorization(pLVAuthorizationBean);
        pLVSocketLoginVO.setMicId(str7);
        pLVSocketLoginVO.setParam4(str8);
        pLVSocketLoginVO.setParam5(str9);
        return pLVSocketLoginVO;
    }
}
