package com.just.agentweb;

import com.hjq.permissions.Permission;

/* loaded from: classes4.dex */
public class AgentWebPermissions {
    public static final String ACTION_CAMERA = "Camera";
    public static final String ACTION_LOCATION = "Location";
    public static final String ACTION_STORAGE = "Storage";
    public static final String[] CAMERA = {Permission.CAMERA};
    public static final String[] LOCATION = {Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION};
    public static final String[] STORAGE = {Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE};
}
