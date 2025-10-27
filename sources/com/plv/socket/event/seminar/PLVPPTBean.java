package com.plv.socket.event.seminar;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVPPTBean implements PLVFoundationVO {
    private String autoId;
    private Double backLeft;
    private Double backTop;
    private Double clientWidth;
    private Double height;
    private Double left;
    private Double pageId;
    private Double step;

    /* renamed from: top, reason: collision with root package name */
    private Double f10947top;
    private Double width;
    private Double zoom;

    public String getAutoId() {
        return this.autoId;
    }

    public Double getBackLeft() {
        return this.backLeft;
    }

    public Double getBackTop() {
        return this.backTop;
    }

    public Double getClientWidth() {
        return this.clientWidth;
    }

    public Double getHeight() {
        return this.height;
    }

    public Double getLeft() {
        return this.left;
    }

    public Double getPageId() {
        return this.pageId;
    }

    public Double getStep() {
        return this.step;
    }

    public Double getTop() {
        return this.f10947top;
    }

    public Double getWidth() {
        return this.width;
    }

    public Double getZoom() {
        return this.zoom;
    }

    public void setAutoId(String str) {
        this.autoId = str;
    }

    public void setBackLeft(Double d3) {
        this.backLeft = d3;
    }

    public void setBackTop(Double d3) {
        this.backTop = d3;
    }

    public void setClientWidth(Double d3) {
        this.clientWidth = d3;
    }

    public void setHeight(Double d3) {
        this.height = d3;
    }

    public void setLeft(Double d3) {
        this.left = d3;
    }

    public void setPageId(Double d3) {
        this.pageId = d3;
    }

    public void setStep(Double d3) {
        this.step = d3;
    }

    public void setTop(Double d3) {
        this.f10947top = d3;
    }

    public void setWidth(Double d3) {
        this.width = d3;
    }

    public void setZoom(Double d3) {
        this.zoom = d3;
    }

    public String toString() {
        return "PLVPPTBean{autoId='" + this.autoId + CharPool.SINGLE_QUOTE + ", backLeft=" + this.backLeft + ", backTop=" + this.backTop + ", clientWidth=" + this.clientWidth + ", height=" + this.height + ", left=" + this.left + ", pageId=" + this.pageId + ", step=" + this.step + ", top=" + this.f10947top + ", width=" + this.width + ", zoom=" + this.zoom + '}';
    }
}
