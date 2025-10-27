package com.psychiatrygarden.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.interfaceclass.ActivityState;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks, ActivityState {
    public List<Activity> activityList = new ArrayList();
    public List<Activity> resumeActivity = new ArrayList();

    @Override // com.psychiatrygarden.interfaceclass.ActivityState
    public int count() {
        return this.activityList.size();
    }

    @Override // com.psychiatrygarden.interfaceclass.ActivityState
    public Activity current() {
        if (this.activityList.size() > 0) {
            return this.activityList.get(0);
        }
        return null;
    }

    @Override // com.psychiatrygarden.interfaceclass.ActivityState
    public boolean isFront() {
        return this.resumeActivity.size() > 0;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        this.activityList.add(0, activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        this.activityList.remove(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (!this.resumeActivity.contains(activity)) {
            this.resumeActivity.add(activity);
        }
        if (activity.getClass().getName().contains("com.psychiatrygarden.activity.circleactivity") || activity.getClass().getName().contains("com.psychiatrygarden.activity.forum") || activity.getClass().getName().equals("com.psychiatrygarden.activity.courselist.CourseListChpterActivity")) {
            if ((!activity.getClass().getName().equals("com.psychiatrygarden.activity.courselist.CourseListChpterActivity") || activity.getIntent().getBooleanExtra("isForumTrue", false)) && "".equals(SharePreferencesUtils.readStrConfig(CommonParameter.forum_time, activity))) {
                SharePreferencesUtils.writeStrConfig(CommonParameter.forum_time, (System.currentTimeMillis() / 1000) + "", activity);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        this.resumeActivity.remove(activity);
        if (activity.getClass().getName().contains("com.psychiatrygarden.activity.circleactivity") || activity.getClass().getName().contains("com.psychiatrygarden.activity.forum") || activity.getClass().getName().equals("com.psychiatrygarden.activity.courselist.CourseListChpterActivity")) {
            if ((!activity.getClass().getName().equals("com.psychiatrygarden.activity.courselist.CourseListChpterActivity") || activity.getIntent().getBooleanExtra("isForumTrue", false)) && !"".equals(SharePreferencesUtils.readStrConfig(CommonParameter.forum_time, activity))) {
                SharePreferencesUtils.writeStrConfig(CommonParameter.forum_time_end, (System.currentTimeMillis() / 1000) + "", activity);
                if (isFront()) {
                    return;
                }
                CommonUtil.putTimeData();
            }
        }
    }
}
