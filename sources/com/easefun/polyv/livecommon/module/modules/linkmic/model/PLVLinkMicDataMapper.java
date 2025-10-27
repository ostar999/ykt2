package com.easefun.polyv.livecommon.module.modules.linkmic.model;

import com.plv.linkmic.model.PLVJoinInfoEvent;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.model.PLVLinkMicJoinSuccess;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes3.dex */
public class PLVLinkMicDataMapper {
    public static PLVLinkMicItemDataBean map2LinkMicItemData(PLVLinkMicJoinSuccess joinSuccess) {
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = new PLVLinkMicItemDataBean();
        pLVLinkMicItemDataBean.setLinkMicId(joinSuccess.getUser().getUserId());
        pLVLinkMicItemDataBean.setNick(joinSuccess.getUser().getNick());
        pLVLinkMicItemDataBean.setUserType(joinSuccess.getUser().getUserType());
        pLVLinkMicItemDataBean.setPic(joinSuccess.getUser().getPic());
        pLVLinkMicItemDataBean.setUserId(joinSuccess.getUser().getUserId());
        return pLVLinkMicItemDataBean;
    }

    public static PLVSocketUserBean map2SocketUserBean(PLVJoinInfoEvent joinInfoEvent) {
        PLVSocketUserBean pLVSocketUserBean = new PLVSocketUserBean();
        pLVSocketUserBean.setBanned(joinInfoEvent.isBanned());
        pLVSocketUserBean.setNick(joinInfoEvent.getNick());
        pLVSocketUserBean.setPic(joinInfoEvent.getPic());
        pLVSocketUserBean.setActor(joinInfoEvent.getActor());
        pLVSocketUserBean.setUserType(joinInfoEvent.getUserType());
        pLVSocketUserBean.setChannelId(joinInfoEvent.getChannelId());
        pLVSocketUserBean.setClientIp(joinInfoEvent.getClientIp());
        pLVSocketUserBean.setUserId(joinInfoEvent.getLoginId());
        pLVSocketUserBean.setUid(joinInfoEvent.getUid());
        pLVSocketUserBean.setRoomId(joinInfoEvent.getRoomId());
        return pLVSocketUserBean;
    }

    public static PLVLinkMicJoinStatus.WaitListBean map2WaitListBean(PLVJoinInfoEvent joinInfoEvent) {
        PLVLinkMicJoinStatus.WaitListBean waitListBean = new PLVLinkMicJoinStatus.WaitListBean();
        waitListBean.setLoginId(joinInfoEvent.getLoginId());
        waitListBean.setActor(joinInfoEvent.getActor());
        waitListBean.setClientIp(joinInfoEvent.getClientIp());
        waitListBean.setNick(joinInfoEvent.getNick());
        waitListBean.setPic(joinInfoEvent.getPic());
        waitListBean.setRoomId(joinInfoEvent.getRoomId());
        waitListBean.setSessionId(joinInfoEvent.getSessionId());
        waitListBean.setStatus(joinInfoEvent.getStatus());
        waitListBean.setUid(joinInfoEvent.getUid());
        waitListBean.setUserId(joinInfoEvent.getUserId());
        waitListBean.setUserType(joinInfoEvent.getUserType());
        waitListBean.setCupNum(joinInfoEvent.getCupNum());
        return waitListBean;
    }

    public static PLVLinkMicItemDataBean map2LinkMicItemData(PLVJoinInfoEvent joinInfoEvent) {
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = new PLVLinkMicItemDataBean();
        pLVLinkMicItemDataBean.setLinkMicId(joinInfoEvent.getUserId());
        pLVLinkMicItemDataBean.setNick(joinInfoEvent.getNick());
        pLVLinkMicItemDataBean.setUserType(joinInfoEvent.getUserType());
        pLVLinkMicItemDataBean.setActor(joinInfoEvent.getActor());
        pLVLinkMicItemDataBean.setPic(joinInfoEvent.getPic());
        pLVLinkMicItemDataBean.setStatus(joinInfoEvent.getStatus());
        pLVLinkMicItemDataBean.setUserId(joinInfoEvent.getUserId());
        return pLVLinkMicItemDataBean;
    }

    public static PLVSocketUserBean map2SocketUserBean(PLVLinkMicJoinStatus.WaitListBean waitListBean) {
        PLVSocketUserBean pLVSocketUserBean = new PLVSocketUserBean();
        pLVSocketUserBean.setNick(waitListBean.getNick());
        pLVSocketUserBean.setPic(waitListBean.getPic());
        pLVSocketUserBean.setActor(waitListBean.getActor());
        pLVSocketUserBean.setUserType(waitListBean.getUserType());
        pLVSocketUserBean.setClientIp(waitListBean.getClientIp());
        pLVSocketUserBean.setUserId(waitListBean.getLoginId());
        pLVSocketUserBean.setUid(waitListBean.getUid());
        pLVSocketUserBean.setRoomId(waitListBean.getRoomId());
        return pLVSocketUserBean;
    }

    public static PLVLinkMicItemDataBean map2LinkMicItemData(PLVLinkMicJoinStatus.WaitListBean waitListBean) {
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = new PLVLinkMicItemDataBean();
        pLVLinkMicItemDataBean.setLinkMicId(waitListBean.getUserId());
        pLVLinkMicItemDataBean.setNick(waitListBean.getNick());
        pLVLinkMicItemDataBean.setUserType(waitListBean.getUserType());
        pLVLinkMicItemDataBean.setActor(waitListBean.getActor());
        pLVLinkMicItemDataBean.setPic(waitListBean.getPic());
        pLVLinkMicItemDataBean.setStatus(waitListBean.getStatus());
        pLVLinkMicItemDataBean.setUserId(waitListBean.getUserId());
        return pLVLinkMicItemDataBean;
    }
}
