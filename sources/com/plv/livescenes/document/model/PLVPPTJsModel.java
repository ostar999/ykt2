package com.plv.livescenes.document.model;

import com.easefun.polyv.livescenes.document.model.PLVSPPTDetail;
import com.plv.foundationsdk.model.PLVFoundationVO;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVPPTJsModel implements PLVFoundationVO {
    private int autoId;
    private String fileName;
    private List<PLVSPPTDetail> pptImages;
    private List<String> smallImages;

    public int getAutoId() {
        return this.autoId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public List<PLVSPPTDetail> getPPTImages() {
        List<PLVSPPTDetail> list = this.pptImages;
        if (list != null) {
            return list;
        }
        this.pptImages = new ArrayList(this.smallImages.size());
        int size = this.smallImages.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.pptImages.add(new PLVSPPTDetail(this.autoId, this.smallImages.get(i2), i2));
            if (i2 == 0) {
                this.pptImages.get(0).setSelected(true);
            }
        }
        return this.pptImages;
    }

    public List<String> getSmallImages() {
        return this.smallImages;
    }

    public void setAutoId(int i2) {
        this.autoId = i2;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setSmallImages(List<String> list) {
        this.smallImages = list;
    }
}
