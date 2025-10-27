package com.beizi.fusion.model;

import androidx.annotation.NonNull;
import cn.hutool.core.text.CharPool;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.umeng.analytics.pro.am;
import java.util.Arrays;
import java.util.List;
import org.bouncycastle.i18n.ErrorBundle;

/* loaded from: classes2.dex */
public class AdSpacesBean {
    public static final int DIRECT_DOWNLOAD_APP_DECIDE = 0;
    public static final int DIRECT_DOWNLOAD_SERVER_NO = 1;
    public static final int DIRECT_DOWNLOAD_SERVER_YES = 2;

    @JsonNode(key = "adType")
    private String adType;

    @JsonNode(key = "appId")
    private String appId;

    @JsonNode(key = "bid")
    private BidBean bid;

    @JsonNode(key = "bidComponent")
    private BidComponent bidComponent;

    @JsonNode(key = "buyer")
    private List<BuyerBean> buyer;

    @JsonNode(key = "bzComponentSsid")
    private String bzComponentSsid;

    @JsonNode(key = "component")
    private ComponentBean component;

    @JsonNode(key = "componentSsid")
    private String componentSsid;

    @JsonNode(key = "eventStrategy")
    private EventStrategyBean eventStrategy;

    @JsonNode(key = "filter")
    private FilterBean filter;

    @JsonNode(key = "filterSsid")
    private String filterSsid;

    @JsonNode(key = "modelType")
    private int modelType;

    @JsonNode(key = "spaceId")
    private String spaceId;

    public static class AdSizeBean {

        @JsonNode(key = "height")
        private String height;

        @JsonNode(key = "width")
        private String width;

        public static AdSizeBean objectFromData(String str) {
            try {
                return (AdSizeBean) JsonResolver.fromJson(str, AdSizeBean.class);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public String getHeight() {
            return this.height;
        }

        public String getWidth() {
            return this.width;
        }

        public void setHeight(String str) {
            this.height = str;
        }

        public void setWidth(String str) {
            this.width = str;
        }

        public String toString() {
            return "AdSizeBean{, width=" + this.width + ", height=" + this.height + '}';
        }
    }

    public static class BidBean {

        @JsonNode(key = "bidTime")
        private int bidTime;

        @JsonNode(key = "reserveFRWTime")
        private int reserveFRWTime;

        @JsonNode(key = "reserveTime")
        private int reserveTime;

        public static BidBean objectFromData(String str) {
            try {
                return (BidBean) JsonResolver.fromJson(str, BidBean.class);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public int getBidTime() {
            return this.bidTime;
        }

        public int getReserveFRWTime() {
            return this.reserveFRWTime;
        }

        public int getReserveTime() {
            return this.reserveTime;
        }

        public void setBidTime(int i2) {
            this.bidTime = i2;
        }

        public void setReserveFRWTime(int i2) {
            this.reserveFRWTime = i2;
        }

        public void setReserveTime(int i2) {
            this.reserveTime = i2;
        }

        public String toString() {
            return "BidBean{reserveFRWTime=" + this.reserveFRWTime + ", reserveTime=" + this.reserveTime + ", bidTime=" + this.bidTime + '}';
        }
    }

    public static class BidComponent {

        @JsonNode(key = "bidList")
        private List<ForwardBean> bidList;

        @JsonNode(key = "rules")
        private List<RulesBean> rules;

        public List<ForwardBean> getBidList() {
            return this.bidList;
        }

        public List<RulesBean> getRules() {
            return this.rules;
        }

        public void setBidList(List<ForwardBean> list) {
            this.bidList = list;
        }

        public void setRules(List<RulesBean> list) {
            this.rules = list;
        }

        public String toString() {
            return "BidComponent{rules=" + this.rules + ", bidList=" + this.bidList + '}';
        }
    }

    public static class BuyerBean {

        @JsonNode(key = "adSize")
        private AdSizeBean adSize;

        @JsonNode(key = "adType")
        private String adType;

        @JsonNode(key = "adWorker")
        private String adWorker;

        @JsonNode(key = "appId")
        private String appId;

        @JsonNode(key = "avgPrice")
        private double avgPrice;

        @JsonNode(key = "bidPrice")
        private double bidPrice;

        @JsonNode(key = "bidType")
        private String bidType;

        @JsonNode(key = "buyerSpaceUuId")
        private String buyerSpaceUuId;

        @JsonNode(key = "cache")
        private int cache;

        @JsonNode(key = "callBackStrategy")
        private List<CallBackStrategyBean> callBackStrategy;

        @JsonNode(key = "callBackStrategyUuid")
        private String callBackStrategyUuid;

        @JsonNode(key = "directDownload")
        private int directDownload;

        @JsonNode(key = "dislike")
        private DislikeConfigBean dislikeConfig;

        @JsonNode(key = "filter")
        private FilterBean filter;

        @JsonNode(key = "filterSsid")
        private String filterSsid;

        @JsonNode(key = "fullScreenClick")
        private FullScreenClickBean fullScreenClick;

        @JsonNode(key = "id")
        private String id;

        @JsonNode(key = "interactionRules")
        private List<RenderRulesBean> interactionRules;

        @JsonNode(key = "priceDict")
        private List<PriceDictBean> priceDict;

        @JsonNode(key = "regionalClickView")
        private RegionalClickViewBean regionalClickView;

        @JsonNode(key = "renderAds")
        private RenderAds renderAds;

        @JsonNode(key = "renderView")
        private List<RenderViewBean> renderView;

        @JsonNode(key = "renderViewSsid")
        private String renderViewSsid;

        @JsonNode(key = "reqTimeOutType")
        private int reqTimeOutType;

        @JsonNode(key = "rollView")
        private RollViewBean rollView;

        @JsonNode(key = "scrollClick")
        private ScrollClickBean scrollClick;

        @JsonNode(key = "shakeView")
        private ShakeViewBean shakeView;

        @JsonNode(key = "sizeRatio")
        private int sizeRatio;

        @JsonNode(key = "spaceId")
        private String spaceId;

        @JsonNode(key = "zone")
        private String zone;

        public static class AliaseShakeViewBean {

            @JsonNode(key = "passivationTime")
            private int passivationTime;

            @JsonNode(key = "rotatAmplitude")
            private double rotatAmplitude;

            @JsonNode(key = "rotatCount")
            private int rotatCount;

            @JsonNode(key = "shakeCount")
            private int shakeCount;

            @JsonNode(key = "shakeEndAmplitude")
            private double shakeEndAmplitude;

            @JsonNode(key = "shakeStartAmplitude")
            private double shakeStartAmplitude;

            public int getPassivationTime() {
                return this.passivationTime;
            }

            public double getRotatAmplitude() {
                return this.rotatAmplitude;
            }

            public int getRotatCount() {
                return this.rotatCount;
            }

            public int getShakeCount() {
                return this.shakeCount;
            }

            public double getShakeEndAmplitude() {
                return this.shakeEndAmplitude;
            }

            public double getShakeStartAmplitude() {
                return this.shakeStartAmplitude;
            }

            public void setPassivationTime(int i2) {
                this.passivationTime = i2;
            }

            public void setRotatAmplitude(double d3) {
                this.rotatAmplitude = d3;
            }

            public void setRotatCount(int i2) {
                this.rotatCount = i2;
            }

            public void setShakeCount(int i2) {
                this.shakeCount = i2;
            }

            public void setShakeEndAmplitude(double d3) {
                this.shakeEndAmplitude = d3;
            }

            public void setShakeStartAmplitude(double d3) {
                this.shakeStartAmplitude = d3;
            }
        }

        public static class CoolRollViewBean {

            @JsonNode(key = "coolTime")
            private long coolTime;

            @JsonNode(key = "rollMinusAmplitude")
            private double rollMinusAmplitude;

            @JsonNode(key = "rollPlusAmplitude")
            private double rollPlusAmplitude;

            @JsonNode(key = "rollTime")
            private long rollTime;

            @JsonNode(key = "userProtectTime")
            private long userProtectTime;

            public long getCoolTime() {
                return this.coolTime;
            }

            public double getRollMinusAmplitude() {
                return this.rollMinusAmplitude;
            }

            public double getRollPlusAmplitude() {
                return this.rollPlusAmplitude;
            }

            public long getRollTime() {
                return this.rollTime;
            }

            public long getUserProtectTime() {
                return this.userProtectTime;
            }

            public void setCoolTime(long j2) {
                this.coolTime = j2;
            }

            public void setRollMinusAmplitude(double d3) {
                this.rollMinusAmplitude = d3;
            }

            public void setRollPlusAmplitude(double d3) {
                this.rollPlusAmplitude = d3;
            }

            public void setRollTime(long j2) {
                this.rollTime = j2;
            }

            public void setUserProtectTime(long j2) {
                this.userProtectTime = j2;
            }
        }

        public static class CoolShakeViewBean {

            @JsonNode(key = "coolTime")
            private long coolTime;

            @JsonNode(key = "feedback")
            private int feedback;

            @JsonNode(key = "rotatAmplitude")
            private double rotatAmplitude;

            @JsonNode(key = "rotatCount")
            private int rotatCount;

            @JsonNode(key = "shakeCount")
            private int shakeCount;

            @JsonNode(key = "shakeEndAmplitude")
            private double shakeEndAmplitude;

            @JsonNode(key = "shakeStartAmplitude")
            private double shakeStartAmplitude;

            @JsonNode(key = "userProtectTime")
            private long userProtectTime;

            public long getCoolTime() {
                return this.coolTime;
            }

            public int getFeedback() {
                return this.feedback;
            }

            public double getRotatAmplitude() {
                return this.rotatAmplitude;
            }

            public int getRotatCount() {
                return this.rotatCount;
            }

            public int getShakeCount() {
                return this.shakeCount;
            }

            public double getShakeEndAmplitude() {
                return this.shakeEndAmplitude;
            }

            public double getShakeStartAmplitude() {
                return this.shakeStartAmplitude;
            }

            public long getUserProtectTime() {
                return this.userProtectTime;
            }

            public void setCoolTime(long j2) {
                this.coolTime = j2;
            }

            public void setFeedback(int i2) {
                this.feedback = i2;
            }

            public void setRotatAmplitude(double d3) {
                this.rotatAmplitude = d3;
            }

            public void setRotatCount(int i2) {
                this.rotatCount = i2;
            }

            public void setShakeCount(int i2) {
                this.shakeCount = i2;
            }

            public void setShakeEndAmplitude(double d3) {
                this.shakeEndAmplitude = d3;
            }

            public void setShakeStartAmplitude(double d3) {
                this.shakeStartAmplitude = d3;
            }

            public void setUserProtectTime(long j2) {
                this.userProtectTime = j2;
            }
        }

        public static class DislikeConfigBean {

            @JsonNode(key = "coolTime")
            private long coolTime;

            @JsonNode(key = "dislikeUuid")
            private String dislikeUuid;

            @JsonNode(key = "isHide")
            private int isHide;

            @JsonNode(key = "orderData")
            private List<OrderDataDislikeConfigBean> orderData;

            @JsonNode(key = "randomNum")
            private int randomNum;

            public long getCoolTime() {
                return this.coolTime;
            }

            public String getDislikeUuid() {
                return this.dislikeUuid;
            }

            public int getIsHide() {
                return this.isHide;
            }

            public List<OrderDataDislikeConfigBean> getOrderData() {
                return this.orderData;
            }

            public int getRandomNum() {
                return this.randomNum;
            }

            public void setCoolTime(long j2) {
                this.coolTime = j2;
            }

            public void setDislikeUuid(String str) {
                this.dislikeUuid = str;
            }

            public void setIsHide(int i2) {
                this.isHide = i2;
            }

            public void setOrderData(List<OrderDataDislikeConfigBean> list) {
                this.orderData = list;
            }

            public void setRandomNum(int i2) {
                this.randomNum = i2;
            }
        }

        public static class FullScreenClickBean {

            @JsonNode(key = "fullScreenClickUuid")
            private String fullScreenClickUuid;

            @JsonNode(key = "orderData")
            private List<OrderDataFullScreenClickBean> orderData;

            @JsonNode(key = "randomClickNum")
            private int randomClickNum;

            public String getFullScreenClickUuid() {
                return this.fullScreenClickUuid;
            }

            public List<OrderDataFullScreenClickBean> getOrderData() {
                return this.orderData;
            }

            public int getRandomClickNum() {
                return this.randomClickNum;
            }

            public void setFullScreenClickUuid(String str) {
                this.fullScreenClickUuid = str;
            }

            public void setOrderData(List<OrderDataFullScreenClickBean> list) {
                this.orderData = list;
            }

            public void setRandomClickNum(int i2) {
                this.randomClickNum = i2;
            }
        }

        public static class OrderDataDislikeConfigBean {

            @JsonNode(key = "dislike")
            private DislikeConfigBean dislike;

            @JsonNode(key = "orderList")
            private List<String> orderList;

            public DislikeConfigBean getDislike() {
                return this.dislike;
            }

            public List<String> getOrderList() {
                return this.orderList;
            }

            public void setDislike(DislikeConfigBean dislikeConfigBean) {
                this.dislike = dislikeConfigBean;
            }

            public void setOrderList(List<String> list) {
                this.orderList = list;
            }
        }

        public static class OrderDataFullScreenClickBean {

            @JsonNode(key = "fullScreenClick")
            private FullScreenClickBean fullScreenClick;

            @JsonNode(key = "orderList")
            private List<String> orderList;

            public FullScreenClickBean getFullScreenClick() {
                return this.fullScreenClick;
            }

            public List<String> getOrderList() {
                return this.orderList;
            }

            public void setFullScreenClick(FullScreenClickBean fullScreenClickBean) {
                this.fullScreenClick = fullScreenClickBean;
            }

            public void setOrderList(List<String> list) {
                this.orderList = list;
            }
        }

        public static class OrderDataRegionalClickViewBean {

            @JsonNode(key = "orderList")
            private List<String> orderList;

            @JsonNode(key = "regionalClickView")
            private RegionalClickViewBean regionalClickView;

            public List<String> getOrderList() {
                return this.orderList;
            }

            public RegionalClickViewBean getRegionalClickView() {
                return this.regionalClickView;
            }

            public void setOrderList(List<String> list) {
                this.orderList = list;
            }

            public void setRegionalClickView(RegionalClickViewBean regionalClickViewBean) {
                this.regionalClickView = regionalClickViewBean;
            }
        }

        public static class OrderDataRollViewBean {

            @JsonNode(key = "orderList")
            private List<String> orderList;

            @JsonNode(key = "rollView")
            private RollViewBean rollView;

            public List<String> getOrderList() {
                return this.orderList;
            }

            public RollViewBean getRollView() {
                return this.rollView;
            }

            public void setOrderList(List<String> list) {
                this.orderList = list;
            }

            public void setRollView(RollViewBean rollViewBean) {
                this.rollView = rollViewBean;
            }
        }

        public static class OrderDataScrollViewOrderBean {

            @JsonNode(key = "orderList")
            private List<String> orderList;

            @JsonNode(key = "scrollClick")
            private ScrollClickBean scrollClick;

            public List<String> getOrderList() {
                return this.orderList;
            }

            public ScrollClickBean getScrollClick() {
                return this.scrollClick;
            }

            public void setOrderList(List<String> list) {
                this.orderList = list;
            }

            public void setScrollClick(ScrollClickBean scrollClickBean) {
                this.scrollClick = scrollClickBean;
            }
        }

        public static class OrderDataShakeViewBean {

            @JsonNode(key = "orderList")
            private List<String> orderList;

            @JsonNode(key = "shakeView")
            private ShakeViewBean shakeView;

            public List<String> getOrderList() {
                return this.orderList;
            }

            public ShakeViewBean getShakeView() {
                return this.shakeView;
            }

            public void setOrderList(List<String> list) {
                this.orderList = list;
            }

            public void setShakeView(ShakeViewBean shakeViewBean) {
                this.shakeView = shakeViewBean;
            }
        }

        public static class PercentPositionBean {

            @JsonNode(key = "centerX")
            private String centerX;

            @JsonNode(key = "centerY")
            private String centerY;

            @JsonNode(key = "height")
            private String height;

            @JsonNode(key = "width")
            private String width;

            public String getCenterX() {
                return this.centerX;
            }

            public String getCenterY() {
                return this.centerY;
            }

            public String getHeight() {
                return this.height;
            }

            public String getWidth() {
                return this.width;
            }

            public void setCenterX(String str) {
                this.centerX = str;
            }

            public void setCenterY(String str) {
                this.centerY = str;
            }

            public void setHeight(String str) {
                this.height = str;
            }

            public void setWidth(String str) {
                this.width = str;
            }

            @NonNull
            public String toString() {
                return "PercentPositionBean{centerX='" + this.centerX + CharPool.SINGLE_QUOTE + ", centerY='" + this.centerY + CharPool.SINGLE_QUOTE + ", width='" + this.width + CharPool.SINGLE_QUOTE + ", height='" + this.height + CharPool.SINGLE_QUOTE + '}';
            }
        }

        public static class PriceDictBean {

            @JsonNode(key = "name")
            private String name;

            @JsonNode(key = "price")
            private int price;

            public String getName() {
                return this.name;
            }

            public int getPrice() {
                return this.price;
            }

            public void setName(String str) {
                this.name = str;
            }

            public void setPrice(int i2) {
                this.price = i2;
            }

            public String toString() {
                return "PriceDictBean{name='" + this.name + CharPool.SINGLE_QUOTE + ", price=" + this.price + '}';
            }
        }

        public static class RegionalClickViewBean {

            @JsonNode(key = "backgroundAlpha")
            private double backgroundAlpha;

            @JsonNode(key = TtmlNode.ATTR_TTS_BACKGROUND_COLOR)
            private String backgroundColor;

            @JsonNode(key = "orderData")
            private List<OrderDataRegionalClickViewBean> orderData;

            @JsonNode(key = "position")
            private PercentPositionBean position;

            @JsonNode(key = "regionalClickUuid")
            private String regionalClickUuid;

            @JsonNode(key = "title")
            private String title;

            @JsonNode(key = "titleColor")
            private String titleColor;

            public double getBackgroundAlpha() {
                return this.backgroundAlpha;
            }

            public String getBackgroundColor() {
                return this.backgroundColor;
            }

            public List<OrderDataRegionalClickViewBean> getOrderData() {
                return this.orderData;
            }

            public PercentPositionBean getPosition() {
                return this.position;
            }

            public String getRegionalClickUuid() {
                return this.regionalClickUuid;
            }

            public String getTitle() {
                return this.title;
            }

            public String getTitleColor() {
                return this.titleColor;
            }

            public void setBackgroundAlpha(double d3) {
                this.backgroundAlpha = d3;
            }

            public void setBackgroundColor(String str) {
                this.backgroundColor = str;
            }

            public void setOrderData(List<OrderDataRegionalClickViewBean> list) {
                this.orderData = list;
            }

            public void setPosition(PercentPositionBean percentPositionBean) {
                this.position = percentPositionBean;
            }

            public void setRegionalClickUuid(String str) {
                this.regionalClickUuid = str;
            }

            public void setTitle(String str) {
                this.title = str;
            }

            public void setTitleColor(String str) {
                this.titleColor = str;
            }
        }

        public static class RenderAds {

            @JsonNode(key = "actionCoordinate")
            private String actionCoordinate;

            @JsonNode(key = "adCoordinate")
            private String adCoordinate;

            @JsonNode(key = "autoClose")
            private String autoClose;

            @JsonNode(key = "bgCoordinate")
            private String bgCoordinate;

            @JsonNode(key = "cec")
            private String cec;

            @JsonNode(key = "clickView")
            private List<String> clickView;

            @JsonNode(key = "closeCoordinate")
            private String closeCoordinate;

            @JsonNode(key = "descCoordinate")
            private String descCoordinate;

            @JsonNode(key = "iconCoordinate")
            private String iconCoordinate;

            @JsonNode(key = "imageCoordinate")
            private String imageCoordinate;

            @JsonNode(key = "maxTime")
            private String maxTime;

            @JsonNode(key = "minTime")
            private String minTime;

            @JsonNode(key = "renderAdsUuid")
            private String renderAdsUuid;

            @JsonNode(key = "rmc")
            private String rmc;

            @JsonNode(key = "scrollCoordinate")
            private String scrollCoordinate;

            @JsonNode(key = "slac")
            private String slac;

            @JsonNode(key = "slc")
            private String slc;

            @JsonNode(key = "titleCoordinate")
            private String titleCoordinate;

            private int strToInt(String str) {
                try {
                    return Integer.parseInt(str);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return 0;
                }
            }

            public String getActionCoordinate() {
                return this.actionCoordinate;
            }

            public String getAdCoordinate() {
                return this.adCoordinate;
            }

            public int getAutoClose() {
                return strToInt(this.autoClose);
            }

            public String getBgCoordinate() {
                return this.bgCoordinate;
            }

            public int getCec() {
                return strToInt(this.cec);
            }

            public List<String> getClickView() {
                return this.clickView;
            }

            public String getCloseCoordinate() {
                return this.closeCoordinate;
            }

            public String getDescCoordinate() {
                return this.descCoordinate;
            }

            public String getIconCoordinate() {
                return this.iconCoordinate;
            }

            public String getImageCoordinate() {
                return this.imageCoordinate;
            }

            public int getMaxTime() {
                return strToInt(this.maxTime);
            }

            public int getMinTime() {
                return strToInt(this.minTime);
            }

            public String getRenderAdsUuid() {
                return this.renderAdsUuid;
            }

            public int getRmc() {
                return strToInt(this.rmc);
            }

            public String getScrollCoordinate() {
                return this.scrollCoordinate;
            }

            public int getSlac() {
                return strToInt(this.slac);
            }

            public int getSlc() {
                return strToInt(this.slc);
            }

            public String getTitleCoordinate() {
                return this.titleCoordinate;
            }

            public void setActionCoordinate(String str) {
                this.actionCoordinate = str;
            }

            public void setAdCoordinate(String str) {
                this.adCoordinate = str;
            }

            public void setAutoClose(String str) {
                this.autoClose = str;
            }

            public void setBgCoordinate(String str) {
                this.bgCoordinate = str;
            }

            public void setCec(String str) {
                this.cec = str;
            }

            public void setClickView(List<String> list) {
                this.clickView = list;
            }

            public void setCloseCoordinate(String str) {
                this.closeCoordinate = str;
            }

            public void setDescCoordinate(String str) {
                this.descCoordinate = str;
            }

            public void setIconCoordinate(String str) {
                this.iconCoordinate = str;
            }

            public void setImageCoordinate(String str) {
                this.imageCoordinate = str;
            }

            public void setMaxTime(String str) {
                this.maxTime = str;
            }

            public void setMinTime(String str) {
                this.minTime = str;
            }

            public void setRenderAdsUuid(String str) {
                this.renderAdsUuid = str;
            }

            public void setRmc(String str) {
                this.rmc = str;
            }

            public void setScrollCoordinate(String str) {
                this.scrollCoordinate = str;
            }

            public void setSlac(String str) {
                this.slac = str;
            }

            public void setSlc(String str) {
                this.slc = str;
            }

            public void setTitleCoordinate(String str) {
                this.titleCoordinate = str;
            }
        }

        public static class RenderRulesBean {

            @JsonNode(key = "results")
            private Integer[] results;

            @JsonNode(key = "type")
            private String type;

            public Integer[] getResults() {
                return this.results;
            }

            public String getType() {
                return this.type;
            }

            public void setResults(Integer[] numArr) {
                this.results = numArr;
            }

            public void setType(String str) {
                this.type = str;
            }
        }

        public static class RollViewBean {

            @JsonNode(key = "bgColor")
            private String bgColor;

            @JsonNode(key = "coolRollView")
            private CoolRollViewBean coolRollView;

            @JsonNode(key = "isClick")
            private int isClick;

            @JsonNode(key = "orderData")
            private List<OrderDataRollViewBean> orderData;

            @JsonNode(key = "position")
            private PercentPositionBean position;

            @JsonNode(key = "rollMinusAmplitude")
            private double rollMinusAmplitude;

            @JsonNode(key = "rollPlusAmplitude")
            private double rollPlusAmplitude;

            @JsonNode(key = "rollTime")
            private long rollTime;

            @JsonNode(key = "rollViewUuid")
            private String rollViewUuid;

            @JsonNode(key = "subTitle")
            private String subTitle;

            @JsonNode(key = "title")
            private String title;

            public String getBgColor() {
                return this.bgColor;
            }

            public CoolRollViewBean getCoolRollView() {
                return this.coolRollView;
            }

            public int getIsClick() {
                return this.isClick;
            }

            public List<OrderDataRollViewBean> getOrderData() {
                return this.orderData;
            }

            public PercentPositionBean getPosition() {
                return this.position;
            }

            public double getRollMinusAmplitude() {
                return this.rollMinusAmplitude;
            }

            public double getRollPlusAmplitude() {
                return this.rollPlusAmplitude;
            }

            public long getRollTime() {
                return this.rollTime;
            }

            public String getRollViewUuid() {
                return this.rollViewUuid;
            }

            public String getSubTitle() {
                return this.subTitle;
            }

            public String getTitle() {
                return this.title;
            }

            public void setBgColor(String str) {
                this.bgColor = str;
            }

            public void setCoolRollView(CoolRollViewBean coolRollViewBean) {
                this.coolRollView = coolRollViewBean;
            }

            public void setIsClick(int i2) {
                this.isClick = i2;
            }

            public void setOrderData(List<OrderDataRollViewBean> list) {
                this.orderData = list;
            }

            public void setPosition(PercentPositionBean percentPositionBean) {
                this.position = percentPositionBean;
            }

            public void setRollMinusAmplitude(double d3) {
                this.rollMinusAmplitude = d3;
            }

            public void setRollPlusAmplitude(double d3) {
                this.rollPlusAmplitude = d3;
            }

            public void setRollTime(long j2) {
                this.rollTime = j2;
            }

            public void setRollViewUuid(String str) {
                this.rollViewUuid = str;
            }

            public void setSubTitle(String str) {
                this.subTitle = str;
            }

            public void setTitle(String str) {
                this.title = str;
            }
        }

        public static class ScrollClickBean {

            @JsonNode(key = ErrorBundle.DETAIL_ENTRY)
            private String details;

            @JsonNode(key = "detailsFont")
            private int detailsFont;

            @JsonNode(key = "orderData")
            private List<OrderDataScrollViewOrderBean> orderData;

            @JsonNode(key = "position")
            private ScrollClickPositionBean position;

            @JsonNode(key = "randomClickNum")
            private int randomClickNum;

            @JsonNode(key = "randomClickTime")
            private int randomClickTime;

            @JsonNode(key = "scrollClickUuid")
            private String scrollClickUuid;

            @JsonNode(key = "scrollDirection")
            private String scrollDirection;

            @JsonNode(key = "scrollDistance")
            private int scrollDistance;

            @JsonNode(key = "title")
            private String title;

            @JsonNode(key = "titleFont")
            private int titleFont;

            public String getDetails() {
                return this.details;
            }

            public int getDetailsFont() {
                return this.detailsFont;
            }

            public List<OrderDataScrollViewOrderBean> getOrderData() {
                return this.orderData;
            }

            public ScrollClickPositionBean getPosition() {
                return this.position;
            }

            public int getRandomClickNum() {
                return this.randomClickNum;
            }

            public int getRandomClickTime() {
                return this.randomClickTime;
            }

            public String getScrollClickUuid() {
                return this.scrollClickUuid;
            }

            public String getScrollDirection() {
                return this.scrollDirection;
            }

            public int getScrollDistance() {
                return this.scrollDistance;
            }

            public String getTitle() {
                return this.title;
            }

            public int getTitleFont() {
                return this.titleFont;
            }

            public void setDetails(String str) {
                this.details = str;
            }

            public void setDetailsFont(int i2) {
                this.detailsFont = i2;
            }

            public void setOrderData(List<OrderDataScrollViewOrderBean> list) {
                this.orderData = list;
            }

            public void setPosition(ScrollClickPositionBean scrollClickPositionBean) {
                this.position = scrollClickPositionBean;
            }

            public void setRandomClickNum(int i2) {
                this.randomClickNum = i2;
            }

            public void setRandomClickTime(int i2) {
                this.randomClickTime = i2;
            }

            public void setScrollClickUuid(String str) {
                this.scrollClickUuid = str;
            }

            public void setScrollDirection(String str) {
                this.scrollDirection = str;
            }

            public void setScrollDistance(int i2) {
                this.scrollDistance = i2;
            }

            public void setTitle(String str) {
                this.title = str;
            }

            public void setTitleFont(int i2) {
                this.titleFont = i2;
            }
        }

        public static class ScrollClickPositionBean {

            @JsonNode(key = "centerX")
            private String centerX;

            @JsonNode(key = "height")
            private String height;

            /* renamed from: top, reason: collision with root package name */
            @JsonNode(key = PLVDanmakuInfo.FONTMODE_TOP)
            private String f5276top;

            @JsonNode(key = "width")
            private String width;

            public String getCenterX() {
                return this.centerX;
            }

            public String getHeight() {
                return this.height;
            }

            public String getTop() {
                return this.f5276top;
            }

            public String getWidth() {
                return this.width;
            }

            public void setCenterX(String str) {
                this.centerX = str;
            }

            public void setHeight(String str) {
                this.height = str;
            }

            public void setTop(String str) {
                this.f5276top = str;
            }

            public void setWidth(String str) {
                this.width = str;
            }
        }

        public static class ShakeViewBean {

            @JsonNode(key = "aliaseShakeView")
            private AliaseShakeViewBean aliaseShakeView;

            @JsonNode(key = "animationInterval")
            private int animationInterval;

            @JsonNode(key = "coolShakeView")
            private CoolShakeViewBean coolShakeView;

            @JsonNode(key = "feedback")
            private int feedback;

            @JsonNode(key = "imageURL")
            private List<String> imageURL;

            @JsonNode(key = "isHideAnim")
            private int isHideAnim;

            @JsonNode(key = "orderData")
            private List<OrderDataShakeViewBean> orderData;

            @JsonNode(key = "position")
            private PercentPositionBean position;

            @JsonNode(key = "randomClickNum")
            private int randomClickNum;

            @JsonNode(key = "randomClickTime")
            private int randomClickTime;

            @JsonNode(key = "renderRate")
            private int renderRate;

            @JsonNode(key = "rotatAmplitude")
            private double rotatAmplitude;

            @JsonNode(key = "rotatCount")
            private int rotatCount;

            @JsonNode(key = "shakeCount")
            private int shakeCount;

            @JsonNode(key = "shakeEndAmplitude")
            private double shakeEndAmplitude;

            @JsonNode(key = "shakeStartAmplitude")
            private double shakeStartAmplitude;

            @JsonNode(key = "shakeViewUuid")
            private String shakeViewUuid;

            @JsonNode(key = "title")
            private String title;

            public AliaseShakeViewBean getAliaseShakeView() {
                return this.aliaseShakeView;
            }

            public int getAnimationInterval() {
                return this.animationInterval;
            }

            public CoolShakeViewBean getCoolShakeView() {
                return this.coolShakeView;
            }

            public int getFeedback() {
                return this.feedback;
            }

            public List<String> getImageURL() {
                return this.imageURL;
            }

            public int getIsHideAnim() {
                return this.isHideAnim;
            }

            public List<OrderDataShakeViewBean> getOrderData() {
                return this.orderData;
            }

            public PercentPositionBean getPosition() {
                return this.position;
            }

            public int getRandomClickNum() {
                return this.randomClickNum;
            }

            public int getRandomClickTime() {
                return this.randomClickTime;
            }

            public int getRenderRate() {
                return this.renderRate;
            }

            public double getRotatAmplitude() {
                return this.rotatAmplitude;
            }

            public int getRotatCount() {
                return this.rotatCount;
            }

            public int getShakeCount() {
                return this.shakeCount;
            }

            public double getShakeEndAmplitude() {
                return this.shakeEndAmplitude;
            }

            public double getShakeStartAmplitude() {
                return this.shakeStartAmplitude;
            }

            public String getShakeViewUuid() {
                return this.shakeViewUuid;
            }

            public String getTitle() {
                return this.title;
            }

            public void setAliaseShakeView(AliaseShakeViewBean aliaseShakeViewBean) {
                this.aliaseShakeView = aliaseShakeViewBean;
            }

            public void setAnimationInterval(int i2) {
                this.animationInterval = i2;
            }

            public void setCoolShakeView(CoolShakeViewBean coolShakeViewBean) {
                this.coolShakeView = coolShakeViewBean;
            }

            public void setFeedback(int i2) {
                this.feedback = i2;
            }

            public void setImageURL(List<String> list) {
                this.imageURL = list;
            }

            public void setIsHideAnim(int i2) {
                this.isHideAnim = i2;
            }

            public void setOrderData(List<OrderDataShakeViewBean> list) {
                this.orderData = list;
            }

            public void setPosition(PercentPositionBean percentPositionBean) {
                this.position = percentPositionBean;
            }

            public void setRandomClickNum(int i2) {
                this.randomClickNum = i2;
            }

            public void setRandomClickTime(int i2) {
                this.randomClickTime = i2;
            }

            public void setRenderRate(int i2) {
                this.renderRate = i2;
            }

            public void setRotatAmplitude(double d3) {
                this.rotatAmplitude = d3;
            }

            public void setRotatCount(int i2) {
                this.rotatCount = i2;
            }

            public void setShakeCount(int i2) {
                this.shakeCount = i2;
            }

            public void setShakeEndAmplitude(double d3) {
                this.shakeEndAmplitude = d3;
            }

            public void setShakeStartAmplitude(double d3) {
                this.shakeStartAmplitude = d3;
            }

            public void setShakeViewUuid(String str) {
                this.shakeViewUuid = str;
            }

            public void setTitle(String str) {
                this.title = str;
            }
        }

        public static BuyerBean objectFromData(String str) {
            try {
                return (BuyerBean) JsonResolver.fromJson(str, BuyerBean.class);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public AdSizeBean getAdSize() {
            return this.adSize;
        }

        public String getAdType() {
            return this.adType;
        }

        public String getAdWorker() {
            return this.adWorker;
        }

        public String getAppId() {
            return this.appId;
        }

        public double getAvgPrice() {
            return this.avgPrice;
        }

        public double getBidPrice() {
            return this.bidPrice;
        }

        public String getBidType() {
            return this.bidType;
        }

        public String getBuyerSpaceUuId() {
            return this.buyerSpaceUuId;
        }

        public int getCache() {
            return this.cache;
        }

        public List<CallBackStrategyBean> getCallBackStrategy() {
            return this.callBackStrategy;
        }

        public String getCallBackStrategyUuid() {
            return this.callBackStrategyUuid;
        }

        public int getDirectDownload() {
            return this.directDownload;
        }

        public DislikeConfigBean getDislikeConfig() {
            return this.dislikeConfig;
        }

        public FilterBean getFilter() {
            return this.filter;
        }

        public String getFilterSsid() {
            return this.filterSsid;
        }

        public FullScreenClickBean getFullScreenClick() {
            return this.fullScreenClick;
        }

        public String getId() {
            return this.id;
        }

        public List<RenderRulesBean> getInteractionRules() {
            return this.interactionRules;
        }

        public List<PriceDictBean> getPriceDict() {
            return this.priceDict;
        }

        public RegionalClickViewBean getRegionalClickView() {
            return this.regionalClickView;
        }

        public RenderAds getRenderAds() {
            return this.renderAds;
        }

        public List<RenderViewBean> getRenderView() {
            return this.renderView;
        }

        public String getRenderViewSsid() {
            return this.renderViewSsid;
        }

        public int getReqTimeOutType() {
            return this.reqTimeOutType;
        }

        public RollViewBean getRollView() {
            return this.rollView;
        }

        public ScrollClickBean getScrollClick() {
            return this.scrollClick;
        }

        public ShakeViewBean getShakeView() {
            return this.shakeView;
        }

        public int getSizeRatio() {
            return this.sizeRatio;
        }

        public String getSpaceId() {
            return this.spaceId;
        }

        public String getZone() {
            return this.zone;
        }

        public void setAdSize(AdSizeBean adSizeBean) {
            this.adSize = adSizeBean;
        }

        public void setAdType(String str) {
            this.adType = str;
        }

        public void setAdWorker(String str) {
            this.adWorker = str;
        }

        public void setAppId(String str) {
            this.appId = str;
        }

        public void setAvgPrice(double d3) {
            this.avgPrice = d3;
        }

        public void setBidPrice(double d3) {
            this.bidPrice = d3;
        }

        public void setBidType(String str) {
            this.bidType = str;
        }

        public void setBuyerSpaceUuId(String str) {
            this.buyerSpaceUuId = str;
        }

        public void setCache(int i2) {
            this.cache = i2;
        }

        public void setCallBackStrategy(List<CallBackStrategyBean> list) {
            this.callBackStrategy = list;
        }

        public void setCallBackStrategyUuid(String str) {
            this.callBackStrategyUuid = str;
        }

        public void setDirectDownload(int i2) {
            this.directDownload = i2;
        }

        public void setDislikeConfig(DislikeConfigBean dislikeConfigBean) {
            this.dislikeConfig = dislikeConfigBean;
        }

        public void setFilter(FilterBean filterBean) {
            this.filter = filterBean;
        }

        public void setFilterSsid(String str) {
            this.filterSsid = str;
        }

        public void setFullScreenClick(FullScreenClickBean fullScreenClickBean) {
            this.fullScreenClick = fullScreenClickBean;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setInteractionRules(List<RenderRulesBean> list) {
            this.interactionRules = list;
        }

        public void setPriceDict(List<PriceDictBean> list) {
            this.priceDict = list;
        }

        public void setRegionalClickView(RegionalClickViewBean regionalClickViewBean) {
            this.regionalClickView = regionalClickViewBean;
        }

        public void setRenderAds(RenderAds renderAds) {
            this.renderAds = renderAds;
        }

        public void setRenderView(List<RenderViewBean> list) {
            this.renderView = list;
        }

        public void setRenderViewSsid(String str) {
            this.renderViewSsid = str;
        }

        public void setReqTimeOutType(int i2) {
            this.reqTimeOutType = i2;
        }

        public void setRollView(RollViewBean rollViewBean) {
            this.rollView = rollViewBean;
        }

        public void setScrollClick(ScrollClickBean scrollClickBean) {
            this.scrollClick = scrollClickBean;
        }

        public void setShakeView(ShakeViewBean shakeViewBean) {
            this.shakeView = shakeViewBean;
        }

        public void setSizeRatio(int i2) {
            this.sizeRatio = i2;
        }

        public void setSpaceId(String str) {
            this.spaceId = str;
        }

        public void setZone(String str) {
            this.zone = str;
        }

        @NonNull
        public String toString() {
            return "BuyerBean{id='" + this.id + CharPool.SINGLE_QUOTE + ", buyerSpaceUuId='" + this.buyerSpaceUuId + CharPool.SINGLE_QUOTE + ", zone='" + this.zone + CharPool.SINGLE_QUOTE + ", appId='" + this.appId + CharPool.SINGLE_QUOTE + ", spaceId='" + this.spaceId + CharPool.SINGLE_QUOTE + ", avgPrice=" + this.avgPrice + ", adWorker='" + this.adWorker + CharPool.SINGLE_QUOTE + ", filter=" + this.filter + ", reqTimeOutType=" + this.reqTimeOutType + ", renderView=" + this.renderView + ", adSize=" + this.adSize + ", directDownload=" + this.directDownload + ", renderViewSsid='" + this.renderViewSsid + CharPool.SINGLE_QUOTE + ", filterSsid='" + this.filterSsid + CharPool.SINGLE_QUOTE + ", bidType='" + this.bidType + CharPool.SINGLE_QUOTE + ", cache='" + this.cache + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public static class CallBackStrategyBean {

        @JsonNode(key = "count")
        private String count;

        @JsonNode(key = "eventCode")
        private String eventCode;

        @JsonNode(key = am.aT)
        private String interval;

        @JsonNode(key = "rate")
        private String rate;

        public String getCount() {
            return this.count;
        }

        public String getEventCode() {
            return this.eventCode;
        }

        public String getInterval() {
            return this.interval;
        }

        public String getRate() {
            return this.rate;
        }

        public void setCount(String str) {
            this.count = str;
        }

        public void setEventCode(String str) {
            this.eventCode = str;
        }

        public void setInterval(String str) {
            this.interval = str;
        }

        public void setRate(String str) {
            this.rate = str;
        }
    }

    public static class ComponentBean {

        @JsonNode(key = "content")
        private String content;

        @JsonNode(key = "forward")
        private List<ForwardBean> forward;

        public String getContent() {
            return this.content;
        }

        public List<ForwardBean> getForward() {
            return this.forward;
        }

        public void setContent(String str) {
            this.content = str;
        }

        public void setForward(List<ForwardBean> list) {
            this.forward = list;
        }

        @NonNull
        public String toString() {
            return "ComponentBean{content='" + this.content + CharPool.SINGLE_QUOTE + ", forward=" + this.forward + '}';
        }
    }

    public static class DpLinkUrlListBean {

        @JsonNode(key = "dpLinkUrL")
        private String dpLinkUrL;

        @JsonNode(key = "optimizePercent")
        private int optimizePercent;

        public static DpLinkUrlListBean objectFromData(String str) {
            try {
                return (DpLinkUrlListBean) JsonResolver.fromJson(str, DpLinkUrlListBean.class);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public String getDpLinkUrL() {
            return this.dpLinkUrL;
        }

        public int getOptimizePercent() {
            return this.optimizePercent;
        }

        public void setDpLinkUrL(String str) {
            this.dpLinkUrL = str;
        }

        public void setOptimizePercent(int i2) {
            this.optimizePercent = i2;
        }

        public String toString() {
            return "DpLinkUrlListBean{dpLinkUrL='" + this.dpLinkUrL + CharPool.SINGLE_QUOTE + ", optimizePercent=" + this.optimizePercent + '}';
        }
    }

    public static class EventStrategyBean {

        @JsonNode(key = "randomStrategy")
        private List<RandomStrategyBean> randomStrategy;

        @JsonNode(key = "validTimeShow")
        private int validTimeShow;

        public List<RandomStrategyBean> getRandomStrategy() {
            return this.randomStrategy;
        }

        public int getValidTimeShow() {
            return this.validTimeShow;
        }

        public void setRandomStrategy(List<RandomStrategyBean> list) {
            this.randomStrategy = list;
        }

        public void setValidTimeShow(int i2) {
            this.validTimeShow = i2;
        }
    }

    public static class FilterBean {

        @JsonNode(key = "frequency")
        private List<FreqItem> frequency;

        @JsonNode(key = "imeiLength")
        private List<Integer> imeiLength;

        @JsonNode(key = "minAdLoadTime")
        private int minAdLoadTime;

        @JsonNode(key = "privilege")
        private List<String> privilege;

        public static FilterBean objectFromData(String str) {
            try {
                return (FilterBean) JsonResolver.fromJson(str, FilterBean.class);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public List<FreqItem> getFrequency() {
            return this.frequency;
        }

        public List<Integer> getImeiLength() {
            return this.imeiLength;
        }

        public int getMinAdLoadTime() {
            return this.minAdLoadTime;
        }

        public List<String> getPrivilege() {
            return this.privilege;
        }

        public void setFrequency(List<FreqItem> list) {
            this.frequency = list;
        }

        public void setImeiLength(List<Integer> list) {
            this.imeiLength = list;
        }

        public void setMinAdLoadTime(int i2) {
            this.minAdLoadTime = i2;
        }

        public void setPrivilege(List<String> list) {
            this.privilege = list;
        }

        public String toString() {
            return "FilterBean{minAdLoadTime=" + this.minAdLoadTime + ", privilege=" + this.privilege + ", imeiLength=" + this.imeiLength + ", frequency=" + this.frequency + '}';
        }
    }

    public static class ForwardBean implements Cloneable {

        @JsonNode(key = "baseId")
        private String baseId;

        @JsonNode(key = "buyerId")
        private String buyerId;

        @JsonNode(key = "buyerSpaceUuId")
        private String buyerSpaceUuId;

        @JsonNode(key = "component")
        private ComponentBean component;

        @JsonNode(key = "forwardId")
        private String forwardId;

        @JsonNode(key = "hotRequestDelay")
        private long hotRequestDelay;

        @JsonNode(key = DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL)
        private String level;

        @JsonNode(key = "parentForwardId")
        private String parentForwardId;

        @JsonNode(key = "rules")
        private List<RulesBean> rules;

        @JsonNode(key = "sleepTime")
        private long sleepTime;

        public static ForwardBean objectFromData(String str) {
            try {
                return (ForwardBean) JsonResolver.fromJson(str, ForwardBean.class);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public String getBaseId() {
            return this.baseId;
        }

        public String getBuyerId() {
            return this.buyerId;
        }

        public String getBuyerSpaceUuId() {
            return this.buyerSpaceUuId;
        }

        public ComponentBean getComponent() {
            return this.component;
        }

        public String getForwardId() {
            return this.forwardId;
        }

        public long getHotRequestDelay() {
            return this.hotRequestDelay;
        }

        public String getLevel() {
            return this.level;
        }

        public String getParentForwardId() {
            return this.parentForwardId;
        }

        public List<RulesBean> getRules() {
            return this.rules;
        }

        public long getSleepTime() {
            return this.sleepTime;
        }

        public void setBaseId(String str) {
            this.baseId = str;
        }

        public void setBuyerId(String str) {
            this.buyerId = str;
        }

        public void setBuyerSpaceUuId(String str) {
            this.buyerSpaceUuId = str;
        }

        public void setComponent(ComponentBean componentBean) {
            this.component = componentBean;
        }

        public void setForwardId(String str) {
            this.forwardId = str;
        }

        public void setHotRequestDelay(long j2) {
            this.hotRequestDelay = j2;
        }

        public void setLevel(String str) {
            this.level = str;
        }

        public void setParentForwardId(String str) {
            this.parentForwardId = str;
        }

        public void setRules(List<RulesBean> list) {
            this.rules = list;
        }

        public void setSleepTime(long j2) {
            this.sleepTime = j2;
        }

        public String toString() {
            return "ForwardBean{buyerId='" + this.buyerId + CharPool.SINGLE_QUOTE + ", baseId='" + this.baseId + CharPool.SINGLE_QUOTE + ", sleepTime=" + this.sleepTime + ", hotRequestDelay=" + this.hotRequestDelay + ", forwardId='" + this.forwardId + CharPool.SINGLE_QUOTE + ", parentForwardId='" + this.parentForwardId + CharPool.SINGLE_QUOTE + ", level='" + this.level + CharPool.SINGLE_QUOTE + ", buyerSpaceUuId='" + this.buyerSpaceUuId + CharPool.SINGLE_QUOTE + ", component=" + this.component + ", rules=" + this.rules + '}';
        }
    }

    public static class PassPolicyBean {

        @JsonNode(key = "passPercent")
        private int passPercent;

        @JsonNode(key = "passType")
        private String passType;

        public static PassPolicyBean objectFromData(String str) {
            try {
                return (PassPolicyBean) JsonResolver.fromJson(str, PassPolicyBean.class);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public int getPassPercent() {
            return this.passPercent;
        }

        public String getPassType() {
            return this.passType;
        }

        public void setPassPercent(int i2) {
            this.passPercent = i2;
        }

        public void setPassType(String str) {
            this.passType = str;
        }

        public String toString() {
            return "passPolicyBean{passType='" + this.passType + CharPool.SINGLE_QUOTE + ", passPercent=" + this.passPercent + '}';
        }
    }

    public static class PositionBean {

        @JsonNode(key = "centerX")
        private double centerX;

        @JsonNode(key = "centerY")
        private double centerY;

        @JsonNode(key = "height")
        private double height;

        @JsonNode(key = "width")
        private double width;

        public static PositionBean objectFromData(String str) {
            try {
                return (PositionBean) JsonResolver.fromJson(str, PositionBean.class);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public double getCenterX() {
            return this.centerX;
        }

        public double getCenterY() {
            return this.centerY;
        }

        public double getHeight() {
            return this.height;
        }

        public double getWidth() {
            return this.width;
        }

        public void setCenterX(double d3) {
            this.centerX = d3;
        }

        public void setCenterY(double d3) {
            this.centerY = d3;
        }

        public void setHeight(double d3) {
            this.height = d3;
        }

        public void setWidth(double d3) {
            this.width = d3;
        }

        public String toString() {
            return "PositionBean{centerX=" + this.centerX + ", centerY=" + this.centerY + ", width=" + this.width + ", height=" + this.height + '}';
        }
    }

    public static class RandomStrategyBean {

        @JsonNode(key = "action")
        private int action;

        @JsonNode(key = "eventType")
        private String eventType;

        @JsonNode(key = "max")
        private int max;

        @JsonNode(key = "min")
        private int min;

        public int getAction() {
            return this.action;
        }

        public String getEventType() {
            return this.eventType;
        }

        public int getMax() {
            return this.max;
        }

        public int getMin() {
            return this.min;
        }

        public void setAction(int i2) {
            this.action = i2;
        }

        public void setEventType(String str) {
            this.eventType = str;
        }

        public void setMax(int i2) {
            this.max = i2;
        }

        public void setMin(int i2) {
            this.min = i2;
        }
    }

    public static class RenderViewBean {

        @JsonNode(key = "borderHeight")
        private String borderHeight;

        @JsonNode(key = "borderWidth")
        private String borderWidth;

        @JsonNode(key = "clickNum")
        private int clickNum;

        @JsonNode(key = "countDownColor")
        private String countDownColor;

        @JsonNode(key = "delayDisplaySkipButton")
        private long delayDisplaySkipButton;

        @JsonNode(key = HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION)
        private String direction;

        @JsonNode(key = "dpLinkUrlList")
        private List<DpLinkUrlListBean> dpLinkUrlList;

        @JsonNode(key = "imageUrl")
        private String imageUrl;

        @JsonNode(key = "layerPosition")
        private PositionBean layerPosition;

        @JsonNode(key = DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL)
        private int level;

        @JsonNode(key = "location")
        private String location;

        @JsonNode(key = "optimizePercent")
        private int optimizePercent;

        @JsonNode(key = "optimizeSize")
        private int optimizeSize;

        @JsonNode(key = "paddingHeight")
        private double paddingHeight;

        @JsonNode(key = "passPolicy")
        private List<PassPolicyBean> passPolicy;

        @JsonNode(key = "picSkipTime")
        private int picSkipTime;

        @JsonNode(key = "scrollDistance")
        private String scrollDistance;

        @JsonNode(key = "scrollDistancePercent")
        private int scrollDistancePercent;

        @JsonNode(key = "showBorder")
        private int showBorder;

        @JsonNode(key = "showCountDown")
        private int showCountDown;

        @JsonNode(key = DatabaseManager.SIZE)
        private String size;

        @JsonNode(key = "skipText")
        private String skipText;

        @JsonNode(key = "skipUnavailableTime")
        private long skipUnavailableTime;

        @JsonNode(key = "skipViewTotalTime")
        private long skipViewTotalTime;

        @JsonNode(key = "tapPosition")
        private PositionBean tapPosition;

        @JsonNode(key = "textColor")
        private String textColor;

        @JsonNode(key = "type")
        private String type;

        @JsonNode(key = "videoSkipTime")
        private int videoSkipTime;

        public static RenderViewBean objectFromData(String str) {
            try {
                return (RenderViewBean) JsonResolver.fromJson(str, RenderViewBean.class);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public String getBorderHeight() {
            return this.borderHeight;
        }

        public String getBorderWidth() {
            return this.borderWidth;
        }

        public int getClickNum() {
            return this.clickNum;
        }

        public String getCountDownColor() {
            return this.countDownColor;
        }

        public long getDelayDisplaySkipButton() {
            return this.delayDisplaySkipButton;
        }

        public String getDirection() {
            return this.direction;
        }

        public List<DpLinkUrlListBean> getDpLinkUrlList() {
            return this.dpLinkUrlList;
        }

        public String getImageUrl() {
            return this.imageUrl;
        }

        public PositionBean getLayerPosition() {
            return this.layerPosition;
        }

        public int getLevel() {
            return this.level;
        }

        public String getLocation() {
            return this.location;
        }

        public int getOptimizePercent() {
            return this.optimizePercent;
        }

        public int getOptimizeSize() {
            return this.optimizeSize;
        }

        public double getPaddingHeight() {
            return this.paddingHeight;
        }

        public List<PassPolicyBean> getPassPolicy() {
            return this.passPolicy;
        }

        public int getPicSkipTime() {
            return this.picSkipTime;
        }

        public String getScrollDistance() {
            return this.scrollDistance;
        }

        public int getScrollDistancePercent() {
            return this.scrollDistancePercent;
        }

        public int getShowBorder() {
            return this.showBorder;
        }

        public int getShowCountDown() {
            return this.showCountDown;
        }

        public String getSize() {
            return this.size;
        }

        public String getSkipText() {
            return this.skipText;
        }

        public long getSkipUnavailableTime() {
            return this.skipUnavailableTime;
        }

        public long getSkipViewTotalTime() {
            return this.skipViewTotalTime;
        }

        public PositionBean getTapPosition() {
            return this.tapPosition;
        }

        public String getTextColor() {
            return this.textColor;
        }

        public String getType() {
            return this.type;
        }

        public int getVideoSkipTime() {
            return this.videoSkipTime;
        }

        public void setBorderHeight(String str) {
            this.borderHeight = str;
        }

        public void setBorderWidth(String str) {
            this.borderWidth = str;
        }

        public void setClickNum(int i2) {
            this.clickNum = i2;
        }

        public void setCountDownColor(String str) {
            this.countDownColor = str;
        }

        public void setDelayDisplaySkipButton(long j2) {
            this.delayDisplaySkipButton = j2;
        }

        public void setDirection(String str) {
            this.direction = str;
        }

        public void setDpLinkUrlList(List<DpLinkUrlListBean> list) {
            this.dpLinkUrlList = list;
        }

        public void setImageUrl(String str) {
            this.imageUrl = str;
        }

        public void setLayerPosition(PositionBean positionBean) {
            this.layerPosition = positionBean;
        }

        public void setLevel(int i2) {
            this.level = i2;
        }

        public void setLocation(String str) {
            this.location = str;
        }

        public void setOptimizePercent(int i2) {
            this.optimizePercent = i2;
        }

        public void setOptimizeSize(int i2) {
            this.optimizeSize = i2;
        }

        public void setPaddingHeight(double d3) {
            this.paddingHeight = d3;
        }

        public void setPassPolicy(List<PassPolicyBean> list) {
            this.passPolicy = list;
        }

        public void setPicSkipTime(int i2) {
            this.picSkipTime = i2;
        }

        public void setScrollDistance(String str) {
            this.scrollDistance = str;
        }

        public void setScrollDistancePercent(int i2) {
            this.scrollDistancePercent = i2;
        }

        public void setShowBorder(int i2) {
            this.showBorder = i2;
        }

        public void setShowCountDown(int i2) {
            this.showCountDown = i2;
        }

        public void setSize(String str) {
            this.size = str;
        }

        public void setSkipText(String str) {
            this.skipText = str;
        }

        public void setSkipUnavailableTime(long j2) {
            this.skipUnavailableTime = j2;
        }

        public void setSkipViewTotalTime(long j2) {
            this.skipViewTotalTime = j2;
        }

        public void setTapPosition(PositionBean positionBean) {
            this.tapPosition = positionBean;
        }

        public void setTextColor(String str) {
            this.textColor = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public void setVideoSkipTime(int i2) {
            this.videoSkipTime = i2;
        }

        public String toString() {
            return "RenderViewBean{type='" + this.type + CharPool.SINGLE_QUOTE + ", dpLinkUrlList=" + this.dpLinkUrlList + ", optimizePercent=" + this.optimizePercent + ", optimizeSize=" + this.optimizeSize + ", scrollDistance='" + this.scrollDistance + CharPool.SINGLE_QUOTE + ", scrollDistancePercent=" + this.scrollDistancePercent + ", direction='" + this.direction + CharPool.SINGLE_QUOTE + ", level=" + this.level + ", skipUnavailableTime=" + this.skipUnavailableTime + ", skipViewTotalTime=" + this.skipViewTotalTime + ", layerPosition=" + this.layerPosition + ", tapPosition=" + this.tapPosition + ", imageUrl='" + this.imageUrl + CharPool.SINGLE_QUOTE + ", passPolicy=" + this.passPolicy + ", delayDisplaySkipButton=" + this.delayDisplaySkipButton + ", paddingHeight=" + this.paddingHeight + ", skipText='" + this.skipText + CharPool.SINGLE_QUOTE + ", textColor='" + this.textColor + CharPool.SINGLE_QUOTE + ", showCountDown=" + this.showCountDown + ", countDownColor='" + this.countDownColor + CharPool.SINGLE_QUOTE + ", showBorder=" + this.showBorder + '}';
        }
    }

    public static class RulesBean {

        @JsonNode(key = "baseId")
        private String baseId;

        @JsonNode(key = "formula")
        private String formula;

        @JsonNode(key = "results")
        private Integer[] results;

        @JsonNode(key = "rules")
        private List<RulesBean> rules;

        public String getBaseId() {
            return this.baseId;
        }

        public String getFormula() {
            return this.formula;
        }

        public Integer[] getResults() {
            return this.results;
        }

        public List<RulesBean> getRules() {
            return this.rules;
        }

        public void setBaseId(String str) {
            this.baseId = str;
        }

        public void setFormula(String str) {
            this.formula = str;
        }

        public void setResults(Integer[] numArr) {
            this.results = numArr;
        }

        public void setRules(List<RulesBean> list) {
            this.rules = list;
        }

        @NonNull
        public String toString() {
            return "RulesBean{formula='" + this.formula + CharPool.SINGLE_QUOTE + ", results=" + Arrays.toString(this.results) + ", rules=" + this.rules + '}';
        }
    }

    public static AdSpacesBean objectFromData(String str) {
        try {
            return (AdSpacesBean) JsonResolver.fromJson(str, AdSpacesBean.class);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public String getAdType() {
        return this.adType;
    }

    public String getAppId() {
        return this.appId;
    }

    public BidBean getBid() {
        return this.bid;
    }

    public BidComponent getBidComponent() {
        return this.bidComponent;
    }

    public List<BuyerBean> getBuyer() {
        return this.buyer;
    }

    public String getBzComponentSsid() {
        return this.bzComponentSsid;
    }

    public ComponentBean getComponent() {
        return this.component;
    }

    public String getComponentSsid() {
        return this.componentSsid;
    }

    public EventStrategyBean getEventStrategy() {
        return this.eventStrategy;
    }

    public FilterBean getFilter() {
        return this.filter;
    }

    public String getFilterSsid() {
        return this.filterSsid;
    }

    public int getModelType() {
        return this.modelType;
    }

    public String getSpaceId() {
        return this.spaceId;
    }

    public void setAdType(String str) {
        this.adType = str;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public void setBid(BidBean bidBean) {
        this.bid = bidBean;
    }

    public void setBidComponent(BidComponent bidComponent) {
        this.bidComponent = bidComponent;
    }

    public void setBuyer(List<BuyerBean> list) {
        this.buyer = list;
    }

    public void setBzComponentSsid(String str) {
        this.bzComponentSsid = str;
    }

    public void setComponent(ComponentBean componentBean) {
        this.component = componentBean;
    }

    public void setComponentSsid(String str) {
        this.componentSsid = str;
    }

    public void setEventStrategy(EventStrategyBean eventStrategyBean) {
        this.eventStrategy = eventStrategyBean;
    }

    public void setFilter(FilterBean filterBean) {
        this.filter = filterBean;
    }

    public void setFilterSsid(String str) {
        this.filterSsid = str;
    }

    public void setModelType(int i2) {
        this.modelType = i2;
    }

    public void setSpaceId(String str) {
        this.spaceId = str;
    }

    @NonNull
    public String toString() {
        return "AdSpacesBean{spaceId='" + this.spaceId + CharPool.SINGLE_QUOTE + ", appId='" + this.appId + CharPool.SINGLE_QUOTE + ", adType='" + this.adType + CharPool.SINGLE_QUOTE + ", modelType=" + this.modelType + ", filter=" + this.filter + ", bid=" + this.bid + ", buyer=" + this.buyer + ", component=" + this.component + ", eventStrategy=" + this.eventStrategy + '}';
    }
}
