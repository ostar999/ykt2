package com.plv.livescenes.model.lottery;

import com.plv.livescenes.model.lottery.PLVLotteryEndVO;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVLotteryWinnerVO {
    private String EVENT;
    private String channelId;
    private String collectInfo;
    private String lotteryId;
    private String prize;
    private String sessionId;
    private String winnerCode;
    private List<PLVLotteryEndVO.WinnersListBean> winnersList;

    public String getChannelId() {
        return this.channelId;
    }

    public String getCollectInfo() {
        return this.collectInfo;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public String getLotteryId() {
        return this.lotteryId;
    }

    public String getPrize() {
        return this.prize;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getWinnerCode() {
        return this.winnerCode;
    }

    public List<PLVLotteryEndVO.WinnersListBean> getWinnersList() {
        return this.winnersList;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setCollectInfo(String str) {
        this.collectInfo = str;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setLotteryId(String str) {
        this.lotteryId = str;
    }

    public void setPrize(String str) {
        this.prize = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setWinnerCode(String str) {
        this.winnerCode = str;
    }

    public void setWinnersList(List<PLVLotteryEndVO.WinnersListBean> list) {
        this.winnersList = list;
    }
}
