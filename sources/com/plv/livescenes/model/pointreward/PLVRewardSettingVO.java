package com.plv.livescenes.model.pointreward;

import com.plv.foundationsdk.model.PLVFoundationVO;
import java.math.BigDecimal;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVRewardSettingVO implements PLVFoundationVO {
    private CashDonateDTO cashDonate;
    private String donateCashEnabled;
    private String donateGiftEnabled;
    private GiftDonateDTO giftDonate;

    public static class CashDonateDTO {
        private String cashMin;
        private List<String> cashs;

        public String getCashMin() {
            return this.cashMin;
        }

        public List<String> getCashs() {
            return this.cashs;
        }

        public void setCashMin(String str) {
            this.cashMin = str;
        }

        public void setCashs(List<String> list) {
            this.cashs = list;
        }
    }

    public static class GiftDonateDTO {
        private List<GiftDetailDTO> cashPays;
        private String cashUnit;
        private String payWay;
        private List<GiftDetailDTO> pointPays;
        private String pointUnit;

        public static class GiftDetailDTO {
            private String enabled;
            private int goodId;
            private String img;
            private String name;
            private String price;
            private Integer sequence;

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

            public boolean isFree() {
                return new BigDecimal(this.price).compareTo(BigDecimal.ZERO) <= 0;
            }

            public void setEnabled(String str) {
                this.enabled = str;
            }

            public void setGoodId(int i2) {
                this.goodId = i2;
            }

            public void setImg(String str) {
                this.img = str;
            }

            public void setName(String str) {
                this.name = str;
            }

            public void setPrice(String str) {
                this.price = str;
            }

            public void setSequence(Integer num) {
                this.sequence = num;
            }
        }

        public List<GiftDetailDTO> getCashPays() {
            return this.cashPays;
        }

        public String getCashUnit() {
            return this.cashUnit;
        }

        public String getPayWay() {
            return this.payWay;
        }

        public List<GiftDetailDTO> getPointPays() {
            return this.pointPays;
        }

        public String getPointUnit() {
            return this.pointUnit;
        }

        public void setCashPays(List<GiftDetailDTO> list) {
            this.cashPays = list;
        }

        public void setCashUnit(String str) {
            this.cashUnit = str;
        }

        public void setPayWay(String str) {
            this.payWay = str;
        }

        public void setPointPays(List<GiftDetailDTO> list) {
            this.pointPays = list;
        }

        public void setPointUnit(String str) {
            this.pointUnit = str;
        }
    }

    public CashDonateDTO getCashDonate() {
        return this.cashDonate;
    }

    public boolean getDonateCashEnabled() {
        return "Y".equals(this.donateCashEnabled);
    }

    public boolean getDonateGiftEnabled() {
        return "Y".equals(this.donateGiftEnabled);
    }

    public GiftDonateDTO getGiftDonate() {
        return this.giftDonate;
    }

    public void setCashDonate(CashDonateDTO cashDonateDTO) {
        this.cashDonate = cashDonateDTO;
    }

    public void setDonateCashEnabled(String str) {
        this.donateCashEnabled = str;
    }

    public void setDonateGiftEnabled(String str) {
        this.donateGiftEnabled = str;
    }

    public void setGiftDonate(GiftDonateDTO giftDonateDTO) {
        this.giftDonate = giftDonateDTO;
    }
}
