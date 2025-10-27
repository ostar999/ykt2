package com.plv.business.model.video;

import com.easefun.polyv.businesssdk.model.video.PolyvDefinitionVO;
import com.plv.business.model.PLVBaseVO;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVBitrateVO implements PLVBaseVO {
    private static final String CLARITY = "clarity";
    private static final String FLUENCY = "fluency";
    private static final String HIGH = "high";
    private String defaultDefinition;
    private String defaultDefinitionUrl;
    private List<PolyvDefinitionVO> definitions;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DEFINITION {
    }

    public String getDefaultDefinition() {
        return this.defaultDefinition;
    }

    public String getDefaultDefinitionUrl() {
        return this.defaultDefinitionUrl;
    }

    public List<PolyvDefinitionVO> getDefinitions() {
        if (this.definitions == null) {
            this.definitions = new ArrayList();
        }
        return this.definitions;
    }

    public void setDefaultDefinition(String str) {
        this.defaultDefinition = str;
    }

    public void setDefaultDefinitionUrl(String str) {
        this.defaultDefinitionUrl = str;
    }

    public void setDefinitions(List<PolyvDefinitionVO> list) {
        this.definitions = list;
    }
}
