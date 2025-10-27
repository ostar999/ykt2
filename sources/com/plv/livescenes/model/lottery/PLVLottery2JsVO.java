package com.plv.livescenes.model.lottery;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.plv.livescenes.model.lottery.PLVLotteryEndVO;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVLottery2JsVO {
    private String collectInfo;
    private String prize;
    private boolean win;
    private String winnerCode;
    private List<PLVLotteryEndVO.WinnersListBean> winnersList;

    public static class LotteryExclusionStrategy implements ExclusionStrategy {
        private boolean win;

        public LotteryExclusionStrategy(PLVLottery2JsVO pLVLottery2JsVO) {
            this.win = pLVLottery2JsVO.win;
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipClass(Class<?> cls) {
            return false;
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            if (this.win) {
                return false;
            }
            return fieldAttributes.getName().equals("prize") || fieldAttributes.getName().equals("winnerCode") || fieldAttributes.getName().equals("collectInfo");
        }
    }

    public PLVLottery2JsVO(boolean z2, String str, String str2) {
        this.win = z2;
        this.prize = str;
        this.winnerCode = str2;
    }

    public String getCollectInfo() {
        return this.collectInfo;
    }

    public String getPrize() {
        return this.prize;
    }

    public String getWinnerCode() {
        return this.winnerCode;
    }

    public List<PLVLotteryEndVO.WinnersListBean> getWinnersList() {
        return this.winnersList;
    }

    public boolean isWin() {
        return this.win;
    }

    public void setCollectInfo(String str) {
        this.collectInfo = str;
    }

    public void setPrize(String str) {
        this.prize = str;
    }

    public void setWin(boolean z2) {
        this.win = z2;
    }

    public void setWinnerCode(String str) {
        this.winnerCode = str;
    }

    public void setWinnersList(List<PLVLotteryEndVO.WinnersListBean> list) {
        this.winnersList = list;
    }

    public String toJson() {
        return new GsonBuilder().addSerializationExclusionStrategy(new LotteryExclusionStrategy(this)).create().toJson(this);
    }
}
