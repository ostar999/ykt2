package com.plv.livescenes.model.interact;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVChatFunctionVO implements PLVFoundationVO {
    private String icon;
    private int imageResourceId;
    private boolean isSelected;
    private boolean isShow;
    private String name;
    private String type;

    public PLVChatFunctionVO() {
    }

    public String getIcon() {
        return this.icon;
    }

    public int getImageResourceId() {
        return this.imageResourceId;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public boolean isShow() {
        return this.isShow;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public void setImageResourceId(int i2) {
        this.imageResourceId = i2;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setSelected(boolean z2) {
        this.isSelected = z2;
    }

    public void setShow(boolean z2) {
        this.isShow = z2;
    }

    public void setType(String str) {
        this.type = str;
    }

    public PLVChatFunctionVO(String str, int i2, String str2) {
        this.type = str;
        this.imageResourceId = i2;
        this.name = str2;
    }

    public PLVChatFunctionVO(String str, int i2, String str2, boolean z2) {
        this.type = str;
        this.imageResourceId = i2;
        this.name = str2;
        this.isShow = z2;
    }

    public PLVChatFunctionVO(String str, String str2, boolean z2, String str3) {
        this.type = str;
        this.name = str2;
        this.isShow = z2;
        this.icon = str3;
    }
}
