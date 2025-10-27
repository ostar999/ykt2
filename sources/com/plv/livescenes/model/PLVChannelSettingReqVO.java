package com.plv.livescenes.model;

/* loaded from: classes5.dex */
public class PLVChannelSettingReqVO {
    private BasicSettingBean basicSetting;

    public static class BasicSettingBean {
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }
    }

    public BasicSettingBean getBasicSetting() {
        return this.basicSetting;
    }

    public void setBasicSetting(BasicSettingBean basicSettingBean) {
        this.basicSetting = basicSettingBean;
    }
}
