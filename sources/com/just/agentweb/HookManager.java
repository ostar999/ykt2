package com.just.agentweb;

import com.just.agentweb.AgentWeb;

/* loaded from: classes4.dex */
public class HookManager {
    public static AgentWeb hookAgentWeb(AgentWeb agentWeb, AgentWeb.AgentBuilder agentBuilder) {
        return agentWeb;
    }

    public static boolean permissionHook(String str, String[] strArr) {
        return true;
    }
}
