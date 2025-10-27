package com.plv.socket.event.commodity;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.livescenes.upload.PLVDocumentUploadConstant;
import com.plv.socket.event.PLVEventHelper;
import java.math.BigDecimal;

/* loaded from: classes5.dex */
public class PLVProductContentBean {
    private String channelId;
    private String cover;
    private long createdTime;
    private String features;
    private long lastModified;
    private String link;
    private int linkType;
    private String mobileAppLink;
    private String mobileLink;
    private String name;
    private String pcLink;
    private String price;
    private String productDesc;
    private int productId;
    private String productType;
    private int rank;
    private String realPrice;
    private int showId;
    private int status;
    private String type;
    private String userId;
    private String wxMiniprogramLink;
    private String wxMiniprogramOriginalId;
    private String yield;

    private static boolean isNumberZero(String str) {
        try {
            return new BigDecimal(str).equals(BigDecimal.ZERO);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return false;
        }
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getCover() {
        return PLVEventHelper.fixChatPic(this.cover);
    }

    public long getCreatedTime() {
        return this.createdTime;
    }

    public String getFeatures() {
        return this.features;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public String getLink() {
        return this.link;
    }

    public String getLinkByType() {
        return isNormalLink() ? getLink() : getMobileAppLink();
    }

    public int getLinkType() {
        return this.linkType;
    }

    public String getMobileAppLink() {
        return this.mobileAppLink;
    }

    public String getMobileLink() {
        return this.mobileLink;
    }

    public String getName() {
        return this.name;
    }

    public String getPcLink() {
        return this.pcLink;
    }

    public String getPrice() {
        return this.price;
    }

    public String getProductDesc() {
        return this.productDesc;
    }

    public int getProductId() {
        return this.productId;
    }

    public String getProductType() {
        return this.productType;
    }

    public int getRank() {
        return this.rank;
    }

    public String getRealPrice() {
        return this.realPrice;
    }

    public int getShowId() {
        return this.showId;
    }

    public int getStatus() {
        return this.status;
    }

    public String getType() {
        return this.type;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getWxMiniprogramLink() {
        return this.wxMiniprogramLink;
    }

    public String getWxMiniprogramOriginalId() {
        return this.wxMiniprogramOriginalId;
    }

    public String getYield() {
        return this.yield;
    }

    public boolean isFinanceProduct() {
        return "finance".equals(this.productType);
    }

    public boolean isFreeForPay() {
        String str = this.realPrice;
        if (str == null) {
            return false;
        }
        return isNumberZero(str);
    }

    public boolean isMultiLink() {
        return 11 == this.linkType;
    }

    public boolean isNormalLink() {
        return 10 == this.linkType;
    }

    public boolean isNormalProduct() {
        return PLVDocumentUploadConstant.ConvertStatus.NORMAL.equals(this.productType);
    }

    public boolean isPullOffShelvesStatus() {
        return !isPutOnShelvesStatus();
    }

    public boolean isPutOnShelvesStatus() {
        return 1 == this.status;
    }

    public boolean isRealPriceEqualsPrice() {
        if (this.realPrice != null && this.price != null) {
            try {
                return new BigDecimal(this.realPrice).equals(new BigDecimal(this.price));
            } catch (NumberFormatException e2) {
                PLVCommonLog.exception(e2);
            }
        }
        return false;
    }

    public boolean isSrcPriceZero() {
        String str = this.price;
        if (str == null) {
            return true;
        }
        return isNumberZero(str);
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setCover(String str) {
        this.cover = str;
    }

    public void setCreatedTime(long j2) {
        this.createdTime = j2;
    }

    public PLVProductContentBean setFeatures(String str) {
        this.features = str;
        return this;
    }

    public void setLastModified(long j2) {
        this.lastModified = j2;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public void setLinkType(int i2) {
        this.linkType = i2;
    }

    public void setMobileAppLink(String str) {
        this.mobileAppLink = str;
    }

    public void setMobileLink(String str) {
        this.mobileLink = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPcLink(String str) {
        this.pcLink = str;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public PLVProductContentBean setProductDesc(String str) {
        this.productDesc = str;
        return this;
    }

    public void setProductId(int i2) {
        this.productId = i2;
    }

    public PLVProductContentBean setProductType(String str) {
        this.productType = str;
        return this;
    }

    public void setRank(int i2) {
        this.rank = i2;
    }

    public void setRealPrice(String str) {
        this.realPrice = str;
    }

    public void setShowId(int i2) {
        this.showId = i2;
    }

    public void setStatus(int i2) {
        this.status = i2;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setWxMiniprogramLink(String str) {
        this.wxMiniprogramLink = str;
    }

    public void setWxMiniprogramOriginalId(String str) {
        this.wxMiniprogramOriginalId = str;
    }

    public PLVProductContentBean setYield(String str) {
        this.yield = str;
        return this;
    }

    public String toString() {
        return "PLVProductContentBean{productId=" + this.productId + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", name='" + this.name + CharPool.SINGLE_QUOTE + ", price='" + this.price + CharPool.SINGLE_QUOTE + ", cover='" + this.cover + CharPool.SINGLE_QUOTE + ", features='" + this.features + CharPool.SINGLE_QUOTE + ", productDesc='" + this.productDesc + CharPool.SINGLE_QUOTE + ", link='" + this.link + CharPool.SINGLE_QUOTE + ", status=" + this.status + ", createdTime=" + this.createdTime + ", lastModified=" + this.lastModified + ", rank=" + this.rank + ", realPrice='" + this.realPrice + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", linkType=" + this.linkType + ", pcLink='" + this.pcLink + CharPool.SINGLE_QUOTE + ", mobileLink='" + this.mobileLink + CharPool.SINGLE_QUOTE + ", wxMiniprogramLink='" + this.wxMiniprogramLink + CharPool.SINGLE_QUOTE + ", wxMiniprogramOriginalId='" + this.wxMiniprogramOriginalId + CharPool.SINGLE_QUOTE + ", showId=" + this.showId + ", mobileAppLink='" + this.mobileAppLink + CharPool.SINGLE_QUOTE + '}';
    }
}
