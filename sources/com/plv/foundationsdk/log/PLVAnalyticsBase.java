package com.plv.foundationsdk.log;

import com.plv.foundationsdk.model.log.PLVProjectInfo;
import java.util.HashMap;

/* loaded from: classes4.dex */
public abstract class PLVAnalyticsBase {
    protected static HashMap<String, PLVProjectInfo> projectInfos = new HashMap<>();

    public abstract PLVProjectInfo buildProjectInfo();

    public PLVProjectInfo createProjectInfo() {
        if (!projectInfos.containsKey(projectName())) {
            projectInfos.put(projectName(), buildProjectInfo());
        }
        return projectInfos.get(projectName()) != null ? projectInfos.get(projectName()) : new PLVProjectInfo("", "", "", "");
    }

    public abstract String projectName();
}
