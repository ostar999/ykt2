package com.easefun.polyv.livecommon.module.modules.streamer.model;

import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.plv.socket.user.PLVClassStatusBean;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes3.dex */
public class PLVMemberItemDataBean {
    private boolean isFrontCamera = true;
    private PLVLinkMicItemDataBean linkMicItemDataBean;
    private PLVSocketUserBean socketUserBean;

    private void syncClassStatusToLinkMicBean(PLVSocketUserBean socketUserBean, PLVLinkMicItemDataBean linkMicItemDataBean) {
        PLVClassStatusBean classStatus;
        if (socketUserBean == null || linkMicItemDataBean == null || (classStatus = socketUserBean.getClassStatus()) == null) {
            return;
        }
        linkMicItemDataBean.setHasPaint(classStatus.hasPaint());
        linkMicItemDataBean.setCupNum(classStatus.getCup());
    }

    public void addBaseLinkMicBean(PLVSocketUserBean socketUserBean) {
        if (socketUserBean == null) {
            return;
        }
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = new PLVLinkMicItemDataBean();
        pLVLinkMicItemDataBean.setUserType(socketUserBean.getUserType());
        pLVLinkMicItemDataBean.setActor(socketUserBean.getActor());
        pLVLinkMicItemDataBean.setNick(socketUserBean.getNick());
        pLVLinkMicItemDataBean.setPic(socketUserBean.getPic());
        pLVLinkMicItemDataBean.setLinkMicId(socketUserBean.getUserId());
        setLinkMicItemDataBean(pLVLinkMicItemDataBean);
    }

    public PLVLinkMicItemDataBean getLinkMicItemDataBean() {
        return this.linkMicItemDataBean;
    }

    public PLVSocketUserBean getSocketUserBean() {
        return this.socketUserBean;
    }

    public boolean isFrontCamera() {
        return this.isFrontCamera;
    }

    public void setFrontCamera(boolean frontCamera) {
        this.isFrontCamera = frontCamera;
    }

    public void setLinkMicItemDataBean(PLVLinkMicItemDataBean linkMicItemDataBean) {
        this.linkMicItemDataBean = linkMicItemDataBean;
        syncClassStatusToLinkMicBean(this.socketUserBean, linkMicItemDataBean);
    }

    public void setSocketUserBean(PLVSocketUserBean socketUserBean) {
        this.socketUserBean = socketUserBean;
        syncClassStatusToLinkMicBean(socketUserBean, this.linkMicItemDataBean);
    }

    public void updateBaseLinkMicBean(PLVLinkMicItemDataBean linkMicItemDataBean) {
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.linkMicItemDataBean;
        if (pLVLinkMicItemDataBean == null || linkMicItemDataBean == null) {
            return;
        }
        pLVLinkMicItemDataBean.setUserType(linkMicItemDataBean.getUserType());
        this.linkMicItemDataBean.setActor(linkMicItemDataBean.getActor());
        this.linkMicItemDataBean.setNick(linkMicItemDataBean.getNick());
        this.linkMicItemDataBean.setPic(linkMicItemDataBean.getPic());
        this.linkMicItemDataBean.setLinkMicId(linkMicItemDataBean.getLinkMicId());
    }
}
