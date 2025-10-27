package com.easefun.polyv.livecommon.module.modules.reward.view.vo;

import cn.hutool.core.text.CharPool;
import com.plv.livescenes.model.pointreward.PLVRewardSettingVO;

/* loaded from: classes3.dex */
public class PLVRewardItemVO {
    private String enabled;
    private int goodId;
    private String img;
    private String name;
    private String price;
    private Integer sequence;
    private String unit;

    public PLVRewardItemVO(PLVRewardSettingVO.GiftDonateDTO.GiftDetailDTO giftDetailDTO, String priceUnit) {
        this.goodId = giftDetailDTO.getGoodId();
        this.name = giftDetailDTO.getName();
        this.img = giftDetailDTO.getImg();
        this.price = giftDetailDTO.getPrice();
        this.sequence = giftDetailDTO.getSequence();
        this.enabled = giftDetailDTO.getEnabled();
        this.unit = priceUnit;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public int getGoodId() {
        return this.goodId;
    }

    public String getImg() {
        return this.img;
    }

    public String getName() {
        return this.name;
    }

    public String getPrice() {
        return this.price;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public String getUnit() {
        return this.unit;
    }

    public PLVRewardItemVO setEnabled(String enabled) {
        this.enabled = enabled;
        return this;
    }

    public PLVRewardItemVO setGoodId(int goodId) {
        this.goodId = goodId;
        return this;
    }

    public PLVRewardItemVO setImg(String img) {
        this.img = img;
        return this;
    }

    public PLVRewardItemVO setName(String name) {
        this.name = name;
        return this;
    }

    public PLVRewardItemVO setPrice(String price) {
        this.price = price;
        return this;
    }

    public PLVRewardItemVO setSequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public PLVRewardItemVO setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public String toString() {
        return "PLVRewardItemVO{name='" + this.name + CharPool.SINGLE_QUOTE + ", img='" + this.img + CharPool.SINGLE_QUOTE + ", price='" + this.price + CharPool.SINGLE_QUOTE + ", unit='" + this.unit + CharPool.SINGLE_QUOTE + ", sequence=" + this.sequence + ", enabled='" + this.enabled + CharPool.SINGLE_QUOTE + '}';
    }
}
